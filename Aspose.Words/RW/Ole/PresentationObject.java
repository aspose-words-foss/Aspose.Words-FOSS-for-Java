// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.StreamUtil;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Base class for OLE1.0 PresentationObject classes.
/// </summary>
abstract class PresentationObject
{
    static PresentationObject read(BinaryReader reader) throws Exception
    {
        if(!StreamUtil.hasEnoughBytesToRead(reader, 8 /* oleVersion + formatId */))
            return new PresentationObjectEmpty();

        // Any arbitrary value.
        reader.readInt32(); // int oleVersion = 
        int formatId = reader.readInt32();

        // Allowed values are 0x00 (ClassName is absent) and 0x05 (ClassName is present).
        if (formatId == 0)
        {
            // There is no PresentationData but PresentationObject itself is valid.
            return new PresentationObjectEmpty();
        }
        else if(formatId != 0x05)
        {
            // Invalid PresentationObject.
            throw new IllegalStateException("Unexpected FormatID value, PresentationObject is corrupted.");
        }

        String className = OleUtil.readLengthPrefixedAnsiString(reader);

        switch(gStringSwitchMap.of(className))
        {
            case /*"METAFILEPICT"*/0:
                return new PresentationObjectMetafile(reader);
            default:
                // WARN. Presentation object of this type is not supported.
                return new PresentationObjectEmpty();
        }
    }

    abstract void write(BinaryWriter writer) throws Exception;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"METAFILEPICT"
	);

}

