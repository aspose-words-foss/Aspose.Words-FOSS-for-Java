// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2014 by Victor Chebotok

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Embeddings, overrides and isolations that control how text is processed by the Unicode Bidi Algorithm.
/// </summary>
/// <remarks>
/// Items of this enumeration correspond to different combinations of 
/// the 'unicode-bidi' (see http://www.w3.org/TR/CSS2/visuren.html#propdef-unicode-bidi)
/// and the 'direction' (see http://www.w3.org/TR/CSS2/visuren.html#propdef-direction) CSS properties.
/// Only active (non-normal) levels supported by our implementation of the bidirectional algorithm are included
/// in this enumeration.
/// </remarks>
public /*enum*/ final class BidiLevel
{
    private BidiLevel(){}
    
    /// <summary>
    /// An additional level of left-to-right embedding is opened for the text.
    /// </summary>
    public static final int EMBED_LTR = 0;
    /// <summary>
    /// An additional level of right-to-left embedding is opened for the text.
    /// </summary>
    public static final int EMBED_RTL = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BidiLevel value.
    /// </summary>
    public static String getName(/*BidiLevel*/int bidiLevel)
    {
    	switch (bidiLevel)
    	{
    		case EMBED_LTR: return "EMBED_LTR";
    		case EMBED_RTL: return "EMBED_RTL";
    		default: return "Unknown BidiLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BidiLevel value.
    /// </summary>
    public static String toString(/*BidiLevel*/int bidiLevel)
    {
    	switch (bidiLevel)
    	{
    		case EMBED_LTR: return "EmbedLtr";
    		case EMBED_RTL: return "EmbedRtl";
    		default: return "Unknown BidiLevel value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BidiLevel name.
    /// </summary>
    public static /*BidiLevel*/int fromName(String bidiLevelName)
    {
    	if ("EMBED_LTR".equals(bidiLevelName)) return EMBED_LTR;
    	if ("EMBED_RTL".equals(bidiLevelName)) return EMBED_RTL;
    	throw new IllegalArgumentException("Unknown BidiLevel name.");
    }
    
    /// <summary>
    /// Returns array of BidiLevel values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EMBED_LTR,
    		EMBED_RTL,
    	};
    }

    public static final int length = 2;
}

