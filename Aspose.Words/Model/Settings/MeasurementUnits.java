// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the unit of measurement.
/// </summary>
public /*enum*/ final class MeasurementUnits
{
    private MeasurementUnits(){}
    
    /// <summary>
    /// Inches.
    /// </summary>
    public static final int INCHES = 0;

    /// <summary>
    /// Centimeters.
    /// </summary>
    public static final int CENTIMETERS = 1;

    /// <summary>
    /// Millimeters.
    /// </summary>
    public static final int MILLIMETERS = 2;

    /// <summary>
    /// Points.
    /// </summary>
    public static final int POINTS = 3;

    /// <summary>
    /// Picas (commonly used in traditional typewriter font spacing).
    /// </summary>
    public static final int PICAS = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MeasurementUnits value.
    /// </summary>
    public static String getName(/*MeasurementUnits*/int measurementUnits)
    {
    	switch (measurementUnits)
    	{
    		case INCHES: return "INCHES";
    		case CENTIMETERS: return "CENTIMETERS";
    		case MILLIMETERS: return "MILLIMETERS";
    		case POINTS: return "POINTS";
    		case PICAS: return "PICAS";
    		default: return "Unknown MeasurementUnits value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MeasurementUnits value.
    /// </summary>
    public static String toString(/*MeasurementUnits*/int measurementUnits)
    {
    	switch (measurementUnits)
    	{
    		case INCHES: return "Inches";
    		case CENTIMETERS: return "Centimeters";
    		case MILLIMETERS: return "Millimeters";
    		case POINTS: return "Points";
    		case PICAS: return "Picas";
    		default: return "Unknown MeasurementUnits value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MeasurementUnits name.
    /// </summary>
    public static /*MeasurementUnits*/int fromName(String measurementUnitsName)
    {
    	if ("INCHES".equals(measurementUnitsName)) return INCHES;
    	if ("CENTIMETERS".equals(measurementUnitsName)) return CENTIMETERS;
    	if ("MILLIMETERS".equals(measurementUnitsName)) return MILLIMETERS;
    	if ("POINTS".equals(measurementUnitsName)) return POINTS;
    	if ("PICAS".equals(measurementUnitsName)) return PICAS;
    	throw new IllegalArgumentException("Unknown MeasurementUnits name.");
    }
    
    /// <summary>
    /// Returns array of MeasurementUnits values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INCHES,
    		CENTIMETERS,
    		MILLIMETERS,
    		POINTS,
    		PICAS,
    	};
    }

    public static final int length = 5;
}

