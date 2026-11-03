// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/04/2025 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public class CvtTable extends TTTable
{
    public static CvtTable read(BigEndianBinaryReader reader, long tableLength) throws Exception
    {
        CvtTable table = new CvtTable();
        table.setValues(new short[(int)tableLength / 2]);
        for (int i = 0; i < table.getValues().length; i++)
            table.getValues()[i] = reader.readInt16();
        return table;
    }

    /*override*/ void write(BigEndianBinaryWriter writer) throws Exception
    {
        for (int i = 0; i < getValues().length; i++)
            writer.writeInt16(getValues()[i]);
    }

    public short[] getValues() { return mValues; }; private void setValues(short[] value) { mValues = value; };

    private short[] mValues;
}

