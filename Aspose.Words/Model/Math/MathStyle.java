// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the script type of the parent math element.
/// </summary>
/*enum*/ final class MathStyle
{
    private MathStyle(){}
    
    public static final int PLAIN = 0;
    
    public static final int BOLD = 1;
    
    public static final int ITALIC = 2;

    public static final int BOLD_ITALIC = 3;

    public static final int DEFAULT = ITALIC;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathStyle value.
    /// </summary>
    public static String getName(/*MathStyle*/int mathStyle)
    {
    	switch (mathStyle)
    	{
    		case PLAIN: return "PLAIN";
    		case BOLD: return "BOLD";
    		case ITALIC:
    		/*case DEFAULT:*/
    			return "ITALIC" + " | " + "DEFAULT";
    		case BOLD_ITALIC: return "BOLD_ITALIC";
    		default: return "Unknown MathStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathStyle value.
    /// </summary>
    public static String toString(/*MathStyle*/int mathStyle)
    {
    	switch (mathStyle)
    	{
    		case PLAIN: return "Plain";
    		case BOLD: return "Bold";
    		case ITALIC:
    		/*case DEFAULT:*/
    			return "Italic" + " | " + "Default";
    		case BOLD_ITALIC: return "BoldItalic";
    		default: return "Unknown MathStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathStyle name.
    /// </summary>
    public static /*MathStyle*/int fromName(String mathStyleName)
    {
    	if ("PLAIN".equals(mathStyleName)) return PLAIN;
    	if ("BOLD".equals(mathStyleName)) return BOLD;
    	if ("ITALIC".equals(mathStyleName)) return ITALIC;
    	if ("BOLD_ITALIC".equals(mathStyleName)) return BOLD_ITALIC;
    	if ("DEFAULT".equals(mathStyleName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathStyle name.");
    }
    
    /// <summary>
    /// Returns array of MathStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PLAIN,
    		BOLD,
    		ITALIC,
    		BOLD_ITALIC,
    		DEFAULT,
    	};
    }

    public static final int length = 5;
}

