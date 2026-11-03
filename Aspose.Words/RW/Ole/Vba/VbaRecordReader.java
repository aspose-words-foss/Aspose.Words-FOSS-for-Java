// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/09/2019 by Alexander Sevidov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements a binary reading routines for a VBA project.
/// </summary>
class VbaRecordReader
{
    VbaRecordReader(BinaryReader binaryReader)
    {
        mReader = binaryReader;
    }

    Encoding getEncoding() { return mEncoding; }
    void setEncoding(Encoding value) { mEncoding = value; }

    int readInt32Record(int id) throws Exception
    {
        int readId = (mReader.readUInt16() & 0xFFFF);
        Debug.assertTrue(id == readId);
        int size = mReader.readInt32();
        Debug.assertTrue(size == 4);
        return mReader.readInt32();
    }

    int readInt32() throws Exception
    {
        return mReader.readInt32();
    }

    short readInt16Record(int id) throws Exception
    {
        int readId = (mReader.readUInt16() & 0xFFFF);
        Debug.assertTrue(id == readId);
        int size = mReader.readInt32();
        Debug.assertTrue(size == 2);
        return mReader.readInt16();
    }

    short readInt16() throws Exception
    {
        return mReader.readInt16();
    }

    /*ushort*/short readUInt16() throws Exception
    {
        return mReader.readUInt16();
    }

    /*uint*/int readUInt32() throws Exception
    {
        return mReader.readUInt32();
    }

    byte[] readBytes(int count) throws Exception
    {
        return mReader.readBytes(count);
    }

    String readStringRecord(int id) throws Exception
    {
        return readStringRecord(id, mEncoding);
    }

    String readString() throws Exception
    {
        return readString(mEncoding);
    }

    /// <summary>
    /// Reads the string record and corresponding unicode record.
    /// </summary>
    String readStringRecords(int id, int idUnicode) throws Exception
    {
        String record = readStringRecord(id, mEncoding);
        String recordUnicode = readStringRecordOptional(idUnicode, Encoding.getUnicode());
        return msString.isNullOrEmpty(recordUnicode) ? record : recordUnicode;
    }

    int peekId() throws Exception
    {
        int id = (mReader.readUInt16() & 0xFFFF);
        mReader.getBaseStream().setPosition(mReader.getBaseStream().getPosition() - 2);

        return id;
    }

    void readVersionRecord(int id) throws Exception
    {
        int readId = (mReader.readUInt16() & 0xFFFF);
        Debug.assertTrue(id == readId);
        int reserved = mReader.readInt32();
        Debug.assertTrue(reserved == 4);
    }

    private String readStringRecord(int id, Encoding encoding) throws Exception
    {
        int readId = (mReader.readUInt16() & 0xFFFF);
        Debug.assertTrue(id == readId);
        return readString(encoding);
    }

    private String readString(Encoding encoding) throws Exception
    {
        int size = mReader.readInt32();
        byte[] bytes = mReader.readBytes(size);
        return encoding.getString(bytes);
    }

    /// <summary>
    /// Reads the optional string record.
    /// </summary>
    private String readStringRecordOptional(int id, Encoding encoding) throws Exception
    {
        return (peekId() == id) ? readStringRecord(id, encoding) : "";
    }

    private /*final*/ BinaryReader mReader;
    private Encoding mEncoding;
}

