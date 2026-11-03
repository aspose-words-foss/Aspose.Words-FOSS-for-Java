// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents values of the 2.24.4.7 ST_FormulaDirection simple types [MS-ODRAWXML].
/// </summary>
/*enum*/ final class FormulaDirection
{
    private FormulaDirection(){}
    
    /// <summary>
    /// The formula is interpreted as producing dimensions column-wise.
    /// </summary>
    public static final int COLUMN = 0;
    /// <summary>
    /// The formula is interpreted as producing dimensions row-wise.
    /// </summary>
    public static final int ROW = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FormulaDirection value.
    /// </summary>
    public static String getName(/*FormulaDirection*/int formulaDirection)
    {
    	switch (formulaDirection)
    	{
    		case COLUMN: return "COLUMN";
    		case ROW: return "ROW";
    		default: return "Unknown FormulaDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FormulaDirection value.
    /// </summary>
    public static String toString(/*FormulaDirection*/int formulaDirection)
    {
    	switch (formulaDirection)
    	{
    		case COLUMN: return "Column";
    		case ROW: return "Row";
    		default: return "Unknown FormulaDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FormulaDirection name.
    /// </summary>
    public static /*FormulaDirection*/int fromName(String formulaDirectionName)
    {
    	if ("COLUMN".equals(formulaDirectionName)) return COLUMN;
    	if ("ROW".equals(formulaDirectionName)) return ROW;
    	throw new IllegalArgumentException("Unknown FormulaDirection name.");
    }
    
    /// <summary>
    /// Returns array of FormulaDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COLUMN,
    		ROW,
    	};
    }

    public static final int length = 2;
}

