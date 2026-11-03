// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2012 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents 5.7.2.229 view3D (View In 3D) element.
/// This element specifies the 3-D view of the chart. 
/// </summary>
class DmlChartView3D extends DmlExtensionListSource implements Cloneable
{
    DmlChartView3D deepClone()
    {
        DmlChartView3D lhs = (DmlChartView3D)memberwiseClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;

    }

    /// <summary>
    /// Valid values are integers between 20 and 2000 inclusive.
    /// </summary>
    int getDepthPercent() { return mDepthPercent; }
    void setDepthPercent(int value)
    {
        if (value >= 20 && value <= 2000)
            mDepthPercent = value;
    }

    /// <summary>
    /// Valid values are integers between 5 and 500 inclusive.
    /// </summary>
    int getHPercent() { return mHPercent; }
    void setHPercent(int value)
    {
        if (value >= 5 && value <= 500)
            mHPercent = value;
    }

    /// <summary>
    /// Valid values are integers between 0 and 100 inclusive.
    /// </summary>
    int getPerspective() { return mPerspective; }
    void setPerspective(int value)
    {
        if (value >= 0 && value <= 100)
            mPerspective = value;
    }

    /// <summary>
    /// Specifies that the chart axes are at right angles, rather than drawn in perspective.
    /// </summary>
    boolean getRAngAx() { return mRAngAx; }
    void setRAngAx(boolean value) { mRAngAx = value; }

    /// <summary>
    /// Valid values are integers between -90 and 90 inclusive.
    /// By specification default value is zero, however in MS Word if this property is omitted, it uses value 15 degrees.
    /// Note: rotation X and Y in MS Word UI are switched.
    /// </summary>
    int getRotX() { return mRotX; }
    void setRotX(int value)
    {
        if (value >= -90 && value <= 90)
            mRotX = value;
    }

    /// <summary>
    /// Valid values are integers between 0 and 360 inclusive.
    /// By specification default value is zero, however in MS Word if this property is omitted, it uses value 20 degrees.
    /// Note: rotation X and Y in MS Word UI are switched.
    /// </summary>
    int getRotY() { return mRotY; }
    void setRotY(int value)
    {
        mHasRotY = true;
        if (value >= 0 && value <= 360)
            mRotY = value;
    }

    boolean hasRotY() { return mHasRotY; }

    private int mDepthPercent = DEFAULT_DEPTH_PERCENT;
    private int mHPercent = DEFAULT_H_PERCENT;
    private int mPerspective = DEFAULT_PERSPECTIVE;
    private int mRotX = DEFAULT_ROT_X;
    private int mRotY = DEFAULT_ROT_Y;
    private boolean mRAngAx;
    private boolean mHasRotY;

    static final int DEFAULT_DEPTH_PERCENT = 100;
    static final int DEFAULT_H_PERCENT = 100;
    static final int DEFAULT_PERSPECTIVE = 30;
    static final int DEFAULT_ROT_X = 15;
    static final int DEFAULT_ROT_Y = 20;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

