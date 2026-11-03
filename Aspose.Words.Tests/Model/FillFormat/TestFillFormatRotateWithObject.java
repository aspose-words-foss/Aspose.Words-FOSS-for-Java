// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// The class for testing RotateWithObject property of the FillFormat object.
/// </summary>
@Test
public class TestFillFormatRotateWithObject extends TestFillFormatBase
{
    /// <summary>
    /// Tests RotateWithObject getter.
    /// </summary>
    /// <remarks>This is the test for fills which getter does not throw in both: VML and DML.</remarks>
    @Test (dataProvider = "testRotateGetterDataProvider")
    public void testRotateGetter(String testName, boolean expectedValue) throws Exception
    {
        // DML
        Document doc = open(testName, ShapeMarkupLanguage.DML);
        Fill fill = getFill(doc);
        msAssert.areEqual(expectedValue, fill.getRotateWithObject());

        // VML
        doc = open(testName, ShapeMarkupLanguage.VML);
        fill = getFill(doc);
        msAssert.areEqual(expectedValue, fill.getRotateWithObject());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRotateGetterDataProvider")
	public static Object[][] testRotateGetterDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid",  false},
			{"PresetTextured",  true},
			{"PresetGradient",  true},
			{"Patterned",  false},
			{"OneColorGradient",  true},
			{"TwoColorGradient",  true},
			{"UserTextured",  true},
			{"UserPicture",  true},
			{"NoFill",  false},
		};
	}

    /// <summary>
    /// Tests RotateWithObject getter throws an exception.
    /// </summary>
    /// <remarks>
    /// This is the test for fills which getter throws in both: Vml and Dml.
    /// Note, to check exception is thrown properly, we should perform tests
    /// for Dml and Vml separately each in its own test case.
    /// </remarks>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = DmlFill.MSG_INVALID_ACTION, dataProvider = "testGetterExceptionDataProvider")
    public void testGetterException(String testName, /*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open(testName, markupLanguage);
        Fill fill = getFill(doc);

        // Getter throws.
        msAssert.isFalse(fill.getRotateWithObject());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testGetterExceptionDataProvider")
	public static Object[][] testGetterExceptionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.VML},
			{"TextOneColorGradient",  ShapeMarkupLanguage.DML},
			{"TextOneColorGradient",  ShapeMarkupLanguage.VML},
			{"TextTwoColorGradient",  ShapeMarkupLanguage.DML},
			{"TextTwoColorGradient",  ShapeMarkupLanguage.VML},
			{"TextPresetGradient",  ShapeMarkupLanguage.DML},
			{"TextPresetGradient",  ShapeMarkupLanguage.VML},
			{"TextNoFill",  ShapeMarkupLanguage.DML},
			{"TextNoFill",  ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests RotateWithObject setter throws an exception.
    /// </summary>
    /// <remarks> The same as above, but checks setter. </remarks>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = DmlFill.MSG_INVALID_ACTION, dataProvider = "testSetterExceptionCDataProvider")
    public void testSetterExceptionC(String testName, /*ShapeMarkupLanguage*/byte markupLanguage) throws Exception
    {
        Document doc = open(testName, markupLanguage);
        Fill fill = getFill(doc);

        // Setter throws.
        fill.setRotateWithObject(false);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetterExceptionCDataProvider")
	public static Object[][] testSetterExceptionCDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextSolid",  ShapeMarkupLanguage.DML},
			{"TextSolid",  ShapeMarkupLanguage.VML},
			{"TextOneColorGradient",  ShapeMarkupLanguage.DML},
			{"TextOneColorGradient",  ShapeMarkupLanguage.VML},
			{"TextTwoColorGradient",  ShapeMarkupLanguage.VML},
			{"TextPresetGradient",  ShapeMarkupLanguage.VML},
			{"TextNoFill",  ShapeMarkupLanguage.DML},
			{"TextNoFill",  ShapeMarkupLanguage.VML},
		};
	}

    /// <summary>
    /// Tests RotateWithObject.
    /// </summary>
    /// <remarks>
    /// This is the test for fills that does not throw either in VML or DML in both: getter and setter.
    /// </remarks>
    @Test (dataProvider = "testRotateADataProvider")
    public void testRotateA(String testName) throws Exception
    {
        // DML
        testFillRotateWithObject(testName, ShapeMarkupLanguage.DML, true, false);
        // VML
        testFillRotateWithObject(testName, ShapeMarkupLanguage.VML, true, false);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRotateADataProvider")
	public static Object[][] testRotateADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"PresetTextured"},
			{"PresetGradient"},
			{"OneColorGradient"},
			{"TwoColorGradient"},
			{"UserTextured"},
			{"UserPicture"},
		};
	}

    /// <summary>
    /// Tests RotateWithObject.
    /// </summary>
    /// <remarks> This is the test for fills that does not throw only in VML in both: getter and setter. </remarks>
    @Test (dataProvider = "testRotateBDataProvider")
    public void testRotateB(String testName) throws Exception
    {
        testFillRotateWithObject(testName, ShapeMarkupLanguage.VML, false, true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testRotateBDataProvider")
	public static Object[][] testRotateBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
			{"Patterned"},
			{"NoFill"},
		};
	}

    /// <summary>
    /// Tests RotateWithObject setter throws an exception.
    /// </summary>
    /// <remarks> This is the test for the fills that throws only in setter (they are Ok in getter). </remarks>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = DmlFill.MSG_VALUE_OUT_OF_RANGE, dataProvider = "testSetterExceptionADataProvider")
    public void testSetterExceptionA(String testName) throws Exception
    {
        testFillRotateWithObject(testName, ShapeMarkupLanguage.DML, false, true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetterExceptionADataProvider")
	public static Object[][] testSetterExceptionADataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Solid"},
			{"Patterned"},
			{"NoFill"},
		};
	}

    /// <summary>
    /// Tests RotateWithObject setter throws an exception.
    /// The same as above, but another exception.
    /// </summary>
    /// <remarks> This is the test for the fills that throws only in setter (they are Ok in getter). </remarks>
    @Test (expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = DmlFill.MSG_INVALID_ACTION, dataProvider = "testSetterExceptionBDataProvider")
    public void testSetterExceptionB(String testName) throws Exception
    {
        testFillRotateWithObject(testName, ShapeMarkupLanguage.DML, false, true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSetterExceptionBDataProvider")
	public static Object[][] testSetterExceptionBDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TextTwoColorGradient"},
			{"TextPresetGradient"},
		};
	}

    /// <summary>
    /// Tests RotateWithObject of the first fillable object in a document at a specified path.
    /// </summary>
    private static void testFillRotateWithObject(String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, boolean originalValue, boolean desiredValue) throws Exception
    {
        testFillRotateWithObject(testFileNameWithoutExtension,
            markupLanguage, originalValue, desiredValue, desiredValue);
    }

    /// <summary>
    /// Tests RotateWithObject of the first fillable object in a document at a specified path.
    /// </summary>
    private static void testFillRotateWithObject(String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, boolean originalValue, boolean desiredValue, boolean actualValue) throws Exception
    {
        Document doc = open(testFileNameWithoutExtension, markupLanguage);
        testFillRotateWithObject(doc, testFileNameWithoutExtension,
            markupLanguage, originalValue, desiredValue, actualValue);
    }

    /// <summary>
    /// Tests RotateWithObject of the first fillable object in a specified document.
    /// </summary>
    private static void testFillRotateWithObject(Document doc, String testFileNameWithoutExtension,
        /*ShapeMarkupLanguage*/byte markupLanguage, boolean originalValue, boolean desiredValue, boolean actualValue) throws Exception
    {
        Fill fillFormat = getFill(doc);
        msAssert.areEqual(originalValue, fillFormat.getRotateWithObject());

        // Set desired value.
        fillFormat.setRotateWithObject(desiredValue);
        msAssert.areEqual(actualValue, fillFormat.getRotateWithObject());

        String ext = markupLanguageToExtension(markupLanguage);
        String outFileName = msString.format("FillFormat\\{0}_RotateWithObject_{1}{2}",
            testFileNameWithoutExtension, desiredValue, ext);

        // Roundtrip the document.
        doc = TestUtil.saveOpen(doc, outFileName, createSaveOptions(markupLanguage), false);

        fillFormat = getFill(doc);
        msAssert.areEqual(actualValue, fillFormat.getRotateWithObject());
    }
}

