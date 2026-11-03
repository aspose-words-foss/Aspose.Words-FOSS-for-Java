// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Hyphenation rule for text runs.
/// RK It seems this is not available in DOCX.
/// </summary>
/*enum*/ final class HyphenRule
{
    private HyphenRule(){}
    
    public static final int NONE = 0;
    public static final int NORMAL = 1;
    public static final int ADD_BEFORE = 2;
    public static final int CHANGE_BEFORE = 3;
    public static final int DELETE_BEFORE = 4;
    public static final int CHANGE_AFTER = 5;
    public static final int DELETE_AND_CHANGE = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HyphenRule value.
    /// </summary>
    public static String getName(/*HyphenRule*/int hyphenRule)
    {
    	switch (hyphenRule)
    	{
    		case NONE: return "NONE";
    		case NORMAL: return "NORMAL";
    		case ADD_BEFORE: return "ADD_BEFORE";
    		case CHANGE_BEFORE: return "CHANGE_BEFORE";
    		case DELETE_BEFORE: return "DELETE_BEFORE";
    		case CHANGE_AFTER: return "CHANGE_AFTER";
    		case DELETE_AND_CHANGE: return "DELETE_AND_CHANGE";
    		default: return "Unknown HyphenRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HyphenRule value.
    /// </summary>
    public static String toString(/*HyphenRule*/int hyphenRule)
    {
    	switch (hyphenRule)
    	{
    		case NONE: return "None";
    		case NORMAL: return "Normal";
    		case ADD_BEFORE: return "AddBefore";
    		case CHANGE_BEFORE: return "ChangeBefore";
    		case DELETE_BEFORE: return "DeleteBefore";
    		case CHANGE_AFTER: return "ChangeAfter";
    		case DELETE_AND_CHANGE: return "DeleteAndChange";
    		default: return "Unknown HyphenRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HyphenRule name.
    /// </summary>
    public static /*HyphenRule*/int fromName(String hyphenRuleName)
    {
    	if ("NONE".equals(hyphenRuleName)) return NONE;
    	if ("NORMAL".equals(hyphenRuleName)) return NORMAL;
    	if ("ADD_BEFORE".equals(hyphenRuleName)) return ADD_BEFORE;
    	if ("CHANGE_BEFORE".equals(hyphenRuleName)) return CHANGE_BEFORE;
    	if ("DELETE_BEFORE".equals(hyphenRuleName)) return DELETE_BEFORE;
    	if ("CHANGE_AFTER".equals(hyphenRuleName)) return CHANGE_AFTER;
    	if ("DELETE_AND_CHANGE".equals(hyphenRuleName)) return DELETE_AND_CHANGE;
    	throw new IllegalArgumentException("Unknown HyphenRule name.");
    }
    
    /// <summary>
    /// Returns array of HyphenRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		NORMAL,
    		ADD_BEFORE,
    		CHANGE_BEFORE,
    		DELETE_BEFORE,
    		CHANGE_AFTER,
    		DELETE_AND_CHANGE,
    	};
    }

    public static final int length = 7;
}

