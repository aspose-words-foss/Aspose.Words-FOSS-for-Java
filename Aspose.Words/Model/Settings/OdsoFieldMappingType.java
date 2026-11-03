// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible types used to indicate if a given mail merge field has been mapped to a column in the given external data source.
/// </summary>
/// <seealso cref="OdsoFieldMapData.Type"/>
public /*enum*/ final class OdsoFieldMappingType
{
    private OdsoFieldMappingType(){}
    
    /// <summary>
    /// Specifies that the mail merge field has been mapped to a column in the given external data source.
    /// </summary>
    public static final int COLUMN = 0;
    /// <summary>
    /// Specifies that the mail merge field has not been mapped to a column in the given external data source.
    /// </summary>
    public static final int NULL = 1;

    /// <summary>
    /// Equals to <see cref="OdsoFieldMappingType.Null"/>.
    /// </summary>
    public static final int DEFAULT = NULL;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OdsoFieldMappingType value.
    /// </summary>
    public static String getName(/*OdsoFieldMappingType*/int odsoFieldMappingType)
    {
    	switch (odsoFieldMappingType)
    	{
    		case COLUMN: return "COLUMN";
    		case NULL:
    		/*case DEFAULT:*/
    			return "NULL" + " | " + "DEFAULT";
    		default: return "Unknown OdsoFieldMappingType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdsoFieldMappingType value.
    /// </summary>
    public static String toString(/*OdsoFieldMappingType*/int odsoFieldMappingType)
    {
    	switch (odsoFieldMappingType)
    	{
    		case COLUMN: return "Column";
    		case NULL:
    		/*case DEFAULT:*/
    			return "Null" + " | " + "Default";
    		default: return "Unknown OdsoFieldMappingType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OdsoFieldMappingType name.
    /// </summary>
    public static /*OdsoFieldMappingType*/int fromName(String odsoFieldMappingTypeName)
    {
    	if ("COLUMN".equals(odsoFieldMappingTypeName)) return COLUMN;
    	if ("NULL".equals(odsoFieldMappingTypeName)) return NULL;
    	if ("DEFAULT".equals(odsoFieldMappingTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown OdsoFieldMappingType name.");
    }
    
    /// <summary>
    /// Returns array of OdsoFieldMappingType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COLUMN,
    		NULL,
    		DEFAULT,
    	};
    }

    public static final int length = 3;
}

