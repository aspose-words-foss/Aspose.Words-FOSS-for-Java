// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.8.34 grayscl (Gray Scale Effect)
/// This element specifies a gray scale effect. 
/// Converts all effect color values to a shade of gray, 
/// corresponding to their luminance. Effect alpha (opacity) values are unaffected.
/// </summary>
class DmlGrayScaleEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.GRAY_SCALE; }
}
