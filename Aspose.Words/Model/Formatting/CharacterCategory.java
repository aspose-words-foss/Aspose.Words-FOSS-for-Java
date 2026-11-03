// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/03/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A run in Microsoft Word can have up to four fonts assigned to it.
/// Different characters in a single run can use different fonts depending on their Unicode character code.
/// This enum identifies character category (that allows to select the proper font to use).
/// http://en.wikipedia.org/wiki/Mapping_of_Unicode_characters
///
/// This enum is also used as the Font Type Hint value.
/// Font Type Hint specifies the font type which shall be used to format any ambiguous characters in a run.
///
/// An interesting note in the RTF specification:
/// South Asian (complex script) runs of text share associated properties with right-to-left runs,
/// but they are considered left-to-right.
/// </summary>
/*enum*/ final class CharacterCategory
{
    private CharacterCategory(){}
    
    /// <summary>
    /// Font used for characters that do not fall into any of the above ranges.
    /// Also known as High ASCII.
    /// </summary>
    public static final int OTHER = 0;
    /// <summary>
    /// Font used for the East Asian characters.
    /// Also known as East Asian.
    /// </summary>
    public static final int FAR_EAST = 1;
    /// <summary>
    /// Font used for the Complex Script characters.
    /// </summary>
    public static final int COMPLEX_SCRIPT = 2;
    /// <summary>
    /// Font used for the 0..127 characters.
    /// </summary>
    /// <remarks>This value is not used as <see cref="RunPr.CharacterCategoryHint"/>,
    /// may be it's better to create separate enum for character hints.</remarks>
    public static final int ASCII = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CharacterCategory value.
    /// </summary>
    public static String getName(/*CharacterCategory*/int characterCategory)
    {
    	switch (characterCategory)
    	{
    		case OTHER: return "OTHER";
    		case FAR_EAST: return "FAR_EAST";
    		case COMPLEX_SCRIPT: return "COMPLEX_SCRIPT";
    		case ASCII: return "ASCII";
    		default: return "Unknown CharacterCategory value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CharacterCategory value.
    /// </summary>
    public static String toString(/*CharacterCategory*/int characterCategory)
    {
    	switch (characterCategory)
    	{
    		case OTHER: return "Other";
    		case FAR_EAST: return "FarEast";
    		case COMPLEX_SCRIPT: return "ComplexScript";
    		case ASCII: return "Ascii";
    		default: return "Unknown CharacterCategory value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CharacterCategory name.
    /// </summary>
    public static /*CharacterCategory*/int fromName(String characterCategoryName)
    {
    	if ("OTHER".equals(characterCategoryName)) return OTHER;
    	if ("FAR_EAST".equals(characterCategoryName)) return FAR_EAST;
    	if ("COMPLEX_SCRIPT".equals(characterCategoryName)) return COMPLEX_SCRIPT;
    	if ("ASCII".equals(characterCategoryName)) return ASCII;
    	throw new IllegalArgumentException("Unknown CharacterCategory name.");
    }
    
    /// <summary>
    /// Returns array of CharacterCategory values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OTHER,
    		FAR_EAST,
    		COMPLEX_SCRIPT,
    		ASCII,
    	};
    }

    public static final int length = 4;
}

