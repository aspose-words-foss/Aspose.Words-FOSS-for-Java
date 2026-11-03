// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates available types of a web extension store.
/// </summary>
public /*enum*/ final class WebExtensionStoreType
{
    private WebExtensionStoreType(){}
    
    /// <summary>
    /// Specifies that the store type is SharePoint corporate catalog.
    /// </summary>
    public static final int SP_CATALOG = 0;
    /// <summary>
    /// Specifies that the store type is Office.com.
    /// </summary>
    public static final int OMEX = 1;
    /// <summary>
    /// Specifies that the store type is a SharePoint web application.
    /// </summary>
    public static final int SP_APP = 2;
    /// <summary>
    /// Specifies that the store type is an Exchange server.
    /// </summary>
    public static final int EXCHANGE = 3;
    /// <summary>
    /// Specifies that the store type is a file system share.
    /// </summary>
    public static final int FILE_SYSTEM = 4;
    /// <summary>
    /// Specifies that the store type is the system registry.
    /// </summary>
    public static final int REGISTRY = 5;
    /// <summary>
    /// Specifies that the store type is Centralized Deployment via Exchange.
    /// </summary>
    public static final int EX_CATALOG = 6;
    /// <summary>
    /// Default value.
    /// </summary>
    public static final int DEFAULT = SP_CATALOG; // Default value according to spec (MS-OWEXML).

    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer WebExtensionStoreType value.
    /// </summary>
    public static String getName(/*WebExtensionStoreType*/int webExtensionStoreType)
    {
    	switch (webExtensionStoreType)
    	{
    		case SP_CATALOG:
    		/*case DEFAULT:*/
    			return "SP_CATALOG" + " | " + "DEFAULT";
    		case OMEX: return "OMEX";
    		case SP_APP: return "SP_APP";
    		case EXCHANGE: return "EXCHANGE";
    		case FILE_SYSTEM: return "FILE_SYSTEM";
    		case REGISTRY: return "REGISTRY";
    		case EX_CATALOG: return "EX_CATALOG";
    		default: return "Unknown WebExtensionStoreType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer WebExtensionStoreType value.
    /// </summary>
    public static String toString(/*WebExtensionStoreType*/int webExtensionStoreType)
    {
    	switch (webExtensionStoreType)
    	{
    		case SP_CATALOG:
    		/*case DEFAULT:*/
    			return "SPCatalog" + " | " + "Default";
    		case OMEX: return "OMEX";
    		case SP_APP: return "SPApp";
    		case EXCHANGE: return "Exchange";
    		case FILE_SYSTEM: return "FileSystem";
    		case REGISTRY: return "Registry";
    		case EX_CATALOG: return "ExCatalog";
    		default: return "Unknown WebExtensionStoreType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by WebExtensionStoreType name.
    /// </summary>
    public static /*WebExtensionStoreType*/int fromName(String webExtensionStoreTypeName)
    {
    	if ("SP_CATALOG".equals(webExtensionStoreTypeName)) return SP_CATALOG;
    	if ("OMEX".equals(webExtensionStoreTypeName)) return OMEX;
    	if ("SP_APP".equals(webExtensionStoreTypeName)) return SP_APP;
    	if ("EXCHANGE".equals(webExtensionStoreTypeName)) return EXCHANGE;
    	if ("FILE_SYSTEM".equals(webExtensionStoreTypeName)) return FILE_SYSTEM;
    	if ("REGISTRY".equals(webExtensionStoreTypeName)) return REGISTRY;
    	if ("EX_CATALOG".equals(webExtensionStoreTypeName)) return EX_CATALOG;
    	if ("DEFAULT".equals(webExtensionStoreTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown WebExtensionStoreType name.");
    }
    
    /// <summary>
    /// Returns array of WebExtensionStoreType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SP_CATALOG,
    		OMEX,
    		SP_APP,
    		EXCHANGE,
    		FILE_SYSTEM,
    		REGISTRY,
    		EX_CATALOG,
    		DEFAULT,
    	};
    }

    public static final int length = 8;
}

