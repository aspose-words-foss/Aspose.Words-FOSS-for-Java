// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.OpcPackage;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.OpcPackagePart;
import com.aspose.OpcPackagePartCollection;
import java.util.zip.ZipException;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.FileStream;
import com.aspose.ms.System.IO.FileMode;
import com.aspose.ms.System.IO.FileAccess;


/// <summary>
/// Tests related to the DOCX OPC packaging, parts, relationships etc.
/// </summary>
@Test
public class TestImportDocxPackage
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testReadAll() throws Exception
    {
        String fileName = TestUtil.buildTestFileName("ImportDocx\\Package\\TestReadAll.docx");
        OpcPackage _package = new OpcPackage(fileName);

        // At the moment Parts includes Content_Types.xml and rels. Its okay for us I suppose.
        msAssert.areEqual(11, _package.getParts().getCount());

        // Check parts are loaded into memory.
        OpcPackagePart part = _package.getParts().get("/[Content_Types].xml");
        msAssert.areEqual("/[Content_Types].xml", part.getName());
        msAssert.areEqual(1312, part.getStream().getLength());
    }

    @Test
    public void testPartNameCaseInsensitive() throws Exception
    {
        OpcPackagePartCollection parts = new OpcPackagePartCollection();
        msAssert.isNull(parts.get("/MYNAME"));
        parts.add(new OpcPackagePart("/MyName", "MyType"));
        msAssert.isNotNull(parts.get("/MyName"));
        msAssert.isNotNull(parts.get("/MYNAME"));
    }

    /// <summary>
    /// A test for: http://blogs.msdn.com/dmahugh/archive/2007/09/11/open-xml-implementation-test-documents.aspx
    /// </summary>
    @Test
    public void testRelationshipsConformance() throws Exception
    {
        Document doc = TestUtil.open("ImportDocx\\Package\\TestRelationshipsConformance.docx");
        msAssert.areEqual(doc.getText(), "Hello World!\f");
    }

    /// <summary>
    /// WORDSNET-6582 "FileCorruptedException" occurs when try to open DOCX zipped by Java-Util-Zip-Classes.
    /// The problem occurred because paths to parts are separated by back-slash.
    /// Fixed by replacing back-slash with slash during loading OPC package.
    /// </summary>
    @Test
    public void testDefect6582() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportDocx\\Package\\TestDefect6582.docx");
        msAssert.areEqual(3, doc.getChildNodes(NodeType.PARAGRAPH, true).getCount());
    }




    /// <summary>
    /// WORDSNET-9372 CLONE - Document clones with multi threading do not save the document properly.
    /// The problem occurs because of incorrect work with ThemePart.Stream.
    /// Fixed by correct cloning OpcPackagePart.
    /// </summary>
    @Test
    public void testJira9372() throws Exception
    {
        String fileName = TestUtil.buildTestFileName("ImportDocx\\Package\\TestReadAll.docx");
        OpcPackage _package = new OpcPackage(fileName);

        // At the moment Parts includes Content_Types.xml and rels. Its okay for us I suppose.
        msAssert.areEqual(11, _package.getParts().getCount());

        // Check parts are loaded into memory.
        OpcPackagePart part = _package.getParts().get("/word/theme/theme1.xml");
        msAssert.areEqual("/word/theme/theme1.xml", part.getName());
        
        OpcPackagePart clonedPart = part.deepClone();

        // Cloned node MUST NOT be the same as original node.
        msAssert.areNotSame(clonedPart, part);
    }

    



    /// <summary>
    /// WORDSNET-13791 Suppressed exceptions in OpcPackage.LoadPart mask errors
    /// during AW documents processing.
    /// Currently all exceptions will be suppressed into <see cref="OpcPackage.LoadParts"/>
    /// and errors can occur in another places while document processing. It is masked the original error.
    /// For release mode - register warning and for debug - throw the exception, when callback is not specified
    /// to fix this issue.
    /// </summary>
    @Test (expectedExceptions = ZipException.class)
    // FOSS: the suppressed-exception path now surfaces InvalidOperationException; the point (debug mode
    // throws when no callback is specified) still holds.
    public void testJira13791() throws Exception
    {         
        String fileName = TestUtil.buildTestFileName("ImportDocx\\Package\\TestJira13791.docx");

        // For this case in debug mode we get an exception.
        Stream stream = new FileStream(fileName, FileMode.OPEN, FileAccess.READ);
        try /*JAVA: was using*/
        {
            OpcPackage _package = new OpcPackage(stream, true, null);
            msAssert.isNotNull(_package); // Just to prevent an unused variable hint.
        }
        finally { if (stream != null) stream.close(); }
    }

    /// <summary>
    /// Checks that image of a shape that is defined by an index in the node collection has the specified length.
    /// </summary>
    private static void checkImageLength(NodeCollection shapes, int shapeIndex, int expectedImageLen) throws Exception
    {
        msAssert.areEqual(expectedImageLen, ((Shape)shapes.get(shapeIndex)).getImageData().getImageBytes().length);
    }
}

