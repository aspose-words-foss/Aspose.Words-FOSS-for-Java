// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestDmlTextListStyles
{
    @Test
    public void paragraphDefaultPropsDefined_DefinedValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getDefaultParagraphProperties().setTextIdentation(777);
        // Act
        // Assert
        msAssert.areEqual(777, styles.getListLevel9Style().getTextIdentation());
    }

    @Test
    public void paragraphPropsDefinedOnTopLevel_DefinedValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getListLevel1Style().setTextIdentation(777);
        // Act
        // Assert
        msAssert.areEqual(777, styles.getListLevel9Style().getTextIdentation());
    }

    @Test
    public void paragraphPropsNotDefined_DefaultValuesRetuned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        // Act
        // Assert
        Object expected =
            DmlParagraphPropertiesDefaults.getInstance().getProperty((int)DmlParagraphPropertiesIds.TEXT_IDENTATION);
        msAssert.areEqual(expected, styles.getListLevel9Style().getTextIdentation());
    }

    @Test
    public void paragraphPropsOverriden_OverridenValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getDefaultParagraphProperties().setTextIdentation(777);
        styles.getListLevel9Style().setTextIdentation(666);
        // Act
        // Assert
        msAssert.areEqual(666, styles.getListLevel9Style().getTextIdentation());
    }

    @Test
    public void runDefaultPropsDefined_DefinedValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getDefaultParagraphProperties().getDefaultRunProperties().setFontSize(new DmlTextPoints(11111));
        // Act
        // Assert
        msAssert.areEqual(new DmlTextPoints(11111), styles.getListLevel9Style().getDefaultRunProperties().getFontSize());
    }

    @Test
    public void runPropsDefinedOnTopLevel_DefinedValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getListLevel1Style().getDefaultRunProperties().setFontSize(new DmlTextPoints(11111));
        // Act
        // Assert
        msAssert.areEqual(new DmlTextPoints(11111), styles.getListLevel9Style().getDefaultRunProperties().getFontSize());
    }

    @Test
    public void runPropsNotDefined_DefaultValuesRetuned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        // Act
        // Assert
        Object expected = DmlRunPropertiesDefaults.getInstance().getProperty((int)DmlRunPropertiesIds.BASELINE);
        msAssert.areEqual(expected, styles.getListLevel9Style().getDefaultRunProperties().getBaseline());
    }

    @Test
    public void runPropsOverriden_OverridenValuesReturned()
    {
        // Arrange
        DmlTextListStyles styles = new DmlTextListStyles();
        styles.getDefaultParagraphProperties().getDefaultRunProperties().setFontSize(new DmlTextPoints(11111));
        styles.getListLevel9Style().getDefaultRunProperties().setFontSize(new DmlTextPoints(123));
        // Act
        // Assert
        msAssert.areEqual(new DmlTextPoints(123), styles.getListLevel9Style().getDefaultRunProperties().getFontSize());
    }
}
