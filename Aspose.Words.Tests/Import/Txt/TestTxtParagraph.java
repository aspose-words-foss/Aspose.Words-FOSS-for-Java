// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/06/2012 by Alexey Butalov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Unit tests for TxtParagraph 
/// </summary>
@Test
public class TestTxtParagraph
{
    @Test
    public void testAppendText()
    {
        TxtParagraph txtParagraph = new TxtParagraph();
        msAssert.areEqual(0, txtParagraph.getLinesCount());
        msAssert.areEqual("", txtParagraph.getText());

        txtParagraph.appendText("line1");
        msAssert.areEqual(1, txtParagraph.getLinesCount());
        msAssert.areEqual("line1", txtParagraph.getText());

        txtParagraph.appendText("line2");
        msAssert.areEqual(2, txtParagraph.getLinesCount());
        msAssert.areEqual("line1 line2", txtParagraph.getText());
    }
}
