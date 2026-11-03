// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
//20/12/19 by Yury Siachko
package com.aspose.ms.java.IO;

import com.aspose.TestFxUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;

@Test
public class TestJavaInputStream
{
    @DataProvider(name = "regularInputStreams")
    public Object[][] getRegularInputStreams()
    {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        return new Object[][]{
                {new BlobInputStream(new ByteArrayInputStream(bytes))},
                {new DataInputStream(new ByteArrayInputStream(bytes))},
                {new ByteArrayInputStream(bytes)}
                // LineNumberInputStream - deprecated
                // StringBufferInputStream - deprecated
        };
    }

    @Test(dataProvider = "regularInputStreams")
    public void testSetPositionBackwardOnRegularStream(InputStream inputStream) throws Exception
    {
        JavaInputStream javaInputStream = new JavaInputStream(inputStream);

        byte[] buffer = new byte[6];
        javaInputStream.read(buffer, 0, 6);
        javaInputStream.setPosition(2);

        javaInputStream.read(buffer, 0, 6);
        Assert.assertEquals(buffer, new byte[]{2, 3, 4, 5, 6, 7});
    }

    @Test(dataProvider = "regularInputStreams")
    public void testSetPositionForwardOnRegularStream(InputStream inputStream) throws Exception
    {
        JavaInputStream javaInputStream = new JavaInputStream(inputStream);

        byte[] buffer = new byte[2];
        javaInputStream.read(buffer, 0, 2);
        javaInputStream.setPosition(4);

        buffer = new byte[4];
        javaInputStream.read(buffer, 0, 4);
        Assert.assertEquals(buffer, new byte[]{4, 5, 6, 7});
    }

    @Test
    public void testSetPositionBackwardOnFileInputStream() throws Exception
    {
        String fileName = TestFxUtil.buildTestFileName("TrueType\\SimpleFile.txt");
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName)))
        {
            JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

            byte[] buffer = new byte[6];
            javaInputStream.read(buffer, 0, 6);
            javaInputStream.setPosition(2);

            javaInputStream.read(buffer, 0, 6);
            Assert.assertEquals(buffer, new byte[]{67, 111, 109, 112, 84, 101}); // ASCII "CompTe"
        }
    }

    @Test
    public void testSetPositionForwardOnFileInputStream() throws Exception
    {
        String fileName = TestFxUtil.buildTestFileName("TrueType\\SimpleFile.txt");
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName)))
        {
            JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

            byte[] buffer = new byte[6];
            javaInputStream.read(buffer, 0, 6);
            javaInputStream.setPosition(10);

            javaInputStream.read(buffer, 0, 6);
            Assert.assertEquals(buffer, new byte[]{68, 97, 116, 97, 49, 50}); // ASCII "Data12"
        }
    }

    @Test
    public void testSetPositionBackwardOnPushBackInputStream() throws Exception
    {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(bytes);
        PushbackInputStream pushbackInputStream = new PushbackInputStream(bytesInputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pushbackInputStream);
        JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

        byte[] buffer = new byte[6];
        javaInputStream.read(buffer, 0, 6);

        pushbackInputStream.unread(65);
        javaInputStream.setPosition(5);

        javaInputStream.read(buffer, 0, 6);
        Assert.assertEquals(buffer, new byte[]{5, 6, 7, 8, 9, 65});
    }

    @Test
    public void testSetPositionForwardOnPushBackInputStream() throws Exception
    {
        byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(bytes);
        PushbackInputStream pushbackInputStream = new PushbackInputStream(bytesInputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pushbackInputStream);
        JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

        byte[] buffer = new byte[4];
        javaInputStream.read(buffer, 0, 4);

        pushbackInputStream.unread(65);
        javaInputStream.setPosition(7);

        javaInputStream.read(buffer, 0, 4);
        Assert.assertEquals(buffer, new byte[]{7, 8, 9, 65});
    }

    @Test
    public void testSetPositionBackwardPipedInputStream() throws Exception
    {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pipedInputStream);

        pipedOutputStream.write(new byte[]{0, 1, 2, 3, 4, 5, 6});

        JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

        byte[] buffer = new byte[6];
        javaInputStream.read(buffer, 0, 6);

        javaInputStream.setPosition(2);
        pipedOutputStream.write(7);

        javaInputStream.read(buffer, 0, 6);
        Assert.assertEquals(buffer, new byte[]{2, 3, 4, 5, 6, 7});
    }

    @Test
    public void testSetPositionForwardPipedInputStream() throws Exception
    {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(pipedInputStream);

        pipedOutputStream.write(new byte[]{0, 1, 2, 3, 4, 5, 6});

        JavaInputStream javaInputStream = new JavaInputStream(bufferedInputStream);

        byte[] buffer = new byte[2];
        javaInputStream.read(buffer, 0, 2);

        javaInputStream.setPosition(6);
        pipedOutputStream.write(7);

        javaInputStream.read(buffer, 0, 2);
        Assert.assertEquals(buffer, new byte[]{6, 7});
    }

    private static class BlobInputStream extends BufferedInputStream
    {
        @Override
        public synchronized int available()
        {
            return 0;
        }

        BlobInputStream(InputStream in)
        {
            super(in, 5);
        }
    }
}

