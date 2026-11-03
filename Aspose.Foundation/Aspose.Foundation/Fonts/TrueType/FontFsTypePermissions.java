// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2024 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*enum*/ final class FontFsTypePermissions
{
    private FontFsTypePermissions(){}
    
    public static final int INSTALLABLE = 0;
    public static final int RESTRICTED_LICENSE = 1;
    public static final int PRINT_AND_PREVIEW = 2;
    public static final int EDITABLE = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontFsTypePermissions value.
    /// </summary>
    public static String getName(/*FontFsTypePermissions*/int fontFsTypePermissions)
    {
    	switch (fontFsTypePermissions)
    	{
    		case INSTALLABLE: return "INSTALLABLE";
    		case RESTRICTED_LICENSE: return "RESTRICTED_LICENSE";
    		case PRINT_AND_PREVIEW: return "PRINT_AND_PREVIEW";
    		case EDITABLE: return "EDITABLE";
    		default: return "Unknown FontFsTypePermissions value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontFsTypePermissions value.
    /// </summary>
    public static String toString(/*FontFsTypePermissions*/int fontFsTypePermissions)
    {
    	switch (fontFsTypePermissions)
    	{
    		case INSTALLABLE: return "Installable";
    		case RESTRICTED_LICENSE: return "RestrictedLicense";
    		case PRINT_AND_PREVIEW: return "PrintAndPreview";
    		case EDITABLE: return "Editable";
    		default: return "Unknown FontFsTypePermissions value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FontFsTypePermissions name.
    /// </summary>
    public static /*FontFsTypePermissions*/int fromName(String fontFsTypePermissionsName)
    {
    	if ("INSTALLABLE".equals(fontFsTypePermissionsName)) return INSTALLABLE;
    	if ("RESTRICTED_LICENSE".equals(fontFsTypePermissionsName)) return RESTRICTED_LICENSE;
    	if ("PRINT_AND_PREVIEW".equals(fontFsTypePermissionsName)) return PRINT_AND_PREVIEW;
    	if ("EDITABLE".equals(fontFsTypePermissionsName)) return EDITABLE;
    	throw new IllegalArgumentException("Unknown FontFsTypePermissions name.");
    }
    
    /// <summary>
    /// Returns array of FontFsTypePermissions values.
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

