// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.DrColor;
import com.aspose.DrKnownColors;
import com.aspose.Base64Splitter;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Utility methods for WordML and DOCX XML processing.
/// Converting base types to/from XML and so on.
///
/// If we find generally useful methods here (non-MS Word specific), should move out
/// so they could be reused by other XML exports (to ODF and so on).
/// </summary>
/*static*/ class NrxXmlUtil
{
	/* Simulation of static class by using private constructor */
	private NrxXmlUtil()
	{}

    /// <summary>
    /// Number format can contain chars 0x00 - 0x08 which designate placeholders for level numbers.
    /// They should be converted to %1 - %9 for WordML and DOCX.
    /// </summary>
    static String numberFormatToXml(String numberFormat)
    {
        for (int i = ListLevel.MIN_LEVEL; i < ListLevel.MAX_LEVELS; i++)
            numberFormat = numberFormat.replace(Character.toString(((char)i)), listLevelToXml(i));

        return numberFormat;
    }

    /// <summary>
    /// Described in ECMA TC 45: 2.13.5.30 numberingChange (Previous Paragraph Numbering Properties).
    /// Example: "%1:3:0:-%2:1:0:%3:1:4:.".
    /// </summary>
    static String getOriginal(ParagraphNumberRevision numberRevision)
    {
        String numFormat = numberRevision.getNumberFormat();
        int[] numValues = numberRevision.getNumberValues();
        /*NumberStyle*/int[] numStyles = numberRevision.getNumberStyles();

        // RK Creating string build each time is not a very good idea.
        // To avoid this, should be an instance method on revision writer or something like that.
        StringBuilder strBuilder = new StringBuilder();

        int numFormatIndex = 0;
        int valueIndex = 0;

        while (numFormatIndex < numFormat.length())
        {
            msStringBuilder.append(strBuilder, listLevelToXml(numFormat.charAt(numFormatIndex++)));
            msStringBuilder.append(strBuilder, ":");
            msStringBuilder.append(strBuilder, FormatterPal.intToXml(numValues[valueIndex]));
            msStringBuilder.append(strBuilder, ":");
            msStringBuilder.append(strBuilder, FormatterPal.intToXml((int)numStyles[valueIndex]));
            msStringBuilder.append(strBuilder, ":");

            if (numFormatIndex < numFormat.length())
            {
                char separator = numFormat.charAt(numFormatIndex);

                if (separator > '\u0008')
                {
                    strBuilder.append(separator);
                    numFormatIndex++;
                }
            }

            valueIndex++;
        }

        return strBuilder.toString();
    }

    /// <summary>
    /// Converts list level, which is int (0-8) in the model, to WordML representation ("%1"-"%9").
    /// </summary>
    static String listLevelToXml(int value)
    {
        return msString.format("%{0}", value + 1);
    }

    /// <summary>
    /// Converts WordML representation ("%1"-"%9") to list level, which is int (0-8) in the model.
    /// </summary>
    static String xmlToListLevel(String numberFormat)
    {
        // Number format contains chars 0x00 - 0x08 which designate placeholders for level numbers.
        // They are represented as %1 - %9 in WordML.
        for (int i = 0; i < 10; i++)
            numberFormat = numberFormat.replace("%" + (i + 1), Character.toString(((char)i)));

        return numberFormat;
    }

    /// <summary>
    /// Converts array of integers to WordML comma delimited string.
    /// </summary>
    /// <param name="values">Array of integers.</param>
    /// <returns>WordML comm delimited string representation of array.</returns>
    static String intArrayToXml(int[] values)
    {
        // RK Creating string build each time is not a very good idea.
        // Used only by VmlDiagramWriter, so should be moved there and made an instance method.
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++)
        {
            msStringBuilder.append(sb, FormatterPal.intToXml(values[i]));
            sb.append(',');
        }

        // remove last comma
        if (sb.length() > 0)
            sb.setLength(sb.length() - 1)/*Property--*/;

        return sb.toString();
    }

    /// <summary>
    /// Converts integer stored in byte array (4 bytes, from less significant to most) to string, e.g. integer value 0x01020304,
    /// stored as byte[] {0x04, 0x03, 0x02, 0x01}, is convereted to "01020304".
    /// </summary>
    /// <param name="bytes">Array of bytes to convert.</param>
    /// <param name="startIndex">The index of the first byte of the converted integer value.</param>
    /// <returns>String representation of the integer stored in byte array.</returns>
    static String intToHex(byte[] bytes, int startIndex)
    {
        return StringUtil.bytesToHex(bytes, startIndex, 4, true);
    }

    /// <summary>
    /// Converts Int32 to WordMl hex string, e.g. integer value 0x01020304 is convereted to "01020304".
    /// </summary>
    /// <param name="value">Int32 to convert.</param>
    /// <returns>WordML hex string representing integer value.</returns>
    static String intToHex(int value)
    {
        return FormatterPal.intToStrX8(value);
    }

    /// <summary>
    /// Converts Int32 to WordMl hex string, e.g. integer value 0x01020304 is converted to "01020304".
    /// </summary>
    /// <param name="value">Object to convert. Must be Int32. If not Int32 or null, returns null.</param>
    /// <returns>WordML hex string representing integer value. If not Int32 or null, returns null.</returns>
    static String intToHex(Object value)
    {
        if (value instanceof /*int*/Integer)
            return FormatterPal.intToStrX8((/*int*/Integer)value);
        else
            return null;
    }

    /// <summary>
    /// Try parses strings containing integers in hexadecimal representation.
    /// on error return int.MinValue
    /// </summary>
    static int tryHexToInt(String value)
    {
        return FormatterPal.tryParseHex(value);
    }

    /// <summary>
    /// Parses strings containing integers in hexadecimal representation.
    /// </summary>
    static int hexToInt(String value)
    {
        return FormatterPal.parseHex(value);
    }

    /// <summary>
    /// Parses strings containing integers in hexadecimal representation,
    /// taking into account only 8 characters from right and discarding all others.
    /// </summary>
    static int hex8ToInt(String value)
    {
        // ISO/IEC 29500-1, 17.18.50  ST_LongHexNumber.
        // This simple type's contents have a length of exactly 8 hexadecimal digit(s).
        final int MAX_HEX_LENGTH = 8;

        if (value.length() > MAX_HEX_LENGTH)
            value = value.substring(value.length() - MAX_HEX_LENGTH);

        return hexToInt(value);
    }

    /// <summary>
    /// Converts hex string into the byte array.
    /// Resulting array length will be equal to the specified desired length.
    ///
    /// RK I think name does not match the name of the corresponding opposite method.
    /// Should be XmlToBytes probably.
    /// </summary>
    static byte[] hexToBytes(String hex, int desiredLength)
    {
        byte[] array = StringUtil.hexToBytes(hex);
        if (array.length == desiredLength)
            return array;

        byte[] newArray = new byte[desiredLength];
        System.arraycopy((array), 0, (newArray), 0, (Math.min(desiredLength, array.length)));

        return newArray;
    }

    /// <summary>
    /// RK I think name does not match the name of the corresponding opposite method.
    /// Should be XmlToBytes probably.
    ///
    /// RK BytesToHex accepts a boolean flag IsReversed but HexToBytes is split into two methods,
    /// making it inconsistent. Either use a boolean flag here and there or split into two methods.
    /// </summary>
    static void hexToBytesReversed(String hex, byte[] bytes, int start)
    {
        // RK Bad practice. Start was meaning one thing, now it means another thing.
        // Should be a different local variable with an apprpriate name.
        start = start + hex.length() / 2 - 1;
        for (int i = 0; i < hex.length(); i += 2)
            bytes[start--] = (byte)FormatterPal.parseHex(hex.substring((i), (i) + (2)));
    }

    /// <summary>
    /// Converts color to WordML color string ("auto", "FF00C0", etc.).
    /// </summary>
    /// <param name="color">Color to convert.</param>
    /// <returns>WordML string representing color.</returns>
    static String colorToXml(DrColor color)
    {
        if (color.isEmpty())
            return "auto";
        else
            return FormatterPal.intToStrX8(color.toArgb()).substring(2);    // RK I don't like this hack of removing "0x" at the beginning.
    }

    /// <summary>
    /// Parses WordML color strings, like "auto", "FF00C0", etc.
    /// </summary>
    static DrColor xmlToColor(String value)
    {
        value = value.toLowerCase();

        if ("auto".equals(value))
            return DrColor.EMPTY;

        // RESILIENCY WORDSNET-21462 The problem occurred because there was '#' at the beginning of color code string.
        // Color code parser did not recognize such code as valid.
        // Fixed by trimming '#' character from the color code string.
        value = msString.trim(value, G_COLOR_TRIM_CHARS);

        if (isHexColorString(value))
            return hexColorStringToColor(value);

        return DrKnownColors.fromName(value);
    }

    /// <summary>
    /// Converts color string, like "A90045", to color.
    /// </summary>
    private static DrColor hexColorStringToColor(String value)
    {
        int xmlColor = hexToInt(value);
        int r = (xmlColor & 0x00ff0000) >> 16;
        int g = (xmlColor & 0x0000ff00) >> 8;
        int b = (xmlColor & 0x000000ff);
        return new DrColor(r, g, b);
    }

    /// <summary>
    /// Checks if hex digit is a color string, like "A90045"
    /// </summary>
    private static boolean isHexColorString(String value)
    {
        if (value.length() != 6)
            return false;

        for (int i = 0; i < value.length(); i++)
        {
            if (!StringUtil.isHexDigit(value.charAt(i)))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Converts numeric value of version of MS Word application to XML string representation.
    /// </summary>
    static String versionNumberToXml(int versionNumber)
    {
        // Generate version number string in X.YYYY format.
        // High 16 bit value contains version number X,
        // low 16 bit value contains build number YYYY.
        int versionHigh = versionNumber >> 16;
        int versionLow = versionNumber % 65536;

        String version = FormatterPal.intToXml(versionHigh);

        version = msString.format("{0}.{1}", version, FormatterPal.intToStrD4(versionLow));

        return version;
    }

    /// <summary>
    /// Converts XML string representation of version to a numeric value.
    /// </summary>
    static int xmlToVersionNumber(String version)
    {
        int versionNumber = 0;

        if (StringUtil.hasChars(version))
        {
            // This comes as something like "11.5604" which must be translated to high and low 16 bit values.
            // It can also be "12" with no dot separator and sub-version digits, so be careful.

            String[] versionParts = msString.split(version, '.');
            int high = FormatterPal.xmlToInt(versionParts[0]);
            int low = 0;

            if (versionParts.length > 1)
                low = FormatterPal.xmlToInt(versionParts[1]);

            versionNumber = ((high << 16) | low);
        }

        return versionNumber;
    }

    static String bytesToBase64Attribute(byte[] value)
    {
        return Base64Splitter.convert(value, "\n");
    }

    /// <summary>
    /// Tries to extract an ordinal from strings like "_x0000_s1096" and "#_x0000_t75".
    ///
    /// Collects all digits from the end of the string until encounters a non digit character and
    /// returns that as a number.
    /// </summary>
    static int tryParseId(String id)
    {
        if (!StringUtil.hasChars(id))
            return Integer.MIN_VALUE;

        // WORDSNET-12060 Id string has value with decimal point.
        int startIndex = id.length() - 1;
        while ((startIndex >= 0) && (((id.charAt(startIndex) >= '0') && (id.charAt(startIndex) <= '9')) || id.charAt(startIndex) == '.'))
            startIndex--;

        return FormatterPal.tryParseIntPortion(id.substring(startIndex + 1));
    }

    /// <summary>
    /// Sets font name to RunPr if given name has chars.
    /// </summary>
    /// <param name="runPr"></param>
    /// <param name="key"></param>
    /// <param name="name">Either simple font name or theme font name.</param>
    /// <param name="isTheme">The meaning of name. If true name is simple font name otherwise name is theme font name.</param>
    static void setFontNameIfHasChars(RunPr runPr, int key, String name, boolean isTheme)
    {
        if (StringUtil.hasChars(name))
        {
            ComplexFontName fontName = isTheme
                                           ? ComplexFontName.fromTheme(NrxRunEnum.xmlToThemeFont(name))
                                           : ComplexFontName.fromName(name);

            // WORDSNET-22517 If we set theme font, but it is actually not a theme font (for example, theme
            // font name is invalid), then we should ignore this name and take it from the global defaults.
            if (isTheme && !fontName.isThemeFont())
            {
                // WORDSNET-28435 Do not override font that already set.
                if (!runPr.contains(key))
                {
                    fontName = (ComplexFontName)runPr.fetchInheritedAttr(key);
                    runPr.setAttr(key, fontName);
                }
            }
            else
                runPr.setAttr(key, fontName);
        }
    }

    /// <summary>
    /// Checks that value is valid OnOff value.
    /// </summary>
    static boolean isOnOffValue(String value)
    {
        return (("on".equals(value)) || ("1".equals(value)) || ("true".equals(value)) || ("t".equals(value)) ||
                ("off".equals(value)) || ("0".equals(value)) || ("false".equals(value)) || ("f".equals(value)));
    }

    /// <summary>
    /// Converts XML border name to CellAttr key.
    /// </summary>
    static int borderNameToCellAttr(String border)
    {
        switch (gStringSwitchMap.of(border))
        {
            case /*"top"*/0:
                return CellAttr.BORDER_TOP;

            case /*"bottom"*/1:
                return CellAttr.BORDER_BOTTOM;

            case /*"left"*/2:
            case /*"start"*/3:
                return CellAttr.BORDER_LEFT;

            case /*"right"*/4:
            case /*"end"*/5:
                return CellAttr.BORDER_RIGHT;

            case /*"tl2br"*/6:
                return CellAttr.BORDER_DIAGONAL_DOWN;

            case /*"tr2bl"*/7:
                return CellAttr.BORDER_DIAGONAL_UP;

            case /*"insideH"*/8:
                return CellAttr.BORDER_HORIZONTAL;

            case /*"insideV"*/9:
                return CellAttr.BORDER_VERTICAL;
            default:
                break;
        }

        throw new IllegalStateException("Invalid border name.");
    }

    /// <summary>
    /// Converts source units into points, and then convert points to target units.
    /// </summary>
    static double convertMeasure(double sourceValue, /*NrxUnit*/int sourceUnit, /*NrxUnit*/int targetUnit)
    {
        double result = sourceValue;
        if (sourceUnit != targetUnit) // only convert to points if values are not in desired target units
        {
            double inPoints = sourceValue / nrxUnitToMultiplier(sourceUnit);
            result = inPoints * nrxUnitToMultiplier(targetUnit);
        }
        return result;
    }

    /// <summary>
    /// Converts desired unit into multiplier that is required to obtain this unit from point.
    /// </summary>
    private static double nrxUnitToMultiplier(/*NrxUnit*/int unit)
    {
        switch (unit)
        {
            case NrxUnit.INCH:
                return 1.0 / ConvertUtilCore.POINTS_PER_INCH;
            case NrxUnit.MILLIMETER:
                return 1.0 / ConvertUtilCore.POINTS_PER_MM;
            case NrxUnit.CENTIMETER:
                return 1.0 / ConvertUtilCore.POINTS_PER_CM;
            case NrxUnit.PICA:
                return 1d / 12d; // 1 pica = 12 points.
            case NrxUnit.POINT:
                return 1.0;
            case NrxUnit.HALF_POINTS:
                return 2.0;
            case NrxUnit.TWIPS:
                return 20.0;
            case NrxUnit.EMUS:
                return ConvertUtilCore.EMUS_PER_POINT;
            case NrxUnit.HUNDREDTHS_OF_POINT:
                return 100.0;
            default:
                throw new IllegalStateException("Unsupported units.");
        }
    }

    static String getShapeId(ShapeBase shape)
    {
        return msString.format("_x0000_{0}{1}",
            (shape.isInline()) ? "i" : "s",
            FormatterPal.intToStrD4(shape.getId()));
    }

    private static /*final*/ char[] G_COLOR_TRIM_CHARS = { '#' };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"top",
		"bottom",
		"left",
		"start",
		"right",
		"end",
		"tl2br",
		"tr2bl",
		"insideH",
		"insideV"
	);

}

