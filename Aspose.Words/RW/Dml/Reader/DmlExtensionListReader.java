// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringToObjDictionary;
import com.aspose.XmlTextReaderNamespaceStorage;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;


/// <summary>
/// Reader for DrawingML extensions.
/// Element 20.1.2.2.15 extLst (Extension List, CT_OfficeArtExtensionList).
/// </summary>
class DmlExtensionListReader extends DmlReaderBase
{
    private DmlExtensionListReader(DocxDocumentReaderBase reader)
    {
        mDocumentReader = reader;
        mXmlReader = reader.getXmlReader();
    }

    public static StringToObjDictionary<DmlExtension> read(DocxDocumentReaderBase reader) throws Exception
    {
        DmlExtensionListReader extensionListReader = new DmlExtensionListReader(reader);
        return extensionListReader.readCore();
    }

    /// <summary>
    /// Reads DmlExtension complex type value of the DML element from the XML reader.
    /// </summary>
    private StringToObjDictionary<DmlExtension> readCore() throws Exception
    {
        StringToObjDictionary<DmlExtension> dmlExtensions = new StringToObjDictionary<DmlExtension>();
        String tagName = mXmlReader.getLocalName();

        // This is the normal behavior when the position is on the element end after reading an element.
        if (!mXmlReader.readChild(tagName))
            return dmlExtensions;

        while (true)
        {
            // We need to exit if reached end of the parent. But if we are on the start of a next element,
            // then everything is already done, we just need to process the tag.
            if (mXmlReader.isEndElement(tagName))
                break;

            readChild(dmlExtensions);
        }

        return dmlExtensions;
    }

    /// <summary>
    /// Reads DML extension and adds it to the array of extensions.
    /// </summary>
    private void readChild(StringToObjDictionary<DmlExtension> dmlExtensions) throws Exception
    {
        switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
        {
            case/*DmlExtensionName.EXT*/0:
            {
                String extensionUri = mXmlReader.readAttribute(DmlExtensionName.URI, "");
                DmlExtension dmlExtension = new DmlExtension(extensionUri, null);
                XmlTextReaderNamespaceStorage nameSpaceStorage = new XmlTextReaderNamespaceStorage(null);

                // Whitespace preserving is significant, otherwise spaces in texts can be corrupted.
                String extensionXml = mXmlReader.readOuterXml(null, nameSpaceStorage);
                mXmlReader = mDocumentReader.createAndPushPartReader(extensionXml, nameSpaceStorage.convertTo());

                switch (gStringSwitchMap.of(extensionUri))
                {
                    case/*DmlExtensionUri.NON_VISUAL_PR*/1:
                        dmlExtension.setNvPr(readNonVisualPrExtension());
                        break;
                    case/*DmlExtensionUri.HIDDEN_FILL*/2:
                        dmlExtension.setFillPr(readHiddenFillExtension(dmlExtension));
                        break;
                    case/*DmlExtensionUri.USE_LOCAL_DPI*/3:
                        dmlExtension.setUseLocalDpi(readUseLocalDpiExtension());
                        break;
                    case/*DmlExtensionUri.DATA_LABELS*/4:
                        dmlExtension.setDataLabelPr(DmlChartComplexTypesReader.readChartExtDataLabelPr(mDocumentReader));
                        break;
                    case/*DmlExtensionUri.FILTERING*/5:
                        readFilteringExtension(dmlExtension, extensionXml);
                        break;
                    case /*DmlExtensionUri.UNIQUE_ID*/6:
                        readUniqueIdExtension(dmlExtension, extensionXml);
                        break;
                    case/*DmlExtensionUri.VIDEO_PR*/7:
                        dmlExtension.setWebVideoPr(readWebVideoExtension());
                        break;
                    case/*DmlExtensionUri.DATA_MODEL_EXT*/8:
                        dmlExtension.setDrawingRelId(readDiagramDataModelExtension());
                        break;
                    case/*DmlExtensionUri.HIDDEN_LINE*/9:
                        dmlExtension.setOutlinePr(readHiddenLineExtension());
                        break;
                    case/*DmlExtensionUri.INVERT_SOLID_FILL_FMT*/10:
                        dmlExtension.setDmlChartSpPr(readDmlSpPrExtension());
                        break;
                    case/*DmlExtensionUri.SVG_BLIP*/11:
                        dmlExtension.setSvgBlip(readSvgBlip());
                        break;
                    case/*DmlExtensionUri.DECORATIVE*/12:
                        dmlExtension.setDecorative(readDecorativeExtension());
                        break;
                    default:
                    {
                        // Whitespace preserving is significant, otherwise spaces in texts can be corrupted.
                        dmlExtension.setXmlDoc(extensionXml);
                        extractEmbeddedImages(DmlExtensionName.EXT, dmlExtension);
                        break;
                    }
                }

                dmlExtensions.set(dmlExtension.getUri(), dmlExtension);
                mXmlReader = mDocumentReader.restorePartReader();
                break;
            }
            default:
            {
                warnUnexpected(mXmlReader);

                // WORDSNET-25518 The empty element cannot be skipped just with XmlReader.Skip().
                // So, we need to move to the next node in this case forcibly.
                if (mXmlReader.isEmptyElement())
                    mXmlReader.readNextNode();
                else
                    mXmlReader.ignoreElementNoWarn();

                break;
            }
        }
    }

    /// <summary>
    /// Reads dataModel extension.
    /// </summary>
    private String readDiagramDataModelExtension()
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.DATA_MODEL_EXT*/13:
                    return mXmlReader.readAttribute("relId", "");
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return "";
    }

    /// <summary>
    /// Reads webVideo extension.
    /// </summary>
    private DmlWebVideoProperties readWebVideoExtension()
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.WEB_VIDEO_PR*/14:
                {
                    if ("wp15".equals(mXmlReader.getPrefix()))
                        mDocumentReader.getComplianceInfo().isDrawingExtensions(true);

                    return readWebVideoPr();
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads webVideoPr element.
    /// </summary>
    /// <remarks>
    /// [MS-ODRAWXML] 2.20.1.1 webVideoPr.
    /// Element that specifies the properties for displaying an online video to the user.
    /// </remarks>
    private DmlWebVideoProperties readWebVideoPr()
    {
        DmlWebVideoProperties webVideoPr = new DmlWebVideoProperties();

        while (mXmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.EMBEDDED_HTML*/15:
                    webVideoPr.setEmbedHtml(mXmlReader.getValue());
                    break;
                case /*"w"*/16:
                    webVideoPr.setFrameWidth(mXmlReader.getValueAsDouble());
                    break;
                case /*"h"*/17:
                    webVideoPr.setFrameHeight(mXmlReader.getValueAsDouble());
                    break;
                default:
                    mXmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX,
                        msString.format(WarningStrings.UNEXPECTED_ATTRIBUTE, mXmlReader.getLocalName()));
                    break;
            }
        }

        return webVideoPr;
    }

    /// <summary>
    /// Reads hiddenLine extension.
    /// </summary>
    private DmlOutline readHiddenLineExtension() throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.HIDDEN_LINE*/18:
                    return DmlOutlineReader.read(mDocumentReader);
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads useLocalDpi extension.
    /// </summary>
    private boolean readUseLocalDpiExtension()
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*DmlExtensionName.USE_LOCAL_DPI*/19:
                {
                    // TODO: Do we need to set IsDrawingExtensions here? It seems yes, but check DD comments in
                    // OoxmlComplianceInfo.GetCompliance() method. Inside test TestDmlPictureBullet it is needed to save
                    // document to Ecma376 but if IsDrawingExtensions is set it will be impossible.
                    return mXmlReader.readBoolAttribute("val", false);
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return false;
    }

    /// <summary>
    /// Reads Decorative extension.
    /// </summary>
    private boolean readDecorativeExtension()
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.DECORATIVE*/20:
                    return mXmlReader.readBoolAttribute("val", false);
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return false;
    }

    /// <summary>
    /// Reads cNvPr extensions.
    /// </summary>
    private DmlNvDrawingProperties readNonVisualPrExtension() throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.C_NV_PR*/21:
                {
                    if ("dgm14".equals(mXmlReader.getPrefix()))
                        mDocumentReader.getComplianceInfo().isDrawingExtensions(true);

                    return readNvPr();
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads hiddenFill extension.
    /// </summary>
    private DmlFill readHiddenFillExtension(DmlExtension dmlExtension) throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.HIDDEN_FILL*/22:
                {
                    mDocumentReader.getComplianceInfo().isDrawingExtensions(true);
                    return readHiddenFill(dmlExtension);
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads hiddenFill element.
    /// </summary>
    private DmlFill readHiddenFill(DmlExtension dmlExtension) throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.HIDDEN_FILL))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"noFill"*/23:
                case /*"grpFill"*/24:
                case /*"pattFill"*/25:
                case /*"blipFill"*/26:
                case /*"gradFill"*/27:
                case /*"solidFill"*/28:
                {
                    // Ignore content, when more then one fill defined.
                    if (dmlExtension.getFillPr() != null)
                    {
                        warnUnexpectedAndIgnoreElement(mXmlReader);
                        break;
                    }

                    return DmlFillReader.read(mDocumentReader);
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads "InvertSolidFillFmt" extension.
    /// </summary>
    private DmlChartSpPr readDmlSpPrExtension() throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case/*DmlExtensionName.INVERT_SOLID_FILL_FMT*/29:
                    return readSpPr();
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads <see cref="DmlChartSpPr"/>.
    /// </summary>
    private DmlChartSpPr readSpPr() throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.INVERT_SOLID_FILL_FMT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"spPr"*/30:
                {
                    mDocumentReader.getComplianceInfo().isDrawingExtensions(true);
                    DmlChartSpPr spPr = new DmlChartSpPr();
                    DmlChartComplexTypesReader.readChartSpPr(mDocumentReader, spPr);
                    return spPr;
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Reads cNvPr element.
    /// </summary>
    private DmlNvDrawingProperties readNvPr() throws Exception
    {
        int id = mXmlReader.readIntAttribute("id", 0);
        String name = mXmlReader.readAttribute("name", null);

        DmlNvDrawingProperties nvPr = new DmlNvDrawingProperties(id, name);

        while (mXmlReader.readChild(DmlExtensionName.C_NV_PR))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*"hlinkClick"*/31:
                    nvPr.setHlinkClick(DmlHlinkReader.read(mDocumentReader));
                    break;
                case /*"hlinkHover"*/32:
                    nvPr.setHlinkHover(DmlHlinkReader.read(mDocumentReader));
                    break;
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return nvPr;
    }

    /// <summary>
    /// Reads svgBlip extension.
    /// </summary>
    private DmlBlip readSvgBlip() throws Exception
    {
        while (mXmlReader.readChild(DmlExtensionName.EXT))
        {
            switch (gStringSwitchMap.of(mXmlReader.getLocalName()))
            {
                case /*DmlExtensionName.SVG_BLIP*/33:
                {
                    return DmlFillReader.readBlip(mDocumentReader);
                }
                default:
                    warnUnexpectedAndIgnoreElement(mXmlReader);
                    break;
            }
        }

        return null;
    }

    /// <summary>
    /// Extracts embedded images and stores them in <see cref="DmlExtension.EmbeddedImages"/> in order
    /// to create correct relation during writing raw xml data.
    /// </summary>
    private void extractEmbeddedImages(String parentElement, DmlExtension dmlExtension) throws Exception
    {
        while (mXmlReader.readChild(parentElement))
        {
            String embed = mXmlReader.readAttribute("embed", "");
            if (StringUtil.hasChars(embed))
            {
                byte[] embedImageBytes = mDocumentReader.isValidImagePart(embed) ? mDocumentReader.getBinData(embed) : null;
                dmlExtension.getEmbeddedImages().add(embed, embedImageBytes);
            }

            if (!mXmlReader.isEmptyElement())
                extractEmbeddedImages(mXmlReader.getLocalName(), dmlExtension);

            mXmlReader.ignoreElementNoWarn();
        }
    }

    /// <summary>
    /// Reads filtering extension.
    /// </summary>
    private void readFilteringExtension(DmlExtension dmlExtension, String extensionXml) throws Exception
    {
        DmlChartValueRef valueRef = DmlChartComplexTypesReader.readChartDataLabelsRange(mDocumentReader);

        // WORDSNET-15427 Read and write "filtering" extension as raw XML, when “datalabelsRange”
        // element is missing.
        if (valueRef != null)
            dmlExtension.getDataLabelsRangeData().setValueRef(valueRef);
        else
            dmlExtension.setXmlDoc(extensionXml);
    }

    /// <summary>
    /// Reads unique ID extension.
    /// </summary>
    private void readUniqueIdExtension(DmlExtension dmlExtension, String extensionXml)
    {
        dmlExtension.setDataLabelId(DmlChartComplexTypesReader.readUniqueIdExtension(mDocumentReader));
        dmlExtension.setXmlDoc(extensionXml);
    }

    private /*final*/ DocxDocumentReaderBase mDocumentReader;
    private NrxXmlReader mXmlReader;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"ext",
		"{E40237B7-FDA0-4F09-8148-C483321AD2D9}",
		"{909E8E84-426E-40DD-AFC4-6F175D3DCCD1}",
		"{28A0092B-C50C-407E-A947-70E740481C1C}",
		"{CE6537A1-D6FC-4f65-9D91-7224C49458BB}",
		"{02D57815-91ED-43cb-92C2-25804820EDAC}",
		"{C3380CC4-5D6E-409C-BE32-E72D297353CC}",
		"{C809E66F-F1BF-436E-b5F7-EEA9579F0CBA}",
		"http://schemas.microsoft.com/office/drawing/2008/diagram",
		"{91240B29-F687-4F45-9708-019B960494DF}",
		"{6F2FDCE9-48DA-4B69-8628-5D25D57E5C99}",
		"{96DAC541-7B7A-43D3-8B79-37D633B846F1}",
		"{C183D7F6-B498-43B3-948B-1728B52AA6E4}",
		"dataModelExt",
		"webVideoPr",
		"embeddedHtml",
		"w",
		"h",
		"hiddenLine",
		"useLocalDpi",
		"decorative",
		"cNvPr",
		"hiddenFill",
		"noFill",
		"grpFill",
		"pattFill",
		"blipFill",
		"gradFill",
		"solidFill",
		"invertSolidFillFmt",
		"spPr",
		"hlinkClick",
		"hlinkHover",
		"svgBlip"
	);

}

