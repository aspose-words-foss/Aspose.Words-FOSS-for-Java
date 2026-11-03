// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

// NOTE! _Aspose prefix is added for autoporter. DashStyle class name clashes in JAVA without this prefex. Please do not remove it.

/// <summary>
/// 20.1.8.48 prstDash (Preset Dash)
/// This element specifies that a preset line dashing scheme should be used.
/// </summary>
class DmlPresetDash extends DmlDash
{
    /*override*/ DmlDash deepClone()
    {
        DmlPresetDash result = new DmlPresetDash();
        result.setPreset(getPreset());
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlPresetDash value = (DmlPresetDash)obj;

        return (value.getDashType() == getDashType()) && (value.getPreset() == getPreset());
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getDashType();
        hash ^= /*enum.hashCode()=(int)value*/getPreset();
        return hash;
    }

    /// <summary>
    /// Specifies which preset dashing scheme is to be used.
    /// </summary>
    /*DashStyle*/int getPreset() { return mPreset; }
    void setPreset(/*DashStyle*/int value) { mPreset = value; }

    /*override*/ /*DmlDashType*/int getDashType() { return DmlDashType.PRESET_DASH; }

    private static /*final*/ float[] G_DASH_DOT_PATTERN = new float[] {4f, 3f, 1f, 3f};
    private static /*final*/ float[] G_DASH_PATTERN = new float[] {4f, 3f};
    private static /*final*/ float[] G_DOT_PATTERN = new float[] {1f, 3f};
    private static /*final*/ float[] G_LARGE_DASH_DOT_DOT_PATTERN = new float[] {8f, 3f, 1f, 3f, 1f, 3f};
    private static /*final*/ float[] G_LARGE_DASH_DOT_PATTERN = new float[] {8f, 3f, 1f, 3f};
    private static /*final*/ float[] G_LARGE_DASH_PATTERN = new float[] {8f, 3f};
    private /*DashStyle*/int mPreset = com.aspose.words.DashStyle.SOLID;
}

