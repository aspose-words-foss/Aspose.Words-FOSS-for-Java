// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/03/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.awt.Color;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;


/// <summary>
/// Tests glow formatting.
/// </summary>
@Test
public class TestGlowFormat
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-26670 Add support to get Glow effect of a Shape.
    /// Tests all glow properties getters and setters in DML shape.
    /// </summary>
    @Test
    public void test26670Dml() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\GlowFormat\\Test26670Dml.docx");

        GlowFormat glow = doc.getFirstSection().getBody().getShapes().get(0).getGlow();

        // Test all getters.
        TestUtil.checkColor(new Color((0x73FF6F00), true), glow.getColor());
        msAssert.areEqual(0.55, glow.getTransparency());
        msAssert.areEqual(18.3, glow.getRadius());

        // Test setters.
        glow.setColor(msColor.getOrangeRed());
        TestUtil.checkColor(new Color((/*unchecked*/((int)(int) 0xFFFF4500L)), true), glow.getColor());

        glow.setTransparency(0.37);
        msAssert.areEqual(0.37, glow.getTransparency());

        glow.setRadius(40.8);
        msAssert.areEqual(40.8, glow.getRadius());

        // Roundtrip and check again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\GlowFormat\\Test26670Dml.docx");
        glow = doc.getFirstSection().getBody().getShapes().get(0).getGlow();
        TestUtil.checkColor(new Color((/*unchecked*/((int)(int) 0xA1FF4500L)), true), glow.getColor());
        msAssert.areEqual(0.37, glow.getTransparency());
        msAssert.areEqual(40.8, glow.getRadius());
    }

    /// <summary>
    /// Relates to WORDSNET-26670.
    /// Tests expected exception in VML shape.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QObject doesn't support this action.\\E")
    public void test26670VmlColor() throws Exception
    {
        // FOSS: Doc reader removed; input converted to .docx. Word keeps the legacy shape as VML, which
        // still doesn't support Glow, so accessing Glow.Color throws as before.
        Document doc = TestUtil.open("Model\\Shape\\GlowFormat\\Test26670Vml.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.isNull(shape.getGlow().getColor());
    }

    /// <summary>
    /// Relates to WORDSNET-26670.
    /// Tests remove glow.
    /// </summary>
    @Test
    public void testRemove() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\GlowFormat\\TestRemove.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        GlowFormat glow = shape.getGlow();
        TestUtil.checkColor(new Color((/*unchecked*/((int)(int) 0xD9FA8072L)), true), glow.getColor());
        msAssert.areEqual(0.15, 0.000001, glow.getTransparency());
        msAssert.areEqual(30, glow.getRadius());

        glow.remove();
        // Check properties and then roundtrip to ensure that
        // access to non-existent glow does not create it.
        TestUtil.checkColor(Color.BLACK, shape.getGlow().getColor());
        msAssert.areEqual(0.0, shape.getGlow().getTransparency());
        msAssert.areEqual(0.0, shape.getGlow().getRadius());

        // Roundtrip and check again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\GlowFormat\\TestRemove.docx");
        shape = doc.getFirstSection().getBody().getShapes().get(0);
        TestUtil.checkColor(Color.BLACK, shape.getGlow().getColor());
        msAssert.areEqual(0.0, shape.getGlow().getTransparency());
        msAssert.areEqual(0.0, shape.getGlow().getRadius());
    }

}

