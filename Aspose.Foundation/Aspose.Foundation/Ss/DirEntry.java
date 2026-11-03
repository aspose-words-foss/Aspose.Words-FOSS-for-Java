// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.msArray;


/// <summary>
/// Directory entry in a structured storage.
/// </summary>
class DirEntry
{
    /// <summary>
    /// Creates an empty directory entry.
    /// </summary>
    DirEntry()
    {
        EntryType = DirEntryType.INVALID;
        EntryColor = DirEntryColor.BLACK;
        LeftSib = NO_STREAM;
        RightSib = NO_STREAM;
        Child = NO_STREAM;
        Clsid = Guid.Empty;
    }

    /// <summary>
    /// Creates a new storage directory entry.
    /// </summary>
    DirEntry(String name, boolean isRoot, Guid clsid)
    {
    	this();
	
        Name = name;
        EntryType = (isRoot) ? DirEntryType.ROOT : DirEntryType.STORAGE;
        Clsid = clsid;
        // As per the clearswift mimesweeper issue and according to the spec,
        // non-root storages should have zero sect start.
        SectStart = (isRoot) ? NO_STREAM : 0;
    }

    /// <summary>
    /// Creates a new stream directory entry.
    /// </summary>
    DirEntry(String name, /*DirEntryType*/int entryType, long size)
    {
    	this();
	
        Name = name;
        EntryType = entryType;
        Size = size;
        SectStart = NO_STREAM;
    }

    /// <summary>
    /// Reads a directory entry.
    /// </summary>
    DirEntry(BinaryReader reader) throws Exception
    {
        int startPos = (int)reader.getBaseStream().getPosition();

        byte[] nameBuf = reader.readBytes(NAME_BUF_LENGTH);

        // WORDSNET-19404 The problem occurred because there is a corrupted directory entry.
        // Its length is only 46 bytes, so we cannot continue reading.
        if (nameBuf.length < NAME_BUF_LENGTH)
            return;

        int nameByteCount = (reader.readUInt16() & 0xFFFF);

        if(nameByteCount > NAME_BUF_LENGTH)
        {
            // WORDSNET-6361 Resilience. Sector is filled with 0xff so nameByteCount is 0xffff.
            // WARN. "The structured storage seems to be corrupt."
            // WORDSNET-28023 Do safe exit on 0xffff entry.
            reader.getBaseStream().setPosition(startPos + STRUCTURE_SIZE);
            return;
        }

        if (nameByteCount > 0)
        {
            if (nameByteCount > nameBuf.length)
                throw new IllegalStateException("The structured storage seems to be corrupt.");

            Name = Encoding.getUnicode().getString(nameBuf, 0, nameByteCount - 2); //Exclude zero terminator.
        }
        else
        {
            Name = null;
        }

        EntryType = (/*DirEntryType*/int)(reader.readByte() & 0xFF);

        // WORDSNET-1048 Opening document in Aspose.Words fails with 'Unable to read beyond the end of the stream.' exception.
        // The directory's last entry is set to be Invalid (not used) and the file is 1 byte short.
        // If I read that directory entry to the end, an exception is thrown.
        // So I'd rather read nothing for invalid (unused) directory entries.
        if (EntryType == DirEntryType.INVALID)
        {
            reader.getBaseStream().setPosition(startPos + STRUCTURE_SIZE);
            return;
        }

        EntryColor = (/*DirEntryColor*/int)(reader.readByte() & 0xFF);
        LeftSib = reader.readUInt32();
        RightSib = reader.readUInt32();
        Child = reader.readUInt32();
        Clsid = new Guid(reader.readBytes(CLSID_LENGTH));
        UserFlags = reader.readUInt32();
        CreateTime = reader.readInt64();
        // WORDSNET-4012 Resilience. DirEntry truncated but WordXP still able to open defect file.
        if (StreamUtil.hasEnoughBytesToRead(reader, JIRA_4012_CHUNK_SIZE))
        {
            ModifyTime = reader.readInt64();
            SectStart = reader.readUInt32();
            Size = (reader.readUInt32() & 0xFFFFFFFFL);
            reader.readInt32(); //Padding
        }
    }

    void write(BinaryWriter writer) throws Exception
    {
        // Name is a fixed array buffer that contains a Unicode string with zero terminator and the rest are zeroes.
        byte[] nameBuf = new byte[NAME_BUF_LENGTH];
        int nameByteCount;
        if (StringUtil.hasChars(Name))
        {
            // Convert full name to Unicode.
            byte[] allNameBuf = Encoding.getUnicode().getBytes(Name);

            // We must have space for the name plus zero terminator in the fixed-size buffer.
            if (allNameBuf.length > NAME_BUF_LENGTH - 2)
                throw new IllegalStateException(msString.format("The name '{0}' is too long for writing to structured storage.", Name));

            msArray.copy(allNameBuf, 0, nameBuf, 0, allNameBuf.length);

            // The number of bytes must include 16 bit zero terminator.
            nameByteCount = allNameBuf.length + 2;
        }
        else
        {
            nameByteCount = 0;
        }

        writer.writeBytes(nameBuf);
        writer.writeUInt16((/*ushort*/short)nameByteCount);
        writer.writeByte((byte)EntryType);
        writer.writeByte((byte)EntryColor);
        writer.writeUInt32((/*uint*/int)(LeftSib & 0xFFFFFFFFL));
        writer.writeUInt32((/*uint*/int)(RightSib & 0xFFFFFFFFL));
        writer.writeUInt32((/*uint*/int)(Child & 0xFFFFFFFFL));
        writer.writeBytes(Clsid.toByteArray());
        writer.writeUInt32((/*uint*/int)(UserFlags & 0xFFFFFFFFL));
        writer.writeInt64((long)CreateTime);
        writer.writeInt64((long)ModifyTime);
        writer.writeUInt32((/*uint*/int)(SectStart & 0xFFFFFFFFL));
        writer.writeUInt32((/*uint*/int)Size);
        writer.writeInt32((int)0);
    }

    /// <summary>
    /// The element name in Unicode, padded with zeros to fill this byte array.
    /// Terminating Unicode NULL is required.
    /// </summary>
    String Name;

    /// <summary>
    /// Type of object. Value taken from the STGTY enumeration
    /// </summary>
    /*DirEntryType*/int EntryType;

    /// <summary>
    /// Value taken from DECOLOR enumeration
    /// </summary>
    /*DirEntryColor*/int EntryColor;

    /// <summary>
    /// SID of the left-sibling of this entryin the directory tree
    /// </summary>
    /*uint*/int LeftSib;

    /// <summary>
    /// SID of the right-sibling of this entry in the directory tree
    /// </summary>
    /*uint*/int RightSib;

    /// <summary>
    /// SID of the child acting as the root of all the children of this element (if _mse=STGTY_STORAGE or STGTY_ROOT)
    /// </summary>
    /*uint*/int Child;

    /// <summary>
    /// CLSID of this storage (if _mse=STGTY_STORAGE or STGTY_ROOT)
    /// </summary>
    Guid Clsid;

    /// <summary>
    /// 04] User flags of this storage (if _mse=STGTY_STORAGE or STGTY_ROOT)
    /// </summary>
    /*uint*/int UserFlags;

    /// <summary>
    /// Create time-stamp (if _mse=STGTY_STORAGE)
    /// </summary>
    long CreateTime;

    /// <summary>
    /// Modify time-stamp (if _mse=STGTY_STORAGE)
    /// </summary>
    long ModifyTime;

    /// <summary>
    /// starting SECT of the stream (if _mse=STGTY_STREAM)
    /// </summary>
    /*uint*/int SectStart;

    /// <summary>
    /// size of stream in bytes (if _mse=STGTY_STREAM)
    /// </summary>
    long Size;

    /// <summary>
    /// Not part of structured storage.
    /// This flag is used to prevent infinite loop when reading an invalid directory with circular reference.
    /// We set this after the data of the directory entry was read.
    /// </summary>
    boolean IsRead;

    /// <summary>
    /// Length of this structure.
    /// </summary>
    static final int STRUCTURE_SIZE = 128;
    /// <summary>
    /// Indicated unallocated directory entry.
    /// </summary>
    static final /*uint*/int NO_STREAM = (int) 0xFFFFFFFFL;

    private static final int NAME_BUF_LENGTH = 64;
    private static final int CLSID_LENGTH = 16;

    /// <summary>
    /// This size defines for ugly resilience Jira4012.
    /// There is certainly invalid file with truncated directory entry,
    /// unfortunately WordXP opens this file without complains and customer wants AW to do the same.
    /// </summary>
    private static final int JIRA_4012_CHUNK_SIZE = (8 + 4 + 4 + 4);
}

