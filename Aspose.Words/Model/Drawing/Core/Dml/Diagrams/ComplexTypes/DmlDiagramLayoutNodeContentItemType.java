// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/04/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Type of diagram layout node. 
/// </summary>
/*enum*/ final class DmlDiagramLayoutNodeContentItemType
{
    private DmlDiagramLayoutNodeContentItemType(){}
    
    public static final int ALGORITHM = 0;
    public static final int CHOOSE = 1;
    public static final int CONSTRAINT_LIST = 2;
    public static final int LAYOUT_NODE = 3;
    public static final int SHAPE = 4;
    public static final int FOR_EACH = 5;
    public static final int LAYOUT_VARIABLE_PROPERTY_SET = 6;
    public static final int NUMERIC_RULE_LIST = 7;
    public static final int PRESENTATION_OF = 8;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlDiagramLayoutNodeContentItemType value.
    /// </summary>
    public static String getName(/*DmlDiagramLayoutNodeContentItemType*/int dmlDiagramLayoutNodeContentItemType)
    {
    	switch (dmlDiagramLayoutNodeContentItemType)
    	{
    		case ALGORITHM: return "ALGORITHM";
    		case CHOOSE: return "CHOOSE";
    		case CONSTRAINT_LIST: return "CONSTRAINT_LIST";
    		case LAYOUT_NODE: return "LAYOUT_NODE";
    		case SHAPE: return "SHAPE";
    		case FOR_EACH: return "FOR_EACH";
    		case LAYOUT_VARIABLE_PROPERTY_SET: return "LAYOUT_VARIABLE_PROPERTY_SET";
    		case NUMERIC_RULE_LIST: return "NUMERIC_RULE_LIST";
    		case PRESENTATION_OF: return "PRESENTATION_OF";
    		default: return "Unknown DmlDiagramLayoutNodeContentItemType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlDiagramLayoutNodeContentItemType value.
    /// </summary>
    public static String toString(/*DmlDiagramLayoutNodeContentItemType*/int dmlDiagramLayoutNodeContentItemType)
    {
    	switch (dmlDiagramLayoutNodeContentItemType)
    	{
    		case ALGORITHM: return "Algorithm";
    		case CHOOSE: return "Choose";
    		case CONSTRAINT_LIST: return "ConstraintList";
    		case LAYOUT_NODE: return "LayoutNode";
    		case SHAPE: return "Shape";
    		case FOR_EACH: return "ForEach";
    		case LAYOUT_VARIABLE_PROPERTY_SET: return "LayoutVariablePropertySet";
    		case NUMERIC_RULE_LIST: return "NumericRuleList";
    		case PRESENTATION_OF: return "PresentationOf";
    		default: return "Unknown DmlDiagramLayoutNodeContentItemType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlDiagramLayoutNodeContentItemType name.
    /// </summary>
    public static /*DmlDiagramLayoutNodeContentItemType*/int fromName(String dmlDiagramLayoutNodeContentItemTypeName)
    {
    	if ("ALGORITHM".equals(dmlDiagramLayoutNodeContentItemTypeName)) return ALGORITHM;
    	if ("CHOOSE".equals(dmlDiagramLayoutNodeContentItemTypeName)) return CHOOSE;
    	if ("CONSTRAINT_LIST".equals(dmlDiagramLayoutNodeContentItemTypeName)) return CONSTRAINT_LIST;
    	if ("LAYOUT_NODE".equals(dmlDiagramLayoutNodeContentItemTypeName)) return LAYOUT_NODE;
    	if ("SHAPE".equals(dmlDiagramLayoutNodeContentItemTypeName)) return SHAPE;
    	if ("FOR_EACH".equals(dmlDiagramLayoutNodeContentItemTypeName)) return FOR_EACH;
    	if ("LAYOUT_VARIABLE_PROPERTY_SET".equals(dmlDiagramLayoutNodeContentItemTypeName)) return LAYOUT_VARIABLE_PROPERTY_SET;
    	if ("NUMERIC_RULE_LIST".equals(dmlDiagramLayoutNodeContentItemTypeName)) return NUMERIC_RULE_LIST;
    	if ("PRESENTATION_OF".equals(dmlDiagramLayoutNodeContentItemTypeName)) return PRESENTATION_OF;
    	throw new IllegalArgumentException("Unknown DmlDiagramLayoutNodeContentItemType name.");
    }
    
    /// <summary>
    /// Returns array of DmlDiagramLayoutNodeContentItemType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALGORITHM,
    		CHOOSE,
    		CONSTRAINT_LIST,
    		LAYOUT_NODE,
    		SHAPE,
    		FOR_EACH,
    		LAYOUT_VARIABLE_PROPERTY_SET,
    		NUMERIC_RULE_LIST,
    		PRESENTATION_OF,
    	};
    }

    public static final int length = 9;
}

