// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2015 by Nikolay Eremin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import org.testng.annotations.DataProvider;


/// <summary>
/// Basic node functionality tests.
/// </summary>
@Test
public class TestFootnote
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// WORDSNET-11107 Support Custom Mark for FootNote
    ///
    /// FootNote can contain ReferenceMark more than 1 char.
    /// </summary>
    @Test
    public void testFootnoteCustomMark() throws Exception
    {
        String customMark = "test123456";
        String text = "testfootnote";
        String fileName = "Model\\Footnote\\TestDefect11107";

        Document doc = new Document();

        Footnote footnote = new Footnote(doc, FootnoteType.FOOTNOTE);
        msAssert.isTrue(footnote.isAuto());
        footnote.setReferenceMark(customMark);
        msAssert.isFalse(footnote.isAuto());
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(footnote);

        Paragraph pa = new Paragraph(doc);
        footnote.getParagraphs().add(pa);
        pa.getRuns().add(new Run(doc, customMark));
        pa.getRuns().add(new Run(doc, " "));
        pa.getRuns().add(new Run(doc, text));

        String footnoteId = footnote.getNodeId();
        String texttoCheck = customMark + " " + text;
        // FOSS: doc/odt/wml/rtf writers removed; keep the Docx custom-footnote-mark roundtrip.
        testDefect11107SaveAndCheck(doc, fileName + ".docx", footnoteId, customMark, texttoCheck);
    }



    private void testDefect11107SaveAndCheck(Document doc, String fileName, String nodeId, String referenceMark, String text) throws Exception
    {
        WarningInfoCollection warnings = new WarningInfoCollection();
        doc.setWarningCallback(warnings);

        Document resavedDoc = TestUtil.saveOpen(doc, fileName, null, false);

        if (resavedDoc.getOriginalLoadFormat() == LoadFormat.RTF)
            msAssert.isTrue(TestUtil.containsWarning(warnings, WarningType.DATA_LOSS, WarningSource.RTF,
                    "RTF format can only store 1 symbol as custom reference mark, other symbols will be truncated."));

        Footnote fn = ms.as(resavedDoc.getNodeById(nodeId), Footnote.class);
        msAssert.isNotNull(fn);
        msAssert.areEqual(referenceMark, fn.getReferenceMark());
        msAssert.areEqual(text + "\r", fn.getFirstParagraph().getText());
    }


    /// <summary>
    /// WORDSNET-13982 Now an exception is thrown, if a document contains several references to the same
    /// footnote/endnote, like MS Word does.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    public void testTwoReferencesToFootnote() throws Exception
    {
        LoadOptions loadOptions = new LoadOptions();
        loadOptions.setRecoveryMode(DocumentRecoveryMode.NONE);
        TestUtil.open("Model\\Footnote\\TestTwoReferencesToFootnote.docx", loadOptions);
    }



    /// <summary>
    /// Additional test related to WORDSNET-22954
    /// </summary>
    // FOSS: Doc reader removed; dropped the three .doc cases (the assertion is format-agnostic).
    @Test (enabled = false)
    public void test22954A(String testName) throws Exception
    {
        Document doc = TestUtil.open(testName);
        msAssert.isTrue(doc.getFootnoteSeparators().isDefault());
    }

    /// <summary>
    /// WORDSNET-24128 InvalidOperationException on DOCX roundtrip.
    /// AW checks that separator has default formatting to make decision whether to write.
    /// Formatting revision attribute has not default value, so use final attribute version to fix the issue.
    /// </summary>
    @Test
    public void test24128() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Footnote\\Test24128", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.isFalse(doc.getFootnoteSeparators().getByFootnoteSeparatorType(FootnoteSeparatorType.FOOTNOTE_SEPARATOR).isDefault());
    }

    /// <summary>
    /// Tests the <see cref="Footnote.ActualReferenceMark"/> property.
    /// </summary>
    @Test (dataProvider = "testActualReferenceMarksDataProvider")
    public void testActualReferenceMarks(String fileName, String[] expectedMarks) throws Exception
    {
        Document doc = TestUtil.open("Model\\Footnote\\" + fileName);
        ArrayList<Footnote> footnotes = doc.getChildNodes(NodeType.FOOTNOTE, true).<Footnote>toList();

        // ReferenceMark properties are not populated yet.
        msAssert.areEqual("", footnotes.get(0).getReferenceMark());

        // Populate actual reference marks.
        doc.updateActualReferenceMarks();

        msAssert.areEqual(expectedMarks.length, footnotes.size());
        for (int i = 0; i < footnotes.size(); i++)
            msAssert.areEqual(expectedMarks[i], footnotes.get(i).getActualReferenceMark());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testActualReferenceMarksDataProvider")
	public static Object[][] testActualReferenceMarksDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestActualReferenceMarks.docx",  new String[]{"1", "xi", "xii", "1", "2", "6"}},
			{"TestActualReferenceMarksWithRevisions.docx",  new String[] { "1", "2", "3", "4" }},
		};
	}

    /// <summary>
    /// WORDSNET-28560 Opening of a corrupted document.
    /// Tests a new public API for load document in recovery mode.
    /// </summary>
    @Test
    public void test28560() throws Exception
    {
        final String FOOTNOTE_TEXT = "\u0002 Canais avulsos sem promoção os valores serão aplicados de " +
                                    "acordo com os Termos e Condições de Uso do Plano. \r";

        WarningInfoCollection warnings = new WarningInfoCollection();
        LoadOptions lo = new LoadOptions();
        lo.setRecoveryMode(DocumentRecoveryMode.TRY_RECOVER);
        lo.setWarningCallback(warnings);

        Document doc = TestUtil.open("Model\\Footnote\\Test28560.docx", lo);

        msAssert.isTrue(TestUtil.containsWarning(warnings, WarningType.DATA_LOSS, WarningSource.DOCX,
                msString.format(WarningStrings.FOOTNOTE_WITH_SEVERAL_REFERENCES, 5)));

        Style footnoteReferenceStyle = doc.getStyles().getByName("Footnote Reference", false);
        NodeCollection footnotes = doc.getChildNodes(NodeType.FOOTNOTE, true);

        msAssert.areEqual(FOOTNOTE_TEXT, footnotes.get(3).getText());
        msAssert.areEqual(footnoteReferenceStyle.getIstd(), ((Footnote)footnotes.get(3)).getRunPr().getIstd());
        msAssert.areEqual("", footnotes.get(4).getText());
        msAssert.areEqual(footnoteReferenceStyle.getIstd(), ((Footnote)footnotes.get(4)).getRunPr().getIstd());
        msAssert.areEqual("", footnotes.get(5).getText());
        msAssert.areEqual(footnoteReferenceStyle.getIstd(), ((Footnote)footnotes.get(5)).getRunPr().getIstd());
    }

    /// <summary>
    /// Relates to WORDSNET-28560.
    /// Checks DocumentRecoveryMode.None behavior for the recovery mode.
    /// </summary>
    @Test (expectedExceptions = FileCorruptedException.class)
    public void test28560None() throws Exception
    {
        LoadOptions lo = new LoadOptions();
        lo.setRecoveryMode(DocumentRecoveryMode.NONE);

        // Default value LoadOptions.RecoveryMode is equal to DocumentRecoveryMode.None.
        // FileCorruptedException is raised here.
        TestUtil.open("Model\\Footnote\\Test28560.docx", lo);
    }
}

