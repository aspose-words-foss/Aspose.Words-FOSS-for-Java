// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// The base class for containers of markdown Inline blocks.
/// </summary>
abstract class InlineContainerBlock extends ContainerBlock
{
    /// <summary>
    /// Parses text in content lines onto child Inline blocks.
    /// </summary>
    /*virtual*/ void parse() throws Exception
    {
        String inlineText = getInlineText();
        LinkedList delimiters = getDelimiters(getDelimiterRuns(inlineText, getLoadOptions()));

        link(delimiters);
        processDelimiters(delimiters, inlineText);

        // No need to keep text lines in a memory after they were parsed as inlines.
        getContentLines().clear();
        removeAllParts();
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        switch (block.getType())
        {
            case BlockType.INDENTED_CODE:
            case BlockType.PARAGRAPH:
            {
                // If block being appended is inside a Quote, then we might close some of the
                // parent Quotes to count their nesting level. As we encounter a non-quote
                // block that means Quote was already accounted, we MUST open all closed
                // Quotes to allow append into them.
                openParentQuotes();

                // We can merge contents (so called lazy-continuation).
                msArrayList.add(getContentLines(), block.getContentLine());
                return true;
            }
            case BlockType.END_OF_LINE:
            {
                // An EOL does not interrupt anything.
                add(block);
                return true;
            }
            default:
                return super.tryAppend(block);
        }
    }

    /// <summary>
    /// Opens all parent Quote blocks.
    /// </summary>
    protected void openParentQuotes()
    {
        openParents(BlockType.QUOTE);
    }

    /// <summary>
    /// Appends inline block with a specified text to this container.
    /// </summary>
    private void appendInline(String text) throws Exception
    {
        if (!StringUtil.hasChars(text))
            return;

        Block parentBlock = getDeepestOpenedChildBlock();
        if (parentBlock == null)
            parentBlock = this;

        // IN: Not sure, this switch block should be here. Looks like it
        // will be more appropriate to process it finally in Block.Write().
        String textToAppend;
        switch (parentBlock.getType())
        {
            case BlockType.INDENTED_CODE:
            case BlockType.FENCED_CODE:
            case BlockType.INLINE_CODE:
            {
                textToAppend = text;
                break;
            }

            case BlockType.LINK_TEXT:
            {
                ((LinkTextBlock)parentBlock).setRawText(text);
                textToAppend = MarkdownUtil.unescapeMarkupSymbols(text);

                break;
            }

            case BlockType.IMAGE_DESCRIPTION:
            {
                ((ImageDescriptionBlock)parentBlock).setRawText(text);
                textToAppend = MarkdownUtil.unescapeMarkupSymbols(text);

                break;
            }

            case BlockType.LINK_DESTINATION:
            {
                textToAppend = msString.replace(text, MarkdownUtil.SOFT_LINE_BREAK_CHAR, ControlChar.LINE_BREAK_CHAR);
                break;
            }

            default:
            {
                String parsedText = parseHardLineBreaks(text);
                textToAppend = MarkdownUtil.unescapeMarkupSymbols(parsedText);

                break;
            }
        }

        // Finally, append inline block with a non-empty text.
        if (textToAppend.length() == 0)
            return;

        // Don't split to HTML inlines for these block types.
        if (parentBlock.getType() == BlockType.INDENTED_CODE ||
            parentBlock.getType() == BlockType.FENCED_CODE ||
            parentBlock.getType() == BlockType.INLINE_CODE ||
            parentBlock.getType() == BlockType.LINK_DESTINATION ||
            parentBlock.getType() == BlockType.AUTOLINK)
        {

            InlineBlock inlineBlock = new InlineBlock(textToAppend);
            if (!tryAppend(inlineBlock))
                add(inlineBlock);
        }
        else
        {
            // WORDSNET-27290 Implemented loading of raw HTML blocks.
            ArrayList<Block> inlineBlocks = getInlines(textToAppend);
            for (Block inlineBlock : inlineBlocks)
            {
                if (!tryAppend(inlineBlock))
                    add(inlineBlock);
            }
        }
    }

    /// <summary>
    /// Returns a collection of inline blocks (<see cref="InlineBlock"/> or <see cref="HtmlTagBlock"/>)
    /// parsed from a specified string.
    /// </summary>
    private static ArrayList<Block> getInlines(String text) throws Exception
    {
        ArrayList<Block> inlineBlocks = new ArrayList<Block>();

        HtmlTokenizer tokenizer = new HtmlTokenizer(text, new Features());

        // Check there is no any valid HTML tokens, but there is non-empty string is passed.
        HtmlToken token = tokenizer.nextToken(false);
        if (token.getType() == HtmlTokenType.END_OF_FILE)
        {
            msArrayList.add(inlineBlocks, new InlineBlock(text));
            return inlineBlocks;
        }

        // Process HTML tokens.
        while (token.getType() != HtmlTokenType.END_OF_FILE)
        {
            switch (token.getType())
            {
                case HtmlTokenType.TAG:
                {
                    HtmlTagToken tagToken = (HtmlTagToken)token;

                    // We need this because HtmlToken doesn't preserve arguments order after it is parsed.
                    // In this case we cannot just restore text of html tag and forced to fall back to
                    // general inline block. For correct <http> tag we assume that there should be exactly
                    // one attribute.
                    if (msString.startsWith(tagToken.getName(), "http", StringComparison.ORDINAL_IGNORE_CASE) && (tagToken.getAttributes().getCount() != 1))
                    {
                        msArrayList.add(inlineBlocks, new InlineBlock(text));
                    }
                    else
                    {
                        msArrayList.add(inlineBlocks, new HtmlTagBlock((HtmlTagToken)token));
                    }

                    break;
                }
                case HtmlTokenType.TEXT:
                {
                    msArrayList.add(inlineBlocks, new InlineBlock(((HtmlTextToken)token).getText()));
                    break;
                }
            }

            token = tokenizer.nextToken(false);
        }

        return inlineBlocks;
    }

    /// <summary>
    /// Gets a plain text from the content lines of the inline container.
    /// </summary>
    protected /*virtual*/ String getInlineText()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getContentLines().size(); i++)
        {
            String line = getContentLines().get(i);
            // Do not append an empty line.
            if (line.length() > 0)
            {
                msStringBuilder.append(sb, line);

                // Append EOL to each line except of very last one.
                if (i < (getContentLines().size() - 1))
                    sb.append(MarkdownUtil.SOFT_LINE_BREAK_CHAR);
            }
        }

        return msString.trim(sb.toString());
    }

    /// <summary>
    /// Returns index of hard line break in a specified string, or -1 if not found.
    /// </summary>
    private static int indexOfHardLineBreak(String txtLine)
    {
        if (!StringUtil.hasChars(txtLine))
            return -1;

        int index = txtLine.length() - 1;

        // Check case with backslash at the end of line.
        if ((txtLine.charAt(index) == '\\') && !MarkdownUtil.isEscaped(txtLine, index))
            return index;

        // Check case with 2 or more spaces at the end of line.
        while (index >= 0 && txtLine.charAt(index) == ' ')
            index--;

        return (txtLine.length() - index) > 2 ? (index + 1) : -1;
    }

    /// <summary>
    /// Gets a collection of the <see cref="DelimiterRun"/> objects from a specified text.
    /// </summary>
    private static ArrayList<DelimiterRun> getDelimiterRuns(String text, MarkdownLoadOptions loadOptions)
    {
        ArrayList<DelimiterRun> delimiterRuns = new ArrayList<DelimiterRun>();

        int start = 0;
        while (start < text.length())
        {
            DelimiterRun delimiterRun = new DelimiterRun(loadOptions);
            if (delimiterRun.tryParse(text, start))
            {
                msArrayList.add(delimiterRuns, delimiterRun);
                start += delimiterRun.getLength();
            }
            else
            {
                // If delimiter was not parsed successfully due to it is escaped,
                // then try to skip escaped character. Otherwise, there are no more
                // delimiter runs, so we should stop to search.
                if (delimiterRun.isEscaped())
                    start = delimiterRun.getStart() + 1;
                else
                    break;
            }
        }

        return delimiterRuns;
    }

    /// <summary>
    /// Gets a doubly linked list of the <see cref="Delimiter"/> objects
    /// from a specified collection of delimiter runs.
    /// </summary>
    private static LinkedList getDelimiters(ArrayList<DelimiterRun> delimiterRuns)
    {
        LinkedList delimiters = new LinkedList();

        for (DelimiterRun delimiterRun : delimiterRuns)
        {
            for (Delimiter delimiter : delimiterRun.getDelimiters())
            {
                if (delimiter.isValid())
                {
                    delimiters.append(delimiter);
                    delimiters.moveObjectSafe(delimiter);
                }
            }
        }

        return delimiters;
    }

    /// <summary>
    /// Links delimiters in a specified doubly linked list so
    /// that they form a pair of Opening and Closing delimiters.
    /// </summary>
    private void link(LinkedList delimiters)
    {
        delimiters.reset();
        while (delimiters.moveNext())
        {
            Delimiter closingDelimiter = ((Delimiter)delimiters.getCurrent()).getPotentialClosingForward();
            if (closingDelimiter != null)
            {
                delimiters.moveObject(closingDelimiter);

                Delimiter openingDelimiter = closingDelimiter.getPotentialOpeningBackward();
                if (canBeLinked(openingDelimiter, closingDelimiter))
                {
                    link(openingDelimiter, closingDelimiter, delimiters);
                    // The InlineLink (or Image) is a complex object that consists of two parts:
                    // LinkText (or ImageDescription in case of it is an Image) and a corresponding LinkDestination.
                    if (openingDelimiter.getType() == DelimiterType.LINK_DESTINATION_OPENING ||
                        openingDelimiter.getType() == DelimiterType.LINK_TEXT_OPENING)
                        linkLinkText(openingDelimiter, delimiters);
                }
            }
        }
    }

    /// <summary>
    /// Links LinkText (or ImageDescription) delimiters corresponded to a specified LinkDestination.
    /// </summary>
    private static void linkLinkText(Delimiter linkOpening, LinkedList delimiters)
    {
        Debug.assertTrue(linkOpening.isLinked());

        Delimiter linkTextClosing = linkOpening.getLinkTextClosing();
        if (linkTextClosing == null)
            return;

        Delimiter linkTextOpening = (linkTextClosing.isLinked())
            ? (linkTextClosing.getLinkedDelimiter().getType() == DelimiterType.FOOTNOTE_OPENING)
                ? ((FootnoteOpeningDelimiter)linkTextClosing.getLinkedDelimiter()).getLinkTextOpening()
                : linkTextClosing.getLinkedDelimiter()
            : linkTextClosing.getPotentialOpeningBackward();

        if (linkTextOpening == null)
            return;

        link(linkTextOpening, linkTextClosing, delimiters);

        // Now we need to link delimiters inside the just linked LinkText.
        Delimiter start = (Delimiter)linkTextOpening.getNextNode();
        // In case of link text is actually an ImageDescription,
        // we have to skip the LinkText delimiter immediately following it.
        if (linkTextOpening.getType() == DelimiterType.IMAGE_DESCRIPTION_OPENING)
            start = (Delimiter)start.getNextNode();

        while (start != linkTextClosing)
        {
            if (!start.isNotIncluded())
            {
                // Find closing delimiter located before the end of link text.
                Delimiter closing = start.getPotentialClosingForward();
                if ((closing == null) || !closing.isBefore(linkTextClosing))
                    break;

                // Find opening delimiter located after the stat of link text.
                Delimiter opening = closing.getPotentialOpeningBackward();
                if ((opening != null) && linkTextOpening.isBefore(opening))
                {
                    link(opening, closing, delimiters);
                    // The InlineLink (or Image) is a complex object that consists of two parts:
                    // LinkText (or ImageDescription in case of it is an Image) and a corresponding LinkDestination.
                    if (opening.getType() == DelimiterType.LINK_DESTINATION_OPENING)
                        linkLinkText((LinkDestinationOpeningDelimiter)opening, delimiters);

                    start = (Delimiter)closing.getNextNode();
                }
                else
                {
                    start = (Delimiter)start.getNextNode();
                }
            }
        }
    }

    /// <summary>
    /// Links specified delimiters.
    /// </summary>
    private static void link(Delimiter openingDelimiter, Delimiter closingDelimiter, LinkedList delimiters)
    {
        // When we link delimiters into a pair, their lengths might been different,
        // so there can be a delimiter that is remained from one of those paired delimiters.
        Delimiter remainedDelimiter = closingDelimiter.link(openingDelimiter);

        if (remainedDelimiter != null)
        {
            if (remainedDelimiter.getEnd() < openingDelimiter.getStart())
            {
                // If delimiter is remained from the Opening, then insert it before it.
                delimiters.moveObjectSafe(openingDelimiter);
                delimiters.insert(remainedDelimiter);
            }
            else
            {
                // If delimiter is remained after the Closing, then insert it after it.
                delimiters.moveObjectSafe(closingDelimiter);
                delimiters.append(remainedDelimiter);
            }

            delimiters.moveObjectSafe(closingDelimiter);
        }

        // Unlink previously linked delimiters inside the new linked pair in accordance with their priority.
        unLink(openingDelimiter, closingDelimiter);

        // Each type of delimiter has its maximum allowed length when it is in a linked state.
        openingDelimiter.split(delimiters);
    }

    /// <summary>
    /// Unlinks linked delimiters between specified ones in accordance with their priority.
    /// </summary>
    private static void unLink(Delimiter a, Delimiter b)
    {
        Delimiter start = a.isBefore(b) ? a : b;
        Delimiter end = a.isBefore(b) ? b : a;

        // We skip range bounds, so move to next node immediately.
        start = (Delimiter)start.getNextNode();
        while (start != end)
        {
            if (start.isLinked() && (start.getPriority() < a.getUnlinkPriority()))
                start.unLink();

            start = (Delimiter)start.getNextNode();
        }
    }

    // IN: This probbaly should be done in Delimiter class.
    /// <summary>
    /// Returns true if the specified opening and closing delimiters can be linked.
    /// </summary>
    private boolean canBeLinked(Delimiter openingDelimiter, Delimiter closingDelimiter)
    {
        if ((openingDelimiter == null) || (closingDelimiter == null))
            return false;

        if (closingDelimiter.getType() == DelimiterType.AUTO_LINK_CLOSING)
        {
            if (!AutolinkInlineBlock.isValid(openingDelimiter, closingDelimiter))
                return false;
        }

        if (closingDelimiter.getType() == DelimiterType.LINK_TEXT_CLOSING)
        {
            switch (openingDelimiter.getType())
            {
                case DelimiterType.IMAGE_DESCRIPTION_OPENING:
                    return canBeLinkedImageDescriptionDelimiters(ms.as(openingDelimiter, ImageDescriptionOpeningDelimiter.class),
                        ms.as(closingDelimiter, LinkTextClosingDelimiter.class));
                case DelimiterType.FOOTNOTE_OPENING:
                    return true;
                case DelimiterType.LINK_TEXT_OPENING:
                    return canBeLinkedLinkTextDelimiters(ms.as(openingDelimiter, LinkTextOpeningDelimiter.class),
                        ms.as(closingDelimiter, LinkTextClosingDelimiter.class));
                default:
                    return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Returns true if the specified opening and closing LinkText delimiters can be linked.
    /// </summary>
    private boolean canBeLinkedLinkTextDelimiters(LinkTextOpeningDelimiter linkTextOpen, LinkTextClosingDelimiter linkTextClose)
    {
        if ((linkTextOpen == null) || (linkTextClose == null))
            return false;

        if (linkTextClose.hasLinkDestination() || linkTextClose.hasReferenceLinkText() || linkTextClose.hasCollapsedLinkText())
            return true;

        String linkText = (linkTextOpen.getStart() < linkTextClose.getEnd())
            ? msString.toUpper(linkTextOpen.getText().substring((linkTextOpen.getStart() + 1), (linkTextOpen.getStart() + 1) + (linkTextClose.getEnd() - linkTextOpen.getStart() - 1)), msCultureInfo.getInvariantCulture())
            : "";

        return getDocument().getLinkDefinitions().containsKey(linkText);
    }

    /// <summary>
    /// Returns true if the specified opening ImageDescription and closing LinkText delimiters can be linked.
    /// </summary>
    private boolean canBeLinkedImageDescriptionDelimiters(ImageDescriptionOpeningDelimiter imageDescOpen, LinkTextClosingDelimiter linkTextClose)
    {
        if ((imageDescOpen == null) || (linkTextClose == null))
            return false;

        if (linkTextClose.hasLinkDestination())
            return true;

        String linkText = (imageDescOpen.getStart() < linkTextClose.getEnd())
            ? msString.toUpper(imageDescOpen.getText().substring((imageDescOpen.getStart() + 2), (imageDescOpen.getStart() + 2) + (linkTextClose.getEnd() - imageDescOpen.getStart() - 2)), msCultureInfo.getInvariantCulture())
            : "";

        return getDocument().getLinkDefinitions().containsKey(linkText);
    }

    /// <summary>
    /// Processes delimiters in a specified text by converting them to a concrete inline blocks.
    /// </summary>
    private void processDelimiters(LinkedList delimiters, String text) throws Exception
    {
        delimiters.reset();

        Delimiter prevLinkedDelimiter = null;
        String inlineText;

        msStack<Block> inlineContainers = new msStack<Block>();
        msStack<ReferenceBlock> unresolvedLinks = new msStack<ReferenceBlock>();

        while (delimiters.moveNext())
        {
            Delimiter curLinkedDelimiter = (Delimiter)delimiters.getCurrent();
            if (curLinkedDelimiter.isLinked())
            {
                // The text of ImageDescription is actually resides inside a corresponding LinkText.
                if ((prevLinkedDelimiter != null) && (prevLinkedDelimiter.getType() == DelimiterType.IMAGE_DESCRIPTION_OPENING))
                    prevLinkedDelimiter = ((ImageDescriptionOpeningDelimiter)prevLinkedDelimiter).getLinkTextOpening();

                // The text before FootnoteReference is actually resides before the corresponding LinkText.
                inlineText = getSubstring(text, prevLinkedDelimiter,
                    (curLinkedDelimiter.getType() == DelimiterType.FOOTNOTE_OPENING)
                        ? ((FootnoteOpeningDelimiter)curLinkedDelimiter).getLinkTextOpening()
                        : curLinkedDelimiter);

                // Append text located before current opening delimiter.
                appendInline(MarkdownUtil.unEscape(inlineText).toString());

                if (curLinkedDelimiter.isOpening())
                {
                    Block inlineContainer = curLinkedDelimiter.toBlock();
                    inlineContainers.push(inlineContainer);
                    if (!tryAppend(inlineContainer))
                        add(inlineContainer);
                }
                else
                {
                    // Normally, we cannot encounter closing delimiter before its opening.
                    Debug.assertTrue(inlineContainers.getCount() > 0);

                    Block inlineContainer = inlineContainers.pop();
                    inlineContainer.close();

                    // Resolve LinkDefinitions.
                    if ((inlineContainer.getType() == BlockType.LINK_TEXT) || (inlineContainer.getType() == BlockType.IMAGE_DESCRIPTION))
                        if (((ReferenceBlock)inlineContainer).resolve(getDocument().getLinkDefinitions(), unresolvedLinks))
                            inlineContainer.remove();

                    // Resolve FootnoteReference.
                    if (inlineContainer.getType() == BlockType.FOOTNOTE_REFERENCE)
                        ((FootnoteReferenceBlock)inlineContainer).resolve(getDocument().getFootnoteDefinitions());
                }

                prevLinkedDelimiter = curLinkedDelimiter;
            }
        }

        while (unresolvedLinks.getCount() > 0)
        {
            ReferenceBlock linkTextBlock = unresolvedLinks.pop();
            if (!linkTextBlock.hasLinkDestination())
                linkTextBlock.expandInlineLinkText();
        }

        // Process a last piece of text.
        inlineText = getSubstring(text, prevLinkedDelimiter, null);
        appendInline(MarkdownUtil.unEscape(inlineText).toString());
    }

    /// <summary>
    /// Gets text between specified delimiters.
    /// </summary>
    private static String getSubstring(String text, Delimiter delimiterBefore, Delimiter delimiterAfter)
    {
        int startIdx = (delimiterBefore == null) ? 0 : delimiterBefore.getEnd() + 1;
        int length = ((delimiterAfter == null) ? text.length() : delimiterAfter.getStart()) - startIdx;

        if (length == 0)
            return "";

        if (length == text.length())
            return text;

        return text.substring((startIdx), (startIdx) + (length));
    }

    /// <summary>
    /// Parses a specified text to a HardLineBreaks
    /// (<see cref="MarkdownUtil.HardLineBreakSpacesChar"/> and
    /// <see cref="MarkdownUtil.HardLineBreakSlashChar"/>).
    /// </summary>
    private static String parseHardLineBreaks(String text)
    {
        StringBuilder sb = new StringBuilder();

        int startIndex = 0;
        int softLineBreakIndex = text.indexOf(MarkdownUtil.SOFT_LINE_BREAK_CHAR);
        while (softLineBreakIndex != -1)
        {
            String line = text.substring((startIndex), (startIndex) + (softLineBreakIndex - startIndex));
            if (line.length() > 0)
            {
                int hardLineBreakIndex = indexOfHardLineBreak(line);
                if (hardLineBreakIndex != -1)
                {
                    msStringBuilder.append(sb, line.substring((0), (0) + (hardLineBreakIndex)));
                    sb.append(line.charAt(hardLineBreakIndex) == '\\'
                        ? MarkdownUtil.HARD_LINE_BREAK_SLASH_CHAR
                        : MarkdownUtil.HARD_LINE_BREAK_SPACES_CHAR);
                }
                else
                {
                    msStringBuilder.append(sb, line);
                    sb.append(MarkdownUtil.SOFT_LINE_BREAK_CHAR);
                }
            }
            else
            {
                sb.append(MarkdownUtil.SOFT_LINE_BREAK_CHAR);
            }

            startIndex = softLineBreakIndex + 1;
            softLineBreakIndex = text.indexOf(MarkdownUtil.SOFT_LINE_BREAK_CHAR, startIndex);
        }

        msStringBuilder.append(sb, text.substring(startIndex));
        return sb.toString();
    }


    /// <summary>
    /// Gets content lines.
    /// </summary>
    ArrayList<String> getContentLines() { return mContentLines; }

    /// <summary>
    /// Gets <see cref="MarkdownLoadOptions"/>.
    /// </summary>
    private MarkdownLoadOptions getLoadOptions() { return (getDocument() == null) ? null : getDocument().getLoadOptions(); }

    private /*final*/ ArrayList<String> mContentLines = new ArrayList<String>();

    /// <summary>
    /// The line separator to insert after each content line in a plain text.
    /// </summary>
    static final char LINE_SEPARATOR = ControlChar.LINE_BREAK_CHAR;
}

