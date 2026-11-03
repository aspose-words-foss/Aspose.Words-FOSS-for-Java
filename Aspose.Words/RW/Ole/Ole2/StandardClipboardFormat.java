// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2012 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines Standard Clipboard formats. See [MS-OLEDS] 2.1.1 Clipboard Formats.
/// </summary>
/*enum*/ final class StandardClipboardFormat
{
    private StandardClipboardFormat(){}
    
    public static final int NONE = 0x0001;
    /// <summary>
    /// Bitmap16 Object structure.
    /// </summary>
    public static final int BITMAP = 0x0002;
    /// <summary>
    /// Windows Metafile.
    /// </summary>
    public static final int METAFILE_PICT = 0x0003;
    /// <summary>
    /// DeviceIndependentBitmap Object structure.
    /// </summary>
    public static final int DIB = 0x0008;
    /// <summary>
    /// Enhanced Metafile.
    /// </summary>
    public static final int ENH_METAFILE = 0x000e;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StandardClipboardFormat value.
    /// </summary>
    public static String getName(/*StandardClipboardFormat*/int standardClipboardFormat)
    {
    	switch (standardClipboardFormat)
    	{
    		case NONE: return "NONE";
    		case BITMAP: return "BITMAP";
    		case METAFILE_PICT: return "METAFILE_PICT";
    		case DIB: return "DIB";
    		case ENH_METAFILE: return "ENH_METAFILE";
    		default: return "Unknown StandardClipboardFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StandardClipboardFormat value.
    /// </summary>
    public static String toString(/*StandardClipboardFormat*/int standardClipboardFormat)
    {
    	switch (standardClipboardFormat)
    	{
    		case NONE: return "None";
    		case BITMAP: return "Bitmap";
    		case METAFILE_PICT: return "MetafilePict";
    		case DIB: return "Dib";
    		case ENH_METAFILE: return "EnhMetafile";
    		default: return "Unknown StandardClipboardFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by StandardClipboardFormat name.
    /// </summary>
    public static /*StandardClipboardFormat*/int fromName(String standardClipboardFormatName)
    {
    	if ("NONE".equals(standardClipboardFormatName)) return NONE;
    	if ("BITMAP".equals(standardClipboardFormatName)) return BITMAP;
    	if ("METAFILE_PICT".equals(standardClipboardFormatName)) return METAFILE_PICT;
    	if ("DIB".equals(standardClipboardFormatName)) return DIB;
    	if ("ENH_METAFILE".equals(standardClipboardFormatName)) return ENH_METAFILE;
    	throw new IllegalArgumentException("Unknown StandardClipboardFormat name.");
    }
    
    /// <summary>
    /// Returns array of StandardClipboardFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BITMAP,
    		METAFILE_PICT,
    		DIB,
    		ENH_METAFILE,
    	};
    }

    public static final int length = 5;
}

