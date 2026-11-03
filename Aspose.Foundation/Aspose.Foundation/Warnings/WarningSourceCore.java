// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/10/2011 by Alexey Titov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a module where warning was generated.
/// </summary>
public /*enum*/ final class WarningSourceCore
{
    private WarningSourceCore(){}
    
    public static final int UNKNOWN = 0;
    public static final int SHAPES = 1;
    public static final int DRAWING_ML = 2;
    public static final int METAFILE = 3;
    public static final int XPS = 4;
    public static final int PDF = 5;
    public static final int IMAGE = 6;
    public static final int SVG = 7;
    public static final int HTML_FIXED = 8;
    public static final int POST_SCRIPT = 9;
    public static final int PICT = 10;
    public static final int SVM = 11;
    public static final int FONT = 12;
    public static final int PCL = 13;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WarningSourceCore value.
    /// </summary>
    public static String getName(/*WarningSourceCore*/int warningSourceCore)
    {
    	switch (warningSourceCore)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case SHAPES: return "SHAPES";
    		case DRAWING_ML: return "DRAWING_ML";
    		case METAFILE: return "METAFILE";
    		case XPS: return "XPS";
    		case PDF: return "PDF";
    		case IMAGE: return "IMAGE";
    		case SVG: return "SVG";
    		case HTML_FIXED: return "HTML_FIXED";
    		case POST_SCRIPT: return "POST_SCRIPT";
    		case PICT: return "PICT";
    		case SVM: return "SVM";
    		case FONT: return "FONT";
    		case PCL: return "PCL";
    		default: return "Unknown WarningSourceCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WarningSourceCore value.
    /// </summary>
    public static String toString(/*WarningSourceCore*/int warningSourceCore)
    {
    	switch (warningSourceCore)
    	{
    		case UNKNOWN: return "Unknown";
    		case SHAPES: return "Shapes";
    		case DRAWING_ML: return "DrawingML";
    		case METAFILE: return "Metafile";
    		case XPS: return "Xps";
    		case PDF: return "Pdf";
    		case IMAGE: return "Image";
    		case SVG: return "Svg";
    		case HTML_FIXED: return "HtmlFixed";
    		case POST_SCRIPT: return "PostScript";
    		case PICT: return "Pict";
    		case SVM: return "Svm";
    		case FONT: return "Font";
    		case PCL: return "Pcl";
    		default: return "Unknown WarningSourceCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WarningSourceCore name.
    /// </summary>
    public static /*WarningSourceCore*/int fromName(String warningSourceCoreName)
    {
    	if ("UNKNOWN".equals(warningSourceCoreName)) return UNKNOWN;
    	if ("SHAPES".equals(warningSourceCoreName)) return SHAPES;
    	if ("DRAWING_ML".equals(warningSourceCoreName)) return DRAWING_ML;
    	if ("METAFILE".equals(warningSourceCoreName)) return METAFILE;
    	if ("XPS".equals(warningSourceCoreName)) return XPS;
    	if ("PDF".equals(warningSourceCoreName)) return PDF;
    	if ("IMAGE".equals(warningSourceCoreName)) return IMAGE;
    	if ("SVG".equals(warningSourceCoreName)) return SVG;
    	if ("HTML_FIXED".equals(warningSourceCoreName)) return HTML_FIXED;
    	if ("POST_SCRIPT".equals(warningSourceCoreName)) return POST_SCRIPT;
    	if ("PICT".equals(warningSourceCoreName)) return PICT;
    	if ("SVM".equals(warningSourceCoreName)) return SVM;
    	if ("FONT".equals(warningSourceCoreName)) return FONT;
    	if ("PCL".equals(warningSourceCoreName)) return PCL;
    	throw new IllegalArgumentException("Unknown WarningSourceCore name.");
    }
    
    /// <summary>
    /// Returns array of WarningSourceCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		SHAPES,
    		DRAWING_ML,
    		METAFILE,
    		XPS,
    		PDF,
    		IMAGE,
    		SVG,
    		HTML_FIXED,
    		POST_SCRIPT,
    		PICT,
    		SVM,
    		FONT,
    		PCL,
    	};
    }

    public static final int length = 14;
}

