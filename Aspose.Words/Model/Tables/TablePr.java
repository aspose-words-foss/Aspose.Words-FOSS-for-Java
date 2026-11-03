// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import com.aspose.Debug;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Collection of table row attributes that allows typed access to the values.
/// </summary>
class TablePr extends WordAttrCollection implements IRowAttrSource
{
    /// <summary>
    /// Creates a table row attributes that have the values set to the values that MS Word uses when creating tables.
    /// </summary>
    static TablePr createMSWordLooking()
    {
        TablePr result = new TablePr();

        final int DEFAULT_LEFT_AND_RIGHT_CELL_PADDING = 108;
        result.setLeftPadding(DEFAULT_LEFT_AND_RIGHT_CELL_PADDING);
        result.setRightPadding(DEFAULT_LEFT_AND_RIGHT_CELL_PADDING);

        result.setAllowAutoFit(true);

        // Although MS Word does not actually set table preferred width to 100% on table creation, it sort of behaves that way.
        // We cannot directly emulate this behavior and hence the next best option is to set preferred width to 100% so
        // any table created by the user in AW will occupy the available width.
        result.setPreferredWidth(PreferredWidth.fromPercent(100.0));

        // This is the default thin black border that MS Word creates.
        for (int key : POSSIBLE_BORDER_KEYS.getValues())
            result.setAttr(key, new Border(LineStyle.SINGLE, 4, DrColor.EMPTY));

        return result;
    }

    void removeBorders()
    {
        for (int key : POSSIBLE_BORDER_KEYS.getValues())
            remove(key);
    }

    void removeShading()
    {
        remove(TableAttr.SHADING);
    }

    /// <summary>
    /// Style index.
    /// </summary>
    int getIstd() { return (/*int*/Integer)fetchAttr(TableAttr.ISTD); }
    void setIstd(int value) { setAttr(TableAttr.ISTD, value); }

    int getStyleRowBandSize() { return (/*int*/Integer)fetchAttr(TableAttr.STYLE_ROW_BAND_SIZE); }

    int getStyleColBandSize() { return (/*int*/Integer)fetchAttr(TableAttr.STYLE_COL_BAND_SIZE); }

    /// <summary>
    /// Reverses a row alignment value.
    /// </summary>
    static /*TableAlignment*/int reverseAlignment(/*TableAlignment*/int alignment)
    {
        switch (alignment)
        {
            case TableAlignment.LEFT:
                return TableAlignment.RIGHT;
            case TableAlignment.RIGHT:
                return TableAlignment.LEFT;
            default:
                return alignment;
        }
    }

    /// <summary>
    /// Inverts RowBands and ColumnBands.
    /// </summary>
    static /*TableStyleOptions*/int invertTableBands(/*TableStyleOptions*/int options)
    {
        return options ^ (TableStyleOptions.ROW_BANDS | TableStyleOptions.COLUMN_BANDS);
    }

    /// <summary>
    /// Returns true if the table properties have same absolute positioning as the specified properties.
    /// There are 11 floating table frame positioning attributes. This efficiently compares all of them if needed.
    /// </summary>
    boolean isSameFloatingPositioning(TablePr rhs)
    {
        boolean isApo1 = isFloating();
        boolean isApo2 = rhs.isFloating();

        // A quick way to get out if both are not absolutely positioned.
        if (!isApo1 && !isApo2)
            return true;

        // A quick way to get out if one is absolutely positioned and the other is not.
        if (isApo1 != isApo2)
            return false;

        // Now we have to compare all frame attributes thoroughly.
        if (getAllowOverlap() != rhs.getAllowOverlap())
            return false;

        if (getRelativeHorizontalPosition() != rhs.getRelativeHorizontalPosition())
            return false;

        if (getRelativeVerticalPosition() != rhs.getRelativeVerticalPosition())
            return false;

        if (getFrameLeft() != rhs.getFrameLeft())
            return false;

        if (getFrameTop() != rhs.getFrameTop())
            return false;

        if (getHorizontalAlignment() != rhs.getHorizontalAlignment())
            return false;

        if (getVerticalAlignment() != rhs.getVerticalAlignment())
            return false;

        return true;
    }

    boolean hasBorders()
    {
        return
            containsKey(TableAttr.BORDER_TOP) ||
            containsKey(TableAttr.BORDER_LEFT) ||
            containsKey(TableAttr.BORDER_BOTTOM) ||
            containsKey(TableAttr.BORDER_RIGHT) ||
            containsKey(TableAttr.BORDER_HORIZONTAL) ||
            containsKey(TableAttr.BORDER_VERTICAL);
    }

    Border getBorderTop() { return (Border)getDirectAttr(TableAttr.BORDER_TOP); }

    Border getBorderLeft() { return (Border)getDirectAttr(TableAttr.BORDER_LEFT); }

    Border getBorderBottom() { return (Border)getDirectAttr(TableAttr.BORDER_BOTTOM); }

    Border getBorderRight() { return (Border)getDirectAttr(TableAttr.BORDER_RIGHT); }

    Border getBorderHorizontal() { return (Border)getDirectAttr(TableAttr.BORDER_HORIZONTAL); }

    Border getBorderVertical() { return (Border)getDirectAttr(TableAttr.BORDER_VERTICAL); }

    Shading getShading() { return (Shading)getDirectAttr(TableAttr.SHADING); }
    void setShading(Shading value) { setAttr(TableAttr.SHADING, value); }

    /*TableStyleOptions*/int getStyleOptions() { return (/*TableStyleOptions*/Integer)fetchAttr(TableAttr.STYLE_OPTIONS); }

    PreferredWidth getPreferredWidth() { return (PreferredWidth)fetchAttr(TableAttr.PREFERRED_WIDTH); }
    void setPreferredWidth(PreferredWidth value) { setAttr(TableAttr.PREFERRED_WIDTH, value); }

    boolean getAllowAutoFit() { return (/*boolean*/Boolean)fetchAttr(TableAttr.ALLOW_AUTO_FIT); }
    void setAllowAutoFit(boolean value) { setAttr(TableAttr.ALLOW_AUTO_FIT, value); }

    /*TableAlignment*/int getAlignment() { return (/*TableAlignment*/Integer)fetchAttr(TableAttr.ALIGNMENT); }
    void setAlignment(/*TableAlignment*/int value) { setAttr(TableAttr.ALIGNMENT, value); }

    boolean getBidi() { return (/*boolean*/Boolean)fetchAttr(TableAttr.BIDI); }
    void setBidi(boolean value) { setAttr(TableAttr.BIDI, value); }

    int getTopPadding() { return (/*int*/Integer)fetchAttr(TableAttr.TOP_PADDING); }
    void setTopPadding(int value) { setAttr(TableAttr.TOP_PADDING, value); }

    int getBottomPadding() { return (/*int*/Integer)fetchAttr(TableAttr.BOTTOM_PADDING); }
    void setBottomPadding(int value) { setAttr(TableAttr.BOTTOM_PADDING, value); }

    int getLeftPadding() { return (/*int*/Integer)fetchAttr(TableAttr.LEFT_PADDING); }
    void setLeftPadding(int value) { setAttr(TableAttr.LEFT_PADDING, value); }

    int getRightPadding() { return (/*int*/Integer)fetchAttr(TableAttr.RIGHT_PADDING); }
    void setRightPadding(int value) { setAttr(TableAttr.RIGHT_PADDING, value); }

    /// <summary>
    /// Note this spacing must be added to both adjoining cells.
    /// </summary>
    PreferredWidth getCellSpacing() { return (PreferredWidth)fetchAttr(TableAttr.CELL_SPACING); }

    /*HeightRule*/int getHeightRule() { return ((Height)fetchAttr(TableAttr.ROW_HEIGHT)).getRule(); }
    void setHeightRule(/*HeightRule*/int value) { ((Height)getOrCreateComplexAttr(TableAttr.ROW_HEIGHT)).setRule(value); }

    int getHeight() { return ((Height)fetchAttr(TableAttr.ROW_HEIGHT)).getValue(); }
    void setHeight(int value) { ((Height)getOrCreateComplexAttr(TableAttr.ROW_HEIGHT)).setValue(value); }

    boolean getAllowBreakAcrossPages() { return (/*boolean*/Boolean)fetchAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES); }

    boolean getHeadingFormat() { return (/*boolean*/Boolean)fetchAttr(TableAttr.HEADING_FORMAT); }

    boolean getAllowOverlap() { return (/*boolean*/Boolean)fetchAttr(TableAttr.ALLOW_OVERLAP); }

    int getFrameDistanceFromTop() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_DISTANCE_FROM_TOP); }

    int getFrameDistanceFromBottom() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_DISTANCE_FROM_BOTTOM); }

    int getFrameDistanceFromRight() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_DISTANCE_FROM_RIGHT); }

    int getFrameDistanceFromLeft() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_DISTANCE_FROM_LEFT); }

    boolean hasNondefaultRelativeHorizontalPosition() { return (containsKey(TableAttr.RELATIVE_HORIZONTAL_POSITION) && (getRelativeHorizontalPosition() != RelativeHorizontalPosition.DEFAULT)); }

    /// <summary>
    /// Table Horizontal Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes</remarks>
    /*RelativeHorizontalPosition*/int getRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)fetchAttr(TableAttr.RELATIVE_HORIZONTAL_POSITION); }
    void setRelativeHorizontalPosition(/*RelativeHorizontalPosition*/int value) { setAttr(TableAttr.RELATIVE_HORIZONTAL_POSITION, value); }

    boolean hasNondefaultRelativeVerticalPosition() { return (containsKey(TableAttr.RELATIVE_VERTICAL_POSITION) && (getRelativeVerticalPosition() != RelativeVerticalPosition.TABLE_DEFAULT)); }

    /// <summary>
    /// Table Vertical Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes</remarks>
    /*RelativeVerticalPosition*/int getRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)fetchAttr(TableAttr.RELATIVE_VERTICAL_POSITION); }
    void setRelativeVerticalPosition(/*RelativeVerticalPosition*/int value) { setAttr(TableAttr.RELATIVE_VERTICAL_POSITION, value); }

    boolean hasFrameTop() { return containsKey(TableAttr.FRAME_TOP); }

    /// <summary>
    /// Absolute Vertical Distance From Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes</remarks>
    int getFrameTop() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_TOP); }
    void setFrameTop(int value) { setAttr(TableAttr.FRAME_TOP, value); }


    boolean hasNondefaultVerticalAlignment() { return (containsKey(TableAttr.VERTICAL_ALIGNMENT) && (getVerticalAlignment() != VerticalAlignment.DEFAULT)); }

    /// <summary>
    /// Relative Vertical Alignment from Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes</remarks>
    /*VerticalAlignment*/int getVerticalAlignment() { return (/*VerticalAlignment*/Integer)fetchAttr(TableAttr.VERTICAL_ALIGNMENT); }

    boolean hasFrameLeft() { return containsKey(TableAttr.FRAME_LEFT); }

    /// <summary>
    /// Absolute Horizontal Distance From Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes</remarks>
    int getFrameLeft() { return (/*int*/Integer)fetchAttr(TableAttr.FRAME_LEFT); }
    void setFrameLeft(int value) { setAttr(TableAttr.FRAME_LEFT, value); }

    boolean hasNondefaultHorizontalAlignment() { return (containsKey(TableAttr.HORIZONTAL_ALIGNMENT) && (getHorizontalAlignment() != HorizontalAlignment.DEFAULT)); }

    /// <summary>
    /// Relative Horizontal Alignment From Anchor
    /// (floating table property).
    /// </summary>
    /// <remarks>should be subject to validation when exposed to public api. See ISO29500 and MS Impl Notes
    /// Dmatv: Per WORDSNET-15239, layout has a special condition to interpret default/missing horizontal alignment as Left
    /// when FrameLeft is not specified. This property has no such condition, so the reported value may differ from MS Word UI
    /// even if it matches the document xml data.
    /// </remarks>
    /*HorizontalAlignment*/int getHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)fetchAttr(TableAttr.HORIZONTAL_ALIGNMENT); }

    /// <summary>
    /// Returns true if this table should be positioned as floating frame.
    /// </summary>
    /// <remarks>
    /// DD: None of MS Specs define precise conditions for text frame to be floating,
    /// so this is experimentally derived conditions.
    ///
    /// Dmatv: MS Word ignores floating attributes for tables nested in shapes.
    /// To correctly detect if a table is floating, IsNested(table, NodeType.Shape) should be used in addition to this property.
    /// </remarks>
    boolean isFloating()
    {
        // DD: The core idea of this logic is that any of the props below can have defaults, and if at least one floating-related property is present
        // then we can use defaults for other ones.
        // But if none are present or if all are defaults, then it is not a floating object.

        boolean hasFloatingAttribute = hasFrameLeft() ||
            hasFrameTop() ||
            hasNondefaultRelativeHorizontalPosition() ||
            hasNondefaultHorizontalAlignment() ||
            hasNondefaultVerticalAlignment();

        // Dmatv: Per WODSNET-17857, a condition to treat "(vertical position is Page and everything else is default)" as not floating was added.
        return hasFloatingAttribute ||
            (hasNondefaultRelativeVerticalPosition() && (getRelativeVerticalPosition() != RelativeVerticalPosition.PAGE));
    }

    /// <summary>
    /// Table left indent.
    /// </summary>
    int getLeftIndent() { return (/*int*/Integer)fetchAttr(TableAttr.LEFT_INDENT); }
    void setLeftIndent(int value) { setAttr(TableAttr.LEFT_INDENT, value); }

    /// <summary>
    /// Table grid calculated by the new algorithm.
    /// </summary>
    /// <remarks>
    /// The new algorithm currently does not support all possible cases.
    /// State property is set to Applied if the grid was calculated successfully.
    /// </remarks>
    TableGridAttr getCalculatedGrid() { return (TableGridAttr)getDirectAttr(TableAttr.SYS_CALCULATED_TABLE_GRID); }
    void setCalculatedGrid(TableGridAttr value) { setAttr(TableAttr.SYS_CALCULATED_TABLE_GRID, value); }

    /// <summary>
    /// "Grid before" the table row. The number of (empty) table grid columns before the first cell in the row.
    /// </summary>
    /// <remarks>
    /// Currently, it is only used by the "new" table grid calculation algorithm.
    /// </remarks>
    int getGridBefore() { return (/*int*/Integer)fetchAttr(TableAttr.SYS_GRID_BEFORE); }

    /// <summary>
    /// "Grid after" the table row. The number of (empty) table grid columns after the last cell in the row.
    /// </summary>
    /// <remarks>
    /// Currently, it is only used by the "new" table grid calculation algorithm.
    /// </remarks>
    int getGridAfter() { return (/*int*/Integer)fetchAttr(TableAttr.SYS_GRID_AFTER); }

    /// <summary>
    /// Gets or sets <see cref="TableAttr.WidthBefore"/>
    /// </summary>
    /// <remarks>
    /// Dmatv: Returning a value with zero width instead of missing attribute is actually not correct.
    /// MS Word interprets zero and missing width before values differently.
    /// </remarks>
    PreferredWidth getWidthBefore() { return (PreferredWidth)fetchAttr(TableAttr.WIDTH_BEFORE); }
    void setWidthBefore(PreferredWidth value) { setAttr(TableAttr.WIDTH_BEFORE, value); }

    /// <summary>
    /// This attribute is used to access the original "width before" stored in the document.
    /// </summary>
    /// <remarks>
    /// The grid calculation may replace the value read from the document based on an incorrect grid.
    /// An incorrect grid may be stored in the document,
    /// or it may be the result of a simplistic grid calculation for a missing grid.
    /// This attribute preserves the original value stored in the document.
    /// </remarks>
    PreferredWidth getWidthBeforeOriginal() { return (PreferredWidth)fetchAttr(TableAttr.WIDTH_BEFORE_ORIGINAL); }

    /// <summary>
    /// Represents <see cref="TableAttr.WidthBefore"/>, but ignores values in percent units.
    /// </summary>
    /// <remarks>
    /// The property preserves older AW behavior before percent units for width before/after values were supported.
    /// It is used in the contexts where AW does not support or is not supposed to handle percent units.
    /// </remarks>
    int getWidthBeforeTwips() { return ((PreferredWidth)fetchAttr(TableAttr.WIDTH_BEFORE)).getValueTwipsSafe(); }
    void setWidthBeforeTwips(int value)
    {
        PreferredWidth typedValue = value > 0
            ? PreferredWidth.fromTwipsSafe(value)
            : PreferredWidth.AUTO;
        setAttr(TableAttr.WIDTH_BEFORE, typedValue);
    }

    /// <summary>
    /// Gets or sets <see cref="TableAttr.WidthAfter"/>
    /// </summary>
    /// <remarks>
    /// Dmatv: Returning a value with zero width instead of missing attribute is actually not correct.
    /// MS Word interprets zero and missing width before values differently.
    /// </remarks>
    PreferredWidth getWidthAfter() { return (PreferredWidth)fetchAttr(TableAttr.WIDTH_AFTER); }
    void setWidthAfter(PreferredWidth value) { setAttr(TableAttr.WIDTH_AFTER, value); }

    /// <summary>
    /// Represents <see cref="TableAttr.WidthAfter"/>, but ignores values in percent units.
    /// </summary>
    /// <remarks>
    /// The property preserves older AW behavior before percent units for width before/after values were supported.
    /// It is used in the contexts where AW does not support or is not supposed to handle percent units.
    /// </remarks>
    int getWidthAfterTwips() { return ((PreferredWidth)fetchAttr(TableAttr.WIDTH_AFTER)).getValueTwipsSafe(); }
    void setWidthAfterTwips(int value)
    {
        PreferredWidth typedValue = value > 0
            ? PreferredWidth.fromTwipsSafe(value)
            : PreferredWidth.AUTO;
        setAttr(TableAttr.WIDTH_AFTER, typedValue);
    }

    PreferredWidth getDirectWidthBeforeAfterValue(int attributeKey)
    {
        Debug.assertTrue(attributeKey == TableAttr.WIDTH_BEFORE || attributeKey == TableAttr.WIDTH_BEFORE_ORIGINAL || attributeKey == TableAttr.WIDTH_AFTER);
        return (PreferredWidth)getDirectAttr(attributeKey);
    }

    /// <summary>
    /// Optional ISO29500 and up attribute. Specifies the caption for the table.
    /// </summary>
    String getTitle() { return (String)fetchAttr(TableAttr.TITLE); }
    void setTitle(String value) { setAttr(TableAttr.TITLE, value); }

    /// <summary>
    /// Optional ISO29500 and up attribute. Specifies the description for the table.
    /// </summary>
    String getDescription() { return (String) fetchAttr(TableAttr.DESCRIPTION); }
    void setDescription(String value) { setAttr(TableAttr.DESCRIPTION, value); }

    boolean getHidden() { return (/*boolean*/Boolean)fetchAttr(TableAttr.HIDDEN); }
    void setHidden(boolean value) { setAttr(TableAttr.HIDDEN, value); }

    /// <summary>
    /// HTML related information <see cref="HtmlBlock" /> is applied to this row.
    /// </summary>
    int getHtmlBlockId() { return (/*int*/Integer)fetchAttr(TableAttr.HTML_BLOCK_ID); }
    void setHtmlBlockId(int value) { setAttr(TableAttr.HTML_BLOCK_ID, value); }

    /// <summary>
    /// Gets the left padding for the first cell either from the cell properties or from the table properties.
    /// </summary>
    int getFirstCellLeftPadding(CellPr firstCellPr)
    {
        Object firstCellLeftPadding = firstCellPr.getDirectAttr(CellAttr.LEFT_PADDING);
        return (firstCellLeftPadding != null) ? (/*int*/Integer)firstCellLeftPadding : getLeftPadding();
    }

    /// <summary>
    /// This method only makes sense for the first row in the table
    /// as I think the table is positioned according to the first row.
    /// Gets the left position of the table.
    /// Includes left indent, left padding, cell spacing and border width as appropriate.
    /// </summary>
    int getTableLeft(CellPr firstCellPr, boolean isNested)
    {
        return getLeftIndent() - getDistanceFromTableLeftToText(firstCellPr, isNested);
    }

    int getDistanceFromTableLeftToText(CellPr firstCellPr, boolean isNested)
    {
        if (isNested)
        {
            return -getLeftBorderWidth(firstCellPr) / 2;
        }
        else
        {
            // AM I changed calculation slightly while testing TDxaLeft values.
            // Seems it better approximates Word behavior.
            int leftPadding = getFirstCellLeftPadding(firstCellPr);
            int fullCellSpacing = getCellSpacing().getValueRaw() * 2;

            int leftBorderWidth = (leftPadding == 0 || fullCellSpacing > 0) ? getLeftBorderWidth(firstCellPr) : 0;

            if (fullCellSpacing == 0)
                leftBorderWidth = leftBorderWidth / 2;

            // WORDSNET-23923 Looks like the Word ignores negative cell spacing while calculating distance from table to text.
            int effectiveFullCellSpacing = fullCellSpacing > 0 ? fullCellSpacing : 0;

            return leftPadding + effectiveFullCellSpacing + leftBorderWidth;
        }
    }

    private int getLeftBorderWidth(CellPr firstCellPr)
    {
        Border leftCellBorder = firstCellPr.getBorderLeft();
        if (leftCellBorder == null)
            leftCellBorder = getBorderLeft();
        return (leftCellBorder != null) ? ConvertUtilCore.eightsPointToTwip(leftCellBorder.getRawLineWidth()) : 0;
    }

    /// <summary>
    /// Note this is only available during read and write of the attributes.
    /// </summary>
    CellPrCollection getCellsPr() { return (CellPrCollection)getDirectAttr(TableAttr.SYS_CELLS); }

    /// <summary>
    /// Removes CellsPr from this collection and from the revision.
    /// </summary>
    void removeCellsPr()
    {
        remove(TableAttr.SYS_CELLS);

        FormatRevision rev = getFormatRevision();
        if (rev != null)
            rev.getRevPr().remove(TableAttr.SYS_CELLS);
    }

    /// <summary>
    /// Returns either revised or original properties depending on TablePr has format revision.
    /// Note that revised properties is NOT accepted properties and needs to be accepted to get full final property set.
    /// </summary>
    TablePr getRevisedPr() { return hasFormatRevision() ? (TablePr)getFormatRevision().getRevPr() : this; }

    /// <summary>
    /// Returns final table properties. If there is a formatting revision applied to the table, accepted table properties
    /// are returned. Otherwise, this table properties are returned as is.
    /// </summary>
    TablePr getFinalPr()
    {
        if (!hasFormatRevision())
        {
            return this;
        }

        TablePr finalPr = AttrCollectionExtension.deepClone(this);
        finalPr.acceptFormatRevision();
        return finalPr;
    }

    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    static Object fetchDefaultAttr(int key)
    {
        return G_DEFAULTS.fetchAttr(key);
    }

    //>>>>>>>> #region  IRowAttrSource

    public Object /*IRowAttrSource.*/getDirectRowAttr(int key)
    {
        return super.getDirectAttr(key);
    }

    public Object /*IRowAttrSource.*/fetchRowAttr(int key)
    {
        return super.fetchAttr(key);
    }

    public Object /*IRowAttrSource.*/fetchInheritedRowAttr(int key)
    {
        return super.fetchInheritedAttr(key);
    }

    public void /*IRowAttrSource.*/setRowAttr(int key, Object value)
    {
        super.setAttr(key, value);
    }

    public void /*IRowAttrSource.*/clearRowAttrs()
    {
        super.clear();
    }

    public void /*IRowAttrSource.*/resetToDefaultAttrs()
    {
        ((IRowAttrSource)this).clearRowAttrs();
    }

    private static /*final*/ TablePr G_DEFAULTS;

    /// <summary>
    /// The key is <see cref="BorderType"/> that represents a border that can occur in a <see cref="TablePr"/> collection.
    /// The value is an <see cref="TableAttr"/> integer that represents the attribute key of that border.
    /// </summary>
    static /*final*/ SortedList<Integer, Integer> POSSIBLE_BORDER_KEYS =
        new SortedList<Integer, Integer>();

    //<<<<<<<< #endregion 

    static/* TablePr()*/
    {
        G_DEFAULTS = new TablePr();

        // These are complex attributes. Be careful not to modify the actual values in them.
        G_DEFAULTS.add(TableAttr.BORDER_TOP, new Border());
        G_DEFAULTS.add(TableAttr.BORDER_LEFT, new Border());
        G_DEFAULTS.add(TableAttr.BORDER_BOTTOM, new Border());
        G_DEFAULTS.add(TableAttr.BORDER_RIGHT, new Border());
        G_DEFAULTS.add(TableAttr.BORDER_HORIZONTAL, new Border());
        G_DEFAULTS.add(TableAttr.BORDER_VERTICAL, new Border());
        G_DEFAULTS.add(TableAttr.SHADING, new Shading());

        G_DEFAULTS.add(TableAttr.ISTD, StyleIndex.TABLE_NORMAL);
        G_DEFAULTS.add(TableAttr.STYLE_OPTIONS, TableStyleOptions.DEFAULT);

        G_DEFAULTS.add(TableAttr.PREFERRED_WIDTH, PreferredWidth.AUTO);
        G_DEFAULTS.add(TableAttr.ALLOW_AUTO_FIT, false);
        G_DEFAULTS.add(TableAttr.LEFT_INDENT, 0);
        G_DEFAULTS.add(TableAttr.WIDTH_BEFORE_ORIGINAL, PreferredWidth.AUTO);
        G_DEFAULTS.add(TableAttr.WIDTH_BEFORE, PreferredWidth.AUTO);
        G_DEFAULTS.add(TableAttr.WIDTH_AFTER, PreferredWidth.AUTO);
        G_DEFAULTS.add(TableAttr.WIDTH_AFTER_ORIGINAL, PreferredWidth.AUTO);
        G_DEFAULTS.add(TableAttr.ALIGNMENT, TableAlignment.LEFT);
        G_DEFAULTS.add(TableAttr.BIDI, false);

        G_DEFAULTS.add(TableAttr.TOP_PADDING, 0);
        G_DEFAULTS.add(TableAttr.BOTTOM_PADDING, 0);
        G_DEFAULTS.add(TableAttr.LEFT_PADDING, 0);
        G_DEFAULTS.add(TableAttr.RIGHT_PADDING, 0);

        G_DEFAULTS.add(TableAttr.CELL_SPACING, PreferredWidth.ZERO_TWIPS);

        // TODO RK MS-OI29500 2.1.179 Part 1 Section 17.4.81, trHeight (Table Row Height)
        // a. The standard states that if the hRule attribute is omitted, then its value is assumed to be auto.
        // In Word, if the hRule attribute is omitted, then its value is assumed to be atLeast.
        //
        // Auto is equivalent to (AtLeast rule with 0 value).
        G_DEFAULTS.add(TableAttr.ROW_HEIGHT, new Height(HeightRule.AUTO, 0));
        G_DEFAULTS.add(TableAttr.ALLOW_BREAK_ACROSS_PAGES, true);
        G_DEFAULTS.add(TableAttr.HEADING_FORMAT, false);

        G_DEFAULTS.add(TableAttr.ALLOW_OVERLAP, true);
        G_DEFAULTS.add(TableAttr.FRAME_DISTANCE_FROM_TOP, 0);
        G_DEFAULTS.add(TableAttr.FRAME_DISTANCE_FROM_BOTTOM, 0);
        G_DEFAULTS.add(TableAttr.FRAME_DISTANCE_FROM_LEFT, 0);
        G_DEFAULTS.add(TableAttr.FRAME_DISTANCE_FROM_RIGHT, 0);
        G_DEFAULTS.add(TableAttr.RELATIVE_HORIZONTAL_POSITION, RelativeHorizontalPosition.DEFAULT);
        G_DEFAULTS.add(TableAttr.RELATIVE_VERTICAL_POSITION, RelativeVerticalPosition.TABLE_DEFAULT);
        G_DEFAULTS.add(TableAttr.FRAME_LEFT, 0);
        G_DEFAULTS.add(TableAttr.FRAME_TOP, 0);
        G_DEFAULTS.add(TableAttr.HORIZONTAL_ALIGNMENT, HorizontalAlignment.NONE);
        G_DEFAULTS.add(TableAttr.VERTICAL_ALIGNMENT, VerticalAlignment.NONE);

        // Defaults are zero as per the DII implementation notes.
        G_DEFAULTS.add(TableAttr.STYLE_ROW_BAND_SIZE, 0);
        G_DEFAULTS.add(TableAttr.STYLE_COL_BAND_SIZE, 0);

        G_DEFAULTS.add(TableAttr.TITLE, "");
        G_DEFAULTS.add(TableAttr.DESCRIPTION, "");

        G_DEFAULTS.add(TableAttr.HIDDEN, false);
        G_DEFAULTS.add(TableAttr.HTML_BLOCK_ID, 0);

        G_DEFAULTS.add(TableAttr.SYS_GRID_BEFORE, 0);
        G_DEFAULTS.add(TableAttr.SYS_GRID_AFTER, 0);
        G_DEFAULTS.add(TableAttr.SYS_LEFT_INDENT_97, 0);
        G_DEFAULTS.add(TableAttr.SYS_LEFT_INDENT_97_BASE, 0);
        G_DEFAULTS.add(TableAttr.SYS_WORD_97_LOGIC, false);
        G_DEFAULTS.add(TableAttr.SYS_DXA_LEFT, 0);
        G_DEFAULTS.add(TableAttr.SYS_DXA_GAP_HALF, 0);
        G_DEFAULTS.add(TableAttr.SYS_ALIGNMENT_97, TableAlignment.LEFT);

        POSSIBLE_BORDER_KEYS.add(BorderType.TOP, TableAttr.BORDER_TOP);
        POSSIBLE_BORDER_KEYS.add(BorderType.LEFT, TableAttr.BORDER_LEFT);
        POSSIBLE_BORDER_KEYS.add(BorderType.BOTTOM, TableAttr.BORDER_BOTTOM);
        POSSIBLE_BORDER_KEYS.add(BorderType.RIGHT, TableAttr.BORDER_RIGHT);
        POSSIBLE_BORDER_KEYS.add(BorderType.HORIZONTAL, TableAttr.BORDER_HORIZONTAL);
        POSSIBLE_BORDER_KEYS.add(BorderType.VERTICAL, TableAttr.BORDER_VERTICAL);
    }
}

