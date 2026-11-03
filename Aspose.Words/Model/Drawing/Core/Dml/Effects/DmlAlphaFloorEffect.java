// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.8.3 alphaFloor (Alpha Floor Effect)
/// This element represents an alpha floor effect.
/// Alpha (opacity) values less than 100% are changed to zero. 
/// In other words, anything partially transparent becomes fully transparent.
/// </summary>
class DmlAlphaFloorEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.ALPHA_FLOOR; }
}
