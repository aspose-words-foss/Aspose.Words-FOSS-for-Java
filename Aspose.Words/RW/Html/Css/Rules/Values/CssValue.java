// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.CssEscape;
import com.aspose.StringUtil;
import com.aspose.DrColor;
import com.aspose.DrKnownColors;
import com.aspose.Debug;


/// <summary>
/// Base CSS value class. 
/// This class is immutable. All derived classes should be immutable also. 
/// </summary>
abstract class CssValue
{
    /// <summary>
    /// Creates a new CSS value.
    /// </summary>
    protected CssValue(/*CssValueType*/int valueType, Object value)
    {
        mValueType = valueType;
        mValue = value;
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Standard comparisons.
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (!(obj instanceof CssValue))
            return false;

        // Invoke the class-specific implementation.
        return equals((CssValue)obj);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int result = (mValue != null) ? mValue.hashCode() : 0;
            //Java-changed: casting enum to int is analog of GetHashCode().
            result = (result * 397) ^ (int)mValueType;
            return result;
        }
    }

    static CssValue createFontFamilyValue(String fontName)
    {
        // If all characters of a font name are allowed in CSS identifiers, the font name can be exported without quotes
        // (as an identifier).
        if (CssEscape.isValidIdentifier(fontName))
            return new CssIdentifierValue(fontName);
        return new CssStringValue(fontName);
    }

    /// <summary>
    /// Parses a length value in pixels according to HTML 5 rules.
    /// </summary>
    /// <param name="s">The value to be parsed.</param>
    /// <returns>
    /// The length value, if the parsing was successful;
    /// null otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from here: https://html.spec.whatwg.org/multipage/rendering.html#maps-to-the-pixel-length-property
    /// </remarks>
    static CssValue parseLegacyPixelLength(String s)
    {
        int value = HtmlUtil.parseNonNegativeInteger(s);
        return (value >= 0)
            ? new CssLengthValue(value, CssUnit.PX)
            : null;
    }

    /// <summary>
    /// Parses a dimension value (either a pixel length or a percentage) according to the HTML 5 rules.
    /// </summary>
    /// <param name="s">The value to be parsed.</param>
    /// <returns>
    /// The parsed value, if parsing was successful;
    /// <c>null</c> otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from https://html.spec.whatwg.org/multipage/rendering.html#maps-to-the-dimension-property
    /// </remarks> 
    static CssValue parseLegacyDimension(String s)
    {
        if (s == null)
            return null;

        // 1. Let input be the string being parsed.
        // 2. Let position be a pointer into input, initially pointing at the start of the string.
        int i = 0;

        // 3. Skip whitespace.
        while ((i < s.length()) && HtmlUtil.isWhitespace(s.charAt(i)))
        {
            ++i;
        }

        // 4. If position is past the end of input, return an error.
        if (i >= s.length())
        {
            return null;
        }

        // 5. If the character indicated by position is a "+" (U+002B) character, advance position to the next character.
        if (s.charAt(i) == '\u002B')
        {
            ++i;
        }

        // 6. Collect a sequence of characters that are "0" (U+0030) characters, and discard them.
        while ((i < s.length()) && (s.charAt(i) == '\u0030'))
        {
            ++i;
        }

        // 7. If position is past the end of input, return an error.
        if (i >= s.length())
        {
            return null;
        }

        // 8. If the character indicated by position is not one of "1" (U+0031) to "9" (U+0039), then return an error.
        if (!StringUtil.isDigit(s.charAt(i)))
        {
            return null;
        }

        // 9. Collect a sequence of characters in the range ASCII digits, and interpret the resulting sequence 
        // as a base-ten integer. Let value be that number.
        double value = 0.0;
        while ((i < s.length()) && StringUtil.isDigit(s.charAt(i)))
        {
            value *= 10.0;
            int digit = s.charAt(i) - '0';
            value += digit;
            ++i;
        }

        // 10. If position is past the end of input, return value as a length.
        if (i >= s.length())
        {
            return new CssLengthValue(value, CssUnit.PX);
        }

        // 11. If the character indicated by position is a "." (U+002E) character:
        if (s.charAt(i) == '\u002E')
        {
            // 11.1. Advance position to the next character.
            ++i;

            // 11.2. If position is past the end of input, or if the character indicated by position 
            // is not one of ASCII digits, then return value as a length.
            if ((i >= s.length()) || !StringUtil.isDigit(s.charAt(i)))
            {
                return new CssLengthValue(value, CssUnit.PX);
            }

            // 11.3. Let divisor have the value 1.
            // Note that the algorithm is slightly modified to reduce the number of divisions.
            // When the number of divisions is high, the result accuracy degrades considerably.
            double divisor = 1.0;
            double fraction = 0.0;

            do
            {
                // 11.4. Fraction loop: Multiply divisor by ten.
                divisor *= 10.0;
                fraction *= 10.0;

                // 11.5. Add the value of the character indicated by position, interpreted as a base-ten digit (0..9)
                // and divided by divisor, to value.
                int digit = s.charAt(i) - '0';
                fraction += digit;

                // 11.6. Advance position to the next character.
                ++i;

                // 11.7. If position is past the end of input, then return value as a length.
                if (i >= s.length())
                {
                    value += fraction / divisor;
                    return new CssLengthValue(value, CssUnit.PX);
                }

                // 11.8. If the character indicated by position is one of ASCII digits, 
                //return to the step labeled fraction loop in these substeps.
            } while (StringUtil.isDigit(s.charAt(i)));

            value += fraction / divisor;
        }

        // 12. If position is past the end of input, return value as a length.
        if (i >= s.length())
        {
            return new CssLengthValue(value, CssUnit.PX);
        }

        // 13. If the character indicated by position is a "%" (U+0025) character, return value as a percentage.
        if (s.charAt(i) == '\u0025')
        {
            return new CssPercentageValue(value);
        }

        // 14. Return value as a length.
        return new CssLengthValue(value, CssUnit.PX);
    }

    /// <summary>
    /// Parses a color value according to HTML 5 rules for legacy colors.
    /// </summary>
    /// <param name="s">The color value to be parsed.</param>
    /// <returns>
    /// The parsed color value, if parsing was sucessful;
    /// <c>null</c> otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from here: https://html.spec.whatwg.org/multipage/common-microsyntaxes.html#rules-for-parsing-a-legacy-colour-value
    /// </remarks>
    static CssValue parseLegacyColor(String s)
    {
        // 1. Let input be the string being parsed.
        // 2. If input is the empty string, then return an error.
        if (!StringUtil.hasChars(s))
            return null;

        // 3. Strip leading and trailing whitespace from input.
        int begin = 0;
        while ((begin < s.length()) && HtmlUtil.isWhitespace(s.charAt(begin)))
        {
            ++begin;
        }
        int end = s.length() - 1;
        while ((end >= begin) && HtmlUtil.isWhitespace(s.charAt(end)))
        {
            --end;
        }
        int substringLength = end - begin + 1;
        if (substringLength < s.length())
        {
            s = (substringLength > 0)
                ? s.substring((begin), (begin) + (substringLength))
                : "";
        }

        // 4. If input is an ASCII case-insensitive match for the string "transparent", then return an error.
        if (StringUtil.equalsIgnoreCase(s, "transparent"))
        {
            return null;
        }

        // 5. If input is an ASCII case-insensitive match for one of the keywords listed in the SVG color keywords section
        // of the CSS3 Color specification, then return the simple color corresponding to that keyword.
        DrColor knownColor = DrKnownColors.fromName(s);
        if (!DrColor.equals(knownColor, DrColor.EMPTY))
        {
            return new CssIdentifierValue(StringUtil.asciiLowerCase(s));
        }

        // 6. If input is four characters long, and the first character in input is a "#" (U+0023) character, 
        // and the last three characters of input are all in the range ASCII digits, U+0041 LATIN CAPITAL LETTER A 
        // to U+0046 LATIN CAPITAL LETTER F, and U+0061 LATIN SMALL LETTER A to U+0066 LATIN SMALL LETTER F, 
        // then run these substeps:
        boolean isHexColorCode =
            (s.length() == 4) &&
            StringUtil.isHexDigit(s.charAt(1)) &&
            StringUtil.isHexDigit(s.charAt(2)) &&
            StringUtil.isHexDigit(s.charAt(3));
        if (isHexColorCode)
        {
            // 1. Let result be a simple color.

            // 2. Interpret the second character of input as a hexadecimal digit.
            // Let the red component of result be the resulting number multiplied by 17.
            int r = StringUtil.hexCharToDigit(s.charAt(1)) * 17;

            // 3. Interpret the third character of input as a hexadecimal digit. 
            // Let the green component of result be the resulting number multiplied by 17.
            int g = StringUtil.hexCharToDigit(s.charAt(2)) * 17;

            // 4. Interpret the fourth character of input as a hexadecimal digit. 
            // let the blue component of result be the resulting number multiplied by 17.
            int b = StringUtil.hexCharToDigit(s.charAt(3)) * 17;

            // 5. Return result.
            return CssHashValue.fromColor(r, g, b);
        }

        // 7. Replace any characters in input that have a Unicode code point greater than U+FFFF 
        // (i.e. any characters that are not in the basic multilingual plane) with the two-character string "00".

        // Characters from supplementary Unicode planes are not supported at the moment.
        // Surrogate characters will be replaced with zero characters at step 10.

        // 8. If input is longer than 128 characters, truncate input, leaving only the first 128 characters.
        StringBuilder sb = new StringBuilder(s);
        if (sb.length() > 128)
        {
            sb.setLength(128);
        }

        // 9. If the first character in input is a "#" (U+0023) character, remove it.
        if ((sb.length() > 0) && (sb.charAt(0) == '\u0023'))
        {
            sb.delete((0), (0) + (1));
        }

        // 10. Replace any character in input that is not in the range ASCII digits, 
        // U+0041 LATIN CAPITAL LETTER A to U+0046 LATIN CAPITAL LETTER F, 
        // and U+0061 LATIN SMALL LETTER A to U+0066 LATIN SMALL LETTER F with the character "0" (U+0030).
        for (int i = 0; i < sb.length(); i++)
        {
            if (!StringUtil.isHexDigit(sb.charAt(i)))
            {
                sb.setCharAt(i, '\u0030');
            }
        }

        // 11. While input's length is zero or not a multiple of three, append a "0" (U+0030) character to input.
        while ((sb.length() == 0) || ((sb.length() % 3) != 0))
        {
            sb.append('\u0030');
        }

        // 12. Split input into three strings of equal length, to obtain three components. Let length be the length 
        // of those components (one third the length of input).
        int length = sb.length() / 3;

        // 13. If length is greater than 8, then remove the leading length-8 characters in each component, 
        // and let length be 8.
        if (length > 8)
        {
            length = 8;
        }

        // 14. While length is greater than two and the first character in each component is a "0" (U+0030) character,
        // remove that character and reduce length by one.
        while (length > 2)
        {
            boolean firstCharacterIsZero = true;
            for (int i = 0; i < 3; i++)
            {
                int offset = sb.length() / 3 * (i + 1) - length;
                if (sb.charAt(offset) != '\u0030')
                {
                    firstCharacterIsZero = false;
                    break;
                }
            }
            if (firstCharacterIsZero)
            {
                --length;
            }
            else
            {
                break;
            }
        }

        // 15. If length is still greater than two, truncate each component, leaving only the first two characters in each.
        // The value 0xFF will be shifted left to the alpha component.
        int argb = 0xFF;
        for (int i = 0; i < 3; i++)
        {
            // 16. Let result be a simple color.
            // For red, green, and blue component:
            // 17-19. Interpret the component as a hexadecimal number. 
            // Let the corresponding component of result be the resulting number.
            int offset = sb.length() / 3 * (i + 1) - length;
            if (length == 1)
            {
                // The component consists only of a low-order digit.
                argb <<= 8;
                argb |= StringUtil.hexCharToDigit(sb.charAt(offset));
            }
            else
            {
                // Process only two characters in each component (one hexadecimal byte).
                argb <<= 4;
                argb |= StringUtil.hexCharToDigit(sb.charAt(offset));
                argb <<= 4;
                argb |= StringUtil.hexCharToDigit(sb.charAt(offset + 1));
            }
        }

        // 20. Return result.
        return CssHashValue.fromColor(argb);
    }

    /// <summary>
    /// Parses a font size value according to the HTML 5 rules for legacy font sizes.
    /// </summary>
    /// <param name="s">The font size value to be parsed.</param>
    /// <returns>
    /// The parsed font size value, if parsing was successful;
    /// <c>null</c> otherwise.
    /// </returns>
    /// <remarks>
    /// The algorithm is taken from https://html.spec.whatwg.org/multipage/rendering.html#rules-for-parsing-a-legacy-font-size
    /// </remarks>
    static CssValue parseLegacyFontSize(String s)
    {
        if (s == null)
            return null;

        // 1. Let input be the attribute's value.
        // 2. Let position be a pointer into input, initially pointing at the start of the string.
        int i = 0;

        // 3. Skip whitespace.
        while ((i < s.length()) && HtmlUtil.isWhitespace(s.charAt(i)))
        {
            ++i;
        }

        // 4. If position is past the end of input, there is no presentational hint. Abort these steps.
        if (i >= s.length())
        {
            return null;
        }

        // 5. If the character at position is a "+" (U+002B) character, then let mode be relative-plus, and advance position
        // to the next character. Otherwise, if the character at position is a "-" (U+002D) character, then let mode
        // be relative-minus, and advance position to the next character. Otherwise, let mode be absolute.
        int mode = 0;
        switch (s.charAt(i))
        {
            case '\u002B':
                mode = 1;
                ++i;
                break;
            case '\u002D':
                mode = -1;
                ++i;
                break;
            default:
                break;
        }

        // 6. Collect a sequence of characters in the range ASCII digits, and let the resulting sequence be digits.
        // 8. Interpret digits as a base-ten integer. Let value be the resulting number.
        int oldPosition = i;
        int value = 0;
        while ((i < s.length()) && StringUtil.isDigit(s.charAt(i)))
        {
            value *= 10;
            int digit = s.charAt(i) - '0';
            value += digit;
            ++i;

            // There is no need to read all digits, if the value is already known to be greater than the maximum.
            boolean overflow = value > 10;
            if (overflow)
            {
                break;
            }
        }

        // 7. If digits is the empty string, there is no presentational hint. Abort these steps.
        if (i == oldPosition)
        {
            return null;
        }

        // 9. If mode is relative-plus, then increment value by 3. 
        // If mode is relative-minus, then let value be the result of subtracting value from 3.
        if (mode > 0)
        {
            value += 3;
        }
        else if (mode < 0)
        {
            value = 3 - value;
        }

        // 10. If value is greater than 7, let it be 7.
        if (value > 7)
        {
            value = 7;
        }

        // 11. If value is less than 1, let it be 1.
        if (value < 1)
        {
            value = 1;
        }

        // 12. Set 'font-size' to the keyword corresponding to the value of value according to the following table:
        switch (value)
        {
            case 1:
                return XSmall;
            case 2:
                return Small;
            case 3:
                return Medium;
            case 4:
                return Large;
            case 5:
                return XLarge;
            case 6:
                return XxLarge;
            case 7:
                return XxxLarge;
            default:
                Debug.assertTrue(false);
                return null;
        }
    }

    /// <summary>
    /// Determines whether the specified CSS value is equal to the current value.
    /// </summary>
    boolean equals(CssValue other)
    {
        // Standard reference comparisons.
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;
        if (getValueType() != other.getValueType())
            return false;

        return doEquals(other);
    }

    /// <summary>
    /// Determines whether the specified CSS value is equal to any specified value.
    /// </summary>
    boolean equalsAny(/*params*/ CssValue... other)
    {
        for (CssValue otherValue : other)
        {
            if (equals(otherValue))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Gets a CSS representation of the property value.
    /// </summary>
    String toCss()
    {
        StringBuilder sb = new StringBuilder();
        toCss(sb);
        return sb.toString();
    }

    /// <summary>
    /// Outputs a CSS representation of the property value to StringBuilder.
    /// </summary>
    abstract void toCss(StringBuilder sb);

    /// <summary>
    /// Returns a value indicating whether this CSS value is a valid length. Valid lengths are: number + unit, zero, and
    /// (in quirks mode) numbers without units.
    /// </summary>
    boolean isLength(boolean allowUnitlessNumbers)
    {
        return (getValueType() == CssValueType.LENGTH) ||
            (allowUnitlessNumbers && (getValueType() == CssValueType.NUMBER)) ||
            equals((CssValue)Zero); // Cast is needed for autoporting.
    }

    /// <summary>
    /// Converts this value to length, if possible.
    /// </summary>
    /// <param name="allowUnitlessNumbers">
    /// A flag indicating whether unitless numbers are accepted. Such values are treated as lengths in pixels. That is,
    /// "123" is converted to "123px".
    /// </param>
    /// <remarks>
    /// This method helps to process different representations of lengths allowed in CSS: normal lengths, unitless lengths,
    /// zero.
    /// Note that the unitless zero value is always accepted and is converted to zero points. That is, "0" is always
    /// converted to "0pt".
    /// </remarks>
    /// <returns>
    /// The converted length value or <c>null</c> if conversion is not possible.
    /// </returns>
    CssLengthValue toLength(boolean allowUnitlessNumbers)
    {
        if (getValueType() == CssValueType.LENGTH)
        {
            return (CssLengthValue)this;
        }

        if (getValueType() == CssValueType.NUMBER)
        {
            CssNumberValue numberValue = (CssNumberValue)this;
            if (numberValue.getDoubleValue() == 0)
            {
                return ZeroPt;
            }

            if (allowUnitlessNumbers)
            {
                return new CssLengthValue(numberValue.getDoubleValue(), CssUnit.PX);
            }
        }

        return null;
    }

    /// <summary>
    /// Tries to parse the CSS value as color.
    /// </summary>
    /// <returns>
    /// The parsed color value. If the CSS value doesn't represent a valid color, <c>null</c> value is returned.
    /// </returns>
    /*virtual*/ DrColor parseAsColor()
    {
        // Most types of CSS values cannot represent a color value.
        return null;
    }

    /// <summary>
    /// Determines whether the specified CSS value is equal to the current value.
    /// Override for class-specific implementation. Reference and null checks aren't needed.
    /// </summary>
    protected /*virtual*/ boolean doEquals(CssValue other)
    {
        return mValue.equals(other.mValue);
    }

    /// <summary>
    /// Value object.
    /// </summary>
    Object getValue() { return mValue; }

    /// <summary>
    /// Gets the value as a number. If the value cannot be converted into a number, returns <see cref="double.MinValue"/>.
    /// </summary>
    /// <remarks>
    /// This getter is expected to be overridden by subclasses that implement numeric values.
    /// </remarks>
    /*virtual*/ double getDoubleValue() { return -Double.MAX_VALUE; }

    /// <summary>
    /// Value type.
    /// </summary>
    /*CssValueType*/int getValueType() { return mValueType; }

    static CssCommaValue Comma = new CssCommaValue();
    // Represents a U+002F SOLIDUS (/) value.
    static CssSolidusValue Solidus = new CssSolidusValue();

    static CssIdentifierValue Transparent = new CssIdentifierValue("transparent");
    static CssIdentifierValue None = new CssIdentifierValue("none");
    static CssIdentifierValue Nil = new CssIdentifierValue("nil");
    /// <summary>
    /// The 'inherit' value can be used to enforce inheritance of values, and it can also be used on properties that 
    /// are not normally inherited.
    /// </summary>
    static CssIdentifierValue Inherit = new CssIdentifierValue("inherit");
    /// <summary>
    /// The initial CSS keyword applies the initial value of a property to an element. 
    /// It is allowed on every CSS property and causes the element for which it is specified 
    /// to use the initial value of the property.
    /// </summary>
    static CssValue Initial = new CssIdentifierValue("initial");

    static CssNumberValue Zero = new CssNumberValue(0.0);
    static CssLengthValue ZeroPt = new CssLengthValue(0.0, CssUnit.PT);
    static CssPercentageValue ZeroPercentage = new CssPercentageValue(0.0);

    static CssLengthValue OnePx = new CssLengthValue(1.0, CssUnit.PX);

    static CssIdentifierValue Ltr = new CssIdentifierValue("ltr");
    static CssIdentifierValue Rtl = new CssIdentifierValue("rtl");
    static CssIdentifierValue Auto = new CssIdentifierValue("auto");
    static CssIdentifierValue Avoid = new CssIdentifierValue("avoid");
    static CssIdentifierValue Capitalize = new CssIdentifierValue("capitalize");
    static CssIdentifierValue LowerCase = new CssIdentifierValue("lowercase");
    static CssIdentifierValue UpperCase = new CssIdentifierValue("uppercase");
    static CssIdentifierValue Normal = new CssIdentifierValue("normal");
    static CssIdentifierValue Underline = new CssIdentifierValue("underline");
    static CssIdentifierValue Overline = new CssIdentifierValue("overline");
    static CssIdentifierValue LineThrough = new CssIdentifierValue("line-through");
    static CssIdentifierValue Blink = new CssIdentifierValue("blink");
    static CssIdentifierValue SmallCaps = new CssIdentifierValue("small-caps");
    static CssIdentifierValue Italic = new CssIdentifierValue("italic");
    static CssIdentifierValue Oblique = new CssIdentifierValue("oblique");
    static CssIdentifierValue Bold = new CssIdentifierValue("bold");
    static CssIdentifierValue Bolder = new CssIdentifierValue("bolder");
    static CssIdentifierValue Lighter = new CssIdentifierValue("lighter");
    static CssIdentifierValue Char = new CssIdentifierValue("char");
    static CssIdentifierValue Always = new CssIdentifierValue("always");
    static CssIdentifierValue Top = new CssIdentifierValue("top");
    static CssIdentifierValue Bottom = new CssIdentifierValue("bottom");
    static CssIdentifierValue Middle = new CssIdentifierValue("middle");
    static CssIdentifierValue Start = new CssIdentifierValue("start");
    static CssIdentifierValue End = new CssIdentifierValue("end");
    static CssIdentifierValue Baseline = new CssIdentifierValue("baseline");
    static CssIdentifierValue Sub = new CssIdentifierValue("sub");
    static CssIdentifierValue Super = new CssIdentifierValue("super");
    static CssIdentifierValue TextBottom = new CssIdentifierValue("text-bottom");
    static CssIdentifierValue Repeat = new CssIdentifierValue("repeat");
    static CssIdentifierValue RepeatX = new CssIdentifierValue("repeat-x");
    static CssIdentifierValue RepeatY = new CssIdentifierValue("repeat-y");
    static CssIdentifierValue NoRepeat = new CssIdentifierValue("no-repeat");
    static CssIdentifierValue Scroll = new CssIdentifierValue("scroll");
    static CssIdentifierValue Fixed = new CssIdentifierValue("fixed");
    static CssIdentifierValue Embed = new CssIdentifierValue("embed");
    static CssIdentifierValue BidiOverride = new CssIdentifierValue("bidi-override");
    // alignment
    static CssIdentifierValue Left = new CssIdentifierValue("left");
    static CssIdentifierValue Right = new CssIdentifierValue("right");
    static CssIdentifierValue Center = new CssIdentifierValue("center");
    static CssIdentifierValue Justify = new CssIdentifierValue("justify");
    static CssIdentifierValue DistributeLetter = new CssIdentifierValue("distribute-letter");
    static CssIdentifierValue DistributeSpace = new CssIdentifierValue("distribute-space");
    static CssIdentifierValue LineEdge = new CssIdentifierValue("line-edge");
    // custom alignment
    // 'start' is treated as 'left' on left-to-right elements, and as 'right' on right-to-left.
    static CssIdentifierValue AwStart = new CssIdentifierValue("-aw-start");
    // This value behaves the same as 'inherit' except that an inherited '-aw-start' is interpreted against the parent's
    // 'direction' value and results in a computed value of either 'left' or 'right'.
    // See http://www.w3.org/TR/css-text-3/#text-align-property 
    static CssIdentifierValue AwMatchParent = new CssIdentifierValue("-aw-match-parent");
    // The following three custom alignment values are similar to the standard ones, except that they affect not only
    // inline-level but also block-level descendant elements. They represent values of the 'align' attribute in CSS.
    static CssIdentifierValue AwLeft = new CssIdentifierValue("-aw-left");
    static CssIdentifierValue AwRight = new CssIdentifierValue("-aw-right");
    static CssIdentifierValue AwCenter = new CssIdentifierValue("-aw-center");
    // display
    static CssIdentifierValue Inline = new CssIdentifierValue("inline");
    static CssIdentifierValue InlineBlock = new CssIdentifierValue("inline-block");
    static CssIdentifierValue Block = new CssIdentifierValue("block");
    static CssIdentifierValue ListItem = new CssIdentifierValue("list-item");

    static CssIdentifierValue RunIn = new CssIdentifierValue("run-in");
    static CssIdentifierValue InlineTable = new CssIdentifierValue("inline-table");
    static CssIdentifierValue Table = new CssIdentifierValue("table");
    static CssIdentifierValue TableRowGroup = new CssIdentifierValue("table-row-group");
    static CssIdentifierValue TableHeaderGroup = new CssIdentifierValue("table-header-group");
    static CssIdentifierValue TableFooterGroup = new CssIdentifierValue("table-footer-group");
    static CssIdentifierValue TableRow = new CssIdentifierValue("table-row");
    static CssIdentifierValue TableColumnGroup = new CssIdentifierValue("table-column-group");
    static CssIdentifierValue TableColumn = new CssIdentifierValue("table-column");
    static CssIdentifierValue TableCell = new CssIdentifierValue("table-cell");
    static CssIdentifierValue TableCaption = new CssIdentifierValue("table-caption");
    // visibility
    static CssIdentifierValue Visible = new CssIdentifierValue("visible");
    //
    static CssIdentifierValue Decimal = new CssIdentifierValue("decimal");
    static CssIdentifierValue DecimalLeadingZero = new CssIdentifierValue("decimal-leading-zero");
    static CssIdentifierValue LowerAlpha = new CssIdentifierValue("lower-alpha");
    static CssIdentifierValue UpperAlpha = new CssIdentifierValue("upper-alpha");
    static CssIdentifierValue LowerRoman = new CssIdentifierValue("lower-roman");
    static CssIdentifierValue UpperRoman = new CssIdentifierValue("upper-roman");
    static CssIdentifierValue LowerGreek = new CssIdentifierValue("lower-greek");
    static CssIdentifierValue LowerLatin = new CssIdentifierValue("lower-latin");
    static CssIdentifierValue UpperLatin = new CssIdentifierValue("upper-latin");
    static CssIdentifierValue Armenian = new CssIdentifierValue("armenian");
    static CssIdentifierValue Georgian = new CssIdentifierValue("georgian");
    static CssIdentifierValue ArabicIndic = new CssIdentifierValue("arabic-indic");
    static CssIdentifierValue Disc = new CssIdentifierValue("disc");
    static CssIdentifierValue Circle = new CssIdentifierValue("circle");
    static CssIdentifierValue Square = new CssIdentifierValue("square");
    static CssIdentifierValue Collapse = new CssIdentifierValue("collapse");
    static CssIdentifierValue Separate = new CssIdentifierValue("separate");
    static CssIdentifierValue Nowrap = new CssIdentifierValue("nowrap");
    static CssIdentifierValue PreWrap = new CssIdentifierValue("pre-wrap");
    static CssIdentifierValue PreLine = new CssIdentifierValue("pre-line");
    static CssIdentifierValue XxSmall = new CssIdentifierValue("xx-small");
    static CssIdentifierValue XSmall = new CssIdentifierValue("x-small");
    static CssIdentifierValue Small = new CssIdentifierValue("small");
    static CssIdentifierValue Large = new CssIdentifierValue("large");
    static CssIdentifierValue XLarge = new CssIdentifierValue("x-large");
    static CssIdentifierValue XxLarge = new CssIdentifierValue("xx-large");
    // The 'xxx-large' value is a non-CSS value used to indicate a font size 50% larger than 'xx-large'.
    // See https://html.spec.whatwg.org/multipage/rendering.html#rules-for-parsing-a-legacy-font-size
    // Maybe this property should be named something like "-aw-xxx-large", as it is non-standard.
    static CssIdentifierValue XxxLarge = new CssIdentifierValue("xxx-large");
    static CssIdentifierValue Both = new CssIdentifierValue("both");
    static CssIdentifierValue TextTop = new CssIdentifierValue("text-top");
    static CssIdentifierValue Pre = new CssIdentifierValue("pre");
    // Relative size
    static CssIdentifierValue Smaller = new CssIdentifierValue("smaller");
    static CssIdentifierValue Larger = new CssIdentifierValue("larger");

    static CssIdentifierValue LrTb = new CssIdentifierValue("lr-tb");
    static CssIdentifierValue RlTb = new CssIdentifierValue("rl-tb");
    static CssIdentifierValue TbRl = new CssIdentifierValue("tb-rl");
    static CssIdentifierValue BtRl = new CssIdentifierValue("bt-rl");
    static CssIdentifierValue TbLr = new CssIdentifierValue("tb-lr");
    static CssIdentifierValue BtLr = new CssIdentifierValue("bt-lr");
    static CssIdentifierValue HorizontalTb = new CssIdentifierValue("horizontal-tb");
    static CssIdentifierValue VerticalRl = new CssIdentifierValue("vertical-rl");
    static CssIdentifierValue VerticalLr = new CssIdentifierValue("vertical-lr");
    static CssIdentifierValue Medium = new CssIdentifierValue("medium");
    static CssIdentifierValue Thin = new CssIdentifierValue("thin");
    static CssIdentifierValue Thick = new CssIdentifierValue("thick");
    // border styles
    static CssIdentifierValue Hidden = new CssIdentifierValue("hidden");
    static CssIdentifierValue Dotted = new CssIdentifierValue("dotted");
    static CssIdentifierValue Dashed = new CssIdentifierValue("dashed");
    static CssIdentifierValue Solid = new CssIdentifierValue("solid");
    static CssIdentifierValue DoubleId = new CssIdentifierValue("double");//JAVA-changed name since (old) Double clashes with Double.MinValue in Java.
    static CssIdentifierValue Groove = new CssIdentifierValue("groove");
    static CssIdentifierValue Ridge = new CssIdentifierValue("ridge");
    static CssIdentifierValue Inset = new CssIdentifierValue("inset");
    static CssIdentifierValue Outset = new CssIdentifierValue("outset");
    // Custom border styles that are used to preserve MS Word-specific line styles during HTML round-trip.
    static CssIdentifierValue Single = new CssIdentifierValue("single");
    static CssIdentifierValue Hairline = new CssIdentifierValue("hairline");
    static CssIdentifierValue Dot = new CssIdentifierValue("dot");
    static CssIdentifierValue DashLargeGap = new CssIdentifierValue("dash-large-gap");
    static CssIdentifierValue DotDash = new CssIdentifierValue("dot-dash");
    static CssIdentifierValue DotDotDash = new CssIdentifierValue("dot-dot-dash");
    static CssIdentifierValue Triple = new CssIdentifierValue("triple");
    static CssIdentifierValue ThinThickSmallGap = new CssIdentifierValue("thin-thick-small-gap");
    static CssIdentifierValue ThickThinSmallGap = new CssIdentifierValue("thick-thin-small-gap");
    static CssIdentifierValue ThinThickThinSmallGap = new CssIdentifierValue("thin-thick-thin-small-gap");
    static CssIdentifierValue ThinThickMediumGap = new CssIdentifierValue("thin-thick-medium-gap");
    static CssIdentifierValue ThickThinMediumGap = new CssIdentifierValue("thick-thin-medium-gap");
    static CssIdentifierValue ThinThickThinMediumGap = new CssIdentifierValue("thin-thick-thin-medium-gap");
    static CssIdentifierValue ThinThickLargeGap = new CssIdentifierValue("thin-thick-large-gap");
    static CssIdentifierValue ThickThinLargeGap = new CssIdentifierValue("thick-thin-large-gap");
    static CssIdentifierValue ThinThickThinLargeGap = new CssIdentifierValue("thin-thick-thin-large-gap");
    static CssIdentifierValue Wave = new CssIdentifierValue("wave");
    static CssIdentifierValue DoubleWave = new CssIdentifierValue("double-wave");
    static CssIdentifierValue DashSmallGap = new CssIdentifierValue("dash-small-gap");
    static CssIdentifierValue DashDotStroker = new CssIdentifierValue("dash-dot-stroker");
    static CssIdentifierValue Emboss3D = new CssIdentifierValue("emboss3d");
    static CssIdentifierValue Engrave3D = new CssIdentifierValue("engrave3d");
    // font
    static CssIdentifierValue Caption = new CssIdentifierValue("caption");
    static CssIdentifierValue Icon = new CssIdentifierValue("icon");
    static CssIdentifierValue Menu = new CssIdentifierValue("menu");
    static CssIdentifierValue MessageBox = new CssIdentifierValue("message-box");
    static CssIdentifierValue SmallCaption = new CssIdentifierValue("small-caption");
    static CssIdentifierValue StatusBar = new CssIdentifierValue("status-bar");
    // 'box-sizing' property values (see http://www.w3.org/TR/css3-ui/#box-sizing)
    static CssIdentifierValue ContentBox = new CssIdentifierValue("content-box");
    static CssIdentifierValue PaddingBox = new CssIdentifierValue("padding-box");
    static CssIdentifierValue BorderBox = new CssIdentifierValue("border-box");
    // font-family
    static CssIdentifierValue Monospace = new CssIdentifierValue("monospace");
    // Lists
    static CssIdentifierValue Inside = new CssIdentifierValue("inside");
    static CssIdentifierValue Outside = new CssIdentifierValue("outside");
    // Images
    static CssIdentifierValue Absolute = new CssIdentifierValue("absolute");
    // Quotes (used in the 'content' property).
    static CssIdentifierValue OpenQuote = new CssIdentifierValue("open-quote");
    static CssIdentifierValue CloseQuote = new CssIdentifierValue("close-quote");
    static CssIdentifierValue NoOpenQuote = new CssIdentifierValue("no-open-quote");
    static CssIdentifierValue NoCloseQuote = new CssIdentifierValue("no-close-quote");
    // Color
    static CssHashValue Black = CssHashValue.fromColor(DrColor.BLACK);
    static CssHashValue Gray = CssHashValue.fromColor(DrColor.GRAY);

    // "-aw-import" values.
    static CssIdentifierValue Ignore = new CssIdentifierValue("ignore");
    static CssIdentifierValue Spaces = new CssIdentifierValue("spaces");

    // SVG
    static CssIdentifierValue CurrentColor = new CssIdentifierValue("currentcolor");
    // stroke-linecap
    static CssIdentifierValue Butt = new CssIdentifierValue("butt");
    static CssIdentifierValue Round = new CssIdentifierValue("round");
    // stroke-linejoin
    static CssIdentifierValue Miter = new CssIdentifierValue("miter");
    static CssIdentifierValue Bevel = new CssIdentifierValue("bevel");
    // fill-rule
    static CssIdentifierValue Nonzero = new CssIdentifierValue("nonzero");
    static CssIdentifierValue Evenodd = new CssIdentifierValue("evenodd");
    // dominant-baseline
    static CssIdentifierValue Central = new CssIdentifierValue("central");
    static CssIdentifierValue Ideographic = new CssIdentifierValue("ideographic");
    static CssIdentifierValue Mathematical = new CssIdentifierValue("mathematical");
    static CssIdentifierValue Alphabetic = new CssIdentifierValue("alphabetic");
    static CssIdentifierValue Hanging = new CssIdentifierValue("hanging");

    // MsoHtml
    static CssIdentifierValue Yes = new CssIdentifierValue("yes");
    static CssIdentifierValue No = new CssIdentifierValue("no");
    static CssIdentifierValue Landscape = new CssIdentifierValue("landscape");
    static CssIdentifierValue Exactly = new CssIdentifierValue("exactly");
    static CssIdentifierValue WidowOrphan = new CssIdentifierValue("widow-orphan");
    static CssIdentifierValue LinesTogether = new CssIdentifierValue("lines-together");
    static CssIdentifierValue NoLineNumbers = new CssIdentifierValue("no-line-numbers");
    static CssIdentifierValue BodyText = new CssIdentifierValue("body-text");

    /// <summary>
    /// string, Color, double or CssValueCollection depending on the type of the value.
    /// </summary>
    private /*final*/ Object mValue;
    private /*final*/ /*CssValueType*/int mValueType;
}

