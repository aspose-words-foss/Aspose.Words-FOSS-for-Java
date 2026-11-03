// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2005 by Roman Korchagin
// 2016/06/20 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestMemoryStream
{
    /// <summary>
    /// Not really much testing because MemoryStream is directly ported from the Rotor source.
    /// </summary>
    @Test
    public void testResizableReadWrite() throws Exception
    {
        MemoryStream s = new MemoryStream();

        //Test properties
        msAssert.areEqual(0, s.getLength());
        msAssert.areEqual(0, s.getPosition());

        byte[] b = { (byte) 1, (byte) 2, (byte) 3, (byte) 4 };
        s.write(b, 0, b.length);

        //Test data was added
        msAssert.areEqual(4, s.getLength());
        msAssert.areEqual(4, s.getPosition());

        //Test change position
        s.setPosition(0);
        msAssert.areEqual(0, s.getPosition());

        //Test read data back
        byte[] x = new byte[10];
        msAssert.areEqual(4, s.read(x, 0, 10));
        msAssert.areEqual(1, x[0]);
        msAssert.areEqual(1, x[0]);
        msAssert.areEqual(2, x[1]);
        msAssert.areEqual(3, x[2]);
        msAssert.areEqual(4, x[3]);

        //Check read past end returns zero bytes.
        msAssert.areEqual(0, s.read(x, 0, 10));
    }
}

