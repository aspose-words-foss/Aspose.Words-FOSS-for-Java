// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/13/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 5.7.2.30 clrMapOvr (Color Map Override)
/// This element represents color mapping information. It is used to override the applications color mapping if the
/// user has selected keep source formatting after a copy-paste.
/// </summary>
class DmlChartColorMapOverride implements Cloneable
{
    DmlChartColorMapOverride deepClone()
    {
        return (DmlChartColorMapOverride)memberwiseClone();
    }

    String Bg1;
    String Tx1;
    String Bg2;
    String Tx2;
    String Accent1;
    String Accent2;
    String Accent3;
    String Accent4;
    String Accent5;
    String Accent6;
    String Hlink;
    String FolHlink;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

