// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the type of a connector. A connector is a shape used to join other shapes.
/// </summary>
/// <seealso cref="Shape.ConnectorType"/>
/*enum*/ final class ConnectorType
{
    private ConnectorType(){}
    
    /// <summary>
    /// </summary>
    public static final int STRAIGHT = 0;
    /// <summary>
    /// </summary>
    public static final int BENT = 1;
    /// <summary>
    /// </summary>
    public static final int CURVED = 2;
    /// <summary>
    /// The shape is not a connector.
    /// </summary>
    public static final int NONE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ConnectorType value.
    /// </summary>
    public static String getName(/*ConnectorType*/int connectorType)
    {
    	switch (connectorType)
    	{
    		case STRAIGHT: return "STRAIGHT";
    		case BENT: return "BENT";
    		case CURVED: return "CURVED";
    		case NONE: return "NONE";
    		default: return "Unknown ConnectorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ConnectorType value.
    /// </summary>
    public static String toString(/*ConnectorType*/int connectorType)
    {
    	switch (connectorType)
    	{
    		case STRAIGHT: return "Straight";
    		case BENT: return "Bent";
    		case CURVED: return "Curved";
    		case NONE: return "None";
    		default: return "Unknown ConnectorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ConnectorType name.
    /// </summary>
    public static /*ConnectorType*/int fromName(String connectorTypeName)
    {
    	if ("STRAIGHT".equals(connectorTypeName)) return STRAIGHT;
    	if ("BENT".equals(connectorTypeName)) return BENT;
    	if ("CURVED".equals(connectorTypeName)) return CURVED;
    	if ("NONE".equals(connectorTypeName)) return NONE;
    	throw new IllegalArgumentException("Unknown ConnectorType name.");
    }
    
    /// <summary>
    /// Returns array of ConnectorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		STRAIGHT,
    		BENT,
    		CURVED,
    		NONE,
    	};
    }

    public static final int length = 4;
}

