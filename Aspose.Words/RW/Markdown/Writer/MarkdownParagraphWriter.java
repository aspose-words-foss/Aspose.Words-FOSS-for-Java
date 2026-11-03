// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/11/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.FontUtil;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FormatterPal;
import com.aspose.ms.System.StringComparison;
import java.util.ArrayList;


/// <summary>
/// The class responsible for saving paragraph in markdown format.
/// </summary>
class MarkdownParagraphWriter
{
    /// <summary>
    /// Creates a new instance from a specified paragraph.
    /// </summary>
    MarkdownParagraphWriter(Paragraph paragraph, MarkdownWriter writer) throws Exception
    {
        Debug.assertTrue(paragraph != null);
        Debug.assertTrue(writer != null);

        mParagraph = paragraph;
        mWriter = writer;
        mPrevParagraphWriter = mWriter.getPrevParagraphWriter();
        mEmphasesWriter = new MarkdownEmphasesWriter(mContentLines, mWriter, getDocument().getWarningCallback());

        init();

        if (isNeedParagraphBreak())
            appendText(ControlChar.LINE_BREAK);
    }

    /// <summary>
    /// Writes content lines of the paragraph into underlying writer.
    /// </summary>
    void write() throws Exception
    {
        // WORDSNET-28043 Added new export option for empty paragraphs.
        // Note, in CodeBlock and Footnote, we should write empty paragraphs as usual.
        if (mParagraph.isEmptyOrContainsOnlyCrossAnnotation() && !isInCodeBlock() && !isInFootnote())
        {
            writeEmptyParagraph();
            if (mWriter.getSaveOptions().getEmptyParagraphExportMode() == MarkdownEmptyParagraphExportMode.NONE)
                return;
        }

        // Let's add one empty line for empty paragraph in order to write its styles markers properly.
        if (mContentLines.getCount() == 0)
            msStringBuilder.append(getCurrentLine(), "");

        updateStylesMarkers();

        if (isFencedCode())
            writeFencedCodeOpening();

        writeContentLines();

        if (isFencedCode())
            writeFencedCodeClosing();

        if (mIsSetextHeading)
            writeSetextHeading();

        if (!isInCell() && !isInFootnote())
            msStringBuilder.append(getBuilder(), getParagraphBreak());
    }

    /// <summary>
    /// Processes a specified run.
    /// </summary>
    void onRun(Run run)
    {
        appendEmphases(run);

        if (run != null)
        {
            String text = TxtWriterBase.getText(run);

            // Don't write custom reference mark of footnote.
            if (isFirstParaInFootnote() && run.isFirstChild())
            {
                Footnote footnote = mWriter.getFootnoteWriter().getFootnote();
                if (msString.equals(text, footnote.getReferenceMark()))
                    return;
            }

            appendText(text);
        }
    }

    /// <summary>
    /// Appends emphases for a specified inline node.
    /// </summary>
    void appendEmphases(IInline inline)
    {
        // Ignore emphases inside code blocks.
        if (!isIndentedCode() && !isFencedCode())
            mEmphasesWriter.appendEmphases(inline);
    }

    /// <summary>
    /// Appends a specified text to the content lines.
    /// </summary>
    void appendText(String text)
	{
		appendText(text, true);
	}

    /// <summary>
    /// Appends a specified text to the content lines.
    /// </summary>
    void appendText(String text, boolean isNeedEscaping)
    {
        if (!StringUtil.hasChars(text))
            return;

        // If text starts from the same character as InlineCode delimiter and InlineCode emphasis is
        // in 'Opening' state, then we need to escape this character with space to avoid it to be
        // a continuation of the opening sequence of the InlineCode delimiter.
        if ((text.charAt(0) == InlineCodeDelimiter.CHARACTER) && mEmphasesWriter.isInlineCodeOpening())
            getCurrentLine().append(' ');

        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            switch (c)
            {
                case ControlChar.LINE_BREAK_CHAR:
                {
                    mContentLines.newLine();
                    break;
                }
                case ControlChar.PAGE_BREAK_CHAR:
                {
                    if (mWriter.getSaveOptions().getForcePageBreaks())
                        msStringBuilder.append(getCurrentLine(), ControlChar.PAGE_BREAK);
                    break;
                }
                case '.':
                {
                    // See TestListEscaping for more details.
                    // WORDSNET-25298 Consider 'isNeedEscaping' option.
                    if (isNeedEscaping &&
                        (getBlockType(getCurrentLine() + ".") == BlockType.ORDERED_LIST_ITEM) && (!mParagraph.isListItem()))
                    {
                        getCurrentLine().append(ControlChar.BACKSLASH_CHAR);
                    }

                    mEmphasesWriter.flushPendingOpeningEmphases();
                    getCurrentLine().append('.');
                    break;
                }
                default:
                {
                    if (!msChar.isWhiteSpace(c))
                    {
                        // Check if the current line starts with whitespace characters,
                        // then wrap it into InlineCode to preserve in exported Markdown paragraph.
                        if ((getCurrentLine().length() > 0) && StringUtil.containsOnlyWhitespaces(getCurrentLine()))
                        {
                            // Let's consider single whitespace in footnote description as a separator between
                            // its reference and its text and do not wrap it into InlineCode markers.
                            if (!isInFootnote() || getCurrentLine().length() > 1)
                                wrapIntoInlineCode();
                        }

                        mEmphasesWriter.flushPendingOpeningEmphases();

                        if (isNeedEscaping)
                        {
                            boolean isInCode = (isInCodeBlock() || mEmphasesWriter.isInInlineCode());
                            if (!isInCode && !mWriter.getUseHtmlSyntax() && MarkdownUtil.isEscapableMarkupCharacter(c))
                                getCurrentLine().append(ControlChar.BACKSLASH_CHAR);
                        }
                    }

                    getCurrentLine().append(FontUtil.unicodeToSymbol(c));
                    break;
                }
            }
        }
    }

    /// <summary>
    /// Returns <paramref name="node"/> if it is numbered paragraph itself,
    /// or previous sibling numbered paragraph that has almost
    /// the same left indent as a specified node.
    /// <seealso cref="MaxLeftIndentDeviationPercent"/>.
    /// </summary>
    static Paragraph getListItemSource(Node node)
    {
        if ((node.getNodeType() == NodeType.PARAGRAPH) && ((Paragraph)node).isListItem())
            return (Paragraph)node;

        // WORDSNET-26133 Consider also 'Markdown' list item.
        int leftIndent = MarkdownUtil.getLeftIndent(node);
        if (leftIndent == -1)
            return null;

        Node prevNode = node.getPreviousSibling();
        while (prevNode != null)
        {
            int prevLeftIndent = MarkdownUtil.getLeftIndent(prevNode);
            if (prevLeftIndent == -1)
                return null;

            // WORDSNET-26757 Allow non-strict comparision with specified allowed deviation.
            double deviation = Math.abs(100.0 - (double)leftIndent / prevLeftIndent * 100.0);
            if (deviation > MAX_LEFT_INDENT_DEVIATION_PERCENT)
                return null;

            if ((prevNode.getNodeType() == NodeType.PARAGRAPH) && ((Paragraph)prevNode).isListItem())
                return (Paragraph)prevNode;

            prevNode = prevNode.getPreviousSibling();
        }

        return null;
    }

    /// <summary>
    /// Returns <see cref="MarkdownParagraphWriter"/> corresponding to a specified paragraph node.
    /// </summary>
    MarkdownParagraphWriter getParagraphWriterByNode(Paragraph paragraph)
    {
        if (paragraph == null)
            return null;

        if (mParagraph == paragraph)
            return this;

        MarkdownParagraphWriter prevWriter = mPrevParagraphWriter;
        while (prevWriter != null)
        {
            if (prevWriter.mParagraph == paragraph)
                return prevWriter;

            prevWriter = prevWriter.mPrevParagraphWriter;
        }

        return null;
    }

    /// <summary>
    /// Gets a boolean value, indicating the paragraph is empty or contains only cross annotation.
    /// </summary>
    boolean isEmpty() { return mParagraph.isEmptyOrContainsOnlyCrossAnnotation(); }

    /// <summary>
    /// Initializes the writer.
    /// </summary>
    private void init() throws Exception
    {
        // Iterate over all styles to restore markdown feature blocks.
        Style style = mParagraph.getParagraphStyle();
        while (style != null)
        {
            /*BlockType*/byte blockType = MarkdownUtil.toBlockType(style);
            switch (blockType)
            {
                case BlockType.BULLET_LIST_ITEM:
                case BlockType.ORDERED_LIST_ITEM:
                {
                    // WORDSNET-24544 A new save option is introduced, consider it.
                    if (mWriter.getSaveOptions().getListExportMode() == MarkdownListExportMode.PLAIN_TEXT)
                        break;

                    if (getListStylesLabels().size() == 0)
                        mFirstListStyleLabelPosition = mStylesMarkers.length();

                    String listLabel = getListLabel(style.getParaPr());
                    msStringBuilder.append(mStylesMarkers, listLabel);

                    if (listLabel.length() > 0)
                        msArrayList.add(getListStylesLabels(), listLabel);

                    if (mStartAt == 0)
                    {
                        List list = style.getListInternal();
                        if (list != null)
                        {
                            int levelNumber = (/*int*/Integer)((IParaAttrSource)style).fetchParaAttr(ParaAttr.LIST_LEVEL);
                            ListLevel level = list.getListLevels().get(levelNumber);
                            mStartAt = level.getStartAt();
                        }
                    }

                    break;
                }
                case BlockType.QUOTE:
                {
                    mStylesMarkers.append('>');
                    // Do not separate quote marks with space inside an opening quote sequence.
                    /*BlockType*/byte nextBlockType = MarkdownUtil.toBlockType(style.getBaseStyle());
                    if (nextBlockType != BlockType.QUOTE)
                        mStylesMarkers.append(' ');

                    mQuoteLevel++;
                    break;
                }
                case BlockType.ATX_HEADING:
                {
                    // WORDSNET-25705 Do not override first occurred Heading style with any possible parent Heading style.
                    if (mAtxHeading == null)
                        mAtxHeading = style;
                    break;
                }
                case BlockType.FENCED_CODE:
                {
                    mFencedCode = style;
                    break;
                }
                case BlockType.INDENTED_CODE:
                {
                    mIsIndentedCode = true;
                    break;
                }
                case BlockType.SETEXT_HEADING:
                {
                    mIsSetextHeading = true;
                    break;
                }
                default:
                    break;
            }

            style = style.getBaseStyle();
        }

        // WORDSNET-19780 Append BlockQuote defined in HtmlBlock of the paragraph, if any.
        if (mParagraph.getHtmlBlockQuoteLevel() > 0)
        {
            mQuoteLevel = mParagraph.getHtmlBlockQuoteLevel() - 1;

            StringBuilder blockQuote = new StringBuilder(msString.newString('>', mParagraph.getHtmlBlockQuoteLevel()));
            blockQuote.append(' ');

            mStylesMarkers.insert(0, blockQuote.toString());
        }

        if (mWriter.getSaveOptions().getListExportMode() == MarkdownListExportMode.MARKDOWN_SYNTAX)
        {
            if (mStartAt == 0)
            {
                ListLevel listLevel = mParagraph.getListLevel(false);
                if (listLevel != null)
                    mStartAt = listLevel.getStartAt();
            }

            // Need this update to determine properly either we need paragraph break in IsNeedParagraphBreak.
            // We should rework it there some day to remove this call.
            updateListLabels();
        }

        mParentFootnote = mWriter.getFootnoteWriter().getFootnote();
    }

    /// <summary>
    /// Writes content lines into underlying writer.
    /// </summary>
    private void writeContentLines() throws Exception
    {
        for (int i = 0; i < mContentLines.getCount(); i++)
        {
            StringBuilder contentLine = mContentLines.get(i);

            writeStylesMarkers(contentLine);

            // Write spaces to make lazy continuation in multiline list item or footnote.
            if (isNeedLazyContinuation(contentLine, i))
            {
                // Lazy continuation in footnotes is blank line + indentation spaces.
                if (isInFootnote())
                    msStringBuilder.append(getBuilder(), getParagraphBreak());

                msStringBuilder.append(getBuilder(), ' ', Block.MAX_INDENTATION_LENGTH + 1);
            }

            // Escape first character of the content line if it starts from a sequence that can be recognized
            // as a some non-regular paragraph block.
            if ((mStylesMarkers.length() == 0) && (contentLine.length() > 0))
                escapeContentLine(contentLine);

            // Append content line text.
            msStringBuilder.append(getBuilder(), msStringBuilder.replace(contentLine, MarkdownUtil.HARD_LINE_BREAK_SLASH, getParagraphBreak()));

            // Write AtxHeading closing sequence.
            if (isHeading())
            {
                if (isInCell())
                    writeHtmlHeading(false);
                else if (!mIsSetextHeading)
                    writeAtxHeadingClosing();
            }

            // Write paragraph break for all lines except of the very last.
            if (i < (mContentLines.getCount() - 1))
            {
                if (mParagraph.isInCell())
                {
                    // Line breaks are not allowed inside tables, so use html tag <br>.
                    msStringBuilder.append(getBuilder(), HTML_LINE_BREAK);
                }
                else
                {
                    // WORDSNET-18341 Implemented hard line breaks.
                    // We don't write line break inside code blocks and footnotes.
                    // Also, we don't write it in a very first line, if it is empty.
                    if (!isInCodeBlock() && !isInFootnote() && ((contentLine.length() > 0) || (i > 0)))
                    {
                        msStringBuilder.append(getBuilder(), 
                            (mWriter.getSaveOptions().getLineBreakExportMode() == MarkdownLineBreakExportMode.BACKSLASH) ||
                            StringUtil.containsOnlyWhitespaces(contentLine)
                                ? "\\"
                                : "  ");
                    }

                    msStringBuilder.append(getBuilder(), getParagraphBreak());
                }
            }

            // The non-blank list label ('-', '+', '*' or ordinal number) must be written only for a very first line of
            // a multiline paragraph. All the rest of lines should be written with a blank list label.
            if (hasDirectListLabel() && (i == getFirstContentLineIndex()))
            {
                for (int j = 0; j < getDirectListLabel().length() - 1; j++)
                    mStylesMarkers.setCharAt(mDirectListLabelPosition + j, ' ');
            }
        }
    }

    /// <summary>
    /// Returns <see cref="MarkdownParagraphWriter"/> corresponding to <see cref="GetListItemSource"/>
    /// list item of the previous sibling node of this <see cref="mParagraph"/>.
    /// </summary>
    private MarkdownParagraphWriter getPrevListItemWriter()
    {
        Node prevNode = mParagraph.getPreviousSibling();
        if (prevNode == null)
            return null;
        Paragraph prevListItem = getListItemSource(prevNode);
        return getParagraphWriterByNode(prevListItem);
    }

    /// <summary>
    /// Updates styles markers.
    /// </summary>
    private void updateStylesMarkers() throws Exception
    {
        // WORDSNET-24544 A new save option is introduced, consider it.
        if (isMarkdownListItem() && (mWriter.getSaveOptions().getListExportMode() == MarkdownListExportMode.MARKDOWN_SYNTAX))
        {
            updateListLabels();

            // There can be paragraph with list level greater than number of applied list styles. In this case
            // we need to append empty list markers. Also note, there cannot be missed list styles if previous
            // paragraph is not a list item.
            MarkdownParagraphWriter prevListItemWriter = getPrevListItemWriter();
            if (prevListItemWriter != null)
                insertMissedListLevels(prevListItemWriter.getFirstListStyleLabelPosition());

            // Append direct list label before very first Leaf block.
            appendDirectListLabel();
        }

        appendLeafBlocks();
    }

    /// <summary>
    /// Updates <see cref="ListLabels"/> for the underlying paragraph.
    /// </summary>
    private void updateListLabels() throws Exception
    {
        int missedListLevelsCount = getMissedListLevelsCount();

        MarkdownParagraphWriter prevListItemWriter = getPrevListItemWriter();
        if (prevListItemWriter != null)
        {
            // Update missed list levels with blank label.
            for (int i = 0; i < missedListLevelsCount; i++)
            {
                getListLabels()[i] = (prevListItemWriter.getListLabels()[i] == null)
                    ? ""
                    : msString.newString(' ', prevListItemWriter.getListLabels()[i].length());
            }
        }

        // Update labels from the List styles of the paragraph.
        for (int i = missedListLevelsCount; i < getListStylesLabels().size(); i++)
            getListLabels()[i] = getListStylesLabels().get(i);

        // At last, update direct list label.
        if (hasDirectListLabel())
            getListLabels()[getListLevel()] = getDirectListLabel();
    }

    /// <summary>
    /// Writes styles markers for a content line with a specified index to a specified writer.
    /// </summary>
    private void writeStylesMarkers(StringBuilder contentLine) throws Exception
    {
        if (StringUtil.containsOnlyWhitespaces(contentLine))
        {
            // Do not write list marker for empty line.
            String stylesMarkers;
            if (getListStylesLabels().size() > 0)
            {
                stylesMarkers = mStylesMarkers.substring((0), (0) + (getFirstListStyleLabelPosition()));
            } else if (hasDirectListLabel())
            {
                stylesMarkers = mStylesMarkers.substring((0), (0) + (mStylesMarkers.length() - getDirectListLabel().length()));
            }
            else
            {
                stylesMarkers = mStylesMarkers.toString();
            }

            // Do not write an extra spaces at the very end of styles markers for empty content line.
            msStringBuilder.append(getBuilder(), msString.trimEnd(stylesMarkers));
        }
        else
        {
            msStringBuilder.append(getBuilder(), mStylesMarkers);
        }
    }

    /// <summary>
    /// Writes fenced code closing sequence.
    /// </summary>
    private void writeFencedCodeOpening()
    {
        // Fenced code is not allowed inside cell.
        if (isInCell())
            return;

        msStringBuilder.append(getBuilder(), mStylesMarkers);
        msStringBuilder.append(getBuilder(), FENCED_CODE_DELIMITER);
        if (getFencedCodeInfoString().length() > 0)
            getBuilder().append(' ');
        msStringBuilder.append(getBuilder(), getFencedCodeInfoString());
        msStringBuilder.append(getBuilder(), getParagraphBreak());
    }

    /// <summary>
    /// Writes fenced code closing sequence.
    /// </summary>
    private void writeFencedCodeClosing()
    {
        // Fenced code is not allowed inside cell.
        if (isInCell())
            return;

        msStringBuilder.append(getBuilder(), getParagraphBreak());
        msStringBuilder.append(getBuilder(), mStylesMarkers);
        msStringBuilder.append(getBuilder(), FENCED_CODE_DELIMITER);
    }

    /// <summary>
    /// Writes SetextHeading opening sequence.
    /// </summary>
    private void writeSetextHeading() throws Exception
    {
        // SetextHeading is not allowed inside cell.
        if (isInCell())
            return;

        msStringBuilder.append(getBuilder(), getParagraphBreak());

        // There should not be a non-blank list item markers in SetextHeading opening sequence.
        if (hasDirectListLabel())
        {
            for (int j = 0; j < getDirectListLabel().length() - 1; j++)
                mStylesMarkers.setCharAt(mDirectListLabelPosition + j, ' ');
        }

        msStringBuilder.append(getBuilder(), mStylesMarkers);
        msStringBuilder.append(getBuilder(), MarkdownUtil.levelToSetextHeadingChar(getSetextHeadingLevel()), mContentLines.getMaxLength());
    }

    /// <summary>
    /// Writes HTML heading tag.
    /// </summary>
    /// <param name="isOpen">When true, writes open tag. Otherwise, writes close tag.</param>
    private void writeHtmlHeading(boolean isOpen)
    {
        Debug.assertTrue(isHeading());
        msStringBuilder.append(getBuilder(), msString.format((isOpen) ? HTML_HEADING_OPEN : HTML_HEADING_CLOSE, getAtxHeadingLevel()));
    }

    /// <summary>
    /// Appends AtxHeading opening sequence to styles markers.
    /// </summary>
    private void appendAtxHeading()
    {
        msStringBuilder.append(mStylesMarkers, '#', getAtxHeadingLevel());
        mStylesMarkers.append(' ');
    }

    /// <summary>
    /// Appends IndentedCode opening sequence to styles markers.
    /// </summary>
    private void appendIndentedCode()
    {
        msStringBuilder.append(mStylesMarkers, ' ', Block.MAX_INDENTATION_LENGTH + 1);
    }

    /// <summary>
    /// Appends Leaf blocks opening sequences to styles markers.
    /// </summary>
    private void appendLeafBlocks()
    {
        if (isIndentedCode())
        {
            appendIndentedCode();
        }
        else if (isHeading())
        {
            if (isInCell())
            {
                // WORDSNET-20407 Headings are not allowed inside cells in markdown.
                // Write them as corresponding <h1>-<h6> HTML tags.
                writeHtmlHeading(true);
            }
            else
            {
                // An ATXHeading cannot be multiline, so it should be converted to a SetextHeading in this case.
                if (isMultiline())
                    mIsSetextHeading = true;

                if (!mIsSetextHeading)
                    appendAtxHeading();
            }
        }
    }

    /// <summary>
    /// Returns string representing list label for a specified ParaPr.
    /// </summary>
    private String getListLabel(ParaPr paraPr) throws Exception
    {
        return getListLabel(paraPr.getListId(), paraPr.getListLevel());
    }

    /// <summary>
    /// Returns string representing list label for a specified listId and level number.
    /// </summary>
    private String getListLabel(int listId, int levelNumber) throws Exception
    {
        if ((listId == 0) || (mWriter.getSaveOptions().getListExportMode() == MarkdownListExportMode.PLAIN_TEXT))
            return "";

        // WORDSNET-22782 Consider style reference.
        List list = getDocument().getLists().fetchListByListIdResolveStyleReference(listId);

        boolean isBulletList = (list.getListDef().getLevels().get(0).getNumberStyle() == NumberStyle.BULLET);

        // WORDSNET-20409 Lists are not allowed inside tables,
        // so take actual list label of the numbered paragraph.
        if (isInCell() && !isBulletList)
        {
            mWriter.updateListLabels();
            return msString.format("{0} ", mParagraph.getListLabel().getLabelString());
        }

        // WORDSNET-27265 Added resilience by fetching list level instead of getting it directly by index.
        ListLevel listLevel = (isBulletList)
            ? list.getListDef().getLevels().fetchListLevel(0)
            : list.getListDef().getLevels().fetchListLevel(levelNumber);

        if (listLevel.getNumberFormat().length() <= 0)
            return "";

        char listMarkerChar = listLevel.getNumberFormat().charAt(listLevel.getNumberFormat().length() - 1);

        /*ListMarker*/int marker = (isBulletList)
            ? MarkdownUtil.toBulletListMarker(listMarkerChar)
            : MarkdownUtil.toOrderedListMarker(listMarkerChar);

        String startAt = (isBulletList) ? "" : FormatterPal.intToStr(listLevel.getStartAt());

        return msString.format("{0}{1} ", startAt, MarkdownUtil.listMarkerToChar(marker));
    }

    /// <summary>
    /// Gets a boolean value indicating either a specified content line
    /// should be written as lazy continuation of the previous line.
    /// </summary>
    private boolean isNeedLazyContinuation(StringBuilder contentLine, int lineIndex)
    {
        // We need to indent all lines except of the very first line in footnote.
        if (isInFootnote() && ((lineIndex != 0) || !isFirstParaInFootnote()))
            return true;

        if (lineIndex <= getFirstContentLineIndex())
            return false;

        if (!isListItem())
            return false;

        if (MarkdownUtil.isOrderedListItem(contentLine))
        {
            // The ordered list must start from '1' to break a paragraph.
            if ((contentLine.charAt(0) != '1') || !MarkdownUtil.isOrderedListMarker(contentLine.charAt(1)))
                return false;
        }
        else if (!MarkdownUtil.isBulletListItem(contentLine))
        {
            return false;
        }

        return true;
    }

    /// <summary>
    /// Appends direct list label to styles markers.
    /// </summary>
    private void appendDirectListLabel() throws Exception
    {
        // Append direct list label.
        mDirectListLabelPosition = mStylesMarkers.length();
        msStringBuilder.append(mStylesMarkers, getDirectListLabel());

        // Update first list style label position.
        if (mFirstListStyleLabelPosition == -1)
            mFirstListStyleLabelPosition = mDirectListLabelPosition;
    }

    /// <summary>
    /// Inserts list labels of the missed list levels into styles markers at a specified position.
    /// </summary>
    private void insertMissedListLevels(int position) throws Exception
    {
        Debug.assertTrue(isMarkdownListItem());

        int missedListLevelsCount = getMissedListLevelsCount();
        if (missedListLevelsCount < 1)
            return;

        Debug.assertTrue(position >= 0);
        mFirstListStyleLabelPosition = position;

        for (int i = 0; i < missedListLevelsCount; i++)
        {
            // Missed list levels should be filled with blank list marker.
            Debug.assertTrue((getListLabels()[i] != null) && StringUtil.containsOnlyWhitespaces(getListLabels()[i]));

            // Whitespace characters in cell will be stripped, so use HTML tag.
            String missedLevelFiller = (isInCell()) ? HTML_INDENT : getListLabels()[i];
            mStylesMarkers.insert(mFirstListStyleLabelPosition, missedLevelFiller);
            // Update direct list marker position.
            mDirectListLabelPosition += missedLevelFiller.length();
        }
    }

    /// <summary>
    /// Returns number of missed list levels for the paragraph.
    /// </summary>
    private int getMissedListLevelsCount() throws Exception
    {
        int labelsCount = getListStylesLabels().size();
        if (hasDirectListLabel())
            labelsCount++;

        int missedListLevelsCount = (getListLevel() + 1) - labelsCount;

        // There can be numbering specified only in list style, when direct numbering is missed. Or even list style
        // without numbering (it has 'List' in abbreviation, but the numbering is not specified).
        // In all such cases we treat it as there are no missed styles.
        return Math.max(0, missedListLevelsCount);
    }

    /// <summary>
    /// Writes AtxHeading closing sequence to the underlying writer.
    /// </summary>
    /// <remarks>
    /// If line of text ends with the same substring as AtxHeading opening sequence,
    /// than we also need to write a closing sequence. Otherwise, this line ending
    /// will be lost, because it will be treated as the closing sequence in markdown.
    /// </remarks>
    private void writeAtxHeadingClosing()
    {
        Debug.assertTrue(isHeading() && !mIsSetextHeading);

        String closingSequence = msString.format(" {0}", msString.newString('#', getAtxHeadingLevel()));
        if (StringUtil.isEndsWith(mContentLines.get(getFirstContentLineIndex()), closingSequence))
            msStringBuilder.append(getBuilder(), closingSequence);
    }

    /// <summary>
    /// Writes escaping character before content line to the underlying writer.
    /// </summary>
    private void escapeContentLine(StringBuilder contentLine)
    {
        Debug.assertTrue((contentLine != null) && (contentLine.length() > 0));

        // WORDSNET-20408 Also ignore tables as there cannot be any other markdown features.
        if (isFencedCode() || isHorizontalRule() || isInCell())
            return;

        // Avoid possible indentation code.
        StringUtil.trimStart(contentLine);

        // WORDSNET-20394 Skip completely empty lines as there is nothing to escape.
        if (contentLine.length() == 0)
            return;

        if (getBlockType(contentLine.toString()) != BlockType.PARAGRAPH)
            getBuilder().append('\\');
    }

    /// <summary>
    /// Wraps current content line into InlineCode delimiters.
    /// </summary>
    private void wrapIntoInlineCode()
    {
        // Do not wrap if this is inside another code block already.
        if (isFencedCode() || isIndentedCode())
            return;

        getCurrentLine().insert(0, InlineCodeDelimiter.CHARACTER);
        getCurrentLine().append(InlineCodeDelimiter.CHARACTER);
    }

    /// <summary>
    /// Returns the BlockType of the specified text fragment.
    /// </summary>
    private /*BlockType*/byte getBlockType(String text)
    {
        return getBlockParser().parse(text, 0).getType();
    }

    /// <summary>
    /// Writes empty paragraph.
    /// </summary>
    private void writeEmptyParagraph()
    {
        if (mWriter.getSaveOptions().getEmptyParagraphExportMode() == MarkdownEmptyParagraphExportMode.NONE)
        {
            if (isLastEmptyParagraph() && isLazyContinuation())
                msStringBuilder.append(getBuilder(), getParagraphBreak());

            return;
        }

        if (mWriter.getSaveOptions().getEmptyParagraphExportMode() == MarkdownEmptyParagraphExportMode.MARKDOWN_HARD_LINE_BREAK)
        {
            if (isInCell())
            {
                // &nbsp
                getBuilder().append('\u00A0');
            }
            else
            {
                // If after an empty paragraph goes node that can be a lazy continuation for this last
                // empty paragraph, then when we write Markdown hard line break '\' character,
                // it is displayed 'as is' instead of making line break. So we need to write
                // HTML <br> tag instead.
                String breakStr = (isLastEmptyParagraph() && !isLazyContinuation()) ? HTML_LINE_BREAK : "\\";
                msStringBuilder.append(getBuilder(), breakStr);

                if (isFirstEmptyParagraph() && (!HTML_LINE_BREAK.equals(breakStr)))
                {
                    msStringBuilder.append(getBuilder(), getParagraphBreak());
                    msStringBuilder.append(getBuilder(), breakStr);
                }
            }
        }
    }

    /// <summary>
    /// Gets a boolean value, indicating either this paragraph is empty
    /// and there are no other empty paragraphs before it.
    /// </summary>
    private boolean isFirstEmptyParagraph()
    {
        if (!mParagraph.isEmptyOrContainsOnlyCrossAnnotation())
            return false;

        Paragraph prevParagraph = ms.as(mParagraph.getPreviousSibling(), Paragraph.class);
        if (prevParagraph == null)
            return true;

        return !prevParagraph.isEmptyOrContainsOnlyCrossAnnotation();
    }

    /// <summary>
    /// Gets a boolean value, indicating either this paragraph is empty
    /// and there are no other empty paragraphs after it.
    /// </summary>
    private boolean isLastEmptyParagraph()
    {
        if (!mParagraph.isEmptyOrContainsOnlyCrossAnnotation())
            return false;

        Paragraph nextParagraph = ms.as(mParagraph.getNextSibling(), Paragraph.class);
        if (nextParagraph == null)
            return true;

        return !nextParagraph.isEmptyOrContainsOnlyCrossAnnotation();
    }

    /// <summary>
    /// Gets a boolean value, indicating either this paragraph
    /// produces a lazy continuation with next sibling node.
    /// </summary>
    private boolean isLazyContinuation()
    {
        Paragraph nextParagraph = ms.as(mParagraph.getNextSibling(), Paragraph.class);
        if (nextParagraph == null)
            return false;

        if (nextParagraph.isListItem())
            return false;

        /*BlockType*/byte blockType = MarkdownUtil.toBlockType(nextParagraph.getParagraphStyle());
        return (blockType == BlockType.PARAGRAPH) || (blockType == BlockType.INLINE_CODE);
    }


    /// <summary>
    /// Returns True if Html syntax shall be used to correctly write the attributes of the current paragraph.
    /// </summary>
    boolean getHtmlSyntaxRequired()
    {
        for (Run run : (Iterable<Run>) mParagraph.getRuns())
        {
            // MD syntax inside html-inline is not allowed. Sup/sub emphasis works only in html-inline syntax.
            // Thus, if there are other emphases, they must also be in html-inline syntax.
            if ((run.getRunPr().getVerticalAlignment() != RunVerticalAlignment.BASELINE) &&
                (run.getRunPr().getBold().toBool() || run.getRunPr().getItalic().toBool() || run.getRunPr().getStrikeThrough().toBool()))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets list label strings for every list level of the list to which this paragraph belongs.
    /// </summary>
    String[] getListLabels()
    {
        if (mListLabels == null)
            mListLabels = new String[9];

        return mListLabels;
    }

    /// <summary>
    /// Gets a document being written.
    /// </summary>
    private DocumentBase getDocument() { return mWriter.getDocument(); }

    /// <summary>
    /// Gets current builder.
    /// </summary>
    private StringBuilder getBuilder() { return mWriter.getBuilder(); }

    /// <summary>
    /// Gets string representing current paragraph break value to use in the writer.
    /// </summary>
    private String getParagraphBreak() { return mWriter.getSaveOptions().getParagraphBreak(); }

    /// <summary>
    /// Gets current line of content lines.
    /// </summary>
    private StringBuilder getCurrentLine() { return mContentLines.getCurrentLine(); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is a HorizontalRule.
    /// </summary>
    private boolean isHorizontalRule()
    {
        if (mParagraph == null)
            return false;

        if ((mParagraph.getFirstChild() == null) || (mParagraph.getFirstChild().getNodeType() != NodeType.SHAPE))
            return false;

        HorizontalRule horizontalRule = ((Shape)(mParagraph.getFirstChild())).getHorizontalRule();
        if (horizontalRule == null)
            return false;

        return horizontalRule.getOn();
    }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is a list item.
    /// </summary>
    private boolean isListItem() { return mParagraph.isListItem(); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is a list item in terms of Markdown.
    /// </summary>
    /// <remarks>
    /// We introduced this behavior in WORDSNET-26133. If non-numbered paragraph in model has the
    /// same left indent as the previous numbered paragraph, then we treat such paragraph as
    /// numbered in Markdown. This behavior is compliant with many third-party Markdown editors.
    /// </remarks>
    private boolean isMarkdownListItem() { return isListItem() || isSameLeftIndentAsPreviousListItem(); }

    /// <summary>
    /// Gets a boolean value, indicating the paragraph has ordered number style.
    /// </summary>
    private boolean hasOrderedNumberStyle()
    {
        Debug.assertTrue(isMarkdownListItem());

        int listId = mParagraph.getParaPr().getListId();
        // WORDSNET-23981 Get list considering style reference.
        List list = getDocument().getLists().fetchListByListIdResolveStyleReference(listId);

        return (list.getListDef().getLevels().get(0).getNumberStyle() == NumberStyle.ARABIC);
    }

    /// <summary>
    /// Gets a boolean value indicating the paragraph has direct list label.
    /// </summary>
    private boolean hasDirectListLabel() throws Exception { return (getDirectListLabel().length() > 0) && !StringUtil.containsOnlyWhitespaces(getDirectListLabel()); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is an AtxHeading.
    /// </summary>
    private boolean isHeading() { return (getAtxHeadingLevel() > 0); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is a FencedCode.
    /// </summary>
    private boolean isFencedCode() { return mFencedCode != null; }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is an IndentedCode.
    /// </summary>
    private boolean isIndentedCode() { return mIsIndentedCode; }

    /// <summary>
    /// Gets a boolean value indicating the paragraph is one of the Code blocks.
    /// </summary>
    private boolean isInCodeBlock() { return (isFencedCode() || isIndentedCode()); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is inside a Cell.
    /// </summary>
    private boolean isInCell() { return mParagraph.isInCell(); }

    /// <summary>
    /// Gets a boolean value indicating either the paragraph is inside Footnote.
    /// </summary>
    private boolean isInFootnote() { return mParentFootnote != null; }

    /// <summary>
    /// Gets a boolean value indicating that the paragraph is the very first inside a Footnote.
    /// </summary>
    private boolean isFirstParaInFootnote()
    {
        if (isInFootnote())
        {
            return (mPrevParagraphWriter == null) || (mParentFootnote != mPrevParagraphWriter.mParentFootnote);
        }

        return false;
    }

    /// <summary>
    /// Gets an integer value representing a topmost Quote level.
    /// </summary>
    private int getQuoteLevel() { return mQuoteLevel; }

    /// <summary>
    /// The position of the first occurred list label inside output string.
    /// </summary>
    private int getFirstListStyleLabelPosition() { return mFirstListStyleLabelPosition; }

    /// <summary>
    /// Gets an integer value representing a first level of the list this paragraph belongs which label is not blank.
    /// </summary>
    private int getFirstNonBlankLabelLevel()
    {
        for (int i = 0; i < getListLabels().length; i++)
        {
            if (!StringUtil.containsOnlyWhitespaces(getListLabels()[i]))
                return i;
        }

        return -1;
    }

    /// <summary>
    /// Gets FencedCode info string.
    /// </summary>
    private String getFencedCodeInfoString()
    {
        if (!isFencedCode())
            return "";

        if (mFencedCodeInfoString == null)
        {
            String styleName = mFencedCode.getName();

            int start = MarkdownUtil.FENCED_CODE_STYLE_NAME.length();
            // One dot (.) is allowed just after style name to increase readability.
            if ((start < styleName.length()) && (styleName.charAt(start) == '.'))
                start++;

            mFencedCodeInfoString = styleName.substring((start), (start) + ((styleName.length() - start)));
        }

        return mFencedCodeInfoString;
    }

    /// <summary>
    /// Gets direct list marker of the current paragraph.
    /// </summary>
    private String getDirectListLabel() throws Exception
    {
        if (mDirectListLabel == null)
        {
            // WORDSNET-21727 There can be situation when there are numbered styles, but no one of them is started
            // from 'List' and no direct listId is specified at the paragraph. In this case the paragraph is
            // still numbered and we need to set direct list label.
            if (mParagraph.getParaPr().contains(ParaAttr.LIST_ID))
            {
                // 1) If list is defined in direct attributes, then just use it.
                mDirectListLabel = getListLabel(mParagraph.getParaPr());
            }
            else
            {
                // 2) Otherwise, lets check that there is at least one numbered style with name started with 'List'.
                // In this case the paragraph numbering will be accounted for such style and the label is added
                // into mListStylesLabels (i.e. no need to set any direct list label, it is empty string).
                int listId = 0;
                Style style = mParagraph.getParagraphStyle();
                while (style != null)
                {
                    if (style.getParaPr().contains(ParaAttr.LIST_ID))
                    {
                        int curListId = style.getParaPr().getListId();
                        // Remember first occurred listId to avoid getting it below one more time.
                        if (listId == 0)
                            listId = curListId;

                        // WORDSNET-22782 Check also whether style is set explicitly to not a list item.
                        if ((curListId == 0) || msString.startsWith(style.getName(), MarkdownUtil.LIST_STYLE_NAME, StringComparison.ORDINAL))
                        {
                            mDirectListLabel = "";
                            break;
                        }
                    }

                    style = style.getBaseStyle();
                }

                // 3) But if there is no numbered style with name started with 'List', there still can
                // be numbered style. Such style is not accounted in mListStylesLabels, but the paragraph
                // is obviously numbered. In this case we should account it in a direct list label.
                if (mDirectListLabel == null)
                    mDirectListLabel = getListLabel(listId, getListLevel());
            }

            if ((mDirectListLabel.length() > 0) && StringUtil.containsOnlyWhitespaces(mDirectListLabel))
                mDirectListLabel = "";
        }

        return mDirectListLabel;
    }

    /// <summary>
    /// Gets an integer value representing an AtxHeading level.
    /// </summary>
    private int getAtxHeadingLevel() { return (mAtxHeading != null) ? (int)mAtxHeading.getStyleIdentifier() : 0; }

    /// <summary>
    /// Gets an integer value representing a SetextHeading level.
    /// </summary>
    private int getSetextHeadingLevel() { return (mIsSetextHeading) ? Math.min(Math.max(getAtxHeadingLevel(), 1), 2) : 0; }

    /// <summary>
    /// Gets block parser object.
    /// </summary>
    private BlockParser getBlockParser()
    {
        if (mBlockParser == null)
            mBlockParser = new BlockParser();

        return mBlockParser;
    }

    /// <summary>
    /// Gets list of strings representing labels for every List style of the paragraph.
    /// </summary>
    private ArrayList<String> getListStylesLabels()
    {
        if (mListStylesLabels == null)
            mListStylesLabels = new ArrayList<String>();

        return mListStylesLabels;
    }

    /// <summary>
    /// Gets list level of the paragraph.
    /// </summary>
    private int getListLevel()
    {
        if (mListLevel == -1)
        {
            // WORDSNET-26133 Consider also 'Markdown' list item.
            Paragraph listItem = getListItemSource(mParagraph);
            if (listItem != null)
                mListLevel = (/*int*/Integer)listItem.fetchParaAttr(ParaAttr.LIST_LEVEL, RevisionsView.ORIGINAL);
        }

        return mListLevel;
    }

    /// <summary>
    /// Gets an index of the first content line of the paragraph.
    /// </summary>
    /// <remarks>
    /// We separate some paragraphs with blank line (<see cref="IsNeedParagraphBreak"/>) by inserting one empty line
    /// before the array of content lines. But this line should not be treated as the content line, but is just a separator.
    /// </remarks>
    private int getFirstContentLineIndex()
    {
        if (mContentLines.getCount() <= 1)
            return mContentLines.getCount() - 1;

        // Do not consider very first blank line, because it is not a content line, but paragraph separator.
        return (StringUtil.containsOnlyWhitespaces(mContentLines.get(0))) ? 1 : 0;
    }

    /// <summary>
    /// Gets a boolean value indicating that current paragraph is multiline.
    /// </summary>
    private boolean isMultiline()
    {
        // Do not consider very first blank line, because it is not a content line, but paragraph separator.
        return ((mContentLines.getCount() > 2) ||
                ((mContentLines.getCount() == 2) && !StringUtil.containsOnlyWhitespaces(mContentLines.get(0))));
    }

    /// <summary>
    /// Gets a boolean value indicating whether we need to append a paragraph break.
    /// </summary>
    private boolean isNeedParagraphBreak() throws Exception
    {
        // Don't add blank line if it is a very first paragraph.
        if (mPrevParagraphWriter == null)
            return false;

        // GitLab (as well as some other editors), which we try to mimic,
        // does not require the break between paragraphs in footnote definitions.
        // But note, it is required for multiline definitions.
        if (isInFootnote())
            return false;

        // There cannot be multiple paragraphs within cell in markdown format
        // (multiline can be achieved through the inline html blocks <p>, <br>).
        if (isInCell())
            return false;

        // If this is already a blank line, i.e. separator itself,
        // then there is no need to separate it once again.
        if (isEmpty())
            return false;

        // If previous paragraph is already a blank line, i.e. separator itself,
        // then there is no need to separate it once again.
        if (mPrevParagraphWriter.isEmpty())
            return false;

        // There is no need to add a blank line before or after Heading because it is not appendable by spec.
        if (isHeading() || mPrevParagraphWriter.isHeading())
            return false;

        // No need to separate Quotes when previous quote has less level.
        if (getQuoteLevel() > mPrevParagraphWriter.getQuoteLevel())
            return false;

        // There is no need to add a blank line after HorizontalRule because it is not appendable by spec.
        if (mPrevParagraphWriter.isHorizontalRule())
            return false;

        // There is no need to add a blank line before FencedCode.
        if (isFencedCode())
            return false;

        // There is no need to add a blank line after IndentedCode because it is not appendable by spec.
        if (mPrevParagraphWriter.isIndentedCode())
            return false;

        if (mPrevParagraphWriter.isListItem())
        {
            // A horizontal rule cannot be appended to a list item.
            if (isHorizontalRule())
                return false;

            if (isListItem())
            {
                // When appending an ordered list that does not start at '1'
                // we need a blank line. Otherwise, it can break the paragraph itself.
                if ((hasOrderedNumberStyle() && mPrevParagraphWriter.hasOrderedNumberStyle()) &&
                    (getFirstNonBlankLabelLevel() > mPrevParagraphWriter.getFirstNonBlankLabelLevel()) &&
                    (mStartAt != 1))
                    return true;

                // When current list can be determined unambiguously (it has non-empty label),
                // then we do not need an additional blank line.
                if (hasDirectListLabel() || (getMissedListLevelsCount() == 0))
                    return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Gets a boolean value indicating either this paragraph has the
    /// same <see cref="ParaAttr.LeftIndent"/> as the previous numbered paragraph.
    /// </summary>
    private boolean isSameLeftIndentAsPreviousListItem() { return (mParagraph.getPreviousSibling() != null) && (getListItemSource(mParagraph.getPreviousSibling()) != null); }

    /// <summary>
    /// The paragraph being written.
    /// </summary>
    private /*final*/ Paragraph mParagraph;

    /// <summary>
    /// The content lines being collected from the underlying paragraph.
    /// </summary>
    private /*final*/ TxtContentLines mContentLines = new TxtContentLines();

    /// <summary>
    /// The writer to write content into.
    /// </summary>
    private /*final*/ MarkdownWriter mWriter;

    /// <summary>
    /// The previously written paragraph.
    /// </summary>
    private /*final*/ MarkdownParagraphWriter mPrevParagraphWriter;

    /// <summary>
    /// The position of the first occurred list style label inside output string.
    /// </summary>
    private int mFirstListStyleLabelPosition = -1;

    /// <summary>
    /// A direct list label.
    /// </summary>
    private String mDirectListLabel;

    /// <summary>
    /// The Heading style of the paragraph.
    /// </summary>
    private Style mAtxHeading;

    /// <summary>
    /// The FencedCode style of the paragraph.
    /// </summary>
    private Style mFencedCode;

    /// <summary>
    /// The info string of the FencedCode.
    /// </summary>
    private String mFencedCodeInfoString;

    /// <summary>
    /// The nesting level of the topmost Quote.
    /// </summary>
    private int mQuoteLevel = -1;

    /// <summary>
    /// A boolean value indicating either the paragraph is an IndentedCode.
    /// </summary>
    private boolean mIsIndentedCode;

    /// <summary>
    /// A boolean value indicating either the paragraph is a SetextHeading.
    /// </summary>
    private boolean mIsSetextHeading;

    // The position of direct list label inside builder of styles markers.
    private int mDirectListLabelPosition;

    /// <summary>
    /// The parent footnote of the paragraph.
    /// </summary>
    private Footnote mParentFootnote;

    /// <summary>
    /// A string builder with styles markers of the paragraph.
    /// </summary>
    private /*final*/ StringBuilder mStylesMarkers = new StringBuilder();

    /// <summary>
    /// Gets StartAt of the paragraph.
    /// </summary>
    /// <remarks>
    /// Note, this is a very first occurred StartAt of the paragraph among all its List styles and direct attributes.
    /// </remarks>
    private int mStartAt;
    private ArrayList<String> mListStylesLabels;
    private String[] mListLabels;
    private int mListLevel = -1;

    private BlockParser mBlockParser;
    private /*final*/ MarkdownEmphasesWriter mEmphasesWriter;

    private static final String HTML_LINE_BREAK = "<br>";
    private static final String HTML_HEADING_OPEN = "<h{0}>";
    private static final String HTML_HEADING_CLOSE = "</h{0}>";
    private static final String HTML_INDENT = "&emsp;";
    private static final String FENCED_CODE_DELIMITER = "~~~";

    /// <summary>
    /// The percent of maximum allowed deviation of the left indents of nodes when
    /// we take decision about either some node falls into a previous list.
    /// The allowed values are in range 0.0 - 100.0.
    /// </summary>
    private static final double MAX_LEFT_INDENT_DEVIATION_PERCENT = 10.0;
}

