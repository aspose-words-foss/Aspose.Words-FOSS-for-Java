// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.MathUtil;


/// <summary>
/// Represents four (top, right, bottom and left) CSS box dimensions measured in points.
/// </summary>
abstract class CssDimensions
{
    protected CssDimensions(String edgeCssPropertyPrefix)
    {
        Debug.assertTrue(StringUtil.hasChars(edgeCssPropertyPrefix));
        mEdgeCssPropertyPrefix = edgeCssPropertyPrefix;
    }

    protected CssDimensions(CssDimensions other)
    {
        mEdgeCssPropertyPrefix = other.mEdgeCssPropertyPrefix;
        mTop = other.getTop();
        mUseTop = other.getUseTop();
        mRight = other.getRight();
        mUseRight = other.getUseRight();
        mBottom = other.getBottom();
        mUseBottom = other.getUseBottom();
        mLeft = other.getLeft();
        mUseLeft = other.getUseLeft();
    }

    public abstract Object deepClone();

    /// <summary>
    /// Converts logical dimensions to phisical dimensions.
    /// The physical dimensions always stay the same (top, right, bottom and left), while the logical dimensions change depending on writing mode, 
    /// direction and text orientation.
    /// </summary>
    /// <param name="blockFlowDirection">Block flow direction.</param>
    /// <param name="rtl">Indicates whether text direction is right-to-left.</param>
    /// <returns>A new instance of the class.</returns>
    protected CssDimensions toPhisical(/*CssBlockFlowDirection*/int blockFlowDirection, boolean rtl)
    {
        CssDimensions phisicalBox = (CssDimensions)deepClone();
        switch (blockFlowDirection)
        {
            case CssBlockFlowDirection.HORIZONTAL_TB:
            {
                if (rtl)
                {
                    phisicalBox.setRight(mLeft);
                    phisicalBox.setUseRight(mUseLeft);
                    phisicalBox.setLeft(mRight);
                    phisicalBox.setUseLeft(mUseRight);
                }
                break; 
            }
            case CssBlockFlowDirection.VERTICAL_RL:
            case CssBlockFlowDirection.VERTICAL_LR:
            {
                phisicalBox.setTop((rtl) ? mRight : mLeft);
                phisicalBox.setUseTop((rtl) ? mUseRight : mUseLeft);
                phisicalBox.setRight(mTop);
                phisicalBox.setUseRight(mUseTop);
                phisicalBox.setBottom((rtl) ? mLeft : mRight);
                phisicalBox.setUseBottom((rtl) ? mUseLeft : mUseRight);
                phisicalBox.setLeft(mBottom);
                phisicalBox.setUseLeft(mUseBottom);
                break;
            }
            default:
            {
                Debug.assertTrue(false, "Unknown value!");
                break;
            }
        }

        return phisicalBox;
    }

    /// <summary>
    /// Converts phisical dimensions to logical dimensions.
    /// The physical dimensions always stay the same (top, right, bottom and left), while the logical dimensions change depending on writing mode, 
    /// direction and text orientation.
    /// </summary>
    /// <param name="blockFlowDirection">Block flow direction.</param>
    /// <param name="rtl">Indicates whether text direction is right-to-left.</param>
    /// <returns>A new instance of the class.</returns>
    protected CssDimensions toLogical(/*CssBlockFlowDirection*/int blockFlowDirection, boolean rtl)
    {
        CssDimensions logicalBox = (CssDimensions)deepClone();
        switch (blockFlowDirection)
        {
            case CssBlockFlowDirection.HORIZONTAL_TB:
            {
                if (rtl)
                {
                    logicalBox.setRight(mLeft);
                    logicalBox.setUseRight(mUseLeft);
                    logicalBox.setLeft(mRight);
                    logicalBox.setUseLeft(mUseRight);
                }
                break;
            }
            case CssBlockFlowDirection.VERTICAL_RL:
            case CssBlockFlowDirection.VERTICAL_LR:
            {
                logicalBox.setTop(mRight);
                logicalBox.setUseTop(mUseRight);
                logicalBox.setRight((rtl) ? mTop : mBottom);
                logicalBox.setUseRight((rtl) ? mUseTop : mUseBottom);
                logicalBox.setBottom(mLeft);
                logicalBox.setUseBottom(mUseLeft);
                logicalBox.setLeft((rtl) ? mBottom : mTop);
                logicalBox.setUseLeft((rtl) ? mUseBottom : mUseTop);
                break;
            }
            default:
            {
                Debug.assertTrue(false, "Unknown value!");
                break;
            }
        }

        return logicalBox;
    }

    void toStyle(CssStyle style)
    {
        if (mUseTop)
            style.setLength(mEdgeCssPropertyPrefix + "top", mTop, CssUnit.PT);
        if (mUseRight)
            style.setLength(mEdgeCssPropertyPrefix + "right", mRight, CssUnit.PT);
        if (mUseBottom)
            style.setLength(mEdgeCssPropertyPrefix + "bottom", mBottom, CssUnit.PT);
        if (mUseLeft)
            style.setLength(mEdgeCssPropertyPrefix + "left", mLeft, CssUnit.PT);
    }

    protected void fromStyle(CssStyle style)
    {
        Debug.assertTrue(style != null);
        CssDeclarationCollection declarations = style.getDeclarations();
        double topValue = declarations.getLength(mEdgeCssPropertyPrefix + "top");
        if (!MathUtil.isMinValue(topValue))
        {
            setTop(topValue);
        }
        double rightValue = declarations.getLength(mEdgeCssPropertyPrefix + "right");
        if (!MathUtil.isMinValue(rightValue))
        {
            setRight(rightValue);
        }
        double bottomValue = declarations.getLength(mEdgeCssPropertyPrefix + "bottom");
        if (!MathUtil.isMinValue(bottomValue))
        {
            setBottom(bottomValue);
        }
        double leftValue = declarations.getLength(mEdgeCssPropertyPrefix + "left");
        if (!MathUtil.isMinValue(leftValue))
        {
            setLeft(leftValue);
        }
    }

    double getTop() { return mTop; }
    void setTop(double value)
    {
        mTop = value;
        mUseTop = true;
    }

    boolean getUseTop() { return mUseTop; }
    void setUseTop(boolean value) { mUseTop = value; }

    double getRight() { return mRight; }
    void setRight(double value)
    {
        mRight = value;
        mUseRight = true;
    }

    boolean getUseRight() { return mUseRight; }
    void setUseRight(boolean value) { mUseRight = value; }

    double getBottom() { return mBottom; }
    void setBottom(double value)
    {
        mBottom = value;
        mUseBottom = true;
    }

    boolean getUseBottom() { return mUseBottom; }
    void setUseBottom(boolean value) { mUseBottom = value; }

    double getLeft() { return mLeft; }
    void setLeft(double value)
    {
        mLeft = value;
        mUseLeft = true;
    }

    boolean getUseLeft() { return mUseLeft; }
    void setUseLeft(boolean value) { mUseLeft = value; }

    boolean getUseAll() { return mUseTop && mUseRight && mUseBottom && mUseLeft; }

    private /*final*/ String mEdgeCssPropertyPrefix;
    private double mTop;
    private boolean mUseTop;
    private double mRight;
    private boolean mUseRight;
    private double mBottom;
    private boolean mUseBottom;
    private double mLeft;
    private boolean mUseLeft;
}

