// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.DrColor;


/// <summary>
/// Converts enumerated types for Run properties between enum and DOCX/WML string.
/// </summary>
class NrxRunEnum
{
    static /*EmphasisMark*/int xmlToEmphasisMark(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"circle"*/0: return EmphasisMark.OVER_WHITE_CIRCLE;
            case /*"comma"*/1: return EmphasisMark.OVER_COMMA;
            case /*"dot"*/2: return EmphasisMark.OVER_SOLID_CIRCLE;
            case /*"underDot"*/3: 
            case /*"under-dot"*/4:
                return EmphasisMark.UNDER_SOLID_CIRCLE;
            default: return EmphasisMark.NONE;
        }
    }

    static String emphasisMarkToXml(/*EmphasisMark*/int value, boolean isDocx)
    {
        switch (value)
        {
            case EmphasisMark.OVER_WHITE_CIRCLE: return "circle";
            case EmphasisMark.OVER_COMMA: return "comma";
            case EmphasisMark.OVER_SOLID_CIRCLE: return "dot";
            case EmphasisMark.UNDER_SOLID_CIRCLE: return isDocx ? "underDot" : "under-dot";
            default: return "";
        }
    }

    static /*CombineBrackets*/int xmlToCombineBrackets(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"round"*/5: return CombineBrackets.ROUND;
            case /*"angle"*/6: return CombineBrackets.ANGLE;
            case /*"square"*/7: return CombineBrackets.SQUARE;
            case /*"curly"*/8: return CombineBrackets.CURLY;
            default: return CombineBrackets.NONE;
        }
    }

    static String combineBracketsToXml(/*CombineBrackets*/int value)
    {
        switch (value)
        {
            case CombineBrackets.ROUND: return "round";
            case CombineBrackets.ANGLE: return "angle";
            case CombineBrackets.SQUARE: return "square";
            case CombineBrackets.CURLY: return "curly";
            default: return "";
        }
    }

    static /*ThemeFontCore*/int xmlToThemeFont(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"majorAscii"*/9: return ThemeFontCore.MAJOR_ASCII;
            case /*"majorBidi"*/10: return ThemeFontCore.MAJOR_BIDI;
            case /*"majorEastAsia"*/11: return ThemeFontCore.MAJOR_EAST_ASIA;
            case /*"majorHAnsi"*/12: return ThemeFontCore.MAJOR_H_ANSI;
            case /*"minorAscii"*/13: return ThemeFontCore.MINOR_ASCII;
            case /*"minorBidi"*/14: return ThemeFontCore.MINOR_BIDI;
            case /*"minorEastAsia"*/15: return ThemeFontCore.MINOR_EAST_ASIA;
            case /*"minorHAnsi"*/16: return ThemeFontCore.MINOR_H_ANSI;
            default: return ThemeFontCore.NONE;
        }
    }

    static String themeFontToXml(/*ThemeFontCore*/int value)
    {
        switch (value)
        {
            case ThemeFontCore.MAJOR_ASCII: return "majorAscii";
            case ThemeFontCore.MAJOR_BIDI: return "majorBidi";
            case ThemeFontCore.MAJOR_EAST_ASIA: return "majorEastAsia";
            case ThemeFontCore.MAJOR_H_ANSI: return "majorHAnsi";
            case ThemeFontCore.MINOR_ASCII: return "minorAscii";
            case ThemeFontCore.MINOR_BIDI: return "minorBidi";
            case ThemeFontCore.MINOR_EAST_ASIA: return "minorEastAsia";
            case ThemeFontCore.MINOR_H_ANSI: return "minorHAnsi";
            default: return "";
        }
    }

    static /*CharacterCategory*/int xmlToHint(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"eastAsia"*/17:
            case /*"fareast"*/18:     // WML
                return CharacterCategory.FAR_EAST;
            case /*"cs"*/19:
                return CharacterCategory.COMPLEX_SCRIPT;
            default:
                return CharacterCategory.OTHER;
        }
    }

    static String hintToXml(/*CharacterCategory*/int value, boolean isDocx)
    {
        switch (value)
        {
            case CharacterCategory.FAR_EAST:
                return isDocx ? "eastAsia" : "fareast";
            case CharacterCategory.COMPLEX_SCRIPT:
                return "cs";
            default:
                return "default";
        }
    }

    static /*LineBreakClear*/int xmlToLineBreakClear(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"none"*/20: return LineBreakClear.NONE;
            case /*"left"*/21: return LineBreakClear.LEFT;
            case /*"right"*/22: return LineBreakClear.RIGHT;
            case /*"all"*/23: return LineBreakClear.ALL;
            default: return LineBreakClear.NONE;
        }
    }

    static String lineBreakClearToXml(/*LineBreakClear*/int value)
    {
        switch (value)
        {
            case LineBreakClear.NONE: return "none";
            case LineBreakClear.LEFT: return "left";
            case LineBreakClear.RIGHT: return "right";
            case LineBreakClear.ALL: return "all";
            default: return "";
        }
    }

    static char xmlToBreak(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"page"*/24: return '\f';
            case /*"column"*/25: return '\u000e';
            case /*"textWrapping"*/26:
            case /*"text-wrapping"*/27:
                return '\u000b';
            default: return ' ';
        }
    }

    static String breakToXml(char value, boolean isDocx)
    {
        switch (value)
        {
            case '\f': return "page";
            case '\u000e': return "column";
            case '\u000b': return isDocx ? "textWrapping" : "text-wrapping";
            default: return "";
        }
    }

    static /*TextEffect*/int xmlToTextEffect(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"blinkBackground"*/28:
            case /*"blink-background"*/29:
                return TextEffect.BLINKING_BACKGROUND;
            case /*"lights"*/30:
                return TextEffect.LAS_VEGAS_LIGHTS;
            case /*"antsBlack"*/31:
            case /*"ants-black"*/32:
                return TextEffect.MARCHING_BLACK_ANTS;
            case /*"antsRed"*/33:
            case /*"ants-red"*/34:
                return TextEffect.MARCHING_RED_ANTS;
            case /*"shimmer"*/35:
                return TextEffect.SHIMMER;
            case /*"sparkle"*/36:
                return TextEffect.SPARKLE_TEXT;
            case /*"none"*/20: 
            default:
                return TextEffect.NONE;
        }
    }

    static String textEffectToXml(/*TextEffect*/int value, boolean isDocx)
    {
        switch (value)
        {
            case TextEffect.BLINKING_BACKGROUND: return isDocx ? "blinkBackground" : "blink-background";
            case TextEffect.LAS_VEGAS_LIGHTS: return "lights";
            case TextEffect.MARCHING_BLACK_ANTS: return isDocx ? "antsBlack" : "ants-black";
            case TextEffect.MARCHING_RED_ANTS: return isDocx ? "antsRed" : "ants-red";
            case TextEffect.SHIMMER: return "shimmer";
            case TextEffect.SPARKLE_TEXT: return "sparkle";
            default: return "none";
        }
    }

    static /*Underline*/int xmlToUnderline(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"single"*/37:
                return Underline.SINGLE;
            case /*"words"*/38:
                return Underline.WORDS;
            case /*"double"*/39:
                return Underline.DOUBLE;
            case /*"thick"*/40:
                return Underline.THICK;
            case /*"dotted"*/41:
                return Underline.DOTTED;
            case /*"dotted-heavy"*/42:
            case /*"dottedHeavy"*/43:
                return Underline.DOTTED_HEAVY;
            case /*"dash"*/44:
                return Underline.DASH;
            case /*"dashed-heavy"*/45:
            case /*"dashedHeavy"*/46:
                return Underline.DASH_HEAVY;
            case /*"dash-long"*/47:
            case /*"dashLong"*/48:
                return Underline.DASH_LONG;
            case /*"dash-long-heavy"*/49:
            case /*"dashLongHeavy"*/50:
                return Underline.DASH_LONG_HEAVY;
            case /*"dot-dash"*/51:
            case /*"dotDash"*/52:
                return Underline.DOT_DASH;
            case /*"dash-dot-heavy"*/53:
            case /*"dashDotHeavy"*/54:
                return Underline.DOT_DASH_HEAVY;
            case /*"dot-dot-dash"*/55:
            case /*"dotDotDash"*/56:
                return Underline.DOT_DOT_DASH;
            case /*"dash-dot-dot-heavy"*/57:
            case /*"dashDotDotHeavy"*/58:
                return Underline.DOT_DOT_DASH_HEAVY;
            case /*"wave"*/59:
                return Underline.WAVY;
            case /*"wavy-heavy"*/60:
            case /*"wavyHeavy"*/61:
                return Underline.WAVY_HEAVY;
            case /*"wavy-double"*/62:
            case /*"wavyDouble"*/63:
                return Underline.WAVY_DOUBLE;
            case /*"none"*/20:
            default:
                return Underline.NONE;
        }
    }

    static String underlineToXml(/*Underline*/int value, boolean isDocx)
    {
        switch (value)
        {
            case Underline.SINGLE: return "single";
            case Underline.WORDS: return "words";
            case Underline.DOUBLE: return "double";
            case Underline.THICK: return "thick";
            case Underline.DOTTED: return "dotted";
            case Underline.DOTTED_HEAVY: return isDocx ? "dottedHeavy" : "dotted-heavy";
            case Underline.DASH: return "dash";
            case Underline.DASH_HEAVY: return isDocx ? "dashedHeavy" : "dashed-heavy";
            case Underline.DASH_LONG: return isDocx ? "dashLong" : "dash-long";
            case Underline.DASH_LONG_HEAVY: return isDocx ? "dashLongHeavy" : "dash-long-heavy";
            case Underline.DOT_DASH: return isDocx ? "dotDash" : "dot-dash";
            case Underline.DOT_DASH_HEAVY: return isDocx ? "dashDotHeavy" : "dash-dot-heavy";
            case Underline.DOT_DOT_DASH: return isDocx ? "dotDotDash" : "dot-dot-dash";
            case Underline.DOT_DOT_DASH_HEAVY: return isDocx ? "dashDotDotHeavy" : "dash-dot-dot-heavy";
            case Underline.WAVY: return "wave";
            case Underline.WAVY_HEAVY: return isDocx ? "wavyHeavy" : "wavy-heavy";
            case Underline.WAVY_DOUBLE: return isDocx ? "wavyDouble" : "wavy-double";
            case Underline.NONE:
            default:
                return "none";
        }
    }

    static DrColor xmlToHighlight(String value)
    {
        // Highlight colors are limited to 16 colors (we call this Word 97 colors).
        // First translate a string into a Word 97 color and then translate to .NET color.
        /*Word97Color*/int color97;
        switch (gStringSwitchMap.of(value))
        {
            case /*"none"*/20: 
                color97 = Word97Color.AUTO;
                break;
            case /*"white"*/64:
                color97 = Word97Color.WHITE;
                break;
            case /*"yellow"*/65:
                color97 = Word97Color.YELLOW;
                break;
            case /*"green"*/66:
                color97 = Word97Color.GREEN;
                break;
            case /*"cyan"*/67:
                color97 = Word97Color.CYAN;
                break;
            case /*"magenta"*/68:
                color97 = Word97Color.MAGENTA;
                break;
            case /*"blue"*/69:
                color97 = Word97Color.BLUE;
                break;
            case /*"red"*/70:
                color97 = Word97Color.RED;
                break;
            case /*"darkBlue"*/71:
            case /*"dark-blue"*/72:
                color97 = Word97Color.DARK_BLUE;
                break;
            case /*"darkCyan"*/73:
            case /*"dark-cyan"*/74:
                color97 = Word97Color.DARK_CYAN;
                break;
            case /*"darkGreen"*/75:
            case /*"dark-green"*/76:
                color97 = Word97Color.DARK_GREEN;
                break;
            case /*"darkMagenta"*/77:
            case /*"dark-magenta"*/78:
                color97 = Word97Color.DARK_MAGENTA;
                break;
            case /*"darkRed"*/79:
            case /*"dark-red"*/80:
                color97 = Word97Color.DARK_RED;
                break;
            case /*"darkYellow"*/81:
            case /*"dark-yellow"*/82:
                color97 = Word97Color.DARK_YELLOW;
                break;
            case /*"darkGray"*/83:
            case /*"dark-gray"*/84:
                color97 = Word97Color.DARK_GRAY;
                break;
            case /*"lightGray"*/85:
            case /*"light-gray"*/86:
                color97 = Word97Color.LIGHT_GRAY;
                break;
            case /*"black"*/87:
                color97 = Word97Color.BLACK;
                break;
            default: color97 = Word97Color.AUTO;
                break;
        }

        return WordColorUtil.word97ColorToColor(color97);
    }

    static String highlightToXml(DrColor value, boolean isDocx)
    {
        // First translate a .NET color into a Word 97 color and then translate into a string.
        /*Word97Color*/int color97 = WordColorUtil.colorToWord97Color(value);

        switch (color97)
        {
            case Word97Color.AUTO: return "none";
            case Word97Color.WHITE: return "white";
            case Word97Color.YELLOW: return "yellow";
            case Word97Color.GREEN: return "green";
            case Word97Color.CYAN: return "cyan";
            case Word97Color.MAGENTA: return "magenta";
            case Word97Color.BLUE: return "blue";
            case Word97Color.RED: return "red";
            case Word97Color.DARK_BLUE: return isDocx ? "darkBlue" : "dark-blue";
            case Word97Color.DARK_CYAN: return isDocx ? "darkCyan" : "dark-cyan";
            case Word97Color.DARK_GREEN: return isDocx ? "darkGreen" : "dark-green";
            case Word97Color.DARK_MAGENTA: return isDocx ? "darkMagenta" : "dark-magenta";
            case Word97Color.DARK_RED: return isDocx ? "darkRed" : "dark-red";
            case Word97Color.DARK_YELLOW: return isDocx ? "darkYellow" : "dark-yellow";
            case Word97Color.DARK_GRAY: return isDocx ? "darkGray" : "dark-gray";
            case Word97Color.LIGHT_GRAY: return isDocx ? "lightGray" : "light-gray";
            case Word97Color.BLACK: return "black";
            default: return "none";
        }
    }

    static /*RunVerticalAlignment*/int xmlToRunVerticalAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"baseline"*/88: return RunVerticalAlignment.BASELINE;
            case /*"superscript"*/89: return RunVerticalAlignment.SUPERSCRIPT;
            case /*"subscript"*/90: return RunVerticalAlignment.SUBSCRIPT;
            default: return RunVerticalAlignment.BASELINE;
        }
    }

    static String runVerticalAlignmentToXml(/*RunVerticalAlignment*/int value)
    {
        switch (value)
        {
            case RunVerticalAlignment.BASELINE: return "baseline";
            case RunVerticalAlignment.SUPERSCRIPT: return "superscript";
            case RunVerticalAlignment.SUBSCRIPT: return "subscript";
            default: return "baseline";
        }
    }

    static /*HyphenRule*/int xmlToHyphenRule(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"none"*/20: return HyphenRule.NONE;
            case /*"normal"*/91: return HyphenRule.NORMAL;
            case /*"add-before"*/92: return HyphenRule.ADD_BEFORE;
            case /*"change-before"*/93: return HyphenRule.CHANGE_BEFORE;
            case /*"delete-before"*/94: return HyphenRule.DELETE_BEFORE;
            case /*"change-after"*/95: return HyphenRule.CHANGE_AFTER;
            case /*"delete-and-change"*/96: return HyphenRule.DELETE_AND_CHANGE;
            default: return HyphenRule.NONE;
        }
    }

    static String hyphenRuleToXml(/*HyphenRule*/int value)
    {
        switch (value)
        {
            case HyphenRule.NONE: return "none";
            case HyphenRule.NORMAL: return "normal";
            case HyphenRule.ADD_BEFORE: return "add-before";
            case HyphenRule.CHANGE_BEFORE: return "change-before";
            case HyphenRule.DELETE_BEFORE: return "delete-before";
            case HyphenRule.CHANGE_AFTER: return "change-after";
            case HyphenRule.DELETE_AND_CHANGE: return "delete-and-change";
            default: return "none";
        }
    }

    static /*RubyAlignment*/int xmlToRubyAlignment(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"center"*/97: return RubyAlignment.CENTER;
            case /*"distributeLetter"*/98: return RubyAlignment.DISTRIBUTE_LETTER;
            case /*"distributeSpace"*/99: return RubyAlignment.DISTRIBUTE_SPACE;
            case /*"left"*/21: return RubyAlignment.LEFT;
            case /*"right"*/22: return RubyAlignment.RIGHT;
            case /*"rightVertical"*/100: return RubyAlignment.RIGHT_VERTICAL;
            default: return RubyAlignment.CENTER;
        }
    }

    static String rubyAlignmentToXml(/*RubyAlignment*/int value)
    {
        switch (value)
        {
            case RubyAlignment.CENTER: return "center";
            case RubyAlignment.DISTRIBUTE_LETTER: return "distributeLetter";
            case RubyAlignment.DISTRIBUTE_SPACE: return "distributeSpace";
            case RubyAlignment.LEFT: return "left";
            case RubyAlignment.RIGHT: return "right";
            case RubyAlignment.RIGHT_VERTICAL: return "rightVertical";
            default: return "center";
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"circle",
		"comma",
		"dot",
		"underDot",
		"under-dot",
		"round",
		"angle",
		"square",
		"curly",
		"majorAscii",
		"majorBidi",
		"majorEastAsia",
		"majorHAnsi",
		"minorAscii",
		"minorBidi",
		"minorEastAsia",
		"minorHAnsi",
		"eastAsia",
		"fareast",
		"cs",
		"none",
		"left",
		"right",
		"all",
		"page",
		"column",
		"textWrapping",
		"text-wrapping",
		"blinkBackground",
		"blink-background",
		"lights",
		"antsBlack",
		"ants-black",
		"antsRed",
		"ants-red",
		"shimmer",
		"sparkle",
		"single",
		"words",
		"double",
		"thick",
		"dotted",
		"dotted-heavy",
		"dottedHeavy",
		"dash",
		"dashed-heavy",
		"dashedHeavy",
		"dash-long",
		"dashLong",
		"dash-long-heavy",
		"dashLongHeavy",
		"dot-dash",
		"dotDash",
		"dash-dot-heavy",
		"dashDotHeavy",
		"dot-dot-dash",
		"dotDotDash",
		"dash-dot-dot-heavy",
		"dashDotDotHeavy",
		"wave",
		"wavy-heavy",
		"wavyHeavy",
		"wavy-double",
		"wavyDouble",
		"white",
		"yellow",
		"green",
		"cyan",
		"magenta",
		"blue",
		"red",
		"darkBlue",
		"dark-blue",
		"darkCyan",
		"dark-cyan",
		"darkGreen",
		"dark-green",
		"darkMagenta",
		"dark-magenta",
		"darkRed",
		"dark-red",
		"darkYellow",
		"dark-yellow",
		"darkGray",
		"dark-gray",
		"lightGray",
		"light-gray",
		"black",
		"baseline",
		"superscript",
		"subscript",
		"normal",
		"add-before",
		"change-before",
		"delete-before",
		"change-after",
		"delete-and-change",
		"center",
		"distributeLetter",
		"distributeSpace",
		"rightVertical"
	);

}

