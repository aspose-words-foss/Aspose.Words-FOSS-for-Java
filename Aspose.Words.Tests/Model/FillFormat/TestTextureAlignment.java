// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/09/2021 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// The class for testing TextureAlignment property of Fill object.
/// </summary>
@Test
public class TestTextureAlignment extends TestFillFormatBase
{
    /// <summary>
    /// Checks obtaining TextureAlignment from DML shapes.
    /// </summary>
    @Test
    public void testGetTextureAlignmentDml() throws Exception
    {
        /*TextureAlignment*/int[] expAlignments = new /*TextureAlignment*/int[]
        {
            TextureAlignment.BOTTOM_LEFT, TextureAlignment.BOTTOM, TextureAlignment.BOTTOM_RIGHT,
            TextureAlignment.RIGHT, TextureAlignment.CENTER, TextureAlignment.LEFT,
            TextureAlignment.TOP_RIGHT, TextureAlignment.TOP, TextureAlignment.TOP_LEFT
        };

        Document doc = open("TextureAlignments", ShapeMarkupLanguage.DML);
        ShapeCollection shapes = doc.getFirstSection().getBody().getShapes();
        for (int i = 0; i < shapes.getCount(); i++)
        {
            Fill fill = shapes.get(i).getFill();

            msAssert.areEqual(expAlignments[i], fill.getTextureAlignment());
        }
    }

    /// <summary>
    /// Checks applying TextureAlignment to DML shapes.
    /// </summary>
    @Test
    public void testSetTextureAlignmentDml() throws Exception
    {
        Document doc = open("UserTextured", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.areEqual(TextureAlignment.TOP_LEFT, fill.getTextureAlignment());
        fill.setTextureAlignment(TextureAlignment.CENTER);
        msAssert.areEqual(TextureAlignment.CENTER, fill.getTextureAlignment());
    }

    /// <summary>
    /// Checks obtaining TextureAlign from VML shape.
    /// </summary>
    @Test
    public void testGetTextureAlignmentVml() throws Exception
    {
        Document doc = open("UserTextured", ShapeMarkupLanguage.VML);
        Fill fill = getFill(doc);
        msAssert.areEqual(TextureAlignment.NONE, fill.getTextureAlignment());
    }

    /// <summary>
    /// Checks applying TextureAlignment to VML shape.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QTextureAlignment cannot be applied to VML shapes.\\E")
    public void testSetTextureAlignmentVml() throws Exception
    {
        Document doc = open("UserTextured", ShapeMarkupLanguage.VML);
        Fill fill = getFill(doc);
        fill.setTextureAlignment(TextureAlignment.CENTER);
    }

    /// <summary>
    /// Checks obtaining TextureAlignment from Font fill.
    /// </summary>
    @Test
    public void testGetTextureAlignmentText() throws Exception
    {
        Document doc = open("TextNoFill", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.areEqual(TextureAlignment.NONE, fill.getTextureAlignment());
    }

    /// <summary>
    /// Checks applying TextureAlignment to Font fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QTextureAlignment cannot be applied to Font.\\E")
    public void testSetTextureAlignmentText() throws Exception
    {
        Document doc = open("TextNoFill", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.setTextureAlignment(TextureAlignment.CENTER);
    }

    /// <summary>
    /// Checks applying TextureAlignment to non-texture fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QTextureAlignment can be applied to texture fill only.\\E")
    public void testTextureAlignmentNotTextureFill() throws Exception
    {
        Document doc = open("Solid", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.setTextureAlignment(TextureAlignment.CENTER);
    }

    /// <summary>
    /// Checks applying TextureAlignment.None.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QTextureNone cannot be applied directly.\\E")
    public void testTextureAlignmentNone() throws Exception
    {
        Document doc = open("UserTextured", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.setTextureAlignment(TextureAlignment.NONE);
    }

    /// <summary>
    /// Checks obtaining TextureAlignment from non-tiling Blip fill.
    /// </summary>
    @Test
    public void testGetPictureAlignment() throws Exception
    {
        Document doc = open("UserPicture", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.areEqual(TextureAlignment.NONE, fill.getTextureAlignment());
    }

    /// <summary>
    /// Checks applying TextureAlignment to non-tiling Blip fill.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "\\QTextureAlignment can be applied to tile texture fill only.\\E")
    public void testSetPictureAlignment() throws Exception
    {
        Document doc = open("UserPicture", ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        fill.setTextureAlignment(TextureAlignment.BOTTOM);
    }
}

