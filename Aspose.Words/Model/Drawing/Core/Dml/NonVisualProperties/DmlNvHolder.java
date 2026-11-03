// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/11/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of Dml node that holds non-visual properties.
/// </summary>
/*enum*/ final class DmlNvHolder
{
    private DmlNvHolder(){}
    
    public static final int CONNECTION_SHAPE = 0;
    public static final int GRAPHIC_FRAME = 1;
    public static final int GROUP_SHAPE = 2;
    public static final int PICTURE = 3;
    public static final int SHAPE = 4;
    public static final int CONTENT_PART = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlNvHolder value.
    /// </summary>
    public static String getName(/*DmlNvHolder*/int dmlNvHolder)
    {
    	switch (dmlNvHolder)
    	{
    		case CONNECTION_SHAPE: return "CONNECTION_SHAPE";
    		case GRAPHIC_FRAME: return "GRAPHIC_FRAME";
    		case GROUP_SHAPE: return "GROUP_SHAPE";
    		case PICTURE: return "PICTURE";
    		case SHAPE: return "SHAPE";
    		case CONTENT_PART: return "CONTENT_PART";
    		default: return "Unknown DmlNvHolder value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlNvHolder value.
    /// </summary>
    public static String toString(/*DmlNvHolder*/int dmlNvHolder)
    {
    	switch (dmlNvHolder)
    	{
    		case CONNECTION_SHAPE: return "ConnectionShape";
    		case GRAPHIC_FRAME: return "GraphicFrame";
    		case GROUP_SHAPE: return "GroupShape";
    		case PICTURE: return "Picture";
    		case SHAPE: return "Shape";
    		case CONTENT_PART: return "ContentPart";
    		default: return "Unknown DmlNvHolder value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlNvHolder name.
    /// </summary>
    public static /*DmlNvHolder*/int fromName(String dmlNvHolderName)
    {
    	if ("CONNECTION_SHAPE".equals(dmlNvHolderName)) return CONNECTION_SHAPE;
    	if ("GRAPHIC_FRAME".equals(dmlNvHolderName)) return GRAPHIC_FRAME;
    	if ("GROUP_SHAPE".equals(dmlNvHolderName)) return GROUP_SHAPE;
    	if ("PICTURE".equals(dmlNvHolderName)) return PICTURE;
    	if ("SHAPE".equals(dmlNvHolderName)) return SHAPE;
    	if ("CONTENT_PART".equals(dmlNvHolderName)) return CONTENT_PART;
    	throw new IllegalArgumentException("Unknown DmlNvHolder name.");
    }
    
    /// <summary>
    /// Returns array of DmlNvHolder values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CONNECTION_SHAPE,
    		GRAPHIC_FRAME,
    		GROUP_SHAPE,
    		PICTURE,
    		SHAPE,
    		CONTENT_PART,
    	};
    }

    public static final int length = 6;
}

