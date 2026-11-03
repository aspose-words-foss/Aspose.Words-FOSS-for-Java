// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.StringToIntBidirectionalMap;


/// <summary>
/// Converts mail merge settings enums to/from DOCX and WML.
/// </summary>
/*static*/ class MailMergeEnum
{
	/* Simulation of static class by using private constructor */
	private MailMergeEnum()
	{}

    static /*MailMergeMainDocumentType*/int xmlToMailMergeMainDocumentType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"catalog"*/0: return MailMergeMainDocumentType.CATALOG;
            case /*"email"*/1: return MailMergeMainDocumentType.EMAIL;
            case /*"envelopes"*/2: return MailMergeMainDocumentType.ENVELOPES;
            case /*"fax"*/3: return MailMergeMainDocumentType.FAX;
            case /*"formLetters"*/4: return MailMergeMainDocumentType.FORM_LETTERS;
            case /*"form-letters"*/5: return MailMergeMainDocumentType.FORM_LETTERS;
            case /*"mailingLabels"*/6: return MailMergeMainDocumentType.MAILING_LABELS;
            case /*"mailing-labels"*/7: return MailMergeMainDocumentType.MAILING_LABELS;
            // This returns a value different from .Default because in the model and in OOXML the default value is different.
            default: return MailMergeMainDocumentType.FORM_LETTERS;
        }
    }

    static String mailMergeMainDocumentTypeToXml(/*MailMergeMainDocumentType*/int value, boolean isDocx)
    {
        // This has no usual check for .Default because in the model and in OOXML the default value is different.

        switch (value)
        {
            case MailMergeMainDocumentType.CATALOG: return "catalog";
            case MailMergeMainDocumentType.EMAIL: return "email";
            case MailMergeMainDocumentType.ENVELOPES: return "envelopes";
            case MailMergeMainDocumentType.FAX: return "fax";
            case MailMergeMainDocumentType.FORM_LETTERS: return (isDocx) ? "formLetters" : "form-letters";
            case MailMergeMainDocumentType.MAILING_LABELS: return (isDocx) ? "mailingLabels" : "mailing-labels";
            // It is better to throw here because this value must be written according to the schema.
            default: throw new IllegalStateException("Unknown mail merge main document type.");
        }
    }

    static /*MailMergeDestination*/int xmlToMailMergeDestination(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"newDocument"*/8: return MailMergeDestination.NEW_DOCUMENT;
            case /*"new-document"*/9: return MailMergeDestination.NEW_DOCUMENT;
            case /*"printer"*/10: return MailMergeDestination.PRINTER;
            case /*"email"*/1: return MailMergeDestination.EMAIL;
            case /*"fax"*/3: return MailMergeDestination.FAX;
            default: return MailMergeDestination.DEFAULT;
        }
    }

    static String mailMergeDestinationToXml(/*MailMergeDestination*/int value, boolean isDocx)
    {
        if (value == MailMergeDestination.DEFAULT)
            return "";

        switch (value)
        {
            case MailMergeDestination.NEW_DOCUMENT: return (isDocx) ? "newDocument" : "new-document";
            case MailMergeDestination.PRINTER: return "printer";
            case MailMergeDestination.EMAIL: return "email";
            case MailMergeDestination.FAX: return "fax";
            default: return "";
        }
    }

    static /*MailMergeDataType*/int xmlToMailMergeDataType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"database"*/11: return MailMergeDataType.DATABASE;
            case /*"Access"*/12: return MailMergeDataType.DATABASE;

            case /*"native"*/13: return MailMergeDataType.NATIVE;
            case /*"ODSO"*/14: return MailMergeDataType.NATIVE;

            case /*"odbc"*/15: return MailMergeDataType.ODBC;
            case /*"ODBC"*/16: return MailMergeDataType.ODBC;

            case /*"query"*/17: return MailMergeDataType.QUERY;
            case /*"QT"*/18: return MailMergeDataType.QUERY;

            case /*"spreadsheet"*/19: return MailMergeDataType.SPREADSHEET;
            case /*"Excel"*/20: return MailMergeDataType.SPREADSHEET;

            case /*"textFile"*/21: return MailMergeDataType.TEXT_FILE;
            case /*"file"*/22: return MailMergeDataType.TEXT_FILE;
                
            default: return MailMergeDataType.DEFAULT;
        }
    }

    static String mailMergeDataTypeToXml(/*MailMergeDataType*/int value, boolean isDocx)
    {
        switch (value)
        {
            case MailMergeDataType.DATABASE: return isDocx ? "database" : "Access";
            case MailMergeDataType.NATIVE: return isDocx ? "native" : "ODSO";
            case MailMergeDataType.ODBC: return isDocx ? "odbc" : "ODBC";
            case MailMergeDataType.QUERY: return isDocx ? "query" : "QT";
            case MailMergeDataType.SPREADSHEET: return isDocx ? "spreadsheet" : "Excel";
            case MailMergeDataType.TEXT_FILE: return isDocx ? "textFile" : "file";
            default: throw new IllegalStateException("Unknown mail merge data source type.");
        }
    }

    static /*OdsoDataSourceType*/int xmlToOdsoDataSourceType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"addressBook"*/23: return OdsoDataSourceType.ADDRESS_BOOK;
            case /*"database"*/11: return OdsoDataSourceType.DATABASE;
            case /*"document1"*/24: return OdsoDataSourceType.DOCUMENT_1;
            case /*"document2"*/25: return OdsoDataSourceType.DOCUMENT_2;
            case /*"email"*/1: return OdsoDataSourceType.EMAIL;
            case /*"legacy"*/26: return OdsoDataSourceType.LEGACY;
            case /*"master"*/27: return OdsoDataSourceType.MASTER;
            case /*"native"*/13: return OdsoDataSourceType.NATIVE;
            case /*"text"*/28: return OdsoDataSourceType.TEXT;
            default:
            {
                int result = FormatterPal.tryParseInt(value);
                if (result != Integer.MIN_VALUE)
                    return (/*OdsoDataSourceType*/int)result;
                else
                    return OdsoDataSourceType.DEFAULT;
            }
        }
    }

    static String odsoDataSourceTypeToXml(/*OdsoDataSourceType*/int value, boolean isDocx)
    {
        if (value == OdsoDataSourceType.DEFAULT)
            return "";

        if (isDocx)
        {
            switch (value)
            {
                case OdsoDataSourceType.ADDRESS_BOOK: return "addressBook";
                case OdsoDataSourceType.DATABASE: return "database";
                case OdsoDataSourceType.DOCUMENT_1: return "document1";
                case OdsoDataSourceType.DOCUMENT_2: return "document2";
                case OdsoDataSourceType.EMAIL: return "email";
                case OdsoDataSourceType.LEGACY: return "legacy";
                case OdsoDataSourceType.MASTER: return "master";
                case OdsoDataSourceType.NATIVE: return "native";
                case OdsoDataSourceType.TEXT: return "text";
                default: return "";
            }
        }
        else
        {
            return Integer.toString(((int)value));
        }
    }

    static /*OdsoFieldMappingType*/int xmlToOdsoFieldMappingType(String value)
    {
        switch (gStringSwitchMap.of(value))
        {
            case /*"dbColumn"*/29: return OdsoFieldMappingType.COLUMN;
            case /*"db-column"*/30: return OdsoFieldMappingType.COLUMN;

            case /*"null"*/31: return OdsoFieldMappingType.NULL;

            // These are present only in WML but not in DOCX. Let's default them for now.
            case /*"address-block"*/32:
            case /*"salutation"*/33:
            case /*"mapped"*/34:
            case /*"barcode"*/35:
                return OdsoFieldMappingType.DEFAULT;

            default: return OdsoFieldMappingType.DEFAULT;
        }
    }

    static String odsoFieldMappingTypeToXml(/*OdsoFieldMappingType*/int value, boolean isDocx)
    {
        if (value == OdsoFieldMappingType.DEFAULT)
            return "";

        switch (value)
        {
            case OdsoFieldMappingType.COLUMN: return isDocx ? "dbColumn" : "db-column";
            case OdsoFieldMappingType.NULL: return "null";
            default: return "";
        }
    }

    static /*PredefinedMergeFieldName*/int stringToPredefinedMergeFieldName(String value)
    {
        return (/*PredefinedMergeFieldName*/int)G_PREDEFINED_MERGE_FIELD_NAME_MAP.getValue(value, (int)PredefinedMergeFieldName.INVALID);
    }

    static String predefinedMergeFieldNameToString(/*PredefinedMergeFieldName*/int value)
    {
        return G_PREDEFINED_MERGE_FIELD_NAME_MAP.getValue((int)value, "");
    }

    private static /*final*/ StringToIntBidirectionalMap G_PREDEFINED_MERGE_FIELD_NAME_MAP = new StringToIntBidirectionalMap();
    static/* MailMergeEnum()*/
    {
        // 30 standard mail merge address fields. Defined in 2.9.162 ODSOPropertyBase in the DOC specification.
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Unique Identifier", (int)PredefinedMergeFieldName.UNIQUE_IDENTIFIER);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Courtesy Title", (int)PredefinedMergeFieldName.COURTESY_TITLE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("First Name", (int)PredefinedMergeFieldName.FIRST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Middle Name", (int)PredefinedMergeFieldName.MIDDLE_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Last Name", (int)PredefinedMergeFieldName.LAST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Suffix", (int)PredefinedMergeFieldName.SUFFIX);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Nickname", (int)PredefinedMergeFieldName.NICKNAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Job Title", (int)PredefinedMergeFieldName.JOB_TITLE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Company", (int)PredefinedMergeFieldName.COMPANY);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Address 1", (int)PredefinedMergeFieldName.ADDRESS_1);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Address 2", (int)PredefinedMergeFieldName.ADDRESS_2);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("City", (int)PredefinedMergeFieldName.CITY);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("State", (int)PredefinedMergeFieldName.STATE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Postal Code", (int)PredefinedMergeFieldName.POSTAL_CODE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Country or Region", (int)PredefinedMergeFieldName.COUNTRY_OR_REGION);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Business Phone", (int)PredefinedMergeFieldName.BUSINESS_PHONE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Business Fax", (int)PredefinedMergeFieldName.BUSINESS_FAX);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Home Phone", (int)PredefinedMergeFieldName.HOME_PHONE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Home Fax", (int)PredefinedMergeFieldName.HOME_FAX);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("E-mail Address", (int)PredefinedMergeFieldName.EMAIL_ADDRESS);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Web Page", (int)PredefinedMergeFieldName.WEB_PAGE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Spouse Courtesy Title", (int)PredefinedMergeFieldName.SPOUSE_COURTESY_TITLE);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Spouse First Name", (int)PredefinedMergeFieldName.SPOUSE_FIRST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Spouse Middle Name", (int)PredefinedMergeFieldName.SPOUSE_MIDDLE_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Spouse Last Name", (int)PredefinedMergeFieldName.SPOUSE_LAST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Spouse Nickname", (int)PredefinedMergeFieldName.SPOUSE_NICKNAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Phonetic Guide for First Name", (int)PredefinedMergeFieldName.PHONETIC_GUIDE_FOR_FIRST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Phonetic Guide for Last Name", (int)PredefinedMergeFieldName.PHONETIC_GUIDE_FOR_LAST_NAME);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Address 3", (int)PredefinedMergeFieldName.ADDRESS_3);
        G_PREDEFINED_MERGE_FIELD_NAME_MAP.addEntry("Department", (int)PredefinedMergeFieldName.DEPARTMENT);

    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"catalog",
		"email",
		"envelopes",
		"fax",
		"formLetters",
		"form-letters",
		"mailingLabels",
		"mailing-labels",
		"newDocument",
		"new-document",
		"printer",
		"database",
		"Access",
		"native",
		"ODSO",
		"odbc",
		"ODBC",
		"query",
		"QT",
		"spreadsheet",
		"Excel",
		"textFile",
		"file",
		"addressBook",
		"document1",
		"document2",
		"legacy",
		"master",
		"text",
		"dbColumn",
		"db-column",
		"null",
		"address-block",
		"salutation",
		"mapped",
		"barcode"
	);

}

