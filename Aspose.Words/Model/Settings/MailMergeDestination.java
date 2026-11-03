// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the possible results which may be generated when a mail merge is carried out on a document.
/// </summary>
/// <seealso cref="MailMergeSettings.Destination"/>
/// <dev>Do not renumber these as they values are used in the DOC codec.</dev>
public /*enum*/ final class MailMergeDestination
{
    private MailMergeDestination(){}
    
    /// <summary>
    /// Specifies that conforming hosting applications shall generate new documents by populating the fields
    /// within a given document with data from the specified external data source.
    /// </summary>
    public static final int NEW_DOCUMENT = 0;
    /// <summary>
    /// Specifies that conforming hosting applications shall print the documents that result from populating the
    /// fields within a given document with external data from the specified external data source.
    /// </summary>
    public static final int PRINTER = 1;
    /// <summary>
    /// Specifies that conforming hosting applications shall generate emails using the documents that result from
    /// populating the fields within a given document with data from the specified external data source.
    /// </summary>
    public static final int EMAIL = 2;
    /// <summary>
    /// Specifies that conforming hosting applications shall generate faxes using the documents that result from
    /// populating the fields within a given document with data from the specified external data source.
    /// </summary>
    public static final int FAX = 4;

    /// <summary>
    /// Equals to the <see cref="NewDocument"/> value.
    /// </summary>
    public static final int DEFAULT = NEW_DOCUMENT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MailMergeDestination value.
    /// </summary>
    public static String getName(/*MailMergeDestination*/int mailMergeDestination)
    {
    	switch (mailMergeDestination)
    	{
    		case NEW_DOCUMENT:
    		/*case DEFAULT:*/
    			return "NEW_DOCUMENT" + " | " + "DEFAULT";
    		case PRINTER: return "PRINTER";
    		case EMAIL: return "EMAIL";
    		case FAX: return "FAX";
    		default: return "Unknown MailMergeDestination value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MailMergeDestination value.
    /// </summary>
    public static String toString(/*MailMergeDestination*/int mailMergeDestination)
    {
    	switch (mailMergeDestination)
    	{
    		case NEW_DOCUMENT:
    		/*case DEFAULT:*/
    			return "NewDocument" + " | " + "Default";
    		case PRINTER: return "Printer";
    		case EMAIL: return "Email";
    		case FAX: return "Fax";
    		default: return "Unknown MailMergeDestination value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MailMergeDestination name.
    /// </summary>
    public static /*MailMergeDestination*/int fromName(String mailMergeDestinationName)
    {
    	if ("NEW_DOCUMENT".equals(mailMergeDestinationName)) return NEW_DOCUMENT;
    	if ("PRINTER".equals(mailMergeDestinationName)) return PRINTER;
    	if ("EMAIL".equals(mailMergeDestinationName)) return EMAIL;
    	if ("FAX".equals(mailMergeDestinationName)) return FAX;
    	if ("DEFAULT".equals(mailMergeDestinationName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MailMergeDestination name.");
    }
    
    /// <summary>
    /// Returns array of MailMergeDestination values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEW_DOCUMENT,
    		PRINTER,
    		EMAIL,
    		FAX,
    		DEFAULT,
    	};
    }

    public static final int length = 5;
}

