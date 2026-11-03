// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/30/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Class simplifies access to effects applied through theme reference.
/// </summary>
class DmlEffectsStyleResolver
{
    DmlEffectsStyleResolver(DmlShapeStyle style, IThemeProvider theme)
    {
        mStyle = style;
        mTheme = theme;
    }

    private EffectStyle getEffectStyle()
    {
        if ((mEffectStyle == null) && (mStyle != null))
            mEffectStyle = mStyle.getEffectStyle(mTheme);

        return mEffectStyle;
    }

    public DmlScene3DProperties getScene3DProperties() { return (getEffectStyle() == null) ? null : getEffectStyle().getScene3DProperties(); }

    public DmlShape3DProperties getShape3DProperties() { return (getEffectStyle() == null) ? null : getEffectStyle().getShape3DProperties(); }

    public DmlShapeEffectsCollection getEffects() { return (getEffectStyle() == null) ? null : getEffectStyle().getEffects(); }

    private EffectStyle mEffectStyle;
    private /*final*/ DmlShapeStyle mStyle;
    private /*final*/ IThemeProvider mTheme;
}

