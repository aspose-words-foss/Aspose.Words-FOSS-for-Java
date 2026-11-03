// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.51 ST_PtType (Point Type)
/// </summary>
/*enum*/ final class DmlPointType
{
    private DmlPointType(){}
    
    public static final int ASSISTANT_ELEMENT = 0;
    public static final int DOCUMENT = 1;
    public static final int NODE = 2;
    public static final int PARENT_TRANSITION = 3;
    public static final int PRESENTATION = 4;
    public static final int SIBLING_TRANSITION = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPointType value.
    /// </summary>
    public static String getName(/*DmlPointType*/int dmlPointType)
    {
    	switch (dmlPointType)
    	{
    		case ASSISTANT_ELEMENT: return "ASSISTANT_ELEMENT";
    		case DOCUMENT: return "DOCUMENT";
    		case NODE: return "NODE";
    		case PARENT_TRANSITION: return "PARENT_TRANSITION";
    		case PRESENTATION: return "PRESENTATION";
    		case SIBLING_TRANSITION: return "SIBLING_TRANSITION";
    		default: return "Unknown DmlPointType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPointType value.
    /// </summary>
    public static String toString(/*DmlPointType*/int dmlPointType)
    {
    	switch (dmlPointType)
    	{
    		case ASSISTANT_ELEMENT: return "AssistantElement";
    		case DOCUMENT: return "Document";
    		case NODE: return "Node";
    		case PARENT_TRANSITION: return "ParentTransition";
    		case PRESENTATION: return "Presentation";
    		case SIBLING_TRANSITION: return "SiblingTransition";
    		default: return "Unknown DmlPointType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPointType name.
    /// </summary>
    public static /*DmlPointType*/int fromName(String dmlPointTypeName)
    {
    	if ("ASSISTANT_ELEMENT".equals(dmlPointTypeName)) return ASSISTANT_ELEMENT;
    	if ("DOCUMENT".equals(dmlPointTypeName)) return DOCUMENT;
    	if ("NODE".equals(dmlPointTypeName)) return NODE;
    	if ("PARENT_TRANSITION".equals(dmlPointTypeName)) return PARENT_TRANSITION;
    	if ("PRESENTATION".equals(dmlPointTypeName)) return PRESENTATION;
    	if ("SIBLING_TRANSITION".equals(dmlPointTypeName)) return SIBLING_TRANSITION;
    	throw new IllegalArgumentException("Unknown DmlPointType name.");
    }
    
    /// <summary>
    /// Returns array of DmlPointType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ASSISTANT_ELEMENT,
    		DOCUMENT,
    		NODE,
    		PARENT_TRANSITION,
    		PRESENTATION,
    		SIBLING_TRANSITION,
    	};
    }

    public static final int length = 6;
}

