// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/12/2019 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.19 ST_ConnectorRouting (Connector Routing)
/// </summary>
/*enum*/ final class DmlConnectorRouting
{
    private DmlConnectorRouting(){}
    
    public static final int BENDING = 0;
    public static final int CURVE = 1;
    public static final int LONG_CURVE = 2;
    public static final int STRAIGHT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConnectorRouting value.
    /// </summary>
    public static String getName(/*DmlConnectorRouting*/int dmlConnectorRouting)
    {
    	switch (dmlConnectorRouting)
    	{
    		case BENDING: return "BENDING";
    		case CURVE: return "CURVE";
    		case LONG_CURVE: return "LONG_CURVE";
    		case STRAIGHT: return "STRAIGHT";
    		default: return "Unknown DmlConnectorRouting value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConnectorRouting value.
    /// </summary>
    public static String toString(/*DmlConnectorRouting*/int dmlConnectorRouting)
    {
    	switch (dmlConnectorRouting)
    	{
    		case BENDING: return "Bending";
    		case CURVE: return "Curve";
    		case LONG_CURVE: return "LongCurve";
    		case STRAIGHT: return "Straight";
    		default: return "Unknown DmlConnectorRouting value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConnectorRouting name.
    /// </summary>
    public static /*DmlConnectorRouting*/int fromName(String dmlConnectorRoutingName)
    {
    	if ("BENDING".equals(dmlConnectorRoutingName)) return BENDING;
    	if ("CURVE".equals(dmlConnectorRoutingName)) return CURVE;
    	if ("LONG_CURVE".equals(dmlConnectorRoutingName)) return LONG_CURVE;
    	if ("STRAIGHT".equals(dmlConnectorRoutingName)) return STRAIGHT;
    	throw new IllegalArgumentException("Unknown DmlConnectorRouting name.");
    }
    
    /// <summary>
    /// Returns array of DmlConnectorRouting values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BENDING,
    		CURVE,
    		LONG_CURVE,
    		STRAIGHT,
    	};
    }

    public static final int length = 4;
}

