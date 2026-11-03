// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.MathUtil;


/// <summary>
/// Stores border information.
/// </summary>
class BorderInfo
{
    BorderInfo(
        CssDeclarationCollection containerDeclarations,
        /*BorderType*/int borderType,
        String paddingPropertyName)
    {
        Debug.assertTrue(containerDeclarations != null);
        Debug.assertTrue(StringUtil.hasChars(paddingPropertyName));

        mBorder = new Border();
        CssBorderStyleConverter.toModelBorder(containerDeclarations, borderType, mBorder);

        mPadding = containerDeclarations.getLength(paddingPropertyName);
    }

    void applyBorderPropertiesTo(Border border)
    {
        Debug.assertTrue(border != null);
        if (mBorder.isVisible())
        {
            border.setLineStyle(mBorder.getLineStyle());
            border.setLineWidth(mBorder.getLineWidth());
            border.setColorInternal(mBorder.getColorInternal());
        }
    }

    void applyPaddingTo(Border border)
    {
        Debug.assertTrue(border != null);
        if (hasPadding())
        {
            border.setDistanceFromTextSafe(mPadding);
        }
    }

    boolean isVisible() { return mBorder.isVisible(); }

    /*LineStyle*/int getLineStyle() { return mBorder.getLineStyle(); }

    boolean hasPadding() { return !MathUtil.isMinValue(mPadding); }

    double getPadding()
    {
        return (hasPadding())
            ? mPadding
            : 0.0;
    }

    private /*final*/ Border mBorder;

    private /*final*/ double mPadding;
}

