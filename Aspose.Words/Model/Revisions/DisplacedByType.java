// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible values for the location of a single custom XML element's start and/or end
/// tag relative to the location of an annotation tag in document order.
/// </summary>
/*enum*/ final class DisplacedByType
{
    private DisplacedByType(){}
    
    /// <summary>
    /// Specifies that this annotation anchor shall be displaced
    /// by the physical representation of the next element of
    /// custom XML markup in the document.
    /// </summary>
    public static final int NEXT = 0;

    /// <summary>
    /// Specifies that this annotation anchor shall be displaced
    /// by the physical representation of the previous element
    /// of custom XML markup in the document.
    /// </summary>
    public static final int PREV = 1;

    /// <summary>
    /// Means that location is not specified.
    /// </summary>
    public static final int UNSPECIFIED = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DisplacedByType value.
    /// </summary>
    public static String getName(/*DisplacedByType*/int displacedByType)
    {
    	switch (displacedByType)
    	{
    		case NEXT: return "NEXT";
    		case PREV: return "PREV";
    		case UNSPECIFIED: return "UNSPECIFIED";
    		default: return "Unknown DisplacedByType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DisplacedByType value.
    /// </summary>
    public static String toString(/*DisplacedByType*/int displacedByType)
    {
    	switch (displacedByType)
    	{
    		case NEXT: return "Next";
    		case PREV: return "Prev";
    		case UNSPECIFIED: return "Unspecified";
    		default: return "Unknown DisplacedByType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DisplacedByType name.
    /// </summary>
    public static /*DisplacedByType*/int fromName(String displacedByTypeName)
    {
    	if ("NEXT".equals(displacedByTypeName)) return NEXT;
    	if ("PREV".equals(displacedByTypeName)) return PREV;
    	if ("UNSPECIFIED".equals(displacedByTypeName)) return UNSPECIFIED;
    	throw new IllegalArgumentException("Unknown DisplacedByType name.");
    }
    
    /// <summary>
    /// Returns array of DisplacedByType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEXT,
    		PREV,
    		UNSPECIFIED,
    	};
    }

    public static final int length = 3;
}

