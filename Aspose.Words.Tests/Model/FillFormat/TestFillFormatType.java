// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing Type property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatType extends TestFillFormatBase
{
    /// <summary>
    /// Tests Type of the various DML fills.
    /// </summary>
    @Test (dataProvider = "testFillTypeDataProvider")
    public void testFillType(String testName) throws Exception
    {
        checkFillType(testName, ShapeMarkupLanguage.DML);
        checkFillType(testName, ShapeMarkupLanguage.VML);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFillTypeDataProvider")
	public static Object[][] testFillTypeDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"NoFill"},
			{"TextNoFill"},
			{"OneColorGradient"},
			{"Patterned"},
			{"PresetGradient"},
			{"PresetTextured"},
			{"Solid"},
			{"TextOneColorGradient"},
			{"TextPresetGradient"},
			{"TextSolid"},
			{"TextTwoColorGradient"},
			{"TwoColorGradient"},
			{"UserPicture"},
			{"UserTextured"},
		};
	}

    /// <summary>
    /// Checks FillFormatType for a specified test file considering markup language.
    /// </summary>
    private static void checkFillType(String testName, /*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open(testName, markupLanguage);
        Fill fill = getFill(doc);

        /*FillType*/int expectedType = getExpectedFillType(testName, markupLanguage);
        msAssert.areEqual(expectedType, fill.getFillType(), msString.format("MarkupLanguage:{0}", markupLanguage));
    }

    /// <summary>
    /// Returns expected <see cref="FillType"/> for a specified test name and markup language.
    /// </summary>
    private static /*FillType*/int getExpectedFillType(String testFileName, /*ShapeMarkupLanguage*/byte markupLanguage)
    {
        switch (gStringSwitchMap.of(testFileName))
        {
            case /*"NoFill"*/0:
            case /*"TextNoFill"*/1:
            case /*"Solid"*/2:
            case /*"TextSolid"*/3:
                return FillType.SOLID;
            case /*"TextPresetGradient"*/4:
            case /*"TextTwoColorGradient"*/5:
            case /*"TextOneColorGradient"*/6:
                return (markupLanguage == ShapeMarkupLanguage.DML) ? FillType.GRADIENT : FillType.SOLID;
            case /*"OneColorGradient"*/7:
            case /*"PresetGradient"*/8:
            case /*"TwoColorGradient"*/9:
                return FillType.GRADIENT;
            case /*"Patterned"*/10:
                return FillType.PATTERNED;
            case /*"PresetTextured"*/11:
            case /*"UserTextured"*/12:
                return FillType.TEXTURED;
            case /*"UserPicture"*/13:
                return FillType.PICTURE;
            default:
                throw new IllegalStateException(msString.format(
                    "Cannot determine expected FillType for '{0}' in '{1}'", testFileName, markupLanguage));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"NoFill",
		"TextNoFill",
		"Solid",
		"TextSolid",
		"TextPresetGradient",
		"TextTwoColorGradient",
		"TextOneColorGradient",
		"OneColorGradient",
		"PresetGradient",
		"TwoColorGradient",
		"Patterned",
		"PresetTextured",
		"UserTextured",
		"UserPicture"
	);

}

