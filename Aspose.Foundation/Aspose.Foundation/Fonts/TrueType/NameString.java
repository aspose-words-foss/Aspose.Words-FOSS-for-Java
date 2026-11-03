// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a string from the true type font Naming Table.
/// </summary>
/*enum*/ final class NameString
{
    private NameString(){}
    
    public static final int COPYRIGHT_NOTICE = 0;
    /// <summary>
    /// Up to four fonts can share the Font Family name, forming a font style linking group 
    /// (regular, italic, bold, bold italic - as defined by OS/2.fsSelection bit settings).
    /// </summary>
    public static final int FAMILY_NAME = 1;
    /// <summary>
    /// The Font Subfamily name distiguishes the font in a group with the same 
    /// Font Family name (name ID 1). This is assumed to address style (italic, oblique) 
    /// and weight (light, bold, black, etc.). A font with no particular differences in 
    /// weight or style (e.g. medium weight, not italic and fsSelection bit 6 set) should 
    /// have the string "Regular" stored in this position.
    /// </summary>
    public static final int SUB_FAMILY_NAME = 2;
    /// <summary>
    /// Unique font identifier
    /// </summary>
    public static final int FONT_ID = 3;
    /// <summary>
    /// Full font name; this should be a combination of strings 1 and 2. 
    /// Exception: if the font is "Regular" as indicated in string 2, 
    /// then use only the family name contained in string 1. 
    /// An exception to the above definition of Full font name is for Microsoft platform 
    /// strings for CFF OpenType fonts: in this case, the Full font name string must be i
    /// dentical to the PostScript FontName in the CFF Name INDEX.
    /// </summary>
    public static final int FULL_FONT_NAME = 4;
    /// <summary>
    /// Version string. Should begin with the syntax 'Version number.number' 
    /// (upper case, lower case, or mixed, with a space between "Version" and the number). 
    /// </summary>
    public static final int VERSION = 5;
    /// <summary>
    /// Postscript name for the font; Name ID 6 specifies a string which is used to invoke a 
    /// PostScript language font that corresponds to this OpenType font. 
    /// If no name ID 6 is present, then there is no defined method for invoking this font 
    /// on a PostScript interpreter.
    /// </summary>
    public static final int POST_SCIPT_NAME = 6;
    /// <summary>
    /// this is used to save any trademark notice/information for this font. 
    /// Such information should be based on legal advice. This is distinctly separate from the copyright. 
    /// </summary>
    public static final int TRADEMARK = 7;
    public static final int MANUFACTURER = 8;
    public static final int DESIGNED = 9;
    public static final int DESCRIPTION = 10;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NameString value.
    /// </summary>
    public static String getName(/*NameString*/int nameString)
    {
    	switch (nameString)
    	{
    		case COPYRIGHT_NOTICE: return "COPYRIGHT_NOTICE";
    		case FAMILY_NAME: return "FAMILY_NAME";
    		case SUB_FAMILY_NAME: return "SUB_FAMILY_NAME";
    		case FONT_ID: return "FONT_ID";
    		case FULL_FONT_NAME: return "FULL_FONT_NAME";
    		case VERSION: return "VERSION";
    		case POST_SCIPT_NAME: return "POST_SCIPT_NAME";
    		case TRADEMARK: return "TRADEMARK";
    		case MANUFACTURER: return "MANUFACTURER";
    		case DESIGNED: return "DESIGNED";
    		case DESCRIPTION: return "DESCRIPTION";
    		default: return "Unknown NameString value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NameString value.
    /// </summary>
    public static String toString(/*NameString*/int nameString)
    {
    	switch (nameString)
    	{
    		case COPYRIGHT_NOTICE: return "CopyrightNotice";
    		case FAMILY_NAME: return "FamilyName";
    		case SUB_FAMILY_NAME: return "SubFamilyName";
    		case FONT_ID: return "FontId";
    		case FULL_FONT_NAME: return "FullFontName";
    		case VERSION: return "Version";
    		case POST_SCIPT_NAME: return "PostSciptName";
    		case TRADEMARK: return "Trademark";
    		case MANUFACTURER: return "Manufacturer";
    		case DESIGNED: return "Designed";
    		case DESCRIPTION: return "Description";
    		default: return "Unknown NameString value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NameString name.
    /// </summary>
    public static /*NameString*/int fromName(String nameStringName)
    {
    	if ("COPYRIGHT_NOTICE".equals(nameStringName)) return COPYRIGHT_NOTICE;
    	if ("FAMILY_NAME".equals(nameStringName)) return FAMILY_NAME;
    	if ("SUB_FAMILY_NAME".equals(nameStringName)) return SUB_FAMILY_NAME;
    	if ("FONT_ID".equals(nameStringName)) return FONT_ID;
    	if ("FULL_FONT_NAME".equals(nameStringName)) return FULL_FONT_NAME;
    	if ("VERSION".equals(nameStringName)) return VERSION;
    	if ("POST_SCIPT_NAME".equals(nameStringName)) return POST_SCIPT_NAME;
    	if ("TRADEMARK".equals(nameStringName)) return TRADEMARK;
    	if ("MANUFACTURER".equals(nameStringName)) return MANUFACTURER;
    	if ("DESIGNED".equals(nameStringName)) return DESIGNED;
    	if ("DESCRIPTION".equals(nameStringName)) return DESCRIPTION;
    	throw new IllegalArgumentException("Unknown NameString name.");
    }
    
    /// <summary>
    /// Returns array of NameString values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COPYRIGHT_NOTICE,
    		FAMILY_NAME,
    		SUB_FAMILY_NAME,
    		FONT_ID,
    		FULL_FONT_NAME,
    		VERSION,
    		POST_SCIPT_NAME,
    		TRADEMARK,
    		MANUFACTURER,
    		DESIGNED,
    		DESCRIPTION,
    	};
    }

    public static final int length = 11;
}

