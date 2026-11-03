// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify which OOXML specification will be used when saving in the DOCX format.
/// </summary>
public /*enum*/ final class OoxmlCompliance
{
    private OoxmlCompliance(){}
    
    /// <summary>
    /// ECMA-376 1st Edition, 2006.
    /// </summary>
    public static final int ECMA_376_2006 = 0;
    
    /// <summary>
    /// ISO/IEC 29500:2008 Transitional compliance level.
    /// </summary>
    public static final int ISO_29500_2008_TRANSITIONAL = 1;
    
    /// <summary>
    /// ISO/IEC 29500:2008 Strict compliance level.
    /// </summary>
    public static final int ISO_29500_2008_STRICT = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OoxmlCompliance value.
    /// </summary>
    public static String getName(/*OoxmlCompliance*/int ooxmlCompliance)
    {
    	switch (ooxmlCompliance)
    	{
    		case ECMA_376_2006: return "ECMA_376_2006";
    		case ISO_29500_2008_TRANSITIONAL: return "ISO_29500_2008_TRANSITIONAL";
    		case ISO_29500_2008_STRICT: return "ISO_29500_2008_STRICT";
    		default: return "Unknown OoxmlCompliance value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OoxmlCompliance value.
    /// </summary>
    public static String toString(/*OoxmlCompliance*/int ooxmlCompliance)
    {
    	switch (ooxmlCompliance)
    	{
    		case ECMA_376_2006: return "Ecma376_2006";
    		case ISO_29500_2008_TRANSITIONAL: return "Iso29500_2008_Transitional";
    		case ISO_29500_2008_STRICT: return "Iso29500_2008_Strict";
    		default: return "Unknown OoxmlCompliance value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OoxmlCompliance name.
    /// </summary>
    public static /*OoxmlCompliance*/int fromName(String ooxmlComplianceName)
    {
    	if ("ECMA_376_2006".equals(ooxmlComplianceName)) return ECMA_376_2006;
    	if ("ISO_29500_2008_TRANSITIONAL".equals(ooxmlComplianceName)) return ISO_29500_2008_TRANSITIONAL;
    	if ("ISO_29500_2008_STRICT".equals(ooxmlComplianceName)) return ISO_29500_2008_STRICT;
    	throw new IllegalArgumentException("Unknown OoxmlCompliance name.");
    }
    
    /// <summary>
    /// Returns array of OoxmlCompliance values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ECMA_376_2006,
    		ISO_29500_2008_TRANSITIONAL,
    		ISO_29500_2008_STRICT,
    	};
    }

    public static final int length = 3;
}

