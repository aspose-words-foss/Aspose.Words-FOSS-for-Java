// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Unit tests for TxtNumbering class
/// </summary>
@Test
public class TestTxtNumbering
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        mArabicDotNumberingStyle = new ArabicDotTxtNumberingStyle();
        mAsteriskNumberingStyle = new AsteriskTxtNumberingStyle();
    }

    @Test
    public void testIsFirstChildForTrue1()
    {
        TxtNumbering parentNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33" }, "2.33.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33", "1" }, "2.33.1.");

        msAssert.isTrue(numbering.isFirstChildFor(parentNumbering));
    }

    @Test
    public void testIsFirstChildForTrue2()
    {
        TxtNumbering parentNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1" }, "1.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1", "1" }, "1.1.");

        msAssert.isTrue(numbering.isFirstChildFor(parentNumbering));
    }

    @Test
    public void testIsFirstChildForFalse1()
    {
        TxtNumbering parentNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1" }, "1.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1", "2" }, "1.2.");

        msAssert.isFalse(numbering.isFirstChildFor(parentNumbering));
    }

    @Test
    public void testIsFirstChildForFalse2()
    {
        TxtNumbering parentNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1" }, "1.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1", "2", "1" }, "1.2.1.");

        msAssert.isFalse(numbering.isFirstChildFor(parentNumbering));
    }

    @Test
    public void testIsFirstChildForFalse3()
    {
        TxtNumbering parentNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "1" }, "1.");
        TxtNumbering numbering = new TxtNumbering(
            mAsteriskNumberingStyle, new String[] { "*" }, "*");

        msAssert.isFalse(numbering.isFirstChildFor(parentNumbering));
    }

    @Test
    public void testIsPrevSiblingForTrue1()
    {
        TxtNumbering nextNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33" }, "2.33.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "32" }, "2.32.");

        msAssert.isTrue(numbering.isPrevSiblingFor(nextNumbering));
    }

    @Test
    public void testIsPrevSiblingForTrue2()
    {
        TxtNumbering nextNumbering = new TxtNumbering(
            mAsteriskNumberingStyle, new String[] { "*" }, "*");
        TxtNumbering numbering = new TxtNumbering(
            mAsteriskNumberingStyle, new String[] { "*" }, "*");

        msAssert.isTrue(numbering.isPrevSiblingFor(nextNumbering));
    }

    @Test
    public void testIsPrevSiblingForFalse1()
    {
        TxtNumbering nextNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33" }, "2.33.");
        TxtNumbering numbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33" }, "2.33.");

        msAssert.isFalse(numbering.isPrevSiblingFor(nextNumbering));
    }

    @Test
    public void testIsPrevSiblingForFalse2()
    {
        TxtNumbering nextNumbering = new TxtNumbering(
            mArabicDotNumberingStyle, new String[] { "2", "33" }, "2.33.");
        TxtNumbering numbering = new TxtNumbering(
            mAsteriskNumberingStyle, new String[] { "*" }, "*");

        msAssert.isFalse(numbering.isPrevSiblingFor(nextNumbering));
    }

    /// <summary>
    /// WORDSNET-28829 Document.GetText() returns the text without numbered lists.
    /// The issue is 'Not a Bug'. Added the verification test.
    /// </summary>
    @Test (dataProvider = "test28829DataProvider")
    public void test28829(boolean autoNumberingDetection) throws Exception
    {
        TxtLoadOptions loadOptions = new TxtLoadOptions();
        loadOptions.setAutoNumberingDetection(autoNumberingDetection);

        Document doc = TestUtil.open("ImportTxt\\Test28829.txt", loadOptions);

        Paragraph para = TestUtil.getParagraphWithText(doc, "...Boil some water...");
        msAssert.areEqual(autoNumberingDetection, para.isListItem());

        String text = doc.getText();
        // When automatic numbering detection is disabled, list label is just plain text within a Run node.
        String expectedText = msString.format("\r{0}Boil some water.", autoNumberingDetection ? "" : "2. ");
        msAssert.areContainsSubstirng(expectedText, text);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test28829DataProvider")
	public static Object[][] test28829DataProvider() throws Exception
	{
		return new Object[][]
		{
			{true},
			{false},
		};
	}

    private ArabicDotTxtNumberingStyle mArabicDotNumberingStyle;

    private AsteriskTxtNumberingStyle mAsteriskNumberingStyle;
}

