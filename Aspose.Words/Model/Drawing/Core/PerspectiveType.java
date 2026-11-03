// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// MS Word defines perspective attributes for shapes. I never actually seen them in MS Word files yet.
/// </summary>
/*enum*/ final class PerspectiveType
{
    private PerspectiveType(){}
    
    /// <summary>
    /// Apply transform in absolute space centered on shape.
    /// </summary>
    public static final int ABSOLUTE = 0;   
    /// <summary>
    /// Apply transform to shape geometry.
    /// </summary>
    public static final int SHAPE = 1;      
    /// <summary>
    /// Apply transform to shape geometry.
    /// </summary>
    public static final int DRAWING = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PerspectiveType value.
    /// </summary>
    public static String getName(/*PerspectiveType*/int perspectiveType)
    {
    	switch (perspectiveType)
    	{
    		case ABSOLUTE: return "ABSOLUTE";
    		case SHAPE: return "SHAPE";
    		case DRAWING: return "DRAWING";
    		default: return "Unknown PerspectiveType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PerspectiveType value.
    /// </summary>
    public static String toString(/*PerspectiveType*/int perspectiveType)
    {
    	switch (perspectiveType)
    	{
    		case ABSOLUTE: return "Absolute";
    		case SHAPE: return "Shape";
    		case DRAWING: return "Drawing";
    		default: return "Unknown PerspectiveType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PerspectiveType name.
    /// </summary>
    public static /*PerspectiveType*/int fromName(String perspectiveTypeName)
    {
    	if ("ABSOLUTE".equals(perspectiveTypeName)) return ABSOLUTE;
    	if ("SHAPE".equals(perspectiveTypeName)) return SHAPE;
    	if ("DRAWING".equals(perspectiveTypeName)) return DRAWING;
    	throw new IllegalArgumentException("Unknown PerspectiveType name.");
    }
    
    /// <summary>
    /// Returns array of PerspectiveType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ABSOLUTE,
    		SHAPE,
    		DRAWING,
    	};
    }

    public static final int length = 3;
}

