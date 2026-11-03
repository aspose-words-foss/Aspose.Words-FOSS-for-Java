// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2016 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Helper class that encapsulates all three binary readers for <see cref="CtfGlyphDecoder"/>.
/// </summary>
class CtfGlyphDecoderContext
{
    /// <summary>
    /// ctor.
    /// </summary>
    CtfGlyphDecoderContext(MemoryStream glyphTableData, MemoryStream pushData, MemoryStream instructions)
    {
        mGlyphTableReader = new BigEndianBinaryReader(glyphTableData);
        mPushDataReader = new BigEndianBinaryReader(pushData);
        mInstructionsReader = new BigEndianBinaryReader(instructions);
    }

    /// <summary>
    /// Glyph table data reader.
    /// </summary>
    BigEndianBinaryReader getGlyphTableReader() { return mGlyphTableReader; }

    /// <summary>
    /// PushData reader.
    /// </summary>
    BigEndianBinaryReader getPushDataReader() { return mPushDataReader; }

    /// <summary>
    /// Instructions reader.
    /// </summary>
    BigEndianBinaryReader getInstructionsReader() { return mInstructionsReader; }

    private /*final*/ BigEndianBinaryReader mGlyphTableReader;
    private /*final*/ BigEndianBinaryReader mPushDataReader;
    private /*final*/ BigEndianBinaryReader mInstructionsReader;
}

