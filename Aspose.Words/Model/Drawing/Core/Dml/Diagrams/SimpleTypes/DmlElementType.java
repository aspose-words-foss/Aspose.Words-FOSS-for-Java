// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.27 ST_ElementType (Data Point Type)
/// </summary>
/*enum*/ final class DmlElementType
{
    private DmlElementType(){}
    
    public static final int ALL = 0;
    public static final int ASSISTANT = 1;
    public static final int DOCUMENT = 2;
    public static final int NODE = 3;
    public static final int NON_ASSISTANT = 4;
    public static final int NON_NORMAL = 5;
    public static final int NORMAL = 6;
    public static final int PARENT_TRANSITION = 7;
    public static final int PRESENTATION = 8;
    public static final int SIBLING_TRANSITION = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlElementType value.
    /// </summary>
    public static String getName(/*DmlElementType*/int dmlElementType)
    {
    	switch (dmlElementType)
    	{
    		case ALL: return "ALL";
    		case ASSISTANT: return "ASSISTANT";
    		case DOCUMENT: return "DOCUMENT";
    		case NODE: return "NODE";
    		case NON_ASSISTANT: return "NON_ASSISTANT";
    		case NON_NORMAL: return "NON_NORMAL";
    		case NORMAL: return "NORMAL";
    		case PARENT_TRANSITION: return "PARENT_TRANSITION";
    		case PRESENTATION: return "PRESENTATION";
    		case SIBLING_TRANSITION: return "SIBLING_TRANSITION";
    		default: return "Unknown DmlElementType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlElementType value.
    /// </summary>
    public static String toString(/*DmlElementType*/int dmlElementType)
    {
    	switch (dmlElementType)
    	{
    		case ALL: return "All";
    		case ASSISTANT: return "Assistant";
    		case DOCUMENT: return "Document";
    		case NODE: return "Node";
    		case NON_ASSISTANT: return "NonAssistant";
    		case NON_NORMAL: return "NonNormal";
    		case NORMAL: return "Normal";
    		case PARENT_TRANSITION: return "ParentTransition";
    		case PRESENTATION: return "Presentation";
    		case SIBLING_TRANSITION: return "SiblingTransition";
    		default: return "Unknown DmlElementType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlElementType name.
    /// </summary>
    public static /*DmlElementType*/int fromName(String dmlElementTypeName)
    {
    	if ("ALL".equals(dmlElementTypeName)) return ALL;
    	if ("ASSISTANT".equals(dmlElementTypeName)) return ASSISTANT;
    	if ("DOCUMENT".equals(dmlElementTypeName)) return DOCUMENT;
    	if ("NODE".equals(dmlElementTypeName)) return NODE;
    	if ("NON_ASSISTANT".equals(dmlElementTypeName)) return NON_ASSISTANT;
    	if ("NON_NORMAL".equals(dmlElementTypeName)) return NON_NORMAL;
    	if ("NORMAL".equals(dmlElementTypeName)) return NORMAL;
    	if ("PARENT_TRANSITION".equals(dmlElementTypeName)) return PARENT_TRANSITION;
    	if ("PRESENTATION".equals(dmlElementTypeName)) return PRESENTATION;
    	if ("SIBLING_TRANSITION".equals(dmlElementTypeName)) return SIBLING_TRANSITION;
    	throw new IllegalArgumentException("Unknown DmlElementType name.");
    }
    
    /// <summary>
    /// Returns array of DmlElementType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL,
    		ASSISTANT,
    		DOCUMENT,
    		NODE,
    		NON_ASSISTANT,
    		NON_NORMAL,
    		NORMAL,
    		PARENT_TRANSITION,
    		PRESENTATION,
    		SIBLING_TRANSITION,
    	};
    }

    public static final int length = 10;
}

