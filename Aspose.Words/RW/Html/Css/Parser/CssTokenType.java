// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents CSS token type.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-syntax-3/#tokenization
/// </remarks>
/*enum*/ final class CssTokenType
{
    private CssTokenType(){}
    
    /// <summary>
    /// Ident token. The token class is <see cref="CssTextToken"/>.
    /// </summary>
    public static final int IDENT = 0;

    /// <summary>
    /// Function token. The token class is <see cref="CssTextToken"/>.
    /// </summary>
    public static final int FUNCTION = 1;

    /// <summary>
    /// At-keyword token. The token class is <see cref="CssTextToken"/>.
    /// </summary>
    public static final int AT_KEYWORD = 2;

    /// <summary>
    /// Hash token. The token class is <see cref="CssHashToken"/>.
    /// </summary>
    public static final int HASH = 3;

    /// <summary>
    /// String token. The token class is <see cref="CssTextToken"/>.
    /// </summary>
    public static final int STRING = 4;

    /// <summary>
    /// Bad string token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int BAD_STRING = 5;

    /// <summary>
    /// URL token. The token class is <see cref="CssTextToken"/>.
    /// </summary>
    public static final int URL = 6;

    /// <summary>
    /// Bad URL token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int BAD_URL = 7;

    /// <summary>
    /// Delim (delimiter) token. The token class is <see cref="CssDelimToken"/>.
    /// </summary>
    public static final int DELIM = 8;

    /// <summary>
    /// Number token. The token class is <see cref="CssNumberToken"/>.
    /// </summary>
    public static final int NUMBER = 9;

    /// <summary>
    /// Percentage token. The token class is <see cref="CssNumberToken"/>.
    /// </summary>
    public static final int PERCENTAGE = 10;

    /// <summary>
    /// Dimension token. The token class is <see cref="CssDimensionToken"/>.
    /// </summary>
    public static final int DIMENSION = 11;

    /// <summary>
    /// Whitespace token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int WHITESPACE = 12;

    /// <summary>
    /// CDO (comment delimiter open) token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int CDO = 13;

    /// <summary>
    /// CDC (comment delimiter close) token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int CDC = 14;

    /// <summary>
    /// Colon token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int COLON = 15;

    /// <summary>
    /// Semicolon token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int SEMICOLON = 16;

    /// <summary>
    /// Comma token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int COMMA = 17;

    /// <summary>
    /// Left round bracket "(" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int ROUND_BRACKET_LEFT = 18;

    /// <summary>
    /// Right round bracket ")" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int ROUND_BRACKET_RIGHT = 19;

    /// <summary>
    /// Left block bracket "{" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int BLOCK_BRACKET_LEFT = 20;

    /// <summary>
    /// Right round bracket "}" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int BLOCK_BRACKET_RIGHT = 21;

    /// <summary>
    /// Left square bracket "[" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int SQUARE_BRACKET_LEFT = 22;

    /// <summary>
    /// Right square bracket "]" token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int SQUARE_BRACKET_RIGHT = 23;

    /// <summary>
    /// End of file token. The token class is <see cref="CssToken"/>.
    /// </summary>
    public static final int EOF = 24;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssTokenType value.
    /// </summary>
    public static String getName(/*CssTokenType*/int cssTokenType)
    {
    	switch (cssTokenType)
    	{
    		case IDENT: return "IDENT";
    		case FUNCTION: return "FUNCTION";
    		case AT_KEYWORD: return "AT_KEYWORD";
    		case HASH: return "HASH";
    		case STRING: return "STRING";
    		case BAD_STRING: return "BAD_STRING";
    		case URL: return "URL";
    		case BAD_URL: return "BAD_URL";
    		case DELIM: return "DELIM";
    		case NUMBER: return "NUMBER";
    		case PERCENTAGE: return "PERCENTAGE";
    		case DIMENSION: return "DIMENSION";
    		case WHITESPACE: return "WHITESPACE";
    		case CDO: return "CDO";
    		case CDC: return "CDC";
    		case COLON: return "COLON";
    		case SEMICOLON: return "SEMICOLON";
    		case COMMA: return "COMMA";
    		case ROUND_BRACKET_LEFT: return "ROUND_BRACKET_LEFT";
    		case ROUND_BRACKET_RIGHT: return "ROUND_BRACKET_RIGHT";
    		case BLOCK_BRACKET_LEFT: return "BLOCK_BRACKET_LEFT";
    		case BLOCK_BRACKET_RIGHT: return "BLOCK_BRACKET_RIGHT";
    		case SQUARE_BRACKET_LEFT: return "SQUARE_BRACKET_LEFT";
    		case SQUARE_BRACKET_RIGHT: return "SQUARE_BRACKET_RIGHT";
    		case EOF: return "EOF";
    		default: return "Unknown CssTokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssTokenType value.
    /// </summary>
    public static String toString(/*CssTokenType*/int cssTokenType)
    {
    	switch (cssTokenType)
    	{
    		case IDENT: return "Ident";
    		case FUNCTION: return "Function";
    		case AT_KEYWORD: return "AtKeyword";
    		case HASH: return "Hash";
    		case STRING: return "String";
    		case BAD_STRING: return "BadString";
    		case URL: return "Url";
    		case BAD_URL: return "BadUrl";
    		case DELIM: return "Delim";
    		case NUMBER: return "Number";
    		case PERCENTAGE: return "Percentage";
    		case DIMENSION: return "Dimension";
    		case WHITESPACE: return "Whitespace";
    		case CDO: return "Cdo";
    		case CDC: return "Cdc";
    		case COLON: return "Colon";
    		case SEMICOLON: return "Semicolon";
    		case COMMA: return "Comma";
    		case ROUND_BRACKET_LEFT: return "RoundBracketLeft";
    		case ROUND_BRACKET_RIGHT: return "RoundBracketRight";
    		case BLOCK_BRACKET_LEFT: return "BlockBracketLeft";
    		case BLOCK_BRACKET_RIGHT: return "BlockBracketRight";
    		case SQUARE_BRACKET_LEFT: return "SquareBracketLeft";
    		case SQUARE_BRACKET_RIGHT: return "SquareBracketRight";
    		case EOF: return "Eof";
    		default: return "Unknown CssTokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssTokenType name.
    /// </summary>
    public static /*CssTokenType*/int fromName(String cssTokenTypeName)
    {
    	if ("IDENT".equals(cssTokenTypeName)) return IDENT;
    	if ("FUNCTION".equals(cssTokenTypeName)) return FUNCTION;
    	if ("AT_KEYWORD".equals(cssTokenTypeName)) return AT_KEYWORD;
    	if ("HASH".equals(cssTokenTypeName)) return HASH;
    	if ("STRING".equals(cssTokenTypeName)) return STRING;
    	if ("BAD_STRING".equals(cssTokenTypeName)) return BAD_STRING;
    	if ("URL".equals(cssTokenTypeName)) return URL;
    	if ("BAD_URL".equals(cssTokenTypeName)) return BAD_URL;
    	if ("DELIM".equals(cssTokenTypeName)) return DELIM;
    	if ("NUMBER".equals(cssTokenTypeName)) return NUMBER;
    	if ("PERCENTAGE".equals(cssTokenTypeName)) return PERCENTAGE;
    	if ("DIMENSION".equals(cssTokenTypeName)) return DIMENSION;
    	if ("WHITESPACE".equals(cssTokenTypeName)) return WHITESPACE;
    	if ("CDO".equals(cssTokenTypeName)) return CDO;
    	if ("CDC".equals(cssTokenTypeName)) return CDC;
    	if ("COLON".equals(cssTokenTypeName)) return COLON;
    	if ("SEMICOLON".equals(cssTokenTypeName)) return SEMICOLON;
    	if ("COMMA".equals(cssTokenTypeName)) return COMMA;
    	if ("ROUND_BRACKET_LEFT".equals(cssTokenTypeName)) return ROUND_BRACKET_LEFT;
    	if ("ROUND_BRACKET_RIGHT".equals(cssTokenTypeName)) return ROUND_BRACKET_RIGHT;
    	if ("BLOCK_BRACKET_LEFT".equals(cssTokenTypeName)) return BLOCK_BRACKET_LEFT;
    	if ("BLOCK_BRACKET_RIGHT".equals(cssTokenTypeName)) return BLOCK_BRACKET_RIGHT;
    	if ("SQUARE_BRACKET_LEFT".equals(cssTokenTypeName)) return SQUARE_BRACKET_LEFT;
    	if ("SQUARE_BRACKET_RIGHT".equals(cssTokenTypeName)) return SQUARE_BRACKET_RIGHT;
    	if ("EOF".equals(cssTokenTypeName)) return EOF;
    	throw new IllegalArgumentException("Unknown CssTokenType name.");
    }
    
    /// <summary>
    /// Returns array of CssTokenType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		IDENT,
    		FUNCTION,
    		AT_KEYWORD,
    		HASH,
    		STRING,
    		BAD_STRING,
    		URL,
    		BAD_URL,
    		DELIM,
    		NUMBER,
    		PERCENTAGE,
    		DIMENSION,
    		WHITESPACE,
    		CDO,
    		CDC,
    		COLON,
    		SEMICOLON,
    		COMMA,
    		ROUND_BRACKET_LEFT,
    		ROUND_BRACKET_RIGHT,
    		BLOCK_BRACKET_LEFT,
    		BLOCK_BRACKET_RIGHT,
    		SQUARE_BRACKET_LEFT,
    		SQUARE_BRACKET_RIGHT,
    		EOF,
    	};
    }

    public static final int length = 25;
}

