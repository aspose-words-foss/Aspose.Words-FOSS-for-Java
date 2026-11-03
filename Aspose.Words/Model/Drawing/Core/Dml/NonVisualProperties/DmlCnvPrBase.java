// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for:
/// 20.1.2.2.4 cNvCxnSpPr (Non-Visual Connector Shape Drawing Properties)
/// 20.1.2.2.5 cNvGraphicFramePr (Non-Visual Graphic Frame Drawing Properties)
/// 20.1.2.2.6 cNvGrpSpPr (Non-Visual Group Shape Drawing Properties)
/// 20.1.2.2.7 cNvPicPr (Non-Visual Picture Drawing Properties)
/// 20.1.2.2.9 cNvSpPr (Non-Visual Shape Drawing Properties)
/// </summary>
abstract class DmlCnvPrBase extends DmlExtensionListSource implements Cloneable
{
    DmlCnvPrBase deepClone()
    {
        DmlCnvPrBase lhs = (DmlCnvPrBase)memberwiseClone();
        lhs.mLocks = mLocks.deepClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;
    }

    abstract /*DmlNvHolder*/int getHolder();

    DmlLocks getLocks() { return mLocks; }

    private DmlLocks mLocks = new DmlLocks();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }        
}

