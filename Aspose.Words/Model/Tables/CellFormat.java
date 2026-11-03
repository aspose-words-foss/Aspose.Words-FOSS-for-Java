// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/05/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Represents all formatting for a table cell.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
public class CellFormat implements IBorderAttrSource, IShadingAttrSource
{
    CellFormat(ICellAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Resets to default cell formatting. Does not change the width of the cell.
    /// </summary>
    public void clearFormatting()
    {
        // WORDSNET-1277 Clearing cell format should not really change the cell width.
        Object savedWidth = mParent.getDirectCellAttr(CellAttr.WIDTH);
        Object savedPreferredWidth = mParent.getDirectCellAttr(CellAttr.PREFERRED_WIDTH);

        mParent.clearCellAttrs();

        if (savedWidth != null)
            mParent.setCellAttr(CellAttr.WIDTH, savedWidth);
        if (savedPreferredWidth != null)
            mParent.setCellAttr(CellAttr.PREFERRED_WIDTH, savedPreferredWidth);
    }

    /// <summary>
    /// Resets to default cell formatting. Note, this includes clearing cell width too.
    /// </summary>
    void clearAllFormatting()
    {
        mParent.clearCellAttrs();
    }

    /// <summary>
    /// Sets the amount of space (in points) to add to the left/top/right/bottom of the contents of cell.
    /// </summary>
    public void setPaddings(double leftPadding, double topPadding, double rightPadding, double bottomPadding)
    {
        setLeftPadding(leftPadding);
        setTopPadding(topPadding);
        setRightPadding(rightPadding);
        setBottomPadding(bottomPadding);
    }

    /// <summary>
    /// Returns or sets the amount of space (in points) to add to the left of the contents of cell.
    /// </summary>
    public double getLeftPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(CellAttr.LEFT_PADDING)); }
    public void setLeftPadding(double value) { mParent.setCellAttr(CellAttr.LEFT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the amount of space (in points) to add to the right of the contents of cell.
    /// </summary>
    public double getRightPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(CellAttr.RIGHT_PADDING)); }
    public void setRightPadding(double value) { mParent.setCellAttr(CellAttr.RIGHT_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the amount of space (in points) to add above the contents of cell.
    /// </summary>
    public double getTopPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(CellAttr.TOP_PADDING)); }
    public void setTopPadding(double value) { mParent.setCellAttr(CellAttr.TOP_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Returns or sets the amount of space (in points) to add below the contents of cell.
    /// </summary>
    public double getBottomPadding() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(CellAttr.BOTTOM_PADDING)); }
    public void setBottomPadding(double value) { mParent.setCellAttr(CellAttr.BOTTOM_PADDING, ConvertUtilCore.pointToTwip(value)); }

    /// <summary>
    /// Gets collection of borders of the cell.
    /// </summary>
    public BorderCollection getBorders()
    {
        if (mBorders == null)
            mBorders = new BorderCollection(this);
        return mBorders;
    }

    /// <summary>
    /// Returns a <see cref="Aspose.Words.Shading"/> object that refers to the shading formatting for the cell.
    /// </summary>
    public Shading getShading()
    {
        //<<GetOrCreateComplexAttr>> pattern
        Shading shading = (Shading)mParent.getDirectCellAttr(CellAttr.SHADING);
        if (shading == null)
        {
            CellFormat parent = this;
            shading = new Shading(parent, CellAttr.SHADING);
            shading = shading;
            mParent.setCellAttr(CellAttr.SHADING, shading);
        }
        return shading;
    }

    /// <summary>
    /// Returns or sets the vertical alignment of text in the cell.
    /// </summary>
    public /*CellVerticalAlignment*/int getVerticalAlignment() { return (/*CellVerticalAlignment*/Integer)fetchAttr(CellAttr.VERTICAL_ALIGNMENT); }
    public void setVerticalAlignment(/*CellVerticalAlignment*/int value) { mParent.setCellAttr(CellAttr.VERTICAL_ALIGNMENT, value); }

    /// <summary>
    /// Returns or sets visibility of cell mark.
    /// </summary>
    /// <remarks>
    /// Specifies that table cell content is rendered with no height if all cells in the row are empty;
    /// however, cells have a visible height if they have nonzero cell borders, cell margins, or cell spacing.
    /// </remarks>
    public boolean getHideMark() { return (/*boolean*/Boolean)fetchAttr(CellAttr.HIDE_MARK); }
    public void setHideMark(boolean value) { mParent.setCellAttr(CellAttr.HIDE_MARK, value); }

    /// <summary>
    /// Gets the width of the cell in points.
    /// </summary>
    /// <remarks>
    /// <para>
    /// The width is calculated by Aspose.Words on document loading and saving.
    /// Currently, not every combination of table, cell and document properties is supported.
    /// The returned value may not be accurate for some documents.
    /// It may not exactly match the cell width as calculated by MS Word when the document is opened in MS Word.
    /// </para>
    ///
    /// <para>
    /// Setting this property is not recommended.
    /// There is no guarantee that the cell will actually have the set width.
    /// The width may be adjusted to accommodate cell contents in an auto-fit table layout.
    /// Cells in other rows may have conflicting width settings.
    /// The table may be resized to fit into the container or to meet table width settings.
    /// Consider using <see cref="PreferredWidth"/> for setting the cell width.
    /// Setting this property sets <see cref="PreferredWidth"/> implicitly since version 15.8.
    /// </para>
    ///
    /// <seealso cref="Aspose.Words.Tables.CellFormat.PreferredWidth"/>
    /// </remarks>
    public double getWidth() { return ConvertUtilCore.twipToPoint((/*int*/Integer)fetchAttr(CellAttr.WIDTH)); }
    public void setWidth(double value)
    {
        int valueTwips = ConvertUtilCore.pointToTwip(value);
        mParent.setCellAttr(CellAttr.WIDTH, valueTwips);

        PreferredWidth preferred = PreferredWidth.fromTwipsSafe(valueTwips);
        mParent.setCellAttr(CellAttr.PREFERRED_WIDTH, preferred);
    }

    /// <summary>
    /// Returns or sets the preferred width of the cell.
    /// </summary>
    /// <remarks>
    /// <para>The preferred width (along with the table's Auto Fit option) determines how the actual
    /// width of the cell is calculated by the table layout algorithm. Table layout can be performed by
    /// Aspose.Words when it saves the document or by Microsoft Word when it displays the document.</para>
    ///
    /// <para>The preferred width can be specified in points or in percent. The preferred width
    /// can also be specified as "auto", which means no preferred width is specified.</para>
    ///
    /// <para>The default value is <see cref="Aspose.Words.Tables.PreferredWidth.Auto"/>.</para>
    ///
    /// <seealso cref="Width"/>
    /// </remarks>
    public PreferredWidth getPreferredWidth() { return (PreferredWidth)fetchAttr(CellAttr.PREFERRED_WIDTH); }
    public void setPreferredWidth(PreferredWidth value) { mParent.setCellAttr(CellAttr.PREFERRED_WIDTH, value); }

    /// <summary>
    /// Specifies how the cell is merged with other cells vertically.
    /// </summary>
    /// <remarks>
    /// <p>Cells can only be merged vertically if their left and right boundaries are identical.</p>
    /// <p>When cells are vertically merged, the display areas of the merged cells are consolidated.
    /// The consolidated area is used to display the contents of the first vertically merged cell
    /// and all other vertically merged cells must be empty.</p>
    /// <seealso cref="HorizontalMerge"/>
    /// </remarks>
    public /*CellMerge*/int getVerticalMerge() { return (/*CellMerge*/Integer)fetchAttr(CellAttr.VERTICAL_MERGE); }
    public void setVerticalMerge(/*CellMerge*/int value) { mParent.setCellAttr(CellAttr.VERTICAL_MERGE, value); }

    /// <summary>
    /// Specifies how the cell is merged horizontally with other cells in the row.
    /// </summary>
    /// <seealso cref="VerticalMerge"/>
    public /*CellMerge*/int getHorizontalMerge() { return (/*CellMerge*/Integer)fetchAttr(CellAttr.HORIZONTAL_MERGE); }
    public void setHorizontalMerge(/*CellMerge*/int value) { mParent.setCellAttr(CellAttr.HORIZONTAL_MERGE, value); }

    /// <summary>
    /// Returns or sets the orientation of text in a table cell.
    /// </summary>
    public /*TextOrientation*/int getOrientation() { return (/*TextOrientation*/Integer)fetchAttr(CellAttr.ORIENTATION); }
    public void setOrientation(/*TextOrientation*/int value) { mParent.setCellAttr(CellAttr.ORIENTATION, value); }

    /// <summary>
    /// If <c>true</c>, fits text in the cell, compressing each paragraph to the width of the cell.
    /// </summary>
    public boolean getFitText() { return (/*boolean*/Boolean)fetchAttr(CellAttr.FIT_TEXT); }
    public void setFitText(boolean value) { mParent.setCellAttr(CellAttr.FIT_TEXT, value); }

    /// <summary>
    /// If <c>true</c>, wrap text for the cell.
    /// </summary>
    public boolean getWrapText() { return (/*boolean*/Boolean)fetchAttr(CellAttr.WRAP_TEXT); }
    public void setWrapText(boolean value) { mParent.setCellAttr(CellAttr.WRAP_TEXT, value); }

    private Object fetchAttr(int key)
    {
        Object value = mParent.getDirectCellAttr(key);
        return (value != null) ? value : mParent.fetchInheritedCellAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/getDirectBorderAttr(int key)
    {
        return mParent.getDirectCellAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IBorderAttrSource.*/fetchInheritedBorderAttr(int key)
    {
        return mParent.fetchInheritedCellAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IBorderAttrSource.*/setBorderAttr(int key, Object value)
    {
        mParent.setCellAttr(key, value);
    }

    @ReservedForInternalUse @Deprecated
    public SortedList<Integer, Integer> /*IBorderAttrSource.*/getPossibleBorderKeys() { return CellPr.POSSIBLE_BORDER_KEYS; }

    @ReservedForInternalUse @Deprecated
    public Object /*IShadingAttrSource.*/fetchInheritedShadingAttr(int key)
    {
        return mParent.fetchInheritedCellAttr(key);
    }

    private /*final*/ ICellAttrSource mParent;
    private BorderCollection mBorders;
}

