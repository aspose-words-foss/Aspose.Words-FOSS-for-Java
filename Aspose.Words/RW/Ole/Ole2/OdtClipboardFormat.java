// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class OdtClipboardFormat
{
    private OdtClipboardFormat(){}
    
    public static final int NONE = 0x0000;
    public static final int RTF = 0x0001;
    public static final int TEXT = 0x0002;
    public static final int METAFILE = 0x0003;
    public static final int BITMAP = 0x0004;
    public static final int DIB = 0x0005;
    public static final int HTML = 0x000a;
    public static final int UNICODE_TEXT = 0x0014;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OdtClipboardFormat value.
    /// </summary>
    public static String getName(/*OdtClipboardFormat*/int odtClipboardFormat)
    {
    	switch (odtClipboardFormat)
    	{
    		case NONE: return "NONE";
    		case RTF: return "RTF";
    		case TEXT: return "TEXT";
    		case METAFILE: return "METAFILE";
    		case BITMAP: return "BITMAP";
    		case DIB: return "DIB";
    		case HTML: return "HTML";
    		case UNICODE_TEXT: return "UNICODE_TEXT";
    		default: return "Unknown OdtClipboardFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OdtClipboardFormat value.
    /// </summary>
    public static String toString(/*OdtClipboardFormat*/int odtClipboardFormat)
    {
    	switch (odtClipboardFormat)
    	{
    		case NONE: return "None";
    		case RTF: return "Rtf";
    		case TEXT: return "Text";
    		case METAFILE: return "Metafile";
    		case BITMAP: return "Bitmap";
    		case DIB: return "Dib";
    		case HTML: return "Html";
    		case UNICODE_TEXT: return "UnicodeText";
    		default: return "Unknown OdtClipboardFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OdtClipboardFormat name.
    /// </summary>
    public static /*OdtClipboardFormat*/int fromName(String odtClipboardFormatName)
    {
    	if ("NONE".equals(odtClipboardFormatName)) return NONE;
    	if ("RTF".equals(odtClipboardFormatName)) return RTF;
    	if ("TEXT".equals(odtClipboardFormatName)) return TEXT;
    	if ("METAFILE".equals(odtClipboardFormatName)) return METAFILE;
    	if ("BITMAP".equals(odtClipboardFormatName)) return BITMAP;
    	if ("DIB".equals(odtClipboardFormatName)) return DIB;
    	if ("HTML".equals(odtClipboardFormatName)) return HTML;
    	if ("UNICODE_TEXT".equals(odtClipboardFormatName)) return UNICODE_TEXT;
    	throw new IllegalArgumentException("Unknown OdtClipboardFormat name.");
    }
    
    /// <summary>
    /// Returns array of OdtClipboardFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		RTF,
    		TEXT,
    		METAFILE,
    		BITMAP,
    		DIB,
    		HTML,
    		UNICODE_TEXT,
    	};
    }

    public static final int length = 8;
}

