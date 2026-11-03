// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/08/2022 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.Path;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Class for testing issues related to DocumentMerger.
/// </summary>
public class TestDocumentMerger
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
    /// Tests merging with an empty list of source documents.
    /// </summary>
    @Test
    public void testEmpty() throws Exception
    {
        Document doc = new Document();
        doc.mergeDocuments();

        msAssert.areEqual("\f", doc.getText());
    }












    /// <summary>
    /// WORDSNET-26045 BiDi text is shown incorrectly in Header after Merge DOCX.
    /// Update SDT content BiDi aware in <see cref="Aspose.Words.Markup.SdtContentHelper.InsertInlineNode"/>
    /// that is used in <see cref="DocumentMerger.UpdateCorePropertiesSdts"/>.
    /// </summary>
    @Test
    public void test26045() throws Exception
    {
        Document mergedDoc = mergeDocs("Test26045Dst.docx", "Test26045Src.docx");
        mergedDoc = TestUtil.saveOpen(mergedDoc, "Model\\DocMerger\\Test26045Merged", UnifiedScenario.DOCX_2_DOCX);

        HeaderFooter hf = mergedDoc.getLastSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        msAssert.areEqual("الفصل الأول:                                                       الأسلوب الانشائي مفهومه وأقسامه\r", hf.getFirstParagraph().getText());
    }


    /// <summary>
    /// WORDSNET-26046 Wrong footnote separator alignment after Merge DOCX.
    /// Do the trick and if there are no footnotes in destination document,
    /// then replace footnote separator formatting with the one from the source document.
    /// </summary>
    @Test
    public void test26046() throws Exception
    {
        Document mergedDoc = mergeDocs("Test26046Dst.docx", "Test26046Src.docx");
        mergedDoc = TestUtil.saveOpen(mergedDoc, "Model\\DocMerger\\Test26046Merged", UnifiedScenario.DOCX_2_DOCX);

        Paragraph para = (Paragraph)mergedDoc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_SEPARATOR).getFirstChild();
        msAssert.areEqual(ParagraphAlignment.RIGHT, para.getParagraphFormat().getAlignment());
    }

    /// <summary>
    /// Relates to WORDSNET-26046.
    /// Checks when destination document has footnote.
    /// </summary>
    @Test
    public void test26046A() throws Exception
    {
        Document mergedDoc = mergeDocs("Test26046DstA.docx", "Test26046SrcA.docx");
        mergedDoc = TestUtil.saveOpen(mergedDoc, "Model\\DocMerger\\Test26046AMerged", UnifiedScenario.DOCX_2_DOCX);

        Paragraph para = (Paragraph)mergedDoc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_SEPARATOR).getFirstChild();
        msAssert.areEqual(ParagraphAlignment.LEFT, para.getParagraphFormat().getAlignment());
    }



    /// <summary>
    /// Relates to WORDSNET-25831.
    /// Tests when destination and source document sections starts page numbering not from 1.
    /// </summary>
    @Test
    public void test25831A() throws Exception
    {
        Document mergedDoc = mergeDocs("Test25831A_1.docx", "Test25831A_2.docx");
        mergedDoc = TestUtil.saveOpen(mergedDoc, "Model\\DocMerger\\Test25831AMerged", UnifiedScenario.DOCX_2_DOCX);

        msAssert.areEqual(3, mergedDoc.getSections().get(0).getPageSetup().getPageStartingNumber());
        msAssert.areEqual(8, mergedDoc.getSections().get(1).getPageSetup().getPageStartingNumber());
        msAssert.isTrue(mergedDoc.getSections().get(1).getPageSetup().getRestartPageNumbering());
    }







    /// <summary>
    /// WORDSNET-26815 MergeDocuments throws exception for empty document.
    /// Need a resilience against completely empty destination document.
    /// </summary>
    @Test
    public void test26815() throws Exception
    {
        Document srcDoc = new Document();
        Document dstDoc = (Document)srcDoc.deepClone(false);

        dstDoc.mergeDocuments(srcDoc);

        msAssert.areEqual("\f", dstDoc.getText());
    }












    /// <summary>
    /// The core helper method to test WORDSNET-28311.
    /// </summary>
    private static void test28311Core(HeaderFooter footer, String expectedText)
    {
        FieldCollection fields = footer.getRange().getFields();
        // There must be only field `Page`. And a second field `NumPages` should be converted to a Run result.
        msAssert.areEqual(1, fields.getCount());
        msAssert.areEqual(FieldType.FIELD_PAGE, fields.get(0).getType());

        Paragraph paragraph = footer.getFirstParagraph();
        msAssert.areEqual(expectedText, paragraph.getText());
    }

    /// <summary>
    /// Returns merged document.
    /// </summary>
    private static Document mergeDocs(String dstTestName, /*params*/ String... srcTestNames) throws Exception
    {
        Document dstDoc = openTestDoc(dstTestName);

        Document[] srcDocs = new Document[srcTestNames.length];
        for (int i = 0; i < srcTestNames.length; i++)
        {
            String srcTestName = srcTestNames[i];
            srcDocs[i] = openTestDoc(srcTestName);
        }

        dstDoc.mergeDocuments(srcDocs);

        return dstDoc;
    }

    /// <summary>
    /// Returns test document with a specified file name.
    /// </summary>
    private static Document openTestDoc(String fileName) throws Exception
    {
        return TestUtil.open(getTestPath(fileName));
    }

    /// <summary>
    /// Returns path relative to 'Model\DocMerger'.
    /// </summary>
    private static String getTestPath(String filename) throws Exception
    {
        return Path.combine("Model\\DocMerger\\", filename);
    }

    /// <summary>
    /// Verifies HeadersFooters against gold.
    /// </summary>
    private static void verifyGoldHfs(HeaderFooterCollection headersFooters, HeaderFooterCollection goldHeadersFooters)
    {
        msAssert.areEqual(goldHeadersFooters.getCount(), headersFooters.getCount());
        for (HeaderFooter hf : (Iterable<HeaderFooter>) headersFooters)
        {
            HeaderFooter goldHf = goldHeadersFooters.getByHeaderFooterType(hf.getHeaderFooterType());
            msAssert.areEqual(goldHf.isLinkedToPrevious(), hf.isLinkedToPrevious());
            msAssert.areEqual(goldHf.getText(), hf.getText());
        }
    }

    /// <summary>
    /// Verifies most common properties of all sections of a specified document against gold document.
    /// </summary>
    private static void verifyGoldSections(Document doc, Document goldDoc)
    {
        msAssert.areEqual(goldDoc.getSections().getCount(), doc.getSections().getCount());

        for (int i = 0; i < doc.getSections().getCount(); i++)
            verifyGoldSection(doc.getSections().get(i), goldDoc.getSections().get(i), msString.format("Section {0}: \n", i));
    }

    /// <summary>
    /// Verifies section most common properties against gold.
    /// </summary>
    private static void verifyGoldSection(Section section, Section goldSection)
	{
		verifyGoldSection(section, goldSection, "");
	}

    /// <summary>
    /// Verifies section most common properties against gold.
    /// </summary>
    private static void verifyGoldSection(Section section,Section goldSection, String msg)
    {
        msAssert.areEqual(goldSection.getBody().getText(), section.getBody().getText());
        verifyGoldHfs(section.getHeadersFooters(), goldSection.getHeadersFooters());
        verifyGoldSectPr(section.getSectPr(), goldSection.getSectPr(), msg);
    }

    /// <summary>
    /// Verifies that specified section properties are equal to specified gold properties,
    /// ignoring difference in global defaults.
    /// </summary>
    private static void verifyGoldSectPr(SectPr sectPr, SectPr goldSectPr)
	{
		verifyGoldSectPr(sectPr, goldSectPr, "");
	}

    /// <summary>
    /// Verifies that specified section properties are equal to specified gold properties,
    /// ignoring difference in global defaults.
    /// </summary>
    private static void verifyGoldSectPr(SectPr sectPr,SectPr goldSectPr, String msg)
    {
        SectPr extraAttrs = AttrCollectionExtension.deepClone(sectPr);
        extraAttrs.removeEquals(goldSectPr);
        extraAttrs.removeGlobalDefaults();

        SectPr missingAttrs = AttrCollectionExtension.deepClone(goldSectPr);
        missingAttrs.removeEquals(sectPr);
        missingAttrs.removeGlobalDefaults();

        StringBuilder sb = null;
        if (extraAttrs.getCount() > 0)
        {
            sb = new StringBuilder();
            msStringBuilder.appendLine(sb, "Extra or changed attributes found in SectPr, which are not exist in Gold:");
            msStringBuilder.appendLine(sb, extraAttrs.toString());
        }

        if (missingAttrs.getCount() > 0)
        {
            if (sb == null)
                sb = new StringBuilder();

            msStringBuilder.appendLine(sb, "Missing or changed attributes found in SectPr, which are exist in Gold:");
            msStringBuilder.appendLine(sb, missingAttrs.toString());
        }

        if (sb != null)
            Assert.fail(msString.format("{0}{1}", msg, sb));
    }

    /// <summary>
    /// Verifies most common properties of all sections of a specified document against gold document.
    /// </summary>
    private static void verifyGoldSections(Document doc) throws Exception
    {
        String testFileName = Path.getFileNameWithoutExtension(doc.getOriginalFileName().replace("Dst", ""));
        String testExt = Path.getExtension(doc.getOriginalFileName());

        String outFileName = getTestPath(msString.format("{0} Out{1}", testFileName, testExt));
        String outPath = TestUtil.getInTestOutPath(outFileName);

        TestUtil.save(doc, outPath);
        Document outDoc = TestUtil.open(outPath);

        String goldFileName = getTestPath(msString.format("{0} Gold{1}", testFileName, testExt));
        Document goldDoc = TestUtil.open(TestUtil.getInTestGoldPath(goldFileName));

        verifyGoldSections(outDoc, goldDoc);
    }
}

