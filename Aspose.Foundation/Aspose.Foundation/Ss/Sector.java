// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Structured storage sector related constants and functions.
/// </summary>
/*static*/ class Sector
{
	/* Simulation of static class by using private constructor */
	private Sector()
	{}

    /// <summary>
    /// Converts sector index into offset in the structured storage.
    /// </summary>
    static long sectorToOffset(/*uint*/int sect, int sectorSize)
    {
        return sectorToOffset(sect, sectorSize == SECTOR_SIZE);
    }

    /// <summary>
    /// Converts sector index into offset in the structured storage.
    /// </summary>
    static long sectorToOffset(/*uint*/int sect, boolean isNormalSector)
    {
        // Casting to uint added to resolve WORDSJAVA-2007 and WORDSJAVA-2014:
        // This method used to calculate Stream Position. But AWJ uses ms MemoryStream internally 
        // when a doc is loaded from Java's InputStream. And the position in the MemoryStream  
        // should be smaller than int.Max-1.
        if (isNormalSector)
            return (((/*uint*/int) (((sect & 0xFFFFFFFFL) + 1) * SECTOR_SIZE)) & 0xFFFFFFFFL);
        else
            return (((/*uint*/int) ((sect & 0xFFFFFFFFL) * MINI_SECTOR_SIZE)) & 0xFFFFFFFFL);
    }

    static /*uint*/int offsetToSector(long offset, boolean isNormalSector)
    {
        if (isNormalSector)
            return (/*uint*/int)((offset / SECTOR_SIZE) - 1);
        else
            return (/*uint*/int)(offset / MINI_SECTOR_SIZE);
    }

    /// <summary>
    /// Writes free sector fat entries to pad to sector size.
    /// </summary>
    static void padFreeSect(BinaryWriter writer) throws Exception
    {
        while (writer.getBaseStream().getPosition() % SECTOR_SIZE != 0)
            writer.writeUInt32((/*uint*/int)(FatEntryType.FREE_SECT & 0xFFFFFFFFL));
    }

    /// <summary>
    /// Normal sector size.
    /// </summary>
    static final int SECTOR_SIZE = 512;
    /// <summary>
    /// Mini stream sector size.
    /// </summary>
    static final int MINI_SECTOR_SIZE = 64;
    /// <summary>
    /// Size of one entry (sect) in fat or in difat.
    /// </summary>
    static final int ENTRY_SIZE = 4;
    /// <summary>
    /// How many entries in a FAT sector.
    /// </summary>
    static final int FAT_ENTRIES_IN_SECTOR = SECTOR_SIZE / ENTRY_SIZE;
    /// <summary>
    /// One entry at the end is used to chain to next DIFAT block.
    /// </summary>
    static final int DIFAT_ENTRIES_IN_SECTOR = FAT_ENTRIES_IN_SECTOR - 1;
}

