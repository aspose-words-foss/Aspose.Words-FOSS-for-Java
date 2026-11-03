// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2018 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the format in which metafiles are saved to HTML documents.
/// </summary>
public /*enum*/ final class HtmlMetafileFormat
{
    private HtmlMetafileFormat(){}
    
    /// <summary>
    /// Metafiles are rendered to raster PNG images.
    /// </summary>
    public static final int PNG = 0;
    /// <summary>
    /// Metafiles are converted to vector SVG images.
    /// </summary>
    public static final int SVG = 1;
    /// <summary>
    /// Metafiles are saved as is, without conversion.
    /// </summary>
    public static final int EMF_OR_WMF = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HtmlMetafileFormat value.
    /// </summary>
    public static String getName(/*HtmlMetafileFormat*/int htmlMetafileFormat)
    {
    	switch (htmlMetafileFormat)
    	{
    		case PNG: return "PNG";
    		case SVG: return "SVG";
    		case EMF_OR_WMF: return "EMF_OR_WMF";
    		default: return "Unknown HtmlMetafileFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HtmlMetafileFormat value.
    /// </summary>
    public static String toString(/*HtmlMetafileFormat*/int htmlMetafileFormat)
    {
    	switch (htmlMetafileFormat)
    	{
    		case PNG: return "Png";
    		case SVG: return "Svg";
    		case EMF_OR_WMF: return "EmfOrWmf";
    		default: return "Unknown HtmlMetafileFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HtmlMetafileFormat name.
    /// </summary>
    public static /*HtmlMetafileFormat*/int fromName(String htmlMetafileFormatName)
    {
    	if ("PNG".equals(htmlMetafileFormatName)) return PNG;
    	if ("SVG".equals(htmlMetafileFormatName)) return SVG;
    	if ("EMF_OR_WMF".equals(htmlMetafileFormatName)) return EMF_OR_WMF;
    	throw new IllegalArgumentException("Unknown HtmlMetafileFormat name.");
    }
    
    /// <summary>
    /// Returns array of HtmlMetafileFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PNG,
    		SVG,
    		EMF_OR_WMF,
    	};
    }

    public static final int length = 3;
}

