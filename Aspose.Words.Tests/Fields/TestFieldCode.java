// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.Language;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import java.util.Iterator;


/// <summary>
/// Tests how differently composed field code is parsed and how the fields are updated.
/// </summary>
@Test
public class TestFieldCode extends TestFieldsBase
{
    /// <summary>
    /// Tests how various field code features are parsed (no spaces, escape symbols etc).
    /// </summary>
    @Test
    public void testParse() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Fields\\FieldCode\\TestFieldCode.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        FieldCode fieldCode = new FieldCode(fields.get(0));
        msAssert.areEqual("FieldName", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("TextBefore", fieldCode.getSwitchArgumentAsString("\\b"));
        msAssert.areEqual("Text after", fieldCode.getSwitchArgumentAsString("\\f"));

        fieldCode = new FieldCode(fields.get(1));
        msAssert.areEqual("Field name with \" and \\ and *", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("TextBefore", fieldCode.getSwitchArgumentAsString("\\b"));
        msAssert.areEqual("Text after", fieldCode.getSwitchArgumentAsString("\\f"));

        fieldCode = new FieldCode(fields.get(2));
        msAssert.areEqual("FieldName", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("\"TextBefore\"", fieldCode.getSwitchArgumentAsString("\\b"));
        msAssert.areEqual("", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\m"));
        msAssert.isNull(fieldCode.getSwitchArgumentAsString("\\m"));
        msAssert.isTrue(fieldCode.hasSwitch("\\v"));
        msAssert.isNull(fieldCode.getSwitchArgumentAsString("\\v"));
        msAssert.isFalse(fieldCode.hasSwitch("\\s"));

        doc.getRange().updateFields();
        TestUtil.saveCheckGold(doc, "Fields\\FieldCode\\TestFieldCode Modified.docx");

        msAssert.areEqual("TextBefore«FieldName»Text after", fields.get(0).getResult());
        msAssert.areEqual("TextBefore«Field name with \" and \\ and *»Text after", fields.get(1).getResult());
        msAssert.areEqual("\"TextBefore\"«FieldName»", fields.get(2).getResult());
    }

    /// <summary>
    /// Test how nested fields are updated.
    /// </summary>
    @Test
    public void testNested() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\FieldCode\\TestNested.docx");
        doc = TestUtil.saveOpen(doc, "Fields\\FieldCode\\TestNested Modified.docx");

        NodeCollection paragraphs = doc.getChildNodes(NodeType.PARAGRAPH, true);

        ArrayList<Field> fields = FieldExtractor.extractToCollection(paragraphs.get(0));
        msAssert.areEqual("not", fields.get(fields.size() - 1).getResult());

        fields = FieldExtractor.extractToCollection(paragraphs.get(1));
        msAssert.areEqual("", fields.get(fields.size() - 1).getResult());

        fields = FieldExtractor.extractToCollection(paragraphs.get(2));
        msAssert.areEqual("Credit not acceptable", fields.get(fields.size() - 1).getResult());

        fields = FieldExtractor.extractToCollection(paragraphs.get(3));
        msAssert.areEqual("-26.2", fields.get(fields.size() - 1).getResult());
    }

    /// <summary>
    /// Test how fields containing paragraph breaks are updated.
    /// </summary>
    @Test
    public void testParagraphBreaks() throws Exception
    {
        Document doc = openSaveOpenUpdateFields("Fields\\FieldCode\\TestParagraphBreaks.docx");
        doc = TestUtil.saveOpen(doc, "Fields\\FieldCode\\TestParagraphBreaks Modified.docx");


        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("-26.2%", fields.get(fields.size() - 1).getResult());
    }

    /// <summary>
    /// Tests how fields are updated when a field code contains nested fields without extra
    /// leading and trailing whitespaces for the corresponding field chars.
    /// </summary>
    @Test
    public void testNoExtraWhitespaces() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldCode\\TestNoExtraWhitespaces.docx");
        doc.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        String[] results = { "0", "1", "", "3", "3", "0", "1", "2", "", "2" };
        msAssert.areEqual(results.length, fields.size());

        for (int i = 0; i < results.length; i++)
            msAssert.areEqual(results[i], fields.get(i).getResult(), fields.get(i).getFieldCode());
    }

    /// <summary>
    /// Tests how field arguments are modified.
    /// </summary>
    @Test
    public void testSetArguments() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldCode\\TestFieldCodeSet.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        Field field = fields.get(0);
        FieldCode fieldCode = field.getFieldCodeCache();

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("0", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));

        // Change the first argument. Set it to an empty string. It should be enclosed in double quotes.
        fieldCode.setArgument(0, "");

        msAssert.areEqual(" LINK  \"\" \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("", fieldCode.getArgumentAsString(0));

        // Change the first argument. Set it to a string without whitespaces. No double quotes this time.
        fieldCode.setArgument(0, "New_argument");

        msAssert.areEqual(" LINK  New_argument \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("New_argument", fieldCode.getArgumentAsString(0));

        // Change the first argument. Set it to a string with a whitespace. It should be enclosed in double quotes
        // again.
        fieldCode.setArgument(0, "New argument");

        msAssert.areEqual(" LINK  \"New argument\" \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("New argument", fieldCode.getArgumentAsString(0));

        // Add the third argument. The second one should be added as an empty string.
        fieldCode.setArgument(2, "New argument");

        msAssert.areEqual(" LINK  \"New argument\" \"\" \"New argument\" \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(3, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("New argument", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("", fieldCode.getArgumentAsString(1));
        msAssert.areEqual("New argument", fieldCode.getArgumentAsString(2));

        // Attempt to set the first argument to null, which causes setting it to an empty string to avoid shifting.
        fieldCode.setArgument(0, null);

        msAssert.areEqual(" LINK  \"\" \"\" \"New argument\" \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(3, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("", fieldCode.getArgumentAsString(1));
        msAssert.areEqual("New argument", fieldCode.getArgumentAsString(2));

        // Set the third argument to null, which works safely.
        fieldCode.setArgument(2, null);

        msAssert.areEqual(" LINK  \"\" \"\"  \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(2, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("", fieldCode.getArgumentAsString(1));

        // Attempt to set the third argument to null now makes no sense.
        fieldCode.setArgument(2, null);

        msAssert.areEqual(" LINK  \"\" \"\"  \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(2, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("", fieldCode.getArgumentAsString(1));

        // Remove all arguments.
        fieldCode.setArgument(1, null);
        fieldCode.setArgument(0, null);

        msAssert.areEqual(" LINK     \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(0, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        // Add the third argument. The first one and second one should be added as an empty string.
        fieldCode.setArgument(2, "New argument");

        msAssert.areEqual(" LINK     \"\" \"\" \"New argument\" \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(3, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("", fieldCode.getArgumentAsString(0));
        msAssert.areEqual("", fieldCode.getArgumentAsString(1));
        msAssert.areEqual("New argument", fieldCode.getArgumentAsString(2));
    }

    /// <summary>
    /// Tests how field switches are set.
    /// </summary>
    @Test
    public void testSetSwitches() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldCode\\TestFieldCodeSet.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        Field field = fields.get(0);
        FieldCode fieldCode = field.getFieldCodeCache();

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("0", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));

        // Setting existing switch changes nothing.
        fieldCode.setSwitch("\\a", true);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a \\f 0 \\p ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("0", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));

        // Setting new switch appends it to the code.
        fieldCode.setSwitch("\\d", true);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a \\f 0 \\p  \\d", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(4, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("0", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));

        // Setting a switch with an argument replaces an existing one.
        fieldCode.setSwitch("\\f", "100500");

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a \\f 100500 \\p  \\d", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(4, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("100500", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));

        // Setting a switch with a null argument removes an existing one.
        fieldCode.setSwitch("\\f", null);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a  \\p  \\d", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(3, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isFalse(fieldCode.hasSwitch("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));

        // Setting a switch with an argument appends it to the code. Also double quotes are added if needed.
        fieldCode.setSwitch("\\f", "100 500");

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a  \\p  \\d \\f \"100 500\"", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(4, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("100 500", fieldCode.getSwitchArgumentAsString("\\f"));

        // Setting a switch with an argument and index appends it to the code.
        fieldCode.setSwitch("\\*", "MERGEFORMAT", 0);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a  \\p  \\d \\f \"100 500\" \\* MERGEFORMAT", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(5, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("100 500", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\*"));
        msAssert.areEqual("MERGEFORMAT", fieldCode.getSwitchArgumentAsString("\\*"));

        // Setting a switch with an argument and existing index replaces it.
        fieldCode.setSwitch("\\*", "CHARFORMAT", 0);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a  \\p  \\d \\f \"100 500\" \\* CHARFORMAT", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(5, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("100 500", fieldCode.getSwitchArgumentAsString("\\f"));
        msAssert.isTrue(fieldCode.hasSwitch("\\*"));
        msAssert.areEqual("CHARFORMAT", fieldCode.getSwitchArgumentAsString("\\*"));

        // Setting a switch with a null argument and existing index removes it.
        fieldCode.setSwitch("\\*", null, 0);

        msAssert.areEqual(" LINK  AcroExch.acrobatsecuritysettings.1 \\a  \\p  \\d \\f \"100 500\" ", field.getFieldCode());

        msAssert.areEqual(1, fieldCode.getArguments().size());
        msAssert.areEqual(4, fieldCode.getSwitches().size());

        msAssert.areEqual("AcroExch.acrobatsecuritysettings.1", fieldCode.getArgumentAsString(0));
        msAssert.isTrue(fieldCode.hasSwitch("\\a"));
        msAssert.isTrue(fieldCode.hasSwitch("\\p"));
        msAssert.isTrue(fieldCode.hasSwitch("\\d"));
        msAssert.isTrue(fieldCode.hasSwitch("\\f"));
        msAssert.areEqual("100 500", fieldCode.getSwitchArgumentAsString("\\f"));
    }

    /// <summary>
    /// Tests how field format is parsed and changed.
    /// </summary>
    @Test
    public void testFieldFormat() throws Exception
    {
        Document doc = TestUtil.open("Fields\\FieldCode\\TestFieldFormat.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        Field field = fields.get(0);
        FieldFormat format = field.getFormat();

        msAssert.areEqual(" DATE  \\@ \"dddd, MMMM dd, yyyy\" \\* Upper \\* Lower \\* FirstCap \\* MERGEFORMAT ", field.getFieldCode());

        msAssert.isNull(format.getNumericFormat());
        msAssert.areEqual("dddd, MMMM dd, yyyy", format.getDateTimeFormat());
        msAssert.areEqual(4, format.getGeneralFormats().getCount());
        msAssert.areEqual(GeneralFormat.UPPER, format.getGeneralFormats().get(0));
        msAssert.areEqual(GeneralFormat.LOWER, format.getGeneralFormats().get(1));
        msAssert.areEqual(GeneralFormat.FIRST_CAP, format.getGeneralFormats().get(2));
        msAssert.areEqual(GeneralFormat.MERGE_FORMAT, format.getGeneralFormats().get(3));

        format.setNumericFormat("0.#");
        format.getGeneralFormats().remove(GeneralFormat.MERGE_FORMAT);

        msAssert.areEqual(" DATE  \\# 0.# \\@ \"dddd, MMMM dd, yyyy\" \\* Upper \\* Lower \\* FirstCap  ", field.getFieldCode());

        msAssert.areEqual("0.#", format.getNumericFormat());
        msAssert.areEqual("dddd, MMMM dd, yyyy", format.getDateTimeFormat());
        msAssert.areEqual(3, format.getGeneralFormats().getCount());
        msAssert.areEqual(GeneralFormat.UPPER, format.getGeneralFormats().get(0));
        msAssert.areEqual(GeneralFormat.LOWER, format.getGeneralFormats().get(1));
        msAssert.areEqual(GeneralFormat.FIRST_CAP, format.getGeneralFormats().get(2));

        format.getGeneralFormats().removeAt(1);
        format.setDateTimeFormat(null);

        msAssert.areEqual(" DATE  \\# 0.#  \\* Upper  \\* FirstCap  ", field.getFieldCode());

        msAssert.areEqual("0.#", format.getNumericFormat());
        msAssert.isNull(format.getDateTimeFormat());
        msAssert.areEqual(2, format.getGeneralFormats().getCount());
        msAssert.areEqual(GeneralFormat.UPPER, format.getGeneralFormats().get(0));
        msAssert.areEqual(GeneralFormat.FIRST_CAP, format.getGeneralFormats().get(1));

        format.getGeneralFormats().add(GeneralFormat.CHAR_FORMAT);

        msAssert.areEqual(" DATE  \\# 0.#  \\* Upper  \\* FirstCap   \\* CHARFORMAT", field.getFieldCode());

        msAssert.areEqual("0.#", format.getNumericFormat());
        msAssert.isNull(format.getDateTimeFormat());
        msAssert.areEqual(3, format.getGeneralFormats().getCount());
        msAssert.areEqual(GeneralFormat.UPPER, format.getGeneralFormats().get(0));
        msAssert.areEqual(GeneralFormat.FIRST_CAP, format.getGeneralFormats().get(1));
        msAssert.areEqual(GeneralFormat.CHAR_FORMAT, format.getGeneralFormats().get(2));
    }

    /// <summary>
    /// Tests how the field API works with existing fields.
    /// </summary>
    @Test
    public void testExistingField() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        // Insert a field.
        FieldSeq field = (FieldSeq)builder.insertField(FieldType.FIELD_SEQUENCE, true);
        field.setBookmarkName("Bookmark1");
        field.setResetHeadingLevel("5");
        field.setHideFieldResult(true);
        field.getFormat().getGeneralFormats().add(GeneralFormat.MERGE_FORMAT);

        msAssert.areEqual(" SEQ  \"\" Bookmark1 \\s 5 \\h \\* MERGEFORMAT", field.getFieldCode());

        // We changed our mind.
        field.setResetHeadingLevel("0");
        msAssert.areEqual(" SEQ  \"\" Bookmark1 \\s 0 \\h \\* MERGEFORMAT", field.getFieldCode());

        // Find the existing field.
        field = (FieldSeq)((FieldEnd)builder.getCurrentStory().getFirstParagraph().getLastChild()).getField();
        msAssert.areEqual(" SEQ  \"\" Bookmark1 \\s 0 \\h \\* MERGEFORMAT", field.getFieldCode());

        field.setSequenceIdentifier("Sequence 1");
        msAssert.areEqual(" SEQ  \"Sequence 1\" Bookmark1 \\s 0 \\h \\* MERGEFORMAT", field.getFieldCode());

        field.setResetNumber("10");
        msAssert.areEqual(" SEQ  \"Sequence 1\" Bookmark1 \\s 0 \\h \\* MERGEFORMAT \\r 10", field.getFieldCode());

        field.setHideFieldResult(false);
        msAssert.areEqual(" SEQ  \"Sequence 1\" Bookmark1 \\s 0  \\* MERGEFORMAT \\r 10", field.getFieldCode());

        field.setSequenceIdentifier(null);
        msAssert.areEqual(" SEQ  \"\" Bookmark1 \\s 0  \\* MERGEFORMAT \\r 10", field.getFieldCode());

        field.setBookmarkName(null);
        msAssert.areEqual(" SEQ  \"\"  \\s 0  \\* MERGEFORMAT \\r 10", field.getFieldCode());

        field.setSequenceIdentifier(null);
        msAssert.areEqual(" SEQ    \\s 0  \\* MERGEFORMAT \\r 10", field.getFieldCode());
    }


    /// <summary>
    /// WORDSNET-11844 Content (Fields) position is changed after conversion from Doc to Pdf
    /// </summary>
    @Test (dataProvider = "testJira11844DataProvider")
    public void testJira11844(String fieldCode) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        FieldIf field = (FieldIf) builder.insertField(fieldCode);

        msAssert.areEqual("1", field.getLeftExpression());
        msAssert.areEqual("=", field.getComparisonOperator());
        msAssert.areEqual("2", field.getRightExpression());
        msAssert.areEqual("true", field.getTrueText());
        msAssert.areEqual("false", field.getFalseText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira11844DataProvider")
	public static Object[][] testJira11844DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"IF 1 = 2 \"true\" \"false\""},
			{"IF 1 = 2 „true“ „false“"},
		};
	}

    /// <summary>
    /// Tests how field type is parsed from field code.
    /// </summary>
    @Test (dataProvider = "testFieldTypeParseDataProvider")
    public void testFieldTypeParse(String fieldCode, /*FieldType*/int expectedType)
    {
        /*FieldType*/int actualType = FieldUtil.getFieldType(fieldCode);
        msAssert.areEqual(expectedType, actualType);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFieldTypeParseDataProvider")
	public static Object[][] testFieldTypeParseDataProvider()
	{
		return new Object[][]
		{
			{"=A",  FieldType.FIELD_FORMULA},
			{"SAVEDATE",  FieldType.FIELD_SAVE_DATE},
			{"QUOT",  FieldType.FIELD_NONE},
			{"QUOTE%",  FieldType.FIELD_NONE},
			{"QUOTES",  FieldType.FIELD_NONE},
			{"NUMPAGES-1",  FieldType.FIELD_NONE},
			{"NUMPAGES-",  FieldType.FIELD_NONE},
			{"NUMPAGES!",  FieldType.FIELD_NONE},
			{"NUMPAGESSSSS",  FieldType.FIELD_NONE},
			{"NUMPAGES",  FieldType.FIELD_NUM_PAGES},
			{"\"NUMPAGES\"",  FieldType.FIELD_NUM_PAGES},
			{" \"NUMPAGES\" blah blah",  FieldType.FIELD_NUM_PAGES},
			{" NUMPAGES blah blah",  FieldType.FIELD_NUM_PAGES},
			{"\"NUMPAGES!\"",  FieldType.FIELD_NONE},
			{"QUOTE”",  FieldType.FIELD_QUOTE},
			{" IF\u0013 ",  FieldType.FIELD_IF},
			{"\\PAGE",  FieldType.FIELD_PAGE},
			{"\\\\PAGE",  FieldType.FIELD_NONE},
			{"\"\\PAGE\"",  FieldType.FIELD_PAGE},
			{"\\\"PAGE\"",  FieldType.FIELD_NONE},
		};
	}

    private static void verifyTestJira11905Fields(Document doc)
    {
        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        msAssert.areEqual(4, fields.size());

        // These values of field codes are the same, as they displayed in MSW.
        // In RTF code of the source document the values ":"  and "\\:" are inverted.
        msAssert.areEqual(" XE \"A\\:B\\:C\" ", fields.get(0).getFieldCode());
        msAssert.areEqual(" XE \"A:B:C\" ", fields.get(1).getFieldCode());
        msAssert.areEqual(" XE \"A\\:B:C\" ", fields.get(2).getFieldCode());
    }

    /// <summary>
    /// Tests how fields with hidden text in code are updated.
    /// </summary>
    @Test
    public void testFieldCodeWithHiddenText() throws Exception
    {
        Document document = TestUtil.open("Fields\\FieldCode\\TestFieldCodeWithHiddenText.docx");

        document.updateFields();

        TestUtil.saveCheckGold(document, "Fields\\FieldCode\\TestFieldCodeWithHiddenText.docx");
    }

    @Test
    public void testSetSwitchShouldNotAffectArguments() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        FieldIncludePicture field = (FieldIncludePicture) builder.insertField("INCLUDEPICTURE C:\\\\Temp\\\\Picuture.png", null);

        field.getFormat().getGeneralFormats().addOrRemove(GeneralFormat.MERGE_FORMAT, true);

        msAssert.areEqual("C:\\Temp\\Picuture.png", field.getSourceFullName());
    }

    @Test
    public void testSetArgumentsSeparately() throws Exception
    {
        RunPr boldPr = new RunPr();
        RunPr italicPr = new RunPr();

        boldPr.setBold(AttrBoolEx.TRUE);
        italicPr.setItalic(AttrBoolEx.TRUE);

        Document document = new Document();

        FieldIf field = (FieldIf)new FieldBuilder(FieldType.FIELD_IF)
            .addArgument("left")
            .addArgument("=")
            .addArgument("right")
            .addArgument(new FieldArgumentBuilder().addNode(new Run(document, "true text (bold)", boldPr)))
            .addArgument(new FieldArgumentBuilder().addNode(new Run(document, "false text (italic)", italicPr)))
            .buildAndInsert(document.getFirstSection().getBody().getFirstParagraph());


        field.setComparisonOperator("<>");

        Run trueText = (Run)document.getNodeById("11.0.0.0");
        Run falseText = (Run)document.getNodeById("15.0.0.0");

        msAssert.areEqual("true text (bold)", trueText.getText());
        msAssert.isTrue(trueText.getFont().getBold());
        msAssert.areEqual("false text (italic)", falseText.getText());
        msAssert.isTrue(falseText.getFont().getItalic());
    }

    @Test
    public void testRemoveDuplicatedSwitch() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        FieldFileName field = (FieldFileName)builder.insertField(" FILENAME \\p \\p \\t \\p ", null);

        field.setIncludeFullPath(false);

        msAssert.isFalse(field.getIncludeFullPath());
        msAssert.areEqual(" FILENAME   \\t  ", field.getFieldCode());
    }

    @Test (dataProvider = "testIsolateArgumentsDataProvider")
    public void testIsolateArguments(int count) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(" REF \"arg 1\" \"arg 2\" \"arg 3\" ", null);
        FieldCode fieldCode = field.getFieldCodeCache();

        for (int i = 0; i < count; i++)
            fieldCode.isolateElements();


        String[] arguments = {"\"arg 1\"", "\"arg 2\"", "\"arg 3\""};
        assertArguments(fieldCode, arguments);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsolateArgumentsDataProvider")
	public static Object[][] testIsolateArgumentsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{1},
			{2},
		};
	}

    @Test (dataProvider = "testIsolateSwitchesDataProvider")
    public void testIsolateSwitches(int count) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(" REF \\d \\f \\h \\p ", null);
        FieldCode fieldCode = field.getFieldCodeCache();

        for (int i = 0; i < count; i++)
            fieldCode.isolateElements();

        String[] switches = {"\\d", "\\f", "\\h", "\\p"};
        assertSwitches(fieldCode, switches, null);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsolateSwitchesDataProvider")
	public static Object[][] testIsolateSwitchesDataProvider() throws Exception
	{
		return new Object[][]
		{
			{1},
			{2},
		};
	}

    @Test (dataProvider = "testIsolateElementsDataProvider")
    public void testIsolateElements(int count) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(" REF \"arg 1\" \\d \"switch arg\" \"arg 2\" \\f \"arg 3\" \\h \\p \\* Upper \\* MERGEFORMAT ", null);
        FieldCode fieldCode = field.getFieldCodeCache();

        for (int i = 0; i < count; i++)
            fieldCode.isolateElements();

        String[] switches = { "\\d", "\\f", "\\h", "\\p", "\\*", "\\*" };
        String[] switchArguments = { "\"switch arg\"", null, null, null, "Upper", "MERGEFORMAT" };
        assertSwitches(fieldCode, switches, switchArguments);

        String[] arguments = { "\"arg 1\"", "\"arg 2\"", "\"arg 3\"" };
        assertArguments(fieldCode, arguments);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testIsolateElementsDataProvider")
	public static Object[][] testIsolateElementsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{1},
			{2},
		};
	}

    private static void assertArguments(FieldCode fieldCode, String[] arguments)
    {
        int i = 0;
        for (FieldArgument argument : fieldCode.getArguments())
        {
            assertRange(argument.getRange(), arguments[i]);
            msAssert.areEqual(arguments[i], argument.getText());
            i++;
        }
    }

    private static void assertSwitches(FieldCode fieldCode, String[] switches, String[] switchArguments)
    {
        if (switchArguments == null)
            switchArguments = new String[switches.length];

        int i = 0;
        for (FieldSwitch fieldSwitch : fieldCode.getSwitches())
        {
            assertRange(fieldSwitch.getRange(), switches[i]);
            msAssert.areEqual(switches[i], fieldSwitch.getName());

            if (switchArguments[i] == null)
                msAssert.isFalse(fieldSwitch.hasArgument());
            else
            {
                assertRange(fieldSwitch.getArgument().getRange(), switchArguments[i]);
                msAssert.areEqual(switchArguments[i], fieldSwitch.getArgument().getText());
            }

            i++;
        }
    }

    private static void assertRange(NodeRange range, String expectedText)
    {
        String text = NodeTextCollector.getText(range);
        msAssert.areEqual(expectedText, text);
        msAssert.isTrue(range.getStart().isStart());
        msAssert.isTrue(range.getEnd().isEnd());
    }

    @Test
    public void testSetArgumentWithNestedField() throws Exception
    {
        Document document = new Document();

        FieldIf field = (FieldIf)new FieldBuilder(FieldType.FIELD_IF)
            .addArgument(new FieldArgumentBuilder().addText("! ").addField(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("field1")).addText(" !"))
            .addArgument("=")
            .addArgument(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("field2"))
            .addArgument("true text")
            .addArgument("false text")
            .buildAndInsert(document.getFirstSection().getBody().getFirstParagraph());

        field.setLeftExpression("left");
        field.setComparisonOperator("<>");
        field.setRightExpression("right");

        msAssert.areEqual("left", field.getLeftExpression());
        msAssert.areEqual("<>", field.getComparisonOperator());
        msAssert.areEqual("right", field.getRightExpression());

        msAssert.areEqual(0, FieldExtractor.extractToCollection(document, true, FieldType.FIELD_MERGE_FIELD).size());
    }



    /// <summary>
    /// WORDSNET-13583 GetFieldCode on a IF field returning Empty.
    /// </summary>
    @Test (dataProvider = "testJira13583DataProvider")
    public void testJira13583(boolean includeChildFieldCodes, String expectedFieldCode) throws Exception
    {
        Document document = new Document();
        Field field = new FieldBuilder(FieldType.FIELD_IF)
            .addArgument(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("Left"))
            .addArgument("=")
            .addArgument(new FieldBuilder(FieldType.FIELD_MERGE_FIELD).addArgument("Right"))
            .addArgument("true text")
            .addArgument("false text")
            .buildAndInsert(document.getFirstSection().getBody().getFirstParagraph());
        field.update();

        String fieldCode = field.getFieldCode(includeChildFieldCodes);

        msAssert.areEqual(expectedFieldCode, fieldCode);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira13583DataProvider")
	public static Object[][] testJira13583DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true,  " IF \u0013 MERGEFIELD Left \u0014\u00ABLeft\u00BB\u0015 = \u0013 MERGEFIELD Right \u0014\u00ABRight\u00BB\u0015 \"true text\" \"false text\" "},
			{false,  " IF \u00ABLeft\u00BB = \u00ABRight\u00BB \"true text\" \"false text\" "},
		};
	}

    /// <summary>
    /// WORDSNET-13773 System.NullReferenceException occurs during converting DOC to PDF.
    /// </summary>
    @Test
    public void testJira13773() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        FieldSet field = (FieldSet)builder.insertField(" SET Bookmark\\Name Bookmark\\Text\\* MERGEFORMAT");

        msAssert.areEqual("BookmarkName", field.getBookmarkName());
        NodeRange nameRange = field.getFieldCodeCache().getArgument(0).getRange();
        msAssert.isFalse(nameRange.isVoid());
        msAssert.areEqual(5, nameRange.getStart().getOffset());
        msAssert.areEqual(18, nameRange.getEnd().getOffset());

        msAssert.areEqual("BookmarkText", field.getBookmarkText());
        NodeRange textRange = field.getFieldCodeCache().getArgument(1).getRange();
        msAssert.isFalse(textRange.isVoid());
        msAssert.areEqual(19, textRange.getStart().getOffset());
        msAssert.areEqual(32, textRange.getEnd().getOffset());
    }

    private static final int NULL_LANGUAGE_ID = Integer.MAX_VALUE;

    //JAVA: method is internalized
	/*@Test*/
    void testFieldLocaleId(int initialLocaleId, int initialLocaleIdBidi, boolean initialBidi, int newLocaleId) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();

        if (initialLocaleId != NULL_LANGUAGE_ID)
            builder.getFont().setLocaleId(initialLocaleId);
        if (initialLocaleIdBidi != NULL_LANGUAGE_ID)
            builder.getFont().setLocaleIdBi(initialLocaleIdBidi);
        builder.getFont().setBidi(initialBidi);
        Field field = builder.insertField("=1", null);

        assertLocaleId(
            field,
            initialBidi
                ? (initialLocaleIdBidi != NULL_LANGUAGE_ID ? initialLocaleIdBidi : RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID)
                : (initialLocaleId != NULL_LANGUAGE_ID ? initialLocaleId : RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID));

        field.setLocaleId(newLocaleId);
        assertLocaleId(field, newLocaleId);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testFieldLocaleIdSourceDataProvider")
	public void testFieldLocaleIdSource(int initialLocaleId, int initialLocaleIdBidi, boolean initialBidi, int newLocaleId) throws Exception
	{
		testFieldLocaleId(initialLocaleId, initialLocaleIdBidi, initialBidi, newLocaleId);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testFieldLocaleIdSourceDataProvider")
	Object[][] testFieldLocaleIdSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = testFieldLocaleIdSource().iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    private static void assertLocaleId(Field field, int expectedLocaleId)
    {
        msAssert.areEqual(expectedLocaleId, field.getLocaleId());
        field = field.getDocument().getRange().getFields().get(0);
        msAssert.areEqual(expectedLocaleId, field.getLocaleId());
    }

    @Test (enabled = false)
    public static ArrayList<TestCaseData> testFieldLocaleIdSource()
    {
        ArrayList<TestCaseData> result = new ArrayList<TestCaseData>();

        int[] languages = {NULL_LANGUAGE_ID, (int)Language.RUSSIAN_RUSSIA, (int)Language.ENGLISH_US};
        int[] languageBis = { NULL_LANGUAGE_ID, (int)Language.HEBREW_ISRAEL, (int)Language.ARABIC_SAUDI_ARABIA };
        int[] newLanguages = {(int)Language.RUSSIAN_RUSSIA, (int)Language.ENGLISH_US, (int)Language.HEBREW_ISRAEL, (int)Language.ARABIC_SAUDI_ARABIA};
        boolean[] bidis = {true, false};

        for (int language : languages)
            for (int languageBi : languageBis)
                for (boolean bidi : bidis)
                    for (int newLanguage : newLanguages)
                        msArrayList.add(result, new TestCaseData(language, languageBi, bidi, newLanguage));

        return result;
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Not a valid LCID:(?s).*", dataProvider = "testInvalidLocaleIdDataProvider")
    public void testInvalidLocaleId(int localeId) throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField("=1", null);

        field.setLocaleId(localeId);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testInvalidLocaleIdDataProvider")
	public static Object[][] testInvalidLocaleIdDataProvider() throws Exception
	{
		return new Object[][]
		{
			{-1},
			{0},
			{9999},
		};
	}

    /// <summary>
    /// WORDSNET-17472 DOCX to PDF conversion issue with SAVEDATE rendering
    /// </summary>
    @Test
    public void  testJira17472() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Field field = builder.insertField(" DATE \\@ «yyyy\"", null);

        field.update();

        msAssert.areEqual("2006", field.getResult());
    }

    /// <summary>
    /// WORDSNET-22027 System.InvalidOperationException occurs upon updating fields.
    /// </summary>
    @Test
    public void test22027() throws Exception
    {
        Document document = TestUtil.open("Fields\\FieldCode\\Test22027.docx");
        Field field = FieldExtractor.extractToCollection(document, true, FieldType.FIELD_EQUATION).get(0);
        FieldArgument argument = field.getFieldCodeCache().getArgument(0);

        String argumentText = FieldCodeParser.getArgumentTextByRange(argument);

        msAssert.areEqual("\\x (1)", argumentText);
    }

    /// <summary>
    /// Tests how quotes with symbolic fonts are parsed.
    /// </summary>
    @Test
    public void testSymbolicQuotes() throws Exception
    {
        Document document = TestUtil.open("Fields\\FieldCode\\TestSymbolicQuotes.docx");

        FieldCollection fields = document.getRange().getFields();

        assertIndexEntryFieldArgument((FieldXE)fields.get(0), true, "\"A“\"", "A“");
        assertIndexEntryFieldArgument((FieldXE)fields.get(1), false, "B“", "B“");
        assertIndexEntryFieldArgument((FieldXE)fields.get(2), false, "“C", "“C");
        assertIndexEntryFieldArgument((FieldXE)fields.get(3), false, "“", "“");
        assertIndexEntryFieldArgument((FieldXE)fields.get(4), false, "“““", "“““");
    }

    private static void assertIndexEntryFieldArgument(FieldXE field, boolean isInDoubleQuotes, String text, String normalizedText) throws Exception
    {
        FieldArgument argument = field.getFieldCodeCache().getArgument(0);

        msAssert.areEqual(isInDoubleQuotes, argument.isInDoubleQuotes());
        msAssert.areEqual(normalizedText, argument.getNormalizedText());
        msAssert.areEqual(text, argument.getText());
        ((IFieldArgument)argument).invalidateText();
        msAssert.areEqual(text, argument.getText());
    }

    /// <summary>
    /// WORDSNET-26596 Hyperlink address is truncated f contains Unicode characters.
    /// </summary>
    @Test
    public void test26596() throws Exception
    {
        final String ADDRESS = "C:\\Temp\\tmp\\⺀⺁⺂⺃¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹ÊËÌÍΑβγδεζηθικדהוזח؉؊ٓ٧٩\\Parent File.docx";

        DocumentBuilder builder = new DocumentBuilder();
        FieldHyperlink field = (FieldHyperlink)builder.insertField(FieldType.FIELD_HYPERLINK, false);

        field.setAddress(ADDRESS);

        msAssert.areEqual(ADDRESS, field.getAddress());
    }

    @Test
    public void testEncodeDoubleQuotesInFieldArgument()
    {
        String encoded = FieldCodeUpdater.encodeArgument("\"«“„»”\"", false);

        msAssert.areEqual("\\\"\\«\\“\\„\\»\\”\\\"", encoded);
    }
}

