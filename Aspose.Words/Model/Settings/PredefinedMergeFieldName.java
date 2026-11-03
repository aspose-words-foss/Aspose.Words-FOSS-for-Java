// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the predefined merge field name. 
/// </summary>
/// <dev>Do not renumber these as they are fixed for the DOC format.</dev>
/*enum*/ final class PredefinedMergeFieldName
{
    private PredefinedMergeFieldName(){}
    
    public static final int INVALID = -1;

    public static final int UNIQUE_IDENTIFIER = 0;
    public static final int COURTESY_TITLE = 1;
    public static final int FIRST_NAME = 2;
    public static final int MIDDLE_NAME = 3;
    public static final int LAST_NAME = 4;
    public static final int SUFFIX = 5;
    public static final int NICKNAME = 6;
    public static final int JOB_TITLE = 7;
    public static final int COMPANY = 8;
    public static final int ADDRESS_1 = 9;
    public static final int ADDRESS_2 = 10;
    public static final int CITY = 11;
    public static final int STATE = 12;
    public static final int POSTAL_CODE = 13;
    public static final int COUNTRY_OR_REGION = 14;
    public static final int BUSINESS_PHONE = 15;
    public static final int BUSINESS_FAX = 16;
    public static final int HOME_PHONE = 17;
    public static final int HOME_FAX = 18;
    public static final int EMAIL_ADDRESS = 19;
    public static final int WEB_PAGE = 20;
    public static final int SPOUSE_COURTESY_TITLE = 21;
    public static final int SPOUSE_FIRST_NAME = 22;
    public static final int SPOUSE_MIDDLE_NAME = 23;
    public static final int SPOUSE_LAST_NAME = 24;
    public static final int SPOUSE_NICKNAME = 25;
    public static final int PHONETIC_GUIDE_FOR_FIRST_NAME = 26;
    public static final int PHONETIC_GUIDE_FOR_LAST_NAME = 27;
    public static final int ADDRESS_3 = 28;
    public static final int DEPARTMENT = 29;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PredefinedMergeFieldName value.
    /// </summary>
    public static String getName(/*PredefinedMergeFieldName*/int predefinedMergeFieldName)
    {
    	switch (predefinedMergeFieldName)
    	{
    		case INVALID: return "INVALID";
    		case UNIQUE_IDENTIFIER: return "UNIQUE_IDENTIFIER";
    		case COURTESY_TITLE: return "COURTESY_TITLE";
    		case FIRST_NAME: return "FIRST_NAME";
    		case MIDDLE_NAME: return "MIDDLE_NAME";
    		case LAST_NAME: return "LAST_NAME";
    		case SUFFIX: return "SUFFIX";
    		case NICKNAME: return "NICKNAME";
    		case JOB_TITLE: return "JOB_TITLE";
    		case COMPANY: return "COMPANY";
    		case ADDRESS_1: return "ADDRESS_1";
    		case ADDRESS_2: return "ADDRESS_2";
    		case CITY: return "CITY";
    		case STATE: return "STATE";
    		case POSTAL_CODE: return "POSTAL_CODE";
    		case COUNTRY_OR_REGION: return "COUNTRY_OR_REGION";
    		case BUSINESS_PHONE: return "BUSINESS_PHONE";
    		case BUSINESS_FAX: return "BUSINESS_FAX";
    		case HOME_PHONE: return "HOME_PHONE";
    		case HOME_FAX: return "HOME_FAX";
    		case EMAIL_ADDRESS: return "EMAIL_ADDRESS";
    		case WEB_PAGE: return "WEB_PAGE";
    		case SPOUSE_COURTESY_TITLE: return "SPOUSE_COURTESY_TITLE";
    		case SPOUSE_FIRST_NAME: return "SPOUSE_FIRST_NAME";
    		case SPOUSE_MIDDLE_NAME: return "SPOUSE_MIDDLE_NAME";
    		case SPOUSE_LAST_NAME: return "SPOUSE_LAST_NAME";
    		case SPOUSE_NICKNAME: return "SPOUSE_NICKNAME";
    		case PHONETIC_GUIDE_FOR_FIRST_NAME: return "PHONETIC_GUIDE_FOR_FIRST_NAME";
    		case PHONETIC_GUIDE_FOR_LAST_NAME: return "PHONETIC_GUIDE_FOR_LAST_NAME";
    		case ADDRESS_3: return "ADDRESS_3";
    		case DEPARTMENT: return "DEPARTMENT";
    		default: return "Unknown PredefinedMergeFieldName value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PredefinedMergeFieldName value.
    /// </summary>
    public static String toString(/*PredefinedMergeFieldName*/int predefinedMergeFieldName)
    {
    	switch (predefinedMergeFieldName)
    	{
    		case INVALID: return "Invalid";
    		case UNIQUE_IDENTIFIER: return "UniqueIdentifier";
    		case COURTESY_TITLE: return "CourtesyTitle";
    		case FIRST_NAME: return "FirstName";
    		case MIDDLE_NAME: return "MiddleName";
    		case LAST_NAME: return "LastName";
    		case SUFFIX: return "Suffix";
    		case NICKNAME: return "Nickname";
    		case JOB_TITLE: return "JobTitle";
    		case COMPANY: return "Company";
    		case ADDRESS_1: return "Address1";
    		case ADDRESS_2: return "Address2";
    		case CITY: return "City";
    		case STATE: return "State";
    		case POSTAL_CODE: return "PostalCode";
    		case COUNTRY_OR_REGION: return "CountryOrRegion";
    		case BUSINESS_PHONE: return "BusinessPhone";
    		case BUSINESS_FAX: return "BusinessFax";
    		case HOME_PHONE: return "HomePhone";
    		case HOME_FAX: return "HomeFax";
    		case EMAIL_ADDRESS: return "EmailAddress";
    		case WEB_PAGE: return "WebPage";
    		case SPOUSE_COURTESY_TITLE: return "SpouseCourtesyTitle";
    		case SPOUSE_FIRST_NAME: return "SpouseFirstName";
    		case SPOUSE_MIDDLE_NAME: return "SpouseMiddleName";
    		case SPOUSE_LAST_NAME: return "SpouseLastName";
    		case SPOUSE_NICKNAME: return "SpouseNickname";
    		case PHONETIC_GUIDE_FOR_FIRST_NAME: return "PhoneticGuideForFirstName";
    		case PHONETIC_GUIDE_FOR_LAST_NAME: return "PhoneticGuideForLastName";
    		case ADDRESS_3: return "Address3";
    		case DEPARTMENT: return "Department";
    		default: return "Unknown PredefinedMergeFieldName value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PredefinedMergeFieldName name.
    /// </summary>
    public static /*PredefinedMergeFieldName*/int fromName(String predefinedMergeFieldNameName)
    {
    	if ("INVALID".equals(predefinedMergeFieldNameName)) return INVALID;
    	if ("UNIQUE_IDENTIFIER".equals(predefinedMergeFieldNameName)) return UNIQUE_IDENTIFIER;
    	if ("COURTESY_TITLE".equals(predefinedMergeFieldNameName)) return COURTESY_TITLE;
    	if ("FIRST_NAME".equals(predefinedMergeFieldNameName)) return FIRST_NAME;
    	if ("MIDDLE_NAME".equals(predefinedMergeFieldNameName)) return MIDDLE_NAME;
    	if ("LAST_NAME".equals(predefinedMergeFieldNameName)) return LAST_NAME;
    	if ("SUFFIX".equals(predefinedMergeFieldNameName)) return SUFFIX;
    	if ("NICKNAME".equals(predefinedMergeFieldNameName)) return NICKNAME;
    	if ("JOB_TITLE".equals(predefinedMergeFieldNameName)) return JOB_TITLE;
    	if ("COMPANY".equals(predefinedMergeFieldNameName)) return COMPANY;
    	if ("ADDRESS_1".equals(predefinedMergeFieldNameName)) return ADDRESS_1;
    	if ("ADDRESS_2".equals(predefinedMergeFieldNameName)) return ADDRESS_2;
    	if ("CITY".equals(predefinedMergeFieldNameName)) return CITY;
    	if ("STATE".equals(predefinedMergeFieldNameName)) return STATE;
    	if ("POSTAL_CODE".equals(predefinedMergeFieldNameName)) return POSTAL_CODE;
    	if ("COUNTRY_OR_REGION".equals(predefinedMergeFieldNameName)) return COUNTRY_OR_REGION;
    	if ("BUSINESS_PHONE".equals(predefinedMergeFieldNameName)) return BUSINESS_PHONE;
    	if ("BUSINESS_FAX".equals(predefinedMergeFieldNameName)) return BUSINESS_FAX;
    	if ("HOME_PHONE".equals(predefinedMergeFieldNameName)) return HOME_PHONE;
    	if ("HOME_FAX".equals(predefinedMergeFieldNameName)) return HOME_FAX;
    	if ("EMAIL_ADDRESS".equals(predefinedMergeFieldNameName)) return EMAIL_ADDRESS;
    	if ("WEB_PAGE".equals(predefinedMergeFieldNameName)) return WEB_PAGE;
    	if ("SPOUSE_COURTESY_TITLE".equals(predefinedMergeFieldNameName)) return SPOUSE_COURTESY_TITLE;
    	if ("SPOUSE_FIRST_NAME".equals(predefinedMergeFieldNameName)) return SPOUSE_FIRST_NAME;
    	if ("SPOUSE_MIDDLE_NAME".equals(predefinedMergeFieldNameName)) return SPOUSE_MIDDLE_NAME;
    	if ("SPOUSE_LAST_NAME".equals(predefinedMergeFieldNameName)) return SPOUSE_LAST_NAME;
    	if ("SPOUSE_NICKNAME".equals(predefinedMergeFieldNameName)) return SPOUSE_NICKNAME;
    	if ("PHONETIC_GUIDE_FOR_FIRST_NAME".equals(predefinedMergeFieldNameName)) return PHONETIC_GUIDE_FOR_FIRST_NAME;
    	if ("PHONETIC_GUIDE_FOR_LAST_NAME".equals(predefinedMergeFieldNameName)) return PHONETIC_GUIDE_FOR_LAST_NAME;
    	if ("ADDRESS_3".equals(predefinedMergeFieldNameName)) return ADDRESS_3;
    	if ("DEPARTMENT".equals(predefinedMergeFieldNameName)) return DEPARTMENT;
    	throw new IllegalArgumentException("Unknown PredefinedMergeFieldName name.");
    }
    
    /// <summary>
    /// Returns array of PredefinedMergeFieldName values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INVALID,
    		UNIQUE_IDENTIFIER,
    		COURTESY_TITLE,
    		FIRST_NAME,
    		MIDDLE_NAME,
    		LAST_NAME,
    		SUFFIX,
    		NICKNAME,
    		JOB_TITLE,
    		COMPANY,
    		ADDRESS_1,
    		ADDRESS_2,
    		CITY,
    		STATE,
    		POSTAL_CODE,
    		COUNTRY_OR_REGION,
    		BUSINESS_PHONE,
    		BUSINESS_FAX,
    		HOME_PHONE,
    		HOME_FAX,
    		EMAIL_ADDRESS,
    		WEB_PAGE,
    		SPOUSE_COURTESY_TITLE,
    		SPOUSE_FIRST_NAME,
    		SPOUSE_MIDDLE_NAME,
    		SPOUSE_LAST_NAME,
    		SPOUSE_NICKNAME,
    		PHONETIC_GUIDE_FOR_FIRST_NAME,
    		PHONETIC_GUIDE_FOR_LAST_NAME,
    		ADDRESS_3,
    		DEPARTMENT,
    	};
    }

    public static final int length = 31;
}

