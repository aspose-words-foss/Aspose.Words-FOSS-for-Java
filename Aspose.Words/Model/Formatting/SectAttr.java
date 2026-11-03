// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a section.
///
/// Note the constant values make sure the attributes are written
/// into a binary file in a specific order and the order is important.
/// </summary>
class SectAttr
{
    /// <summary>
    /// enum
    /// </summary>
    static final int PAGE_NUMBER_STYLE = 2010;
    /// <summary>
    /// enum
    /// </summary>
    static final int CHAPTER_PAGE_SEPARATOR = 2020;
    /// <summary>
    /// SectionStart as enum
    /// </summary>
    static final int SECTION_START = 2030;
    /// <summary>
    /// bool
    /// </summary>
    static final int DIFFERENT_FIRST_PAGE_HEADER_FOOTER = 2040;
    /// <summary>
    /// bool
    /// </summary>
    static final int RESTART_PAGE_NUMBERING = 2050;
    /// <summary>
    /// bool
    /// </summary>
    static final int COLUMNS_LINE_BETWEEN = 2060;
    /// <summary>
    /// int
    /// </summary>
    static final int FIRST_PAGE_TRAY = 2070;
    /// <summary>
    /// int
    /// </summary>
    static final int OTHER_PAGES_TRAY = 2080;
    /// <summary>
    /// int, undocumented
    /// </summary>
    static final int PAPER_CODE = 2090;
    /// <summary>
    /// bool
    /// </summary>
    static final int SUPPRESS_ENDNOTES = 2100;
    /// <summary>
    /// enum
    /// </summary>
    static final int LINE_NUMBER_RESTART_MODE = 2110;
    /// <summary>
    /// int
    /// </summary>
    static final int LINE_NUMBER_COUNT_BY = 2120;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_TOP = 2130;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_LEFT = 2140;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_BOTTOM = 2150;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_RIGHT = 2160;
    /// <summary>
    /// int
    /// </summary>
    static final int LINE_PITCH = 2170;
    /// <summary>
    /// int
    /// </summary>
    static final int LINE_STARTING_NUMBER = 2180;
    /// <summary>
    /// int
    /// </summary>
    static final int HEADING_LEVEL_FOR_CHAPTER = 2190;
    /// <summary>
    /// int
    /// </summary>
    static final int PAGE_STARTING_NUMBER = 2200;
    /// <summary>
    /// enum
    /// </summary>
    static final int ORIENTATION = 2210;
    /// <summary>
    /// enum
    /// </summary>
    static final int BORDER_APPLIES_TO = 2220;
    /// <summary>
    /// bool
    /// </summary>
    static final int BORDER_ALWAYS_IN_FRONT = 2230;
    /// <summary>
    /// enum
    /// </summary>
    static final int BORDER_DISTANCE_FROM = 2240;
    /// <summary>
    /// Specifies a revision save ID for the section.
    /// </summary>
    /// <remarks>
    /// int, no default defined for this one.
    /// </remarks>
    static final int RSID = 2250;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int PAGE_WIDTH = 2260;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int PAGE_HEIGHT = 2270;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int LEFT_MARGIN = 2280;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int RIGHT_MARGIN = 2290;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int TOP_MARGIN = 2300;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int BOTTOM_MARGIN = 2310;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int GUTTER = 2312;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int HEADER_DISTANCE = 2320;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int FOOTER_DISTANCE = 2330;
    /// <summary>
    /// enum
    /// </summary>
    static final int VERTICAL_ALIGNMENT = 2340;
    /// <summary>
    /// int
    /// </summary>
    static final int COLUMNS_COUNT = 2350;
    /// <summary>
    /// bool
    /// </summary>
    static final int COLUMNS_EVENLY_SPACED = 2360;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int COLUMNS_SPACING = 2370;
    /// <summary>
    /// TextColumnCollection, complex attribute, cannot be inherited. No default for this one.
    /// </summary>
    static final int COLUMNS = 2380;
    /// <summary>
    /// bool
    /// </summary>
    static final int UNLOCKED = 2390;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int LINE_NUMBER_DISTANCE_FROM_TEXT = 2400;
    /// <summary>
    /// bool
    /// </summary>
    static final int RTL_GUTTER = 2410;
    /// <summary>
    /// int
    /// </summary>
    static final int CHAR_SPACE = 2420;
    /// <summary>
    /// VA: I have implemented DocGridType enum.
    /// Test document is Section\TestDocGridType.doc.
    /// </summary>
    static final int GRID_TYPE = 2430;
    /// <summary>
    /// enum
    /// </summary>
    static final int TEXT_FLOW = 2440;
    /// <summary>
    /// bool
    /// </summary>
    static final int BIDI = 2450;

    /// <summary>
    /// Don't renumber these.
    /// enums <see cref="FootnotePosition"/>, <see cref="Words.Notes.FootnoteLocation"/>
    /// </summary>
    static final int FOOTNOTE_LOCATION = 2500;
    /// <summary>
    /// enum <see cref="Words.Notes.FootnoteNumberingRule"/>
    /// </summary>
    static final int FOOTNOTE_NUMBERING_RULE = 2510;
    /// <summary>
    /// int
    /// </summary>
    static final int FOOTNOTE_START_NUMBER = 2520;
    /// <summary>
    /// enum <see cref="Aspose.Words.NumberStyle"/>
    /// </summary>
    static final int FOOTNOTE_NUMBER_STYLE = 2530;
    /// <summary>
    /// Number of footnote columns.
    /// </summary>
    static final int FOOTNOTE_COLUMNS = 2540;

    /// <summary>
    /// Don't renumber these.
    /// This is only a document-wide setting in DocPr. If encountered in SectPr it should be ignored.
    /// enums <see cref="EndnotePosition"/>, <see cref="Words.Notes.FootnoteLocation"/>
    /// </summary>
    static final int ENDNOTE_LOCATION = 2600;
    /// <summary>
    /// enum <see cref="Aspose.Words.Notes.FootnoteNumberingRule"/>
    /// </summary>
    static final int ENDNOTE_NUMBERING_RULE = 2610;
    /// <summary>
    /// int
    /// </summary>
    static final int ENDNOTE_START_NUMBER = 2620;
    /// <summary>
    /// enum <see cref="Aspose.Words.NumberStyle"/>
    /// </summary>
    static final int ENDNOTE_NUMBER_STYLE = 2630;

    /// <summary>
    /// Keys for endnote attributes are same as footnotes, just need to add this value.
    /// </summary>
    static final int ENDNOTE_KEY_DELTA = SectAttr.ENDNOTE_LOCATION - SectAttr.FOOTNOTE_LOCATION;

    /// <summary>
    /// Header/Footer presence flags. Used only during Word 6.0 document import.
    /// </summary>
    static final int SYS_GPRF_IHDT = 2650;
}

