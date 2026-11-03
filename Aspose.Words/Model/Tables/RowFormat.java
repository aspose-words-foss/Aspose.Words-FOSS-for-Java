// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Represents all formatting for a table row.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <dev>
/// RK I don't expose Shading here because default shading in MS Word works very strange,
/// cells do not seem to inherit the shading specified for the table so it will be pretty
/// confusing to work with. Setting row shading should actually go and set shading for
/// all of the cells in the row.
/// </dev>
public class RowFormat implements IBorderAttrSource
{
    RowFormat(IRowAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Resets to default row formatting.
    /// </summary>
    public void clearFormatting() throws Exception
    {
        mParent.resetToDefaultAttrs();
    }

    /// <summary>
    /// Gets the collection of default cell borders for the row.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBorders == null)
            mBorders = new BorderCollection(this);
        return mBorders;
    }

    /// <summary>
    /// Gets or sets the height of the table row in points.
    /// </summary>
    public double getHeight()
    {
        Height height = (Height)mParent.fetchRowAttr(TableAttr.ROW_HEIGHT);
        return ConvertUtilCore.twipToPoint(height.getValue());
    }
    public void setHeight(double value)
    {
        Height height = (Height)fetchOrCreateComplexRowAttr(TableAttr.ROW_HEIGHT);
        height.setValue(ConvertUtilCore.pointToTwip(value));
    }

    /// <summary>
    /// Gets or sets the rule for determining the height of the table row.
    /// </summary>
    public /*HeightRule*/int getHeightRule()
    {
        Height height = (Height)mParent.fetchRowAttr(TableAttr.ROW_HEIGHT);
        return height.getRule();
    }
    public void setHeightRule(/*HeightRule*/int value)
    {
        Height height = (Height)fetchOrCreateComplexRowAttr(TableAttr.ROW_HEIGHT);
        height.setRule(value);
    }

    /// <summary>
    /// True if the text in a table row is allowed to split across a page break.
    /// </summary>
    public boolean getAllowBreakAcrossPages() { return (/*boolean*/Boolean)fetchAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES); }
    public void setAllowBreakAcrossPages(boolean value) { mParent.setRowAttr(TableAttr.ALLOW_BREAK_ACROSS_PAGES, value); }

    /// <summary>
    /// True if the row is repeated as a table heading on every page when the table spans more than one page.
    /// </summary>
    public boolean getHeadingFormat() { return (/*boolean*/Boolean)fetchAttr(TableAttr.HEADING_FORMAT); }
    public void setHeadingFormat(boolean value) { mParent.setRowAttr(TableAttr.HEADING_FORMAT, value); }

    private Object fetchAttr(int key)
    {
        return mParent.fetchRowAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectRowAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedRowAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mParent.setRowAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return TablePr.POSSIBLE_BORDER_KEYS; }

    private Object fetchOrCreateComplexRowAttr(int key)
    {
        Object directValue = mParent.getDirectRowAttr(key);

        if (directValue != null)
            return directValue;

        Object defaultValue = TablePr.fetchDefaultAttr(key);

        Object clonedValue = ((IComplexAttr)defaultValue).deepCloneComplexAttr();
        mParent.setRowAttr(key, clonedValue);

        return clonedValue;
    }

    private /*final*/ IRowAttrSource mParent;
    private BorderCollection mBorders;
}

