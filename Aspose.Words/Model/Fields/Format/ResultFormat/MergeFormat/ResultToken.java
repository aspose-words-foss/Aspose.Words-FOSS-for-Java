// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a token <see cref="ResultEnumerator"/> that may be encountered during result enumeration.
/// </summary>
/*enum*/ final class ResultToken
{
    private ResultToken(){}
    
    /// <summary>
    /// The token is undefined.
    /// </summary>
    public static final int UNDEFINED = 0;
    /// <summary>
    /// Letter, digit or one of special characters.
    /// </summary>
    public static final int WORD_CHARACTER = 1;
    /// <summary>
    /// The ' ' character, other whitespace seem to be considered a separator.
    /// </summary>
    public static final int SPACE = 2;
    /// <summary>
    /// Any other character.
    /// </summary>
    public static final int SEPARATOR = 3;
    /// <summary>
    /// A paragraph break.
    /// </summary>
    public static final int PARGARAPH_BREAK = 4;
    /// <summary>
    /// A table
    /// </summary>
    public static final int TABLE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ResultToken value.
    /// </summary>
    public static String getName(/*ResultToken*/int resultToken)
    {
    	switch (resultToken)
    	{
    		case UNDEFINED: return "UNDEFINED";
    		case WORD_CHARACTER: return "WORD_CHARACTER";
    		case SPACE: return "SPACE";
    		case SEPARATOR: return "SEPARATOR";
    		case PARGARAPH_BREAK: return "PARGARAPH_BREAK";
    		case TABLE: return "TABLE";
    		default: return "Unknown ResultToken value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ResultToken value.
    /// </summary>
    public static String toString(/*ResultToken*/int resultToken)
    {
    	switch (resultToken)
    	{
    		case UNDEFINED: return "Undefined";
    		case WORD_CHARACTER: return "WordCharacter";
    		case SPACE: return "Space";
    		case SEPARATOR: return "Separator";
    		case PARGARAPH_BREAK: return "PargaraphBreak";
    		case TABLE: return "Table";
    		default: return "Unknown ResultToken value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ResultToken name.
    /// </summary>
    public static /*ResultToken*/int fromName(String resultTokenName)
    {
    	if ("UNDEFINED".equals(resultTokenName)) return UNDEFINED;
    	if ("WORD_CHARACTER".equals(resultTokenName)) return WORD_CHARACTER;
    	if ("SPACE".equals(resultTokenName)) return SPACE;
    	if ("SEPARATOR".equals(resultTokenName)) return SEPARATOR;
    	if ("PARGARAPH_BREAK".equals(resultTokenName)) return PARGARAPH_BREAK;
    	if ("TABLE".equals(resultTokenName)) return TABLE;
    	throw new IllegalArgumentException("Unknown ResultToken name.");
    }
    
    /// <summary>
    /// Returns array of ResultToken values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNDEFINED,
    		WORD_CHARACTER,
    		SPACE,
    		SEPARATOR,
    		PARGARAPH_BREAK,
    		TABLE,
    	};
    }

    public static final int length = 6;
}

