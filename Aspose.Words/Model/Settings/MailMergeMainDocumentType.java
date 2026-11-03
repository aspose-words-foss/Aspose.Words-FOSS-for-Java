// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the possible types for a mail merge source document.
/// </summary>
/// <seealso cref="MailMergeSettings.MainDocumentType"/>
/// <dev>Do not renumber these because the values are used in the DOC codec.</dev>
public /*enum*/ final class MailMergeMainDocumentType
{
    private MailMergeMainDocumentType(){}
    
    /// <summary>
    /// This document is not a mail merge document.
    /// </summary>
    public static final int NOT_A_MERGE_DOCUMENT = 0x00;
    /// <summary>
    /// Specifies that the mail merge source document is of the form letter type.
    /// </summary>
    public static final int FORM_LETTERS = 0x01;
    /// <summary>
    /// Specifies that the mail merge source document is of the mailing label type.
    /// </summary>
    public static final int MAILING_LABELS = 0x02;
    /// <summary>
    /// Specifies that the mail merge source document is of the envelope type.
    /// </summary>
    public static final int ENVELOPES = 0x04;
    /// <summary>
    /// Specifies that the mail merge source document is of the catalog type.
    /// </summary>
    public static final int CATALOG = 0x08;
    /// <summary>
    /// Specifies that the mail merge source document is of the e-mail message type.
    /// </summary>
    public static final int EMAIL = 0x10;
    /// <summary>
    /// Specifies that the mail merge source document is of the fax type.
    /// </summary>
    public static final int FAX = 0x20; 

    /// <summary>
    /// Equals to <see cref="NotAMergeDocument"/>
    /// </summary>
    public static final int DEFAULT = NOT_A_MERGE_DOCUMENT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MailMergeMainDocumentType value.
    /// </summary>
    public static String getName(/*MailMergeMainDocumentType*/int mailMergeMainDocumentType)
    {
    	switch (mailMergeMainDocumentType)
    	{
    		case NOT_A_MERGE_DOCUMENT:
    		/*case DEFAULT:*/
    			return "NOT_A_MERGE_DOCUMENT" + " | " + "DEFAULT";
    		case FORM_LETTERS: return "FORM_LETTERS";
    		case MAILING_LABELS: return "MAILING_LABELS";
    		case ENVELOPES: return "ENVELOPES";
    		case CATALOG: return "CATALOG";
    		case EMAIL: return "EMAIL";
    		case FAX: return "FAX";
    		default: return "Unknown MailMergeMainDocumentType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MailMergeMainDocumentType value.
    /// </summary>
    public static String toString(/*MailMergeMainDocumentType*/int mailMergeMainDocumentType)
    {
    	switch (mailMergeMainDocumentType)
    	{
    		case NOT_A_MERGE_DOCUMENT:
    		/*case DEFAULT:*/
    			return "NotAMergeDocument" + " | " + "Default";
    		case FORM_LETTERS: return "FormLetters";
    		case MAILING_LABELS: return "MailingLabels";
    		case ENVELOPES: return "Envelopes";
    		case CATALOG: return "Catalog";
    		case EMAIL: return "Email";
    		case FAX: return "Fax";
    		default: return "Unknown MailMergeMainDocumentType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MailMergeMainDocumentType name.
    /// </summary>
    public static /*MailMergeMainDocumentType*/int fromName(String mailMergeMainDocumentTypeName)
    {
    	if ("NOT_A_MERGE_DOCUMENT".equals(mailMergeMainDocumentTypeName)) return NOT_A_MERGE_DOCUMENT;
    	if ("FORM_LETTERS".equals(mailMergeMainDocumentTypeName)) return FORM_LETTERS;
    	if ("MAILING_LABELS".equals(mailMergeMainDocumentTypeName)) return MAILING_LABELS;
    	if ("ENVELOPES".equals(mailMergeMainDocumentTypeName)) return ENVELOPES;
    	if ("CATALOG".equals(mailMergeMainDocumentTypeName)) return CATALOG;
    	if ("EMAIL".equals(mailMergeMainDocumentTypeName)) return EMAIL;
    	if ("FAX".equals(mailMergeMainDocumentTypeName)) return FAX;
    	if ("DEFAULT".equals(mailMergeMainDocumentTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MailMergeMainDocumentType name.");
    }
    
    /// <summary>
    /// Returns array of MailMergeMainDocumentType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NOT_A_MERGE_DOCUMENT,
    		FORM_LETTERS,
    		MAILING_LABELS,
    		ENVELOPES,
    		CATALOG,
    		EMAIL,
    		FAX,
    		DEFAULT,
    	};
    }

    public static final int length = 8;
}

