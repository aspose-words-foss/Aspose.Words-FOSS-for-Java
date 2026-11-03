// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestBuildingBlocks
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    @Test
    public void testCreate() throws Exception
    {
        Document doc = new Document();
        msAssert.isNull(doc.getGlossaryDocument());

        GlossaryDocument glossary = new GlossaryDocument();
        doc.setGlossaryDocument(glossary);

        BuildingBlock block = new BuildingBlock(glossary);
        glossary.appendChild(block);

        msAssert.areEqual("(Empty Name)", block.getName());
        msAssert.areEqual("(Empty Category)", block.getCategory());
        msAssert.areEqual(BuildingBlockBehavior.CONTENT, block.getBehavior());

        block.setName("Test1");
        block.setGallery(BuildingBlockGallery.QUICK_PARTS);
        block.setCategory("General");
        block.setBehavior(BuildingBlockBehavior.PAGE);

        Section section = new Section(glossary);
        block.appendChild(section);

        Body body = new Body(glossary);
        section.appendChild(body);

        Paragraph para = new Paragraph(glossary);
        body.appendChild(para);

        Run run = new Run(glossary, "Hello!");
        para.appendChild(run);

        // Check after open save open.
        doc = TestUtil.saveOpen(doc, "Model\\BuildingBlocks\\TestCreate.dotx");
        glossary = doc.getGlossaryDocument();
        block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 0, true);
        msAssert.areEqual("Hello!\f", block.getText());
    }

    @Test
    public void testNoEmptyName() throws Exception
    {
        try
        {
            Document doc = new Document();
            doc.setGlossaryDocument(new GlossaryDocument());
            BuildingBlock block = new BuildingBlock(doc.getGlossaryDocument());
            block.setName("");
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("cannot be null or empty string") > 0);
        }
    }

    @Test
    public void testNoEmptyCategory() throws Exception
    {
        try
        {
            Document doc = new Document();
            doc.setGlossaryDocument(new GlossaryDocument());
            BuildingBlock block = new BuildingBlock(doc.getGlossaryDocument());
            block.setCategory("");
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            msAssert.isTrue(e.getMessage().indexOf("cannot be null or empty string") > 0);
        }
    }

    /// <summary>
    /// Open, save open and check gold plus some basic checks that glossary is a separate document
    /// and contains its styles etc.
    /// </summary>
    @Test
    public void testBasic() throws Exception
    {
        Document doc = TestUtil.openSaveOpen("Model\\BuildingBlocks\\TestBuildingBlocks.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        msAssert.areEqual("\f", doc.getText());
        msAssert.isNotNull(glossary);
        msAssert.isTrue(doc != (DocumentBase)glossary);
        msAssert.isTrue(doc.getFontInfos() != glossary.getFontInfos());
        msAssert.isTrue(doc.getStyles() != glossary.getStyles());
        msAssert.isTrue(doc.getStyles().get(0) != glossary.getStyles().get(0));
        msAssert.areSame(glossary, glossary.getStyles().get(0).getDocument());
    }

    @Test
    public void testInline() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuildingBlocks.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        // Check an inline part.
        BuildingBlock block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 0, true);
        msAssert.areEqual(BuildingBlockBehavior.CONTENT, block.getBehavior());
        msAssert.areEqual(BuildingBlockGallery.QUICK_PARTS, block.getGallery());
        msAssert.areEqual("General", block.getCategory());
        msAssert.areEqual("Simple inline", block.getName());
        msAssert.areEqual("", block.getDescription());
        msAssert.areEqual("59a69c94-f85c-470c-b306-3af54dbf86b9", block.getGuidInternal().toString());
        msAssert.areEqual("Normal", block.getStyle());
        msAssert.areEqual(BuildingBlockType.NONE, block.getType());

        msAssert.areEqual("Simple inline level.\f", block.getText());
        msAssert.areEqual(1, block.getChildNodes(NodeType.PARAGRAPH, true).getCount());

        Paragraph para = (Paragraph)block.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual(glossary.getStyles().get("Normal"), para.getParagraphStyle());

        // Although this is just a run block we have a paragraph and a section in the model.
        msAssert.areEqual(1, block.getSections().getCount());
        msAssert.areEqual(1, block.getFirstSection().getBody().getParagraphs().getCount());

        msAssert.areEqual(block, glossary.getBuildingBlock(BuildingBlockGallery.QUICK_PARTS, "General", "Simple inline"));
        msAssert.areEqual(null, glossary.getBuildingBlock(BuildingBlockGallery.QUICK_PARTS, "General", "simple inline"));
    }


    @Test
    public void testParagraph() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuildingBlocks.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        // Check a paragraph part that contains a comment, endnote, list numbering and a bookmark.
        BuildingBlock block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 1, true);
        msAssert.areEqual(BuildingBlockBehavior.PARAGRAPH, block.getBehavior());
        msAssert.areEqual(BuildingBlockGallery.CUSTOM_QUICK_PARTS, block.getGallery());
        msAssert.areEqual("TestCategory", block.getCategory());

        Paragraph para = (Paragraph)block.getChild(NodeType.PARAGRAPH, 0, true);
        msAssert.areEqual("A quick part with a comment\u0005Comment to a quick part.\r, endnote\u0002 Endnote to a quick part.\r,\r", para.getText());

        msAssert.areEqual(0, doc.getRange().getBookmarks().getCount());
        msAssert.areEqual(1, glossary.getRange().getBookmarks().getCount());
        msAssert.areEqual("bookmark", glossary.getRange().getBookmarks().get("bmk1").getText());

        // The selection is only two paragraphs, but the second paragraph includes the paragraph mark,
        // therefore there is one empty paragraph at the end according to the OOXML spec.
        msAssert.areEqual(1, block.getSections().getCount());
        msAssert.areEqual(3, block.getFirstSection().getBody().getParagraphs().getCount());

        msAssert.areEqual(block, glossary.getBuildingBlock(BuildingBlockGallery.CUSTOM_QUICK_PARTS, "TestCategory", "QuickPartWithCommentEtc"));
    }

    @Test
    public void testSection() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuildingBlocks.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        // Check a block with one section break and text in two sections.
        BuildingBlock block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 3, true);

        msAssert.areEqual("Building Block with a Section Break.\r\fNext section.\r\f", block.getText());

        // Two sections it is correct. Last section contains text in one paragraph
        // and it also contains one dummy paragraph mark according to the OOXML spec.
        msAssert.areEqual(2, block.getSections().getCount());
        msAssert.areEqual(2, block.getSections().get(0).getBody().getParagraphs().getCount());
        msAssert.areEqual(2, block.getSections().get(1).getBody().getParagraphs().getCount());

        PageSetup ps = block.getSections().get(0).getPageSetup();
        msAssert.areEqual(2, ps.getTextColumns().getCount());

        ps = block.getSections().get(1).getPageSetup();
        msAssert.areEqual(1, ps.getTextColumns().getCount());
    }

    @Test
    public void testSectionWithHeader() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuildingBlocks.dotx");
        GlossaryDocument glossary = doc.getGlossaryDocument();

        // Check a block with one section, a section break at the end and a header.
        BuildingBlock block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 4, true);

        msAssert.areEqual("Header in Building Block\rSection with a Header in a Building Block\f\f", block.getText());

        // There is only one section in the building block in the document, but because
        // the section break is the last char, MS Word adds one more paragraph break according
        // to the OOXML spec and that is stored as yet another section in our model.
        msAssert.areEqual(2, block.getSections().getCount());
        msAssert.areEqual(1, block.getSections().get(0).getBody().getParagraphs().getCount());
        msAssert.areEqual(1, block.getSections().get(1).getBody().getParagraphs().getCount());

        HeaderFooter header = block.getSections().get(0).getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
        msAssert.areEqual("Header in Building Block\r", header.getText());
    }

    /// <summary>
    /// This is a file that comes with MS Word 2007. Has lots of blocks, lets check we can read it okay.
    /// </summary>
    @Test
    public void testBuiltIn() throws Exception
    {
        Document doc = TestUtil.open("Model\\BuildingBlocks\\TestBuiltIn.dotx");

        GlossaryDocument glossary = doc.getGlossaryDocument();

        BuildingBlock block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 2, true);
        msAssert.areEqual(BuildingBlockBehavior.PAGE, block.getBehavior());
        msAssert.areEqual(BuildingBlockGallery.COVER_PAGE, block.getGallery());
        msAssert.areEqual("Built-In", block.getCategory());
        msAssert.areEqual("Conservative", block.getName());
        msAssert.areEqual("Top-aligned information block with accent line between title and subtitle; bottom-aligned abstract", block.getDescription());
        msAssert.areEqual("No Spacing", block.getStyle());
        msAssert.areEqual(BuildingBlockType.NONE, block.getType());

        block = (BuildingBlock)glossary.getChild(NodeType.BUILDING_BLOCK, 3, true);
        msAssert.areEqual(BuildingBlockBehavior.CONTENT, block.getBehavior());
        msAssert.areEqual(BuildingBlockGallery.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT, block.getGallery());
        msAssert.areEqual("General", block.getCategory());
        msAssert.areEqual("3A5B8D0E64CA4985BBFCEFDF165F36CC", block.getName());
        msAssert.areEqual("", block.getDescription());
        msAssert.areEqual("", block.getStyle());
        msAssert.areEqual(BuildingBlockType.STRUCTURED_DOCUMENT_TAG_PLACEHOLDER_TEXT, block.getType());
    }

    /// <summary>
    /// RK This is supposed to be a method available in the public API later to allow easy insertion of building blocks.
    /// Need to finish this.
    /// </summary>
    /// <param name="dstContainer">The container node where the insertion takes place.</param>
    /// <param name="srcBlock">The building block that is being inserted.</param>
    /// <param name="dstRefNode">The reference node (cursor) before or after which the
    /// insertion takes place. If not null, must be a child of the container.</param>
    /// <param name="isAfter">True to insert after the cursor, false to insert before.</param>
    private static void insertSmart(
        CompositeNode dstContainer,
        BuildingBlock srcBlock,
        Node dstRefNode,
        boolean isAfter)
    {
        if (dstContainer == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "container");
        if (srcBlock == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "block");

        if (dstContainer.getDocument() == srcBlock.getDocument())
            throw new IllegalArgumentException("The block to be inserted is expected to be from a different document.");

        if ((dstRefNode != null) && (dstRefNode.getParentNode() != dstContainer))
            throw new IllegalArgumentException("The reference node should be a child of the container node.");

        // We need both nodes before and after the cursor because we are trying to mostly
        // use CompositeNode.InsertAfter because currently it is faster than InsertBefore.
        // When later InsertBefore is improved, then we can delete this code and use
        // both InsertAfter and InsertBefore where appropriate.
        Node nodeAfterCursor;
        Node nodeBeforeCursor;
        if (isAfter)
        {
            nodeBeforeCursor = dstRefNode;
            nodeAfterCursor = (dstRefNode == null) ? dstContainer.getFirstChild() : dstRefNode.getNextSibling();
        }
        else
        {
            nodeAfterCursor = dstRefNode;
            nodeBeforeCursor = (dstRefNode == null) ? dstContainer.getLastChild() : dstRefNode.getPreviousSibling();
        }

        NodeImporter importer = new NodeImporter(srcBlock.getDocument(), dstContainer.getDocument(), ImportFormatMode.KEEP_SOURCE_FORMATTING);
        for (Node srcBlockChild : (Iterable<Node>) srcBlock)
        {
            // This clones the building block nodes.
            Section section = (Section)importer.importNode(srcBlockChild, true);

            Node bodyChild = section.getBody().getFirstChild();
            while (bodyChild != null)
            {
                // This has to be cached now because we move, not clone building block nodes.
                Node nextBodyChild = bodyChild.getNextSibling();

                switch (bodyChild.getNodeType())
                {
                    case NodeType.PARAGRAPH:
                    {
                        Paragraph para = (Paragraph)bodyChild;

                        if (para.isLastChild())
                        {
                            // For the last paragraph we do not need the paragraph itself.
                            // We just move all inline nodes from it to be before the cursor.
                            dstContainer.insertAfter(para.getFirstChild(), null, nodeBeforeCursor);
                        }
                        else
                        {
                            // This has to be cached before we move the paragraph.
                            boolean isFirstPara = para.isFirstChild();

                            // Just move the paragraph to be before the destination para.
                            dstContainer.insertPrevious(para);

                            if (isFirstPara)
                            {
                                // For the first paragraph move all inlines from the destination para
                                // up to the cursor to the beginning of the first para.
                                para.insertAfter(dstContainer.getFirstChild(), nodeAfterCursor, null);
                            }
                        }

                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected node type.");
                }

                bodyChild = nextBodyChild;
            }
        }
    }




    /// <summary>
    /// WORDSNET-28455 InvalidOperationException upon saving document after appending text to tables in a Building Block
    /// The error occurred in the <see cref="ParagraphMeasurer"/> class because <see cref="Node.FetchDocument"/>
    /// was used for a node of a glossary document. Now it has been changed to calling the method
    /// <see cref="Node.FetchDocumentOrGlossaryMain"/>.
    /// </summary>
    @Test
    public void test28455() throws Exception
    {
        Document doc = new Document();
        GlossaryDocument glossaryDoc = new GlossaryDocument();
        doc.setGlossaryDocument(glossaryDoc);

        final String BUILDING_BLOCK_NAME = "ProductList";

        BuildingBlock block = new BuildingBlock(glossaryDoc);
        {
            block.setName(BUILDING_BLOCK_NAME);
            block.setGallery(BuildingBlockGallery.QUICK_PARTS);
            block.setCategory("General");
            block.setBehavior(BuildingBlockBehavior.PARAGRAPH);
        }
        glossaryDoc.appendChild(block);

        Section section = new Section(glossaryDoc);
        block.getSections().add(section);

        Body body = new Body(glossaryDoc);
        section.appendChild(body);

        body.appendParagraph("AAA");

        Table newTable = new Table(glossaryDoc);
        body.appendChild(newTable);
        newTable.ensureMinimum();

        Cell headerCell1 = newTable.getFirstRow().getFirstCell();
        headerCell1.ensureMinimum();

        Cell headerCell2 = new Cell(glossaryDoc);
        newTable.getFirstRow().getCells().add(headerCell2);
        headerCell2.ensureMinimum();

        newTable.setStyleIdentifier(StyleIdentifier.LIST_TABLE_4_ACCENT_1);

        headerCell1.getFirstParagraph().appendChild(new Run(glossaryDoc, "Header 1"));
        headerCell2.getFirstParagraph().appendChild(new Run(glossaryDoc, "Header 2"));

        // Just check that no exception occurs.
        doc = TestUtil.saveOpen(doc, "Model\\Markup\\Test28455.dotx", null, false);

        block = (BuildingBlock)doc.getGlossaryDocument().getChild(NodeType.BUILDING_BLOCK, 0, false);
        msAssert.areEqual(BUILDING_BLOCK_NAME, block.getName());
    }

    // todo 0 normal_0 font size 1638 !!!

    // todo 0 maybe allow insertion without passing the container?

    // todo 0 tables

    // block has only one para or multiple
    // last para is empty or not
    // insertion point at beginning of para/in the middle/end of para

    // block has only one section or multiple
    // last section consists of one empty para or more

    // insert a block containing a table

    // insert in a table, shape, comment, footnote, headerfooter
}

