// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.DateTime;
import java.util.ArrayList;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.SingleThreadTestGroup;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.TestEnvironment;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.IO.File;
import com.aspose.SystemPal;
import org.testng.annotations.DataProvider;


/// <summary>
/// Tests how the document information fields work.
/// </summary>
@Test
public class TestDocumentInformation extends TestFieldsBase
{
    /// <summary>
    /// Test how the DOCPROPERTY field is updated.
    /// </summary>
    @Test
    public void testDocProperty() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Fields\\DocumentInformation\\TestDocProperty.docx");

        doc.getCustomDocumentProperties().get("propS").fromString("aaa");
        doc.getCustomDocumentProperties().get("propInt").fromInt(999);
        doc.getCustomDocumentProperties().get("prop Bool").fromBool(false);
        doc.getCustomDocumentProperties().get("propDate").fromDateTime(new DateTime(2005, 6, 1));
        doc.getBuiltInDocumentProperties().get("Author").fromString("New Author");
        doc.getBuiltInDocumentProperties().get("Title").fromString("New Title");

        doc.updateFields();
        doc = TestUtil.saveOpen(doc, "Fields\\DocumentInformation\\TestDocProperty Modified.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("aaa", fields.get(0).getResult());
        msAssert.areEqual("999", fields.get(1).getResult());
        msAssert.areEqual("N", fields.get(2).getResult());
        msAssert.areEqual("1/06/2005", fields.get(3).getResult());
        msAssert.areEqual("New Author", fields.get(4).getResult());
        msAssert.areEqual("New Title", fields.get(5).getResult());
    }

    /// <summary>
    /// WORDSNET-602 Throws when setting result of a DOCPROPERTY field that does not have a separator.
    /// </summary>
    @Test
    public void testDocPropertyNoSeparator() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Fields\\DocumentInformation\\TestDocPropertyNoSeparator.docx");

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.isNotNull(fields.get(0).getSeparator());
        msAssert.areEqual("", fields.get(0).getResult());
        fields.get(0).setResult("test");
        msAssert.isNotNull(fields.get(0).getSeparator());
        msAssert.areEqual("test", fields.get(0).getResult());
    }



    /// <summary>
    /// WORDSNET-4290 DOCPROPERTY fields related to DateTime are not updated correctly.
    /// </summary>
    /// <remarks>
    /// It is unable to test weather value of the date/time document property is converted from UTC to
    /// local time value during forming of field result, since test mode timezone is always UTC + 0.
    /// So we rely on DateTimeUtil.ToLocalTime implementation out of test mode.
    /// </remarks>
    @Test (groups = SingleThreadTestGroup.SINGLE_THREAD)
    public void testJira4290() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestJira4290.docx");

        try
        {
            // Common date format.
            // Common time format.
            testJira4290Case(
                "d/MM/yyyy",
                "H:mm:ss",
                ":",
                doc,
                new String[] { "8/06/2012 16:51", "08/06/2012 16:51:00", "17/06/2010", "17/06/2010 00:00:00" });

            // Date format with "yy" year format. It must be converted to "yyyy".
            // Time format with short hour. PM values are not distinguished from AM.
            testJira4290Case(
                "d/MM/yy",
                "h:mm:ss",
                ":",
                doc,
                new String[] { "8/06/2012 4:51", "08/06/2012 04:51:00", "17/06/2010", "17/06/2010 00:00:00" });

            // Date format with year designator enclosed by single quotes. Enclosed year designator must not be converted.
            // Time format with hour designator enclosed by single quotes. Not enclosed hour designator must be used.
            String[] expectedResults = { "8/06/2012y. 16:51", "08/06/2012 16:51:00", "17/06/2010y.", "17/06/2010 00:00:00" };
            testJira4290Case(
                "d/MM/yyyy'y.'",
                "'h: 'HH' m: 'mm",
                " m: ",
                doc,
                expectedResults);

            // Date format with "y" year format. It must be converted to "yy".
            // Time format without hour designator. "H" must be used by default.
            testJira4290Case(
                "d/MM/y",
                "mm:ss",
                ":",
                doc,
                new String[] { "8/06/12 16:51", "08/06/2012 16:51:00", "17/06/10", "17/06/2010 00:00:00" });

            // Date format with inversed date part order.
            // Time format with one character separator other than ":". Specified separator must be used.
            testJira4290Case(
                "yyyy-MM-dd",
                "hh.mm.ss",
                ".",
                doc,
                new String[] { "2012-06-08 04.51", "08/06/2012 04:51:00", "2010-06-17", "17/06/2010 00:00:00" });

            // Date format with day and month replaced by each other.
            // Time format with more than one character separator. ":" must be used by default.
            testJira4290Case(
                "M/dd/yyyy",
                "hh--mm--ss",
                "--",
                doc,
                new String[] { "6/08/2012 04:51", "08/06/2012 04:51:00", "6/17/2010", "17/06/2010 00:00:00" });

            // Common date format.
            // Time format without time separator. ":" must be used by default.
            testJira4290Case(
                "d/MM/yyyy",
                "hhmmss",
                "",
                doc,
                new String[] { "8/06/2012 04:51", "08/06/2012 04:51:00", "17/06/2010", "17/06/2010 00:00:00" });

            // Common date format.
            // Time format with short minute. "mm" must be used anyway.
            testJira4290Case(
                "d/MM/yyyy",
                "h:m:s",
                ":",
                doc,
                new String[] { "8/06/2012 4:51", "08/06/2012 04:51:00", "17/06/2010", "17/06/2010 00:00:00" });

            // Common date format.
            // Time format without minute designator. "mm" must be used anyway.
            testJira4290Case(
                "d/MM/yyyy",
                "h:s",
                ":",
                doc,
                new String[] { "8/06/2012 4:51", "08/06/2012 04:51:00", "17/06/2010", "17/06/2010 00:00:00" });
        }
        finally
        {
            FormatterPal.setInitialCurrentDateTimeFormatInfo();
        }
    }

    /// <summary>
    /// Performs Jira 4290 single case testing.
    /// </summary>
    /// <param name="shortDatePattern">case short date pattern</param>
    /// <param name="longTimePattern">case long time pattern</param>
    /// <param name="timeSeparator">case time separator</param>
    /// <param name="doc">document object</param>
    /// <param name="expectedResults">four-item array of expected field results</param>
    private static void testJira4290Case(
        String shortDatePattern,
        String longTimePattern,
        String timeSeparator,
        Document doc,
        String[] expectedResults) throws Exception
    {
        try
        {
            FormatterPal.setCurrentNativeShortDatePattern(shortDatePattern);
            FormatterPal.setCurrentNativeLongTimePattern(longTimePattern);
            FormatterPal.setCurrentTimeSeparator(timeSeparator);

            doc.updateFields();

            ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
            int index = 0;

            for (Field field : fields)
            {
                String message = msString.format(
                    "Date format: {0}; time format: {1}; field code: {2}.",
                    shortDatePattern,
                    longTimePattern,
                    field.getFieldCode());

                msAssert.areEqual(expectedResults[index++], field.getResult(), message);
            }
        }
        finally
        {
            FormatterPal.setInitialCurrentDateTimeFormatInfo();
        }
    }

    /// <summary>
    /// Test how the AUTHOR field is updated.
    /// </summary>
    @Test
    public void testAuthor() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestAuthor.docx");
        msAssert.areEqual("Dmitry", doc.getBuiltInDocumentProperties().getAuthor());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("Dmitry", fields.get(0).getResult());
        msAssert.areEqual("Dmitry", doc.getBuiltInDocumentProperties().getAuthor());

        // The second field updates the value and displays it.
        fields.get(1).update();
        msAssert.areEqual("New Author", fields.get(1).getResult());
        msAssert.areEqual("New Author", doc.getBuiltInDocumentProperties().getAuthor());

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("New Author", fields.get(0).getResult());
    }

    /// <summary>
    /// Test how the COMMENTS field is updated.
    /// </summary>
    @Test
    public void testComments() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestComments.docx");
        msAssert.areEqual("My comments", doc.getBuiltInDocumentProperties().getComments());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("My comments", fields.get(0).getResult());
        msAssert.areEqual("My comments", doc.getBuiltInDocumentProperties().getComments());

        // The second field updates the value and displays it.
        fields.get(1).update();
        msAssert.areEqual("New comments", fields.get(1).getResult());
        msAssert.areEqual("New comments", doc.getBuiltInDocumentProperties().getComments());

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("New comments", fields.get(0).getResult());
    }

    /// <summary>
    /// Test how the KEYWORDS field is updated.
    /// </summary>
    @Test
    public void testKeywords() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestKeywords.docx");
        msAssert.areEqual("My keywords", doc.getBuiltInDocumentProperties().getKeywords());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("My keywords", fields.get(0).getResult());
        msAssert.areEqual("My keywords", doc.getBuiltInDocumentProperties().getKeywords());

        // The second field updates the value and displays it.
        fields.get(1).update();
        msAssert.areEqual("New keywords", fields.get(1).getResult());
        msAssert.areEqual("New keywords", doc.getBuiltInDocumentProperties().getKeywords());

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("New keywords", fields.get(0).getResult());
    }

    /// <summary>
    /// Test how the SUBJECT field is updated.
    /// </summary>
    @Test
    public void testSubject() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestSubject.docx");
        msAssert.areEqual("My subject", doc.getBuiltInDocumentProperties().getSubject());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("My subject", fields.get(0).getResult());
        msAssert.areEqual("My subject", doc.getBuiltInDocumentProperties().getSubject());

        // The second field updates the value and displays it.
        fields.get(1).update();
        msAssert.areEqual("New subject", fields.get(1).getResult());
        msAssert.areEqual("New subject", doc.getBuiltInDocumentProperties().getSubject());

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("New subject", fields.get(0).getResult());
    }

    /// <summary>
    /// Test how the TITLE field is updated.
    /// </summary>
    @Test
    public void testTitle() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestTitle.docx");
        msAssert.areEqual("My Title", doc.getBuiltInDocumentProperties().getTitle());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("My Title", fields.get(0).getResult());
        msAssert.areEqual("My Title", doc.getBuiltInDocumentProperties().getTitle());

        // The second field updates the value and displays it.
        fields.get(1).update();
        msAssert.areEqual("New Title", fields.get(1).getResult());
        msAssert.areEqual("New Title", doc.getBuiltInDocumentProperties().getTitle());

        // The first field displays current property value.
        fields.get(0).update();
        msAssert.areEqual("New Title", fields.get(0).getResult());
    }

    /// <summary>
    /// Test how the LASTSAVEDBY field is updated.
    /// </summary>
    @Test
    public void testLastSavedBy() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestLastSavedBy.docx");
        msAssert.areEqual("Dmitry", doc.getBuiltInDocumentProperties().getLastSavedBy());

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        fields.get(0).update();
        msAssert.areEqual("Dmitry", fields.get(0).getResult());

        doc.getBuiltInDocumentProperties().setLastSavedBy("Someone else");
        fields.get(0).update();
        msAssert.areEqual("Someone else", fields.get(0).getResult());
    }




    /// <summary>
    /// Test how the FILENAME field is updated.
    /// </summary>
    @Test
    public void testFileName() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestFileName.docx");
        msAssert.areEqual(TestEnvironment.getTestData("Fields\\DocumentInformation\\TestFileName.docx"), doc.getOriginalFileName());

        doc.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        msAssert.areEqual("TestFileName.docx", fields.get(0).getResult());
        msAssert.areEqual(TestEnvironment.getTestData("Fields\\DocumentInformation\\TestFileName.docx"), fields.get(1).getResult());
    }

    /// <summary>
    /// Test how the FILENAME field is updated when the document is created blank.
    /// </summary>
    @Test
    public void testFileNameMissing() throws Exception
    {
        Document doc = new Document();
        msAssert.isNull(doc.getOriginalFileName());

        DocumentBuilder builder = new DocumentBuilder(doc);
        FieldFileName field = (FieldFileName)builder.insertField("FILENAME");
        msAssert.isEmpty(field.getResult());
    }

    /// <summary>
    /// Test how the FILESIZE field is updated.
    /// </summary>
    @Test
    public void testFileSize() throws Exception
    {
        Document doc = TestUtil.open("Fields\\DocumentInformation\\TestFileSize.docx");
        doc.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(doc);
        // File size in bytes.
        msAssert.areEqual("27392", fields.get(0).getResult());
        // File size in kilobytes.
        msAssert.areEqual("27", fields.get(1).getResult());
        // File size in megabytes.
        msAssert.areEqual("0", fields.get(2).getResult());
    }

    /// <summary>
    /// Test how the FILESIZE field is updated when the document is created blank.
    /// </summary>
    @Test
    public void testFileSizeMissing() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        FieldFileSize field = (FieldFileSize)builder.insertField("FILESIZE");
        msAssert.areEqual("0", field.getResult());
    }


    /// <summary>
    /// When you update field "AUTHOR" value should be taken from the option "DefaultDocumentAuthor"
    /// </summary>
    @Test
    public void whenFieldUpdateAuthorNameShouldBeTakenFromOptionTest() throws Exception
    {
        final String AUTHOR_NAME = "John Smith";

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        Field authorField = builder.insertField("AUTHOR \\* MERGEFORMAT");

        doc.getFieldOptions().setDefaultDocumentAuthor(AUTHOR_NAME);
        doc.updateFields();

        msAssert.areEqual(AUTHOR_NAME, authorField.getResult());
    }

    /// <summary>
    /// When you update field "AUTHOR" value should not be taken from the option "DefaultDocumentAuthor", if builtin document property "Author" exist
    /// </summary>
    @Test
    public void whenFieldUpdateAuthorNameShouldNotBeTakenFromOptionIfExistDocumentPropertyTest() throws Exception
    {
        final String AUTHOR_NAME_FROM_OPTION = "John Smith";
        final String AUTHOR_NAME_FROM_BUILT_IN_PROPERTY = "authorNameFromBuiltInProperty";

        Document doc = new Document();
        doc.getBuiltInDocumentProperties().setAuthor(AUTHOR_NAME_FROM_BUILT_IN_PROPERTY);
        DocumentBuilder builder = new DocumentBuilder(doc);
        Field authorField = builder.insertField("AUTHOR \\* MERGEFORMAT");

        doc.getFieldOptions().setDefaultDocumentAuthor(AUTHOR_NAME_FROM_OPTION);
        doc.updateFields();

        msAssert.areEqual(AUTHOR_NAME_FROM_BUILT_IN_PROPERTY, authorField.getResult());
    }


    private static final String EN_CA_AM = "AM";
    private static final String EN_CA_PM = "PM";
    private static final String EN_GB_AM = "AM";
    private static final String EN_GB_PM = "PM";



    /// <summary>
    /// WORDSNET-14678 When DOCX is saved as PDF by using a FileStream or MemoryStream, then the output document is missing some elements.
    /// </summary>
    @Test
    public void testJira14678() throws Exception
    {
        Document document;
        Stream stream = File.openRead(TestUtil.buildTestFileName("Fields\\DocumentInformation\\TestFileName.docx"));
        try /*JAVA: was using*/
    	{
            document = new Document(stream);
    	}
        finally { if (stream != null) stream.close(); }

        final String FILE_NAME = "TestJira14678.docx";
        String fullPath = TestEnvironment.getUserHome() + FILE_NAME;
        document.getFieldOptions().setFileName(fullPath);

        document.updateFields();

        ArrayList<Field> fields = FieldExtractor.extractToCollection(document, true);
        msAssert.areEqual(FILE_NAME, fields.get(0).getResult());
        msAssert.areEqual(fullPath, fields.get(1).getResult());
    }

    /// <summary>
    /// Tests the priority order of <see cref="Document.OriginalFileName"/> and <see cref="FieldOptions.FileName"/>.
    /// </summary>
    @Test
    public void testFileNameFieldOptionPriority() throws Exception
    {
        Document document = TestUtil.open("Fields\\DocumentInformation\\TestFileName.docx");

        final String FILE_NAME = "Blah blah blah.docx";
        String fullPath = TestEnvironment.getUserHome() + FILE_NAME;
        document.getFieldOptions().setFileName(fullPath);

        document.updateFields();
        ArrayList<Field> fields = FieldExtractor.extractToCollection(document, true);
        msAssert.areEqual(FILE_NAME, fields.get(0).getResult());
        msAssert.areEqual(fullPath, fields.get(1).getResult());
    }

    /// <summary>
    /// WORDSNET-15318 Document.UpdateFields changes decimal symbol for Finnish culture.
    /// </summary>
    @Test (dataProvider = "testJira15318DataProvider")
    public void testJira15318(String cultureName, String format, String expectedResult) throws Exception
    {
        SystemPal.setCulture(cultureName);

        DocumentBuilder builder = new DocumentBuilder();
        builder.getDocument().getCustomDocumentProperties().add("TestProperty", 123.4);
        Field field = builder.insertField(msString.format("DOCPROPERTY TestProperty \\# {0}", format), null);

        field.update();

        msAssert.areEqual(expectedResult, field.getResult());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira15318DataProvider")
	public static Object[][] testJira15318DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"en-US",  "0.00",  "123.40"},
			{"fi-FI",  "0,00",  "123,40"},
		};
	}
}

