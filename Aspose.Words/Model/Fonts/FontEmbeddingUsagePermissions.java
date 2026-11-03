// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2024 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents the font embedding usage permissions.
/// </summary>
public /*enum*/ final class FontEmbeddingUsagePermissions
{
    private FontEmbeddingUsagePermissions(){}
    
    /// <summary>
    /// The font may be embedded, and may be permanently installed for use on a remote systems, or for use by
    /// other users.
    /// </summary>
    public static final int INSTALLABLE = 0;
    /// <summary>
    /// The font must not be modified, embedded or exchanged in any manner without first obtaining explicit
    /// permission of the legal owner.
    /// </summary>
    public static final int RESTRICTED_LICENSE = 1;
    /// <summary>
    /// The font may be embedded, and may be temporarily loaded on other systems for purposes of viewing or
    /// printing the document.
    /// </summary>
    /// <remarks>
    /// Documents containing Preview &amp; Print fonts must be opened “read-only”; no edits may
    /// be applied to the document.
    /// </remarks>
    public static final int PRINT_AND_PREVIEW = 2;
    /// <summary>
    /// The font may be embedded, and may be temporarily loaded on other systems.
    /// </summary>
    /// <remarks>
    /// As with Preview &amp; Print embedding, documents containing Editable fonts may be opened for reading.
    /// In addition, editing is permitted, including ability to format new text using the embedded font, and
    /// changes may be saved.
    /// </remarks>
    public static final int EDITABLE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontEmbeddingUsagePermissions value.
    /// </summary>
    public static String getName(/*FontEmbeddingUsagePermissions*/int fontEmbeddingUsagePermissions)
    {
    	switch (fontEmbeddingUsagePermissions)
    	{
    		case INSTALLABLE: return "INSTALLABLE";
    		case RESTRICTED_LICENSE: return "RESTRICTED_LICENSE";
    		case PRINT_AND_PREVIEW: return "PRINT_AND_PREVIEW";
    		case EDITABLE: return "EDITABLE";
    		default: return "Unknown FontEmbeddingUsagePermissions value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontEmbeddingUsagePermissions value.
    /// </summary>
    public static String toString(/*FontEmbeddingUsagePermissions*/int fontEmbeddingUsagePermissions)
    {
    	switch (fontEmbeddingUsagePermissions)
    	{
    		case INSTALLABLE: return "Installable";
    		case RESTRICTED_LICENSE: return "RestrictedLicense";
    		case PRINT_AND_PREVIEW: return "PrintAndPreview";
    		case EDITABLE: return "Editable";
    		default: return "Unknown FontEmbeddingUsagePermissions value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FontEmbeddingUsagePermissions name.
    /// </summary>
    public static /*FontEmbeddingUsagePermissions*/int fromName(String fontEmbeddingUsagePermissionsName)
    {
    	if ("INSTALLABLE".equals(fontEmbeddingUsagePermissionsName)) return INSTALLABLE;
    	if ("RESTRICTED_LICENSE".equals(fontEmbeddingUsagePermissionsName)) return RESTRICTED_LICENSE;
    	if ("PRINT_AND_PREVIEW".equals(fontEmbeddingUsagePermissionsName)) return PRINT_AND_PREVIEW;
    	if ("EDITABLE".equals(fontEmbeddingUsagePermissionsName)) return EDITABLE;
    	throw new IllegalArgumentException("Unknown FontEmbeddingUsagePermissions name.");
    }
    
    /// <summary>
    /// Returns array of FontEmbeddingUsagePermissions values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INSTALLABLE,
    		RESTRICTED_LICENSE,
    		PRINT_AND_PREVIEW,
    		EDITABLE,
    	};
    }

    public static final int length = 4;
}

