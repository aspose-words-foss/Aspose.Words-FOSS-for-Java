// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/17/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;


class DmlColorReplaceEffect extends DmlEffect implements Cloneable
{
    /*override*/ /*DmlEffectType*/int getType() { return DmlEffectType.COLOR_REPLACE; }

    public /*override*/ boolean equals(Object obj)
    {
        if (!super.equals(obj))
            return false;

        DmlColorReplaceEffect value = (DmlColorReplaceEffect)obj;

        return msObject.equals(value.getColor(), getColor());
    }

    public /*override*/ int hashCode()
    {
        int hash = super.hashCode();
        if (getColor() != null)
            hash ^= getColor().hashCode();
        return hash;
    }

    /*override*/ DmlEffect deepClone()
    {
        DmlColorReplaceEffect lhs = (DmlColorReplaceEffect)memberwiseClone();

        if (mColor != null)
            lhs.mColor = mColor.deepClone();

        return lhs;
    }

    DmlColor getColor() { return mColor; }
    void setColor(DmlColor value) { mColor = value; }

    private DmlColor mColor;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

