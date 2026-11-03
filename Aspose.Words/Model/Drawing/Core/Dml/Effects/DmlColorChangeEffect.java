// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/05/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// 20.1.8.16 clrChange (Color Change Effect)
/// This element specifies a Color Change Effect.
/// Instances of clrFrom are replaced with instances of clrTo.
/// </summary>
class DmlColorChangeEffect extends DmlEffect
{
    /// <summary>
    /// Gets the type of effects.
    /// </summary>
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.COLOR_CHANGE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlColorChangeEffect value = (DmlColorChangeEffect)obj;

        return (value.getConsiderAlphaValues() == getConsiderAlphaValues()) &&
               msObject.equals(value.getSourceColor(), getSourceColor()) &&
               msObject.equals(value.getDestinationColor(), getDestinationColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getSourceColor() != null)
            hash ^= getSourceColor().hashCode();
        if (getDestinationColor() != null)
            hash ^= getDestinationColor().hashCode();
        hash ^= msNumber.booleanHashCode(getConsiderAlphaValues());
        return hash;
    }

    DmlColor getSourceColor() { return mSourceColor; }
    void setSourceColor(DmlColor value) { mSourceColor = value; }

    DmlColor getDestinationColor() { return mDestinationColor; }
    void setDestinationColor(DmlColor value) { mDestinationColor = value; }

    /// <summary>
    /// Specifies whether alpha values are considered for the effect. 
    /// Effect alpha values are considered if useA is true, else they are ignored.
    /// </summary>
    boolean getConsiderAlphaValues() { return mConsiderAlphaValues; }
    void setConsiderAlphaValues(boolean value) { mConsiderAlphaValues = value; }

    private DmlColor mSourceColor;
    private DmlColor mDestinationColor;
    private boolean mConsiderAlphaValues;
}

