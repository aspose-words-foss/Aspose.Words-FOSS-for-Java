// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msArray;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ConvertUtilCore;
import com.aspose.MathUtil;


/// <summary>
/// Represents a table style.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <dev>
/// It is interesting to note that all attribute collections in the style are not supposed
/// to contain attributes that reference another style. E.g. TablePr of the style should not
/// contain Istd of a table style. It is actually a good thing. The only problem is that we
/// do not validate it and maybe reading from some formats could leave Istd in these collections.
/// </dev>
public class TableStyle extends Style implements ICellAttrSource, IRowAttrSource, IBorderAttrSource, IShadingAttrSource
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TableStyle()
    {
    	super(StyleType.TABLE);
	
    }

    /// <summary>
    /// See <see cref="Style.Clone"/> for more info.
    /// </summary>
    /*override*/ Style deepClone()
    {
        TableStyle lhs = (TableStyle)super.deepClone();

        cloneProperties(this, lhs);

        return lhs;
    }

    /// <summary>
    /// Clones table style properties.
    /// </summary>
    static void cloneProperties(TableStyle src, TableStyle dst)
    {
        if (src.getTablePr() != null)
            dst.setTablePr(AttrCollectionExtension.deepClone(src.getTablePr()));

        if (src.getRowPr() != null)
            dst.setRowPr(AttrCollectionExtension.deepClone(src.getRowPr()));

        if (src.getCellPr() != null)
            dst.setCellPr(AttrCollectionExtension.deepClone(src.getCellPr()));

        if (src.mConditionalStyles != null)
        {
            dst.mConditionalStyles = new ConditionalStyleCollection(dst);
            for (ConditionalStyle conditionalStyle : src.mConditionalStyles.getDefinedStyles())
                dst.getConditionalStyles().add(conditionalStyle.deepClone());
        }
    }

    void addConditionalStyle(ConditionalStyle conditionalStyle)
    {
        if (!getConditionalStyles().containsTableStyleOverride(conditionalStyle.getOverrideType()))
            getConditionalStyles().add(conditionalStyle);
    }

    void clearConditionalStyles()
    {
        if (mConditionalStyles != null)
            mConditionalStyles.clear();
    }

    TablePr getExpandedTablePr()
    {
        TablePr dstTablePr = new TablePr();
        expandTablePr(dstTablePr);
        return dstTablePr;
    }

    /// <summary>
    /// Expands table style paragraph formatting including conditional formatting.
    /// </summary>
    void expandParaPr(Cell cell, ParaPr dstParaPr)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();
        if (baseStyle != null)
            baseStyle.expandParaPr(cell, dstParaPr);

        // Expand whole formatting first.
        getParaPr().expandTo(dstParaPr);

        // Expand conditional formatting.
        ConditionalStyle conditionalStyle = nextTableStylePrDesc(null);
        while (conditionalStyle != null)
        {
            if ((conditionalStyle.getParaPr() != null) && isApplicable(cell, conditionalStyle))
                conditionalStyle.getParaPr().expandTo(dstParaPr);

            conditionalStyle = nextTableStylePrDesc(conditionalStyle);
        }
    }

    /// <summary>
    /// Expands table style font formatting including conditional formatting.
    /// </summary>
    void expandRunPr(Cell cell, RunPr dstRunPr)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();
        if (baseStyle != null)
            baseStyle.expandRunPr(cell, dstRunPr);

        // Expand whole formatting first.
        getRunPr().expandTo(dstRunPr);

        // Expand conditional formatting.
        ConditionalStyle conditionalStyle = nextTableStylePrDesc(null);
        while (conditionalStyle != null)
        {
            if ((conditionalStyle.getRunPr() != null) && isApplicable(cell, conditionalStyle))
                conditionalStyle.getRunPr().expandTo(dstRunPr);

            conditionalStyle = nextTableStylePrDesc(conditionalStyle);
        }
    }

    private ConditionalStyle nextTableStylePrDesc(ConditionalStyle tableStylePr)
    {
        int startFrom = (tableStylePr == null) ? 0 : msArray.indexOf((G_FETCH_ORDER_DESC), (tableStylePr.getOverrideType()), 0, (G_FETCH_ORDER_DESC).length) + 1;

        // Look for first occurred item in fetch order.
        for (int i = startFrom; i < G_FETCH_ORDER_DESC.length; i++)
        {
            if (getConditionalStyles().containsTableStyleOverride(G_FETCH_ORDER_DESC[i]))
                return getConditionalStyles().getByTableStyleOverrideType(G_FETCH_ORDER_DESC[i]);
        }

        return null;
    }

    private void expandTablePr(TablePr dstTablePr)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();
        if (baseStyle != null)
            baseStyle.expandTablePr(dstTablePr);

        if (mTablePr != null)
            mTablePr.expandTo(dstTablePr);
    }

    TablePr getExpandedRowPr()
    {
        TablePr dstRowPr = new TablePr();
        expandRowPr(dstRowPr);
        return dstRowPr;
    }

    private void expandRowPr(TablePr dstRowPr)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();
        if (baseStyle != null)
            baseStyle.expandRowPr(dstRowPr);

        if (mRowPr != null)
            mRowPr.expandTo(dstRowPr);
    }

    CellPr getExpandedCellPr()
    {
        CellPr dstCellPr = new CellPr();
        expandCellPr(dstCellPr);
        return dstCellPr;
    }

    /// <summary>
    /// Returns expanded conditional formatting of given type.
    /// </summary>
    ConditionalStyle getExpandedConditionalStyle(/*TableStyleOverrideType*/int type)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();

        ConditionalStyle expandedConditionalStyle = null;

        if (baseStyle != null)
            expandedConditionalStyle = baseStyle.getExpandedConditionalStyle(type);

        if (expandedConditionalStyle == null)
            expandedConditionalStyle = new ConditionalStyle(type);

        if (getConditionalStyles().containsTableStyleOverride(type))
        {
            ConditionalStyle thisConditionalStyle = getConditionalStyles().getByTableStyleOverrideType(type);
            if (thisConditionalStyle.hasRunFormatting())
            {
                thisConditionalStyle.getRunPr().expandTo(expandedConditionalStyle.getRunPr());
                thisConditionalStyle.getRunPr().themeColorInheritanceHack(expandedConditionalStyle.getRunPr());
            }

            if (thisConditionalStyle.hasCellFormatting())
                thisConditionalStyle.getCellPr().expandTo(expandedConditionalStyle.getCellPr());

            if (thisConditionalStyle.hasParagraphFormatting())
                thisConditionalStyle.getParaPr().expandTo(expandedConditionalStyle.getParaPr());

            if (thisConditionalStyle.hasTableFormatting())
                thisConditionalStyle.getTablePr().expandTo(expandedConditionalStyle.getTablePr());

            if (thisConditionalStyle.hasRowFormatting())
                thisConditionalStyle.getRowPr().expandTo(expandedConditionalStyle.getRowPr());
        }

        return expandedConditionalStyle;
    }

    void expandCellPr(Cell cell, CellPr dstCellPr)
    {
        expandCellPr(dstCellPr);

        // Expand conditional formatting.
        ConditionalStyle conditionalStyle = nextTableStylePrDesc(null);
        while (conditionalStyle != null)
        {
            if ((conditionalStyle.getCellPr() != null) && isApplicable(cell, conditionalStyle))
                conditionalStyle.getCellPr().expandTo(dstCellPr);

            conditionalStyle = nextTableStylePrDesc(conditionalStyle);
        }
    }

    private void expandCellPr(CellPr dstCellPr)
    {
        TableStyle baseStyle = (TableStyle)getBaseStyle();
        if (baseStyle != null)
            baseStyle.expandCellPr(dstCellPr);

        if (mCellPr != null)
            mCellPr.expandTo(dstCellPr);
    }

    //>>>>>>>> #region  ICellAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/getDirectCellAttr(int key)
    {
        // AM. Word ignores borders defined in table style CellPr
        if (CellPr.POSSIBLE_BORDER_KEYS.containsValue(key))
            return null;

        return mCellPr.get(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchCellAttr(int key)
    {
        Object value = mCellPr.get(key);
        return (value != null) ? value : ((ICellAttrSource)this).fetchInheritedCellAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*ICellAttrSource.*/fetchInheritedCellAttr(int key)
    {
        TableStyle baseStyle = ms.as(getBaseStyle(), TableStyle.class);

        return (baseStyle != null) ? ((ICellAttrSource)baseStyle).fetchCellAttr(key) : CellPr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/setCellAttr(int key, Object value)
    {
        mCellPr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*ICellAttrSource.*/clearCellAttrs()
    {
        mCellPr.clear();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IRowAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/getDirectRowAttr(int key)
    {
        // WORDSNET-12776
        // At the moment we don't have interface to get value from TablePr. And we use IRowAttrSource to get values from TablePr.
        // Added RowPr to get all style attribute.
        Object value = mRowPr.get(key);
        return (value != null) ? value : mTablePr.get(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchRowAttr(int key)
    {
        Object value = ((IRowAttrSource)this).getDirectRowAttr(key);
        return (value != null) ? value : ((IRowAttrSource)this).fetchInheritedRowAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRowAttrSource.*/fetchInheritedRowAttr(int key)
    {
        TableStyle baseStyle = ms.as(getBaseStyle(), TableStyle.class);
        return (baseStyle != null) ? ((IRowAttrSource)baseStyle).fetchRowAttr(key) : TablePr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/setRowAttr(int key, Object value)
    {
        mTablePr.set(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/clearRowAttrs()
    {
        // WORDSNET-12776
        // Added clearing both objects, because we get values from them.
        mRowPr.clear();
        mTablePr.clear();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRowAttrSource.*/resetToDefaultAttrs()
    {
        // WORDSNET-12776
        // Added clearing both objects, because we get values from them.
        mRowPr.clear();
        mTablePr.clear();
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IBorderAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mTablePr.get(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        TableStyle baseStyle = ms.as(getBaseStyle(), TableStyle.class);
        if (baseStyle != null)
        {
            Object value = ((IBorderAttrSource)baseStyle).getDirectBorderAttr(key);
            if (value != null)
                return value;

            return ((IBorderAttrSource)baseStyle).fetchInheritedBorderAttr(key);
        }

        return TablePr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mTablePr.setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return TablePr.POSSIBLE_BORDER_KEYS; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShadingAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IShadingAttrSource.*/fetchInheritedShadingAttr(int key)
    {
        TableStyle baseStyle = ms.as(getBaseStyle(), TableStyle.class);
        if (baseStyle != null)
        {
            Object value = baseStyle.getCellPr().get(key);
            if (value != null)
                return value;

            return ((IShadingAttrSource)baseStyle).fetchInheritedShadingAttr(key);
        }

        return CellPr.fetchDefaultAttr(key);
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets or sets a flag indicating whether text in a table row is allowed to split across a page break.
    /// </summary>
    /// <remarks>
    /// The default value is <c>true</c>.
    /// </remarks>
    public boolean getAllowBreakAcrossPages() { return (/*boolean*/Boolean)((IRowAttrSource)this).fetchRowAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES); }
    public void setAllowBreakAcrossPages(boolean value)
    {
        mRowPr.setAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES, value);
        mTablePr.remove(TableAttr.ALLOW_BREAK_ACROSS_PAGES); // Word stores the attribute in trPr.
    }

    /// <summary>
    /// Gets the collection of default cell borders for the style.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBorders == null)
            mBorders = new BorderCollection(this);
        return mBorders;
    }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the left of the contents of table cells.
    /// </summary>
    public double getLeftPadding() { return fetchPadding(TableAttr.LEFT_PADDING, CellAttr.LEFT_PADDING); }
    public void setLeftPadding(double value) { mTablePr.setAttr(TableAttr.LEFT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the right of the contents of table cells.
    /// </summary>
    public double getRightPadding() { return fetchPadding(TableAttr.RIGHT_PADDING, CellAttr.RIGHT_PADDING); }
    public void setRightPadding(double value) { mTablePr.setAttr(TableAttr.RIGHT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add above the contents of table cells.
    /// </summary>
    public double getTopPadding() { return fetchPadding(TableAttr.TOP_PADDING, CellAttr.TOP_PADDING); }
    public void setTopPadding(double value) { mTablePr.setAttr(TableAttr.TOP_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add below the contents of table cells.
    /// </summary>
    public double getBottomPadding() { return fetchPadding(TableAttr.BOTTOM_PADDING, CellAttr.BOTTOM_PADDING); }
    public void setBottomPadding(double value) { mTablePr.setAttr(TableAttr.BOTTOM_PADDING, ConvertUtilCore.pointToTwip(value)); }

    // WORDSJAVA-2491 check both the Table and Cell attributes to find Paddings.
    private double fetchPadding(int tablePaddingAttr, int cellPaddingAttr)
    {
        if ((/*int*/Integer)((IRowAttrSource)this).fetchRowAttr(tablePaddingAttr) != 0)
            return ConvertUtilCore.twipToPoint((/*int*/Integer)((IRowAttrSource)this).fetchRowAttr(tablePaddingAttr));
        else
            return ConvertUtilCore.twipToPoint((/*int*/Integer)((ICellAttrSource)this).fetchCellAttr(cellPaddingAttr));
    }

    /// <summary>
    /// Specifies the alignment for the table style.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="TableAlignment.Left"/>.
    /// </remarks>
    public /*TableAlignment*/int getAlignment() { return (/*TableAlignment*/Integer)((IRowAttrSource)this).fetchRowAttr(TableAttr.ALIGNMENT); }
    public void setAlignment(/*TableAlignment*/int value)
    {
        // Word stores the attribute in tblPr and trPr.
        mRowPr.setAttr(TableAttr.ALIGNMENT, value);
        mTablePr.setAttr(TableAttr.ALIGNMENT, value);
    }

    /// <summary>
    /// Gets or sets the amount of space (in points) between the cells.
    /// </summary>
    public double getCellSpacing()
    {
        PreferredWidth cellSpacing = (PreferredWidth)((IRowAttrSource)this).fetchRowAttr(TableAttr.CELL_SPACING);
        return ConvertUtilCore.twipToPoint(cellSpacing.getValueRaw());
    }
    public void setCellSpacing(double value)
    {
        // Word stores the attribute in tblPr and trPr.
        mRowPr.setAttr(TableAttr.CELL_SPACING, PreferredWidth.fromPointsSafe(value));
        mTablePr.setAttr(TableAttr.CELL_SPACING, PreferredWidth.fromPointsSafe(value));
    }

    /// <summary>
    /// Gets or sets the value that represents the left indent of a table.
    /// </summary>
    public double getLeftIndent() { return ConvertUtilCore.twipToPoint((/*int*/Integer)((IRowAttrSource)this).fetchRowAttr(TableAttr.LEFT_INDENT)); }
    public void setLeftIndent(double value)
    {
        mTablePr.setAttr(TableAttr.LEFT_INDENT, ConvertUtilCore.pointToTwip(value));
        mRowPr.remove(TableAttr.LEFT_INDENT); // Word stores the attribute in tblPr.
    }

    /// <summary>
    /// Gets a <see cref="Words.Shading"/> object that refers to the shading formatting for table cells.
    /// </summary>
    public Shading getShading()
    {
        Shading shading = (Shading)mCellPr.get(CellAttr.SHADING);
        if (shading == null)
        {
            TableStyle parent = this;
            shading = new Shading(parent, CellAttr.SHADING);
            shading = shading;
            mCellPr.setAttr(CellAttr.SHADING, shading);
        }
        return shading;
    }

    /// <summary>
    /// Specifies the vertical alignment for the cells.
    /// </summary>
    /// <remarks>
    /// The default value is <see cref="CellVerticalAlignment.Top"/>.
    /// </remarks>
    /// <dev>
    /// AM. We copy Word VBA here and do not expose CellFormat object.
    /// I think the reason is that CellFormat contains properties such as VerticalMerge which is not applicable
    /// to table style.
    /// </dev>
    public /*CellVerticalAlignment*/int getVerticalAlignment() { return (/*CellVerticalAlignment*/Integer)((ICellAttrSource)this).fetchCellAttr(CellAttr.VERTICAL_ALIGNMENT); }
    public void setVerticalAlignment(/*CellVerticalAlignment*/int value) { mCellPr.setAttr(CellAttr.VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Gets or sets a number of rows to include in the banding when the style specifies odd/even row banding.
    /// </summary>
    public int getRowStripe() { return (/*int*/Integer)((IRowAttrSource)this).fetchRowAttr(TableAttr.STYLE_ROW_BAND_SIZE); }
    public void setRowStripe(int value)
    {
        mTablePr.setAttr(TableAttr.STYLE_ROW_BAND_SIZE, value);
        mRowPr.remove(TableAttr.STYLE_ROW_BAND_SIZE); // Word stores the attribute in tblPr.
    }

    /// <summary>
    /// Gets or sets a number of columns to include in the banding when the style specifies odd/even columns banding.
    /// </summary>
    public int getColumnStripe() { return (/*int*/Integer)((IRowAttrSource)this).fetchRowAttr(TableAttr.STYLE_COL_BAND_SIZE); }
    public void setColumnStripe(int value)
    {
        mTablePr.setAttr(TableAttr.STYLE_COL_BAND_SIZE, value);
        mRowPr.remove(TableAttr.STYLE_COL_BAND_SIZE); // Word stores the attribute in tblPr.
    }

    /// <summary>
    /// Collection of conditional styles that may be defined for this table style.
    /// </summary>
    public ConditionalStyleCollection getConditionalStyles()
    {
        if (mConditionalStyles == null)
            mConditionalStyles = new ConditionalStyleCollection(this);
        return mConditionalStyles;
    }

    /// <summary>
    /// 2.7.5.4        Style Table Properties
    /// </summary>
    TablePr getTablePr() { return mTablePr; }
    void setTablePr(TablePr value) { mTablePr = value; }

    /// <summary>
    /// 2.7.5.11    Style Table Row Properties
    /// </summary>
    TablePr getRowPr() { return mRowPr; }
    void setRowPr(TablePr value) { mRowPr = value; }

    /// <summary>
    /// 2.7.5.8        Style Table Cell Properties
    /// </summary>
    CellPr getCellPr() { return mCellPr; }
    void setCellPr(CellPr value) { mCellPr = value; }

    /// <summary>
    /// Returns attributes collection of a conditional style of the given type and PrType.
    /// Performs creation of collection if they don't exist.
    /// </summary>
    /// <returns>CellPr attribute collection.</returns>
    WordAttrCollection fetchConditionalStylePr(/*TableStyleOverrideType*/int type, /*AttrCollectionType*/int prType)
    {
        ConditionalStyle conditionalStyle = getConditionalStyles().getByTableStyleOverrideType(type);

        switch (prType)
        {
            case AttrCollectionType.CELL_PR:
                return conditionalStyle.getCellPr();
            case AttrCollectionType.PARA_PR:
                return conditionalStyle.getParaPr();
            case AttrCollectionType.RUN_PR:
                return conditionalStyle.getRunPr();
            default:
                throw new IllegalStateException("Unknown AttrCollectionType value.");
        }
    }

    /// <summary>
    /// Returns true if there is at least one formatting attribute specified in this table style.
    /// </summary>
    /*override*/ boolean hasFormatting()
    {
        if (super.hasFormatting())
            return true;

        if (mTablePr.getCount() > 0)
            return true;
        if (mRowPr.getCount() > 0)
            return true;
        if (mCellPr.getCount() > 0)
            return true;

        if (hasConditionalFormatting())
            return true;

        return false;
    }

    /// <summary>
    /// Returns attribute value defined in this style considering conditional formatting.
    /// Does not fetch global defaults.
    /// Does not inherit table borders or paddings.
    /// </summary>
    Object getCellAttr(int key, Cell cell)
    {
        return getAttr(key, cell, AttrCollectionType.CELL_PR);
    }

    /// <summary>
    /// Returns attribute value defined in this style considering conditional formatting.
    /// Does not fetch global defaults.
    /// </summary>
    Object getRunAttr(int key, Cell cell)
    {
        return getAttr(key, cell, AttrCollectionType.RUN_PR);
    }

    /// <summary>
    /// Returns attribute value defined in this style considering conditional formatting.
    /// Does not fetch global defaults.
    /// </summary>
    Object getParaAttr(int key, Cell cell)
    {
        return getAttr(key, cell, AttrCollectionType.PARA_PR);
    }

    /// <summary>
    /// Returns attribute value defined in this style considering conditional formatting.
    /// Does not fetch global defaults.
    /// Does not inherit table borders or paddings.
    /// </summary>
    private Object getAttr(int key, Cell cell, /*AttrCollectionType*/int type)
    {
        Object value = null;
        WordAttrCollection pr;

        // Try conditional formatting first.
        if (hasConditionalFormatting())
        {
            ConditionalStyle conditionalStyle = nextConditionalStyle(null);
            while (conditionalStyle != null)
            {
                pr = conditionalStyle.getCollection(type);

                if ((pr != null) && isApplicable(cell, conditionalStyle))
                {
                    // Before get property we might need to transform key.
                    // AM. Actually this is needed for cell border keys only but
                    // for simplicity reason lets do it always.
                    int newKey = transformCellBorderKey(conditionalStyle.getOverrideType(), cell, key);

                    value = pr.get(newKey);

                    if (value != null)
                        return value;
                }

                conditionalStyle = nextConditionalStyle(conditionalStyle);
            }
        }

        // Get whole table formatting if no conditional formatting was found.
        pr = getCollection(type);
        if (pr != null)
            value = pr.get(key);

        if (value != null)
            return value;

        TableStyle baseStyle = ms.as(getBaseStyle(), TableStyle.class);
        return (baseStyle != null) ? baseStyle.getAttr(key, cell, type) : null;
    }

    /// <summary>
    /// Iterates through defined conditional styles in certain order.
    /// </summary>
    /// <remarks>
    /// If null is passed as parameter, the first conditional style is returned
    /// otherwise next one is returned or null if this is the last conditional style.
    /// </remarks>
    private ConditionalStyle nextConditionalStyle(ConditionalStyle conditionalStyle)
    {
        int startFrom = (conditionalStyle == null) ? 0 : msArray.indexOf((G_FETCH_ORDER), (conditionalStyle.getOverrideType()), 0, (G_FETCH_ORDER).length) + 1;

        // Look for first occurred item in fetch order.
        for (int i = startFrom; i < G_FETCH_ORDER.length; i++)
        {
            if (getConditionalStyles().containsTableStyleOverride(G_FETCH_ORDER[i]))
                return getConditionalStyles().getByTableStyleOverrideType(G_FETCH_ORDER[i]);
        }

        return null;
    }

    /// <summary>
    /// Checks if the conditional style can be applied to the given cell.
    /// </summary>
    boolean isApplicable(Cell cell, ConditionalStyle conditionalStyle)
    {
        Row row = cell.getParentRow();

        if ((row == null) || (row.getTablePr() == null))
            return false;

        boolean isFirstRow = row.isFirstRow() || row.isHeadingRow();
        boolean isLastRow = row.isLastRow();
        boolean isFirstCell = cell.isFirstCell();
        boolean isLastCell = cell.isLastCell();
        boolean hasHeadingRow = row.getParentTable().getFirstRow().isHeadingRow();

        /*TableStyleOptions*/int options = row.getTablePr().getStyleOptions();
        boolean isApplyFirstRow = (options & TableStyleOptions.FIRST_ROW) != 0;
        boolean isApplyLastRow = (options & TableStyleOptions.LAST_ROW) != 0;
        boolean isApplyFirstCol = (options & TableStyleOptions.FIRST_COLUMN) != 0;
        boolean isApplyLastCol = (options & TableStyleOptions.LAST_COLUMN) != 0;
        boolean isApplyRowBand = (options & TableStyleOptions.ROW_BANDS) != 0;
        boolean isApplyColBand = (options & TableStyleOptions.COLUMN_BANDS) != 0;

        int rowBandSize = (getTablePr().getStyleRowBandSize() > 0) ? getTablePr().getStyleRowBandSize() : 1;
        int colBandSize = (getTablePr().getStyleColBandSize() > 0) ? getTablePr().getStyleColBandSize() : 1;

        // Table has first row/column only if table style specifies conditional formatting for the first row.
        // WORDSNET-20751, 21796. MS Word excludes rows marked as repeated on every new page (hasHeadingRow) from banding
        // regardless of whether the First Row conditional style is enabled or not. Do the same.
        boolean hasFirstRow = (isApplyFirstRow || hasHeadingRow) &&
            getConditionalStyles().containsTableStyleOverride(TableStyleOverrideType.FIRST_ROW);
        boolean hasFirstCol = isApplyFirstCol &&
            getConditionalStyles().containsTableStyleOverride(TableStyleOverrideType.FIRST_COLUMN);

        boolean isOddRowBand = false, isOddColBand = false;
        /*TableStyleOverrideType*/int conditionalStyleType = conditionalStyle.getOverrideType();

        // WORDSNET-15746 Skip calculation of indexes of row and cell to avoid affect to performance.
        switch (conditionalStyleType)
        {
            case TableStyleOverrideType.ODD_ROW_BANDING:
            case TableStyleOverrideType.EVEN_ROW_BANDING:
                isOddRowBand = MathUtil.isOdd((row.getRowIndex() + (hasFirstRow ? 0 : 1)) / rowBandSize);
                break;
            case TableStyleOverrideType.ODD_COLUMN_BANDING:
            case TableStyleOverrideType.EVEN_COLUMN_BANDING:
                isOddColBand = MathUtil.isOdd((cell.getColumnIndex() + (hasFirstCol ? 0 : 1)) / colBandSize);
                break;
            default:
                break;
        }

        switch (conditionalStyleType)
        {
            case TableStyleOverrideType.BOTTOM_RIGHT_CELL: return isApplyLastRow && isApplyLastCol && isLastRow && isLastCell;
            case TableStyleOverrideType.BOTTOM_LEFT_CELL: return isApplyLastRow && isApplyFirstCol && isLastRow && isFirstCell;
            case TableStyleOverrideType.TOP_RIGHT_CELL: return isApplyFirstRow && isApplyLastCol && isFirstRow && isLastCell;
            case TableStyleOverrideType.TOP_LEFT_CELL: return isApplyFirstRow && isApplyFirstCol && isFirstRow && isFirstCell;
            case TableStyleOverrideType.LAST_ROW: return isApplyLastRow && isLastRow;
            case TableStyleOverrideType.FIRST_ROW: return isApplyFirstRow && isFirstRow;
            case TableStyleOverrideType.LAST_COLUMN: return isApplyLastCol && isLastCell;
            case TableStyleOverrideType.FIRST_COLUMN: return isApplyFirstCol && isFirstCell;
            case TableStyleOverrideType.ODD_ROW_BANDING: return isApplyRowBand && isOddRowBand && (!isLastRow || !isApplyLastRow);
            case TableStyleOverrideType.EVEN_ROW_BANDING: return isApplyRowBand && !isOddRowBand && (!isLastRow || !isApplyLastRow);
            case TableStyleOverrideType.ODD_COLUMN_BANDING: return isApplyColBand && isOddColBand;
            case TableStyleOverrideType.EVEN_COLUMN_BANDING: return isApplyColBand && !isOddColBand;
            default:
                throw new IllegalStateException("Unexpected TableStyleOverrideType value.");
        }
    }

    /// <summary>
    /// Returns <c>true</c> if any conditional formatting is defined in this style.
    /// </summary>
    boolean hasConditionalFormatting()
    {
        if (mConditionalStyles != null)
        {
            for (ConditionalStyle conditionalStyle : mConditionalStyles.getDefinedStyles())
            {
                if (conditionalStyle.hasFormatting())
                    return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Converts cell border key if needed.
    /// </summary>
    /// <remarks>
    /// For certain TableStyleOverrideTypes we need to apply horizontal/vertical borders instead
    /// of left/right borders if cell is not first/last in the row/column.
    /// </remarks>
    private static int transformCellBorderKey(/*TableStyleOverrideType*/int type, Cell cell, int key)
    {
        int newKey = key;
        switch (type)
        {
            case TableStyleOverrideType.FIRST_ROW:
            case TableStyleOverrideType.LAST_ROW:
            case TableStyleOverrideType.ODD_ROW_BANDING:
            case TableStyleOverrideType.EVEN_ROW_BANDING:
                if ((key == CellAttr.BORDER_RIGHT) && !cell.isLastCell())
                    newKey = CellAttr.BORDER_VERTICAL;
                else if ((key == CellAttr.BORDER_LEFT) && !cell.isFirstCell())
                    newKey = CellAttr.BORDER_VERTICAL;
                break;

            case TableStyleOverrideType.FIRST_COLUMN:
            case TableStyleOverrideType.LAST_COLUMN:
            case TableStyleOverrideType.ODD_COLUMN_BANDING:
            case TableStyleOverrideType.EVEN_COLUMN_BANDING:
                if ((key == CellAttr.BORDER_BOTTOM) && !cell.getParentRow().isLastRow())
                    newKey = CellAttr.BORDER_HORIZONTAL;
                else if ((key == CellAttr.BORDER_TOP) && !cell.getParentRow().isFirstRow())
                    newKey = CellAttr.BORDER_HORIZONTAL;
                break;

            default:
                break;
        }

        return newKey;
    }

    /// <summary>
    /// Returns collection of given type.
    /// </summary>
    private WordAttrCollection getCollection(/*AttrCollectionType*/int type)
    {
        switch (type)
        {
            case AttrCollectionType.RUN_PR:
                return getRunPr();
            case AttrCollectionType.PARA_PR:
                return getParaPr();
            case AttrCollectionType.CELL_PR:
                return getCellPr();
            default:
                throw new IllegalStateException("Unexpected AttrCollectionType value.");
        }
    }

    private TablePr mTablePr = new TablePr();
    private TablePr mRowPr = new TablePr();
    private CellPr mCellPr = new CellPr();

    private BorderCollection mBorders;
    private ConditionalStyleCollection mConditionalStyles;

    /// <summary>
    /// Specifies order for attribute fetching.
    /// </summary>
    private static /*final*/ /*TableStyleOverrideType*/int[] G_FETCH_ORDER = new /*TableStyleOverrideType*/int[]
                                                         {
                                                             TableStyleOverrideType.BOTTOM_RIGHT_CELL,
                                                             TableStyleOverrideType.BOTTOM_LEFT_CELL,
                                                             TableStyleOverrideType.TOP_RIGHT_CELL,
                                                             TableStyleOverrideType.TOP_LEFT_CELL,
                                                             TableStyleOverrideType.LAST_ROW,
                                                             TableStyleOverrideType.FIRST_ROW,
                                                             TableStyleOverrideType.LAST_COLUMN,
                                                             TableStyleOverrideType.FIRST_COLUMN,
                                                             TableStyleOverrideType.ODD_ROW_BANDING,
                                                             TableStyleOverrideType.EVEN_ROW_BANDING,
                                                             TableStyleOverrideType.ODD_COLUMN_BANDING,
                                                             TableStyleOverrideType.EVEN_COLUMN_BANDING
                                                         };

    private static /*final*/ /*TableStyleOverrideType*/int[] G_FETCH_ORDER_DESC = new /*TableStyleOverrideType*/int[]
    {
        TableStyleOverrideType.EVEN_COLUMN_BANDING,
        TableStyleOverrideType.ODD_COLUMN_BANDING,
        TableStyleOverrideType.EVEN_ROW_BANDING,
        TableStyleOverrideType.ODD_ROW_BANDING,
        TableStyleOverrideType.FIRST_COLUMN,
        TableStyleOverrideType.LAST_COLUMN,
        TableStyleOverrideType.FIRST_ROW,
        TableStyleOverrideType.LAST_ROW,
        TableStyleOverrideType.TOP_LEFT_CELL,
        TableStyleOverrideType.TOP_RIGHT_CELL,
        TableStyleOverrideType.BOTTOM_LEFT_CELL,
        TableStyleOverrideType.BOTTOM_RIGHT_CELL,
    };
}

