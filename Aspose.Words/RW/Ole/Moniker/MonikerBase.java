// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;


/// <summary>
/// Base class for all monikers objects.
/// </summary>
abstract class MonikerBase
{
    static MonikerBase create(Guid clsId)
    {
        MonikerBase moniker = null;

        if (clsId.equals(ITEM_MONIKER_CLS_ID))
            moniker = new ItemMoniker();
        else if (clsId.equals(FILE_MONIKER_CLS_ID))
            moniker = new FileMoniker();
        else if (clsId.equals(URL_MONIKER_CLS_ID))
            moniker = new UrlMoniker();

        return moniker;
    }

    abstract void read(BinaryReader reader) throws Exception;

    abstract void write(BinaryWriter write) throws Exception;

    abstract Guid getClsId();

    protected static /*final*/ Guid FILE_MONIKER_CLS_ID = new Guid("00000303-0000-0000-C000-000000000046");
    protected static /*final*/ Guid ITEM_MONIKER_CLS_ID = new Guid("00000304-0000-0000-C000-000000000046");
    protected static /*final*/ Guid URL_MONIKER_CLS_ID = new Guid("79EAC9E0-BAF9-11CE-8C82-00AA004BA90B");
}

