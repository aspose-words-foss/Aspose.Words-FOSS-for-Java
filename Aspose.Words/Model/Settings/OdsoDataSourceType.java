// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of the external data source to be connected to as part of the ODSO connection information.
/// </summary>
/// <remarks>
/// <para>The OOXML specification is very vague for this enum. I guess it might correspond to the WdMergeSubType
/// enumeration http://msdn.microsoft.com/en-us/library/bb237801.aspx.</para>
/// 
/// <seealso cref="Odso.DataSourceType"/>
/// </remarks>
/// <dev>Do not renumber these.</dev>
public /*enum*/ final class OdsoDataSourceType
{
    private OdsoDataSourceType(){}
    
    /// <summary>
    /// Specifies that a given document has been connected to a text file.
    /// Possibly wdMergeSubTypeOther.
    /// </summary>
    public static final int TEXT = 0;
    /// <summary>
    /// Specifies that a given document has been connected to a database.
    /// Possibly wdMergeSubTypeAccess.
    /// </summary>
    public static final int DATABASE = 1;
    /// <summary>
    /// Specifies that a given document has been connected to an address book of contacts.
    /// Possibly wdMergeSubTypeOAL.
    /// </summary>
    public static final int ADDRESS_BOOK = 2;
    /// <summary>
    /// Specifies that a given document has been connected to another document format supported by the producing application.
    /// Possibly wdMergeSubTypeOLEDBWord.
    /// </summary>
    public static final int DOCUMENT_1 = 3;
    /// <summary>
    /// Specifies that a given document has been connected to another document format supported by the producing application.
    /// Possibly wdMergeSubTypeWorks.
    /// </summary>
    public static final int DOCUMENT_2 = 4;
    /// <summary>
    /// Specifies that a given document has been connected to another document format native to the producing application.
    /// Possibly wdMergeSubTypeOLEDBText
    /// </summary>
    public static final int NATIVE = 5;
    /// <summary>
    /// Specifies that a given document has been connected to an e-mail application.
    /// Possibly wdMergeSubTypeOutlook.
    /// </summary>
    public static final int EMAIL = 6;
    /// <summary>
    /// The type of the external data source is not specified.
    /// Possibly wdMergeSubTypeWord.
    /// </summary>
    public static final int NONE = 7;
    /// <summary>
    /// Specifies that a given document has been connected to a legacy document format supported by the producing application 
    /// Possibly wdMergeSubTypeWord2000.
    /// </summary>
    public static final int LEGACY = 8;
    /// <summary>
    /// Specifies that a given document has been connected to a data source which aggregates other data sources.
    /// </summary>
    public static final int MASTER = 9;

    /// <summary>
    /// Equals to <see cref="OdsoDataSourceType.None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OdsoDataSourceType value.
    /// </summary>
    public static String getName(/*OdsoDataSourceType*/int odsoDataSourceType)
    {
    	switch (odsoDataSourceType)
    	{
    		case TEXT: return "TEXT";
    		case DATABASE: return "DATABASE";
    		case ADDRESS_BOOK: return "ADDRESS_BOOK";
    		case DOCUMENT_1: return "DOCUMENT_1";
    		case DOCUMENT_2: return "DOCUMENT_2";
    		case NATIVE: return "NATIVE";
    		case EMAIL: return "EMAIL";
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case LEGACY: return "LEGACY";
    		case MASTER: return "MASTER";
    		default: return "Unknown OdsoDataSourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdsoDataSourceType value.
    /// </summary>
    public static String toString(/*OdsoDataSourceType*/int odsoDataSourceType)
    {
    	switch (odsoDataSourceType)
    	{
    		case TEXT: return "Text";
    		case DATABASE: return "Database";
    		case ADDRESS_BOOK: return "AddressBook";
    		case DOCUMENT_1: return "Document1";
    		case DOCUMENT_2: return "Document2";
    		case NATIVE: return "Native";
    		case EMAIL: return "Email";
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case LEGACY: return "Legacy";
    		case MASTER: return "Master";
    		default: return "Unknown OdsoDataSourceType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OdsoDataSourceType name.
    /// </summary>
    public static /*OdsoDataSourceType*/int fromName(String odsoDataSourceTypeName)
    {
    	if ("TEXT".equals(odsoDataSourceTypeName)) return TEXT;
    	if ("DATABASE".equals(odsoDataSourceTypeName)) return DATABASE;
    	if ("ADDRESS_BOOK".equals(odsoDataSourceTypeName)) return ADDRESS_BOOK;
    	if ("DOCUMENT_1".equals(odsoDataSourceTypeName)) return DOCUMENT_1;
    	if ("DOCUMENT_2".equals(odsoDataSourceTypeName)) return DOCUMENT_2;
    	if ("NATIVE".equals(odsoDataSourceTypeName)) return NATIVE;
    	if ("EMAIL".equals(odsoDataSourceTypeName)) return EMAIL;
    	if ("NONE".equals(odsoDataSourceTypeName)) return NONE;
    	if ("LEGACY".equals(odsoDataSourceTypeName)) return LEGACY;
    	if ("MASTER".equals(odsoDataSourceTypeName)) return MASTER;
    	if ("DEFAULT".equals(odsoDataSourceTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown OdsoDataSourceType name.");
    }
    
    /// <summary>
    /// Returns array of OdsoDataSourceType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		DATABASE,
    		ADDRESS_BOOK,
    		DOCUMENT_1,
    		DOCUMENT_2,
    		NATIVE,
    		EMAIL,
    		NONE,
    		LEGACY,
    		MASTER,
    		DEFAULT,
    	};
    }

    public static final int length = 11;
}

