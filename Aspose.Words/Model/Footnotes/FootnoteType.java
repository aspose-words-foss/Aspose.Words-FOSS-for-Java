// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether this is a footnote or an endnote.
/// </summary>
/// <remarks>
/// <para>Both footnotes and endnotes are represented by objects by the <see cref="Footnote"/>
/// class. Use <see cref="Aspose.Words.Notes.Footnote.FootnoteType"/> to distinguish between footnotes 
/// and endnotes.</para>
///
/// <seealso cref="Footnote"/>
/// </remarks>
public /*enum*/ final class FootnoteType
{
    private FootnoteType(){}
    
    /// <summary>
    /// The object is a footnote.
    /// </summary>
    public static final int FOOTNOTE = 0;
    /// <summary>
    /// The object is an endnote.
    /// </summary>
    public static final int ENDNOTE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FootnoteType value.
    /// </summary>
    public static String getName(/*FootnoteType*/int footnoteType)
    {
    	switch (footnoteType)
    	{
    		case FOOTNOTE: return "FOOTNOTE";
    		case ENDNOTE: return "ENDNOTE";
    		default: return "Unknown FootnoteType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FootnoteType value.
    /// </summary>
    public static String toString(/*FootnoteType*/int footnoteType)
    {
    	switch (footnoteType)
    	{
    		case FOOTNOTE: return "Footnote";
    		case ENDNOTE: return "Endnote";
    		default: return "Unknown FootnoteType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FootnoteType name.
    /// </summary>
    public static /*FootnoteType*/int fromName(String footnoteTypeName)
    {
    	if ("FOOTNOTE".equals(footnoteTypeName)) return FOOTNOTE;
    	if ("ENDNOTE".equals(footnoteTypeName)) return ENDNOTE;
    	throw new IllegalArgumentException("Unknown FootnoteType name.");
    }
    
    /// <summary>
    /// Returns array of FootnoteType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FOOTNOTE,
    		ENDNOTE,
    	};
    }

    public static final int length = 2;
}

