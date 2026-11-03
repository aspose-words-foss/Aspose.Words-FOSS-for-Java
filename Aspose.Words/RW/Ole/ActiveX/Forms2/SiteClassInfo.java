// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;


class SiteClassInfo
{
    SiteClassInfo(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == 0x0000);

        /*uint*/int cbClassTable = (reader.readUInt16() & 0xFFFF);

        // Ignore data in this class for a while.
        reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + (cbClassTable & 0xFFFFFFFFL));
    }
}

