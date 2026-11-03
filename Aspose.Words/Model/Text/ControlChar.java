// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/04/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Control characters often encountered in documents.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-control-characters/">Working With Control Characters</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Provides both char and string versions of the same constants. For example:
/// string <see cref="LineBreak"/> and char <see cref="LineBreakChar"/> have the same value.
/// </remarks>
public /*static*/ class ControlChar
{
	/* Simulation of static class by using private constructor */
	private ControlChar()
	{}

    /// <summary>
    /// Placeholder for an inline picture: (char)1.
    /// At the moment occurs in the model in the FormField node. Ideally should not occur in the model.
    /// </summary>
    static final char PICTURE_CHAR = (char)1;

    /// <summary>
    /// Footnote reference character: (char)2.
    /// Occurs in the model inside text of a footnote node. Represents a placeholder where
    /// MS Word will display the footnote number.
    /// </summary>
    static final char FOOTNOTE_REF_CHAR = (char)2;
    /// <summary>
    /// Occurs in the model in <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.
    /// </summary>
    static final char FOOTNOTE_SEPARATOR_CHAR = (char)3;
    /// <summary>
    /// Occurs in the model in <see cref="Aspose.Words.Notes.FootnoteSeparator"/>.
    /// </summary>
    static final char FOOTNOTE_CONTINUATION_CHAR = (char)4;
    /// <summary>
    /// Annotation (comment) reference character: (char)5.
    ///
    /// Represents a placeholder where MS Word will display the author name and comment number.
    /// Normally occurs as the first character, but can be moved to anywhere in the comment text.
    /// If this character is omitted, MS Word will display the author name at the beginning of the comment text.
    ///
    /// In OOXML this is not a character, but a w:annotationRef element. Maybe we should convert
    /// it into an element in the model as well? But for consistency we will need to create similar
    /// classes for footnotes and endnotes.
    /// </summary>
    static final char ANNOTATION_REF_CHAR = (char)5;
    /// <summary>
    /// End of a table cell or end of a table row character: (char)7 or "\a".
    /// </summary>
    public static final char CELL_CHAR = (char)7;
    /// <summary>
    /// Tab character: (char)9 or "\t".
    /// </summary>
    public static final char TAB_CHAR = (char)9;
    /// <summary>
    /// Line feed character: (char)10 or "\n".
    /// </summary>
    public static final char LINE_FEED_CHAR = (char)10;
    /// <summary>
    /// Line break character: (char)11 or "\v".
    /// </summary>
    public static final char LINE_BREAK_CHAR = (char)11;
    /// <summary>
    /// Page break character: (char)12 or "\f".
    /// </summary>
    public static final char PAGE_BREAK_CHAR = (char)12;
    /// <summary>
    /// End of section character: (char)12 or "\f".
    /// </summary>
    public static final char SECTION_BREAK_CHAR = (char)12;
    /// <summary>
    /// End of paragraph character: (char)13 or "\r".
    /// </summary>
    public static final char PARAGRAPH_BREAK_CHAR = (char)13;
    /// <summary>
    /// End of column character: (char)14.
    /// </summary>
    public static final char COLUMN_BREAK_CHAR = (char)14;
    /// <summary>
    /// Start of MS Word field character: (char)19.
    /// </summary>
    public static final char FIELD_START_CHAR = (char)19;
    /// <summary>
    /// Field separator character separates field code from field value. Optional in some fields. Value: (char)20.
    /// </summary>
    public static final char FIELD_SEPARATOR_CHAR = (char)20;
    /// <summary>
    /// End of MS Word field character: (char)21.
    /// </summary>
    public static final char FIELD_END_CHAR = (char)21;
    /// <summary>
    /// Non-breaking Hyphen in Microsoft Word is (char)30.
    /// </summary>
    /// <remarks>
    /// <p>Non-breaking Hyphen in Microsoft Word does not correspond to the
    /// Unicode character U+2011 non-breaking hyphen but instead represents
    /// internal information that tells Microsoft Word to display a hyphen and not to break a line.</p>
    /// <p>Useful info: http://www.cs.tut.fi/~jkorpela/dashes.html#linebreaks.</p>
    /// </remarks>
    public static final char NON_BREAKING_HYPHEN_CHAR = (char)30;
    /// <summary>
    /// Optional Hyphen in Microsoft Word is (char)31.
    /// </summary>
    /// <remarks>
    /// <p>Optional Hyphen in Microsoft Word does not correspond to the Unicode character U+00AD soft hyphen.
    /// Instead, it inserts internal information that tells Word about a possible hyphenation point.</p>
    /// </remarks>
    public static final char OPTIONAL_HYPHEN_CHAR = (char)31;
    /// <summary>
    /// Space character: (char)32.
    /// </summary>
    public static final char SPACE_CHAR = (char)32;

    /// <summary>
    /// The start and end characters of structured document tag (in Model it's custom xml).
    /// </summary>
    static final char SDT_START_CHAR = (char)0x3C;
    static final char SDT_END_CHAR = (char)0x3E;

    /// <summary>
    /// \ - backslash.
    /// </summary>
    static final char BACKSLASH_CHAR = '\\';
    /// <summary>
    /// Non-breaking space character: (char)160.
    /// </summary>
    public static final char NON_BREAKING_SPACE_CHAR = '\u00a0';
    /// <summary>
    /// Unicode Yen currency character.
    /// </summary>
    static final char YEN_CHAR = '\u00a5';
    /// <summary>
    /// Soft hyphen character http://www.fileformat.info/info/unicode/char/00ad/index.htm
    /// </summary>
    static final char UNICODE_OPTIONAL_HYPHEN_CHAR = '\u00ad';

    /// <summary>
    /// This is the "o" character used as a default value in text input form fields.
    /// </summary>
    public static final char DEFAULT_TEXT_INPUT_CHAR = '\u2002';

    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2002/index.htm
    /// </summary>
    static final char EN_SPACE_CHAR = '\u2002';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2003/index.htm
    /// </summary>
    static final char EM_SPACE_CHAR = '\u2003';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2005/index.htm
    /// </summary>
    static final char FOUR_PER_EM_SPACE_CHAR = '\u2005';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/3000/index.htm
    /// </summary>
    static final char IDEOGRAPHIC_SPACE_CHAR = '\u3000';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/200e/index.htm
    /// </summary>
    static final char LEFT_TO_RIGHT_MARK_CHAR = '\u200e';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/200f/index.htm
    /// </summary>
    static final char RIGHT_TO_LEFT_MARK_CHAR = '\u200f';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/202a/index.htm
    /// </summary>
    static final char LEFT_TO_RIGHT_EMBEDDING = '\u202A';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/202b/index.htm
    /// </summary>
    static final char RIGHT_TO_LEFT_EMBEDDING = '\u202B';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/202d/index.htm
    /// </summary>
    static final char LEFT_TO_RIGHT_OVERRIDE = '\u202D';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/202e/index.htm
    /// </summary>
    static final char RIGHT_TO_LEFT_OVERRIDE = '\u202E';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/202c/index.htm
    /// </summary>
    static final char POP_DIRECTIONAL_FORMATTING = '\u202C';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2066/index.htm
    /// </summary>
    static final char LEFT_TO_RIGHT_ISOLATE = '\u2066';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2067/index.htm
    /// </summary>
    static final char RIGHT_TO_LEFT_ISOLATE = '\u2067';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2068/index.htm
    /// </summary>
    static final char FIRST_STRONG_ISOLATE = '\u2068';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2069/index.htm
    /// </summary>
    static final char POP_DIRECTIONAL_ISOLATE = '\u2069';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/061c/index.htm
    /// </summary>
    static final char ARABIC_LETTER_MARK = '\u061C';
    /// <summary>
    /// See <see cref="NonBreakingHyphenChar"/>.
    /// </summary>
    static final char UNICODE_NON_BREAKING_HYPHEN_CHAR = '\u2011';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2013/index.htm
    /// </summary>
    static final char EN_DASH_CHAR = '\u2013';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2014/index.htm
    /// </summary>
    static final char EM_DASH_CHAR = '\u2014';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/200c/index.htm
    /// a.k.a No-Width Optional Break
    /// </summary>
    static final char ZERO_WIDTH_NON_JOINER_CHAR = '\u200c';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/200d/index.htm
    /// a.k.a No-Width Non Break
    /// </summary>
    static final char ZERO_WIDTH_JOINER_CHAR = '\u200d';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/200b/index.htm
    /// </summary>
    static final char ZERO_WIDTH_SPACE_CHAR = '\u200b';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/feff/index.htm
    /// Not sure if MS Word uses this.
    /// </summary>
    static final char ZERO_WITH_NO_BREAK_SPACE = '\ufeff';
    /// <summary>
    /// http://www.fileformat.info/info/unicode/char/2028/index.htm
    /// Unicode character 'LINE SEPARATOR'.
    /// </summary>
    static final char UNICODE_LINE_SEPARATOR = '\u2028';

    static final char NO_WIDTH_OPTIONAL_BREAK_CHAR = ZERO_WIDTH_NON_JOINER_CHAR;
    static final char NO_WIDTH_NON_BREAK_CHAR = ZERO_WIDTH_JOINER_CHAR;

    /// <summary>
    /// Placeholder for an inline picture: "\x0001".
    /// </summary>
    static /*final*/ String PICTURE = Character.toString(PICTURE_CHAR);
    /// <summary>
    /// Footnote reference character: "\x0002".
    /// </summary>
    static /*final*/ String FOOTNOTE_REF = Character.toString(FOOTNOTE_REF_CHAR);
    /// <summary>
    /// Annotation (comment) reference character: "\x0005".
    /// </summary>
    static /*final*/ String ANNOTATION_REF = Character.toString(ANNOTATION_REF_CHAR);
    /// <summary>
    /// End of a table cell or end of a table row character: "\x0007" or "\a".
    /// </summary>
    public static /*final*/ String CELL = Character.toString(CELL_CHAR);
    /// <summary>
    /// Tab character: "\x0009" or "\t".
    /// </summary>
    public static /*final*/ String TAB = Character.toString(TAB_CHAR);
    /// <summary>
    /// Line feed character: "\x000a" or "\n". Same as <see cref="LineFeed"/>.
    /// </summary>
    public static /*final*/ String LF = Character.toString(LINE_FEED_CHAR);
    /// <summary>
    /// Line feed character: "\x000a" or "\n". Same as <see cref="Lf"/>.
    /// </summary>
    public static /*final*/ String LINE_FEED = Character.toString(LINE_FEED_CHAR);
    /// <summary>
    /// Line break character: "\x000b" or "\v".
    /// </summary>
    public static /*final*/ String LINE_BREAK = Character.toString(LINE_BREAK_CHAR);
    /// <summary>
    /// Page break character: "\x000c" or "\f". Note it has the same value as <see cref="SectionBreak"/>.
    /// </summary>
    public static /*final*/ String PAGE_BREAK = Character.toString(PAGE_BREAK_CHAR);
    /// <summary>
    /// End of section character: "\x000c" or "\f". Note it has the same value as <see cref="PageBreak"/>.
    /// </summary>
    public static /*final*/ String SECTION_BREAK = Character.toString(SECTION_BREAK_CHAR);
    /// <summary>
    /// Carriage return character: "\x000d" or "\r". Same as <see cref="ParagraphBreak"/>.
    /// </summary>
    public static /*final*/ String CR = Character.toString(PARAGRAPH_BREAK_CHAR);
    /// <summary>
    /// End of paragraph character: "\x000d" or "\r". Same as <see cref="Cr"/>
    /// </summary>
    public static /*final*/ String PARAGRAPH_BREAK = Character.toString(PARAGRAPH_BREAK_CHAR);
    /// <summary>
    /// End of column character: "\x000e".
    /// </summary>
    public static /*final*/ String COLUMN_BREAK = Character.toString(COLUMN_BREAK_CHAR);
    /// <summary>
    /// Carriage return followed by line feed character: "\x000d\x000a" or "\r\n".
    /// Not used as such in Microsoft Word documents, but commonly used in text files for paragraph breaks.
    /// </summary>
    public static /*final*/ String CR_LF = CR + LF;

    static /*final*/ String FIELD_START = Character.toString(FIELD_START_CHAR);

    static /*final*/ String FIELD_SEPARATOR = Character.toString(FIELD_SEPARATOR_CHAR);

    static /*final*/ String FIELD_END = Character.toString(FIELD_END_CHAR);

    static /*final*/ String SDT_START = Character.toString(SDT_START_CHAR);
    static /*final*/ String SDT_END = Character.toString(SDT_END_CHAR);

    static /*final*/ String SPACE = Character.toString(SPACE_CHAR);

    /// <summary>
    /// Non-breaking space character: "\x00a0".
    /// </summary>
    public static /*final*/ String NON_BREAKING_SPACE = Character.toString(NON_BREAKING_SPACE_CHAR);

    /// <summary>
    /// Special char used to designate non-breaking hyphen. Can occur in run's text.
    /// </summary>
    static /*final*/ String NON_BREAKING_HYPHEN = Character.toString(NON_BREAKING_HYPHEN_CHAR);
    /// <summary>
    /// Special char used to designate optional hyphen. Can occur in run's text.
    /// </summary>
    static /*final*/ String OPTIONAL_HYPHEN = Character.toString(OPTIONAL_HYPHEN_CHAR);

    static /*final*/ String UNICODE_NON_BREAKING_HYPHEN = Character.toString(UNICODE_NON_BREAKING_HYPHEN_CHAR);
    static /*final*/ String UNICODE_OPTIONAL_HYPHEN = Character.toString(UNICODE_OPTIONAL_HYPHEN_CHAR);

    static /*final*/ String BACKSLASH = Character.toString(BACKSLASH_CHAR);
    static /*final*/ String YEN = Character.toString(YEN_CHAR);

    static /*final*/ String DEFAULT_TEXT_INPUT = Character.toString(DEFAULT_TEXT_INPUT_CHAR);
}

