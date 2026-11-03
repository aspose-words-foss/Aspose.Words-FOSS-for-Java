// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/06/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.aspose.RandomUtil;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.awt.Color;
import com.aspose.Language;
import com.aspose.ms.System.DateTime;
import com.aspose.SystemPal;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import com.aspose.HashUtil;
import com.aspose.DigestAlgorithm;
import com.aspose.DrColor;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msObject;
import java.util.ArrayList;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.XmlUtilPal;
import com.aspose.ms.System.Drawing.msColor;
import com.aspose.ms.System.Text.UTF8Encoding;
import com.aspose.ms.System.IO.Path;
import org.testng.annotations.DataProvider;


/// <summary>
/// Test Sdt behavior related to public API and model.
/// </summary>
/// <remarks>
/// 1. Additional tests related to roundtrip of Sdt are placed in the ImportDocx testing area here <see cref="TestImportDocxSdt"/>
/// 2. Tests containing UC abbreviation in summary relate to Approved Use-cases defined in the project wiki,
/// they are marked as UC1:, UC2:, etc.
/// </remarks>
@Test
public class TestSdt
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
    /// WORDSNET-6902 StructuredDocumentTag.SdtType issue with RichText
    /// It seems MS Word 2010 omits writing w:richText element inside SdtPr making it silent default.
    /// </summary>
    @Test
    public void testJira6902() throws Exception
    {
        final String TEST_NAME = "Model\\Markup\\TestJira6902.docx";
        Document doc = TestUtil.open(TEST_NAME);
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
            msAssert.areEqual(SdtType.RICH_TEXT, sdt.getSdtType());
    }

    /// <summary>
    /// UC1: Create an SDT
    /// </summary>
    @Test
    public void testCreateSdt() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();
        Paragraph para = builder.getCurrentParagraph();

        // 1. Ctor.
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.INLINE);
        para.appendChild(sdt);

        BuildingBlock block =
            doc.getGlossaryDocument().getBuildingBlock(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT,
                                                  SdtPlaceholderManager.BUILDING_BLOCK_CATEGORY,
                                                  SdtPlaceholderManager.TEXT_PLACEHOLDER_NAME);
        msAssert.isNotNull(block);
        msAssert.areSame(block, sdt.getPlaceholder());
        msAssert.areEqual(block.getName(), sdt.getPlaceholderName());
        msAssert.areEqual("Click here to enter text.\f", sdt.getPlaceholder().getText());

        TestUtil.saveOpen(doc, "Model\\Markup\\TestCreateSdt.docx");
    }

    /// <summary>
    /// WORDSNET-6168 Cannot properly create a Row level StructuredDocumentTag.
    /// Added extra wrapping of para content into row->cell.
    /// </summary>
    @Test
    public void testJira6168() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();
        builder.insertCell();
        builder.insertCell();
        builder.endTable();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.ROW);
        sdt.setTag("row");
        sdt.removeAllChildren();

        sdt.appendChild(table.getFirstRow());
        table.appendChild(sdt);

        // verify that generated docx is opened nicely in MS Word.
        TestUtil.saveOpen(doc, "Model\\Markup\\TestJira6168.docx");
    }

    /// <summary>
    /// UC2: Access Common Properties of an SDT
    /// </summary>
    @Test
    public void testAccessCommonProperties() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.PICTURE, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        tag.setLockContents(true);
        tag.setLockContentControl(true);

        // I don't like tag.Tag
        tag.setTag("My TestSdt tag");
        tag.setTitle("A friendly name");
        tag.getContentsFont().setBold(true);
        tag.getContentsFont().setColor(Color.BLUE);
        tag.getEndCharacterFont().setItalic(true);
        tag.getEndCharacterFont().setColor(Color.RED);
        tag.isShowingPlaceholderText(true);
        tag.isTemporary(true);

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestSetPropertiesTrue.docx");
        tag = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        msAssert.isTrue(tag.getLockContents());
        msAssert.isTrue(tag.getLockContentControl());
        msAssert.areEqual("My TestSdt tag", tag.getTag());
        msAssert.areEqual("A friendly name", tag.getTitle());
        msAssert.isTrue(tag.getContentsFont().getBold());
        msAssert.areEqual(Color.BLUE.getRGB(), tag.getContentsFont().getColor().getRGB());
        msAssert.isTrue(tag.getEndCharacterFont().getItalic());
        msAssert.areEqual(Color.RED.getRGB(), tag.getEndCharacterFont().getColor().getRGB());
        msAssert.isTrue(tag.isShowingPlaceholderText());
        msAssert.isTrue(tag.isTemporary());

        tag.isShowingPlaceholderText(false);
        tag.setLockContents(false);
        tag.setLockContentControl(false);
        tag.isTemporary(false);
        tag.setTag("");
        tag.setTitle("");
        TestUtil.saveOpen(doc, "Model\\Markup\\TestSetPropertiesFalse.docx");
    }

    /// <summary>
    /// UC3: RemoveSelfOnly
    /// </summary>
    @Test
    public void testRemoveSelfOnly() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().appendChild(new Paragraph(doc));
        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "First para. First run"));

        doc.getFirstSection().getBody().appendChild(new Paragraph(doc));
        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, "Second para. First run."));

        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        doc.getFirstSection().getBody().appendChild(new Paragraph(doc));
        final String PARA_4 = "Fourth para. First run.";
        doc.getFirstSection().getBody().getLastParagraph().appendChild(new Run(doc, PARA_4));

        tag.removeSelfOnly();
        msAssert.areEqual(0, doc.getFirstSection().getBody().getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());

        TestUtil.saveOpen(doc, "Model\\Markup\\TestRemoveSelfOnly.docx");

        NodeCollection para = doc.getFirstSection().getBody().getChildNodes(NodeType.PARAGRAPH, false);
        msAssert.areEqual(5, para.getCount());
        msAssert.areEqual("Click here to enter text.\r", para.get(3).getText());
        msAssert.areEqual(PARA_4 + "\f", para.get(4).getText());
    }


    /// <summary>
    /// UC4: Access specific properties of Calendar
    /// </summary>
    @Test
    public void testCalendarProps() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        tag.setDateDisplayLocale((int)Language.UZBEK_LATIN);
        tag.setDateDisplayFormat("mm/yy/DD");
        tag.setDateStorageFormat(SdtDateStorageFormat.TEXT);
        tag.setCalendarType(SdtCalendarType.TAIWAN);
        tag.setFullDateInternal(new DateTime(2011, 11, 26));

        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestCalendarProps.docx");

        tag = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual((int)Language.UZBEK_LATIN, tag.getDateDisplayLocale());
        msAssert.areEqual("mm/yy/DD", tag.getDateDisplayFormat());
        msAssert.areEqual(SdtDateStorageFormat.TEXT, tag.getDateStorageFormat());
        msAssert.areEqual(SdtCalendarType.TAIWAN, tag.getCalendarType());
        msAssert.areEqual(new DateTime(2011, 11, 26), tag.getFullDateInternal());
    }

    /// <summary>
    /// UC 5: Access specific properties of SdtBuildingBlockGallery (previously SdtDocPartList)
    /// </summary>
    @Test
    public void testBuildingBlockGalleryProps() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.BUILDING_BLOCK_GALLERY, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        tag.setBuildingBlockCategory("General");
        tag.setBuildingBlockGallery("table of contents");

        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestBuildingBlockGalleryProps.docx");

        tag = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual("table of contents", tag.getBuildingBlockGallery());
        msAssert.areEqual("General", tag.getBuildingBlockCategory());
    }

    /// <summary>
    /// UC 6: Access specific properties of ComboBox and DropDownList
    /// </summary>
    @Test
    public void testListProps() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.DROP_DOWN_LIST, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        SdtListItemCollection listItems = tag.getListItems();
        listItems.add(new SdtListItem("my disp text 1", "my value 1"));
        listItems.add(new SdtListItem("my disp text 2"));
        listItems.setSelectedValue(listItems.get(1));

        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestListProps.docx");

        tag = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(2, tag.getListItems().getCount());
        msAssert.areEqual("my value 1", tag.getListItems().get(0).getValue());
        msAssert.areEqual("my disp text 2", tag.getListItems().get(1).getDisplayText());

        tag.getListItems().removeAt(0);
        msAssert.areEqual("my disp text 2", tag.getListItems().getSelectedValue().getValue());

        tag.getListItems().removeAt(0);
        msAssert.isNull(tag.getListItems().getSelectedValue());
    }

    /// <summary>
    /// WORDSNET-6502 Allow programmatic access to Checked/Unchecked state of SDT Checkbox.
    /// Create Checkbox from scratch and set Checked to True. Save and verify.
    /// </summary>
    @Test
    public void testCheckboxChecked() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.CHECKBOX, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        msAssert.isFalse(tag.getChecked());
        tag.setChecked(true);
        msAssert.isTrue(tag.getChecked());
        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestCheckboxChecked.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        tag = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.isTrue(tag.getChecked());
    }

    /// <summary>
    /// WORDSNET-6502 Allow programmatic access to Checked/Unchecked state of SDT Checkbox.
    /// Create Checkbox from scratch and save with default state. Verify it is false upon load.
    /// </summary>
    @Test
    public void testCheckboxUnchecked() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.CHECKBOX, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        msAssert.isFalse(tag.getChecked());
        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestCheckboxUnchecked.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        tag = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.isFalse(tag.getChecked());
    }




    /// <summary>
    /// WORDSNET-6502 Allow programmatic access to Checked/Unchecked state of SDT Checkbox.
    /// Verify exception.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testCheckboxException() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.DROP_DOWN_LIST, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);
        tag.setChecked(true);
    }

    /// <summary>
    /// WORDSNET-5779 SDTs combo box, SelectedItem doesn't show up.
    /// Added code into DocumentValidator to update Sdt node content with selected value text and setting IsShowingPlaceholderText to false.
    /// </summary>
    @Test
    public void testJira5779() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.COMBO_BOX, MarkupLevel.BLOCK);
        SdtListItem sli = new SdtListItem("Item 1", "1");
        sdt.getListItems().add(sli);
        sdt.getListItems().setSelectedValue(sli);
        doc.getFirstSection().getBody().appendChild(sdt);
        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestJira5779.docx");

        StructuredDocumentTag updatedSdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.isFalse(updatedSdt.isShowingPlaceholderText());
        msAssert.areEqual("Item 1\f", updatedSdt.getText());
    }

    /// <summary>
    /// UC 7: Access specific properties of RichText and PlainText
    /// </summary>
    @Test
    public void testTextProps() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag plainText = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        StructuredDocumentTag richText = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(plainText);
        doc.getFirstSection().getBody().appendChild(richText);

        plainText.setMultiline(true);
        richText.setMultiline(true);

        Document resaved = TestUtil.saveOpen(doc, "Model\\Markup\\TestTextProps.docx");

        plainText = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        richText = (StructuredDocumentTag)resaved.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);
        msAssert.isTrue(plainText.getMultiline());
        msAssert.isTrue(richText.getMultiline());
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void testWrongPropertyAccess() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        tag.setDateDisplayLocale(1);
    }

    /// <summary>
    /// Special test that verifies two things:
    /// 1) Id's are preserved where possible. That means if we insert a node into dstDoc and there are no id conflicts inside that dstDoc,
    /// then Id does not change
    /// 2) If there are conflicts: e.g. we clone a node into the same doc, then the id of the cloned sdt is changed to maintain uniqueness.
    /// </summary>
    @Test
    public void testIdPersistanceAndUniqueness() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        // import into other document, id should be retained
        {
            Document dstDoc = new Document();
            StructuredDocumentTag dstTag = (StructuredDocumentTag)dstDoc.importNode(tag, true);
            dstDoc.getFirstSection().getBody().appendChild(dstTag);
            msAssert.areEqual(tag.getId(), dstTag.getId());
        }

        // now clone the whole doc, the id's should be retained
        {
            Document dstDoc = (Document)doc.deepClone(true);
            NodeCollection sdts = dstDoc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
            msAssert.areEqual(1, sdts.getCount());
            StructuredDocumentTag dstTag = (StructuredDocumentTag)sdts.get(0);
            msAssert.areEqual(tag.getId(), dstTag.getId());
        }

        // clone to the same doc: id should change
        {
            StructuredDocumentTag tagClone = (StructuredDocumentTag)tag.deepClone(true);
            msAssert.areNotEqual(tag.getId(), tagClone.getId());
        }

        // now import into the same doc, id should change
        {
            StructuredDocumentTag newTag = (StructuredDocumentTag)doc.importNode(tag, true);
            doc.getFirstSection().getBody().appendChild(newTag);
            msAssert.areNotEqual(tag.getId(), newTag.getId());
            newTag.remove();
        }
    }

    /// <summary>
    /// Verify that id is generated for sdts not having such in source doc.
    /// </summary>
    @Test
    public void testEmptyIdRegenerated() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestEmptyId.docx");
        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(1, sdts.getCount());
        StructuredDocumentTag tag = (StructuredDocumentTag)sdts.get(0);
        msAssert.areEqual(0, tag.getId());
    }

    /// <summary>
    /// Suppose we have an sdt in the loaded document, and we want to create new sdt of the same type.
    /// Verify that this new sdt reuses existing placeholder.
    /// </summary>
    @Test
    public void testReusePlaceholderFromLoadedDoc() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdtPlaceholders.docx");

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.INLINE);
        String testTagName = "DD Test Tag";
        sdt.setTitle(testTagName);
        doc.getFirstSection().getBody().getLastParagraph().appendChild(sdt);
        TestUtil.saveOpen(doc, "Model\\Markup\\TestSdtReusePlaceholdersFromLoadedDoc.docx");

        NodeCollection c =  doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        StructuredDocumentTag testTag = ((StructuredDocumentTag)c.get(c.getCount() - 1));
        msAssert.areEqual(testTagName, testTag.getTitle());
        msAssert.areSame(((StructuredDocumentTag)c.get(0)).getPlaceholder(), testTag.getPlaceholder());
    }

    /// <summary>
    /// We create several sdt nodes and verify that placeholders are reused when applicable.
    /// </summary>
    @Test
    public void testReusePlaceholdersFromNewlyCreatedNodes() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();
        Paragraph para = builder.getCurrentParagraph();

        // 1. Ctor.
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.COMBO_BOX, MarkupLevel.INLINE);
        para.appendChild(sdt);

        Table t = builder.startTable();
        Row r = new Row(doc);
        t.appendChild(r);

        StructuredDocumentTag cellLevelSdt = new StructuredDocumentTag(doc, SdtType.DROP_DOWN_LIST, MarkupLevel.CELL);
        r.appendChild(cellLevelSdt);

        msAssert.areEqual(SdtPlaceholderManager.LISTBOX_PLACEHOLDER_NAME, sdt.getPlaceholderName());
        msAssert.areEqual(sdt.getPlaceholderName(), cellLevelSdt.getPlaceholderName());
        msAssert.areSame(sdt.getPlaceholder(), cellLevelSdt.getPlaceholder());
        msAssert.areEqual("Choose an item.\f", sdt.getPlaceholder().getText());
    }

    /// <summary>
    /// Verify that placeholders are removed when we delete last sdt that uses them.
    /// </summary>
    @Test
    public void testPlaceholderRemoval() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().appendChild(new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK));
        doc.getFirstSection().getBody().appendChild(new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK));
        msAssert.areEqual(1, doc.getGlossaryDocument().getBuildingBlocks().getCount());

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        while (sdts.getCount() > 0)
            sdts.get(0).remove();

        doc.getFirstSection().getBody().appendChild(new StructuredDocumentTag(doc, SdtType.BUILDING_BLOCK_GALLERY, MarkupLevel.BLOCK));

        SaveOptions so = new OoxmlSaveOptions();
        WarningInfoCollection warnings = new WarningInfoCollection();
        doc.setWarningCallback(warnings);

        Document savedDoc = TestUtil.saveOpen(doc, "Model\\Markup\\SdtPlaceholdersRemoved.docx", so);
        msAssert.areEqual(1, savedDoc.getGlossaryDocument().getBuildingBlocks().getCount());
        msAssert.isTrue(TestUtil.containsWarningBySource(warnings, WarningSource.VALIDATOR, WarningStrings.DOCUMENT_VALIDATOR_SDT_PLACEHOLDER_UNUSED));
    }

    /// <summary>
    /// Verify that sdts inside glossary work fine.
    /// </summary>
    @Test
    public void testSdtInsideGlossary() throws Exception
    {
        Document doc = new Document();
        doc.setGlossaryDocument(new GlossaryDocument());

        // Add sdt as block into glossary
        BuildingBlock block = createAndInsertBuildingBlock(doc.getGlossaryDocument());
        StructuredDocumentTag tag = new StructuredDocumentTag(doc.getGlossaryDocument(), SdtType.DROP_DOWN_LIST, MarkupLevel.BLOCK);
        block.getFirstSection().getBody().appendChild(tag);
        msAssert.areEqual(2, doc.getGlossaryDocument().getBuildingBlocks().getCount());

        // now insert this sdt into main doc.
        StructuredDocumentTag mainTag = (StructuredDocumentTag)doc.importNode(tag, true);
        doc.getFirstSection().getBody().appendChild(mainTag);
        msAssert.areEqual(tag.getPlaceholderName(), mainTag.getPlaceholderName());
        msAssert.areSame(tag.getPlaceholder(), mainTag.getPlaceholder());
        msAssert.areNotSame(tag, mainTag);

        TestUtil.saveOpen(doc, "Model\\Markup\\SdtInsideGlossary.docx");
    }

    /// <summary>
    /// Verify placeholder is imported during Sdt import.
    /// </summary>
    @Test
    public void testPlaceholderImport() throws Exception
    {
        Document srcDoc = new Document();
        StructuredDocumentTag srcTag = new StructuredDocumentTag(srcDoc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        srcDoc.getFirstSection().getBody().appendChild(srcTag);

        // first import sdt into a newly created doc
        Document dstDoc = new Document();
        StructuredDocumentTag dstTag = (StructuredDocumentTag)dstDoc.importNode(srcTag, true);
        dstDoc.getFirstSection().getBody().appendChild(dstTag);
        checkImportedPlaceholder(srcTag, dstTag);
        TestUtil.saveOpen(dstDoc, "Model\\Markup\\TestPlaceholderImportIntoDocument.docx");

        // then import sdt into a glossary doc of a newly created doc
        Document dstDoc2 = new Document();
        dstDoc2.setGlossaryDocument(new GlossaryDocument());
        StructuredDocumentTag dstTagGlossary = (StructuredDocumentTag)dstDoc2.getGlossaryDocument().importNode(srcTag, true);
        BuildingBlock blockDoc2 = createAndInsertBuildingBlock(dstDoc2.getGlossaryDocument());
        blockDoc2.getFirstSection().getBody().appendChild(dstTagGlossary);
        checkImportedPlaceholder(srcTag, dstTagGlossary);
        TestUtil.saveOpen(dstDoc2, "Model\\Markup\\TestPlaceholderImportIntoGlossary.docx");
    }


    /// <summary>
    /// UC: Create custom placeholder and assign it to SDT. Verify that this SDT is roundtripped and
    /// is shown in MS Word ok. Also verify that custom placeholder is not reused for newly created nodes.
    /// </summary>
    @Test
    public void testCustomPlaceholderBlock() throws Exception
    {
        Document srcDoc = createDocWithCustomPlaceholder();

        // Now create a new sdt and verify that placeholder is default for this one.
        StructuredDocumentTag newTag = new StructuredDocumentTag(srcDoc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        srcDoc.getFirstSection().getBody().appendChild(newTag);

        // roundtrip document.
        Document doc = TestUtil.saveOpen(srcDoc, "Model\\Markup\\TestCustomPlaceholderCopy.docx");
        msAssert.areEqual("my fancy block", ((StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true)).getPlaceholderName());

        msAssert.areEqual(2, doc.getGlossaryDocument().getCount());
        msAssert.areEqual("Click here to enter text.\f", ((StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true)).getPlaceholder().getText());
    }

    /// <summary>
    /// UC: Create custom placeholder and import it to a doc with another SDT.
    /// Verify that both SDT now contain different placeholders and are roundtripped and shown in MS Word ok.
    /// </summary>
    @Test
    public void testCustomPlaceholderImport() throws Exception
    {
        Document srcDoc = createDocWithCustomPlaceholder();
        StructuredDocumentTag tag = (StructuredDocumentTag)srcDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        Document dstDoc = new Document();
        StructuredDocumentTag anotherTag = new StructuredDocumentTag(dstDoc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        dstDoc.getFirstSection().getBody().appendChild(anotherTag);

        StructuredDocumentTag importedTag = (StructuredDocumentTag)dstDoc.importNode(tag, true);
        dstDoc.getFirstSection().getBody().appendChild(importedTag);

        msAssert.areEqual(2, dstDoc.getFirstSection().getBody().getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
        msAssert.areEqual(2, dstDoc.getGlossaryDocument().getCount());
        msAssert.areNotSame(anotherTag.getPlaceholder(), importedTag.getPlaceholder());
        msAssert.areNotEqual(anotherTag.getPlaceholderName(), importedTag.getPlaceholderName());
        msAssert.areEqual("Enter your name here.\f", importedTag.getPlaceholder().getText());
        msAssert.areEqual("my fancy block", importedTag.getPlaceholderName());

        msAssert.areEqual(SdtPlaceholderManager.TEXT_PLACEHOLDER_NAME, anotherTag.getPlaceholderName());
        msAssert.areEqual("Click here to enter text.\f", anotherTag.getPlaceholder().getText());

        TestUtil.saveOpen(dstDoc, "Model\\Markup\\TestCustomPlaceholderImport.docx");
    }

    /// <summary>
    /// Test Append document with Custom placeholder to an empty doc. Also insert some non-SDT bblock into
    /// glossary and verify that it also is imported into new doc after AppendDocument.
    ///
    /// DD: This test passes, but the MS Word does not show SDT with custom Placeholder in this doc.
    /// Preliminary analysis of BuildingBlock and Sdt tags did not show any major differences,
    /// but some small ones e.g. style names etc.
    /// TODO: Investigate more during SDT API V2 update.
    /// </summary>
    @Test
    public void testCustomPlaceholderBlockAppendDoc() throws Exception
    {
        Document srcDoc = createDocWithCustomPlaceholder();
        Document dstDoc = new Document();

        // add arbitrary building block to glossary
        GlossaryDocument glossary = new GlossaryDocument();
        srcDoc.setGlossaryDocument(glossary);
        BuildingBlock otherBlock = new BuildingBlock(glossary);
        otherBlock.setCategory("Equation");
        otherBlock.appendChild(new Section(glossary));
        otherBlock.getFirstSection().appendChild(new Body(glossary));
        otherBlock.getFirstSection().getBody().appendChild(new Paragraph(glossary));
        otherBlock.getLastSection().getBody().getLastParagraph().appendChild(new Run(glossary, "This is not placeholder text."));

        dstDoc.appendDocument(srcDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);

        // DD: actually it should be equal to 2 below, but we don't presently import Glossary during AppendDocument
        msAssert.areEqual(1, dstDoc.getGlossaryDocument().getCount());
        msAssert.areEqual("my fancy block", ((StructuredDocumentTag)dstDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true)).getPlaceholderName());

        // SaveOpen this document for SDT API V2 and validate
        // that MS Word shows SDT "Model\Markup\TestCustomPlaceholderAppendDocument.docx".
    }

    /// <summary>
    /// Verify that default content is changed if custom placeholder is set.
    /// </summary>
    @Test
    public void testCustomPlaceholderText() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);
        msAssert.areEqual("Click here to enter text.\f", tag.getText());

        tag.setPlaceholderName(createCustomPlaceholder(doc));
        msAssert.areEqual("Enter your name here.\f", tag.getText());
    }

    /// <summary>
    /// Verify that:
    /// - placeholder text style is created in glossary doc similar to what MS Word does,
    /// - it is imported into main doc
    /// - it is referenced from both instances of runs in def Sdt content and in placeholder.
    /// </summary>
    @Test
    public void testPlaceholderStyle() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(tag);

        Run sdtDefaultRun = (Run)tag.getChild(NodeType.RUN, 0, true);
        verifyStyleProperties(doc, sdtDefaultRun);

        Run placeholderRun = tag.getPlaceholder().getFirstSection().getBody().getFirstParagraph().getFirstRun();
        verifyStyleProperties(doc.getGlossaryDocument(), placeholderRun);
    }

    /// <summary>
    /// A simple test for Reference counter class.
    /// </summary>
    @Test
    public void testReferenceCounter() throws Exception
    {
        ReferenceCounter r = new ReferenceCounter();

        Document doc = new Document();
        Paragraph para1 = new Paragraph(doc);

        r.incrementReference(para1);
        msAssert.isTrue(r.isReferenced(para1));

        r.decrementReference(para1);
        msAssert.isFalse(r.isReferenced(para1));
    }


    /// <summary>
    /// Tests <see cref="CompositeNode.HasNonMarkupDescendants"/> function.
    /// </summary>
    @Test
    public void testHasNonMarkupChildNodes() throws Exception
    {
        DocumentBuilder builder = new DocumentBuilder();
        Document doc = builder.getDocument();

        Paragraph para = builder.getCurrentParagraph();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, MarkupLevel.INLINE);
        para.appendChild(sdt);

        msAssert.isFalse(para.hasNonMarkupDescendants());

        SmartTag tag = new SmartTag(doc);
        sdt.appendChild(tag);
        msAssert.isFalse(para.hasNonMarkupDescendants());
        msAssert.isFalse(sdt.hasNonMarkupDescendants());
        msAssert.isFalse(tag.hasNonMarkupDescendants());

        Run r = new Run(doc, "simple text");
        tag.appendChild(r);

        msAssert.isTrue(para.hasNonMarkupDescendants());
        msAssert.isTrue(sdt.hasNonMarkupDescendants());
        msAssert.isTrue(tag.hasNonMarkupDescendants());
    }

    /// <summary>
    /// At least some checks for incorrect sdt insertion in the tree.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E")
    public void testInsertInlineAtBlockLevel() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().appendChild(new StructuredDocumentTag(doc, MarkupLevel.INLINE));
    }

    /// <summary>
    /// At least some checks for incorrect sdt insertion in the tree.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E")
    public void testInsertCellAtTableLevel() throws Exception
    {
        Document doc = new Document();
        Table table = new Table(doc);
        doc.getFirstSection().getBody().appendChild(table);
        table.appendChild(new StructuredDocumentTag(doc, MarkupLevel.CELL));
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "\\QCannot insert a node of this type at this location.\\E")
    public void testInsertSdtIntoSdtWrongLevel() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag blockSdt = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(blockSdt);

        // This is the wrong level sdt we are trying to insert, should throw.
        blockSdt.appendChild(new StructuredDocumentTag(doc, MarkupLevel.INLINE));
    }

    /// <summary>
    /// Verify that copying an sdt node into another document also copies all formatting attributes correctly.
    /// </summary>
    @Test
    public void testImportSdtNodes() throws Exception
    {
        Document srcDoc = new Document();
        StructuredDocumentTag srcSdt = new StructuredDocumentTag(srcDoc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        srcDoc.getSections().get(0).getBody().appendChild(srcSdt);

        srcSdt.getContentsRunPr().setIstd(srcDoc.getStyles().add(StyleType.CHARACTER, "My Contents Style").getIstd());
        srcSdt.getEndCharacterRunPr().setIstd(srcDoc.getStyles().add(StyleType.CHARACTER, "My End Character Style").getIstd());

        Document dstDoc = new Document();
        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        // Verify that the two styles referenced by the sdt node have been copied to the new document correctly.
        msAssert.areEqual(7, srcDoc.getStyles().getCount());
        msAssert.areEqual(7, dstDoc.getStyles().getCount());
        StructuredDocumentTag dstSdt = (StructuredDocumentTag)dstDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual("My Contents Style", dstDoc.getStyles().getByIstd(dstSdt.getContentsRunPr().getIstd(), false).getName());
        msAssert.areEqual("My End Character Style", dstDoc.getStyles().getByIstd(dstSdt.getEndCharacterRunPr().getIstd(), false).getName());
        msAssert.isNotNull(dstSdt.getPlaceholder());
        msAssert.areEqual(srcSdt.getId(), dstSdt.getId());
    }

    /// <summary>
    /// Test that cloning of StructuredDocumentTag results in deeply cloned instance.
    /// </summary>
    @Test
    public void testSdtClone() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.COMBO_BOX, MarkupLevel.BLOCK);

        SdtListItem item = new SdtListItem("item1display", "item1");
        tag.getListItems().add(item);

        tag.getXmlMapping().setMapping("testStoreItemId", "testXPath", "testPrefix");
        tag.getListItems().setSelectedValue(item);

        StructuredDocumentTag tagClone = (StructuredDocumentTag)tag.deepClone(true);
        msAssert.areNotSame(tag, tagClone);
        msAssert.areNotSame(tag.getControlProperties(), tagClone.getControlProperties());
        msAssert.areNotSame(tag.getXmlMapping(), tagClone.getXmlMapping());
        msAssert.areNotSame(tag.getListItems().get(0), tagClone.getListItems().get(0));
        msAssert.areNotSame(tag.getContentsRunPr(), tagClone.getContentsRunPr());
        msAssert.areNotSame(tag.getEndCharacterRunPr(), tagClone.getEndCharacterRunPr());
        msAssert.areNotEqual(tag.getId(), tagClone.getId());
        msAssert.areSame(tagClone.getPlaceholder(), tag.getPlaceholder()); // reuse sdt placeholder.
        msAssert.areSame(tagClone.getListItems().getSelectedValue(), tagClone.getListItems().get(0));
        msAssert.areNotSame(tag.getListItems().getSelectedValue(), tagClone.getListItems().getSelectedValue());
    }

    /// <summary>
    /// Test that cloning of StructuredDocumentTag CheckBox results in deeply cloned instance.
    /// </summary>
    @Test
    public void testSdtCheckboxClone() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, MarkupLevel.BLOCK);
        SdtCheckBox checkbox = new SdtCheckBox();
        tag.setControlProperties(checkbox);

        StructuredDocumentTag tagClone = (StructuredDocumentTag)tag.deepClone(true);
        msAssert.areNotSame(tag, tagClone);
        msAssert.areNotSame(tag.getControlProperties(), tagClone.getControlProperties());
        msAssert.areNotSame(((SdtCheckBox)tag.getControlProperties()).getCheckedStateInfo(), ((SdtCheckBox)tagClone.getControlProperties()).getCheckedStateInfo());
        msAssert.areNotSame(((SdtCheckBox)tag.getControlProperties()).getUncheckedStateInfo(), ((SdtCheckBox)tagClone.getControlProperties()).getUncheckedStateInfo());
    }

    /// <summary>
    /// WORDSNET-6911 StructuredDocumentTag Dropdown and ComboBox SelectedValue is null.
    /// andrnosk: Fixed by adding mechanism to set SelectedValue by sdtContent.
    /// </summary>
    @Test
    public void testJira6911() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira6911.docx");
        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
        {
            msAssert.isNotNull(sdt.getListItems().getSelectedValue());
        }

        StructuredDocumentTag dropDownList = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual("Dit is item 3", dropDownList.getListItems().getSelectedValue().getDisplayText());
        msAssert.areEqual("id3", dropDownList.getListItems().getSelectedValue().getValue());
    }







    /// <summary>
    /// WORDSNET-8546 Date Type StructuredDocumentTag do not render correctly in output Pdf
    /// Updated DateTime formats for parsing time.
    /// </summary>
    @Test
    public void testJira8546() throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

            Document doc = TestUtil.open("Model\\Markup\\TestJira8546.docx");
            doc.save(new MemoryStream(), SaveFormat.DOCX);   // FOSS: was Doc; DocumentValidator runs on the OOXML save.

            StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

            // Since we output local date as Word does result can be different depending on local timezone.
            int timeOffset = ((DateTime.subtract(DateTime.getNow(), DateTime.getUtcNow())).getHours());

            String formattedDateTime = (timeOffset >= 12) ? "Apr 3, 2013" : "Apr 2, 2013";
            msAssert.areEqual(formattedDateTime, sdt.getText());
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }




    /// <summary>
    /// Update nested repeating sections on Row-Level.
    /// </summary>
    @Test
    public void testNestedRepeatingSection() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestNestedRepeatingSection", LoadFormat.DOCX, SaveFormat.DOCX);   // FOSS: WordML save removed
        Table table = (Table)doc.getChild(NodeType.TABLE, 0, true);
        msAssert.areEqual(8, table.getRows().getCount());

        Paragraph para = table.getLastRow().getLastCell().getParagraphs().get(0);
        Run run = para.getRuns().get(0);

        msAssert.areEqual(2, para.getRuns().getCount());
        msAssert.areEqual("Col3:", run.getText());

        msAssert.areEqual("Child5.Value3", para.getRuns().get(1).getText());
    }

    /// <summary>
    /// Tests that a nested repeating section is not moved as a direct child of a parent repeating section on updating
    /// as it happens with a test document of <see cref="TestNestedRepeatingSection"/>, if number of child repeating
    /// section items matches to number of referenced XML nodes (document #1) or if a nested repeating section contains
    /// one repeating section item (document #2).
    /// </summary>
    @Test (dataProvider = "testPreservedNestedRepeatingSectionDataProvider")
    public void testPreservedNestedRepeatingSection(String documentName) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\" + documentName);

        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation. // Updates SDT contents

        StructuredDocumentTag repeatingSection =
            (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(SdtType.REPEATING_SECTION, repeatingSection.getSdtType());
        msAssert.areEqual("/root[1]/Data[1]/Children[1]/Child", repeatingSection.getXmlMapping().getXPath());

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, repeatingSection.getFirstChild().getNodeType());
        StructuredDocumentTag childSdt = (StructuredDocumentTag)repeatingSection.getFirstChild();

        // Checks that the first child of the parent repeating section is not a repeating section.
        msAssert.areNotEqual(SdtType.REPEATING_SECTION, childSdt.getSdtType());
        msAssert.isTrue(childSdt.isRepeatingSectionItem());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testPreservedNestedRepeatingSectionDataProvider")
	public static Object[][] testPreservedNestedRepeatingSectionDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestPreservedNestedRepeatingSection1.docx"},
			{"TestPreservedNestedRepeatingSection2.docx"},
		};
	}


    // FOSS: TestJira9716 removed — it verified that SDTs are unwrapped when converting DOCX->WordML
    // (validating with WordML2003SaveOptions, then asserting 0 SDTs after a WordML save); WordML is gone.

    /// <summary>
    /// WORDSNET-9999 Custom XML to SDT binding: leading slash required
    /// Word extracts data bound value even if slash missing at 'root' element.
    /// </summary>
    @Test
    public void testJira9999() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira9999.docx");

        msAssert.areEqual("It's the real text that should display.\f", doc.getFirstSection().getBody().getFirstParagraph().getText());
    }


    /// <summary>
    /// WORDSNET-10270 Custom XML to SDT binding issue with date and time format.
    /// The "tt" custom format specifier represents the entire AM/PM designator for DateTime.ToString(),
    /// but sdt's formatting string has "AM/PM", which is passed to this method.
    /// </summary>
    @Test
    public void testJira10270() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10270.docx");

        // Let SDT updater work.
        doc.save(new MemoryStream(), SaveFormat.DOCX);   // FOSS: was Doc; DocumentValidator runs on the OOXML save.

        SystemPal.saveCulture();
        try
        {
            SystemPal.setCulture("en-US");

            // This date and time string was taken from the problematic sdt.
            String dt = FormatterPal.xmlToDateTimeExact("2013-04-02T06:52:20.9977400-06:00").toLocalTime().toString("MMM d, yyyy h:mm tt");
            // Check sdt's text has been calculated properly by SdtContentUpdater().
            msAssert.areEqual(dt, doc.getFirstSection().getBody().getFirstParagraph().getRuns().get(1).getText());
            msAssert.areEqual(dt, doc.getFirstSection().getBody().getLastParagraph().getRuns().get(1).getText());
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }

    /// <summary>
    /// WORDSNET-10091 Custom XML to SDT binding: images inside XML are not working
    /// Basic support for SdtType.Picture data bound update.
    /// </summary>
    @Test
    public void testJira10091() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10091.docx");
        doc.save(new MemoryStream(), SaveFormat.DOCX);   // FOSS: was Doc; DocumentValidator runs on the OOXML save.

        // Verify that image data was updated.
        Shape shape = (Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true);

        String md5 = msString.trim(ArrayUtil.dumpArray(HashUtil.computeHash(DigestAlgorithm.MD_5, shape.getImageData().getImageBytes())));
        msAssert.areEqual("F8 DB 2A 7F 7F 46 70 2C AE 01 0F 26 A1 95 3D 86", md5);
    }

    /// <summary>
    /// WORDSNET-10445 Date formatting issue with Custom XML to SDT binding
    /// </summary>
    @Test
    public void testDefect10445() throws Exception
    {
        SystemPal.saveCulture();
        try
        {
            SystemPal.setStandardCulture();

            Document doc = TestUtil.open("Model\\Markup\\TestDefect10445.docx");

            // Let SDT updater work.
            doc.save(new MemoryStream(), SaveFormat.DOCX);   // FOSS: was Pdf

            msAssert.areEqual("Jul 4, 1970", doc.getFirstSection().getBody().getFirstParagraph().getChildNodes(NodeType.ANY, false).get(5).getText());
        }
        finally
        {
            SystemPal.restoreCulture();
        }
    }


    /// <summary>
    /// WORDSNET-10935 Document.UpdatePageLayout throws System.FormatException.
    /// String with image bytes contains illegal symbols in Base64.
    /// Additionally, decoded image bytes does not represents a valid image and shouldn't be updated.
    /// </summary>
    @Test
    public void testJira10935() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira10935.docx");
        // No update page layout in FOSS.

        Shape dml = (Shape)doc.getChild(NodeType.SHAPE, 0, true);

        msAssert.areEqual(12302, dml.getImageData().getImageBytes().length);
    }






    /// <summary>
    /// WORDSNET-11759 System.NullReferenceException is thrown while creating instance of StructuredDocumentTag
    ///
    /// Check situation when Placeholder contains paragraph but doesn't contain run with content.
    /// </summary>
    @Test
    public void testJira11759() throws Exception
    {
        Document document = TestUtil.open("Model\\Markup\\TestJira11759.dotm");
        StructuredDocumentTag sdt = new StructuredDocumentTag(document, SdtType.PLAIN_TEXT, MarkupLevel.INLINE);
        msAssert.areEqual(0, sdt.getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// WORDSNET-12052 SDT data is lost during saving to PDF.
    /// Should not replace sdt content if it's property 'IsShowingPlaceholderText' is true.
    /// </summary>
    @Test
    public void testJira12052() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira12052.docx");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was a raw validator run with null SaveOptions (now asserts).

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        StructuredDocumentTag sdt = (StructuredDocumentTag) sdts.get(0);

        // Preserve IsShowingPlaceholderText.
        msAssert.isTrue(sdt.isShowingPlaceholderText());
        msAssert.areEqual("Placeholder text.", sdt.getText());

        sdt = (StructuredDocumentTag)sdts.get(1);
        msAssert.isTrue(sdt.isShowingPlaceholderText());
        msAssert.areEqual("[Type the document title]", sdt.getText());
    }




    /// <summary>
    /// Currently there is no way to create RepeatingSection using public API.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void doNotAllowCreateRepeatingSection() throws Exception
    {
        StructuredDocumentTag structuredDocumentTag = new StructuredDocumentTag(
            new Document(),
            SdtType.REPEATING_SECTION,
            MarkupLevel.INLINE );
    }


    /// <summary>
    /// WORDSNET-12528 Text formatting is not correct inside SDT after using DocumentBuilder.InsertDocument.
    /// Looks like during inserting the content into inline SDT we have to use the style from the source document no matter
    /// UseDestinationStyles import format mode is set, mimic MS Word behavior.
    /// </summary>
    @Test
    public void testJira12528() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Markup\\TestJira12528A.docx");
        Document srcDoc = TestUtil.open("Model\\Markup\\TestJira12528B.docx");

        DocumentBuilder builder = new DocumentBuilder(dstDoc);

        for (StructuredDocumentTag sdt : (Iterable<StructuredDocumentTag>) dstDoc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true))
        {
            if ("sdtBlock".equals(sdt.getTag()))
            {
                sdt.removeAllChildren();
                Paragraph para = new Paragraph(dstDoc);
                sdt.appendChild(para);
                builder.moveTo(para);

                builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);
            }

            if ("sdtInline".equals(sdt.getTag()))
            {
                sdt.removeAllChildren();
                Run r = new Run(dstDoc);
                sdt.appendChild(r);
                builder.moveTo(r);
                builder.insertDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);
            }
        }

        // It is better to use gold comparing here, because there are really strange
        // rules how MS Word process the content inside SDT.
        dstDoc = TestUtil.saveOpen(dstDoc, "Model\\Markup\\TestJira12528.docx");

        Node[] sdtNodes = dstDoc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).toArray();
        StructuredDocumentTag sdt2 = (StructuredDocumentTag)sdtNodes[2];
        Node[] sdtRuns = sdt2.getChildNodes(NodeType.RUN, true).toArray();

        msAssert.areEqual(AttrBoolEx.TRUE, ((Run)sdtRuns[1]).getRunPr().getBold());

        // The style is applied. So we have to expand and check attributes.
        RunPr runPr = ((Run)sdtRuns[3]).getExpandedRunPr(RunPrExpandFlags.NORMAL);
        msAssert.isNotNull(runPr.getIstd());

        // The size specified in the style equals 13 (26).
        msAssert.areEqual(26, runPr.getSize());
        msAssert.areEqual(-13732683, runPr.getColor().toArgb());

        msAssert.areEqual(Underline.SINGLE, ((Run)sdtRuns[5]).getRunPr().getUnderline());
    }


    /// <summary>
    /// Tests reading/writing the 'color' element of a structured document tag.
    /// </summary>
    @Test
    public void testReadingWritingColor() throws Exception
    {
        final String DOC_FILE_NAME = "Model\\Markup\\TestSdtColor.docx";
        Document doc = TestUtil.open(DOC_FILE_NAME);

        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(3, nodes.getCount());
        StructuredDocumentTag sdt1 = (StructuredDocumentTag)nodes.get(0);
        checkColor(sdt1, "993300", null, null, null);
        checkColor((StructuredDocumentTag)nodes.get(1), "385623", "accent6", "80", null);
        checkColor((StructuredDocumentTag)nodes.get(2), "9CC2E5", "accent1", null, "99");

        sdt1.setBaseColor(DrColor.fromArgb(32, 64, 128));
        doc = TestUtil.saveOpen(doc, DOC_FILE_NAME, null, false);

        nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(3, nodes.getCount());
        checkColor((StructuredDocumentTag)nodes.get(0), "204080", null, null, null);
        checkColor((StructuredDocumentTag)nodes.get(1), "385623", "accent6", "80", null);
        checkColor((StructuredDocumentTag)nodes.get(2), "9CC2E5", "accent1", null, "99");
    }

    /// <summary>
    /// WORDSNET-14104 Tests reading/writing the 2.5.1.1 appearance, 2.5.1.12 webExtensionCreated,
    /// 2.5.1.13 webExtensionLinked, 2.6.1.15 entityPicker [MS-DOCX] elements of a structured document tag.
    /// </summary>
    @Test
    public void testJira14104() throws Exception
    {
        // The extensions except 'appearance' are created manually in the source document.
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\TestSdtExtensions", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);

        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, ((StructuredDocumentTag)nodes.get(0)).getAppearance());
        msAssert.areEqual(SdtAppearance.TAGS, ((StructuredDocumentTag)nodes.get(1)).getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, ((StructuredDocumentTag)nodes.get(2)).getAppearance());

        msAssert.areEqual(SdtType.ENTITY_PICKER, ((StructuredDocumentTag)nodes.get(3)).getSdtType());

        msAssert.areEqual(SdtWebExtensionRelationship.NONE, ((StructuredDocumentTag)nodes.get(3)).getWebExtensionRelationship());
        msAssert.areEqual(SdtWebExtensionRelationship.CREATED_BY_WEB_EXTENSION, ((StructuredDocumentTag)nodes.get(4)).getWebExtensionRelationship());
        msAssert.areEqual(SdtWebExtensionRelationship.LINKED_TO_WEB_EXTENSION, ((StructuredDocumentTag)nodes.get(5)).getWebExtensionRelationship());

        // FOSS: the SDT-appearance / entity-picker / web-extension data-loss warnings were verified
        // by validating to the removed .doc format; that warning check is dropped. The SDT property
        // reads above still exercise the model.
    }



    /// <summary>
    /// WORDSNET-13458 Docx to Doc/Pdf conversion issue with bounded check-boxes
    /// Added support for data bound checkbox update.
    /// </summary>
    @Test
    public void testJira13458() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira13458.docx");

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual("\u2612", sdts.get(0).getText());
        msAssert.areEqual("\u2610", sdts.get(1).getText());
        msAssert.areEqual("\u2612", sdts.get(2).getText());
        msAssert.areEqual("\u2610", sdts.get(3).getText());
    }

    /// <summary>
    /// WORDSNET-13897 State of checkbox SDT is lost after conversion from Docx to Doc/Fixed file format
    /// Bound lowercase value 'true' also indicates that checkbox should be checked.
    /// </summary>
    @Test
    public void testJira13897() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira13897.docx");

        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation.

        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual("\u2612", sdts.get(0).getText());
    }

    /// <summary>
    /// WORDSNET-13622 StructuredDocumentTag of type DROP_DOWN_LIST is not
    /// retaining its value when saving as PDF format.
    /// 'IsUpdateableType' must be 'true' for this sdt type.
    /// </summary>
    @Test
    public void testJira13622() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira13622.docx");
        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation.

        Table tbl = doc.getFirstSection().getBody().getTables().get(0);

        Cell cell = tbl.getCell(1, 2);
        msAssert.areEqual("False", cell.getFirstParagraph().getFirstRun().getText());

        cell = tbl.getCell(5, 2);
        msAssert.areEqual("Masda", cell.getFirstParagraph().getFirstRun().getText());
    }



    /// <summary>
    /// WORDSNET-14295 An image was lost after resaving the document. A 'pict' element of the image is
    /// direct child of a 'sdt' element that is not allowed by ISO 29500 but MS Word reads the document well.
    /// </summary>
    @Test
    public void testJira14295() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira14295.docx");
        msAssert.areEqual(1, doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST).getShapes().getCount());
    }








    /// <summary>
    /// This test is the part of the WORDSNET-15466
    /// Checks that SDT without bound data does not update when it is placed into <see cref="GlossaryDocument"/>.
    /// </summary>
    @Test
    public void testJira15466NoBoundData() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira15466NoBoundData.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)
            doc.getGlossaryDocument().getFirstBuildingBlock().getFirstSection().getBody().getFirstChild();

        // Check the result of the updating and content of the SDT was not updated.
        sdt.getXmlMapping().updateContent();
        msAssert.isFalse(((Paragraph)sdt.getFirstChild()).hasChildNodes());

        // Check that document can be validate without errors.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation.
    }



    /// <summary>
    /// Tests that a structured document tag is updated correctly when mapped to an extended property.
    /// </summary>
    @Test
    public void testUpdatingFromExtendedProperty() throws Exception
    {
        Document doc = new Document();

        doc.getBuiltInDocumentProperties().get(PropertyName.COMPANY).setValue("Test Company");

        // Insert a SDT and map to the extended property.
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(sdt);
        sdt.getXmlMapping().setMapping("{6668398D-A668-4E3E-A5EB-62B293D839F1}", "/ns0:Properties[1]/ns0:Company[1]",
            "xmlns:ns0='http://schemas.openxmlformats.org/officeDocument/2006/extended-properties'");

        msAssert.isTrue(sdt.getXmlMapping().updateContent());
        msAssert.areEqual("Test Company", msString.trim(sdt.getText()));
    }

    /// <summary>
    /// WORDSNET-15651 StructuredDocumentTag.FullDate is not updating.
    /// When SDT has DataBinding and user changes its value through setter,
    /// we should update corresponding XmlNode value along with updating its content.
    /// </summary>
    @Test
    public void testJira15651() throws Exception
    {
        final String TEST_FILE = "Model\\Markup\\TestJira15651";
        Document doc = TestUtil.open(TEST_FILE, LoadFormat.DOCX);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(1);

        DateTime date = new DateTime(2017, 8, 9);
        sdt.setFullDateInternal(date);

        doc = TestUtil.saveOpen(doc, TEST_FILE, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        sdt = (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(1);
        msAssert.areEqual(date, sdt.getFullDateInternal());

        String datetText = date.toString(sdt.getDateDisplayFormat());
        msAssert.areEqual(datetText, ((Paragraph)sdt.getFirstChild()).getFirstRun().getText());
        msAssert.areEqual(datetText, sdt.getXmlMapping().getValue());
    }

    /// <summary>
    /// WORDSNET-15953 System.InvalidOperationException throws StructuredDocumentTag.PlaceholderName.
    /// StructuredDocumentTag can't found BuildingBlock by new name when it changes placeholder name. Added resilience.
    /// </summary>
    @Test
    public void testJira15953() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(sdt);

        GlossaryDocument glossaryDocument = doc.getGlossaryDocument();
        final String NAME = "testPlaceholder";

        BuildingBlock placeholder = new BuildingBlock(glossaryDocument);
        placeholder.setBehavior(BuildingBlockBehavior.CONTENT);
        placeholder.setType(BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        placeholder.setName(NAME);
        placeholder.setGuidInternal(Guid.newGuid());
        placeholder.setCategory("NetBrain");
        placeholder.setGallery(BuildingBlockGallery.CUSTOM_QUICK_PARTS);
        Section section = placeholder.getFirstSection();
        if (section == null)
        {
            section = new Section(glossaryDocument);
            placeholder.getSections().add(section);
        }

        Body body = section.getBody();
        if (body == null)
        {
            body = new Body(glossaryDocument);
            section.appendChild(body);
        }

        Paragraph para = body.getFirstParagraph();
        if (para == null)
        {
            para = new Paragraph(glossaryDocument);
            body.appendChild(para);
        }

        Run run = ms.as(para.getFirstChild(), Run.class);
        if (run == null)
        {
            run = new Run(glossaryDocument);
            para.appendChild(run);
        }
        run.setText("[Test PlaceHolder]");

        glossaryDocument.getBuildingBlocks().add(placeholder);

        sdt.setPlaceholderName(placeholder.getName());

        msAssert.areEqual(NAME, sdt.getPlaceholderName());

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestJira15953", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        sdt = (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);
        msAssert.areEqual(NAME, sdt.getPlaceholderName());
        msAssert.areEqual(NAME, sdt.getPlaceholder().getName());
    }



    /// <summary>
    /// Relates to WORDSNET-16155
    /// Document contains nested repeating section SDT with invalid XPath.
    /// </summary>
    @Test
    public void testJira16155Nested() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira16155_Nested.docx");
        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation.

        NodeCollection sdts = doc.getFirstSection().getBody().getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(7, sdts.getCount());
        msAssert.isTrue(sdts.get(0).getText().startsWith("Repeating:"));
        msAssert.isTrue(sdts.get(2).getText().startsWith("Repeating2"));
    }


    /// <summary>
    /// WORDSNET-16532 Allow accessing Color property of StructuredDocumentTag objects.
    /// Added public property to get and set Color.
    /// </summary>
    @Test
    public void testJira16532() throws Exception
    {
        final String FILE_NAME = "Model\\Markup\\TestJira16532";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);
        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);

        // DrColor.Empty is used, when no color specified, or <w15:color w:val="auto" /> is specified.
        msAssert.areEqual((DrColor.EMPTY).toNativeColor(), sdt.getColor());

        sdt.setColor(Color.RED);

        TestUtil.saveOpen(doc, FILE_NAME, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        sdt = (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);

        msAssert.areEqual(new Color((255), (0), (0), (255)), sdt.getColor());
    }


    /// <summary>
    /// WORDSNET-14601 Provide option to Use a style to format text typed into the SDT control.
    /// Added a public property to get and set the SDT style.
    /// </summary>
    @Test (dataProvider = "testJira14601DataProvider")
    public void testJira14601(String styleName) throws Exception
    {
        final String FILE_NAME = "Model\\Markup\\TestJira14601";
        Document doc = TestUtil.open(FILE_NAME, LoadFormat.DOCX);

        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);

        msAssert.areEqual(sdt.getStyle().getName(), "Default Paragraph Font");

        sdt.setStyle(doc.getStyles().get(styleName));
        msAssert.areEqual(sdt.getStyle().getName(), styleName);
        msAssert.areEqual(sdt.getStyleName(), styleName);

        OoxmlSaveOptions saveOptions = new OoxmlSaveOptions(SaveFormat.DOCX);
        TestUtil.save(doc, msString.format("{0}{1}.docx", FILE_NAME, styleName), saveOptions, true);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira14601DataProvider")
	public static Object[][] testJira14601DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Quote"},
			{"Strong"},
		};
	}

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Tests throwing exception when setting null as an SDT style.
    /// </summary>
    @Test (expectedExceptions = NullPointerException.class)
    public void testJira14601SetNull() throws Exception
    {
        Document doc = new Document();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.CELL);
        sdt.setStyle(null);
    }

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Tests throwing exception when setting not character and not linked style.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira14601SetNormal() throws Exception
    {
        Document doc = new Document();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.CELL);
        sdt.setStyle(doc.getStyles().get("Normal"));
    }

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Tests throwing exception when setting not character and not linked style accesed by StyleName.
    /// </summary>
    @Test (expectedExceptions = IllegalStateException.class)
    public void testJira14601SetNormalByStyleName() throws Exception
    {
        Document doc = new Document();

        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.CELL);
        sdt.setStyleName("Normal");
    }

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Tests getting the default style when no style is set.
    /// </summary>
    @Test
    public void testJira14601GetDefaultStyle() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.DATE, MarkupLevel.CELL);

        msAssert.areEqual("Default Paragraph Font", sdt.getStyle().getName());
    }

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Setting the default style.
    /// </summary>
    @Test
    public void testJira14601ClearingStyle() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira14601", LoadFormat.DOCX);

        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);

        sdt.setStyleName("Quote");
        msAssert.areEqual(doc.getStyles().get("Quote Char").getIstd(), sdt.getContentsRunPr().get(FontAttr.ISTD));

        sdt.setStyleName("Default Paragraph Font");
        msAssert.areEqual(StyleIndex.NIL, sdt.getContentsRunPr().get(FontAttr.ISTD));
    }

    /// <summary>
    /// Relates to WORDSNET-14601
    /// Setting linked char style.
    /// </summary>
    @Test
    public void testJira14601SetLinkedChar() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira14601", LoadFormat.DOCX);

        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0);

        sdt.setStyleName("Heading 1 Char");
        msAssert.areEqual("Heading 1", sdt.getStyleName());
    }


    /// <summary>
    /// WORDSNET-17905 If an SDT is mapped to custom XML and a mapped XML node contains a FlatOpc
    /// document with SDTs inside, the SDTs were not inserted on updating the SDT of the main document, but their
    /// non-updated contents only. MS Word inserts SDTs into the updating SDT and if necessary moves the SDT to
    /// block level. The same has been implemented now.
    /// </summary>
    @Test (dataProvider = "testJira17905DataProvider")
    public void testJira17905(String fileName, String expectedText, /*MarkupLevel*/int expectedLevel, int expectedChildSdts) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\" + fileName);
        TestUtil.executeValidator(doc, SaveFormat.DOCX);   // FOSS: was Pdf; SDT-content update runs in document validation.

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(expectedLevel, sdt.getLevel());
        msAssert.areEqual(expectedChildSdts, sdt.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
        msAssert.areEqual(expectedText, doc.getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testJira17905DataProvider")
	public static Object[][] testJira17905DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestJira17905_InlineSdt.docx", 
        "Updating SDT: Inserting SDT: XML node value\f",  MarkupLevel.INLINE,  1},
			{"TestJira17905_BlockSdtAndParaToStartOfPara.docx", 
        "XML node value\r- Inserting SDT\r- Updating SDT\f",  MarkupLevel.BLOCK,  1},
			{"TestJira17905_ParaAndBlockSdtToMiddleOfPara.docx", 
        "Updating SDT: \rInserting SDT: \rXML node value\r- Updating SDT\f",  MarkupLevel.BLOCK,  1},
			{"TestJira17905_ParaAndBlockSdtToEndOfPara.docx", 
        "Updating SDT: \rInserting SDT: \rXML node value\f",  MarkupLevel.BLOCK,  1},
			{"TestJira17905_ParaAndBlockSdtWithReplacingPara.docx", 
        "Inserting SDT: \rXML node value\f",  MarkupLevel.BLOCK,  1},
			{"TestJira17905_BlockSdt.docx", 
        "Updating SDT: XML node value\f",  MarkupLevel.INLINE,  1},
			{"TestJira17905_TwoSections.docx", 
        "Updating SDT: \rSection 1\rSection 2\r\f",  MarkupLevel.BLOCK,  0},
		};
	}

    /// <summary>
    /// Test MS Word inconsistency related to XML Mapping update.
    /// Tested on Microsoft® Word 2019 MSO (Version 2309 Build 16.0.16827.20166) 32-bit
    /// This is slightly complex case:
    /// Original file saved in Word will have three paragraphs.
    /// It can be saved again in Word and document layout will be preserved, still three paragraphs.
    /// But if we delete mapped SDT checksum in file produced by Word then extra paragraph will appear.
    ///
    /// This means that first SDT update from mapped XML was locked by checksum and if we remove checksum Word will update
    /// SDT mapped XML one more time and produces another result (extra paragraph).
    /// </summary>
    @Test
    public void testWordInconsistency() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestWordInconsistency.docx");

        // Original TestWordInconsistency.docx was saved in MS Word.
        Document docMs = TestUtil.open("Model\\Markup\\TestWordInconsistency.docx ms.docx");

        // Original TestWordInconsistency.docx was saved in MS Word and storeItemChecksum element was manually removed.
        Document docMsNoChecksum = TestUtil.open("Model\\Markup\\TestWordInconsistency.docx ms NoChecksum.docx");

        // Three paragraphs.
        msAssert.areEqual("Updating SDT: \rSection 1\rSection 2\f", doc.getText());

        // Still three paragraphs because mapping update is locked by checksum.
        msAssert.areEqual("Updating SDT: \rSection 1\rSection 2\f", docMs.getText());

        // Extra paragraph is appeared.
        msAssert.areEqual("Updating SDT: \rSection 1\rSection 2\r\f", docMsNoChecksum.getText());
    }

    /// <summary>
    /// WORDSNET-15602 StructuredDocumentTag.ContentsFont does not apply RunPr for SDT Content.
    /// Fixed by simultaneously applying attributes for both RunPrs - for SDT and for SDT Content.
    /// </summary>
    @Test
    public void testJira15602() throws Exception
    {
        Document doc = new Document();

        StructuredDocumentTag checkbox = new StructuredDocumentTag(doc, SdtType.CHECKBOX, MarkupLevel.INLINE);
        Run checkboxRun = (Run)checkbox.getChild(NodeType.RUN, 0, true);

        checkbox.getContentsFont().setBold(true);
        msAssert.areEqual(true, checkboxRun.getFont().getBold());

        checkbox.setChecked(true);
        checkbox.getContentsFont().setName("Arial");
        String desiredFontName = ((SdtCheckBox)checkbox.getControlProperties()).getCheckedStateInfo().getFontName();
        msAssert.areNotEqual("Arial", checkboxRun.getFont().getName());
        msAssert.areEqual(desiredFontName, checkboxRun.getFont().getName());
    }

    /// <summary>
    /// WORDSNET-18112 AW does not update SDT bound content.
    /// Fixed by updating SDT bound content on document load.
    /// </summary>
    @Test
    public void testJira18112() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestJira18112.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        Node sdtContentNode = sdt.getChild(NodeType.RUN, 0, true);

        msAssert.areEqual("10", ((Run)sdtContentNode).getText());
    }

    /// <summary>
    /// WORDSNET-18729 Document get corrupted after saving DOCX file with enable track changes and user comments.
    /// Document contains SDT which end with section break.
    /// </summary>
    @Test
    public void test18729() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test18729", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        msAssert.areEqual(3, doc.getSections().getCount());

        // SDT is a ranged STD.
        msAssert.areEqual(3, doc.getFirstSection().getBody().getCount());

        StructuredDocumentTagRangeStart sdtRangeStart =
            (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();
        msAssert.areEqual("IN:Schema", sdtRangeStart.getTag());
        msAssert.areEqual(3, sdtRangeStart.getChildNodes().getCount());

        // Tested on Microsoft® Word 2019 MSO (Version 2309 Build 16.0.16827.20166) 32-bit.
        // Now we have two paragraphs in second section.
        msAssert.areEqual("\r\f", doc.getSections().get(1).getText());
    }

    /// <summary>
    /// Related to WORDSNET-18729
    /// Word does not move SDT to the block level when mapped document contains only two paragraphs
    /// and last paragraph is empty.
    /// </summary>
    @Test
    public void test18729InlineSDT() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test18729InlineSDT.docx");

        // Check model structure.
        Body body = doc.getFirstSection().getBody();
        msAssert.areEqual(3, body.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(0, body.getLastParagraph().getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(", ", body.getFirstParagraph().getFirstRun().getText());

        // AM. Seems MS Word behavior has changed, now SDT is moved out of paragraph in case of updating from
        // multiline document.
        StructuredDocumentTag sdt = (StructuredDocumentTag)body.getFirstParagraph().getNextSibling();
        msAssert.areEqual(MarkupLevel.BLOCK, sdt.getLevel());

        Run innerRun = ((Paragraph)sdt.getFirstChild()).getFirstRun();
        msAssert.areEqual("=lorem(2000)", innerRun.getText());
        msAssert.isTrue(innerRun.getRunPr().hasInsertRevision());

        msAssert.areEqual(2, sdt.getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// WORDSNET-12655 Tests ability to create a repeating section and a repeating section item.
    /// </summary>
    @Test
    public void test12655() throws Exception
    {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        CustomXmlPart xmlPart = doc.getCustomXmlParts().add("1234",
            "<books><book><title>Everyday Italian</title><author>Giada De Laurentiis</author></book>" +
            "<book><title>Harry Potter</title><author>J K. Rowling</author></book>" +
            "<book><title>Learning XML</title><author>Erik T. Ray</author></book></books>");

        Table table = builder.startTable();

        builder.insertCell();
        builder.write("Title");

        builder.insertCell();
        builder.write("Author");

        builder.endRow();
        builder.endTable();

        StructuredDocumentTag repeatingSectionSdt =
            new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION, MarkupLevel.ROW);
        repeatingSectionSdt.getXmlMapping().setMapping(xmlPart, "/books[1]/book", "");
        table.appendChild(repeatingSectionSdt);

        StructuredDocumentTag repeatingSectionItemSdt =
            new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION_ITEM, MarkupLevel.ROW);
        repeatingSectionSdt.appendChild(repeatingSectionItemSdt);

        Row row = new Row(doc, AttrCollectionExtension.deepClone(table.getFirstRow().getTablePr()));
        repeatingSectionItemSdt.appendChild(row);

        // SDT placeholder contains a cell.
        StructuredDocumentTag titleSdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.CELL);
        titleSdt.getXmlMapping().setMapping(xmlPart, "/books[1]/book[1]/title[1]", "");
        row.appendChild(titleSdt);

        StructuredDocumentTag authorSdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.CELL);
        authorSdt.getXmlMapping().setMapping(xmlPart, "/books[1]/book[1]/author[1]", "");
        row.appendChild(authorSdt);

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test12655", UnifiedScenario.DOCX_2_DOCX | UnifiedScenario.EXPORT_ONLY);

        StructuredDocumentTag repeatingSectionItem =
            (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);
        msAssert.areEqual(SdtType.REPEATING_SECTION_ITEM, repeatingSectionItem.getSdtType());
    }

    /// <summary>
    /// WORDSNET-12655 Tests that exception is thrown on creation an inline repeating section.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testInlineRepeatingSection() throws Exception
    {
        Document doc = new Document();
        new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION, MarkupLevel.INLINE);
    }

    /// <summary>
    /// WORDSNET-12655 Tests that exception is thrown on creation an inline repeating section item.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testInlineRepeatingSectionItem() throws Exception
    {
        Document doc = new Document();
        new StructuredDocumentTag(doc, SdtType.REPEATING_SECTION_ITEM, MarkupLevel.INLINE);
    }





    /// <summary>
    /// WORDSNET-20257 Aspose.Words does not import StructuredDocumentTag containing section break
    /// Partially reverted WORDSNET-18729
    /// </summary>
    @Test
    public void test20257() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test20257.docx");
        msAssert.areEqual(4, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());

        // Check problematic SDT.
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).get(3);
        msAssert.areEqual("AAAA", sdt.getTag());

        // Check ranged SDT.
        StructuredDocumentTagRangeStart rangeStart = (StructuredDocumentTagRangeStart)doc.getSections().get(3).getBody().getChildNodes(NodeType.ANY, false).get(1);
        StructuredDocumentTagRangeEnd rangeEnd = (StructuredDocumentTagRangeEnd)doc.getSections().get(4).getBody().getChildNodes(NodeType.ANY, false).get(1);

        msAssert.isTrue(msObject.referenceEquals(rangeStart.getRangeEnd(), rangeEnd));
        msAssert.areEqual(0x51dd23bc, rangeStart.getId());
        msAssert.areEqual(0x51dd23bc, rangeEnd.getId());

        msAssert.areEqual(SdtType.RICH_TEXT, rangeStart.getSdtType());
        msAssert.areEqual("BBBB", rangeStart.getTag());
        msAssert.areEqual("BBBB", rangeStart.getTitle());
    }


    /// <summary>
    /// Tests how range nodes are cloned when whole document cloned.
    /// </summary>
    @Test
    public void testSdtRangeClone() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test13519.docx");
        Document clonedDoc = (Document)doc.deepClone(true);

        StructuredDocumentTagRangeStart rangeStart =
            (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();

        StructuredDocumentTagRangeStart clonedRangeStart =
            (StructuredDocumentTagRangeStart)clonedDoc.getFirstSection().getBody().getFirstChild();

        // All range parts are cloned.
        msAssert.isFalse(msObject.referenceEquals(rangeStart, clonedRangeStart));
        msAssert.isFalse(msObject.referenceEquals(rangeStart.getInternalSdt(), clonedRangeStart.getInternalSdt()));
        msAssert.isFalse(msObject.referenceEquals(rangeStart.getInternalSdt().getDocument(), clonedRangeStart.getInternalSdt().getDocument()));
    }

    /// <summary>
    /// Demonstrates that ranged structured document tag will created only when it is necessary.
    /// </summary>
    @Test
    public void testRangedNecessity() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdtRange2.docx");
        StructuredDocumentTagRangeStart sdtRangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);

        // Ranged SDT was loaded.
        msAssert.isNotNull(sdtRangeStart.getRangeEnd());
        msAssert.areEqual("ExhibitB", sdtRangeStart.getTag());

        // Move range end node to first section to remove range necessity.
        sdtRangeStart.getParentNode().appendChild(sdtRangeStart.getRangeEnd());

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestSdtRange2", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // Now we have no ranged SDT.
        msAssert.areEqual(0, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true).getCount());

        // And have normal SDT instead.
        msAssert.areEqual(1, doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
    }

    /// <summary>
    /// Demonstrates we can insert section breaks into normal SDT.
    /// </summary>
    @Test
    public void testSdtSectionImport() throws Exception
    {
        Document dstDoc = TestUtil.open("Model\\Markup\\TestSdtRange4 Dst.docx");
        Document srcDoc = TestUtil.open("Model\\Markup\\TestSdtRange4 Src.docx");
        DocumentBuilder builder = new DocumentBuilder(dstDoc);

        StructuredDocumentTag sdt = (StructuredDocumentTag)dstDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        builder.moveTo(sdt.getFirstChild());
        builder.insertDocument(srcDoc, ImportFormatMode.KEEP_DIFFERENT_STYLES);

        // Check original SDT has been split and removed.
        msAssert.isTrue(sdt.isRemoved());
        StructuredDocumentTagRangeStart sdtStart =
            (StructuredDocumentTagRangeStart)dstDoc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0,
                false);
        msAssert.isNotNull(sdtStart);
        StructuredDocumentTagRangeEnd sdtEnd =
            (StructuredDocumentTagRangeEnd)dstDoc.getLastSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END, 0,
                false);
        msAssert.isNotNull(sdtEnd);
    }

    /// <summary>
    /// Tests section break insertion into normal SDT.
    /// </summary>
    /// <remarks>
    /// Currently we split SDT content between sections leaving SDT with partial content in first section.
    /// Maybe we need to transform SDT into ranged but lets postpone for a while.
    /// </remarks>
    @Test
    public void testSdtInsertSectionBreak() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestSdtRange4 Dst.docx");

        DocumentBuilder builder = new DocumentBuilder(doc);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        builder.moveTo(sdt.getFirstChild());
        builder.insertSection(SectionStart.NEW_PAGE);

        msAssert.areEqual(2, doc.getSections().getCount());
        msAssert.areEqual(1, doc.getSections().get(0).getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
        msAssert.areEqual(0, doc.getSections().get(1).getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).getCount());
    }

    /// <summary>
    /// Tests case when ranged SDT is imported into another ranged SDT.
    /// </summary>
    @Test
    public void testRangedSdtImportToItself() throws Exception
    {
        final String TEST_NAME = "Model\\Markup\\TestSdtRange5";

        Document doc = TestUtil.open(TEST_NAME, LoadFormat.DOCX);

        // Originally we have one ranged SDT and it is ranged.
        NodeCollection sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true);
        msAssert.areEqual(1, sdts.getCount());

        // Go to content of ranged SDT and insert document clone into.
        DocumentBuilder builder = new DocumentBuilder(doc);

        builder.moveTo(sdts.get(0));
        builder.insertDocument(doc.deepClone(), ImportFormatMode.KEEP_DIFFERENT_STYLES);

        // Check document is saved/loaded properly.
        doc = TestUtil.saveOpen(doc, TEST_NAME, UnifiedScenario.DOCX_2_DOCX);

        sdts = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true);

        // Now we have two ranged SDTs.
        msAssert.areEqual(2, sdts.getCount());

        // Both ranged.
        StructuredDocumentTagRangeStart sdtRangeStart = (StructuredDocumentTagRangeStart)sdts.get(0);
        msAssert.areEqual(-1750807311, sdtRangeStart.getId());
        msAssert.areEqual("ExhibitB", sdtRangeStart.getTag());

        // Nested Sdt.Id updated during import.
        sdtRangeStart = (StructuredDocumentTagRangeStart)sdts.get(1);
        msAssert.areEqual(1, sdtRangeStart.getId());
        msAssert.areEqual("ExhibitB", sdtRangeStart.getTag());
    }

    /// <summary>
    /// Tests that structured document tag is preserved during Docx2Docx roundtrip.
    /// </summary>
    @Test
    public void test15659() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test15659.docx");

        // Document contains one normal SDT and one ranged SDT.
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual("ExhibitA", sdt.getTag());

        StructuredDocumentTagRangeStart sdtRangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        msAssert.areEqual("ExhibitB", sdtRangeStart.getTag());


        StructuredDocumentTagRangeEnd sdtRangeEnd = (StructuredDocumentTagRangeEnd)doc.getLastSection().getBody().getChildNodes(NodeType.ANY, false).get(3);
        msAssert.areEqual(sdtRangeStart.getId(), sdtRangeEnd.getId());
        msAssert.isTrue(msObject.referenceEquals(sdtRangeStart.getRangeEnd(), sdtRangeEnd));
    }

    /// <summary>
    /// WORDSNET-13519 Aspose.Words does not import StructuredDocumentTag containing section break
    /// </summary>
    @Test
    public void test13519() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test13519", UnifiedScenario.DOCX_2_DOCX);

        StructuredDocumentTagRangeStart tag = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();
        msAssert.areEqual("L2", tag.getTag());
        msAssert.areEqual("L2", tag.getTitle());
        msAssert.areEqual(MarkupLevel.BLOCK, tag.getLevel());

        // Check how range nodes located.
        msAssert.isTrue(msObject.referenceEquals(doc.getSections().get(0), tag.getAncestor(NodeType.SECTION)));
        msAssert.isTrue(msObject.referenceEquals(doc.getSections().get(1), tag.getRangeEnd().getAncestor(NodeType.SECTION)));
    }

    /// <summary>
    /// Relates to WORDSNET-13519 Checks that placeholder references are updated properly.
    /// </summary>
    @Test
    public void test13519A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test13519.docx");
        doc = doc.deepClone();
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        msAssert.areEqual(1, doc.getGlossaryDocument().getChildNodes(NodeType.ANY, false).getCount());
    }




    /// <summary>
    /// Demonstrates manual creation of ranged structured document tag.
    /// </summary>
    @Test
    public void testRangedSdtCreateManually() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\TestManual.docx");

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getFirstChild();

        // Say we want enclose both sections into structured document tag.
        sdt.convertToRange(doc.getSections().get(1).getBody().getFirstParagraph(), true);

        TestUtil.saveCheckGold(doc, "Model\\Markup\\TestManual Modified.docx");
    }


    /// <summary>
    /// Tests that range start node cannot be Paragraph node child.
    /// </summary>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCanInsert() throws Exception
    {
        Document doc = new Document();
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(
            new StructuredDocumentTagRangeStart(doc, new StructuredDocumentTag(doc, MarkupLevel.BLOCK)));
    }


    /// <summary>
    /// WORDSNET-9908 RichText Content Control with Section Break are lost
    /// Resolved per WORDSNET-15659
    /// </summary>
    @Test
    public void test9908() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test9908.docx");

        // Document contains ranged SDT.
        StructuredDocumentTagRangeStart sdtRangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);

        msAssert.areEqual("contentcontrolwithsectionbreak", sdtRangeStart.getTag());
        msAssert.areEqual("contentcontrolwithsectionbreak", sdtRangeStart.getTitle());
        msAssert.areEqual(-354802571, sdtRangeStart.getId());
    }


    /// <summary>
    /// WORDSNET-7150 SDT controls are getting lost during open/save
    /// Resolved per WORDSNET-15659
    /// </summary>
    @Test
    public void test7150() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test7150.docx");

        // Document contains two ranged SDT.
        StructuredDocumentTagRangeStart sdtRangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        msAssert.areEqual("", sdtRangeStart.getTag());
        msAssert.areEqual("", sdtRangeStart.getTitle());
        msAssert.areEqual(12315546, sdtRangeStart.getId());

        sdtRangeStart = (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 1, true);
        msAssert.areEqual("", sdtRangeStart.getTag());
        msAssert.areEqual("", sdtRangeStart.getTitle());
        msAssert.areEqual(12315552, sdtRangeStart.getId());
    }


    /// <summary>
    /// WORDSNET-19173 Content Control gets lost during open/save a DOCX
    /// Cannot reproduce issue.
    /// </summary>
    @Test
    public void test19173() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test19173.docx");

        IStructuredDocumentTag sdt = doc.getRange().getStructuredDocumentTags().get(0);

        msAssert.areEqual("IN:Schedule of Activities", sdt.getTag());
        msAssert.areEqual("Schedule of Activities", sdt.getTitle());
        msAssert.areEqual(-1419859338, sdt.getId());
    }

    /// <summary>
    /// WORDSNET-19017 Content control lost during open/save a DOCX
    /// Cannot reproduce issue.
    /// </summary>
    @Test
    public void test19017() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test19017.docx");

        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        ArrayList<StructuredDocumentTag> tags = nodes.<StructuredDocumentTag>toList();

        msAssert.areEqual(3, tags.size());

        msAssert.areEqual("type=ReportObject;reportobjectid=464;", tags.get(0).getTag());
        msAssert.areEqual("", tags.get(0).getTitle());
        msAssert.areEqual(512048618, tags.get(0).getId());

        msAssert.areEqual("type=ReportObject;reportobjectid=462;", tags.get(1).getTag());
        msAssert.areEqual("", tags.get(1).getTitle());
        msAssert.areEqual(1197336250, tags.get(1).getId());

        msAssert.areEqual("type=ReportObject;reportobjectid=463;", tags.get(2).getTag());
        msAssert.areEqual("", tags.get(2).getTitle());
        msAssert.areEqual(1313943143, tags.get(2).getId());
    }

    /// <summary>
    /// WORDSNET-13601 (fixed partially) - Aspose.Words does not import content controls containing Table
    /// OOXML import/export resolved per WORDSNET-15659
    /// </summary>
    @Test
    public void test13601() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test13601.docx");

        // Document contains two ranged SDT.
        NodeCollection nodes = doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true);
        ArrayList<StructuredDocumentTagRangeStart> tags = nodes.<StructuredDocumentTagRangeStart>toList();

        msAssert.areEqual(2, tags.size());

        msAssert.areEqual("", tags.get(0).getTitle());
        msAssert.areEqual(1343885161, tags.get(0).getId());

        msAssert.areEqual("תוכן", tags.get(1).getTitle());
        msAssert.areEqual(1698058318, tags.get(1).getId());
    }

    /// <summary>
    /// WORDSNET-20690 Empty Rich Text CustomXmlPart control pdf save issue.
    /// Added special handling for empty and whitespace only strings.
    /// </summary>
    @Test
    public void test20690() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20690.docx");

        CustomXmlPart customXmlPart = doc.getCustomXmlParts().get(1);
        String customXmlPartData = com.aspose.ms.System.Text.Encoding.getUTF8().getString(customXmlPart.getData());

        // Set bounded value to single space.
        customXmlPartData = customXmlPartData.replace(">SimpleBooleanObject19.Value", "> ");
        customXmlPart.setData(com.aspose.ms.System.Text.Encoding.getUTF8().getBytes(customXmlPartData));

        StructuredDocumentTag sdt =
            (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        sdt.getXmlMapping().updateContent();

        msAssert.areEqual(" \r", sdt.getText());
    }

    /// <summary>
    /// WORDSNET-21062 Implemented public <see cref="StructuredDocumentTagRangeStart.XmlMapping"/>
    /// property.
    /// </summary>
    @Test
    public void testSdtRangeXmlMapping() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21062.docx");

        StructuredDocumentTagRangeStart rangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        XmlMapping xmlMapping = rangeStart.getXmlMapping();

        msAssert.areEqual("{59080277-8942-4087-8692-E264018C2D64}", xmlMapping.getStoreItemId());
        msAssert.areEqual("/I[1]/C[1]", xmlMapping.getXPath());

        // Generate document to put to custom XMl part as source of SDT XML mapping.
        DocumentBuilder builder = new DocumentBuilder();
        builder.writeln("First section.");
        builder.insertSection(SectionStart.NEW_PAGE);
        builder.writeln("Next section.");

        String sdtDocXml;
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            builder.getDocument().save(stream, SaveFormat.FLAT_OPC);
            // Get data without BOM.
            final int BOM_LENGTH = 3;
            sdtDocXml = Encoding.getUTF8().getString(stream.toArray(), BOM_LENGTH, (int)(stream.getLength() - BOM_LENGTH));
        }
        finally { if (stream != null) stream.close(); }

        // Use XmlDocument just to encode sdtDocXml to put into XML.
        org.w3c.dom.Document xmlDoc = XmlUtilPal.loadXml("<ns:parent xmlns:ns='someNamespace'><ns:child></ns:child></ns:parent>",true);
        xmlDoc.getChildNodes().item(0).getChildNodes().item(0).setTextContent(sdtDocXml);

        String newPartId = Guid.newGuid().toString("B");
        CustomXmlPart newPart = doc.getCustomXmlParts().add(newPartId, XmlUtilPal.getOuterXml(xmlDoc));

        xmlMapping.setMapping(newPart, "/ns:parent/ns:child", "xmlns:ns='someNamespace'");

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test21062", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        rangeStart = (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        xmlMapping = rangeStart.getXmlMapping();

        msAssert.areEqual(newPartId, xmlMapping.getStoreItemId());
        msAssert.areEqual("/ns:parent/ns:child", xmlMapping.getXPath());
        msAssert.areEqual("xmlns:ns='someNamespace'", xmlMapping.getPrefixMappings());
    }

    /// <summary>
    /// WORDSNET-21062 Checks that <see cref="StructuredDocumentTagRangeStart.XmlMapping"/> data is copied
    /// correctly on importing a document containing <see cref="StructuredDocumentTagRangeStart"/> mapped to XML part.
    /// </summary>
    @Test
    public void testImportXmlMappedSdtRange() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Markup\\Test21062.docx");
        Document dstDoc = new Document();
        dstDoc.removeAllChildren();
        dstDoc.appendDocument(srcDoc, ImportFormatMode.USE_DESTINATION_STYLES);

        StructuredDocumentTagRangeStart rangeStart =
            (StructuredDocumentTagRangeStart)dstDoc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        XmlMapping xmlMapping = rangeStart.getXmlMapping();

        msAssert.areEqual("{59080277-8942-4087-8692-E264018C2D64}", xmlMapping.getStoreItemId());
        msAssert.isNotNull(xmlMapping.getCustomXmlPart());
        msAssert.areEqual("/I[1]/C[1]", xmlMapping.getXPath());
    }

    /// <summary>
    /// WORDSNET-20969 Bullet is lost after re-saving DOCX
    /// Formatting of last paragraph of SDT content should be preserved.
    /// </summary>
    @Test
    public void test20969() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20969.docx");
        doc.updateListLabels();

        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        msAssert.areEqual("\uf0b7", header.getParagraphs().get(0).getListLabel().getLabelString());
        msAssert.areEqual("\uf0b7", header.getParagraphs().get(1).getListLabel().getLabelString());
        msAssert.areEqual("\uf0b7", header.getParagraphs().get(2).getListLabel().getLabelString());
        msAssert.areEqual("", header.getParagraphs().get(3).getListLabel().getLabelString());
        msAssert.areEqual("\uf0b7", header.getParagraphs().get(4).getListLabel().getLabelString());
    }

    /// <summary>
    /// Additional test for WORDSNET-20969.
    /// </summary>
    @Test
    public void test20969A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20969A.docx");
        HeaderFooter header = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);

        // Tested on Microsoft® Word 2019 MSO (Version 2309 Build 16.0.16827.20166) 32-bit
        // Now last SDT paragraph is moved outside of SDT before update.
        // I think the reason is attempt to preserve at least one paragraph in story while SDT is being updated.
        msAssert.isNull(header.getParagraphs().get(3).getParaPr().get(ParaAttr.ALIGNMENT));
        msAssert.areEqual(ParagraphAlignment.CENTER, header.getParagraphs().get(4).getParaPr().get(ParaAttr.ALIGNMENT));
    }

    /// <summary>
    /// Additional test for WORDSNET-20969.
    /// </summary>
    @Test
    public void test20969B() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20969B.docx");
        Story headerPrimary = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        Paragraph lastPara = headerPrimary.getLastParagraph();

        msAssert.areEqual(400, lastPara.getParaPr().get(ParaAttr.SPACE_AFTER));
    }

    /// <summary>
    /// Additional test for WORDSNET-20969.
    /// </summary>
    @Test
    public void test20969C() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20969C.docx");

        // Check primary header.
        Story headerPrimary = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        StructuredDocumentTag sdt = (StructuredDocumentTag)headerPrimary.getFirstChild();

        ArrayList<Paragraph> paras = sdt.getChildNodes(NodeType.PARAGRAPH, false).<Paragraph>toList();
        msAssert.areEqual(4, paras.size());

        msAssert.areEqual("Header 1\r", paras.get(0).getText());
        msAssert.areEqual("Header 2\r", paras.get(1).getText());
        msAssert.areEqual("Header 3\r", paras.get(2).getText());
        msAssert.areEqual("Header 4\r", paras.get(3).getText());

        for (Paragraph para : new Paragraph[] { paras.get(0), paras.get(1), paras.get(2) })
        {
            msAssert.areEqual(0x0f, para.getParaPr().get(ParaAttr.ISTD));
            msAssert.areEqual(0, para.getParaPr().get(ParaAttr.LIST_LEVEL));
            msAssert.areEqual(1, para.getParaPr().get(ParaAttr.LIST_ID));
        }

        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.LIST_ID));

        Paragraph lastPara = headerPrimary.getLastParagraph();
        msAssert.areEqual(sdt.getNextSibling(), lastPara);
        msAssert.areEqual("\r", lastPara.getText());

        msAssert.areEqual(200, lastPara.getParaPr().get(ParaAttr.SPACE_AFTER));
        msAssert.isNull(lastPara.getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.isNull(lastPara.getParaPr().get(ParaAttr.LIST_ID));

        // Check primary footer.
        Story footerPrimary = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
        sdt = (StructuredDocumentTag)footerPrimary.getFirstChild();

        paras = sdt.getChildNodes(NodeType.PARAGRAPH, false).<Paragraph>toList();
        msAssert.areEqual(4, paras.size());

        // All paragraph except last paragraph of SDT should have same formatting.
        for (Paragraph para : new Paragraph[] { paras.get(0), paras.get(1), paras.get(2), footerPrimary.getLastParagraph() })
        {
            msAssert.areEqual(0x11, para.getParaPr().get(ParaAttr.ISTD));
            msAssert.areEqual(0, para.getParaPr().get(ParaAttr.LIST_LEVEL));
            msAssert.areEqual(2, para.getParaPr().get(ParaAttr.LIST_ID));
        }

        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.isNull(paras.get(3).getParaPr().get(ParaAttr.LIST_ID));
    }

    /// <summary>
    /// Additional test for WORDSNET-20969.
    /// </summary>
    @Test
    public void test20969D() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test20969D.docx");

        Story headerPrimary = doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        StructuredDocumentTag sdt = (StructuredDocumentTag)headerPrimary.getRange().getStructuredDocumentTags().get(0);

        // Last paragraph of SDT is moved out regardless of first paragraph in header.
        Paragraph para = (Paragraph)sdt.getNextSibling();
        msAssert.areEqual(200, para.getParaPr().get(ParaAttr.SPACE_AFTER));
    }

    /// <summary>
    /// WORDSNET-21138 Content in last cell of table get duplicated after breaking the mapping between
    /// Content Control and CustomXMLPart.
    /// Fixed by removing all SDT child nodes when pasting content from SDT XML into a cell.
    /// </summary>
    /// <remarks>
    /// AM. Seems Microsoft is constantly changing logic.
    /// Tested on Microsoft® Word 2019 MSO (Version 2309 Build 16.0.16827.20130) 32-bit.
    /// </remarks>
    @Test
    public void test21138() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21138.docx");
        doc.updateListLabels();

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(0);

        // SDT content has got 4 paragraphs after the inserting from the SDT XML.
        Cell cell = ms.as(sdt.getFirstChild(), Cell.class);
        msAssert.areEqual(4, cell.getParagraphs().getCount());

        // The third paragraph was copied from the SDT XML with using existing numbering with ListId equals to 2.
        Paragraph para = cell.getParagraphs().get(2);
        msAssert.areEqual("Bullet 2\r", para.getText());
        msAssert.areEqual(0x16, para.getParaPr().get(ParaAttr.ISTD));
        msAssert.areEqual(2, para.getParaPr().get(ParaAttr.LIST_ID));
        msAssert.areEqual(0, para.getParaPr().get(ParaAttr.LIST_LEVEL));
        msAssert.areEqual("\uf0b7", para.getListLabel().getLabelString());

        // The fourth paragraph was copied from the SDT XML without numbering.
        // This paragraph received numbering of the previous content with ListId equals to 2.
        // (This is behaviour of the MS Word.)
        para = cell.getParagraphs().get(3);
        msAssert.areEqual("Bullet 3\u0007", para.getText());
        msAssert.isNull(para.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(para.getParaPr().get(ParaAttr.LIST_ID));
        msAssert.isNull(para.getParaPr().get(ParaAttr.LIST_LEVEL));
    }


    /// <summary>
    /// WORDSNET-21246 Provide more properties/methods in StructuredDocumentTagRangeStart Class.
    /// Access Common Properties of an structured document tag range start.
    /// </summary>
    @Test
    public void testAccessSDTRangeStartCommonProperties() throws Exception
    {
        final String PLACEHOLDER_NAME = "Custom placeholder name";
        final String TAG_NAME = "Custom tag";
        final String TITLE_NAME = "Custom title";

        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.PLAIN_TEXT, MarkupLevel.BLOCK);
        BuildingBlock block =
            doc.getGlossaryDocument().getBuildingBlock(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT,
                                                  SdtPlaceholderManager.BUILDING_BLOCK_CATEGORY,
                                                  SdtPlaceholderManager.TEXT_PLACEHOLDER_NAME);
        block.setName(PLACEHOLDER_NAME);
        doc.getFirstSection().getBody().appendChild(sdt);
        doc.appendChild(new Section(doc)).appendChild(new Body(doc));
        Paragraph para = new Paragraph(doc);
        doc.getLastSection().getBody().appendChild(para);

        StructuredDocumentTagRangeStart sdtRangeStart = sdt.convertToRange(para, true);
        sdtRangeStart.setColor(msColor.getAzure());
        sdtRangeStart.setPlaceholderName(PLACEHOLDER_NAME);
        sdtRangeStart.setTag(TAG_NAME);
        sdtRangeStart.setTitle(TITLE_NAME);
        sdtRangeStart.isShowingPlaceholderText(true);
        sdtRangeStart.setLockContents(true);
        sdtRangeStart.setLockContentControl(true);

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\TestSDTStartSetProperties.docx", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        sdtRangeStart = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getChildNodes(NodeType.ANY, false).get(1);
        block = doc.getGlossaryDocument().getBuildingBlock(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT,
                                                  SdtPlaceholderManager.BUILDING_BLOCK_CATEGORY,
                                                  SdtPlaceholderManager.TEXT_PLACEHOLDER_NAME);

        msAssert.areEqual(msColor.getAzure().getRGB(), sdtRangeStart.getColor().getRGB());
        msAssert.areEqual(PLACEHOLDER_NAME, sdtRangeStart.getPlaceholderName());
        msAssert.areEqual(block, sdtRangeStart.getPlaceholder());
        msAssert.areEqual(TAG_NAME, sdtRangeStart.getTag());
        msAssert.areEqual(TITLE_NAME, sdtRangeStart.getTitle());
        msAssert.isTrue(sdtRangeStart.isShowingPlaceholderText());
        msAssert.isTrue(sdtRangeStart.getLockContents());
        msAssert.isTrue(sdtRangeStart.getLockContentControl());
    }

    /// <summary>
    /// WORDSNET-21436 w14:paraId and w14:textId removed after re-saving the document.
    /// Fixed by copying ParaId and TextId properties of the paragraph when pasting content from SDT XML.
    /// </summary>
    @Test
    public void test21436() throws Exception
    {
        final String FILE_NAME_WITHOUT_EXTENSION = "Model\\Markup\\Test21436";

        Document doc = TestUtil.open(FILE_NAME_WITHOUT_EXTENSION, LoadFormat.DOCX);
        OoxmlSaveOptions so = new OoxmlSaveOptions(); { so.isTestMode(true); so.setWriteExtendedIds(true); }
        doc = TestUtil.saveOpen(doc, FILE_NAME_WITHOUT_EXTENSION, so, false);

        Paragraph para = ms.as((ms.as(doc.getFirstSection().getBody().getFirstChild(), StructuredDocumentTag.class)).getFirstChild(), Paragraph.class);
        msAssert.areEqual(340224409, para.getParaId());
        msAssert.areEqual(359770520, para.getTextId());
    }



    /// <summary>
    /// WORDSNET-21246 Provide more properties/methods in StructuredDocumentTagRangeStart Class.
    /// LastChild property added. AppendChild() added.
    /// </summary>
    @Test
    public void test21246_LastAppendChild() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_1.docx");

        // First level.
        StructuredDocumentTagRangeStart sdtStart =
            (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getChild(
                NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);
        Paragraph expLastChild = doc.getSections().get(2).getBody().getParagraphs().get(1);
        msAssert.areEqual("3 para inside sdtContent after SectionBreak para.\r", expLastChild.getText());
        msAssert.areEqual(expLastChild, sdtStart.getLastChild());
        Run run = new Run(doc);
        run.setText("First level.");
        Paragraph para = new Paragraph(doc);
        para.appendChild(run);
        sdtStart.appendChild(para);
        msAssert.areEqual(para, sdtStart.getLastChild());
    }

    /// <summary>
    /// Relates to WORDSNET-21246
    /// </summary>
    @Test
    public void test21246_LastAppendChildNested() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_1.docx");
        // Nested level.
        StructuredDocumentTagRangeStart sdtStart = (StructuredDocumentTagRangeStart)doc.getSections().get(1).getBody().getChild(
            NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);
        Paragraph expLastChild = doc.getSections().get(2).getBody().getFirstParagraph();
        msAssert.areEqual("11 para insideInside sdtContent after SectionBreak para.", expLastChild.getText().substring((0), (0) + (56)));
        msAssert.areEqual(expLastChild, sdtStart.getLastChild());
        Run run = new Run(doc);
        run.setText("Nested level.");
        Paragraph para = new Paragraph(doc);
        para.appendChild(run);
        sdtStart.appendChild(para);
        msAssert.areEqual(para, sdtStart.getLastChild());
    }

    /// <summary>
    /// WORDSNET-21770 StoreItemChecksum removed after re-saving document.
    /// The new property StoreItemChecksum has been added to the SDT XmlMapping.
    /// </summary>
    @Test
    public void test21770() throws Exception
    {
        // This property is from the Word preview version, there is no spec for it.
        // Therefore at the moment only property reading/writing is implemented.
        // There is no any related behavior or public API changes, but it can be changed in the future.

        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test21770", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        StructuredDocumentTag sdt = ms.as(doc.getFirstSection().getBody().getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(0), StructuredDocumentTag.class);

        msAssert.areEqual("D2D3xg==", sdt.getXmlMapping().getStoreItemChecksum());
    }

    /// <summary>
    /// WORDSNET-21591 Random content copied to the output after converting the DOCX file with Content Controls
    /// Do not update content of SDT loaded with mapped document in case of missing custom XML part.
    /// </summary>
    @Test
    public void test21591() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21591.docx");

        StructuredDocumentTag tag = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);
        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG, tag.getParentNode().getNodeType());

        msAssert.areEqual("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. " +
                            "Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.\r" +
                            "Nunc viverra imperdiet enim. Fusce est. Vivamus a tellus.\r", tag.getText());
    }


    /// <summary>
    /// Tests nested ranged SDT ChildNodes property.
    /// </summary>
    @Test
    public void test21189A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21189A.docx");
        StructuredDocumentTagRangeStart outerTag = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();
        NodeCollection childNodes = outerTag.getChildNodes();

        // Check every child node of outer tag.
        msAssert.areEqual(3, childNodes.getCount());

        msAssert.areEqual(NodeType.PARAGRAPH, childNodes.get(0).getNodeType());
        msAssert.isTrue(childNodes.get(0).getText().startsWith("First"));

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, childNodes.get(1).getNodeType());

        msAssert.areEqual(NodeType.PARAGRAPH, childNodes.get(2).getNodeType());
        msAssert.isTrue(childNodes.get(2).getText().startsWith("Second"));

        // Check every child node of inner tag.
        StructuredDocumentTagRangeStart innerTag =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 1, true);
        childNodes = innerTag.getChildNodes();

        msAssert.areEqual(2, childNodes.getCount());

        msAssert.areEqual(NodeType.PARAGRAPH, childNodes.get(0).getNodeType());
        msAssert.isTrue(childNodes.get(0).getText().startsWith("Inner First"));

        msAssert.areEqual(NodeType.PARAGRAPH, childNodes.get(1).getNodeType());
        msAssert.isTrue(childNodes.get(1).getText().startsWith("Inner Second"));
    }

    /// <summary>
    /// Relates to WORDSNET-21189
    /// Tests that ChildNodes and flat GetChildNodes are equal.
    /// </summary>
    @Test
    public void test21189GetChildNodes() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21189A.docx");

        for (StructuredDocumentTagRangeStart tag :
            (Iterable<StructuredDocumentTagRangeStart>) doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, true))
        {
            NodeCollection col1 = tag.getChildNodes();
            NodeCollection col2 = tag.getChildNodes(NodeType.ANY, false);

            msAssert.areEqual(col1.getCount(), col2.getCount());

            for (int i = 0; i < col1.getCount(); i++)
                msAssert.isTrue(msObject.referenceEquals(col1.get(i), col2.get(i)));
        }
    }

    /// <summary>
    /// Relates to WORDSNET-21189
    /// Tests deep collection behavior.
    /// </summary>
    @Test
    public void test21189GetChildNodesDeep() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21189A.docx");
        StructuredDocumentTagRangeStart outerTag = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();
        NodeCollection childNodes = outerTag.getChildNodes(NodeType.ANY, true);

        // Check few child node of outer tag.
        msAssert.areEqual(12, childNodes.getCount());

        msAssert.areEqual(NodeType.RUN, childNodes.get(1).getNodeType());
        msAssert.areEqual("First", childNodes.get(1).getText());

        msAssert.areEqual(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, childNodes.get(2).getNodeType());

        msAssert.areEqual(NodeType.RUN, childNodes.get(4).getNodeType());
        msAssert.areEqual("Inner ", childNodes.get(4).getText());

        msAssert.areEqual(NodeType.RUN, childNodes.get(5).getNodeType());
        msAssert.areEqual("First", childNodes.get(5).getText());

        msAssert.areEqual(NodeType.RUN, childNodes.get(7).getNodeType());
        msAssert.areEqual("Inner ", childNodes.get(7).getText());

        msAssert.areEqual(NodeType.RUN, childNodes.get(8).getNodeType());
        msAssert.areEqual("Second", childNodes.get(8).getText());
    }

    /// <summary>
    /// Relates to WORDSNET-21189
    /// Tests deep collection behavior if NodeType specified.
    /// </summary>
    @Test
    public void test21189GetChildNodesDeepNodeType() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21189A.docx");
        StructuredDocumentTagRangeStart outerTag = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getFirstChild();
        NodeCollection childNodes = outerTag.getChildNodes(NodeType.RUN, true);

        // Check few child node of outer tag.
        msAssert.areEqual(6, childNodes.getCount());

        msAssert.areEqual(NodeType.RUN, childNodes.get(0).getNodeType());
        msAssert.areEqual("First", childNodes.get(0).getText());

        msAssert.areEqual(NodeType.RUN, childNodes.get(1).getNodeType());
        msAssert.areEqual("Inner ", childNodes.get(1).getText());
    }

    /// <summary>
    /// WORDSNET-21636 Content formatting changed after delete XmlMapping.
    /// Several changes have been made in the SDT XML mapping.
    /// </summary>
    @Test
    public void test21636() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21636.docx");

        StructuredDocumentTag tag1 = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        StructuredDocumentTag tag2 = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);
        StructuredDocumentTag tag3 = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 2, true);

        // The first sdt should contain 4 paragraphs, and the last para should be list
        msAssert.areEqual(4, tag1.getChildNodes(NodeType.ANY, false).getCount());
        Paragraph theLastPara = ms.as(tag1.getChildNodes(NodeType.ANY, false).get(3), Paragraph.class);

        msAssert.isTrue(theLastPara.isListItem());

        // The second sdt should contain 4 paragraphs
        msAssert.areEqual(5, tag2.getChildNodes(NodeType.ANY, false).getCount());

        // The third sdt should contain 5 paragraphs
        msAssert.areEqual(6, tag3.getChildNodes(NodeType.ANY, false).getCount());
    }

    /// <summary>
    /// WORDSNET-21607 New style introduced after adding a new SDT.
    /// Fixed via using the same logic for inserting placeholder content as when inserting document into SDT.
    /// </summary>
    @Test
    public void test21607() throws Exception
    {
        final String FILE_NAME_WITHOUT_EXTENSION = "Model\\Markup\\Test21607";
        final String PLACEHOLDER_NAME = "DFF1698259834A289927FD7FC8BA2CC7";
        final String EXPECTED_PLACEHOLDER_TEXT = "Click or tap here to enter text.\f";
        final int EXPECTED_ISTD = 16;

        Document doc = TestUtil.open(FILE_NAME_WITHOUT_EXTENSION, LoadFormat.DOCX);
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        doc.getFirstSection().getBody().appendChild(sdt);

        // Setting the placeholder name triggers the SDT content update.
        sdt.setPlaceholderName((ms.as(doc.getGlossaryDocument().getChildNodes(NodeType.ANY, false).get(0), BuildingBlock.class)).getName());

        // Placeholder style has NOT been imported.
        msAssert.isNull(doc.getStyles().getByName(PLACEHOLDER_NAME, false));

        // But this style is in the GlossaryDocument.Styles.
        msAssert.isNotNull(doc.getGlossaryDocument().getStyles().getByName(PLACEHOLDER_NAME, false));

        Paragraph para = ms.as(sdt.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class);

        // There is no style in the updated SDT content.
        msAssert.isFalse(para.getParaPr().containsKey(ParaAttr.ISTD));
        msAssert.areEqual(EXPECTED_PLACEHOLDER_TEXT, para.getText());

        para = sdt.getPlaceholder().getFirstSection().getBody().getFirstParagraph();

        // The style is still in the placeholder.
        msAssert.areEqual(EXPECTED_ISTD, (/*int*/Integer)para.getParaPr().get(ParaAttr.ISTD));
        msAssert.areEqual(PLACEHOLDER_NAME, sdt.getPlaceholderName());

        // Saving and reading show the same the result.
        doc = TestUtil.saveOpen(doc, FILE_NAME_WITHOUT_EXTENSION, UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        sdt = ms.as(doc.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true).get(1), StructuredDocumentTag.class);

        msAssert.isNull(doc.getStyles().getByName(PLACEHOLDER_NAME, false));
        msAssert.isNotNull(doc.getGlossaryDocument().getStyles().getByName(PLACEHOLDER_NAME, false));
        msAssert.areEqual(PLACEHOLDER_NAME, sdt.getPlaceholderName());

        para = ms.as(sdt.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class);
        msAssert.isFalse(para.getParaPr().containsKey(ParaAttr.ISTD));
        msAssert.areEqual(EXPECTED_PLACEHOLDER_TEXT, para.getText());

        para = sdt.getPlaceholder().getFirstSection().getBody().getFirstParagraph();
        msAssert.areEqual(EXPECTED_ISTD, (/*int*/Integer)para.getParaPr().get(ParaAttr.ISTD));
        msAssert.areEqual(EXPECTED_PLACEHOLDER_TEXT, para.getText());
    }


    /// <summary>
    /// WORDSNET-21246
    /// Added public method RemoveSelfOnly to ranged SDT.
    /// </summary>
    @Test
    public void testRangeRemoveSelfOnly() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_2.docx");

        StructuredDocumentTagRangeStart start = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getChild(
            NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);
        StructuredDocumentTagRangeEnd end = start.getRangeEnd();
        Node pseudoChild = start.getNextSibling();
        start.removeSelfOnly();

        msAssert.isTrue(start.isRemoved());
        msAssert.isTrue(end.isRemoved());
        msAssert.isFalse(pseudoChild.isRemoved());
    }

    /// <summary>
    /// WORDSNET-21246
    /// Added public method RemoveAllChildren to ranged SDT.
    /// </summary>
    @Test
    public void testRangeRemoveAllChildren() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_2", LoadFormat.DOCX);

        // First level.
        StructuredDocumentTagRangeStart start = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getChild(
            NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);
        msAssert.areEqual(0, start.getId());

        Section sect = ms.as(start.getParentNode().getParentNode(), Section.class);
        msAssert.areEqual(6240, sect.getSectPr().getPageWidth());

        start.removeAllChildren();

        // Original StructuredDocumentTagRangeStart jumps into the other section.
        start = (StructuredDocumentTagRangeStart)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START,
            0, false);
        sect = ms.as(start.getParentNode().getParentNode(), Section.class);
        msAssert.areEqual(12240, sect.getSectPr().getPageWidth());

        // Now both range nodes are located in one section. Check that RemoveAllChildren does not fail.
        msAssert.isTrue(msObject.referenceEquals(start.getNextSibling(), start.getRangeEnd()));
        start.removeAllChildren();

        // Check the same but with single node between.
        start.getParentNode().insertAfter(new Paragraph(doc), start);
        msAssert.isFalse(msObject.referenceEquals(start.getNextSibling(), start.getRangeEnd()));
        start.removeAllChildren();
        msAssert.isTrue(msObject.referenceEquals(start.getNextSibling(), start.getRangeEnd()));
    }

    /// <summary>
    /// WORDSNET-21246
    /// Test how RemoveAllChildren handles nested ranged SDT.
    /// </summary>
    @Test
    public void testRangeRemoveAllChildrenNested() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_2.docx");

        int nodeCountBefore = doc.getChildNodes(NodeType.ANY, true).getCount();
        msAssert.areEqual(28, nodeCountBefore);
        // Nested level.
        StructuredDocumentTagRangeStart start = (StructuredDocumentTagRangeStart)doc.getSections().get(1).getBody().getChild(
            NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);
        StructuredDocumentTagRangeEnd end = start.getRangeEnd();
        start.removeAllChildren();

        int nodeCountAfter = doc.getChildNodes(NodeType.ANY, true).getCount();

        msAssert.isFalse(start.isRemoved());
        msAssert.isFalse(end.isRemoved());
        msAssert.areEqual(end, start.getNextSibling());
        msAssert.areEqual(18, nodeCountAfter);
    }

    /// <summary>
    /// WORDSNET-21246
    /// Tests IEnumerable implementing.
    /// </summary>
    @Test (dataProvider = "testSdtRangeEnumerableDataProvider")
    public void testSdtRangeEnumerable(int sectionIndex, /*NodeType*/int[] types) throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test21246_2.docx");

        StructuredDocumentTagRangeStart start = (StructuredDocumentTagRangeStart)doc.getSections().get(sectionIndex).getBody().getChild(
            NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, false);

        int nodeCount = 0;
        for (Node node : start)
        {
            msAssert.areEqual(types[nodeCount], node.getNodeType());
            nodeCount++;
        }
        msAssert.areEqual(nodeCount, types.length);

        // Checks empty range.
        start.removeAllChildren();
        for (Node node : start)
            msAssert.isTrue(false);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testSdtRangeEnumerableDataProvider")
	public static Object[][] testSdtRangeEnumerableDataProvider() throws Exception
	{
		return new Object[][]
		{
			{0,  new /*NodeType*/int[] { NodeType.PARAGRAPH, NodeType.PARAGRAPH,
        NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, NodeType.PARAGRAPH, NodeType.PARAGRAPH,
        NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END, NodeType.PARAGRAPH }},
			{1,  new /*NodeType*/int[] { NodeType.PARAGRAPH, NodeType.PARAGRAPH }},
		};
	}

    /// <summary>
    /// WORDSNET-21246
    /// Tests public constructors for SDT range start and SDT range end.
    /// </summary>
    @Test
    public void testSdtRangePublicCtors() throws Exception
    {
        final String FILENAME = "Model\\Markup\\Test21246_3.docx";
        Document doc = TestUtil.open(FILENAME);

        StructuredDocumentTagRangeStart start = new StructuredDocumentTagRangeStart(doc, SdtType.REPEATING_SECTION_ITEM);
        StructuredDocumentTagRangeEnd end = new StructuredDocumentTagRangeEnd(doc, start.getId());

        doc.getFirstSection().getBody().insertAfter(start, doc.getFirstSection().getBody().getFirstParagraph());
        doc.getLastSection().getBody().insertBefore(end, doc.getLastSection().getBody().getLastParagraph());

        msAssert.areEqual(end, start.getRangeEnd());
        msAssert.areEqual(start.getId(), end.getId());
        msAssert.areEqual(SdtType.REPEATING_SECTION_ITEM, start.getSdtType());
        msAssert.areEqual(MarkupLevel.BLOCK, start.getLevel());

        TestUtil.save(doc, FILENAME, new OoxmlSaveOptions(SaveFormat.DOCX), true);
    }

    /// <summary>
    /// WORDSNET-22091 Resaving DOCX file corrupts table in output file.
    /// Fixed by keeping the reference node if it is a paragraph after the table in the SDT content.
    /// </summary>
    @Test
    public void test22091() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test22091", LoadFormat.DOCX);
        StructuredDocumentTag tag = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(2, tag.getChildNodes(NodeType.ANY, false).getCount());
        msAssert.areEqual(NodeType.TABLE, tag.getChildNodes(NodeType.ANY, false).get(0).getNodeType());

        // Check that mandatory paragraph node exists after table.
        msAssert.areEqual(NodeType.PARAGRAPH, tag.getChildNodes(NodeType.ANY, false).get(1).getNodeType());
    }

    /// <summary>
    /// WORDSNET-21818 Add feature to set content control (checkbox) checked/unchecked symbol.
    /// Corresponding public methods for the <see cref="StructuredDocumentTag"/> class have been implemented.
    /// </summary>
    /// <remarks>
    /// See <see cref="StructuredDocumentTag.SetCheckedSymbol(int, string)"/> and
    /// <see cref="StructuredDocumentTag.SetUncheckedSymbol(int, string)"/>.
    /// </remarks>
    @Test
    public void test21818() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.CHECKBOX, MarkupLevel.BLOCK);
        doc.getSections().get(0).getBody().appendChild(tag);
        tag.setCheckedSymbol(0x006F, "Wingdings");
        tag.setUncheckedSymbol(0x00FC, "Wingdings");

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test21818", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getSections().get(0).getBody().getChildNodes(NodeType.ANY, false).get(1);
        msAssert.areEqual(SdtType.CHECKBOX, sdt.getControlProperties().getType());
        SdtCheckBox checkbox = (SdtCheckBox)sdt.getControlProperties();
        msAssert.isFalse(checkbox.getChecked());
        SdtCheckBoxStateInfo stateInfo = checkbox.getCheckedStateInfo();
        msAssert.areEqual("Wingdings", stateInfo.getFontName());
        msAssert.areEqual(0x006F, stateInfo.getCharacterCode());
        stateInfo = checkbox.getUncheckedStateInfo();
        msAssert.areEqual("Wingdings", stateInfo.getFontName());
        msAssert.areEqual(0x00FC, stateInfo.getCharacterCode());
    }

    /// <summary>
    /// Related to WORDSNET-21818
    /// Checks thrown exceptions for unsuitable SDT types.
    /// </summary>
    @Test
    public void test21818_Exceptions() throws Exception
    {
        final String INVALID_OP_EXCEPTION_EXPECTED = "An InvalidOperationException was expected but was not thrown.";
        final String UNEXPECTED_EXCEPTION = "Unexpected exception.";

        Document doc = new Document();

        /*SdtType*/int[] sdtTypes = SdtType.getValues();
        for (/*SdtType*/int sdtType : sdtTypes)
        {
            if (sdtType == SdtType.CHECKBOX)
                continue;
            try
            {
                StructuredDocumentTag tag = new StructuredDocumentTag(doc, sdtType, MarkupLevel.BLOCK);
                try
                {
                    tag.setCheckedSymbol(0x006F, "Wingdings");
                    Assert.fail(INVALID_OP_EXCEPTION_EXPECTED);
                }
                catch (IllegalStateException e) { }
                catch(Exception e) { Assert.fail(UNEXPECTED_EXCEPTION); }
                try
                {
                    tag.setUncheckedSymbol(0x00FC, "Wingdings");
                    Assert.fail(INVALID_OP_EXCEPTION_EXPECTED);
                }
                catch (IllegalStateException e) { }
                catch(Exception e) { Assert.fail(UNEXPECTED_EXCEPTION); }
            }
            catch (IllegalArgumentException e) { }
            catch(Exception e) { Assert.fail(UNEXPECTED_EXCEPTION); }
        }
    }

    /// <summary>
    /// WORDSNET-22031 Content Control (dropdown) value is changed after re-saving DOCX.
    /// Fixed by adjusting the XmlMapping.StoreItemId of the imported SDT when loading the alt-chunk document.
    /// </summary>
    @Test (dataProvider = "test22031DataProvider")
    public void test22031(boolean doRoundTrip) throws Exception
    {
        final String TEST_FILE_NAME_WITHOUT_EXTENSION = "Model\\Markup\\Test22031";
        final String STORE_ITEM_ID = "{63F03B72-BEF0-4E2E-A36D-39BD14C5C261}";

        // XML mapping updated during both post loading and validation stages and it useful to test with/without roundtrip.
        Document doc = doRoundTrip
            ? TestUtil.openSaveOpen(TEST_FILE_NAME_WITHOUT_EXTENSION, UnifiedScenario.DOCX_2_DOCX_NO_GOLD)
            : TestUtil.open(TEST_FILE_NAME_WITHOUT_EXTENSION, LoadFormat.DOCX);

        msAssert.isNotNull(doc.getCustomXmlParts().getById(STORE_ITEM_ID));

        StructuredDocumentTag tag = ms.as(doc.getSections().get(0).getBody().getChildNodes(NodeType.ANY, false).get(0), StructuredDocumentTag.class);
        StructuredDocumentTag sdt = ms.as((ms.as(tag.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class)).getChildNodes(NodeType.ANY, false).get(3), StructuredDocumentTag.class);

        // The 1st SDT imported from the alt-chunk document refers to the existing CustomXmlPart.
        msAssert.areEqual(STORE_ITEM_ID, sdt.getXmlMapping().getStoreItemId());
        msAssert.areEqual("Existing Client", (ms.as(sdt.getChildNodes(NodeType.ANY, false).get(0), Run.class)).getText());

        sdt = ms.as((ms.as(tag.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class)).getChildNodes(NodeType.ANY, false).get(5), StructuredDocumentTag.class);

        // The 2nd SDT imported from the alt-chunk document refers to the existing CustomXmlPart.
        msAssert.areEqual(STORE_ITEM_ID, sdt.getXmlMapping().getStoreItemId());
        msAssert.areEqual("Test Contact", (ms.as(sdt.getChildNodes(NodeType.ANY, false).get(0), Run.class)).getText());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22031DataProvider")
	public static Object[][] test22031DataProvider() throws Exception
	{
		return new Object[][]
		{
			{false},
			{true},
		};
	}

    /// <summary>
    /// WORDSNET-20920 StructuredDocumentTag content is changed after UpdatePageLayout().
    /// Checks if the content has actually been replaced.
    /// </summary>
    @Test
    public void test20920() throws Exception
    {
        Document doc = TestUtil.open("Other\\DocumentPager\\TestGlossary", LoadFormat.DOCX);
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG,
            4, true);

        msAssert.isTrue(sdt.isShowingPlaceholderText());
        // No update page layout in FOSS.
        msAssert.isTrue(sdt.isShowingPlaceholderText());
        msAssert.areEqual("Type the document title", ((Run)sdt.getChildNodes(NodeType.ANY, false).get(0)).getText());
    }

    /// <summary>
    /// WORDSNET-22241 Checked symbol of content control is changed after first click. Fixed by
    /// adjusting the mechanism to determine whether to write the contents of Checkbox SDT as a symbol or text.
    /// </summary>
    @Test
    public void test22241() throws Exception
    {
        Document doc = new Document();
        StructuredDocumentTag tag = new StructuredDocumentTag(doc, SdtType.CHECKBOX, MarkupLevel.BLOCK);
        doc.getSections().get(0).getBody().appendChild(tag);
        tag.setCheckedSymbol(0x00A4, "Wingdings");
        tag.setUncheckedSymbol(0x00A6, "Wingdings");
        tag.setChecked(true);

        Run run = (ms.as(tag.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class)).getRuns().get(0);
        msAssert.isTrue(run.isWriteAsSymbol());
        msAssert.isFalse(run.isSymbolic());
        msAssert.areEqual("¤", run.getText());

        DocxExportContext ctx = new DocxExportContext(doc);
        org.w3c.dom.NodeList runs = ctx.getXmlDocument().getElementsByTagName("w:r");
        msAssert.areEqual(2, runs.item(0).getChildNodes().getLength());
        msAssert.areEqual("w:rPr", runs.item(0).getChildNodes().item(0).getNodeName());
        msAssert.areEqual("w:sym", runs.item(0).getChildNodes().item(1).getNodeName());
        msAssert.areEqual(2, runs.item(0).getChildNodes().item(1).getAttributes().getLength());
        msAssert.areEqual("Wingdings", runs.item(0).getChildNodes().item(1).getAttributes().getNamedItem("w:font").getNodeValue());
        msAssert.areEqual("00A4", runs.item(0).getChildNodes().item(1).getAttributes().getNamedItem("w:char").getNodeValue());

        // Re-saving and opening confirm the result.
        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test22241", UnifiedScenario.DOCX_2_DOCX);
        tag = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);
        run = (ms.as(tag.getChildNodes(NodeType.ANY, false).get(0), Paragraph.class)).getRuns().get(0);
        msAssert.isTrue(run.isWriteAsSymbol());
        msAssert.isFalse(run.isSymbolic());
        msAssert.areEqual("¤", run.getText());
    }


    /// <summary>
    /// WORDSNET-20808 AW does not import content controls containing Table.
    /// ToString() method for StructuredDocumentTagRangeStart node has been implemented.
    /// </summary>
    @Test
    public void test20808() throws Exception
    {
        final String STD_CONTENT =
            "A\r\n\r\n\r\n\r\n#\rZ\rY\rX\r1.\rZ1\rY1\rX1\r2.\rZ1\rY1\rX1\r3.\rZ1\rY1\rX1\r4.\rZ1\rY1\rX1\r5.\rZ1\rY1" +
            "\rX1\r6.\rZ1\rY1\rX1\r7.\rZ1\rY1\rX1\r8.\rZ1\rY1\rX1\r9.\rZ1\rY1\rX1\r10.\rZ1\rY1\rX1\r11.\r\n\rZ1\rY1" +
            "\rX1\r12.\rZ1\rY1\rX1\rCCC\r13.\rZ1\rY1\rX1\rBBB\r14.\rZ1\rY1\rX1\r15.\rZ1\rY1\rX1\r16.\rZ1\rY1\rX1\r17." +
            "\rZ1\rY1\rX1\r18.\rZ1\rY1\rX1\r\r\nA\r\n";

        Document doc = TestUtil.open("Model\\Markup\\Test20808.docx");
        StructuredDocumentTagRangeStart start = ms.as(doc.getFirstSection().getBody().getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START,
                0, false), StructuredDocumentTagRangeStart.class);
        msAssert.areEqual(STD_CONTENT, start.toString(new TxtSaveOptions()));
    }

    /// <summary>
    /// WORDSNET-22651 StructuredDocumentTag - control Appearance.
    /// Appearance property has been published.
    /// </summary>
    @Test
    public void test22651() throws Exception
    {
        final String TEST_FILE = "Model\\Markup\\Test22651.docx";
        Document doc = TestUtil.open(TEST_FILE);

        StructuredDocumentTag sdt1 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true), StructuredDocumentTag.class);
        StructuredDocumentTag sdt2 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true), StructuredDocumentTag.class);
        StructuredDocumentTag sdt3 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 2, true), StructuredDocumentTag.class);

        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, sdt1.getAppearance());
        msAssert.areEqual(SdtAppearance.TAGS, sdt2.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, sdt3.getAppearance());

        sdt1.setAppearance(SdtAppearance.TAGS);
        sdt2.setAppearance(SdtAppearance.HIDDEN);
        sdt3.setAppearance(SdtAppearance.BOUNDING_BOX);

        TestUtil.saveCheckGold(doc, TEST_FILE);
    }

    /// <summary>
    /// WORDSNET-22616 StructuredDocumentTagRangeStart\End pair removed from document model
    /// The last node of the range, with which the error occurred, from the point of view of the reader, is
    /// a paragraph in a table. An end node of an SDT range cannot be inserted at this position. Therefore,
    /// an appropriate ancestor of a range last node is now used as the end of the range.
    /// </summary>
    @Test
    public void test22616() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test22616", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        StructuredDocumentTagRangeStart rangeStart =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        msAssert.isNotNull(rangeStart);
        msAssert.areSame(doc.getFirstSection().getBody().getFirstChild(), rangeStart.getPreviousSibling());

        StructuredDocumentTagRangeEnd rangeEnd =
            (StructuredDocumentTagRangeEnd)doc.getLastSection().getBody().getChildNodes(NodeType.ANY, false).get(-2);

        msAssert.areSame(rangeStart.getRangeEnd(), rangeEnd);
        msAssert.areEqual(1479353831, rangeStart.getId());
        msAssert.areEqual(1479353831, rangeEnd.getId());
        msAssert.areEqual(SdtType.RICH_TEXT, rangeStart.getSdtType());
    }

    /// <summary>
    /// WORDSNET-22906 Rich text content control is not visible when the SetMapping function is used
    /// The SDT became empty after update and then lost on further opening/saving. Now the default contents is
    /// inserted if an SDT is empty after update, just like MS Word does.
    /// </summary>
    @Test
    public void test22906() throws Exception
    {
        final String FILE_NAME = "Model\\Markup\\Test22906.docx";
        Document document = TestUtil.open(FILE_NAME);

        // Save empty word document to FlatOPC.
        String flatOpcXml;
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            Document flatOpc = new Document();
            flatOpc.save(stream, SaveFormat.FLAT_OPC);
            Encoding encoding = new UTF8Encoding(true);
            byte[] data = stream.toArray();
            flatOpcXml = encoding.getString(data);
        }
        finally { if (stream != null) stream.close(); }

        // Map FlatOPC.
        String xml = msString.format("<i>{0}</i>", escapeXmlText(flatOpcXml));
        CustomXmlPart customXmlPart = document.getCustomXmlParts().add("test", xml);
        NodeCollection sdts = document.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(1, sdts.getCount());
        StructuredDocumentTag sdt = (StructuredDocumentTag)sdts.get(0);
        sdt.getXmlMapping().setMapping(customXmlPart, "/i", "");

        // Update and check for non-empty string to determine whether placeholder is inserted.
        sdt.getXmlMapping().updateContent();
        msAssert.areEqual("Click or tap here to enter text.\r", sdt.getText());

        document = TestUtil.saveOpen(document, FILE_NAME, null, false);
        sdts = document.getChildNodes(NodeType.STRUCTURED_DOCUMENT_TAG, true);
        msAssert.areEqual(1, sdts.getCount());
        msAssert.areEqual("Click or tap here to enter text.\r", sdts.get(0).getText());
    }

    /// <summary>
    /// Test the <see cref="Document.HasCoverPage"/> method finds cover page in different circumstances.
    /// </summary>
    @Test (dataProvider = "testCoverPageDataProvider")
    public void testCoverPage(String file) throws Exception
    {
        Document document = TestUtil.open(Path.combine("Model\\Markup", file));

        boolean hasCoverPage = document.hasCoverPage();

        msAssert.isTrue(hasCoverPage);
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "testCoverPageDataProvider")
	public static Object[][] testCoverPageDataProvider() throws Exception
	{
		return new Object[][]
		{
			{"TestCoverPage.docx"},
			{"TestCoverPageEdited.docx"},
			{"TestCoverPageMultiple.docx"},
			{"TestCoverPageAfterBreaks.docx"},
		};
	}

    /// <summary>
    /// Test the <see cref="Document.HasCoverPage"/> method ignore improper cover pages.
    /// </summary>
    @Test
    public void testNotCoverPage() throws Exception
    {
        Document document = TestUtil.open("Model\\Markup\\TestNotCoverPage.docx");

        boolean hasCoverPage = document.hasCoverPage();

        msAssert.isFalse(hasCoverPage);
    }

    /// <summary>
    /// WORDSNET-22883 Cross-reference not working after removing XML mapping
    /// Tests that <see cref="BookmarkStart.DisplacedBy"/> and <see cref="BookmarkEnd.DisplacedBy"/> properties of
    /// bookmarks that refer to a <see cref="StructuredDocumentTag"/> are reset after the SDT is removed.
    /// </summary>
    @Test (dataProvider = "test22883DataProvider")
    public void test22883(String fileName, String bookmarkName, boolean isStartInsideSdt) throws Exception
    {
        Document document = TestUtil.open(fileName);
        Document clone = document.deepClone();

        // Test StructuredDocumentTag.RemoveSelfOnly.

        Bookmark bookmark = document.getRange().getBookmarks().get(bookmarkName);
        String bookmarkText = bookmark.getText();

        BookmarkStart start = bookmark.getBookmarkStart();
        BookmarkEnd end = bookmark.getBookmarkEnd();
        StructuredDocumentTag sdt = isStartInsideSdt
            ? (StructuredDocumentTag)start.getParentNode()
            : (StructuredDocumentTag)start.getNextNonAnnotationSibling();

        sdt.removeSelfOnly(false);

        msAssert.areEqual(bookmarkText, bookmark.getText());
        msAssert.areEqual(DisplacedByType.UNSPECIFIED, start.getDisplacedBy());
        msAssert.areEqual(DisplacedByType.UNSPECIFIED, end.getDisplacedBy());

        // Test StructuredDocumentTag.Remove.

        bookmark = clone.getRange().getBookmarks().get(bookmarkName);
        start = bookmark.getBookmarkStart();
        end = bookmark.getBookmarkEnd();
        sdt = isStartInsideSdt
            ? (StructuredDocumentTag)start.getParentNode()
            : (StructuredDocumentTag)start.getNextNonAnnotationSibling();

        sdt.remove();

        msAssert.areEqual(DisplacedByType.UNSPECIFIED, start.getDisplacedBy());
        msAssert.areEqual(DisplacedByType.UNSPECIFIED, end.getDisplacedBy());
    }

	//JAVA-added data provider for test method
	@DataProvider(name = "test22883DataProvider")
	public static Object[][] test22883DataProvider() throws Exception
	{
		return new Object[][]
		{
			{"Model\\Markup\\Test22883.docx",  "_Ref85734851",  false},
			{"Model\\Markup\\Test22883.docx",  "_Ref85734870",  false},
		};
	}

    /// <summary>
    /// WORDSNET-22883 Cross-reference not working after removing XML mapping
    /// Tests that <see cref="BookmarkStart.DisplacedBy"/> and <see cref="BookmarkEnd.DisplacedBy"/> properties of
    /// bookmarks that refer to an SDT range are reset after start/end nodes of the range are removed.
    /// </summary>
    @Test ()
    public void test22883SdtRange() throws Exception
    {
        Document document = TestUtil.open("Model\\Markup\\Test22883SdtRange.docx");

        checkBookmarks(document, 4, "Text 1\fText 2", false, false);

        Node stdRangeStart = document.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        stdRangeStart.remove();

        Node stdRangeEnd = document.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END, 0, true);
        stdRangeEnd.remove();

        checkBookmarks(document, 4, "Text 1\fText 2", true, false);
    }

    /// <summary>
    /// WORDSNET-22883 Cross-reference not working after removing XML mapping
    /// Tests that <see cref="BookmarkStart.DisplacedBy"/> and <see cref="BookmarkEnd.DisplacedBy"/> properties of
    /// bookmarks that refer to a <see cref="StructuredDocumentTag"/> are NOT reset after the SDT is removed if
    /// there is another SDT they can refer to.
    /// </summary>
    @Test ()
    public void test22883MultipleSdts() throws Exception
    {
        Document document = TestUtil.open("Model\\Markup\\Test22883MultipleSdts.docx");
        Document clone = document.deepClone();

        checkBookmarks(document, 2, "123", false, true);

        StructuredDocumentTag std = (StructuredDocumentTag)document.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        std.removeSelfOnly();

        checkBookmarks(document, 2, "123", false, true);

        std = (StructuredDocumentTag)clone.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 1, true);
        std.removeSelfOnly();

        checkBookmarks(clone, 2, "123", false, true);
    }

    private static void checkBookmarks(Document document, int expectedCount, String expectedText,
        boolean checkForUnspecifiedDisplacedByType, boolean checkForNonUnspecifiedDisplacedByType) throws Exception
    {
        msAssert.areEqual(expectedCount, document.getRange().getBookmarks().getCount());

        for (Bookmark bookmark : document.getRange().getBookmarks())
        {
            msAssert.areEqual(expectedText, bookmark.getText());

            if (checkForUnspecifiedDisplacedByType)
            {
                msAssert.areEqual(DisplacedByType.UNSPECIFIED, bookmark.getBookmarkStart().getDisplacedBy());
                msAssert.areEqual(DisplacedByType.UNSPECIFIED, bookmark.getBookmarkEnd().getDisplacedBy());
            }

            if (checkForNonUnspecifiedDisplacedByType)
            {
                msAssert.areNotEqual(DisplacedByType.UNSPECIFIED, bookmark.getBookmarkStart().getDisplacedBy());
                msAssert.areNotEqual(DisplacedByType.UNSPECIFIED, bookmark.getBookmarkEnd().getDisplacedBy());
            }
        }
    }

    /// <summary>
    /// WORDSNET-23213 The end-of-cell marker has been removed from the new row.
    /// When updating TextBox SDT, we should not remove last empty paragraph, if it is numbered.
    /// </summary>
    @Test
    public void test23213() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test23213.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        CustomXmlPart cxp = doc.getCustomXmlParts().get(0);
        sdt.getXmlMapping().setMapping(cxp, "/ns0:I[1]", "xmlns:ns0='urn:in:item1'");

        // Force to update SDT content.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        // Check that cell marker is not deleted in problematic cell.
        Cell cell = doc.getFirstSection().getBody().getTables().get(0).getFirstRow().getCells().get(1);
        msAssert.isFalse(cell.getLastParagraph().isDeleteRevision());
        msAssert.areEqual(4, cell.getChildNodes(NodeType.ANY, false).getCount());
    }


    /// <summary>
    /// WORDSNET-23301 Consider providing API to access SDTs by id or name.
    /// Tests getting the structured document tag by the title, tag and Id.
    /// </summary>
    @Test
    public void test23301() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test23301.docx");

        // Checks getting the structured document tag by Id.
        IStructuredDocumentTag sdt = doc.getRange().getStructuredDocumentTags().getById(1160505028);
        StructuredDocumentTag sdtNode = ms.as(sdt.getNode(), StructuredDocumentTag.class);
        msAssert.areEqual(false, sdt.isMultiSection());
        msAssert.areEqual(1160505028, sdt.getId());
        msAssert.areEqual("Alias1", sdt.getTitle());
        msAssert.areEqual("Tag1", sdt.getTag());
        msAssert.areEqual(1, sdtNode.getChildNodes(NodeType.ANY, false).getCount());
        // Checks getting the ranged structured document tag by Id.
        IStructuredDocumentTag sdt1 = doc.getRange().getStructuredDocumentTags().getById(1373447100);
        StructuredDocumentTagRangeStart sdtRangeNode = ms.as(sdt1.getNode(), StructuredDocumentTagRangeStart.class);
        msAssert.areEqual(true, sdt1.isMultiSection());
        msAssert.areEqual(1373447100, sdt1.getId());
        msAssert.areEqual("Alias4", sdt1.getTitle());
        msAssert.areEqual("Tag5", sdt1.getTag());
        msAssert.areEqual(3, sdtRangeNode.getChildNodes().getCount());
        // Checks getting the sdt by the tag. Normal.
        sdt = doc.getRange().getStructuredDocumentTags().getByTag("Tag2");
        msAssert.areEqual(1491995098, sdt.getId());
        // Checks getting the sdt by the title. Non-unique title.
        sdt = doc.getRange().getStructuredDocumentTags().getByTitle("Alias4");
        msAssert.areEqual(sdt.getTitle(), sdt1.getTitle());
        msAssert.areNotEqual(sdt.getId(), sdt1.getId());
        msAssert.areEqual(515359460, sdt.getId());
        // Checks getting the sdt by the title. Missing title.
        sdt = doc.getRange().getStructuredDocumentTags().getByTitle("Alias6");
        msAssert.isNull(sdt);
    }


    /// <summary>
    /// Relates to WORDSNET-23301
    /// Tests the structured document tag collection.
    /// </summary>
    @Test
    public void testStructuredDocumentTagsEnumerator() throws Exception
    {
        ArrayList<Integer> sdtListId = new ArrayList<Integer>(); { sdtListId.add(1160505028); sdtListId.add(1491995098); sdtListId.add(284420510); sdtListId.add(515359460); sdtListId.add(1373447100); }

        Document doc = TestUtil.open("Model\\Markup\\Test23301.docx");

        msAssert.areEqual(5, doc.getRange().getStructuredDocumentTags().getCount());
        for (IStructuredDocumentTag sdt : doc.getRange().getStructuredDocumentTags())
        {
            // Checks IStructuredDocumentTag interface.
            if (sdt.isMultiSection())
            {
                StructuredDocumentTagRangeStart start = (StructuredDocumentTagRangeStart)sdt.getNode();
                msAssert.areEqual(sdt.getColor(), start.getColor());
                msAssert.areEqual(sdt.getTitle(), start.getTitle());
                msAssert.areEqual(sdt.getId(), start.getId());
                msAssert.areEqual(sdt.isShowingPlaceholderText(), start.isShowingPlaceholderText());
                msAssert.areEqual(sdt.getLevel(), start.getLevel());
                msAssert.areEqual(sdt.getLockContentControl(), start.getLockContentControl());
                msAssert.areEqual(sdt.getLockContents(), start.getLockContents());
                msAssert.areEqual(sdt.getPlaceholder(), start.getPlaceholder());
                msAssert.areEqual(sdt.getPlaceholderName(), start.getPlaceholderName());
                msAssert.areEqual(sdt.getSdtType(), start.getSdtType());
                msAssert.areEqual(sdt.getTag(), start.getTag());
                msAssert.areEqual(sdt.getWordOpenXML(), start.getWordOpenXML());
                msAssert.areEqual(sdt.getXmlMapping(), start.getXmlMapping());
            }
            else
            {
                StructuredDocumentTag tag = (StructuredDocumentTag)sdt.getNode();
                msAssert.areEqual(sdt.getColor(), tag.getColor());
                msAssert.areEqual(sdt.getTitle(), tag.getTitle());
                msAssert.areEqual(sdt.getId(), tag.getId());
                msAssert.areEqual(sdt.isShowingPlaceholderText(), tag.isShowingPlaceholderText());
                msAssert.areEqual(sdt.getLevel(), tag.getLevel());
                msAssert.areEqual(sdt.getLockContentControl(), tag.getLockContentControl());
                msAssert.areEqual(sdt.getLockContents(), tag.getLockContents());
                msAssert.areEqual(sdt.getPlaceholder(), tag.getPlaceholder());
                msAssert.areEqual(sdt.getPlaceholderName(), tag.getPlaceholderName());
                msAssert.areEqual(sdt.getSdtType(), tag.getSdtType());
                msAssert.areEqual(sdt.getTag(), tag.getTag());
                msAssert.areEqual(sdt.getWordOpenXML(), tag.getWordOpenXML());
                msAssert.areEqual(sdt.getXmlMapping(), tag.getXmlMapping());
            }
            // Checks iteration of all structured document tags.
            sdtListId.set(sdtListId.indexOf(sdt.getId()), 0);
        }

        // Checks removing.
        doc.getRange().getStructuredDocumentTags().remove(284420510);
        msAssert.areEqual(4, doc.getRange().getStructuredDocumentTags().getCount());
    }

    /// <summary>
    /// WORDSNET-23774 Rework StructuredDocumentTagCollection to be indexed by index.
    /// </summary>
    @Test
    public void testStructuredDocumentTagsIndexer() throws Exception
    {
        ArrayList<Integer> sdtListId = new ArrayList<Integer>(); { sdtListId.add(1160505028); sdtListId.add(1491995098); sdtListId.add(284420510); sdtListId.add(515359460); sdtListId.add(1373447100); }

        Document doc = TestUtil.open("Model\\Markup\\Test23301.docx");
        for (int i = 0; i < doc.getRange().getStructuredDocumentTags().getCount(); i++)
            msAssert.areEqual(sdtListId.get(i), doc.getRange().getStructuredDocumentTags().get(i).getId());

        msAssert.areEqual(2, doc.getRange().getStructuredDocumentTags().indexOf(sdtListId.get(2)));
        doc.getRange().getStructuredDocumentTags().removeAt(2);
        msAssert.areEqual(-1, doc.getRange().getStructuredDocumentTags().indexOf(sdtListId.get(2)));
    }

    /// <summary>
    /// WORDSNET-23826 Aspose.Words produces corrupted DOCX document.
    /// MS Word throws an error when opening a document containing an SDT that ends with a table, all the cells of
    /// the last row of which are merged vertically. Appending an empty paragraph has been implemented for this case.
    /// </summary>
    @Test
    public void test23826() throws Exception
    {
        final String FILE_NAME = "Model\\Markup\\Test23826.docx";
        Document doc = TestUtil.open(FILE_NAME);
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);

        doc.getFirstSection().getBody().getFirstParagraph().remove();
        msAssert.areEqual(NodeType.TABLE, sdt.getLastChild().getNodeType());

        TestUtil.executeValidator(doc, SaveFormat.DOCX);
        msAssert.areEqual(NodeType.PARAGRAPH, sdt.getLastChild().getNodeType());

        TestUtil.saveCheckGoldExportOnly(doc, FILE_NAME);
    }

    /// <summary>
    /// WORDSNET-24185 NullReferenceException is thrown upon getting ChildNodes from imported StructuredDocumentTagRangeStart.
    /// Added resilience in RangeEnd getter.
    /// </summary>
    @Test
    public void test24185() throws Exception
    {
        Document srcDoc = TestUtil.open("Model\\Markup\\Test24185.docx");
        Document dstDoc = TestUtil.open("Model\\Markup\\Test24185.docx");
        dstDoc.getSections().clear();

        Section importedSection = (Section)dstDoc.importNode(srcDoc.getFirstSection(), true);
        StructuredDocumentTagRangeStart sdtRangeStart = (StructuredDocumentTagRangeStart)importedSection.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);

        // Exception occurs while getting ChildNodes.
        msAssert.isNotNull(sdtRangeStart.getChildNodes());
    }

    /// <summary>
    /// WORDSNET-24144 Allow creating SDT of Group type on Row level.
    /// </summary>
    @Test
    public void test24144() throws Exception
    {
        // A document similar to that provided by the customer is generated.

        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Table table = builder.startTable();

        StructuredDocumentTag groupSdt = new StructuredDocumentTag(doc, SdtType.GROUP, MarkupLevel.ROW);
        table.appendChild(groupSdt);

        groupSdt.isShowingPlaceholderText(false);
        groupSdt.setLockContents(true);
        groupSdt.removeAllChildren();

        Row row = new Row(doc);
        groupSdt.appendChild(row);

        Cell cell = new Cell(doc);
        row.appendChild(cell);

        builder.endTable();

        cell.ensureMinimum();
        builder.moveTo(cell.getLastParagraph());

        builder.write("Added Content Inside a Table and locked using Group Type");

        builder.moveTo(table.getNextSibling());
        builder.write("Text after the table.");

        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test24144.docx", null, true);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG, 0, true);
        msAssert.areEqual(SdtType.GROUP, sdt.getSdtType());
        msAssert.areEqual(MarkupLevel.ROW, sdt.getLevel());
    }

    /// <summary>
    /// WORDSNET-24458 Creating StructuredDocumentTag of SdtType.Citation type
    /// </summary>
    @Test
    public void test24458() throws Exception
    {
        // A document similar to that provided by the customer is generated.

        Document doc = new Document();
        StructuredDocumentTag sdt = new StructuredDocumentTag(doc, SdtType.CITATION, MarkupLevel.INLINE);
        doc.getFirstSection().getBody().getFirstParagraph().appendChild(sdt);

        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.moveToParagraph(0, -1);
        builder.insertField("CITATION Dem22 \\l 1033 ", "(Demo Author, 2022)");

        sdt.insertBefore(sdt.getNextSibling(), null, null);

        TestUtil.saveCheckGoldExportOnly(doc, "Model\\Markup\\Test24458.docx");
    }

    /// <summary>
    /// WORDSNET-24793 SDT dropdown list item with empty value is lost after open/save document.
    /// Seems that empty list item value is valid for MS Word.
    /// </summary>
    @Test
    public void test24793() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test24793", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().get(1).getNode();
        msAssert.areEqual("Choose an item.", sdt.getListItems().get(0).getDisplayText());
        msAssert.isNull(sdt.getListItems().get(0).getValue());
    }



    /// <summary>
    /// WORDSNET-25482 Placeholder text lost after saving
    /// SDT placeholder reference should be updated even for SDT marked for deletion.
    /// </summary>
    @Test
    public void test25482() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test25482", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        // Check original placeholder is preserved.
        BuildingBlockCollection buildingBlocks = doc.getGlossaryDocument().getBuildingBlocks();
        msAssert.areEqual(1, buildingBlocks.getCount());

        BuildingBlock placeholder = buildingBlocks.get(0);
        msAssert.areEqual("DefaultPlaceholder_-1854013440", placeholder.getName());
        msAssert.areEqual("3b9ca7f6-fcfd-44df-8a22-92ac373f2c3f", placeholder.getGuidInternal().toString());
        msAssert.areEqual("Click or tap here to enter text.\f", placeholder.getText());
    }


    /// <summary>
    /// Simplified test for WORDSNET-26027.
    /// </summary>
    @Test
    public void test26027A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26027A.docx");
        Paragraph para = doc.getFirstSection().getBody().getTables().get(0).getRows().get(1).getFirstCell().getLastParagraph();

        msAssert.areEqual("|Test format and Size libero, sit amet commodo magna eros quis urna|.\u0007", para.getText());

        msAssert.isNull(para.getParaPr().get(ParaAttr.ISTD));
        msAssert.isNull(para.getParaPr().get(ParaAttr.SPACE_BEFORE));
        msAssert.areEqual(250, para.getParaPr().get(ParaAttr.SPACE_AFTER));
        msAssert.areEqual(new LineSpacing(300, LineSpacingRule.AT_LEAST), para.getParaPr().get(ParaAttr.LINE_SPACING));

        msAssert.areEqual(0x29, para.getParagraphBreakRunPr().get(FontAttr.ISTD));
        msAssert.areEqual(22, para.getParagraphBreakRunPr().get(FontAttr.SIZE_BI));
    }



    /// <summary>
    /// WORDSNET-26434 StructuredDocumentTagRangeStart class Appearance property.
    /// Appearance public property has been added to StructuredDocumentTagRangeStart.
    /// </summary>
    @Test
    public void test26434() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26434.docx");

        StructuredDocumentTagRangeStart start1 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true), StructuredDocumentTagRangeStart.class);
        StructuredDocumentTagRangeStart start2 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 1, true), StructuredDocumentTagRangeStart.class);
        StructuredDocumentTagRangeStart start3 = ms.as(doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 2, true), StructuredDocumentTagRangeStart.class);

        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, start1.getAppearance());
        msAssert.areEqual(SdtAppearance.TAGS, start2.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, start3.getAppearance());

        start1.setAppearance(SdtAppearance.TAGS);
        start2.setAppearance(SdtAppearance.HIDDEN);
        start3.setAppearance(SdtAppearance.BOUNDING_BOX);

        msAssert.areEqual(SdtAppearance.TAGS, start1.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, start2.getAppearance());
        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, start3.getAppearance());
    }

    /// <summary>
    /// Relates to WORDSNET-26434.
    /// Appearance public property has been added to IStructuredDocumentTag.
    /// </summary>
    @Test
    public void test26434A() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test26434.docx");

        IStructuredDocumentTag std1 = doc.getRange().getStructuredDocumentTags().get(0);
        IStructuredDocumentTag std2 = doc.getRange().getStructuredDocumentTags().get(1);
        IStructuredDocumentTag std3 = doc.getRange().getStructuredDocumentTags().get(2);
        IStructuredDocumentTag std4 = doc.getRange().getStructuredDocumentTags().get(3);

        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, std1.getAppearance());
        msAssert.areEqual(SdtAppearance.TAGS, std2.getAppearance());
        msAssert.areEqual(SdtAppearance.TAGS, std3.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, std4.getAppearance());

        std1.setAppearance(SdtAppearance.TAGS);
        std2.setAppearance(SdtAppearance.HIDDEN);
        std3.setAppearance(SdtAppearance.HIDDEN);
        std4.setAppearance(SdtAppearance.BOUNDING_BOX);

        msAssert.areEqual(SdtAppearance.TAGS, std1.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, std2.getAppearance());
        msAssert.areEqual(SdtAppearance.HIDDEN, std3.getAppearance());
        msAssert.areEqual(SdtAppearance.BOUNDING_BOX, std4.getAppearance());
    }



    /// <summary>
    /// Relates to WORDSNET-27292
    /// Checks that new IDs are generated for cloned SDT range nodes during document validation.
    /// </summary>
    @Test
    public void testCloningSdtRanges() throws Exception
    {
        Document doc = TestUtil.open("Reporting\\Test27292.docx");

        final int SDT_ID = -2086217063;

        StructuredDocumentTagRangeStart start =
            (StructuredDocumentTagRangeStart)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_START, 0, true);
        msAssert.areEqual(SDT_ID, start.getId());

        StructuredDocumentTagRangeEnd end =
            (StructuredDocumentTagRangeEnd)doc.getChild(NodeType.STRUCTURED_DOCUMENT_TAG_RANGE_END, 0, true);
        msAssert.areEqual(SDT_ID, end.getId());

        StructuredDocumentTagRangeStart start2 = (StructuredDocumentTagRangeStart)start.deepClone(false);
        msAssert.areEqual(SDT_ID, start2.getId());
        start.getParentNode().insertAfter(start2, start);

        StructuredDocumentTagRangeStart start3 = (StructuredDocumentTagRangeStart)start.deepClone(false);
        msAssert.areEqual(SDT_ID, start3.getId());
        start.getParentNode().insertAfter(start3, start2);

        StructuredDocumentTagRangeEnd end2 = (StructuredDocumentTagRangeEnd)end.deepClone(false);
        msAssert.areEqual(SDT_ID, end2.getId());
        end.getParentNode().insertBefore(end2, end);

        StructuredDocumentTagRangeEnd end3 = (StructuredDocumentTagRangeEnd)end.deepClone(false);
        msAssert.areEqual(SDT_ID, end3.getId());
        end.getParentNode().insertBefore(end3, end2);

        StructuredDocumentTagRangeStart nonClosedStart = (StructuredDocumentTagRangeStart)start.deepClone(false);
        msAssert.areEqual(SDT_ID, nonClosedStart.getId());
        end.getParentNode().insertAfter(nonClosedStart, end);

        StructuredDocumentTagRangeEnd nonOpenedEnd = (StructuredDocumentTagRangeEnd)end.deepClone(false);
        msAssert.areEqual(SDT_ID, nonOpenedEnd.getId());
        start.getParentNode().insertBefore(nonOpenedEnd, start);

        // Validator re-generates duplicate SDT range IDs and removes invalid start/end nodes.
        TestUtil.executeValidator(doc, SaveFormat.DOCX);

        msAssert.areEqual(start.getId(), end.getId());

        msAssert.areNotEqual(start.getId(), start2.getId());
        msAssert.areEqual(start2.getId(), end2.getId());

        msAssert.areNotEqual(start.getId(), start3.getId());
        msAssert.areNotEqual(start2.getId(), start3.getId());
        msAssert.areEqual(start3.getId(), end3.getId());

        // Check that the invalid start and end nodes are removed.
        msAssert.isNull(nonClosedStart.getParentNode());
        msAssert.isNull(nonOpenedEnd.getParentNode());
    }


    /// <summary>
    /// WORDSNET-27816 NullReferenceException is thrown upon removing nodes from SDT.
    /// Tests clearing the SDT ranged content which leaves an empty Body.
    /// </summary>
    @Test
    public void test27816() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test27816.docx");

        StructuredDocumentTagCollection sdts = doc.getRange().getStructuredDocumentTags();
        NodeCollection coll = sdts.get(0).getChildNodes(NodeType.ANY, false);

        msAssert.areEqual(3, sdts.getCount());
        msAssert.areEqual(5, coll.getCount());

        // Additionally NullReferenceException is being checked here.
        coll.clear();

        msAssert.areEqual(0, coll.getCount());
        // Checks that only ranged SDT content has been removed.
        msAssert.areEqual(3, sdts.getCount());
    }


    private Paragraph firstParagraph(NodeCollection paragraphs)
    {
        for (Paragraph firstParagraph : (Iterable<Paragraph>) paragraphs)
             return firstParagraph;
        return null;
    }

    /// <summary>
    /// WORDSNET-28344 Track changes are lost in dropdown content controls mapped to Custom XML when saving DOCX
    /// Do not update SDT content if mapped content is the same as current SDT content for Dropdown control.
    /// </summary>
    @Test
    public void test28344() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\Markup\\Test28344", UnifiedScenario.DOCX_2_DOCX_NO_GOLD);

        msAssert.areEqual(1, doc.getRevisions().getGroups().getCount());
        msAssert.areEqual("sample 2", doc.getRevisions().getGroups().get(0).getText());
    }


    /// <summary>
    /// WORDSNET-28998 FileCorruptedException is thrown upon loading '.docx' document.
    /// Resilience paragraph is inserted incorrectly during empty SDT resolution.
    /// </summary>
    @Test
    public void test28998() throws Exception
    {
        Document doc = TestUtil.open("Model\\Markup\\Test28998.docx");
        StructuredDocumentTag sdt = (StructuredDocumentTag)doc.getRange().getStructuredDocumentTags().getById(539474896);
        msAssert.areEqual("\r", sdt.getText());
    }

    /// <summary>
    /// Core body of <see cref="TestPlaceholderStyle"/>
    /// </summary>
    private static void verifyStyleProperties(DocumentBase doc, Run sdtDefaultRun)
    {
        msAssert.isTrue(sdtDefaultRun.getRunPr().containsKey(FontAttr.ISTD));
        Style sdtDefaultRunStyle = doc.getStyles().getByIstd((/*int*/Integer)sdtDefaultRun.getRunPr().get(FontAttr.ISTD), false);
        msAssert.areEqual(StyleIndex.DEFAULT_PARAGRAPH_FONT, sdtDefaultRunStyle.getBasedOnIstd());
        msAssert.areEqual("Placeholder Text", sdtDefaultRunStyle.getName());
    }

    private static Document createDocWithCustomPlaceholder() throws Exception
    {
        Document srcDoc = new Document();
        StructuredDocumentTag srcTag = new StructuredDocumentTag(srcDoc, SdtType.RICH_TEXT, MarkupLevel.BLOCK);
        srcDoc.getFirstSection().getBody().appendChild(srcTag);
        srcTag.setPlaceholderName(createCustomPlaceholder(srcDoc));
        msAssert.areEqual(srcTag.getPlaceholderName(), srcTag.getPlaceholder().getName());
        msAssert.areEqual("Enter your name here.\f", srcTag.getPlaceholder().getText());
        ((Run)((Paragraph)srcTag.getChildNodes(NodeType.ANY, false).get(0)).getChildNodes(NodeType.ANY, false).get(0)).setText("Enter your name here.");
        return srcDoc;
    }

    /// <summary>
    /// This might be a good candidate to move into BuildingBlock class.
    /// </summary>
    private static BuildingBlock createAndInsertBuildingBlock(GlossaryDocument dstDocument)
    {
        BuildingBlock block = new BuildingBlock(dstDocument);
        dstDocument.appendChild(block);

        Section section = new Section(dstDocument);
        block.appendChild(section);

        Body b = new Body(dstDocument);
        section.appendChild(b);


        return block;
    }

    /// <summary>
    /// This actually is not very simple use case, so it should either become a public function, or a text in learning examples.
    /// </summary>
    private static String createCustomPlaceholder(Document srcDoc)
    {
        if (srcDoc.getGlossaryDocument() == null)
            srcDoc.setGlossaryDocument(new GlossaryDocument());
        BuildingBlock block = createAndInsertBuildingBlock(srcDoc.getGlossaryDocument());
        block.setCategory(SdtPlaceholderManager.BUILDING_BLOCK_CATEGORY);
        block.setGallery(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        block.setType(BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT);
        block.setBehavior(BuildingBlockBehavior.CONTENT);
        block.setGuidInternal(RandomUtil.newGuid(1234));
        block.setName("my fancy block");
        Paragraph para = new Paragraph(srcDoc.getGlossaryDocument());
        block.getFirstSection().getBody().appendChild(para);
        Run r = new Run(srcDoc.getGlossaryDocument(), "Enter your name here.");

        Style style = srcDoc.getGlossaryDocument().getStyles().getByName("Placeholder Text", true);
        r.getRunPr().setAttr(FontAttr.ISTD, style.getIstd());
        para.appendChild(r);

        return block.getName();
    }

    private static void checkImportedPlaceholder(StructuredDocumentTag srcTag, StructuredDocumentTag dstTag)
    {
        msAssert.isNotNull(dstTag.getPlaceholder());
        msAssert.areNotSame(srcTag.getPlaceholder(), dstTag.getPlaceholder());
        msAssert.areEqual(dstTag.getPlaceholderName(), dstTag.getPlaceholder().getName());
    }

    /// <summary>
    /// Checks sdt. Helper for TestJira11088().
    /// </summary>
    private static void checkSdt(StructuredDocumentTag sdt, /*MarkupLevel*/int expectedLevel, String expectedText)
    {
        msAssert.areEqual(expectedLevel, sdt.getLevel());
        msAssert.isTrue(sdt.getText().startsWith(expectedText));
    }

    /// <summary>
    /// Checks the color properties of a structured document tag.
    /// </summary>
    private static void checkColor(StructuredDocumentTag sdt, String expectedColor, String expectedThemeColor,
        String expectedThemeShade, String expectedThemeTint)
    {
        msAssert.areEqual(expectedColor, NrxXmlUtil.colorToXml(sdt.getBaseColor()), "Color is wrong.");
        msAssert.areEqual(expectedThemeColor, sdt.getThemeColor(), "Theme color is wrong.");
        msAssert.areEqual(expectedThemeShade, sdt.getThemeShade(), "Theme shade is wrong.");
        msAssert.areEqual(expectedThemeTint, sdt.getThemeTint(), "Theme tint is wrong.");
    }

    /// <summary>
    /// Escapes text removing traces of XML offending characters that could be wrongfully interpreted as markup.
    /// </summary>
    private static String escapeXmlText(String text)
    {
        String escapedText = text.replace("&", "&amp;");
        escapedText = escapedText.replace("'", "&apos;");
        escapedText = escapedText.replace("\"", "&quot;");
        escapedText = escapedText.replace(">", "&gt;");
        return escapedText.replace("<", "&lt;");
    }
}

