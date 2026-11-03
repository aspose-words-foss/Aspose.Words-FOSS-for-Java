// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents glyph point in CTF Glyph Table.
/// </summary>
class CtfGlyphPoint
{
    /// <summary>
    /// Reads next glyph point from binary reader utilizing previously read flag value.
    /// </summary>
    public static CtfGlyphPoint read(BigEndianBinaryReader reader, byte flag) throws Exception
    {
        CtfGlyphPoint point = new CtfGlyphPoint();

        TripletEncoding.readTriplet(reader, flag, point);

        return point;
    }

    public short X;
    public short Y;
    public boolean IsOnCurve;
}

