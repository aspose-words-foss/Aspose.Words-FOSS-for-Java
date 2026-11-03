// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the token to separate fields or records in the mail merge data file.
/// Due to Microsoft Word limitations can be only stored in DOC files.
/// </summary>
/// <seealso cref="MailMergeSettings"/>
/*enum*/ final class MailMergeLegacySeparator
{
    private MailMergeLegacySeparator(){}
    
    public static final int NONE = 0x00;
    public static final int ENTER = 0x02;
    public static final int TAB = 0x06;
    public static final int COMMA = 0x0a;
    public static final int DOT = 0x0b;
    public static final int EXCLAMATION = 0x0c;
    public static final int SHARP = 0x0d;
    public static final int DOLLAR = 0x0e;
    public static final int PERCENT = 0x0f;
    public static final int AMPERSAND = 0x10;
    public static final int PARENTHESIS_LEFT = 0x11;
    public static final int PARENTHESIS_RIGHT = 0x12;
    public static final int ASTERISK = 0x13;
    public static final int PLUS = 0x14;
    public static final int MINUS = 0x15;
    public static final int SLASH_FORWARD = 0x16;
    public static final int COLON = 0x17;
    public static final int SEMICOLON = 0x18;
    public static final int LESS_THAN = 0x19;
    public static final int EQUALS = 0x1a;
    public static final int GREATER_THAN = 0x1b;
    public static final int QUESTION_MARK = 0x1c;
    public static final int COMMERCIAL_AT = 0x1d;
    public static final int BRACKET_LEFT = 0x1e;
    public static final int BRACKET_RIGHT = 0x1f;
    public static final int POWER = 0x21;
    public static final int UNDERSCORE = 0x22;
    public static final int APOSTROPHE = 0x23;
    public static final int CURLY_BRACKET_LEFT = 0x24;
    public static final int CURLY_BRACKET_RIGHT = 0x25;
    public static final int BAR = 0x26;
    public static final int TILD = 0x27;
    public static final int FIELD_END = 0x46;
    public static final int TABLE_CELL = 0x47;
    public static final int TABLE_ROW = 0x48;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MailMergeLegacySeparator value.
    /// </summary>
    public static String getName(/*MailMergeLegacySeparator*/int mailMergeLegacySeparator)
    {
    	switch (mailMergeLegacySeparator)
    	{
    		case NONE: return "NONE";
    		case ENTER: return "ENTER";
    		case TAB: return "TAB";
    		case COMMA: return "COMMA";
    		case DOT: return "DOT";
    		case EXCLAMATION: return "EXCLAMATION";
    		case SHARP: return "SHARP";
    		case DOLLAR: return "DOLLAR";
    		case PERCENT: return "PERCENT";
    		case AMPERSAND: return "AMPERSAND";
    		case PARENTHESIS_LEFT: return "PARENTHESIS_LEFT";
    		case PARENTHESIS_RIGHT: return "PARENTHESIS_RIGHT";
    		case ASTERISK: return "ASTERISK";
    		case PLUS: return "PLUS";
    		case MINUS: return "MINUS";
    		case SLASH_FORWARD: return "SLASH_FORWARD";
    		case COLON: return "COLON";
    		case SEMICOLON: return "SEMICOLON";
    		case LESS_THAN: return "LESS_THAN";
    		case EQUALS: return "EQUALS";
    		case GREATER_THAN: return "GREATER_THAN";
    		case QUESTION_MARK: return "QUESTION_MARK";
    		case COMMERCIAL_AT: return "COMMERCIAL_AT";
    		case BRACKET_LEFT: return "BRACKET_LEFT";
    		case BRACKET_RIGHT: return "BRACKET_RIGHT";
    		case POWER: return "POWER";
    		case UNDERSCORE: return "UNDERSCORE";
    		case APOSTROPHE: return "APOSTROPHE";
    		case CURLY_BRACKET_LEFT: return "CURLY_BRACKET_LEFT";
    		case CURLY_BRACKET_RIGHT: return "CURLY_BRACKET_RIGHT";
    		case BAR: return "BAR";
    		case TILD: return "TILD";
    		case FIELD_END: return "FIELD_END";
    		case TABLE_CELL: return "TABLE_CELL";
    		case TABLE_ROW: return "TABLE_ROW";
    		default: return "Unknown MailMergeLegacySeparator value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MailMergeLegacySeparator value.
    /// </summary>
    public static String toString(/*MailMergeLegacySeparator*/int mailMergeLegacySeparator)
    {
    	switch (mailMergeLegacySeparator)
    	{
    		case NONE: return "None";
    		case ENTER: return "Enter";
    		case TAB: return "Tab";
    		case COMMA: return "Comma";
    		case DOT: return "Dot";
    		case EXCLAMATION: return "Exclamation";
    		case SHARP: return "Sharp";
    		case DOLLAR: return "Dollar";
    		case PERCENT: return "Percent";
    		case AMPERSAND: return "Ampersand";
    		case PARENTHESIS_LEFT: return "ParenthesisLeft";
    		case PARENTHESIS_RIGHT: return "ParenthesisRight";
    		case ASTERISK: return "Asterisk";
    		case PLUS: return "Plus";
    		case MINUS: return "Minus";
    		case SLASH_FORWARD: return "SlashForward";
    		case COLON: return "Colon";
    		case SEMICOLON: return "Semicolon";
    		case LESS_THAN: return "LessThan";
    		case EQUALS: return "Equals";
    		case GREATER_THAN: return "GreaterThan";
    		case QUESTION_MARK: return "QuestionMark";
    		case COMMERCIAL_AT: return "CommercialAt";
    		case BRACKET_LEFT: return "BracketLeft";
    		case BRACKET_RIGHT: return "BracketRight";
    		case POWER: return "Power";
    		case UNDERSCORE: return "Underscore";
    		case APOSTROPHE: return "Apostrophe";
    		case CURLY_BRACKET_LEFT: return "CurlyBracketLeft";
    		case CURLY_BRACKET_RIGHT: return "CurlyBracketRight";
    		case BAR: return "Bar";
    		case TILD: return "Tild";
    		case FIELD_END: return "FieldEnd";
    		case TABLE_CELL: return "TableCell";
    		case TABLE_ROW: return "TableRow";
    		default: return "Unknown MailMergeLegacySeparator value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MailMergeLegacySeparator name.
    /// </summary>
    public static /*MailMergeLegacySeparator*/int fromName(String mailMergeLegacySeparatorName)
    {
    	if ("NONE".equals(mailMergeLegacySeparatorName)) return NONE;
    	if ("ENTER".equals(mailMergeLegacySeparatorName)) return ENTER;
    	if ("TAB".equals(mailMergeLegacySeparatorName)) return TAB;
    	if ("COMMA".equals(mailMergeLegacySeparatorName)) return COMMA;
    	if ("DOT".equals(mailMergeLegacySeparatorName)) return DOT;
    	if ("EXCLAMATION".equals(mailMergeLegacySeparatorName)) return EXCLAMATION;
    	if ("SHARP".equals(mailMergeLegacySeparatorName)) return SHARP;
    	if ("DOLLAR".equals(mailMergeLegacySeparatorName)) return DOLLAR;
    	if ("PERCENT".equals(mailMergeLegacySeparatorName)) return PERCENT;
    	if ("AMPERSAND".equals(mailMergeLegacySeparatorName)) return AMPERSAND;
    	if ("PARENTHESIS_LEFT".equals(mailMergeLegacySeparatorName)) return PARENTHESIS_LEFT;
    	if ("PARENTHESIS_RIGHT".equals(mailMergeLegacySeparatorName)) return PARENTHESIS_RIGHT;
    	if ("ASTERISK".equals(mailMergeLegacySeparatorName)) return ASTERISK;
    	if ("PLUS".equals(mailMergeLegacySeparatorName)) return PLUS;
    	if ("MINUS".equals(mailMergeLegacySeparatorName)) return MINUS;
    	if ("SLASH_FORWARD".equals(mailMergeLegacySeparatorName)) return SLASH_FORWARD;
    	if ("COLON".equals(mailMergeLegacySeparatorName)) return COLON;
    	if ("SEMICOLON".equals(mailMergeLegacySeparatorName)) return SEMICOLON;
    	if ("LESS_THAN".equals(mailMergeLegacySeparatorName)) return LESS_THAN;
    	if ("EQUALS".equals(mailMergeLegacySeparatorName)) return EQUALS;
    	if ("GREATER_THAN".equals(mailMergeLegacySeparatorName)) return GREATER_THAN;
    	if ("QUESTION_MARK".equals(mailMergeLegacySeparatorName)) return QUESTION_MARK;
    	if ("COMMERCIAL_AT".equals(mailMergeLegacySeparatorName)) return COMMERCIAL_AT;
    	if ("BRACKET_LEFT".equals(mailMergeLegacySeparatorName)) return BRACKET_LEFT;
    	if ("BRACKET_RIGHT".equals(mailMergeLegacySeparatorName)) return BRACKET_RIGHT;
    	if ("POWER".equals(mailMergeLegacySeparatorName)) return POWER;
    	if ("UNDERSCORE".equals(mailMergeLegacySeparatorName)) return UNDERSCORE;
    	if ("APOSTROPHE".equals(mailMergeLegacySeparatorName)) return APOSTROPHE;
    	if ("CURLY_BRACKET_LEFT".equals(mailMergeLegacySeparatorName)) return CURLY_BRACKET_LEFT;
    	if ("CURLY_BRACKET_RIGHT".equals(mailMergeLegacySeparatorName)) return CURLY_BRACKET_RIGHT;
    	if ("BAR".equals(mailMergeLegacySeparatorName)) return BAR;
    	if ("TILD".equals(mailMergeLegacySeparatorName)) return TILD;
    	if ("FIELD_END".equals(mailMergeLegacySeparatorName)) return FIELD_END;
    	if ("TABLE_CELL".equals(mailMergeLegacySeparatorName)) return TABLE_CELL;
    	if ("TABLE_ROW".equals(mailMergeLegacySeparatorName)) return TABLE_ROW;
    	throw new IllegalArgumentException("Unknown MailMergeLegacySeparator name.");
    }
    
    /// <summary>
    /// Returns array of MailMergeLegacySeparator values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ENTER,
    		TAB,
    		COMMA,
    		DOT,
    		EXCLAMATION,
    		SHARP,
    		DOLLAR,
    		PERCENT,
    		AMPERSAND,
    		PARENTHESIS_LEFT,
    		PARENTHESIS_RIGHT,
    		ASTERISK,
    		PLUS,
    		MINUS,
    		SLASH_FORWARD,
    		COLON,
    		SEMICOLON,
    		LESS_THAN,
    		EQUALS,
    		GREATER_THAN,
    		QUESTION_MARK,
    		COMMERCIAL_AT,
    		BRACKET_LEFT,
    		BRACKET_RIGHT,
    		POWER,
    		UNDERSCORE,
    		APOSTROPHE,
    		CURLY_BRACKET_LEFT,
    		CURLY_BRACKET_RIGHT,
    		BAR,
    		TILD,
    		FIELD_END,
    		TABLE_CELL,
    		TABLE_ROW,
    	};
    }

    public static final int length = 35;
}

