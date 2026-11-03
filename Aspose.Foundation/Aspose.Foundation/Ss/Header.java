// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Header of a structured storage.
/// </summary>
class Header
{
    Header()
    {
        MinorVersion = (short) 33;
        MajorVersion = SUPPORTED_MAJOR_VERSION;
        SectorShift = (short) 9;        //512 byte sectors
        MiniSectorShift = (short) 6;    //64 byte sectors
        MiniSectorCutoff = 4096;
    }

    Header(BinaryReader reader) throws Exception
    {
        long sig = reader.readInt64();
        if (sig != SIGNATURE)
            throw new IllegalStateException("This is not a structured storage file.");

        reader.readBytes(CLSID_LENGTH);    //Skip CLSID because it must be zero.
        MinorVersion = reader.readUInt16();
        MajorVersion = reader.readUInt16();
        if ((MajorVersion & 0xFFFF) > SUPPORTED_MAJOR_VERSION)
            throw new UnsupportedOperationException("This structured storage version is not supported.");

        reader.readUInt16();    //byte order
        SectorShift = reader.readUInt16();
        MiniSectorShift = reader.readUInt16();
        reader.readUInt16();    //reserved
        reader.readUInt32();    //reserved
        CsectDir = reader.readInt32();
        CsectFat = reader.readInt32();
        SectDirStart = reader.readUInt32();
        reader.readUInt32();    //signature
        MiniSectorCutoff = reader.readUInt32();
        SectMiniFatStart = reader.readUInt32();
        CsectMiniFat = reader.readInt32();
        SectDifStart = reader.readUInt32();
        CsectDif = reader.readInt32();
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt64((long)SIGNATURE);
        writer.writeBytes(new byte[CLSID_LENGTH]);
        writer.writeUInt16((/*ushort*/short)(MinorVersion & 0xFFFF));
        writer.writeUInt16((/*ushort*/short)(MajorVersion & 0xFFFF));
        writer.writeUInt16((/*ushort*/short)0xfffe);    //byte order
        writer.writeUInt16((/*ushort*/short)(SectorShift & 0xFFFF));
        writer.writeUInt16((/*ushort*/short)(MiniSectorShift & 0xFFFF));
        writer.writeInt16((short)0);        //reserved
        writer.writeInt32((int)0);        //reserved
        writer.writeInt32((int)CsectDir);
        writer.writeInt32((int)CsectFat);
        writer.writeUInt32((/*uint*/int)(SectDirStart & 0xFFFFFFFFL));
        writer.writeInt32((int)0);        //signature
        writer.writeUInt32((/*uint*/int)(MiniSectorCutoff & 0xFFFFFFFFL));
        writer.writeUInt32((/*uint*/int)(SectMiniFatStart & 0xFFFFFFFFL));
        writer.writeInt32((int)CsectMiniFat);
        writer.writeUInt32((/*uint*/int)(SectDifStart & 0xFFFFFFFFL));
        writer.writeInt32((int)CsectDif);
    }

    /// <summary>
    /// Returns true if the specified stream size is to be written into fat or into mini fat.
    /// </summary>
    boolean isBigEnoughForFat(long streamLength)
    {
        return (streamLength >= (MiniSectorCutoff & 0xFFFFFFFFL));
    }

    int getMiniFatLength() { return CsectMiniFat * Sector.SECTOR_SIZE; }

    /// <summary>
    /// minor version of the format: 33 is written by reference implementation
    /// </summary>
    /*ushort*/short MinorVersion;

    /// <summary>
    /// major version of the dll/format: 3 for 512-byte sectors, 4 for 4 KB sectors
    /// </summary>
    /*ushort*/short MajorVersion;

    /// <summary>
    /// size of sectors in power-of-two; typically 9 indicating 512-byte sectors
    /// </summary>
    /*ushort*/short SectorShift;

    /// <summary>
    /// size of mini-sectors in power-of-two; typically 6 indicating 64-byte mini-sectors
    /// </summary>
    /*ushort*/short MiniSectorShift;

    /// <summary>
    /// must be zero for 512-byte sectors, number of SECTs in directory chain for 4 KB sectors
    /// </summary>
    int CsectDir;

    /// <summary>
    /// number of SECTs in the FAT chain
    /// </summary>
    int CsectFat;

    /// <summary>
    /// first SECT in the directory chain
    /// </summary>
    /*uint*/int SectDirStart;

    /// <summary>
    /// maximum size for a mini stream; typically 4096 bytes
    /// </summary>
    /*uint*/int MiniSectorCutoff;

    /// <summary>
    /// first SECT in the MiniFAT chain
    /// </summary>
    /*uint*/int SectMiniFatStart;

    /// <summary>
    /// number of SECTs in the MiniFAT chain
    /// </summary>
    int CsectMiniFat;

    /// <summary>
    /// first SECT in the DIFAT chain
    /// </summary>
    /*uint*/int SectDifStart;

    /// <summary>
    /// number of SECTs in the DIFAT chain
    /// </summary>
    int CsectDif;

    /// <summary>
    /// Max number of DIFAT records in the header sector.
    /// </summary>
    static final int MAX_DIFAT_ENTRIES_IN_HEADER = 109;
    /// <summary>
    /// Fat sectors start in the header at this offset.
    /// </summary>
    static final int DIFAT_IN_HEADER_OFFSET = 0x4c;

    /// <summary>
    /// Version 3 uses 512 byte sectors. Version 4 was introduced in Windows 2000 and uses 4KB sectors.
    /// MS Word uses Version 3 so we can throw everything else.
    /// </summary>
    private static final int SUPPORTED_MAJOR_VERSION = 3;
    private static final long SIGNATURE = /*unchecked*/((long)0xe11ab1a1e011cfd0L);
    private static final int CLSID_LENGTH = 16;
}

