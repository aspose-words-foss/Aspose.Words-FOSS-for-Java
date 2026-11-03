// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.4.2.8 effectRef (Effect Reference)
/// This element defines a reference to an effect style within the style matrix. 
/// </summary>
class DmlEffectReference extends DmlStyleReferenceBase
{
    /// <summary>
    /// Specifies the style matrix index of the style referred to.
    /// The content is a restriction of the W3C XML Schema unsignedInt datatype.
    /// </summary>
    int getStyleMatrixIndex() { return mStyleMatrixIndex; }
    void setStyleMatrixIndex(int value) { mStyleMatrixIndex = value; }

    private int mStyleMatrixIndex;

    public EffectStyle getEffectStyle(IThemeProvider theme)
    {
        // Zero index disables effects.
        if ((getStyleMatrixIndex() == 0) || (theme == null))
            return null;

        // In theme effect references have zero based indexes, but in document one based. 
        return theme.getEffectStyle(getStyleMatrixIndex() - 1);
    }
}

