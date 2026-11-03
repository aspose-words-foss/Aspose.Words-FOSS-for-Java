// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of an external mail merge data source.
/// </summary>
/// <seealso cref="MailMergeSettings.DataType"/>
/// <dev>Do not renumber these values as they are used in the DOC codec.</dev>
public /*enum*/ final class MailMergeDataType
{
    private MailMergeDataType(){}
    
    /// <summary>
    /// No mail merge data source is specified.
    /// </summary>
    public static final int NONE = -1;
    /// <summary>
    /// Specifies that a given document has been connected to a text file via the Dynamic Data Exchange (DDE) system.
    /// </summary>
    public static final int TEXT_FILE = 0;
    /// <summary>
    /// Specifies that a given document has been connected to an Access database via the Dynamic Data Exchange (DDE) system.
    /// </summary>
    public static final int DATABASE = 1;
    /// <summary>
    /// Specifies that a given document has been connected to an Excel spreadsheet via the Dynamic Data Exchange (DDE) system.
    /// </summary>
    public static final int SPREADSHEET = 2;
    /// <summary>
    /// Specifies that a given document has been connected to an external data source using an external query tool.
    /// </summary>
    public static final int QUERY = 3;
    /// <summary>
    /// Specifies that a given document has been connected to an external data source via the Open Database Connectivity interface.
    /// </summary>
    public static final int ODBC = 4;
    /// <summary>
    /// Specifies that a given document has been connected to an external data source via the Office Data Source Object (ODSO) interface.
    /// </summary>
    public static final int NATIVE = 5;

    /// <summary>
    /// Equals to <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MailMergeDataType value.
    /// </summary>
    public static String getName(/*MailMergeDataType*/int mailMergeDataType)
    {
    	switch (mailMergeDataType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case TEXT_FILE: return "TEXT_FILE";
    		case DATABASE: return "DATABASE";
    		case SPREADSHEET: return "SPREADSHEET";
    		case QUERY: return "QUERY";
    		case ODBC: return "ODBC";
    		case NATIVE: return "NATIVE";
    		default: return "Unknown MailMergeDataType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MailMergeDataType value.
    /// </summary>
    public static String toString(/*MailMergeDataType*/int mailMergeDataType)
    {
    	switch (mailMergeDataType)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case TEXT_FILE: return "TextFile";
    		case DATABASE: return "Database";
    		case SPREADSHEET: return "Spreadsheet";
    		case QUERY: return "Query";
    		case ODBC: return "Odbc";
    		case NATIVE: return "Native";
    		default: return "Unknown MailMergeDataType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MailMergeDataType name.
    /// </summary>
    public static /*MailMergeDataType*/int fromName(String mailMergeDataTypeName)
    {
    	if ("NONE".equals(mailMergeDataTypeName)) return NONE;
    	if ("TEXT_FILE".equals(mailMergeDataTypeName)) return TEXT_FILE;
    	if ("DATABASE".equals(mailMergeDataTypeName)) return DATABASE;
    	if ("SPREADSHEET".equals(mailMergeDataTypeName)) return SPREADSHEET;
    	if ("QUERY".equals(mailMergeDataTypeName)) return QUERY;
    	if ("ODBC".equals(mailMergeDataTypeName)) return ODBC;
    	if ("NATIVE".equals(mailMergeDataTypeName)) return NATIVE;
    	if ("DEFAULT".equals(mailMergeDataTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MailMergeDataType name.");
    }
    
    /// <summary>
    /// Returns array of MailMergeDataType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TEXT_FILE,
    		DATABASE,
    		SPREADSHEET,
    		QUERY,
    		ODBC,
    		NATIVE,
    		DEFAULT,
    	};
    }

    public static final int length = 8;
}

