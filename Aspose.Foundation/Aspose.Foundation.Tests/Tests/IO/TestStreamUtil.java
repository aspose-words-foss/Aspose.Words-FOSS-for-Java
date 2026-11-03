// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/06/2023 by Ilya Navrotskiy

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing <see cref="StreamUtil"/>.
/// </summary>
@Test
public class TestStreamUtil
{
    /// <summary>
    /// Tests <see cref="StreamUtil.IsEndsWith"/> when stream has zero length.
    /// </summary>
    @Test
    public void testStreamUtilEndsWithZeroStream() throws Exception
    {
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            msAssert.isFalse(StreamUtil.isEndsWith(stream, new byte[] { (byte) 0x0 }));
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Tests <see cref="StreamUtil.IsEndsWith"/> with various input data.
    /// </summary>
    @Test (dataProvider = "testStreamUtilEndsWithDataProvider")
    public void testStreamUtilEndsWith(byte[] bytes, boolean expectedResult) throws Exception
    {
        MemoryStream stream = new MemoryStream(new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 });
        try /*JAVA: was using*/
        {
            // Check with different buffer length.
            // Check when buffer length equals to stream length.
            msAssert.areEqual(expectedResult, StreamUtil.isEndsWith(stream, bytes, 5));
            // Check when buffer length is greater than stream length.
            msAssert.areEqual(expectedResult, StreamUtil.isEndsWith(stream, bytes, 10));
            // Check when buffer length is less than stream length.
            msAssert.areEqual(expectedResult, StreamUtil.isEndsWith(stream, bytes, 2));
        }
        finally { if (stream != null) stream.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testStreamUtilEndsWithDataProvider")
	public static Object[][] testStreamUtilEndsWithDataProvider() throws Exception
	{
		return new Object[][]
		{
			{new byte[] { },  false},
			{new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 },  true},
			{new byte[] { (byte) 3, (byte) 4, (byte) 5 },  true},
			{new byte[] { (byte) 5 },  true},
			{new byte[] { (byte) 1, (byte) 5 },  false},
			{new byte[] { (byte) 1 },  false},
			{new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4 },  false},
			{new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6 },  false},
			{new byte[] { (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 },  false},
		};
	}
}

