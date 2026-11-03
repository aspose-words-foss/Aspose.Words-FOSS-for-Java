// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2013 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import com.aspose.ms.System.IO.Directory;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.MemoryStream;


@Test
public class TestFileSystem
{
    @BeforeMethod (alwaysRun = true)
    public void setUp() throws Exception
    {
        Directory.createDirectory(TestFxUtil.getInTestOutPath("Ss"));
    }

    @Test
    public void testReadHeader() throws Exception
    {
        Stream s = File.openRead(TestFxUtil.buildTestFileName("Ss\\TestSmall.doc"));
        try /*JAVA: was using*/
        {
            Header header = new Header(new BinaryReader(s, Encoding.getUnicode()));
            msAssert.areEqual(0, header.CsectDif);
            msAssert.areEqual(0, header.CsectDir);
            msAssert.areEqual(1, header.CsectFat);
            msAssert.areEqual(1, header.CsectMiniFat);
            msAssert.areEqual((/*ushort*/short)3, header.MajorVersion);
            msAssert.areEqual((int) 0xfffffffeL, header.SectDifStart);    //End of chain
            msAssert.areEqual((/*uint*/int)17, header.SectDirStart);            //Looked up in the file.
            msAssert.areEqual((/*uint*/int)18, header.SectMiniFatStart);        //Looked up in the file.
        }
        finally { if (s != null) s.close(); }
    }

    @Test
    public void testReadSmallDifat() throws Exception
    {
        Stream s = File.openRead(TestFxUtil.buildTestFileName("Ss\\TestSmall.doc"));
        try /*JAVA: was using*/
        {
            SectCollection difat = Difat.read(s, 1, FatEntryType.END_OF_CHAIN, 0);
            msAssert.areEqual(1, difat.getCount());    //There is only one FAT sector in this small file.
            msAssert.areEqual((/*uint*/int)16, difat.get(0));        //Looked up in the file.
        }
        finally { if (s != null) s.close(); }
    }

    @Test
    public void testReadBigDifat() throws Exception
    {
        Stream s = File.openRead(TestFxUtil.buildTestFileName("Ss\\TestBig.doc"));
        try /*JAVA: was using*/
        {
            Header header = new Header(new BinaryReader(s, Encoding.getUnicode()));
            msAssert.areEqual(207, header.CsectFat);            //Looked up in the file. There are 207 FAT sectors.
            msAssert.areEqual((/*uint*/int)26180, header.SectDifStart);    //Looked up in the file. This is where first DIFAT sector starts.
            msAssert.areEqual(1, header.CsectDif);            //There is only one DIFAT sector.

            SectCollection difat = Difat.read(s, header.CsectFat, header.SectDifStart, header.CsectDif);
            //109 DIFAT entries in the header plus 127 DIFAT entries in the DIFAT sector 
            //(1 last entry out of 128 is used for chaining DIFAT sectors.
            msAssert.areEqual(236, difat.getCount());
            //All DIFAT sectors starting from 207 are not used (free).
            msAssert.isTrue(difat.get((/*uint*/int)206) != FatEntryType.FREE_SECT);   // Cast to uint to allow autoporting to Java.
            msAssert.isTrue(difat.get((/*uint*/int)207) == FatEntryType.FREE_SECT);
        }
        finally { if (s != null) s.close(); }
    }

    @Test
    public void testMinimalEmpty() throws Exception
    {
        String fileName = TestFxUtil.buildOutFileName("Ss\\TestMinimalEmpty.bin", "", "");
        Guid guid = new Guid("{00112233-4455-6677-8899-aabbccddeeff}");

        // Write an empty document.
        {
            FileSystem fs = new FileSystem(guid);
            fs.save(fileName);
        }

        // Read the document and check.
        {
            FileSystem fs = new FileSystem(fileName);
            MemoryStorage root = fs.getRoot();
            msAssert.areEqual(guid, root.getClsid());
            msAssert.areEqual(0, root.getCount());     // There are no streams in the empty file.
        }
    }

    @Test
    public void testMinimalStream() throws Exception
    {
        String fileName = TestFxUtil.buildOutFileName("Ss\\TestMinimalStream.bin", "", "");
        final String HELLO_WORLD = "Hello World!";

        // Write a document with just one stream and a string in it.
        {
            MemoryStream stream = new MemoryStream();
            byte[] data = Encoding.getUTF8().getBytes(HELLO_WORLD);
            stream.write(data, 0, data.length);

            FileSystem fs = new FileSystem(Guid.Empty);
            fs.getRoot().add("MyStream", stream);
            fs.save(fileName);
        }

        // Read the document and check.
        {
            FileSystem fs = new FileSystem(fileName);
            MemoryStream stream = fs.getRoot().fetchStream("MyStream");
            String s = Encoding.getUTF8().getString(stream.getBuffer(), 0, (int)stream.getLength());
            msAssert.areEqual(HELLO_WORLD, s);

        }
    }

    /// <summary>
    /// Read, write and read again a small file to see if we can eat our own output.
    /// </summary>
    @Test
    public void testSmallFile() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestSmall.doc"));
        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestSmall.doc", "", "");
        fs.save(outFileName);
        fs = new FileSystem(outFileName);

        //There are only 3 streams in this document.
        msAssert.areEqual(3, fs.getRoot().getCount());

        MemoryStream doc = ms.as(fs.getRoot().get("WordDocument"), MemoryStream.class);
        msAssert.areEqual(0x1000L, doc.getLength());

        MemoryStream table = ms.as(fs.getRoot().get("1Table"), MemoryStream.class);
        msAssert.areEqual(0x1000L, table.getLength());

        MemoryStream obj = ms.as(fs.getRoot().get("\u0001CompObj"), MemoryStream.class);
        msAssert.areEqual(0x6aL, obj.getLength());
    }

    @Test
    public void testBigFile() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestBig.doc"));
        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestBig.doc", "", "");
        fs.save(outFileName);
        fs = new FileSystem(outFileName);

        //7 objects at this level.
        msAssert.areEqual(7, fs.getRoot().getCount());

        MemoryStream data = ms.as(fs.getRoot().get("Data"), MemoryStream.class);
        msAssert.areEqual(0xcc4498L, data.getLength());    //This is a long stream that contains all the images.

        MemoryStorage macros = ms.as(fs.getRoot().get("Macros"), MemoryStorage.class);
        msAssert.areEqual(3, macros.getCount());

        MemoryStorage vba = ms.as(macros.get("VBA"), MemoryStorage.class);
        msAssert.areEqual(5, vba.getCount());

        //For some reason it is important that the stream called _VBA_PROJECT appears as a last child,
        //otherwise enumeration does not work in Win32 structured storage.
        MemoryStream vbaProject = ms.as(vba.getByIndex(4), MemoryStream.class);
        msAssert.areEqual(0x0a09L, vbaProject.getLength());
    }

    /// <summary>
    /// Used to be a problem with a non zero length string written after a zero length stream.
    /// </summary>
    @Test
    public void testZeroLength() throws Exception
    {
        FileSystem srcFs = new FileSystem(Guid.Empty);

        //This is the zero length stream.
        MemoryStream srcA = new MemoryStream();
        srcFs.getRoot().set("A", srcA);

        //Non zero length stream that follows the zero length one. 
        //Need to write more than one length of mini fat sector (more than 64 bytes).
        MemoryStream srcB = new MemoryStream();
        for (int i = 0; i < 100; i++)
            srcB.writeByte((byte)i);
        srcFs.getRoot().set("B", srcB);

        //Save the complete file system.
        MemoryStream dstStream = new MemoryStream();
        srcFs.save(dstStream);

        //Load the file system from the created ss.
        dstStream.setPosition(0);
        FileSystem dstFs = new FileSystem(dstStream);

        //Check the streams loaded okay.
        MemoryStream dstA = ms.as(dstFs.getRoot().get("A"), MemoryStream.class);
        msAssert.areEqual(0L, dstA.getLength());

        MemoryStream dstB = ms.as(dstFs.getRoot().get("B"), MemoryStream.class);
        msAssert.areEqual(100L, dstB.getLength());
        for (int i = 0; i < 100; i++)
            msAssert.areEqual(i, dstB.readByte());
    }

    /// <summary>
    /// Test a file with an OLE2 object embedded.
    /// </summary>
    @Test
    public void testOle2() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestOle2.doc"));
        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestOle2.doc", "", "");
        fs.save(outFileName);
        fs = new FileSystem(outFileName);

        MemoryStorage objPool = ms.as(fs.getRoot().get("ObjectPool"), MemoryStorage.class);
        MemoryStorage obj = ms.as(objPool.get("_1157519979"), MemoryStorage.class);    //This is object id in the storage.

        //It is important these streams are sorted properly, otherwise the file has problems in Win32 SS.
        msAssert.areEqual(0, obj.indexOfKey("\u0003PRINT"));
        msAssert.areEqual(1, obj.indexOfKey("\u0001CompObj"));
        msAssert.areEqual(2, obj.indexOfKey("\u0003ObjInfo"));
        msAssert.areEqual(3, obj.indexOfKey("\u0003OCXNAME"));
        msAssert.areEqual(4, obj.indexOfKey("contents"));
    }

    /// <summary>
    /// Reported by Laurence, trouble for 111 fat sectors. 
    /// Looks like my code was writing one less fat sector than indicated.
    /// </summary>
    @Test
    public void testBigExcel() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestBigExcel.xls"));
        //Fat itself uses 111 sectors from 14070 to 14080
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(14070));
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(14080));

        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestBigExcel.xls", "", "");
        fs.save(outFileName);

        //Load out own file.
        fs = new FileSystem(TestFxUtil.buildTestFileName(outFileName));
        //Fat should have the same position, but used to end one position too early.
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(14070));
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(14080));
    }

    /// <summary>
    /// Used to go into infinite loop, but now reads okay.
    /// Looks like does not have "end of chain" marker in the fat.
    /// </summary>
    @Test
    public void testInvalid1() throws Exception
    {
        new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestInvalid1.xls"));
    }

    /// <summary>
    /// WORDSNET-1016 Aspose generated documents sometimes are handled incorrectly by MS Outlook.
    /// </summary>
    @Test
    public void testDefect1016() throws Exception
    {
        // Although this file opens in MS Excel okay, if you send this file via Outlook,
        // the file that you will receive is likely to be corrupted.
        // This happens because the file has FAT that is not entirely correct and Outlook
        // modifies it just a little bit to update some properties in the document.
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestDefect1016.xls"));
        msAssert.areEqual(256, fs.getFat().getCount());
        // This is actually where the problem is visible. 
        msAssert.areEqual(0x01, fs.getFat().get(0x80));
        msAssert.areEqual(0x02, fs.getFat().get(0x81));

        // Saving actually corrects the file.
        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestDefect1016.xls", "", "");
        fs.save(outFileName);

        // Load out own file.
        fs = new FileSystem(TestFxUtil.buildTestFileName(outFileName));
        msAssert.areEqual(256, fs.getFat().getCount());
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(0x80));
        msAssert.areEqual(FatEntryType.FAT_SECT, fs.getFat().get(0x81));
        msAssert.areEqual(FatEntryType.FREE_SECT, fs.getFat().get(0x82));
    }

    /// <summary>
    /// WORDSNET-1045 Document hangs up Aspose.Words when opened.
    /// Reported by Salman Safraz: Document causes hang-up when opened by Aspose.Words. 
    /// Caused by invalid directory in the structured storage file, made resilient.
    /// </summary>
    @Test
    public void testDefect1045() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestDefect1045.doc"));
        fs.save(TestFxUtil.buildOutFileName("Ss\\TestDefect1045.doc", "", ""));
    }

    /// <summary>
    /// WORDSNET-1048 Opening document in Aspose.Words fails with 'Unable to read beyond the end of the stream.' exception.
    /// The structures storage file is 1 byte shorter than it should be.
    /// Forum thread:
    ///   'Exceptions when trying to open mail merge document'
    ///   https://www.aspose.com/Community/forums/thread/50346.aspx
    ///   Openning document in Aspose fails with 'Unable to read beyond the end of the stream.' exception.
    /// </summary>
    @Test
    public void testDefect1048() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestDefect1048.doc"));
        fs.save(TestFxUtil.buildOutFileName("Ss\\TestDefect1048.doc", "", ""));
    }

    /// <summary>
    /// WORDSNET-1049 Opening document in Aspose.Words fails with 'Object reference not set to an instance of an object.' exception.
    /// The problem was the file was shorter by 1 byte. Made our code resilient.
    /// Forum thread:
    ///   'Exceptions when trying to open mail merge document'
    ///   https://www.aspose.com/Community/forums/thread/50346.aspx
    ///   Openning document in Aspose fails with 'Object reference not set to an instance of an object.' exception.
    /// </summary>
    @Test
    public void testDefect1049() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestDefect1049.doc"));
        fs.save(TestFxUtil.buildOutFileName("Ss\\TestDefect1049.doc", "", ""));
    }

    /// <summary>
    /// RK Used to throw because I was skipping EntryType.Invalid directory entries. 
    /// </summary>
    @Test
    public void testDirEntryTypeInvalid() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestDirEntryTypeInvalid.doc"));
        fs.save(TestFxUtil.buildOutFileName("Ss\\TestDirEntryTypeInvalid.doc", "", ""));
    }
}

