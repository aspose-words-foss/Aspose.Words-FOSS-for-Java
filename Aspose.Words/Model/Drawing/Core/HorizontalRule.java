// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ArgumentUtil;
import com.aspose.DrColor;


/// <summary>
/// Defines attributes of a horizontal rule shape.
/// </summary>
class HorizontalRule
{
    HorizontalRule(IShapeAttrSource parent)
    {
        Debug.assertTrue(parent != null);
        mParent = parent;
    }

    /// <summary>
    /// True when this shape is a horizontal rule.
    /// </summary>
    boolean getOn() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.HR_ON); }
    void setOn(boolean value) { setAttr(ShapeAttr.HR_ON, value); }

    /// <summary>
    /// True when this horizontal rule is drawn using one color.
    /// </summary>
    boolean getNoShade() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.HR_NO_SHADE); }
    void setNoShade(boolean value) { setAttr(ShapeAttr.HR_NO_SHADE, value); }

    /// <summary>
    /// True when this horizontal rule is a standard one (does not use a picture).
    /// </summary>
    boolean getStandard() { return (/*boolean*/Boolean)fetchAttr(ShapeAttr.HR_STANDARD); }
    void setStandard(boolean value) { setAttr(ShapeAttr.HR_STANDARD, value); }

    /// <summary>
    /// Specifies alignment of the horizontal rule.
    /// </summary>
    /*HorizontalRuleAlignment*/int getAlign() { return (/*HorizontalRuleAlignment*/Integer)fetchAttr(ShapeAttr.HR_ALIGN); }
    void setAlign(/*HorizontalRuleAlignment*/int value) { setAttr(ShapeAttr.HR_ALIGN, value); }

    /// <summary>
    /// Gets or sets the width of the horizontal rule in percent.
    /// </summary>
    /// <remarks>
    /// <p>The default value is 100.</p>
    /// <p>If this value is zero, then the width of the horizontal rule is specified
    /// by the shape size in points.</p>
    /// </remarks>
    double getPercent() { return tenthsPercentToPercent((/*int*/Integer)fetchAttr(ShapeAttr.HR_PCT)); }
    void setPercent(double value) { setPercentCore(value, true); }

    void setPercentSafe(double value)
    {
        setPercentCore(value, false);
    }

    /// <summary>
    /// Sets percentage value to the horizontal rule.
    /// </summary>
    private void setPercentCore(double value, boolean isThrow)
    {
        value = ArgumentUtil.validateRange(value, 0.0, 0.0, 100.0, 100.0, isThrow, "percent");
        setAttr(ShapeAttr.HR_PCT, percentToTenthsPercent(value));
    }

    private static double tenthsPercentToPercent(int value)
    {
        return value / 10.0;
    }

    private static int percentToTenthsPercent(double value)
    {
        return (int)(value * 10.0);
    }

    private Object fetchAttr(int key)
    {
        return mParent.fetchShapeAttr(key);
    }

    private void setAttr(int key, Object value)
    {
        mParent.setShapeAttr(key, value);
    }

    private /*final*/ IShapeAttrSource mParent;

    static /*final*/ DrColor DEFAULT_COLOR = DrColor.GRAY;
}

