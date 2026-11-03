// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2019 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.17 ST_ConnectorDimension (Connector Dimension)
/// </summary>
/*enum*/ final class DmlConnectorDimensions
{
    private DmlConnectorDimensions(){}
    
    public static final int ONE_DIMENSIONAL = 0;
    public static final int TWO_DIMENSIONAL = 1;
    public static final int CUSTOM = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConnectorDimensions value.
    /// </summary>
    public static String getName(/*DmlConnectorDimensions*/int dmlConnectorDimensions)
    {
    	switch (dmlConnectorDimensions)
    	{
    		case ONE_DIMENSIONAL: return "ONE_DIMENSIONAL";
    		case TWO_DIMENSIONAL: return "TWO_DIMENSIONAL";
    		case CUSTOM: return "CUSTOM";
    		default: return "Unknown DmlConnectorDimensions value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConnectorDimensions value.
    /// </summary>
    public static String toString(/*DmlConnectorDimensions*/int dmlConnectorDimensions)
    {
    	switch (dmlConnectorDimensions)
    	{
    		case ONE_DIMENSIONAL: return "OneDimensional";
    		case TWO_DIMENSIONAL: return "TwoDimensional";
    		case CUSTOM: return "Custom";
    		default: return "Unknown DmlConnectorDimensions value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConnectorDimensions name.
    /// </summary>
    public static /*DmlConnectorDimensions*/int fromName(String dmlConnectorDimensionsName)
    {
    	if ("ONE_DIMENSIONAL".equals(dmlConnectorDimensionsName)) return ONE_DIMENSIONAL;
    	if ("TWO_DIMENSIONAL".equals(dmlConnectorDimensionsName)) return TWO_DIMENSIONAL;
    	if ("CUSTOM".equals(dmlConnectorDimensionsName)) return CUSTOM;
    	throw new IllegalArgumentException("Unknown DmlConnectorDimensions name.");
    }
    
    /// <summary>
    /// Returns array of DmlConnectorDimensions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ONE_DIMENSIONAL,
    		TWO_DIMENSIONAL,
    		CUSTOM,
    	};
    }

    public static final int length = 3;
}

