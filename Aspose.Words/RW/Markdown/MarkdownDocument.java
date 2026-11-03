// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.HashMap;


/// <summary>
/// Represents a markdown document.
/// </summary>
class MarkdownDocument extends ContainerBlock
{
    /// <summary>
    /// Initializes a new instance of <see cref="MarkdownDocument"/> class.
    /// </summary>
    MarkdownDocument()
    {
    }

    /// <summary>
    /// Initializes a new instance of <see cref="MarkdownDocument"/> class.
    /// </summary>
    MarkdownDocument(MarkdownLoadOptions loadOptions)
    {
        mLoadOptions = loadOptions;
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return false;
    }

    /// <summary>
    /// Reads a content from a specified reader into the markdown document.
    /// </summary>
    void read(StreamReader reader, LoadingProgressProcessor progressProcessor) throws Exception
    {
        String txtLine = reader.readLine();

        // Note, a markdown block can occupy NOT whole line of a text,
        // so this is a start position of the block inside a current line.
        int start = 0;
        while (txtLine != null)
        {
            // WORDSNET-22892 Adding a Callback event that notifies the progress of loading document.
            if (progressProcessor != null)
                progressProcessor.execute(reader.getBaseStream());

            mCurParsedBlock = mBlockParser.parse(txtLine, start);

            // Actually, any line of a text MUST be parsed successfully. Even if it was not parsed
            // as a some special block type, it had to be parsed as a regular paragraph.
            if (mCurParsedBlock == null)
                throw new IllegalStateException("Cannot parse a line of text.");

            // Check if the current block falls under some parent list item block, then reparse it appropriately.
            // WORDSNET-26368 Do not reparse Table block as list item. Actually, I think Table block can be
            // inside a list in Markdown. However, it can not be a list item in DOM. So, for a moment just
            // excluded from reparsing.
            ListItemBlock listItemBlock = null;
            if (mCurParsedBlock.getType() != BlockType.TABLE)
                listItemBlock = tryReparseAsListItem(txtLine, start);

            boolean isAppended = tryAppend(mCurParsedBlock);

             // WORDSNET-26064 Implemented a new load option.
            if ((getLoadOptions() != null) && getLoadOptions().getPreserveEmptyLines() &&
                (mCurParsedBlock.getType() == BlockType.BLANK_LINE))
            {
                // IndentedCode allows blank lines by design,
                // so do not add extra ones in this case.
                if ((getLast() == null) || (getLast().getType() != BlockType.INDENTED_CODE))
                    add(new ParagraphBlock()).close();
            }

            // Sometimes while we are trying to append a block there can be a situation
            // when processed block type MUST be excluded from the parser to allow
            // it to be parsed as a some another block type. For example, see SetextHeading.
            if (mCurParsedBlock.getAction() == BlockAction.EXCLUDE)
            {
                mBlockParser.exclude(mCurParsedBlock.getType());
                continue;
            }

            if (!isAppended)
                addBlock(mCurParsedBlock);

            if (listItemBlock != null)
                listItemBlock.setAction(BlockAction.NONE);

            // Some types might been excluded from a parser (see code above),
            // so initialize it again with the all possible types.
            mBlockParser.initTypes();

            // Advance start position to a next block.
            start += mCurParsedBlock.getLength();
            Debug.assertTrue(start <= txtLine.length());

            // Cache footnote definitions to perform a quick resolve later.
            // Note, footnote references are inline level nodes, so they are not defined at this point
            // and will be parsed after call to ParseInlines() below.
            if (mCurParsedBlock.getType() == BlockType.FOOTNOTE_DEFINITION)
                msArrayList.add(mFootnoteDefinitions, (FootnoteDefinitionBlock)mCurParsedBlock);

            // If there is no remaining text in the current line, then read a new one.
            if ((mCurParsedBlock.getType() == BlockType.END_OF_LINE) || (mCurParsedBlock.getType() == BlockType.BLANK_LINE))
            {
                txtLine = reader.readLine();
                start = 0;
            }
        }

        parseLinkDefinitions();

        // Remove unnecessary last EOL from a tree, if any.
        Block lastBlock = getDeepestOpenedChildBlock();
        if ((lastBlock != null) && (lastBlock.getType() == BlockType.END_OF_LINE))
            lastBlock.remove();

        // After all block structures are read, we can parse InlineContainer blocks.
        parseInlines(this);
    }

    /// <summary>
    /// Adds a specified block to the root of the document.
    /// </summary>
    private void addBlock(Block block)
    {
        // It is not necessary to keep blank blocks in a markdown tree.
        if ((block.getType() != BlockType.BLANK_LINE) && (block.getType() != BlockType.END_OF_LINE))
            add(block);
    }

    /// <summary>
    /// Parses link reference definition blocks.
    /// </summary>
    private void parseLinkDefinitions()
    {
        ArrayList<InlineContainerBlock> emptyParas = new ArrayList<InlineContainerBlock>();
        for (Block childBlock : (Iterable<Block>) this)
        {
            if ((childBlock.getType() == BlockType.PARAGRAPH) || (childBlock.getType() == BlockType.SETEXT_HEADING))
            {
                InlineContainerBlock paragraphBlock = (InlineContainerBlock)childBlock;

                 // WORDSNET-26064 We have introduced a new option that allows to preserve blank lines.
                 // So, let's skip paragraphs that are blank lines originally to not remove them later
                 // at the end of parse process.
                if (paragraphBlock.isEmpty() && (paragraphBlock.getContentLines().size() == 0))
                    continue;

                StringBuilder linkDefinitionContent = new StringBuilder();
                LinkDefinitionBlock linkDefinitionBlock = new LinkDefinitionBlock();

                ArrayList<String> contentLines = paragraphBlock.getContentLines();
                int startContentIndex = 0;
                int endContentIndex = 0;
                while (endContentIndex < contentLines.size())
                {
                    String linkDefCandidate = msString.format("{0} {1}",
                        linkDefinitionContent,
                        contentLines.get(endContentIndex));
                    if (!linkDefinitionBlock.tryParse(linkDefCandidate, 0))
                    {
                        linkDefinitionBlock.reset();
                        linkDefinitionBlock.tryParse(linkDefinitionContent.toString(), 0);
                        linkDefinitionContent.setLength(0);
                        if (linkDefinitionBlock.isValid())
                        {
                            endContentIndex = 0;
                            if (!mLinkDefinitions.containsKey(linkDefinitionBlock.getReference()))
                                msDictionary.add(mLinkDefinitions, linkDefinitionBlock.getReference(), linkDefinitionBlock);
                            msArrayList.removeRange(contentLines, startContentIndex, endContentIndex - startContentIndex + 1);
                            linkDefinitionBlock = new LinkDefinitionBlock();
                        }
                        else
                            endContentIndex++;

                        startContentIndex = endContentIndex;
                    }
                    else
                    {
                        msStringBuilder.appendLine(linkDefinitionContent, contentLines.get(endContentIndex));
                        endContentIndex++;
                    }
                }

                if (linkDefinitionBlock.isValid())
                {
                    endContentIndex--;
                    if (!mLinkDefinitions.containsKey(linkDefinitionBlock.getReference()))
                        msDictionary.add(mLinkDefinitions, linkDefinitionBlock.getReference(), linkDefinitionBlock);
                    msArrayList.removeRange(contentLines, startContentIndex, endContentIndex - startContentIndex + 1);
                }

                if ((paragraphBlock.getCount() == 0) && (paragraphBlock.getContentLines().size() == 0))
                    msArrayList.add(emptyParas, paragraphBlock);
            }
        }

        for (InlineContainerBlock paragraphBlock : emptyParas)
            paragraphBlock.remove();
    }

    /// <summary>
    /// Parses text inside child InlineContainers onto Inline blocks.
    /// </summary>
    private static void parseInlines(Block block) throws Exception
    {
        InlineContainerBlock inlineContainer = ms.as(block, InlineContainerBlock.class);
        if (inlineContainer != null)
        {
            inlineContainer.parse();
        }
        else
        {
            ContainerBlock containerBlock = ms.as(block, ContainerBlock.class);
            if (containerBlock != null)
            {
                for (Block childBlock : containerBlock)
                    parseInlines(childBlock);
            }
        }
    }

    /// <summary>
    /// Tries to reparse the current block as a list item.
    /// </summary>
    /// <returns>
    /// Returns a list item block where re-parsed block should be added,
    /// or <c>null</c> if current block does not fall under any list item.
    /// </returns>
    private ListItemBlock tryReparseAsListItem(String txtLine, int start)
    {
        ListItemBlock listItemBlock =
            (ListItemBlock)getDeepestOpenedChildBlock(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);

        while ((listItemBlock != null) && (listItemBlock.getAction() != BlockAction.REPARSE_LIST_ITEM))
        {
            // In order to fall under the list item inside a Quote, the block must be in Quote too.
            if ((!listItemBlock.isInQuote() || (listItemBlock.getParent(BlockType.QUOTE, false) != null))
                && (mCurParsedBlock.getIndentation() >= listItemBlock.getListLabelLength()))
            {
                // The block indentation must be greater or equal to
                // list label length in order to fall under the list item.
                // Get number of characters to skip in original text for a new block.
                int skipCount = MarkdownUtil.getCharsCount(txtLine, start, listItemBlock.getListLabelLength());
                // Reparse as block inside the list item.
                mCurParsedBlock = mBlockParser.parse(txtLine, start + skipCount);
                if (mCurParsedBlock == null)
                    throw new IllegalStateException("Cannot parse a line of text inside a list item.");

                // Adjust an opening indentation sequence.
                mCurParsedBlock.setOpeningIndentation(msString.format("{0}{1}", txtLine.substring((start), (start) + (skipCount)), mCurParsedBlock.getOpeningIndentation()));

                listItemBlock.setAction(BlockAction.REPARSE_LIST_ITEM);
                break;
            }

            listItemBlock =
                (ListItemBlock)listItemBlock.getPreviousDeepTraversed(true,
                    BlockType.BULLET_LIST_ITEM,
                    BlockType.ORDERED_LIST_ITEM);
        }

        return listItemBlock;
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.DOCUMENT; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.BLOCK; }

    /// <summary>
    /// Gets Link Definitions.
    /// </summary>
    HashMap<String, LinkDefinitionBlock> getLinkDefinitions() { return mLinkDefinitions; }

    /// <summary>
    /// Gets Footnote Definitions.
    /// </summary>
    ArrayList<FootnoteDefinitionBlock> getFootnoteDefinitions() { return mFootnoteDefinitions; }

    /// <summary>
    /// Markdown load options.
    /// </summary>
    MarkdownLoadOptions getLoadOptions() { return mLoadOptions; };

    private MarkdownLoadOptions mLoadOptions;

    private /*final*/ BlockParser mBlockParser = new BlockParser();

    /// <summary>
    /// The currently parsed block.
    /// </summary>
    private Block mCurParsedBlock;

    /// <summary>
    /// The collection of FootnoteDefinitionBlocks in the document, in document order.
    /// </summary>
    /// <remarks>
    /// A list, not a set: when several definitions share a label the first one wins, so the order is part
    /// of the result. A hash set enumerates in insertion order only by accident of the .NET implementation,
    /// and in no particular order in Java.
    /// </remarks>
    private /*final*/ ArrayList<FootnoteDefinitionBlock> mFootnoteDefinitions = new ArrayList<FootnoteDefinitionBlock>();

    private /*final*/ HashMap<String, LinkDefinitionBlock> mLinkDefinitions = new HashMap<String, LinkDefinitionBlock>();
}

