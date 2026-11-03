// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlPatternFill
{
    @Test
    public void applyStyleColor_StyleColorAppliedToAnyPlaceholder()
    {
        DmlPatternFill fill = new DmlPatternFill();
        //Arrange
        fill.setForegroundColor(new DmlPlaceholderColor());
        fill.setBackgroundColor(new DmlPlaceholderColor());
        DmlSystemColor color = new DmlSystemColor();
        //Act
        fill.applyStyleColor(color);
        //Assert
        msAssert.areEqual(color, ((DmlPlaceholderColor)fill.getBackgroundColor()).getStyleColor());
        msAssert.areEqual(color, ((DmlPlaceholderColor)fill.getForegroundColor()).getStyleColor());
    }

}

