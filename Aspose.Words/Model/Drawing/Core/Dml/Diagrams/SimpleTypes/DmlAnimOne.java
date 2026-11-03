// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.3 ST_AnimOneStr (One by One Animation Value Definition)
/// </summary>
/*enum*/ final class DmlAnimOne
{
    private DmlAnimOne(){}
    
    public static final int NONE = 0;
    public static final int ONE = 1;
    public static final int BRANCH = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlAnimOne value.
    /// </summary>
    public static String getName(/*DmlAnimOne*/int dmlAnimOne)
    {
    	switch (dmlAnimOne)
    	{
    		case NONE: return "NONE";
    		case ONE: return "ONE";
    		case BRANCH: return "BRANCH";
    		default: return "Unknown DmlAnimOne value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlAnimOne value.
    /// </summary>
    public static String toString(/*DmlAnimOne*/int dmlAnimOne)
    {
    	switch (dmlAnimOne)
    	{
    		case NONE: return "None";
    		case ONE: return "One";
    		case BRANCH: return "Branch";
    		default: return "Unknown DmlAnimOne value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlAnimOne name.
    /// </summary>
    public static /*DmlAnimOne*/int fromName(String dmlAnimOneName)
    {
    	if ("NONE".equals(dmlAnimOneName)) return NONE;
    	if ("ONE".equals(dmlAnimOneName)) return ONE;
    	if ("BRANCH".equals(dmlAnimOneName)) return BRANCH;
    	throw new IllegalArgumentException("Unknown DmlAnimOne name.");
    }
    
    /// <summary>
    /// Returns array of DmlAnimOne values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ONE,
    		BRANCH,
    	};
    }

    public static final int length = 3;
}

