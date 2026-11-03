// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies subdocument type in a Word file.
/// Note the order of these is important because it is the order in which they occur in Word documents.
/// </summary>
/*enum*/ final class SubDocType
{
    private SubDocType(){}
    
    public static final int MAIN = 0;
    public static final int FOOTNOTE = 1;
    public static final int HEADER = 2;
    public static final int COMMENT = 3;
    public static final int ENDNOTE = 4;
    public static final int TEXTBOX = 5;
    public static final int HEADER_TEXT_BOX = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SubDocType value.
    /// </summary>
    public static String getName(/*SubDocType*/int subDocType)
    {
    	switch (subDocType)
    	{
    		case MAIN: return "MAIN";
    		case FOOTNOTE: return "FOOTNOTE";
    		case HEADER: return "HEADER";
    		case COMMENT: return "COMMENT";
    		case ENDNOTE: return "ENDNOTE";
    		case TEXTBOX: return "TEXTBOX";
    		case HEADER_TEXT_BOX: return "HEADER_TEXT_BOX";
    		default: return "Unknown SubDocType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SubDocType value.
    /// </summary>
    public static String toString(/*SubDocType*/int subDocType)
    {
    	switch (subDocType)
    	{
    		case MAIN: return "Main";
    		case FOOTNOTE: return "Footnote";
    		case HEADER: return "Header";
    		case COMMENT: return "Comment";
    		case ENDNOTE: return "Endnote";
    		case TEXTBOX: return "Textbox";
    		case HEADER_TEXT_BOX: return "HeaderTextBox";
    		default: return "Unknown SubDocType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SubDocType name.
    /// </summary>
    public static /*SubDocType*/int fromName(String subDocTypeName)
    {
    	if ("MAIN".equals(subDocTypeName)) return MAIN;
    	if ("FOOTNOTE".equals(subDocTypeName)) return FOOTNOTE;
    	if ("HEADER".equals(subDocTypeName)) return HEADER;
    	if ("COMMENT".equals(subDocTypeName)) return COMMENT;
    	if ("ENDNOTE".equals(subDocTypeName)) return ENDNOTE;
    	if ("TEXTBOX".equals(subDocTypeName)) return TEXTBOX;
    	if ("HEADER_TEXT_BOX".equals(subDocTypeName)) return HEADER_TEXT_BOX;
    	throw new IllegalArgumentException("Unknown SubDocType name.");
    }
    
    /// <summary>
    /// Returns array of SubDocType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MAIN,
    		FOOTNOTE,
    		HEADER,
    		COMMENT,
    		ENDNOTE,
    		TEXTBOX,
    		HEADER_TEXT_BOX,
    	};
    }

    public static final int length = 7;
}

