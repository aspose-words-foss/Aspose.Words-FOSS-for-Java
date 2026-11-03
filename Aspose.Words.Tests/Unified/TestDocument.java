// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.FileNotFoundException;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.File;


/// <summary>
/// Test document-wide features.
/// </summary>
@Test
public class TestDocument
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test (expectedExceptions = FileNotFoundException.class)
    public void testOpenFileNotFound() throws Exception
    {
        new Document("C:\\Dummy.doc");
    }


    // FOSS: TestAttachedTemplate was removed. It read attached-template metadata from .doc
    // inputs and round-tripped through .doc, all removed in FOSS. The AttachedTemplate
    // get/set + OOXML round-trip is still covered by TestJira2177.


    /// <summary>
    /// WORDSNET-2177 Consider adding “Automatically update document styles” option.
    /// andrnosk: Document.LinkStyles option is exposed to public API.
    /// The styles in the document will be updated to match the styles in the
    /// attached template each time the document is opened using MSWord.
    /// </summary>
    @Test
    public void testJira2177() throws Exception
    {
        final String TEMPLATE_NAME = "C:\\MyPath\\MyTemplate.dot";
        Document doc = new Document();
        doc.setAttachedTemplate(TEMPLATE_NAME);

        msAssert.isFalse(doc.getAutomaticallyUpdateStyles());
        doc.setAutomaticallyUpdateStyles(true);

        doc = TestUtil.saveOpen(doc, "Model\\Document\\TestJira2177", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.areEqual(TEMPLATE_NAME, doc.getAttachedTemplate());
        msAssert.isTrue(doc.getAutomaticallyUpdateStyles());
    }

    private static void checkTemplateDoc(
        String srcTemplateName,
        String dstTemplateName,
        String dstDocumentName,
        /*LoadFormat*/int templateFormatExpected,
        /*LoadFormat*/int documentFormatExpected) throws Exception
    {
        Document doc = TestUtil.open("Model\\Document\\" + srcTemplateName);
        msAssert.areEqual(templateFormatExpected, doc.getOriginalLoadFormat());

        // Test save as a template.
        // Open in MS Word 97 and try Save As. Check saving to other types is disabled there.
        doc = TestUtil.saveOpen(doc, "Model\\Document\\" + dstTemplateName);
        msAssert.areEqual(templateFormatExpected, doc.getOriginalLoadFormat());

        // Test save as a normal document.
        // Open in MS Word 97 and try Save As. Check saving to other types is enabled there.
        doc = TestUtil.saveOpen(doc, "Model\\Document\\" + dstDocumentName);
        msAssert.areEqual(documentFormatExpected, doc.getOriginalLoadFormat());
    }

    @Test
    public void testCloneSectionAndContents() throws Exception
    {
        Document doc = new Document();

        Section section = new Section(doc);
        section.getPageSetup().setSectionStart(SectionStart.ODD_PAGE);

        Body body = new Body(doc);
        section.appendChild(body);

        Paragraph para = new Paragraph(doc);
        para.getParagraphFormat().setLeftIndent(10.0);
        body.appendChild(para);

        Run run = new Run(doc, "Hello");
        run.getFont().setBold(true);
        para.appendChild(run);

        Section newSection = (Section)section.deepClone(true);

        //Verify it was deep cloned.
        msAssert.isTrue(section.getSectPr() != newSection.getSectPr());    //Reference is not the same.
        msAssert.areEqual(SectionStart.ODD_PAGE, newSection.getPageSetup().getSectionStart());    //But data is copied.

        Paragraph newPara = newSection.getBody().getFirstParagraph();
        msAssert.isTrue(newPara.getParaPr() != para.getParaPr());
        msAssert.areEqual(10d, newPara.getParagraphFormat().getLeftIndent());

        Run newRun = (Run)newPara.getChildNodes(NodeType.ANY, false).get(0);
        msAssert.areEqual(newPara, newRun.getParentNode(), "Parent is not set properly.");
        msAssert.isTrue(newRun.getRunPr() != run.getRunPr());
        msAssert.areEqual("Hello", newRun.getText());
        msAssert.areEqual(true, newRun.getFont().getBold());
    }

    @Test
    public void testCloneFontInfo() throws Exception
    {
        Document srcDoc = new Document();
        msAssert.areEqual(3, srcDoc.getFontInfos().getCount());

        Document dstDoc = srcDoc.deepClone();
        dstDoc.getFontInfos().merge(new FontInfo("TestFont"));
        //Check that FontInfo was copied from the original
        msAssert.areEqual(4, dstDoc.getFontInfos().getCount());

        //Check that it was actually copied.
        msAssert.areEqual(3, srcDoc.getFontInfos().getCount());
    }

    /// <summary>
    /// When cloning a document, the event handlers of the new document should be null.
    /// </summary>
    @Test
    public void testCloneWithHandlers() throws Exception
    {
        Document srcDoc = new Document();
        HandleNodeInserted handler = new HandleNodeInserted();
        srcDoc.setNodeChangingCallback(handler);

        Document dstDoc = srcDoc.deepClone();

        msAssert.areEqual(0, handler.getEventCount());
        srcDoc.appendChild(new Section(srcDoc));
        msAssert.areEqual(1, handler.getEventCount());

        dstDoc.appendChild(new Section(dstDoc));
        //Only original node should have raised the event, but not the copied node.
        msAssert.areEqual(1, handler.getEventCount());
    }

    private static class HandleNodeInserted implements INodeChangingCallback
    {
        public void /*INodeChangingCallback.*/nodeInserting(NodeChangingArgs e)
        {
            // Do nothing.
        }

        public void /*INodeChangingCallback.*/nodeInserted(NodeChangingArgs e)
        {
            setEventCount(getEventCount() + 1)/*++Property*/;
        }

        public void /*INodeChangingCallback.*/nodeRemoving(NodeChangingArgs e)
        {
            // Do nothing.
        }

        public void /*INodeChangingCallback.*/nodeRemoved(NodeChangingArgs e)
        {
            // Do nothing.
        }

        public int getEventCount() { return mEventCount; }; private void setEventCount(int value) { mEventCount = value; };

        private int mEventCount;
    }

    /// <summary>
    /// WORDSNET-612 Throws "Attempted to copy a by reference attribute '16'.
    /// </summary>
    @Test
    public void testCloneAfterSave() throws Exception
    {
        Document doc = new Document();
        MemoryStream stream = new MemoryStream();
        doc.save(stream, SaveFormat.DOCX);   // FOSS: was SaveFormat.Doc; format is incidental to the clone-after-save check.

        //This used to throw because writing injected attributes that were not possible to clone.
        Document newDoc = doc.deepClone();
        msAssert.isNotNull(newDoc);
    }

    /// <summary>
    /// Make sure the stream is not closed after writing the document.
    /// </summary>
    @Test
    public void testStreamNotClosed() throws Exception
    {
        // FOSS: only OOXML, Text and Markdown writers survive — Doc/Rtf/Odt/Html/WordML/Mhtml were removed.
        doTestStreamNotClosed(SaveFormat.TEXT);
        doTestStreamNotClosed(SaveFormat.DOCX);
        doTestStreamNotClosed(SaveFormat.DOCM);
        doTestStreamNotClosed(SaveFormat.MARKDOWN);
    }

    private static void doTestStreamNotClosed(/*SaveFormat*/int saveFormat) throws Exception
    {
        Document doc = new Document();
        MemoryStream stream = new MemoryStream();
        doc.save(stream, saveFormat);

        // Make sure the position is at the end of the stream.
        msAssert.areEqual(stream.getPosition(), stream.getLength());

        // Make sure the stream is not closed by trying to move by the stream.
        stream.setPosition(0);
        msAssert.areEqual(0L, stream.getPosition());
    }

    @Test
    public void testBlank() throws Exception
    {
        Document doc = new Document();
        msAssert.areEqual("\f", doc.getText());
        TestUtil.save(doc, "Model\\Document\\TestBlank.docx");   // FOSS: was .doc.
    }

    /// <summary>
    /// A customer reported that saving and opening via memory stream does not work.
    /// </summary>
    @Test
    public void testOpenSaveByteArray() throws Exception
    {
        Document srcDoc = new Document();

        MemoryStream streamOut = new MemoryStream();
        srcDoc.save(streamOut, SaveFormat.DOCX);   // FOSS: was SaveFormat.Doc.

        byte[] docBytes = streamOut.toArray();
        MemoryStream streamIn = new MemoryStream(docBytes);

        Document dstDoc = new Document(streamIn);

        msAssert.areEqual("\f", dstDoc.getText());
    }


    @Test
    public void testSaveWithoutExtensionWithOptions() throws Exception
    {
        String fileNameA = TestUtil.getInTestOutPath("Model\\Document\\TestSaveWithoutExtensionA Out");
        String fileNameB = TestUtil.getInTestOutPath("Model\\Document\\TestSaveWithoutExtensionB Out.");

        File.delete(fileNameA);
        File.delete(fileNameB);

        Document doc = new Document();
        doc.save(fileNameA, new OoxmlSaveOptions());
        doc.save(fileNameB, new OoxmlSaveOptions());

        msAssert.isTrue(File.exists(fileNameA));
        msAssert.isTrue(File.exists(fileNameB));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QInvalid save format requested.\\E")
    public void testSaveWithoutExtensionWithoutOptions() throws Exception
    {
        Document doc = new Document();
        doc.save(TestUtil.getInTestOutPath("Model\\Document\\TestSaveWithoutExtensionAuto Out"));
    }






    /// <summary>
    /// WORDSNET-16234 FontInfoCollection.EmbedSystemFonts default value is incorrect.
    /// The problem is resource file blank.doc has this property set to true.
    /// Should reset it to false after blank.doc is loaded upon creating new document.
    /// </summary>
    @Test
    public void testJira16234() throws Exception
    {
        Document doc = new Document();

        msAssert.isFalse(doc.getFontInfos().getEmbedSystemFonts());
        msAssert.isFalse(doc.getDocPr().EmbedSystemFonts);
    }

    /// <summary>
    /// WORDSNET-13875 Spell check disabled after document save.
    /// Checks that appropriate DocPr members are set when public properties are used.
    /// </summary>
    @Test
    public void test13875() throws Exception
    {
        Document doc = new Document();

        // Checking ShowGrammaticalErrors.
        doc.getDocPr().ProofStateGrammar = com.aspose.words.ProofState.DIRTY;
        msAssert.isTrue(doc.getGrammarChecked());
        doc.setGrammarChecked(false);
        msAssert.areEqual(doc.getDocPr().ProofStateGrammar, com.aspose.words.ProofState.NONE);

        // Checking ShowSpellingErrors.
        doc.getDocPr().ProofStateSpelling = com.aspose.words.ProofState.DIRTY;
        msAssert.isTrue(doc.getSpellingChecked());
        doc.setSpellingChecked(false);
        msAssert.areEqual(doc.getDocPr().ProofStateSpelling, com.aspose.words.ProofState.NONE);
    }



    // FOSS: Test28870 was removed. CompObj is a binary-.doc OLE structure and the test
    // both loads and saves .doc, all removed in FOSS.
}

