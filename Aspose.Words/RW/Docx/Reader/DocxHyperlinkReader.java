// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.UriUtil;
import com.aspose.StringUtil;


/// <summary>
/// Reads a hyperlink DOCX element.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
class DocxHyperlinkReader extends NrxHyperlinkReaderBase
{
    /// <summary>
    /// Reads a hyperlink DOCX element.
    /// RK Hyperlink in DOCX is very different from hlink in WordML.
    /// </summary>
    /*override*/ void read(NrxDocumentReaderBase reader) throws Exception
    {
        // Firstly, read hyperlink attributes.
        String bookmark = "";
        String dest = "";
        String targetFrame = "";
        String tooltip = "";
        String docLocation = "";
        boolean history = false;

        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"anchor"*/0:
                    // Specifies the name of a bookmark.
                    bookmark = xmlReader.getValue();
                    break;
                case /*"docLocation"*/1:
                    docLocation = xmlReader.getValue();
                    break;
                case /*"history"*/2:
                    history = xmlReader.getValueAsBool();
                    break;
                case /*"id"*/3:
                    // Specifies the ID of the relationship whose target shall be used as the target.
                    dest = reader.getRelationshipTarget(xmlReader.getValue());
                    break;
                case /*"tgtFrame"*/4:
                    targetFrame = xmlReader.getValue();
                    break;
                case /*"tooltip"*/5:
                    tooltip = xmlReader.getValue();
                    break;
                default:
                    // We don't know this attribute. Skip it.
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.DOCX, xmlReader.getLocalName());
                    break;
            }
        }

        // Remember the last insertion point so we can insert the HYPERLINK field here if needed.
        Node lastNodeBeforeHyperlink = reader.getCurContainer().getLastChild();

        // In DOCX we have a hyperlink element, but in the model we either have a
        // HYPERLINK field or shape properties. To figure out what to do, we need
        // to read the contents of the hyperlink element first.
        readChildren(reader);

        Node lastChild = reader.getCurContainer().getLastChild();
        // WORDSNET-25908 Check that shape is only child.
        if ((lastChild instanceof ShapeBase) && (lastChild.getPreviousSibling() == lastNodeBeforeHyperlink))
        {
            ShapeBase shape = (ShapeBase) lastChild;
            // The hyperlinked node is a shape, the hyperlink is stored as attributes of the shape.
            shape.setShapeAttrInternal(ShapeAttr.HYPERLINK_ADDRESS, UriUtil.appendSubAddress(dest, bookmark));

            if (StringUtil.hasChars(targetFrame))
                shape.setShapeAttrInternal(ShapeAttr.HYPERLINK_TARGET, targetFrame);

            if (StringUtil.hasChars(tooltip))
                shape.setShapeAttrInternal(ShapeAttr.SCREEN_TIP, tooltip);
        }
        else
        {
            // The hyperlinked nodes are runs etc. The hyperlink must be turned into a field.
            FieldStart fieldStart = new FieldStart(reader.getDocument(), new RunPr(), FieldType.FIELD_HYPERLINK);
            reader.insertChildAfter(reader.getCurContainer(), fieldStart, lastNodeBeforeHyperlink);

            FieldCodeHyperlink fieldCode = new FieldCodeHyperlink(
                dest,
                bookmark,
                tooltip,
                targetFrame,
                docLocation,
                !history);
            fieldCode.normalizeAddressAndSubAddress();
            Run fieldCodeRun = new Run(reader.getDocument(), fieldCode.toFieldCodeString());

            reader.insertChildAfter(reader.getCurContainer(), fieldCodeRun, fieldStart);

            FieldSeparator fieldSeparator = new FieldSeparator(reader.getDocument(), new RunPr(), FieldType.FIELD_HYPERLINK);
            reader.insertChildAfter(reader.getCurContainer(), fieldSeparator, fieldCodeRun);

            FieldEnd fieldEnd = new FieldEnd(reader.getDocument(), new RunPr(), FieldType.FIELD_HYPERLINK, true);
            reader.addChild(fieldEnd);

            // WORDSNET-16965 Mimic MSW, fieldCode run receives runPr.
            if ((lastChild != null) && (lastChild.getNodeType() == NodeType.RUN))
            {
                RunPr runPr = ((Run)lastChild).getRunPr();

                // WORDSNET-28099 Only Hidden attribute is copied to field code.
                runPr.expandToInclusive(fieldCodeRun.getRunPr(), FontAttr.HIDDEN);

                runPr.expandToInclusive(fieldStart.getRunPr(), FontAttr.HIDDEN);
                runPr.expandToInclusive(fieldSeparator.getRunPr(), FontAttr.HIDDEN);
                runPr.expandToInclusive(fieldEnd.getRunPr(), FontAttr.HIDDEN);
            }

        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"anchor",
		"docLocation",
		"history",
		"id",
		"tgtFrame",
		"tooltip"
	);

}

