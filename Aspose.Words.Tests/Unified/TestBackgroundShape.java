// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.DrColor;
import com.aspose.ms.System.Drawing.msColor;


@Test
public class TestBackgroundShape
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testNullBackground() throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual(null, doc.getBackgroundShape());
        TestUtil.saveOpen(doc, "Model\\BackgroundShape\\TestNullBackground.docx");
    }

    /// <summary>
    /// Checks it is possible to set the background shape to null.
    /// </summary>
    @Test
    public void testSetNullBackground() throws Exception
    {
        Document doc = new Document();
        doc.setBackgroundShape(new Shape(doc, ShapeType.RECTANGLE));
        msAssert.isNotNull(doc.getBackgroundShape());
        doc.setBackgroundShape(null);
        msAssert.isNull(doc.getBackgroundShape());
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe shape was created from a different document.\\E")
    public void testSetOtherDocumentShapeAsBackground() throws Exception
    {
        Document otherDoc = new Document();
        Document myDoc = new Document();
        myDoc.setBackgroundShape(new Shape(otherDoc, ShapeType.RECTANGLE));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QThe shape is a child of another node.\\E")
    public void testSetShapeWithParentAsBackground() throws Exception
    {
        Document doc = new Document();
        Shape shape = new Shape(doc, ShapeType.RECTANGLE);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(shape);
        doc.setBackgroundShape(shape);
    }

    @Test
    public void testRectangleBackground() throws Exception
    {
        Document doc = new Document();
        doc.setBackgroundShape(new Shape(doc, ShapeType.RECTANGLE));
        doc.getBackgroundShape().setFillColor(Color.YELLOW);
        TestUtil.saveOpen(doc, "Model\\BackgroundShape\\TestRectangleBackground.docx");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QOnly a rectangle shape can be set as a document background.\\E")
    public void testTriangleBackground() throws Exception
    {
        Document doc = new Document();
        doc.setBackgroundShape(new Shape(doc, ShapeType.TRIANGLE));
    }

    @Test
    public void testPageColor() throws Exception
    {
        Document doc = new Document();
        doc.setPageColor(DrColor.GREEN.toNativeColor());  // Going via DrColor.Green because native green on Java and .NET are different color values.
        TestUtil.saveOpen(doc, "Model\\BackgroundShape\\TestPageColor.docx");
    }

    /// <summary>
    /// WORDSNET-20068 Document.PageColor sets the page color to black when it is set to Color.Empty.
    /// When PageColor is set to Color.Empty BackgroundShape should be removed.
    /// </summary>
    @Test
    public void test20068() throws Exception
    {
        Document doc = new Document();

        doc.setPageColor(msColor.Empty);
        msAssert.isNull(doc.getBackgroundShape());
        msAssert.areEqual(msColor.Empty, doc.getPageColor());

        doc.setPageColor(Color.RED);
        msAssert.isNotNull(doc.getBackgroundShape());

        doc.setPageColor(msColor.Empty);
        msAssert.isNull(doc.getBackgroundShape());
        msAssert.areEqual(msColor.Empty, doc.getPageColor());
    }
}

