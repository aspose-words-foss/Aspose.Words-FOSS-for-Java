// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text line's type
/// </summary>
/*enum*/ final class TxtLineType
{
    private TxtLineType(){}
    
    /// <summary>
    /// Line with a text
    /// </summary>
    public static final int TEXT_LINE = 0;

    /// <summary>
    /// Line starts a list
    /// </summary>
    public static final int NUMBERING_LINE = 1;

    /// <summary>
    /// End of file marker
    /// </summary>
    public static final int EOF = 2;

    /// <summary>
    /// Empty line
    /// </summary>
    public static final int EMPTY_LINE = 3;

    /// <summary>
    /// Delimiter line like:
    /// -------------------------------------
    /// ***************************************
    /// ==============================
    /// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /// </summary>
    public static final int DELIMITER_LINE = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TxtLineType value.
    /// </summary>
    public static String getName(/*TxtLineType*/int txtLineType)
    {
    	switch (txtLineType)
    	{
    		case TEXT_LINE: return "TEXT_LINE";
    		case NUMBERING_LINE: return "NUMBERING_LINE";
    		case EOF: return "EOF";
    		case EMPTY_LINE: return "EMPTY_LINE";
    		case DELIMITER_LINE: return "DELIMITER_LINE";
    		default: return "Unknown TxtLineType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TxtLineType value.
    /// </summary>
    public static String toString(/*TxtLineType*/int txtLineType)
    {
    	switch (txtLineType)
    	{
    		case TEXT_LINE: return "TextLine";
    		case NUMBERING_LINE: return "NumberingLine";
    		case EOF: return "Eof";
    		case EMPTY_LINE: return "EmptyLine";
    		case DELIMITER_LINE: return "DelimiterLine";
    		default: return "Unknown TxtLineType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TxtLineType name.
    /// </summary>
    public static /*TxtLineType*/int fromName(String txtLineTypeName)
    {
    	if ("TEXT_LINE".equals(txtLineTypeName)) return TEXT_LINE;
    	if ("NUMBERING_LINE".equals(txtLineTypeName)) return NUMBERING_LINE;
    	if ("EOF".equals(txtLineTypeName)) return EOF;
    	if ("EMPTY_LINE".equals(txtLineTypeName)) return EMPTY_LINE;
    	if ("DELIMITER_LINE".equals(txtLineTypeName)) return DELIMITER_LINE;
    	throw new IllegalArgumentException("Unknown TxtLineType name.");
    }
    
    /// <summary>
    /// Returns array of TxtLineType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT_LINE,
    		NUMBERING_LINE,
    		EOF,
    		EMPTY_LINE,
    		DELIMITER_LINE,
    	};
    }

    public static final int length = 5;
}
