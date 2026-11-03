// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/02/2011 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Convert;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Base class helps to read fldChar from WML or DOCX document.
/// It is created as a result of refactoring WmlFldCharReader and DocxFldCharReader.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxFldCharReaderBase
{
    /// <summary>
    /// The problem with reading fields from WordML and DOCX is that we don't know the type of the field
    /// when we encounter the field char nodes. The actual type of the field must be derived from
    /// text stored in runs that occur between field start and field end or separator nodes.
    /// This text forms a field code. The first word in this code represents the type of the field.
    /// </summary>
    void read(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        boolean isFieldEnd = false;

        // Read attributes.
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fldCharType"*/0:
                {
                    switch (gStringSwitchMap.of(xmlReader.getValue()))
                    {
                        case /*"begin"*/3:
                            readFieldStart(reader, runPr);
                            break;
                        case /*"separate"*/4:
                            readFieldSeparator(reader, runPr);
                            break;
                        case /*"end"*/5:
                            isFieldEnd = true;
                            break;
                        default:
                            // Do nothing.
                            break;
                    }
                    break;
                }
                case /*"dirty"*/1:
                {
                    if (reader.getXmlReader().getFieldNodesStack().getCount() > 0)
                    {
                        FieldChar fieldChar = xmlReader.getFieldNodesStack().peek();
                        fieldChar.isDirty(xmlReader.getValueAsBool());
                    }
                    break;
                }
                case /*"fldLock"*/2:
                {
                    if (reader.getXmlReader().getFieldNodesStack().getCount() > 0)
                    {
                        FieldChar fieldChar = xmlReader.getFieldNodesStack().peek();
                        fieldChar.isLocked(xmlReader.getValueAsBool());
                    }
                    break;
                }
                default:
                    // Do nothing.
                    break;
            }
        }

        // WORDSNET-6774 We should create FieldEnd when all properties FieldEnd are read.
        FieldEnd fieldEnd = isFieldEnd
            ? readFieldEnd(reader, runPr)
            : null;

        // Read child elements.
        while (xmlReader.readChild("fldChar"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fldData"*/6: // WordML.
                    String fieldData = xmlReader.readString();

                    if (reader.isDocx())
                    {
                        // Custom Field Data. It should be stored only.
                        // If current field character is not start, field data may be ignored
                        // (§14.9.5 of ISO 29500, Part 4).
                        FieldChar fieldChar = xmlReader.getFieldNodesStack().peek();
                        if (fieldChar.getNodeType() == NodeType.FIELD_START)
                            ((FieldStart)fieldChar).setFieldData(Convert.fromBase64String(fieldData));
                    }
                    else
                    {
                        // If 'fldChar' has 'fldData' defined, then we should memorize this data and
                        // use it later, when type of this field will become known to us.
                        xmlReader.FieldData = fieldData;
                    }
                    break;
                case /*"numberingChange"*/7:
                case /*"annotation"*/8:
                    if (fieldEnd == null)
                        break;

                    NrxAnnotation annotation = new NrxAnnotation(xmlReader);
                    fieldEnd.setNumberRevision(new FieldNumberRevision(
                        annotation.getAuthor(),
                        annotation.getDate(),
                        annotation.getOriginal()));

                    break;
                default:
                    if (!readFormatSpecificAttribute(xmlReader.getLocalName(), reader, runPr))
                        xmlReader.ignoreElement();
                    break;
            }
        }
    }

    protected abstract boolean readFormatSpecificAttribute(String attrName, NrxDocumentReaderBase reader, RunPr runPr);

    /// <summary>
    /// Reads field data and stores it in a field start node.
    /// </summary>
    static void readFieldData(NrxDocumentReaderBase reader)
    {
        Debug.assertTrue(reader.getCurContainer() != null);
        FieldStart fieldStart = (FieldStart)reader.getCurContainer().getChild(NodeType.FIELD_START, 0, false);
        if (fieldStart != null)
            fieldStart.setFieldData(Convert.fromBase64String(reader.getXmlReader().readString()));
    }

    private static void readFieldStart(NrxDocumentReaderBase reader, RunPr runPr)
    {
        FieldStart fieldStart = new FieldStart(reader.getDocument(), runPr, FieldType.FIELD_NONE);
        reader.addChild(fieldStart);

        reader.getXmlReader().getFieldNodesStack().push(fieldStart);
    }

    private static void readFieldSeparator(NrxDocumentReaderBase reader, RunPr runPr)
    {
        // RESILIENCY 4503 A DOCPROPERTY field contains two separators. Ignore extra separators.
        if (reader.getXmlReader().getFieldNodesStack().getCount() > 0 && reader.getXmlReader().getFieldNodesStack().peek() instanceof FieldSeparator)
            return;

        FieldSeparator fieldSeparator = new FieldSeparator(reader.getDocument(), runPr, FieldType.FIELD_NONE);
        reader.addChild(fieldSeparator);

        reader.getXmlReader().getFieldNodesStack().push(fieldSeparator);
    }

    private static FieldEnd readFieldEnd(NrxDocumentReaderBase reader, RunPr runPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        FieldEnd fieldEnd = new FieldEnd(reader.getDocument(), runPr, FieldType.FIELD_NONE, false);
        reader.addChild(fieldEnd);

        // RK Guard against doubled field ends.
        if (xmlReader.getFieldNodesStack().getCount() == 0)
            return null;

        // Build the field bundle. Field type and whether the field has a separator are set below.
        FieldBundle field = new FieldBundle();
        field.setEnd(fieldEnd);

        // We might have a field separator.
        if (xmlReader.getFieldNodesStack().peek() instanceof FieldSeparator)
            field.setSeparator((FieldSeparator)xmlReader.getFieldNodesStack().pop());

        // WORDSNET-7690 The document contains corrupted field (without FieldStart).
        // We should ignore such fields as MS Word does.
        if (xmlReader.getFieldNodesStack().getCount() == 0)
        {
            field.removeFieldNodes();
            return null;
        }
        else
        {
            field.setStart((FieldStart)xmlReader.getFieldNodesStack().pop());
        }


        // Now we can determine and set field type.
        field.determineFieldType();

        // Synthesize field separator.
        if ((field.getSeparator() == null) &&
            (FieldUtil.getSeparatorPresence(field.getFieldType()) == FieldSeparatorPresence.ALWAYS))
        {
            // At the moment the model assumes that whether the field type has or does not have
            // a separator is determined by the field type. However, in MS Word files, fields
            // that are supposed to have a separator, sometimes do not have it.
            // We synthesize a separator for such fields.
            field.setSeparator(new FieldSeparator(reader.getDocument(), runPr, field.getFieldType()));
            reader.insertChildBefore(reader.getCurContainer(), field.getSeparator(), field.getEnd());
        }

        // Update the HasSeparator status of the field end. Hopefully I can get rid of this later.
        field.getEnd().setHasSeparator(field.getSeparator() != null);
        field.updateDirtyLocked();

        transformField(reader, field);

        return fieldEnd;
    }

    private static void transformField(NrxDocumentReaderBase reader, FieldBundle field) throws Exception
    {
        switch (field.getStart().getFieldType())
        {
            case FieldType.FIELD_FORM_TEXT_INPUT:
            case FieldType.FIELD_FORM_CHECK_BOX:
            case FieldType.FIELD_FORM_DROP_DOWN:
                transformFormField(reader, field);
                break;
            case FieldType.FIELD_INCLUDE_PICTURE:
            case FieldType.FIELD_IMPORT:
                if (!reader.getLoadOptions().getPreserveIncludePictureField())
                    transformIncludePictureField(reader, field);
                break;
            case FieldType.FIELD_EQUATION:
                RubyConverter.transformEquation(field);
                break;
            default:
                // Do nothing.
                break;
        }
    }

    private static void transformFormField(NrxDocumentReaderBase reader, FieldBundle field)
    {
        boolean isDocx = reader.isDocx();
        NrxXmlReader xmlReader = reader.getXmlReader();

        // Get current FormFieldPr (the first one from stack).
        FormFieldPr currentFormFieldPr = xmlReader.getCurrentFormFieldPr();

        // If there was no FfData, just leave the field in the document without creating a form field.
        if (((currentFormFieldPr == null) && isDocx) || ((xmlReader.FieldData == null) && !isDocx))
            return;

        // RK We are creating a new node and need a set of run properties.
        // Lets get a clone of run properties from the field start.
        // Interesting that there is no run properties for the form field node itself in DOCX,
        // but there are properties in DOC and in the model. So I'm keeping this as is.
        RunPr runPr = AttrCollectionExtension.deepClone(field.getStart().getRunPr());
        FormFieldPr formFieldPr = currentFormFieldPr;

        if (!isDocx &&
            (field.getFieldType() == FieldType.FIELD_FORM_CHECK_BOX) &&
            (reader.getLoadOptions().getMswVersion() > MsWordVersion.WORD_2013))
        {
            formFieldPr.setCheckBoxDefault(formFieldPr.getCheckBoxDefault() & msString.endsWith(xmlReader.FieldData, "\r\n", StringComparison.ORDINAL));
        }

        FormField formField = new FormField(reader.getDocument(), formFieldPr, runPr);
        Node fieldCodeEnd = field.getFieldCodeEnd();
        reader.insertChildBefore(fieldCodeEnd.getParentNode(), formField, fieldCodeEnd);

        if (currentFormFieldPr != null)
            xmlReader.popFormFieldPr();
    }

    private static void transformIncludePictureField(NrxDocumentReaderBase reader, FieldBundle field) throws Exception
    {
        Debug.assertTrue(field.getStart().getFieldType() == FieldType.FIELD_INCLUDE_PICTURE || field.getStart().getFieldType() == FieldType.FIELD_IMPORT);

        // The shape must be right after the field separator, but access it with care.
        Shape shape;
        Node nodeAfterSeparator = field.getSeparator().getNextSibling();

        // WORDSNET-19296 Invalid INCLUDEPICTURE field.
        // This case is similar to FieldEnd occurred.
        /*NodeType*/int nodeType = (nodeAfterSeparator != null)
            ? nodeAfterSeparator.getNodeType()
            : NodeType.FIELD_END;

        switch (nodeType)
        {
            case NodeType.SHAPE:
            {
                // This is what's expected.
                shape = (Shape)nodeAfterSeparator;
                break;
            }
            case NodeType.GROUP_SHAPE:
            {
                Debug.assertTrue(nodeAfterSeparator != null);

                // WORDSNET-1325 We got group shape followed by shape. Strange, but happens.
                // Actually, used in the document as an image map. Not sure if this is what MS Word does.
                shape = (Shape)nodeAfterSeparator.getNextSibling();
                break;
            }
            case NodeType.FIELD_END:
            {
                // RESILIENCY C:\GoogleProof\Doc\arabic\4030.doc
                // This document has INCLUDEPICTURE field but no shape. We have to synthesize the shape.
                shape = new Shape(reader.getDocument(), ShapeType.IMAGE);
                shape.setWidth(1.0);
                shape.setHeight(1.0);
                shape.setWrapType(WrapType.INLINE);
                reader.insertChildAfter(field.getSeparator().getParentNode(), shape, field.getSeparator());
                break;
            }
            case NodeType.BOOKMARK_START:
            {
                Debug.assertTrue(nodeAfterSeparator != null);

                // RESILIENCY WORDSNET-3119 Bookmark start is inside the field result, just before the shape node.
                shape = (Shape)nodeAfterSeparator.nextSiblingOfType(NodeType.SHAPE);
                // We also move the bookmark start out of the field to be before the field.
                BookmarkStart bmkStart = (BookmarkStart)nodeAfterSeparator;
                reader.insertChildBefore(field.getStart().getParentNode(), bmkStart, field.getStart());
                break;
            }
            default:
            {
                // DV An exception used to be thrown here. However, I don't think this is correct. INCLUDEPICTURE,
                // like any other field, may have any result content, including an error message. Just leave such
                // field as is without transforming.
                return;
            }
        }

        Debug.assertTrue(shape != null);

        // RESILIENCY WORDSNET-3119 All images included with an INCLUDEPICTURE field are supposed to be inline,
        // but apparently in one document is not like this, we correct it.
        // WORDSNET-11613 Don't do it for shapes behind text.
        if (!shape.getBehindText())
            shape.setWrapType(WrapType.INLINE);

        // WORDSNET-1310 When an image is included inside an INCLUDEPICTURE field, its blip name type in Escher
        // does not have the link flag set, therefore it is treated by the escher reader as non linked.
        // We have to correct it here.
        if (!shape.getImageData().isLink())
        {
            FieldCodeIncludePicture fieldCode = FieldCodeIncludePicture.parse(field);
            shape.getImageData().setSourceFullName(fieldCode.getSourceFullName());
        }

        removeShapeField(field);
    }

    /// <summary>
    /// Removes all nodes of a shape or ole field except the shape itself.
    /// </summary>
    private static void removeShapeField(FieldBundle field)
    {
        Node curNode = field.getStart();
        while (curNode != field.getEnd().getNextSibling())
        {
            Node nextNode = curNode.getNextSibling();

            switch (curNode.getNodeType())
            {
                case NodeType.SHAPE:
                case NodeType.GROUP_SHAPE:
                    // Do nothing, since the purpose of this loop is to keep the shape node.
                    break;
                default:
                    curNode.remove();
                    break;
            }

            curNode = nextNode;
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"fldCharType",
		"dirty",
		"fldLock",
		"begin",
		"separate",
		"end",
		"fldData",
		"numberingChange",
		"annotation"
	);

}

