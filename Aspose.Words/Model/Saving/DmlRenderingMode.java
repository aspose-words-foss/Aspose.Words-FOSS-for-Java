// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/12/2013 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how DrawingML shapes are rendered to fixed page formats.
/// </summary>
public /*enum*/ final class DmlRenderingMode
{
    private DmlRenderingMode(){}
    
    /// <summary>
    /// If fall-back shape is available for DrawingML, Aspose.Words renders fall-back shape instead of the DrawingML.
    /// </summary>
    /// <remarks>
    /// Please note that after saving a document to a fixed page format with fall-back DML rendering mode,
    /// DML shapes in the AW document model are permanently replaced with their fall-back counterparts.
    /// As a result, saving the same document again will always use fall-back shapes, even if <see cref="DmlRenderingMode"/> is set to <see cref="DrawingML"/>.
    /// </remarks>
    public static final int FALLBACK = 0;
    /// <summary>
    /// Aspose.Words ignores fall-back shape of DrawingML and renders DrawingML itself.
    /// This is the default mode.
    /// </summary>
    public static final int DRAWING_ML = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlRenderingMode value.
    /// </summary>
    public static String getName(/*DmlRenderingMode*/int dmlRenderingMode)
    {
    	switch (dmlRenderingMode)
    	{
    		case FALLBACK: return "FALLBACK";
    		case DRAWING_ML: return "DRAWING_ML";
    		default: return "Unknown DmlRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlRenderingMode value.
    /// </summary>
    public static String toString(/*DmlRenderingMode*/int dmlRenderingMode)
    {
    	switch (dmlRenderingMode)
    	{
    		case FALLBACK: return "Fallback";
    		case DRAWING_ML: return "DrawingML";
    		default: return "Unknown DmlRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlRenderingMode name.
    /// </summary>
    public static /*DmlRenderingMode*/int fromName(String dmlRenderingModeName)
    {
    	if ("FALLBACK".equals(dmlRenderingModeName)) return FALLBACK;
    	if ("DRAWING_ML".equals(dmlRenderingModeName)) return DRAWING_ML;
    	throw new IllegalArgumentException("Unknown DmlRenderingMode name.");
    }
    
    /// <summary>
    /// Returns array of DmlRenderingMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FALLBACK,
    		DRAWING_ML,
    	};
    }

    public static final int length = 2;
}

