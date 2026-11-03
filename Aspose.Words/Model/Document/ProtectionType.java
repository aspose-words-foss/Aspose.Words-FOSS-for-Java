// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/05/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Protection type for a document.
/// </summary>
/// <dev>Keep in the Aspose.Words namespace (not in Aspose.Words.Settings) because many clients are using
/// this enum and it will be a big and unnecessary breaking change.</dev>
public /*enum*/ final class ProtectionType
{
    private ProtectionType(){}
    
    /// <summary>
    /// User can only modify comments in the document.
    /// </summary>
    public static final int ALLOW_ONLY_COMMENTS = 1;
    /// <summary>
    /// User can only enter data in the form fields in the document.
    /// </summary>
    public static final int ALLOW_ONLY_FORM_FIELDS = 2;
    /// <summary>
    /// User can only add revision marks to the document.
    /// </summary>
    public static final int ALLOW_ONLY_REVISIONS = 0;
    /// <summary>
    /// No changes are allowed to the document. Available since Microsoft Word 2003.
    /// </summary>
    public static final int READ_ONLY = 3;
    /// <summary>
    /// The document is not protected.
    /// </summary>
    public static final int NO_PROTECTION = -1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ProtectionType value.
    /// </summary>
    public static String getName(/*ProtectionType*/int protectionType)
    {
    	switch (protectionType)
    	{
    		case ALLOW_ONLY_COMMENTS: return "ALLOW_ONLY_COMMENTS";
    		case ALLOW_ONLY_FORM_FIELDS: return "ALLOW_ONLY_FORM_FIELDS";
    		case ALLOW_ONLY_REVISIONS: return "ALLOW_ONLY_REVISIONS";
    		case READ_ONLY: return "READ_ONLY";
    		case NO_PROTECTION: return "NO_PROTECTION";
    		default: return "Unknown ProtectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ProtectionType value.
    /// </summary>
    public static String toString(/*ProtectionType*/int protectionType)
    {
    	switch (protectionType)
    	{
    		case ALLOW_ONLY_COMMENTS: return "AllowOnlyComments";
    		case ALLOW_ONLY_FORM_FIELDS: return "AllowOnlyFormFields";
    		case ALLOW_ONLY_REVISIONS: return "AllowOnlyRevisions";
    		case READ_ONLY: return "ReadOnly";
    		case NO_PROTECTION: return "NoProtection";
    		default: return "Unknown ProtectionType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ProtectionType name.
    /// </summary>
    public static /*ProtectionType*/int fromName(String protectionTypeName)
    {
    	if ("ALLOW_ONLY_COMMENTS".equals(protectionTypeName)) return ALLOW_ONLY_COMMENTS;
    	if ("ALLOW_ONLY_FORM_FIELDS".equals(protectionTypeName)) return ALLOW_ONLY_FORM_FIELDS;
    	if ("ALLOW_ONLY_REVISIONS".equals(protectionTypeName)) return ALLOW_ONLY_REVISIONS;
    	if ("READ_ONLY".equals(protectionTypeName)) return READ_ONLY;
    	if ("NO_PROTECTION".equals(protectionTypeName)) return NO_PROTECTION;
    	throw new IllegalArgumentException("Unknown ProtectionType name.");
    }
    
    /// <summary>
    /// Returns array of ProtectionType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ALLOW_ONLY_COMMENTS,
    		ALLOW_ONLY_FORM_FIELDS,
    		ALLOW_ONLY_REVISIONS,
    		READ_ONLY,
    		NO_PROTECTION,
    	};
    }

    public static final int length = 5;
}

