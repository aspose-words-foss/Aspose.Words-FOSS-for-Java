// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2011 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// <para>Specifies format of particular embedded font inside <see cref="FontInfo"/> object.</para>
/// <para>When saving a document to a file, only embedded fonts of corresponding format are written down.</para>
/// </summary>
public /*enum*/ final class EmbeddedFontFormat
{
    private EmbeddedFontFormat(){}
    
    /// <summary>
    /// <para>Specifies Embedded OpenType (EOT) File Format.</para>
    /// <para>This format of embedded fonts used in DOC files.</para>
    /// </summary>
    /// <remarks>
    /// <para>See http://www.w3.org/Submission/EOT for description of the format.</para>
    /// </remarks>
    public static final int EMBEDDED_OPEN_TYPE = 0;

    /// <summary>
    /// <para>Specifies font, embedded as plain copy of OpenType (TrueType) font file.</para>
    /// <para>This format of embedded fonts used in Open Office XML format, including DOCX files.</para>
    /// </summary>
    public static final int OPEN_TYPE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EmbeddedFontFormat value.
    /// </summary>
    public static String getName(/*EmbeddedFontFormat*/int embeddedFontFormat)
    {
    	switch (embeddedFontFormat)
    	{
    		case EMBEDDED_OPEN_TYPE: return "EMBEDDED_OPEN_TYPE";
    		case OPEN_TYPE: return "OPEN_TYPE";
    		default: return "Unknown EmbeddedFontFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EmbeddedFontFormat value.
    /// </summary>
    public static String toString(/*EmbeddedFontFormat*/int embeddedFontFormat)
    {
    	switch (embeddedFontFormat)
    	{
    		case EMBEDDED_OPEN_TYPE: return "EmbeddedOpenType";
    		case OPEN_TYPE: return "OpenType";
    		default: return "Unknown EmbeddedFontFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EmbeddedFontFormat name.
    /// </summary>
    public static /*EmbeddedFontFormat*/int fromName(String embeddedFontFormatName)
    {
    	if ("EMBEDDED_OPEN_TYPE".equals(embeddedFontFormatName)) return EMBEDDED_OPEN_TYPE;
    	if ("OPEN_TYPE".equals(embeddedFontFormatName)) return OPEN_TYPE;
    	throw new IllegalArgumentException("Unknown EmbeddedFontFormat name.");
    }
    
    /// <summary>
    /// Returns array of EmbeddedFontFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMBEDDED_OPEN_TYPE,
    		OPEN_TYPE,
    	};
    }

    public static final int length = 2;
}

