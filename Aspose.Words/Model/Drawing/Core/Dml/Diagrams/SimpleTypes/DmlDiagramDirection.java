// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.26 ST_Direction (Diagram Direction Definition)
/// </summary>
/*enum*/ final class DmlDiagramDirection
{
    private DmlDiagramDirection(){}
    
    public static final int NORMAL = 0;
    public static final int REVERSED = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlDiagramDirection value.
    /// </summary>
    public static String getName(/*DmlDiagramDirection*/int dmlDiagramDirection)
    {
    	switch (dmlDiagramDirection)
    	{
    		case NORMAL: return "NORMAL";
    		case REVERSED: return "REVERSED";
    		default: return "Unknown DmlDiagramDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlDiagramDirection value.
    /// </summary>
    public static String toString(/*DmlDiagramDirection*/int dmlDiagramDirection)
    {
    	switch (dmlDiagramDirection)
    	{
    		case NORMAL: return "Normal";
    		case REVERSED: return "Reversed";
    		default: return "Unknown DmlDiagramDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlDiagramDirection name.
    /// </summary>
    public static /*DmlDiagramDirection*/int fromName(String dmlDiagramDirectionName)
    {
    	if ("NORMAL".equals(dmlDiagramDirectionName)) return NORMAL;
    	if ("REVERSED".equals(dmlDiagramDirectionName)) return REVERSED;
    	throw new IllegalArgumentException("Unknown DmlDiagramDirection name.");
    }
    
    /// <summary>
    /// Returns array of DmlDiagramDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		REVERSED,
    	};
    }

    public static final int length = 2;
}

