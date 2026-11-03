// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Represents information about property id and its offset in a property set section.
/// </summary>
class PropIdOffset
{
    PropIdOffset(int id, int offset)
    {
        Id = id;
        Offset = offset;
    }

    PropIdOffset(BinaryReader reader) throws Exception
    {
        Id = reader.readInt32();
        Offset = reader.readInt32();
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(Id);
        writer.writeInt32(Offset);
    }

    int Id;
    int Offset;
}

