// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for:
/// 20.1.2.2.25 nvCxnSpPr (Non-Visual Properties for a Connection Shape)
/// 20.1.2.2.26 nvGraphicFramePr (Non-Visual Properties for a Graphic Frame)
/// 20.1.2.2.27 nvGrpSpPr (Non-Visual Properties for a Group Shape)
/// 20.1.2.2.28 nvPicPr (Non-Visual Properties for a Picture)
/// 20.1.2.2.29 nvSpPr (Non-Visual Properties for a Shape)
/// </summary>
abstract class DmlNvPrBase implements Cloneable
{
    abstract /*DmlNvHolder*/int getHolder();

    DmlNvPrBase deepClone()
    {
        DmlNvPrBase lhs = (DmlNvPrBase)memberwiseClone();
        if (mNvDrawingProperties != null)
            lhs.mNvDrawingProperties = mNvDrawingProperties.deepClone();
        if (mCNvProperties != null)
            lhs.mCNvProperties = mCNvProperties.deepClone();

        return lhs;
    }

    DmlCnvPrBase getCNvProperties() { return mCNvProperties; }
    void setCNvProperties(DmlCnvPrBase value) { mCNvProperties = value; }

    DmlNvDrawingProperties getNvDrawingProperties() { return mNvDrawingProperties; }
    void setNvDrawingProperties(DmlNvDrawingProperties value) { mNvDrawingProperties = value; }

    private DmlNvDrawingProperties mNvDrawingProperties;
    private DmlCnvPrBase mCNvProperties;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

