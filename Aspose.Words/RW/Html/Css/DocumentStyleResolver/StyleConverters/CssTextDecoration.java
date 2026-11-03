// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.NullableBool;
import com.aspose.DrColor;
import com.aspose.Debug;


/// <summary>
/// Stores 'text-decoration' CSS value. Allows to combine decorations.
/// </summary>
class CssTextDecoration
{
    CssTextDecoration()
    {
        // Empty constructor.
    }

    private CssTextDecoration(
        /*NullableBool*/byte underline,
        /*NullableBool*/byte strikeThrough,
        DrColor underlineColor)
    {
        mUnderline = underline;
        mStrikeThrough = strikeThrough;
        mUnderlineColor = underlineColor;
    }

    CssTextDecoration apply(CssDeclarationCollection declarations, boolean clearOnNone)
    {
        /*NullableBool*/byte underline = mUnderline;
        /*NullableBool*/byte strikeThrough = mStrikeThrough;
        DrColor underlineColor = mUnderlineColor;

        // 'text-decoration: none' is not propagated to inner elements so we don't propagate False values.
        if (underline == NullableBool.FALSE)
        {
            underline = NullableBool.NOT_DEFINED;
        }
        if (strikeThrough == NullableBool.FALSE)
        {
            strikeThrough = NullableBool.NOT_DEFINED;
        }

        CssTextDecorationPropertyValue textDecorationValue = getTextDecoration(declarations);
        if (textDecorationValue != null)
        {
            if (textDecorationValue.isNone())
            {
                if (clearOnNone)
                {
                    underline = NullableBool.FALSE;
                    underlineColor = null;
                    strikeThrough = NullableBool.FALSE;
                }
            }
            else
            {
                if (textDecorationValue.isUnderline())
                {
                    underline = NullableBool.TRUE;
                    underlineColor = declarations.getColor("color");
                }
                if (textDecorationValue.isLineThrough())
                {
                    strikeThrough = NullableBool.TRUE;
                }
            }
        }

        return new CssTextDecoration(underline, strikeThrough, underlineColor);
    }

    /// <summary>
    /// Creates a copy of this text decoration instance but with underline style and color removed.
    /// </summary>
    CssTextDecoration withoutUnderline()
    {
        return new CssTextDecoration(NullableBool.NOT_DEFINED, mStrikeThrough, null);
    }

    /*NullableBool*/byte getUnderline() { return mUnderline; }

    DrColor getUnderlineColor() { return mUnderlineColor; }

    /*NullableBool*/byte getStrikeThrough() { return mStrikeThrough; }

    private static CssTextDecorationPropertyValue getTextDecoration(CssDeclarationCollection declarations)
    {
        Debug.assertTrue(declarations != null);

        CssDeclaration declaration = declarations.get("text-decoration");
        if (declaration == null)
        {
            return null;
        }

        CssTextDecorationPropertyValue textDeclarationValue = ms.as(declaration.getValue(), CssTextDecorationPropertyValue.class);
        return (textDeclarationValue != null)
            ? textDeclarationValue
            : null;
    }

    private /*final*/ /*NullableBool*/byte mUnderline;
    private /*final*/ DrColor mUnderlineColor;
    private /*final*/ /*NullableBool*/byte mStrikeThrough;
}

