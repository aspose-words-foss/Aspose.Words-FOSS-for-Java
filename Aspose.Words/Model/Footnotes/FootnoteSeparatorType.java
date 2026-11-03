// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of the footnote/endnote separator.
/// </summary>
public /*enum*/ final class FootnoteSeparatorType
{
    private FootnoteSeparatorType(){}
    
    /// <summary>
    /// Separator between main text and footnote text.
    /// </summary>
    /// <dev>
    /// In Word 2003 files this is \x03\r
    /// Do not renumber! Values below are used by DOC import/export.
    /// </dev>
    public static final int FOOTNOTE_SEPARATOR = 0;

    /// <summary>
    /// Printed above footnote text on a page when the text must be continued from a previous page.
    /// </summary>
    /// <dev>
    /// Usually \x04\r
    /// </dev>
    public static final int FOOTNOTE_CONTINUATION_SEPARATOR = 1;

    /// <summary>
    /// Printed below footnote text on a page when footnote text must be continued on a succeeding page.
    /// </summary>
    /// <dev>
    /// Usually empty.
    /// </dev>
    public static final int FOOTNOTE_CONTINUATION_NOTICE = 2;

    /// <summary>
    /// Separator between main text and endnote text.
    /// </summary>
    /// <dev>
    /// Usually \x03\r
    /// </dev>
    public static final int ENDNOTE_SEPARATOR = 3;

    /// <summary>
    /// Printed above endnote text on a page when the text must be continued from a previous page.
    /// </summary>
    /// <dev>
    /// Usually \x04\r
    /// </dev>
    public static final int ENDNOTE_CONTINUATION_SEPARATOR = 4;

    /// <summary>
    /// Printed below endnote text on a page when endnote text must be continued on a succeeding page.
    /// </summary>
    /// <dev>
    /// Usually empty.
    /// </dev>
    public static final int ENDNOTE_CONTINUATION_NOTICE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FootnoteSeparatorType value.
    /// </summary>
    public static String getName(/*FootnoteSeparatorType*/int footnoteSeparatorType)
    {
    	switch (footnoteSeparatorType)
    	{
    		case FOOTNOTE_SEPARATOR: return "FOOTNOTE_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FOOTNOTE_CONTINUATION_SEPARATOR";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FOOTNOTE_CONTINUATION_NOTICE";
    		case ENDNOTE_SEPARATOR: return "ENDNOTE_SEPARATOR";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "ENDNOTE_CONTINUATION_SEPARATOR";
    		case ENDNOTE_CONTINUATION_NOTICE: return "ENDNOTE_CONTINUATION_NOTICE";
    		default: return "Unknown FootnoteSeparatorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FootnoteSeparatorType value.
    /// </summary>
    public static String toString(/*FootnoteSeparatorType*/int footnoteSeparatorType)
    {
    	switch (footnoteSeparatorType)
    	{
    		case FOOTNOTE_SEPARATOR: return "FootnoteSeparator";
    		case FOOTNOTE_CONTINUATION_SEPARATOR: return "FootnoteContinuationSeparator";
    		case FOOTNOTE_CONTINUATION_NOTICE: return "FootnoteContinuationNotice";
    		case ENDNOTE_SEPARATOR: return "EndnoteSeparator";
    		case ENDNOTE_CONTINUATION_SEPARATOR: return "EndnoteContinuationSeparator";
    		case ENDNOTE_CONTINUATION_NOTICE: return "EndnoteContinuationNotice";
    		default: return "Unknown FootnoteSeparatorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FootnoteSeparatorType name.
    /// </summary>
    public static /*FootnoteSeparatorType*/int fromName(String footnoteSeparatorTypeName)
    {
    	if ("FOOTNOTE_SEPARATOR".equals(footnoteSeparatorTypeName)) return FOOTNOTE_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_SEPARATOR".equals(footnoteSeparatorTypeName)) return FOOTNOTE_CONTINUATION_SEPARATOR;
    	if ("FOOTNOTE_CONTINUATION_NOTICE".equals(footnoteSeparatorTypeName)) return FOOTNOTE_CONTINUATION_NOTICE;
    	if ("ENDNOTE_SEPARATOR".equals(footnoteSeparatorTypeName)) return ENDNOTE_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_SEPARATOR".equals(footnoteSeparatorTypeName)) return ENDNOTE_CONTINUATION_SEPARATOR;
    	if ("ENDNOTE_CONTINUATION_NOTICE".equals(footnoteSeparatorTypeName)) return ENDNOTE_CONTINUATION_NOTICE;
    	throw new IllegalArgumentException("Unknown FootnoteSeparatorType name.");
    }
    
    /// <summary>
    /// Returns array of FootnoteSeparatorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FOOTNOTE_SEPARATOR,
    		FOOTNOTE_CONTINUATION_SEPARATOR,
    		FOOTNOTE_CONTINUATION_NOTICE,
    		ENDNOTE_SEPARATOR,
    		ENDNOTE_CONTINUATION_SEPARATOR,
    		ENDNOTE_CONTINUATION_NOTICE,
    	};
    }

    public static final int length = 6;
}

