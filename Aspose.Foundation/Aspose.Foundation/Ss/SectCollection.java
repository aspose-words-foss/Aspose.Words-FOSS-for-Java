// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// This is a collection of SECTs.
/// SECT is a UInt32 that is used as an index into a FAT and can 
/// also be converted into offfset in the structured storage file.
/// </summary>
class SectCollection
{
    SectCollection()
    {
    }

    SectCollection(Stream stream) throws Exception
    {
        stream.setPosition(0);
        read(stream, (int)stream.getLength() / Sector.ENTRY_SIZE);
    }

    void read(Stream stream, int entries) throws Exception
    {
        BinaryReader reader = new BinaryReader(stream, Encoding.getUnicode());

        int newCount = mItems.getCount() + entries;
        if (mItems.getCapacity() < newCount)
            mItems.setCapacity(newCount);

        for (int i = 0; i < entries; i++)
            mItems.add((reader.readUInt32() & 0xFFFFFFFFL));
    }

    MemoryStream toMemoryStream() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(stream, Encoding.getUnicode());

        for (/*uint*/int i = 0; (i & 0xFFFFFFFFL) < mItems.getCount(); i++)
            writer.writeUInt32(this.get(i));

        return stream;
    }

    void add(/*uint*/int value)
    {
        mItems.add((value & 0xFFFFFFFFL));
    }

    /*uint*/int get(/*uint*/int index) { return (/*uint*/int)mItems.get((int)(index & 0xFFFFFFFFL)); }
    void set(/*uint*/int index, /*uint*/int value) { mItems.set((int)(index & 0xFFFFFFFFL), (long)value); }

    int getCount() { return mItems.getCount(); }

    private /*final*/ LongList mItems = new LongList();
}

