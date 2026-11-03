// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the visual appearance of the control.
/// </summary>
/*enum*/ final class SpecialEffect
{
    private SpecialEffect(){}
    
    public static final int FLAT = 0;
    public static final int RAISED = 1;
    public static final int SUNKEN = 2;
    public static final int ETCHED = 3;
    public static final int BUMP = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SpecialEffect value.
    /// </summary>
    public static String getName(/*SpecialEffect*/int specialEffect)
    {
    	switch (specialEffect)
    	{
    		case FLAT: return "FLAT";
    		case RAISED: return "RAISED";
    		case SUNKEN: return "SUNKEN";
    		case ETCHED: return "ETCHED";
    		case BUMP: return "BUMP";
    		default: return "Unknown SpecialEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SpecialEffect value.
    /// </summary>
    public static String toString(/*SpecialEffect*/int specialEffect)
    {
    	switch (specialEffect)
    	{
    		case FLAT: return "Flat";
    		case RAISED: return "Raised";
    		case SUNKEN: return "Sunken";
    		case ETCHED: return "Etched";
    		case BUMP: return "Bump";
    		default: return "Unknown SpecialEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SpecialEffect name.
    /// </summary>
    public static /*SpecialEffect*/int fromName(String specialEffectName)
    {
    	if ("FLAT".equals(specialEffectName)) return FLAT;
    	if ("RAISED".equals(specialEffectName)) return RAISED;
    	if ("SUNKEN".equals(specialEffectName)) return SUNKEN;
    	if ("ETCHED".equals(specialEffectName)) return ETCHED;
    	if ("BUMP".equals(specialEffectName)) return BUMP;
    	throw new IllegalArgumentException("Unknown SpecialEffect name.");
    }
    
    /// <summary>
    /// Returns array of SpecialEffect values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FLAT,
    		RAISED,
    		SUNKEN,
    		ETCHED,
    		BUMP,
    	};
    }

    public static final int length = 5;
}

