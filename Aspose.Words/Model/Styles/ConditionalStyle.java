// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.Collections.SortedList;
import com.aspose.ConvertUtilCore;
import com.aspose.Debug;


/// <summary>
/// Represents special formatting applied to some area of a table with assigned table style.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
public final class ConditionalStyle implements IBorderAttrSource, IShadingAttrSource, IParaAttrSource, IRunAttrSource, Cloneable
{
    ConditionalStyle(/*TableStyleOverrideType*/int overrideType)
    {
    	this (overrideType, null);
	
    }

    ConditionalStyle(/*TableStyleOverrideType*/int overrideType, TableStyle parentStyle)
    {
        mOverrideType = overrideType;
        mParentStyle = parentStyle;
    }

    /// <summary>
    /// Clears formatting of this conditional style.
    /// </summary>
    public void clearFormatting()
    {
        if (mParaPr != null)
            mParaPr.clear();
        if (mRunPr != null)
            mRunPr.clear();
        if (mTablePr != null)
            mTablePr.clear();
        if (mRowPr != null)
            mRowPr.clear();
        if (mCellPr != null)
            mCellPr.clear();
    }

    /// <summary>
    /// Makes a deep copy of the object.
    /// </summary>
    ConditionalStyle deepClone()
    {
        ConditionalStyle lhs = (ConditionalStyle)memberwiseClone();

        if (mParaPr != null)
            lhs.mParaPr = AttrCollectionExtension.deepClone(mParaPr);

        if (mRunPr != null)
            lhs.mRunPr = AttrCollectionExtension.deepClone(mRunPr);

        if (mTablePr != null)
            lhs.mTablePr = AttrCollectionExtension.deepClone(mTablePr);

        if (mRowPr != null)
            lhs.mRowPr = AttrCollectionExtension.deepClone(mRowPr);

        if (mCellPr != null)
            lhs.mCellPr = AttrCollectionExtension.deepClone(mCellPr);

        return lhs;
    }

    /// <summary>
    /// Compares this conditional style with the specified object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (ConditionalStyle.class != obj.getClass())
            return false;

        return equals((ConditionalStyle)obj);
    }

    /// <summary>
    /// Compares this conditional style with the specified one.
    /// </summary>
    boolean equals(ConditionalStyle style)
    {
        if (style == null)
            return false;

        return
            (getOverrideType() == style.getOverrideType()) &&
            (getRunPr().equals(style.getRunPr(), Style.COMPARISON_IGNORABLE_KEYS)) &&
            (getParaPr().equals(style.getParaPr(), Style.COMPARISON_IGNORABLE_KEYS)) &&
            (getCellPr().equals(style.getCellPr(), Style.COMPARISON_IGNORABLE_KEYS)) &&
            (getRowPr().equals(style.getRowPr(), Style.COMPARISON_IGNORABLE_KEYS)) &&
            (getTablePr().equals(style.getTablePr(), Style.COMPARISON_IGNORABLE_KEYS));
    }

    /// <summary>
    /// Calculates hash code for this object.
    /// </summary>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        int hashCode = /*enum.hashCode()=(int)value*/getOverrideType();
        hashCode = (hashCode * 397) ^ (hasRunFormatting() ? mRunPr.hashCode(Style.COMPARISON_IGNORABLE_KEYS) : 0);
        hashCode = (hashCode * 397) ^ (hasParagraphFormatting() ? mParaPr.hashCode(Style.COMPARISON_IGNORABLE_KEYS) : 0);
        hashCode = (hashCode * 397) ^ (hasCellFormatting() ? mCellPr.hashCode(Style.COMPARISON_IGNORABLE_KEYS) : 0);
        hashCode = (hashCode * 397) ^ (hasRowFormatting() ? mRowPr.hashCode(Style.COMPARISON_IGNORABLE_KEYS) : 0);
        hashCode = (hashCode * 397) ^ (hasTableFormatting() ? mTablePr.hashCode(Style.COMPARISON_IGNORABLE_KEYS) : 0);
        return hashCode;
    }

    /// <summary>
    /// Sets parent table style for this conditional style.
    /// </summary>
    void setParentStyle(TableStyle value)
    {
        mParentStyle = value;
    }

    //>>>>>>>> #region  IBorderAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return (mCellPr != null) ? mCellPr.get(key) : null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        Object value = getInheritedConditionalStyleAttr(AttrCollectionType.CELL_PR, key);
        if (value != null)
            return value;

        // Not taken from table borders like in VBA.
        return CellPr.fetchDefaultAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        getCellPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return CellPr.POSSIBLE_BORDER_KEYS; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IShadingAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IShadingAttrSource.*/fetchInheritedShadingAttr(int key)
    {
        return fetchInheritedCellAttr(key);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IParaAttrSource

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/setParaAttr(int key, Object value)
    {
        getParaPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/removeParaAttr(int key)
    {
        if (mParaPr != null)
            mParaPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IParaAttrSource.*/clearParaAttrs()
    {
        if (mParaPr != null)
            mParaPr.clear();
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchInheritedParaAttr(int key)
    {
        Object value = getInheritedConditionalStyleAttr(AttrCollectionType.PARA_PR, key);
        if (value != null)
            return value;

        value = mParentStyle.getParaAttr(key, RevisionsView.ORIGINAL);
        if (value != null)
            return value;

        return mParentStyle.getStyles().getDefaultParaPr().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key)
    {
        return (mParaPr != null) ? mParaPr.getDirectAttr(key) : null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/getDirectParaAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return (mParaPr != null) ? mParaPr.getDirectAttr(key, revisionsView) : null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IParaAttrSource.*/fetchParaAttr(int key)
    {
        Object value = (mParaPr != null) ? mParaPr.get(key) : null;
        return (value != null) ? value : ((IParaAttrSource)this).fetchInheritedParaAttr(key);
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return ((IRunAttrSource)this).getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        return (mRunPr != null) ? mRunPr.getDirectAttr(key, revisionsView) : null;
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        Object value = getInheritedConditionalStyleAttr(AttrCollectionType.RUN_PR, key);
        return (value != null) ? value : mParentStyle.getFontAttr(key, true);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        getRunPr().setAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        if (mRunPr != null)
            mRunPr.remove(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        if (mRunPr != null)
            mRunPr.clear();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Fetches inherited attribute of the conditional style from the specified attribute collection.
    /// Only base conditional styles are searched for.
    /// </summary>
    private Object getInheritedConditionalStyleAttr(/*AttrCollectionType*/int type, int key)
    {
        TableStyle style = mParentStyle;

        while (true)
        {
            style = ms.as(style.getBaseStyle(), TableStyle.class);
            if (style == null)
                break;

            if (!style.getConditionalStyles().containsTableStyleOverride(mOverrideType))
                continue;

            AttrCollection collection = style.getConditionalStyles().getByTableStyleOverrideType(mOverrideType).getCollection(type);
            if (collection == null)
                continue;

            Object value = collection.get(key);
            if (value != null)
                return value;
        }

        return null;
    }

    /// <summary>
    /// Fetches inherited cell attribute from the specified attribute collection.
    /// </summary>
    /// <remarks>
    /// At first the attribute is searched in base conditional styles, then in table styles (in parent and base
    /// styles). If not found, a default value is returned.
    /// </remarks>
    private Object fetchInheritedCellAttr(int key)
    {
        Object value = getInheritedConditionalStyleAttr(AttrCollectionType.CELL_PR, key);
        return (value != null) ? value : ((ICellAttrSource)mParentStyle).fetchCellAttr(key);
    }

    /// <summary>
    /// Gets a direct cell attribute, or inherited if not defined.
    /// </summary>
    private Object getCellAttr(int key)
    {
        if (mCellPr != null)
        {
            Object value = mCellPr.get(key);
            if (value != null)
                return value;
        }

        return fetchInheritedCellAttr(key);
    }

    /// <summary>
    /// Gets the paragraph formatting of the conditional style.
    /// </summary>
    public ParagraphFormat getParagraphFormat()
    {
        if (mParagraphFormatCache == null)
            mParagraphFormatCache = new ParagraphFormat(this, mParentStyle.getStyles());
        return mParagraphFormatCache;
    }

    /// <summary>
    /// Gets the character formatting of the conditional style.
    /// </summary>
    public Font getFont()
    {
        if (mFontCache == null)
            mFontCache = new Font(this, mParentStyle.getDocument());
        return mFontCache;
    }

    /// <summary>
    /// Gets a <see cref="Words.Shading"/> object that refers to the shading formatting for this conditional style.
    /// </summary>
    public Shading getShading()
    {
        Shading shading = (Shading)getCellPr().get(CellAttr.SHADING);
        if (shading == null)
        {
            ConditionalStyle parent = this;
            shading = new Shading(parent, CellAttr.SHADING);
            shading = shading;
            getCellPr().setAttr(CellAttr.SHADING, shading);
        }
        return shading;
    }

    /// <summary>
    /// Gets the collection of default cell borders for the conditional style.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBordersCache == null)
            mBordersCache = new BorderCollection(this);
        return mBordersCache;
    }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the left of the contents of table cells.
    /// </summary>
    public double getLeftPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)getCellAttr(CellAttr.LEFT_PADDING)); }
    public void setLeftPadding(double value) { getCellPr().setAttr(CellAttr.LEFT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add to the right of the contents of table cells.
    /// </summary>
    public double getRightPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)getCellAttr(CellAttr.RIGHT_PADDING)); }
    public void setRightPadding(double value) { getCellPr().setAttr(CellAttr.RIGHT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add above the contents of table cells.
    /// </summary>
    public double getTopPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)getCellAttr(CellAttr.TOP_PADDING)); }
    public void setTopPadding(double value) { getCellPr().setAttr(CellAttr.TOP_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets or sets the amount of space (in points) to add below the contents of table cells.
    /// </summary>
    public double getBottomPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)getCellAttr(CellAttr.BOTTOM_PADDING)); }
    public void setBottomPadding(double value) { getCellPr().setAttr(CellAttr.BOTTOM_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets table area to which this conditional style relates.
    /// </summary>
    public /*ConditionalStyleType*/int getType()
    {
        switch (mOverrideType)
        {
            case TableStyleOverrideType.FIRST_ROW:
                return ConditionalStyleType.FIRST_ROW;
            case TableStyleOverrideType.FIRST_COLUMN:
                return ConditionalStyleType.FIRST_COLUMN;
            case TableStyleOverrideType.LAST_ROW:
                return ConditionalStyleType.LAST_ROW;
            case TableStyleOverrideType.LAST_COLUMN:
                return ConditionalStyleType.LAST_COLUMN;
            case TableStyleOverrideType.ODD_ROW_BANDING:
                return ConditionalStyleType.ODD_ROW_BANDING;
            case TableStyleOverrideType.ODD_COLUMN_BANDING:
                return ConditionalStyleType.ODD_COLUMN_BANDING;
            case TableStyleOverrideType.EVEN_ROW_BANDING:
                return ConditionalStyleType.EVEN_ROW_BANDING;
            case TableStyleOverrideType.EVEN_COLUMN_BANDING:
                return ConditionalStyleType.EVEN_COLUMN_BANDING;
            case TableStyleOverrideType.TOP_LEFT_CELL:
                return ConditionalStyleType.TOP_LEFT_CELL;
            case TableStyleOverrideType.TOP_RIGHT_CELL:
                return ConditionalStyleType.TOP_RIGHT_CELL;
            case TableStyleOverrideType.BOTTOM_LEFT_CELL:
                return ConditionalStyleType.BOTTOM_LEFT_CELL;
            case TableStyleOverrideType.BOTTOM_RIGHT_CELL:
                return ConditionalStyleType.BOTTOM_RIGHT_CELL;
            case TableStyleOverrideType.WHOLE_TABLE:
            case TableStyleOverrideType.NONE:
            default:
            {
                // WholeTable and None conditional styles are for internal usage and are not be visible for
                // customers.
                // 'Word does not apply and discards on save any properties within the tblStylePr element when
                // the "type" attribute has a value of "wholeTable"' [MS-OI29500].
                Debug.assertTrue(false);
                return ConditionalStyleType.FIRST_ROW;
            }
        }
    }

    /// <summary>
    /// Gets table area to which this conditional style relates.
    /// </summary>
    /*TableStyleOverrideType*/int getOverrideType() { return mOverrideType; }

    /// <summary>
    /// Paragraph properties of the conditional style.
    /// </summary>
    ParaPr getParaPr()
    {
        if (mParaPr == null)
            mParaPr = new ParaPr();
        return mParaPr;
    }
    void setParaPr(ParaPr value) { mParaPr = value; }

    /// <summary>
    /// Returns <c>true</c> if this conditional style has any paragraph formatting defined.
    /// </summary>
    boolean hasParagraphFormatting()
    {
        return (mParaPr != null) && (mParaPr.getCount() > 0);
    }

    /// <summary>
    /// Run properties of the conditional style.
    /// </summary>
    RunPr getRunPr()
    {
        if (mRunPr == null)
            mRunPr = new RunPr();
        return mRunPr;
    }
    void setRunPr(RunPr value) { mRunPr = value; }

    /// <summary>
    /// Returns <c>true</c> if this conditional style has any run formatting defined.
    /// </summary>
    boolean hasRunFormatting()
    {
        return (mRunPr != null) && (mRunPr.getCount() > 0);
    }

    /// <summary>
    /// Table properties of the conditional style.
    /// </summary>
    TablePr getTablePr()
    {
        if (mTablePr == null)
            mTablePr = new TablePr();
        return mTablePr;
    }
    void setTablePr(TablePr value) { mTablePr = value; }

    /// <summary>
    /// Returns <c>true</c> if this conditional style has any table formatting defined.
    /// </summary>
    boolean hasTableFormatting()
    {
        return (mTablePr != null) && (mTablePr.getCount() > 0);
    }

    /// <summary>
    /// Row properties of the conditional style.
    /// </summary>
    TablePr getRowPr()
    {
        if (mRowPr == null)
            mRowPr = new TablePr();
        return mRowPr;
    }
    void setRowPr(TablePr value) { mRowPr = value; }

    /// <summary>
    /// Returns <c>true</c> if this conditional style has any row formatting defined.
    /// </summary>
    boolean hasRowFormatting()
    {
        return (mRowPr != null) && (mRowPr.getCount() > 0);
    }

    /// <summary>
    /// Cell properties of the conditional style.
    /// </summary>
    CellPr getCellPr()
    {
        if (mCellPr == null)
            mCellPr = new CellPr();
        return mCellPr;
    }
    void setCellPr(CellPr value) { mCellPr = value; }

    /// <summary>
    /// Returns <c>true</c> if this conditional style has any cell formatting defined.
    /// </summary>
    boolean hasCellFormatting()
    {
        return (mCellPr != null) && (mCellPr.getCount() > 0);
    }

    /// <summary>
    /// Returns <c>true</c> if there is at least one formatting attribute specified in this conditional style.
    /// </summary>
    boolean hasFormatting()
    {
        return
            hasTableFormatting() ||
            hasRowFormatting() ||
            hasCellFormatting() ||
            hasParagraphFormatting() ||
            hasRunFormatting();
    }

    /// <summary>
    /// Returns property collection of given type.
    /// </summary>
    WordAttrCollection getCollection(/*AttrCollectionType*/int type)
    {
        switch (type)
        {
            case AttrCollectionType.RUN_PR:
                return mRunPr;
            case AttrCollectionType.PARA_PR:
                return mParaPr;
            case AttrCollectionType.CELL_PR:
                return mCellPr;

            default:
                throw new IllegalStateException("Unexpected AttrCollectionType value.");
        }
    }

    private /*final*/ /*TableStyleOverrideType*/int mOverrideType;
    private ParaPr mParaPr;
    private RunPr mRunPr;
    private TablePr mTablePr;
    private TablePr mRowPr;
    private CellPr mCellPr;
    private TableStyle mParentStyle;

    private ParagraphFormat mParagraphFormatCache;
    private Font mFontCache;
    private BorderCollection mBordersCache;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

