// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/07/2012 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The type of revision mark. Note that MS Word VBA defines many different revision types that
/// are not really documented and I don't know if they are used or not, for example wdRevisionDisplayField.
///
/// Revision types are much simpler in WordML so this enumeration is more similar to WordML than to MS Word VBA.
/// </summary>
/*enum*/ final class EditRevisionType
{
    private EditRevisionType(){}
    
    public static final int INSERTION = 0;
    public static final int DELETION = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EditRevisionType value.
    /// </summary>
    public static String getName(/*EditRevisionType*/int editRevisionType)
    {
    	switch (editRevisionType)
    	{
    		case INSERTION: return "INSERTION";
    		case DELETION: return "DELETION";
    		default: return "Unknown EditRevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EditRevisionType value.
    /// </summary>
    public static String toString(/*EditRevisionType*/int editRevisionType)
    {
    	switch (editRevisionType)
    	{
    		case INSERTION: return "Insertion";
    		case DELETION: return "Deletion";
    		default: return "Unknown EditRevisionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EditRevisionType name.
    /// </summary>
    public static /*EditRevisionType*/int fromName(String editRevisionTypeName)
    {
    	if ("INSERTION".equals(editRevisionTypeName)) return INSERTION;
    	if ("DELETION".equals(editRevisionTypeName)) return DELETION;
    	throw new IllegalArgumentException("Unknown EditRevisionType name.");
    }
    
    /// <summary>
    /// Returns array of EditRevisionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INSERTION,
    		DELETION,
    	};
    }

    public static final int length = 2;
}

