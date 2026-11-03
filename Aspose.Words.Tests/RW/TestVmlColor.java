// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.DrColor;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Drawing.msColor;
import java.awt.Color;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests related to conversion of Color values to/from VML string representation.
/// </summary>
@Test
public class TestVmlColor
{
    /// <summary>
    /// WORDSNET-11964 Aspose.Words.FileCorruptedException is thrown while loading Docx
    /// that has #0 as shape strokecolor value.
    /// </summary>
    @Test (dataProvider = "testOneDigitColorDataProvider")
    public void testOneDigitColor(String vmlColor, int expectedR, int expectedG, int expectedB)
    {
        DrColor color = VmlColor.vmlToColor(vmlColor);
        msAssert.areEqual(expectedR, color.getR(), "R");
        msAssert.areEqual(expectedG, color.getG(), "G");
        msAssert.areEqual(expectedB, color.getB(), "B");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testOneDigitColorDataProvider")
	public static Object[][] testOneDigitColorDataProvider()
	{
		return new Object[][]
		{
			{"#0",  0,  0,  0},
			{"#f",  0,  0,  0xFF},
		};
	}

    /// <summary>
    /// WORDSNET-15983 The orange color was written as 'orange' string that is not present as
    /// predefined color constant in VML documentation at section 6.1.3.1 ST_ColorType (Color Type).
    /// </summary>
    @Test
    public void testOrangeColor()
    {
        msAssert.areEqual("#ffa500", VmlColor.colorToVml(new DrColor(msColor.getOrange().getRGB())));
    }

    /// <summary>
    /// Tests parsing the base color and the modifier from VML extended color string.
    /// </summary>
    @Test (dataProvider = "testBaseColorAndModifierDataProvider")
    public void testBaseColorAndModifier(String vmlColor, int expR, int expG, int expB, int expModifier)
    {
        DrColor expectedColor = (expR == 0 && expG == 0 && expB == 0)
            ? null
            : DrColor.fromArgb(expR, expG, expB);

        msAssert.areEqual(expectedColor, VmlColor.getBaseColor(vmlColor));
        msAssert.areEqual(expModifier, VmlColor.getColorModifier(vmlColor));
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testBaseColorAndModifierDataProvider")
	public static Object[][] testBaseColorAndModifierDataProvider()
	{
		return new Object[][]
		{
			{"#d5b9c7 [rgb(149,79,114) lighten(102)]",  149,  79,  114,  102},
			{"#954f72 [rgb(0,1,114) lighten(102)]",  0,  1,  114,  102},
			{"#3b1f2d [rgb(255,255,255) darken(1)]",  255,  255,  255,  -1},
			{"#683750 [rgb(52,5,1) darken(12)]",  52,  5,  1,  -12},
			{"black [rgb(149,79,114) darken(10)]",  149,  79,  114,  -10},
			{"#9bbb59 [3042]",  0,  0,  0,  0},
		};
	}
}

