// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/07/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a shape shadow.
/// </summary>
/*enum*/ final class ShadowTypeCore
{
    private ShadowTypeCore(){}
    
    /// <summary>
    /// N pixel offset shadow.
    /// </summary>
    public static final int OFFSET = 0;   
    /// <summary>
    /// Use second offset too.
    /// </summary>
    public static final int DOUBLE = 1;    
    /// <summary>
    /// Rich perspective shadow (cast relative to shape).
    /// </summary>
    public static final int RICH = 2;      
    /// <summary>
    /// Rich perspective shadow (cast in shape space).
    /// </summary>
    public static final int SHAPE = 3;     
    /// <summary>
    /// Perspective shadow cast in drawing space.
    /// </summary>
    public static final int DRAWING = 4;   
    public static final int EMBOSS_OR_ENGRAVE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShadowTypeCore value.
    /// </summary>
    public static String getName(/*ShadowTypeCore*/int shadowTypeCore)
    {
    	switch (shadowTypeCore)
    	{
    		case OFFSET: return "OFFSET";
    		case DOUBLE: return "DOUBLE";
    		case RICH: return "RICH";
    		case SHAPE: return "SHAPE";
    		case DRAWING: return "DRAWING";
    		case EMBOSS_OR_ENGRAVE: return "EMBOSS_OR_ENGRAVE";
    		default: return "Unknown ShadowTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShadowTypeCore value.
    /// </summary>
    public static String toString(/*ShadowTypeCore*/int shadowTypeCore)
    {
    	switch (shadowTypeCore)
    	{
    		case OFFSET: return "Offset";
    		case DOUBLE: return "Double";
    		case RICH: return "Rich";
    		case SHAPE: return "Shape";
    		case DRAWING: return "Drawing";
    		case EMBOSS_OR_ENGRAVE: return "EmbossOrEngrave";
    		default: return "Unknown ShadowTypeCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ShadowTypeCore name.
    /// </summary>
    public static /*ShadowTypeCore*/int fromName(String shadowTypeCoreName)
    {
    	if ("OFFSET".equals(shadowTypeCoreName)) return OFFSET;
    	if ("DOUBLE".equals(shadowTypeCoreName)) return DOUBLE;
    	if ("RICH".equals(shadowTypeCoreName)) return RICH;
    	if ("SHAPE".equals(shadowTypeCoreName)) return SHAPE;
    	if ("DRAWING".equals(shadowTypeCoreName)) return DRAWING;
    	if ("EMBOSS_OR_ENGRAVE".equals(shadowTypeCoreName)) return EMBOSS_OR_ENGRAVE;
    	throw new IllegalArgumentException("Unknown ShadowTypeCore name.");
    }
    
    /// <summary>
    /// Returns array of ShadowTypeCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OFFSET,
    		DOUBLE,
    		RICH,
    		SHAPE,
    		DRAWING,
    		EMBOSS_OR_ENGRAVE,
    	};
    }

    public static final int length = 6;
}

