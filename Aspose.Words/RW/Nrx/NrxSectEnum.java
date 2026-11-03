// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;


/*static*/ class NrxSectEnum
{
	/* Simulation of static class by using private constructor */
	private NrxSectEnum()
	{}

    static /*FootnoteNumberingRule*/int xmlToNoteNumberingRule(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"continuous"*/0: return FootnoteNumberingRule.CONTINUOUS;
            case /*"eachSect"*/1: return FootnoteNumberingRule.RESTART_SECTION;
            case /*"each-sect"*/2: return FootnoteNumberingRule.RESTART_SECTION;
            case /*"eachPage"*/3: return FootnoteNumberingRule.RESTART_PAGE;
            case /*"each-page"*/4: return FootnoteNumberingRule.RESTART_PAGE;
            default: return FootnoteNumberingRule.DEFAULT;
        }
    }

    static String noteNumberingRuleToXml(/*FootnoteNumberingRule*/int value, boolean isDocx)
    {
        if (value == FootnoteNumberingRule.DEFAULT)
            return "";

        switch (value)
        {
            case FootnoteNumberingRule.CONTINUOUS: return "continuous";
            case FootnoteNumberingRule.RESTART_SECTION: return (isDocx) ? "eachSect" : "each-sect";
            case FootnoteNumberingRule.RESTART_PAGE: return (isDocx) ? "eachPage" : "each-page";
            default: return "";
        }
    }

    static /*SectionLayoutMode*/int xmlToDocGridType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"linesAndChars"*/5:
            case /*"lines-and-chars"*/6:
                return SectionLayoutMode.GRID;
            case /*"lines"*/7:
                return SectionLayoutMode.LINE_GRID;
            case /*"snapToChars"*/8:
            case /*"snap-to-chars"*/9:
                return SectionLayoutMode.SNAP_TO_CHARS;
            default:
                return SectionLayoutMode.DEFAULT;
        }
    }

    static String docGridTypeToXml(/*SectionLayoutMode*/int value, boolean isDocx)
    {
        switch (value)
        {
            case SectionLayoutMode.GRID:
                return isDocx ? "linesAndChars" : "lines-and-chars";
            case SectionLayoutMode.LINE_GRID:
                return "lines";
            case SectionLayoutMode.SNAP_TO_CHARS:
                return isDocx ? "snapToChars" : "snap-to-chars";
            default:
                return "";
        }
    }

    static /*HeaderFooterType*/int xmlToHeaderFooterType(String value, boolean isHeader)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"even"*/10:
                if (isHeader)
                    return HeaderFooterType.HEADER_EVEN;
                else
                    return HeaderFooterType.FOOTER_EVEN;
            case /*"first"*/11:
                if (isHeader)
                    return HeaderFooterType.HEADER_FIRST;
                else
                    return HeaderFooterType.FOOTER_FIRST;
            default:
                if (isHeader)
                    return HeaderFooterType.HEADER_PRIMARY;
                else
                    return HeaderFooterType.FOOTER_PRIMARY;
        }
    }

    static String headerFooterTypeToXml(/*HeaderFooterType*/int value, boolean isDocx)
    {
        switch (value)
        {
            case HeaderFooterType.HEADER_EVEN:
            case HeaderFooterType.FOOTER_EVEN:
                return "even";
            case HeaderFooterType.HEADER_FIRST:
            case HeaderFooterType.FOOTER_FIRST:
                return "first";
            case HeaderFooterType.HEADER_PRIMARY:
            case HeaderFooterType.FOOTER_PRIMARY:
                return isDocx ? "default" : "odd";
            default:
                throw new IllegalStateException("Unknown header footer type.");
        }
    }

    static /*FootnotePosition*/int xmlToFootnotePosition(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"pageBottom"*/12:
            case /*"page-bottom"*/13:
                return FootnotePosition.BOTTOM_OF_PAGE;
            case /*"beneathText"*/14:
            case /*"beneath-text"*/15:
                return FootnotePosition.BENEATH_TEXT;
            default:
                return FootnotePosition.BOTTOM_OF_PAGE;
        }
    }

    static /*EndnotePosition*/int xmlToEndnotePosition(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"sectEnd"*/16:
            case /*"sect-end"*/17:
                return EndnotePosition.END_OF_SECTION;
            case /*"docEnd"*/18:
            case /*"doc-end"*/19:
                return EndnotePosition.END_OF_DOCUMENT;
            default:
                return EndnotePosition.END_OF_DOCUMENT;
        }
    }

    static String footnotePositionToXml(/*FootnotePosition*/int value, boolean isDocx)
    {
        switch (value)
        {
            case FootnotePosition.BOTTOM_OF_PAGE: return isDocx ? "pageBottom" : "page-bottom";
            case FootnotePosition.BENEATH_TEXT: return isDocx ? "beneathText" : "beneath-text";
            default: return "";
        }
    }

    static String endnotePositionToXml(/*EndnotePosition*/int value, boolean isDocx)
    {
        switch (value)
        {
            case EndnotePosition.END_OF_SECTION: return isDocx ? "sectEnd" : "sect-end";
            case EndnotePosition.END_OF_DOCUMENT: return isDocx ? "docEnd" : "doc-end";
            default: return "";
        }
    }

    static /*PageVerticalAlignment*/int xmlToPageVerticalAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"top"*/20: return PageVerticalAlignment.TOP;
            case /*"center"*/21: return PageVerticalAlignment.CENTER;
            case /*"both"*/22: return PageVerticalAlignment.JUSTIFY;
            case /*"bottom"*/23: return PageVerticalAlignment.BOTTOM;
            default: return PageVerticalAlignment.TOP;
        }
    }

    static String pageVerticalAlignmentToXml(/*PageVerticalAlignment*/int value)
    {
        switch (value)
        {
            case PageVerticalAlignment.TOP: return "top";
            case PageVerticalAlignment.CENTER: return "center";
            case PageVerticalAlignment.JUSTIFY: return "both";
            case PageVerticalAlignment.BOTTOM: return "bottom";
            default: return "";
        }
    }

    static /*ChapterPageSeparator*/int xmlToChapterPageSeparator(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"hyphen"*/24:
                return ChapterPageSeparator.HYPHEN;
            case /*"period"*/25:
                return ChapterPageSeparator.PERIOD;
            case /*"colon"*/26:
                return ChapterPageSeparator.COLON;
            case /*"emDash"*/27:
            case /*"em-dash"*/28:
                return ChapterPageSeparator.EM_DASH;
            case /*"enDash"*/29:
            case /*"en-dash"*/30:
                return ChapterPageSeparator.EN_DASH;
            default:
                return ChapterPageSeparator.HYPHEN;
        }
    }

    static String chapterPageSeparatorToXml(/*ChapterPageSeparator*/int value, boolean isDocx)
    {
        switch (value)
        {
            case ChapterPageSeparator.HYPHEN: return "hyphen";
            case ChapterPageSeparator.PERIOD: return "period";
            case ChapterPageSeparator.COLON: return "colon";
            case ChapterPageSeparator.EM_DASH: return isDocx ? "emDash" : "em-dash";
            case ChapterPageSeparator.EN_DASH: return isDocx ? "enDash" : "en-dash";
            default: return "";
        }
    }

    static /*LineNumberRestartMode*/int xmlToLineNumberRestartMode(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"continuous"*/0:
                return LineNumberRestartMode.CONTINUOUS;
            case /*"newPage"*/31:
            case /*"new-page"*/32:
                return LineNumberRestartMode.RESTART_PAGE;
            case /*"newSection"*/33:
            case /*"new-section"*/34:
                return LineNumberRestartMode.RESTART_SECTION;
            default:
                return LineNumberRestartMode.RESTART_PAGE;
        }
    }

    static String lineNumberRestartModeToXml(/*LineNumberRestartMode*/int value, boolean isDocx)
    {
        switch (value)
        {
            case LineNumberRestartMode.CONTINUOUS: return "continuous";
            case LineNumberRestartMode.RESTART_PAGE: return isDocx ? "newPage" : "new-page";
            case LineNumberRestartMode.RESTART_SECTION: return isDocx ? "newSection" : "new-section";
            default: return "";
        }
    }

    static /*PageBorderDistanceFrom*/int xmlToPageBorderDistanceFrom(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"page"*/35: return PageBorderDistanceFrom.PAGE_EDGE;
            case /*"text"*/36: return PageBorderDistanceFrom.TEXT;
            default: return PageBorderDistanceFrom.PAGE_EDGE;
        }
    }

    static String pageBorderDistanceFromToXml(/*PageBorderDistanceFrom*/int value)
    {
        switch (value)
        {
            case PageBorderDistanceFrom.PAGE_EDGE: return "page";
            case PageBorderDistanceFrom.TEXT: return "text";
            default: return "";
        }
    }

    static /*PageBorderAppliesTo*/int xmlToPageBorderAppliesTo(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"allPages"*/37:
            case /*"all-pages"*/38:
                return PageBorderAppliesTo.ALL_PAGES;
            case /*"firstPage"*/39:
            case /*"first-page"*/40:
                return PageBorderAppliesTo.FIRST_PAGE;
            case /*"notFirstPage"*/41:
            case /*"not-first-page"*/42:
                return PageBorderAppliesTo.OTHER_PAGES;
            default:
                return PageBorderAppliesTo.ALL_PAGES;
        }
    }

    static String pageBorderAppliesToToXml(/*PageBorderAppliesTo*/int value, boolean isDocx)
    {
        switch (value)
        {
            case PageBorderAppliesTo.ALL_PAGES: return isDocx ? "allPages" : "all-pages";
            case PageBorderAppliesTo.FIRST_PAGE: return isDocx ? "firstPage" : "first-page";
            case PageBorderAppliesTo.OTHER_PAGES: return isDocx ? "notFirstPage" : "not-first-page";
            default: return "";
        }
    }

    static /*Orientation*/int xmlToOrientation(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"landscape"*/43: return Orientation.LANDSCAPE;
            case /*"portrait"*/44: return Orientation.PORTRAIT;
            default: return Orientation.PORTRAIT;
        }
    }

    static String orientationToXml(/*Orientation*/int value)
    {
        switch (value)
        {
            case Orientation.LANDSCAPE: return "landscape";
            case Orientation.PORTRAIT: return "portrait";
            default: return "";
        }
    }

    static /*SectionStart*/int xmlToSectionStart(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"continuous"*/0:
                return SectionStart.CONTINUOUS;
            case /*"evenPage"*/45:
            case /*"even-page"*/46:
                return SectionStart.EVEN_PAGE;
            case /*"nextColumn"*/47:
            case /*"next-column"*/48:
                return SectionStart.NEW_COLUMN;
            case /*"nextPage"*/49:
            case /*"next-page"*/50:
                return SectionStart.NEW_PAGE;
            case /*"oddPage"*/51:
            case /*"odd-page"*/52:
                return SectionStart.ODD_PAGE;
            default:
                return SectionStart.NEW_PAGE;
        }
    }

    static String sectionStartToXml(/*SectionStart*/int value, boolean isDocx)
    {
        switch (value)
        {
            case SectionStart.CONTINUOUS: return "continuous";
            case SectionStart.EVEN_PAGE: return isDocx ? "evenPage" : "even-page";
            case SectionStart.NEW_COLUMN: return isDocx ? "nextColumn" : "next-column";
            case SectionStart.NEW_PAGE: return isDocx ? "nextPage" : "next-page";
            case SectionStart.ODD_PAGE: return isDocx ? "oddPage" : "odd-page";
            default: return "";
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"continuous",
		"eachSect",
		"each-sect",
		"eachPage",
		"each-page",
		"linesAndChars",
		"lines-and-chars",
		"lines",
		"snapToChars",
		"snap-to-chars",
		"even",
		"first",
		"pageBottom",
		"page-bottom",
		"beneathText",
		"beneath-text",
		"sectEnd",
		"sect-end",
		"docEnd",
		"doc-end",
		"top",
		"center",
		"both",
		"bottom",
		"hyphen",
		"period",
		"colon",
		"emDash",
		"em-dash",
		"enDash",
		"en-dash",
		"newPage",
		"new-page",
		"newSection",
		"new-section",
		"page",
		"text",
		"allPages",
		"all-pages",
		"firstPage",
		"first-page",
		"notFirstPage",
		"not-first-page",
		"landscape",
		"portrait",
		"evenPage",
		"even-page",
		"nextColumn",
		"next-column",
		"nextPage",
		"next-page",
		"oddPage",
		"odd-page"
	);

}

