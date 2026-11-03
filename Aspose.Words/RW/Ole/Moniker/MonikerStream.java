// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Implements [MS-OLEDS] 2.3.3.1 MONIKERSTREAM structure. http://msdn.microsoft.com/en-us/library/dd942007.aspx
/// </summary>
class MonikerStream
{
    static MonikerBase read(BinaryReader reader, int length) throws Exception
    {
        if (length == 0)
            return null;

        int startPos = (int)reader.getBaseStream().getPosition();

        Guid clsId = new Guid(reader.readBytes(16));
        MonikerBase moniker = MonikerBase.create(clsId);
        moniker.read(reader);

        // Adjust length because it occurred that moniker can have some extra undocumented data, FileMoniker for example.
        reader.getBaseStream().setPosition(startPos + length - 4);

        return moniker;
    }

    static void write(BinaryWriter writer, MonikerBase moniker) throws Exception
    {
        MemoryStream tempStream = new MemoryStream();
        BinaryWriter tempWriter = new BinaryWriter(tempStream);

        moniker.write(tempWriter);

        int monikerLength = (int)tempStream.getLength();

        // MonikerSize
        writer.writeInt32(monikerLength + 16 /* ClsId */+ 4 /* monikerLength size */);
        byte[] monikerBytes = tempStream.toArray();
        writer.writeBytes(moniker.getClsId().toByteArray());
        writer.writeBytes(monikerBytes, 0, monikerBytes.length);
    }
}

