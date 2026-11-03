// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of an OLE object.
/// Ideally I would want this to replace the two OLE-related shape types.
/// </summary>
/*enum*/ final class OleObjectType
{
    private OleObjectType(){}
    
    /// <summary>
    /// The object is not an OLE object.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The object is an embedded OLE object.
    /// </summary>
    public static final int EMBEDDED = 1;
    /// <summary>
    /// The object is a linked OLE object.
    /// </summary>
    public static final int LINKED = 2;
    /// <summary>
    /// The object is an ActiveX control.
    /// </summary>
    public static final int CONTROL = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OleObjectType value.
    /// </summary>
    public static String getName(/*OleObjectType*/int oleObjectType)
    {
    	switch (oleObjectType)
    	{
    		case NONE: return "NONE";
    		case EMBEDDED: return "EMBEDDED";
    		case LINKED: return "LINKED";
    		case CONTROL: return "CONTROL";
    		default: return "Unknown OleObjectType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OleObjectType value.
    /// </summary>
    public static String toString(/*OleObjectType*/int oleObjectType)
    {
    	switch (oleObjectType)
    	{
    		case NONE: return "None";
    		case EMBEDDED: return "Embedded";
    		case LINKED: return "Linked";
    		case CONTROL: return "Control";
    		default: return "Unknown OleObjectType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OleObjectType name.
    /// </summary>
    public static /*OleObjectType*/int fromName(String oleObjectTypeName)
    {
    	if ("NONE".equals(oleObjectTypeName)) return NONE;
    	if ("EMBEDDED".equals(oleObjectTypeName)) return EMBEDDED;
    	if ("LINKED".equals(oleObjectTypeName)) return LINKED;
    	if ("CONTROL".equals(oleObjectTypeName)) return CONTROL;
    	throw new IllegalArgumentException("Unknown OleObjectType name.");
    }
    
    /// <summary>
    /// Returns array of OleObjectType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		EMBEDDED,
    		LINKED,
    		CONTROL,
    	};
    }

    public static final int length = 4;
}

