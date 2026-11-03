// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a table row.
///
/// Note the constant values make sure the attributes are written
/// into a binary file in a specific order and the order is important.
/// </summary>
/*static*/ class TableAttr
{
	/* Simulation of static class by using private constructor */
	private TableAttr()
	{}

    /// <summary>
    /// int. Table style index.
    /// </summary>
    static final int ISTD = 4005;
    /// <summary>
    /// enum. TableAlignment by itself does not make table floating.
    /// </summary>
    static final int ALIGNMENT = 4010;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int LEFT_PADDING = 4020;
    /// <summary>
    /// bool
    /// </summary>
    static final int HEADING_FORMAT = 4040;
    static final int BORDER_TOP = 4050;
    static final int BORDER_LEFT = 4060;
    static final int BORDER_BOTTOM = 4070;
    static final int BORDER_RIGHT = 4080;
    static final int BORDER_HORIZONTAL = 4090;
    static final int BORDER_VERTICAL = 4100;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int ROW_HEIGHT = 4120;
    /// <summary>
    /// enum. <see cref="TableStyleOptions"/>
    /// </summary>
    static final int STYLE_OPTIONS = 4140;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int RELATIVE_HORIZONTAL_POSITION = 4150;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int RELATIVE_VERTICAL_POSITION = 4160;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_LEFT = 4170;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int HORIZONTAL_ALIGNMENT = 4180;
    /// <summary>
    /// int, twips. Text frame.
    /// </summary>
    static final int FRAME_TOP = 4190;
    /// <summary>
    /// enum. Text frame.
    /// </summary>
    static final int VERTICAL_ALIGNMENT = 4200;
    /// <summary>
    /// int. Text frame.
    /// </summary>
    static final int FRAME_DISTANCE_FROM_LEFT = 4210;
    /// <summary>
    /// int. Text frame.
    /// </summary>
    static final int FRAME_DISTANCE_FROM_TOP = 4220;
    /// <summary>
    /// TableWidth
    /// </summary>
    static final int PREFERRED_WIDTH = 4230;
    /// <summary>
    /// bool
    /// </summary>
    static final int ALLOW_AUTO_FIT = 4240;

    /// <summary>
    /// Preferred width before the first cell of a table row.
    /// </summary>
    /// <remarks>
    /// The attribute is used in jagged tables to specify the width of an empty space before the first cell in a row.
    /// This can be interpreted as a preferred width of the missing cells on the left of a row.
    /// As the cells are missing, this is where the preferred width is stored.
    /// The width is preferred in a sense that it may not match the actual width of the missing columns (though it often does).
    /// The value specified in the document must be used when re-calculating table layout.
    /// The value specified in the document can be replaced as a result of table layout re-calculation.
    /// It normally happens with missing values though, or zero values might be removed.
    /// </remarks>
    static final int WIDTH_BEFORE = 4250;

    /// <summary>
    /// This attribute is used to access the original "width before" value stored in the document.
    /// </summary>
    /// <remarks>
    /// NrxTableReader uses some naive technique to set the <see cref="WidthBefore"/> value from tblGrid column widths
    /// specified in the document. As tblGrid values might be incorrect, the assigned value may differ from
    /// the value stored in the document. This attribute preserves the original stored value.
    /// </remarks>
    static final int WIDTH_BEFORE_ORIGINAL = 4251;

    /// <summary>
    /// Preferred width after the last cell of a table row.
    /// </summary>
    /// <remarks>
    /// The attribute is used in jagged tables to specify the width of an empty space after the last cell in a row.
    /// This can be interpreted as a preferred width of the missing cells on the right of a row.
    /// As the cells are missing, this is where the preferred width is stored.
    /// The width is preferred in a sense that it may not match the actual width of the missing columns (though it often does).
    /// The value specified in the document must be used when re-calculating table layout.
    /// The value specified in the document can be replaced as a result of table layout re-calculation.
    /// It normally happens with missing values though, or zero values might be removed.
    /// </remarks>
    static final int WIDTH_AFTER = 4260;

    /// <summary>
    /// This attribute is used to access the original "width after" value stored in the document.
    /// </summary>
    /// <remarks>
    /// The attribute is used to preserve the original value from the document
    /// in scenarios when the new table layout logic is not applied.
    /// </remarks>
    static final int WIDTH_AFTER_ORIGINAL = 4261;

    /// <summary>
    /// int. Text frame.
    /// </summary>
    static final int FRAME_DISTANCE_FROM_RIGHT = 4270;
    /// <summary>
    /// int. Text frame.
    /// </summary>
    static final int FRAME_DISTANCE_FROM_BOTTOM = 4280;

    /// <summary>
    /// Specifies table cell spacing (the spacing between adjacent cells and the edges of the table),
    /// <see cref="PreferredWidth"/>.
    /// </summary>
    /// <remarks>
    /// In MS Word when zero cell spacing is specified, the table appears differently
    /// from the case where cell spacing is not specified at all. Looks like MS Word glitch.
    /// <see cref="Table.AllowCellSpacing"/> controls the presence or missing of the CellSpacing attribute.
    /// </remarks>
    static final int CELL_SPACING = 4290;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int TOP_PADDING = 4300;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int BOTTOM_PADDING = 4310;
    /// <summary>
    /// int, twips
    /// </summary>
    static final int RIGHT_PADDING = 4320;
    /// <summary>
    /// Shading
    /// </summary>
    static final int SHADING = 4330;
    /// <summary>
    /// int, twips.
    /// Left indent of the table.
    /// </summary>
    static final int LEFT_INDENT = 4340;
    /// <summary>
    /// bool, Text frame.
    /// </summary>
    static final int ALLOW_OVERLAP = 4350;
    /// <summary>
    /// bool.
    /// </summary>
    static final int ALLOW_BREAK_ACROSS_PAGES = 4360;
    /// <summary>
    /// bool
    /// </summary>
    static final int BIDI = 4380;
    /// <summary>
    /// int
    /// </summary>
    static final int RSID_TR = 4400;

    /// <summary>
    /// int. Stored in table attributes of a table style.
    /// Default 0. When 0, there is no banding. Valid range in MS Word 0..3, we should enforce too.
    /// </summary>
    static final int STYLE_ROW_BAND_SIZE = 4500;
    /// <summary>
    /// int. Stored in table attributes of a table style.
    /// Default 0. When 0, there is no banding. Valid range in MS Word 0..3, we should enforce too.
    /// </summary>
    static final int STYLE_COL_BAND_SIZE = 4510;

    static final int HIDDEN = 4520;

    /// <summary>
    /// Docx Iso29500 only attribute. It is not applicable to WML, RTF and DOC, so the number is not for writing,
    /// but a unique ID.
    /// </summary>
    static final int TITLE = 5000;

    /// <summary>
    /// Docx Iso29500 only attribute. It is not applicable to WML, RTF and DOC, so the number is not for writing,
    /// but a unique ID.
    /// </summary>
    static final int DESCRIPTION = 5010;

    // ReSharper disable InconsistentNaming

    /// <summary>
    /// HTML related information <see cref="HtmlBlock" /> is applied to this row.
    /// </summary>
    static final int HTML_BLOCK_ID = 5015;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> left margin.
    /// </summary>
    static final int HTML_MARGIN_LEFT = 5016;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> right margin.
    /// </summary>
    static final int HTML_MARGIN_RIGHT = 5017;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> top margin.
    /// </summary>
    static final int HTML_MARGIN_TOP = 5018;

    /// <summary>
    /// HTML block <see cref="HtmlBlock" /> bottom margin.
    /// </summary>
    static final int HTML_MARGIN_BOTTOM = 5019;

    /// <summary>
    /// CellPrCollection, note this is stored only temporarily during read and write.
    /// </summary>
    static final int SYS_CELLS = 5100;

    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used to temporarily store a non bidi aware Word 97 attribute that is resolved into a model attribute.
    /// </summary>
    static final int SYS_ALIGNMENT_97 = 5101;

    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used to temporarily store first cell position in row when old table definition is read.
    /// Removed after table converted to new style.
    /// </summary>
    static final int SYS_LEFT_INDENT_97 = 5102;

    /// <summary>
    /// Do not use anywhere except DOCX/WML import/export.
    /// Specifies table grid for table.
    /// </summary>
    static final int SYS_TABLE_GRID = 5103;

    /// <summary>
    /// Do not use anywhere except DOCX/WML import/export.
    /// Specifies grid before for row.
    /// </summary>
    static final int SYS_GRID_BEFORE = 5104;

    /// <summary>
    /// Do not use anywhere except DOCX/WML import/export.
    /// Specifies grid after for row.
    /// </summary>
    static final int SYS_GRID_AFTER = 5105;

    /// <summary>
    /// Stores the table grid calculated by the new algorithm.
    /// </summary>
    /// <remarks>
    /// The new algorithm currently does not support all possible cases.
    /// State property is set to Applied if the grid was calculated successfully.
    /// </remarks>
    static final int SYS_CALCULATED_TABLE_GRID = 5106;

    /// <summary>
    /// Stores the table grid originally read from document.
    /// </summary>
    /// <remarks>
    /// It was introduced by the new table grid calculation algorithm to co-exist with the old algorithm.
    /// Currently, a grid missing in the document may be created, or an existing grid may be updated by the old algorithm.
    /// This logic may break the new algorithm which needs the values as they are stored in the  document,
    /// before any changes are applied or missing values are added.
    /// So this attribute just stores tblGrid read from document before it is changed in any way.
    /// Eventually, when all cases are handled by the "new" algorithm, there will be no need to have two attributes.
    /// </remarks>
    static final int SYS_TABLE_GRID_FOR_NEW_ALGORITHM = 5107;

    /// <summary>
    /// Combined with Sys_DxaGapHalf, specifies the location of the horizontal origin of the table relative to the logical left margin.
    /// </summary>
    static final int SYS_DXA_LEFT = 5108;

    /// <summary>
    /// Average width, in twips, between the left and right default cell margins for the first cell in the row.
    /// </summary>
    static final int SYS_DXA_GAP_HALF = 5109;

    /// <summary>
    /// The attribute is used to remember the leftmost row indent for doc and rtf,
    /// as it may be lost when readers reformat the table.
    /// </summary>
    static final int SYS_LEFT_INDENT_97_BASE = 5110;

    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used to temporarily store a Word 97 bidi table attribute that is resolved into a model attribute.
    /// </summary>
    static final int SYS_BIDI_TABLE_97 = 5111;

    /// <summary>
    /// Indicates if the logic that uses older Word 97 attributes was used to compute 'width before' value for doc/rtf.
    /// </summary>
    static final int SYS_WORD_97_LOGIC = 5112;
    // ReSharper restore InconsistentNaming
}

