// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;



/// <summary>
/// Provides static method for reading table properties from different document parts.
/// </summary>
/*static*/ class DocxTablePrReader
{
	/* Simulation of static class by using private constructor */
	private DocxTablePrReader()
	{}

    /// <summary>
    /// Reads table 'w:tblPr' element from the specified reader.
    /// </summary>
    static void readTblPr(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        readTblPr(reader, tablePr, false);
    }

    /// <summary>
    /// Reads style 'w:tblPr' element from the specified reader.
    /// </summary>
    static void readStyleTblPr(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        readTblPr(reader, tablePr, true);
    }

    /// <summary>
    /// Reads 'w:tblPr' element from the specified reader.
    /// </summary>
    /// <param name="reader">DocxReader to read from. Should be positioned to element start.</param>
    /// <param name="tablePr">Table properties.</param>
    /// <param name="isStyle">True when reading a style, otherwise false.</param>
    private static void readTblPr(NrxDocumentReaderBase reader, TablePr tablePr, boolean isStyle)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();
        xmlReader.moveToElement();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            // WORDSNET-13301 Table layout type should be read to determine if merged cells should be combined.
            // Otherwise, different text may be returned in the skip-formatting mode.
            readTableLayoutIfExists(reader, tablePr);
            return;
        }

        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"tblStyle"*/0:
                    int istd = reader.resolveStyleIdToIstd(xmlReader.readVal(), StyleIndex.TABLE_NORMAL);
                    tablePr.setIstd(istd);
                    break;
                case /*"tblpPr"*/1:
                    // MS-OI29500 17.7.6.4.
                    // Word does not allow tblpPr element to be children of the tblPr element in style table properties.
                    if (!isStyle)
                        readTblpPr(xmlReader, tablePr, complianceInfo);
                    break;
                case /*"tblOverlap"*/2:
                    if ("never".equals(xmlReader.readVal()))
                        tablePr.setAttr(TableAttr.ALLOW_OVERLAP, false);
                    break;
                case /*"bidiVisual"*/3:
                    tablePr.setAttr(TableAttr.BIDI, xmlReader.readBoolVal());
                    break;
                case /*"tblStyleRowBandSize"*/4:
                    tablePr.setAttr(TableAttr.STYLE_ROW_BAND_SIZE, xmlReader.readIntVal());
                    break;
                case /*"tblStyleColBandSize"*/5:
                    tablePr.setAttr(TableAttr.STYLE_COL_BAND_SIZE, xmlReader.readIntVal());
                    break;
                default:
                    readTblPrCommon(reader, tablePr);
                    break;
            }
        }

        // WORDSNET-22587 When TableNormal is not default paragraph style and there is no style defined at the table,
        // Word applies default style to this table explicitly.
        if (!isStyle)
            reader.applyDefaultStyle(tablePr, StyleType.TABLE);
    }

    /// <summary>
    /// Reads a tblLayout element if it exists in children of current XML node.
    /// </summary>
    private static void readTableLayoutIfExists(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        String tagName = xmlReader.getLocalName();

        while (xmlReader.readChild(tagName))
        {
            if ("tblLayout".equals(xmlReader.getLocalName()))
                readTblLayout(xmlReader, tablePr);
            else
                xmlReader.ignoreElementNoWarn();
        }
    }

    /// <summary>
    /// Reads 'w:tblpPr' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    private static void readTblpPr(NrxXmlReader xmlReader, TablePr tablePr, OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"leftFromText"*/6:
                    tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_LEFT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"rightFromText"*/7:
                    tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_RIGHT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"topFromText"*/8:
                    tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_TOP, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"bottomFromText"*/9:
                    tablePr.setAttr(TableAttr.FRAME_DISTANCE_FROM_BOTTOM, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"vertAnchor"*/10:
                    tablePr.setAttr(TableAttr.RELATIVE_VERTICAL_POSITION, StyleConvertUtil.xmlToRelativeVerticalPosition(xmlReader.getValue()));
                    break;
                case /*"horzAnchor"*/11:
                    tablePr.setAttr(TableAttr.RELATIVE_HORIZONTAL_POSITION, StyleConvertUtil.xmlToTableRelativeHorizontalPosition(xmlReader.getValue()));
                    break;
                case /*"tblpXSpec"*/12:
                    tablePr.setAttr(TableAttr.HORIZONTAL_ALIGNMENT, StyleConvertUtil.xmlToHorizontalAlignment(xmlReader.getValue()));
                    break;
                case /*"tblpX"*/13:
                    NrxTableUtil.setPositionAttribute(tablePr, TableAttr.FRAME_LEFT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"tblpYSpec"*/14:
                    tablePr.setAttr(TableAttr.VERTICAL_ALIGNMENT, StyleConvertUtil.xmlToVerticalAlignment(xmlReader.getValue()));
                    break;
                case /*"tblpY"*/15:
                    NrxTableUtil.setPositionAttribute(tablePr, TableAttr.FRAME_TOP, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                default:
                    break;
            }
        }
    }

    /// <summary>
    /// Reads 'w:tblPrEx' element from the specified reader. Reader should be positioned to element start.
    /// </summary>
    static void readTblPrEx(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            xmlReader.ignoreElementNoWarn();
            return;
        }

        while (xmlReader.readChild("tblPrEx"))
            readTblPrCommon(reader, tablePr);
    }

    /// <summary>
    /// Reads child elements common to 'w:tblPr' and 'w:tblPrEx'. Reader should be positioned to child element start.
    /// </summary>
    private static void readTblPrCommon(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"tblW"*/16:
                tablePr.setAttr(TableAttr.PREFERRED_WIDTH, xmlReader.readLength(reader.getComplianceInfo()));
                break;
            case /*"jc"*/17:
                tablePr.setAlignment(NrxTableEnum.xmlToTableAlignment(xmlReader.readVal(), reader.getComplianceInfo()));
                break;
            case /*"tblCellSpacing"*/18:
                {
                    PreferredWidth cellSpacing = xmlReader.readCellSpacing(reader.getComplianceInfo());
                    if (cellSpacing != null)
                        tablePr.setAttr(TableAttr.CELL_SPACING, cellSpacing);
                    break;
                }
            case /*"tblInd"*/19:
                tablePr.setAttr(TableAttr.LEFT_INDENT, xmlReader.readLengthInTwips(reader.getComplianceInfo()));
                break;
            case /*"tblBorders"*/20:
                readBorders(xmlReader, tablePr, reader.getComplianceInfo());
                break;
            case /*"shd"*/21:
                tablePr.setAttr(TableAttr.SHADING, xmlReader.readShading());
                break;
            case /*"tblLayout"*/22:
                readTblLayout(xmlReader, tablePr);
                break;
            case /*"tblCellMar"*/23:
                readMargins(xmlReader, tablePr, reader.getComplianceInfo());
                break;
            case /*"tblLook"*/24:
                tablePr.setAttr(TableAttr.STYLE_OPTIONS,
                    DocxTableStyleLookReader.readTableStyleLook(xmlReader, reader.getComplianceInfo()));
                break;
            case /*"tblPrChange"*/25:     // This occurs when reading tbl.tblPr
            case /*"tblPrExChange"*/26:   // This occurs when reading tr.tblPrEx
                DocxAnnotationReader.readTablePrFormatRevision(reader, tablePr);
                break;
            case /*"tblDescription"*/27:
                tablePr.setDescription(xmlReader.readVal());
                break;
            case /*"tblCaption"*/28:
                tablePr.setTitle(xmlReader.readVal());
                break;
            default:
                xmlReader.ignoreElement();
                break;
        }
    }

    private static void readBorders(NrxXmlReader xmlReader, TablePr tablePr, OoxmlComplianceInfo cInfo)
    {
        while (xmlReader.readChild("tblBorders"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/29:
                    tablePr.setAttr(TableAttr.BORDER_TOP, xmlReader.readBorder());
                    break;
                case /*"left"*/30:
                    tablePr.setAttr(TableAttr.BORDER_LEFT, xmlReader.readBorder());
                    break;
                case /*"start"*/31: // iso 29500
                    {
                        cInfo.markAsIsoTransitional();
                        tablePr.setAttr(TableAttr.BORDER_LEFT, xmlReader.readBorder());
                        break;
                    }
                case /*"bottom"*/32:
                    tablePr.setAttr(TableAttr.BORDER_BOTTOM, xmlReader.readBorder());
                    break;
                case /*"right"*/33:
                    tablePr.setAttr(TableAttr.BORDER_RIGHT, xmlReader.readBorder());
                    break;
                case /*"end"*/34: // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        tablePr.setAttr(TableAttr.BORDER_RIGHT, xmlReader.readBorder());
                        break;
                    }
                case /*"insideH"*/35:
                    tablePr.setAttr(TableAttr.BORDER_HORIZONTAL, xmlReader.readBorder());
                    break;
                case /*"insideV"*/36:
                    tablePr.setAttr(TableAttr.BORDER_VERTICAL, xmlReader.readBorder());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readTblLayout(NrxXmlReader xmlReader, TablePr tablePr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/37:
                {
                    // RK In the OOXML spec there it is not clear whether the value is "auto" or "autofit" so work from the opposite.
                    tablePr.setAttr(TableAttr.ALLOW_AUTO_FIT, (!"fixed".equals(xmlReader.getValue())));
                    break;
                }
                default:
                    break;
            }
        }
    }

    private static void readMargins(NrxXmlReader xmlReader, TablePr tablePr, OoxmlComplianceInfo cInfo)
    {
        while (xmlReader.readChild("tblCellMar"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/29:
                    tablePr.setTopPadding(xmlReader.readLengthInTwips(cInfo));
                    break;
                case /*"left"*/30:
                    tablePr.setLeftPadding(xmlReader.readLengthInTwips(cInfo));
                    break;
                case /*"start"*/31: // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        tablePr.setLeftPadding(xmlReader.readLengthInTwips(cInfo));
                        break;
                    }
                case /*"bottom"*/32:
                    tablePr.setBottomPadding(xmlReader.readLengthInTwips(cInfo));
                    break;
                case /*"right"*/33:
                    tablePr.setRightPadding(xmlReader.readLengthInTwips(cInfo));
                    break;
                case /*"end"*/34: // iso29500
                    {
                        cInfo.markAsIsoTransitional();
                        tablePr.setRightPadding(xmlReader.readLengthInTwips(cInfo));
                        break;
                    }
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"tblStyle",
		"tblpPr",
		"tblOverlap",
		"bidiVisual",
		"tblStyleRowBandSize",
		"tblStyleColBandSize",
		"leftFromText",
		"rightFromText",
		"topFromText",
		"bottomFromText",
		"vertAnchor",
		"horzAnchor",
		"tblpXSpec",
		"tblpX",
		"tblpYSpec",
		"tblpY",
		"tblW",
		"jc",
		"tblCellSpacing",
		"tblInd",
		"tblBorders",
		"shd",
		"tblLayout",
		"tblCellMar",
		"tblLook",
		"tblPrChange",
		"tblPrExChange",
		"tblDescription",
		"tblCaption",
		"top",
		"left",
		"start",
		"bottom",
		"right",
		"end",
		"insideH",
		"insideV",
		"type"
	);

}

