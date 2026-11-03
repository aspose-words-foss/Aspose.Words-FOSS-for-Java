// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.StringComparison;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ArrayUtil;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Text.Encoding;


/// <summary>
/// An utility class to work with a markdown.
/// </summary>
/*static*/ class MarkdownUtil
{
	/* Simulation of static class by using private constructor */
	private MarkdownUtil()
	{}

    /// <summary>
    /// Converts a specified level to a SetextHeading character.
    /// </summary>
    static char levelToSetextHeadingChar(int level)
    {
        switch (level)
        {
            case 1:
                return SetextHeadingBlock.HEADING_1_CHAR;
            case 2:
                return SetextHeadingBlock.HEADING_2_CHAR;
            default:
                throw new IllegalStateException(msString.format("Invalid level for a SetextHeading: '{0}'", level));
        }
    }

    /// <summary>
    /// Converts a specified character to a corresponding <see cref="ListMarker"/> enumeration.
    /// </summary>
    static /*ListMarker*/int toListMarker(char c)
    {
        if (msChar.isWhiteSpace(c))
            return ListMarker.NONE;

        switch (c)
        {
            case '.':
                return ListMarker.DOT;
            case ')':
                return ListMarker.PARENTHESIS;
            case '+':
                return ListMarker.PLUS;
            case '*':
                return ListMarker.ASTERISK;
            default:
                return ListMarker.MINUS;
        }
    }

    /// <summary>
    /// Converts a specified character to a corresponding <see cref="ListMarker"/> enumeration
    /// in respect of it is an ordered list.
    /// </summary>
    static /*ListMarker*/int toOrderedListMarker(char c)
    {
        if (msChar.isWhiteSpace(c))
            return ListMarker.NONE;

        switch (c)
        {
            case ')':
                return ListMarker.PARENTHESIS;
            default:
                return ListMarker.DOT;
        }
    }

    /// <summary>
    /// Converts a specified character to a corresponding <see cref="ListMarker"/> enumeration
    /// in respect of it is a bullet list.
    /// </summary>
    static /*ListMarker*/int toBulletListMarker(char c)
    {
        if (msChar.isWhiteSpace(c))
            return ListMarker.NONE;

        switch (c)
        {
            case '+':
                return ListMarker.PLUS;
            case '*':
                return ListMarker.ASTERISK;
            default:
                return ListMarker.MINUS;
        }
    }

    /// <summary>
    /// Converts a specified <see cref="ListMarker"/> enumeration to a corresponding char.
    /// </summary>
    static char listMarkerToChar(/*ListMarker*/int marker)
    {
        switch (marker)
        {
            case ListMarker.DOT:
                return '.';
            case ListMarker.PARENTHESIS:
                return ')';
            case ListMarker.MINUS:
                return '-';
            case ListMarker.PLUS:
                return '+';
            case ListMarker.ASTERISK:
                return '*';
            case ListMarker.NONE:
                return ControlChar.SPACE_CHAR;
            default:
                throw new IllegalStateException(msString.format("Unknown list marker: '{0}'", marker));
        }
    }

    /// <summary>
    /// Converts a specified style to a block type.
    /// </summary>
    static /*BlockType*/byte toBlockType(Style style)
    {
        if (style != null)
        {
            if (msString.startsWith(style.getName(), QUOTE_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.QUOTE;

            if (msString.startsWith(style.getName(), LIST_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.BULLET_LIST_ITEM;

            if (msString.startsWith(style.getName(), INDENTED_CODE_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.INDENTED_CODE;

            if (msString.startsWith(style.getName(), FENCED_CODE_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.FENCED_CODE;

            if (msString.startsWith(style.getName(), SETEXT_HEADING_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.SETEXT_HEADING;

            if (msString.startsWith(style.getName(), INLINE_CODE_STYLE_NAME, StringComparison.ORDINAL))
                return BlockType.INLINE_CODE;

            if (style.isHeading())
                return BlockType.ATX_HEADING;
        }

        return BlockType.PARAGRAPH;
    }

    /// <summary>
    /// Returns true, if a specified character is one of the ordered list markers ('.', ')').
    /// </summary>
    static boolean isOrderedListMarker(char c)
    {
        return (c == '.') || (c == ')');
    }

    /// <summary>
    /// Returns true if a specified line can be a bullet list item.
    /// </summary>
    static boolean isBulletListItem(StringBuilder line)
    {
        Debug.assertTrue(line != null);

        // There must be at least list marker followed by whitespace character.
        if (line.length() < 2)
            return false;

        if (!isBulletListMarker(line.charAt(0)))
            return false;

        if (!StringUtil.isWhiteSpace(line.charAt(1)))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true if a specified line can be an ordered list item.
    /// </summary>
    static boolean isOrderedListItem(StringBuilder line)
    {
        Debug.assertTrue(line != null);

        // The ordered list must start from a digits.
        int i = 0;
        while ((i < line.length()) && StringUtil.isDigit(line.charAt(i)))
            i++;

        // The length of ordered list number must be less than 10.
        if ((i == 0) || (i >= 10))
            return false;

        // There must be at least ordered list marker followed by whitespace character.
        if ((i + 1) >= line.length())
            return false;

        if (!isOrderedListMarker(line.charAt(i)))
            return false;

        if (!StringUtil.isWhiteSpace(line.charAt(i + 1)))
            return false;

        return true;
    }

    /// <summary>
    /// Returns true, if a text between specified characters is intraword.
    /// </summary>
    static boolean isIntraword(char prevChar, char nextChar)
    {
        return (Character.isLetterOrDigit(prevChar) && Character.isLetterOrDigit(nextChar));
    }

    /// <summary>
    /// Gets a flanking type.
    /// </summary>
    /// <remarks>
    /// See 6.4 Emphasis and strong emphasis at https://spec.commonmark.org for details.
    /// </remarks>
    static /*FlankingType*/int getFlankingType(char prevChar, char nextChar)
    {
        boolean isLeftFlanking = isLeftFlanking(prevChar, nextChar);
        boolean isRightFlanking = isRightFlanking(prevChar, nextChar);

        if (isLeftFlanking && isRightFlanking)
            return FlankingType.BOTH;

        if (isLeftFlanking)
            return FlankingType.LEFT;

        if (isRightFlanking)
            return FlankingType.RIGHT;

        return FlankingType.NONE;
    }

    /// <summary>
    /// Normalizes InlineCode block.
    /// </summary>
    /// <remarks>
    ///  See chapter 6.3 of markdown specification at https://spec.commonmark.org
    ///  Note! Only spaces, and not Unicode whitespace in general, are stripped in this way
    /// (see `Example 333` of specification that is TestInlineCodeF()).
    /// </remarks>
    static String normalize(String text)
    {
        // First, line endings are converted to spaces.
        String normalizedText = msString.replace(text, SOFT_LINE_BREAK_CHAR, ControlChar.SPACE_CHAR);

        // If the resulting string both begins and ends with a space character, but does not consist entirely
        // of space characters, a single space character is removed from the front and back.
        if (!StringUtil.containsOnlyWhitespaces(normalizedText) &&
            (normalizedText.charAt(0) == ControlChar.SPACE_CHAR) &&
            (normalizedText.charAt(normalizedText.length() - 1) == ControlChar.SPACE_CHAR))
            normalizedText = normalizedText.substring((1), (1) + (normalizedText.length() - 2));

        return normalizedText;
    }

    /// <summary>
    /// Returns length of a specified character.
    /// </summary>
    static int getLength(char c)
    {
        return (c == ControlChar.TAB_CHAR) ? TAB_SIZE : 1;
    }

    /// <summary>
    /// Returns length of a specified string considering tabs.
    /// </summary>
    static int getLength(String value)
    {
        if (!StringUtil.hasChars(value))
            return 0;

        int length = 0;
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            length += getLength(c);
        }

        return length;
    }

    /// <summary>
    /// Gets the number of characters that should be taken from a specified text to make
    /// their total length become not less than a specified required length, considering tabs.
    /// </summary>
    /// <remarks>
    /// This is a reverse operation to <see cref="GetLength(string)"/>.
    /// </remarks>
    static int getCharsCount(String text, int start, int requiredLength)
    {
        if (!StringUtil.hasChars(text))
            return 0;

        int startDiff = start;
        while ((requiredLength > 0) && (startDiff < text.length()))
        {
            requiredLength -= getLength(text.charAt(startDiff));
            startDiff++;
        }

        return startDiff - start;
    }

    /// <summary>
    /// Returns text with the backslash-escaped double quotes.
    /// </summary>
    static String escapeDoubleQuotes(String text)
    {
        return (text == null)
            ? null
            : text.replace(ESCAPED_DOUBLE_QUOTE, DOUBLE_QUOTE).replace(DOUBLE_QUOTE, ESCAPED_DOUBLE_QUOTE);
    }

    /// <summary>
    /// Returns text with unescaped quotes and right parentheses.
    /// </summary>
    static String unescapeQuotesAndParentheses(String text)
    {
        final String QUOTE = "'";
        final String ESCAPED_QUOTE = "\\'";
        final String PARENTHESIS = ")";
        final String ESCAPED_PARENTHESIS = "\\)";

        if (text == null)
            return null;

        return text
            .replace(ESCAPED_QUOTE, QUOTE)
            .replace(ESCAPED_DOUBLE_QUOTE, DOUBLE_QUOTE)
            .replace(ESCAPED_PARENTHESIS, PARENTHESIS);
    }

    /// <summary>
    /// Adds backslash-escaping for markup symbols.
    /// </summary>
    static String escapeMarkupSymbols(String text)
    {
        return escapeSymbols(text, G_MARKUP_SYMBOLS_FOR_ESCAPING);
    }

    /// <summary>
    /// Removes backslash-escaping for markup symbols.
    /// </summary>
    static String unescapeMarkupSymbols(String text)
    {
        return unescapeSymbols(text, G_ESCAPABLE_MARKUP_SYMBOLS);
    }

    /// <summary>
    /// Adds backslash-escaping for square brackets.
    /// </summary>
    static String escapeSquareBrackets(String text)
    {
        return escapeSymbols(text, G_SQUARE_BRACKETS);
    }

    /// <summary>
    /// Removes backslash-escaping for square brackets.
    /// </summary>
    static String unescapeSquareBrackets(String text)
    {
        return unescapeSymbols(text, G_SQUARE_BRACKETS);
    }

    /// <summary>
    /// Returns index of the first Whitespace character or -1 if not found.
    /// The search starts at a specified character position.
    /// </summary>
    static int indexOfWhitespace(String text, int startIndex, int endIndex)
    {
        if (text != null)
        {
            for (int i = startIndex; i <= endIndex; i++)
            {
                if (isWhitespaceCharacter(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns index of the first non-Whitespace character or -1 if not found.
    /// The search starts at a specified character position.
    /// </summary>
    static int indexOfNonWhitespace(String text, int startIndex, int endIndex)
    {
        if (text != null)
        {
            for (int i = startIndex; i <= endIndex; i++)
            {
                if (!isWhitespaceCharacter(text.charAt(i)))
                    return i;
            }
        }

        return -1;
    }

    /// <summary>
    /// Returns index of the first occurrence of the specified unescaped character or -1 if not found.
    /// The search starts at a specified character position.
    /// </summary>
    static int indexOfUnescaped(String text, char c, int startIndex, int endIndex)
    {
        while (startIndex < endIndex)
        {
            startIndex = msString.indexOf(text, c, startIndex, endIndex - startIndex + 1);

            if (startIndex == -1)
                return -1;

            if (isEscaped(text, startIndex))
                startIndex++;
            else
                return startIndex;
        }

        return -1;
    }

    /// <summary>
    /// Returns true, if a specified character is markdown ASCII whitespace.
    /// </summary>
    /// <remarks>
    /// According to spec at https://spec.commonmark.org/0.28/#whitespace
    /// Whitespace is a sequence of one or more whitespace characters.
    /// A whitespace character is a space (U+0020), tab (U+0009), newline (U+000A), line tabulation (U+000B),
    /// form feed (U+000C), or carriage return (U+000D).
    /// </remarks>
    static boolean isWhitespaceCharacter(char c)
    {
        return (
            (c == ControlChar.SPACE_CHAR) ||
            (c == ControlChar.TAB_CHAR) ||
            (c == ControlChar.LINE_FEED_CHAR) ||
            (c == ControlChar.LINE_BREAK_CHAR) ||
            (c == ControlChar.PAGE_BREAK_CHAR) ||
            (c == ControlChar.PARAGRAPH_BREAK_CHAR) ||
            // WORDSNET-18341 Also added SoftLineBreakChar as it actually
            // will be converted to some of the whitespace characters.
            (c == SOFT_LINE_BREAK_CHAR));
    }

    /// <summary>
    /// Returns number after a specified substring inside a text.
    /// </summary>
    static int getNumberAfterSubstring(String text, String substring)
    {
        int substringIdx = msString.indexOf(text, substring, StringComparison.INVARIANT_CULTURE_IGNORE_CASE);

        if (substringIdx == -1)
            return -1;

        int numberIdx = substringIdx + substring.length();

        // One dot (.) is allowed just after substring to increase readability.
        if ((numberIdx < text.length()) && (text.charAt(numberIdx) == '.'))
            numberIdx++;

        int numberLength = 0;
        while (((numberIdx + numberLength) < text.length()) && StringUtil.isDigit(text.charAt(numberIdx + numberLength)))
            numberLength++;

        if (numberLength == 0)
            return 0;

        String number = text.substring((numberIdx), (numberIdx) + (numberLength));

        return FormatterPal.parseInt(number);
    }

    /// <summary>
    /// Returns true, if specified unescaped brackets are balanced.
    /// </summary>
    static boolean areBalanced(String text, int startIndex, int endIndex, char leftBracket, char rightBracket)
    {
        Debug.assertTrue(text != null);

        int leftBracketCounter = 0;
        int rightBracketCounter = 0;

        char prevChar = Character.MIN_VALUE;
        for (int i = startIndex; i <= endIndex; i++)
        {
            char curChar = text.charAt(i);
            // Skip escaped characters.
            if (prevChar != ControlChar.BACKSLASH_CHAR)
            {
                if (curChar == leftBracket)
                    leftBracketCounter++;
                else if (curChar == rightBracket)
                    rightBracketCounter++;

                if (leftBracketCounter < rightBracketCounter)
                    return false;
            }

            prevChar = curChar;
        }

        return (leftBracketCounter == rightBracketCounter);
    }

    /// <summary>
    /// Returns true, if specified unescaped brackets are balanced.
    /// </summary>
    static boolean areBalanced(Delimiter opening, Delimiter closing, char leftBracket, char rightBracket)
    {
        String text = opening.getText();
        int startIndex = opening.getEnd() + 1;
        int endIndex = closing.getStart() - 1;


        int leftBracketCounter = 0;
        int rightBracketCounter = 0;

        char prevChar = Character.MIN_VALUE;
        for (int i = startIndex; i <= endIndex; i++)
        {
            // Skip characters inside linked delimiters with higher priority.
            if (getPriority(i, opening, closing) > closing.getPriority())
            {
                prevChar = Character.MIN_VALUE;
                continue;
            }

            char curChar = text.charAt(i);
            // Skip escaped characters.
            if (prevChar != ControlChar.BACKSLASH_CHAR)
            {
                if (curChar == leftBracket)
                    leftBracketCounter++;
                else if (curChar == rightBracket)
                    rightBracketCounter++;

                if (leftBracketCounter < rightBracketCounter)
                    return false;
            }

            prevChar = curChar;
        }

        return (leftBracketCounter == rightBracketCounter);
    }

    /// <summary>
    /// Returns true, if character at a specified index in text is escaped.
    /// </summary>
    static boolean isEscaped(String text, int index)
    {
        Debug.assertTrue(text != null);
        Debug.assertTrue((index >= 0) && (index < text.length()));

        int escapingStart = index - 1;
        while ((escapingStart >= 0) && (text.charAt(escapingStart) == ControlChar.BACKSLASH_CHAR))
            escapingStart--;

        int length = (index - escapingStart) + 1;
        return ((length % 2) == 1);
    }

    /// <summary>
    /// Returns true, if a specified text has the non-escaped specified characters.
    /// </summary>
    static boolean hasNonEscapedCharacters(String text, int startIndex, int endIndex, /*params*/ char... values)
    {
        Debug.assertTrue(text != null);

        char prevChar = Character.MIN_VALUE;
        for (int i = startIndex; i <= endIndex; i++)
        {
            char curChar = text.charAt(i);
            if (ArrayUtil.findCharInArray(values, curChar) && (prevChar != ControlChar.BACKSLASH_CHAR))
                return true;

            prevChar = curChar;
        }

        return false;
    }

    /// <summary>
    /// Returns true, if a specified character is escapable.
    /// </summary>
    static boolean isEscapableMarkupCharacter(char c)
    {
        return ArrayUtil.findCharInArray(G_ESCAPABLE_MARKUP_CHARACTERS, c);
    }

    /// <summary>
    /// Unescapes text.
    /// </summary>
    static StringBuilder unEscape(String text)
    {
        StringBuilder unescapedText = new StringBuilder();
        if (text.length() == 0)
            return unescapedText;

        char prevChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++)
        {
            char curChar = text.charAt(i);

            if ((prevChar != ControlChar.BACKSLASH_CHAR) || !isEscapableMarkupCharacter(curChar))
                unescapedText.append(prevChar);

            prevChar = curChar;
        }

        unescapedText.append(prevChar);

        return unescapedText;
    }

    /// <summary>
    /// Gets first linked delimiter of a specified type in a specified range.
    /// Range boundaries are not included.
    /// </summary>
    static Delimiter getLinkedInRange(/*DelimiterType*/int type, Delimiter a, Delimiter b)
    {
        Debug.assertTrue((a != null) && (b != null));

        Delimiter start = (a.isBefore(b)) ? a : b;
        Delimiter end = (a == start) ? b : a;

        // The boundaries are not included.
        start = (Delimiter)start.getNextNode();
        while (start != end)
        {
            if (!start.isNotIncluded() && start.isLinked() && (start.getType() == type))
                return start;

            start = (Delimiter)start.getNextNode();
        }

        return null;
    }

    /// <summary>
    /// Returns left indent for a specified node in twips.
    /// </summary>
    static int getLeftIndent(Node node)
    {
        if (node == null)
            return -1;

        switch (node.getNodeType())
        {
            case NodeType.PARAGRAPH:
            {
                return (/*int*/Integer)((Paragraph)node).fetchParaAttr(ParaAttr.LEFT_INDENT, RevisionsView.ORIGINAL);
            }

            case NodeType.TABLE:
            {
                Row firstRow = ((Table)node).getFirstRow();
                return (firstRow != null) ? (/*int*/Integer)((IRowAttrSource)firstRow).fetchRowAttr(TableAttr.LEFT_INDENT) : 0;
            }

            // Skip bookmarks.
            case NodeType.BOOKMARK_START:
            case NodeType.BOOKMARK_END:
            {
                return getLeftIndent(node.getPreviousSibling());
            }

            default:
                return -1;
        }
    }

    /// <summary>
    /// Returns true, if a specified character is markdown ASCII control character.
    /// </summary>
    /// <remarks>
    /// An ASCII control character is a character between U+0000–1F (both including) or U+007F.
    /// </remarks>
    static boolean isAsciiControlChar(char c)
    {
        return ((0 <= c) && (c <= '\u001F')) || (c == '\u007F');
    }

    /// <summary>
    /// Resolves whitespaces and line breaks by inserting, removing or converting them appropriately.
    /// </summary>
    static String resolveWhitespacesAndLineBreaks(Block block, char softLineBreakCharReplacement)
    {
        ArrayList<StringBuilder> lines = new ArrayList<StringBuilder>();

        StringBuilder currentLine = new StringBuilder();
        for (int i = 0; i < block.getText().length(); i++)
        {
        	char c = block.getText().charAt(i);
            // We need to remove leading spaces in the beginning of each line.
            if ((currentLine.length() == 0) && isWhitespaceCharacter(c))
            {
                // For the non-first line we are obviously at the very beginning in this point.
                if (lines.size() > 0)
                    continue;

                // Otherwise, we need some checks to ensure we are at the beginning of the text line:
                boolean isSkip = true;
                Block curBlock = block;
                while (curBlock != null)
                {
                    if (curBlock.getPreviousSibling(
                            BlockType.INLINE,
                            BlockType.UNDERLINE,
                            BlockType.BOLD_INLINE,
                            BlockType.ITALIC_INLINE,
                            BlockType.STRIKETHROUGH,
                            BlockType.INLINE_CODE,
                            BlockType.AUTOLINK,
                            BlockType.IMAGE_DESCRIPTION,
                            BlockType.FOOTNOTE_REFERENCE,
                            BlockType.LINK_DESTINATION) != null)
                    {
                        isSkip = false;
                        break;
                    }

                    curBlock = curBlock.getParent();
                    if (curBlock instanceof InlineContainerBlock)
                    {
                        break;
                    }
                }

                if (isSkip)
                    continue;
            }

            if (c == SOFT_LINE_BREAK_CHAR)
            {
                // Trim all whitespaces from end and place space character instead of soft line break char.
                StringUtil.trimEnd(currentLine);
                currentLine.append(softLineBreakCharReplacement);
            }
            else if (c == HARD_LINE_BREAK_SLASH_CHAR)
            {
                // Don't trim anything and start a new line.
                currentLine.append(ControlChar.LINE_BREAK_CHAR);
            }
            else if (c == HARD_LINE_BREAK_SPACES_CHAR)
            {
                // Trim all whitespaces from end and start a new line.
                StringUtil.trimEnd(currentLine);
                currentLine.append(ControlChar.LINE_BREAK_CHAR);
            }
            else
            {
                currentLine.append(c);
                continue;
            }

            msArrayList.add(lines, currentLine);
            currentLine = new StringBuilder();
        }

        msArrayList.add(lines, currentLine);

        StringBuilder sb = lines.get(0);
        for (int i = 1; i < lines.size(); i++)
            msStringBuilder.append(sb, lines.get(i));

        return sb.toString();
    }

    /// <summary>
    /// Replaces specified characters within a specified string with
    /// a corresponding hexadecimal numeric character references.
    /// </summary>
    /// <remarks>
    /// See https://spec.commonmark.org/0.31.2/#hexadecimal-numeric-character-references
    /// </remarks>
    static String replaceWithHexRef(String s, String charactersToReplace)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
        {
        	char c = s.charAt(i);
            if (charactersToReplace.indexOf(c) == -1)
            {
                sb.append(c);
                continue;
            }

            byte[] bytes = Encoding.getUTF8().getBytes(new char[] { c });
            String hexCode = StringUtil.bytesToHex(bytes);

            msStringBuilder.append(msStringBuilder.append(sb, "&#x"), hexCode).append(';');
        }

        return sb.toString();
    }

    /// <summary>
    /// Returns priority of the delimiter that is located between specified two delimiters at a specified position.
    /// </summary>
    private static /*DelimiterPriority*/int getPriority(int position, Delimiter left, Delimiter right)
    {
        Debug.assertTrue(left.isBefore(right));

        /*DelimiterPriority*/int priority = DelimiterPriority.LOWEST;

        Delimiter start = left;
        while (start != right)
        {
            if (!start.isNotIncluded() && start.isLinked())
            {
                if ((start.getEnd() < position) && (position < start.getLinkedDelimiter().getStart()))
                    priority = start.getPriority();
            }

            start = (Delimiter)start.getNextNode();
        }

        return priority;
    }

    /// <summary>
    /// Adds backslash-escaping for specified symbols.
    /// </summary>
    private static String escapeSymbols(String text, String[] symbols)
    {
        Debug.assertTrue(text != null);
        Debug.assertTrue(symbols != null);
        String result = text;
        for (String symbol : symbols)
        {
            String escaped = msString.format("{0}{1}", ControlChar.BACKSLASH_CHAR, symbol);
            result = result.replace(symbol, escaped);
        }

        return result;
    }


    /// <summary>
    /// Removes backslash-escaping for specified symbols.
    /// </summary>
    private static String unescapeSymbols(String text, String[] symbols)
    {
        Debug.assertTrue(text != null);
        Debug.assertTrue(symbols != null);
        String result = text;
        if (result.contains(Character.toString(ControlChar.BACKSLASH_CHAR)))
        {
            for (String symbol : symbols)
            {
                String escaped = msString.format("{0}{1}", ControlChar.BACKSLASH_CHAR, symbol);
                result = result.replace(escaped, symbol);
            }
        }
        return result;
    }

    /// <summary>
    /// Returns true, if a specified character is one of the bullet list markers (-, +, *).
    /// </summary>
    private static boolean isBulletListMarker(char c)
    {
        return (c == '-') || (c == '+') || (c == '*');
    }

    /// <summary>
    /// Returns true, if delimiter runs between specified characters is left-flanking.
    /// </summary>
    /// <remarks>
    /// See 6.4 Emphasis and strong emphasis at https://spec.commonmark.org for details.
    /// </remarks>
    private static boolean isLeftFlanking(char prevChar, char nextChar)
    {
        return (!isWhiteSpace(nextChar) &&
                (!isPunctuation(nextChar) || isWhiteSpace(prevChar) || isPunctuation(prevChar)));
    }

    /// <summary>
    /// Returns true, if delimiter runs between specified characters is right-flanking.
    /// </summary>
    /// <remarks>
    /// See 6.4 Emphasis and strong emphasis at https://spec.commonmark.org for details.
    /// </remarks>
    private static boolean isRightFlanking(char prevChar, char nextChar)
    {
        return (!isWhiteSpace(prevChar) &&
                (!isPunctuation(prevChar) || isWhiteSpace(nextChar) || isPunctuation(nextChar)));
    }

    /// <summary>
    /// Returns true, if a specified character is whitespace character.
    /// </summary>
    private static boolean isWhiteSpace(char c)
    {
        return msChar.isWhiteSpace(c) || (c == SOFT_LINE_BREAK_CHAR);
    }

    /// <summary>
    /// Returns true, if a specified character is markdown punctuation character.
    /// </summary>
    /// <remarks>
    /// See details at https://spec.commonmark.org/0.29/#punctuation-character.
    /// </remarks>
    private static boolean isPunctuation(char c)
    {
        return (c == '$') ||
               (c == '+') ||
               (c == '<') ||
               (c == '=') ||
               (c == '>') ||
               (c == '\\') ||
               (c == '^') ||
               (c == '`') ||
               (c == '~') ||
               (c == '|') ||
               msChar.isPunctuation(c);
    }

    /// <summary>
    /// Escapable markup symbols.
    /// </summary>
    /// <remarks>
    /// The following markup symbols can be backslash-escaped, as usual in Markdown:
    /// https://spec.commonmark.org/0.29/#example-496
    /// </remarks>
    private static /*final*/ String[] G_ESCAPABLE_MARKUP_SYMBOLS =
    {
        "'", ".", "+", "-", "=", "*", "_", "`", "~", ":", "!", "#", "$", "%", "&", "@", "/", "?", "^", "|", ";", "\\",
        "\"", "<", ">", "[", "]", "(", ")", "{", "}"
    };

    /// <summary>
    /// Escapable markup characters.
    /// </summary>
    /// <remarks>
    /// This should be the same characters as in gEscapableMarkupSymbols,
    /// but it is limited to the specified ones (bold and italic emphases delimiters) for a while.
    /// </remarks>
    private static /*final*/ char[] G_ESCAPABLE_MARKUP_CHARACTERS = { '*', '_' };

    /// <summary>
    /// Markup symbols for backslash-escaping.
    /// </summary>
    private static /*final*/ String[] G_MARKUP_SYMBOLS_FOR_ESCAPING = { "(", ")", "<", ">" };

    /// <summary>
    /// Square brackets.
    /// </summary>
    private static /*final*/ String[] G_SQUARE_BRACKETS = { "[", "]" };

    /// <summary>
    /// A Tab size in spaces.
    /// </summary>
    static final int TAB_SIZE = 4;

    static final String QUOTE_STYLE_NAME = "Quote";
    static final String LIST_STYLE_NAME = "List";
    static final String SETEXT_HEADING_STYLE_NAME = "SetextHeading";
    static final String HEADING_STYLE_NAME = "Heading";
    static final String FENCED_CODE_STYLE_NAME = "FencedCode";
    static final String INDENTED_CODE_STYLE_NAME = "IndentedCode";
    static final String FOOTNOTE_STYLE_NAME = "Footnote Text";
    static final String INLINE_CODE_STYLE_NAME = "InlineCode";

    /// <summary>
    /// Space character.
    /// </summary>
    static final char SPACE_CHAR = '\u0020';

    /// <summary>
    /// Soft line break.
    /// </summary>
    static final char SOFT_LINE_BREAK_CHAR = '\uE00b';

    /// <summary>
    /// Hard line break character for backslash.
    /// </summary>
    static final char HARD_LINE_BREAK_SLASH_CHAR = '\uE00d';

    /// <summary>
    /// Hard line break character for two spaces.
    /// </summary>
    static final char HARD_LINE_BREAK_SPACES_CHAR = '\uE00a';

    /// <summary>
    /// Hard line break string for backslash.
    /// </summary>
    static /*final*/ String HARD_LINE_BREAK_SLASH = Character.toString(HARD_LINE_BREAK_SLASH_CHAR);

    /// <summary>
    /// Double Quote character.
    /// </summary>
    private static final String DOUBLE_QUOTE = "\"";

    /// <summary>
    /// Backslash-escaped Double Quote character.
    /// </summary>
    private static final String ESCAPED_DOUBLE_QUOTE = "\\\"";
}

