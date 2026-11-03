// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies an elementary field code token like a text character or a child field's start or end.
/// </summary>
/*enum*/ final class FieldCodeToken
{
    private FieldCodeToken(){}
    
    /// <summary>
    /// A regular text character.
    /// </summary>
    public static final int TEXT_CHAR = 0;
    /// <summary>
    /// A node whose text cannot be read separately and which should be treated as a separate token.
    /// An example is a shape or a table.
    /// </summary>
    public static final int NON_TEXT_NODE = 1;
    /// <summary>
    /// A paragraph node.
    /// </summary>
    public static final int PARAGRAPH = 2;
    /// <summary>
    /// A section node.
    /// </summary>
    public static final int SECTION = 3;
    /// <summary>
    /// The parser is entering a child field's code.
    /// </summary>
    public static final int CHILD_FIELD_START = 4;
    /// <summary>
    /// The parser is leaving a child field's code and entering a child field's result.
    /// </summary>
    public static final int CHILD_FIELD_SEPARATOR = 5;
    /// <summary>
    /// The parser is leaving a child field's result.
    /// </summary>
    public static final int CHILD_FIELD_END = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldCodeToken value.
    /// </summary>
    public static String getName(/*FieldCodeToken*/int fieldCodeToken)
    {
    	switch (fieldCodeToken)
    	{
    		case TEXT_CHAR: return "TEXT_CHAR";
    		case NON_TEXT_NODE: return "NON_TEXT_NODE";
    		case PARAGRAPH: return "PARAGRAPH";
    		case SECTION: return "SECTION";
    		case CHILD_FIELD_START: return "CHILD_FIELD_START";
    		case CHILD_FIELD_SEPARATOR: return "CHILD_FIELD_SEPARATOR";
    		case CHILD_FIELD_END: return "CHILD_FIELD_END";
    		default: return "Unknown FieldCodeToken value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldCodeToken value.
    /// </summary>
    public static String toString(/*FieldCodeToken*/int fieldCodeToken)
    {
    	switch (fieldCodeToken)
    	{
    		case TEXT_CHAR: return "TextChar";
    		case NON_TEXT_NODE: return "NonTextNode";
    		case PARAGRAPH: return "Paragraph";
    		case SECTION: return "Section";
    		case CHILD_FIELD_START: return "ChildFieldStart";
    		case CHILD_FIELD_SEPARATOR: return "ChildFieldSeparator";
    		case CHILD_FIELD_END: return "ChildFieldEnd";
    		default: return "Unknown FieldCodeToken value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldCodeToken name.
    /// </summary>
    public static /*FieldCodeToken*/int fromName(String fieldCodeTokenName)
    {
    	if ("TEXT_CHAR".equals(fieldCodeTokenName)) return TEXT_CHAR;
    	if ("NON_TEXT_NODE".equals(fieldCodeTokenName)) return NON_TEXT_NODE;
    	if ("PARAGRAPH".equals(fieldCodeTokenName)) return PARAGRAPH;
    	if ("SECTION".equals(fieldCodeTokenName)) return SECTION;
    	if ("CHILD_FIELD_START".equals(fieldCodeTokenName)) return CHILD_FIELD_START;
    	if ("CHILD_FIELD_SEPARATOR".equals(fieldCodeTokenName)) return CHILD_FIELD_SEPARATOR;
    	if ("CHILD_FIELD_END".equals(fieldCodeTokenName)) return CHILD_FIELD_END;
    	throw new IllegalArgumentException("Unknown FieldCodeToken name.");
    }
    
    /// <summary>
    /// Returns array of FieldCodeToken values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT_CHAR,
    		NON_TEXT_NODE,
    		PARAGRAPH,
    		SECTION,
    		CHILD_FIELD_START,
    		CHILD_FIELD_SEPARATOR,
    		CHILD_FIELD_END,
    	};
    }

    public static final int length = 7;
}

