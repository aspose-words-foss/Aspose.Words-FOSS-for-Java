// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the behavior of the TAB key in the last control of a form.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.16 Cycle.
/// </dev>
/*enum*/ final class Cycle
{
    private Cycle(){}
    
    public static final int ALL_FORMS = 0x00;
    public static final int CURRENT_FORM = 0x02;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Cycle value.
    /// </summary>
    public static String getName(/*Cycle*/int cycle)
    {
    	switch (cycle)
    	{
    		case ALL_FORMS: return "ALL_FORMS";
    		case CURRENT_FORM: return "CURRENT_FORM";
    		default: return "Unknown Cycle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Cycle value.
    /// </summary>
    public static String toString(/*Cycle*/int cycle)
    {
    	switch (cycle)
    	{
    		case ALL_FORMS: return "AllForms";
    		case CURRENT_FORM: return "CurrentForm";
    		default: return "Unknown Cycle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Cycle name.
    /// </summary>
    public static /*Cycle*/int fromName(String cycleName)
    {
    	if ("ALL_FORMS".equals(cycleName)) return ALL_FORMS;
    	if ("CURRENT_FORM".equals(cycleName)) return CURRENT_FORM;
    	throw new IllegalArgumentException("Unknown Cycle name.");
    }
    
    /// <summary>
    /// Returns array of Cycle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALL_FORMS,
    		CURRENT_FORM,
    	};
    }

    public static final int length = 2;
}

