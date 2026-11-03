// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/04/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import com.aspose.SystemPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.DateTime;
import java.util.ArrayList;
import java.util.Iterator;
import com.aspose.ms.NUnit.Framework.TestCaseData;
import com.aspose.ms.System.Collections.msArrayList;
import org.testng.annotations.DataProvider;
import com.aspose.ms.System.msArray;


/// <summary>
/// Unified tests for form fields.
/// </summary>
@Test (groups = "SingleThreadGroup")
public class TestFormFields extends UnifiedTestsBase
{

    /// <summary>
    /// There is a case here when form field bookmark starts not where expected.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormFieldRename(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestFormFieldRename", lf, sf);

        FormFieldCollection formFields = doc.getSections().get(0).getRange().getFormFields();
        msAssert.isTrue(formFields.get("Text1") != null);
        msAssert.isTrue(formFields.get("Text2") != null);
        msAssert.isTrue(formFields.get("Text3") != null);
        msAssert.isTrue(formFields.get("Text4") != null);
        msAssert.isTrue(formFields.get("Text5") != null);
        msAssert.isTrue(formFields.get("Text6") != null);
        msAssert.isTrue(formFields.get("Text7") != null);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormFieldRenameSourceDataProvider")
	public void unifiedTestFormFieldRenameSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormFieldRename(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormFieldRenameSourceDataProvider")
	Object[][] unifiedTestFormFieldRenameSourceDataProvider() throws Exception
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
    /// Text input with multiple paragraphs was not supported.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormFieldMultiParagraph(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestFormFieldMultiParagraph", lf, sf);

        msAssert.areEqual(2, doc.getRange().getFormFields().getCount());
        //Check that loading multiparagraph text works.
        msAssert.areEqual("Line1\rLine2", doc.getRange().getFormFields().get(0).getResult());
        msAssert.areEqual("Line2\rLine3", doc.getRange().getFormFields().get(1).getResult());

        NodeList paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(3, paras.getCount());
        msAssert.areEqual("\u0013 FORMTEXT \u0001\u0014Line1\r", paras.get(0).getText());
        msAssert.areEqual("Line2\u0015 \u0013 FORMTEXT \u0001\u0014Line2\r", paras.get(1).getText());
        msAssert.areEqual("Line3\u0015\f", paras.get(2).getText());

        //Check that when inserting text that contains fewer paragraphs, it actually removes paragraphs from the
        doc.getRange().getFormFields().get(0).setResult("X");
        doc.getRange().getFormFields().get(1).setResult("Y");
        paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(1, paras.getCount());
        msAssert.areEqual("\u0013 FORMTEXT \u0001\u0014X\u0015 \u0013 FORMTEXT \u0001\u0014Y\u0015\f", paras.get(0).getText());

        //Check that inserting text with multiple paragraph works.
        doc.getRange().getFormFields().get(0).setResult("Line1\rLine2");
        paras = doc.selectNodes("//Paragraph");
        msAssert.areEqual(2, paras.getCount());
        msAssert.areEqual("\u0013 FORMTEXT \u0001\u0014Line1\r", paras.get(0).getText());
        msAssert.areEqual("Line2\u0015 \u0013 FORMTEXT \u0001\u0014Y\u0015\f", paras.get(1).getText());

        TestUtil.saveOpen(doc, "Model\\FormField\\TestFormFieldMultiParagraph Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormFieldMultiParagraphSourceDataProvider")
	public void unifiedTestFormFieldMultiParagraphSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormFieldMultiParagraph(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormFieldMultiParagraphSourceDataProvider")
	Object[][] unifiedTestFormFieldMultiParagraphSourceDataProvider() throws Exception
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
    /// Long default text form field value was causing crashes in pre Word 2003.
    /// So I changed that we limit field default value to 255 chars.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormFieldLong(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.insertTextInput(
            "xxx",
            TextFormFieldType.REGULAR,
            "",
            "[begin]" + msString.newString('x', 800) + "[end]",
            2000);

        FormField formField = (FormField)doc.selectSingleNode("//FormField");
        formField.getFormFieldPr().setTextInputDefault("[begin]" + msString.newString('y', 900) + "[end]");

        doc = TestUtil.saveOpen(doc, "Model\\FormField\\TestFormFieldLong", lf, sf);

        formField = (FormField)doc.selectSingleNode("//FormField");
        msAssert.areEqual(812, formField.getResult().length());
        // The default value got truncated.
        msAssert.areEqual(255, formField.getFormFieldPr().getTextInputDefault().length());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormFieldLongSourceDataProvider")
	public void unifiedTestFormFieldLongSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormFieldLong(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormFieldLongSourceDataProvider")
	Object[][] unifiedTestFormFieldLongSourceDataProvider() throws Exception
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
    /// Test various form field properties.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormFieldProperties(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestFormFieldProperties", lf, sf);

        FormField f = doc.getRange().getFormFields().get(0);
        msAssert.areEqual("", f.getStatusText());
        msAssert.areEqual("", f.getHelpText());
        msAssert.areEqual(false, f.getOwnStatus());
        msAssert.areEqual(false, f.getOwnHelp());
        msAssert.areEqual(true, f.getEnabled());
        msAssert.areEqual(false, f.getCalculateOnExit());

        f = doc.getRange().getFormFields().get(1);
        msAssert.areEqual("My own status bar.", f.getStatusText());
        msAssert.areEqual("RK", f.getHelpText());
        msAssert.areEqual(true, f.getOwnStatus());
        msAssert.areEqual(false, f.getOwnHelp());
        msAssert.areEqual(false, f.getEnabled());

        f = doc.getRange().getFormFields().get(2);
        msAssert.areEqual("АВИАПОЧТОЙ", f.getStatusText());
        msAssert.areEqual("Own help.", f.getHelpText());
        msAssert.areEqual(false, f.getOwnStatus());
        msAssert.areEqual(true, f.getOwnHelp());
        msAssert.areEqual(true, f.getCalculateOnExit());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormFieldPropertiesSourceDataProvider")
	public void unifiedTestFormFieldPropertiesSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormFieldProperties(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormFieldPropertiesSourceDataProvider")
	Object[][] unifiedTestFormFieldPropertiesSourceDataProvider() throws Exception
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
    /// Tests support of text input form field formats and types. Text input type controls how formatting is performed.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormat(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            // RK Have to use standard culture because form field result is formatted using current culture.
            SystemPal.setStandardCulture();

            Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestFormat", lf, sf);

            FormField f = doc.getRange().getFormFields().get(0);
            f.setTextInputValue("Hello");
            msAssert.areEqual("HELLO", f.getResult());

            f = doc.getRange().getFormFields().get(1);
            // RK Different casing is written by different MS Word versions.
            msAssert.isTrue(StringUtil.equalsIgnoreCase("Title case", f.getTextInputFormat()));
            msAssert.areEqual(TextFormFieldType.REGULAR, f.getTextInputType());
            f.setTextInputValue("hello hello");
            msAssert.areEqual("Hello Hello", f.getResult());

            f = doc.getRange().getFormFields().get(2);
            msAssert.areEqual("$#,##0.00;($#,##0.00)", f.getTextInputFormat());
            msAssert.areEqual(TextFormFieldType.NUMBER, f.getTextInputType());
            f.setTextInputValue(-1234);
            msAssert.areEqual("($1,234.00)", f.getResult());

            f = doc.getRange().getFormFields().get(3);
            msAssert.areEqual("M/d/yyyy h:mm am/pm", f.getTextInputFormat());
            msAssert.areEqual(TextFormFieldType.DATE, f.getTextInputType());
            f.setTextInputValue(new DateTime(2005, 12, 15, 20, 9, 10));
            msAssert.areEqual("12/15/2005 8:09 pm", f.getResult());
            f = doc.getRange().getFormFields().get(4);
            msAssert.areEqual("0.00%", f.getTextInputFormat());
            msAssert.areEqual(TextFormFieldType.NUMBER, f.getTextInputType());
            f.setTextInputValue(0.175);
            msAssert.areEqual("17.50%", f.getResult());

            f = doc.getRange().getFormFields().get(6);
            msAssert.areEqual("#,##0", f.getTextInputFormat());
            msAssert.areEqual(TextFormFieldType.CALCULATED, f.getTextInputType());
            msAssert.areEqual("=100+1000", f.getTextInputDefault());
            msAssert.areEqual("1,100", f.getResult());
            f.setTextInputDefault("=100 * 100 + 12.5");
            msAssert.areEqual("10,013", f.getResult()); // Cool! Calculated like in MS Word immediately.
            f = doc.getRange().getFormFields().get(7);
            // RK This is a bug in Word 2007. It writes incorrect value when saving to DOCX.
            /*TextFormFieldType*/int inputType = (lf != LoadFormat.DOCX) ? TextFormFieldType.CURRENT_DATE : TextFormFieldType.CURRENT_TIME;
            msAssert.areEqual(inputType, f.getTextInputType());
            msAssert.areEqual("27-Feb-10", f.getResult());

            f = doc.getRange().getFormFields().get(8);
            // RK This is a bug in Word 2007. It writes incorrect value when saving to DOCX.
            inputType = (lf != LoadFormat.DOCX) ? TextFormFieldType.CURRENT_TIME : TextFormFieldType.CURRENT_DATE;
            msAssert.areEqual(inputType, f.getTextInputType());
            msAssert.areEqual("01:29", f.getResult());
            // Check that calculated form fields are indeed updated.
            doc.updateFields();

            f = doc.getRange().getFormFields().get(6);
            msAssert.areEqual("10,013", f.getResult());
            f = doc.getRange().getFormFields().get(7);
            msAssert.areEqual("5-Jan-06", f.getResult());  // This is a hardcoded test constant when updating to current date time.
            f = doc.getRange().getFormFields().get(8);
            msAssert.areEqual("19:09", f.getResult());     // This is a hardcoded test constant when updating to current date time.

            TestUtil.saveOpen(doc, "Model\\FormField\\TestFormat Modified", lf, sf);
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormatSourceDataProvider")
	public void unifiedTestFormatSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormat(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormatSourceDataProvider")
	Object[][] unifiedTestFormatSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDropDown(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestDropDown", lf, sf);

        FormField f = doc.getRange().getFormFields().get(0);
        msAssert.areEqual(2, f.getDropDownItems().getCount());
        msAssert.areEqual(0, f.getDropDownSelectedIndex());
        msAssert.areEqual("AAA", f.getResult());

        f.setDropDownSelectedIndex(f.getDropDownItems().add("CCC"));
        msAssert.areEqual(2, f.getDropDownSelectedIndex());
        msAssert.areEqual("CCC", f.getResult());

        //At the moment deleting/inserting items does not update the selected index.
        f.getDropDownItems().removeAt(2);
        msAssert.areEqual(2, f.getDropDownSelectedIndex());
        msAssert.areEqual("", f.getResult());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDropDownSourceDataProvider")
	public void unifiedTestDropDownSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDropDown(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDropDownSourceDataProvider")
	Object[][] unifiedTestDropDownSourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestCheckBox(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestCheckBox", lf, sf);

        FormField f = doc.getRange().getFormFields().get(0);
        msAssert.areEqual(false, f.getChecked());
        msAssert.areEqual(false, f.isCheckBoxExactSize());
        msAssert.areEqual("0", f.getResult());

        f = doc.getRange().getFormFields().get(1);
        msAssert.areEqual(true, f.getChecked());
        msAssert.areEqual("1", f.getResult());

        f = doc.getRange().getFormFields().get(2);
        msAssert.areEqual(true, f.getChecked());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestCheckBoxSourceDataProvider")
	public void unifiedTestCheckBoxSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestCheckBox(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestCheckBoxSourceDataProvider")
	Object[][] unifiedTestCheckBoxSourceDataProvider() throws Exception
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
    /// User noted that FormField.Remove does not remove the form field.
    /// Indeed it deleted only the FormField node, but did not delete all other nodes
    /// of the form field. As a temp workaround added FormField.Remove, but
    /// maybe should later rework FormField to make it a wrapper, not a node.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRemoveFormField(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestRemoveFormField", lf, sf);
        DocumentBuilder builder = new DocumentBuilder(doc);

        doc.getRange().getFormFields().remove("Text1");
        builder.moveToBookmark("Text1");
        builder.write("John Doe");

        doc = TestUtil.saveOpen(doc, "Model\\FormField\\TestRemoveFormField Modified", lf, sf);
        msAssert.areEqual("John Doe\r\f", doc.getText());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveFormFieldSourceDataProvider")
	public void unifiedTestRemoveFormFieldSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveFormField(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveFormFieldSourceDataProvider")
	Object[][] unifiedTestRemoveFormFieldSourceDataProvider() throws Exception
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
    /// WORDSNET-1014 Aspose.Words does not show formfield name if the enclosing bookmark is deleted.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestRemoveBookmarkFromFormField(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestRemoveBookmarkFromFormField", lf, sf);
        msAssert.isNotNull(doc.getRange().getBookmarks().get("Text1"));

        FormField field = doc.getRange().getFormFields().get(0);
        msAssert.areEqual("Text1", field.getName());

        // Remove the bookmark.
        doc.getRange().getBookmarks().remove("Text1");

        // Check the name of the form field still stays.
        msAssert.areEqual("Text1", field.getName());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestRemoveBookmarkFromFormFieldSourceDataProvider")
	public void unifiedTestRemoveBookmarkFromFormFieldSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestRemoveBookmarkFromFormField(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestRemoveBookmarkFromFormFieldSourceDataProvider")
	Object[][] unifiedTestRemoveBookmarkFromFormFieldSourceDataProvider() throws Exception
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
    /// WORDSNET-3564 Text inserted by FormField.SetTextInputValue does not have an expected formatting.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestDefect3564(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\FormField\\TestDefect3564", lf, sf);
        FormFieldCollection formFields = doc.getFirstSection().getRange().getFormFields();

        FormField formField1 = formFields.get("OSEBA_NAS_POS_SIF_D");
        FormField formField2 = formFields.get("OSEBA_NAS_POS_NAZ_D");
        FormField formField3 = formFields.get("O_DOSEG_MOBI__1");
        FormField formField4 = formFields.get("O_DOSEG_SMS");
        FormField formField5 = formFields.get("SEMINAR_DATUM");

        formField1.setTextInputValue("3000");
        formField2.setTextInputValue("Celje");
        formField3.setTextInputValue("041555555");
        formField4.setTextInputValue("x");
        formField5.setTextInputValue("18.1.2009");

        Run run1 = (Run)formField1.getNextSibling().getNextSibling();
        Run run2 = (Run)formField2.getNextSibling().getNextSibling();
        Run run3 = (Run)formField3.getNextSibling().getNextSibling();
        Run run4 = (Run)formField4.getNextSibling().getNextSibling();
        Run run5 = (Run)formField5.getNextSibling().getNextSibling();

        // Comment these lines to save/show result document.
        msAssert.isTrue(run1.getFont().getBold(), "The run font should be bold.");
        msAssert.isTrue(run2.getFont().getBold(), "The run font should be bold.");
        msAssert.isFalse(run3.getFont().getBold(), "The run font should NOT be bold.");
        msAssert.areEqual("Wingdings", run4.getFont().getName(), "The run font should be 'Wingdings'.");
        msAssert.isTrue(run5.getFont().getBold(), "The run font should be bold.");

        TestUtil.saveOpen(doc, "Model\\FormField\\TestDefect3564 Modified", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestDefect3564SourceDataProvider")
	public void unifiedTestDefect3564Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestDefect3564(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestDefect3564SourceDataProvider")
	Object[][] unifiedTestDefect3564SourceDataProvider() throws Exception
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

    //JAVA: method is internalized
	/*@Test*/
    void unifiedTestFormFieldSettings(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        TestUtil.openSaveOpen("Model\\FormField\\TestFormFieldSettings", lf, sf);
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "unifiedTestFormFieldSettingsSourceDataProvider")
	public void unifiedTestFormFieldSettingsSource(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		unifiedTestFormFieldSettings(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "unifiedTestFormFieldSettingsSourceDataProvider")
	Object[][] unifiedTestFormFieldSettingsSourceDataProvider() throws Exception
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
    /// WORDSNET-6137 “NullReferenceException” occurs during Saving Document.
    /// The error occurred because a form field spans several paragraphs and the last paragraph containing FieldEnd
    /// is deleted by the user. I made it resilient to avoid throwing. But we still write an incomplete field for the time being.
    /// </summary>
    //JAVA: method is internalized
	/*@Test*/
    void testDefect6137(/*LoadFormat*/int lf, /*SaveFormat*/int sf) throws Exception
    {
        Document doc = TestUtil.open("Model\\FormField\\TestDefect6137", lf);
        msAssert.areEqual(1475, doc.getTextLength());
        doc.getFirstSection().getBody().getLastParagraph().remove();

        TestUtil.saveOpen(doc, "Model\\FormField\\TestDefect6137", lf, sf);
        msAssert.areEqual(1423, doc.getTextLength());
    }

	//JAVA-added wrapper for test method with test source
	@Test(dataProvider = "testDefect6137SourceDataProvider")
	public void testDefect6137Source(/*LoadFormat*/int lf,/*SaveFormat*/int sf) throws Exception
	{
		testDefect6137(lf, sf);
	}

	//JAVA-added data provider for test method with test source
	@DataProvider(name = "testDefect6137SourceDataProvider")
	Object[][] testDefect6137SourceDataProvider() throws Exception
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
    /// Test tries to add to DropDown form field more than maximum elements count using DropDownItems.Add(...).
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testDropDownAdd() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        FormField comboBox = builder.insertComboBox("name", new String[] { "0" }, 0);
        final int COUNT = DropDownItemCollection.MAX_ITEMS_COUNT + 1;
        for (int i = 1; i < COUNT; i++)
            comboBox.getDropDownItems().add(Integer.toString(i));
    }

    /// <summary>
    /// Test tries to create DropDown form field based on array with more than maximum elements count.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testDropDownInsert() throws Exception
    {
        final int COUNT = DropDownItemCollection.MAX_ITEMS_COUNT + 1;
        String[] arItem = new String[COUNT];
        for (int i = 0; i < COUNT; i++)
            arItem[i] = Integer.toString(i);
        DocumentBuilder builder = new DocumentBuilder();
        builder.insertComboBox("name", arItem, 0);
    }
}

