// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/04/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Contains CTF encoded data.
/// </summary>
/// <remarks>
/// In CTF format all font tables are separated into the three parts: FontTable, PushData and Instructions.
/// This class represents these three data arrays.
/// Note, that FontTable part can be used not only for a 'glyph' table, but also for a union of all font tables.
/// This depends on a place where it is used.
/// </remarks>
class CtfEncodedData
{
    /// <summary>
    /// Font table(s) data.
    /// </summary>
    byte[] getFontTable() { return mFontTable; }
    void setFontTable(byte[] value) { mFontTable = value; }

    /// <summary>
    /// Push data.
    /// </summary>
    byte[] getPushData() { return mPushData; }
    void setPushData(byte[] value) { mPushData = value; }

    /// <summary>
    /// Instructions data.
    /// </summary>
    byte[] getInstructions() { return mInstructions; }
    void setInstructions(byte[] value) { mInstructions = value; }

    private byte[] mFontTable;
    private byte[] mPushData;
    private byte[] mInstructions;
}

