// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for typed TrueType/OpenType physical font data.
/// </summary>
public class PhysicalFontData
{
    public PhysicalFontData(IFontData fileData)
    {
        mFileData = fileData;
    }

    public IFontData getFileData() { return mFileData; };

    private IFontData mFileData;

    public /*virtual*/ boolean isTtc() { return false; }
}

