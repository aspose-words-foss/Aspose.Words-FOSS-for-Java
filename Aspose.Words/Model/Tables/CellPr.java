// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.DateTime;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Collection of cell attributes. Provides typed access to the values stored in the collection.
/// </summary>
class CellPr extends WordAttrCollection implements ICellAttrSource
{
    /// <summary>
    /// Removes all borders.
    /// </summary>
    void removeBorders()
    {
        for (int key : POSSIBLE_BORDER_KEYS.getValues())
            remove(key);
    }

    /// <summary>
    /// Copies all borders to the specified CellPr collection.
    /// If there is no border in this collection,
    /// then removes it from the destination collection too.
    /// </summary>
    void copyBordersTo(CellPr dstCellPr)
    {
        for (int key : POSSIBLE_BORDER_KEYS.getValues())
        {
            if (contains(key))
            {
                Border border = new Border();
                border.copyFrom((Border)this.get(key));
                dstCellPr.set(key, border);
            }
            else
            {
                dstCellPr.remove(key);
            }
        }
    }
    
    void removeShading()
    {
        remove(CellAttr.SHADING);
    }

    /// <summary>
    /// Makes preferred width and width valid.
    /// Returns true if the width was modified by this method.
    /// </summary>
    boolean validateWidth()
    {
        PreferredWidth prefWidth = (PreferredWidth)getDirectAttr(CellAttr.PREFERRED_WIDTH); 

        // Validate width. If the cell is not merged to previous, it is invalid for it to have zero width.
        // It is also invalid to have a negative width. Correct this situation by storing preferred width 
        // or the default cell width as the cell width.
        if ((getWidth() <= 0) && (!isMergedToPrevious()))
        {
            if ((prefWidth != null) && prefWidth.isFixed() && prefWidth.isPositive())
            {
                setWidth(prefWidth.getValueTwips());
            }
            else
            {
                // RK We have to set non-zero cell width for a document to be valid.
                setWidth(DEFAULT_CELL_WIDTH_TWIPS);
                // Dmatv: There are some test documents indicating that MS Word does not set the above default width in such cases.
                // I've made some experiments with the document from TestDefect14170, trying to use minimum cell widths.
                // I was not able to imitate the layout MS Word shows on opening the document.
                // It appears that MS Word actually constructs the grid using a negative cell width,
                // so that the cell with the negative width becomes completely invisible, and the cells after it
                // appear shifted left for because of the negative width before them.
                // I believe AW should not try to imitate that behavior.
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /// <summary>
    /// RK This method is useful when reading DOCX and WML. In these documents we read preferred width first.
    /// Then we read table grid and set width from table grid. But table grid is not always in sync.
    /// So we improve our chances of getting the correct width by setting width from the preferred width (when it is fixed).
    /// It seems that the value in the preferred width is correct more often than in the table grid.
    /// </summary>
    void setPreferredWidthAndWidth(PreferredWidth preferredWidth)
    {
        this.setPreferredWidth(preferredWidth);
        if (preferredWidth.isFixed())
            this.setWidth(preferredWidth.getValueTwips());
    }

    /// <summary>
    /// Sets the specified value in the desired collection depending on RevisionsView parameter, meanwhile the old value is being reserved in RevPr.
    /// </summary>
    /// <remarks>
    /// <para>
    /// This method is currently used exclusively for setting a vertical merge value depending on the revision view.
    /// The second part of the method, when RevisionsView is equal to Final, is currently not being tested and is not being used anywhere in the code.
    /// </para>
    /// <para>
    /// AM. I don't fully understand how to set final properties. 
    /// Should we make new format revision if it absent? Where we get format revision author/date then?
    /// Or should we just set original property in this case? 
    /// That's why not going to put this into WordAttrCollection for a while.
    /// </para>
    /// </remarks>
    void setAttrRevisionAware(int key, Object value, /*RevisionsView*/int view)
    {
        if (view == RevisionsView.ORIGINAL)
        {
            if (getFormatRevision() != null)
            {
                if (!getFormatRevision().getRevPr().contains(key))
                {
                    // first we need to preserve attribute in final collection.
                    moveTo(getFormatRevision().getRevPr(), key);
                }
            }

            setAttr(key, value);
        }
        else
        {
            // WORDSNET-21986 Do not create format revision if new value equals to original.
            Object originalValue = fetchAttr(key, view);
            if (msObject.equals(originalValue, value))
                return;

            if (getFormatRevision() == null)
                setFormatRevision(new FormatRevision(new CellPr(), "", DateTime.getNow()));

            getFormatRevision().getRevPr().setAttr(key, value);
        }
    }

    int getWidth() { return (/*int*/Integer)fetchAttr(CellAttr.WIDTH); }
    void setWidth(int value) { setAttr(CellAttr.WIDTH, value); }

    PreferredWidth getPreferredWidth() { return (PreferredWidth)fetchAttr(CellAttr.PREFERRED_WIDTH); }
    void setPreferredWidth(PreferredWidth value) { setAttr(CellAttr.PREFERRED_WIDTH, value); }

    /*CellMerge*/int getHorizontalMerge() { return (/*CellMerge*/Integer)fetchAttr(CellAttr.HORIZONTAL_MERGE); }
    void setHorizontalMerge(/*CellMerge*/int value) { setAttr(CellAttr.HORIZONTAL_MERGE, value); }

    /*CellMerge*/int getVerticalMerge() { return (/*CellMerge*/Integer)fetchAttr(CellAttr.VERTICAL_MERGE); }
    void setVerticalMerge(/*CellMerge*/int value) { setAttr(CellAttr.VERTICAL_MERGE, value); }

    boolean isMergedToPrevious() { return ((getHorizontalMerge() == CellMerge.PREVIOUS) || (getVerticalMerge() == CellMerge.PREVIOUS)); }

    /*TextOrientation*/int getOrientation() { return (/*TextOrientation*/Integer)fetchAttr(CellAttr.ORIENTATION); }

    /*CellVerticalAlignment*/int getVerticalAlignment() { return (/*CellVerticalAlignment*/Integer)fetchAttr(CellAttr.VERTICAL_ALIGNMENT); }
    void setVerticalAlignment(/*CellVerticalAlignment*/int value) { setAttr(CellAttr.VERTICAL_ALIGNMENT, value); }

    boolean getWrapText() { return (/*boolean*/Boolean)fetchAttr(CellAttr.WRAP_TEXT); }

    boolean getHideMark() { return (/*boolean*/Boolean) fetchAttr(CellAttr.HIDE_MARK); }
    void setHideMark(boolean value) { setAttr(CellAttr.HIDE_MARK, value); }

    boolean getFitText() { return (/*boolean*/Boolean)fetchAttr(CellAttr.FIT_TEXT); }

    int getTopPadding() { return (/*int*/Integer)fetchAttr(CellAttr.TOP_PADDING); }
    void setTopPadding(int value) { setAttr(CellAttr.TOP_PADDING, value); }

    int getBottomPadding() { return (/*int*/Integer)fetchAttr(CellAttr.BOTTOM_PADDING); }
    void setBottomPadding(int value) { setAttr(CellAttr.BOTTOM_PADDING, value); }

    int getLeftPadding() { return (/*int*/Integer)fetchAttr(CellAttr.LEFT_PADDING); }
    void setLeftPadding(int value) { setAttr(CellAttr.LEFT_PADDING, value); }

    int getRightPadding() { return (/*int*/Integer)fetchAttr(CellAttr.RIGHT_PADDING); }
    void setRightPadding(int value) { setAttr(CellAttr.RIGHT_PADDING, value); }

    Border getBorderTop() { return (Border)getDirectAttr(CellAttr.BORDER_TOP); }
    void setBorderTop(Border value) { setAttr(CellAttr.BORDER_TOP, value); }

    Border getBorderBottom() { return (Border)getDirectAttr(CellAttr.BORDER_BOTTOM); }
    void setBorderBottom(Border value) { setAttr(CellAttr.BORDER_BOTTOM, value); }

    Border getBorderLeft() { return (Border)getDirectAttr(CellAttr.BORDER_LEFT); }
    void setBorderLeft(Border value) { setAttr(CellAttr.BORDER_LEFT, value); }

    Border getBorderRight() { return (Border)getDirectAttr(CellAttr.BORDER_RIGHT); }
    void setBorderRight(Border value) { setAttr(CellAttr.BORDER_RIGHT, value); }

    Border getBorderDiagonalDown() { return (Border)getDirectAttr(CellAttr.BORDER_DIAGONAL_DOWN); }
    void setBorderDiagonalDown(Border value) { setAttr(CellAttr.BORDER_DIAGONAL_DOWN, value); }

    Border getBorderDiagonalUp() { return (Border)getDirectAttr(CellAttr.BORDER_DIAGONAL_UP); }
    void setBorderDiagonalUp(Border value) { setAttr(CellAttr.BORDER_DIAGONAL_UP, value); }

    Border getBorderHorizontal() { return (Border)getDirectAttr(CellAttr.BORDER_HORIZONTAL); }
    void setBorderHorizontal(Border value) { setAttr(CellAttr.BORDER_HORIZONTAL, value); }

    Border getBorderVertical() { return (Border)getDirectAttr(CellAttr.BORDER_VERTICAL); }
    void setBorderVertical(Border value) { setAttr(CellAttr.BORDER_VERTICAL, value); }

    Shading getShading() { return (Shading)getDirectAttr(CellAttr.SHADING); }
    void setShading(Shading value) { setAttr(CellAttr.SHADING, value); }

    /// <summary>
    /// Returns either revised or original properties depending on CellPr has format revision.
    /// Note that revised properties is NOT accepted properties and needs to be accepted to get full final property set.
    /// </summary>
    CellPr getRevisedPr() { return hasFormatRevision() ? (CellPr)getFormatRevision().getRevPr() : this; }

    /// <summary>
    /// Returns final cell properties. If there is a formatting revision applied to the cell, accepted cell properties
    /// are returned. Otherwise, this cell properties are returned as is.
    /// </summary>
    CellPr getFinalPr()
    {
        if (!hasFormatRevision())
        {
            return this;
        }

        CellPr finalPr = AttrCollectionExtension.deepClone(this);
        finalPr.acceptFormatRevision();
        return finalPr;
    }

    int getGridSpan()
    {
        // DM originally I treated 0 as invalid value of the attribute.
        // However when working on adjacent table merge it turned out that in doc/rtf merged cells can have zero width,
        // and the whole width of the merged cell is allocated to the first cell in the merge.
        // So that cell gets the whole grid span, and the rest of the cells have grid span 0.
        // I tried removing such cells from the model right away, but eventually decided that
        // it is better to do it as a separate change later. 
        // It affects about ten tests that check the model.

        return (/*int*/Integer)fetchAttr(CellAttr.SYS_CELL_SPAN);
        // FetchAttr() will return the default, that is, 1, if the attribute is not specified.
    }
    void setGridSpan(int value)
    {
        Debug.assertTrue(value >= 0);
        setAttr(CellAttr.SYS_CELL_SPAN, value);
    }

    protected /*override*/ AttrCollection getDefaults()
    {
        return G_DEFAULTS;
    }

    static Object fetchDefaultAttr(int key)
    {
        return G_DEFAULTS.fetchAttr(key);
    }

    //>>>>>>>> #region  ICellAttrSource

    public Object /*ICellAttrSource.*/getDirectCellAttr(int key)
    {
        return super.getDirectAttr(key);
    }

    public Object /*ICellAttrSource.*/fetchCellAttr(int key)
    {
        return super.fetchAttr(key);
    }

    public Object /*ICellAttrSource.*/fetchInheritedCellAttr(int key)
    {
        return super.fetchInheritedAttr(key);
    }

    public void /*ICellAttrSource.*/setCellAttr(int key, Object value)
    {
        super.setAttr(key, value);
    }

    public void /*ICellAttrSource.*/clearCellAttrs()
    {
        super.clear();
    }

    private static /*final*/ CellPr G_DEFAULTS;

    /// <summary>
    /// The key is <see cref="BorderType"/> that represents a border that can occur in a <see cref="TablePr"/> collection.
    /// The value is an <see cref="TableAttr"/> integer that represents the attribute key of that border.
    /// </summary>
    static /*final*/ SortedList<Integer, Integer> POSSIBLE_BORDER_KEYS = 
        new SortedList<Integer, Integer>();

    /// <summary>
    /// A default cell width to be used in case of zero cell width.
    /// </summary>
    /// <remarks>
    /// Zero cell width is invalid.
    /// </remarks>
    static final int DEFAULT_CELL_WIDTH_TWIPS = 72 * 20;    //1"

    /// <summary>
    /// All possible cell border keys.
    /// </summary>
    static /*final*/ int[] ALL_BORDER_KEYS = new int[]
    {
        CellAttr.BORDER_LEFT,
        CellAttr.BORDER_RIGHT,
        CellAttr.BORDER_TOP,
        CellAttr.BORDER_BOTTOM,
        CellAttr.BORDER_VERTICAL,
        CellAttr.BORDER_HORIZONTAL,
        CellAttr.BORDER_DIAGONAL_DOWN,
        CellAttr.BORDER_DIAGONAL_UP
    };

    //<<<<<<<< #endregion 

    static/* CellPr()*/
    {
        G_DEFAULTS = new CellPr();

        G_DEFAULTS.add(CellAttr.WIDTH, 0);
        G_DEFAULTS.add(CellAttr.PREFERRED_WIDTH, PreferredWidth.AUTO);
        G_DEFAULTS.add(CellAttr.VERTICAL_MERGE, CellMerge.NONE);
        G_DEFAULTS.add(CellAttr.HORIZONTAL_MERGE, CellMerge.NONE);
        G_DEFAULTS.add(CellAttr.ORIENTATION, TextOrientation.HORIZONTAL);
        G_DEFAULTS.add(CellAttr.VERTICAL_ALIGNMENT, CellVerticalAlignment.TOP);
        G_DEFAULTS.add(CellAttr.TOP_PADDING, 0);
        G_DEFAULTS.add(CellAttr.BOTTOM_PADDING, 0);
        G_DEFAULTS.add(CellAttr.LEFT_PADDING, 0);
        G_DEFAULTS.add(CellAttr.RIGHT_PADDING, 0);
        G_DEFAULTS.add(CellAttr.WRAP_TEXT, true);
        G_DEFAULTS.add(CellAttr.HIDE_MARK, false);
        G_DEFAULTS.add(CellAttr.FIT_TEXT, false);

        // I'm of two minds whether cell borders and shading should have defaults.
        // On one hand, they should not because they must be resolved to the table defaults,
        // but on the other hand resolving to table defaults is quite hard, so I have these here for now.
        // These are complex attributes. Be careful not to modify the actual values in them.
        G_DEFAULTS.add(CellAttr.BORDER_TOP, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_LEFT, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_BOTTOM, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_RIGHT, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_DIAGONAL_DOWN, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_DIAGONAL_UP, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_HORIZONTAL, new Border());
        G_DEFAULTS.add(CellAttr.BORDER_VERTICAL, new Border());

        G_DEFAULTS.add(CellAttr.SHADING, new Shading());

        G_DEFAULTS.add(CellAttr.SYS_CELL_SPAN, 1);

        POSSIBLE_BORDER_KEYS.add(BorderType.TOP, CellAttr.BORDER_TOP);
        POSSIBLE_BORDER_KEYS.add(BorderType.LEFT, CellAttr.BORDER_LEFT);
        POSSIBLE_BORDER_KEYS.add(BorderType.BOTTOM, CellAttr.BORDER_BOTTOM);
        POSSIBLE_BORDER_KEYS.add(BorderType.RIGHT, CellAttr.BORDER_RIGHT);
        POSSIBLE_BORDER_KEYS.add(BorderType.DIAGONAL_DOWN, CellAttr.BORDER_DIAGONAL_DOWN);
        POSSIBLE_BORDER_KEYS.add(BorderType.DIAGONAL_UP, CellAttr.BORDER_DIAGONAL_UP);
    }
}

