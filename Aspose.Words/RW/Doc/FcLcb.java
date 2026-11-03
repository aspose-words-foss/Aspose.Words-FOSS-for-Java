// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/05/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.msString;


/// <summary>
/// FC/LCB pair value.
/// </summary>
class FcLcb
{
    FcLcb()
    {
        Fc = 0;
        Lcb = 0;
    }

    FcLcb(int fc, int lcb)
    {
        Fc = fc;
        Lcb = lcb;
    }

    void read(BinaryReader reader) throws Exception
    {
        Fc = reader.readInt32();
        Lcb = reader.readInt32();
    }

    void write(BinaryWriter writer) throws Exception
    {
        writer.writeInt32(Fc);
        writer.writeInt32(Lcb);
    }

    public /*override*/ String toString()
    {
        return (((Fc == 0) && (Lcb == 0)) ? "-" : msString.format("{0}, {1}", Fc, Lcb));
    }

    int Fc;
    int Lcb;
}


