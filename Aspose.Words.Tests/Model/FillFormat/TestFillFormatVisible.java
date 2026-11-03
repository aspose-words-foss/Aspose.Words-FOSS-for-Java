// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing Visible property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatVisible extends TestFillFormatBase
{
    /// <summary>
    /// Tests Visible of the various DML fills.
    /// </summary>
    @Test (dataProvider = "testFillVisibleDmlDataProvider")
    public void testFillVisibleDml(String testName, boolean expectedOriginalValue) throws Exception
    {
        testFillVisible(testName, ShapeMarkupLanguage.DML, expectedOriginalValue);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillVisibleDmlDataProvider")
	public static Object[][] testFillVisibleDmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill",  false},
			{"TextNoFill",  false},
			{"OneColorGradient",  true},
			{"Patterned",  true},
			{"PresetGradient",  true},
			{"PresetTextured",  true},
			{"Solid",  true},
			{"TextOneColorGradient",  true},
			{"TextPresetGradient",  true},
			{"TextSolid",  true},
			{"TextTwoColorGradient",  true},
			{"TwoColorGradient",  true},
			{"UserPicture",  true},
			{"UserTextured",  true},
		};
	}

    /// <summary>
    /// Tests Visible of the VML shape fills.
    /// </summary>
    @Test (dataProvider = "testShapeFillVisibleVmlDataProvider")
    public void testShapeFillVisibleVml(String testName, boolean expectedOriginalValue) throws Exception
    {
        testFillVisible(testName, ShapeMarkupLanguage.VML, expectedOriginalValue);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testShapeFillVisibleVmlDataProvider")
	public static Object[][] testShapeFillVisibleVmlDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill",  false},
			{"OneColorGradient",  true},
			{"Patterned",  true},
			{"PresetGradient",  true},
			{"PresetTextured",  true},
			{"Solid",  true},
			{"TwoColorGradient",  true},
			{"UserPicture",  true},
			{"UserTextured",  true},
		};
	}

    /// <summary>
    /// Tests Visible of the first fillable object in a document at a specified path.
    /// </summary>
    private static void testFillVisible(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        boolean expectedOriginalValue) throws Exception
    {
        boolean desiredValue = !expectedOriginalValue;
        testFillVisible(testFileNameWithoutExtension, markupLanguage, expectedOriginalValue, desiredValue, desiredValue);
    }

    /// <summary>
    /// Tests Visible of the first fillable object in a document at a specified path.
    /// </summary>
    private static void testFillVisible(String testFileNameWithoutExtension, /*ShapeMarkupLanguage*/byte markupLanguage,
        boolean expectedOriginalValue, boolean desiredValue, boolean actualValue) throws Exception
    {
        Document doc = open(testFileNameWithoutExtension, markupLanguage);
        testFillVisible(doc, testFileNameWithoutExtension, markupLanguage,
            expectedOriginalValue, desiredValue, actualValue);
    }

    /// <summary>
    /// Tests Visible of the first fillable object in a specified document.
    /// </summary>
    private static void testFillVisible(Document doc, String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, boolean expectedOriginalValue, boolean desiredValue, boolean actualValue) throws Exception
    {
        Fill fill = getFill(doc);
        msAssert.areEqual(expectedOriginalValue, fill.getVisible());

        // Set desired Value.
        fill.setVisible(desiredValue);
        msAssert.areEqual(desiredValue, fill.getVisible());

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\{0}_Visible_{1}{2}",
            testFileNameWithoutExtension, fill.getVisible(), ext);

        // Roundtrip the document.
        doc = TestUtil.saveOpen(doc, outFileName, createSaveOptions(markupLanguage), false);

        fill = getFill(doc);
        msAssert.areEqual(actualValue, fill.getVisible());
    }
}

