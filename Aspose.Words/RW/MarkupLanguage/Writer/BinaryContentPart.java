// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/11/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.Debug;


/// <summary>
/// Binary subsidiary content.
/// </summary>
class BinaryContentPart extends SubsidiaryContentPart
{
    BinaryContentPart(
        String contentType,
        String uri,
        byte[] data)
    {
    	this(null, contentType, uri, data, 0, data.length);
	
        // Empty constructor.
    }

    BinaryContentPart(
        String contentType,
        String uri,
        MemoryStream stream) throws Exception
    {
    	this(null, contentType, uri, stream);
	
        // Empty constructor.
    }

    BinaryContentPart(
        String contentType,
        String uri,
        byte[] data,
        int offset,
        int length)
    {
    	this(null, contentType, uri, data, offset, length);
	
        // Empty constructor.
    }

    BinaryContentPart(
        Encoding textEncoding,
        String contentType,
        String uri,
        MemoryStream stream) throws Exception
    {
    	this(textEncoding, contentType, uri, stream.getBuffer(), 0, stream.getLength());
	
        // Empty constructor.
    }

    BinaryContentPart(
        Encoding textEncoding,
        String contentType,
        String uri,
        byte[] data,
        int offset,
        long length)
    {
    	super(textEncoding, contentType, uri);
	
        Debug.assertTrue(data != null);
        Debug.assertTrue(offset >= 0);
        Debug.assertTrue(length >= 0);
        Debug.assertTrue(length <= Integer.MAX_VALUE);
        Debug.assertTrue((offset + length) <= data.length);

        mData = data;
        mOffset = offset;
        mLength = (int)length;
    }

    /*override*/ MemoryStream createStream() throws Exception
    {
        // Return a read-only stream. However, we keep the base array publicly visible, because it was publicly visible
        // when this class was created.
        return new MemoryStream(mData, mOffset, mLength, false, true);
    }

    private /*final*/ byte[] mData;
    private /*final*/ int mOffset;
    private /*final*/ int mLength;
}

