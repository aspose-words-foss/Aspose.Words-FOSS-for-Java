// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2017 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.Debug;


/// <summary>
/// Tests DocumentCleaner feature.
/// </summary>
@Test
public class TestDocumentCleaner
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @BeforeMethod (alwaysRun = true)
    public void init()
    {
        RandomUtil.reset();
    }



    /// <summary>
    /// Tests that Click-and-Type and default table styles are preserved
    /// in a document after removing unused resources.
    /// </summary>
    @Test
    public void testPreservingStylesAfterRemovingUnusedResources() throws Exception
    {
        Document doc = new Document();

        Style style = doc.getStyles().add(StyleType.PARAGRAPH, "MyClickAndType");
        style.setBaseStyleName("Heading 3");
        doc.getDocPr().ClickTypeParaStyleIstd = style.getIstd();

        style = doc.getStyles().add(StyleType.PARAGRAPH, "MyTable");
        style.setBaseStyleName("Heading 4");
        doc.getDocPr().DefaultTableStyleIstd = style.getIstd();

        doc.cleanup();

        msAssert.isNotNull(doc.getStyles().getByName("MyClickAndType", false));
        msAssert.isNotNull(doc.getStyles().getByName("MyTable", false));
    }


    // FOSS: TestStyleIstdNormalization (WORDSNET-19345) removed. Its inputs were binary .doc files
    // authored with non-contiguous (missing) style ISTDs, which is the precondition the test asserts
    // (Debug.Assert(MaxIstd < oldMaxIstd)). The .doc reader is removed; the Word .doc->.docx conversion
    // renumbers styles contiguously, eliminating the ISTD gaps, so the scenario cannot be reproduced.


    /// <summary>
    /// WORDSNET-20094 Implemented ability (optional) to remove duplicate styles.
    /// </summary>
    @Test
    public void testRemovingDuplicateStyles() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestRemovingDuplicateStyles.docx");
        StyleCollection styles = doc.getStyles();
        Paragraph para3 = doc.getFirstSection().getBody().getParagraphs().get(2);
        Paragraph para4 = doc.getFirstSection().getBody().getParagraphs().get(3);
        Debug.assertTrue(styles.getCount() == 12);
        Debug.assertTrue("Style3".equals(para3.getParagraphStyle().getName()));
        Debug.assertTrue("Style4".equals(para4.getParagraphStyle().getName()));

        CleanupOptions options = new CleanupOptions();
        options.setUnusedStyles(false);
        options.setUnusedLists(false);
        options.setDuplicateStyle(true);

        doc.cleanup(options);

        msAssert.areEqual(8, styles.getCount());
        msAssert.isNull(styles.get("Style3"));
        msAssert.isNull(styles.get("Style4"));
        msAssert.areEqual("Style1", para3.getParagraphStyle().getName());
        msAssert.areEqual("Style2", para4.getParagraphStyle().getName());
    }


    /// <summary>
    /// WORDSNET-19312 Tests removing unused and duplicate styles in the customer's document.
    /// </summary>
    @Test
    public void test19312() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test19312.docx");
        StyleCollection styles = doc.getStyles();
        StyleCollection glossaryStyles = doc.getGlossaryDocument().getStyles();

        Debug.assertTrue(styles.getCount() == 58);
        Debug.assertTrue(glossaryStyles.getCount() == 2502);

        CleanupOptions options = new CleanupOptions();
        options.setDuplicateStyle(true);

        doc.cleanup(options);

        msAssert.areEqual(52, styles.getCount());
        msAssert.areEqual(7, glossaryStyles.getCount());
    }

    /// <summary>
    /// WORDSNET-20094 Tests removing duplicate table styles.
    /// </summary>
    @Test
    public void testRemovingDuplicateTableStyles() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\TestComparingTableStyles.docx");
        StyleCollection styles = doc.getStyles();
        Table table2 = doc.getFirstSection().getBody().getTables().get(1);
        Debug.assertTrue(styles.getCount() == 8);
        Debug.assertTrue("TableStyle2".equals(table2.getStyleName()));

        CleanupOptions options = new CleanupOptions();
        options.setUnusedStyles(false);
        options.setUnusedLists(false);
        options.setDuplicateStyle(true);

        doc.cleanup(options);

        msAssert.areEqual(7, styles.getCount());
        msAssert.isNull(styles.get("TableStyle2"));
        msAssert.areEqual("TableStyle1", table2.getStyle().getName());
    }

    /// <summary>
    /// WORDSNET-21597 Unused styles are not cleaned from DOCX.
    /// Added public property UnusedBuiltinStyles.
    /// </summary>
    @Test
    public void test21977() throws Exception
    {
        Document doc = TestUtil.open("Model\\Style\\Test21977.docx");

        msAssert.areEqual(64, doc.getStyles().getCount());

        CleanupOptions cleanupOptions = new CleanupOptions();
        cleanupOptions.setDuplicateStyle(true);
        cleanupOptions.setUnusedStyles(true);
        cleanupOptions.setUnusedLists(true);
        cleanupOptions.setUnusedBuiltinStyles(true);
        doc.cleanup(cleanupOptions);

        msAssert.areEqual(9, doc.getStyles().getCount());
    }
}

