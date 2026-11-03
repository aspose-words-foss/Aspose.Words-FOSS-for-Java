// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;
import com.aspose.Debug;
import com.aspose.ms.System.msInt;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.MathUtil;
import com.aspose.ObjToIntDictionary;


/// <summary>
/// Represents a CSS border object.
/// </summary>
/// <remarks>
/// This class is immutable (read-only), don't change this behaviour.
/// </remarks>
class CssBorder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private CssBorder(boolean isAwBorder)
    {
        mLineStyle = LineStyle.NONE;
        mCssLineStyle = "";
        mLineWidth = 0.0;
        mCssLineWidth = 0.0;
        mColorInternal = DrColor.EMPTY;
        mIsHidden = false;
        mIsUndefined = true;
        isNil(false);
        isAwBorder(isAwBorder);
    }

    /// <summary>
    /// Creates a CSS border object from CSS declarations.
    /// </summary>
    static CssBorder createBorder(
        CssDeclarationCollection declarations,
        /*BorderType*/int borderType,
        boolean invertInsetOutsetLineStyle)
    {
        return createBorder(declarations, borderType, false, invertInsetOutsetLineStyle);
    }

    /// <summary>
    /// Creates a CSS border object from CSS declarations from the HTML block.
    /// </summary>
    static CssBorder createHtmlBlockBorder(
        CssDeclarationCollection declarations,
        /*BorderType*/int borderType)
    {
        // MS Word preserves the line style for the HTML block if the border width is zero.
        return createBorder(declarations, borderType, true, false);
    }

    /// <summary>
    /// Returns a border width value from CSS declarations.
    /// </summary>
    /// <remarks>
    /// This method takes into account that the minimum border width value that's used to calculate padding values during
    /// export to HTML is 0.75pt.
    /// </remarks>
    static double getCssBorderWidth(CssDeclarationCollection declarations, /*BorderType*/int borderType)
    {
        String styleProperty;
        String widthProperty;
        String colorProperty;
        switch (borderType)
        {
            case BorderType.TOP:
                styleProperty = "border-top-style";
                widthProperty = "border-top-width";
                colorProperty = "border-top-color";
                break;
            case BorderType.RIGHT:
                styleProperty = "border-right-style";
                widthProperty = "border-right-width";
                colorProperty = "border-right-color";
                break;
            case BorderType.BOTTOM:
                styleProperty = "border-bottom-style";
                widthProperty = "border-bottom-width";
                colorProperty = "border-bottom-color";
                break;
            case BorderType.LEFT:
                styleProperty = "border-left-style";
                widthProperty = "border-left-width";
                colorProperty = "border-left-color";
                break;
            default:
                Debug.assertTrue(false, "Unsupported border type!");
                return 0.0;
        }

        CssBorder cssBorder = createBorder(
            declarations,
            styleProperty,
            widthProperty,
            colorProperty,
            null,
            null,
            null,
            false,
            false);
        return (cssBorder.getLineWidth() > 0)
            ? cssBorder.getLineWidth()
            : 0.0;
    }

    /// <summary>
    /// Creates a CSS border object from CSS declarations in case all four border sides are specified.
    /// </summary>
    static CssBorder createBoxBorder(CssDeclarationCollection declarations)
    {
        CssBorder leftBorder = createBorder(declarations, BorderType.LEFT, false);
        CssBorder rightBorder = createBorder(declarations, BorderType.RIGHT, false);
        CssBorder topBorder = createBorder(declarations, BorderType.TOP, false);
        CssBorder bottomBorder = createBorder(declarations, BorderType.BOTTOM, false);

        boolean allBordersSpecified =
            (!leftBorder.isUndefined()) &&
            (!rightBorder.isUndefined()) &&
            (!topBorder.isUndefined()) &&
            (!bottomBorder.isUndefined());

        if (allBordersSpecified)
        {
            boolean allBordersAreOfSameStyle =
                (leftBorder.getLineStyle() == topBorder.getLineStyle()) &&
                (rightBorder.getLineStyle() == topBorder.getLineStyle()) &&
                (bottomBorder.getLineStyle() == topBorder.getLineStyle());
            if (allBordersAreOfSameStyle)
            {
                return topBorder;
            }
        }

        return getEmpty();
    }

    static CssBorder createInsideBorder(CssDeclarationCollection declarations, /*BorderType*/int borderType)
    {
        String styleProperty;
        String widthProperty;
        String colorProperty;
        switch (borderType)
        {
            case BorderType.HORIZONTAL:
                styleProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_H_STYLE;
                widthProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_H_WIDTH;
                colorProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_H_COLOR;
                break;
            case BorderType.VERTICAL:
                styleProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_V_STYLE;
                widthProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_V_WIDTH;
                colorProperty = HtmlConstants.ASPOSE_BORDER_INSIDE_V_COLOR;
                break;
            default:
                Debug.assertTrue(false, "Unsupported border type!");
                return null;
        }
        return createBorder(
            declarations,
            null,
            null,
            null,
            styleProperty,
            widthProperty,
            colorProperty,
            false,
            false);
    }

    /// <summary>
    /// Represents an empty CSS border object.
    /// </summary>
    static CssBorder getEmpty() { return G_EMPTY; }

    /// <summary>
    /// Checks the border wins an another border in the border conflict.
    /// </summary>
    /// <remarks>
    /// This comparison algorithm is based on rules 1-3 of Border Conflict Resolution specification
    /// (http://www.w3.org/TR/CSS21/tables.html#border-conflict-resolution).
    /// </remarks>
    /// <param name="other">A <see cref="CssBorder"/> instance.</param>
    /// <returns>A signed number indicating whether the border wins.
    /// Less than zero - this border loses.
    /// Zero - there is no winner (border styles differ only in color).
    /// Greater than zero - this border wins.</returns>
    int wins(CssBorder other)
    {
        // Rule 1: Borders with the 'border-style' of 'hidden' take precedence over all other conflicting borders.
        // Any border with this value suppresses all borders at this location.
        if (mIsHidden && !other.isHidden())
            return 1;
        if (mIsHidden && other.isHidden())
            return 0;
        if (!mIsHidden && other.isHidden())
            return -1;

        // Rule 2: Borders with a style of 'none' have the lowest priority. Only if the border properties of all the elements meeting at this
        // edge are 'none' will the border be omitted (but note that 'none' is the default value for the border style.)
        if (!isNone() && other.isNone())
            return 1;
        if (isNone() && other.isNone())
            return 0;
        if (isNone() && !other.isNone())
            return -1;

        // Rule 3: If none of the styles are 'hidden' and at least one of them is not 'none', then narrow borders are discarded in favor of wider ones.
        // If several have the same 'border-width' then styles are preferred in this order: 'double', 'solid', 'dashed', 'dotted', 'ridge', 'outset', 'groove',
        // and the lowest: 'inset'.
        if (mCssLineWidth > other.getCssLineWidth())
            return 1;
        if (mCssLineWidth < other.getCssLineWidth())
            return -1;
        if (mLineStyle != other.getLineStyle())
        {
            int stylePriority = getStylePriority(mCssLineStyle);
            int otherStylePriority = getStylePriority(other.getCssLineStyle());
            if (stylePriority != otherStylePriority)
                return msInt.compareTo(stylePriority, otherStylePriority);
        }

        // Rule 4 cannot be implemented in this class therefore we return zero.
        return 0;
    }

    /// <summary>
    /// Applies CSS border style, width and color styles to a model border.
    /// </summary>
    void toModelBorder(Border modelBorder)
    {
        modelBorder.setLineStyleInternal(mLineStyle);
        modelBorder.setLineWidthSafe(mLineWidth);
        modelBorder.setColorInternal(mColorInternal);
        modelBorder.isNil(isNil());
    }

    /// <summary>
    /// Applies CSS border style, width and color to a <see cref="Stroke"/> instance.
    /// </summary>
    void toStroke(Stroke stroke)
    {
        if (mIsHidden)
        {
            stroke.setOn(false);
            return;
        }

        switch (gStringSwitchMap.of(mCssLineStyle))
        {
            case /*"solid"*/0:
            case /*"groove"*/1:
            case /*"ridge"*/2:
            case /*"inset"*/3:
            case /*"outset"*/4:
                // There are no stroke counterparts for "3D" border styles like "groove" or "inset", so we import such
                // borders as solid-color strokes.
                stroke.setLineStyle(ShapeLineStyle.SINGLE);
                stroke.setDashStyle(DashStyle.SOLID);
                break;
            case /*"double"*/5:
                stroke.setLineStyle(ShapeLineStyle.DOUBLE);
                stroke.setDashStyle(DashStyle.SOLID);
                break;
            case /*"dotted"*/6:
                stroke.setLineStyle(ShapeLineStyle.SINGLE);
                stroke.setDashStyle(DashStyle.SHORT_DOT);
                break;
            case /*"dashed"*/7:
                stroke.setLineStyle(ShapeLineStyle.SINGLE);
                stroke.setDashStyle(DashStyle.SHORT_DASH);
                break;
            default:
                // Unknown line styles are not supported.
                stroke.setOn(false);
                return;
        }

        stroke.setOn(true);
        stroke.setWeight(mCssLineWidth);
        stroke.setColorInternal(mColorInternal);
        stroke.setJoinStyle(JoinStyle.MITER);
    }

    private static CssBorder createBorder(
        CssDeclarationCollection declarations,
        /*BorderType*/int borderType,
        boolean preserveZeroWidthBorderStyle,
        boolean invertInsetOutsetLineStyle)
    {
        CssBorder cssBorder = null;
        switch (borderType)
        {
            case BorderType.TOP:
                cssBorder = createBorder(
                    declarations,
                    "border-top-style",
                    "border-top-width",
                    "border-top-color",
                    HtmlConstants.ASPOSE_BORDER_TOP_STYLE,
                    HtmlConstants.ASPOSE_BORDER_TOP_WIDTH,
                    HtmlConstants.ASPOSE_BORDER_TOP_COLOR,
                    preserveZeroWidthBorderStyle,
                    invertInsetOutsetLineStyle);
                break;
            case BorderType.RIGHT:
                cssBorder = createBorder(
                    declarations,
                    "border-right-style",
                    "border-right-width",
                    "border-right-color",
                    HtmlConstants.ASPOSE_BORDER_RIGHT_STYLE,
                    HtmlConstants.ASPOSE_BORDER_RIGHT_WIDTH,
                    HtmlConstants.ASPOSE_BORDER_RIGHT_COLOR,
                    preserveZeroWidthBorderStyle,
                    invertInsetOutsetLineStyle);
                break;
            case BorderType.BOTTOM:
                cssBorder = createBorder(
                    declarations,
                    "border-bottom-style",
                    "border-bottom-width",
                    "border-bottom-color",
                    HtmlConstants.ASPOSE_BORDER_BOTTOM_STYLE,
                    HtmlConstants.ASPOSE_BORDER_BOTTOM_WIDTH,
                    HtmlConstants.ASPOSE_BORDER_BOTTOM_COLOR,
                    preserveZeroWidthBorderStyle,
                    invertInsetOutsetLineStyle);
                break;
            case BorderType.LEFT:
                cssBorder = createBorder(
                    declarations,
                    "border-left-style",
                    "border-left-width",
                    "border-left-color",
                    HtmlConstants.ASPOSE_BORDER_LEFT_STYLE,
                    HtmlConstants.ASPOSE_BORDER_LEFT_WIDTH,
                    HtmlConstants.ASPOSE_BORDER_LEFT_COLOR,
                    preserveZeroWidthBorderStyle,
                    invertInsetOutsetLineStyle);
                break;
            default:
                Debug.assertTrue(false, "Unsupported border type!");
                break;
        }

        return cssBorder;
    }

    /// <summary>
    /// Gets priority of the specified CSS line style.
    /// </summary>
    /// <param name="lineStyleName">CSS line style.</param>
    /// <returns>A unsigned number indicating priority of the line style. Larger value corresponds to higher priority.</returns>
    private static int getStylePriority(String lineStyleName)
    {
        // If several have the same 'border-width' then styles are preferred in this order: 'double', 'solid', 'dashed', 'dotted',
        // 'ridge', 'outset', 'groove', and the lowest: 'inset'.
        switch (gStringSwitchMap.of(msString.toLowerInvariant(lineStyleName)))
        {
            case /*"double"*/5:
                return 10;
            case /*"solid"*/0:
                return 9;
            case /*"dashed"*/7:
                return 8;
            case /*"dotted"*/6:
                return 7;
            case /*"ridge"*/2:
                return 6;
            case /*"outset"*/4:
                return 5;
            case /*"groove"*/1:
                return 4;
            case /*"inset"*/3:
                return 3;
            default:
                return 0;
        }
    }

    /// <summary>
    /// Creates a CSS border object from CSS declarations.
    /// </summary>
    private static CssBorder createBorder(
        CssDeclarationCollection declarations,
        String stylePropertyName,
        String widthPropertyName,
        String colorPropertyName,
        String awBorderStyleName,
        String awBorderWidthName,
        String awBorderColorName,
        boolean preserveZeroWidthBorderStyle,
        boolean invertInsetOutsetLineStyle)
    {
        CssDeclaration borderStyleDeclaration = StringUtil.hasChars(stylePropertyName)
            ? declarations.get(stylePropertyName)
            : null;
        CssDeclaration awBorderStyleDeclaration = StringUtil.hasChars(awBorderStyleName)
            ? declarations.get(awBorderStyleName)
            : null;

        if ((borderStyleDeclaration == null) && (awBorderStyleDeclaration == null))
            return getEmpty();

        Debug.assertTrue(StringUtil.hasChars(widthPropertyName) || StringUtil.hasChars(awBorderWidthName));
        Debug.assertTrue(StringUtil.hasChars(colorPropertyName) || StringUtil.hasChars(awBorderColorName));

        CssDeclaration borderWidthDeclaration = StringUtil.hasChars(widthPropertyName)
            ? declarations.get(widthPropertyName)
            : null;
        CssDeclaration awBorderWidthDeclaration = StringUtil.hasChars(awBorderWidthName)
            ? declarations.get(awBorderWidthName)
            : null;

        CssDeclaration borderColorDeclaration = StringUtil.hasChars(colorPropertyName)
            ? declarations.get(colorPropertyName)
            : null;
        CssDeclaration awBorderColorDeclaration = StringUtil.hasChars(awBorderColorName)
            ? declarations.get(awBorderColorName)
            : null;

        CssPropertyValue borderStyleValue = (awBorderStyleDeclaration != null)
            ? awBorderStyleDeclaration.getValue()
            : borderStyleDeclaration.getValue();

        // WORDSNET-20289 Get border width and style from custom CSS properties. If a custom property is present,
        // it takes precedence over the corresponding standard CSS property.
        // CSS width property is used to calculate table preffered width, cell paddings and cell preffered width.
        // Custom border width property is used to restore the original border line width after round-trip.
        CssPropertyValue borderWidthValue = null;
        if (borderStyleDeclaration == null)
        {
            borderWidthValue = new CssPropertyValue(CssValue.ZeroPt);
        }
        else if (borderWidthDeclaration == null)
        {
            borderWidthValue = new CssPropertyValue(CssValue.Medium);
        }
        else
        {
            borderWidthValue = borderWidthDeclaration.getValue();
        }

        Debug.assertTrue(borderWidthValue != null);

        CssPropertyValue borderLineWidthValue = (awBorderWidthDeclaration != null)
            ? awBorderWidthDeclaration.getValue()
            : borderWidthValue;

        DrColor color;
        if ((borderColorDeclaration == null) && (awBorderColorDeclaration == null))
        {
            color = DrColor.EMPTY;
        }
        else
        {
            CssPropertyValue borderColorValue = (awBorderColorDeclaration != null)
                ? awBorderColorDeclaration.getValue()
                : borderColorDeclaration.getValue();

            if (borderColorValue.equals(CssValue.Transparent))
            {
                color = DrColor.TRANSPARENT;
            }
            else if (borderColorValue.equals(CssValue.CurrentColor))
            {
                // WORDSNET-27791 The used value of 'currentcolor' in 'border-xxx-color' properties is the used value
                // of the 'color' property on the same element.
                CssDeclaration colorDeclaration = declarations.get("color");
                color = (colorDeclaration != null)
                    ? colorDeclaration.getValue().parseAsColor()
                    : DrColor.EMPTY;
            }
            else
            {
                color = borderColorValue.parseAsColor();
            }
        }

        boolean isAwBorder = awBorderStyleDeclaration != null;
        CssBorder cssBorder = new CssBorder(isAwBorder);
        // "-aw-border-width" stores the original line width value (not the whole border width) that doesn't require
        // any recalculation on import.
        boolean convertBorderWidthToLineWidth = awBorderWidthDeclaration == null;
        cssBorder.setProperties(
            borderStyleValue,
            borderLineWidthValue,
            color,
            borderWidthValue,
            convertBorderWidthToLineWidth,
            preserveZeroWidthBorderStyle,
            invertInsetOutsetLineStyle && !cssBorder.isAwBorder());

        return cssBorder;
    }

    /// <summary>
    /// Sets CSS line style, width and color property values.
    /// </summary>
    private void setProperties(
        CssPropertyValue lineStyleValue,
        CssPropertyValue lineWidthValue,
        DrColor color,
        CssPropertyValue borderWidthValue,
        boolean convertBorderWidthToLineWidth,
        boolean preserveZeroWidthBorderStyle,
        boolean invertInsetOutsetLineStyle)
    {
        Debug.assertTrue(lineStyleValue != null);
        Debug.assertTrue(lineWidthValue != null);

        if ((lineStyleValue.getCount() != 1) || (lineWidthValue.getCount() != 1) || (color == null))
            return;

        if (lineStyleValue.getFirstValue().equals(CssValue.Hidden))
        {
            mIsHidden = true;
            mIsUndefined = false;
        }
        else if (lineStyleValue.getFirstValue().equals(CssValue.None))
        {
            mLineStyle = LineStyle.NONE;
            mIsUndefined = false;
        }
        else if (lineStyleValue.getFirstValue().equals(CssValue.Nil))
        {
            mLineStyle = LineStyle.NONE;
            isNil(true);
            mIsUndefined = false;
        }
        else
        {
            setBorderLineStyle(lineStyleValue.getFirstValue(), invertInsetOutsetLineStyle);
            setBorderLineWidth(lineWidthValue.getFirstValue());
            setCssBorderWidth(borderWidthValue.getFirstValue());
            if ((mLineStyle != LineStyle.NONE) &&
                (!MathUtil.isZero(mLineWidth) || preserveZeroWidthBorderStyle))
            {
                // WORDSNET-15436 ExportImport: Roundtrip border width
                if (convertBorderWidthToLineWidth)
                    convertBorderWidthToLineWidthAfterImport();

                mIsUndefined = false;
                mColorInternal = color;
            }
            else
            {
                mLineStyle = LineStyle.NONE;
                mLineWidth = 0.0;
            }
        }
    }

    /// <summary>
    /// Sets the border line width from CSS line width.
    /// </summary>
    /// <param name="cssValue">CSS width value or CSS extended width value.</param>
    /// <remarks>This value may be taken or from CSS border width property
    /// or from CSS extended border width property.</remarks>
    private void setBorderLineWidth(CssValue cssValue)
    {
        double borderWidth = getBorderWidthValue(cssValue);

        // We store the original border width in mCssLineWidth because mLineWidth can be corrected
        // in CorrectBorderWidthAfterImport() function.
        mCssLineWidth = borderWidth;
        mLineWidth = borderWidth;
    }

    /// <summary>
    /// Sets the border width from CSS border width.
    /// </summary>
    /// <param name="cssValue">CSS width value.</param>
    private void setCssBorderWidth(CssValue cssValue)
    {
        mCssBorderWidth = getBorderWidthValue(cssValue);
    }

    /// <summary>
    /// Corrects border width according to line style. Needed after import.
    /// Maybe other attributes also should be corrected.
    /// </summary>
    private void convertBorderWidthToLineWidthAfterImport()
    {
        float coeff = Border.getActualWidth(mLineStyle, 1.0f);
        if (coeff > 1.0f)
            mLineWidth /= coeff;
    }

    /// <summary>
    /// Sets the border line style from CSS line style.
    /// </summary>
    private void setBorderLineStyle(
        CssValue cssValue,
        boolean invertInsetOutsetLineStyle)
    {
        int lineStyleValue = G_CSS_VALUE_TO_LINE_STYLE_MAP.get(cssValue);
        if (ObjToIntDictionary.isNullSubstitute(lineStyleValue))
        {
            lineStyleValue = (int)LineStyle.NONE;
            Debug.fail("Unsupported line style.");
        }
        mLineStyle = (/*LineStyle*/int)lineStyleValue;

        // WORDSNET-24112 MS Word supports "inset" and "outset" border styles but renders them inverted
        // on table cell elements. That is, "inset" is rendered by MS Word as "outset" on table cells, and vice versa.
        // In HTML, however, each of these line styles is always rendered in the same way on all elements. In order to
        // compensate for this difference, MS Word explicitly inverts "inset" and "outset" styles on cell borders
        // when saving to HTML (for example, it writes "outset" instead of "inset") and inverts them back upon loading.
        // We do the same.
        if (invertInsetOutsetLineStyle)
        {
            switch (mLineStyle)
            {
                case LineStyle.INSET:
                    mLineStyle = LineStyle.OUTSET;
                    break;
                case LineStyle.OUTSET:
                    mLineStyle = LineStyle.INSET;
                    break;
                default:
                    // Keep other line styles unchanged.
                    break;
            }
        }

        mCssLineStyle = (mLineStyle != LineStyle.NONE)
            ? msString.toLowerInvariant(((CssIdentifierValue)cssValue).getValue())
            : "none";
    }

    /// <summary>
    /// Gets the border style.
    /// </summary>
    /*LineStyle*/int getLineStyle() { return mLineStyle; }

    /// <summary>
    /// Gets the border CSS style name.
    /// </summary>
    String getCssLineStyle() { return mCssLineStyle; }

    /// <summary>
    /// Gets the border width in points.
    /// </summary>
    double getLineWidth() { return mLineWidth; }

    /// <summary>
    /// Gets the border CSS line width in points.
    /// </summary>
    double getCssLineWidth() { return mCssLineWidth; }

    /// <summary>
    /// Gets the border CSS width in points.
    /// </summary>
    double getCssBorderWidth() { return mCssBorderWidth; }

    /// <summary>
    /// Gets the border color.
    /// </summary>
    DrColor getColorInternal() { return mColorInternal; }

    /// <summary>
    /// Indicates whether the border style is 'hidden'.
    /// </summary>
    boolean isHidden() { return mIsHidden; }

    /// <summary>
    /// Indicates whether the border style is 'none'.
    /// </summary>
    boolean isNone() { return mLineStyle == LineStyle.NONE; }

    boolean isNil() { return mIsNil; }; private void isNil(boolean value) { mIsNil = value; };

    private boolean mIsNil;

    /// <summary>
    /// Indicates whether the border is undefined.
    /// </summary>
    boolean isUndefined() { return mIsUndefined; }

    /// <summary>
    /// Indicates whether the border is visible.
    /// </summary>
    boolean isVisible() { return !(mIsUndefined || mIsHidden || isNone()); }

    boolean isAwBorder() { return mIsAwBorder; }; private void isAwBorder(boolean value) { mIsAwBorder = value; };

    private boolean mIsAwBorder;

    private static double getBorderWidthValue(CssValue cssValue)
    {
        Debug.assertTrue(cssValue != null);

        double borderWidth;
        if (cssValue.equals(CssValue.Thin))
        {
            borderWidth = 1.5; // Looks like IE uses this width.
        }
        else if (cssValue.equals(CssValue.Medium))
        {
            borderWidth = 3.0; // Looks like IE uses this width.
        }
        else if (cssValue.equals(CssValue.Thick))
        {
            borderWidth = 4.5; // Looks like IE uses this width.
        }
        else
        {
            double length = CssUtil.lengthToPoint(cssValue);
            borderWidth = (!MathUtil.isMinValue(length)) ? length : 0.0;
        }
        return borderWidth;
    }

    private static /*final*/ ObjToIntDictionary<CssValue> G_CSS_VALUE_TO_LINE_STYLE_MAP;
    private static /*final*/ CssBorder G_EMPTY = new CssBorder(false);

    private /*LineStyle*/int mLineStyle;
    private double mLineWidth;
    private DrColor mColorInternal;
    private boolean mIsHidden;
    private String mCssLineStyle;
    private boolean mIsUndefined;
    private double mCssLineWidth;
    private double mCssBorderWidth;

    static/* CssBorder()*/
    {
        G_CSS_VALUE_TO_LINE_STYLE_MAP = new ObjToIntDictionary<CssValue>();

        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.None, (int)LineStyle.NONE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Hidden, (int)LineStyle.NONE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Dotted, (int)LineStyle.DOT);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Dashed, (int)LineStyle.DASH_SMALL_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DoubleId, (int)LineStyle.DOUBLE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Groove, (int)LineStyle.EMBOSS_3_D);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Ridge, (int)LineStyle.ENGRAVE_3_D);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Solid, (int)LineStyle.SINGLE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Inset, (int)LineStyle.INSET);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Outset, (int)LineStyle.OUTSET);

        // The values below are custom AW border styles that are used to preserve
        // MS Word-specific border styles during HTML round-trip.
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Single, (int)LineStyle.SINGLE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Thick, (int)LineStyle.THICK);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Hairline, (int)LineStyle.HAIRLINE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Dot, (int)LineStyle.DOT);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DashLargeGap, (int)LineStyle.DASH_LARGE_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DotDash, (int)LineStyle.DOT_DASH);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DotDotDash, (int)LineStyle.DOT_DOT_DASH);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Triple, (int)LineStyle.TRIPLE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickSmallGap, (int)LineStyle.THIN_THICK_SMALL_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThickThinSmallGap, (int)LineStyle.THICK_THIN_SMALL_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickThinSmallGap, (int)LineStyle.THIN_THICK_THIN_SMALL_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickMediumGap, (int)LineStyle.THIN_THICK_MEDIUM_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThickThinMediumGap, (int)LineStyle.THICK_THIN_MEDIUM_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickThinMediumGap, (int)LineStyle.THIN_THICK_THIN_MEDIUM_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickLargeGap, (int)LineStyle.THIN_THICK_LARGE_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThickThinLargeGap, (int)LineStyle.THICK_THIN_LARGE_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.ThinThickThinLargeGap, (int)LineStyle.THIN_THICK_THIN_LARGE_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Wave, (int)LineStyle.WAVE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DoubleWave, (int)LineStyle.DOUBLE_WAVE);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DashSmallGap, (int)LineStyle.DASH_SMALL_GAP);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.DashDotStroker, (int)LineStyle.DASH_DOT_STROKER);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Emboss3D, (int)LineStyle.EMBOSS_3_D);
        G_CSS_VALUE_TO_LINE_STYLE_MAP.add(CssValue.Engrave3D, (int)LineStyle.ENGRAVE_3_D);
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"solid",
		"groove",
		"ridge",
		"inset",
		"outset",
		"double",
		"dotted",
		"dashed"
	);

}

