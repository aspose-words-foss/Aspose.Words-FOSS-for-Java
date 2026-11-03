// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Allows to specify the measurement units for model values in AW.
/// </summary>
/*enum*/ final class NrxUnit
{
    private NrxUnit(){}
    
    /// <summary>
    /// Unit not defined or not applicable
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Pica, a typographic unit of measure corresponding to 12 pt
    /// </summary>
    public static final int PICA = 1;

    /// <summary>
    /// Typographic Point 1/72th of an inch
    /// </summary>
    public static final int POINT = 2;
    
    /// <summary>
    /// Imperial inch, equal 2.54 cm
    /// </summary>
    public static final int INCH = 3;
    
    /// <summary>
    /// metric millimeter, 1/10 of cm
    /// </summary>
    public static final int MILLIMETER = 4;
    
    /// <summary>
    /// Metric centimeter
    /// </summary>
    public static final int CENTIMETER = 5;
    
    /// <summary>
    /// 1/2 of a point
    /// </summary>
    public static final int HALF_POINTS = 6;
    
    /// <summary>
    /// 1/20 of a point
    /// </summary>
    public static final int TWIPS = 7;

    /// <summary>
    /// 1/100 of a point
    /// </summary>
    public static final int HUNDREDTHS_OF_POINT = 8;

    /// <summary>
    /// English Metric Units, 1/12700 of a point
    /// </summary>
    public static final int EMUS = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NrxUnit value.
    /// </summary>
    public static String getName(/*NrxUnit*/int nrxUnit)
    {
    	switch (nrxUnit)
    	{
    		case NONE: return "NONE";
    		case PICA: return "PICA";
    		case POINT: return "POINT";
    		case INCH: return "INCH";
    		case MILLIMETER: return "MILLIMETER";
    		case CENTIMETER: return "CENTIMETER";
    		case HALF_POINTS: return "HALF_POINTS";
    		case TWIPS: return "TWIPS";
    		case HUNDREDTHS_OF_POINT: return "HUNDREDTHS_OF_POINT";
    		case EMUS: return "EMUS";
    		default: return "Unknown NrxUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NrxUnit value.
    /// </summary>
    public static String toString(/*NrxUnit*/int nrxUnit)
    {
    	switch (nrxUnit)
    	{
    		case NONE: return "None";
    		case PICA: return "Pica";
    		case POINT: return "Point";
    		case INCH: return "Inch";
    		case MILLIMETER: return "Millimeter";
    		case CENTIMETER: return "Centimeter";
    		case HALF_POINTS: return "HalfPoints";
    		case TWIPS: return "Twips";
    		case HUNDREDTHS_OF_POINT: return "HundredthsOfPoint";
    		case EMUS: return "Emus";
    		default: return "Unknown NrxUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NrxUnit name.
    /// </summary>
    public static /*NrxUnit*/int fromName(String nrxUnitName)
    {
    	if ("NONE".equals(nrxUnitName)) return NONE;
    	if ("PICA".equals(nrxUnitName)) return PICA;
    	if ("POINT".equals(nrxUnitName)) return POINT;
    	if ("INCH".equals(nrxUnitName)) return INCH;
    	if ("MILLIMETER".equals(nrxUnitName)) return MILLIMETER;
    	if ("CENTIMETER".equals(nrxUnitName)) return CENTIMETER;
    	if ("HALF_POINTS".equals(nrxUnitName)) return HALF_POINTS;
    	if ("TWIPS".equals(nrxUnitName)) return TWIPS;
    	if ("HUNDREDTHS_OF_POINT".equals(nrxUnitName)) return HUNDREDTHS_OF_POINT;
    	if ("EMUS".equals(nrxUnitName)) return EMUS;
    	throw new IllegalArgumentException("Unknown NrxUnit name.");
    }
    
    /// <summary>
    /// Returns array of NrxUnit values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PICA,
    		POINT,
    		INCH,
    		MILLIMETER,
    		CENTIMETER,
    		HALF_POINTS,
    		TWIPS,
    		HUNDREDTHS_OF_POINT,
    		EMUS,
    	};
    }

    public static final int length = 10;
}


