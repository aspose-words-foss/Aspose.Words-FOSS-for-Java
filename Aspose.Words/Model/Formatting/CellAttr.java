// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a cell.
///
/// Note the constant values make sure the attributes are written
/// into a binary file in a specific order and the order is important.
/// </summary>
/*static*/ class CellAttr
{
	/* Simulation of static class by using private constructor */
	private CellAttr()
	{}

    /// <summary>
    /// int, twips, no default
    /// </summary>
    static final int WIDTH = 3010;
    /// <summary>
    /// <see cref="Aspose.Words.Tables.PreferredWidth"/>
    /// </summary>
    static final int PREFERRED_WIDTH = 3020;
    /// <summary>
    /// enum
    /// </summary>
    static final int VERTICAL_MERGE = 3030;
    /// <summary>
    /// enum
    /// </summary>
    static final int HORIZONTAL_MERGE = 3040;
    /// <summary>
    /// enum, <see cref="TextOrientation"/>
    /// </summary>
    static final int ORIENTATION = 3050;
    /// <summary>
    /// enum
    /// </summary>
    static final int VERTICAL_ALIGNMENT = 3060;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int TOP_PADDING = 3070;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int BOTTOM_PADDING = 3080;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int LEFT_PADDING = 3090;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int RIGHT_PADDING = 3100;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_TOP = 3110;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_LEFT = 3120;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_BOTTOM = 3130;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_RIGHT = 3140;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_DIAGONAL_DOWN = 3150;
    /// <summary>
    /// Border
    /// </summary>
    static final int BORDER_DIAGONAL_UP = 3160;
    /// <summary>
    /// Shading
    /// </summary>
    static final int SHADING = 3170;
    /// <summary>
    /// bool
    /// </summary>
    static final int WRAP_TEXT = 3180;
    /// <summary>
    /// bool
    /// </summary>
    static final int FIT_TEXT = 3190;

    /// <summary>
    /// Border. Only used in conditional formatting in table styles.
    /// </summary>
    static final int BORDER_HORIZONTAL = 3200;

    /// <summary>
    /// Border. Only used in conditional formatting in table styles.
    /// </summary>
    static final int BORDER_VERTICAL = 3210;

    /// <summary>
    /// Bool. Specifies that table cell content is rendered with no height if all cells in the row are empty; 
    /// however, cells have a visible height if they have nonzero cell borders, cell margins, or cell spacing.
    /// </summary>
    static final int HIDE_MARK = 3220;

    /// <summary>
    /// Specifies grid span for cell. 
    /// </summary>
    /// <remarks>
    /// The attribute is originally set by DOCX/WML import.
    /// Table grid calculation, <see cref="Table.AutoFit"/> or DOCX/WML export may modify the attribute.
    /// Modifying the attributes directly bypassing the above scenarios may change the table structure and should be avoided.
    /// </remarks>
    static final int SYS_CELL_SPAN = 3900;
}

