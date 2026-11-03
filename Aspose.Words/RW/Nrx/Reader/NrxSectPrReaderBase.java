// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2011 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MathUtil;


abstract class NrxSectPrReaderBase
{
    /// <summary>
    /// Reads 'w:sectPr' element from the specified reader.
    /// Reader should be positioned to element start.
    /// </summary>
    /// <param name="reader">DocxReader to read from. Should be positioned to element start.</param>
    /// <param name="sectPr">Section where to read properties to.</param>
    void read(NrxDocumentReaderBase reader, SectPr sectPr) throws Exception
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        // andrnosk: The document has several body/sectPr in the middle of the document.
        // MS Word takes HeadersFooters from the first sectPr and the other properties from the last sectPr.
        // That is why we need to preserve HeadersFooters from the first read sectPr.
        // IN. There can be SectPrs without any properties, but having HeaderFooter
        // references (see TestJira8816 for example). We should consider it too.
        // WORDSNET-27383, WORDSNET-27273 If a sectPr element is a direct child of a body, MS Word ignores
        // headers/footers of subsequent sectPr elements.
        boolean hasBlockLevelSectPr = reader.isBlockLevelGlobalSectPr();
        Section curSection = (Section)reader.getCurContainer().getAncestor(NodeType.SECTION);
        boolean hasHeadersFooters = (curSection.getHeadersFooters().getCount() > 0);

        // If we read DOCX document we need to read section id.
        if (reader.isDocx())
        {
            while (xmlReader.moveToNextAttribute())
            {
                switch (gStringSwitchMap.of(xmlReader.getLocalName()))
                {
                    case /*"rsidSect"*/0:
                    {
                        int rsid = NrxXmlUtil.tryHexToInt(xmlReader.getValue());
                        if (rsid != Integer.MIN_VALUE)
                            sectPr.setAttr(SectAttr.RSID, rsid);
                        break;
                    }
                    default:
                        // Do nothing.
                        break;
                }
            }
        }

        while (xmlReader.readChild("sectPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/1:
                    sectPr.setAttr(SectAttr.SECTION_START, NrxSectEnum.xmlToSectionStart(xmlReader.readVal()));
                    break;
                case /*"pgSz"*/2:
                    readPageSize(xmlReader, sectPr, complianceInfo);
                    break;
                case /*"pgMar"*/3:
                    readPageMargins(xmlReader, sectPr, complianceInfo);
                    break;
                case /*"paperSrc"*/4:
                    readPaperSource(xmlReader, sectPr);
                    break;
                case /*"pgBorders"*/5:
                    readBorders(xmlReader, sectPr);
                    break;
                case /*"lnNumType"*/6:
                    readLineNumberType(xmlReader, sectPr, complianceInfo);
                    break;
                case /*"pgNumType"*/7:
                    readPageNumberType(reader, sectPr);
                    break;
                case /*"cols"*/8:
                    readCols(xmlReader, sectPr, complianceInfo);
                    break;
                case /*"formProt"*/9:
                    // WORDSNET-20831 SectAttr.Unlocked is true when the section is not protected with forms,
                    // but formProt = 0 (false) when the section is not protected (see 2.6.6 in OOXML spec). 
                    // So the correct value of SectAttr.Unlocked should be !formProt.
                    sectPr.setAttr(SectAttr.UNLOCKED, !xmlReader.readBoolVal());
                    break;
                case /*"vAlign"*/10:
                    sectPr.setAttr(SectAttr.VERTICAL_ALIGNMENT,
                        NrxSectEnum.xmlToPageVerticalAlignment(xmlReader.readVal()));
                    break;
                case /*"noEndnote"*/11:
                    sectPr.setAttr(SectAttr.SUPPRESS_ENDNOTES, xmlReader.readBoolVal());
                    break;
                case /*"titlePg"*/12:
                    sectPr.setAttr(SectAttr.DIFFERENT_FIRST_PAGE_HEADER_FOOTER, xmlReader.readBoolVal());
                    break;
                case /*"textDirection"*/13: // DOCX
                {
                    /*TextOrientation*/int orientation = StyleConvertUtil.xmlToTextOrientation(xmlReader.readVal(),
                        complianceInfo);
                    sectPr.setAttr(SectAttr.TEXT_FLOW, TextFlowOrientationConverter.fromTextOrientation(orientation));
                    break;
                }
                case /*"textFlow"*/14: // WML
                {
                    /*TextOrientation*/int orientation = StyleConvertUtil.xmlToTextOrientation(xmlReader.readVal(),
                        complianceInfo);
                    sectPr.setAttr(SectAttr.TEXT_FLOW, TextFlowOrientationConverter.fromTextOrientation(orientation));
                    break;
                }
                case /*"bidi"*/15:
                    sectPr.setAttr(SectAttr.BIDI, xmlReader.readBoolVal());
                    break;
                case /*"rtlGutter"*/16:
                    sectPr.setAttr(SectAttr.RTL_GUTTER, xmlReader.readBoolVal());
                    break;
                case /*"docGrid"*/17:
                    readDocGrid(xmlReader, sectPr);
                    break;
                case /*"br"*/18:
                    xmlReader.getRunTextBuilder().append(NrxRunReaderBase.readBreak(xmlReader, new RunPr()));
                    break;
                case /*"footnoteColumns"*/19:
                {
                    int columnCount = xmlReader.readIntVal();
                    if (columnCount > 0)
                    {
                        sectPr.setAttr(SectAttr.FOOTNOTE_COLUMNS, columnCount);
                        complianceInfo.isDocxExtensions(true);
                    }
                    break;
                }
                case /*"p"*/20:
                {
                    // WORDSNET-16627 Resilency. Paragraph is inside w:sectPr.
                    resilentParaRead(reader);
                    break;
                }
                case /*"tbl"*/21:
                {
                    // WORDSNET-16627 Resilency. Table is inside w:sectPr.
                    DocxTableReader.read(reader);
                    break;
                }
                // WORDSNET-19577 Added reading MultiplePagesType.
                case /*"bookFoldPrinting"*/22:
                case /*"bookFoldRevPrinting"*/23:
                case /*"mirrorMargins"*/24:
                case /*"printTwoOnOne"*/25:
                    NrxSettingsReader.readMultiplePagesType(reader, reader.getDocument().getDocPr());
                    break;
                default:
                    readFormatSpecific(xmlReader.getLocalName(), reader, sectPr, hasBlockLevelSectPr, hasHeadersFooters);
                    break;
            }
        }
    }

    /// <summary>
    /// Resiliently reads paragraph.
    /// </summary>
    /// <param name="reader">
    /// DocxReader to read from.
    /// </param>
    private static void resilentParaRead(NrxDocumentReaderBase reader) throws Exception
    {
        DocxHyperlinkReader hyperlinkReader = new DocxHyperlinkReader();

        DocxSmartTagReader smartTagReader = new DocxSmartTagReader();

        DocxInlineReader inlineReader = new DocxInlineReader(hyperlinkReader, smartTagReader);
        hyperlinkReader.setInlineReader(inlineReader);
        
        DocxParaReader paraReader =
            new DocxParaReader(
                DocxReaderFactory.getParaPrReader(),
                inlineReader);
        paraReader.read(reader);
    }

    protected abstract void readFormatSpecific(String localName, 
        NrxDocumentReaderBase reader, SectPr sectPr,
        boolean hasSectPr, boolean hasHeadersFooters) throws Exception;

    private static void readPageSize(NrxXmlReader xmlReader, SectPr sectPr, OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"w"*/26:
                    sectPr.setAttr(SectAttr.PAGE_WIDTH, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"h"*/27:
                    sectPr.setAttr(SectAttr.PAGE_HEIGHT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"orient"*/28:
                    sectPr.setAttr(SectAttr.ORIENTATION, NrxSectEnum.xmlToOrientation(xmlReader.getValue()));
                    break;
                case /*"code"*/29:
                    sectPr.setAttr(SectAttr.PAPER_CODE, xmlReader.getValueAsInt());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readPageMargins(NrxXmlReader xmlReader, SectPr sectPr, OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/30:
                    sectPr.setAttr(SectAttr.TOP_MARGIN, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"right"*/31:
                    sectPr.setAttr(SectAttr.RIGHT_MARGIN, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"bottom"*/32:
                    sectPr.setAttr(SectAttr.BOTTOM_MARGIN, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"left"*/33:
                    sectPr.setAttr(SectAttr.LEFT_MARGIN, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"header"*/34:
                    sectPr.setAttr(SectAttr.HEADER_DISTANCE, MathUtil.castIntToShort(xmlReader.getValueAsTwips(complianceInfo)));
                    break;
                case /*"footer"*/35:
                    sectPr.setAttr(SectAttr.FOOTER_DISTANCE, MathUtil.castIntToShort(xmlReader.getValueAsTwips(complianceInfo)));
                    break;
                case /*"gutter"*/36:
                    sectPr.setAttr(SectAttr.GUTTER, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readPaperSource(NrxXmlReader xmlReader, SectPr sectPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"first"*/37:
                    sectPr.setAttr(SectAttr.FIRST_PAGE_TRAY, xmlReader.getValueAsInt());
                    break;
                case /*"other"*/38:
                    sectPr.setAttr(SectAttr.OTHER_PAGES_TRAY, xmlReader.getValueAsInt());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readBorders(NrxXmlReader xmlReader, SectPr sectPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"zOrder"*/39: // DOCX
                case /*"z-order"*/40: // WML
                {
                    switch (gStringSwitchMap.of(xmlReader.getValue()))
                    {
                        case /*"front"*/44:
                            sectPr.setAttr(SectAttr.BORDER_ALWAYS_IN_FRONT, true);
                            break;
                        case /*"back"*/45:
                            sectPr.setAttr(SectAttr.BORDER_ALWAYS_IN_FRONT, false);
                            break;
                        default:
                            xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getValue());
                            break;
                    }
                    break;
                }
                case /*"display"*/41:
                    sectPr.setAttr(SectAttr.BORDER_APPLIES_TO, NrxSectEnum.xmlToPageBorderAppliesTo(xmlReader.getValue()));
                    break;
                case /*"offsetFrom"*/42: // DOCX
                case /*"offset-from"*/43: // WML
                    sectPr.setAttr(SectAttr.BORDER_DISTANCE_FROM, NrxSectEnum.xmlToPageBorderDistanceFrom(xmlReader.getValue()));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }

        while (xmlReader.readChild("pgBorders"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/30:
                    sectPr.setAttr(SectAttr.BORDER_TOP, xmlReader.readBorder());
                    break;
                case /*"left"*/33:
                    sectPr.setAttr(SectAttr.BORDER_LEFT, xmlReader.readBorder());
                    break;
                case /*"bottom"*/32:
                    sectPr.setAttr(SectAttr.BORDER_BOTTOM, xmlReader.readBorder());
                    break;
                case /*"right"*/31:
                    sectPr.setAttr(SectAttr.BORDER_RIGHT, xmlReader.readBorder());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readLineNumberType(NrxXmlReader xmlReader, SectPr sectPr, 
        OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"countBy"*/46: // DOCX
                case /*"count-by"*/47: // WML
                    sectPr.setAttr(SectAttr.LINE_NUMBER_COUNT_BY, xmlReader.getValueAsInt());
                    break;
                case /*"start"*/48:
                    sectPr.setAttr(SectAttr.LINE_STARTING_NUMBER, xmlReader.getValueAsInt() + 1);
                    break;
                case /*"distance"*/49:
                    sectPr.setAttr(SectAttr.LINE_NUMBER_DISTANCE_FROM_TEXT, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"restart"*/50:
                    sectPr.setAttr(SectAttr.LINE_NUMBER_RESTART_MODE, NrxSectEnum.xmlToLineNumberRestartMode(xmlReader.getValue()));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readPageNumberType(NrxDocumentReaderBase reader, SectPr sectPr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"fmt"*/51:
                    sectPr.setPageNumberStyle(reader.isDocx() ? 
                        DocxEnum.docxToNumberStyle(xmlReader.getValue()) : 
                        WmlEnum.wmlToNumberStyle(xmlReader.getValue()));
                    break;
                case /*"start"*/48:
                    sectPr.setPageStartingNumber(xmlReader.getValueAsInt());
                    sectPr.setRestartPageNumbering(true);
                    break;
                case /*"chapStyle"*/52: // DOCX
                case /*"chap-style"*/53: // WML
                    sectPr.setHeadingLevelForChapter(xmlReader.getValueAsInt());
                    break;
                case /*"chapSep"*/54: // DOCX
                case /*"chap-sep"*/55: // WML
                    sectPr.setChapterPageSeparator(NrxSectEnum.xmlToChapterPageSeparator(xmlReader.getValue()));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS,
                        reader.isDocx() ? WarningSource.DOCX : WarningSource.WORD_ML,
                        xmlReader.getLocalName());
                    break;
            }
        }
    }

    private static void readCols(NrxXmlReader xmlReader, SectPr sectPr, OoxmlComplianceInfo complianceInfo)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"num"*/56:
                    sectPr.setAttr(SectAttr.COLUMNS_COUNT, xmlReader.getValueAsInt());
                    break;
                case /*"sep"*/57:
                    sectPr.setAttr(SectAttr.COLUMNS_LINE_BETWEEN, xmlReader.getValueAsBool());
                    break;
                case /*"space"*/58:
                    sectPr.setAttr(SectAttr.COLUMNS_SPACING, xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"equalWidth"*/59:
                    sectPr.setAttr(SectAttr.COLUMNS_EVENLY_SPACED, xmlReader.getValueAsBool());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }

        TextColumnCollectionInternal cols = new TextColumnCollectionInternal();

        while (xmlReader.readChild("cols"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"col"*/60:
                    cols.add(readCol(xmlReader, complianceInfo));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }

        if (cols.getCount() > 0)
            sectPr.setAttr(SectAttr.COLUMNS, cols);
    }

    private static TextColumn readCol(NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        TextColumn col = new TextColumn();

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"w"*/26:
                    col.setRawWidth(xmlReader.getValueAsTwips(complianceInfo));
                    break;
                case /*"space"*/58:
                    col.setRawSpaceAfter(xmlReader.getValueAsTwips(complianceInfo));
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }

        return col;
    }

    private static void readDocGrid(NrxXmlReader xmlReader, SectPr sectPr)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"type"*/1:
                    /*SectionLayoutMode*/int gridType = NrxSectEnum.xmlToDocGridType(xmlReader.getValue());
                    sectPr.setAttr(SectAttr.GRID_TYPE, gridType);
                    break;
                case /*"linePitch"*/61: // DOCX
                case /*"line-pitch"*/62: // WML
                    sectPr.setAttr(SectAttr.LINE_PITCH, xmlReader.getValueAsInt());
                    break;
                case /*"charSpace"*/63: // DOCX
                case /*"char-space"*/64: // WML
                    sectPr.setAttr(SectAttr.CHAR_SPACE, xmlReader.getValueAsInt());
                    break;
                default:
                    xmlReader.warn(WarningType.MINOR_FORMATTING_LOSS, WarningSource.UNKNOWN, xmlReader.getLocalName());
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rsidSect",
		"type",
		"pgSz",
		"pgMar",
		"paperSrc",
		"pgBorders",
		"lnNumType",
		"pgNumType",
		"cols",
		"formProt",
		"vAlign",
		"noEndnote",
		"titlePg",
		"textDirection",
		"textFlow",
		"bidi",
		"rtlGutter",
		"docGrid",
		"br",
		"footnoteColumns",
		"p",
		"tbl",
		"bookFoldPrinting",
		"bookFoldRevPrinting",
		"mirrorMargins",
		"printTwoOnOne",
		"w",
		"h",
		"orient",
		"code",
		"top",
		"right",
		"bottom",
		"left",
		"header",
		"footer",
		"gutter",
		"first",
		"other",
		"zOrder",
		"z-order",
		"display",
		"offsetFrom",
		"offset-from",
		"front",
		"back",
		"countBy",
		"count-by",
		"start",
		"distance",
		"restart",
		"fmt",
		"chapStyle",
		"chap-style",
		"chapSep",
		"chap-sep",
		"num",
		"sep",
		"space",
		"equalWidth",
		"col",
		"linePitch",
		"line-pitch",
		"charSpace",
		"char-space"
	);

}

