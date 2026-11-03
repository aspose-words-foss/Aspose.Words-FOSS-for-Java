// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class for the algorithm that expands conditional formatting of table styles.
/// </summary>
abstract class CnfExpanderBase
{
    void expand(TableFormattingExpander mother, AttrCollection dstAttrs) throws Exception
    {
        mMother = mother;
        mDstAttrs = dstAttrs;

        // 1. Banded rows
        if (mMother.isRowBandNow())
            doExpand(mMother.getRowBandNow());

        // 2. Banded cols
        if (mMother.isColBandNow())
            doExpand(mMother.getColBandNow());

        // 3. First/last col
        if (mMother.isFirstColNow())
            doExpand(TableStyleOverrideType.FIRST_COLUMN);

        if (mMother.isLastColNow())
            doExpand(TableStyleOverrideType.LAST_COLUMN);

        // 4. First/last row
        if (mMother.isFirstRowNow())
            doExpand(TableStyleOverrideType.FIRST_ROW);

        if (mMother.isLastRowNow())
            doExpand(TableStyleOverrideType.LAST_ROW);

        // 5. Corner cells.
        if (mMother.isNWCellNow())
            doExpand(TableStyleOverrideType.TOP_LEFT_CELL);

        if (mMother.isNECellNow())
            doExpand(TableStyleOverrideType.TOP_RIGHT_CELL);

        if (mMother.isSWCellNow())
            doExpand(TableStyleOverrideType.BOTTOM_LEFT_CELL);

        if (mMother.isSECellNow())
            doExpand(TableStyleOverrideType.BOTTOM_RIGHT_CELL);
    }

    protected abstract void doExpand(/*TableStyleOverrideType*/int type) throws Exception;

    protected TableFormattingExpander mMother;
    protected AttrCollection mDstAttrs;
}

class CnfExpanderRunPr extends CnfExpanderBase
{
    protected /*override*/ void doExpand(/*TableStyleOverrideType*/int type)
    {
        ConditionalStyle conditionalStyle = mMother.getConditionalStyle(type);
        if ((conditionalStyle != null) && conditionalStyle.hasRunFormatting())
        {
            conditionalStyle.getRunPr().expandTo(mDstAttrs);

            conditionalStyle.getRunPr().themeColorInheritanceHack(mDstAttrs);
        }
    }
}

class CnfExpanderParaPr extends CnfExpanderBase
{
    protected /*override*/ void doExpand(/*TableStyleOverrideType*/int type)
    {
        ConditionalStyle conditionalStyle = mMother.getConditionalStyle(type);
        if ((conditionalStyle != null) && conditionalStyle.hasParagraphFormatting())
            conditionalStyle.getParaPr().expandTo(mDstAttrs);
    }
}

class CnfExpanderCellPr extends CnfExpanderBase implements IExpandedAttrSaver
{
    private /*TableStyleOverrideType*/int mType; 

    protected /*override*/ void doExpand(/*TableStyleOverrideType*/int type)
    {
        ConditionalStyle conditionalStyle = mMother.getConditionalStyle(type);
        if ((conditionalStyle == null) || !conditionalStyle.hasCellFormatting())
            return;

        mType = type;
        conditionalStyle.getCellPr().expandTo(mDstAttrs, this);
    }

    /// <summary>
    /// When expanding cell properties of conditional formatting, we need to use
    /// inside horizontal and vertical borders instead of top, left, right and bottom
    /// where appropriate.
    /// </summary>
    public void /*IExpandedAttrSaver.*/save(AttrCollection dstAttrs, int key, Object value)
    {
        switch (mType)
        {
            case TableStyleOverrideType.FIRST_ROW:
            case TableStyleOverrideType.LAST_ROW:
            {
                if (expandVerticalBorders(key, value))
                    return;

                break;
            }
            case TableStyleOverrideType.FIRST_COLUMN:
            case TableStyleOverrideType.LAST_COLUMN:
            {
                if (expandInsideHorizontal(key, value))
                    return;

                break;
            }
            case TableStyleOverrideType.ODD_ROW_BANDING:
            case TableStyleOverrideType.EVEN_ROW_BANDING:
            {
                boolean done = expandVerticalBorders(key, value);

                // Expand horizontal borders for wide row bands.
                if (!done && (mMother.getRowBandSize() > 1))
                    done = expandInsideAndOutsideBorders(
                        key,
                        value,
                        CellAttr.BORDER_HORIZONTAL,
                        CellAttr.BORDER_TOP,
                        CellAttr.BORDER_BOTTOM,
                        mMother.isFirstRowInBand(),
                        mMother.isLastRowInBand());

                if (done)
                    return;

                break;

            }
            case TableStyleOverrideType.ODD_COLUMN_BANDING:
            case TableStyleOverrideType.EVEN_COLUMN_BANDING:
            {
                boolean done = expandInsideHorizontal(key, value);

                // Expand vertical borders for wide column bands.
                if (!done && (mMother.getColBandSize() > 1))
                    done = expandInsideAndOutsideBorders(
                        key,
                        value,
                        CellAttr.BORDER_VERTICAL,
                        CellAttr.BORDER_LEFT,
                        CellAttr.BORDER_RIGHT,
                        mMother.isFirstColInBand(),
                        mMother.isLastColInBand());

                if (done)
                    return;

                break;
            }
            default:
                break;
        }

        mDstAttrs.set(key, value);
    }

    private boolean expandVerticalBorders(int key, Object value)
    {
        return expandInsideAndOutsideBorders(
            key,
            value,
            CellAttr.BORDER_VERTICAL,
            CellAttr.BORDER_LEFT,
            CellAttr.BORDER_RIGHT,
            mMother.isFirstCol(),
            mMother.isLastCol());
    }

    private boolean expandInsideHorizontal(int key, Object value)
    {
        return expandInsideAndOutsideBorders(
            key,
            value,
            CellAttr.BORDER_HORIZONTAL,
            CellAttr.BORDER_TOP,
            CellAttr.BORDER_BOTTOM,
            mMother.isFirstRow(),
            mMother.isLastRow());
    }

    private boolean expandInsideAndOutsideBorders(
        int key,
        Object value,
        int keyInside,
        int keyFirst,
        int keyLast,
        boolean isFirst,
        boolean isLast)
    {
        if (key == keyFirst)
        {
            // The left border should only be stored in the first cell.
            if (isFirst)
                mDstAttrs.set(keyFirst, value);

            return true;
        }
        else if (key == keyLast)
        {
            // The right border should only be stored in the last cell.
            if (isLast)
                mDstAttrs.set(keyLast, value);

            return true;
        }
        else if (key == keyInside)
        {
            // The inside vertical border should become a left and/or right border.
            // If we use it for both left and right, we need to clone it.
            boolean isNeedClone = false;

            if (!isLast)
            {
                mDstAttrs.set(keyLast, value);
                isNeedClone = true;
            }

            if (!isFirst)
            {
                Object leftBorder = (isNeedClone) ? ((Border)value).deepClone() : value;
                mDstAttrs.set(keyFirst, leftBorder);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}

class CnfExpanderRowPr extends CnfExpanderBase
{
    protected /*override*/ void doExpand(/*TableStyleOverrideType*/int type)
    {
        ConditionalStyle conditionalStyle = mMother.getConditionalStyle(type);
        if (conditionalStyle == null) 
            return;
        
        if (conditionalStyle.hasTableFormatting())
            conditionalStyle.getTablePr().expandTo(mDstAttrs);

        if (conditionalStyle.hasRowFormatting())
            conditionalStyle.getRowPr().expandTo(mDstAttrs);
    }
}

