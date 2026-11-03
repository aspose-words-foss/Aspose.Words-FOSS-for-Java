// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/06/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the character that separates the list label from the text of the paragraph.
/// </summary>
/// <remarks>
/// <p>Used as a value for the <see cref="ListLevel.TrailingCharacter"/> property.</p>
/// </remarks>
public /*enum*/ final class ListTrailingCharacter
{
    private ListTrailingCharacter(){}
    
    /// <summary>
    /// A tab character is placed between the list label and text of the paragraph.
    /// </summary>
    public static final int TAB = 0;
    /// <summary>
    /// A space character is placed between the list label and text of the paragraph.
    /// </summary>
    public static final int SPACE = 1;
    /// <summary>
    /// There is no separator character between the list label and text of the paragraph.
    /// </summary>
    public static final int NOTHING = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListTrailingCharacter value.
    /// </summary>
    public static String getName(/*ListTrailingCharacter*/int listTrailingCharacter)
    {
    	switch (listTrailingCharacter)
    	{
    		case TAB: return "TAB";
    		case SPACE: return "SPACE";
    		case NOTHING: return "NOTHING";
    		default: return "Unknown ListTrailingCharacter value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListTrailingCharacter value.
    /// </summary>
    public static String toString(/*ListTrailingCharacter*/int listTrailingCharacter)
    {
    	switch (listTrailingCharacter)
    	{
    		case TAB: return "Tab";
    		case SPACE: return "Space";
    		case NOTHING: return "Nothing";
    		default: return "Unknown ListTrailingCharacter value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListTrailingCharacter name.
    /// </summary>
    public static /*ListTrailingCharacter*/int fromName(String listTrailingCharacterName)
    {
    	if ("TAB".equals(listTrailingCharacterName)) return TAB;
    	if ("SPACE".equals(listTrailingCharacterName)) return SPACE;
    	if ("NOTHING".equals(listTrailingCharacterName)) return NOTHING;
    	throw new IllegalArgumentException("Unknown ListTrailingCharacter name.");
    }
    
    /// <summary>
    /// Returns array of ListTrailingCharacter values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TAB,
    		SPACE,
    		NOTHING,
    	};
    }

    public static final int length = 3;
}

