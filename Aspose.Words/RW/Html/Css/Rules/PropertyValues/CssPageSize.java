// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies page size media names.
/// </summary>
/// <remarks>
/// A page size can be specified using one of the following media names. 
/// This is the equivalent of specifying the ‘page-size’ using length values. 
/// The definition of the media names comes from Media Standardized Names.
/// http://www.w3.org/TR/css3-page/#page-size-prop
/// </remarks>
/*enum*/ final class CssPageSize
{
    private CssPageSize(){}
    
    public static final int NOT_SPECIFIED = 0;
    /// <summary>
    /// Equivalent to the size of ISO A5 media: 148mm wide and 210 mm high.
    /// </summary>
    public static final int A5 = 1;
    /// <summary>
    /// Equivalent to the size of ISO A4 media: 210 mm wide and 297 mm high.
    /// </summary>
    public static final int A4 = 2;
    /// <summary>
    /// Equivalent to the size of ISO A3 media: 297mm wide and 420mm high.
    /// </summary>
    public static final int A3 = 3;
    /// <summary>
    /// Equivalent to the size of ISO B5 media: 176mm wide by 250mm high.
    /// </summary>
    public static final int B5 = 4;
    /// <summary>
    /// Equivalent to the size of ISO B4 media: 250mm wide by 353mm high.
    /// </summary>
    public static final int B4 = 5;
    /// <summary>
    /// Equivalent to the size of North American letter media: 8.5 inches wide and 11 inches high
    /// </summary>
    public static final int LETTER = 6;
    /// <summary>
    /// Equivalent to the size of North American legal: 8.5 inches wide by 14 inches high.
    /// </summary>
    public static final int LEGAL = 7;
    /// <summary>
    /// Equivalent to the size of North American ledger: 11 inches wide by 17 inches high.
    /// </summary>
    public static final int LEDGER = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssPageSize value.
    /// </summary>
    public static String getName(/*CssPageSize*/int cssPageSize)
    {
    	switch (cssPageSize)
    	{
    		case NOT_SPECIFIED: return "NOT_SPECIFIED";
    		case A5: return "A5";
    		case A4: return "A4";
    		case A3: return "A3";
    		case B5: return "B5";
    		case B4: return "B4";
    		case LETTER: return "LETTER";
    		case LEGAL: return "LEGAL";
    		case LEDGER: return "LEDGER";
    		default: return "Unknown CssPageSize value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssPageSize value.
    /// </summary>
    public static String toString(/*CssPageSize*/int cssPageSize)
    {
    	switch (cssPageSize)
    	{
    		case NOT_SPECIFIED: return "NotSpecified";
    		case A5: return "A5";
    		case A4: return "A4";
    		case A3: return "A3";
    		case B5: return "B5";
    		case B4: return "B4";
    		case LETTER: return "Letter";
    		case LEGAL: return "Legal";
    		case LEDGER: return "Ledger";
    		default: return "Unknown CssPageSize value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssPageSize name.
    /// </summary>
    public static /*CssPageSize*/int fromName(String cssPageSizeName)
    {
    	if ("NOT_SPECIFIED".equals(cssPageSizeName)) return NOT_SPECIFIED;
    	if ("A5".equals(cssPageSizeName)) return A5;
    	if ("A4".equals(cssPageSizeName)) return A4;
    	if ("A3".equals(cssPageSizeName)) return A3;
    	if ("B5".equals(cssPageSizeName)) return B5;
    	if ("B4".equals(cssPageSizeName)) return B4;
    	if ("LETTER".equals(cssPageSizeName)) return LETTER;
    	if ("LEGAL".equals(cssPageSizeName)) return LEGAL;
    	if ("LEDGER".equals(cssPageSizeName)) return LEDGER;
    	throw new IllegalArgumentException("Unknown CssPageSize name.");
    }
    
    /// <summary>
    /// Returns array of CssPageSize values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NOT_SPECIFIED,
    		A5,
    		A4,
    		A3,
    		B5,
    		B4,
    		LETTER,
    		LEGAL,
    		LEDGER,
    	};
    }

    public static final int length = 9;
}

