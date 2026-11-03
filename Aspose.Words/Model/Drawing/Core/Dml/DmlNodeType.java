// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/27/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of the DrawingML node.
/// </summary>
/*enum*/ final class DmlNodeType
{
    private DmlNodeType(){}
    
    /// <summary>
    /// 20.1.2.2.33 sp (Shape)
    /// This element specifies the existence of a single shape.
    /// </summary>
    public static final int SHAPE = 0;

    /// <summary>
    /// 20.1.2.2.10 cxnSp (Connection Shape)
    /// This element specifies a connection shape that is used to connect two sp elements.
    /// </summary>
    public static final int CONNECTOR_SHAPE = 1;

    /// <summary>
    /// 20.2.2.5 pic (Picture)
    /// This element specifies the existence of a picture object within the document.
    /// </summary>
    public static final int PICTURE = 2;

    /// <summary>
    /// 20.1.2.2.20 grpSp (Group shape)
    /// This element specifies a group shape that represents many shapes grouped together
    /// </summary>
    public static final int GROUP_SHAPE = 3;

    /// <summary>
    /// Represents diagram drawing. actually is a group shape.
    /// </summary>
    public static final int SP_TREE = 4;

    /// <summary>
    /// 20.3.2.1 lockedCanvas (Locked Canvas Container)
    /// The locked canvas element acts as a container for more advanced drawing objects.
    /// </summary>
    public static final int LOCKED_CANVAS = 5;

    /// <summary>
    /// 21.2.2.29 chartSpace (Chart Space)
    /// This element specifies overall settings for a single chart, and is the root node for the chart part.
    /// </summary>
    public static final int CHART = 6;

    /// <summary>
    /// Diagram allows the definition of diagrams using DrawingML objects and constructs.
    /// </summary>
    public static final int DIAGRAM = 7;

    /// <summary>
    /// ISO 29500 extension Wordprocessing shape.
    /// This type defines a shape in a WordprocessingML document.
    /// </summary>
    public static final int WORDPROCESSING_SHAPE = 8;

    /// <summary>
    /// ISO 29500 extension Wordprocessing group shape.
    /// This complex type defines the data that represents a group of graphical objects in WordprocessingML.
    /// </summary>
    public static final int WORDPROCESSING_GROUP_SHAPE = 9;

    /// <summary>
    /// ISO 29500 extension Wordprocessing canvas.
    /// This type defines a drawing canvas in a WordprocessingML document.
    /// </summary>
    public static final int WORDPROCESSING_CANVAS = 10;

    /// <summary>
    /// ISO 29500 contentPart.
    /// This element specifies a reference to XML content in a format not defined by ISO/IEC 29500.
    /// </summary>
    public static final int CONTENT_PART = 11;

    /// <summary>
    /// 20.4.2.31 graphicFrame (Graphical object container) 
    /// This element type specifies a container for a graphical object in WordprocessingML. 
    /// </summary>
    public static final int GRAPHIC_FRAME = 12;

    /// <summary>
    /// 2.24.1.2 chartSpace [MS-ODRAWXML]
    /// This element specifies the chart container object. It is introduced in MS Word 2016.
    /// </summary>
    public static final int CHART_EX = 13;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlNodeType value.
    /// </summary>
    public static String getName(/*DmlNodeType*/int dmlNodeType)
    {
    	switch (dmlNodeType)
    	{
    		case SHAPE: return "SHAPE";
    		case CONNECTOR_SHAPE: return "CONNECTOR_SHAPE";
    		case PICTURE: return "PICTURE";
    		case GROUP_SHAPE: return "GROUP_SHAPE";
    		case SP_TREE: return "SP_TREE";
    		case LOCKED_CANVAS: return "LOCKED_CANVAS";
    		case CHART: return "CHART";
    		case DIAGRAM: return "DIAGRAM";
    		case WORDPROCESSING_SHAPE: return "WORDPROCESSING_SHAPE";
    		case WORDPROCESSING_GROUP_SHAPE: return "WORDPROCESSING_GROUP_SHAPE";
    		case WORDPROCESSING_CANVAS: return "WORDPROCESSING_CANVAS";
    		case CONTENT_PART: return "CONTENT_PART";
    		case GRAPHIC_FRAME: return "GRAPHIC_FRAME";
    		case CHART_EX: return "CHART_EX";
    		default: return "Unknown DmlNodeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlNodeType value.
    /// </summary>
    public static String toString(/*DmlNodeType*/int dmlNodeType)
    {
    	switch (dmlNodeType)
    	{
    		case SHAPE: return "Shape";
    		case CONNECTOR_SHAPE: return "ConnectorShape";
    		case PICTURE: return "Picture";
    		case GROUP_SHAPE: return "GroupShape";
    		case SP_TREE: return "SpTree";
    		case LOCKED_CANVAS: return "LockedCanvas";
    		case CHART: return "Chart";
    		case DIAGRAM: return "Diagram";
    		case WORDPROCESSING_SHAPE: return "WordprocessingShape";
    		case WORDPROCESSING_GROUP_SHAPE: return "WordprocessingGroupShape";
    		case WORDPROCESSING_CANVAS: return "WordprocessingCanvas";
    		case CONTENT_PART: return "ContentPart";
    		case GRAPHIC_FRAME: return "GraphicFrame";
    		case CHART_EX: return "ChartEx";
    		default: return "Unknown DmlNodeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlNodeType name.
    /// </summary>
    public static /*DmlNodeType*/int fromName(String dmlNodeTypeName)
    {
    	if ("SHAPE".equals(dmlNodeTypeName)) return SHAPE;
    	if ("CONNECTOR_SHAPE".equals(dmlNodeTypeName)) return CONNECTOR_SHAPE;
    	if ("PICTURE".equals(dmlNodeTypeName)) return PICTURE;
    	if ("GROUP_SHAPE".equals(dmlNodeTypeName)) return GROUP_SHAPE;
    	if ("SP_TREE".equals(dmlNodeTypeName)) return SP_TREE;
    	if ("LOCKED_CANVAS".equals(dmlNodeTypeName)) return LOCKED_CANVAS;
    	if ("CHART".equals(dmlNodeTypeName)) return CHART;
    	if ("DIAGRAM".equals(dmlNodeTypeName)) return DIAGRAM;
    	if ("WORDPROCESSING_SHAPE".equals(dmlNodeTypeName)) return WORDPROCESSING_SHAPE;
    	if ("WORDPROCESSING_GROUP_SHAPE".equals(dmlNodeTypeName)) return WORDPROCESSING_GROUP_SHAPE;
    	if ("WORDPROCESSING_CANVAS".equals(dmlNodeTypeName)) return WORDPROCESSING_CANVAS;
    	if ("CONTENT_PART".equals(dmlNodeTypeName)) return CONTENT_PART;
    	if ("GRAPHIC_FRAME".equals(dmlNodeTypeName)) return GRAPHIC_FRAME;
    	if ("CHART_EX".equals(dmlNodeTypeName)) return CHART_EX;
    	throw new IllegalArgumentException("Unknown DmlNodeType name.");
    }
    
    /// <summary>
    /// Returns array of DmlNodeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SHAPE,
    		CONNECTOR_SHAPE,
    		PICTURE,
    		GROUP_SHAPE,
    		SP_TREE,
    		LOCKED_CANVAS,
    		CHART,
    		DIAGRAM,
    		WORDPROCESSING_SHAPE,
    		WORDPROCESSING_GROUP_SHAPE,
    		WORDPROCESSING_CANVAS,
    		CONTENT_PART,
    		GRAPHIC_FRAME,
    		CHART_EX,
    	};
    }

    public static final int length = 14;
}

