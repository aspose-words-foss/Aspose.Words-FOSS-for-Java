// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A collection of all four (top, right, bottom and left) CSS border objects.
/// </summary>
class CssBoxBorders
{
    /// <summary>
    /// Ctor.
    /// </summary>
    CssBoxBorders(CssBorder top, CssBorder right, CssBorder bottom, CssBorder left)
    {
        mTop = top;
        mRight = right;
        mBottom = bottom;
        mLeft = left;
    }

    /// <summary>
    /// Creates a CSS borders object from CSS border styles.
    /// </summary>
    static CssBoxBorders createBorders(
        CssDeclarationCollection declarations,
        boolean invertInsetOutsetLineStyle)
    {
        CssBorder top = CssBorder.createBorder(declarations, BorderType.TOP, invertInsetOutsetLineStyle);
        CssBorder right = CssBorder.createBorder(declarations, BorderType.RIGHT, invertInsetOutsetLineStyle);
        CssBorder bottom = CssBorder.createBorder(declarations, BorderType.BOTTOM, invertInsetOutsetLineStyle);
        CssBorder left = CssBorder.createBorder(declarations, BorderType.LEFT, invertInsetOutsetLineStyle);
        return new CssBoxBorders(top, right, bottom, left);
    }

    /// <summary>
    /// Creates an empty CSS borders object.
    /// </summary>
    static CssBoxBorders createEmpty()
    {
        return new CssBoxBorders(CssBorder.getEmpty(), CssBorder.getEmpty(), CssBorder.getEmpty(), CssBorder.getEmpty());
    }

    /// <summary>
    /// Applies CSS borders style, width and color styles to model borders.
    /// </summary>
    void toModelBorders(BorderCollection modelBorders) throws Exception
    {
        mTop.toModelBorder(modelBorders.getTop());
        mRight.toModelBorder(modelBorders.getRight());
        mBottom.toModelBorder(modelBorders.getBottom());
        mLeft.toModelBorder(modelBorders.getLeft());
    }

    /// <summary>
    /// Retrieves a CSS Border object by type.
    /// </summary>
    /// <param name="borderType">The type of the border to retrieve.</param>
    CssBorder getByBorderType(/*BorderType*/int borderType)
    {
        switch (borderType)
        {
            case BorderType.TOP:
                return mTop;
            case BorderType.RIGHT:
                return mRight;
            case BorderType.BOTTOM:
                return mBottom;
            case BorderType.LEFT:
                return mLeft;
            default:
                throw new IllegalStateException("Unknown border type.");
        }
    }

    /// <summary>
    /// Gets the left border.
    /// </summary>
    CssBorder getLeft() { return mLeft; }

    /// <summary>
    /// Gets the right border.
    /// </summary>
    CssBorder getRight() { return mRight; }

    /// <summary>
    /// Gets the top border.
    /// </summary>
    CssBorder getTop() { return mTop; }

    /// <summary>
    /// Gets the bottom border.
    /// </summary>
    CssBorder getBottom() { return mBottom; }

    private /*final*/ CssBorder mLeft;
    private /*final*/ CssBorder mRight;
    private /*final*/ CssBorder mTop;
    private /*final*/ CssBorder mBottom;
}

