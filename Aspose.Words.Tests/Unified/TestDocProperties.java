// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import com.aspose.SystemPal;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.DateTimeUtil;
import java.util.HashMap;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.FileSystem;
import com.aspose.ms.System.IO.Stream;
import com.aspose.PropertySet;
import com.aspose.PropertySetSection;
import com.aspose.Property;
import com.aspose.DocSummaryInfoProperty;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for built-in and custom document properties.
/// RK When reading RTF, MS Word seems to use current culture.
/// Therefore we need to switch to the culture in which the document was created.
/// </summary>
@Test
public class TestDocProperties extends UnifiedTestsBase
{
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDocProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();  // This is really need for RTF only, but okay lets keep it for all.

            Document doc = TestUtil.openSaveOpen("Model\\Property\\TestDocProperties", lf, sf);

            // This property is from SummaryInfo
            msAssert.areEqual("Test Author", (String)doc.getBuiltInDocumentProperties().get("Author").getValue());
            // This property is from DocSummaryInfo
            msAssert.areEqual("Test Company", (String)doc.getBuiltInDocumentProperties().get("Company").getValue());
            // This property is from the user defined properties.
            msAssert.areEqual("http://www.example.com/", (String)doc.getBuiltInDocumentProperties().get("HyperlinkBase").getValue());
            // Unicode chars
            msAssert.areEqual("КУКУ", doc.getBuiltInDocumentProperties().get("Title").toString());

            // Test various types of custom properties
            msAssert.areEqual(7, doc.getCustomDocumentProperties().getCount());
            msAssert.areEqual("Test String", doc.getCustomDocumentProperties().get("MyString").toString());
            msAssert.areEqual(123, doc.getCustomDocumentProperties().get("MyInt").toInt());
            msAssert.areEqual(123.45, doc.getCustomDocumentProperties().get("MyDouble").toDouble());
            // RK The delta here is needed only for RTF and WordML. Strange, but MS files have a difference.
            msAssert.areEqual(3.26954133990695E-302, 1.0E-315, doc.getCustomDocumentProperties().get("MyDoubleSmall").toDouble());
            msAssert.areEqual(true, doc.getCustomDocumentProperties().get("MyBool").toBool());

            DocumentProperty prop = doc.getCustomDocumentProperties().get("MyDate");
            DateTime d = prop.toDateTimeInternal();
            msAssert.areEqual(new DateTime(2003, 1, 31, 12, 0, 0), d);
            msAssert.areEqual("", prop.getLinkTarget());

            prop = doc.getCustomDocumentProperties().get("MyLinked");
            msAssert.areEqual("Hello", (String)prop.getValue());
            msAssert.areEqual("bmk1", prop.getLinkTarget());
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDocPropertiesSourceDataProvider")
	public void unifiedTestDocPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDocProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDocPropertiesSourceDataProvider")
	Object[][] unifiedTestDocPropertiesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// A customer complained that Comments property does not work.
    ///
    /// WORDSNET-3503 WordML import cannot deal with custom property names that contain Russian or Unicode characters.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDocPropertiesModify(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();  // This is really need for RTF only, but okay lets keep it for all.

            Document doc = TestUtil.open("Model\\Property\\TestDocProperties", lf);

            // Set some built int properties.
            msAssert.areEqual("Test Comments", (String)doc.getBuiltInDocumentProperties().get("Comments").getValue());
            doc.getBuiltInDocumentProperties().setTotalEditingTime(12);
            doc.getBuiltInDocumentProperties().get("Comments").setValue("New Comments");
            doc.getBuiltInDocumentProperties().get("Manager").setValue("New Manager");
            doc.getBuiltInDocumentProperties().setTemplate("AAA.dot");
            doc.getBuiltInDocumentProperties().setHyperlinkBase("1234");

            // Try adding custom properties.
            doc.getCustomDocumentProperties().add("Лозунг 1_&.\u0001<\\>\r", "Слава\r\n КПСС \"\'&<\\>\u0002");
            doc.getCustomDocumentProperties().add("AddedProperty2", 321);

            // Try deleting a custom property.
            msAssert.areEqual(true, doc.getCustomDocumentProperties().contains("MyBool"));
            doc.getCustomDocumentProperties().removeAt(doc.getCustomDocumentProperties().indexOf("MyBool"));
            msAssert.areEqual(false, doc.getCustomDocumentProperties().contains("MyBool"));

            doc = TestUtil.saveOpen(doc, "Model\\Property\\TestDocProperties Modified", lf, sf);

            // Check built in properties.
            msAssert.areEqual(12, doc.getBuiltInDocumentProperties().getTotalEditingTime());
            msAssert.areEqual("New Comments", (String)doc.getBuiltInDocumentProperties().get("Comments").getValue());
            msAssert.areEqual("New Manager", doc.getBuiltInDocumentProperties().get("Manager").toString());
            msAssert.areEqual("1234", doc.getBuiltInDocumentProperties().getHyperlinkBase());


            // Template name is not stored in WordML and RTF.
            if ((sf != SaveFormat.WORD_ML) && (sf != SaveFormat.RTF))
                msAssert.areEqual("AAA.dot", doc.getBuiltInDocumentProperties().getTemplate());

            // Check custom properties. 7 existing + 2 new properties now there - 1 deleted.
            msAssert.areEqual(8, doc.getCustomDocumentProperties().getCount());
            msAssert.areEqual(321, doc.getCustomDocumentProperties().get("AddedProperty2").toInt());
            switch (TestUtil.getUnifiedScenario(lf, sf))
            {
                case UnifiedScenario.DOC_2_DOC:
                case UnifiedScenario.DOC_2_RTF:
                case UnifiedScenario.RTF_2_RTF_NO_GOLD:
                    // All bytes are preserved.
                    msAssert.areEqual("Слава\r\n КПСС \"\'&<\\>\u0002", doc.getCustomDocumentProperties().get("Лозунг 1_&.\u0001<\\>\r").toString());
                    break;
                case UnifiedScenario.DOC_2_DOCX:
                case UnifiedScenario.DOCX_2_DOCX_NO_GOLD:
                    // \x0001 etc chars are stripped out in value and in name because they are written as XML text.
                    msAssert.areEqual("Слава\n КПСС \"\'&<\\>_x0002_", doc.getCustomDocumentProperties().get("Лозунг 1_&.<\\>\r").toString());
                    break;
                case UnifiedScenario.DOC_2_WML:
                case UnifiedScenario.WML_2_WML_NO_GOLD:
                    // \x0001 etc chars are stripped out in value only because it is written as XML text.
                    msAssert.areEqual("Слава\r\n КПСС \"\'&<\\>", doc.getCustomDocumentProperties().get("Лозунг 1_&.\u0001<\\>\r").toString());
                    break;
                default:
                    throw new IllegalStateException("Unknown file format.");
            }
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDocPropertiesModifySourceDataProvider")
	public void unifiedTestDocPropertiesModifySource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDocPropertiesModify(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDocPropertiesModifySourceDataProvider")
	Object[][] unifiedTestDocPropertiesModifySourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Tests that when document is cloned, the properties are cloned okay.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestClone(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();  // This is really need for RTF only, but okay lets keep it for all.

            Document srcDoc = TestUtil.open("Model\\Property\\TestDocProperties", lf);
            // Check the original property is there.
            DateTime d = srcDoc.getCustomDocumentProperties().get("MyDate").toDateTimeInternal();
            msAssert.areEqual(new DateTime(2003, 1, 31, 12, 0, 0), d);

            Document dstDoc = srcDoc.deepClone();
            // Check its copied.
            d = dstDoc.getCustomDocumentProperties().get("MyDate").toDateTimeInternal();
            msAssert.areEqual(new DateTime(2003, 1, 31, 12, 0, 0), d);
            dstDoc.getCustomDocumentProperties().get("MyDate").fromDateTime(new DateTime(2004, 6, 18));

            // Check the original is still original.
            d = srcDoc.getCustomDocumentProperties().get("MyDate").toDateTimeInternal();
            msAssert.areEqual(new DateTime(2003, 1, 31, 12, 0, 0), d);
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCloneSourceDataProvider")
	public void unifiedTestCloneSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestClone(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCloneSourceDataProvider")
	Object[][] unifiedTestCloneSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Tests alternate property names. New name first
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAlternateNamesNewFirst(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Property\\TestDocProperties", lf);

        BuiltInDocumentProperties props = doc.getBuiltInDocumentProperties();
        msAssert.areEqual(props.get("NameOfApplication"), props.get("Application Name"));
        msAssert.areEqual(props.get("LastSavedBy"), props.get("Last Author"));
        msAssert.areEqual(props.get("CreateTime"), props.get("Creation Date"));
        msAssert.areEqual(props.get("LastPrinted"), props.get("Last Print Date"));
        msAssert.areEqual(props.get("LastSavedTime"), props.get("Last Save Time"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlternateNamesNewFirstSourceDataProvider")
	public void unifiedTestAlternateNamesNewFirstSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlternateNamesNewFirst(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlternateNamesNewFirstSourceDataProvider")
	Object[][] unifiedTestAlternateNamesNewFirstSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// Tests alternate property names. Old name first
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestAlternateNamesOldFirst(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Property\\TestDocProperties", lf);

        BuiltInDocumentProperties props = doc.getBuiltInDocumentProperties();
        msAssert.areEqual(props.get("Application Name"), props.get("NameOfApplication"));
        msAssert.areEqual(props.get("Last Author"), props.get("LastSavedBy"));
        msAssert.areEqual(props.get("Creation Date"), props.get("CreateTime"));
        msAssert.areEqual(props.get("Last Print Date"), props.get("LastPrinted"));
        msAssert.areEqual(props.get("Last Save Time"), props.get("LastSavedTime"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestAlternateNamesOldFirstSourceDataProvider")
	public void unifiedTestAlternateNamesOldFirstSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestAlternateNamesOldFirst(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestAlternateNamesOldFirstSourceDataProvider")
	Object[][] unifiedTestAlternateNamesOldFirstSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// WORDSNET-395 Custom document properties are not saved.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCustomPropertyLost(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        doc.getCustomDocumentProperties().add("DocumentID", "12345");

        doc = TestUtil.saveOpen(doc, "Model\\Property\\TestCustomPropertyLost", lf, sf);
        msAssert.areEqual("12345", (String)doc.getCustomDocumentProperties().get("DocumentID").getValue());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCustomPropertyLostSourceDataProvider")
	public void unifiedTestCustomPropertyLostSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCustomPropertyLost(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCustomPropertyLostSourceDataProvider")
	Object[][] unifiedTestCustomPropertyLostSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    /// <summary>
    /// RK It is very strange, but looks a bug in MS Word 2007 SP2. If you convert this DOC to
    /// DOCX or WML using MS Word 2007 SP2, the variables will be screwed! You can check that in Visual Basic window.
    /// But Word 2007 without SP2 seems to convert them okay. So I have these old ms files and added them to SVN.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDocVariables(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Property\\TestDocVariables", lf, sf);
        msAssert.areEqual(2, doc.getVariables().getCount());
        msAssert.areEqual("AAA", doc.getVariables().get("V1"));
        msAssert.areEqual("AAA", doc.getVariables().get("v1"));
        msAssert.areEqual("123", doc.getVariables().get("V2"));
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDocVariablesSourceDataProvider")
	public void unifiedTestDocVariablesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDocVariables(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDocVariablesSourceDataProvider")
	Object[][] unifiedTestDocVariablesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}

    @Test
    public void testDefaults() throws Exception
    {
        Document doc = new Document();
        checkDefaults(doc);
        doc = TestUtil.saveOpen(doc, "Model\\Property\\TestDefaults.docx");
        checkDefaults(doc);
    }

    private static void checkDefaults(Document doc)
    {
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getAuthor());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getCategory());
        msAssert.areEqual(0, doc.getBuiltInDocumentProperties().getCharacters());
        msAssert.areEqual(0, doc.getBuiltInDocumentProperties().getCharactersWithSpaces());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getComments());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getCompany());
        msAssert.areEqual(DateTime.MinValue, doc.getBuiltInDocumentProperties().getCreatedTimeInternal());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getHyperlinkBase());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getKeywords());
        msAssert.areEqual(DateTime.MinValue, doc.getBuiltInDocumentProperties().getLastPrintedInternal());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getLastSavedBy());
        msAssert.areEqual(DateTime.MinValue, doc.getBuiltInDocumentProperties().getLastSavedTimeInternal());
        msAssert.areEqual(1, doc.getBuiltInDocumentProperties().getLines());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getManager());
        msAssert.areEqual(1, doc.getBuiltInDocumentProperties().getPages());
        msAssert.areEqual(1, doc.getBuiltInDocumentProperties().getParagraphs());
        msAssert.areEqual(1, doc.getBuiltInDocumentProperties().getRevisionNumber());
        msAssert.areEqual(DocumentSecurity.NONE, doc.getBuiltInDocumentProperties().getSecurity());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getSubject());
        msAssert.areEqual("Normal.dot", doc.getBuiltInDocumentProperties().getTemplate());
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getTitle());
        msAssert.areEqual(0, doc.getBuiltInDocumentProperties().getTotalEditingTime());
        msAssert.areEqual(786432, doc.getBuiltInDocumentProperties().getVersion());
        msAssert.areEqual(0, doc.getBuiltInDocumentProperties().getWords());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void testSetBuiltInToNull() throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual("", doc.getBuiltInDocumentProperties().getAuthor());
        doc.getBuiltInDocumentProperties().setAuthor(null);
    }

    /// <summary>
    /// Setting null string is not allowed.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void testAddNullValue() throws Exception
    {
        Document doc = new Document();
        doc.getCustomDocumentProperties().add("test", (String)null); // Need to cast to disambiguate for Java.
    }

    /// <summary>
    /// Setting empty string is allowed.
    /// </summary>
    @Test
    public void testAddEmptyValue() throws Exception
    {
        Document doc = new Document();
        doc.getCustomDocumentProperties().add("test", "");
        msAssert.areEqual("", (String)doc.getCustomDocumentProperties().get("TEST").getValue());
    }

    /// <summary>
    /// Adding property with null name is not allowed.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testAddNullName() throws Exception
    {
        Document doc = new Document();
        doc.getCustomDocumentProperties().add(null, "test");
    }

    /// <summary>
    /// Adding property with empty name is not allowed.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testAddEmptyName() throws Exception
    {
        Document doc = new Document();
        doc.getCustomDocumentProperties().add("", "test");
    }




    private static void checkHeadingPairs(Document doc, String[] goldTitlesOfParts, Object[] goldHeadingPairs)
    {
        String[] titlesOfParts = doc.getBuiltInDocumentProperties().getTitlesOfParts();
        msAssert.areEqual(goldTitlesOfParts.length, titlesOfParts.length);
        for (int i = 0; i < titlesOfParts.length; i++)
            msAssert.areEqual(goldTitlesOfParts[i], titlesOfParts[i]);

        Object[] headingPairs = doc.getBuiltInDocumentProperties().getHeadingPairs();
        msAssert.areEqual(goldHeadingPairs.length, headingPairs.length);
        for (int i = 0; i < headingPairs.length; i++)
            msAssert.areEqual(goldHeadingPairs[i], headingPairs[i]);
    }

    /// <summary>
    /// WORDSNET-6815 White spaces at the beginning and at the end are missed from DocumentProperty value.
    /// Avoided trimming of UserDefined properties.
    /// All other properties will be trimmed. this is needed to make all gold ExportImport tests work.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestWhiteSpaces(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Property\\TestWhiteSpaces", lf, sf);

        msAssert.areEqual(" Test White Spaces", (String)doc.getCustomDocumentProperties().get("WhiteSpaceBefore").getValue());
        msAssert.areEqual("Test White Spaces ", (String)doc.getCustomDocumentProperties().get("WhiteSpaceAfter").getValue());
        msAssert.areEqual(" Test White Spaces ", (String)doc.getCustomDocumentProperties().get("WhiteSpaceBeforeAndAfter").getValue());

        msAssert.areEqual("alexey", doc.getBuiltInDocumentProperties().getAuthor());
        msAssert.areEqual("Aspose", doc.getBuiltInDocumentProperties().getCompany());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestWhiteSpacesSourceDataProvider")
	public void unifiedTestWhiteSpacesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestWhiteSpaces(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestWhiteSpacesSourceDataProvider")
	Object[][] unifiedTestWhiteSpacesSourceDataProvider() throws Exception
	{
		ArrayList testCaseSources = new ArrayList();
		Iterator it = msArray.iterableArray(DEFAULT_TEST_SCENARIOS).iterator();
		while (it.hasNext())
		{
			Object arg = it.next();
			TestCaseData testCaseData = arg instanceof TestCaseData? (TestCaseData) arg : new TestCaseData(arg);
			Object[] arguments = testCaseData.getArguments();
			testCaseSources.add(arguments);
		}
		return (Object[][]) msArrayList.toArray(testCaseSources, Object[].class);
	}



    /// <summary>
    /// Test Jira 3721  Standard behavior of .NET collection is to throw exception on Add operation
    /// if value being added already exists.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testJira3721Regression() throws Exception
    {
        Document doc = new Document();

        doc.getCustomDocumentProperties().add("Boobi", "InitialValue");
        msAssert.areEqual("InitialValue", doc.getCustomDocumentProperties().get("Boobi").toString());

        // ArgumentException is thrown here.
        doc.getCustomDocumentProperties().add("Boobi", "OtherValue");
    }

    /// <summary>
    /// WORDSNET-14499 Document endnote/footnote options were not saved into Docx and Wml
    /// if there is no endnotes/footnotes.
    /// </summary>
    @Test (dataProvider = "testFootnoteOptionsDataProvider")
    public void testFootnoteOptions(/*UnifiedScenario*/int scenario) throws Exception
    {
        Document doc = new Document();

        doc.getEndnoteOptions().setPosition(EndnotePosition.END_OF_SECTION);
        doc.getEndnoteOptions().setNumberStyle(NumberStyle.CHICAGO_MANUAL);
        doc.getEndnoteOptions().setRestartRule(FootnoteNumberingRule.RESTART_SECTION);
        doc.getEndnoteOptions().setStartNumber(2);

        doc.getFootnoteOptions().setPosition(FootnotePosition.BENEATH_TEXT);
        doc.getFootnoteOptions().setNumberStyle(NumberStyle.UPPERCASE_ROMAN);
        doc.getFootnoteOptions().setRestartRule(FootnoteNumberingRule.RESTART_PAGE);
        doc.getFootnoteOptions().setStartNumber(3);

        doc = TestUtil.saveOpen(doc, "ExportDocx\\TestFootnoteOptions", scenario | UnifiedScenario.NO_GOLD);

        msAssert.areEqual(EndnotePosition.END_OF_SECTION, doc.getEndnoteOptions().getPosition());
        msAssert.areEqual(NumberStyle.CHICAGO_MANUAL, doc.getEndnoteOptions().getNumberStyle());
        msAssert.areEqual(FootnoteNumberingRule.RESTART_SECTION, doc.getEndnoteOptions().getRestartRule());
        msAssert.areEqual(2, doc.getEndnoteOptions().getStartNumber());

        msAssert.areEqual(FootnotePosition.BENEATH_TEXT, doc.getFootnoteOptions().getPosition());
        msAssert.areEqual(NumberStyle.UPPERCASE_ROMAN, doc.getFootnoteOptions().getNumberStyle());
        msAssert.areEqual(FootnoteNumberingRule.RESTART_PAGE, doc.getFootnoteOptions().getRestartRule());
        msAssert.areEqual(3, doc.getFootnoteOptions().getStartNumber());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testFootnoteOptionsDataProvider")
	public static Object[][] testFootnoteOptionsDataProvider() throws Exception
	{
		return new Object[][]
		{
			{UnifiedScenario.DOCX_2_DOCX},
		};
	}

    /// <summary>
    /// WORDSNET-19103 Add feature to link CustomDocumentProperty to Bookmark.
    /// <see cref="CustomDocumentProperties.AddLinkToContent"/> was implemented to get
    /// ability to add such linked to content properties.
    /// <see cref="DocumentProperty.LinkSource"/> and <see cref="DocumentProperty.IsLinkToContent"/> were
    /// implemented as well.
    /// </summary>
    @Test
    public void test19103() throws Exception
    {
        // Source document contains a bookmark.
        Document doc = TestUtil.open("Model\\Property\\Test19103.docx");

        CustomDocumentProperties customProperties = doc.getCustomDocumentProperties();

        // Add linked to content property
        DocumentProperty property = customProperties.addLinkToContent("pr1", "B1");

        msAssert.areEqual("pr1", property.getName());
        msAssert.areEqual("B1", property.getLinkSource());
        msAssert.isTrue(property.isLinkToContent());

        // Verify no text from field code or textbox appeared.
        msAssert.areEqual("dddd\rtxt8/28/2019\r", property.getValue());

        // Try to create link for invalid bookmark.
        msAssert.areEqual(1, customProperties.getCount());
        DocumentProperty invalidProperty = customProperties.addLinkToContent("pr2", "nonexistentBookmark");
        msAssert.isNull(invalidProperty);
        msAssert.areEqual(1, customProperties.getCount());

        // Verify roundtrip.
        String fileName = TestUtil.buildOutFileName(TestUtil.buildTestFileName("Model\\Property\\Test19103.docx"), "", SaveFormat.DOCX);
        doc = TestUtil.saveOpen(doc, fileName, null, false);

        customProperties = doc.getCustomDocumentProperties();
        msAssert.areEqual(1, customProperties.getCount());
        property = customProperties.get(0);

        msAssert.areEqual("pr1", property.getName());
        msAssert.areEqual("B1", property.getLinkSource());
        msAssert.isTrue(property.isLinkToContent());
        msAssert.areEqual("dddd\rtxt8/28/2019\r", property.getValue());
    }

    /// <summary>
    /// WORDSNET-19776 PrintDate field is not updated after save to PDF
    /// <see cref="SaveOptions.UpdateLastPrintedProperty"/> was implemented to get
    /// ability to update that property during save to PDF and XPS formats.
    /// </summary>
    @Test (dataProvider = "test19776DataProvider")
    public void test19776(/*SaveFormat*/int saveFormat, boolean shouldUpdateProperty) throws Exception
    {
        Document doc = new Document();

        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
        {
            DateTime dateBeforeSave = doc.getBuiltInDocumentProperties().getLastPrintedInternal();
            doc.save(ms, saveFormat);
            DateTime dateAfterSave = doc.getBuiltInDocumentProperties().getLastPrintedInternal();
            msAssert.areNotEqual(shouldUpdateProperty, dateAfterSave.equals(dateBeforeSave));
        }
        finally { if (ms != null) ms.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test19776DataProvider")
	public static Object[][] test19776DataProvider() throws Exception
	{
		return new Object[][]
		{
			{SaveFormat.DOCX,  false},
		};
	}

    /// <summary>
    /// WORDSNET-11848 Add a saveoptions either to mimics MS Word behavior or not for created, modified and printed dates
    /// A new <see cref="SaveOptions.UpdateCreatedTimeProperty"/> was implemented to get
    /// ability to update that property on save.
    /// </summary>
    @Test (dataProvider = "test11848DataProvider")
    public void test11848(boolean shouldUpdateProperty) throws Exception
    {
        Document doc = new Document();

        // For test mode we need date not equal to UnitTestingDateTime
        DateTime dateBeforeSave = DateTimeUtil.getNow().addDays(-1);
        doc.getBuiltInDocumentProperties().setCreatedTimeInternal(dateBeforeSave);

        SaveOptions so = SaveOptions.createSaveOptions(SaveFormat.DOCX);
        so.setUpdateCreatedTimeProperty(shouldUpdateProperty);

        MemoryStream ms = new MemoryStream();
        try /*JAVA: was using*/
        {
            doc.save(ms, so);
            msAssert.areNotEqual(shouldUpdateProperty, dateBeforeSave.equals(doc.getBuiltInDocumentProperties().getCreatedTimeInternal()));
        }
        finally { if (ms != null) ms.close(); }
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test11848DataProvider")
	public static Object[][] test11848DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    /// <summary>
    /// WORDSNET-22135 Line break is removed from Variable value.
    /// Fixed by encoding the correct writing of the Line Break character for the 'w:docVar' element.
    /// </summary>
    @Test
    public void test22135() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Property\\Test22135", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual("Value with\u000bline break", doc.getVariables().get(0));
    }

    private static void assertLinkedDocumentProperties(Document doc, /*SaveFormat*/int sf)
    {
        HashMap<String, String> nameToLinkTargetMap = new HashMap<String, String>();
        msDictionary.add(nameToLinkTargetMap, "Document number", "Number");
        msDictionary.add(nameToLinkTargetMap, "RevisionInfo", "Version");
        msDictionary.add(nameToLinkTargetMap, "lifeCycleState", "State");
        msDictionary.add(nameToLinkTargetMap, "test", "testBM");
        msDictionary.add(nameToLinkTargetMap, "wtname", "Name");

        for (DocumentProperty property : (Iterable<DocumentProperty>) doc.getCustomDocumentProperties())
        {
            if (!property.isLinkToContent())
                continue;

            String propName = property.getName();
            msAssert.areEqual(nameToLinkTargetMap.get(propName), property.getLinkTarget(), "Linked target value is wrong for property: " + propName);

            if (property.getType() != PropertyType.STRING)
            {
                msAssert.areEqual(sf == SaveFormat.WORD_ML ? PropertyType.NUMBER : PropertyType.DOUBLE, property.getType(), "Property type mismatch: " + propName);
            }
        }
    }


    /// <summary>
    /// Tests reading properties values. All properties were set to false.
    /// </summary>
    /// <remarks>
    /// Seems only DOC/DOCX document formats support these properties.
    /// </remarks>
    @Test (dataProvider = "test27154ADataProvider")
    public void test27154A(/*LoadFormat*/int lf) throws Exception
    {
        Document doc = TestUtil.open("Model\\Property\\Test27154A", lf);
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getScaleCrop());
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getSharedDocument());
        msAssert.isFalse(doc.getBuiltInDocumentProperties().getHyperlinksChanged());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test27154ADataProvider")
	public static Object[][] test27154ADataProvider() throws Exception
	{
		return new Object[][]
		{
			{LoadFormat.DOCX},
		};
	}

    private static void testMetadata(String fileName) throws Exception
    {
        FileSystem fs = new FileSystem(fileName);
        Stream srcStream = ms.as(fs.getRoot().get("\u0005DocumentSummaryInformation"), Stream.class);
        PropertySet ps = new PropertySet(srcStream);

        MemoryStream dstStream = new MemoryStream();
        ps.save(dstStream);
        fs.getRoot().set("\u0005DocumentSummaryInformation", dstStream);
        PropertySetSection section = ps.getSections().get(0);

        Property scaleCrop = section.getProperties().getById(DocSummaryInfoProperty.SCALE_CROP);
        msAssert.isNotNull(scaleCrop);
        msAssert.areEqual(false, (/*boolean*/Boolean)scaleCrop.getValue());
    }

}

