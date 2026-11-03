// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests soft edge formatting.
/// </summary>
@Test
public class TestSoftEdgeFormat
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-26672 Add support to get Soft Edges effect of a Shape.
    /// Tests soft edge properties getters and setters in DML shape.
    /// </summary>
    @Test
    public void test26672Dml() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\SoftEdgeFormat\\Test26672Dml.docx");

        SoftEdgeFormat softEdge = doc.getFirstSection().getBody().getShapes().get(0).getSoftEdge();

        // Test getter.
        msAssert.areEqual(27, softEdge.getRadius());

        // Test setter.
        softEdge.setRadius(40.8);
        msAssert.areEqual(40.8, softEdge.getRadius());

        // Roundtrip and check again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\SoftEdgeFormat\\Test26672Dml.docx");
        softEdge = doc.getFirstSection().getBody().getShapes().get(0).getSoftEdge();
        msAssert.areEqual(40.8, softEdge.getRadius());
    }

    // FOSS: Test26670VmlRadius removed — loads a VML .doc to assert that SoftEdge is
    // unsupported on VML shapes. The Doc reader is removed, so the .doc can no longer load.


}

