// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/05/2013 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Docx relationship type names enum.
/// </summary>
/*enum*/ final class DocxNamespace
{
    private DocxNamespace(){}
    
    public static final int RELATIONSHIPS = 0;
    public static final int MAIN = 1;
    public static final int WML_BETA = 2;
    public static final int SCHEMA_LIBRARY = 3;
    public static final int CUSTOM_XML = 4;
    public static final int DRAWING_ML = 5;
    public static final int DRAWING_ML_MAIN = 6;
    public static final int DRAWING_ML_PICTURE = 7;
    public static final int DRAWING_ML_DIAGRAM = 8;
    public static final int DRAWING_ML_CHART = 9;
    public static final int DRAWING_ML_LOCKED_CANVAS = 10;
    public static final int ACTIVE_X = 11;
    public static final int DIGITAL_SIGNATURE = 12;
    public static final int MICROSOFT_DIGITAL_SIGNATURE = 13;
    public static final int MARKUP_COMPATIBILITY = 14;
    public static final int WORDPROCESSING_CANVAS = 15;
    public static final int DRAWING_ML_ISO_29500 = 16;
    public static final int WORDPROCESSING_GROUP = 17;
    public static final int WORDPROCESSING_INK = 18;
    public static final int WORDROCESSING_SHAPE = 19;
    public static final int W1_4_MARKUP = 20;
    public static final int W1_5_MARKUP = 21;
    public static final int DRAWING_ML_CHART_COLOR_STYLE = 22;
    public static final int DRAWING_ML_CHART_STYLE = 23;
    public static final int DRAWING_ML_DIAGRAM_2008 = 24;
    public static final int DRAWING_ML_CHART_2007 = 25;
    public static final int DRAWING_ML_CHART_DRAWING = 26;
    public static final int MATH = 27;
    public static final int DRAWING_ML_CHART_EX = 28;
    public static final int DRAWING_ML_CHART_EX_1 = 29;
    public static final int DRAWING_ML_CHART_EX_2 = 30;
    public static final int DRAWING_ML_CHART_EX_3 = 31;
    public static final int DRAWING_ML_CHART_EX_4 = 32;
    public static final int DRAWING_ML_CHART_EX_5 = 33;
    public static final int W1_6_SYMEX = 34;
    public static final int W1_6_CID = 35;
    public static final int W1_6_MARKUP = 36;
    public static final int W1_6_CEX = 37;
    public static final int W1_6_SDTDH = 38;
    public static final int CHART_16_AC = 39;
    public static final int COMMENT_REACTION = 40;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DocxNamespace value.
    /// </summary>
    public static String getName(/*DocxNamespace*/int docxNamespace)
    {
    	switch (docxNamespace)
    	{
    		case RELATIONSHIPS: return "RELATIONSHIPS";
    		case MAIN: return "MAIN";
    		case WML_BETA: return "WML_BETA";
    		case SCHEMA_LIBRARY: return "SCHEMA_LIBRARY";
    		case CUSTOM_XML: return "CUSTOM_XML";
    		case DRAWING_ML: return "DRAWING_ML";
    		case DRAWING_ML_MAIN: return "DRAWING_ML_MAIN";
    		case DRAWING_ML_PICTURE: return "DRAWING_ML_PICTURE";
    		case DRAWING_ML_DIAGRAM: return "DRAWING_ML_DIAGRAM";
    		case DRAWING_ML_CHART: return "DRAWING_ML_CHART";
    		case DRAWING_ML_LOCKED_CANVAS: return "DRAWING_ML_LOCKED_CANVAS";
    		case ACTIVE_X: return "ACTIVE_X";
    		case DIGITAL_SIGNATURE: return "DIGITAL_SIGNATURE";
    		case MICROSOFT_DIGITAL_SIGNATURE: return "MICROSOFT_DIGITAL_SIGNATURE";
    		case MARKUP_COMPATIBILITY: return "MARKUP_COMPATIBILITY";
    		case WORDPROCESSING_CANVAS: return "WORDPROCESSING_CANVAS";
    		case DRAWING_ML_ISO_29500: return "DRAWING_ML_ISO_29500";
    		case WORDPROCESSING_GROUP: return "WORDPROCESSING_GROUP";
    		case WORDPROCESSING_INK: return "WORDPROCESSING_INK";
    		case WORDROCESSING_SHAPE: return "WORDROCESSING_SHAPE";
    		case W1_4_MARKUP: return "W1_4_MARKUP";
    		case W1_5_MARKUP: return "W1_5_MARKUP";
    		case DRAWING_ML_CHART_COLOR_STYLE: return "DRAWING_ML_CHART_COLOR_STYLE";
    		case DRAWING_ML_CHART_STYLE: return "DRAWING_ML_CHART_STYLE";
    		case DRAWING_ML_DIAGRAM_2008: return "DRAWING_ML_DIAGRAM_2008";
    		case DRAWING_ML_CHART_2007: return "DRAWING_ML_CHART_2007";
    		case DRAWING_ML_CHART_DRAWING: return "DRAWING_ML_CHART_DRAWING";
    		case MATH: return "MATH";
    		case DRAWING_ML_CHART_EX: return "DRAWING_ML_CHART_EX";
    		case DRAWING_ML_CHART_EX_1: return "DRAWING_ML_CHART_EX_1";
    		case DRAWING_ML_CHART_EX_2: return "DRAWING_ML_CHART_EX_2";
    		case DRAWING_ML_CHART_EX_3: return "DRAWING_ML_CHART_EX_3";
    		case DRAWING_ML_CHART_EX_4: return "DRAWING_ML_CHART_EX_4";
    		case DRAWING_ML_CHART_EX_5: return "DRAWING_ML_CHART_EX_5";
    		case W1_6_SYMEX: return "W1_6_SYMEX";
    		case W1_6_CID: return "W1_6_CID";
    		case W1_6_MARKUP: return "W1_6_MARKUP";
    		case W1_6_CEX: return "W1_6_CEX";
    		case W1_6_SDTDH: return "W1_6_SDTDH";
    		case CHART_16_AC: return "CHART_16_AC";
    		case COMMENT_REACTION: return "COMMENT_REACTION";
    		default: return "Unknown DocxNamespace value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DocxNamespace value.
    /// </summary>
    public static String toString(/*DocxNamespace*/int docxNamespace)
    {
    	switch (docxNamespace)
    	{
    		case RELATIONSHIPS: return "Relationships";
    		case MAIN: return "Main";
    		case WML_BETA: return "WmlBeta";
    		case SCHEMA_LIBRARY: return "SchemaLibrary";
    		case CUSTOM_XML: return "CustomXml";
    		case DRAWING_ML: return "DrawingML";
    		case DRAWING_ML_MAIN: return "DrawingMLMain";
    		case DRAWING_ML_PICTURE: return "DrawingMLPicture";
    		case DRAWING_ML_DIAGRAM: return "DrawingMLDiagram";
    		case DRAWING_ML_CHART: return "DrawingMLChart";
    		case DRAWING_ML_LOCKED_CANVAS: return "DrawingMLLockedCanvas";
    		case ACTIVE_X: return "ActiveX";
    		case DIGITAL_SIGNATURE: return "DigitalSignature";
    		case MICROSOFT_DIGITAL_SIGNATURE: return "MicrosoftDigitalSignature";
    		case MARKUP_COMPATIBILITY: return "MarkupCompatibility";
    		case WORDPROCESSING_CANVAS: return "WordprocessingCanvas";
    		case DRAWING_ML_ISO_29500: return "DrawingMLIso29500";
    		case WORDPROCESSING_GROUP: return "WordprocessingGroup";
    		case WORDPROCESSING_INK: return "WordprocessingInk";
    		case WORDROCESSING_SHAPE: return "WordrocessingShape";
    		case W1_4_MARKUP: return "W14Markup";
    		case W1_5_MARKUP: return "W15Markup";
    		case DRAWING_ML_CHART_COLOR_STYLE: return "DrawingMLChartColorStyle";
    		case DRAWING_ML_CHART_STYLE: return "DrawingMLChartStyle";
    		case DRAWING_ML_DIAGRAM_2008: return "DrawingMLDiagram2008";
    		case DRAWING_ML_CHART_2007: return "DrawingMLChart2007";
    		case DRAWING_ML_CHART_DRAWING: return "DrawingMLChartDrawing";
    		case MATH: return "Math";
    		case DRAWING_ML_CHART_EX: return "DrawingMLChartEx";
    		case DRAWING_ML_CHART_EX_1: return "DrawingMLChartEx1";
    		case DRAWING_ML_CHART_EX_2: return "DrawingMLChartEx2";
    		case DRAWING_ML_CHART_EX_3: return "DrawingMLChartEx3";
    		case DRAWING_ML_CHART_EX_4: return "DrawingMLChartEx4";
    		case DRAWING_ML_CHART_EX_5: return "DrawingMLChartEx5";
    		case W1_6_SYMEX: return "W16Symex";
    		case W1_6_CID: return "W16Cid";
    		case W1_6_MARKUP: return "W16Markup";
    		case W1_6_CEX: return "W16Cex";
    		case W1_6_SDTDH: return "W16Sdtdh";
    		case CHART_16_AC: return "Chart16Ac";
    		case COMMENT_REACTION: return "CommentReaction";
    		default: return "Unknown DocxNamespace value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DocxNamespace name.
    /// </summary>
    public static /*DocxNamespace*/int fromName(String docxNamespaceName)
    {
    	if ("RELATIONSHIPS".equals(docxNamespaceName)) return RELATIONSHIPS;
    	if ("MAIN".equals(docxNamespaceName)) return MAIN;
    	if ("WML_BETA".equals(docxNamespaceName)) return WML_BETA;
    	if ("SCHEMA_LIBRARY".equals(docxNamespaceName)) return SCHEMA_LIBRARY;
    	if ("CUSTOM_XML".equals(docxNamespaceName)) return CUSTOM_XML;
    	if ("DRAWING_ML".equals(docxNamespaceName)) return DRAWING_ML;
    	if ("DRAWING_ML_MAIN".equals(docxNamespaceName)) return DRAWING_ML_MAIN;
    	if ("DRAWING_ML_PICTURE".equals(docxNamespaceName)) return DRAWING_ML_PICTURE;
    	if ("DRAWING_ML_DIAGRAM".equals(docxNamespaceName)) return DRAWING_ML_DIAGRAM;
    	if ("DRAWING_ML_CHART".equals(docxNamespaceName)) return DRAWING_ML_CHART;
    	if ("DRAWING_ML_LOCKED_CANVAS".equals(docxNamespaceName)) return DRAWING_ML_LOCKED_CANVAS;
    	if ("ACTIVE_X".equals(docxNamespaceName)) return ACTIVE_X;
    	if ("DIGITAL_SIGNATURE".equals(docxNamespaceName)) return DIGITAL_SIGNATURE;
    	if ("MICROSOFT_DIGITAL_SIGNATURE".equals(docxNamespaceName)) return MICROSOFT_DIGITAL_SIGNATURE;
    	if ("MARKUP_COMPATIBILITY".equals(docxNamespaceName)) return MARKUP_COMPATIBILITY;
    	if ("WORDPROCESSING_CANVAS".equals(docxNamespaceName)) return WORDPROCESSING_CANVAS;
    	if ("DRAWING_ML_ISO_29500".equals(docxNamespaceName)) return DRAWING_ML_ISO_29500;
    	if ("WORDPROCESSING_GROUP".equals(docxNamespaceName)) return WORDPROCESSING_GROUP;
    	if ("WORDPROCESSING_INK".equals(docxNamespaceName)) return WORDPROCESSING_INK;
    	if ("WORDROCESSING_SHAPE".equals(docxNamespaceName)) return WORDROCESSING_SHAPE;
    	if ("W1_4_MARKUP".equals(docxNamespaceName)) return W1_4_MARKUP;
    	if ("W1_5_MARKUP".equals(docxNamespaceName)) return W1_5_MARKUP;
    	if ("DRAWING_ML_CHART_COLOR_STYLE".equals(docxNamespaceName)) return DRAWING_ML_CHART_COLOR_STYLE;
    	if ("DRAWING_ML_CHART_STYLE".equals(docxNamespaceName)) return DRAWING_ML_CHART_STYLE;
    	if ("DRAWING_ML_DIAGRAM_2008".equals(docxNamespaceName)) return DRAWING_ML_DIAGRAM_2008;
    	if ("DRAWING_ML_CHART_2007".equals(docxNamespaceName)) return DRAWING_ML_CHART_2007;
    	if ("DRAWING_ML_CHART_DRAWING".equals(docxNamespaceName)) return DRAWING_ML_CHART_DRAWING;
    	if ("MATH".equals(docxNamespaceName)) return MATH;
    	if ("DRAWING_ML_CHART_EX".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX;
    	if ("DRAWING_ML_CHART_EX_1".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX_1;
    	if ("DRAWING_ML_CHART_EX_2".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX_2;
    	if ("DRAWING_ML_CHART_EX_3".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX_3;
    	if ("DRAWING_ML_CHART_EX_4".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX_4;
    	if ("DRAWING_ML_CHART_EX_5".equals(docxNamespaceName)) return DRAWING_ML_CHART_EX_5;
    	if ("W1_6_SYMEX".equals(docxNamespaceName)) return W1_6_SYMEX;
    	if ("W1_6_CID".equals(docxNamespaceName)) return W1_6_CID;
    	if ("W1_6_MARKUP".equals(docxNamespaceName)) return W1_6_MARKUP;
    	if ("W1_6_CEX".equals(docxNamespaceName)) return W1_6_CEX;
    	if ("W1_6_SDTDH".equals(docxNamespaceName)) return W1_6_SDTDH;
    	if ("CHART_16_AC".equals(docxNamespaceName)) return CHART_16_AC;
    	if ("COMMENT_REACTION".equals(docxNamespaceName)) return COMMENT_REACTION;
    	throw new IllegalArgumentException("Unknown DocxNamespace name.");
    }
    
    /// <summary>
    /// Returns array of DocxNamespace values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RELATIONSHIPS,
    		MAIN,
    		WML_BETA,
    		SCHEMA_LIBRARY,
    		CUSTOM_XML,
    		DRAWING_ML,
    		DRAWING_ML_MAIN,
    		DRAWING_ML_PICTURE,
    		DRAWING_ML_DIAGRAM,
    		DRAWING_ML_CHART,
    		DRAWING_ML_LOCKED_CANVAS,
    		ACTIVE_X,
    		DIGITAL_SIGNATURE,
    		MICROSOFT_DIGITAL_SIGNATURE,
    		MARKUP_COMPATIBILITY,
    		WORDPROCESSING_CANVAS,
    		DRAWING_ML_ISO_29500,
    		WORDPROCESSING_GROUP,
    		WORDPROCESSING_INK,
    		WORDROCESSING_SHAPE,
    		W1_4_MARKUP,
    		W1_5_MARKUP,
    		DRAWING_ML_CHART_COLOR_STYLE,
    		DRAWING_ML_CHART_STYLE,
    		DRAWING_ML_DIAGRAM_2008,
    		DRAWING_ML_CHART_2007,
    		DRAWING_ML_CHART_DRAWING,
    		MATH,
    		DRAWING_ML_CHART_EX,
    		DRAWING_ML_CHART_EX_1,
    		DRAWING_ML_CHART_EX_2,
    		DRAWING_ML_CHART_EX_3,
    		DRAWING_ML_CHART_EX_4,
    		DRAWING_ML_CHART_EX_5,
    		W1_6_SYMEX,
    		W1_6_CID,
    		W1_6_MARKUP,
    		W1_6_CEX,
    		W1_6_SDTDH,
    		CHART_16_AC,
    		COMMENT_REACTION,
    	};
    }

    public static final int length = 41;
}

