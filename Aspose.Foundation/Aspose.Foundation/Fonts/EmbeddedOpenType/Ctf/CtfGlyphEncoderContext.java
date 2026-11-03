// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Helper class that encapsulates all three binary writers for <see cref="CtfGlyphEncoder"/>.
/// </summary>
class CtfGlyphEncoderContext
{
    /// <summary>
    /// ctor.
    /// </summary>
    CtfGlyphEncoderContext(MemoryStream glyphTable, MemoryStream pushData, MemoryStream instructions) throws Exception
    {
        mGlyphTableWriter = new BigEndianBinaryWriter(glyphTable);
        mPushDataWriter = new BigEndianBinaryWriter(pushData);
        mInstructionsWriter = new BigEndianBinaryWriter(instructions);
    }

    /// <summary>
    /// Glyph table data writer.
    /// </summary>
    BigEndianBinaryWriter getGlyphTableWriter() { return mGlyphTableWriter; }

    /// <summary>
    /// PushData writer.
    /// </summary>
    BigEndianBinaryWriter getPushDataWriter() { return mPushDataWriter; }

    /// <summary>
    /// Instructions writer.
    /// </summary>
    BigEndianBinaryWriter getInstructionsWriter() { return mInstructionsWriter; }

    private /*final*/ BigEndianBinaryWriter mGlyphTableWriter;
    private /*final*/ BigEndianBinaryWriter mPushDataWriter;
    private /*final*/ BigEndianBinaryWriter mInstructionsWriter;
}

