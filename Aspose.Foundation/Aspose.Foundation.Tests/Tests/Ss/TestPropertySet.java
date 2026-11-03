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
import com.aspose.ms.System.IO.MemoryStream;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// Test that structured storage property sets can be loaded and saved.
/// </summary>
@Test
public class TestPropertySet
{
    @BeforeMethod (alwaysRun = true)
    public void setUp() throws Exception
    {
        Directory.createDirectory(TestFxUtil.getInTestOutPath("Ss"));
    }

    /// <summary>
    /// Test how unicode property names and values are read.
    /// </summary>
    @Test
    public void testPropertiesReadUnicode() throws Exception
    {
        Stream srcStream = File.openRead(TestFxUtil.buildTestFileName("Ss\\DocumentSummaryInfoUnicode.bin"));
        try /*JAVA: was using*/
        {
            PropertySet ps = new PropertySet(srcStream);
            doTestProperties(ps, 11, 5, "Test String");
        }
        finally { if (srcStream != null) srcStream.close(); }
    }

    /// <summary>
    /// Test how non unicode property names and values are read.
    /// </summary>
    @Test
    public void testPropertiesRead() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestProperties.doc"));
        doTestProperties(fs);
    }

    /// <summary>
    /// Test how non unicode property names and values are read and written.
    /// Note that we always write in unicode.
    /// </summary>
    @Test
    public void testPropertiesReadWrite() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestProperties.doc"));

        //Load and save the property set.
        Stream srcStream = ms.as(fs.getRoot().get("\u0005DocumentSummaryInformation"), Stream.class);
        PropertySet ps = new PropertySet(srcStream);

        MemoryStream dstStream = new MemoryStream();
        ps.save(dstStream);
        fs.getRoot().set("\u0005DocumentSummaryInformation", dstStream);

        //Load and check that the property set can be loaded after it was saved by us.
        String outFileName = TestFxUtil.buildOutFileName("Ss\\TestProperties.doc", "", "");
        fs.save(outFileName);
        fs = new FileSystem(outFileName);
        doTestProperties(fs);
    }

    private void doTestProperties(FileSystem fs) throws Exception
    {
        Stream stream = ms.as(fs.getRoot().get("\u0005DocumentSummaryInformation"), Stream.class);
        PropertySet ps = new PropertySet(stream);
        doTestProperties(ps, 13, 6, "Привет");
    }

    private static void doTestProperties(PropertySet ps, int sect1Count, int sect2Count, String myStringValue)
    {
        msAssert.areEqual(2, ps.getSections().size());

        //These are doc summary info properties
        PropertySetSection sect1 = (PropertySetSection)ps.getSections().get(0);
        msAssert.areEqual(PropertySetSection.FMTID_DOC_SUMMARY_INFO, sect1.getFmtId());
        msAssert.areEqual(sect1Count, sect1.getProperties().getCount());
        msAssert.areEqual("Test Company", (String)sect1.getProperties().getById((int)DocSummaryInfoProperty.COMPANY).getValue());

        //These are user defined properties
        PropertySetSection sect2 = (PropertySetSection)ps.getSections().get(1);
        msAssert.areEqual(PropertySetSection.FMTID_USER_DEFINED, sect2.getFmtId());
        msAssert.areEqual(sect2Count, sect2.getProperties().getCount());
        msAssert.areEqual(myStringValue, sect2.getProperties().getByName("MyString").getValue());
        msAssert.areEqual(123, sect2.getProperties().getByName("MyInt").getValue());
        msAssert.areEqual(123.45, (/*double*/Double)sect2.getProperties().getByName("MyDouble").getValue());
        msAssert.areEqual(true, sect2.getProperties().getByName("MyBool").getValue());
        DateTime d = (DateTime)(sect2.getProperties().getByName("MyDate").getValue());
        msAssert.areEqual(new DateTime(2003, 1, 31, 12, 0, 0), d);
    }

    /// <summary>
    /// Just trying to figure out how Guid is saved into byte array, no need to port to Java.
    /// </summary>
    @Test
    public void testGuid() throws Exception
    {
        Guid guid = new Guid("00010203-0405-0607-0809-0a0b0c0d0e0f");
        byte[] x = guid.toByteArray();
        BinaryReader reader = new BinaryReader(new MemoryStream(x), Encoding.getUnicode());
        msAssert.areEqual(0x0607040500010203L, reader.readInt64());
        msAssert.areEqual(0x0f0e0d0c0b0a0908L, reader.readInt64());
    }

    /// <summary>
    /// Make sure DateTime.MinValue is written and read from the file okay.
    /// </summary>
    @Test
    public void testZeroDateTime() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestProperties.doc"));

        //Store DateTime.MinValue into a property.
        Stream stream = ms.as(fs.getRoot().get("\u0005DocumentSummaryInformation"), Stream.class);
        PropertySet ps = new PropertySet(stream);
        PropertySetSection sect = (PropertySetSection)ps.getSections().get(1);
        sect.getProperties().getByName("MyDate").setValue(DateTime.MinValue);
        stream.setPosition(0);
        ps.save(stream);

        //Read the property again and check what's there.
        stream.setPosition(0);
        ps = new PropertySet(stream);
        sect = (PropertySetSection)ps.getSections().get(1);
        msAssert.areEqual(DateTime.MinValue, sect.getProperties().getByName("MyDate").getValue());
    }

    /// <summary>
    /// When you open the output file in Excel, you will see a security warning
    /// with digital signature details. But it is also important to go into the 
    /// VBA Editor (Alt + F11), Tools, Digital Signature menu and check the project
    /// is signed there, since I've seen digital signature alright in the warning,
    /// but missing in the VBA Editor.
    /// </summary>
    @Test
    public void testExcelSigned() throws Exception
    {
        FileSystem fs = new FileSystem(TestFxUtil.buildTestFileName("Ss\\TestExcelSigned.xls"));

        // Read and save the property set.
        Stream stream = ms.as(fs.getRoot().get("\u0005DocumentSummaryInformation"), Stream.class);
        PropertySet ps = new PropertySet(stream);
        stream.setPosition(0);
        ps.save(stream);

        // Check the digital signature property is there. The code was not reading VT_BLOB properties.
        PropertySetSection sect = (PropertySetSection)ps.getSections().get(0);
        Property prop = sect.getProperties().getById(24);
        msAssert.isNotNull(prop);
        msAssert.areEqual(5508, ((byte[])prop.getValue()).length);

        // Here was another problem. It looks like if the section with user properties
        // did not have a dictionary of property names (even if it was empty), 
        // the digital signature was disappearing in the VBA Editor.
        sect = (PropertySetSection)ps.getSections().get(1);
        byte[] userProps = sect.toByteArray();
        byte[] goldUserProps = new byte[]
        {
            (byte) 0x24, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // length of the section
            (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // count of properties
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // id of the dictionary property
            (byte) 0x18, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // offset of the dictionary property
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // id of the code page property
            (byte) 0x1c, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // offset of the code page property
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // the value of the dictionary propertyt (zero property names)
            (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,    // VT_I2
            (byte) 0xb0, (byte) 0x04, (byte) 0x00, (byte) 0x00,    // 1200 unicode code page
        };
        ArrayUtil.checkArraysEqual(userProps, goldUserProps);

        // And just write it out so I can play in MS Excel to see if it recognizes it as signed.
        fs.save(TestFxUtil.buildOutFileName("Ss\\TestExcelSigned.xls", "", ""));
    }
}

