// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Attributes that can be defined for a text run.
///
/// Note the constant values make sure the attributes are written
/// into a binary file in a specific order and the order is important.
/// </summary>
/*static*/ class FontAttr
{
	/* Simulation of static class by using private constructor */
	private FontAttr()
	{}

    /// <summary>
    /// AttrBoolEx. Specifies that this line break does not indicate a line break but serves as a style separator.
    /// A style separator allows one paragraph to consist of parts that have different paragraph styles.
    /// Can only be applied to the paragraph break run properties.
    /// </summary>
    static final int SPECIAL_HIDDEN = 10;
    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used by the binary writer to trigger writing of the first block of the system attributes.
    /// </summary>
    static final int SYS_ATTRS_1_TRIGGER = 16;
    /// <summary>
    /// Color. Default WordColor.Auto.
    /// </summary>
    static final int HIGHLIGHT_COLOR = 20;
    /// <summary>
    /// int, no default
    ///
    /// Specifies a unique identifier used to track the editing session when the run properties
    /// were last modified in the main document.
    /// </summary>
    static final int RSID_R_PR = 30;
    /// <summary>
    /// int, no default
    ///
    /// Specifies a unique identifier used to track the editing session when the run was
    /// added to the main document.
    /// </summary>
    static final int RSID_R = 40;
    /// <summary>
    /// LineBreakClear enum. Default LineBreakClear.None.
    /// </summary>
    static final int LINE_BREAK_CLEAR = 45;
    /// <summary>
    /// int, istd of the character style. Default StyleIndex.DefaultParagraphFont.
    /// </summary>
    static final int ISTD = 50;
    /// <summary>
    /// AttrBoolEx, bold font. Default BoolEx.False.
    /// </summary>
    static final int BOLD = 60;
    /// <summary>
    /// AttrBoolEx, italic font. Default BoolEx.False.
    /// </summary>
    static final int ITALIC = 70;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int STRIKE_THROUGH = 80;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int OUTLINE = 90;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int SHADOW = 100;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int SMALL_CAPS = 110;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int ALL_CAPS = 120;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int HIDDEN = 130;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int WEB_HIDDEN = 132;
    /// <summary>
    /// Underline enum. Default Underline.None
    /// </summary>
    static final int UNDERLINE = 140;
    /// <summary>
    /// int, spacing between chars in twips. Default 0.
    /// </summary>
    static final int SPACING = 150;
    /// <summary>
    /// Color, font color. Default WordColor.Auto.
    /// </summary>
    static final int COLOR = 160;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int EMBOSS = 170;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int ENGRAVE = 180;
    /// <summary>
    /// int, font size in half points. Default 20.
    /// </summary>
    static final int SIZE = 190;
    /// <summary>
    /// int, half points. Default 0.
    /// </summary>
    static final int POSITION = 200;
    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used by the binary writer to trigger writing of the second block of the system attributes.
    /// </summary>
    static final int SYS_ATTRS_2_TRIGGER = 202;
    /// <summary>
    /// RunVerticalAlignment enum. Default VerticalAlignment.Baseline.
    /// </summary>
    static final int VERTICAL_ALIGNMENT = 210;
    /// <summary>
    /// int, half points. Default 0.
    /// </summary>
    static final int KERNING = 220;
    /// <summary>
    /// string. font name for ascii chars 0-127. Default Times New Roman.
    /// </summary>
    static final int NAME_ASCII = 230;
    /// <summary>
    /// string, font name for Far East runs. Default Times New Roman.
    /// </summary>
    static final int NAME_FAR_EAST = 235;
    /// <summary>
    /// string, font name for ascii chars 128-255. Default Times New Roman.
    /// </summary>
    static final int NAME_OTHER = 240;
    /// <summary>
    /// AttrBoolEx, bold font for rtl runs. Default BoolEx.False.
    /// </summary>
    static final int BOLD_BI = 250;
    /// <summary>
    /// AttrBoolEx, italic font for rtl runs. Default BoolEx.False.
    /// </summary>
    static final int ITALIC_BI = 260;
    /// <summary>
    /// AttrBoolEx. This corresponds to "rtl" in WordML. Default BoolEx.False.
    /// </summary>
    static final int BIDI = 265;
    /// <summary>
    /// AttrBoolEx. This corresponds to "cs" in WordML. Default BoolEx.False.
    /// </summary>
    static final int COMPLEX_SCRIPT = 268;
    /// <summary>
    /// string, font name for RTL runs. Default Times New Roman.
    /// </summary>
    static final int NAME_BI = 270;
    /// <summary>
    /// int, char width in percent. Default 100.
    /// </summary>
    static final int SCALING = 290;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int DOUBLE_STRIKE_THROUGH = 300;
    /// <summary>
    /// TextEffect, animation effect enum. Default TextEffect.None.
    /// </summary>
    static final int TEXT_EFFECT = 310;
    /// <summary>
    /// AttrBoolEx, asian. Default BoolEx.False.
    /// </summary>
    static final int SNAP_TO_GRID = 330;
    /// <summary>
    /// int. Default 0x0400.
    /// </summary>
    static final int LOCALE_ID_BI = 340;
    /// <summary>
    /// int, font size for rtl runs in half points. Default 20.
    /// </summary>
    static final int SIZE_BI = 350;
    /// <summary>
    /// Border, complex attr, border around run. Default Border object.
    /// </summary>
    static final int BORDER = 360;
    /// <summary>
    /// Shading, complex attribute. Default Shading object.
    /// </summary>
    static final int SHADING = 370;
    /// <summary>
    /// int. Default 0x0400.
    /// </summary>
    static final int LOCALE_ID = 380;
    /// <summary>
    /// int. Default 0x0400.
    /// </summary>
    static final int LOCALE_ID_FAR_EAST = 390;
    /// <summary>
    /// A <see cref="CharacterCategory"/> value. Corresponds to rFonts.hint in WordML.
    /// Default CharacterCategory.Other.
    /// </summary>
    static final int CHARACTER_CATEGORY_HINT = 400;
    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// Used by the binary writer to trigger writing of few attributes in right place.
    /// </summary>
    static final int WORD_XP_ATTRS_TRIGGER = 402;
    /// <summary>
    /// AttrBoolEx. Default BoolEx.False.
    /// </summary>
    static final int NO_PROOFING = 440;
    /// <summary>
    /// Color. Default WordColor.Autor.
    /// </summary>
    static final int UNDERLINE_COLOR = 450;
    /// <summary>
    /// HyphenRule enum. Default HyphenRule.None.
    /// </summary>
    static final int HYPHEN_RULE = 460;
    /// <summary>
    /// int. Default 0.
    /// </summary>
    static final int HYPHEN_CHAR = 470;
    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// int. Looks like the picture bullet id. Not always effective. See <see cref="PictureBulletFlags"/>.
    /// </summary>
    static final int PICTURE_BULLET_ID = 480;
    /// <summary>
    /// Do not use anywhere except DOC import/export.
    /// enum. Looks like the picture bullet flag.
    /// </summary>
    static final int PICTURE_BULLET_FLAGS = 490;

    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_COLOR = 500;
    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_SHADE = 510;
    /// <summary>
    /// At the moment this is an unparsed string value.
    /// </summary>
    static final int THEME_TINT = 520;

    static final int UNDERLINE_THEME_COLOR = 521;
    static final int UNDERLINE_THEME_SHADE = 522;
    static final int UNDERLINE_THEME_TINT = 523;

    /// <summary>
    /// Specifies the justification of equations in the paragraph. Currently ignored.
    /// </summary>
    static final int CF_MATH_PR = 600;

    /// <summary>
    /// bool
    /// </summary>
    static final int MATH_IS_LITERAL = 700;

    /// <summary>
    /// bool
    /// </summary>
    static final int MATH_IS_NORMAL_TEXT = 710;

    /// <summary>
    /// enum. <see cref="Aspose.Words.Math.MathScript"/>
    /// </summary>
    static final int MATH_SCRIPT = 720;

    /// <summary>
    /// enum. <see cref="StyleType"/>
    /// </summary>
    static final int MATH_STYLE = 730;

    /// <summary>
    /// object, <see cref="Aspose.Words.Math.MathLineBreak"/>
    /// </summary>
    static final int MATH_LINE_BREAK = 740;

    /// <summary>
    /// bool
    /// </summary>
    static final int MATH_IS_ALIGNMENT_POINT = 750;

    /// <summary>
    /// bool.
    /// </summary>
    static final int MATH_IS_O_MATH = 760;

    /// <summary>
    /// enum. <see cref="Words.EmphasisMark" />.
    /// </summary>
    static final int EMPHASIS_MARK = 770;

    /// <summary>
    /// Complex attribute.
    /// </summary>
    static final int FAR_EAST_LAYOUT = 780;

    /// <summary>
    /// Complex attribute.
    /// </summary>
    static final int ALTERNATE_CONTENT = 790;

    /// <summary>
    /// Raw XML. Specifies glow effect of text.
    /// </summary>
    static final int EFFECT_GLOW = 810;

    /// <summary>
    /// Raw XML. Specifies shadow effect of text.
    /// </summary>
    static final int EFFECT_SHADOW = 815;

    /// <summary>
    /// Raw XML. Specifies reflection effect of text.
    /// </summary>
    static final int EFFECT_REFLECTION = 820;

    /// <summary>
    /// Raw XML. Specifies outline effect of text.
    /// </summary>
    static final int EFFECT_OUTLINE = 825;

    /// <summary>
    /// Raw XML. Specifies fill effect of text.
    /// </summary>
    static final int EFFECT_FILL = 830;

    /// <summary>
    /// Raw XML. Specifies the 3-D scene properties of text, including camera and lighting.
    /// </summary>
    static final int EFFECT_SCENE_3_D = 835;

    /// <summary>
    /// Raw XML. Specifies the 3-D properties of text, including bevel, extrusion, contour, and material.
    /// </summary>
    static final int EFFECT_PROPS_3_D = 840;

    /// <summary>
    /// Specifies which kinds of ligatures <see cref="Ligature"/> to use when displaying the text.
    /// </summary>
    static final int OPEN_TYPE_LIGATURE = 850;

    /// <summary>
    /// Specifies the form <see cref="NumForm" />in which numerals are displayed.
    /// </summary>
    static final int OPEN_TYPE_NUM_FORM = 855;

    /// <summary>
    /// Specifies the form <see cref="NumSpacing"/> in which numerals are displayed.
    /// </summary>
    static final int OPEN_TYPE_NUM_SPACING = 860;

    /// <summary>
    /// Specifies references to sets of character forms defined within the font to be used as stylistic sets.
    /// </summary>
    static final int OPEN_TYPE_STYLISTIC_SETS = 865;

    /// <summary>
    /// Specifies whether to display the characters using contextual alternates.
    /// </summary>
    static final int OPEN_TYPE_CONTEXTUAL_ALTERNATES = 870;

    /// <summary>
    /// Specifies a width, in twips, to which text is expanded or condensed to fit.
    /// </summary>
    static final int FIT_TEXT = 880;

    /// <summary>
    /// <see cref="Ruby"/>. Specifies that run is actually phonetic guide run.
    /// </summary>
    static final int RUBY = 885;

    /// <summary>
    /// bool. Indicates that run was read from Symbol. Used for unit testing only.
    /// </summary>
    static final int SYS_SYMBOL = 890;
}

