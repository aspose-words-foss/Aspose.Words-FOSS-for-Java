// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/19/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;


@Test
public class TestDmlGeometryFallback
{
    @BeforeMethod (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testFallbackGeometry() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestFallbackGeometry.docx");
        // Remove alternate content. Test checks how alternate content is generated using our code.
        removeFallback(doc);
        TestUtil.saveOpen(doc, "Model\\DrawingML\\TestFallbackGeometry.docx");
    }

    @Test
    public void testFallbackGroupGeometry() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlGroup.docx");
        // Remove alternate content. Test checks how alternate content is generated using our code.
        removeFallback(doc);
        TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlGroup.docx");
    }

    @Test
    public void testFallbackNestedGroupGeometry() throws Exception
    {
        Document doc = TestUtil.open("Model\\DrawingML\\TestDmlNestedGroup.docx");
        // Remove alternate content. Test checks how alternate content is generated using our code.
        removeFallback(doc);
        TestUtil.saveOpen(doc, "Model\\DrawingML\\TestDmlNestedGroup.docx");
    }

    private static void removeFallback(Document doc)
    {
        NodeCollection dmls = doc.getChildNodes(NodeType.GROUP_SHAPE, true);
        for (GroupShape dml : (Iterable<GroupShape>) dmls)
            dml.getRunPr().remove(FontAttr.ALTERNATE_CONTENT);

        dmls = doc.getChildNodes(NodeType.SHAPE, true);
        for (Shape dml : (Iterable<Shape>) dmls)
            dml.getRunPr().remove(FontAttr.ALTERNATE_CONTENT);
    }
}

