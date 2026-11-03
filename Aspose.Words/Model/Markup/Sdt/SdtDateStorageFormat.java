// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/11/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how the date for a date SDT is stored/retrieved when the SDT is bound to an XML node in the document's data store.
/// </summary>
public /*enum*/ final class SdtDateStorageFormat
{
    private SdtDateStorageFormat(){}
    
    /// <summary>
    /// The date value for a date SDT is stored as a date in the standard XML Schema Date format.
    /// </summary>
    public static final int DATE = 0;

    /// <summary>
    /// The date value for a date SDT is stored as a date in the standard XML Schema DateTime format.
    /// </summary>
    public static final int DATE_TIME = 1; 
    
    /// <summary>
    ///  The date value for a date SDT is stored as text. 
    /// </summary>
    public static final int TEXT = 2;

    /// <summary>
    /// Defaults to <see cref="DateTime"/>
    /// </summary>
    public static final int DEFAULT = DATE_TIME;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtDateStorageFormat value.
    /// </summary>
    public static String getName(/*SdtDateStorageFormat*/int sdtDateStorageFormat)
    {
    	switch (sdtDateStorageFormat)
    	{
    		case DATE: return "DATE";
    		case DATE_TIME:
    		/*case DEFAULT:*/
    			return "DATE_TIME" + " | " + "DEFAULT";
    		case TEXT: return "TEXT";
    		default: return "Unknown SdtDateStorageFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtDateStorageFormat value.
    /// </summary>
    public static String toString(/*SdtDateStorageFormat*/int sdtDateStorageFormat)
    {
    	switch (sdtDateStorageFormat)
    	{
    		case DATE: return "Date";
    		case DATE_TIME:
    		/*case DEFAULT:*/
    			return "DateTime" + " | " + "Default";
    		case TEXT: return "Text";
    		default: return "Unknown SdtDateStorageFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtDateStorageFormat name.
    /// </summary>
    public static /*SdtDateStorageFormat*/int fromName(String sdtDateStorageFormatName)
    {
    	if ("DATE".equals(sdtDateStorageFormatName)) return DATE;
    	if ("DATE_TIME".equals(sdtDateStorageFormatName)) return DATE_TIME;
    	if ("TEXT".equals(sdtDateStorageFormatName)) return TEXT;
    	if ("DEFAULT".equals(sdtDateStorageFormatName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown SdtDateStorageFormat name.");
    }
    
    /// <summary>
    /// Returns array of SdtDateStorageFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DATE,
    		DATE_TIME,
    		TEXT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

