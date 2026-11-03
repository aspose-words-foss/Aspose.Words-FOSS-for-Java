// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/04/2007 by Vladimir Averkin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies script language of the script anchor.
/// Now, the only known script language is javascipt.
/// </summary>
/*enum*/ final class ScriptLanguage
{
    private ScriptLanguage(){}
    
    public static final int NONE = -1;
    public static final int JAVA_SCRIPT = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ScriptLanguage value.
    /// </summary>
    public static String getName(/*ScriptLanguage*/int scriptLanguage)
    {
    	switch (scriptLanguage)
    	{
    		case NONE: return "NONE";
    		case JAVA_SCRIPT: return "JAVA_SCRIPT";
    		default: return "Unknown ScriptLanguage value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ScriptLanguage value.
    /// </summary>
    public static String toString(/*ScriptLanguage*/int scriptLanguage)
    {
    	switch (scriptLanguage)
    	{
    		case NONE: return "None";
    		case JAVA_SCRIPT: return "JavaScript";
    		default: return "Unknown ScriptLanguage value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ScriptLanguage name.
    /// </summary>
    public static /*ScriptLanguage*/int fromName(String scriptLanguageName)
    {
    	if ("NONE".equals(scriptLanguageName)) return NONE;
    	if ("JAVA_SCRIPT".equals(scriptLanguageName)) return JAVA_SCRIPT;
    	throw new IllegalArgumentException("Unknown ScriptLanguage name.");
    }
    
    /// <summary>
    /// Returns array of ScriptLanguage values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		JAVA_SCRIPT,
    	};
    }

    public static final int length = 2;
}

