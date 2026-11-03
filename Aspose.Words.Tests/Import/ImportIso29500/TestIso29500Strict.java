// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/03/2013 by Andrey Noskov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Test that OOXML ISO/IEC 29500 Strict compliance level files are opened correctly.
/// </summary>
@Test
public class TestIso29500Strict
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// andrnosk: WORDSNET-7906 Read characterSet attribute (strict OOXML).
    /// </summary>
    @Test
    public void testJira7741CharacterSet() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("ImportIso29500S\\TestJira7741CharacterSet.docx");

        msAssert.isFalse(doc.getComplianceInfo().isIsoStrict());

        FontInfoCollection fontInfos = doc.getFontInfos();

        msAssert.areEqual(204, fontInfos.get(0).getCharset());
        msAssert.areEqual(204, fontInfos.get(1).getCharset());
        msAssert.areEqual(0, fontInfos.get(2).getCharset());
        msAssert.areEqual(177, fontInfos.get(3).getCharset());
        msAssert.areEqual(128, fontInfos.get(14).getCharset());
        msAssert.areEqual(129, fontInfos.get(16).getCharset());
        msAssert.areEqual(204, fontInfos.get(29).getCharset());
        msAssert.areEqual(0, fontInfos.get(30).getCharset());
        msAssert.areEqual(177, fontInfos.get(62).getCharset());
        msAssert.areEqual(136, fontInfos.get(63).getCharset());
        msAssert.areEqual(134, fontInfos.get(79).getCharset());
        msAssert.areEqual(134, fontInfos.get(124).getCharset());
        msAssert.areEqual(128, fontInfos.get(151).getCharset());
        msAssert.areEqual(136, fontInfos.get(154).getCharset());
        msAssert.areEqual(134, fontInfos.get(161).getCharset());
        msAssert.areEqual(177, fontInfos.get(168).getCharset());
    }

    /// <summary>
    /// andrnosk: WORDSNET-8049 Resolve the problem with the difference 
    /// between Docx RelationshipTypes in strict and transitional OOXML.
    /// </summary>
    @Test
    public void testJira8049() throws Exception
    {
        // Test document was created using MS Word 2013.
        Document doc = TestUtil.open("ImportIso29500S\\TestJira8049.docx");
        msAssert.isTrue(doc.getComplianceInfo().isIsoStrict());
    }

    /// <summary>
    /// andrnosk: WORDSNET-8241 (DocxNamespaces) Resolve the problem with the difference between namespaces 
    /// in strict and transitional OOXML.
    /// </summary>
    @Test
    public void testJira8241() throws Exception
    {
        // Test document is Strict.
        Document doc = TestUtil.open("ImportIso29500S\\TestJira7741DocxNamespaces.docx");
        msAssert.isTrue(doc.getComplianceInfo().isIsoStrict());
        msAssert.areEqual(1, doc.getChildNodes(NodeType.SHAPE, true).getCount());

        // This fix allows us correctly read DrawingMLRelatedDataCollection.
        Shape drawingML = (Shape)doc.getChild(NodeType.SHAPE, 0, true);
        msAssert.isTrue(drawingML.getImageData().hasImage());
    }
}
