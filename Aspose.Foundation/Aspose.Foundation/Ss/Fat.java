// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/03/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents the FAT in a structured storage file.
/// </summary>
class Fat extends SectCollection
{
    Fat(Stream stream, Header header)
    {
        mStream = stream;
        mHeader = header;
    }

    /// <summary>
    /// Reads all FAT entires from the structured storage.
    /// </summary>
    void read() throws Exception
    {
        SectCollection difat = Difat.read(mStream, mHeader.CsectFat, mHeader.SectDifStart, mHeader.CsectDif);

        // Cache each FAT sector to read FAT entries from it, not from the stream. This is quicker.
        byte[] fatCache = new byte[Sector.SECTOR_SIZE];
        BinaryReader fatReader = new BinaryReader(new MemoryStream(fatCache), Encoding.getUnicode());

        for (/*uint*/int difatIdx = 0; (difatIdx & 0xFFFFFFFFL) < mHeader.CsectFat; difatIdx++)
        {
            mStream.setPosition(Sector.sectorToOffset(difat.get(difatIdx), true));
            StreamUtil.tryRead(mStream, fatCache, 0, Sector.SECTOR_SIZE);

            fatReader.getBaseStream().setPosition(0);
            for (int fatIdx = 0; fatIdx < Sector.FAT_ENTRIES_IN_SECTOR; fatIdx++)
                this.add(fatReader.readUInt32());
        }
    }

    /// <summary>
    /// Writes FAT and DIFAT and updates the header.
    /// </summary>
    void write() throws Exception
    {
        MemoryStream srcStream = super.toMemoryStream();
        BinaryWriter writer = new BinaryWriter(mStream, Encoding.getUnicode());

        //Remember where the fat starts in the file.
        /*uint*/int sectFatStart = Sector.offsetToSector(mStream.getPosition(), true);

        //The goal is to make sure that the fat contains an entry for every sector in the
        //file including data, fat and difat sectors.
        int fatEntriesForData = super.getCount();
        int fatEntriesForFat = MathUtil.divUp(fatEntriesForData, Sector.FAT_ENTRIES_IN_SECTOR);

        // WORDSNET-1016 sending document via Outlook resulting in corruption of the file.
        // There were 0x80 fat entries for data. This requires a complete 1 sector of fat.
        // But when we write 0x81 fat entries, it actually requires 2 sectors of fat.
        // So we have to recalculate the required number of sectors for the fat once again.
        fatEntriesForFat = MathUtil.divUp(fatEntriesForData + fatEntriesForFat, Sector.FAT_ENTRIES_IN_SECTOR);

        //Write fat entries for data
        mStream.write(srcStream.getBuffer(), 0, (int)srcStream.getLength());

        //Write fat entries for fat itself
        for (int i = 0; i < fatEntriesForFat; i++)
            writer.writeUInt32((/*uint*/int)(FatEntryType.FAT_SECT & 0xFFFFFFFFL));

        //Figure out whether need DIFAT sectors or not.
        int difatEntries = MathUtil.divUp((fatEntriesForData + fatEntriesForFat), Sector.FAT_ENTRIES_IN_SECTOR);
        if (difatEntries > Header.MAX_DIFAT_ENTRIES_IN_HEADER)
        {
            //Need DIFAT.
            int fatEntriesForDifat = MathUtil.divUp(difatEntries - Header.MAX_DIFAT_ENTRIES_IN_HEADER, Sector.DIFAT_ENTRIES_IN_SECTOR);

            //I don't fully understand this myself, but seems this is needed (TestBigExcel).
            for (int i = 0; i < fatEntriesForDifat; i++)
                writer.writeUInt32((/*uint*/int)(FatEntryType.FAT_SECT & 0xFFFFFFFFL));

            //Write fat entries for difat
            for (int i = 0; i < fatEntriesForDifat; i++)
                writer.writeUInt32((/*uint*/int)(FatEntryType.DIF_SECT & 0xFFFFFFFFL));

            difatEntries = MathUtil.divUp((fatEntriesForData + fatEntriesForFat + fatEntriesForDifat), Sector.FAT_ENTRIES_IN_SECTOR);
        }

        mHeader.CsectFat = difatEntries;
        Sector.padFreeSect(writer);

        Difat.write(mStream, sectFatStart, difatEntries, mHeader);
    }

    private /*final*/ Stream mStream;
    private /*final*/ Header mHeader;
}

