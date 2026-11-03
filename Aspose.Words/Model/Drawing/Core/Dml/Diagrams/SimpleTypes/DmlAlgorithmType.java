// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.1 ST_AlgorithmType
/// </summary>
/*enum*/ final class DmlAlgorithmType
{
    private DmlAlgorithmType(){}
    
    public static final int COMPOSITE = 0;
    public static final int CONNECTOR = 1;
    public static final int CYCLE = 2;
    public static final int HIERARCHY_CHILD = 3;
    public static final int HIERARCHY_ROOT = 4;
    public static final int LINEAR = 5;
    public static final int PYRAMID = 6;
    public static final int SNAKE = 7;
    public static final int SPACE = 8;
    public static final int TEXT = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAlgorithmType value.
    /// </summary>
    public static String getName(/*DmlAlgorithmType*/int dmlAlgorithmType)
    {
    	switch (dmlAlgorithmType)
    	{
    		case COMPOSITE: return "COMPOSITE";
    		case CONNECTOR: return "CONNECTOR";
    		case CYCLE: return "CYCLE";
    		case HIERARCHY_CHILD: return "HIERARCHY_CHILD";
    		case HIERARCHY_ROOT: return "HIERARCHY_ROOT";
    		case LINEAR: return "LINEAR";
    		case PYRAMID: return "PYRAMID";
    		case SNAKE: return "SNAKE";
    		case SPACE: return "SPACE";
    		case TEXT: return "TEXT";
    		default: return "Unknown DmlAlgorithmType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAlgorithmType value.
    /// </summary>
    public static String toString(/*DmlAlgorithmType*/int dmlAlgorithmType)
    {
    	switch (dmlAlgorithmType)
    	{
    		case COMPOSITE: return "Composite";
    		case CONNECTOR: return "Connector";
    		case CYCLE: return "Cycle";
    		case HIERARCHY_CHILD: return "HierarchyChild";
    		case HIERARCHY_ROOT: return "HierarchyRoot";
    		case LINEAR: return "Linear";
    		case PYRAMID: return "Pyramid";
    		case SNAKE: return "Snake";
    		case SPACE: return "Space";
    		case TEXT: return "Text";
    		default: return "Unknown DmlAlgorithmType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAlgorithmType name.
    /// </summary>
    public static /*DmlAlgorithmType*/int fromName(String dmlAlgorithmTypeName)
    {
    	if ("COMPOSITE".equals(dmlAlgorithmTypeName)) return COMPOSITE;
    	if ("CONNECTOR".equals(dmlAlgorithmTypeName)) return CONNECTOR;
    	if ("CYCLE".equals(dmlAlgorithmTypeName)) return CYCLE;
    	if ("HIERARCHY_CHILD".equals(dmlAlgorithmTypeName)) return HIERARCHY_CHILD;
    	if ("HIERARCHY_ROOT".equals(dmlAlgorithmTypeName)) return HIERARCHY_ROOT;
    	if ("LINEAR".equals(dmlAlgorithmTypeName)) return LINEAR;
    	if ("PYRAMID".equals(dmlAlgorithmTypeName)) return PYRAMID;
    	if ("SNAKE".equals(dmlAlgorithmTypeName)) return SNAKE;
    	if ("SPACE".equals(dmlAlgorithmTypeName)) return SPACE;
    	if ("TEXT".equals(dmlAlgorithmTypeName)) return TEXT;
    	throw new IllegalArgumentException("Unknown DmlAlgorithmType name.");
    }
    
    /// <summary>
    /// Returns array of DmlAlgorithmType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COMPOSITE,
    		CONNECTOR,
    		CYCLE,
    		HIERARCHY_CHILD,
    		HIERARCHY_ROOT,
    		LINEAR,
    		PYRAMID,
    		SNAKE,
    		SPACE,
    		TEXT,
    	};
    }

    public static final int length = 10;
}

