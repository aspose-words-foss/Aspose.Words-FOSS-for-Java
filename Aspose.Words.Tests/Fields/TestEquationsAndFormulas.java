// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.SystemPal;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests how the equations and formulas fields work.
/// </summary>
@Test
public class TestEquationsAndFormulas extends TestFieldsBase
{
    /// <summary>
    /// Test how the = (formula) fields are updated. Also tests the numeric formatting.
    /// </summary>
    @Test
    public void testFormula() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestFormula.docx");
        doc = TestUtil.saveOpen(doc, "Fields\\EquationsAndFormulas\\TestFormula Modified.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("56", fields.get(0).getResult());
        msAssert.areEqual("2.654", fields.get(1).getResult());
        msAssert.areEqual("0.004", fields.get(2).getResult());
        msAssert.areEqual("4", fields.get(3).getResult());
        msAssert.areEqual("249.4", fields.get(4).getResult());
        msAssert.areEqual("(225.55)", fields.get(5).getResult());
        msAssert.areEqual("776,458", fields.get(6).getResult());
        msAssert.areEqual("  38.00", fields.get(7).getResult());
        msAssert.areEqual("$   0.00", fields.get(8).getResult());
        msAssert.areEqual("13", fields.get(9).getResult());
        msAssert.areEqual("6%", fields.get(10).getResult());
        msAssert.areEqual("555.00", fields.get(11).getResult());
        msAssert.areEqual("-1.00%", fields.get(12).getResult());
        msAssert.areEqual("6.89", fields.get(13).getResult());
    }

    /// <summary>
    /// Test how the = (formula) fields containing functions are updated. Also tests the numeric formatting.
    /// </summary>
    @Test
    public void testFunctions() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestFunctions.docx");
        doc = TestUtil.saveOpen(doc, "Fields\\EquationsAndFormulas\\TestFunctions Modified.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // ABS

        msAssert.areEqual("15", fields.get(0).getResult());
        msAssert.areEqual("1.829", fields.get(1).getResult());

        // AND

        msAssert.areEqual("0", fields.get(2).getResult());
        msAssert.areEqual("0", fields.get(3).getResult());
        msAssert.areEqual("1", fields.get(4).getResult());

        // AVERAGE

        msAssert.areEqual("1", fields.get(5).getResult());
        msAssert.areEqual("21.129", fields.get(6).getResult());

        // COUNT

        msAssert.areEqual("4", fields.get(7).getResult());

        // DEFINED

        msAssert.areEqual("1", fields.get(8).getResult());
        msAssert.areEqual("0", fields.get(9).getResult());

        // FALSE

        msAssert.areEqual("0", fields.get(10).getResult());

        // IF

        msAssert.areEqual("6", fields.get(11).getResult());
        msAssert.areEqual("32", fields.get(12).getResult());

        // INT

        msAssert.areEqual("-64", fields.get(13).getResult());

        // MAX

        msAssert.areEqual("6", fields.get(14).getResult());

        // MIN

        msAssert.areEqual("-0.534", fields.get(15).getResult());

        // MOD

        msAssert.areEqual("1", fields.get(16).getResult());
        msAssert.areEqual("-0.6547", fields.get(17).getResult());

        // NOT

        msAssert.areEqual("0", fields.get(18).getResult());
        msAssert.areEqual("1", fields.get(19).getResult());

        // OR

        msAssert.areEqual("0", fields.get(20).getResult());
        msAssert.areEqual("1", fields.get(21).getResult());
        msAssert.areEqual("1", fields.get(22).getResult());

        // PRODUCT

        msAssert.areEqual("102,237,900.8", fields.get(23).getResult());

        // ROUND

        msAssert.areEqual("4", fields.get(24).getResult());
        msAssert.areEqual("0", fields.get(25).getResult());

        // SIGN

        msAssert.areEqual("-1", fields.get(26).getResult());
        msAssert.areEqual("0", fields.get(27).getResult());
        msAssert.areEqual("1", fields.get(28).getResult());

        // SUM

        msAssert.areEqual("-5254.5", fields.get(29).getResult());

        // TRUE

        msAssert.areEqual("1", fields.get(30).getResult());
    }

    /// <summary>
    /// WORDSNET-13542 Test how bookmark references work.
    /// </summary>
    @Test
    public void testBookmarkReference() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestBookmarkReference.docx");
        doc = TestUtil.saveOpen(doc, "Fields\\EquationsAndFormulas\\TestBookmarkReference Modified.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("4", fields.get(0).getResult());
        msAssert.areEqual("28", fields.get(1).getResult());
        msAssert.areEqual("14", fields.get(2).getResult());
        msAssert.areEqual("0", fields.get(3).getResult());
        msAssert.areEqual("48.7", fields.get(4).getResult());
    }

    /// <summary>
    /// Test how cell references work.
    /// </summary>
    @Test
    public void testCellReference() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestCellReference.docx");
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestCellReference Modified.docx");
    }



    /// <summary>
    /// WORDSNET-11066 Support cell reference, like ABOVE upon updating fields.
    /// </summary>
    @Test
    public void testDefect11066() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestDefect11066.docx");
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestDefect11066 Modified.docx");
    }


    /// <summary>
    /// WORDSNET-711 Allow format strings in any culture.
    /// Specific culture's separators are now replaced with invariant culture's '.' and ',' as .NET requires.
    /// </summary>
    @Test
    public void testDefect711() throws Exception
    {
        SystemPal.setCulture("ru-RU");

        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestDefect711.docx");
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestDefect711 Modified.docx");
    }

    /// <summary>
    /// WORDSNET-16189 SUM(ABOVE) is calculated improperly if there is merged cells.
    /// The value was taken from "previous" merged cells. Now I have implemented searching for the first merged cell.
    /// </summary>
    @Test
    public void testDefect16189() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\EquationsAndFormulas\\TestDefect16189.docx");
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestDefect16189 Modified.docx");
    }

    private static String relativePath(String fileName)
    {
        return "Fields\\EquationsAndFormulas\\" + fileName;
    }

    private static Document openTestDocument(String fileName) throws Exception
    {
        return TestUtil.open(relativePath(fileName));
    }

    /// <summary>
    /// WORDSNET-24122 SUM(ABOVE) is calculated improperly if there are merged(simply wide) cells.
    /// Cell width should be taken into account.
    /// </summary>
    @Test (dataProvider = "testDefect24122DataProvider")
    public void testDefect24122(String file) throws Exception
    {
        testUpdatedFields(file, "33", "34", "35");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testDefect24122DataProvider")
	public static Object[][] testDefect24122DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestDefect24122.docx"},
			{"TestDefect24122Flipped.docx"},
		};
	}

    /// <summary>
    /// Tests SUM(ABOVE)/BELOW for a jagged table.
    /// </summary>
    @Test (dataProvider = "testJaggedTableWithSumsDataProvider")
    public void testJaggedTableWithSums(String file) throws Exception
    {
        testUpdatedFields(file, "67", "56", "33", "34", "0");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJaggedTableWithSumsDataProvider")
	public static Object[][] testJaggedTableWithSumsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestJaggedTableWithSums.docx"},
			{"TestJaggedTableWithSumsFlipped.docx"},
		};
	}

    /// <summary>
    /// WORDSNET-25839 NullReferenceException when updating a field with SUM(BELOW).
    /// Table boundaries shall be checked when skipping merged cells.
    /// </summary>
    @Test
    public void testDefect25839MergedCellAtLastRow() throws Exception
    {
        testUpdatedFields("TestDefect25839MergedCellAtLastRow.docx", "21");
    }

    /// <summary>
    /// WORDSNET-25839  NullReferenceException when updating a field with SUM(BELOW).
    /// Table boundaries shall be checked when skipping merged cells.
    /// This test checks a similar problem with SUM(RIGHT).
    /// </summary>
    @Test
    public void testDefect25839MergedCellAtLastColumn() throws Exception
    {
        testUpdatedFields("TestDefect25839MergedCellAtLastColumn.docx", "12");
    }

    /// <summary>
    /// WORDSNET-25839 NullReferenceException when updating a field with SUM(BELOW).
    /// Checks that merged cells are skipped.
    /// </summary>
    @Test
    public void testDefect25839IgnoreMergedCells() throws Exception
    {
        testUpdatedFields("TestDefect25839IgnoreMergedCells.docx", "26", "24", "31", "14", "15");
    }

    private static void testUpdatedFields(String testFileName, /*params*/ String... expectedValues) throws Exception
    {
        // Do the test.
        Document doc = openTestDocument(testFileName);
        doc.updateFields();

        // Check the calculated field values.
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        String[] actualValues = new String[Math.min(fields.size(), expectedValues.length)];
        for (int i = 0; i < actualValues.length; i++)
            actualValues[i] = fields.get(i).getResult();

        msAssert.areEqual(expectedValues, actualValues);
    }








    /// <summary>
    /// WORDSNET-5563 SET field inside IF field is not updated properly.
    /// </summary>
    @Test
    public void testJira5563() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira5563.docx");
        doc.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // Check bookmark values.
        msAssert.areEqual("equal", fields.get(4).getResult(), fields.get(4).getFieldCode());
        msAssert.areEqual("equal", fields.get(8).getResult(), fields.get(8).getFieldCode());
        msAssert.areEqual("equal", fields.get(12).getResult(), fields.get(12).getFieldCode());
        msAssert.areEqual("equal-1", fields.get(23).getResult(), fields.get(23).getFieldCode());

        // Check IF field results except last one.
        // They must be empty, as SET field result must not be included to IF field result.
        int index = 0;
        int lastIndex = fields.size() - 1;
        for (Field field : fields)
        {
            if ((index++ != lastIndex) && (field.getType() == FieldType.FIELD_IF))
                msAssert.areEqual("", field.getResult(), field.getFieldCode());
        }

        // Check last IF field result.
        msAssert.areEqual("Clause [-5--]", fields.get(lastIndex).getResult(), fields.get(lastIndex).getFieldCode());

        // Perform GOLD check just to ensure that simple SET field result contains nothing but bookmark.
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestJira5563 Modified.docx");
    }

    /// <summary>
    /// WORDSNET-5671 a + b formula is calculated incorrectly in case b is negative.
    /// </summary>
    @Test
    public void testJira5671() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira5671.docx");
        doc.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        String[] fieldResults = { "10", "10", "20", "20", "250", "-10", "100", "-1", "80" };
        int fieldIndex = 2;
        for (String fieldResult : fieldResults)
        {
            msAssert.areEqual(fieldResult, fields.get(fieldIndex).getResult(), fields.get(fieldIndex).getFieldCode());
            fieldIndex++;
        }
    }


    /// <summary>
    /// WORDSNET-9201 Mimic MS Word for rounding decimal numbers to correct precision
    /// </summary>
    @Test
    public void testDefect9201() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestDefect9201.docx");
        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestDefect9201.docx");
    }

    /// <summary>
    /// WORDSNET-10008 System.InvalidOperationException is thrown during calculation INT function with incorrect parameters.
    /// </summary>
    @Test
    public void testDefect10008() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.insertField("= INT (trash)", "");

        doc.updateFields();
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        msAssert.areEqual("!Undefined Bookmark, TRASH", fields.get(0).getResult());
    }

    /// <summary>
    /// WORDSNET-10309 multiple numbers in cell are not calculated by SUM(ABOVE)
    /// </summary>
    @Test
    public void testDefect10309() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestDefect10309.docx");
        doc.updateFields();
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("6", fields.get(0).getResult());
        msAssert.areEqual("10", fields.get(1).getResult());
        msAssert.areEqual("19", fields.get(2).getResult());
    }

    /// <summary>
    /// WORDSNET-10899 Document.UpdateFields does not update the formula field
    /// </summary>
    @Test
    public void testDefect10899() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestDefect10899.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestDefect10899.docx");
    }


    /// <summary>
    /// WORDSNET-10943 Document.UpdateFields does not update the formula field
    /// </summary>
    @Test (dataProvider = "testJira10943DataProvider")
    public void testJira10943(String code, String expectedFormulaFieldResult) throws Exception
    {
        DocumentBuilder db = new DocumentBuilder();
        db.insertField("SET book " + code, null);
        Field formulaField = db.insertField("=book", null);

        db.getDocument().updateFields();

        msAssert.areEqual(expectedFormulaFieldResult, formulaField.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira10943DataProvider")
	public static Object[][] testJira10943DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"text",  "0"},
			{"text text",  "0"},
			{"\"text text\"",  "0"},
			{"=",  "0"},
			{"0",  "0"},
			{"=0",  "0"},
			{"= 0",  "0"},
			{"1",  "1"},
			{"=1",  "1"},
			{"= 1",  "0"},
			{"=2=2",  "4"},
			{"=2",  "2"},
			{"2=2",  "4"},
			{"2=",  "2"},
			{"2+2*2",  "6"},
		};
	}


    /// <summary>
    /// Test COMPARE field logic with expressions contains "*".
    /// </summary>
    @Test
    public void testCompareWildcards() throws Exception
    {
        Document doc = TestUtil.openUpdateFields("Fields\\EquationsAndFormulas\\TestCompareWildcards.docx");
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc, true);
        msAssert.areEqual("1", fields.get(3).getResult());
        msAssert.areEqual("1", fields.get(4).getResult());
        msAssert.areEqual("1", fields.get(5).getResult());
        msAssert.areEqual("1", fields.get(6).getResult());
        msAssert.areEqual("1", fields.get(7).getResult());
        msAssert.areEqual("1", fields.get(8).getResult());
        msAssert.areEqual("1", fields.get(9).getResult());
        msAssert.areEqual("1", fields.get(10).getResult());
        msAssert.areEqual("1", fields.get(11).getResult());
        msAssert.areEqual("1", fields.get(12).getResult());
        msAssert.areEqual("0", fields.get(13).getResult());
        msAssert.areEqual("0", fields.get(14).getResult());
        msAssert.areEqual("1", fields.get(15).getResult());
        msAssert.areEqual("1", fields.get(16).getResult());
        msAssert.areEqual("1", fields.get(17).getResult());
        msAssert.areEqual("0", fields.get(18).getResult());
        msAssert.areEqual("1", fields.get(19).getResult());
        msAssert.areEqual("0", fields.get(20).getResult());
        msAssert.areEqual("1", fields.get(21).getResult());
        msAssert.areEqual("1", fields.get(22).getResult());
        msAssert.areEqual("1", fields.get(23).getResult());
        msAssert.areEqual("1", fields.get(24).getResult());
        msAssert.areEqual("0", fields.get(25).getResult());
        msAssert.areEqual("0", fields.get(26).getResult());
        msAssert.areEqual("1", fields.get(27).getResult()); //p = p*
        msAssert.areEqual("1", fields.get(28).getResult());
        msAssert.areEqual("1", fields.get(29).getResult());
        msAssert.areEqual("0", fields.get(30).getResult());
        msAssert.areEqual("1", fields.get(31).getResult());
        msAssert.areEqual("1", fields.get(32).getResult());
        msAssert.areEqual("1", fields.get(33).getResult());
        msAssert.areEqual("0", fields.get(34).getResult());
        msAssert.areEqual("1", fields.get(35).getResult());
        msAssert.areEqual("0", fields.get(36).getResult());
        msAssert.areEqual("1", fields.get(37).getResult());
        msAssert.areEqual("0", fields.get(38).getResult());
        msAssert.areEqual("1", fields.get(39).getResult());
        msAssert.areEqual("0", fields.get(40).getResult());
        msAssert.areEqual("1", fields.get(41).getResult());
        msAssert.areEqual("0", fields.get(42).getResult());
        msAssert.areEqual("1", fields.get(43).getResult());
        msAssert.areEqual("0", fields.get(44).getResult());
        msAssert.areEqual("1", fields.get(45).getResult());
        msAssert.areEqual("0", fields.get(46).getResult());
        msAssert.areEqual("0", fields.get(47).getResult());
        msAssert.areEqual("1", fields.get(48).getResult());
        msAssert.areEqual("0", fields.get(49).getResult());
        msAssert.areEqual("0", fields.get(50).getResult());
        msAssert.areEqual("1", fields.get(51).getResult());
        msAssert.areEqual("0", fields.get(52).getResult());
        msAssert.areEqual("0", fields.get(53).getResult());
        msAssert.areEqual("1", fields.get(54).getResult());
        msAssert.areEqual("0", fields.get(55).getResult());
        msAssert.areEqual("1", fields.get(56).getResult());
        msAssert.areEqual("1", fields.get(57).getResult());
        msAssert.areEqual("1", fields.get(58).getResult());
        msAssert.areEqual("1", fields.get(59).getResult());
        msAssert.areEqual("1", fields.get(60).getResult());
        msAssert.areEqual("0", fields.get(61).getResult());
        msAssert.areEqual("0", fields.get(62).getResult());
        msAssert.areEqual("0", fields.get(63).getResult());
        msAssert.areEqual("1", fields.get(64).getResult());
        msAssert.areEqual("1", fields.get(65).getResult());
        msAssert.areEqual("1", fields.get(66).getResult());
    }


    /// <summary>
    /// WORDSNET-11470 StackOverflowException encountered during mail merge process
    /// </summary>
    @Test
    public void testJira11470() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira11470.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestJira11470.docx");
    }


    /// <summary>
    /// Test how spacing between digits processed.
    /// </summary>
    @Test
    public void testExpressionWithSpacingBetweenDigits() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestExpressionWithSpacingBetweenDigits.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestExpressionWithSpacingBetweenDigits.docx");
    }

    /// <summary>
    /// Test how current culture currency symbol is used by formula field.
    /// </summary>
    @Test (dataProvider = "testFormulaFieldWithCurrencySymbolDataProvider")
    public void testFormulaFieldWithCurrencySymbol(String culture) throws Exception
    {
        SystemPal.setCulture(culture);

        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestFormulaFieldWithCurrencySymbol.docx");
        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestFormulaFieldWithCurrencySymbol " + culture + ".docx");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFormulaFieldWithCurrencySymbolDataProvider")
	public static Object[][] testFormulaFieldWithCurrencySymbolDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"nl-NL"},
			{"en-US"},
			{"en-GB"},
		};
	}

    /// <summary>
    /// WORDSNET-11647 CARDTEXT swtich does not work for Dutch language
    /// </summary>
    @Test
    public void testJira11647() throws Exception
    {
        SystemPal.setCulture("nl-NL");

        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira11647.docx");
        doc.updateFields();
        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestJira11647.docx");
    }

    /// <summary>
    /// WORDSNET-9670 Document.UpdateFields does not update the IF field.
    /// </summary>
    @Test
    public void testJira9670() throws Exception
    {
        Document doc = TestUtil.openUpdateFields("Fields\\EquationsAndFormulas\\TestJira9670.docx");
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc, true);

        msAssert.areEqual("TRUE", fields.get(0).getResult());
        msAssert.areEqual("TRUE", fields.get(1).getResult());
        msAssert.areEqual("TRUE", fields.get(2).getResult());
        msAssert.areEqual("FALSE", fields.get(3).getResult());
        msAssert.areEqual("TRUE", fields.get(4).getResult());
        msAssert.areEqual("FALSE", fields.get(5).getResult());
        msAssert.areEqual("TRUE", fields.get(6).getResult());
        msAssert.areEqual("TRUE", fields.get(7).getResult());
        msAssert.areEqual("TRUE", fields.get(8).getResult());
        msAssert.areEqual("TRUE", fields.get(9).getResult());
        msAssert.areEqual("FALSE", fields.get(10).getResult());
        msAssert.areEqual("TRUE", fields.get(11).getResult());
        msAssert.areEqual("TRUE", fields.get(12).getResult());
        msAssert.areEqual("FALSE", fields.get(13).getResult());
        msAssert.areEqual("TRUE", fields.get(14).getResult());
        msAssert.areEqual("TRUE", fields.get(15).getResult());
        msAssert.areEqual("TRUE", fields.get(16).getResult());
        msAssert.areEqual("FALSE", fields.get(17).getResult());
        msAssert.areEqual("TRUE", fields.get(18).getResult());
        msAssert.areEqual("FALSE", fields.get(19).getResult());
        msAssert.areEqual("TRUE", fields.get(20).getResult());
    }

    /// <summary>
    /// WORDSNET-11907 Document.UpdateFields does not update SUM(LEFT) and SUM(ABOVE) fields.
    /// </summary>
    @Test
    public void testJira11907() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira11907.docx");

        doc.updateFields();

        TestUtil.saveCheckGold(doc, "Fields\\EquationsAndFormulas\\TestJira11907.docx");
    }



    /// <summary>
    /// WORDSNET-12361 If field does not evaluate correctly when one part of expression is result of another field and it contains only whitespaces.
    /// </summary>
    @Test
    public void testJira12361() throws Exception
    {
        Document doc = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira12361.docx");
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        Field fieldIf = fields.get(2);

        doc.updateFields();

        msAssert.areEqual("false", fieldIf.getResult());
    }

    /// <summary>
    /// WORDSNET-12522 ExecuteWithRegions throws System.ArgumentOutOfRangeException when NumberGroupSeparator is used as ",".
    /// </summary>
    @Test (dataProvider = "testJira12522DataProvider")
    public void testJira12522(String cultureName, String fieldSeparator, String cultureSeparator, String expectedResult) throws Exception
    {
        SystemPal.setCulture(cultureName);
        msCultureInfo culture = CurrentThread.getCurrentCulture();
        culture.getTextInfo().setListSeparator(cultureSeparator);

        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(msString.format("= MOD(23{0}5)", fieldSeparator), null);

        field.update();

        msAssert.areEqual(expectedResult, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira12522DataProvider")
	public static Object[][] testJira12522DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"de-CH",  ",",  ";",  "!Syntax Error"},
			{"de-CH",  ",",  ",",  "3"},
			{"de-CH",  ";",  ";",  "3"},
			{"de-CH",  ";",  ",",  "!Syntax Error"},
			{"en-US",  ",",  ";",  "!Syntax Error"},
			{"en-US",  ",",  ",",  "3"},
			{"en-US",  ";",  ";",  "3"},
			{"en-US",  ";",  ",",  "!Syntax Error"},
		};
	}

    /// <summary>
    /// WORDSNET-13114 Nested IF statements cause error when merge field contains trailing backslash.
    /// </summary>
    @Test
    public void testJira13114() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira13114.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\EquationsAndFormulas\\TestJira13114.docx");
    }

    /// <summary>
    /// AW ignores the sign of the currency when calculating the sum of the range of table cells but Word saves one and shows it in the result field.
    /// </summary>
    @Test (dataProvider = "testJira6470DataProvider")
    public void testJira6470(String culture) throws Exception
    {
        SystemPal.setCulture(culture);

        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira6470.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\EquationsAndFormulas\\TestJira6470 " + culture + ".docx");
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira6470DataProvider")
	public static Object[][] testJira6470DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"en-US"},
			{"de-DE"},
		};
	}

    /// <summary>
    /// WORDSNET-13697 UpdateFields throws "Index was outside the bounds of the array" exception.
    /// </summary>
    @Test
    public void testJira13697() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira13697.docx");

        document.updateFields();

        msAssert.areEqual("0", document.getRange().getFields().get(0).getResult());
        msAssert.areEqual("0", document.getRange().getFields().get(1).getResult());
    }

    /// <summary>
    /// WORDSNET-13693, WORDSNET-13694 Document.UpdateFields incorrectly calculates SUM fields value.
    /// </summary>
    @Test
    public void testJira13693() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\TestJira13693.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\EquationsAndFormulas\\TestJira13693.docx");
    }

    @Test (dataProvider = "test13733DataProvider")
    public void test13733(String[] cells, int notused) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        builder.startTable();

        builder.insertCell();
        Field field = builder.insertField("=SUM(RIGHT)", null);

        String result = cells[0];
        for (int i = 1; i < cells.length; i++)
        {
            if (cells[i] == null)
                break;

            builder.insertCell();
            builder.write(cells[i]);
        }

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test13733DataProvider")
	public static Object[][] test13733DataProvider() throws Exception
	{
		return new Object[][]
		{
			{new String[]{"10", "1000%", null},  0},
			{new String[]{"1", "100%", null},  0},
			{new String[]{"0.1", "10%", null},  0},
			{new String[]{"0.01", "1%", null},  0},
			{new String[]{"0.001", "0.1%", null},  0},
			{new String[]{"10", "1000 %", null},  0},
			{new String[]{"1", "100 %", null},  0},
			{new String[]{"0.1", "10 %", null},  0},
			{new String[]{"0.01", "1 %", null},  0},
			{new String[]{"0.001", "0.1 %", null},  0},
			{new String[]{"10.0001", "1000.01%", null},  0},
			{new String[]{"1.0001", "100.01%", null},  0},
			{new String[]{"0.1001", "10.01%", null},  0},
			{new String[]{"0.0101", "1.01%", null},  0},
			{new String[]{"0.00101", "0.101%", null},  0},
			{new String[]{"10", "1000.00%", null},  0},
			{new String[]{"1", "100.00%", null},  0},
			{new String[]{"0.1", "10.00%", null},  0},
			{new String[]{"0.01", "1.00%", null},  0},
			{new String[]{"0.001", "0.100%", null},  0},
			{new String[]{"0.0", "0%", null},  0},
			{new String[]{"4.02", "1", "2%", "3"},  0},
			{new String[]{"1", "1", "2% 3", "4"},  0},
			{new String[]{"7.03", "1 2", "3%", "4"},  0},
			{new String[]{"3.03", "1 2", "3%", "4 5"},  0},
			{new String[]{"12.03", "1 2", "3% 4", "5"},  0},
			{new String[]{"18.03", "1 2", "3% 4", "5 6"},  0},
			{new String[]{"11.04", "1 2", "3 4%", "5"},  0},
			{new String[]{"1", "1", "2 %", "3"},  0},
			{new String[]{"1", "1", "2 % 3", "4"},  0},
			{new String[]{"7.03", "1 2", "3 %", "4"},  0},
			{new String[]{"12.03", "1 2", "3 %", "4 5"},  0},
			{new String[]{"12.03", "1 2", "3 % 4", "5"},  0},
			{new String[]{"18.03", "1 2", "3 % 4", "5 6"},  0},
			{new String[]{"11.04", "1 2", "3 4 %", "5"},  0},
		};
	}

    /// <summary>
    /// WORDSNET-8651 SUM(ABOVE) not correct working with values in brackets.
    /// </summary>
    @Test (dataProvider = "testJira8651SumAboveDataProvider")
    public void testJira8651SumAbove(String[] cells, int notused) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.startTable();

        String result = cells[0];
        for (int i = 1; i < cells.length; i++)
        {
            builder.insertCell();
            builder.write(cells[i]);
            builder.endRow();
        }

        builder.insertCell();
        Field field = builder.insertField("=SUM(ABOVE)", null);
        builder.endRow();

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira8651SumAboveDataProvider")
	public static Object[][] testJira8651SumAboveDataProvider() throws Exception
	{
		return new Object[][]
		{
			{new String[]{"10", "1", "2", "3", "4"},  0},
			{new String[]{"9", "(1)", "2", "3", "4"},  0},
			{new String[]{"7", "(1)", "(2)", "3", "4"},  0},
			{new String[]{"4", "(1)", "(2)", "(3)", "4"},  0},
			{new String[]{"(10)", "(1)", "(2)", "(3)", "(4)"},  0},
			{new String[]{"(8)", "1", "(2)", "(3)", "(4)"},  0},
			{new String[]{"(4)", "1", "2", "(3)", "(4)"},  0},
			{new String[]{"2", "1", "2", "3", "(4)"},  0},
			{new String[]{"8", "-1", "2", "3", "4"},  0},
			{new String[]{"4", "-1", "-2", "3", "4"},  0},
			{new String[]{"-2", "-1", "-2", "-3", "4"},  0},
			{new String[]{"5", "(1)", "-2", "3", "4"},  0},
			{new String[]{"-7", "(1)", "-(2)", "-3", "-4"},  0},
			{new String[]{"-4", "(1)", "(2)", "(3)", "-4"},  0},
			{new String[]{"(2)", "(1)", "(2)", "(3)", "-(4)"},  0},
			{new String[]{"2", "1", "-(2)", "-(3)", "(4)"},  0},
			{new String[]{"2", "1", "2", "-(3)", "(4)"},  0},
			{new String[]{"(6)", "-1", "2", "-3", "(4)"},  0},
			{new String[]{"1", "-1", "(2)", "5", "(4)"},  0},
			{new String[]{"5", "1", "1", "1", "1", "1"},  0},
			{new String[]{"1", "1", "1", "1", "(12222)", "1"},  0},
			{new String[]{"3", "1", "(1)", "1", "1", "1"},  0},
			{new String[]{"1", "(1)", "1", "1", "(1)", "1"},  0},
			{new String[]{"1", "1", "1", "1", "(1)", "(1)"},  0},
			{new String[]{"(4)", "1", "1", "(1)", "(4)", "(1)"},  0},
			{new String[]{"(1)", "100000", "(19999)", "1", "(1)", "(1)"},  0},
			{new String[]{"102", "100000000", "(345667)", "100", "1", "1", "1", "(1)"},  0},
			{new String[]{"-10", "100000000", "(345667)", "-10", "1", "-1", "-1", "1"},  0},
			{new String[]{"10", "100000000", "(345667)", "10", "1", "-1", "-1", "1"},  0},
			{new String[]{"-12", "100000000", "(345667)", "-10", "1", "-1", "-1", "(-1)"},  0},
			{new String[]{"(12)", "100000000", "(345667)", "-10", "1", "-1", "-1", "(1)"},  0},
		};
	}

    /// <summary>
    /// WORDSNET-8651 SUM(ABOVE) not correct working with values in brackets.
    /// </summary>
    @Test (dataProvider = "testJira8651FormulaDataProvider")
    public void testJira8651Formula(String formula, boolean useFormat, String result) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        String format = useFormat ? "\\# \"#\"" : null;
        Field field = builder.insertField(msString.format(" = {0} {1}", formula, format), null);

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira8651FormulaDataProvider")
	public static Object[][] testJira8651FormulaDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"(1)",  false,  "(1)"},
			{"(1)",  true,  "-1"},
			{"(-1)",  false,  "-1"},
			{"(-1)",  true,  "-1"},
			{"(1-2)",  false,  "-1"},
			{"(1-2)",  true,  "-1"},
			{"3 * (2)",  false,  "(6)"},
			{"3 * (2)",  true,  "-6"},
			{"(3) * 2",  false,  "(6)"},
			{"(3) * 2",  true,  "-6"},
			{"MOD((5), 2)",  false,  "(1)"},
			{"MOD((5), 2)",  true,  "-1"},
			{"SIGN((5))",  false,  "-1"},
			{"SIGN((5))",  true,  "-1"},
			{"(100)%",  false,  "(1)"},
			{"(100)%",  true,  "-1"},
			{"(5)+2$",  false,  "($3.00)"},
			{"(5)+2$",  true,  "-3"},
		};
	}


    /// <summary>
    /// WORDSNET-15783 Document.UpdateFields does not update the formula field
    /// </summary>
    @Test (dataProvider = "testJira15783DataProvider")
    public void testJira15783(String fieldCode) throws Exception
    {
        SystemPal.setCulture("nl-NL");

        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(fieldCode, null);

        field.update();

        msAssert.areEqual("#.##0,00", field.getFormat().getNumericFormat());
        msAssert.areEqual("6.100,28", field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira15783DataProvider")
	public static Object[][] testJira15783DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"=4*1525,07 \\# “#.##0,00”"},
			{"=4*1525,07\\# “#.##0,00”"},
		};
	}

    /// <summary>
    /// WORDSNET-19209 UpdateFields does not calculate SUM inside FORMTEXT.
    /// </summary>
    @Test
    public void test19209() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\Test19209.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\EquationsAndFormulas\\Test19209.docx");
    }


    @Test (dataProvider = "testFunctionParametersWhenListDelimiterEqualsToGroupDelimiterDataProvider")
    public void testFunctionParametersWhenListDelimiterEqualsToGroupDelimiter(String fieldCode, String result) throws Exception
    {
        // Culture with list delimiter == group separator.
        SystemPal.setCulture("en-UK");

        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(fieldCode, null);

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFunctionParametersWhenListDelimiterEqualsToGroupDelimiterDataProvider")
	public static Object[][] testFunctionParametersWhenListDelimiterEqualsToGroupDelimiterDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"=ABS(100)",  "100"},
			{"=ABS(100,000.00)",  "100,000"},
			{"=ABS(12,000+1000 - 1,000)",  "12,000"},
			{"=ABS(12,000+1000.00 -ABS(-1,000))",  "12,000"},
			{"=NOT(1,000 >=1.0)",  "0"},
		};
	}

    /// <summary>
    /// WORDSNET-19482 Document.UpdateFields does not update the formula fields.
    /// </summary>
    @Test
    public void test19482() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\Test19482.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\EquationsAndFormulas\\Test19482.docx");
    }



    /// <summary>
    /// WORDSNET-25941 FORMULA field dies not recognize table cell coordinates references.
    /// </summary>
    @Test (dataProvider = "test25941DataProvider")
    public void test25941(String formula, String result) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        builder.startTable();
        addTableRow(builder, "1", "2", "3", "4");
        addTableRow(builder, "15", "26", "37", "48");
        addTableRow(builder, "111", "222", "333", "444");
        builder.insertCell();
        Field field = builder.insertField(msString.format(" = {0}", formula), null);
        builder.endTable();

        field.update();

        msAssert.areEqual(result, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test25941DataProvider")
	public static Object[][] test25941DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"R2C2",  "26"},
			{"R1C3 + R3C1",  "114"},
			{"SUM(R1C1, R2C2, R3C3)",  "360"},
			{"R5C5",  "!E5 Is Not In Table"},
			{"R0C0",  "!Undefined Bookmark, R0C0"},
			{"SUM(R1C1:R3C3)",  "!Syntax Error"},
		};
	}

    private static void addTableRow(DocumentBuilder builder, /*params*/ String... values)
    {
        for (String value : values)
        {
            builder.insertCell();
            builder.write(value);
        }

        builder.endRow();
    }


    /// <summary>
    /// WORDSNET-28506 Formula fields are updated improperly.
    /// </summary>
    @Test
    public void test28506() throws Exception
    {
        Document document = TestUtil.open("Fields\\EquationsAndFormulas\\Test28506.docx");

        document.updateFields();

        FieldCollection fields = document.getRange().getFields();
        msAssert.areEqual("6", fields.get(0).getResult());
        msAssert.areEqual("1", fields.get(1).getResult());
        msAssert.areEqual("1", fields.get(2).getResult());
        msAssert.areEqual("1", fields.get(3).getResult());
        msAssert.areEqual("5", fields.get(4).getResult());
    }
}

