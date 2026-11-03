// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/04/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how document is printed out. 
/// </summary>
public /*enum*/ final class MultiplePagesType
{
    private MultiplePagesType(){}
    
    /// <summary>
    /// Normal printing, no multiple pages specified.
    /// </summary>
    public static final int NORMAL = 0;

    /// <summary>
    /// Swaps left and right margins on facing pages.
    /// </summary>
    public static final int MIRROR_MARGINS = 1;

    /// <summary>
    /// Prints two pages per sheet.
    /// </summary>
    public static final int TWO_PAGES_PER_SHEET = 2;

    /// <summary>
    /// Specifies whether to print the document as a book fold.
    /// </summary>
    public static final int BOOK_FOLD_PRINTING = 3;

    /// <summary>
    /// Specifies whether to print the document as a reverse book fold.
    /// </summary>
    public static final int BOOK_FOLD_PRINTING_REVERSE = 4;

    /// <summary>
    /// Default value is <see cref="Normal"/>
    /// </summary>
    public static final int DEFAULT = NORMAL;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MultiplePagesType value.
    /// </summary>
    public static String getName(/*MultiplePagesType*/int multiplePagesType)
    {
    	switch (multiplePagesType)
    	{
    		case NORMAL:
    		/*case DEFAULT:*/
    			return "NORMAL" + " | " + "DEFAULT";
    		case MIRROR_MARGINS: return "MIRROR_MARGINS";
    		case TWO_PAGES_PER_SHEET: return "TWO_PAGES_PER_SHEET";
    		case BOOK_FOLD_PRINTING: return "BOOK_FOLD_PRINTING";
    		case BOOK_FOLD_PRINTING_REVERSE: return "BOOK_FOLD_PRINTING_REVERSE";
    		default: return "Unknown MultiplePagesType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MultiplePagesType value.
    /// </summary>
    public static String toString(/*MultiplePagesType*/int multiplePagesType)
    {
    	switch (multiplePagesType)
    	{
    		case NORMAL:
    		/*case DEFAULT:*/
    			return "Normal" + " | " + "Default";
    		case MIRROR_MARGINS: return "MirrorMargins";
    		case TWO_PAGES_PER_SHEET: return "TwoPagesPerSheet";
    		case BOOK_FOLD_PRINTING: return "BookFoldPrinting";
    		case BOOK_FOLD_PRINTING_REVERSE: return "BookFoldPrintingReverse";
    		default: return "Unknown MultiplePagesType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MultiplePagesType name.
    /// </summary>
    public static /*MultiplePagesType*/int fromName(String multiplePagesTypeName)
    {
    	if ("NORMAL".equals(multiplePagesTypeName)) return NORMAL;
    	if ("MIRROR_MARGINS".equals(multiplePagesTypeName)) return MIRROR_MARGINS;
    	if ("TWO_PAGES_PER_SHEET".equals(multiplePagesTypeName)) return TWO_PAGES_PER_SHEET;
    	if ("BOOK_FOLD_PRINTING".equals(multiplePagesTypeName)) return BOOK_FOLD_PRINTING;
    	if ("BOOK_FOLD_PRINTING_REVERSE".equals(multiplePagesTypeName)) return BOOK_FOLD_PRINTING_REVERSE;
    	if ("DEFAULT".equals(multiplePagesTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MultiplePagesType name.");
    }
    
    /// <summary>
    /// Returns array of MultiplePagesType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		MIRROR_MARGINS,
    		TWO_PAGES_PER_SHEET,
    		BOOK_FOLD_PRINTING,
    		BOOK_FOLD_PRINTING_REVERSE,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}

