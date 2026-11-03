// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Determines how Aspose.Words resizes the table when you invoke the <see cref="Table.AutoFit"/> method.
/// </summary>
public /*enum*/ final class AutoFitBehavior
{
    private AutoFitBehavior(){}
    
    /// <summary>
    /// <para>Aspose.Words enables the AutoFit option, removes the preferred width from the table and all cells and then updates the table layout.</para>
    /// 
    /// <para>In the resulting table, cell widths are updated to fit the table contents. Most likely, the table will shrink.</para>
    /// </summary>
    public static final int AUTO_FIT_TO_CONTENTS = 0;

    /// <summary>
    /// <para>When you use this value, Aspose.Words enables the AutoFit option, sets the preferred width for the table to 100%, 
    /// removes preferred widths from all cells and then updates the table layout.</para>
    /// 
    /// <para>As a result, the table occupies all available width and the cell widths are updated to fit table contents.</para>
    /// </summary>
    public static final int AUTO_FIT_TO_WINDOW = 1;

    /// <summary>
    /// <para>Aspose.Words disables the AutoFit option and removes the preferred with from the table.</para>
    /// 
    /// <para>The widths of the cells remain as they are specified by their <see cref="CellFormat.Width"/> properties.</para>
    /// </summary>
    public static final int FIXED_COLUMN_WIDTHS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AutoFitBehavior value.
    /// </summary>
    public static String getName(/*AutoFitBehavior*/int autoFitBehavior)
    {
    	switch (autoFitBehavior)
    	{
    		case AUTO_FIT_TO_CONTENTS: return "AUTO_FIT_TO_CONTENTS";
    		case AUTO_FIT_TO_WINDOW: return "AUTO_FIT_TO_WINDOW";
    		case FIXED_COLUMN_WIDTHS: return "FIXED_COLUMN_WIDTHS";
    		default: return "Unknown AutoFitBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AutoFitBehavior value.
    /// </summary>
    public static String toString(/*AutoFitBehavior*/int autoFitBehavior)
    {
    	switch (autoFitBehavior)
    	{
    		case AUTO_FIT_TO_CONTENTS: return "AutoFitToContents";
    		case AUTO_FIT_TO_WINDOW: return "AutoFitToWindow";
    		case FIXED_COLUMN_WIDTHS: return "FixedColumnWidths";
    		default: return "Unknown AutoFitBehavior value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AutoFitBehavior name.
    /// </summary>
    public static /*AutoFitBehavior*/int fromName(String autoFitBehaviorName)
    {
    	if ("AUTO_FIT_TO_CONTENTS".equals(autoFitBehaviorName)) return AUTO_FIT_TO_CONTENTS;
    	if ("AUTO_FIT_TO_WINDOW".equals(autoFitBehaviorName)) return AUTO_FIT_TO_WINDOW;
    	if ("FIXED_COLUMN_WIDTHS".equals(autoFitBehaviorName)) return FIXED_COLUMN_WIDTHS;
    	throw new IllegalArgumentException("Unknown AutoFitBehavior name.");
    }
    
    /// <summary>
    /// Returns array of AutoFitBehavior values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTO_FIT_TO_CONTENTS,
    		AUTO_FIT_TO_WINDOW,
    		FIXED_COLUMN_WIDTHS,
    	};
    }

    public static final int length = 3;
}

