// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// List of properties that occur in the DocSummaryInformation property set.
/// </summary>
public /*static*/ class DocSummaryInfoProperty
{
	/* Simulation of static class by using private constructor */
	private DocSummaryInfoProperty()
	{}

    public static final int CODE_PAGE = 0x01;
    /// <summary>
    /// String.
    /// </summary>
    public static final int CATEGORY = 0x02;
    /// <summary>
    /// String.
    /// PowerPoint property.
    /// </summary>
    public static final int PRESENTATION_TARGET = 0x03;
    /// <summary>
    /// Int.
    /// </summary>
    public static final int BYTES = 0x04;
    /// <summary>
    /// Int.
    /// </summary>
    public static final int LINES = 0x05;
    /// <summary>
    /// Int.
    /// </summary>
    public static final int PARAGRAPHS = 0x06;
    /// <summary>
    /// Int.
    /// PowerPoint property.
    /// </summary>
    public static final int SLIDES = 0x07;
    /// <summary>
    /// Int.
    /// PowerPoint property.
    /// </summary>
    public static final int NOTES = 0x08;
    /// <summary>
    /// Int.
    /// PowerPoint property.
    /// </summary>
    public static final int HIDDEN_SLIDES = 0x09;
    /// <summary>
    /// Int.
    /// PowerPoint property.
    /// </summary>
    public static final int MULTIMEDIA_CLIPS = 0x0A;
    /// <summary>
    /// Bool.
    /// MUST be a VT_BOOL TypedPropertyValue ([MS-OLEPS] section 2.15) property. 
    /// The value of the property MUST be FALSE.
    /// </summary>
    public static final int SCALE_CROP = 0x0B;
    /// <summary>
    /// HeadingPair[]
    /// MUST be a VtVecHeadingPair property. Each VtHeadingPair element in 
    /// VtVecHeadingPair.vtValue.rgHeadingPairs defines a heading string and a count of 
    /// document parts as found in the GKPIDDSI_DOCPARTS property to which this heading applies. 
    /// The total sum of document counts for all headers in this property MUST be equal to the 
    /// number of elements in the GKPIDDSI_DOCPARTS property.
    /// </summary>
    public static final int HEADING_PAIRS = 0x0C;
    /// <summary>
    /// string[]
    /// MUST be a VtVecUnalignedLpstr or VtVecLpwstr property. Each string element of the vector 
    /// specifies a part of the document. The elements of this vector are ordered according to 
    /// the header they belong to as defined in the GKPIDDSI_HEADINGPAIR property.
    /// 
    /// Example: The first element of the heading pair vector indicates it has four document 
    /// parts associated with it. Elements 1-4 of the document parts vector are grouped under this header. 
    /// The next element of the heading pair vector indicates it has three document parts associated with it.
    /// The document part vector elements 5-7 are grouped under this header, and so forth.
    /// </summary>
    public static final int TITLES_OF_PARTS = 0x0D;
    /// <summary>
    /// String.
    /// </summary>
    public static final int MANAGER = 0x0E;
    /// <summary>
    /// String.
    /// </summary>
    public static final int COMPANY = 0x0F;
    /// <summary>
    /// Bool.
    /// MUST be a VT_BOOL TypedPropertyValue ([MS-OLEPS] section 2.15) property. 
    /// The property value specifies TRUE (any value other than 0x00000000) if any of the values for 
    /// the linked properties in the User Defined Property Set have changed outside of the application, 
    /// which would require linked field fix up on document load.
    /// </summary>
    public static final int LINKS_UP_TO_DATE = 0x10;
    /// <summary>
    /// MUST be a VT_I4 TypedPropertyValue ([MS-OLEPS] section 2.15) property. The integer value of the 
    /// property specifies an estimate of the number of characters in the document including whitespace.
    /// </summary>
    public static final int CHARACTERS_WITH_SPACES = 0x11;
    /// <summary>
    /// MUST be a VT_BOOL TypedPropertyValue ([MS-OLEPS] section 2.15) property. 
    /// The property value MUST be FALSE (0x00000000).
    /// </summary>
    public static final int SHARE_DOC = 0x13;
    /// <summary>
    /// MUST NOT be written (to built-in properties). The base URL property is persisted to the User Defined Property 
    /// Set with the _PID_LINKBASE property name.
    /// </summary>
    public static final int LINK_BASE = 0x14;
    /// <summary>
    /// MUST NOT be written (to built-in properties). The hyperlinks property is persisted to the User Defined Property 
    /// Set with the _PID_HLINKS property name.
    /// </summary>
    public static final int HYPERLINKS = 0x15;
    /// <summary>
    /// MUST be a VT_BOOL TypedPropertyValue ([MS-OLEPS] section 2.15) property. The property value 
    /// specifies TRUE (any value other than 0x00000000) if the _PID_HLINKS property in the User 
    /// Defined Property Set has changed outside of the application, which would require hyperlink 
    /// fix up on document load.
    /// </summary>
    public static final int HYPERLINKS_CHANGED = 0x16;
    /// <summary>
    /// Version of the application that created the document.
    /// 
    /// MUST be a VT_I4 TypedPropertyValue ([MS-OLEPS] section 2.15) property. The unsigned integer 
    /// value of the property specifies the version of the application that wrote the property set storage. 
    /// The two high order bytes specify an unsigned integer specifying the major version number. 
    /// The two low order bytes specify an unsigned integer specifying the minor version number. 
    /// The value MUST have the major version number set to a nonzero value, and the minor version 
    /// number SHOULD always be 0x0000. The minor version number MAY be set to the minor version 
    /// number of the application that wrote the property set storage.
    /// </summary>
    public static final int VERSION = 0x17;
    public static final int EXCEL_DIGITAL_SIGNATURE = 0x18;
    /// <summary>
    /// MUST be a VtString property. VtString.stringValue specifies the content type of the file. 
    /// MAY be absent.
    /// </summary>
    public static final int CONTENT_TYPE = 0x1a;
    /// <summary>
    /// MUST be a VtString property. VtString.stringValue specifies the document status. MAY be absent.
    /// </summary>
    public static final int CONTENT_STATUS = 0x1b;
    /// <summary>
    /// MUST be a VtString property. SHOULD be absent.
    /// </summary>
    public static final int LANGUAGE = 0x1c;
    /// <summary>
    /// MUST be a VtString property. SHOULD be absent.
    /// </summary>
    public static final int DOC_VERSION = 0x1d;
    // Not sure how to make it compile. Don't need it anyway.
    // LocaleId = 0x80000000
}

