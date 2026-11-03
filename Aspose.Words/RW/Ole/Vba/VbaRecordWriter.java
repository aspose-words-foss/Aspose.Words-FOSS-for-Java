// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Implements a binary writing routines for a VBA project.
/// </summary>
class VbaRecordWriter
{
    VbaRecordWriter(BinaryWriter binaryWriter, Encoding encoding)
    {
        mWriter = binaryWriter;
        mEncoding = encoding;
    }

    Encoding getEncoding() { return mEncoding; }

    void writeInt32Record(/*ushort*/short id, int value) throws Exception
    {
        mWriter.writeUInt16(id);
        int size = 4;
        mWriter.writeInt32(size);
        mWriter.writeInt32(value);
    }

    void writeInt32(int value) throws Exception
    {
        mWriter.writeInt32(value);
    }

    void writeInt16Record(/*ushort*/short id, short value) throws Exception
    {
        mWriter.writeUInt16(id);
        int size = 2;
        mWriter.writeInt32(size);
        mWriter.writeInt16(value);
    }

    void writeInt16(short value) throws Exception
    {
        mWriter.writeInt16(value);
    }

    void writeUInt16(/*ushort*/short value) throws Exception
    {
        mWriter.writeUInt16(value);
    }

    void writeUInt32(/*uint*/int value) throws Exception
    {
        mWriter.writeUInt32(value);
    }

    void writeBytes(byte[] bytes) throws Exception
    {
        mWriter.writeBytes(bytes);
    }

    void writeStringRecord(/*ushort*/short id, Encoding encoding, String value) throws Exception
    {
        mWriter.writeUInt16(id);
        writeString(encoding, value);
    }

    void writeStringRecord(/*ushort*/short id, String value) throws Exception
    {
        writeStringRecord(id, mEncoding, value);
    }

    void writeString(Encoding encoding, String value) throws Exception
    {
        byte[] bytes = value!= null ? encoding.getBytes(value) : new byte[0];
        int size = bytes.length;
        mWriter.writeInt32(size);
        mWriter.writeBytes(bytes);
    }

    void writeString(String value) throws Exception
    {
        writeString(mEncoding, value);
    }

    private /*final*/ BinaryWriter mWriter;
    private /*final*/ Encoding mEncoding;
}


