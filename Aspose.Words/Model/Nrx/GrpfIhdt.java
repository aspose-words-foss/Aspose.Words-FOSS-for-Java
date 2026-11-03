// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/12/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Word 6.0 headers/footers presence flags.
/// </summary>
/*enum*/ final class GrpfIhdt
{
    private GrpfIhdt(){}
    
    public static final int NONE = 0x00;

    public static final int HEADER_EVEN = 0x01;
    public static final int HEADER_PRIMARY = 0x02;
    public static final int FOOTER_EVEN = 0x04;
    public static final int FOOTER_PRIMARY = 0x08;
    public static final int HEADER_FIRST = 0x10;
    public static final int FOOTER_FIRST = 0x20;

    public static final int ALL = FOOTER_FIRST | HEADER_FIRST | FOOTER_PRIMARY | FOOTER_EVEN | HEADER_PRIMARY | HEADER_EVEN;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GrpfIhdt value.
    /// </summary>
    public static String getName(/*GrpfIhdt*/int grpfIhdt)
    {
    	switch (grpfIhdt)
    	{
    		case NONE: return "NONE";
    		case HEADER_EVEN: return "HEADER_EVEN";
    		case HEADER_PRIMARY: return "HEADER_PRIMARY";
    		case FOOTER_EVEN: return "FOOTER_EVEN";
    		case FOOTER_PRIMARY: return "FOOTER_PRIMARY";
    		case HEADER_FIRST: return "HEADER_FIRST";
    		case FOOTER_FIRST: return "FOOTER_FIRST";
    		case ALL: return "ALL";
    		default: return "Unknown GrpfIhdt value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer GrpfIhdt value.
    /// </summary>
    public static Set<String> getNames(/*GrpfIhdt*/int grpfIhdt)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & grpfIhdt) == NONE) names.add("NONE");
    	if ((HEADER_EVEN & grpfIhdt) == HEADER_EVEN) names.add("HEADER_EVEN");
    	if ((HEADER_PRIMARY & grpfIhdt) == HEADER_PRIMARY) names.add("HEADER_PRIMARY");
    	if ((FOOTER_EVEN & grpfIhdt) == FOOTER_EVEN) names.add("FOOTER_EVEN");
    	if ((FOOTER_PRIMARY & grpfIhdt) == FOOTER_PRIMARY) names.add("FOOTER_PRIMARY");
    	if ((HEADER_FIRST & grpfIhdt) == HEADER_FIRST) names.add("HEADER_FIRST");
    	if ((FOOTER_FIRST & grpfIhdt) == FOOTER_FIRST) names.add("FOOTER_FIRST");
    	if ((ALL & grpfIhdt) == ALL) names.add("ALL");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GrpfIhdt value.
    /// </summary>
    public static String toString(/*GrpfIhdt*/int grpfIhdt)
    {
    	switch (grpfIhdt)
    	{
    		case NONE: return "None";
    		case HEADER_EVEN: return "HeaderEven";
    		case HEADER_PRIMARY: return "HeaderPrimary";
    		case FOOTER_EVEN: return "FooterEven";
    		case FOOTER_PRIMARY: return "FooterPrimary";
    		case HEADER_FIRST: return "HeaderFirst";
    		case FOOTER_FIRST: return "FooterFirst";
    		case ALL: return "All";
    		default: return toStringSet(grpfIhdt);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GrpfIhdt value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by GrpfIhdt name.
    /// </summary>
    public static /*GrpfIhdt*/int fromName(String grpfIhdtName)
    {
    	if ("NONE".equals(grpfIhdtName)) return NONE;
    	if ("HEADER_EVEN".equals(grpfIhdtName)) return HEADER_EVEN;
    	if ("HEADER_PRIMARY".equals(grpfIhdtName)) return HEADER_PRIMARY;
    	if ("FOOTER_EVEN".equals(grpfIhdtName)) return FOOTER_EVEN;
    	if ("FOOTER_PRIMARY".equals(grpfIhdtName)) return FOOTER_PRIMARY;
    	if ("HEADER_FIRST".equals(grpfIhdtName)) return HEADER_FIRST;
    	if ("FOOTER_FIRST".equals(grpfIhdtName)) return FOOTER_FIRST;
    	if ("ALL".equals(grpfIhdtName)) return ALL;
    	throw new IllegalArgumentException("Unknown GrpfIhdt name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by GrpfIhdt names.
    /// </summary>
    public static /*GrpfIhdt*/int fromNames(Set<String> grpfIhdtNames)
    {
    	/*GrpfIhdt*/int result = 0;
    	for (String name : grpfIhdtNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of GrpfIhdt values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		HEADER_EVEN,
    		HEADER_PRIMARY,
    		FOOTER_EVEN,
    		FOOTER_PRIMARY,
    		HEADER_FIRST,
    		FOOTER_FIRST,
    		ALL,
    	};
    }

    public static final int length = 8;
}

/// <summary>
/// Word 6.0 footnotes presence flags.
/// </summary>
/*enum*/ final class GrpfIhdtDop
{
    private GrpfIhdtDop(){}
    
    public static final int NONE = 0x00;

    public static final int FOOTNOTE_SEPARATOR = 0x01;
    public static final int FOOTNOTE_CONTINUATION_SEPARATOR = 0x02;
    public static final int FOOTNOTE_CONTINUATION_NOTICE = 0x04;

    public static final int ENDNOTE_SEPARATOR = 0x08;
    public static final int ENDNOTE_CONTINUATION_SEPARATOR = 0x10;
    public static final int ENDNOTE_CONTINUATION_NOTICE = 0x20;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GrpfIhdtDop value.
    /// </summary>
    public static String getName(/*GrpfIhdtDop*/int grpfIhdtDop)
    {
    	switch (grpfIhdtDop)
    	{
    		case NONE: return "NONE";
    		case FOOTNOTE_SEPARATOR: return "FOOTNOTE_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FOOTNOTE_CONTINUATION_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FOOTNOTE_CONTINUATION_NOTICE";
    		case ENDNOTE_SEPARATOR: return "ENDNOTE_SEPARATOR";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "ENDNOTE_CONTINUATION_SEPARATOR";
    		case ENDNOTE_CONTINUATION_NOTICE: return "ENDNOTE_CONTINUATION_NOTICE";
    		default: return "Unknown GrpfIhdtDop value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer GrpfIhdtDop value.
    /// </summary>
    public static Set<String> getNames(/*GrpfIhdtDop*/int grpfIhdtDop)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & grpfIhdtDop) == NONE) names.add("NONE");
    	if ((FOOTNOTE_SEPARATOR & grpfIhdtDop) == FOOTNOTE_SEPARATOR) names.add("FOOTNOTE_SEPARATOR");
    	if ((FOOTNOTE_CONTINUATION_SEPARATOR & grpfIhdtDop) == FOOTNOTE_CONTINUATION_SEPARATOR) names.add("FOOTNOTE_CONTINUATION_SEPARATOR");
    	if ((FOOTNOTE_CONTINUATION_NOTICE & grpfIhdtDop) == FOOTNOTE_CONTINUATION_NOTICE) names.add("FOOTNOTE_CONTINUATION_NOTICE");
    	if ((ENDNOTE_SEPARATOR & grpfIhdtDop) == ENDNOTE_SEPARATOR) names.add("ENDNOTE_SEPARATOR");
    	if ((ENDNOTE_CONTINUATION_SEPARATOR & grpfIhdtDop) == ENDNOTE_CONTINUATION_SEPARATOR) names.add("ENDNOTE_CONTINUATION_SEPARATOR");
    	if ((ENDNOTE_CONTINUATION_NOTICE & grpfIhdtDop) == ENDNOTE_CONTINUATION_NOTICE) names.add("ENDNOTE_CONTINUATION_NOTICE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GrpfIhdtDop value.
    /// </summary>
    public static String toString(/*GrpfIhdtDop*/int grpfIhdtDop)
    {
    	switch (grpfIhdtDop)
    	{
    		case NONE: return "None";
    		case FOOTNOTE_SEPARATOR: return "FootnoteSeparator";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FootnoteContinuationSeparator";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FootnoteContinuationNotice";
    		case ENDNOTE_SEPARATOR: return "EndnoteSeparator";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "EndnoteContinuationSeparator";
    		case ENDNOTE_CONTINUATION_NOTICE: return "EndnoteContinuationNotice";
    		default: return toStringSet(grpfIhdtDop);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GrpfIhdtDop value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by GrpfIhdtDop name.
    /// </summary>
    public static /*GrpfIhdtDop*/int fromName(String grpfIhdtDopName)
    {
    	if ("NONE".equals(grpfIhdtDopName)) return NONE;
    	if ("FOOTNOTE_SEPARATOR".equals(grpfIhdtDopName)) return FOOTNOTE_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_SEPARATOR".equals(grpfIhdtDopName)) return FOOTNOTE_CONTINUATION_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_NOTICE".equals(grpfIhdtDopName)) return FOOTNOTE_CONTINUATION_NOTICE;
    	if ("ENDNOTE_SEPARATOR".equals(grpfIhdtDopName)) return ENDNOTE_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_SEPARATOR".equals(grpfIhdtDopName)) return ENDNOTE_CONTINUATION_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_NOTICE".equals(grpfIhdtDopName)) return ENDNOTE_CONTINUATION_NOTICE;
    	throw new IllegalArgumentException("Unknown GrpfIhdtDop name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by GrpfIhdtDop names.
    /// </summary>
    public static /*GrpfIhdtDop*/int fromNames(Set<String> grpfIhdtDopNames)
    {
    	/*GrpfIhdtDop*/int result = 0;
    	for (String name : grpfIhdtDopNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of GrpfIhdtDop values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		FOOTNOTE_SEPARATOR,
    		FOOTNOTE_CONTINUATION_SEPARATOR,
    		FOOTNOTE_CONTINUATION_NOTICE,
    		ENDNOTE_SEPARATOR,
    		ENDNOTE_CONTINUATION_SEPARATOR,
    		ENDNOTE_CONTINUATION_NOTICE,
    	};
    }

    public static final int length = 7;
}

