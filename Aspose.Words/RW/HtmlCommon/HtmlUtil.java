// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Utility functions to deal with HTML conversion.
/// </summary>
/*static*/ class HtmlUtil
{
	/* Simulation of static class by using private constructor */
	private HtmlUtil()
	{}

    /// <summary>
    /// Removes Word control characters from a string (all characters with codes U+0001..U+001F but tab U+0009
    /// and line feed U+000A, which are processed somewhere else).
    /// </summary>
    /// <param name="text">Text for processing.</param>
    static String removeControlCharacters(String text)
    {
        return removeControlCharsAndWhitespaces(text, true, false, false);
    }

    /// <summary>
    /// Trims whitespace from a string.
    /// White space can be leading (left), in the middle and trailing.
    /// Middle and trailing whitespace is processed in the same way - it is collapsed into one space character.
    /// Leading whitespace processing has two options. It can either trim it completely or leave one space.
    /// </summary>
    /// <param name="text">Text for processing.</param>
    /// <param name="isFullLeftTrim">Whether to completely trim leading whitespace or leave one space character.</param>
    static String removeWhitespaces(String text, boolean isFullLeftTrim)
    {
        return removeControlCharsAndWhitespaces(text, false, true, isFullLeftTrim);
    }

    /// <summary>
    /// Removes Word control characters from a string (all characters with codes U+0001..U+001F but tab U+0009
    /// and line feed U+000A, which are processed somewhere else) and trims whitespace from a string.
    /// White space can be leading (left), in the middle and trailing.
    /// Middle and trailing whitespace is processed in the same way - it is collapsed into one space character.
    /// Leading whitespace processing has two options. It can either trim it completely or leave one space.
    /// </summary>
    /// <param name="text">Text for processing.</param>
    /// <param name="isFullLeftTrim">Whether to completely trim leading whitespace or leave one space character.</param>
    static String removeControlCharsAndWhitespaces(String text, boolean isFullLeftTrim)
    {
        return removeControlCharsAndWhitespaces(text, true, true, isFullLeftTrim);
    }

    static boolean containsAnythingButWhitespaces(String text, boolean newlinesAreWhitespaces)
    {
        for (int i = 0; i < text.length(); i++)
        {
        	char c = text.charAt(i);
            boolean isWhitespace = (c == '\n')
                ? newlinesAreWhitespaces
                : isWhitespace(c);
            if (!isWhitespace)
            {
                return true;
            }
        }
        return false;
    }

    static boolean isWhitespace(char c)
    {
        // http://www.w3.org/TR/html5/infrastructure.html#space-character
        return (c == '\u0009') ||
               (c == '\n') ||
               (c == '\f') ||
               (c == '\r') ||
               (c == '\u0020');
    }

    /// <summary>
    /// Validates an URI string read from a HTML attribute like "href" or "src".
    /// </summary>
    /// <remarks>
    /// <para>Browsers validate URIs as follows:</para>
    /// <para>1) remove leading and trailing space (U+0020) and control (U+0001..U+001F) characters;</para>
    /// <para>2) remove all control characters except for EOL (U+000A and U+000D) and TAB (U+0009).</para>
    /// <para>Additionally, we escape the double quote (U+0022) character.</para>
    /// </remarks>
    static String validateUri(String uri)
    {
        if (!StringUtil.hasChars(uri))
            return uri;

        int leading = 0;
        for (int i = 0; i < uri.length(); i++)
        {
            if (!isControlCharacter(uri.charAt(i)) && !isWhitespace(uri.charAt(i)))
            {
                break;
            }
            ++leading;
        }

        int trailing = 0;
        for (int i = uri.length() - 1; i >= leading; i--)
        {
            if (!isControlCharacter(uri.charAt(i)) && !isWhitespace(uri.charAt(i)))
            {
                break;
            }
            ++trailing;
        }

        int trimmedLength = uri.length() - leading - trailing;
        if (trimmedLength == 0)
            return "";

        // The specified length will be not enough if the URI contains quotation marks but it's a rare case.
        StringBuilder validatedUri = new StringBuilder(trimmedLength);
        for (int i = leading; i < (leading + trimmedLength); i++)
        {
            char c = uri.charAt(i);
            if (c == '\"')
            {
                // The 'QUOTATION MARK' (U+0022) character causes problems in field codes and we have to escape it.
                msStringBuilder.append(validatedUri, "%22");
            }
            else if ((c == '\n') || (c == '\r') || (c == '\t'))
            {
                // These characters are removed from URIs upon validation.
            }
            else
            {
                // Append all other characters as is.
                validatedUri.append(c);
            }
        }

        return validatedUri.toString();
    }

    /// <summary>
    /// Converts a string into Word tab alignment.
    /// </summary>
    /// <param name="tabAlignment">Text representation of Word tab alignment.</param>
    /// <param name="result">Result tab alignment value.</param>
    /// <returns>
    /// <c>true</c>, if the tab alignment is converted sucessfully.
    /// <c>false</c> otherwise.
    /// </returns>
    static boolean parseTapStopAlignment(String tabAlignment, /*out*/RefInt result)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(tabAlignment)))
        {
            case /*"left"*/0:
                result.set(TabAlignment.LEFT);
                return true;
            case /*"center"*/1:
                result.set(TabAlignment.CENTER);
                return true;
            case /*"right"*/2:
                result.set(TabAlignment.RIGHT);
                return true;
            case /*"decimal"*/3:
                result.set(TabAlignment.DECIMAL);
                return true;
            case /*"bar"*/4:
                result.set(TabAlignment.BAR);
                return true;
            case /*"list"*/5:
                result.set(TabAlignment.LIST);
                return true;
            case /*"clear"*/6:
                result.set(TabAlignment.CLEAR);
                return true;
            default:
                result.set(TabAlignment.LEFT);
                return false;
        }
    }

    /// <summary>
    /// Converts a string into Word tab leader.
    /// </summary>
    /// <param name="tabLeader">Text representation of Word tab leader.</param>
    /// <param name="result">Result tab leader value.</param>
    /// <returns>
    /// <c>true</c>, if the tab leader is converted sucessfully.
    /// <c>false</c> otherwise.
    /// </returns>
    static boolean parseTapStopLeader(String tabLeader, /*out*/RefInt result)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(tabLeader)))
        {
            case /*"none"*/7:
                result.set(TabLeader.NONE);
                return true;
            case /*"dots"*/8:
                result.set(TabLeader.DOTS);
                return true;
            case /*"dashes"*/9:
                result.set(TabLeader.DASHES);
                return true;
            case /*"line"*/10:
                result.set(TabLeader.LINE);
                return true;
            case /*"heavy"*/11:
                result.set(TabLeader.HEAVY);
                return true;
            case /*"middledot"*/12:
                result.set(TabLeader.MIDDLE_DOT);
                return true;
            default:
                result.set(TabLeader.NONE);
                return false;
        }
    }

    /// <summary>
    /// Converts a string into Word wrap type.
    /// </summary>
    /// <param name="wrapType">Text representation of Word wrap type.</param>
    /// <param name="result">Result wrap type value.</param>
    /// <returns>
    /// <c>true</c>, if the wrap type is converted sucessfully.
    /// <c>false</c> otherwise.
    /// </returns>
    static boolean parseWrapType(String wrapType, /*out*/RefInt result)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(wrapType)))
        {
            case /*"none"*/7:
                result.set(WrapType.NONE);
                return true;
            case /*"inline"*/13:
                result.set(WrapType.INLINE);
                return true;
            case /*"topbottom"*/14:
                result.set(WrapType.TOP_BOTTOM);
                return true;
            case /*"square"*/15:
                result.set(WrapType.SQUARE);
                return true;
            case /*"tight"*/16:
                result.set(WrapType.TIGHT);
                return true;
            case /*"through"*/17:
                result.set(WrapType.THROUGH);
                return true;
            default:
                result.set(WrapType.NONE);
                return false;
        }
    }

    /// <summary>
    /// Converts a string into relative horizontal position.
    /// </summary>
    /// <param name="position">Text representation of relative horizontal position.</param>
    /// <param name="result">Result relative horizontal position value.</param>
    /// <returns>
    /// <c>true</c>, if the relative horizontal position is converted sucessfully.
    /// <c>false</c> otherwise.
    /// </returns>
    static boolean parseRelativeHorizontalPosition(String position, /*out*/RefInt result)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(position)))
        {
            case /*"margin"*/18:
                result.set(RelativeHorizontalPosition.MARGIN);
                return true;
            case /*"page"*/19:
                result.set(RelativeHorizontalPosition.PAGE);
                return true;
            case /*"column"*/20:
                result.set(RelativeHorizontalPosition.COLUMN);
                return true;
            case /*"character"*/21:
                result.set(RelativeHorizontalPosition.CHARACTER);
                return true;
            case /*"leftmargin"*/22:
                result.set(RelativeHorizontalPosition.LEFT_MARGIN);
                return true;
            case /*"rightmargin"*/23:
                result.set(RelativeHorizontalPosition.RIGHT_MARGIN);
                return true;
            case /*"insidemargin"*/24:
                result.set(RelativeHorizontalPosition.INSIDE_MARGIN);
                return true;
            case /*"outsidemargin"*/25:
                result.set(RelativeHorizontalPosition.OUTSIDE_MARGIN);
                return true;
            default:
                result.set(RelativeHorizontalPosition.COLUMN);
                return false;
        }
    }

    /// <summary>
    /// Converts a string into relative vertical position.
    /// </summary>
    /// <param name="position">Text representation of relative vertical position.</param>
    /// <param name="result">Result relative vertical position value.</param>
    /// <returns>
    /// <c>true</c>, if the relative vertical position is converted sucessfully.
    /// <c>false</c> otherwise.
    /// </returns>
    static boolean parseRelativeVerticalPosition(String position, /*out*/RefInt result)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(position)))
        {
            case /*"margin"*/18:
                result.set(RelativeVerticalPosition.MARGIN);
                return true;
            case /*"page"*/19:
                result.set(RelativeVerticalPosition.PAGE);
                return true;
            case /*"paragraph"*/26:
                result.set(RelativeVerticalPosition.PARAGRAPH);
                return true;
            case /*"line"*/10:
                result.set(RelativeVerticalPosition.LINE);
                return true;
            case /*"topmargin"*/27:
                result.set(RelativeVerticalPosition.TOP_MARGIN);
                return true;
            case /*"bottommargin"*/28:
                result.set(RelativeVerticalPosition.BOTTOM_MARGIN);
                return true;
            case /*"insidemargin"*/24:
                result.set(RelativeVerticalPosition.INSIDE_MARGIN);
                return true;
            case /*"outsidemargin"*/25:
                result.set(RelativeVerticalPosition.OUTSIDE_MARGIN);
                return true;
            default:
                result.set(RelativeVerticalPosition.PARAGRAPH);
                return false;
        }
    }

    /// <summary>
    /// Parses a non-negative integer according to HTML 5 rules.
    /// </summary>
    /// <param name="s">String to parse.</param>
    /// <returns>
    /// The parsed value, if parsing was successful;
    /// <c>int.MinValue</c> otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from here:
    /// http://www.w3.org/TR/html5/infrastructure.html#rules-for-parsing-non-negative-integers
    /// Returned values, if sucessfully parsed, are in range [0..2147483647]
    /// (in other words, the range is [0..int.MaxValue]).
    /// In case of an overflow the value of 2147483647 (int.MaxValue) is returned.
    /// </remarks>
    static int parseNonNegativeInteger(String s)
    {
        if (s == null)
            return Integer.MIN_VALUE;

        int value = parseInteger(s);
        return (value >= 0) ? value : Integer.MIN_VALUE;
    }

    /// <summary>
    /// Parses an integer according to HTML 5 rules.
    /// </summary>
    /// <param name="s">String to parse.</param>
    /// <returns>
    /// The parsed value, if parsing was successful;
    /// <c>int.MinValue</c> otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from here: http://www.w3.org/TR/html5/infrastructure.html#rules-for-parsing-integers
    /// Returned values are in range [-2147483647..2147483647] (in other words, the range is [-int.MaxValue..int.MaxValue]).
    /// In case of an overflow 2147483647 is returned if the result is positive,
    /// and -2147483647 is returned if the result is negative.
    /// </remarks>
    static int parseInteger(String s)
    {
        if (s == null)
            return Integer.MIN_VALUE;

        // 1. Let input be the string being parsed.
        // 2. Let position be a pointer into input, initially pointing at the start of the string.
        // 3. Let sign have the value "positive".
        int i = 0;
        int sign = 1;

        // 4. Skip whitespace.
        while ((i < s.length()) && isWhitespace(s.charAt(i)))
        {
            ++i;
        }

        // 5. If position is past the end of input, return an error.
        if (i >= s.length())
        {
            return Integer.MIN_VALUE;
        }

        // 6. If the character indicated by position (the first character) is a "-" (U+002D) character:
        if (s.charAt(i) == '\u002D')
        {
            // 6.1. Let sign be "negative".
            sign = -1;

            // 6.2. Advance position to the next character.
            ++i;
        }
        // 6. Otherwise, if the character indicated by position (the first character) is a "+" (U+002B) character:
        else if (s.charAt(i) == '\u002B')
        {
            // 6.1. Advance position to the next character. (The "+" is ignored, but it is not conforming.)
            ++i;
        }

        // 6.2. (6.3.) If position is past the end of input, return an error.
        if (i >= s.length())
        {
            return Integer.MIN_VALUE;
        }

        // 7. If the character indicated by position is not one of ASCII digits, then return an error.
        if (!StringUtil.isDigit(s.charAt(i)))
        {
            return Integer.MIN_VALUE;
        }

        // 8. Collect a sequence of characters in the range ASCII digits, and interpret the resulting sequence
        // as a base-ten integer. Let value be that integer.
        long value = 0;
        while ((i < s.length()) && StringUtil.isDigit(s.charAt(i)))
        {
            value *= 10;
            int digit = s.charAt(i) - '0';
            value += digit;
            ++i;

            boolean overflow = value > Integer.MAX_VALUE;
            if (overflow)
            {
                return (sign > 0)
                    ? Integer.MAX_VALUE
                    : -Integer.MAX_VALUE;
            }
        }

        // 9. If sign is "positive", return value, otherwise return the result of subtracting value from zero.
        return (sign > 0)
            ? (int)value
            : -(int)value;
    }

    /// <summary>
    /// Parses an integer value according to HTML 5 rules and clips it to the specified range.
    /// Returns the min specified value in case of an error.
    /// </summary>
    static int parseIntegerInRange(String s, int min, int max)
    {
        // Note that ParseInteger correctly works with null and empty strings.
        int value = parseInteger(s);
        if (value < min)
        {
            // Parsing errors are covered by this branch.
            return min;
        }
        if (value > max)
        {
            return max;
        }
        return value;
    }

    static boolean isParagraphHasOnlyFloatingShapes(Paragraph para)
    {
        boolean hasAtLeastOneFloatingShape = false;
        for (Node node = para.getFirstChild(); node != null; node = node.getNextNonAnnotationSibling())
        {
            if (!(node instanceof ShapeBase))
                return false;

            ShapeBase shapeBase = (ShapeBase)node;
            if (shapeBase.getWrapType() != WrapType.NONE)
                return false;

            hasAtLeastOneFloatingShape = true;
        }

        return hasAtLeastOneFloatingShape;
    }

    static boolean isParagraphHasOnlyPageOrColumnBreaks(Paragraph para)
    {
        boolean hasAtLeastOnePageOrColumnBreak = false;
        for (Node node = para.getFirstChild(); node != null; node = node.getNextNonAnnotationSibling())
        {
            if (!(node instanceof Inline))
                return false;

            String text = node.getText();
            for (int i = 0; i < text.length(); i++)
            {
                if ((text.charAt(i) != ControlChar.PAGE_BREAK_CHAR) && (text.charAt(i) != ControlChar.COLUMN_BREAK_CHAR))
                    return false;

                hasAtLeastOnePageOrColumnBreak = true;
            }
        }

        return hasAtLeastOnePageOrColumnBreak;
    }

    static boolean isParagraphHasOnlyEmptyRuns(Paragraph para)
    {
        for (Node node = para.getFirstChild(); node != null; node = node.getNextSibling())
        {
            if (node.getNodeType() != NodeType.RUN)
                return false;

            Run run = (Run)node;
            if (StringUtil.hasChars(run.getText()))
                return false;

            if (run.getRunPr().getDirectAttr(FontAttr.RUBY) != null)
                return false;
        }

        return true;
    }

    /// <summary>
    /// Sometime MS Word doesn't export frames.
    /// This functions determines if paragraph will have representable frame.
    /// </summary>
    static boolean isParagraphHasRepresentableFrame(Paragraph para, ParaPr expandedParaPr)
    {
        // WORDSNET-17638 Ignore frame if paragraph is in cell.
        // It correctly takes markup nodes like SDT and SmartTag into account.
        if (para.isInCell())
        {
            return false;
        }

        // WORDSNET-8922, 10578 - We should ignore zero-width and zero-height frames if width and height
        // were explicitly specified.
        if ((expandedParaPr.hasFrameWidth() && (expandedParaPr.getFrameWidth() == 0)) ||
            (expandedParaPr.hasFrameHeight() && (expandedParaPr.getFrameHeight() == 0)))
        {
            return false;
        }

        return expandedParaPr.hasFrameAttributes();
    }

    /// <summary>
    /// Determines if paragraph mark is used to break the end of a paragraph for display.
    /// </summary>
    /// <param name="paragraph">Inspected paragraph.</param>
    /// <param name="expandedParaPr">Extended properties of inspected paragraph.</param>
    /// <param name="nextParagraph">Paragraph next to inspected paragraph.</param>
    /// <param name="expandedNextParaPr">Extended properties of paragraph next to inspected paragraph.</param>
    static boolean isParagraphBreakEndsParagraph(
        Paragraph paragraph,
        ParaPr expandedParaPr,
        Paragraph nextParagraph,
        ParaPr expandedNextParaPr)
    {
        if ((paragraph == null) || (nextParagraph == null))
            return true;

        // 1. Currently we export documents as if Show/Hidden is turned off in MS Word editor.
        //    FontAttr.SpecialHidden makes difference only when Show/Hidden is turned on, so we can ignore it here for now.
        // 2. FontAttr.Hidden doesn't have effect when paragraph is in frame.
        return !paragraph.getParagraphBreakFont().getHidden() ||
               isParagraphHasRepresentableFrame(paragraph, expandedParaPr) ||
               isParagraphHasRepresentableFrame(nextParagraph, expandedNextParaPr);
    }

    static boolean isParagraphOrHeadingElement(String elementName)
    {
        return ("p".equals(elementName)) || isHeadingElement(elementName);
    }

    static boolean isHeadingElement(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"h1"*/29:
            case /*"h2"*/30:
            case /*"h3"*/31:
            case /*"h4"*/32:
            case /*"h5"*/33:
            case /*"h6"*/34:
                return true;
            default:
                return false;
        }
    }

    /// <summary>
    /// Returns string representation of list level's number in HTML.
    /// </summary>
    static String getListLevelNumberString(int listLevelNumber)
    {
        return "%" + FormatterPal.intToStr(listLevelNumber);
    }

    /// <summary>
    /// Converts HTML the frame text wrapping to WrapType enumeration.
    /// </summary>
    static /*WrapType*/int parseFrameWrapType(String wraptype)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(wraptype)))
        {
            case /*"around"*/35:
                return WrapType.SQUARE;
            case /*"auto"*/36:
                return WrapType.INLINE;
            case /*"no-wrap-beside"*/37:
                return WrapType.TOP_BOTTOM;
            default:
                return WrapType.NONE;
        }
    }

    /// <summary>
    /// Converts HTML relative vertical position of the frame to RelativeVerticalPosition enumeration.
    /// </summary>
    static boolean parseFrameRelativeVerticalPosition(
        String position,
        /*out*/RefInt frameRelativeVerticalPosition)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(position)))
        {
            case /*"page"*/19:
                frameRelativeVerticalPosition.set(RelativeVerticalPosition.PAGE);
                return true;
            case /*"paragraph"*/26:
                frameRelativeVerticalPosition.set(RelativeVerticalPosition.PARAGRAPH);
                return true;
            default:
                frameRelativeVerticalPosition.set(RelativeVerticalPosition.MARGIN);
                return false;
        }
    }

    /// <summary>
    /// Converts HTML relative horizontal position of the frame to RelativeHorizontalPosition enumeration.
    /// </summary>
    static /*RelativeHorizontalPosition*/int parseFrameRelativeHorizontalPosition(String position)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(position)))
        {
            case /*"page"*/19:
                return RelativeHorizontalPosition.PAGE;
            case /*"column"*/20:
                return RelativeHorizontalPosition.COLUMN;
            default:
                return RelativeHorizontalPosition.MARGIN;
        }
    }

    /// <summary>
    /// Converts HTML height rule of the frame to HeightRule enumeration.
    /// </summary>
    static /*HeightRule*/int parseFrameHeightRule(String heightRule)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(heightRule)))
        {
            case /*"at-least"*/38:
                return HeightRule.AT_LEAST;
            case /*"exactly"*/39:
                return HeightRule.EXACTLY;
            default:
                return HeightRule.AUTO;
        }
    }

    /// <summary>
    /// Converts HTML horizontal alignment of the frame to HorizontalAlignment enumeration.
    /// </summary>
    static /*HorizontalAlignment*/int parseFrameHorizontalAligment(String aligment)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(aligment)))
        {
            case /*"center"*/1:
                return HorizontalAlignment.CENTER;
            case /*"inside"*/40:
                return HorizontalAlignment.INSIDE;
            case /*"outside"*/41:
                return HorizontalAlignment.OUTSIDE;
            case /*"right"*/2:
                return HorizontalAlignment.RIGHT;
            default:
                return HorizontalAlignment.DEFAULT;
        }
    }

    /// <summary>
    /// Converts HTML vertical alignment of the frame to VerticalAlignment enumeration.
    /// </summary>
    static /*VerticalAlignment*/int parseFrameVerticalAlignment(String aligment)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(aligment)))
        {
            case /*"center"*/1:
                return VerticalAlignment.CENTER;
            case /*"bottom"*/42:
                return VerticalAlignment.BOTTOM;
            case /*"top"*/43:
                return VerticalAlignment.TOP;
            case /*"inside"*/40:
                return VerticalAlignment.INSIDE;
            case /*"outside"*/41:
                return VerticalAlignment.OUTSIDE;
            default:
                return VerticalAlignment.DEFAULT;
        }
    }

    /// <summary>
    /// Removes Word control characters from a string (all characters with codes U+0001..U+001F but tab U+0009
    /// and line feed U+000A, which are processed somewhere else) and trims whitespace from a string.
    /// White space can be leading (left), in the middle and trailing.
    /// Middle and trailing whitespace is processed in the same way - it is collapsed into one space character.
    /// Leading whitespace processing has two options. It can either trim it completely or leave one space.
    /// </summary>
    /// <param name="text">Text for processing.</param>
    /// <param name="isRemoveControlChars">Whether to remove control chars.</param>
    /// <param name="isRemoveWhitespaces">Whether to remove whitespace.</param>
    /// <param name="isFullLeftTrim">Whether to completely trim leading whitespace or leave one space character.</param>
    private static String removeControlCharsAndWhitespaces(
        String text,
        boolean isRemoveControlChars,
        boolean isRemoveWhitespaces,
        boolean isFullLeftTrim)
    {
        // A flag indicating whether the current whitespace run is the leading whitespace run in the source text.
        boolean isFirstWhitespaceRun = isFullLeftTrim;
        // A flag indicating whether the current whitespace character is the first character of the current whitespace run.
        boolean isFirstWhitespaceChar = true;

        // Performance optimization. Delay instantiation of modified resulting string until we meet a character that
        // needs to be modified (removed or replaced).
        StringBuilder modifiedText = null;

        final char SPACE_CHARACTER = '\u0020';

        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);

            // A flag indicating whether to output this character to the resulting string.
            boolean writeCharacter = true;
            // A flag indicating whether to replace this character by the space character. The replacement will only take
            // place if the character is to be output to the resulting string (is not removed completely).
            boolean writeSpaceInstead = false;

            // Check whether the current character is a control or a whitespace character. It is important to check
            // for control characters first, because the ranges of control and whitespace characters intersect and control
            // characters take precedence. For example, U+000C is both considered a control character and a whitespace
            // character. However, this algorithm processes it as a control character and removes it.
            boolean isControlChar = isRemoveControlChars && isControlCharacter(c);
            boolean isWhitespace = !isControlChar && isRemoveWhitespaces && isWhitespace(c);

            // Decide what to do with the current character: remove, replace by a space character, or copy unchanged.
            // Set flags accordingly.
            if (isWhitespace)
            {
                if (!isFirstWhitespaceRun && isFirstWhitespaceChar)
                {
                    // Collapse whitespace characters to single space. Replace the current character by the space character.
                    // If the current character is already the space character, it is instead copied unchanged.
                    writeSpaceInstead = c != SPACE_CHARACTER;
                }
                else
                {
                    // Remove all collapsed whitespace characters except the first one.
                    writeCharacter = false;
                }

                // We've just processed the first character of this whitespace run.
                isFirstWhitespaceChar = false;
            }
            else if (isControlChar)
            {
                // Remove all control characters. Keep whitespace run flags unchanged.
                writeCharacter = false;
            }
            else
            {
                // We've met a non-whitespace character. Reset whitespace run flags.
                isFirstWhitespaceRun = false;
                isFirstWhitespaceChar = true;
            }

            // If the current character is modified (is not copied unchanged), we need to create a working copy
            // of the source text that can be altered.
            boolean charIsModified = !writeCharacter || writeSpaceInstead;
            if (charIsModified && (modifiedText == null))
            {
                modifiedText = new StringBuilder(text.length());
                // Copy unmodified characters from the source string up to the current character.
                modifiedText.append((text), (0), (0) + (i));
            }

            // If the current character is not removed, output it to the resulting string.
            if (writeCharacter)
            {
                if (writeSpaceInstead)
                {
                    modifiedText.append(SPACE_CHARACTER);
                }
                else if (modifiedText != null)
                {
                    modifiedText.append(c);
                }
                else
                {
                    // Do nothing. So far we hasn't meet any characters that need to be removed or replaced.
                }
            }
        }

        // Performance optimization. If no modifications were made, return the source string unchanged.
        return (modifiedText != null)
            ? modifiedText.toString()
            : text;
    }

    private static boolean isControlCharacter(char c)
    {
        return (c >= '\u0001') && (c <= '\u001F') &&
            (c != '\u0009') &&
            (c != '\n');
    }

    /// <summary>
    /// Name of the custom document property that MS Word uses to store the "target" attribute value
    /// of the BASE element (the default target for hyperlinks).
    /// </summary>
    static final String BASE_TARGET_DOCUMENT_PROPERTY = "Base Target";

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"left",
		"center",
		"right",
		"decimal",
		"bar",
		"list",
		"clear",
		"none",
		"dots",
		"dashes",
		"line",
		"heavy",
		"middledot",
		"inline",
		"topbottom",
		"square",
		"tight",
		"through",
		"margin",
		"page",
		"column",
		"character",
		"leftmargin",
		"rightmargin",
		"insidemargin",
		"outsidemargin",
		"paragraph",
		"topmargin",
		"bottommargin",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"around",
		"auto",
		"no-wrap-beside",
		"at-least",
		"exactly",
		"inside",
		"outside",
		"bottom",
		"top"
	);

}

