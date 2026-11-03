// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2016 by Anatoliy Sidorenko
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.FontStyle;
import java.awt.Font;
import com.aspose.ms.System.Drawing.msFont;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestFont
{

    @Test
    public void testCtor()
    {
        testCtor("Arial", 8.0f);
        testCtor("Times New Roman", 8.1f);
        testCtor("Arial", 8.0f, FontStyle.BOLD);
        testCtor("Times New Roman", 8.1f, FontStyle.BOLD | FontStyle.ITALIC);
    }

    private void testCtor(String fontName, float sizeInPoints)
    {
        Font testFont = msFont.newFont(fontName, sizeInPoints);
        msAssert.areEqual(testFont.getName(), fontName);
        msAssert.areEqual(testFont.getSize2D(), sizeInPoints);
    }

    private void testCtor(String fontName, float sizeInPoints, /*FontStyle*/int fontStyle)
    {
        Font testFont = msFont.newFont(fontName, sizeInPoints, fontStyle);
        msAssert.areEqual(testFont.getName(), fontName);
        msAssert.areEqual(testFont.getSize2D(), sizeInPoints);
        msAssert.areEqual(testFont.getStyle(), fontStyle);
    }
}

