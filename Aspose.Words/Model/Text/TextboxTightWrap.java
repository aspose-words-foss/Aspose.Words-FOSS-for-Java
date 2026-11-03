// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/03/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text wrapping options for a text box when tight wrapping is set for the text box.
/// </summary>
/// <remarks>
/// See [ECMA-376] Part 4, Section 2.18.99 ST_TextboxTightWrap.
/// </remarks>
/*enum*/ final class TextboxTightWrap
{
    private TextboxTightWrap(){}
    
    /// <summary>
    /// No lines of the paragraph allow the surrounding text to tightly wrap around their edges.
    /// </summary>
    public static final int NONE = 0x00;

    /// <summary>
    /// All lines of the paragraph allow the surrounding text to tightly wrap to their edges.
    /// </summary>
    public static final int ALL_LINES = 0x01;

    /// <summary>
    /// Only the first and last lines of the paragraph allow the surrounding text to tightly wrap around their edges.
    /// </summary>
    public static final int FIRST_AND_LAST_LINE = 0x02;

    /// <summary>
    /// Only the first line of the paragraph allows the surrounding text to tightly wrap around its edges.
    /// </summary>
    public static final int FIRST_LINE_ONLY = 0x03;

    /// <summary>
    /// Only the last line of the paragraph allows the surrounding text to tightly wrap around its edges.
    /// </summary>
    public static final int LAST_LINE_ONLY = 4;

    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextboxTightWrap value.
    /// </summary>
    public static String getName(/*TextboxTightWrap*/int textboxTightWrap)
    {
    	switch (textboxTightWrap)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case ALL_LINES: return "ALL_LINES";
    		case FIRST_AND_LAST_LINE: return "FIRST_AND_LAST_LINE";
    		case FIRST_LINE_ONLY: return "FIRST_LINE_ONLY";
    		case LAST_LINE_ONLY: return "LAST_LINE_ONLY";
    		default: return "Unknown TextboxTightWrap value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextboxTightWrap value.
    /// </summary>
    public static String toString(/*TextboxTightWrap*/int textboxTightWrap)
    {
    	switch (textboxTightWrap)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case ALL_LINES: return "AllLines";
    		case FIRST_AND_LAST_LINE: return "FirstAndLastLine";
    		case FIRST_LINE_ONLY: return "FirstLineOnly";
    		case LAST_LINE_ONLY: return "LastLineOnly";
    		default: return "Unknown TextboxTightWrap value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextboxTightWrap name.
    /// </summary>
    public static /*TextboxTightWrap*/int fromName(String textboxTightWrapName)
    {
    	if ("NONE".equals(textboxTightWrapName)) return NONE;
    	if ("ALL_LINES".equals(textboxTightWrapName)) return ALL_LINES;
    	if ("FIRST_AND_LAST_LINE".equals(textboxTightWrapName)) return FIRST_AND_LAST_LINE;
    	if ("FIRST_LINE_ONLY".equals(textboxTightWrapName)) return FIRST_LINE_ONLY;
    	if ("LAST_LINE_ONLY".equals(textboxTightWrapName)) return LAST_LINE_ONLY;
    	if ("DEFAULT".equals(textboxTightWrapName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown TextboxTightWrap name.");
    }
    
    /// <summary>
    /// Returns array of TextboxTightWrap values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ALL_LINES,
    		FIRST_AND_LAST_LINE,
    		FIRST_LINE_ONLY,
    		LAST_LINE_ONLY,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}

