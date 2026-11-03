// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Locale independent style identifier.
/// </summary>
/// <remarks>
/// <p>The names of built-in styles in MS Word are localized for different languages.
/// Using a style identifier you can find the correct style regardless of the document language.</p>
/// <p>All user defined styles are assigned the <see cref="StyleIdentifier.User"/> value.</p>
/// </remarks>
/// <dev>DO NOT RENUMBER! These values are important for DOC import/export.</dev>
/// <dev>Please note that the CURRENT USED MAXIMUM COUNT is SmartLink (371).</dev>
public /*enum*/ final class StyleIdentifier
{
    private StyleIdentifier(){}
    
    // Character styles.

    /// <summary>
    /// </summary>
    public static final int BOOK_TITLE = 264;
    /// <summary>
    /// The Annotation (Comment) Reference style.
    /// </summary>
    public static final int COMMENT_REFERENCE = 39;    // 0x0027  char style
    /// <summary>
    /// The Default Paragraph Font style.
    /// </summary>
    public static final int DEFAULT_PARAGRAPH_FONT = 65;    // 0x0041  char style
    /// <summary>
    /// </summary>
    public static final int EMPHASIS = 88;    // 0x0058  char style
    /// <summary>
    /// The Endnote Reference style.
    /// </summary>
    public static final int ENDNOTE_REFERENCE = 42;    // 0x002A  char style
    /// <summary>
    /// </summary>
    public static final int FOLLOWED_HYPERLINK = 86; // 0x0056   char style
    /// <summary>
    /// The Footnote Reference style.
    /// </summary>
    public static final int FOOTNOTE_REFERENCE = 38;    // 0x0026  char style
    /// <summary>
    /// </summary>
    public static final int HTML_ACRONYM = 95;
    /// <summary>
    /// </summary>
    public static final int HTML_CITE = 97;
    /// <summary>
    /// </summary>
    public static final int HTML_CODE = 98;
    /// <summary>
    /// </summary>
    public static final int HTML_DEFINITION = 99;
    /// <summary>
    /// </summary>
    public static final int HTML_KEYBOARD = 100;
    /// <summary>
    /// </summary>
    public static final int HTML_SAMPLE = 102;
    /// <summary>
    /// </summary>
    public static final int HTML_TYPEWRITER = 103;
    /// <summary>
    /// </summary>
    public static final int HTML_VARIABLE = 104;
    /// <summary>
    /// The Hyperlink style.
    /// </summary>
    public static final int HYPERLINK = 85;    // 0x0055  char style
    /// <summary>
    /// </summary>
    public static final int INTENSE_EMPHASIS = 261;
    /// <summary>
    /// </summary>
    public static final int INTENSE_REFERENCE = 263;
    /// <summary>
    /// The Line Number style.
    /// </summary>
    public static final int LINE_NUMBER = 40;    // 0x0028  char style
    /// <summary>
    /// The Page Number style.
    /// </summary>
    public static final int PAGE_NUMBER = 41;    // 0x0029  char style
    /// <summary>
    /// </summary>
    public static final int PLACEHOLDER_TEXT = 156;
    /// <summary>
    /// The Smart Link style.
    /// </summary>
    public static final int SMART_LINK = 371;
    /// <summary>
    /// </summary>
    public static final int STRONG = 87;    // 0x0057  char style
    /// <summary>
    /// </summary>
    public static final int SUBTLE_EMPHASIS = 260;
    /// <summary>
    /// </summary>
    public static final int SUBTLE_REFERENCE = 262;


    // Paragraph styles that have linked character styles.
    /// <summary>
    /// </summary>
    public static final int BALLOON_TEXT = 153;    // 0x0099
    /// <summary>
    /// The Body Text style.
    /// </summary>
    public static final int BODY_TEXT = 66;    // 0x0042
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_2 = 80;    // 0x0050
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_3 = 81;    // 0x0051
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_1_I = 77;    // 0x004D
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_1_I_2 = 78;    // 0x004E
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_IND = 67;    // 0x0043
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_IND_2 = 82;   // 0x0052
    /// <summary>
    /// </summary>
    public static final int BODY_TEXT_IND_3 = 83;   // 0x0053
    /// <summary>
    /// </summary>
    public static final int CLOSING = 63;    // 0x003F
    /// <summary>
    /// </summary>
    public static final int COMMENT_SUBJECT = 106;
    /// <summary>
    /// The Annotation (Comment) Text style.
    /// </summary>
    public static final int COMMENT_TEXT = 30;
    /// <summary>
    /// </summary>
    public static final int DATE = 76;    // 0X004C
    /// <summary>
    /// </summary>
    public static final int DOCUMENT_MAP = 89;    // 0x0059  char style
    /// <summary>
    /// </summary>
    public static final int EMAIL_SIGNATURE = 91; // 0x005b
    /// <summary>
    /// The Endnote Text style.
    /// </summary>
    public static final int ENDNOTE_TEXT = 43;    // 0x002B
    /// <summary>
    /// The Footer style.
    /// </summary>
    public static final int FOOTER = 32;
    /// <summary>
    /// The Footnote Text style.
    /// </summary>
    public static final int FOOTNOTE_TEXT = 29;
    /// <summary>
    /// The Header style.
    /// </summary>
    public static final int HEADER = 31;
    /// <summary>
    /// The Heading 1 style.
    /// </summary>
    public static final int HEADING_1 = 1;
    /// <summary>
    /// The Heading 2 style.
    /// </summary>
    public static final int HEADING_2 = 2;
    /// <summary>
    /// The Heading 3 style.
    /// </summary>
    public static final int HEADING_3 = 3;
    /// <summary>
    /// The Heading 4 style.
    /// </summary>
    public static final int HEADING_4 = 4;
    /// <summary>
    /// The Heading 5 style.
    /// </summary>
    public static final int HEADING_5 = 5;
    /// <summary>
    /// The Heading 6 style.
    /// </summary>
    public static final int HEADING_6 = 6;
    /// <summary>
    /// The Heading 7 style.
    /// </summary>
    public static final int HEADING_7 = 7;
    /// <summary>
    /// The Heading 8 style.
    /// </summary>
    public static final int HEADING_8 = 8;
    /// <summary>
    /// The Heading 9 style.
    /// </summary>
    public static final int HEADING_9 = 9;
    /// <summary>
    /// </summary>
    public static final int HTML_ADDRESS = 96;
    /// <summary>
    /// </summary>
    public static final int HTML_TOP_OF_FORM = 92; // 0x005c       // This is an obscure style. Can be seen in the RTF spec and on the internet.
    /// <summary>
    /// </summary>
    public static final int HTML_BOTTOM_OF_FORM = 93; // 0x005d    // This is an obscure style. Can be seen in the RTF spec and on the internet.
    /// <summary>
    /// </summary>
    public static final int HTML_PREFORMATTED = 101;
    /// <summary>
    /// </summary>
    public static final int INTENSE_QUOTE = 181;
    /// <summary>
    /// </summary>
    public static final int MACRO = 45;    // 0x002D
    /// <summary>
    /// </summary>
    public static final int MESSAGE_HEADER = 73;    // 0x0049
    /// <summary>
    /// </summary>
    public static final int NOTE_HEADING = 79;    // 0x004F
    /// <summary>
    /// </summary>
    public static final int PLAIN_TEXT = 90;    // 0x005A
    /// <summary>
    /// </summary>
    public static final int QUOTE = 180;
    /// <summary>
    /// </summary>
    public static final int SALUTATION = 75;    // 0x004B
    /// <summary>
    /// </summary>
    public static final int SIGNATURE = 64;    // 0x0040
    /// <summary>
    /// </summary>
    public static final int SUBTITLE = 74;    // 0x004A
    /// <summary>
    /// The Title style.
    /// </summary>
    public static final int TITLE = 62;    // 0x003E


    // Paragraph styles.
    /// <summary>
    /// </summary>
    public static final int BIBLIOGRAPHY = 265;
    /// <summary>
    /// </summary>
    public static final int BLOCK_TEXT = 84;    // 0x0054
    /// <summary>
    /// </summary>
    public static final int CAPTION = 34;
    /// <summary>
    /// The Envelope Address style.
    /// </summary>
    public static final int ENVELOPE_ADDRESS = 36;
    /// <summary>
    /// The Envelope Return style.
    /// </summary>
    public static final int ENVELOPE_RETURN = 37;
    /// <summary>
    /// </summary>
    public static final int INDEX_1 = 10;
    /// <summary>
    /// </summary>
    public static final int INDEX_2 = 11;
    /// <summary>
    /// </summary>
    public static final int INDEX_3 = 12;
    /// <summary>
    /// </summary>
    public static final int INDEX_4 = 13;
    /// <summary>
    /// </summary>
    public static final int INDEX_5 = 14;
    /// <summary>
    /// </summary>
    public static final int INDEX_6 = 15;
    /// <summary>
    /// </summary>
    public static final int INDEX_7 = 16;
    /// <summary>
    /// </summary>
    public static final int INDEX_8 = 17;
    /// <summary>
    /// </summary>
    public static final int INDEX_9 = 18;
    /// <summary>
    /// The Index Heading style.
    /// </summary>
    public static final int INDEX_HEADING = 33;
    /// <summary>
    /// The List style.
    /// </summary>
    public static final int LIST = 47;    // 0x002F
    /// <summary>
    /// </summary>
    public static final int LIST_2 = 50;    // 0x0032
    /// <summary>
    /// </summary>
    public static final int LIST_3 = 51;    // 0x0033
    /// <summary>
    /// </summary>
    public static final int LIST_4 = 52;    // 0x0034
    /// <summary>
    /// </summary>
    public static final int LIST_5 = 53;    // 0x0035
    /// <summary>
    /// The List Bullet style.
    /// </summary>
    public static final int LIST_BULLET = 48;    // 0x0030
    /// <summary>
    /// </summary>
    public static final int LIST_BULLET_2 = 54;    // 0x0036
    /// <summary>
    /// </summary>
    public static final int LIST_BULLET_3 = 55;    // 0x0037
    /// <summary>
    /// </summary>
    public static final int LIST_BULLET_4 = 56;    // 0x0038
    /// <summary>
    /// </summary>
    public static final int LIST_BULLET_5 = 57;    // 0x0039
    /// <summary>
    /// </summary>
    public static final int LIST_CONTINUE = 68;    // 0x0044
    /// <summary>
    /// </summary>
    public static final int LIST_CONTINUE_2 = 69;    // 0x0045
    /// <summary>
    /// </summary>
    public static final int LIST_CONTINUE_3 = 70;    // 0x0046
    /// <summary>
    /// </summary>
    public static final int LIST_CONTINUE_4 = 71;    // 0x0047
    /// <summary>
    /// </summary>
    public static final int LIST_CONTINUE_5 = 72;    // 0x0048
    /// <summary>
    /// The List Number style.
    /// </summary>
    public static final int LIST_NUMBER = 49;    // 0x0031
    /// <summary>
    /// </summary>
    public static final int LIST_NUMBER_2 = 58;    // 0x003A
    /// <summary>
    /// </summary>
    public static final int LIST_NUMBER_3 = 59;    // 0x003B
    /// <summary>
    /// </summary>
    public static final int LIST_NUMBER_4 = 60;    // 0x003C
    /// <summary>
    /// </summary>
    public static final int LIST_NUMBER_5 = 61;    // 0x003D
    /// <summary>
    /// </summary>
    public static final int LIST_PARAGRAPH = 179;
    /// <summary>
    /// </summary>
    public static final int NO_SPACING = 157;
    /// <summary>
    /// The Normal style.
    /// </summary>
    public static final int NORMAL = 0;
    /// <summary>
    /// </summary>
    public static final int NORMAL_WEB = 94;        // 0x005e
    /// <summary>
    /// The Normal Indent style.
    /// </summary>
    public static final int NORMAL_INDENT = 28;
    /// <summary>
    /// </summary>
    public static final int TABLE_OF_AUTHORITIES = 44;    // 0x002C
    /// <summary>
    /// The Table of Figures style.
    /// </summary>
    public static final int TABLE_OF_FIGURES = 35;
    /// <summary>
    /// </summary>
    public static final int TOA_HEADING = 46;    // 0x002E
    /// <summary>
    /// </summary>
    public static final int TOC_1 = 19;
    /// <summary>
    /// </summary>
    public static final int TOC_2 = 20;
    /// <summary>
    /// </summary>
    public static final int TOC_3 = 21;
    /// <summary>
    /// </summary>
    public static final int TOC_4 = 22;
    /// <summary>
    /// </summary>
    public static final int TOC_5 = 23;
    /// <summary>
    /// </summary>
    public static final int TOC_6 = 24;
    /// <summary>
    /// </summary>
    public static final int TOC_7 = 25;
    /// <summary>
    /// </summary>
    public static final int TOC_8 = 26;
    /// <summary>
    /// </summary>
    public static final int TOC_9 = 27;
    /// <summary>
    /// </summary>
    public static final int TOC_HEADING = 266;
    /// <summary>
    /// </summary>
    public static final int REVISION = 178;


    // List styles.
    /// <summary>
    /// The 1 / a / i style.
    /// </summary>
    public static final int OUTLINE_LIST_1 = 108;
    /// <summary>
    /// The 1 / 1.1 / 1.1.1 style.
    /// </summary>
    public static final int OUTLINE_LIST_2 = 109;
    /// <summary>
    /// The Article / Section style.
    /// </summary>
    public static final int OUTLINE_LIST_3 = 110;
    /// <summary>
    /// </summary>
    public static final int NO_LIST = 107;        // 0x006b


    // Table styles.
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID = 171;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_1 = 189;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_2 = 203;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_3 = 217;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_4 = 231;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_5 = 245;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_GRID_ACCENT_6 = 259;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST = 170;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_1 = 188;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_2 = 202;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_3 = 216;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_4 = 230;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_5 = 244;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_LIST_ACCENT_6 = 258;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING = 169;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_1 = 187;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_2 = 201;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_3 = 215;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_4 = 229;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_5 = 243;
    /// <summary>
    /// </summary>
    public static final int COLORFUL_SHADING_ACCENT_6 = 257;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST = 168;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_1 = 186;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_2 = 200;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_3 = 214;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_4 = 228;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_5 = 242;
    /// <summary>
    /// </summary>
    public static final int DARK_LIST_ACCENT_6 = 256;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID = 160;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_1 = 174;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_2 = 192;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_3 = 206;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_4 = 220;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_5 = 234;
    /// <summary>
    /// </summary>
    public static final int LIGHT_GRID_ACCENT_6 = 248;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST = 159;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_1 = 173;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_2 = 191;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_3 = 205;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_4 = 219;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_5 = 233;
    /// <summary>
    /// </summary>
    public static final int LIGHT_LIST_ACCENT_6 = 247;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING = 158;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_1 = 172;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_2 = 190;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_3 = 204;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_4 = 218;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_5 = 232;
    /// <summary>
    /// </summary>
    public static final int LIGHT_SHADING_ACCENT_6 = 246;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1 = 165;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_1 = 183;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_2 = 197;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_3 = 211;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_4 = 225;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_5 = 239;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_1_ACCENT_6 = 253;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2 = 166;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_1 = 184;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_2 = 198;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_3 = 212;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_4 = 226;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_5 = 240;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_2_ACCENT_6 = 254;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3 = 167;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_1 = 185;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_2 = 199;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_3 = 213;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_4 = 227;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_5 = 241;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_GRID_3_ACCENT_6 = 255;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1 = 163;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_1 = 177;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_2 = 195;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_3 = 209;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_4 = 223;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_5 = 237;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_1_ACCENT_6 = 251;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2 = 164;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_1 = 182;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_2 = 196;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_3 = 210;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_4 = 224;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_5 = 238;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_LIST_2_ACCENT_6 = 252;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1 = 161;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_1 = 175;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_2 = 193;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_3 = 207;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_4 = 221;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_5 = 235;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_1_ACCENT_6 = 249;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2 = 162;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_1 = 176;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_2 = 194;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_3 = 208;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_4 = 222;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_5 = 236;
    /// <summary>
    /// </summary>
    public static final int MEDIUM_SHADING_2_ACCENT_6 = 250;
    /// <summary>
    /// </summary>
    public static final int TABLE_3_D_EFFECTS_1 = 142;
    /// <summary>
    /// </summary>
    public static final int TABLE_3_D_EFFECTS_2 = 143;
    /// <summary>
    /// </summary>
    public static final int TABLE_3_D_EFFECTS_3 = 144;
    /// <summary>
    /// </summary>
    public static final int TABLE_CLASSIC_1 = 114;
    /// <summary>
    /// </summary>
    public static final int TABLE_CLASSIC_2 = 115;
    /// <summary>
    /// </summary>
    public static final int TABLE_CLASSIC_3 = 116;
    /// <summary>
    /// </summary>
    public static final int TABLE_CLASSIC_4 = 117;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLORFUL_1 = 118;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLORFUL_2 = 119;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLORFUL_3 = 120;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLUMNS_1 = 121;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLUMNS_2 = 122;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLUMNS_3 = 123;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLUMNS_4 = 124;
    /// <summary>
    /// </summary>
    public static final int TABLE_COLUMNS_5 = 125;
    /// <summary>
    /// </summary>
    public static final int TABLE_CONTEMPORARY = 145;
    /// <summary>
    /// </summary>
    public static final int TABLE_ELEGANT = 146;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID = 154;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_1 = 126;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_2 = 127;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_3 = 128;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_4 = 129;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_5 = 130;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_6 = 131;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_7 = 132;
    /// <summary>
    /// </summary>
    public static final int TABLE_GRID_8 = 133;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_1 = 134;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_2 = 135;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_3 = 136;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_4 = 137;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_5 = 138;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_6 = 139;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_7 = 140;
    /// <summary>
    /// </summary>
    public static final int TABLE_LIST_8 = 141;
    /// <summary>
    /// </summary>
    public static final int TABLE_NORMAL = 105;    // 0x0069
    /// <summary>
    /// </summary>
    public static final int TABLE_PROFESSIONAL = 147;
    /// <summary>
    /// </summary>
    public static final int TABLE_SIMPLE_1 = 111;
    /// <summary>
    /// </summary>
    public static final int TABLE_SIMPLE_2 = 112;
    /// <summary>
    /// </summary>
    public static final int TABLE_SIMPLE_3 = 113;
    /// <summary>
    /// </summary>
    public static final int TABLE_SUBTLE_1 = 148;
    /// <summary>
    /// </summary>
    public static final int TABLE_SUBTLE_2 = 149;
    /// <summary>
    /// </summary>
    public static final int TABLE_THEME = 155;
    /// <summary>
    /// </summary>
    public static final int TABLE_WEB_1 = 150;
    /// <summary>
    /// </summary>
    public static final int TABLE_WEB_2 = 151;
    /// <summary>
    /// </summary>
    public static final int TABLE_WEB_3 = 152;

    //>>>>>>>> #region  New table styles
    
    /// <summary>
    /// Plain Table 1
    /// </summary>
    public static final int PLAIN_TABLE_1 = 267;
    /// <summary>
    /// Plain Table 2
    /// </summary>
    public static final int PLAIN_TABLE_2 = 268;
    /// <summary>
    /// Plain Table 3
    /// </summary>
    public static final int PLAIN_TABLE_3 = 269;
    /// <summary>
    /// Plain Table 4
    /// </summary>
    public static final int PLAIN_TABLE_4 = 270;
    /// <summary>
    /// Plain Table 5
    /// </summary>
    public static final int PLAIN_TABLE_5 = 271;
    /// <summary>
    /// Table Grid Light
    /// </summary>
    public static final int TABLE_GRID_LIGHT = 272;
    /// <summary>
    /// Grid Table 1 Light
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT = 273;
    /// <summary>
    /// Grid Table 2
    /// </summary>
    public static final int GRID_TABLE_2 = 274;
    /// <summary>
    /// Grid Table 3
    /// </summary>
    public static final int GRID_TABLE_3 = 275;
    /// <summary>
    /// Grid Table 4
    /// </summary>
    public static final int GRID_TABLE_4 = 276;
    /// <summary>
    /// Grid Table 5 Dark
    /// </summary>
    public static final int GRID_TABLE_5_DARK = 277;
    /// <summary>
    /// Grid Table 6 Colorful
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL = 278;
    /// <summary>
    /// Grid Table 7 Colorful
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL = 279;
    /// <summary>
    /// Grid Table 1 Light - Accent 1
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_1 = 280;
    /// <summary>
    /// Grid Table 2 - Accent 1
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_1 = 281;
    /// <summary>
    /// Grid Table 3 - Accent 1
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_1 = 282;
    /// <summary>
    /// Grid Table 4 - Accent 1
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_1 = 283;
    /// <summary>
    /// Grid Table 5 Dark - Accent 1
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_1 = 284;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 1
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_1 = 285;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 1
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_1 = 286;
    /// <summary>
    /// Grid Table 1 Light - Accent 2
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_2 = 287;
    /// <summary>
    /// Grid Table 2 - Accent 2
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_2 = 288;
    /// <summary>
    /// Grid Table 3 - Accent 2
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_2 = 289;
    /// <summary>
    /// Grid Table 4 - Accent 2
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_2 = 290;
    /// <summary>
    /// Grid Table 5 Dark - Accent 2
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_2 = 291;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 2
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_2 = 292;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 2
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_2 = 293;
    /// <summary>
    /// Grid Table 1 Light - Accent 3
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_3 = 294;
    /// <summary>
    /// Grid Table 2 - Accent 3
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_3 = 295;
    /// <summary>
    /// Grid Table 3 - Accent 3
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_3 = 296;
    /// <summary>
    /// Grid Table 4 - Accent 3
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_3 = 297;
    /// <summary>
    /// Grid Table 5 Dark - Accent 3
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_3 = 298;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 3
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_3 = 299;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 3
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_3 = 300;
    /// <summary>
    /// Grid Table 1 Light - Accent 4
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_4 = 301;
    /// <summary>
    /// Grid Table 2 - Accent 4
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_4 = 302;
    /// <summary>
    /// Grid Table 3 - Accent 4
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_4 = 303;
    /// <summary>
    /// Grid Table 4 - Accent 4
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_4 = 304;
    /// <summary>
    /// Grid Table 5 Dark - Accent 4
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_4 = 305;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 4
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_4 = 306;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 4
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_4 = 307;
    /// <summary>
    /// Grid Table 1 Light - Accent 5
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_5 = 308;
    /// <summary>
    /// Grid Table 2 - Accent 5
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_5 = 309;
    /// <summary>
    /// Grid Table 3 - Accent 5
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_5 = 310;
    /// <summary>
    /// Grid Table 4 - Accent 5
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_5 = 311;
    /// <summary>
    /// Grid Table 5 Dark - Accent 5
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_5 = 312;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 5
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_5 = 313;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 5
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_5 = 314;
    /// <summary>
    /// Grid Table 1 Light - Accent 6
    /// </summary>
    public static final int GRID_TABLE_1_LIGHT_ACCENT_6 = 315;
    /// <summary>
    /// Grid Table 2 - Accent 6
    /// </summary>
    public static final int GRID_TABLE_2_ACCENT_6 = 316;
    /// <summary>
    /// Grid Table 3 - Accent 6
    /// </summary>
    public static final int GRID_TABLE_3_ACCENT_6 = 317;
    /// <summary>
    /// Grid Table 4 - Accent 6
    /// </summary>
    public static final int GRID_TABLE_4_ACCENT_6 = 318;
    /// <summary>
    /// Grid Table 5 Dark - Accent 6
    /// </summary>
    public static final int GRID_TABLE_5_DARK_ACCENT_6 = 319;
    /// <summary>
    /// Grid Table 6 Colorful - Accent 6
    /// </summary>
    public static final int GRID_TABLE_6_COLORFUL_ACCENT_6 = 320;
    /// <summary>
    /// Grid Table 7 Colorful - Accent 6
    /// </summary>
    public static final int GRID_TABLE_7_COLORFUL_ACCENT_6 = 321;
    /// <summary>
    /// List Table 1 Light
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT = 322;
    /// <summary>
    /// List Table 2
    /// </summary>
    public static final int LIST_TABLE_2 = 323;
    /// <summary>
    /// List Table 3
    /// </summary>
    public static final int LIST_TABLE_3 = 324;
    /// <summary>
    /// List Table 4
    /// </summary>
    public static final int LIST_TABLE_4 = 325;
    /// <summary>
    /// List Table 5 Dark
    /// </summary>
    public static final int LIST_TABLE_5_DARK = 326;
    /// <summary>
    /// List Table 6 Colorful
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL = 327;
    /// <summary>
    /// List Table 7 Colorful
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL = 328;
    /// <summary>
    /// List Table 1 Light - Accent 1
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_1 = 329;
    /// <summary>
    /// List Table 2 - Accent 1
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_1 = 330;
    /// <summary>
    /// List Table 3 - Accent 1
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_1 = 331;
    /// <summary>
    /// List Table 4 - Accent 1
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_1 = 332;
    /// <summary>
    /// List Table 5 Dark - Accent 1
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_1 = 333;
    /// <summary>
    /// List Table 6 Colorful - Accent 1
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_1 = 334;
    /// <summary>
    /// List Table 7 Colorful - Accent 1
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_1 = 335;
    /// <summary>
    /// List Table 1 Light - Accent 2
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_2 = 336;
    /// <summary>
    /// List Table 2 - Accent 2
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_2 = 337;
    /// <summary>
    /// List Table 3 - Accent 2
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_2 = 338;
    /// <summary>
    /// List Table 4 - Accent 2
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_2 = 339;
    /// <summary>
    /// List Table 5 Dark - Accent 2
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_2 = 340;
    /// <summary>
    /// List Table 6 Colorful - Accent 2
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_2 = 341;
    /// <summary>
    /// List Table 7 Colorful - Accent 2
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_2 = 342;
    /// <summary>
    /// List Table 1 Light - Accent 3
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_3 = 343;
    /// <summary>
    /// List Table 2 - Accent 3
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_3 = 344;
    /// <summary>
    /// List Table 3 - Accent 3
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_3 = 345;
    /// <summary>
    /// List Table 4 - Accent 3
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_3 = 346;
    /// <summary>
    /// List Table 5 Dark - Accent 3
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_3 = 347;
    /// <summary>
    /// List Table 6 Colorful - Accent 3
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_3 = 348;
    /// <summary>
    /// List Table 7 Colorful - Accent 3
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_3 = 349;
    /// <summary>
    /// List Table 1 Light - Accent 4
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_4 = 350;
    /// <summary>
    /// List Table 2 - Accent 4
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_4 = 351;
    /// <summary>
    /// List Table 3 - Accent 4
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_4 = 352;
    /// <summary>
    /// List Table 4 - Accent 4
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_4 = 353;
    /// <summary>
    /// List Table 5 Dark - Accent 4
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_4 = 354;
    /// <summary>
    /// List Table 6 Colorful - Accent 4
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_4 = 355;
    /// <summary>
    /// List Table 7 Colorful - Accent 4
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_4 = 356;
    /// <summary>
    /// List Table 1 Light - Accent 5
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_5 = 357;
    /// <summary>
    /// List Table 2 - Accent 5
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_5 = 358;
    /// <summary>
    /// List Table 3 - Accent 5
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_5 = 359;
    /// <summary>
    /// List Table 4 - Accent 5
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_5 = 360;
    /// <summary>
    /// List Table 5 Dark - Accent 5
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_5 = 361;
    /// <summary>
    /// List Table 6 Colorful - Accent 5
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_5 = 362;
    /// <summary>
    /// List Table 7 Colorful - Accent 5
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_5 = 363;
    /// <summary>
    /// List Table 1 Light - Accent 6
    /// </summary>
    public static final int LIST_TABLE_1_LIGHT_ACCENT_6 = 364;
    /// <summary>
    /// List Table 2 - Accent 6
    /// </summary>
    public static final int LIST_TABLE_2_ACCENT_6 = 365;
    /// <summary>
    /// List Table 3 - Accent 6
    /// </summary>
    public static final int LIST_TABLE_3_ACCENT_6 = 366;
    /// <summary>
    /// List Table 4 - Accent 6
    /// </summary>
    public static final int LIST_TABLE_4_ACCENT_6 = 367;
    /// <summary>
    /// List Table 5 Dark - Accent 6
    /// </summary>
    public static final int LIST_TABLE_5_DARK_ACCENT_6 = 368;
    /// <summary>
    /// List Table 6 Colorful - Accent 6
    /// </summary>
    public static final int LIST_TABLE_6_COLORFUL_ACCENT_6 = 369;
    /// <summary>
    /// List Table 7 Colorful - Accent 6
    /// </summary>
    public static final int LIST_TABLE_7_COLORFUL_ACCENT_6 = 370;
    //<<<<<<<< #endregion  New table styles

    /// <summary>
    /// The Mention style.
    /// </summary>
    public static final int MENTION = 372;

    /// <summary>
    /// The SmartHyperlink style.
    /// </summary>
    public static final int SMART_HYPERLINK = 373;

    /// <summary>
    /// The Hashtag style.
    /// </summary>
    public static final int HASHTAG = 374;

    /// <summary>
    /// The UnresolvedMention style.
    /// </summary>
    public static final int UNRESOLVED_MENTION = 375;

    /// <summary>
    /// A user defined style.
    /// </summary>
    public static final int USER = 0x0FFE;
    /// <summary>
    /// Reserved for internal use.
    /// </summary>
    public static final int NIL = 0x2FFF;
    // WORDSCPP workaround - get rid of cyclic dependencies between StyleIdentifier and StyleIndex
    // Nil = StyleIndex.Nil

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StyleIdentifier value.
    /// </summary>
    public static String getName(/*StyleIdentifier*/int styleIdentifier)
    {
    	switch (styleIdentifier)
    	{
    		case BOOK_TITLE: return "BOOK_TITLE";
    		case COMMENT_REFERENCE: return "COMMENT_REFERENCE";
    		case DEFAULT_PARAGRAPH_FONT: return "DEFAULT_PARAGRAPH_FONT";
    		case EMPHASIS: return "EMPHASIS";
    		case ENDNOTE_REFERENCE: return "ENDNOTE_REFERENCE";
    		case FOLLOWED_HYPERLINK: return "FOLLOWED_HYPERLINK";
    		case FOOTNOTE_REFERENCE: return "FOOTNOTE_REFERENCE";
    		case HTML_ACRONYM: return "HTML_ACRONYM";
    		case HTML_CITE: return "HTML_CITE";
    		case HTML_CODE: return "HTML_CODE";
    		case HTML_DEFINITION: return "HTML_DEFINITION";
    		case HTML_KEYBOARD: return "HTML_KEYBOARD";
    		case HTML_SAMPLE: return "HTML_SAMPLE";
    		case HTML_TYPEWRITER: return "HTML_TYPEWRITER";
    		case HTML_VARIABLE: return "HTML_VARIABLE";
    		case HYPERLINK: return "HYPERLINK";
    		case INTENSE_EMPHASIS: return "INTENSE_EMPHASIS";
    		case INTENSE_REFERENCE: return "INTENSE_REFERENCE";
    		case LINE_NUMBER: return "LINE_NUMBER";
    		case PAGE_NUMBER: return "PAGE_NUMBER";
    		case PLACEHOLDER_TEXT: return "PLACEHOLDER_TEXT";
    		case SMART_LINK: return "SMART_LINK";
    		case STRONG: return "STRONG";
    		case SUBTLE_EMPHASIS: return "SUBTLE_EMPHASIS";
    		case SUBTLE_REFERENCE: return "SUBTLE_REFERENCE";
    		case BALLOON_TEXT: return "BALLOON_TEXT";
    		case BODY_TEXT: return "BODY_TEXT";
    		case BODY_TEXT_2: return "BODY_TEXT_2";
    		case BODY_TEXT_3: return "BODY_TEXT_3";
    		case BODY_TEXT_1_I: return "BODY_TEXT_1_I";
    		case BODY_TEXT_1_I_2: return "BODY_TEXT_1_I_2";
    		case BODY_TEXT_IND: return "BODY_TEXT_IND";
    		case BODY_TEXT_IND_2: return "BODY_TEXT_IND_2";
    		case BODY_TEXT_IND_3: return "BODY_TEXT_IND_3";
    		case CLOSING: return "CLOSING";
    		case COMMENT_SUBJECT: return "COMMENT_SUBJECT";
    		case COMMENT_TEXT: return "COMMENT_TEXT";
    		case DATE: return "DATE";
    		case DOCUMENT_MAP: return "DOCUMENT_MAP";
    		case EMAIL_SIGNATURE: return "EMAIL_SIGNATURE";
    		case ENDNOTE_TEXT: return "ENDNOTE_TEXT";
    		case FOOTER: return "FOOTER";
    		case FOOTNOTE_TEXT: return "FOOTNOTE_TEXT";
    		case HEADER: return "HEADER";
    		case HEADING_1: return "HEADING_1";
    		case HEADING_2: return "HEADING_2";
    		case HEADING_3: return "HEADING_3";
    		case HEADING_4: return "HEADING_4";
    		case HEADING_5: return "HEADING_5";
    		case HEADING_6: return "HEADING_6";
    		case HEADING_7: return "HEADING_7";
    		case HEADING_8: return "HEADING_8";
    		case HEADING_9: return "HEADING_9";
    		case HTML_ADDRESS: return "HTML_ADDRESS";
    		case HTML_TOP_OF_FORM: return "HTML_TOP_OF_FORM";
    		case HTML_BOTTOM_OF_FORM: return "HTML_BOTTOM_OF_FORM";
    		case HTML_PREFORMATTED: return "HTML_PREFORMATTED";
    		case INTENSE_QUOTE: return "INTENSE_QUOTE";
    		case MACRO: return "MACRO";
    		case MESSAGE_HEADER: return "MESSAGE_HEADER";
    		case NOTE_HEADING: return "NOTE_HEADING";
    		case PLAIN_TEXT: return "PLAIN_TEXT";
    		case QUOTE: return "QUOTE";
    		case SALUTATION: return "SALUTATION";
    		case SIGNATURE: return "SIGNATURE";
    		case SUBTITLE: return "SUBTITLE";
    		case TITLE: return "TITLE";
    		case BIBLIOGRAPHY: return "BIBLIOGRAPHY";
    		case BLOCK_TEXT: return "BLOCK_TEXT";
    		case CAPTION: return "CAPTION";
    		case ENVELOPE_ADDRESS: return "ENVELOPE_ADDRESS";
    		case ENVELOPE_RETURN: return "ENVELOPE_RETURN";
    		case INDEX_1: return "INDEX_1";
    		case INDEX_2: return "INDEX_2";
    		case INDEX_3: return "INDEX_3";
    		case INDEX_4: return "INDEX_4";
    		case INDEX_5: return "INDEX_5";
    		case INDEX_6: return "INDEX_6";
    		case INDEX_7: return "INDEX_7";
    		case INDEX_8: return "INDEX_8";
    		case INDEX_9: return "INDEX_9";
    		case INDEX_HEADING: return "INDEX_HEADING";
    		case LIST: return "LIST";
    		case LIST_2: return "LIST_2";
    		case LIST_3: return "LIST_3";
    		case LIST_4: return "LIST_4";
    		case LIST_5: return "LIST_5";
    		case LIST_BULLET: return "LIST_BULLET";
    		case LIST_BULLET_2: return "LIST_BULLET_2";
    		case LIST_BULLET_3: return "LIST_BULLET_3";
    		case LIST_BULLET_4: return "LIST_BULLET_4";
    		case LIST_BULLET_5: return "LIST_BULLET_5";
    		case LIST_CONTINUE: return "LIST_CONTINUE";
    		case LIST_CONTINUE_2: return "LIST_CONTINUE_2";
    		case LIST_CONTINUE_3: return "LIST_CONTINUE_3";
    		case LIST_CONTINUE_4: return "LIST_CONTINUE_4";
    		case LIST_CONTINUE_5: return "LIST_CONTINUE_5";
    		case LIST_NUMBER: return "LIST_NUMBER";
    		case LIST_NUMBER_2: return "LIST_NUMBER_2";
    		case LIST_NUMBER_3: return "LIST_NUMBER_3";
    		case LIST_NUMBER_4: return "LIST_NUMBER_4";
    		case LIST_NUMBER_5: return "LIST_NUMBER_5";
    		case LIST_PARAGRAPH: return "LIST_PARAGRAPH";
    		case NO_SPACING: return "NO_SPACING";
    		case NORMAL: return "NORMAL";
    		case NORMAL_WEB: return "NORMAL_WEB";
    		case NORMAL_INDENT: return "NORMAL_INDENT";
    		case TABLE_OF_AUTHORITIES: return "TABLE_OF_AUTHORITIES";
    		case TABLE_OF_FIGURES: return "TABLE_OF_FIGURES";
    		case TOA_HEADING: return "TOA_HEADING";
    		case TOC_1: return "TOC_1";
    		case TOC_2: return "TOC_2";
    		case TOC_3: return "TOC_3";
    		case TOC_4: return "TOC_4";
    		case TOC_5: return "TOC_5";
    		case TOC_6: return "TOC_6";
    		case TOC_7: return "TOC_7";
    		case TOC_8: return "TOC_8";
    		case TOC_9: return "TOC_9";
    		case TOC_HEADING: return "TOC_HEADING";
    		case REVISION: return "REVISION";
    		case OUTLINE_LIST_1: return "OUTLINE_LIST_1";
    		case OUTLINE_LIST_2: return "OUTLINE_LIST_2";
    		case OUTLINE_LIST_3: return "OUTLINE_LIST_3";
    		case NO_LIST: return "NO_LIST";
    		case COLORFUL_GRID: return "COLORFUL_GRID";
    		case COLORFUL_GRID_ACCENT_1: return "COLORFUL_GRID_ACCENT_1";
    		case COLORFUL_GRID_ACCENT_2: return "COLORFUL_GRID_ACCENT_2";
    		case COLORFUL_GRID_ACCENT_3: return "COLORFUL_GRID_ACCENT_3";
    		case COLORFUL_GRID_ACCENT_4: return "COLORFUL_GRID_ACCENT_4";
    		case COLORFUL_GRID_ACCENT_5: return "COLORFUL_GRID_ACCENT_5";
    		case COLORFUL_GRID_ACCENT_6: return "COLORFUL_GRID_ACCENT_6";
    		case COLORFUL_LIST: return "COLORFUL_LIST";
    		case COLORFUL_LIST_ACCENT_1: return "COLORFUL_LIST_ACCENT_1";
    		case COLORFUL_LIST_ACCENT_2: return "COLORFUL_LIST_ACCENT_2";
    		case COLORFUL_LIST_ACCENT_3: return "COLORFUL_LIST_ACCENT_3";
    		case COLORFUL_LIST_ACCENT_4: return "COLORFUL_LIST_ACCENT_4";
    		case COLORFUL_LIST_ACCENT_5: return "COLORFUL_LIST_ACCENT_5";
    		case COLORFUL_LIST_ACCENT_6: return "COLORFUL_LIST_ACCENT_6";
    		case COLORFUL_SHADING: return "COLORFUL_SHADING";
    		case COLORFUL_SHADING_ACCENT_1: return "COLORFUL_SHADING_ACCENT_1";
    		case COLORFUL_SHADING_ACCENT_2: return "COLORFUL_SHADING_ACCENT_2";
    		case COLORFUL_SHADING_ACCENT_3: return "COLORFUL_SHADING_ACCENT_3";
    		case COLORFUL_SHADING_ACCENT_4: return "COLORFUL_SHADING_ACCENT_4";
    		case COLORFUL_SHADING_ACCENT_5: return "COLORFUL_SHADING_ACCENT_5";
    		case COLORFUL_SHADING_ACCENT_6: return "COLORFUL_SHADING_ACCENT_6";
    		case DARK_LIST: return "DARK_LIST";
    		case DARK_LIST_ACCENT_1: return "DARK_LIST_ACCENT_1";
    		case DARK_LIST_ACCENT_2: return "DARK_LIST_ACCENT_2";
    		case DARK_LIST_ACCENT_3: return "DARK_LIST_ACCENT_3";
    		case DARK_LIST_ACCENT_4: return "DARK_LIST_ACCENT_4";
    		case DARK_LIST_ACCENT_5: return "DARK_LIST_ACCENT_5";
    		case DARK_LIST_ACCENT_6: return "DARK_LIST_ACCENT_6";
    		case LIGHT_GRID: return "LIGHT_GRID";
    		case LIGHT_GRID_ACCENT_1: return "LIGHT_GRID_ACCENT_1";
    		case LIGHT_GRID_ACCENT_2: return "LIGHT_GRID_ACCENT_2";
    		case LIGHT_GRID_ACCENT_3: return "LIGHT_GRID_ACCENT_3";
    		case LIGHT_GRID_ACCENT_4: return "LIGHT_GRID_ACCENT_4";
    		case LIGHT_GRID_ACCENT_5: return "LIGHT_GRID_ACCENT_5";
    		case LIGHT_GRID_ACCENT_6: return "LIGHT_GRID_ACCENT_6";
    		case LIGHT_LIST: return "LIGHT_LIST";
    		case LIGHT_LIST_ACCENT_1: return "LIGHT_LIST_ACCENT_1";
    		case LIGHT_LIST_ACCENT_2: return "LIGHT_LIST_ACCENT_2";
    		case LIGHT_LIST_ACCENT_3: return "LIGHT_LIST_ACCENT_3";
    		case LIGHT_LIST_ACCENT_4: return "LIGHT_LIST_ACCENT_4";
    		case LIGHT_LIST_ACCENT_5: return "LIGHT_LIST_ACCENT_5";
    		case LIGHT_LIST_ACCENT_6: return "LIGHT_LIST_ACCENT_6";
    		case LIGHT_SHADING: return "LIGHT_SHADING";
    		case LIGHT_SHADING_ACCENT_1: return "LIGHT_SHADING_ACCENT_1";
    		case LIGHT_SHADING_ACCENT_2: return "LIGHT_SHADING_ACCENT_2";
    		case LIGHT_SHADING_ACCENT_3: return "LIGHT_SHADING_ACCENT_3";
    		case LIGHT_SHADING_ACCENT_4: return "LIGHT_SHADING_ACCENT_4";
    		case LIGHT_SHADING_ACCENT_5: return "LIGHT_SHADING_ACCENT_5";
    		case LIGHT_SHADING_ACCENT_6: return "LIGHT_SHADING_ACCENT_6";
    		case MEDIUM_GRID_1: return "MEDIUM_GRID_1";
    		case MEDIUM_GRID_1_ACCENT_1: return "MEDIUM_GRID_1_ACCENT_1";
    		case MEDIUM_GRID_1_ACCENT_2: return "MEDIUM_GRID_1_ACCENT_2";
    		case MEDIUM_GRID_1_ACCENT_3: return "MEDIUM_GRID_1_ACCENT_3";
    		case MEDIUM_GRID_1_ACCENT_4: return "MEDIUM_GRID_1_ACCENT_4";
    		case MEDIUM_GRID_1_ACCENT_5: return "MEDIUM_GRID_1_ACCENT_5";
    		case MEDIUM_GRID_1_ACCENT_6: return "MEDIUM_GRID_1_ACCENT_6";
    		case MEDIUM_GRID_2: return "MEDIUM_GRID_2";
    		case MEDIUM_GRID_2_ACCENT_1: return "MEDIUM_GRID_2_ACCENT_1";
    		case MEDIUM_GRID_2_ACCENT_2: return "MEDIUM_GRID_2_ACCENT_2";
    		case MEDIUM_GRID_2_ACCENT_3: return "MEDIUM_GRID_2_ACCENT_3";
    		case MEDIUM_GRID_2_ACCENT_4: return "MEDIUM_GRID_2_ACCENT_4";
    		case MEDIUM_GRID_2_ACCENT_5: return "MEDIUM_GRID_2_ACCENT_5";
    		case MEDIUM_GRID_2_ACCENT_6: return "MEDIUM_GRID_2_ACCENT_6";
    		case MEDIUM_GRID_3: return "MEDIUM_GRID_3";
    		case MEDIUM_GRID_3_ACCENT_1: return "MEDIUM_GRID_3_ACCENT_1";
    		case MEDIUM_GRID_3_ACCENT_2: return "MEDIUM_GRID_3_ACCENT_2";
    		case MEDIUM_GRID_3_ACCENT_3: return "MEDIUM_GRID_3_ACCENT_3";
    		case MEDIUM_GRID_3_ACCENT_4: return "MEDIUM_GRID_3_ACCENT_4";
    		case MEDIUM_GRID_3_ACCENT_5: return "MEDIUM_GRID_3_ACCENT_5";
    		case MEDIUM_GRID_3_ACCENT_6: return "MEDIUM_GRID_3_ACCENT_6";
    		case MEDIUM_LIST_1: return "MEDIUM_LIST_1";
    		case MEDIUM_LIST_1_ACCENT_1: return "MEDIUM_LIST_1_ACCENT_1";
    		case MEDIUM_LIST_1_ACCENT_2: return "MEDIUM_LIST_1_ACCENT_2";
    		case MEDIUM_LIST_1_ACCENT_3: return "MEDIUM_LIST_1_ACCENT_3";
    		case MEDIUM_LIST_1_ACCENT_4: return "MEDIUM_LIST_1_ACCENT_4";
    		case MEDIUM_LIST_1_ACCENT_5: return "MEDIUM_LIST_1_ACCENT_5";
    		case MEDIUM_LIST_1_ACCENT_6: return "MEDIUM_LIST_1_ACCENT_6";
    		case MEDIUM_LIST_2: return "MEDIUM_LIST_2";
    		case MEDIUM_LIST_2_ACCENT_1: return "MEDIUM_LIST_2_ACCENT_1";
    		case MEDIUM_LIST_2_ACCENT_2: return "MEDIUM_LIST_2_ACCENT_2";
    		case MEDIUM_LIST_2_ACCENT_3: return "MEDIUM_LIST_2_ACCENT_3";
    		case MEDIUM_LIST_2_ACCENT_4: return "MEDIUM_LIST_2_ACCENT_4";
    		case MEDIUM_LIST_2_ACCENT_5: return "MEDIUM_LIST_2_ACCENT_5";
    		case MEDIUM_LIST_2_ACCENT_6: return "MEDIUM_LIST_2_ACCENT_6";
    		case MEDIUM_SHADING_1: return "MEDIUM_SHADING_1";
    		case MEDIUM_SHADING_1_ACCENT_1: return "MEDIUM_SHADING_1_ACCENT_1";
    		case MEDIUM_SHADING_1_ACCENT_2: return "MEDIUM_SHADING_1_ACCENT_2";
    		case MEDIUM_SHADING_1_ACCENT_3: return "MEDIUM_SHADING_1_ACCENT_3";
    		case MEDIUM_SHADING_1_ACCENT_4: return "MEDIUM_SHADING_1_ACCENT_4";
    		case MEDIUM_SHADING_1_ACCENT_5: return "MEDIUM_SHADING_1_ACCENT_5";
    		case MEDIUM_SHADING_1_ACCENT_6: return "MEDIUM_SHADING_1_ACCENT_6";
    		case MEDIUM_SHADING_2: return "MEDIUM_SHADING_2";
    		case MEDIUM_SHADING_2_ACCENT_1: return "MEDIUM_SHADING_2_ACCENT_1";
    		case MEDIUM_SHADING_2_ACCENT_2: return "MEDIUM_SHADING_2_ACCENT_2";
    		case MEDIUM_SHADING_2_ACCENT_3: return "MEDIUM_SHADING_2_ACCENT_3";
    		case MEDIUM_SHADING_2_ACCENT_4: return "MEDIUM_SHADING_2_ACCENT_4";
    		case MEDIUM_SHADING_2_ACCENT_5: return "MEDIUM_SHADING_2_ACCENT_5";
    		case MEDIUM_SHADING_2_ACCENT_6: return "MEDIUM_SHADING_2_ACCENT_6";
    		case TABLE_3_D_EFFECTS_1: return "TABLE_3_D_EFFECTS_1";
    		case TABLE_3_D_EFFECTS_2: return "TABLE_3_D_EFFECTS_2";
    		case TABLE_3_D_EFFECTS_3: return "TABLE_3_D_EFFECTS_3";
    		case TABLE_CLASSIC_1: return "TABLE_CLASSIC_1";
    		case TABLE_CLASSIC_2: return "TABLE_CLASSIC_2";
    		case TABLE_CLASSIC_3: return "TABLE_CLASSIC_3";
    		case TABLE_CLASSIC_4: return "TABLE_CLASSIC_4";
    		case TABLE_COLORFUL_1: return "TABLE_COLORFUL_1";
    		case TABLE_COLORFUL_2: return "TABLE_COLORFUL_2";
    		case TABLE_COLORFUL_3: return "TABLE_COLORFUL_3";
    		case TABLE_COLUMNS_1: return "TABLE_COLUMNS_1";
    		case TABLE_COLUMNS_2: return "TABLE_COLUMNS_2";
    		case TABLE_COLUMNS_3: return "TABLE_COLUMNS_3";
    		case TABLE_COLUMNS_4: return "TABLE_COLUMNS_4";
    		case TABLE_COLUMNS_5: return "TABLE_COLUMNS_5";
    		case TABLE_CONTEMPORARY: return "TABLE_CONTEMPORARY";
    		case TABLE_ELEGANT: return "TABLE_ELEGANT";
    		case TABLE_GRID: return "TABLE_GRID";
    		case TABLE_GRID_1: return "TABLE_GRID_1";
    		case TABLE_GRID_2: return "TABLE_GRID_2";
    		case TABLE_GRID_3: return "TABLE_GRID_3";
    		case TABLE_GRID_4: return "TABLE_GRID_4";
    		case TABLE_GRID_5: return "TABLE_GRID_5";
    		case TABLE_GRID_6: return "TABLE_GRID_6";
    		case TABLE_GRID_7: return "TABLE_GRID_7";
    		case TABLE_GRID_8: return "TABLE_GRID_8";
    		case TABLE_LIST_1: return "TABLE_LIST_1";
    		case TABLE_LIST_2: return "TABLE_LIST_2";
    		case TABLE_LIST_3: return "TABLE_LIST_3";
    		case TABLE_LIST_4: return "TABLE_LIST_4";
    		case TABLE_LIST_5: return "TABLE_LIST_5";
    		case TABLE_LIST_6: return "TABLE_LIST_6";
    		case TABLE_LIST_7: return "TABLE_LIST_7";
    		case TABLE_LIST_8: return "TABLE_LIST_8";
    		case TABLE_NORMAL: return "TABLE_NORMAL";
    		case TABLE_PROFESSIONAL: return "TABLE_PROFESSIONAL";
    		case TABLE_SIMPLE_1: return "TABLE_SIMPLE_1";
    		case TABLE_SIMPLE_2: return "TABLE_SIMPLE_2";
    		case TABLE_SIMPLE_3: return "TABLE_SIMPLE_3";
    		case TABLE_SUBTLE_1: return "TABLE_SUBTLE_1";
    		case TABLE_SUBTLE_2: return "TABLE_SUBTLE_2";
    		case TABLE_THEME: return "TABLE_THEME";
    		case TABLE_WEB_1: return "TABLE_WEB_1";
    		case TABLE_WEB_2: return "TABLE_WEB_2";
    		case TABLE_WEB_3: return "TABLE_WEB_3";
    		case PLAIN_TABLE_1: return "PLAIN_TABLE_1";
    		case PLAIN_TABLE_2: return "PLAIN_TABLE_2";
    		case PLAIN_TABLE_3: return "PLAIN_TABLE_3";
    		case PLAIN_TABLE_4: return "PLAIN_TABLE_4";
    		case PLAIN_TABLE_5: return "PLAIN_TABLE_5";
    		case TABLE_GRID_LIGHT: return "TABLE_GRID_LIGHT";
    		case GRID_TABLE_1_LIGHT: return "GRID_TABLE_1_LIGHT";
    		case GRID_TABLE_2: return "GRID_TABLE_2";
    		case GRID_TABLE_3: return "GRID_TABLE_3";
    		case GRID_TABLE_4: return "GRID_TABLE_4";
    		case GRID_TABLE_5_DARK: return "GRID_TABLE_5_DARK";
    		case GRID_TABLE_6_COLORFUL: return "GRID_TABLE_6_COLORFUL";
    		case GRID_TABLE_7_COLORFUL: return "GRID_TABLE_7_COLORFUL";
    		case GRID_TABLE_1_LIGHT_ACCENT_1: return "GRID_TABLE_1_LIGHT_ACCENT_1";
    		case GRID_TABLE_2_ACCENT_1: return "GRID_TABLE_2_ACCENT_1";
    		case GRID_TABLE_3_ACCENT_1: return "GRID_TABLE_3_ACCENT_1";
    		case GRID_TABLE_4_ACCENT_1: return "GRID_TABLE_4_ACCENT_1";
    		case GRID_TABLE_5_DARK_ACCENT_1: return "GRID_TABLE_5_DARK_ACCENT_1";
    		case GRID_TABLE_6_COLORFUL_ACCENT_1: return "GRID_TABLE_6_COLORFUL_ACCENT_1";
    		case GRID_TABLE_7_COLORFUL_ACCENT_1: return "GRID_TABLE_7_COLORFUL_ACCENT_1";
    		case GRID_TABLE_1_LIGHT_ACCENT_2: return "GRID_TABLE_1_LIGHT_ACCENT_2";
    		case GRID_TABLE_2_ACCENT_2: return "GRID_TABLE_2_ACCENT_2";
    		case GRID_TABLE_3_ACCENT_2: return "GRID_TABLE_3_ACCENT_2";
    		case GRID_TABLE_4_ACCENT_2: return "GRID_TABLE_4_ACCENT_2";
    		case GRID_TABLE_5_DARK_ACCENT_2: return "GRID_TABLE_5_DARK_ACCENT_2";
    		case GRID_TABLE_6_COLORFUL_ACCENT_2: return "GRID_TABLE_6_COLORFUL_ACCENT_2";
    		case GRID_TABLE_7_COLORFUL_ACCENT_2: return "GRID_TABLE_7_COLORFUL_ACCENT_2";
    		case GRID_TABLE_1_LIGHT_ACCENT_3: return "GRID_TABLE_1_LIGHT_ACCENT_3";
    		case GRID_TABLE_2_ACCENT_3: return "GRID_TABLE_2_ACCENT_3";
    		case GRID_TABLE_3_ACCENT_3: return "GRID_TABLE_3_ACCENT_3";
    		case GRID_TABLE_4_ACCENT_3: return "GRID_TABLE_4_ACCENT_3";
    		case GRID_TABLE_5_DARK_ACCENT_3: return "GRID_TABLE_5_DARK_ACCENT_3";
    		case GRID_TABLE_6_COLORFUL_ACCENT_3: return "GRID_TABLE_6_COLORFUL_ACCENT_3";
    		case GRID_TABLE_7_COLORFUL_ACCENT_3: return "GRID_TABLE_7_COLORFUL_ACCENT_3";
    		case GRID_TABLE_1_LIGHT_ACCENT_4: return "GRID_TABLE_1_LIGHT_ACCENT_4";
    		case GRID_TABLE_2_ACCENT_4: return "GRID_TABLE_2_ACCENT_4";
    		case GRID_TABLE_3_ACCENT_4: return "GRID_TABLE_3_ACCENT_4";
    		case GRID_TABLE_4_ACCENT_4: return "GRID_TABLE_4_ACCENT_4";
    		case GRID_TABLE_5_DARK_ACCENT_4: return "GRID_TABLE_5_DARK_ACCENT_4";
    		case GRID_TABLE_6_COLORFUL_ACCENT_4: return "GRID_TABLE_6_COLORFUL_ACCENT_4";
    		case GRID_TABLE_7_COLORFUL_ACCENT_4: return "GRID_TABLE_7_COLORFUL_ACCENT_4";
    		case GRID_TABLE_1_LIGHT_ACCENT_5: return "GRID_TABLE_1_LIGHT_ACCENT_5";
    		case GRID_TABLE_2_ACCENT_5: return "GRID_TABLE_2_ACCENT_5";
    		case GRID_TABLE_3_ACCENT_5: return "GRID_TABLE_3_ACCENT_5";
    		case GRID_TABLE_4_ACCENT_5: return "GRID_TABLE_4_ACCENT_5";
    		case GRID_TABLE_5_DARK_ACCENT_5: return "GRID_TABLE_5_DARK_ACCENT_5";
    		case GRID_TABLE_6_COLORFUL_ACCENT_5: return "GRID_TABLE_6_COLORFUL_ACCENT_5";
    		case GRID_TABLE_7_COLORFUL_ACCENT_5: return "GRID_TABLE_7_COLORFUL_ACCENT_5";
    		case GRID_TABLE_1_LIGHT_ACCENT_6: return "GRID_TABLE_1_LIGHT_ACCENT_6";
    		case GRID_TABLE_2_ACCENT_6: return "GRID_TABLE_2_ACCENT_6";
    		case GRID_TABLE_3_ACCENT_6: return "GRID_TABLE_3_ACCENT_6";
    		case GRID_TABLE_4_ACCENT_6: return "GRID_TABLE_4_ACCENT_6";
    		case GRID_TABLE_5_DARK_ACCENT_6: return "GRID_TABLE_5_DARK_ACCENT_6";
    		case GRID_TABLE_6_COLORFUL_ACCENT_6: return "GRID_TABLE_6_COLORFUL_ACCENT_6";
    		case GRID_TABLE_7_COLORFUL_ACCENT_6: return "GRID_TABLE_7_COLORFUL_ACCENT_6";
    		case LIST_TABLE_1_LIGHT: return "LIST_TABLE_1_LIGHT";
    		case LIST_TABLE_2: return "LIST_TABLE_2";
    		case LIST_TABLE_3: return "LIST_TABLE_3";
    		case LIST_TABLE_4: return "LIST_TABLE_4";
    		case LIST_TABLE_5_DARK: return "LIST_TABLE_5_DARK";
    		case LIST_TABLE_6_COLORFUL: return "LIST_TABLE_6_COLORFUL";
    		case LIST_TABLE_7_COLORFUL: return "LIST_TABLE_7_COLORFUL";
    		case LIST_TABLE_1_LIGHT_ACCENT_1: return "LIST_TABLE_1_LIGHT_ACCENT_1";
    		case LIST_TABLE_2_ACCENT_1: return "LIST_TABLE_2_ACCENT_1";
    		case LIST_TABLE_3_ACCENT_1: return "LIST_TABLE_3_ACCENT_1";
    		case LIST_TABLE_4_ACCENT_1: return "LIST_TABLE_4_ACCENT_1";
    		case LIST_TABLE_5_DARK_ACCENT_1: return "LIST_TABLE_5_DARK_ACCENT_1";
    		case LIST_TABLE_6_COLORFUL_ACCENT_1: return "LIST_TABLE_6_COLORFUL_ACCENT_1";
    		case LIST_TABLE_7_COLORFUL_ACCENT_1: return "LIST_TABLE_7_COLORFUL_ACCENT_1";
    		case LIST_TABLE_1_LIGHT_ACCENT_2: return "LIST_TABLE_1_LIGHT_ACCENT_2";
    		case LIST_TABLE_2_ACCENT_2: return "LIST_TABLE_2_ACCENT_2";
    		case LIST_TABLE_3_ACCENT_2: return "LIST_TABLE_3_ACCENT_2";
    		case LIST_TABLE_4_ACCENT_2: return "LIST_TABLE_4_ACCENT_2";
    		case LIST_TABLE_5_DARK_ACCENT_2: return "LIST_TABLE_5_DARK_ACCENT_2";
    		case LIST_TABLE_6_COLORFUL_ACCENT_2: return "LIST_TABLE_6_COLORFUL_ACCENT_2";
    		case LIST_TABLE_7_COLORFUL_ACCENT_2: return "LIST_TABLE_7_COLORFUL_ACCENT_2";
    		case LIST_TABLE_1_LIGHT_ACCENT_3: return "LIST_TABLE_1_LIGHT_ACCENT_3";
    		case LIST_TABLE_2_ACCENT_3: return "LIST_TABLE_2_ACCENT_3";
    		case LIST_TABLE_3_ACCENT_3: return "LIST_TABLE_3_ACCENT_3";
    		case LIST_TABLE_4_ACCENT_3: return "LIST_TABLE_4_ACCENT_3";
    		case LIST_TABLE_5_DARK_ACCENT_3: return "LIST_TABLE_5_DARK_ACCENT_3";
    		case LIST_TABLE_6_COLORFUL_ACCENT_3: return "LIST_TABLE_6_COLORFUL_ACCENT_3";
    		case LIST_TABLE_7_COLORFUL_ACCENT_3: return "LIST_TABLE_7_COLORFUL_ACCENT_3";
    		case LIST_TABLE_1_LIGHT_ACCENT_4: return "LIST_TABLE_1_LIGHT_ACCENT_4";
    		case LIST_TABLE_2_ACCENT_4: return "LIST_TABLE_2_ACCENT_4";
    		case LIST_TABLE_3_ACCENT_4: return "LIST_TABLE_3_ACCENT_4";
    		case LIST_TABLE_4_ACCENT_4: return "LIST_TABLE_4_ACCENT_4";
    		case LIST_TABLE_5_DARK_ACCENT_4: return "LIST_TABLE_5_DARK_ACCENT_4";
    		case LIST_TABLE_6_COLORFUL_ACCENT_4: return "LIST_TABLE_6_COLORFUL_ACCENT_4";
    		case LIST_TABLE_7_COLORFUL_ACCENT_4: return "LIST_TABLE_7_COLORFUL_ACCENT_4";
    		case LIST_TABLE_1_LIGHT_ACCENT_5: return "LIST_TABLE_1_LIGHT_ACCENT_5";
    		case LIST_TABLE_2_ACCENT_5: return "LIST_TABLE_2_ACCENT_5";
    		case LIST_TABLE_3_ACCENT_5: return "LIST_TABLE_3_ACCENT_5";
    		case LIST_TABLE_4_ACCENT_5: return "LIST_TABLE_4_ACCENT_5";
    		case LIST_TABLE_5_DARK_ACCENT_5: return "LIST_TABLE_5_DARK_ACCENT_5";
    		case LIST_TABLE_6_COLORFUL_ACCENT_5: return "LIST_TABLE_6_COLORFUL_ACCENT_5";
    		case LIST_TABLE_7_COLORFUL_ACCENT_5: return "LIST_TABLE_7_COLORFUL_ACCENT_5";
    		case LIST_TABLE_1_LIGHT_ACCENT_6: return "LIST_TABLE_1_LIGHT_ACCENT_6";
    		case LIST_TABLE_2_ACCENT_6: return "LIST_TABLE_2_ACCENT_6";
    		case LIST_TABLE_3_ACCENT_6: return "LIST_TABLE_3_ACCENT_6";
    		case LIST_TABLE_4_ACCENT_6: return "LIST_TABLE_4_ACCENT_6";
    		case LIST_TABLE_5_DARK_ACCENT_6: return "LIST_TABLE_5_DARK_ACCENT_6";
    		case LIST_TABLE_6_COLORFUL_ACCENT_6: return "LIST_TABLE_6_COLORFUL_ACCENT_6";
    		case LIST_TABLE_7_COLORFUL_ACCENT_6: return "LIST_TABLE_7_COLORFUL_ACCENT_6";
    		case MENTION: return "MENTION";
    		case SMART_HYPERLINK: return "SMART_HYPERLINK";
    		case HASHTAG: return "HASHTAG";
    		case UNRESOLVED_MENTION: return "UNRESOLVED_MENTION";
    		case USER: return "USER";
    		case NIL: return "NIL";
    		default: return "Unknown StyleIdentifier value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StyleIdentifier value.
    /// </summary>
    public static String toString(/*StyleIdentifier*/int styleIdentifier)
    {
    	switch (styleIdentifier)
    	{
    		case BOOK_TITLE: return "BookTitle";
    		case COMMENT_REFERENCE: return "CommentReference";
    		case DEFAULT_PARAGRAPH_FONT: return "DefaultParagraphFont";
    		case EMPHASIS: return "Emphasis";
    		case ENDNOTE_REFERENCE: return "EndnoteReference";
    		case FOLLOWED_HYPERLINK: return "FollowedHyperlink";
    		case FOOTNOTE_REFERENCE: return "FootnoteReference";
    		case HTML_ACRONYM: return "HtmlAcronym";
    		case HTML_CITE: return "HtmlCite";
    		case HTML_CODE: return "HtmlCode";
    		case HTML_DEFINITION: return "HtmlDefinition";
    		case HTML_KEYBOARD: return "HtmlKeyboard";
    		case HTML_SAMPLE: return "HtmlSample";
    		case HTML_TYPEWRITER: return "HtmlTypewriter";
    		case HTML_VARIABLE: return "HtmlVariable";
    		case HYPERLINK: return "Hyperlink";
    		case INTENSE_EMPHASIS: return "IntenseEmphasis";
    		case INTENSE_REFERENCE: return "IntenseReference";
    		case LINE_NUMBER: return "LineNumber";
    		case PAGE_NUMBER: return "PageNumber";
    		case PLACEHOLDER_TEXT: return "PlaceholderText";
    		case SMART_LINK: return "SmartLink";
    		case STRONG: return "Strong";
    		case SUBTLE_EMPHASIS: return "SubtleEmphasis";
    		case SUBTLE_REFERENCE: return "SubtleReference";
    		case BALLOON_TEXT: return "BalloonText";
    		case BODY_TEXT: return "BodyText";
    		case BODY_TEXT_2: return "BodyText2";
    		case BODY_TEXT_3: return "BodyText3";
    		case BODY_TEXT_1_I: return "BodyText1I";
    		case BODY_TEXT_1_I_2: return "BodyText1I2";
    		case BODY_TEXT_IND: return "BodyTextInd";
    		case BODY_TEXT_IND_2: return "BodyTextInd2";
    		case BODY_TEXT_IND_3: return "BodyTextInd3";
    		case CLOSING: return "Closing";
    		case COMMENT_SUBJECT: return "CommentSubject";
    		case COMMENT_TEXT: return "CommentText";
    		case DATE: return "Date";
    		case DOCUMENT_MAP: return "DocumentMap";
    		case EMAIL_SIGNATURE: return "EmailSignature";
    		case ENDNOTE_TEXT: return "EndnoteText";
    		case FOOTER: return "Footer";
    		case FOOTNOTE_TEXT: return "FootnoteText";
    		case HEADER: return "Header";
    		case HEADING_1: return "Heading1";
    		case HEADING_2: return "Heading2";
    		case HEADING_3: return "Heading3";
    		case HEADING_4: return "Heading4";
    		case HEADING_5: return "Heading5";
    		case HEADING_6: return "Heading6";
    		case HEADING_7: return "Heading7";
    		case HEADING_8: return "Heading8";
    		case HEADING_9: return "Heading9";
    		case HTML_ADDRESS: return "HtmlAddress";
    		case HTML_TOP_OF_FORM: return "HtmlTopOfForm";
    		case HTML_BOTTOM_OF_FORM: return "HtmlBottomOfForm";
    		case HTML_PREFORMATTED: return "HtmlPreformatted";
    		case INTENSE_QUOTE: return "IntenseQuote";
    		case MACRO: return "Macro";
    		case MESSAGE_HEADER: return "MessageHeader";
    		case NOTE_HEADING: return "NoteHeading";
    		case PLAIN_TEXT: return "PlainText";
    		case QUOTE: return "Quote";
    		case SALUTATION: return "Salutation";
    		case SIGNATURE: return "Signature";
    		case SUBTITLE: return "Subtitle";
    		case TITLE: return "Title";
    		case BIBLIOGRAPHY: return "Bibliography";
    		case BLOCK_TEXT: return "BlockText";
    		case CAPTION: return "Caption";
    		case ENVELOPE_ADDRESS: return "EnvelopeAddress";
    		case ENVELOPE_RETURN: return "EnvelopeReturn";
    		case INDEX_1: return "Index1";
    		case INDEX_2: return "Index2";
    		case INDEX_3: return "Index3";
    		case INDEX_4: return "Index4";
    		case INDEX_5: return "Index5";
    		case INDEX_6: return "Index6";
    		case INDEX_7: return "Index7";
    		case INDEX_8: return "Index8";
    		case INDEX_9: return "Index9";
    		case INDEX_HEADING: return "IndexHeading";
    		case LIST: return "List";
    		case LIST_2: return "List2";
    		case LIST_3: return "List3";
    		case LIST_4: return "List4";
    		case LIST_5: return "List5";
    		case LIST_BULLET: return "ListBullet";
    		case LIST_BULLET_2: return "ListBullet2";
    		case LIST_BULLET_3: return "ListBullet3";
    		case LIST_BULLET_4: return "ListBullet4";
    		case LIST_BULLET_5: return "ListBullet5";
    		case LIST_CONTINUE: return "ListContinue";
    		case LIST_CONTINUE_2: return "ListContinue2";
    		case LIST_CONTINUE_3: return "ListContinue3";
    		case LIST_CONTINUE_4: return "ListContinue4";
    		case LIST_CONTINUE_5: return "ListContinue5";
    		case LIST_NUMBER: return "ListNumber";
    		case LIST_NUMBER_2: return "ListNumber2";
    		case LIST_NUMBER_3: return "ListNumber3";
    		case LIST_NUMBER_4: return "ListNumber4";
    		case LIST_NUMBER_5: return "ListNumber5";
    		case LIST_PARAGRAPH: return "ListParagraph";
    		case NO_SPACING: return "NoSpacing";
    		case NORMAL: return "Normal";
    		case NORMAL_WEB: return "NormalWeb";
    		case NORMAL_INDENT: return "NormalIndent";
    		case TABLE_OF_AUTHORITIES: return "TableOfAuthorities";
    		case TABLE_OF_FIGURES: return "TableOfFigures";
    		case TOA_HEADING: return "ToaHeading";
    		case TOC_1: return "Toc1";
    		case TOC_2: return "Toc2";
    		case TOC_3: return "Toc3";
    		case TOC_4: return "Toc4";
    		case TOC_5: return "Toc5";
    		case TOC_6: return "Toc6";
    		case TOC_7: return "Toc7";
    		case TOC_8: return "Toc8";
    		case TOC_9: return "Toc9";
    		case TOC_HEADING: return "TocHeading";
    		case REVISION: return "Revision";
    		case OUTLINE_LIST_1: return "OutlineList1";
    		case OUTLINE_LIST_2: return "OutlineList2";
    		case OUTLINE_LIST_3: return "OutlineList3";
    		case NO_LIST: return "NoList";
    		case COLORFUL_GRID: return "ColorfulGrid";
    		case COLORFUL_GRID_ACCENT_1: return "ColorfulGridAccent1";
    		case COLORFUL_GRID_ACCENT_2: return "ColorfulGridAccent2";
    		case COLORFUL_GRID_ACCENT_3: return "ColorfulGridAccent3";
    		case COLORFUL_GRID_ACCENT_4: return "ColorfulGridAccent4";
    		case COLORFUL_GRID_ACCENT_5: return "ColorfulGridAccent5";
    		case COLORFUL_GRID_ACCENT_6: return "ColorfulGridAccent6";
    		case COLORFUL_LIST: return "ColorfulList";
    		case COLORFUL_LIST_ACCENT_1: return "ColorfulListAccent1";
    		case COLORFUL_LIST_ACCENT_2: return "ColorfulListAccent2";
    		case COLORFUL_LIST_ACCENT_3: return "ColorfulListAccent3";
    		case COLORFUL_LIST_ACCENT_4: return "ColorfulListAccent4";
    		case COLORFUL_LIST_ACCENT_5: return "ColorfulListAccent5";
    		case COLORFUL_LIST_ACCENT_6: return "ColorfulListAccent6";
    		case COLORFUL_SHADING: return "ColorfulShading";
    		case COLORFUL_SHADING_ACCENT_1: return "ColorfulShadingAccent1";
    		case COLORFUL_SHADING_ACCENT_2: return "ColorfulShadingAccent2";
    		case COLORFUL_SHADING_ACCENT_3: return "ColorfulShadingAccent3";
    		case COLORFUL_SHADING_ACCENT_4: return "ColorfulShadingAccent4";
    		case COLORFUL_SHADING_ACCENT_5: return "ColorfulShadingAccent5";
    		case COLORFUL_SHADING_ACCENT_6: return "ColorfulShadingAccent6";
    		case DARK_LIST: return "DarkList";
    		case DARK_LIST_ACCENT_1: return "DarkListAccent1";
    		case DARK_LIST_ACCENT_2: return "DarkListAccent2";
    		case DARK_LIST_ACCENT_3: return "DarkListAccent3";
    		case DARK_LIST_ACCENT_4: return "DarkListAccent4";
    		case DARK_LIST_ACCENT_5: return "DarkListAccent5";
    		case DARK_LIST_ACCENT_6: return "DarkListAccent6";
    		case LIGHT_GRID: return "LightGrid";
    		case LIGHT_GRID_ACCENT_1: return "LightGridAccent1";
    		case LIGHT_GRID_ACCENT_2: return "LightGridAccent2";
    		case LIGHT_GRID_ACCENT_3: return "LightGridAccent3";
    		case LIGHT_GRID_ACCENT_4: return "LightGridAccent4";
    		case LIGHT_GRID_ACCENT_5: return "LightGridAccent5";
    		case LIGHT_GRID_ACCENT_6: return "LightGridAccent6";
    		case LIGHT_LIST: return "LightList";
    		case LIGHT_LIST_ACCENT_1: return "LightListAccent1";
    		case LIGHT_LIST_ACCENT_2: return "LightListAccent2";
    		case LIGHT_LIST_ACCENT_3: return "LightListAccent3";
    		case LIGHT_LIST_ACCENT_4: return "LightListAccent4";
    		case LIGHT_LIST_ACCENT_5: return "LightListAccent5";
    		case LIGHT_LIST_ACCENT_6: return "LightListAccent6";
    		case LIGHT_SHADING: return "LightShading";
    		case LIGHT_SHADING_ACCENT_1: return "LightShadingAccent1";
    		case LIGHT_SHADING_ACCENT_2: return "LightShadingAccent2";
    		case LIGHT_SHADING_ACCENT_3: return "LightShadingAccent3";
    		case LIGHT_SHADING_ACCENT_4: return "LightShadingAccent4";
    		case LIGHT_SHADING_ACCENT_5: return "LightShadingAccent5";
    		case LIGHT_SHADING_ACCENT_6: return "LightShadingAccent6";
    		case MEDIUM_GRID_1: return "MediumGrid1";
    		case MEDIUM_GRID_1_ACCENT_1: return "MediumGrid1Accent1";
    		case MEDIUM_GRID_1_ACCENT_2: return "MediumGrid1Accent2";
    		case MEDIUM_GRID_1_ACCENT_3: return "MediumGrid1Accent3";
    		case MEDIUM_GRID_1_ACCENT_4: return "MediumGrid1Accent4";
    		case MEDIUM_GRID_1_ACCENT_5: return "MediumGrid1Accent5";
    		case MEDIUM_GRID_1_ACCENT_6: return "MediumGrid1Accent6";
    		case MEDIUM_GRID_2: return "MediumGrid2";
    		case MEDIUM_GRID_2_ACCENT_1: return "MediumGrid2Accent1";
    		case MEDIUM_GRID_2_ACCENT_2: return "MediumGrid2Accent2";
    		case MEDIUM_GRID_2_ACCENT_3: return "MediumGrid2Accent3";
    		case MEDIUM_GRID_2_ACCENT_4: return "MediumGrid2Accent4";
    		case MEDIUM_GRID_2_ACCENT_5: return "MediumGrid2Accent5";
    		case MEDIUM_GRID_2_ACCENT_6: return "MediumGrid2Accent6";
    		case MEDIUM_GRID_3: return "MediumGrid3";
    		case MEDIUM_GRID_3_ACCENT_1: return "MediumGrid3Accent1";
    		case MEDIUM_GRID_3_ACCENT_2: return "MediumGrid3Accent2";
    		case MEDIUM_GRID_3_ACCENT_3: return "MediumGrid3Accent3";
    		case MEDIUM_GRID_3_ACCENT_4: return "MediumGrid3Accent4";
    		case MEDIUM_GRID_3_ACCENT_5: return "MediumGrid3Accent5";
    		case MEDIUM_GRID_3_ACCENT_6: return "MediumGrid3Accent6";
    		case MEDIUM_LIST_1: return "MediumList1";
    		case MEDIUM_LIST_1_ACCENT_1: return "MediumList1Accent1";
    		case MEDIUM_LIST_1_ACCENT_2: return "MediumList1Accent2";
    		case MEDIUM_LIST_1_ACCENT_3: return "MediumList1Accent3";
    		case MEDIUM_LIST_1_ACCENT_4: return "MediumList1Accent4";
    		case MEDIUM_LIST_1_ACCENT_5: return "MediumList1Accent5";
    		case MEDIUM_LIST_1_ACCENT_6: return "MediumList1Accent6";
    		case MEDIUM_LIST_2: return "MediumList2";
    		case MEDIUM_LIST_2_ACCENT_1: return "MediumList2Accent1";
    		case MEDIUM_LIST_2_ACCENT_2: return "MediumList2Accent2";
    		case MEDIUM_LIST_2_ACCENT_3: return "MediumList2Accent3";
    		case MEDIUM_LIST_2_ACCENT_4: return "MediumList2Accent4";
    		case MEDIUM_LIST_2_ACCENT_5: return "MediumList2Accent5";
    		case MEDIUM_LIST_2_ACCENT_6: return "MediumList2Accent6";
    		case MEDIUM_SHADING_1: return "MediumShading1";
    		case MEDIUM_SHADING_1_ACCENT_1: return "MediumShading1Accent1";
    		case MEDIUM_SHADING_1_ACCENT_2: return "MediumShading1Accent2";
    		case MEDIUM_SHADING_1_ACCENT_3: return "MediumShading1Accent3";
    		case MEDIUM_SHADING_1_ACCENT_4: return "MediumShading1Accent4";
    		case MEDIUM_SHADING_1_ACCENT_5: return "MediumShading1Accent5";
    		case MEDIUM_SHADING_1_ACCENT_6: return "MediumShading1Accent6";
    		case MEDIUM_SHADING_2: return "MediumShading2";
    		case MEDIUM_SHADING_2_ACCENT_1: return "MediumShading2Accent1";
    		case MEDIUM_SHADING_2_ACCENT_2: return "MediumShading2Accent2";
    		case MEDIUM_SHADING_2_ACCENT_3: return "MediumShading2Accent3";
    		case MEDIUM_SHADING_2_ACCENT_4: return "MediumShading2Accent4";
    		case MEDIUM_SHADING_2_ACCENT_5: return "MediumShading2Accent5";
    		case MEDIUM_SHADING_2_ACCENT_6: return "MediumShading2Accent6";
    		case TABLE_3_D_EFFECTS_1: return "Table3DEffects1";
    		case TABLE_3_D_EFFECTS_2: return "Table3DEffects2";
    		case TABLE_3_D_EFFECTS_3: return "Table3DEffects3";
    		case TABLE_CLASSIC_1: return "TableClassic1";
    		case TABLE_CLASSIC_2: return "TableClassic2";
    		case TABLE_CLASSIC_3: return "TableClassic3";
    		case TABLE_CLASSIC_4: return "TableClassic4";
    		case TABLE_COLORFUL_1: return "TableColorful1";
    		case TABLE_COLORFUL_2: return "TableColorful2";
    		case TABLE_COLORFUL_3: return "TableColorful3";
    		case TABLE_COLUMNS_1: return "TableColumns1";
    		case TABLE_COLUMNS_2: return "TableColumns2";
    		case TABLE_COLUMNS_3: return "TableColumns3";
    		case TABLE_COLUMNS_4: return "TableColumns4";
    		case TABLE_COLUMNS_5: return "TableColumns5";
    		case TABLE_CONTEMPORARY: return "TableContemporary";
    		case TABLE_ELEGANT: return "TableElegant";
    		case TABLE_GRID: return "TableGrid";
    		case TABLE_GRID_1: return "TableGrid1";
    		case TABLE_GRID_2: return "TableGrid2";
    		case TABLE_GRID_3: return "TableGrid3";
    		case TABLE_GRID_4: return "TableGrid4";
    		case TABLE_GRID_5: return "TableGrid5";
    		case TABLE_GRID_6: return "TableGrid6";
    		case TABLE_GRID_7: return "TableGrid7";
    		case TABLE_GRID_8: return "TableGrid8";
    		case TABLE_LIST_1: return "TableList1";
    		case TABLE_LIST_2: return "TableList2";
    		case TABLE_LIST_3: return "TableList3";
    		case TABLE_LIST_4: return "TableList4";
    		case TABLE_LIST_5: return "TableList5";
    		case TABLE_LIST_6: return "TableList6";
    		case TABLE_LIST_7: return "TableList7";
    		case TABLE_LIST_8: return "TableList8";
    		case TABLE_NORMAL: return "TableNormal";
    		case TABLE_PROFESSIONAL: return "TableProfessional";
    		case TABLE_SIMPLE_1: return "TableSimple1";
    		case TABLE_SIMPLE_2: return "TableSimple2";
    		case TABLE_SIMPLE_3: return "TableSimple3";
    		case TABLE_SUBTLE_1: return "TableSubtle1";
    		case TABLE_SUBTLE_2: return "TableSubtle2";
    		case TABLE_THEME: return "TableTheme";
    		case TABLE_WEB_1: return "TableWeb1";
    		case TABLE_WEB_2: return "TableWeb2";
    		case TABLE_WEB_3: return "TableWeb3";
    		case PLAIN_TABLE_1: return "PlainTable1";
    		case PLAIN_TABLE_2: return "PlainTable2";
    		case PLAIN_TABLE_3: return "PlainTable3";
    		case PLAIN_TABLE_4: return "PlainTable4";
    		case PLAIN_TABLE_5: return "PlainTable5";
    		case TABLE_GRID_LIGHT: return "TableGridLight";
    		case GRID_TABLE_1_LIGHT: return "GridTable1Light";
    		case GRID_TABLE_2: return "GridTable2";
    		case GRID_TABLE_3: return "GridTable3";
    		case GRID_TABLE_4: return "GridTable4";
    		case GRID_TABLE_5_DARK: return "GridTable5Dark";
    		case GRID_TABLE_6_COLORFUL: return "GridTable6Colorful";
    		case GRID_TABLE_7_COLORFUL: return "GridTable7Colorful";
    		case GRID_TABLE_1_LIGHT_ACCENT_1: return "GridTable1LightAccent1";
    		case GRID_TABLE_2_ACCENT_1: return "GridTable2Accent1";
    		case GRID_TABLE_3_ACCENT_1: return "GridTable3Accent1";
    		case GRID_TABLE_4_ACCENT_1: return "GridTable4Accent1";
    		case GRID_TABLE_5_DARK_ACCENT_1: return "GridTable5DarkAccent1";
    		case GRID_TABLE_6_COLORFUL_ACCENT_1: return "GridTable6ColorfulAccent1";
    		case GRID_TABLE_7_COLORFUL_ACCENT_1: return "GridTable7ColorfulAccent1";
    		case GRID_TABLE_1_LIGHT_ACCENT_2: return "GridTable1LightAccent2";
    		case GRID_TABLE_2_ACCENT_2: return "GridTable2Accent2";
    		case GRID_TABLE_3_ACCENT_2: return "GridTable3Accent2";
    		case GRID_TABLE_4_ACCENT_2: return "GridTable4Accent2";
    		case GRID_TABLE_5_DARK_ACCENT_2: return "GridTable5DarkAccent2";
    		case GRID_TABLE_6_COLORFUL_ACCENT_2: return "GridTable6ColorfulAccent2";
    		case GRID_TABLE_7_COLORFUL_ACCENT_2: return "GridTable7ColorfulAccent2";
    		case GRID_TABLE_1_LIGHT_ACCENT_3: return "GridTable1LightAccent3";
    		case GRID_TABLE_2_ACCENT_3: return "GridTable2Accent3";
    		case GRID_TABLE_3_ACCENT_3: return "GridTable3Accent3";
    		case GRID_TABLE_4_ACCENT_3: return "GridTable4Accent3";
    		case GRID_TABLE_5_DARK_ACCENT_3: return "GridTable5DarkAccent3";
    		case GRID_TABLE_6_COLORFUL_ACCENT_3: return "GridTable6ColorfulAccent3";
    		case GRID_TABLE_7_COLORFUL_ACCENT_3: return "GridTable7ColorfulAccent3";
    		case GRID_TABLE_1_LIGHT_ACCENT_4: return "GridTable1LightAccent4";
    		case GRID_TABLE_2_ACCENT_4: return "GridTable2Accent4";
    		case GRID_TABLE_3_ACCENT_4: return "GridTable3Accent4";
    		case GRID_TABLE_4_ACCENT_4: return "GridTable4Accent4";
    		case GRID_TABLE_5_DARK_ACCENT_4: return "GridTable5DarkAccent4";
    		case GRID_TABLE_6_COLORFUL_ACCENT_4: return "GridTable6ColorfulAccent4";
    		case GRID_TABLE_7_COLORFUL_ACCENT_4: return "GridTable7ColorfulAccent4";
    		case GRID_TABLE_1_LIGHT_ACCENT_5: return "GridTable1LightAccent5";
    		case GRID_TABLE_2_ACCENT_5: return "GridTable2Accent5";
    		case GRID_TABLE_3_ACCENT_5: return "GridTable3Accent5";
    		case GRID_TABLE_4_ACCENT_5: return "GridTable4Accent5";
    		case GRID_TABLE_5_DARK_ACCENT_5: return "GridTable5DarkAccent5";
    		case GRID_TABLE_6_COLORFUL_ACCENT_5: return "GridTable6ColorfulAccent5";
    		case GRID_TABLE_7_COLORFUL_ACCENT_5: return "GridTable7ColorfulAccent5";
    		case GRID_TABLE_1_LIGHT_ACCENT_6: return "GridTable1LightAccent6";
    		case GRID_TABLE_2_ACCENT_6: return "GridTable2Accent6";
    		case GRID_TABLE_3_ACCENT_6: return "GridTable3Accent6";
    		case GRID_TABLE_4_ACCENT_6: return "GridTable4Accent6";
    		case GRID_TABLE_5_DARK_ACCENT_6: return "GridTable5DarkAccent6";
    		case GRID_TABLE_6_COLORFUL_ACCENT_6: return "GridTable6ColorfulAccent6";
    		case GRID_TABLE_7_COLORFUL_ACCENT_6: return "GridTable7ColorfulAccent6";
    		case LIST_TABLE_1_LIGHT: return "ListTable1Light";
    		case LIST_TABLE_2: return "ListTable2";
    		case LIST_TABLE_3: return "ListTable3";
    		case LIST_TABLE_4: return "ListTable4";
    		case LIST_TABLE_5_DARK: return "ListTable5Dark";
    		case LIST_TABLE_6_COLORFUL: return "ListTable6Colorful";
    		case LIST_TABLE_7_COLORFUL: return "ListTable7Colorful";
    		case LIST_TABLE_1_LIGHT_ACCENT_1: return "ListTable1LightAccent1";
    		case LIST_TABLE_2_ACCENT_1: return "ListTable2Accent1";
    		case LIST_TABLE_3_ACCENT_1: return "ListTable3Accent1";
    		case LIST_TABLE_4_ACCENT_1: return "ListTable4Accent1";
    		case LIST_TABLE_5_DARK_ACCENT_1: return "ListTable5DarkAccent1";
    		case LIST_TABLE_6_COLORFUL_ACCENT_1: return "ListTable6ColorfulAccent1";
    		case LIST_TABLE_7_COLORFUL_ACCENT_1: return "ListTable7ColorfulAccent1";
    		case LIST_TABLE_1_LIGHT_ACCENT_2: return "ListTable1LightAccent2";
    		case LIST_TABLE_2_ACCENT_2: return "ListTable2Accent2";
    		case LIST_TABLE_3_ACCENT_2: return "ListTable3Accent2";
    		case LIST_TABLE_4_ACCENT_2: return "ListTable4Accent2";
    		case LIST_TABLE_5_DARK_ACCENT_2: return "ListTable5DarkAccent2";
    		case LIST_TABLE_6_COLORFUL_ACCENT_2: return "ListTable6ColorfulAccent2";
    		case LIST_TABLE_7_COLORFUL_ACCENT_2: return "ListTable7ColorfulAccent2";
    		case LIST_TABLE_1_LIGHT_ACCENT_3: return "ListTable1LightAccent3";
    		case LIST_TABLE_2_ACCENT_3: return "ListTable2Accent3";
    		case LIST_TABLE_3_ACCENT_3: return "ListTable3Accent3";
    		case LIST_TABLE_4_ACCENT_3: return "ListTable4Accent3";
    		case LIST_TABLE_5_DARK_ACCENT_3: return "ListTable5DarkAccent3";
    		case LIST_TABLE_6_COLORFUL_ACCENT_3: return "ListTable6ColorfulAccent3";
    		case LIST_TABLE_7_COLORFUL_ACCENT_3: return "ListTable7ColorfulAccent3";
    		case LIST_TABLE_1_LIGHT_ACCENT_4: return "ListTable1LightAccent4";
    		case LIST_TABLE_2_ACCENT_4: return "ListTable2Accent4";
    		case LIST_TABLE_3_ACCENT_4: return "ListTable3Accent4";
    		case LIST_TABLE_4_ACCENT_4: return "ListTable4Accent4";
    		case LIST_TABLE_5_DARK_ACCENT_4: return "ListTable5DarkAccent4";
    		case LIST_TABLE_6_COLORFUL_ACCENT_4: return "ListTable6ColorfulAccent4";
    		case LIST_TABLE_7_COLORFUL_ACCENT_4: return "ListTable7ColorfulAccent4";
    		case LIST_TABLE_1_LIGHT_ACCENT_5: return "ListTable1LightAccent5";
    		case LIST_TABLE_2_ACCENT_5: return "ListTable2Accent5";
    		case LIST_TABLE_3_ACCENT_5: return "ListTable3Accent5";
    		case LIST_TABLE_4_ACCENT_5: return "ListTable4Accent5";
    		case LIST_TABLE_5_DARK_ACCENT_5: return "ListTable5DarkAccent5";
    		case LIST_TABLE_6_COLORFUL_ACCENT_5: return "ListTable6ColorfulAccent5";
    		case LIST_TABLE_7_COLORFUL_ACCENT_5: return "ListTable7ColorfulAccent5";
    		case LIST_TABLE_1_LIGHT_ACCENT_6: return "ListTable1LightAccent6";
    		case LIST_TABLE_2_ACCENT_6: return "ListTable2Accent6";
    		case LIST_TABLE_3_ACCENT_6: return "ListTable3Accent6";
    		case LIST_TABLE_4_ACCENT_6: return "ListTable4Accent6";
    		case LIST_TABLE_5_DARK_ACCENT_6: return "ListTable5DarkAccent6";
    		case LIST_TABLE_6_COLORFUL_ACCENT_6: return "ListTable6ColorfulAccent6";
    		case LIST_TABLE_7_COLORFUL_ACCENT_6: return "ListTable7ColorfulAccent6";
    		case MENTION: return "Mention";
    		case SMART_HYPERLINK: return "SmartHyperlink";
    		case HASHTAG: return "Hashtag";
    		case UNRESOLVED_MENTION: return "UnresolvedMention";
    		case USER: return "User";
    		case NIL: return "Nil";
    		default: return "Unknown StyleIdentifier value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by StyleIdentifier name.
    /// </summary>
    public static /*StyleIdentifier*/int fromName(String styleIdentifierName)
    {
    	if ("BOOK_TITLE".equals(styleIdentifierName)) return BOOK_TITLE;
    	if ("COMMENT_REFERENCE".equals(styleIdentifierName)) return COMMENT_REFERENCE;
    	if ("DEFAULT_PARAGRAPH_FONT".equals(styleIdentifierName)) return DEFAULT_PARAGRAPH_FONT;
    	if ("EMPHASIS".equals(styleIdentifierName)) return EMPHASIS;
    	if ("ENDNOTE_REFERENCE".equals(styleIdentifierName)) return ENDNOTE_REFERENCE;
    	if ("FOLLOWED_HYPERLINK".equals(styleIdentifierName)) return FOLLOWED_HYPERLINK;
    	if ("FOOTNOTE_REFERENCE".equals(styleIdentifierName)) return FOOTNOTE_REFERENCE;
    	if ("HTML_ACRONYM".equals(styleIdentifierName)) return HTML_ACRONYM;
    	if ("HTML_CITE".equals(styleIdentifierName)) return HTML_CITE;
    	if ("HTML_CODE".equals(styleIdentifierName)) return HTML_CODE;
    	if ("HTML_DEFINITION".equals(styleIdentifierName)) return HTML_DEFINITION;
    	if ("HTML_KEYBOARD".equals(styleIdentifierName)) return HTML_KEYBOARD;
    	if ("HTML_SAMPLE".equals(styleIdentifierName)) return HTML_SAMPLE;
    	if ("HTML_TYPEWRITER".equals(styleIdentifierName)) return HTML_TYPEWRITER;
    	if ("HTML_VARIABLE".equals(styleIdentifierName)) return HTML_VARIABLE;
    	if ("HYPERLINK".equals(styleIdentifierName)) return HYPERLINK;
    	if ("INTENSE_EMPHASIS".equals(styleIdentifierName)) return INTENSE_EMPHASIS;
    	if ("INTENSE_REFERENCE".equals(styleIdentifierName)) return INTENSE_REFERENCE;
    	if ("LINE_NUMBER".equals(styleIdentifierName)) return LINE_NUMBER;
    	if ("PAGE_NUMBER".equals(styleIdentifierName)) return PAGE_NUMBER;
    	if ("PLACEHOLDER_TEXT".equals(styleIdentifierName)) return PLACEHOLDER_TEXT;
    	if ("SMART_LINK".equals(styleIdentifierName)) return SMART_LINK;
    	if ("STRONG".equals(styleIdentifierName)) return STRONG;
    	if ("SUBTLE_EMPHASIS".equals(styleIdentifierName)) return SUBTLE_EMPHASIS;
    	if ("SUBTLE_REFERENCE".equals(styleIdentifierName)) return SUBTLE_REFERENCE;
    	if ("BALLOON_TEXT".equals(styleIdentifierName)) return BALLOON_TEXT;
    	if ("BODY_TEXT".equals(styleIdentifierName)) return BODY_TEXT;
    	if ("BODY_TEXT_2".equals(styleIdentifierName)) return BODY_TEXT_2;
    	if ("BODY_TEXT_3".equals(styleIdentifierName)) return BODY_TEXT_3;
    	if ("BODY_TEXT_1_I".equals(styleIdentifierName)) return BODY_TEXT_1_I;
    	if ("BODY_TEXT_1_I_2".equals(styleIdentifierName)) return BODY_TEXT_1_I_2;
    	if ("BODY_TEXT_IND".equals(styleIdentifierName)) return BODY_TEXT_IND;
    	if ("BODY_TEXT_IND_2".equals(styleIdentifierName)) return BODY_TEXT_IND_2;
    	if ("BODY_TEXT_IND_3".equals(styleIdentifierName)) return BODY_TEXT_IND_3;
    	if ("CLOSING".equals(styleIdentifierName)) return CLOSING;
    	if ("COMMENT_SUBJECT".equals(styleIdentifierName)) return COMMENT_SUBJECT;
    	if ("COMMENT_TEXT".equals(styleIdentifierName)) return COMMENT_TEXT;
    	if ("DATE".equals(styleIdentifierName)) return DATE;
    	if ("DOCUMENT_MAP".equals(styleIdentifierName)) return DOCUMENT_MAP;
    	if ("EMAIL_SIGNATURE".equals(styleIdentifierName)) return EMAIL_SIGNATURE;
    	if ("ENDNOTE_TEXT".equals(styleIdentifierName)) return ENDNOTE_TEXT;
    	if ("FOOTER".equals(styleIdentifierName)) return FOOTER;
    	if ("FOOTNOTE_TEXT".equals(styleIdentifierName)) return FOOTNOTE_TEXT;
    	if ("HEADER".equals(styleIdentifierName)) return HEADER;
    	if ("HEADING_1".equals(styleIdentifierName)) return HEADING_1;
    	if ("HEADING_2".equals(styleIdentifierName)) return HEADING_2;
    	if ("HEADING_3".equals(styleIdentifierName)) return HEADING_3;
    	if ("HEADING_4".equals(styleIdentifierName)) return HEADING_4;
    	if ("HEADING_5".equals(styleIdentifierName)) return HEADING_5;
    	if ("HEADING_6".equals(styleIdentifierName)) return HEADING_6;
    	if ("HEADING_7".equals(styleIdentifierName)) return HEADING_7;
    	if ("HEADING_8".equals(styleIdentifierName)) return HEADING_8;
    	if ("HEADING_9".equals(styleIdentifierName)) return HEADING_9;
    	if ("HTML_ADDRESS".equals(styleIdentifierName)) return HTML_ADDRESS;
    	if ("HTML_TOP_OF_FORM".equals(styleIdentifierName)) return HTML_TOP_OF_FORM;
    	if ("HTML_BOTTOM_OF_FORM".equals(styleIdentifierName)) return HTML_BOTTOM_OF_FORM;
    	if ("HTML_PREFORMATTED".equals(styleIdentifierName)) return HTML_PREFORMATTED;
    	if ("INTENSE_QUOTE".equals(styleIdentifierName)) return INTENSE_QUOTE;
    	if ("MACRO".equals(styleIdentifierName)) return MACRO;
    	if ("MESSAGE_HEADER".equals(styleIdentifierName)) return MESSAGE_HEADER;
    	if ("NOTE_HEADING".equals(styleIdentifierName)) return NOTE_HEADING;
    	if ("PLAIN_TEXT".equals(styleIdentifierName)) return PLAIN_TEXT;
    	if ("QUOTE".equals(styleIdentifierName)) return QUOTE;
    	if ("SALUTATION".equals(styleIdentifierName)) return SALUTATION;
    	if ("SIGNATURE".equals(styleIdentifierName)) return SIGNATURE;
    	if ("SUBTITLE".equals(styleIdentifierName)) return SUBTITLE;
    	if ("TITLE".equals(styleIdentifierName)) return TITLE;
    	if ("BIBLIOGRAPHY".equals(styleIdentifierName)) return BIBLIOGRAPHY;
    	if ("BLOCK_TEXT".equals(styleIdentifierName)) return BLOCK_TEXT;
    	if ("CAPTION".equals(styleIdentifierName)) return CAPTION;
    	if ("ENVELOPE_ADDRESS".equals(styleIdentifierName)) return ENVELOPE_ADDRESS;
    	if ("ENVELOPE_RETURN".equals(styleIdentifierName)) return ENVELOPE_RETURN;
    	if ("INDEX_1".equals(styleIdentifierName)) return INDEX_1;
    	if ("INDEX_2".equals(styleIdentifierName)) return INDEX_2;
    	if ("INDEX_3".equals(styleIdentifierName)) return INDEX_3;
    	if ("INDEX_4".equals(styleIdentifierName)) return INDEX_4;
    	if ("INDEX_5".equals(styleIdentifierName)) return INDEX_5;
    	if ("INDEX_6".equals(styleIdentifierName)) return INDEX_6;
    	if ("INDEX_7".equals(styleIdentifierName)) return INDEX_7;
    	if ("INDEX_8".equals(styleIdentifierName)) return INDEX_8;
    	if ("INDEX_9".equals(styleIdentifierName)) return INDEX_9;
    	if ("INDEX_HEADING".equals(styleIdentifierName)) return INDEX_HEADING;
    	if ("LIST".equals(styleIdentifierName)) return LIST;
    	if ("LIST_2".equals(styleIdentifierName)) return LIST_2;
    	if ("LIST_3".equals(styleIdentifierName)) return LIST_3;
    	if ("LIST_4".equals(styleIdentifierName)) return LIST_4;
    	if ("LIST_5".equals(styleIdentifierName)) return LIST_5;
    	if ("LIST_BULLET".equals(styleIdentifierName)) return LIST_BULLET;
    	if ("LIST_BULLET_2".equals(styleIdentifierName)) return LIST_BULLET_2;
    	if ("LIST_BULLET_3".equals(styleIdentifierName)) return LIST_BULLET_3;
    	if ("LIST_BULLET_4".equals(styleIdentifierName)) return LIST_BULLET_4;
    	if ("LIST_BULLET_5".equals(styleIdentifierName)) return LIST_BULLET_5;
    	if ("LIST_CONTINUE".equals(styleIdentifierName)) return LIST_CONTINUE;
    	if ("LIST_CONTINUE_2".equals(styleIdentifierName)) return LIST_CONTINUE_2;
    	if ("LIST_CONTINUE_3".equals(styleIdentifierName)) return LIST_CONTINUE_3;
    	if ("LIST_CONTINUE_4".equals(styleIdentifierName)) return LIST_CONTINUE_4;
    	if ("LIST_CONTINUE_5".equals(styleIdentifierName)) return LIST_CONTINUE_5;
    	if ("LIST_NUMBER".equals(styleIdentifierName)) return LIST_NUMBER;
    	if ("LIST_NUMBER_2".equals(styleIdentifierName)) return LIST_NUMBER_2;
    	if ("LIST_NUMBER_3".equals(styleIdentifierName)) return LIST_NUMBER_3;
    	if ("LIST_NUMBER_4".equals(styleIdentifierName)) return LIST_NUMBER_4;
    	if ("LIST_NUMBER_5".equals(styleIdentifierName)) return LIST_NUMBER_5;
    	if ("LIST_PARAGRAPH".equals(styleIdentifierName)) return LIST_PARAGRAPH;
    	if ("NO_SPACING".equals(styleIdentifierName)) return NO_SPACING;
    	if ("NORMAL".equals(styleIdentifierName)) return NORMAL;
    	if ("NORMAL_WEB".equals(styleIdentifierName)) return NORMAL_WEB;
    	if ("NORMAL_INDENT".equals(styleIdentifierName)) return NORMAL_INDENT;
    	if ("TABLE_OF_AUTHORITIES".equals(styleIdentifierName)) return TABLE_OF_AUTHORITIES;
    	if ("TABLE_OF_FIGURES".equals(styleIdentifierName)) return TABLE_OF_FIGURES;
    	if ("TOA_HEADING".equals(styleIdentifierName)) return TOA_HEADING;
    	if ("TOC_1".equals(styleIdentifierName)) return TOC_1;
    	if ("TOC_2".equals(styleIdentifierName)) return TOC_2;
    	if ("TOC_3".equals(styleIdentifierName)) return TOC_3;
    	if ("TOC_4".equals(styleIdentifierName)) return TOC_4;
    	if ("TOC_5".equals(styleIdentifierName)) return TOC_5;
    	if ("TOC_6".equals(styleIdentifierName)) return TOC_6;
    	if ("TOC_7".equals(styleIdentifierName)) return TOC_7;
    	if ("TOC_8".equals(styleIdentifierName)) return TOC_8;
    	if ("TOC_9".equals(styleIdentifierName)) return TOC_9;
    	if ("TOC_HEADING".equals(styleIdentifierName)) return TOC_HEADING;
    	if ("REVISION".equals(styleIdentifierName)) return REVISION;
    	if ("OUTLINE_LIST_1".equals(styleIdentifierName)) return OUTLINE_LIST_1;
    	if ("OUTLINE_LIST_2".equals(styleIdentifierName)) return OUTLINE_LIST_2;
    	if ("OUTLINE_LIST_3".equals(styleIdentifierName)) return OUTLINE_LIST_3;
    	if ("NO_LIST".equals(styleIdentifierName)) return NO_LIST;
    	if ("COLORFUL_GRID".equals(styleIdentifierName)) return COLORFUL_GRID;
    	if ("COLORFUL_GRID_ACCENT_1".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_1;
    	if ("COLORFUL_GRID_ACCENT_2".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_2;
    	if ("COLORFUL_GRID_ACCENT_3".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_3;
    	if ("COLORFUL_GRID_ACCENT_4".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_4;
    	if ("COLORFUL_GRID_ACCENT_5".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_5;
    	if ("COLORFUL_GRID_ACCENT_6".equals(styleIdentifierName)) return COLORFUL_GRID_ACCENT_6;
    	if ("COLORFUL_LIST".equals(styleIdentifierName)) return COLORFUL_LIST;
    	if ("COLORFUL_LIST_ACCENT_1".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_1;
    	if ("COLORFUL_LIST_ACCENT_2".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_2;
    	if ("COLORFUL_LIST_ACCENT_3".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_3;
    	if ("COLORFUL_LIST_ACCENT_4".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_4;
    	if ("COLORFUL_LIST_ACCENT_5".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_5;
    	if ("COLORFUL_LIST_ACCENT_6".equals(styleIdentifierName)) return COLORFUL_LIST_ACCENT_6;
    	if ("COLORFUL_SHADING".equals(styleIdentifierName)) return COLORFUL_SHADING;
    	if ("COLORFUL_SHADING_ACCENT_1".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_1;
    	if ("COLORFUL_SHADING_ACCENT_2".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_2;
    	if ("COLORFUL_SHADING_ACCENT_3".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_3;
    	if ("COLORFUL_SHADING_ACCENT_4".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_4;
    	if ("COLORFUL_SHADING_ACCENT_5".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_5;
    	if ("COLORFUL_SHADING_ACCENT_6".equals(styleIdentifierName)) return COLORFUL_SHADING_ACCENT_6;
    	if ("DARK_LIST".equals(styleIdentifierName)) return DARK_LIST;
    	if ("DARK_LIST_ACCENT_1".equals(styleIdentifierName)) return DARK_LIST_ACCENT_1;
    	if ("DARK_LIST_ACCENT_2".equals(styleIdentifierName)) return DARK_LIST_ACCENT_2;
    	if ("DARK_LIST_ACCENT_3".equals(styleIdentifierName)) return DARK_LIST_ACCENT_3;
    	if ("DARK_LIST_ACCENT_4".equals(styleIdentifierName)) return DARK_LIST_ACCENT_4;
    	if ("DARK_LIST_ACCENT_5".equals(styleIdentifierName)) return DARK_LIST_ACCENT_5;
    	if ("DARK_LIST_ACCENT_6".equals(styleIdentifierName)) return DARK_LIST_ACCENT_6;
    	if ("LIGHT_GRID".equals(styleIdentifierName)) return LIGHT_GRID;
    	if ("LIGHT_GRID_ACCENT_1".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_1;
    	if ("LIGHT_GRID_ACCENT_2".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_2;
    	if ("LIGHT_GRID_ACCENT_3".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_3;
    	if ("LIGHT_GRID_ACCENT_4".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_4;
    	if ("LIGHT_GRID_ACCENT_5".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_5;
    	if ("LIGHT_GRID_ACCENT_6".equals(styleIdentifierName)) return LIGHT_GRID_ACCENT_6;
    	if ("LIGHT_LIST".equals(styleIdentifierName)) return LIGHT_LIST;
    	if ("LIGHT_LIST_ACCENT_1".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_1;
    	if ("LIGHT_LIST_ACCENT_2".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_2;
    	if ("LIGHT_LIST_ACCENT_3".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_3;
    	if ("LIGHT_LIST_ACCENT_4".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_4;
    	if ("LIGHT_LIST_ACCENT_5".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_5;
    	if ("LIGHT_LIST_ACCENT_6".equals(styleIdentifierName)) return LIGHT_LIST_ACCENT_6;
    	if ("LIGHT_SHADING".equals(styleIdentifierName)) return LIGHT_SHADING;
    	if ("LIGHT_SHADING_ACCENT_1".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_1;
    	if ("LIGHT_SHADING_ACCENT_2".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_2;
    	if ("LIGHT_SHADING_ACCENT_3".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_3;
    	if ("LIGHT_SHADING_ACCENT_4".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_4;
    	if ("LIGHT_SHADING_ACCENT_5".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_5;
    	if ("LIGHT_SHADING_ACCENT_6".equals(styleIdentifierName)) return LIGHT_SHADING_ACCENT_6;
    	if ("MEDIUM_GRID_1".equals(styleIdentifierName)) return MEDIUM_GRID_1;
    	if ("MEDIUM_GRID_1_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_1;
    	if ("MEDIUM_GRID_1_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_2;
    	if ("MEDIUM_GRID_1_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_3;
    	if ("MEDIUM_GRID_1_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_4;
    	if ("MEDIUM_GRID_1_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_5;
    	if ("MEDIUM_GRID_1_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_GRID_1_ACCENT_6;
    	if ("MEDIUM_GRID_2".equals(styleIdentifierName)) return MEDIUM_GRID_2;
    	if ("MEDIUM_GRID_2_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_1;
    	if ("MEDIUM_GRID_2_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_2;
    	if ("MEDIUM_GRID_2_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_3;
    	if ("MEDIUM_GRID_2_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_4;
    	if ("MEDIUM_GRID_2_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_5;
    	if ("MEDIUM_GRID_2_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_GRID_2_ACCENT_6;
    	if ("MEDIUM_GRID_3".equals(styleIdentifierName)) return MEDIUM_GRID_3;
    	if ("MEDIUM_GRID_3_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_1;
    	if ("MEDIUM_GRID_3_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_2;
    	if ("MEDIUM_GRID_3_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_3;
    	if ("MEDIUM_GRID_3_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_4;
    	if ("MEDIUM_GRID_3_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_5;
    	if ("MEDIUM_GRID_3_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_GRID_3_ACCENT_6;
    	if ("MEDIUM_LIST_1".equals(styleIdentifierName)) return MEDIUM_LIST_1;
    	if ("MEDIUM_LIST_1_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_1;
    	if ("MEDIUM_LIST_1_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_2;
    	if ("MEDIUM_LIST_1_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_3;
    	if ("MEDIUM_LIST_1_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_4;
    	if ("MEDIUM_LIST_1_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_5;
    	if ("MEDIUM_LIST_1_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_LIST_1_ACCENT_6;
    	if ("MEDIUM_LIST_2".equals(styleIdentifierName)) return MEDIUM_LIST_2;
    	if ("MEDIUM_LIST_2_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_1;
    	if ("MEDIUM_LIST_2_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_2;
    	if ("MEDIUM_LIST_2_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_3;
    	if ("MEDIUM_LIST_2_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_4;
    	if ("MEDIUM_LIST_2_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_5;
    	if ("MEDIUM_LIST_2_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_LIST_2_ACCENT_6;
    	if ("MEDIUM_SHADING_1".equals(styleIdentifierName)) return MEDIUM_SHADING_1;
    	if ("MEDIUM_SHADING_1_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_1;
    	if ("MEDIUM_SHADING_1_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_2;
    	if ("MEDIUM_SHADING_1_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_3;
    	if ("MEDIUM_SHADING_1_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_4;
    	if ("MEDIUM_SHADING_1_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_5;
    	if ("MEDIUM_SHADING_1_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_SHADING_1_ACCENT_6;
    	if ("MEDIUM_SHADING_2".equals(styleIdentifierName)) return MEDIUM_SHADING_2;
    	if ("MEDIUM_SHADING_2_ACCENT_1".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_1;
    	if ("MEDIUM_SHADING_2_ACCENT_2".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_2;
    	if ("MEDIUM_SHADING_2_ACCENT_3".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_3;
    	if ("MEDIUM_SHADING_2_ACCENT_4".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_4;
    	if ("MEDIUM_SHADING_2_ACCENT_5".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_5;
    	if ("MEDIUM_SHADING_2_ACCENT_6".equals(styleIdentifierName)) return MEDIUM_SHADING_2_ACCENT_6;
    	if ("TABLE_3_D_EFFECTS_1".equals(styleIdentifierName)) return TABLE_3_D_EFFECTS_1;
    	if ("TABLE_3_D_EFFECTS_2".equals(styleIdentifierName)) return TABLE_3_D_EFFECTS_2;
    	if ("TABLE_3_D_EFFECTS_3".equals(styleIdentifierName)) return TABLE_3_D_EFFECTS_3;
    	if ("TABLE_CLASSIC_1".equals(styleIdentifierName)) return TABLE_CLASSIC_1;
    	if ("TABLE_CLASSIC_2".equals(styleIdentifierName)) return TABLE_CLASSIC_2;
    	if ("TABLE_CLASSIC_3".equals(styleIdentifierName)) return TABLE_CLASSIC_3;
    	if ("TABLE_CLASSIC_4".equals(styleIdentifierName)) return TABLE_CLASSIC_4;
    	if ("TABLE_COLORFUL_1".equals(styleIdentifierName)) return TABLE_COLORFUL_1;
    	if ("TABLE_COLORFUL_2".equals(styleIdentifierName)) return TABLE_COLORFUL_2;
    	if ("TABLE_COLORFUL_3".equals(styleIdentifierName)) return TABLE_COLORFUL_3;
    	if ("TABLE_COLUMNS_1".equals(styleIdentifierName)) return TABLE_COLUMNS_1;
    	if ("TABLE_COLUMNS_2".equals(styleIdentifierName)) return TABLE_COLUMNS_2;
    	if ("TABLE_COLUMNS_3".equals(styleIdentifierName)) return TABLE_COLUMNS_3;
    	if ("TABLE_COLUMNS_4".equals(styleIdentifierName)) return TABLE_COLUMNS_4;
    	if ("TABLE_COLUMNS_5".equals(styleIdentifierName)) return TABLE_COLUMNS_5;
    	if ("TABLE_CONTEMPORARY".equals(styleIdentifierName)) return TABLE_CONTEMPORARY;
    	if ("TABLE_ELEGANT".equals(styleIdentifierName)) return TABLE_ELEGANT;
    	if ("TABLE_GRID".equals(styleIdentifierName)) return TABLE_GRID;
    	if ("TABLE_GRID_1".equals(styleIdentifierName)) return TABLE_GRID_1;
    	if ("TABLE_GRID_2".equals(styleIdentifierName)) return TABLE_GRID_2;
    	if ("TABLE_GRID_3".equals(styleIdentifierName)) return TABLE_GRID_3;
    	if ("TABLE_GRID_4".equals(styleIdentifierName)) return TABLE_GRID_4;
    	if ("TABLE_GRID_5".equals(styleIdentifierName)) return TABLE_GRID_5;
    	if ("TABLE_GRID_6".equals(styleIdentifierName)) return TABLE_GRID_6;
    	if ("TABLE_GRID_7".equals(styleIdentifierName)) return TABLE_GRID_7;
    	if ("TABLE_GRID_8".equals(styleIdentifierName)) return TABLE_GRID_8;
    	if ("TABLE_LIST_1".equals(styleIdentifierName)) return TABLE_LIST_1;
    	if ("TABLE_LIST_2".equals(styleIdentifierName)) return TABLE_LIST_2;
    	if ("TABLE_LIST_3".equals(styleIdentifierName)) return TABLE_LIST_3;
    	if ("TABLE_LIST_4".equals(styleIdentifierName)) return TABLE_LIST_4;
    	if ("TABLE_LIST_5".equals(styleIdentifierName)) return TABLE_LIST_5;
    	if ("TABLE_LIST_6".equals(styleIdentifierName)) return TABLE_LIST_6;
    	if ("TABLE_LIST_7".equals(styleIdentifierName)) return TABLE_LIST_7;
    	if ("TABLE_LIST_8".equals(styleIdentifierName)) return TABLE_LIST_8;
    	if ("TABLE_NORMAL".equals(styleIdentifierName)) return TABLE_NORMAL;
    	if ("TABLE_PROFESSIONAL".equals(styleIdentifierName)) return TABLE_PROFESSIONAL;
    	if ("TABLE_SIMPLE_1".equals(styleIdentifierName)) return TABLE_SIMPLE_1;
    	if ("TABLE_SIMPLE_2".equals(styleIdentifierName)) return TABLE_SIMPLE_2;
    	if ("TABLE_SIMPLE_3".equals(styleIdentifierName)) return TABLE_SIMPLE_3;
    	if ("TABLE_SUBTLE_1".equals(styleIdentifierName)) return TABLE_SUBTLE_1;
    	if ("TABLE_SUBTLE_2".equals(styleIdentifierName)) return TABLE_SUBTLE_2;
    	if ("TABLE_THEME".equals(styleIdentifierName)) return TABLE_THEME;
    	if ("TABLE_WEB_1".equals(styleIdentifierName)) return TABLE_WEB_1;
    	if ("TABLE_WEB_2".equals(styleIdentifierName)) return TABLE_WEB_2;
    	if ("TABLE_WEB_3".equals(styleIdentifierName)) return TABLE_WEB_3;
    	if ("PLAIN_TABLE_1".equals(styleIdentifierName)) return PLAIN_TABLE_1;
    	if ("PLAIN_TABLE_2".equals(styleIdentifierName)) return PLAIN_TABLE_2;
    	if ("PLAIN_TABLE_3".equals(styleIdentifierName)) return PLAIN_TABLE_3;
    	if ("PLAIN_TABLE_4".equals(styleIdentifierName)) return PLAIN_TABLE_4;
    	if ("PLAIN_TABLE_5".equals(styleIdentifierName)) return PLAIN_TABLE_5;
    	if ("TABLE_GRID_LIGHT".equals(styleIdentifierName)) return TABLE_GRID_LIGHT;
    	if ("GRID_TABLE_1_LIGHT".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT;
    	if ("GRID_TABLE_2".equals(styleIdentifierName)) return GRID_TABLE_2;
    	if ("GRID_TABLE_3".equals(styleIdentifierName)) return GRID_TABLE_3;
    	if ("GRID_TABLE_4".equals(styleIdentifierName)) return GRID_TABLE_4;
    	if ("GRID_TABLE_5_DARK".equals(styleIdentifierName)) return GRID_TABLE_5_DARK;
    	if ("GRID_TABLE_6_COLORFUL".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL;
    	if ("GRID_TABLE_7_COLORFUL".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_1;
    	if ("GRID_TABLE_2_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_1;
    	if ("GRID_TABLE_3_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_1;
    	if ("GRID_TABLE_4_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_1;
    	if ("GRID_TABLE_5_DARK_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_1;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_1;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_1".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_1;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_2;
    	if ("GRID_TABLE_2_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_2;
    	if ("GRID_TABLE_3_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_2;
    	if ("GRID_TABLE_4_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_2;
    	if ("GRID_TABLE_5_DARK_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_2;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_2;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_2".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_2;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_3;
    	if ("GRID_TABLE_2_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_3;
    	if ("GRID_TABLE_3_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_3;
    	if ("GRID_TABLE_4_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_3;
    	if ("GRID_TABLE_5_DARK_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_3;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_3;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_3".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_3;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_4;
    	if ("GRID_TABLE_2_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_4;
    	if ("GRID_TABLE_3_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_4;
    	if ("GRID_TABLE_4_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_4;
    	if ("GRID_TABLE_5_DARK_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_4;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_4;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_4".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_4;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_5;
    	if ("GRID_TABLE_2_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_5;
    	if ("GRID_TABLE_3_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_5;
    	if ("GRID_TABLE_4_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_5;
    	if ("GRID_TABLE_5_DARK_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_5;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_5;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_5".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_5;
    	if ("GRID_TABLE_1_LIGHT_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_1_LIGHT_ACCENT_6;
    	if ("GRID_TABLE_2_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_2_ACCENT_6;
    	if ("GRID_TABLE_3_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_3_ACCENT_6;
    	if ("GRID_TABLE_4_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_4_ACCENT_6;
    	if ("GRID_TABLE_5_DARK_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_5_DARK_ACCENT_6;
    	if ("GRID_TABLE_6_COLORFUL_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_6_COLORFUL_ACCENT_6;
    	if ("GRID_TABLE_7_COLORFUL_ACCENT_6".equals(styleIdentifierName)) return GRID_TABLE_7_COLORFUL_ACCENT_6;
    	if ("LIST_TABLE_1_LIGHT".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT;
    	if ("LIST_TABLE_2".equals(styleIdentifierName)) return LIST_TABLE_2;
    	if ("LIST_TABLE_3".equals(styleIdentifierName)) return LIST_TABLE_3;
    	if ("LIST_TABLE_4".equals(styleIdentifierName)) return LIST_TABLE_4;
    	if ("LIST_TABLE_5_DARK".equals(styleIdentifierName)) return LIST_TABLE_5_DARK;
    	if ("LIST_TABLE_6_COLORFUL".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL;
    	if ("LIST_TABLE_7_COLORFUL".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_1;
    	if ("LIST_TABLE_2_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_1;
    	if ("LIST_TABLE_3_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_1;
    	if ("LIST_TABLE_4_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_1;
    	if ("LIST_TABLE_5_DARK_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_1;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_1;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_1".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_1;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_2;
    	if ("LIST_TABLE_2_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_2;
    	if ("LIST_TABLE_3_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_2;
    	if ("LIST_TABLE_4_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_2;
    	if ("LIST_TABLE_5_DARK_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_2;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_2;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_2".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_2;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_3;
    	if ("LIST_TABLE_2_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_3;
    	if ("LIST_TABLE_3_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_3;
    	if ("LIST_TABLE_4_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_3;
    	if ("LIST_TABLE_5_DARK_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_3;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_3;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_3".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_3;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_4;
    	if ("LIST_TABLE_2_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_4;
    	if ("LIST_TABLE_3_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_4;
    	if ("LIST_TABLE_4_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_4;
    	if ("LIST_TABLE_5_DARK_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_4;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_4;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_4".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_4;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_5;
    	if ("LIST_TABLE_2_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_5;
    	if ("LIST_TABLE_3_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_5;
    	if ("LIST_TABLE_4_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_5;
    	if ("LIST_TABLE_5_DARK_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_5;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_5;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_5".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_5;
    	if ("LIST_TABLE_1_LIGHT_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_1_LIGHT_ACCENT_6;
    	if ("LIST_TABLE_2_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_2_ACCENT_6;
    	if ("LIST_TABLE_3_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_3_ACCENT_6;
    	if ("LIST_TABLE_4_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_4_ACCENT_6;
    	if ("LIST_TABLE_5_DARK_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_5_DARK_ACCENT_6;
    	if ("LIST_TABLE_6_COLORFUL_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_6_COLORFUL_ACCENT_6;
    	if ("LIST_TABLE_7_COLORFUL_ACCENT_6".equals(styleIdentifierName)) return LIST_TABLE_7_COLORFUL_ACCENT_6;
    	if ("MENTION".equals(styleIdentifierName)) return MENTION;
    	if ("SMART_HYPERLINK".equals(styleIdentifierName)) return SMART_HYPERLINK;
    	if ("HASHTAG".equals(styleIdentifierName)) return HASHTAG;
    	if ("UNRESOLVED_MENTION".equals(styleIdentifierName)) return UNRESOLVED_MENTION;
    	if ("USER".equals(styleIdentifierName)) return USER;
    	if ("NIL".equals(styleIdentifierName)) return NIL;
    	throw new IllegalArgumentException("Unknown StyleIdentifier name.");
    }
    
    /// <summary>
    /// Returns array of StyleIdentifier values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOOK_TITLE,
    		COMMENT_REFERENCE,
    		DEFAULT_PARAGRAPH_FONT,
    		EMPHASIS,
    		ENDNOTE_REFERENCE,
    		FOLLOWED_HYPERLINK,
    		FOOTNOTE_REFERENCE,
    		HTML_ACRONYM,
    		HTML_CITE,
    		HTML_CODE,
    		HTML_DEFINITION,
    		HTML_KEYBOARD,
    		HTML_SAMPLE,
    		HTML_TYPEWRITER,
    		HTML_VARIABLE,
    		HYPERLINK,
    		INTENSE_EMPHASIS,
    		INTENSE_REFERENCE,
    		LINE_NUMBER,
    		PAGE_NUMBER,
    		PLACEHOLDER_TEXT,
    		SMART_LINK,
    		STRONG,
    		SUBTLE_EMPHASIS,
    		SUBTLE_REFERENCE,
    		BALLOON_TEXT,
    		BODY_TEXT,
    		BODY_TEXT_2,
    		BODY_TEXT_3,
    		BODY_TEXT_1_I,
    		BODY_TEXT_1_I_2,
    		BODY_TEXT_IND,
    		BODY_TEXT_IND_2,
    		BODY_TEXT_IND_3,
    		CLOSING,
    		COMMENT_SUBJECT,
    		COMMENT_TEXT,
    		DATE,
    		DOCUMENT_MAP,
    		EMAIL_SIGNATURE,
    		ENDNOTE_TEXT,
    		FOOTER,
    		FOOTNOTE_TEXT,
    		HEADER,
    		HEADING_1,
    		HEADING_2,
    		HEADING_3,
    		HEADING_4,
    		HEADING_5,
    		HEADING_6,
    		HEADING_7,
    		HEADING_8,
    		HEADING_9,
    		HTML_ADDRESS,
    		HTML_TOP_OF_FORM,
    		HTML_BOTTOM_OF_FORM,
    		HTML_PREFORMATTED,
    		INTENSE_QUOTE,
    		MACRO,
    		MESSAGE_HEADER,
    		NOTE_HEADING,
    		PLAIN_TEXT,
    		QUOTE,
    		SALUTATION,
    		SIGNATURE,
    		SUBTITLE,
    		TITLE,
    		BIBLIOGRAPHY,
    		BLOCK_TEXT,
    		CAPTION,
    		ENVELOPE_ADDRESS,
    		ENVELOPE_RETURN,
    		INDEX_1,
    		INDEX_2,
    		INDEX_3,
    		INDEX_4,
    		INDEX_5,
    		INDEX_6,
    		INDEX_7,
    		INDEX_8,
    		INDEX_9,
    		INDEX_HEADING,
    		LIST,
    		LIST_2,
    		LIST_3,
    		LIST_4,
    		LIST_5,
    		LIST_BULLET,
    		LIST_BULLET_2,
    		LIST_BULLET_3,
    		LIST_BULLET_4,
    		LIST_BULLET_5,
    		LIST_CONTINUE,
    		LIST_CONTINUE_2,
    		LIST_CONTINUE_3,
    		LIST_CONTINUE_4,
    		LIST_CONTINUE_5,
    		LIST_NUMBER,
    		LIST_NUMBER_2,
    		LIST_NUMBER_3,
    		LIST_NUMBER_4,
    		LIST_NUMBER_5,
    		LIST_PARAGRAPH,
    		NO_SPACING,
    		NORMAL,
    		NORMAL_WEB,
    		NORMAL_INDENT,
    		TABLE_OF_AUTHORITIES,
    		TABLE_OF_FIGURES,
    		TOA_HEADING,
    		TOC_1,
    		TOC_2,
    		TOC_3,
    		TOC_4,
    		TOC_5,
    		TOC_6,
    		TOC_7,
    		TOC_8,
    		TOC_9,
    		TOC_HEADING,
    		REVISION,
    		OUTLINE_LIST_1,
    		OUTLINE_LIST_2,
    		OUTLINE_LIST_3,
    		NO_LIST,
    		COLORFUL_GRID,
    		COLORFUL_GRID_ACCENT_1,
    		COLORFUL_GRID_ACCENT_2,
    		COLORFUL_GRID_ACCENT_3,
    		COLORFUL_GRID_ACCENT_4,
    		COLORFUL_GRID_ACCENT_5,
    		COLORFUL_GRID_ACCENT_6,
    		COLORFUL_LIST,
    		COLORFUL_LIST_ACCENT_1,
    		COLORFUL_LIST_ACCENT_2,
    		COLORFUL_LIST_ACCENT_3,
    		COLORFUL_LIST_ACCENT_4,
    		COLORFUL_LIST_ACCENT_5,
    		COLORFUL_LIST_ACCENT_6,
    		COLORFUL_SHADING,
    		COLORFUL_SHADING_ACCENT_1,
    		COLORFUL_SHADING_ACCENT_2,
    		COLORFUL_SHADING_ACCENT_3,
    		COLORFUL_SHADING_ACCENT_4,
    		COLORFUL_SHADING_ACCENT_5,
    		COLORFUL_SHADING_ACCENT_6,
    		DARK_LIST,
    		DARK_LIST_ACCENT_1,
    		DARK_LIST_ACCENT_2,
    		DARK_LIST_ACCENT_3,
    		DARK_LIST_ACCENT_4,
    		DARK_LIST_ACCENT_5,
    		DARK_LIST_ACCENT_6,
    		LIGHT_GRID,
    		LIGHT_GRID_ACCENT_1,
    		LIGHT_GRID_ACCENT_2,
    		LIGHT_GRID_ACCENT_3,
    		LIGHT_GRID_ACCENT_4,
    		LIGHT_GRID_ACCENT_5,
    		LIGHT_GRID_ACCENT_6,
    		LIGHT_LIST,
    		LIGHT_LIST_ACCENT_1,
    		LIGHT_LIST_ACCENT_2,
    		LIGHT_LIST_ACCENT_3,
    		LIGHT_LIST_ACCENT_4,
    		LIGHT_LIST_ACCENT_5,
    		LIGHT_LIST_ACCENT_6,
    		LIGHT_SHADING,
    		LIGHT_SHADING_ACCENT_1,
    		LIGHT_SHADING_ACCENT_2,
    		LIGHT_SHADING_ACCENT_3,
    		LIGHT_SHADING_ACCENT_4,
    		LIGHT_SHADING_ACCENT_5,
    		LIGHT_SHADING_ACCENT_6,
    		MEDIUM_GRID_1,
    		MEDIUM_GRID_1_ACCENT_1,
    		MEDIUM_GRID_1_ACCENT_2,
    		MEDIUM_GRID_1_ACCENT_3,
    		MEDIUM_GRID_1_ACCENT_4,
    		MEDIUM_GRID_1_ACCENT_5,
    		MEDIUM_GRID_1_ACCENT_6,
    		MEDIUM_GRID_2,
    		MEDIUM_GRID_2_ACCENT_1,
    		MEDIUM_GRID_2_ACCENT_2,
    		MEDIUM_GRID_2_ACCENT_3,
    		MEDIUM_GRID_2_ACCENT_4,
    		MEDIUM_GRID_2_ACCENT_5,
    		MEDIUM_GRID_2_ACCENT_6,
    		MEDIUM_GRID_3,
    		MEDIUM_GRID_3_ACCENT_1,
    		MEDIUM_GRID_3_ACCENT_2,
    		MEDIUM_GRID_3_ACCENT_3,
    		MEDIUM_GRID_3_ACCENT_4,
    		MEDIUM_GRID_3_ACCENT_5,
    		MEDIUM_GRID_3_ACCENT_6,
    		MEDIUM_LIST_1,
    		MEDIUM_LIST_1_ACCENT_1,
    		MEDIUM_LIST_1_ACCENT_2,
    		MEDIUM_LIST_1_ACCENT_3,
    		MEDIUM_LIST_1_ACCENT_4,
    		MEDIUM_LIST_1_ACCENT_5,
    		MEDIUM_LIST_1_ACCENT_6,
    		MEDIUM_LIST_2,
    		MEDIUM_LIST_2_ACCENT_1,
    		MEDIUM_LIST_2_ACCENT_2,
    		MEDIUM_LIST_2_ACCENT_3,
    		MEDIUM_LIST_2_ACCENT_4,
    		MEDIUM_LIST_2_ACCENT_5,
    		MEDIUM_LIST_2_ACCENT_6,
    		MEDIUM_SHADING_1,
    		MEDIUM_SHADING_1_ACCENT_1,
    		MEDIUM_SHADING_1_ACCENT_2,
    		MEDIUM_SHADING_1_ACCENT_3,
    		MEDIUM_SHADING_1_ACCENT_4,
    		MEDIUM_SHADING_1_ACCENT_5,
    		MEDIUM_SHADING_1_ACCENT_6,
    		MEDIUM_SHADING_2,
    		MEDIUM_SHADING_2_ACCENT_1,
    		MEDIUM_SHADING_2_ACCENT_2,
    		MEDIUM_SHADING_2_ACCENT_3,
    		MEDIUM_SHADING_2_ACCENT_4,
    		MEDIUM_SHADING_2_ACCENT_5,
    		MEDIUM_SHADING_2_ACCENT_6,
    		TABLE_3_D_EFFECTS_1,
    		TABLE_3_D_EFFECTS_2,
    		TABLE_3_D_EFFECTS_3,
    		TABLE_CLASSIC_1,
    		TABLE_CLASSIC_2,
    		TABLE_CLASSIC_3,
    		TABLE_CLASSIC_4,
    		TABLE_COLORFUL_1,
    		TABLE_COLORFUL_2,
    		TABLE_COLORFUL_3,
    		TABLE_COLUMNS_1,
    		TABLE_COLUMNS_2,
    		TABLE_COLUMNS_3,
    		TABLE_COLUMNS_4,
    		TABLE_COLUMNS_5,
    		TABLE_CONTEMPORARY,
    		TABLE_ELEGANT,
    		TABLE_GRID,
    		TABLE_GRID_1,
    		TABLE_GRID_2,
    		TABLE_GRID_3,
    		TABLE_GRID_4,
    		TABLE_GRID_5,
    		TABLE_GRID_6,
    		TABLE_GRID_7,
    		TABLE_GRID_8,
    		TABLE_LIST_1,
    		TABLE_LIST_2,
    		TABLE_LIST_3,
    		TABLE_LIST_4,
    		TABLE_LIST_5,
    		TABLE_LIST_6,
    		TABLE_LIST_7,
    		TABLE_LIST_8,
    		TABLE_NORMAL,
    		TABLE_PROFESSIONAL,
    		TABLE_SIMPLE_1,
    		TABLE_SIMPLE_2,
    		TABLE_SIMPLE_3,
    		TABLE_SUBTLE_1,
    		TABLE_SUBTLE_2,
    		TABLE_THEME,
    		TABLE_WEB_1,
    		TABLE_WEB_2,
    		TABLE_WEB_3,
    		PLAIN_TABLE_1,
    		PLAIN_TABLE_2,
    		PLAIN_TABLE_3,
    		PLAIN_TABLE_4,
    		PLAIN_TABLE_5,
    		TABLE_GRID_LIGHT,
    		GRID_TABLE_1_LIGHT,
    		GRID_TABLE_2,
    		GRID_TABLE_3,
    		GRID_TABLE_4,
    		GRID_TABLE_5_DARK,
    		GRID_TABLE_6_COLORFUL,
    		GRID_TABLE_7_COLORFUL,
    		GRID_TABLE_1_LIGHT_ACCENT_1,
    		GRID_TABLE_2_ACCENT_1,
    		GRID_TABLE_3_ACCENT_1,
    		GRID_TABLE_4_ACCENT_1,
    		GRID_TABLE_5_DARK_ACCENT_1,
    		GRID_TABLE_6_COLORFUL_ACCENT_1,
    		GRID_TABLE_7_COLORFUL_ACCENT_1,
    		GRID_TABLE_1_LIGHT_ACCENT_2,
    		GRID_TABLE_2_ACCENT_2,
    		GRID_TABLE_3_ACCENT_2,
    		GRID_TABLE_4_ACCENT_2,
    		GRID_TABLE_5_DARK_ACCENT_2,
    		GRID_TABLE_6_COLORFUL_ACCENT_2,
    		GRID_TABLE_7_COLORFUL_ACCENT_2,
    		GRID_TABLE_1_LIGHT_ACCENT_3,
    		GRID_TABLE_2_ACCENT_3,
    		GRID_TABLE_3_ACCENT_3,
    		GRID_TABLE_4_ACCENT_3,
    		GRID_TABLE_5_DARK_ACCENT_3,
    		GRID_TABLE_6_COLORFUL_ACCENT_3,
    		GRID_TABLE_7_COLORFUL_ACCENT_3,
    		GRID_TABLE_1_LIGHT_ACCENT_4,
    		GRID_TABLE_2_ACCENT_4,
    		GRID_TABLE_3_ACCENT_4,
    		GRID_TABLE_4_ACCENT_4,
    		GRID_TABLE_5_DARK_ACCENT_4,
    		GRID_TABLE_6_COLORFUL_ACCENT_4,
    		GRID_TABLE_7_COLORFUL_ACCENT_4,
    		GRID_TABLE_1_LIGHT_ACCENT_5,
    		GRID_TABLE_2_ACCENT_5,
    		GRID_TABLE_3_ACCENT_5,
    		GRID_TABLE_4_ACCENT_5,
    		GRID_TABLE_5_DARK_ACCENT_5,
    		GRID_TABLE_6_COLORFUL_ACCENT_5,
    		GRID_TABLE_7_COLORFUL_ACCENT_5,
    		GRID_TABLE_1_LIGHT_ACCENT_6,
    		GRID_TABLE_2_ACCENT_6,
    		GRID_TABLE_3_ACCENT_6,
    		GRID_TABLE_4_ACCENT_6,
    		GRID_TABLE_5_DARK_ACCENT_6,
    		GRID_TABLE_6_COLORFUL_ACCENT_6,
    		GRID_TABLE_7_COLORFUL_ACCENT_6,
    		LIST_TABLE_1_LIGHT,
    		LIST_TABLE_2,
    		LIST_TABLE_3,
    		LIST_TABLE_4,
    		LIST_TABLE_5_DARK,
    		LIST_TABLE_6_COLORFUL,
    		LIST_TABLE_7_COLORFUL,
    		LIST_TABLE_1_LIGHT_ACCENT_1,
    		LIST_TABLE_2_ACCENT_1,
    		LIST_TABLE_3_ACCENT_1,
    		LIST_TABLE_4_ACCENT_1,
    		LIST_TABLE_5_DARK_ACCENT_1,
    		LIST_TABLE_6_COLORFUL_ACCENT_1,
    		LIST_TABLE_7_COLORFUL_ACCENT_1,
    		LIST_TABLE_1_LIGHT_ACCENT_2,
    		LIST_TABLE_2_ACCENT_2,
    		LIST_TABLE_3_ACCENT_2,
    		LIST_TABLE_4_ACCENT_2,
    		LIST_TABLE_5_DARK_ACCENT_2,
    		LIST_TABLE_6_COLORFUL_ACCENT_2,
    		LIST_TABLE_7_COLORFUL_ACCENT_2,
    		LIST_TABLE_1_LIGHT_ACCENT_3,
    		LIST_TABLE_2_ACCENT_3,
    		LIST_TABLE_3_ACCENT_3,
    		LIST_TABLE_4_ACCENT_3,
    		LIST_TABLE_5_DARK_ACCENT_3,
    		LIST_TABLE_6_COLORFUL_ACCENT_3,
    		LIST_TABLE_7_COLORFUL_ACCENT_3,
    		LIST_TABLE_1_LIGHT_ACCENT_4,
    		LIST_TABLE_2_ACCENT_4,
    		LIST_TABLE_3_ACCENT_4,
    		LIST_TABLE_4_ACCENT_4,
    		LIST_TABLE_5_DARK_ACCENT_4,
    		LIST_TABLE_6_COLORFUL_ACCENT_4,
    		LIST_TABLE_7_COLORFUL_ACCENT_4,
    		LIST_TABLE_1_LIGHT_ACCENT_5,
    		LIST_TABLE_2_ACCENT_5,
    		LIST_TABLE_3_ACCENT_5,
    		LIST_TABLE_4_ACCENT_5,
    		LIST_TABLE_5_DARK_ACCENT_5,
    		LIST_TABLE_6_COLORFUL_ACCENT_5,
    		LIST_TABLE_7_COLORFUL_ACCENT_5,
    		LIST_TABLE_1_LIGHT_ACCENT_6,
    		LIST_TABLE_2_ACCENT_6,
    		LIST_TABLE_3_ACCENT_6,
    		LIST_TABLE_4_ACCENT_6,
    		LIST_TABLE_5_DARK_ACCENT_6,
    		LIST_TABLE_6_COLORFUL_ACCENT_6,
    		LIST_TABLE_7_COLORFUL_ACCENT_6,
    		MENTION,
    		SMART_HYPERLINK,
    		HASHTAG,
    		UNRESOLVED_MENTION,
    		USER,
    		NIL,
    	};
    }

    public static final int length = 378;
}

