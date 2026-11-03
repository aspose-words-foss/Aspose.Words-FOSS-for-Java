// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/03/2005 by Roman Korchagin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Double-indirection FAT - basically FAT of FAT in a structured storage.
/// </summary>
/*static*/ class Difat
{
	/* Simulation of static class by using private constructor */
	private Difat()
	{}

    /// <summary>
    /// Reads and returns DIFAT entries from the header and from extra DIFAT sectors (if present).
    /// </summary>
    /// <param name="stream">Structured storage we are reading.</param>
    /// <param name="difatEntries">Number of DIFAT entries (number of FAT sectors).</param>
    /// <param name="sectDifStart">First DIFAT sector (will be end of chain if DIFAT fits into the header).</param>
    /// <param name="difatSectors">Number of difat sectors.</param>
    static SectCollection read(Stream stream, int difatEntries, /*uint*/int sectDifStart, int difatSectors) throws Exception
    {
        //Create a stream to read difat into.
        MemoryStream difatStream = new MemoryStream();

        //Used to copy between source and difat streams.
        byte[] buf = new byte[Sector.SECTOR_SIZE];

        //Read DIFAT from the header
        stream.setPosition(Header.DIFAT_IN_HEADER_OFFSET);
        int lengthInHeader = Math.min(difatEntries, Header.MAX_DIFAT_ENTRIES_IN_HEADER) * Sector.ENTRY_SIZE;
        StreamUtil.read(stream, buf, 0, lengthInHeader);

        difatStream.write(buf, 0, lengthInHeader);

        //Read additional DIFAT sectors (if present).
        /*uint*/int difatSect = sectDifStart;
        for (int i = 0; i < difatSectors; i++)
        {
            stream.setPosition(Sector.sectorToOffset(difatSect, true));
            StreamUtil.read(stream, buf, 0, Sector.SECTOR_SIZE);

            //Last entry in the DIFAT sector is used to find the next DIFAT sector so exclude it.
            int chainPos = Sector.DIFAT_ENTRIES_IN_SECTOR * Sector.ENTRY_SIZE;
            difatStream.write(buf, 0, chainPos);
            
            //Retrieve next DIFAT sect from the last entry in the buffer.
            difatSect = (/*uint*/int)((buf[chainPos] & 0xFF) | ((buf[chainPos + 1] & 0xFF) << 8) | ((buf[chainPos + 2] & 0xFF) << 16) | ((buf[chainPos + 3] & 0xFF) << 24));
        }

        return new SectCollection(difatStream);
    }

    /// <summary>
    /// Writes DIFAT to the header and to extra DIFAT sectors (if needed) at the current position in the stream.
    /// </summary>
    /// <param name="stream">Where to write difat to.</param>
    /// <param name="sectFatStart">First FAT sector. Note that FAT must be in consequtive sectors.</param>
    /// <param name="difatEntries">Number of DIFAT entries (FAT sectors).</param>
    /// <param name="header">Updated <see cref="Header.SectDifStart"/> and <see cref="Header.CsectDif"/> fields in this structure.</param>
    static void write(
        Stream stream, 
        /*uint*/int sectFatStart,
        int difatEntries,
        Header header) throws Exception
    {
        /*uint*/int sectFat = sectFatStart;

        BinaryWriter writer = new BinaryWriter(stream, Encoding.getUnicode());

        //Write DIFAT sects into the header
        long savePos = stream.getPosition();
        stream.setPosition(Header.DIFAT_IN_HEADER_OFFSET);
        int difatEntriesInHeader = Math.min(difatEntries, Header.MAX_DIFAT_ENTRIES_IN_HEADER);
        for (int i = 0; i < difatEntriesInHeader; i++)
        {
            writer.writeUInt32((/*uint*/int)(sectFat & 0xFFFFFFFFL));
            sectFat++;
        }
        Sector.padFreeSect(writer);
        stream.setPosition(savePos);

        //Write remaining DIFAT into sectors.
        int remainingDifatEntries = difatEntries - difatEntriesInHeader;
        if (remainingDifatEntries > 0)
        {
            header.SectDifStart = Sector.offsetToSector(stream.getPosition(), true);
            header.CsectDif = 0;

            //Loop to create as many DIFAT sectors as needed.
            while (remainingDifatEntries > 0)
            {
                int difatEntriesInSector = Math.min(remainingDifatEntries, Sector.DIFAT_ENTRIES_IN_SECTOR);
                for (int i = 0; i < difatEntriesInSector; i++)
                {
                    writer.writeUInt32((/*uint*/int)(sectFat & 0xFFFFFFFFL));
                    sectFat++;
                }
                Sector.padFreeSect(writer);
            
                remainingDifatEntries -= difatEntriesInSector;
                header.CsectDif++;

                //Rewrite the last entry in the DIFAT sector to chain it to the next DIFAT sector.
                stream.setPosition(stream.getPosition() - Sector.ENTRY_SIZE);
                if (remainingDifatEntries > 0)
                {
                    /*uint*/int nextDifatSect = (/*uint*/int)((Sector.offsetToSector(stream.getPosition(), true) & 0xFFFFFFFFL) + 1);
                    writer.writeUInt32((/*uint*/int)(nextDifatSect & 0xFFFFFFFFL));
                }
                else
                    writer.writeUInt32(FatEntryType.END_OF_CHAIN);    
            }
        }
        else
        {
            header.SectDifStart = FatEntryType.END_OF_CHAIN;
            header.CsectDif = 0;
        }
    }
}

