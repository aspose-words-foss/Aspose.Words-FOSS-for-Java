// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Implements base class for OLE1.0 objects.
/// </summary>
abstract class Ole1Object
{
    /// <summary>
    /// Factory method that reads and returns either Ole1LinkedObject or Ole1EmbeddedObject instance.
    /// </summary>
    static Ole1Object read(BinaryReader reader) throws Exception
    {
        ObjectHeader objectHeader = ObjectHeader.read(reader);

        // Invalid OLE object header, cancel further reading.
        if (objectHeader == null)
            return null;

        switch (objectHeader.getOleObjectType())
        {
            case OleObjectType.LINKED:
                return new Ole1LinkedObject(objectHeader, reader);

            case OleObjectType.EMBEDDED:
                return new Ole1EmbeddedObject(objectHeader, reader);

            default:
                throw new IllegalStateException("Unexpected OleObjectType value.");

        }
    }

    abstract /*OleObjectType*/int getObjectType();

    /// <summary>
    /// Writes object to given writer.
    /// </summary>
    abstract void write(BinaryWriter writer) throws Exception;

    /// <summary>
    /// Specifies the creating application in an implementation-specific manner.
    /// </summary>
    String getClassName() { return mClassName; }

    protected String mClassName;
}

