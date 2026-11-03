// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents fsType field in 'OS/2' table.
/// </summary>
public class FontFsType
{
    public FontFsType(/*ushort*/short value)
    {
        mValue = value;
        mPermissions = parsePermissions(value);
        mNoSubsetting = ((value & 0xFFFF) & NO_SUBSETTING_MASK) != 0;
        mBitmapEmbeddingOnly = ((value & 0xFFFF) & BITMAP_EMBEDDING_ONLY_MASK) != 0;
    }

    private static /*FontFsTypePermissions*/int parsePermissions(/*ushort*/short fsType)
    {
        // Quote from spec:
        // this version of the OS/2 table makes bits 0 - 3 a set of exclusive bits.
        // in the event that more than one of bits 0 to 3 are set in a given font, then the least-restrictive
        // permission indicated take precedence.
        if (((fsType & 0xFFFF) & EDITABLE_EMBEDDING_MASK) != 0)
            return FontFsTypePermissions.EDITABLE;
        if (((fsType & 0xFFFF) & PREVIEW_AND_PRINT_MASK) != 0)
            return FontFsTypePermissions.PRINT_AND_PREVIEW;
        if (((fsType & 0xFFFF) & RESTRICTED_LICENSE_MASK) != 0)
            return FontFsTypePermissions.RESTRICTED_LICENSE;
        return FontFsTypePermissions.INSTALLABLE;
    }

    public /*ushort*/short getValue() { return mValue; };

    private  /*ushort*/short mValue;
    public /*FontFsTypePermissions*/int getPermissions() { return mPermissions; };

    private /*FontFsTypePermissions*/int mPermissions;
    public boolean getNoSubsetting() { return mNoSubsetting; };

    private  boolean mNoSubsetting;
    public boolean getBitmapEmbeddingOnly() { return mBitmapEmbeddingOnly; };

    private  boolean mBitmapEmbeddingOnly;

    private static final int RESTRICTED_LICENSE_MASK = 0x0002;
    private static final int PREVIEW_AND_PRINT_MASK = 0x0004;
    private static final int EDITABLE_EMBEDDING_MASK = 0x0008;
    private static final int NO_SUBSETTING_MASK = 0x0100;
    private static final int BITMAP_EMBEDDING_ONLY_MASK = 0x0200;
}

