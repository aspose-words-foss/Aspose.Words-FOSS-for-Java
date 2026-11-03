// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.

package com.aspose.ms.java.IO;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

@Test
public class TestBufferedFileAccess
{
    /**
     * The same random sequence of operations goes to a BufferedFileAccess and to a plain RandomAccessFile on another file.
     * Every result, the position and the length have to agree after each step, and so do the two files at the end.
     * The buffer is tiny, so that the sequence keeps crossing its edges.
     */
    @Test
    public void testBehavesLikeRandomAccessFile() throws Exception
    {
        for (int bufferSize : new int[]{1, 7, 16, 64})
            checkAgainstRandomAccessFile(bufferSize, 12345 + bufferSize);
    }

    private static void checkAgainstRandomAccessFile(int bufferSize, long seed) throws Exception
    {
        File actualFile = createScratchFile();
        File expectedFile = createScratchFile();
        Random random = new Random(seed);

        BufferedFileAccess actual = new BufferedFileAccess(actualFile, true, bufferSize);
        RandomAccessFile expected = new RandomAccessFile(expectedFile, "rw");
        try
        {
            for (int step = 0; step < 5000; step++)
            {
                String where = "buffer " + bufferSize + ", step " + step;
                switch (random.nextInt(8))
                {
                    case 0:
                    {
                        // Now and then beyond the end of the file.
                        long position = random.nextInt((int) expected.length() + 40);
                        actual.setPosition(position);
                        expected.seek(position);
                        break;
                    }
                    case 1:
                        Assert.assertEquals(actual.read(), expected.read(), where);
                        break;
                    case 2:
                    {
                        // Shorter and longer than the buffer.
                        int count = random.nextInt(3 * bufferSize + 2);
                        byte[] actualBytes = new byte[count + 4];
                        byte[] expectedBytes = new byte[count + 4];
                        Assert.assertEquals(actual.read(actualBytes, 2, count), readFully(expected, expectedBytes, 2, count), where);
                        Assert.assertEquals(actualBytes, expectedBytes, where);
                        break;
                    }
                    case 3:
                    {
                        int value = random.nextInt();
                        actual.write(value);
                        expected.write(value);
                        break;
                    }
                    case 4:
                    case 5:
                    {
                        byte[] bytes = new byte[random.nextInt(3 * bufferSize + 2) + 4];
                        random.nextBytes(bytes);
                        actual.write(bytes, 2, bytes.length - 4);
                        expected.write(bytes, 2, bytes.length - 4);
                        break;
                    }
                    case 6:
                    {
                        if (random.nextInt(10) == 0)
                        {
                            long length = random.nextInt((int) expected.length() + 20);
                            actual.setLength(length);
                            expected.setLength(length);
                        }
                        break;
                    }
                    default:
                        actual.flush();
                        break;
                }

                Assert.assertEquals(actual.getPosition(), expected.getFilePointer(), where);
                Assert.assertEquals(actual.getLength(), expected.length(), where);
            }
        }
        finally
        {
            actual.close();
            expected.close();
        }

        Assert.assertEquals(Files.readAllBytes(actualFile.toPath()), Files.readAllBytes(expectedFile.toPath()));
        Assert.assertTrue(actualFile.delete());
        Assert.assertTrue(expectedFile.delete());
    }

    /**
     * What is written is not in the file before the buffer is flushed, but it is already part of the length and can be read back.
     */
    @Test
    public void testUnflushedDataIsVisible() throws Exception
    {
        File file = createScratchFile();
        BufferedFileAccess access = new BufferedFileAccess(file, true);
        try
        {
            access.write(new byte[]{1, 2, 3}, 0, 3);
            Assert.assertEquals(file.length(), 0);
            Assert.assertEquals(access.getLength(), 3);

            access.setPosition(1);
            Assert.assertEquals(access.read(), 2);

            access.flush();
            Assert.assertEquals(file.length(), 3);
        }
        finally
        {
            access.close();
        }

        Assert.assertEquals(Files.readAllBytes(file.toPath()), new byte[]{1, 2, 3});
        Assert.assertTrue(file.delete());
    }

    @Test
    public void testCloseFlushesAndCanBeRepeated() throws Exception
    {
        File file = createScratchFile();
        BufferedFileAccess access = new BufferedFileAccess(file, true);
        access.write(0x1FF);
        access.close();
        access.close();

        Assert.assertEquals(Files.readAllBytes(file.toPath()), new byte[]{(byte) 0xFF});
        Assert.assertTrue(file.delete());
    }

    @Test
    public void testReadOnly() throws Exception
    {
        File file = createScratchFile();
        Files.write(file.toPath(), new byte[]{10, 20, 30});

        BufferedFileAccess access = new BufferedFileAccess(file, false);
        try
        {
            byte[] bytes = new byte[8];
            Assert.assertEquals(access.read(bytes, 0, 8), 3);
            Assert.assertEquals(Arrays.copyOf(bytes, 3), new byte[]{10, 20, 30});
            Assert.assertEquals(access.read(bytes, 0, 8), -1);
            Assert.assertEquals(access.read(bytes, 0, 0), 0);
            Assert.assertEquals(access.read(), -1);

            try
            {
                access.write(1);
                Assert.fail("A write to a file opened for reading has to fail at once.");
            }
            catch (IOException e)
            {
                // Expected.
            }
        }
        finally
        {
            access.close();
        }

        Assert.assertTrue(file.delete());
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void testReadOnlyFileHasToExist() throws Exception
    {
        File file = createScratchFile();
        Assert.assertTrue(file.delete());
        new BufferedFileAccess(file, false);
    }

    @Test
    public void testBadArguments() throws Exception
    {
        File file = createScratchFile();
        BufferedFileAccess access = new BufferedFileAccess(file, true);
        try
        {
            try
            {
                access.setPosition(-1);
                Assert.fail();
            }
            catch (IOException e)
            {
                // Expected.
            }

            try
            {
                access.read(new byte[4], 2, 3);
                Assert.fail();
            }
            catch (IndexOutOfBoundsException e)
            {
                // Expected.
            }

            try
            {
                access.write(new byte[4], -1, 2);
                Assert.fail();
            }
            catch (IndexOutOfBoundsException e)
            {
                // Expected.
            }
        }
        finally
        {
            access.close();
        }

        Assert.assertTrue(file.delete());
    }

    /**
     * RandomAccessFile may return fewer bytes than asked for; BufferedFileAccess reads until the end of the file.
     */
    private static int readFully(RandomAccessFile file, byte[] bytes, int offset, int count) throws IOException
    {
        int total = 0;
        while (total < count)
        {
            int n = file.read(bytes, offset + total, count - total);
            if (n <= 0)
                break;
            total += n;
        }

        return (total == 0 && count > 0) ? -1 : total;
    }

    private static File createScratchFile() throws IOException
    {
        return File.createTempFile("TestBufferedFileAccess", ".bin");
    }
}
