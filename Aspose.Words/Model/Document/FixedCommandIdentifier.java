// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// [MD-DOC] 2.9.75 Fci, a 13-bit unsigned integer that specifies a built-in command.
/// 
/// RK I have declared only fci values that are used by the 2.9.1 Acd structure. 
/// The complete list of fci values is too long so we should do it only if really becomes necessary.
/// </summary>
/*enum*/ final class FixedCommandIdentifier
{
    private FixedCommandIdentifier(){}
    
    /// <summary>
    /// RK Added this. Indicates the command is unused. Used to maintain the proper command index according to the original DOC structure.
    /// </summary>
    public static final int NONE = 0x0000;
    /// <summary>
    /// Applies the indicated style to the selected text.
    /// </summary>
    public static final int APPLY_STYLE_NAME = 0x0065;
    /// <summary>
    /// Applies the indicated font to the selected text.
    /// </summary>
    public static final int APPLY_FONT_NAME = 0x0164;
    /// <summary>
    /// Inserts the indicated AutoText entry in the document.
    /// </summary>
    public static final int APPLY_AUTO_TEXT_NAME = 0x0211;
    /// <summary>
    /// Changes the number of columns in the selected sections.
    /// </summary>
    public static final int COLUMNS = 0x0212;
    /// <summary>
    /// Sets the font character spacing of the selection to condensed.
    /// </summary>
    public static final int CONDENSED = 0x0213;
    /// <summary>
    /// Sets the font character spacing of the selection to expanded.
    /// </summary>
    public static final int EXPANDED = 0x0214;
    /// <summary>
    /// Changes the font size of the selection.
    /// </summary>
    public static final int FONT_SIZE = 0x0215;
    /// <summary>
    /// Lowers the selection below the base line.
    /// </summary>
    public static final int LOWERED = 0x0216;
    /// <summary>
    /// Raises the selection above the base line.
    /// </summary>
    public static final int RAISED = 0x0217;
    /// <summary>
    /// Opens a document.
    /// </summary>
    public static final int FILE_OPEN_FILE = 0x0218;
    /// <summary>
    /// Changes the background shading of paragraphs and table cells.
    /// </summary>
    public static final int SHADING = 0x0222;
    /// <summary>
    /// Changes the borders of paragraphs, table cells, and pictures.
    /// </summary>
    public static final int BORDERS = 0x0223;
    /// <summary>
    /// Changes the color of the selected text.
    /// </summary>
    public static final int COLOR = 0x0224;
    /// <summary>
    /// Inserts a special character.
    /// </summary>
    public static final int SYMBOL = 0x022B;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FixedCommandIdentifier value.
    /// </summary>
    public static String getName(/*FixedCommandIdentifier*/int fixedCommandIdentifier)
    {
    	switch (fixedCommandIdentifier)
    	{
    		case NONE: return "NONE";
    		case APPLY_STYLE_NAME: return "APPLY_STYLE_NAME";
    		case APPLY_FONT_NAME: return "APPLY_FONT_NAME";
    		case APPLY_AUTO_TEXT_NAME: return "APPLY_AUTO_TEXT_NAME";
    		case COLUMNS: return "COLUMNS";
    		case CONDENSED: return "CONDENSED";
    		case EXPANDED: return "EXPANDED";
    		case FONT_SIZE: return "FONT_SIZE";
    		case LOWERED: return "LOWERED";
    		case RAISED: return "RAISED";
    		case FILE_OPEN_FILE: return "FILE_OPEN_FILE";
    		case SHADING: return "SHADING";
    		case BORDERS: return "BORDERS";
    		case COLOR: return "COLOR";
    		case SYMBOL: return "SYMBOL";
    		default: return "Unknown FixedCommandIdentifier value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FixedCommandIdentifier value.
    /// </summary>
    public static String toString(/*FixedCommandIdentifier*/int fixedCommandIdentifier)
    {
    	switch (fixedCommandIdentifier)
    	{
    		case NONE: return "None";
    		case APPLY_STYLE_NAME: return "ApplyStyleName";
    		case APPLY_FONT_NAME: return "ApplyFontName";
    		case APPLY_AUTO_TEXT_NAME: return "ApplyAutoTextName";
    		case COLUMNS: return "Columns";
    		case CONDENSED: return "Condensed";
    		case EXPANDED: return "Expanded";
    		case FONT_SIZE: return "FontSize";
    		case LOWERED: return "Lowered";
    		case RAISED: return "Raised";
    		case FILE_OPEN_FILE: return "FileOpenFile";
    		case SHADING: return "Shading";
    		case BORDERS: return "Borders";
    		case COLOR: return "Color";
    		case SYMBOL: return "Symbol";
    		default: return "Unknown FixedCommandIdentifier value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FixedCommandIdentifier name.
    /// </summary>
    public static /*FixedCommandIdentifier*/int fromName(String fixedCommandIdentifierName)
    {
    	if ("NONE".equals(fixedCommandIdentifierName)) return NONE;
    	if ("APPLY_STYLE_NAME".equals(fixedCommandIdentifierName)) return APPLY_STYLE_NAME;
    	if ("APPLY_FONT_NAME".equals(fixedCommandIdentifierName)) return APPLY_FONT_NAME;
    	if ("APPLY_AUTO_TEXT_NAME".equals(fixedCommandIdentifierName)) return APPLY_AUTO_TEXT_NAME;
    	if ("COLUMNS".equals(fixedCommandIdentifierName)) return COLUMNS;
    	if ("CONDENSED".equals(fixedCommandIdentifierName)) return CONDENSED;
    	if ("EXPANDED".equals(fixedCommandIdentifierName)) return EXPANDED;
    	if ("FONT_SIZE".equals(fixedCommandIdentifierName)) return FONT_SIZE;
    	if ("LOWERED".equals(fixedCommandIdentifierName)) return LOWERED;
    	if ("RAISED".equals(fixedCommandIdentifierName)) return RAISED;
    	if ("FILE_OPEN_FILE".equals(fixedCommandIdentifierName)) return FILE_OPEN_FILE;
    	if ("SHADING".equals(fixedCommandIdentifierName)) return SHADING;
    	if ("BORDERS".equals(fixedCommandIdentifierName)) return BORDERS;
    	if ("COLOR".equals(fixedCommandIdentifierName)) return COLOR;
    	if ("SYMBOL".equals(fixedCommandIdentifierName)) return SYMBOL;
    	throw new IllegalArgumentException("Unknown FixedCommandIdentifier name.");
    }
    
    /// <summary>
    /// Returns array of FixedCommandIdentifier values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		APPLY_STYLE_NAME,
    		APPLY_FONT_NAME,
    		APPLY_AUTO_TEXT_NAME,
    		COLUMNS,
    		CONDENSED,
    		EXPANDED,
    		FONT_SIZE,
    		LOWERED,
    		RAISED,
    		FILE_OPEN_FILE,
    		SHADING,
    		BORDERS,
    		COLOR,
    		SYMBOL,
    	};
    }

    public static final int length = 15;
}

