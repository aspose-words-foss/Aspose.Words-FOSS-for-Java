// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/02/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the script type of the parent math element.
/// </summary>
/*enum*/ final class MathScript
{
    private MathScript(){}
    
    public static final int ROMAN = 0;

    public static final int SCRIPT = 1;

    public static final int FRAKTUR = 2;

    public static final int DOUBLE_STRUCK = 3;

    public static final int SANS_SERIF = 4;

    public static final int MONOSPACE = 5; 

    public static final int DEFAULT = ROMAN;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathScript value.
    /// </summary>
    public static String getName(/*MathScript*/int mathScript)
    {
    	switch (mathScript)
    	{
    		case ROMAN:
    		/*case DEFAULT:*/
    			return "ROMAN" + " | " + "DEFAULT";
    		case SCRIPT: return "SCRIPT";
    		case FRAKTUR: return "FRAKTUR";
    		case DOUBLE_STRUCK: return "DOUBLE_STRUCK";
    		case SANS_SERIF: return "SANS_SERIF";
    		case MONOSPACE: return "MONOSPACE";
    		default: return "Unknown MathScript value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathScript value.
    /// </summary>
    public static String toString(/*MathScript*/int mathScript)
    {
    	switch (mathScript)
    	{
    		case ROMAN:
    		/*case DEFAULT:*/
    			return "Roman" + " | " + "Default";
    		case SCRIPT: return "Script";
    		case FRAKTUR: return "Fraktur";
    		case DOUBLE_STRUCK: return "DoubleStruck";
    		case SANS_SERIF: return "SansSerif";
    		case MONOSPACE: return "Monospace";
    		default: return "Unknown MathScript value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathScript name.
    /// </summary>
    public static /*MathScript*/int fromName(String mathScriptName)
    {
    	if ("ROMAN".equals(mathScriptName)) return ROMAN;
    	if ("SCRIPT".equals(mathScriptName)) return SCRIPT;
    	if ("FRAKTUR".equals(mathScriptName)) return FRAKTUR;
    	if ("DOUBLE_STRUCK".equals(mathScriptName)) return DOUBLE_STRUCK;
    	if ("SANS_SERIF".equals(mathScriptName)) return SANS_SERIF;
    	if ("MONOSPACE".equals(mathScriptName)) return MONOSPACE;
    	if ("DEFAULT".equals(mathScriptName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathScript name.");
    }
    
    /// <summary>
    /// Returns array of MathScript values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ROMAN,
    		SCRIPT,
    		FRAKTUR,
    		DOUBLE_STRUCK,
    		SANS_SERIF,
    		MONOSPACE,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

