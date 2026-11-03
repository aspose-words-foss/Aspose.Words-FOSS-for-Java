// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/10/2013 by Evgeny Ivanov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests for <see cref="ComparisonEvaluator"/>
/// </summary>
@Test
public class ComparisonEvaluatorTest
{
    /// <summary>
    /// String with string should be compared correctly
    /// </summary>
    @Test
    public void compareStringWithStringTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        FieldIf field = (FieldIf)builder.insertField(FieldType.FIELD_IF, false);
        field.setFieldCode("IF \"TEST\" = \"TEST\"");
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(true, result.getValueBoolean());
    }

    /// <summary>
    /// Bookmark with string should be compared as bookmark string result and string
    /// </summary>
    @Test
    public void compareBookmarkWithStringTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        builder.startBookmark("TEST_BM");
        builder.write("TEST");
        builder.endBookmark("TEST_BM");

        FieldIf field = (FieldIf)builder.insertField(FieldType.FIELD_IF, false);
        field.setFieldCode("IF TEST_BM = \"TEST\"");
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(true, result.getValueBoolean());
    }

    /// <summary>
    /// String with Bookmark should be compared as bookmark string result and string
    /// </summary>
    @Test
    public void compareStringWithBookmarkTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        builder.startBookmark("TEST_BM");
        builder.write("TEST");
        builder.endBookmark("TEST_BM");
        FieldIf field = (FieldIf)builder.insertField(FieldType.FIELD_IF, false);
        field.setFieldCode("IF \"TEST\" = TEST_BM ");
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(true, result.getValueBoolean());
    }

    /// <summary>
    /// String in quotes with binary expressions should not be compared as doubles
    /// </summary>
    @Test
    public void compareStringWithBinaryExpressionTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        FieldIf field = (FieldIf)builder.insertField(FieldType.FIELD_IF, false);
        field.setFieldCode("IF \"4\" = 2+2");
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(false, result.getValueBoolean());
    }

    /// <summary>
    /// Double with binary expressions should be compared as doubles
    /// </summary>
    @Test
    public void compareDoubleWithBinaryExpressionTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        FieldIf field = (FieldIf)builder.insertField(FieldType.FIELD_IF, false);
        field.setFieldCode("IF 4 = 2+2");
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(true, result.getValueBoolean());
    }

    /// <summary>
    /// WORDSNET-11795 IF fields in bookmark text not resolving properly.
    /// </summary>
    @Test (dataProvider = "testJira11795DataProvider")
    public void testJira11795(String expression, boolean expectedResult) throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();

        builder.startBookmark("BBB");
        builder.startBookmark("DDD");
        builder.write("bookmark text");
        builder.endBookmark("BBB");
        builder.endBookmark("DDD");

        builder.startBookmark("EEE");
        builder.write("this--is+(not/+^expression(");
        builder.endBookmark("EEE");

        builder.startBookmark("FFF");
        builder.write("5");
        builder.endBookmark("FFF");

        builder.startBookmark("GGG");
        builder.write("1 2 3");
        builder.endBookmark("GGG");

        builder.writeln();

        Field field = builder.insertField(msString.format("IF {0} ", expression), null);
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        // Act
        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        // Assert
        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(expectedResult, result.getValueBoolean());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira11795DataProvider")
	public static Object[][] testJira11795DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"\"AAA\" = \"AAA\"",  true},
			{"AAA = \"AAA\"",  true},
			{"\"AAA\" = AAA",  true},
			{"AAA = AAA",  true},
			{"\"BBB\" = \"BBB\"",  true},
			{"BBB = \"BBB\"",  false},
			{"\"BBB\" = BBB",  false},
			{"BBB = BBB",  true},
			{"\"bookmark text\" = \"BBB\"",  false},
			{"\"bookmark text\" = BBB",  true},
			{"\"BBB\" = \"bookmark text\"",  false},
			{"BBB = \"bookmark text\"",  true},
			{"\"AAA\" = \"BBB\"",  false},
			{"AAA = \"BBB\"",  false},
			{"\"AAA\" = BBB",  false},
			{"AAA = BBB",  false},
			{"\"BBB\" = \"AAA\"",  false},
			{"BBB = \"AAA\"",  false},
			{"\"BBB\" = AAA",  false},
			{"BBB = AAA",  false},
			{"\"AAA\" = \"CCC\"",  false},
			{"AAA = \"CCC\"",  false},
			{"\"AAA\" = CCC",  false},
			{"AAA = CCC",  false},
			{"\"CCC\" = \"AAA\"",  false},
			{"CCC = \"AAA\"",  false},
			{"\"CCC\" = AAA",  false},
			{"CCC = AAA",  false},
			{"\"BBB\" = \"DDD\"",  false},
			{"BBB = \"DDD\"",  false},
			{"\"BBB\" = DDD",  false},
			{"BBB = DDD",  true},
			{"\"4\" = 2*2",  false},
			{"4 = \"2*2\"",  true},
			{"\"4\" = \"2*2\"",  false},
			{"4 = 2*2",  true},
			{"\"2*2\" = 4",  false},
			{"2*2 = \"4\"",  true},
			{"\"2*2\" = \"4\"",  false},
			{"2*2 = 4",  true},
			{"EEE = \"this--is+(not/+^expression(\"",  true},
			{"\"EEE\" = \"this--is+(not/+^expression(\"",  false},
			{"\"this--is+(not/+^expression(\" = EEE",  true},
			{"\"this--is+(not/+^expression(\" = \"EEE\"",  false},
			{"BBB+3 = 3+BBB",  false},
			{"BBB+3 = BBB+3",  true},
			{"AAA+3 = 3+AAA",  false},
			{"AAA+3 = AAA+3",  true},
			{"\"BBB+3\" = 3+BBB",  false},
			{"\"BBB+3\" = BBB+3",  true},
			{"\"AAA+3\" = 3+AAA",  false},
			{"\"AAA+3\" = AAA+3",  true},
			{"BBB+3 = \"3+BBB\"",  false},
			{"BBB+3 = \"BBB+3\"",  true},
			{"AAA+3 = \"3+AAA\"",  false},
			{"AAA+3 = \"AAA+3\"",  true},
			{"FFF = 2+3",  true},
			{"\"1+2+3\" = GGG",  false},
			{"FFF = \"2+3\"",  false},
			{"\"1 2 3\" = GGG",  true},
			{"GGG = \"1 2 3\"",  true},
			{"\"6\" = GGG",  false},
			{"GGG = \"6\"",  false},
			{"GGG = \"1+2+3\"",  false},
			{"GGG = 1+2+3",  true},
		};
	}

    /// <summary>
    /// WORDSNET-6498 BookmarkReference.Evaluate method potential bugs.
    /// </summary>
    @Test (dataProvider = "testJira6498DataProvider")
    public void testJira6498(String expression, boolean expectedResult) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.startBookmark("test");
        builder.write("1 2 3");
        builder.endBookmark("test");

        Field field = builder.insertField(msString.format("IF {0} ", expression), null);
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(expectedResult, result.getValueBoolean());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira6498DataProvider")
	public static Object[][] testJira6498DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"6 = 6",  true},
			{"6 = \"6\"",  true},
			{"6 = test",  true},
			{"6 = \"test\"",  false},
			{"6 = \"1 2 3\"",  true},
			{"6 = 1+2+3",  true},
			{"6 = \"1+2+3\"",  true},
			{"6 = 2+4",  true},
			{"6 = \"2+4\"",  true},
			{"\"6\" = 6",  true},
			{"\"6\" = \"6\"",  true},
			{"\"6\" = test",  false},
			{"\"6\" = \"test\"",  false},
			{"\"6\" = \"1 2 3\"",  false},
			{"\"6\" = 1+2+3",  false},
			{"\"6\" = \"1+2+3\"",  false},
			{"\"6\" = 2+4",  false},
			{"\"6\" = \"2+4\"",  false},
			{"test = 6",  true},
			{"test = \"6\"",  false},
			{"test = test",  true},
			{"test = \"test\"",  false},
			{"test = \"1 2 3\"",  true},
			{"test = 1+2+3",  true},
			{"test = \"1+2+3\"",  false},
			{"test = 2+4",  true},
			{"test = \"2+4\"",  false},
			{"\"test\" = 6",  false},
			{"\"test\" = \"6\"",  false},
			{"\"test\" = test",  false},
			{"\"test\" = \"test\"",  true},
			{"\"test\" = \"1 2 3\"",  false},
			{"\"test\" = 1+2+3",  false},
			{"\"test\" = \"1+2+3\"",  false},
			{"\"test\" = 2+4",  false},
			{"\"test\" = \"2+4\"",  false},
			{"\"1 2 3\" = 6",  false},
			{"\"1 2 3\" = \"6\"",  false},
			{"\"1 2 3\" = test",  true},
			{"\"1 2 3\" = \"test\"",  false},
			{"\"1 2 3\" = \"1 2 3\"",  true},
			{"\"1 2 3\" = 1+2+3",  false},
			{"\"1 2 3\" = \"1+2+3\"",  false},
			{"\"1 2 3\" = 2+4",  false},
			{"\"1 2 3\" = \"2+4\"",  false},
			{"1+2+3 = 6",  true},
			{"1+2+3 = \"6\"",  true},
			{"1+2+3 = test",  true},
			{"1+2+3 = \"test\"",  false},
			{"1+2+3 = \"1 2 3\"",  true},
			{"1+2+3 = 1+2+3",  true},
			{"1+2+3 = \"1+2+3\"",  true},
			{"1+2+3 = 2+4",  true},
			{"1+2+3 = \"2+4\"",  true},
			{"\"1+2+3\" = 6",  false},
			{"\"1+2+3\" = \"6\"",  false},
			{"\"1+2+3\" = test",  false},
			{"\"1+2+3\" = \"test\"",  false},
			{"\"1+2+3\" = \"1 2 3\"",  false},
			{"\"1+2+3\" = 1+2+3",  true},
			{"\"1+2+3\" = \"1+2+3\"",  true},
			{"\"1+2+3\" = 2+4",  false},
			{"\"1+2+3\" = \"2+4\"",  false},
			{"2+4 = 6",  true},
			{"2+4 = \"6\"",  true},
			{"2+4 = test",  true},
			{"2+4 = \"test\"",  false},
			{"2+4 = \"1 2 3\"",  true},
			{"2+4 = 1+2+3",  true},
			{"2+4 = \"1+2+3\"",  true},
			{"2+4 = 2+4",  true},
			{"2+4 = \"2+4\"",  true},
			{"\"2+4\" = 6",  false},
			{"\"2+4\" = \"6\"",  false},
			{"\"2+4\" = test",  false},
			{"\"2+4\" = \"test\"",  false},
			{"\"2+4\" = \"1 2 3\"",  false},
			{"\"2+4\" = 1+2+3",  false},
			{"\"2+4\" = \"1+2+3\"",  false},
			{"\"2+4\" = 2+4",  true},
			{"\"2+4\" = \"2+4\"",  true},
			{"true = \"true\"",  true},
			{"false = \"false\"",  true},
		};
	}

    /// <summary>
    /// WORDSNET-23980 IF field with wildcard is updated improperly.
    /// </summary>
    @Test (dataProvider = "test23980DataProvider")
    public void test23980(String left, String right, boolean expectedResult) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(msString.format("IF {0} = {1}", left, right), null);
        FieldCodeComparison comparison = new FieldCodeComparison(field.getFieldCodeCache());

        Constant result = ComparisonEvaluator.evaluate(field, comparison);

        msAssert.isInstanceOf(BooleanConstant.class, result);
        msAssert.areEqual(expectedResult, result.getValueBoolean());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test23980DataProvider")
	public static Object[][] test23980DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"1c",  "\"1c\"",  true},
			{"\"1c\"",  "\"1c\"",  true},
			{"\"1c\"",  "1c",  true},
			{"1c",  "1c",  true},
			{"2",  "\"2*\"",  true},
			{"\"2\"",  "\"2*\"",  true},
			{"\"2\"",  "2*",  false},
			{"2",  "2*",  false},
			{"a",  "\"a*\"",  true},
			{"\"a\"",  "\"a*\"",  true},
			{"\"a\"",  "a*",  true},
			{"a",  "a*",  true},
			{"1c",  "\"1c*\"",  true},
			{"\"1c\"",  "\"1c*\"",  true},
			{"\"1c\"",  "1c*",  false},
			{"1c",  "1c*",  false},
			{"\"12foo5\"",  "\"4+8\"",  false},
			{"\"12foo5\"",  "4+8",  false},
			{"\"12foo5\"",  "\"4bar+8\"",  false},
			{"\"12foo5\"",  "4bar+8",  false},
			{"12foo5",  "\"4+bar8\"",  false},
			{"\"12foo5\"",  "\"4+bar8\"",  false},
			{"\"12foo5\"",  "4+bar8",  false},
			{"12foo5",  "4+bar8",  false},
			{"\"2foo5\"",  "\"2*5\"",  true},
			{"\"2foo5\"",  "2*5",  false},
			{"2foo5",  "2*5",  false},
			{"10blah",  "\"2*5\"",  true},
			{"\"10blah\"",  "\"2*5\"",  false},
			{"\"10blah\"",  "2*5",  false},
			{"10blah",  "2*5",  true},
			{"2foo*3",  "\"2foo*3\"",  true},
			{"2foo*3",  "\"2foo*\"",  true},
			{"2foo*3",  "\"2foo3\"",  false},
			{"2foo*3",  "\"2foo\"",  false},
			{"2foo*",  "\"2foo*3\"",  true},
			{"2foo*",  "\"2foo*\"",  true},
			{"2foo*",  "\"2foo3\"",  false},
			{"2foo*",  "\"2foo\"",  false},
			{"2foo3",  "\"2foo*\"",  true},
			{"2foo3",  "\"2foo3\"",  true},
			{"2foo",  "\"2foo*3\"",  false},
			{"2foo",  "\"2foo*\"",  true},
			{"2foo",  "\"2foo\"",  true},
			{"3foo+3",  "\"6\"",  true},
			{"2foo*4",  "\"8\"",  true},
			{"3foo+3",  "\"2foo*3\"",  true},
			{"2foo*4",  "\"2foo*\"",  true},
			//Ignored TestCase: {"12foo5",  "\"4+8\"",  true, "Not supported"},
			//Ignored TestCase: {"12foo5",  "\"4bar+8\"",  true, "Not supported"},
			//Ignored TestCase: {"12foo5",  "4+8",  true, "Not supported"},
			//Ignored TestCase: {"12foo5",  "4bar+8",  true, "Not supported"},
			//Ignored TestCase: {"2foo5",  "\"2*5\"",  false, "Not supported"},
			//Ignored TestCase: {"2foo3",  "\"2foo*3\"",  false, "Not supported"},
			//Ignored TestCase: {"2foo3",  "\"2foo\"",  true, "Not supported"},
			//Ignored TestCase: {"2foo",  "\"2foo3\"",  true, "Not supported"},
			//Ignored TestCase: {"2foo13",  "\"2foo*3\"",  false, "Not supported"},
		};
	}
}

