// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.awt.Color;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.msString;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing FillFormat properties using DocumentBuilder.
/// </summary>
@Test
public class TestFillFormatBuilder extends TestFillFormatBase
{
       /// <summary>
    /// Tests FillFormat properties of the Font.
    /// </summary>
    @Test (dataProvider = "testFontDataProvider")
    public void testFont(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Color foreColor = msColor.getChocolate();
        Color backColor = msColor.getBlueViolet();
        final double TRANSPARENCY = 0.43;

        DocumentBuilder builder = createDocument(markupLanguage);

        Fill fill = builder.getFont().getFill();
        fill.setForeColor(foreColor);
        fill.setBackColor(backColor);
        fill.setTransparency(TRANSPARENCY);

        builder.writeln("Text");

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\TextBuilder{0}{1}", markupLanguage, ext);

        // Roundtrip the document.
        Document doc = TestUtil.saveOpen(builder.getDocument(), outFileName, createSaveOptions(markupLanguage), false);

        fill = getFill(doc);

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(true, fill.getVisible());
        msAssert.areEqual(msColor.Empty, fill.getBackColor());

        Color expectedForeColor = (markupLanguage == ShapeMarkupLanguage.DML) ? foreColor : msColor.Empty;
        checkColor(expectedForeColor, fill.getForeColor());

        double expectedTransparency = (markupLanguage == ShapeMarkupLanguage.DML) ? TRANSPARENCY : 0.0;
        msAssert.areEqual(expectedTransparency, 0.005, fill.getTransparency());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontDataProvider")
	public static Object[][] testFontDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML},
			{ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests default FillFormat properties of the Font.
    /// </summary>
    @Test (dataProvider = "testFontDefaultDataProvider")
    public void testFontDefault(/*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        DocumentBuilder builder = createDocument(markupLanguage);

        builder.writeln("Text");

        Fill fill = getFill(builder.getDocument());

        msAssert.areEqual(FillType.SOLID, fill.getFillType());
        msAssert.areEqual(msColor.Empty, fill.getForeColor());
        msAssert.areEqual(msColor.Empty, fill.getBackColor());
        msAssert.areEqual(0.0, 0.001, fill.getTransparency());
        msAssert.areEqual(true, fill.getVisible());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFontDefaultDataProvider")
	public static Object[][] testFontDefaultDataProvider() throws Exception
	{
		return new Object[][]
		{
			{ShapeMarkupLanguage.DML},
			{ShapeMarkupLanguage.VML},
		};
	}
}

