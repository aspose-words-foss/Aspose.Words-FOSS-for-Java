// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Types of tokens into which conditional expressions are split during parsing.
/// </summary>
/*enum*/ final class TokenType
{
    private TokenType(){}
    
    public static final int END_OF_TEXT = 0;
    public static final int ERROR = 1;
    public static final int WHITESPACE = 2;
    public static final int OPEN_PARENTHESIS = 3;
    public static final int CLOSE_PARENTHESIS = 4;
    public static final int NOT = 5;
    public static final int AND = 6;
    public static final int OR = 7;
    public static final int IDENTIFIER = 8;
    public static final int VERSION = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TokenType value.
    /// </summary>
    public static String getName(/*TokenType*/int tokenType)
    {
    	switch (tokenType)
    	{
    		case END_OF_TEXT: return "END_OF_TEXT";
    		case ERROR: return "ERROR";
    		case WHITESPACE: return "WHITESPACE";
    		case OPEN_PARENTHESIS: return "OPEN_PARENTHESIS";
    		case CLOSE_PARENTHESIS: return "CLOSE_PARENTHESIS";
    		case NOT: return "NOT";
    		case AND: return "AND";
    		case OR: return "OR";
    		case IDENTIFIER: return "IDENTIFIER";
    		case VERSION: return "VERSION";
    		default: return "Unknown TokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TokenType value.
    /// </summary>
    public static String toString(/*TokenType*/int tokenType)
    {
    	switch (tokenType)
    	{
    		case END_OF_TEXT: return "EndOfText";
    		case ERROR: return "Error";
    		case WHITESPACE: return "Whitespace";
    		case OPEN_PARENTHESIS: return "OpenParenthesis";
    		case CLOSE_PARENTHESIS: return "CloseParenthesis";
    		case NOT: return "Not";
    		case AND: return "And";
    		case OR: return "Or";
    		case IDENTIFIER: return "Identifier";
    		case VERSION: return "Version";
    		default: return "Unknown TokenType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TokenType name.
    /// </summary>
    public static /*TokenType*/int fromName(String tokenTypeName)
    {
    	if ("END_OF_TEXT".equals(tokenTypeName)) return END_OF_TEXT;
    	if ("ERROR".equals(tokenTypeName)) return ERROR;
    	if ("WHITESPACE".equals(tokenTypeName)) return WHITESPACE;
    	if ("OPEN_PARENTHESIS".equals(tokenTypeName)) return OPEN_PARENTHESIS;
    	if ("CLOSE_PARENTHESIS".equals(tokenTypeName)) return CLOSE_PARENTHESIS;
    	if ("NOT".equals(tokenTypeName)) return NOT;
    	if ("AND".equals(tokenTypeName)) return AND;
    	if ("OR".equals(tokenTypeName)) return OR;
    	if ("IDENTIFIER".equals(tokenTypeName)) return IDENTIFIER;
    	if ("VERSION".equals(tokenTypeName)) return VERSION;
    	throw new IllegalArgumentException("Unknown TokenType name.");
    }
    
    /// <summary>
    /// Returns array of TokenType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		END_OF_TEXT,
    		ERROR,
    		WHITESPACE,
    		OPEN_PARENTHESIS,
    		CLOSE_PARENTHESIS,
    		NOT,
    		AND,
    		OR,
    		IDENTIFIER,
    		VERSION,
    	};
    }

    public static final int length = 10;
}

