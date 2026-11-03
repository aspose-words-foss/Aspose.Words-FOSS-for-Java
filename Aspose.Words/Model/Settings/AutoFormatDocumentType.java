// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the document type used by the AutoFormat feature.
/// </summary>
/*enum*/ final class AutoFormatDocumentType
{
    private AutoFormatDocumentType(){}
    
    public static final int NORMAL = 0;
    public static final int LETTER = 1;
    public static final int EMAIL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AutoFormatDocumentType value.
    /// </summary>
    public static String getName(/*AutoFormatDocumentType*/int autoFormatDocumentType)
    {
    	switch (autoFormatDocumentType)
    	{
    		case NORMAL: return "NORMAL";
    		case LETTER: return "LETTER";
    		case EMAIL: return "EMAIL";
    		default: return "Unknown AutoFormatDocumentType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AutoFormatDocumentType value.
    /// </summary>
    public static String toString(/*AutoFormatDocumentType*/int autoFormatDocumentType)
    {
    	switch (autoFormatDocumentType)
    	{
    		case NORMAL: return "Normal";
    		case LETTER: return "Letter";
    		case EMAIL: return "Email";
    		default: return "Unknown AutoFormatDocumentType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AutoFormatDocumentType name.
    /// </summary>
    public static /*AutoFormatDocumentType*/int fromName(String autoFormatDocumentTypeName)
    {
    	if ("NORMAL".equals(autoFormatDocumentTypeName)) return NORMAL;
    	if ("LETTER".equals(autoFormatDocumentTypeName)) return LETTER;
    	if ("EMAIL".equals(autoFormatDocumentTypeName)) return EMAIL;
    	throw new IllegalArgumentException("Unknown AutoFormatDocumentType name.");
    }
    
    /// <summary>
    /// Returns array of AutoFormatDocumentType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NORMAL,
    		LETTER,
    		EMAIL,
    	};
    }

    public static final int length = 3;
}

