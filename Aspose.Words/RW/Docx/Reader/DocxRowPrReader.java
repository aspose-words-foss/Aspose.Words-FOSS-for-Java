// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/08/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Provides static method for reading row properties from different document parts.
/// </summary>
/*static*/ class DocxRowPrReader
{
	/* Simulation of static class by using private constructor */
	private DocxRowPrReader()
	{}

    /// <summary>
    /// Reads 'w:trPr' element from the specified reader. Reader should be positioned to element start.
    /// Returns a w:gridBefore value if encountered it.
    /// </summary>
    static void read(NrxDocumentReaderBase reader, TablePr tablePr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            xmlReader.ignoreElementNoWarn();
            return;
        }

        while (xmlReader.readChild("trPr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"cnfStyle"*/0:
                    // This is a calculated "helper" value, ignore.
                    xmlReader.ignoreElementNoWarn();
                    break;
                case /*"divId"*/1:
                    tablePr.setAttr(TableAttr.HTML_BLOCK_ID, xmlReader.readIntVal());
                    break;
                case /*"wBefore"*/2:
                {
                    PreferredWidth widthBefore = xmlReader.readLength(complianceInfo);
                    tablePr.setAttr(TableAttr.WIDTH_BEFORE, widthBefore);
                    tablePr.setAttr(TableAttr.WIDTH_BEFORE_ORIGINAL, widthBefore);
                    break;
                }
                case /*"wAfter"*/3:
                {
                    PreferredWidth widthAfter = xmlReader.readLength(complianceInfo);
                    tablePr.setAttr(TableAttr.WIDTH_AFTER, widthAfter);
                    tablePr.setAttr(TableAttr.WIDTH_AFTER_ORIGINAL, widthAfter);
                    break;
                }
                case /*"gridBefore"*/4:
                    tablePr.setAttr(TableAttr.SYS_GRID_BEFORE, xmlReader.readIntVal());
                    break;
                case /*"gridAfter"*/5:
                    tablePr.setAttr(TableAttr.SYS_GRID_AFTER, xmlReader.readIntVal());
                    break;
                case /*"cantSplit"*/6:
                    tablePr.setAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES, !xmlReader.readBoolVal());
                    break;
                case /*"trHeight"*/7:
                    readHeight(tablePr, xmlReader, complianceInfo);
                    break;
                case /*"tblHeader"*/8:
                    tablePr.setAttr(TableAttr.HEADING_FORMAT, xmlReader.readBoolVal());
                    break;
                case /*"tblCellSpacing"*/9:
                    {
                        PreferredWidth cellSpacing = xmlReader.readCellSpacing(complianceInfo);
                        if (cellSpacing != null)
                            tablePr.setAttr(TableAttr.CELL_SPACING, cellSpacing);
                        break;
                    }
                case /*"jc"*/10:
                    tablePr.setAlignment(NrxTableEnum.xmlToTableAlignment(xmlReader.readVal(), complianceInfo));
                    break;
                case /*"trPrChange"*/11:
                    // RK I'm not sure this is correct, but lets keep it for now.
                    // WORDSNET-9223 When there is changes in row formatting (trPr), we should read trPrChange
                    // when we read tblPrChange instead, nodes after trPrChange can be ignored. This causes problems during opening
                    DocxAnnotationReader.readRowPrFormatRevision(reader, tablePr);
                    break;
                case /*"del"*/12:
                    DocxAnnotationReader.readEditRevision(reader, tablePr, EditRevisionType.DELETION);
                    break;
                case /*"ins"*/13:
                    DocxAnnotationReader.readEditRevision(reader, tablePr, EditRevisionType.INSERTION);
                    break;
                case /*"hidden"*/14:
                    tablePr.setAttr(TableAttr.HIDDEN, xmlReader.readBoolVal());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    private static void readHeight(TablePr tablePr, NrxXmlReader xmlReader, OoxmlComplianceInfo complianceInfo)
    {
        /*HeightRule*/int hRule = HeightRule.AT_LEAST;
        int val = 0;

        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"hRule"*/15:
                    hRule = NrxTableEnum.xmlToHeightRule(xmlReader.getValue());
                    break;
                case /*"val"*/16:
                    // WORDSNET-8026 Word ignores value with leading space.
                    // Limit fix to this element for a while.
                    if(!msString.startsWith(xmlReader.getValue(), " ", StringComparison.ORDINAL))
                    {
                        val = xmlReader.getValueAsTwips(complianceInfo);
                    }
                    break;
                default:
                {
                    String message = msString.format(WarningStrings.UNEXPECTED_ATTRIBUTE, xmlReader.getLocalName());
                    xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.DOCX, message);
                    break;
                }
            }
        }

        tablePr.setAttr(TableAttr.ROW_HEIGHT, new Height(hRule, val));
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"cnfStyle",
		"divId",
		"wBefore",
		"wAfter",
		"gridBefore",
		"gridAfter",
		"cantSplit",
		"trHeight",
		"tblHeader",
		"tblCellSpacing",
		"jc",
		"trPrChange",
		"del",
		"ins",
		"hidden",
		"hRule",
		"val"
	);

}

