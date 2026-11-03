// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2003 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.RegularExpressions.GroupCollection;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FontUtil;
import com.aspose.Debug;
import com.aspose.UnicodeCharacterDataResolver;
import com.aspose.StackExtension;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Responsible for saving document or a fragment of a document in plain text format.
/// </summary>
class TxtWriter extends TxtWriterBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TxtWriter()
    {
        mCachedListLabelsStack = new msStack<ListLabel>();
        // WORDSNET-455 and 4821 When user calls Run.ToTxt, then this stack is empty and it causes an exception.
        // The list was populated only when processing paragraph start and of course customer can just call Run.ToTxt.
        // So we push just one dummy item to the stack and it works.
        mCachedListLabelsStack.push(null);
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph paragraph)
    {
        mBlockQuoteLevel = paragraph.getHtmlBlockQuoteLevel();

        // Don't write ListLabel immediately because paragraph may start with
        // PageBreakChar or ColumnBreakChar which must be written before.

        // ListLabel is written to the stack to handle the case with nested paragraphs.
        // Topmost element of mCachedListLabelsStack is set to 'null' if there is no need to write label for
        // the paragraph, null value is also used as a flag that list label has been already written to file.
        mCachedListLabelsStack.push(paragraph.hasListLabel() ? paragraph.getListLabel() : null);

        // WORDSNET-19050 Start accumulating text for the paragraph inside a table.
        if (getSaveOptions().getPreserveTableLayout() && isInTable())
            getTableBuilder().startParagraph(paragraph);

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        if (!isLocked())
        {
            ListLabel listLabel = mCachedListLabelsStack.peek();

            String text = getText(run);

            if (listLabel != null)
            {
                // This while loop is ended with i set to first not-breaking (page break or column break) character
                // in run.Text. If all characters in the run are breaks, i is set to position after last character.
                int i = 0;
                while (i < text.length())
                {
                    char ch = text.charAt(i);
                    if ((ch != ControlChar.PAGE_BREAK_CHAR) && (ch != ControlChar.COLUMN_BREAK_CHAR))
                        break;

                    i++;
                }

                // If run.Text starts with one or several breaks, write this breaks (before list label).
                if (i > 0)
                    appendText(text.substring((0), (0) + (i)));

                // If there are any not-breaking character in the run, write list label and then these characters.
                if (i < text.length())
                {
                    appendText(buildListLabel(listLabel));
                    // Add space after label. It's possible to use
                    // paragraph.ListLabel.TrailingCharacter
                    // as separator but space looks the best for plain text.
                    appendText(" ");
                    text = text.substring(i);

                    // Paragraph can have several runs so we need to mark the paragraph list label written for
                    // that this list label must not be written for this paragraph anymore.
                    // 'null' means that we have written label for the paragraph and do not need to do it anymore.
                    mCachedListLabelsStack.pop();
                    mCachedListLabelsStack.push(null);
                }
            }

            // WORDSNET-8213 We should add ControlChar.RightToLeftMarkChar before each RtL run.
            // WORDSNET-16814 AddBidiMarks option is introduced to add RTL control marks only when this option is enabled.
            // WORDSNET-17853 The algorithm of adding BiDi marks is improved, so that BiDi marks now are wrapping current
            // run and their type is chosen depending on BiDi property of this and previous/next run and BiDi category
            // of first/last character of this run.
            if (getSaveOptions().getAddBidiMarks())
                appendTextBiDiAware(text, run);
            else
                appendText(text);
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        if (fieldStart.getFieldType() == FieldType.FIELD_MACRO_BUTTON)
            appendText(((FieldMacroButton)fieldStart.getField()).getDisplayText());

        lock();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        if (isLocked()) // WORDSNET-11447
            unlock();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        // If the field has separator, the output was already unlocked.
        if (!fieldEnd.hasSeparator() &&
            isLocked()) // WORDSNET-11447
            unlock();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphEnd(Paragraph paragraph)
    {
        if (!isLocked())
        {
            ListLabel listLabel = mCachedListLabelsStack.peek();

            if (listLabel != null)
                appendText(buildListLabel(listLabel));

            // WORDSNET-8796 MS Word writes the end of table cell as CR only (not CR+LF).
            appendText((paragraph.isEndOfCell() && !getSaveOptions().getPreserveTableLayout())
                ? ControlChar.CR
                : getSaveOptions().getParagraphBreak());
        }

        mCachedListLabelsStack.pop();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitTableStart(Table table)
    {
        mTableBuilderStack.push(new TxtTableBuilder(table, getTableBuilder(), getSaveOptions()));
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitTableEnd(Table table)
    {
        if ((getSaveOptions().getPreserveTableLayout()) && (mTableBuilderStack.getCount() == 1))
        {
            // Add table lines.
            for (int i = 0; i < getTableBuilder().getLines().getCount(); i++)
            {
                StringBuilder line = getTableBuilder().getLines().get(i);
                appendTextFinal(line.toString());
                appendTextFinal(getSaveOptions().getParagraphBreak());
            }
        }

        mTableBuilderStack.pop();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowStart(Row row)
    {
        if (getSaveOptions().getPreserveTableLayout())
            getTableBuilder().startRow();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRowEnd(Row row)
    {
        if (getSaveOptions().getPreserveTableLayout())
            getTableBuilder().endRow();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellStart(Cell cell)
    {
        if (getSaveOptions().getPreserveTableLayout())
        {
            // WORDSNET-6949 The problem occurs because "previous" cells (HorizontalMerge == CellMerge.Previous) were skipped
            // but nested tables inside these cells were processed.
            // Disable processing of the "previous" cells content.
            if (!getTableBuilder().startCell(cell))
                return VisitorAction.SKIP_THIS_NODE;
        }
        else if (cell.getCellPr().isMergedToPrevious())
        {
            // WORDSNET-7020 The problem occurs because merged cells aren't skipped when PreserveTableLayout option is false.
            // All the merged cells in a range are processed and saved to output. It causes data duplication.
            // Skip the merged cells to avoid data duplication in an output.
            return VisitorAction.SKIP_THIS_NODE;
        }
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellEnd(Cell cell)
    {
        if (getSaveOptions().getPreserveTableLayout())
            getTableBuilder().endCell();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCommentStart(Comment comment)
    {
        // WORDSNET-25287 Put comment into a separate line.
        if ((getBuilder().length() > 0) && (getBuilder().charAt(getBuilder().length() - 1) != '\r') && (getBuilder().charAt(getBuilder().length() - 1) != '\n'))
            appendText(getSaveOptions().getParagraphBreak());

        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Called when enumeration of a Office Math object has started.
    /// </summary>
    public /*override*/ /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath)
    {
        // FOSS
        return VisitorAction.SKIP_THIS_NODE;
    }

    protected /*override*/ void beforeNodeAccepted(Node node) throws Exception
    {
        super.beforeNodeAccepted(node);

        if (!node.isRemoved() && getSaveOptions().getPreserveTableLayout() &&
            ((node.getNodeType() == NodeType.CELL) || (node.getNodeType() == NodeType.ROW)))
        {
            Table table = node.getNodeType() == NodeType.CELL
                ? ((Cell)node).getParentTable()
                : ((Row)node).getParentTable();
            visitTableStart(table);
        }
    }

    protected /*override*/ void afterNodeAccepted(Node node)
    {
        super.afterNodeAccepted(node);

        if (!node.isRemoved() && getSaveOptions().getPreserveTableLayout() &&
            ((node.getNodeType() == NodeType.CELL) || (node.getNodeType() == NodeType.ROW)))
        {
            Table table = node.getNodeType() == NodeType.CELL
                ? ((Cell)node).getParentTable()
                : ((Row)node).getParentTable();
            visitTableEnd(table);
        }
    }

    /// <summary>
    /// Appends a specified text.
    /// </summary>
    private void appendText(String text)
    {
        // WORDSNET-13651 Field argument passed as text parameter is null.
        // It is raises "ArgumentNullException" while trying to find matches using regular expressions.
        if (msString.isNullOrEmpty(text))
            return;

        MatchCollection matches = G_REGEX_BREAKS.matches(text);

        for (Match match : (Iterable<Match>) matches)
        {
            GroupCollection matchGroups = match.getGroups();
            int index = getFirstNumberedGroup(matchGroups);
            index = index > 0 ? index : matchGroups.getCount() - 1;
            boolean isTextGroup = index == matchGroups.getCount() - 1;

            if (isTextGroup)
                appendTextValue(matchGroups.get(matchGroups.getCount() - 1).getValue());
            else
                appendBreak(matchGroups.get(index).getValue());
        }
    }

    /// <summary>
    /// Appends text to <see cref="StringBuilder"/>.
    /// </summary>
    /// <param name="value">Text to append.</param>
    private void appendTextValue(String value)
    {
        if (!StringUtil.hasChars(value))
            return;

        if (getSaveOptions().getPreserveTableLayout() && isInTable())
            getTableBuilder().appendText(value);
        else
            appendTextFinal(value);
    }

    /// <summary>
    /// <para>Writes text to <see cref="StringBuilder"/> calling <see cref="FontUtil.UnicodeToSymbol(char)"/>
    /// for each its character. See <see cref="FontUtil.UnicodeToSymbol(char)"/> for explanation.</para>
    /// </summary>
    private void appendTextFinal(String text)
    {
        if (msString.equals(text, getSaveOptions().getParagraphBreak()))
        {
            flushCurWord();
            msStringBuilder.append(getBuilder(), text);
            mCurPosition = 0;
        }
        else
        {
            // WORDSNET-19780 Append BlockQuote defined in HtmlBlock of the paragraph, if any.
            // IN. I think we should write it only once even when line is split due to limit of max page size.
            if (isAtStart() && (mBlockQuoteLevel > 0))
                msStringBuilder.append(getBuilder(), msString.format("{0} ", msString.newString('>', mBlockQuoteLevel)));

            for (int i = 0; i < text.length(); i++)
            {
            	char c = text.charAt(i);
                char curChar = FontUtil.unicodeToSymbol(c);
                if (getSaveOptions().getMaxCharactersPerLine() < 1)
                {
                    // The number of characters in line is not limited, so just append "as is".
                    getBuilder().append(curChar);
                }
                else
                {
                    // The number of characters in line is limited,
                    // so let's first split by words to try fit them in a single line.
                    if (Character.isLetterOrDigit(curChar))
                    {
                        mCurWord.append(curChar);
                    }
                    else
                    {
                        flushCurWord();
                        if (mCurPosition == getSaveOptions().getMaxCharactersPerLine())
                        {
                            msStringBuilder.append(getBuilder(), getSaveOptions().getParagraphBreak());
                            mCurPosition = 0;
                        }

                        getBuilder().append(curChar);
                        mCurPosition++;
                    }
                }
            }
        }
    }

    /// <summary>
    /// Flushes current word into a builder considering a maximum allowed length of line.
    /// </summary>
    private void flushCurWord()
    {
        if (mCurWord.length() == 0)
            return;

        // If number of characters in line is not limited, then just append a whole word.
        if (getSaveOptions().getMaxCharactersPerLine() < 1)
        {
            msStringBuilder.append(getBuilder(), mCurWord);
            return;
        }

        // Otherwise, check if the word can fit entirely in a current line.
        if ((mCurPosition + mCurWord.length()) <= getSaveOptions().getMaxCharactersPerLine())
        {
            // If so, then just append it.
            msStringBuilder.append(getBuilder(), mCurWord);
            mCurPosition += mCurWord.length();
        }
        else
        {
            // Otherwise, check if word can fit in the line at all.
            if (mCurWord.length() <= getSaveOptions().getMaxCharactersPerLine())
            {
                // As the word can not fit in the current line,
                // then we start a new one, where the word can definitely fit.
                msStringBuilder.append(getBuilder(), getSaveOptions().getParagraphBreak());
                msStringBuilder.append(getBuilder(), mCurWord);
                mCurPosition = mCurWord.length();
            }
            else
            {
                // If we here, then the word can not fit in the line at all (its length greater,
                // than allowed length of line). In this case, write word by characters as much,
                // as can fit in the line.
                int i = 0;
                while (i < mCurWord.length())
                {
                    if (mCurPosition == getSaveOptions().getMaxCharactersPerLine())
                    {
                        msStringBuilder.append(getBuilder(), getSaveOptions().getParagraphBreak());
                        mCurPosition = 0;
                    }

                    getBuilder().append(mCurWord.charAt(i));
                    i++;
                    mCurPosition++;
                }
            }
        }

        mCurWord.setLength(0);
    }

    /// <summary>
    /// Appends text, considering BiDi properties.
    /// </summary>
    /// <param name="text">The text to append.</param>
    /// <param name="run">The run of the text that is being appended.</param>
    private void appendTextBiDiAware(String text, Run run)
    {
        if (!StringUtil.hasChars(text))
            return;

        char bidiMark = (InlineHelper.getBool(run, FontAttr.BIDI))
            ? ControlChar.RIGHT_TO_LEFT_MARK_CHAR
            : ControlChar.LEFT_TO_RIGHT_MARK_CHAR;

        if (isNeedBiDiMark(text, run, true))
            getBuilder().append(bidiMark);

        appendText(text);

        if (isNeedBiDiMark(text, run, false))
            getBuilder().append(bidiMark);
    }


    /// <summary>
    /// Returns true, if a BiDi control character mark should be added to a specified text.
    /// </summary>
    private static boolean isNeedBiDiMark(String text, Run run, boolean isBefore)
    {
        Debug.assertTrue(StringUtil.hasChars(run.getText()));

        // Don't add a BiDi mark, if edge character of text has Strong Unicode type.
        char c = (isBefore) ? text.charAt(0) : text.charAt(run.getText().length() - 1);
        if (UnicodeCharacterDataResolver.isStrongBidiCharacterType(c))
            return false;

        Run siblingRun = (isBefore)
            ? (Run)run.previousSiblingOfType(NodeType.RUN)
            : (Run)run.nextSiblingOfType(NodeType.RUN);

        // Add a BiDi mark to a very first/last run.
        if (siblingRun == null)
            return true;

        boolean isBiDiRun = InlineHelper.getBool(run, FontAttr.BIDI);
        boolean isBiDiSiblingRun = InlineHelper.getBool(siblingRun, FontAttr.BIDI);

        // Add a BiDi mark, if run of text and its sibling have different BiDi.
        return (isBiDiRun != isBiDiSiblingRun);
    }

    /// <summary>
    /// Appends break control characters to <see cref="StringBuilder"/>.
    /// </summary>
    /// <param name="value">String value with control character.</param>
    private void appendBreak(String value)
    {
        if (!StringUtil.hasChars(value))
            return;

        flushCurWord();

        switch (value.charAt(0))
        {
            case ControlChar.LINE_BREAK_CHAR:
                appendTextValue(ControlChar.CR_LF);
                break;
            case ControlChar.PAGE_BREAK_CHAR:
                // WORDSNET-13598 Remove 0x0C (page break) character from output while
                // saving to text file.
                // WORDSNET-14405 Page breaks are written optionally.
                if (getSaveOptions().getForcePageBreaks())
                    appendTextValue(ControlChar.PAGE_BREAK);
                break;
            default:
                IWarningCallback callBack = getDocument().getWarningCallback();
                WarningUtil.warnUnexpected(callBack, WarningSource.TEXT, WarningStrings.UNEXPECTED_BREAK);
                break;
        }

        mCurPosition = 0;
    }

    /// <summary>
    /// Obtains index of the first numbered group which was captured successfully,
    /// otherwise return zero.
    /// </summary>
    /// <param name="groups">Collection with groups.</param>
    /// <returns>Index of the numbered group or zero.</returns>
    private static int getFirstNumberedGroup(GroupCollection groups)
    {
        // There are expected initialized group object which contains
        // at least one numbered (unnamed) group.
        Debug.assertTrue((groups != null) && (groups.getCount() > 1));

        int index = 0;

        for (int i = 1; i < groups.getCount(); ++i)
            index = groups.get(i).getSuccess() ? i : index;

        return index;
    }

    /// <summary>
    /// Gets the complete list label if normal one is needed or calls building of
    /// customized list label if SimplifyListLabels option chosen.
    /// </summary>
    /// <remarks>
    /// Considers a list indentation specified in SaveOptions of the writer.
    /// </remarks>
    private String buildListLabel(ListLabel listLabel)
    {
        // WORDSNET-17778 The ListIndentation option is introduced.
        String indent = getSaveOptions().getListIndentation().getIndent(listLabel.getListLevel().getLevelNumber());

        String label = (getSaveOptions().getSimplifyListLabels())
            ? buildSimplifiedListLabel(listLabel.getNumberState())
            : listLabel.getLabelStringOriginal();

        return msString.format("{0}{1}", indent, label);
    }

    /// <summary>
    /// <para>Builds the complete list label if SimplifyListLabels option chosen.</para>
    /// <para>In this case list label format from the model is ignored and some common rules of list labels generation
    /// are applied for all list items in a document.</para>
    ///
    /// <para>There are two rules:</para>
    /// <para>1. If current list level specifies a bulleted label, one of standard characters is inserted as list label.
    /// See <see cref="gSimplifiedBulletsCharacters"/> for list of used characters. This character is indented
    /// proportionally to list level number.</para>
    ///
    /// <para>Example:</para>
    ///
    /// <para>Original list items:</para>
    /// <para>$ First level</para>
    /// <para>        % Second level</para>
    /// <para>  * Third level</para>
    ///
    /// <para>List items with simplified labels:</para>
    /// <para>* First level</para>
    /// <para>  > Second level</para>
    /// <para>    + Third level</para>
    ///
    /// <para>2. If current list level specifies a numbered label, a sequence of numbers divided by dots is
    /// inserted as list label. The sequence is always contains numbers of all levels up to current.</para>
    ///
    /// <para>Example:</para>
    ///
    /// <para>Original list items:</para>
    /// <para>1. First level</para>
    /// <para>3) Second level</para>
    /// <para>d) Third level</para>
    ///
    /// <para>List items with simplified labels:</para>
    /// <para>1. First level</para>
    /// <para>1.3. Second level</para>
    /// <para>1.3.4. Third level</para>
    /// </summary>
    /// <remarks>There is enough place to improve the algorithm, e.g. skip numbers for list levels which are not used
    /// in current list.</remarks>
    private static String buildSimplifiedListLabel(ListNumberState state)
    {
        StringBuilder builder = new StringBuilder();

        ListLevel levelPr = state.getListLevel();

        if (levelPr.getNumberStyle() == NumberStyle.BULLET)
        {
            for (int j = 0; j < levelPr.getLevelNumber(); j++)
            {
                msStringBuilder.append(builder, "  ");
            }
            builder.append(G_SIMPLIFIED_BULLETS_CHARACTERS[levelPr.getLevelNumber()]);
        }
        else // If curListLevel.NumberStyle is any numbered format.
        {
            for (int j = 0; j <= levelPr.getLevelNumber(); j++)
            {
                builder.append(state.getNumber(j));
                builder.append('.');
            }
        }

        return builder.toString();
    }

    /// <summary>
    /// Gets a boolean value indicating either current position in Builder is at very start of a new line.
    /// </summary>
    private boolean isAtStart()
    {
        if (getBuilder().length() == 0)
            return true;

        char lastChar = getBuilder().charAt(getBuilder().length() - 1);
        return (lastChar == '\n') || (lastChar == '\u000b') || (lastChar == '\f');
    }

    /// <summary>
    /// Gets a boolean value indicating whether a cursor is inside a Table.
    /// </summary>
    private boolean isInTable() { return (mTableBuilderStack.getCount() > 0); }

    /// <summary>
    /// Gets <see cref="TxtTableBuilder"/> object.
    /// </summary>
    private TxtTableBuilder getTableBuilder() { return StackExtension.top(mTableBuilderStack); }

    /// <summary>
    /// Gets <see cref="TxtSaveOptions"/> object specified for this writer.
    /// </summary>
    private TxtSaveOptions getSaveOptions() { return (TxtSaveOptions)getSaveOptionsBase(); }

    private /*final*/ msStack<TxtTableBuilder> mTableBuilderStack = new msStack<TxtTableBuilder>();

    /// <summary>
    /// This stack is used to store information whether we need to write list label for a paragraph.
    /// </summary>
    private /*final*/ msStack<ListLabel> mCachedListLabelsStack;

    /// <summary>
    /// The cursor position inside a current line.
    /// </summary>
    private int mCurPosition;

    /// <summary>
    /// The current word being written.
    /// </summary>
    private /*final*/ StringBuilder mCurWord = new StringBuilder();

    /// <summary>
    /// Regular expression for searching line and page breaks symbols.
    /// </summary>
    /// <remarks>
    /// There are expected mutually exclusive conditions.
    /// </remarks>
    private static /*final*/ Regex G_REGEX_BREAKS = new Regex(
        "(" + ControlChar.LINE_BREAK + ")|" +
        "(" + ControlChar.PAGE_BREAK + ")|" +
        "([^" + msString.format("{0}{1}", ControlChar.LINE_BREAK, ControlChar.PAGE_BREAK) + "]*)",
        RegexOptions.SINGLELINE | RegexOptions.COMPILED);

    /// <summary>
    /// List of 9 ASCII replacements of list bullets for each level.
    /// Used TxtSaveOptions.SimplifyListLabels option is chosen.
    /// </summary>
    private static /*final*/ char[] G_SIMPLIFIED_BULLETS_CHARACTERS = { '*', '>', '+', '-', 'o', '>', '+', '-', 'o' };

    /// <summary>
    /// The BlockQuote level of <see cref="HtmlBlock"/> the current paragraph belongs.
    /// </summary>
    private int mBlockQuoteLevel;
}

