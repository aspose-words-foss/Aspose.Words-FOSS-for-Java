// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArgumentUtil;
import com.aspose.StringUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ImageUtil;
import com.aspose.FileFormat;
import com.aspose.ImageSizeCore;
import com.aspose.MathUtil;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Xml.XmlNodeType;
import com.aspose.AnyXmlBuilder;
import com.aspose.OpcCorePropertiesWriter;
import com.aspose.StringToStringDictionary;


/// <summary>
/// Specifies the information that is used to establish a mapping between the parent
/// structured document tag and an XML element stored within a custom XML data part in the document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
public class XmlMapping implements Cloneable
{
    /// <summary>
    /// Ctor. Assigns a link to a parent structured document tag.
    /// </summary>
    XmlMapping(StructuredDocumentTag sdt)
    {
        mSdt = sdt;
    }

    /// <summary>
    /// Sets a mapping between the parent structured document tag and an XML node of a custom XML data part.
    /// </summary>
    /// <param name="customXmlPart">A custom XML data part to map to.</param>
    /// <param name="xPath">An XPath expression to find the XML node.</param>
    /// <param name="prefixMapping">XML namespace prefix mappings to evaluate the XPath.</param>
    /// <returns>A flag indicating whether the parent structured document tag is successfully mapped to
    /// the XML node.</returns>
    public boolean setMapping(CustomXmlPart customXmlPart, String xPath, String prefixMapping) throws Exception
    {
        ArgumentUtil.checkHasChars(xPath, "xPath");
        ArgumentUtil.checkNotNull(customXmlPart, "customXmlPart");

        // It looks like that the following SDT types cannot be mapped to XML. Let's raise an exception.
        if ((mSdt.getSdtType() == SdtType.BUILDING_BLOCK_GALLERY) ||
            (mSdt.getSdtType() == SdtType.DOC_PART_OBJ) ||
            (mSdt.getSdtType() == SdtType.CITATION))
            throw new IllegalStateException("Cannot map a structured document tag of this type to XML data.");

        setMapping("", xPath, prefixMapping);
        mCustomXmlPart = customXmlPart;

        return isMapped();
    }

    /// <summary>
    /// Deletes mapping of the parent structured document to XML data.
    /// </summary>
    public void delete()
    {
        mPrefixMappings = "";
        mXPath = "";
        mStoreItemId = "";
        mCustomXmlPart = null;
    }

    /// <summary>
    /// Returns a copy of this instance.
    /// </summary>
    XmlMapping deepClone(StructuredDocumentTag parentSdt)
    {
        // On cloning a document, parentSdt has still source document as parent here, so custom XML part has to
        // be resolved later in the ResolveCustomXmlPart method.
        XmlMapping lhs = (XmlMapping)memberwiseClone();
        lhs.mSdt = parentSdt;

        // On cloning from one document to another, mCustomXmlPart refers to an XML part of another document:
        // need to reassign reference using mStoreItemId to be resolved to correct XML part in CustomXmlPart
        // property getter on accessing.
        if (mCustomXmlPart != null)
        {
            mStoreItemId = mCustomXmlPart.getId();
            mCustomXmlPart = null;
        }

        return lhs;
    }

    /// <summary>
    /// Finds and reassigns a custom XML part. This method is called on cloning a document to set custom XML part
    /// of a cloned document.
    /// </summary>
    void resolveCustomXmlPart()
    {
        mCustomXmlPart = getCustomXmlPartById(getStoreItemId());
    }

    /// <summary>
    /// Sets a mapping between the parent structured document tag and an XML node of a custom XML data part.
    /// </summary>
    /// <param name="storeItemId">Identifier of a store item: either a custom XML data part or a storage of
    /// document properties.</param>
    /// <param name="xPath">An XPath expression to find the XML node.</param>
    /// <param name="prefixMapping">XML namespace prefix mappings to evaluate the XPath.</param>
    void setMapping(String storeItemId, String xPath, String prefixMapping)
    {
        mPrefixMappings = prefixMapping;
        mXPath = xPath;
        mCustomXmlPart = StringUtil.hasChars(storeItemId) ? getCustomXmlPartById(storeItemId) : null;
        // Store either custom XML part or its ID.
        mStoreItemId = mCustomXmlPart == null ? storeItemId : "";
    }

    /// <summary>
    /// Returns true if this XML mapping is valid.
    /// </summary>
    /// <remarks>
    /// This method only checks <see cref="XPath"/> since <see cref="StoreItemId"/> and <see cref="PrefixMappings"/>
    /// may not be required by MS Word. Use the <see cref="IsMapped"/> property for more strict check.
    /// </remarks>
    boolean isValid() { return StringUtil.hasChars(getXPath()); }

    /// <summary>
    /// Sets XPath expression, which is evaluated to find a custom XML node
    /// that is mapped to the parent structured document tag.
    /// </summary>
    /// <dev>
    /// It is separate from the corresponding property since the property is public.
    /// </dev>
    void setXPath(String value)
    {
        mXPath = value;
    }

    /// <summary>
    /// Gets value of an XML node, to which this object is mapped.
    /// </summary>
    String getValue() throws Exception
    {
        if (!isEmpty())
        {
            ArrayList<org.w3c.dom.Node> xmlNodes = getBoundXmlNodes(null);
            if (xmlNodes.size() > 0)
                return xmlNodes.get(0).getTextContent();
        }
        return null;
    }

    /// <summary>
    /// Sets value of an XML node, to which this object is mapped.
    /// </summary>
    void setValue(String value) throws Exception
    {
        ArrayList<String> values = new ArrayList<String>();
        msArrayList.add(values, value);

        setValues(values);
    }

    /// <summary>
    /// Sets values of an XML nodes, to which this object is mapped.
    /// </summary>
    void setValues(ArrayList<String> values) throws Exception
    {
        if (isEmpty())
            throw new IllegalStateException("XML mapping is not defined.");

        Document doc = mSdt.fetchDocument();
        if (isCoreProperties() || isExtendedProperties())
        {
            // MS Word also allows inserting SDTs that are mapped to cover page properties, but they are stored
            // by standard way: in a special custom XML part.
            String propertyName = getPropertyName();
            if (!StringUtil.hasChars(propertyName))
                throw new IllegalStateException("Cannot find document property.");

            doc.getBuiltInDocumentProperties().get(propertyName).setValue(values.get(0));
        }
        else
        {
            CustomXmlPart xmlPart = doc.getCustomXmlParts().getById(getStoreItemId());

            if (xmlPart == null)
            {
                // WORDSNET-28368 Try to find missing part.
                xmlPart = tryFindMissingPart(doc.getCustomXmlParts(), getXPath(), getPrefixMappings(), getStoreItemId());

                if (xmlPart == null)
                    throw new IllegalStateException("Custom XML part is not found.");
            }

            ArrayList<org.w3c.dom.Node> xmlNodes = CustomXmlUtil.extractXmlNodes(xmlPart.getData(), getXPath(), getPrefixMappings(), getStoreItemId(), null);

            // WORDSNET-28668 Create empty mapped XML node if missing.
            if (xmlNodes.size() == 0)
                xmlNodes = CustomXmlUtil.createMappedXmlNode(getXPath(), getPrefixMappings());

            if (xmlNodes.size() != values.size())
                throw new IllegalStateException("Incorrect XML nodes count.");

            if (CustomXmlUtil.isPartnerControlsNamespace(xmlNodes))
            {
                for (int i = 0; i < xmlNodes.size(); i++)
                    xmlNodes.get(i).setTextContent(values.get(i));
            }
            else
                xmlNodes.get(0).setTextContent(values.get(0));

            MemoryStream stream = new MemoryStream();
            XmlUtilPal.saveXml(xmlNodes.get(0).getOwnerDocument(), stream);
            xmlPart.setData(stream.toArray());
        }
    }

    /// <summary>
    /// Updates structured document tag content from data bound XML data.
    /// </summary>
    boolean updateContent() throws Exception
    {
        return updateContent(new XmlMappingContext());
    }

    /// <summary>
    /// Updates structured document tag content from data bound XML data using cache context.
    /// </summary>
    boolean updateContent(XmlMappingContext ctx) throws Exception
    {
        SuspendMappedCustomXmlUpdateDocument suspendMappedCustomXmlUpdateDocumentResource = new SuspendMappedCustomXmlUpdateDocument(mSdt.getDocument());
        try /*JAVA: was using*/
        {
            return updateDataBoundContent(mSdt, ctx);
        }
        finally { if (suspendMappedCustomXmlUpdateDocumentResource != null) suspendMappedCustomXmlUpdateDocumentResource.dispose(); }
    }

    int getMappedNodesCount() throws Exception
    {
        return getBoundXmlNodes(null).size();
    }

    /// <summary>
    /// Updates structured document tag content with bound data.
    /// </summary>
    /// <returns>True if content was updated successfully.</returns>
    private static boolean updateDataBoundContent(StructuredDocumentTag sdt, XmlMappingContext ctx) throws Exception
    {
        if (!sdt.canBeUpdated())
            return true;

        // No data binding - treat update as successful.
        if (sdt.getXmlMapping().isEmpty())
            return true;

        if (!sdt.getXmlMapping().isValid())
            return false;

        // WORDSNET-16888 Break cyclic reference for nested SDT.
        if (sdt.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
        {
            StructuredDocumentTag parentSdt = (StructuredDocumentTag)sdt.getParentNode();
            if ((msString.equals(sdt.getXmlMapping().getXPath(), parentSdt.getXmlMapping().getXPath())) &&
                (msString.equals(sdt.getXmlMapping().getPrefixMappings(), parentSdt.getXmlMapping().getPrefixMappings())))
                return true;
        }

        if (!sdt.isUpdateable())
            return false;

        if (sdt.isRepeatingSection())
            return SdtRepeatingSection.updateRepeatingSection(sdt);

        ArrayList<org.w3c.dom.Node> xmlNodes = sdt.getXmlMapping().getBoundXmlNodes(ctx);

        if (xmlNodes.size() > 0)
        {
            String newContent = CustomXmlUtil.getOverallInnerText(xmlNodes);

            // WORDSNET-28846 Update if IsShowingPlaceholder is set, regardless of whether SDT content equals the mapped value.
            if (areSameVisibleContent(sdt, newContent) && !sdt.isShowingPlaceholderText())
                return false;

            if (StringUtil.hasChars(newContent))
            {
                switch (sdt.getSdtType())
                {
                    case SdtType.PLAIN_TEXT:
                    {
                        // WORDSNET-20331 Seems that Word does not update PlainText content
                        // if bound XmlNode has child XmlNode.
                        if (hasChildElementNode(xmlNodes.get(0)))
                            return false;

                        return updateTextboxSdt(sdt, newContent, ctx);
                    }
                    case SdtType.RICH_TEXT:
                    {
                        return updateTextboxSdt(sdt, newContent, ctx);
                    }
                    case SdtType.COMBO_BOX:
                    case SdtType.DROP_DOWN_LIST:
                    {
                        // See ISO 29500, §17.5.2.5, 15 for information of getting display value of SDT bound to XML
                        SdtDropDownListBase dropDownListProps = (SdtDropDownListBase)sdt.getControlProperties();
                        SdtListItemCollection listItems = dropDownListProps.getListItems();

                        // Searching for the value in list items
                        int itemIndex = listItems.indexOfItemValue(newContent);
                        if (itemIndex >= 0)
                        {
                            SdtContentHelper.setDisplayText(sdt, listItems.get(itemIndex).getDisplayText());
                            listItems.setSelectedValue(listItems.get(itemIndex));
                            listItems.setLastItemValue(null); // if the last value is different, clear it
                            return true;
                        }

                        // If the XML node value equals to the last value, current display text should be retained.
                        boolean retainCurDisplayText = (msString.equals(listItems.getLastItemValue(), newContent)) &&
                                                    (sdt.getChildNodes(NodeType.ANY, false).getCount() > 0);
                        if (!retainCurDisplayText)
                        {
                            // Setting the value as display text
                            SdtContentHelper.setDisplayText(sdt, newContent);
                        }

                        return true;
                    }
                    case SdtType.DATE:
                    {
                        String sdtDateDisplayValue =
                            SdtContentHelper.getSdtDateDisplayValue(sdt, newContent, ctx);

                        if (!msString.equals(sdt.getVisibleText(), sdtDateDisplayValue))
                            SdtContentHelper.setDisplayText(sdt, sdtDateDisplayValue);

                        // WORDSNET-24530 Method should return false only in a case of issues while updating.
                        return true;
                    }
                    case SdtType.PICTURE:
                        return updatePicture(sdt, newContent);

                    case SdtType.CHECKBOX:
                        return updateCheckbox(sdt, newContent);

                    default:
                        break;
                }
            }
            else
            {
                // WORDSNET-21979 Update SDT with the default content when mapped value is empty.
                if (!sdt.isShowingPlaceholderText())
                    SdtContentHelper.insertDefaultContent(sdt, true);

                return true;
            }
        }
        else
        {
            if (sdt.getXmlMapping().isCoreProperties())
            {
                // Content bound to empty core property should be replaced with placeholder.
                SdtContentHelper.replaceContentWithPlaceholder(sdt);

                // Lets think that it is successful update.
                return true;
            }
        }

        // Data binding is failed.
        return false;
    }

    /// <summary>
    /// Checks that visible content of SDT equals to new content and therefore should not be updated.
    /// </summary>
    private static boolean areSameVisibleContent(StructuredDocumentTag sdt, String newContent)
    {
        // WORDSNET-21506 We shall not update sdtContent in case the displayed text for XMLMapping
        // is identical to that for sdtContent.
        //
        // WORDSNET-23777 Do not update not empty plain SDT neither with mapped content nor with placeholder
        // when visible content equal to mapped content even if mapped content is empty.
        //
        // WORDSNET-24529 Check for RichText as well.
        // WORDSNET-28344 Do not update SDT content if mapped content is the same as current SDT content for Dropdown control.
        if ((sdt.getSdtType() != SdtType.PLAIN_TEXT) && (sdt.getSdtType() != SdtType.RICH_TEXT) && sdt.getSdtType() != SdtType.DROP_DOWN_LIST)
            return false;

        if (!StringUtil.hasChars(sdt.getText()))
            return false;

        if (sdt.getSdtType() == SdtType.PLAIN_TEXT)
        {
            // WORDSNET-28036 Avoid unwanted update on SDT removal.
            // We collect runs to get visible text but PlainText SDT can be at block level.
            newContent = msString.trimEnd(newContent, ControlChar.PARAGRAPH_BREAK_CHAR);
        }
        else if (sdt.getSdtType() == SdtType.DROP_DOWN_LIST)
        {
            // WORDSNET-28443 Check DropDownList content by item value.
            int itemIndex = sdt.getListItems().indexOfItemValue(newContent);

            if (itemIndex >= 0)
                newContent = sdt.getListItems().get(itemIndex).getDisplayText();
        }

        return (msString.equals(sdt.getVisibleText(), newContent));
    }

    /// <summary>
    /// Updates SDT having PlainText or RichText types.
    /// </summary>
    private static boolean updateTextboxSdt(StructuredDocumentTag sdt, String newContent, XmlMappingContext ctx) throws Exception
    {
        Debug.assertTrue((sdt.getSdtType() == SdtType.PLAIN_TEXT) || (sdt.getSdtType() == SdtType.RICH_TEXT));

        MemoryStream stream = new MemoryStream(Encoding.getUTF8().getBytes(newContent));
        /*LoadFormat*/int lf;

        if (StringUtil.containsOnlyWhitespaces(newContent))
        {
            // WORDSNET-20690 If innerText is empty or whitespace only we will treat it as text.
            lf = LoadFormat.TEXT;
        }
        else if (!newContent.contains("<pkg:package"))
        {
            // WORDSNET-20828 Seems that there can be FlatOpc document only, do fast check (code taken from our FileFormatDetector).
            lf = LoadFormat.TEXT;
        }
        else
            lf = FileFormatUtil.detectFileFormat(stream).getLoadFormat();

        boolean updated = true;
        Document mappedDoc = null;
        int mappedParaIstd = Integer.MIN_VALUE;

        // WORDSNET-28046 Save first SDT paragraph to restore its properties in case of update from placeholder.
        Paragraph savedSdtPara = null;
        if (sdt.hasChildNodes() && (sdt.getFirstChild().getNodeType() == NodeType.PARAGRAPH))
            savedSdtPara = (Paragraph)((Paragraph)sdt.getFirstChild()).deepClone(false);

        // See TestJira7286 for case with "Unknown" load format and plain text SDT.
        if ((lf == LoadFormat.TEXT) || ((lf == LoadFormat.UNKNOWN) && (sdt.getSdtType() == SdtType.PLAIN_TEXT)))
        {
            // WORDSNET-15134 Mimic MSW and remove CrLf for plain text SDT.
            // WORDSNET-26419 Word ignores \r as well.
            newContent = (sdt.getSdtType() == SdtType.RICH_TEXT) || sdt.getMultiline()
                ? newContent
                : newContent.replace(ControlChar.CR_LF, "").replace(ControlChar.PARAGRAPH_BREAK, "");

            // WORDSNET-9830 MSW ignores these symbols.
            newContent = newContent.replace("_x000d_", "").replace("_x000D_", "");

            SdtContentHelper.setDisplayText(sdt, newContent);
        }
        else if (lf != LoadFormat.UNKNOWN)
        {
            // Skip updating mapped SDT which is not on the top level. Check Test22617UpdateNestedSdt for details.
            if ((sdt.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) &&
                !((StructuredDocumentTag)sdt.getParentNode()).getXmlMapping().isEmpty())
            {
                return true;
            }

            // WORDSNET-17759 Recognize the content of FlatOpc document, which mapped to the "PlainText" SDT.
            mappedDoc = new Document(stream, null, false);

            // AM. Seems that MS Word applies paragraph style of first paragraph to placeholder.
            //
            // Mapped document might be modified during preparation and first paragraph can be removed at
            // placeholder applying stage. Maybe we need some refactoring here but lets save Istd and apply it later.
            mappedParaIstd = mappedDoc.getFirstSection().getBody().getFirstParagraph().getParaPr().getIstd();

            updated = insertDocument(sdt, mappedDoc);

            // Collect inner SDT loaded with mapped document, they need special processing later.
            for (StructuredDocumentTag innerTag : (Iterable<StructuredDocumentTag>) sdt.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
                msArrayList.add(ctx.getInnerTags(), innerTag);
        }
        else
            return false;

        // If the SDT is removed from the document, it has been converted to a structured document range during
        // update. This means that the SDT is not empty (the inserted contents contains sections), no default
        // contents is needed.
        if (!sdt.isRemoved() && !sdt.hasChildNodes())
        {
            SdtContentHelper.insertDefaultContent(sdt, true);

            // WORDSNET-26601 Apply paragraph style from mapped document to placeholder.
            if ((mappedDoc != null) && (mappedParaIstd != Integer.MIN_VALUE) && (sdt.getFirstChild().getNodeType() == NodeType.PARAGRAPH))
            {
                Paragraph firstPara = (Paragraph)sdt.getFirstChild();

                // WORDSNET-28046 Restore paragraph  properties.
                // Actually I think Word does not restore properties and rather updates from placeholder
                // leaving original paragraph untouched but I dont see easy way to implemented it now.
                if (savedSdtPara != null)
                {
                    savedSdtPara.getParaPr().expandTo(firstPara.getParaPr());
                    savedSdtPara.getParagraphBreakRunPr().expandTo(firstPara.getParagraphBreakRunPr());
                    firstPara.setTextId(savedSdtPara.getTextId());
                    firstPara.setParaId(savedSdtPara.getParaId());
                }

                Style importedStyle = sdt.getDocument().getStyles().importStyle(mappedDoc.getStyles().getByIstd(mappedParaIstd, true));
                firstPara.getParaPr().setIstd(importedStyle.getIstd());
            }
        }
        else
        {
            // Reset showing placeholder flag upon successful content update.
            sdt.isShowingPlaceholderText(false);
        }

        return updated;
    }

    /// <summary>
    /// Inserts document contents into the given SDT and, if necessary, moves the SDT from inline to block level.
    /// </summary>
    private static boolean insertDocument(StructuredDocumentTag sdt, Document doc)
    {
        if ((sdt.getLevel() != MarkupLevel.INLINE) ||
            (sdt.getParentNode().getNodeType() != NodeType.PARAGRAPH) ||
            !needMoveCurrentSdtToBlockLevel(doc))
        {
            return SdtContentHelper.insertContent(sdt, doc, true) != null;
        }

        // WORDSNET-23284 Moved removing last paragraph to here from the place before the call to this method
        // to allow SdtContentHelper.InsertDocument() to check properties of last paragraph properly.
        Paragraph lastPara = doc.getLastSection().getBody().getLastParagraph();
        if (SdtContentHelper.canRemove(lastPara))
            lastPara.remove();

        // Insert document contents.
        SdtContentHelper.insertContent(sdt, doc, true);

        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if the current inline SDT should be moved to block level on inserting the specified
    /// document.
    /// </summary>
    private static boolean needMoveCurrentSdtToBlockLevel(Document insertedDoc)
    {
        if (insertedDoc.getSections().getCount() > 1)
            return true;

        Body body = insertedDoc.getFirstSection().getBody();
        Node first = body.getFirstNonAnnotationChild();
        Node last = body.getLastNonAnnotationChild();

        // WORDSNET-23284 Actual removing of last empty paragraph is not performed at this point yet,
        // so we need this additional check here.
        if (SdtContentHelper.canRemove(ms.as(last, Paragraph.class)))
            last = last.getPreviousNonAnnotationSibling();

        if (last == null)
            return false;

        // Current SDT has to be moved to block level when:
        // 1. Document body contains more than two non annotation child nodes.
        // 2. Document body has two child nodes and last child is not empty paragraph.
        return (first != last) &&
               ((first.getNextNonAnnotationSibling() != last) ||
                (last.getNodeType() != NodeType.PARAGRAPH) ||
                ((Paragraph)last).hasChildNodes());
    }

    /// <summary>
    /// TODO this code never used.
    /// Updates first Shape or DrawingML of SDT with given image.
    /// </summary>
    /// <param name="sdt"></param>
    /// <param name="imageBytes">Base64 encoded image bytes.</param>
    /// <returns>True if update was successful, false otherwise.</returns>
    private static boolean updatePicture(StructuredDocumentTag sdt, String imageBytes) throws Exception
    {
        // WORDSNET-10935 Ensure the string is Base64 encoded and decoded image bytes represents a valid image.
        // WORDSNET-18887 If Base64 is corrupted, return an empty byte array.
        byte[] imageData = StringUtil.convertFromBase64Safe(imageBytes);

        if (ImageUtil.getImageType(imageData) == FileFormat.UNKNOWN)
            return false;

        MemoryStream stream = new MemoryStream(imageData);

        Shape contentShape = (Shape)sdt.getChild(NodeType.SHAPE, 0, true);

        if (contentShape == null)
            return false;

        contentShape.getImageData().setImage(stream);
        // WORDSNET-16472 Update size of the SDT with given image according to original size ratio and actual aspect ratio.
        updatePictureSize(contentShape);

        return true;
    }

    /// <summary>
    /// Updates size of the specified picture.
    /// </summary>
    private static void updatePictureSize(Shape contentShape) throws Exception
    {
        double width = contentShape.getWidth();
        double height = contentShape.getHeight();

        ImageSizeCore origImgSize = ImageUtil.getImageSize(contentShape.getImageData().getImageBytes());
        contentShape.setWidthSafe(origImgSize.getWidthPoints());
        contentShape.setHeightSafe(origImgSize.getHeightPoints());

        boolean hasAspectRatio = contentShape.getAspectRatioLocked();
        contentShape.setAspectRatioLocked(true);

        if (!MathUtil.areEqual(width, height))
        {
            if (width > height)
                contentShape.setHeight(height);
            else
                contentShape.setWidth(width);
        }
        else
        {
            if (origImgSize.getWidthEmus() < origImgSize.getHeightEmus())
                contentShape.setHeight(height);
            else
                contentShape.setWidth(width);
        }

        contentShape.setAspectRatioLocked(hasAspectRatio);
    }

    /// <summary>
    /// Updates state of data bound check box.
    /// </summary>
    private static boolean updateCheckbox(StructuredDocumentTag sdt, String value) throws Exception
    {
        boolean newChecked = ("1".equals(value)) || ("true".equals(value));

        // WORDSNET-24036 Word updates checkbox content only when value is changed.
        if (sdt.getChecked() != newChecked)
            sdt.setChecked(newChecked);

        return true;
    }

    /// <summary>
    /// Selects data bound XmlNodes from customXmlParts.
    /// </summary>
    ArrayList<org.w3c.dom.Node> getBoundXmlNodes(XmlMappingContext ctx) throws Exception
    {
        Document doc = mSdt.fetchDocument();

        ArrayList<org.w3c.dom.Node> xmlNodes = new ArrayList<org.w3c.dom.Node>();

        if (isCoreProperties())
        {
            byte[] corePropsXml = createCorePropertiesXml(doc.getBuiltInDocumentProperties());

            // WORDSNET-25289 Use default prefix mapping for core properties.
            String prefixMapping = StringUtil.hasChars(getPrefixMappings())
                ? getPrefixMappings()
                : DEFAULT_CORE_PROPS_PREFIX_MAPPING;

            xmlNodes = CustomXmlUtil.extractXmlNodes(corePropsXml, getXPath(), prefixMapping, getStoreItemId(), ctx);
        }
        else if (isExtendedProperties())
        {
            xmlNodes = CustomXmlUtil.extractXmlNodes(createExtendedPropertiesXml(doc.getBuiltInDocumentProperties()),
                getXPath(), getPrefixMappings(), getStoreItemId(), ctx);
        }
        else
        {
            CustomXmlPart part = doc.getCustomXmlParts().getById(getStoreItemId());

            if (part == null)
            {
                // StoreItemId is specified but corresponding custom XML part is missed.

                // WARN. Unable to find custom XML part with given ID.

                // WORDSNET-21591 Seems that Word does not update content of inner SDT in case of missing custom XML part.
                boolean updateMissing = (ctx == null) || !ctx.getInnerTags().contains(mSdt);

                if (updateMissing)
                {
                    // AM. Part not found but for TestJira3913.docx Word gets data somehow. I think it tries every part.
                    part = tryFindMissingPart(doc.getCustomXmlParts(), getXPath(), getPrefixMappings(), getStoreItemId());
                }
            }

            if (part != null)
            {
                // Seems MS Word updates StoreItemId with part identifier if missing but lets postpone.

                // Part is found, get node.
                xmlNodes = CustomXmlUtil.extractXmlNodes(part.getData(), getXPath(), getPrefixMappings(), part.getId(), ctx);
            }
        }

        return xmlNodes;
    }

    /// <summary>
    /// Gets document property name, to which the parent structured document tag is mapped.
    /// </summary>
    /// <returns></returns>
    String getPropertyName()
    {
        if (!isCoreProperties() && !isExtendedProperties())
            return null;

        if ((mXPath == null) ||
            (isCoreProperties() && (msString.indexOf(mXPath, "COREPROPERTIES", StringComparison.ORDINAL_IGNORE_CASE) == -1)) ||
            (isExtendedProperties() && (msString.indexOf(mXPath, "PROPERTIES", StringComparison.ORDINAL_IGNORE_CASE) == -1)))
            return null;

        int beforeIndex = mXPath.lastIndexOf('/');
        if (beforeIndex < 0)
            beforeIndex = mXPath.lastIndexOf('\\');
        if (beforeIndex < 0)
            return null;

        int afterIndex = mXPath.lastIndexOf('[');
        if ((afterIndex < 0) || (afterIndex < beforeIndex))
            afterIndex = mXPath.length();

        String nodeName = mXPath.substring((beforeIndex + 1), (beforeIndex + 1) + (afterIndex - beforeIndex - 1));

        int prefixIndex = nodeName.indexOf(':');
        if (prefixIndex >= 0)
            nodeName = nodeName.substring(prefixIndex + 1);

        if (isCoreProperties())
        {
            if (G_PATH_TO_PROPERTY_NAME.containsKey(nodeName))
                return G_PATH_TO_PROPERTY_NAME.get(nodeName);
            else
                return null;
        }
        else
        {
            return nodeName;
        }
    }

    /// <summary>
    /// Iterates custom XML part and tries to extract some nodes using given xPath,
    /// </summary>
    /// <returns>
    /// Returns first occured part that contains nodes at given xpath or null if none part contain appropriate nodes.
    /// </returns>
    private static CustomXmlPart tryFindMissingPart(CustomXmlPartCollection customXmlParts,
        String xPath, String prefixMappings, String storeItemId) throws Exception
    {
        for (CustomXmlPart customXmlPart : customXmlParts)
        {
            // Note this operation should not be cached as we actually accessed data incorrectly.
            ArrayList<org.w3c.dom.Node> xmlNodes = CustomXmlUtil.extractXmlNodes(
                customXmlPart.getData(), xPath, prefixMappings, storeItemId, null);
            if (xmlNodes.size() > 0)
                return customXmlPart;
        }

        return null;
    }

    /// <summary>
    /// Finds a custom XML part of a current document by its ID.
    /// </summary>
    private CustomXmlPart getCustomXmlPartById(String partId)
    {
        Document doc = ms.as(mSdt.getDocument(), Document.class);
        return doc != null ? doc.getCustomXmlParts().getById(partId) : null;
    }

    /// <summary>
    /// Checks that given XmlNode has child XmlNode element.
    /// </summary>
    private static boolean hasChildElementNode(org.w3c.dom.Node xmlNode)
    {
        org.w3c.dom.NodeList nodeList = xmlNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
            if (XmlUtilPal.isNodeType(nodeList.item(i), XmlNodeType.ELEMENT))
                return true;

        return false;
    }

    /// <summary>
    /// Returns extended properties stored as XML. Used to update content of structured document tags.
    /// </summary>
    private static byte[] createExtendedPropertiesXml(BuiltInDocumentProperties props) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        AnyXmlBuilder xmlBuilder = new AnyXmlBuilder(stream, true);

        // This code slightly duplicates DocxExtendedPropertiesWriter.Write,
        // but probably reference to DocxExtendedPropertiesWriter from here is worse.
        xmlBuilder.startDocument("Properties");

        xmlBuilder.writeAttributeString("xmlns",
            "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties");
        xmlBuilder.writeAttributeString("xmlns:vt",
            "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes");

        // Only two properties are allowed in MS Word.
        xmlBuilder.writeOptionalElement("Manager", props.getManager());
        xmlBuilder.writeElement("Company", props.getCompany());

        xmlBuilder.endDocument();

        return stream.toArray();
    }

    /// <summary>
    /// Returns Builtin properties stored as XML. Used to update structured document tags content.
    /// </summary>
    private static byte[] createCorePropertiesXml(BuiltInDocumentProperties props) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        AnyXmlBuilder xmlBuilder = new AnyXmlBuilder(stream, true);

        // AM. This code slightly duplicates DocxWriter.WriteCoreProperties
        // but I think links to DocxWriter from here is worse.
        OpcCorePropertiesWriter.write(
            xmlBuilder,
            props.getTitle(),
            props.getSubject(),
            props.getAuthor(),
            props.getKeywords(),
            props.getComments(),
            props.getLastSavedBy(),
            Integer.toString(props.getRevisionNumber()),
            props.getLastPrintedInternal(),
            props.getCreatedTimeInternal(),
            props.getLastSavedTimeInternal(),
            props.getCategory(),
            props.getContentStatus(), "", "");

        return stream.toArray();
    }

    /// <summary>
    /// Returns XML namespace prefix mappings to evaluate the <see cref="XPath"/>.
    /// </summary>
    /// <remarks>
    /// Specifies the set of prefix mappings, which shall be used to interpret the XPath expression
    /// when the XPath expression is evaluated against the custom XML data parts in the document.
    /// </remarks>
    public String getPrefixMappings() { return mPrefixMappings; }

    /// <summary>
    /// Returns the XPath expression, which is evaluated to find the custom XML node
    /// that is mapped to the parent structured document tag.
    /// </summary>
    public String getXPath() { return mXPath; }

    /// <summary>
    /// Returns the custom XML data part to which the parent structured document tag is mapped.
    /// </summary>
    public CustomXmlPart getCustomXmlPart() throws Exception
    {
        if (isCoreProperties() && (mCustomXmlPart == null))
        {
            // WORDSNET-27328 Create runtime CustomXmlPart for core properties mapping.
            Document doc = ms.as(mSdt.getDocument(), Document.class);

            if (doc != null)
            {
                mCustomXmlPart = new CustomXmlPart();
                mCustomXmlPart.setId(CORE_PROPERTIES_STORE_ITEM_ID);
                mCustomXmlPart.setData(createCorePropertiesXml(doc.getBuiltInDocumentProperties()));
                mCustomXmlPart.getSchemas().add("http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
                mCustomXmlPart.getSchemas().add("http://purl.org/dc/elements/1.1/");
            }
        }

        if ((mCustomXmlPart == null) && StringUtil.hasChars(mStoreItemId))
        {
            mCustomXmlPart = getCustomXmlPartById(mStoreItemId);
            // Store either custom XML part or store item ID.
            if (mCustomXmlPart != null)
                mStoreItemId = null;
        }
        return mCustomXmlPart;
    }

    /// <summary>
    /// Returns <c>true</c> if the parent structured document tag is successfully mapped to XML data.
    /// </summary>
    public boolean isMapped() throws Exception
    {
        if (!isValid())
            return false;

        return getMappedNodesCount() > 0;
    }

    /// <summary>
    /// Specifies the custom XML data identifier for the custom XML data part which
    /// shall be used to evaluate the <see cref="XPath"/> expression.
    /// </summary>
    public String getStoreItemId()
    {
        if (mCustomXmlPart != null)
            return mCustomXmlPart.getId();
        return StringUtil.hasChars(mStoreItemId) ? mStoreItemId : "";
    }
    void setStoreItemId(String value)
    {
        mCustomXmlPart = getCustomXmlPartById(value);
        // Store either custom XML part or store item ID.
        mStoreItemId = (mCustomXmlPart == null) ? value : "";
    }

    /// <summary>
    /// Returns true, if this XML Mapping can be achieved only with using extensions defined
    /// in the version 15 of MS Word.
    /// </summary>
    boolean isDocx15Extension()
    {
        return !isEmpty() &&
            // It is not completely clear if BuildingBlockGallery, DocPartObj and Citation can be mapped to XML.
            // MS Word raises an error if they are mapped with the ISO dataBinding element, but does not when
            // w15:dataBinding is used. Let's include these SDT types into the check below for safety. Mapping
            // of such SDTs may be only read from file since AW public interface does not allow to do that.
            ((mSdt.getSdtType() == SdtType.RICH_TEXT) || (mSdt.getSdtType() == SdtType.BUILDING_BLOCK_GALLERY) ||
                (mSdt.getSdtType() == SdtType.DOC_PART_OBJ) || (mSdt.getSdtType() == SdtType.CITATION) ||
                (mSdt.getSdtType() == SdtType.CHECKBOX) || mSdt.isRepeatingSection() || mSdt.isRepeatingSectionItem());
    }

    /// <summary>
    /// Returns <c>true</c> if this XML mapping is not defined.
    /// </summary>
    boolean isEmpty()
    {
        return !StringUtil.hasChars(mXPath) && !StringUtil.hasChars(mPrefixMappings) &&
            !StringUtil.hasChars(mStoreItemId) && (mCustomXmlPart == null);
    }

    /// <summary>
    /// Specifies the w16sdtdh:storeItemChecksum attribute.
    /// </summary>
    /// <remarks>
    /// Added for compatibility with Word beta version 2102.
    /// Behavior and description can be changed in the future.
    /// </remarks>
    String getStoreItemChecksum() { return mStoreItemChecksum; }; void setStoreItemChecksum(String value) { mStoreItemChecksum = value; };

    private String mStoreItemChecksum;

    /// <summary>
    /// Indicates that stored checksum differs from actual.
    /// </summary>
    boolean checksumNotEqual(XmlMappingContext context) throws Exception
    {
        if (isEmpty())
            return false;

        if (!StringUtil.hasChars(getStoreItemChecksum()))
            return true;

        // WORDSNET-22189 Do not update SDT content when XML part checksum equal to saved in the SDT.
        String actualChecksum = context.getStoreItemChecksum(this);

        return !msString.equals(actualChecksum, getStoreItemChecksum());
    }

    /// <summary>
    /// Indicates that this object defines mapping to document core properties.
    /// </summary>
    boolean isCoreProperties() { return CORE_PROPERTIES_STORE_ITEM_ID.equals(getStoreItemId()); }

    /// <summary>
    /// Indicates that this object defines mapping to document extended properties.
    /// </summary>
    private boolean isExtendedProperties() { return EXTENDED_PROPERTIES_STORE_ITEM_ID.equals(getStoreItemId()); }

    private String mXPath = "";
    private String mPrefixMappings = "";
    private String mStoreItemId;
    private CustomXmlPart mCustomXmlPart;

    private StructuredDocumentTag mSdt;

    private static final String CORE_PROPERTIES_STORE_ITEM_ID = "{6C3C8BC8-F283-45AE-878A-BAB7291924A1}";
    private static final String EXTENDED_PROPERTIES_STORE_ITEM_ID = "{6668398D-A668-4E3E-A5EB-62B293D839F1}";

    private static final String DEFAULT_CORE_PROPS_PREFIX_MAPPING =
        "xmlns:ns0='http://purl.org/dc/elements/1.1/' xmlns:ns1='http://schemas.openxmlformats.org/package/2006/metadata/core-properties'";

    private static /*final*/ StringToStringDictionary G_PATH_TO_PROPERTY_NAME;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    /// <summary>
    /// Static ctor.
    /// </summary>
    static/* XmlMapping()*/
    {
        G_PATH_TO_PROPERTY_NAME = new StringToStringDictionary(false);
        G_PATH_TO_PROPERTY_NAME.add("title", PropertyName.TITLE);
        G_PATH_TO_PROPERTY_NAME.add("subject", PropertyName.SUBJECT);
        G_PATH_TO_PROPERTY_NAME.add("creator", PropertyName.AUTHOR);
        G_PATH_TO_PROPERTY_NAME.add("keywords", PropertyName.KEYWORDS);
        G_PATH_TO_PROPERTY_NAME.add("description", PropertyName.COMMENTS);
        G_PATH_TO_PROPERTY_NAME.add("category", PropertyName.CATEGORY);
        G_PATH_TO_PROPERTY_NAME.add("contentStatus", PropertyName.CONTENT_STATUS);
    }
}

