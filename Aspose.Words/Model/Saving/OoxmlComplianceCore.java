// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2015 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify which OOXML specification will be used when saving in the DOCX format.
/// This is internal version of the public enum <see cref="OoxmlCompliance"/>.
/// </summary>
/*enum*/ final class OoxmlComplianceCore
{
    private OoxmlComplianceCore(){}
    
    /// <summary>
    /// ECMA-376 1st Edition, 2006.
    /// </summary>
    public static final int ECMA_376 = 0;

    /// <summary>
    /// ISO/IEC 29500:2008 Transitional compliance level.
    /// </summary>
    public static final int ISO_TRANSITIONAL = 1;

    /// <summary>
    /// ISO/IEC 29500:2012 Strict compliance level.
    /// </summary>
    public static final int ISO_STRICT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OoxmlComplianceCore value.
    /// </summary>
    public static String getName(/*OoxmlComplianceCore*/int ooxmlComplianceCore)
    {
    	switch (ooxmlComplianceCore)
    	{
    		case ECMA_376: return "ECMA_376";
    		case ISO_TRANSITIONAL: return "ISO_TRANSITIONAL";
    		case ISO_STRICT: return "ISO_STRICT";
    		default: return "Unknown OoxmlComplianceCore value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OoxmlComplianceCore value.
    /// </summary>
    public static String toString(/*OoxmlComplianceCore*/int ooxmlComplianceCore)
    {
    	switch (ooxmlComplianceCore)
    	{
    		case ECMA_376: return "Ecma376";
    		case ISO_TRANSITIONAL: return "IsoTransitional";
    		case ISO_STRICT: return "IsoStrict";
    		default: return "Unknown OoxmlComplianceCore value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OoxmlComplianceCore name.
    /// </summary>
    public static /*OoxmlComplianceCore*/int fromName(String ooxmlComplianceCoreName)
    {
    	if ("ECMA_376".equals(ooxmlComplianceCoreName)) return ECMA_376;
    	if ("ISO_TRANSITIONAL".equals(ooxmlComplianceCoreName)) return ISO_TRANSITIONAL;
    	if ("ISO_STRICT".equals(ooxmlComplianceCoreName)) return ISO_STRICT;
    	throw new IllegalArgumentException("Unknown OoxmlComplianceCore name.");
    }
    
    /// <summary>
    /// Returns array of OoxmlComplianceCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ECMA_376,
    		ISO_TRANSITIONAL,
    		ISO_STRICT,
    	};
    }

    public static final int length = 3;
}

