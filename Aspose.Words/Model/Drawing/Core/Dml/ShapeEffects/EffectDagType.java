// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/25/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of effect container, either sibling or tree.
/// </summary>
/*enum*/ final class EffectDagType
{
    private EffectDagType(){}
    
    /// <summary>
    /// Each effect is separately applied to the parent object.
    /// </summary>
    public static final int SIBLING = 0;

    /// <summary>
    /// Each effect is applied to the result of the previous effect.
    /// </summary>
    public static final int TREE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EffectDagType value.
    /// </summary>
    public static String getName(/*EffectDagType*/int effectDagType)
    {
    	switch (effectDagType)
    	{
    		case SIBLING: return "SIBLING";
    		case TREE: return "TREE";
    		default: return "Unknown EffectDagType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EffectDagType value.
    /// </summary>
    public static String toString(/*EffectDagType*/int effectDagType)
    {
    	switch (effectDagType)
    	{
    		case SIBLING: return "Sibling";
    		case TREE: return "Tree";
    		default: return "Unknown EffectDagType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EffectDagType name.
    /// </summary>
    public static /*EffectDagType*/int fromName(String effectDagTypeName)
    {
    	if ("SIBLING".equals(effectDagTypeName)) return SIBLING;
    	if ("TREE".equals(effectDagTypeName)) return TREE;
    	throw new IllegalArgumentException("Unknown EffectDagType name.");
    }
    
    /// <summary>
    /// Returns array of EffectDagType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SIBLING,
    		TREE,
    	};
    }

    public static final int length = 2;
}

