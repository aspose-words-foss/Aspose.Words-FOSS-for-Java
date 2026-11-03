// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2017 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;


/// <summary>
/// Implements reading common elements related to document setting for both DOCX and WordML formats.
/// </summary>
/*static*/ class NrxSettingsReader
{
	/* Simulation of static class by using private constructor */
	private NrxSettingsReader()
	{}

    static void readRsids(NrxXmlReader xmlReader, DocPr docPr)
    {
        String attrVal;

        while (xmlReader.readChild("rsids"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"rsidRoot"*/0:
                {  
                    attrVal = xmlReader.readVal();
                    // WORDSNET-16208 Do not set document properties when attribute "val" is missed in "rsid" or "rsidRoot".
                    if (StringUtil.hasChars(attrVal))
                        docPr.RsidRoot = NrxXmlUtil.hexToInt(attrVal);
                    break;
                }
                case /*"rsid"*/1:
                {
                    attrVal = xmlReader.readVal();
                    if (StringUtil.hasChars(attrVal))
                        docPr.Rsids.add(NrxXmlUtil.hexToInt(attrVal));
                    break;
                }
                default:
                    // Ignore.
                    break;
            }
        }
    }

    static void readDivs(NrxDocumentReaderBase reader)
    {
        readDivs(reader, 0);
    }

    /// <summary>
    /// Reads <see cref="MultiplePagesType"/>.
    /// </summary>
    /// <remarks>
    /// Word seems to set the priority for types when reading several different types,
    /// but lets postpone this for a while.
    /// </remarks>
    static void readMultiplePagesType(NrxDocumentReaderBase reader, DocPr docPr)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        switch (gStringSwitchMap.of(xmlReader.getLocalName()))
        {
            case /*"bookFoldPrinting"*/2:
            {
                if (xmlReader.readBoolVal())
                    docPr.MultiplePages = MultiplePagesType.BOOK_FOLD_PRINTING;
                break;
            }
            case /*"bookFoldRevPrinting"*/3:
            {
                if (xmlReader.readBoolVal())
                    docPr.MultiplePages = MultiplePagesType.BOOK_FOLD_PRINTING_REVERSE;
                break;
            }
            case /*"mirrorMargins"*/4:
            {
                if (xmlReader.readBoolVal())
                    docPr.MultiplePages = MultiplePagesType.MIRROR_MARGINS;
                break;
            }
            case /*"printTwoOnOne"*/5:
            {
                if (xmlReader.readBoolVal())
                    docPr.MultiplePages = MultiplePagesType.TWO_PAGES_PER_SHEET;
                break;
            }
            default:
                xmlReader.ignoreElement();
                break;
        }
    }

    private static void readDivs(NrxDocumentReaderBase reader, int parentId)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        if (reader.getLoadOptions().getSkipFormatting())
        {
            xmlReader.ignoreElementNoWarn();
            return;
        }

        String tagName = xmlReader.getLocalName();
        while (xmlReader.readChild(tagName))
        {
            if ("div".equals(xmlReader.getLocalName()))
            {
                HtmlBlock htmlBlock = new HtmlBlock(xmlReader.readIntAttribute("id", -1));
                htmlBlock.setParentId(parentId);
                readDiv(reader, htmlBlock);
                htmlBlock.refine();
                reader.getDocument().getHtmlBlockCollection().add(htmlBlock);
            }
            else
                xmlReader.ignoreElement();
        }
    }

    private static void readBorders(NrxDocumentReaderBase reader, HtmlBlock htmlBlock)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();

        while (xmlReader.readChild("divBdr"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"top"*/6:
                    htmlBlock.getParaPr().setAttr(ParaAttr.BORDER_TOP, xmlReader.readBorder());
                    break;
                case /*"bottom"*/7:
                    htmlBlock.getParaPr().setAttr(ParaAttr.BORDER_BOTTOM, xmlReader.readBorder());
                    break;
                case /*"left"*/8:
                    htmlBlock.getParaPr().setAttr(ParaAttr.BORDER_LEFT, xmlReader.readBorder());
                    break;
                case /*"right"*/9:
                    htmlBlock.getParaPr().setAttr(ParaAttr.BORDER_RIGHT, xmlReader.readBorder());
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

    /// <summary>
    /// Reads single 'div' element .
    /// </summary>
    private static void readDiv(NrxDocumentReaderBase reader, HtmlBlock htmlBlock)
    {
        NrxXmlReader xmlReader = reader.getXmlReader();
        OoxmlComplianceInfo complianceInfo = reader.getComplianceInfo();

        while (xmlReader.readChild("div"))
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"blockQuote"*/10:
                    htmlBlock.setHtmlBlockType(HtmlBlockType.BLOCK_QUOTE);
                    break;
                case /*"bodyDiv"*/11:
                    htmlBlock.setHtmlBlockType(HtmlBlockType.BODY);
                    break;
                case /*"divsChild"*/12:
                    readDivs(reader, htmlBlock.getId());
                    break;
                case /*"divBdr"*/13:
                    readBorders(reader, htmlBlock);
                    break;
                case /*"marLeft"*/14:
                    htmlBlock.getParaPr().setAttr(ParaAttr.HTML_MARGIN_LEFT, xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"marRight"*/15:
                    htmlBlock.getParaPr().setAttr(ParaAttr.HTML_MARGIN_RIGHT, xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"marTop"*/16:
                    htmlBlock.getParaPr().setAttr(ParaAttr.HTML_MARGIN_TOP, xmlReader.readValAsTwips(complianceInfo));
                    break;
                case /*"marBottom"*/17:
                    htmlBlock.getParaPr().setAttr(ParaAttr.HTML_MARGIN_BOTTOM, xmlReader.readValAsTwips(complianceInfo));
                    break;
                default:
                    xmlReader.ignoreElement();
                    break;
            }
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"rsidRoot",
		"rsid",
		"bookFoldPrinting",
		"bookFoldRevPrinting",
		"mirrorMargins",
		"printTwoOnOne",
		"top",
		"bottom",
		"left",
		"right",
		"blockQuote",
		"bodyDiv",
		"divsChild",
		"divBdr",
		"marLeft",
		"marRight",
		"marTop",
		"marBottom"
	);

}

