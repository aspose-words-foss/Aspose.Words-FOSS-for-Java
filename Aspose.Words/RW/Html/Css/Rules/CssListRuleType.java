// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2025 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class CssListRuleType
{
    private CssListRuleType(){}
    
    // @list l0
    public static final int LIST_DEF = 0;
    // @list l0:level1
    public static final int LIST_LEVEL = 1;
    // @list l0 lfo1
    public static final int LIST_LFO = 2;
    // @list l0:level1 lfo1
    public static final int LIST_LEVEL_LFO = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssListRuleType value.
    /// </summary>
    public static String getName(/*CssListRuleType*/int cssListRuleType)
    {
    	switch (cssListRuleType)
    	{
    		case LIST_DEF: return "LIST_DEF";
    		case LIST_LEVEL: return "LIST_LEVEL";
    		case LIST_LFO: return "LIST_LFO";
    		case LIST_LEVEL_LFO: return "LIST_LEVEL_LFO";
    		default: return "Unknown CssListRuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssListRuleType value.
    /// </summary>
    public static String toString(/*CssListRuleType*/int cssListRuleType)
    {
    	switch (cssListRuleType)
    	{
    		case LIST_DEF: return "ListDef";
    		case LIST_LEVEL: return "ListLevel";
    		case LIST_LFO: return "ListLfo";
    		case LIST_LEVEL_LFO: return "ListLevelLfo";
    		default: return "Unknown CssListRuleType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssListRuleType name.
    /// </summary>
    public static /*CssListRuleType*/int fromName(String cssListRuleTypeName)
    {
    	if ("LIST_DEF".equals(cssListRuleTypeName)) return LIST_DEF;
    	if ("LIST_LEVEL".equals(cssListRuleTypeName)) return LIST_LEVEL;
    	if ("LIST_LFO".equals(cssListRuleTypeName)) return LIST_LFO;
    	if ("LIST_LEVEL_LFO".equals(cssListRuleTypeName)) return LIST_LEVEL_LFO;
    	throw new IllegalArgumentException("Unknown CssListRuleType name.");
    }
    
    /// <summary>
    /// Returns array of CssListRuleType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LIST_DEF,
    		LIST_LEVEL,
    		LIST_LFO,
    		LIST_LEVEL_LFO,
    	};
    }

    public static final int length = 4;
}

