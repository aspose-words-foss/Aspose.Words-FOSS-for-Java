// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/04/2024 by Vyacheslav Deryushev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests adjustment raw values.
/// </summary>
@Test
public class TestAdjustments
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }




    /// <summary>
    /// WORDSNET-27018 Incorrect count of adjustments after setting adjust to 0.
    /// We should not exclude zero values from the collection.
    /// </summary>
    @Test
    public void test27018() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Shape shape = new Shape(doc, ShapeType.ARROW);
        shape.setWidth(100.0);
        shape.setHeight(100.0);
        shape.getAdjustments().get(0).setValue(0);

        builder.moveToDocumentStart();
        builder.insertNode(shape);

        OoxmlSaveOptions ooxmlSaveOptions = new OoxmlSaveOptions();
        ooxmlSaveOptions.setCompliance(OoxmlCompliance.ECMA_376_2006);

        doc = TestUtil.saveOpen(doc, "Model\\Shape\\Adjustments\\Test27018.docx", ooxmlSaveOptions);

        shape = (Shape)doc.getChildNodes(NodeType.SHAPE, true).get(0);
        AdjustmentCollection adjustments = shape.getAdjustments();

        msAssert.areEqual(0, adjustments.get(0).getValue());
        msAssert.areEqual(5400, adjustments.get(1).getValue());
        msAssert.areEqual(2, adjustments.getCount());
    }
}

