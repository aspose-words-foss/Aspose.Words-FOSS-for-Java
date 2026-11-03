// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of the OLE link.
/// </summary>
/*enum*/ final class OleLinkType
{
    private OleLinkType(){}
    
    /// <summary>
    /// Inserts the linked object as a picture. Default.
    /// </summary>
    public static final int PICTURE = 0;
    /// <summary>
    /// Inserts the linked object as a bitmap.
    /// </summary>
    public static final int BITMAP = 1;
    /// <summary>
    /// Inserts the linked object as HTML text.
    /// </summary>
    public static final int HTML = 2;
    /// <summary>
    /// Inserts the linked object in RTF format.
    /// </summary>
    public static final int RTF = 3;
    /// <summary>
    /// Inserts the linked object in text-only format.
    /// </summary>
    public static final int TEXT = 4;
    /// <summary>
    /// Inserts the linked object as Unicode text.
    /// </summary>
    public static final int UNICODE = 5;
    //JAVA: moved here to exclude illegal forward reference.
    /// <summary>
    /// Default value is <see cref="Picture"/>.
    /// </summary>
    public static final int DEFAULT = PICTURE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OleLinkType value.
    /// </summary>
    public static String getName(/*OleLinkType*/int oleLinkType)
    {
    	switch (oleLinkType)
    	{
    		case PICTURE:
    		/*case DEFAULT:*/
    			return "PICTURE" + " | " + "DEFAULT";
    		case BITMAP: return "BITMAP";
    		case HTML: return "HTML";
    		case RTF: return "RTF";
    		case TEXT: return "TEXT";
    		case UNICODE: return "UNICODE";
    		default: return "Unknown OleLinkType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OleLinkType value.
    /// </summary>
    public static String toString(/*OleLinkType*/int oleLinkType)
    {
    	switch (oleLinkType)
    	{
    		case PICTURE:
    		/*case DEFAULT:*/
    			return "Picture" + " | " + "Default";
    		case BITMAP: return "Bitmap";
    		case HTML: return "Html";
    		case RTF: return "Rtf";
    		case TEXT: return "Text";
    		case UNICODE: return "Unicode";
    		default: return "Unknown OleLinkType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OleLinkType name.
    /// </summary>
    public static /*OleLinkType*/int fromName(String oleLinkTypeName)
    {
    	if ("PICTURE".equals(oleLinkTypeName)) return PICTURE;
    	if ("BITMAP".equals(oleLinkTypeName)) return BITMAP;
    	if ("HTML".equals(oleLinkTypeName)) return HTML;
    	if ("RTF".equals(oleLinkTypeName)) return RTF;
    	if ("TEXT".equals(oleLinkTypeName)) return TEXT;
    	if ("UNICODE".equals(oleLinkTypeName)) return UNICODE;
    	if ("DEFAULT".equals(oleLinkTypeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown OleLinkType name.");
    }
    
    /// <summary>
    /// Returns array of OleLinkType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PICTURE,
    		BITMAP,
    		HTML,
    		RTF,
    		TEXT,
    		UNICODE,
    		DEFAULT,
    	};
    }

    public static final int length = 7;
}

