// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/06/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies one of the predefined list formats available in Microsoft Word.
/// </summary>
/// <remarks>
/// <p>A list template value is used as a parameter into the
/// <see cref="ListCollection.Add(ListTemplate)"/> method.</p>
///
/// <p>Aspose.Words list templates correspond to the 21 list templates available
/// in the Bullets and Numbering dialog box in Microsoft Word 2003.</p>
/// </remarks>
public /*enum*/ final class ListTemplate
{
    private ListTemplate(){}
    
    /// <summary>
    /// <p>Default bulleted list with 9 levels. Bullet of the first level is a disc,
    /// bullet of the second level is a circle, bullet of the third level is a square.
    /// Then formatting repeats for the remaining levels.</p>
    ///
    /// <p>Each level is indented to the right by 0.25" relative to the previous level.</p>
    ///
    /// <p>Corresponds to the 1st bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_DEFAULT = 0;
    /// <summary>
    /// <p>Same as <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 1st bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_DISK = BULLET_DEFAULT;
    /// <summary>
    /// <p>The bullet of the first level is a circle. The remaining levels are same as in <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 2nd bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_CIRCLE = 1;
    /// <summary>
    /// <p>The bullet of the first level is a square. The remaining levels are same as in <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 3rd bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_SQUARE = 2;

    // bullet 4 here we don't have yet, it has a picture.

    /// <summary>
    /// <p>The bullet of the first level is a 4-diamond Wingding character. The remaining levels are same as in <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 5th bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_DIAMONDS = 3;
    /// <summary>
    /// <p>The bullet of the first level is an arrow head Wingding character. The remaining levels are same as in <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 6th bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_ARROW_HEAD = 4;
    /// <summary>
    /// <p>The bullet of the first level is a tick Wingding character. The remaining levels are same as in <see cref="BulletDefault"/>.</p>
    /// <p>Corresponds to the 7th bulleted list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int BULLET_TICK = 5;

    /// <summary>
    /// <p>Default numbered list with 9 levels. Arabic numbering (1., 2., 3., ...) for the first level,
    /// lowercase letter numbering (a., b., c., ...) for the second level,
    /// lowercase roman numbering (i., ii., iii., ...) for the third level.
    /// Then formatting repeats for the remaining levels.</p>
    ///
    /// <p>Each level is indented to the right by 0.25" relative to the previous level.</p>
    ///
    /// <p>Corresponds to the 1st numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_DEFAULT = 6;
    /// <summary>
    /// <p>Same as <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 1st numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_ARABIC_DOT = NUMBER_DEFAULT;
    /// <summary>
    /// <p>The number of the first level is "1)". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 2nd numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_ARABIC_PARENTHESIS = 7;
    /// <summary>
    /// <p>The number of the first level is "I.". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 3rd numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_UPPERCASE_ROMAN_DOT = 8;
    /// <summary>
    /// <p>The number of the first level is "A.". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 4th numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_UPPERCASE_LETTER_DOT = 9;
    /// <summary>
    /// <p>The number of the first level is "a)". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 5th numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_LOWERCASE_LETTER_PARENTHESIS = 10;
    /// <summary>
    /// <p>The number of the first level is "a.". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 6th numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_LOWERCASE_LETTER_DOT = 11;
    /// <summary>
    /// <p>The number of the first level is "i.". The remaining levels are same as in <see cref="NumberDefault"/>.</p>
    /// <p>Corresponds to the 7th numbered list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int NUMBER_LOWERCASE_ROMAN_DOT = 12;

    /// <summary>
    /// <p>An outline list with levels numbered "1), a), i), (1), (a), (i), 1., a., i.".</p>
    /// <p>Corresponds to the 1st outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_NUMBERS = 13;
    /// <summary>
    /// <p>An outline list with levels are numbered "1., 1.1., 1.1.1, ...".</p>
    /// <p>Corresponds to the 2nd outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_LEGAL = 14;
    /// <summary>
    /// <p>An outline lists with various bullets for different levels.</p>
    /// <p>Corresponds to the 3rd outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_BULLETS = 15;
    /// <summary>
    /// <p>An outline list with levels linked to Heading styles.</p>
    /// <p>Corresponds to the 4th outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_HEADINGS_ARTICLE_SECTION = 16;
    /// <summary>
    /// <p>An outline list with levels linked to Heading styles.</p>
    /// <p>Corresponds to the 5th outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_HEADINGS_LEGAL = 17;
    /// <summary>
    /// <p>An outline list with levels linked to Heading styles.</p>
    /// <p>Corresponds to the 6th outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_HEADINGS_NUMBERS = 18;
    /// <summary>
    /// <p>An outline list with levels linked to Heading styles.</p>
    /// <p>Corresponds to the 7th outline list template in the Bullets and Numbering dialog box in Microsoft Word.</p>
    /// </summary>
    public static final int OUTLINE_HEADINGS_CHAPTER = 19;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListTemplate value.
    /// </summary>
    public static String getName(/*ListTemplate*/int listTemplate)
    {
    	switch (listTemplate)
    	{
    		case BULLET_DEFAULT:
    		/*case BULLET_DISK:*/
    			return "BULLET_DEFAULT" + " | " + "BULLET_DISK";
    		case BULLET_CIRCLE: return "BULLET_CIRCLE";
    		case BULLET_SQUARE: return "BULLET_SQUARE";
    		case BULLET_DIAMONDS: return "BULLET_DIAMONDS";
    		case BULLET_ARROW_HEAD: return "BULLET_ARROW_HEAD";
    		case BULLET_TICK: return "BULLET_TICK";
    		case NUMBER_DEFAULT:
    		/*case NUMBER_ARABIC_DOT:*/
    			return "NUMBER_DEFAULT" + " | " + "NUMBER_ARABIC_DOT";
    		case NUMBER_ARABIC_PARENTHESIS: return "NUMBER_ARABIC_PARENTHESIS";
    		case NUMBER_UPPERCASE_ROMAN_DOT: return "NUMBER_UPPERCASE_ROMAN_DOT";
    		case NUMBER_UPPERCASE_LETTER_DOT: return "NUMBER_UPPERCASE_LETTER_DOT";
    		case NUMBER_LOWERCASE_LETTER_PARENTHESIS: return "NUMBER_LOWERCASE_LETTER_PARENTHESIS";
    		case NUMBER_LOWERCASE_LETTER_DOT: return "NUMBER_LOWERCASE_LETTER_DOT";
    		case NUMBER_LOWERCASE_ROMAN_DOT: return "NUMBER_LOWERCASE_ROMAN_DOT";
    		case OUTLINE_NUMBERS: return "OUTLINE_NUMBERS";
    		case OUTLINE_LEGAL: return "OUTLINE_LEGAL";
    		case OUTLINE_BULLETS: return "OUTLINE_BULLETS";
    		case OUTLINE_HEADINGS_ARTICLE_SECTION: return "OUTLINE_HEADINGS_ARTICLE_SECTION";
    		case OUTLINE_HEADINGS_LEGAL: return "OUTLINE_HEADINGS_LEGAL";
    		case OUTLINE_HEADINGS_NUMBERS: return "OUTLINE_HEADINGS_NUMBERS";
    		case OUTLINE_HEADINGS_CHAPTER: return "OUTLINE_HEADINGS_CHAPTER";
    		default: return "Unknown ListTemplate value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListTemplate value.
    /// </summary>
    public static String toString(/*ListTemplate*/int listTemplate)
    {
    	switch (listTemplate)
    	{
    		case BULLET_DEFAULT:
    		/*case BULLET_DISK:*/
    			return "BulletDefault" + " | " + "BulletDisk";
    		case BULLET_CIRCLE: return "BulletCircle";
    		case BULLET_SQUARE: return "BulletSquare";
    		case BULLET_DIAMONDS: return "BulletDiamonds";
    		case BULLET_ARROW_HEAD: return "BulletArrowHead";
    		case BULLET_TICK: return "BulletTick";
    		case NUMBER_DEFAULT:
    		/*case NUMBER_ARABIC_DOT:*/
    			return "NumberDefault" + " | " + "NumberArabicDot";
    		case NUMBER_ARABIC_PARENTHESIS: return "NumberArabicParenthesis";
    		case NUMBER_UPPERCASE_ROMAN_DOT: return "NumberUppercaseRomanDot";
    		case NUMBER_UPPERCASE_LETTER_DOT: return "NumberUppercaseLetterDot";
    		case NUMBER_LOWERCASE_LETTER_PARENTHESIS: return "NumberLowercaseLetterParenthesis";
    		case NUMBER_LOWERCASE_LETTER_DOT: return "NumberLowercaseLetterDot";
    		case NUMBER_LOWERCASE_ROMAN_DOT: return "NumberLowercaseRomanDot";
    		case OUTLINE_NUMBERS: return "OutlineNumbers";
    		case OUTLINE_LEGAL: return "OutlineLegal";
    		case OUTLINE_BULLETS: return "OutlineBullets";
    		case OUTLINE_HEADINGS_ARTICLE_SECTION: return "OutlineHeadingsArticleSection";
    		case OUTLINE_HEADINGS_LEGAL: return "OutlineHeadingsLegal";
    		case OUTLINE_HEADINGS_NUMBERS: return "OutlineHeadingsNumbers";
    		case OUTLINE_HEADINGS_CHAPTER: return "OutlineHeadingsChapter";
    		default: return "Unknown ListTemplate value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListTemplate name.
    /// </summary>
    public static /*ListTemplate*/int fromName(String listTemplateName)
    {
    	if ("BULLET_DEFAULT".equals(listTemplateName)) return BULLET_DEFAULT;
    	if ("BULLET_DISK".equals(listTemplateName)) return BULLET_DISK;
    	if ("BULLET_CIRCLE".equals(listTemplateName)) return BULLET_CIRCLE;
    	if ("BULLET_SQUARE".equals(listTemplateName)) return BULLET_SQUARE;
    	if ("BULLET_DIAMONDS".equals(listTemplateName)) return BULLET_DIAMONDS;
    	if ("BULLET_ARROW_HEAD".equals(listTemplateName)) return BULLET_ARROW_HEAD;
    	if ("BULLET_TICK".equals(listTemplateName)) return BULLET_TICK;
    	if ("NUMBER_DEFAULT".equals(listTemplateName)) return NUMBER_DEFAULT;
    	if ("NUMBER_ARABIC_DOT".equals(listTemplateName)) return NUMBER_ARABIC_DOT;
    	if ("NUMBER_ARABIC_PARENTHESIS".equals(listTemplateName)) return NUMBER_ARABIC_PARENTHESIS;
    	if ("NUMBER_UPPERCASE_ROMAN_DOT".equals(listTemplateName)) return NUMBER_UPPERCASE_ROMAN_DOT;
    	if ("NUMBER_UPPERCASE_LETTER_DOT".equals(listTemplateName)) return NUMBER_UPPERCASE_LETTER_DOT;
    	if ("NUMBER_LOWERCASE_LETTER_PARENTHESIS".equals(listTemplateName)) return NUMBER_LOWERCASE_LETTER_PARENTHESIS;
    	if ("NUMBER_LOWERCASE_LETTER_DOT".equals(listTemplateName)) return NUMBER_LOWERCASE_LETTER_DOT;
    	if ("NUMBER_LOWERCASE_ROMAN_DOT".equals(listTemplateName)) return NUMBER_LOWERCASE_ROMAN_DOT;
    	if ("OUTLINE_NUMBERS".equals(listTemplateName)) return OUTLINE_NUMBERS;
    	if ("OUTLINE_LEGAL".equals(listTemplateName)) return OUTLINE_LEGAL;
    	if ("OUTLINE_BULLETS".equals(listTemplateName)) return OUTLINE_BULLETS;
    	if ("OUTLINE_HEADINGS_ARTICLE_SECTION".equals(listTemplateName)) return OUTLINE_HEADINGS_ARTICLE_SECTION;
    	if ("OUTLINE_HEADINGS_LEGAL".equals(listTemplateName)) return OUTLINE_HEADINGS_LEGAL;
    	if ("OUTLINE_HEADINGS_NUMBERS".equals(listTemplateName)) return OUTLINE_HEADINGS_NUMBERS;
    	if ("OUTLINE_HEADINGS_CHAPTER".equals(listTemplateName)) return OUTLINE_HEADINGS_CHAPTER;
    	throw new IllegalArgumentException("Unknown ListTemplate name.");
    }
    
    /// <summary>
    /// Returns array of ListTemplate values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BULLET_DEFAULT,
    		BULLET_DISK,
    		BULLET_CIRCLE,
    		BULLET_SQUARE,
    		BULLET_DIAMONDS,
    		BULLET_ARROW_HEAD,
    		BULLET_TICK,
    		NUMBER_DEFAULT,
    		NUMBER_ARABIC_DOT,
    		NUMBER_ARABIC_PARENTHESIS,
    		NUMBER_UPPERCASE_ROMAN_DOT,
    		NUMBER_UPPERCASE_LETTER_DOT,
    		NUMBER_LOWERCASE_LETTER_PARENTHESIS,
    		NUMBER_LOWERCASE_LETTER_DOT,
    		NUMBER_LOWERCASE_ROMAN_DOT,
    		OUTLINE_NUMBERS,
    		OUTLINE_LEGAL,
    		OUTLINE_BULLETS,
    		OUTLINE_HEADINGS_ARTICLE_SECTION,
    		OUTLINE_HEADINGS_LEGAL,
    		OUTLINE_HEADINGS_NUMBERS,
    		OUTLINE_HEADINGS_CHAPTER,
    	};
    }

    public static final int length = 22;
}

