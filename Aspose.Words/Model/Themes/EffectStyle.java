// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/11/2015 by Nikolay Eryomin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 20.1.4.1.11 effectStyle (Effect Style)
/// This element defines a set of effects and 3D properties that can be applied to an object.
/// </summary>
class EffectStyle
{
    /// <summary>
    /// Defines optional scene-level 3D properties to apply to an object.
    /// </summary>
    DmlScene3DProperties getScene3DProperties() { return mScene3DProperties; }
    void setScene3DProperties(DmlScene3DProperties value) { mScene3DProperties = value; }

    /// <summary>
    /// Defines the 3D properties to apply to an object.
    /// </summary>
    DmlShape3DProperties getShape3DProperties() { return mShape3DProperties; }
    void setShape3DProperties(DmlShape3DProperties value) { mShape3DProperties = value; }

    /// <summary>
    /// Collection of effects applied to an object.
    /// </summary>
    DmlShapeEffectsCollection getEffects() { return mEffects; }
    void setEffects(DmlShapeEffectsCollection value) { mEffects = value; }

    /*virtual*/ EffectStyle deepClone()
    {
        EffectStyle newEffectStyle = new EffectStyle();

        if (mScene3DProperties != null)
            newEffectStyle.mScene3DProperties = mScene3DProperties.deepClone();

        if (mShape3DProperties != null)
            newEffectStyle.mShape3DProperties = mShape3DProperties.deepClone();

        if (mEffects != null)
            newEffectStyle.mEffects = mEffects.deepClone();

        return newEffectStyle;
    }
    
    private DmlScene3DProperties mScene3DProperties;
    private DmlShape3DProperties mShape3DProperties;
    private DmlShapeEffectsCollection mEffects;
}

