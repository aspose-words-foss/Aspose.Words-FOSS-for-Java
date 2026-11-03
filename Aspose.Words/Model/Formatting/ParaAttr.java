// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a paragraph.
///
/// Note the constant values make sure the attributes are written
/// into a binary file in a specific order and the order is important.
/// </summary>
/*static*/ class ParaAttr
{
	/* Simulation of static class by using private constructor */
	private ParaAttr()
	{}

    /// <summary>
    /// int, istd of the paragraph style
    /// </summary>
    static final int ISTD = 1000;
    /// <summary>
    /// enum
    /// </summary>
    static final int ALIGNMENT = 1020;
    /// <summary>
    /// bool
    /// </summary>
    static final int NO_SPACE_BETWEEN_SAME_STYLE = 1022;
    /// <summary>
    /// bool, not documented well
    /// </summary>
    static final int SIDE_BY_SIDE = 1030;
    /// <summary>
    /// bool
    /// </summary>
    static final int KEEP_TOGETHER = 1040;
    /// <summary>
    /// bool
    /// </summary>
    static final int KEEP_WITH_NEXT = 1050;
    /// <summary>
    /// bool
    /// </summary>
    static final int PAGE_BREAK_BEFORE = 1060;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int FAR_EAST_LINE_BREAK_CONTROL = 1070;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int WORD_WRAP = 1080;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int HANGING_PUNCTUATION = 1090;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int TOP_LINE_PUNCTUATION = 1100;
    /// <summary>
    /// int
    /// </summary>
    static final int LIST_LEVEL = 1110;
    /// <summary>
    /// int
    /// </summary>
    static final int LIST_ID = 1120;

    /// <summary>
    /// bool
    /// </summary>
    static final int SUPPRESS_LINE_NUMBERS = 1130;
    /// <summary>
    /// TabStopCollection, complex, at the moment cannot be inherited.
    /// </summary>
    static final int TAB_STOPS = 1140;
    /// <summary>
    /// bool
    /// </summary>
    static final int MIRROR_INDENTS = 1145;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int RIGHT_INDENT = 1150;
    /// <summary>
    /// int, 1/100 of character unit.
    /// </summary>
    static final int RIGHT_INDENT_UNITS = 1155;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int LEFT_INDENT = 1160;
    /// <summary>
    /// int, 1/100 of character unit.
    /// </summary>
    static final int LEFT_INDENT_UNITS = 1165;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int FIRST_LINE_INDENT = 1170;
    /// <summary>
    /// int, 1/100 of character unit.
    /// </summary>
    static final int FIRST_LINE_INDENT_UNITS = 1175;
    /// <summary>
    /// line spacing, complex.
    /// </summary>
    static final int LINE_SPACING = 1650;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int SPACE_BEFORE = 1200;
    /// <summary>
    /// int, 1/100 of line unit.
    /// </summary>
    static final int SPACE_BEFORE_UNITS = 1205;
    /// <summary>
    /// bool
    /// </summary>
    static final int SPACE_BEFORE_AUTO = 1210;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int SPACE_AFTER = 1220;
    /// <summary>
    /// int, 1/100 of line unit.
    /// </summary>
    static final int SPACE_AFTER_UNITS = 1225;
    /// <summary>
    /// bool
    /// </summary>
    static final int SPACE_AFTER_AUTO = 1230;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int ADD_SPACE_BETWEEN_FAR_EAST_AND_ALPHA = 1240;
    /// <summary>
    /// bool, asian
    /// </summary>
    static final int ADD_SPACE_BETWEEN_FAR_EAST_AND_DIGIT = 1250;
    /// <summary>
    /// bool
    /// </summary>
    static final int SNAP_TO_GRID = 1260;
    /// <summary>
    /// bool
    /// </summary>
    static final int AUTO_ADJUST_RIGHT_INDENT = 1270;
    /// <summary>
    /// OutlineLevel
    /// </summary>
    static final int OUTLINE_LEVEL = 1280;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_HORIZONTAL_ALIGNMENT = 1290;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_LEFT = 1292;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_VERTICAL_ALIGNMENT = 1300;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_TOP = 1302;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_WIDTH = 1310;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_RELATIVE_HORIZONTAL_POSITION = 1320;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_RELATIVE_VERTICAL_POSITION = 1330;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_WRAP_TYPE = 1340;
    /// <summary>
    /// bool. Text frame.
    /// </summary>
    static final int FRAME_SUPPRESS_OVERLAP = 1660;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_TOP = 1350;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_LEFT = 1360;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_BOTTOM = 1370;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_RIGHT = 1380;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_BETWEEN = 1390;
    /// <summary>
    /// Border, complex, can be inherited
    /// </summary>
    static final int BORDER_BAR = 1400;
    /// <summary>
    /// bool
    /// </summary>
    static final int SUPPRESS_AUTO_HYPHENS = 1410;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_HEIGHT = 1430;
    /// <summary>
    /// enum
    /// </summary>
    static final int DROP_CAP_POSITION = 1440;
    /// <summary>
    /// int
    /// </summary>
    static final int DROP_CAP_LINES_TO_DROP = 1450;
    /// <summary>
    /// Shading, complex, can be inherited
    /// </summary>
    static final int SHADING = 1460;
    /// <summary>
    /// bool
    /// </summary>
    static final int WIDOW_CONTROL = 1470;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int FRAME_TEXT_ORIENTATION = 1480;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_VERTICAL_DISTANCE_FROM_TEXT = 1490;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_HORIZONTAL_DISTANCE_FROM_TEXT = 1500;
    /// <summary>
    /// enum
    /// </summary>
    static final int BASELINE_ALIGNMENT = 1510;
    /// <summary>
    /// bool. Text frame.
    /// </summary>
    static final int FRAME_LOCK_ANCHOR = 1520;
    /// <summary>
    /// bool
    /// </summary>
    static final int BIDI = 1560;
    /// <summary>
    /// int.
    /// Specifies a unique identifier used to track the editing session when the
    /// paragraph's properties were last modified in this document.
    /// </summary>
    static final int RSID_P = 1580;

    /// <summary>
    /// HTML related information <see cref="HtmlBlock" /> is applied to this paragraph.
    /// </summary>
    static final int HTML_BLOCK_ID = 1585;

    /// <summary>
    /// Specifies text wrapping options <see cref="TextboxTightWrap"/> for a text box when tight wrapping is set.
    /// </summary>
    static final int TEXTBOX_TIGHT_WRAP = 1590;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> left margin.
    /// </summary>
    static final int HTML_MARGIN_LEFT = 1592;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> right margin.
    /// </summary>
    static final int HTML_MARGIN_RIGHT = 1593;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> top margin.
    /// </summary>
    static final int HTML_MARGIN_TOP = 1594;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> bottom margin.
    /// </summary>
    static final int HTML_MARGIN_BOTTOM = 1595;

    /// <summary>
    /// Not in the model.
    /// Used to temporarily store a non bidi aware Word 97 attribute that is resolved into a model attribute.
    /// </summary>
    static final int SYS_ALIGNMENT_97 = 1610;
    /// <summary>
    /// Not in the model.
    /// Used to temporarily store a non bidi aware Word 97 attribute that is resolved into a model attribute.
    /// </summary>
    static final int SYS_LEFT_INDENT_97 = 1620;
    /// <summary>
    /// Not in the model.
    /// Used to temporarily store a non bidi aware Word 97 attribute that is resolved into a model attribute.
    /// </summary>
    static final int SYS_RIGHT_INDENT_97 = 1630;

    static final int SYS_FIRST_LINE_INDENT_97 = 1640;

    /// <summary>
    /// Word60 list definition. Used only during import of Word95 files.
    /// </summary>
    static final int SYS_ANLD = 1641;

    /// <summary>
    /// Word60 list definition. Used only during import of Word95 files.
    /// </summary>
    static final int SYS_LVL_ANM = 1642;

    /// <summary>
    /// Not in the model.
    /// Used to temporarily mark a list item paragraph for restarting numbering that will be performed later
    /// for entire document.
    /// </summary>
    static final int SYS_RESTART_NUMBERING = 1643;

    /// <summary>
    /// Collapsed state of a paragraph. MS Word 2013 and higher.
    /// </summary>
    static final int COLLAPSED = 1670;
}

