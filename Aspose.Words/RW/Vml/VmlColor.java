// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/10/2006 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.Debug;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import java.lang.Integer;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;



/// <summary>
/// Contains static methods for conversion of Color values to/from VML string representation.
/// Note: I have decided to throw on unknown colors, at least for some time after WordML release, until we decide otherwise.
/// </summary>
/*static*/ class VmlColor
{
	/* Simulation of static class by using private constructor */
	private VmlColor()
	{}

    /// <summary>
    /// Converts VML color representation to <see cref="System.Drawing.Color"/>.
    /// Examples of VML color strings:
    ///  - 'fill darken(51)'
    ///  - 'yellow'
    ///  - '#38B'
    ///  - '#3388BB'
    ///  - 'FFFFFF'
    /// </summary>
    static DrColor vmlToColor(String color)
    {
        Match match = G_REGEX_COLOR.match(color);

        String action = match.getGroups().get(ACTION_GROUP).getValue();
        String name = match.getGroups().get(NAME_GROUP).getValue();
        String codeSix = match.getGroups().get(CODE_SIX_GROUP).getValue();
        String codeHashSix = match.getGroups().get(CODE_HASH_SIX_GROUP).getValue();
        String codeHashThree = match.getGroups().get(CODE_HASH_THREE_GROUP).getValue();
        String codeHashOne = match.getGroups().get(CODE_HASH_ONE_GROUP).getValue();

        if (StringUtil.hasChars(action))
        {
            int a = 0xef;
            int r;
            int g;
            int b;

            switch (gStringSwitchMap.of(match.getGroups().get(ACTION_1_GROUP).getValue()))
            {
                case /*"fill"*/0:
                    r = 0xf0;
                    break;
                case /*"lineOrFill"*/1:
                    r = 0xf1;
                    break;
                case /*"line"*/2:
                    r = 0xf2;
                    break;
                case /*"shadow"*/3:
                    r = 0xf3;
                    break;
                default:
                    throw new IllegalStateException(getColorImportExceptionMessage(color));
            }

            switch (gStringSwitchMap.of(match.getGroups().get(ACTION_2_GROUP).getValue()))
            {
                case /*"darken"*/4:
                    g = 0x01;
                    break;
                case /*"lighten"*/5:
                    g = 0x02;
                    break;
                case /*"add"*/6:
                    g = 0x03;
                    break;
                default:
                    throw new IllegalStateException(getColorImportExceptionMessage(color));
            }

            b = FormatterPal.parseInt(match.getGroups().get(ACTION_CODE_GROUP).getValue());

            return new DrColor(a, r, g, b);
        }
        else if (StringUtil.hasChars(name))
        {
            // The color is expressed in named form.
            // It can be a known color name from CSS2, like 'fuchsia', 'navy' etc.
            // Or it can be a special name, like 'this', 'window', etc.

            if ("window".equals(name))
                return DrColor.WINDOW;
            else if ("windowText".equals(name))
                return DrColor.BLACK;
            else if ("this".equals(name))
                return DrColor.EMPTY;
            else
                return VmlColorEnum.vmlToPalColor(name);
        }
        else if (StringUtil.hasChars(codeSix))
        {
            // The color is expressed in color code, like '#3388BB'. We take only '3388BB' from this string.
            return NrxXmlUtil.xmlToColor(codeSix);
        }
        else if (StringUtil.hasChars(codeHashSix))
        {
            // The color is expressed in color code, like '3388BB'.
            return NrxXmlUtil.xmlToColor(codeHashSix);
        }
        else if (StringUtil.hasChars(codeHashThree))
        {
            // The color is expressed in short code, like '#38B'. The full color code for it will be '#3388BB'

            // Convert short color code to full color code.
            StringBuilder fullColorCodeBuilder = new StringBuilder(6);

            for (int i = 0; i < codeHashThree.length(); i++)
            {
                char ch = codeHashThree.charAt(i);
                fullColorCodeBuilder.append(ch);
                fullColorCodeBuilder.append(ch);
            }

            return NrxXmlUtil.xmlToColor(fullColorCodeBuilder.toString());
        }
        else if (StringUtil.hasChars(codeHashOne))
        {
            // The color is in form #X, for example #0 or #f. The full color code for #f will be #0000ff. WORDSNET-11964

            // Converting short color code to full color code.
            StringBuilder fullColorCodeBuilder = new StringBuilder(6);
            msStringBuilder.append(fullColorCodeBuilder, "0000");
            Debug.assertTrue(codeHashOne.length() == 2);
            fullColorCodeBuilder.append(codeHashOne.charAt(1));
            fullColorCodeBuilder.append(codeHashOne.charAt(1));

            return NrxXmlUtil.xmlToColor(fullColorCodeBuilder.toString());
        }
        else
            throw new IllegalStateException(getColorImportExceptionMessage(color));
    }

    /// <summary>
    /// Converts <see cref="System.Drawing.Color"/> to VML color string.
    /// </summary>
    /// <param name="color">Color to convert.</param>
    /// <returns>VML color string, like 'fill darken(51)', 'yellow', '#38B' or '#3388BB'.</returns>
    static String colorToVml(DrColor color)
    {
        return colorToVml(color, true, false);
    }

    /// <summary>
    /// Converts <see cref="System.Drawing.Color"/> to VML color string.
    /// </summary>
    /// <param name="color">Color to convert.</param>
    /// <param name="isShortNotationAllowed">True - to allow short color notations like #7fc, instead of #77ffcc.</param>
    /// <param name="isDigitalNotationUpper">True - to output digital color notation in upper case, like #77FFCC.</param>
    /// <returns>VML color string, like 'fill darken(51)', 'yellow', '#38B' or '#3388BB'.</returns>
    static String colorToVml(DrColor color, boolean isShortNotationAllowed, boolean isDigitalNotationUpper)
    {
        if (color.isEmpty())
            return "this";

        // 0xef in transparency means that this color is actually a coloring action definition, e.g. 'fill darken(51)'.
        // This definitions are not described anywhere - I had to figure them out.
        if (color.getA() == 0xef)
        {
            StringBuilder sb = new StringBuilder();

            if (color.getR() >= 0xf0)
            {
                switch (color.getR())
                {
                    case 0xf0:
                        msStringBuilder.append(sb, "fill ");
                        break;
                    case 0xf1:
                        msStringBuilder.append(sb, "lineOrFill ");
                        break;
                    case 0xf2:
                        // andrnosk: WORDSNET-5034 Added one more shadow color type.
                        msStringBuilder.append(sb, "line ");
                        break;
                    case 0xf3:
                        msStringBuilder.append(sb, "shadow ");
                        break;
                    case 0xf4: // Seen in DOC files sometimes (e.g. TestLeorMeller.doc) but not written to WordML.
                    case 0xf7: // Seen in TestDefect1402 in Doc2Docx conversion.
                    default:
                        return null;
                }

                switch (color.getG())
                {
                    case 0x01:
                        msStringBuilder.append(sb, "darken");
                        break;
                    case 0x02:
                        msStringBuilder.append(sb, "lighten");
                        break;
                    case 0x03:
                        msStringBuilder.append(sb, "add");
                        break;
                    default:
                        return null;
                }

                msStringBuilder.appendFormat(sb, "({0})", color.getB());
            }
            else
            {
                switch (color.getR())
                {
                    case 0x11:
                        msStringBuilder.append(sb, "window");
                        break;
                    case 0x01: // Seen in TestJira5597 in Doc2Docx conversion.
                               // See TestExportDocx.TestDefect5597 for details.
                    default:
                        return null;
                }
            }

            return sb.toString();
        }

        // Check if the color is a known color and can be output in a human readable form,
        // e.g. 'yellow' instead of '#FFFF00'.

        String knownColorName = VmlColorEnum.palColorToVml(color);

        if (!"".equals(knownColorName))
            return knownColorName;


        if (isShortNotationAllowed)
        {
            // Check if the color can be output in short form, e.g. '#38B' instead of '#3388BB'.

            if (color.getA() == 0xff && isShortColorComponent(color.getR()) && isShortColorComponent(color.getG()) && isShortColorComponent(color.getB()))
            {
                return msString.format("#{0}{1}{2}", colorComponentToShortVml(color.getR()), colorComponentToShortVml(color.getG()), colorComponentToShortVml(color.getB()));
            }
        }

        String digitalNotation = msString.format("#{0}{1}{2}", colorComponentToVml(color.getR()), colorComponentToVml(color.getG()), colorComponentToVml(color.getB()));

        if (isDigitalNotationUpper)
            return digitalNotation.toUpperCase();

        return digitalNotation;
    }

    /// <summary>
    /// Returns the base color value from VML color extended markup.
    /// #3b1f2d [rgb(149,79,114) darken(102)] returns DrColor(149, 79, 114).
    /// </summary>
    static DrColor getBaseColor(String vmlColor)
    {
        MatchCollection matchCol = G_REGEX_BASE_COLOR.matches(vmlColor);

        if ((matchCol.getCount() != 3) || (!matchCol.get(0).getGroups().get(1).getValue().contains("rgb")))
            return null;

        int r = Integer.parseInt(matchCol.get(0).getGroups().get(2).getValue());
        int g = Integer.parseInt(matchCol.get(1).getGroups().get(2).getValue());
        int b = Integer.parseInt(matchCol.get(2).getGroups().get(2).getValue());

        return DrColor.fromArgb(r, g, b);
    }

    /// <summary>
    /// Returns the modifier value from VML color extended markup.
    /// #3b1f2d [rgb(149,79,114) darken(102)] returns 102.
    /// </summary>
    static int getColorModifier(String vmlColor)
    {
        Match match = G_REGEX_MODIFIER.match(vmlColor);

        if (msString.startsWith(match.getGroups().get(1).getValue(), "lighten", StringComparison.INVARIANT_CULTURE))
            return Integer.parseInt(match.getGroups().get(2).getValue());

        if (msString.startsWith(match.getGroups().get(1).getValue(), "darken", StringComparison.INVARIANT_CULTURE))
            return -Integer.parseInt(match.getGroups().get(2).getValue());

        return 0;
    }

    private static String getColorImportExceptionMessage(String color)
    {
        return msString.format("Unknown color type encountered during WordML import - {0}.", color);
    }

    private static String colorComponentToVml(int colorComponent)
    {
        return FormatterPal.intToStrX2Lower(colorComponent);
    }

    private static String colorComponentToShortVml(int colorComponent)
    {
        return FormatterPal.intToStrXLower(colorComponent % 16);
    }

    private static boolean isShortColorComponent(int colorComponent)
    {
        return (colorComponent / 16) == (colorComponent % 16);
    }

    // In Java we must use group indexes, not names.
    private static final int ACTION_GROUP = 2;
    private static final int ACTION_1_GROUP = 3;
    private static final int ACTION_2_GROUP = 4;
    private static final int ACTION_CODE_GROUP = 5;
    private static final int CODE_SIX_GROUP = 6;
    private static final int NAME_GROUP = 8;
    private static final int CODE_HASH_SIX_GROUP = 10;
    private static final int CODE_HASH_THREE_GROUP = 12;
    private static final int CODE_HASH_ONE_GROUP = 13;

    /// <summary>
    /// RK this was created by VA originally. It included regex for rgb, cmyk and ink.
    /// But this was not used and I think it was not tested. I couldn't verify it by just reading the regex.
    /// It caused problems in DOCX for color like "#9bbb59 [3042]" because of the number in braces.
    /// I removed parsing of rgb, cmyk and ink until we need to support it and until we test it.
    /// </summary>
    private static /*final*/ Regex G_REGEX_COLOR = new Regex(
        "^(" +
        // 'fill darken(51)'
        "(([a-z]+) ([a-z]+)\\((\\d+)\\))|" +              // Groups: Action1 Action2(ActionCode). The whole thing is the "action" group.
                                                        // 'FFFFFF'
        "([0-9a-f]{6})|" +                             // Group: CodeSix
                                                        // 'yellow' and sometimes 'white [3102]' etc.
        "(([a-z]+).*)|" +                              // Group: Name
                                                        // '#3388BB' and sometimes '#9bbb59 [rgb(1,2,3)]' etc.
        "(#([0-9a-f]{6}).*)|" +                        // Group: CodeHashSix
                                                        // '#38B' and sometimes '#ddd [3204]'
        "(#([0-9a-f]{3}).*)|" +                        // Group: CodeHashThree
                                                        // '#0' WORDSNET-11964
        "(#[0-9a-f])" +                                // Group: CodeHashOne
        ")$",
        RegexOptions.COMPILED | RegexOptions.IGNORE_CASE);

    private static /*final*/ Regex G_REGEX_BASE_COLOR =
        new Regex("(rgb\\(|,)(\\b[\\d]+)", RegexOptions.COMPILED | RegexOptions.IGNORE_CASE);
    private static /*final*/ Regex G_REGEX_MODIFIER =
        new Regex("(darken\\(|lighten\\()(\\b[\\d]+)", RegexOptions.COMPILED | RegexOptions.IGNORE_CASE);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"fill",
		"lineOrFill",
		"line",
		"shadow",
		"darken",
		"lighten",
		"add"
	);

}

