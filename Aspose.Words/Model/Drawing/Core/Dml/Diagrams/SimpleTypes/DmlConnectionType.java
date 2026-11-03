// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.23 ST_CxnType (Connection Type)
/// </summary>
/*enum*/ final class DmlConnectionType
{
    private DmlConnectionType(){}
    
    public static final int PARENT_OF = 0;
    public static final int PRESENTATION_OF = 1;
    public static final int PRESENTATION_PARENT_OF = 2;
    public static final int UNKNOWN_RELATIONSHIP = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConnectionType value.
    /// </summary>
    public static String getName(/*DmlConnectionType*/int dmlConnectionType)
    {
    	switch (dmlConnectionType)
    	{
    		case PARENT_OF: return "PARENT_OF";
    		case PRESENTATION_OF: return "PRESENTATION_OF";
    		case PRESENTATION_PARENT_OF: return "PRESENTATION_PARENT_OF";
    		case UNKNOWN_RELATIONSHIP: return "UNKNOWN_RELATIONSHIP";
    		default: return "Unknown DmlConnectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConnectionType value.
    /// </summary>
    public static String toString(/*DmlConnectionType*/int dmlConnectionType)
    {
    	switch (dmlConnectionType)
    	{
    		case PARENT_OF: return "ParentOf";
    		case PRESENTATION_OF: return "PresentationOf";
    		case PRESENTATION_PARENT_OF: return "PresentationParentOf";
    		case UNKNOWN_RELATIONSHIP: return "UnknownRelationship";
    		default: return "Unknown DmlConnectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConnectionType name.
    /// </summary>
    public static /*DmlConnectionType*/int fromName(String dmlConnectionTypeName)
    {
    	if ("PARENT_OF".equals(dmlConnectionTypeName)) return PARENT_OF;
    	if ("PRESENTATION_OF".equals(dmlConnectionTypeName)) return PRESENTATION_OF;
    	if ("PRESENTATION_PARENT_OF".equals(dmlConnectionTypeName)) return PRESENTATION_PARENT_OF;
    	if ("UNKNOWN_RELATIONSHIP".equals(dmlConnectionTypeName)) return UNKNOWN_RELATIONSHIP;
    	throw new IllegalArgumentException("Unknown DmlConnectionType name.");
    }
    
    /// <summary>
    /// Returns array of DmlConnectionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PARENT_OF,
    		PRESENTATION_OF,
    		PRESENTATION_PARENT_OF,
    		UNKNOWN_RELATIONSHIP,
    	};
    }

    public static final int length = 4;
}

