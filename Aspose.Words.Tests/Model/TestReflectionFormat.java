// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests reflection formatting.
/// </summary>
@Test
public class TestReflectionFormat
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-26671 Add support to get Reflection effect of a Shape.
    /// Tests all reflection properties getters and setters in DML shape.
    /// </summary>
    @Test
    public void test26671Dml() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\ReflectionFormat\\Test26671Dml.docx");

        ReflectionFormat reflection = doc.getFirstSection().getBody().getShapes().get(0).getReflection();

        // Test all getters.
        msAssert.areEqual(0.55, reflection.getTransparency());
        msAssert.areEqual(0.26, reflection.getSize());
        msAssert.areEqual(4.7, reflection.getBlur());
        msAssert.areEqual(12.7, reflection.getDistance());

        // Test setters.
        reflection.setTransparency(0.37);
        msAssert.areEqual(0.37, reflection.getTransparency());

        reflection.setSize(0.48);
        msAssert.areEqual(0.48, reflection.getSize());

        reflection.setBlur(17.5);
        msAssert.areEqual(17.5, reflection.getBlur());

        reflection.setDistance(9.2);
        msAssert.areEqual(9.2, reflection.getDistance());

        // Roundtrip and check again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\ReflectionFormat\\Test26671Dml.docx");
        reflection = doc.getFirstSection().getBody().getShapes().get(0).getReflection();
        msAssert.areEqual(0.37, reflection.getTransparency());
        msAssert.areEqual(0.48, reflection.getSize());
        msAssert.areEqual(17.5, reflection.getBlur());
        msAssert.areEqual(9.2, reflection.getDistance());
    }

    // FOSS: Test26671Vml removed — loads a VML .doc to assert that Reflection is unsupported
    // on VML shapes. The Doc reader is removed, so the .doc can no longer load. The DML path
    // stays covered by Test26671Dml.

    /// <summary>
    /// Relates to WORDSNET-26671.
    /// Tests remove reflection.
    /// </summary>
    @Test
    public void testRemove() throws Exception
    {
        Document doc = TestUtil.open("Model\\Shape\\ReflectionFormat\\TestRemove.docx");

        Shape shape = doc.getFirstSection().getBody().getShapes().get(0);
        ReflectionFormat reflection = shape.getReflection();
        msAssert.areEqual(0.55, 0.000001, reflection.getTransparency());
        msAssert.areEqual(0.68, 0.000001, reflection.getSize());
        msAssert.areEqual(14, reflection.getBlur());
        msAssert.areEqual(6, reflection.getDistance());

        reflection.remove();
        // Check properties and then roundtrip to ensure that
        // access to non-existent reflection does not create it.
        msAssert.areEqual(0.0, shape.getReflection().getTransparency());
        msAssert.areEqual(0.0, shape.getReflection().getDistance());
        msAssert.areEqual(0.0, shape.getReflection().getSize());
        msAssert.areEqual(0.0, shape.getReflection().getBlur());

        // Roundtrip and check again.
        doc = TestUtil.saveOpen(doc, "Model\\Shape\\ReflectionFormat\\TestRemove.docx");
        shape = doc.getFirstSection().getBody().getShapes().get(0);
        msAssert.areEqual(0.0, shape.getReflection().getTransparency());
        msAssert.areEqual(0.0, shape.getReflection().getDistance());
        msAssert.areEqual(0.0, shape.getReflection().getSize());
        msAssert.areEqual(0.0, shape.getReflection().getBlur());
    }

}

