// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.IO.StreamReader;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// The base class for testing markdown features parsing.
/// </summary>
/// <remarks>
/// The implementation of markdown parser conforms to CommonMark Spec: https://spec.commonmark.org/
/// To verify correctness of markdown tree please use the corresponding tool: https://spec.commonmark.org/dingus/
/// </remarks>
@Test
public abstract class TestMarkdownParserBase
{

    static class ExpectedInline
    {
        public ExpectedInline(/*BlockType*/byte blockType, String text)
        {
            mType = blockType;
            mText = text;
        }

        public /*BlockType*/byte getType() { return mType; };

        private /*BlockType*/byte mType;
        public String getText() { return mText; };

        private  String mText;
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*virtual*/ MarkdownDocument open(String path) throws Exception
    {
        return open(path, null);
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*virtual*/ MarkdownDocument open(String path, MarkdownLoadOptions loadOptions) throws Exception
    {
        String fileName = TestUtil.buildTestFileName(path);
        StreamReader reader = new StreamReader(fileName);

        MarkdownDocument doc = new MarkdownDocument(loadOptions);
        doc.read(reader, null);

        return doc;
    }

    /// <summary>
    /// Verifies SetextHeading block.
    /// </summary>
    static void checkSetextHeading(Block heading, int expectedLevel, String expectedText)
    {
        checkInlineContainer(heading, BlockType.SETEXT_HEADING, expectedText);
        msAssert.areEqual(expectedLevel, ((HeadingBlock)heading).getLevel());
    }

    /// <summary>
    /// Verifies Paragraph block.
    /// </summary>
    static void checkParagraph(Block paragraph, String expectedText)
    {
        checkInlineContainer(paragraph, BlockType.PARAGRAPH, expectedText);
    }

    /// <summary>
    /// Verifies HorizontalRule block.
    /// </summary>
    static void checkHorizontalRule(Block horizontalRule)
    {
        msAssert.areEqual(BlockType.HORIZONTAL_RULE, horizontalRule.getType());
    }

    /// <summary>
    /// Verifies IndentedCode block.
    /// </summary>
    static void checkIndentedCode(Block indentedCode, String expectedText)
    {
        checkInlineContainer(indentedCode, BlockType.INDENTED_CODE, expectedText);
    }

    /// <summary>
    /// Verifies FencedCode block.
    /// </summary>
    static void checkFencedCode(Block fencedCode, String expectedText)
	{
		checkFencedCode(fencedCode, expectedText, "");
	}

    /// <summary>
    /// Verifies FencedCode block.
    /// </summary>
    static void checkFencedCode(Block fencedCode, String expectedText, String expectedInfoString)
    {
        checkInlineContainer(fencedCode, BlockType.FENCED_CODE, expectedText);
        msAssert.areEqual(expectedInfoString, ((FencedCodeBlock)fencedCode).getInfo());
    }

    /// <summary>
    /// Verifies AtxHeading block.
    /// </summary>
    static void checkAtxHeading(Block heading, int expectedLevel, String expectedText)
    {
        checkInlineContainer(heading, BlockType.ATX_HEADING, expectedText);
        msAssert.areEqual(expectedLevel, ((HeadingBlock)heading).getLevel());
    }

    /// <summary>
    /// Verifies BulletListItem block.
    /// </summary>
    static void checkBulletListItem(Block bulletListItem, /*ListMarker*/int expectedMarker, String expectedText)
    {
        checkInlineContainer(bulletListItem, BlockType.BULLET_LIST_ITEM, expectedText);
        msAssert.areEqual(expectedMarker, ((BulletListItemBlock)bulletListItem).getMarker());
    }

    /// <summary>
    /// Verifies OrderedListItem block.
    /// </summary>
    static void checkOrderedListItem(Block orderedListItem,
        int expectedStartAt, /*ListMarker*/int expectedMarker, String expectedText)
    {
        checkInlineContainer(orderedListItem, BlockType.ORDERED_LIST_ITEM, expectedText);
        msAssert.areEqual(expectedMarker, ((OrderedListItemBlock)orderedListItem).getMarker());
        msAssert.areEqual(expectedStartAt, ((OrderedListItemBlock)orderedListItem).getStartAt());
    }

    /// <summary>
    /// Verifies LinkText block.
    /// </summary>
    static void checkLinkTextBlock(Block linkStartBlock,
        String expectedDisplayText, String expectedUri)
	{
		checkLinkTextBlock(linkStartBlock, expectedDisplayText, expectedUri, "");
	}

    /// <summary>
    /// Verifies LinkText block.
    /// </summary>
    static void checkLinkTextBlock(Block linkStartBlock,
        String expectedDisplayText, String expectedUri, String expectedTitle)
    {
        LinkTextBlock linkTextBlock = (LinkTextBlock)linkStartBlock;
        msAssert.areEqual(expectedDisplayText, linkTextBlock.getText());
        msAssert.areEqual(expectedUri, linkTextBlock.getLinkDestinationBlock().getUri());
        msAssert.areEqual(expectedTitle, linkTextBlock.getLinkDestinationBlock().getTitle());
    }

    /// <summary>
    /// Verifies ImageDescription block.
    /// </summary>
    static void checkImageBlock(
        Block imageStartBlock,
        String expectedDescription, String expectedUri)
	{
		checkImageBlock(imageStartBlock, expectedDescription, expectedUri, "");
	}

    /// <summary>
    /// Verifies ImageDescription block.
    /// </summary>
    static void checkImageBlock(Block imageStartBlock,
        String expectedDescription, String expectedUri, String expectedTitle)
    {
        ImageDescriptionBlock imageDescriptionBlock = (ImageDescriptionBlock)imageStartBlock;
        msAssert.areEqual(expectedDescription, imageDescriptionBlock.getText());
        msAssert.areEqual(expectedUri, imageDescriptionBlock.getLinkDestinationBlock().getUri());
        msAssert.areEqual(expectedTitle, imageDescriptionBlock.getLinkDestinationBlock().getTitle());
    }

    /// <summary>
    /// Verifies Inline blocks inside a specified InlineContainer.
    /// </summary>
    static void checkInlines(Block inlineContainer, ExpectedInline[] expectedInlines)
    {
        int blockCount = expectedInlines.length;

        msAssert.areEqual(blockCount, ((ContainerBlock)inlineContainer).getCount());

        for (int i = 0; i < ((ContainerBlock)inlineContainer).getCount(); i++)
        {
            Block inlineBlock = ((ContainerBlock)inlineContainer).get(i);
            msAssert.areEqual(expectedInlines[i].getType(), inlineBlock.getType());
            msAssert.areEqual(expectedInlines[i].getText(), inlineBlock.getText());
        }
    }

    /// <summary>
    /// Verifies single Inline block.
    /// </summary>
    static void checkInline(Block inline, String expectedText)
    {
        msAssert.areEqual(BlockType.INLINE, inline.getType());
        msAssert.areEqual(expectedText, inline.getText());
    }

    /// <summary>
    /// Verifies Italic Inline block.
    /// </summary>
    static void checkItalicInline(Block inline, String expectedText)
    {
        msAssert.areEqual(BlockType.ITALIC_INLINE, inline.getType());
        msAssert.areEqual(expectedText, inline.getText());
    }

    /// <summary>
    /// Verifies LinkDefinition block.
    /// </summary>
    static void checkLinkDefinition(Block block, String expectedDestination)
	{
		checkLinkDefinition(block, expectedDestination, " ");
	}

    /// <summary>
    /// Verifies LinkDefinition block.
    /// </summary>
    static void checkLinkDefinition(Block block, String expectedDestination, String expectedUri)
	{
		checkLinkDefinition(block, expectedDestination, expectedUri, " ");
	}

    /// <summary>
    /// Verifies LinkDefinition block.
    /// </summary>
    static void checkLinkDefinition(Block block, String expectedDestination, String expectedUri, String expectedTitle)
    {
        LinkDefinitionBlock linkDefinitionBlock = (LinkDefinitionBlock)block;
        msAssert.areEqual(expectedDestination, msString.trim(linkDefinitionBlock.getDestination()));
        if (!" ".equals(expectedUri))
            msAssert.areEqual(expectedUri, linkDefinitionBlock.getLinkDestination().getUri());
        if (!" ".equals(expectedTitle))
            msAssert.areEqual(expectedTitle, linkDefinitionBlock.getLinkDestination().getTitle());
    }

    /// <summary>
    /// Verifies InlineContainer block.
    /// </summary>
    private static void checkInlineContainer(Block inlineContainer,
        /*BlockType*/byte expectedContainerBlockType, String expectedText)
    {
        msAssert.areEqual(expectedContainerBlockType, inlineContainer.getType());
        msAssert.areEqual(expectedText, inlineContainer.getText());
    }

    protected static /*final*/ String SOFT_BREAK = Character.toString(MarkdownUtil.SOFT_LINE_BREAK_CHAR);
    protected static /*final*/ String HARD_BREAK_SPACES = Character.toString(MarkdownUtil.HARD_LINE_BREAK_SPACES_CHAR);
    protected static /*final*/ String HARD_BREAK_SLASH = Character.toString(MarkdownUtil.HARD_LINE_BREAK_SLASH_CHAR);
}

