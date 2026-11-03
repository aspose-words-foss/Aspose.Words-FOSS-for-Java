// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/05/2021 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how ink (InkML) objects are rendered to fixed page formats.
/// </summary>
public /*enum*/ final class ImlRenderingMode
{
    private ImlRenderingMode(){}
    
    /// <summary>
    /// If fall-back shape is available for ink (InkML) object, Aspose.Words renders fall-back shape instead of the InkML.
    /// </summary>
    /// <remarks>
    /// Please note that after saving a document to a fixed page format with fall-back rendering mode,
    /// InkML objects in the AW document model are permanently replaced with their fall-back counterparts.
    /// As a result, saving the same document again will always use fall-back shapes, even if <see cref="ImlRenderingMode"/> is set to <see cref="InkML"/>.
    /// </remarks>
    public static final int FALLBACK = 0;
    /// <summary>
    /// Aspose.Words ignores fall-back shape of ink (InkML) object and renders InkML itself.
    /// This is the default mode.
    /// </summary>
    public static final int INK_ML = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ImlRenderingMode value.
    /// </summary>
    public static String getName(/*ImlRenderingMode*/int imlRenderingMode)
    {
    	switch (imlRenderingMode)
    	{
    		case FALLBACK: return "FALLBACK";
    		case INK_ML: return "INK_ML";
    		default: return "Unknown ImlRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ImlRenderingMode value.
    /// </summary>
    public static String toString(/*ImlRenderingMode*/int imlRenderingMode)
    {
    	switch (imlRenderingMode)
    	{
    		case FALLBACK: return "Fallback";
    		case INK_ML: return "InkML";
    		default: return "Unknown ImlRenderingMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ImlRenderingMode name.
    /// </summary>
    public static /*ImlRenderingMode*/int fromName(String imlRenderingModeName)
    {
    	if ("FALLBACK".equals(imlRenderingModeName)) return FALLBACK;
    	if ("INK_ML".equals(imlRenderingModeName)) return INK_ML;
    	throw new IllegalArgumentException("Unknown ImlRenderingMode name.");
    }
    
    /// <summary>
    /// Returns array of ImlRenderingMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FALLBACK,
    		INK_ML,
    	};
    }

    public static final int length = 2;
}

