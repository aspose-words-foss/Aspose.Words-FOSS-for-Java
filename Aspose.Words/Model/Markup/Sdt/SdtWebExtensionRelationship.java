// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/10/2016 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a relationship between a structured document tag and an Office Web Extension (webExtension).
/// </summary>
/// <dev>
/// Represents value of the 2.5.1.12 webExtensionCreated and 2.5.1.13 webExtensionLinked [MS-DOCX] elements.
/// </dev>
/*enum*/ final class SdtWebExtensionRelationship
{
    private SdtWebExtensionRelationship(){}
    
    /// <summary>
    /// No relationship. This is a default value.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// The structured document tag was created by, and is bound to, at least one webExtension.
    /// </summary>
    public static final int CREATED_BY_WEB_EXTENSION = 1;

    /// <summary>
    /// The structured document tag is bound to at least one webExtension.
    /// </summary>
    public static final int LINKED_TO_WEB_EXTENSION = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtWebExtensionRelationship value.
    /// </summary>
    public static String getName(/*SdtWebExtensionRelationship*/int sdtWebExtensionRelationship)
    {
    	switch (sdtWebExtensionRelationship)
    	{
    		case NONE: return "NONE";
    		case CREATED_BY_WEB_EXTENSION: return "CREATED_BY_WEB_EXTENSION";
    		case LINKED_TO_WEB_EXTENSION: return "LINKED_TO_WEB_EXTENSION";
    		default: return "Unknown SdtWebExtensionRelationship value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtWebExtensionRelationship value.
    /// </summary>
    public static String toString(/*SdtWebExtensionRelationship*/int sdtWebExtensionRelationship)
    {
    	switch (sdtWebExtensionRelationship)
    	{
    		case NONE: return "None";
    		case CREATED_BY_WEB_EXTENSION: return "CreatedByWebExtension";
    		case LINKED_TO_WEB_EXTENSION: return "LinkedToWebExtension";
    		default: return "Unknown SdtWebExtensionRelationship value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtWebExtensionRelationship name.
    /// </summary>
    public static /*SdtWebExtensionRelationship*/int fromName(String sdtWebExtensionRelationshipName)
    {
    	if ("NONE".equals(sdtWebExtensionRelationshipName)) return NONE;
    	if ("CREATED_BY_WEB_EXTENSION".equals(sdtWebExtensionRelationshipName)) return CREATED_BY_WEB_EXTENSION;
    	if ("LINKED_TO_WEB_EXTENSION".equals(sdtWebExtensionRelationshipName)) return LINKED_TO_WEB_EXTENSION;
    	throw new IllegalArgumentException("Unknown SdtWebExtensionRelationship name.");
    }
    
    /// <summary>
    /// Returns array of SdtWebExtensionRelationship values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		CREATED_BY_WEB_EXTENSION,
    		LINKED_TO_WEB_EXTENSION,
    	};
    }

    public static final int length = 3;
}

