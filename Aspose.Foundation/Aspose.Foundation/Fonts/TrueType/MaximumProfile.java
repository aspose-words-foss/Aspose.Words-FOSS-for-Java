// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/05/2006 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// maxp - Maximum Profile 
/// 
/// This table establishes the memory requirements for this font. 
/// </summary>
class MaximumProfile extends TTTable
{
    /// <summary>
    /// 0x00005000 for version 0.5 
    /// 0x00010000 for version 1.0
    /// </summary>
    /*uint*/int Version;
    /// <summary>
    /// The number of glyphs in the font.
    /// </summary>
    /*ushort*/short NumGlyphs;
    /// <summary>
    /// Maximum points in a non-composite glyph.
    /// </summary>
    /*ushort*/short MaxPoints;
    /// <summary>
    /// Maximum contours in a non-composite glyph.
    /// </summary>
    /*ushort*/short MaxContours;
    /// <summary>
    /// Maximum points in a composite glyph.
    /// </summary>
    /*ushort*/short MaxCompositePoints;
    /// <summary>
    /// Maximum contours in a composite glyph.
    /// </summary>
    /*ushort*/short MaxCompositeContours;
    /// <summary>
    /// 1 if instructions do not use the twilight zone (Z0), or 2 if instructions do use Z0; 
    /// should be set to 2 in most cases.
    /// </summary>
    /*ushort*/short MaxZones;
    /// <summary>
    /// Maximum points used in Z0.
    /// </summary>
    /*ushort*/short MaxTwilightPoints;
    /// <summary>
    /// Number of Storage Area locations. 
    /// </summary>
    /*ushort*/short MaxStorage;
    /// <summary>
    /// Number of FDEFs.
    /// </summary>
    /*ushort*/short MaxFunctionDefs;
    /// <summary>
    /// Number of IDEFs.
    /// </summary>
    /*ushort*/short MaxInstructionDefs;
    /// <summary>
    /// Maximum stack depth.
    /// </summary>
    /*ushort*/short MaxStackElements;
    /// <summary>
    /// Maximum byte count for glyph instructions.
    /// </summary>
    /*ushort*/short MaxSizeOfInstructions;
    /// <summary>
    /// Maximum number of components referenced at "top level" for any composite glyph.
    /// </summary>
    /*ushort*/short MaxComponentElements;
    /// <summary>
    /// Maximum levels of recursion; 1 for simple components.
    /// </summary>
    /*ushort*/short MaxComponentDepth;

    /// <summary>
    /// Ctor. Reads the table.
    /// </summary>
    MaximumProfile(BigEndianBinaryReader reader) throws Exception
    {
        Version = reader.readUInt32();
        NumGlyphs = reader.readUInt16();

        if (Version == 0x00005000)
            return;

        if (Version != 0x00010000)
            throw new UnsupportedOperationException("Unsupported maximum profile version.");

        MaxPoints = reader.readUInt16();
        MaxContours = reader.readUInt16();
        MaxCompositePoints = reader.readUInt16();
        MaxCompositeContours = reader.readUInt16();

        MaxZones = reader.readUInt16();
        MaxTwilightPoints = reader.readUInt16();
        MaxStorage = reader.readUInt16();
        MaxFunctionDefs = reader.readUInt16();

        MaxInstructionDefs = reader.readUInt16();
        MaxStackElements = reader.readUInt16();
        MaxSizeOfInstructions = reader.readUInt16();
        MaxComponentElements = reader.readUInt16();
        MaxComponentDepth = reader.readUInt16();
    }

    /// <summary>
    /// Writes the table.
    /// </summary>
    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        writer.writeUInt32(Version);
        writer.writeInt16((NumGlyphs & 0xFFFF));

        if (Version == 0x00005000)
            return;

        writer.writeInt16((MaxPoints & 0xFFFF));
        writer.writeInt16((MaxContours & 0xFFFF));
        writer.writeInt16((MaxCompositePoints & 0xFFFF));
        writer.writeInt16((MaxCompositeContours & 0xFFFF));

        writer.writeInt16((MaxZones & 0xFFFF));
        writer.writeInt16((MaxTwilightPoints & 0xFFFF));
        writer.writeInt16((MaxStorage & 0xFFFF));
        writer.writeInt16((MaxFunctionDefs & 0xFFFF));

        writer.writeInt16((MaxInstructionDefs & 0xFFFF));
        writer.writeInt16((MaxStackElements & 0xFFFF));
        writer.writeInt16((MaxSizeOfInstructions & 0xFFFF));
        writer.writeInt16((MaxComponentElements & 0xFFFF));
        writer.writeInt16((MaxComponentDepth & 0xFFFF));
    }
}

