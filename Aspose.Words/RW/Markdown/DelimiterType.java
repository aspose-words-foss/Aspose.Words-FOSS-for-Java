// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible types of the <see cref="Delimiter"/> object.
/// </summary>
/*enum*/ final class DelimiterType
{
    private DelimiterType(){}
    
    public static final int ASTERISK = 0;
    public static final int UNDERSCORE = 1;
    public static final int STRIKE_THROUGH = 2;
    public static final int UNDERLINE = 3;
    public static final int INLINE_CODE = 4;
    public static final int AUTO_LINK_OPENING = 5;
    public static final int AUTO_LINK_CLOSING = 6;
    public static final int LINK_TEXT_OPENING = 7;
    public static final int LINK_TEXT_CLOSING = 8;
    public static final int LINK_DESTINATION_OPENING = 9;
    public static final int LINK_DESTINATION_CLOSING = 10;
    public static final int IMAGE_DESCRIPTION_OPENING = 11;
    public static final int FOOTNOTE_OPENING = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DelimiterType value.
    /// </summary>
    public static String getName(/*DelimiterType*/int delimiterType)
    {
    	switch (delimiterType)
    	{
    		case ASTERISK: return "ASTERISK";
    		case UNDERSCORE: return "UNDERSCORE";
    		case STRIKE_THROUGH: return "STRIKE_THROUGH";
    		case UNDERLINE: return "UNDERLINE";
    		case INLINE_CODE: return "INLINE_CODE";
    		case AUTO_LINK_OPENING: return "AUTO_LINK_OPENING";
    		case AUTO_LINK_CLOSING: return "AUTO_LINK_CLOSING";
    		case LINK_TEXT_OPENING: return "LINK_TEXT_OPENING";
    		case LINK_TEXT_CLOSING: return "LINK_TEXT_CLOSING";
    		case LINK_DESTINATION_OPENING: return "LINK_DESTINATION_OPENING";
    		case LINK_DESTINATION_CLOSING: return "LINK_DESTINATION_CLOSING";
    		case IMAGE_DESCRIPTION_OPENING: return "IMAGE_DESCRIPTION_OPENING";
    		case FOOTNOTE_OPENING: return "FOOTNOTE_OPENING";
    		default: return "Unknown DelimiterType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DelimiterType value.
    /// </summary>
    public static String toString(/*DelimiterType*/int delimiterType)
    {
    	switch (delimiterType)
    	{
    		case ASTERISK: return "Asterisk";
    		case UNDERSCORE: return "Underscore";
    		case STRIKE_THROUGH: return "StrikeThrough";
    		case UNDERLINE: return "Underline";
    		case INLINE_CODE: return "InlineCode";
    		case AUTO_LINK_OPENING: return "AutoLinkOpening";
    		case AUTO_LINK_CLOSING: return "AutoLinkClosing";
    		case LINK_TEXT_OPENING: return "LinkTextOpening";
    		case LINK_TEXT_CLOSING: return "LinkTextClosing";
    		case LINK_DESTINATION_OPENING: return "LinkDestinationOpening";
    		case LINK_DESTINATION_CLOSING: return "LinkDestinationClosing";
    		case IMAGE_DESCRIPTION_OPENING: return "ImageDescriptionOpening";
    		case FOOTNOTE_OPENING: return "FootnoteOpening";
    		default: return "Unknown DelimiterType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DelimiterType name.
    /// </summary>
    public static /*DelimiterType*/int fromName(String delimiterTypeName)
    {
    	if ("ASTERISK".equals(delimiterTypeName)) return ASTERISK;
    	if ("UNDERSCORE".equals(delimiterTypeName)) return UNDERSCORE;
    	if ("STRIKE_THROUGH".equals(delimiterTypeName)) return STRIKE_THROUGH;
    	if ("UNDERLINE".equals(delimiterTypeName)) return UNDERLINE;
    	if ("INLINE_CODE".equals(delimiterTypeName)) return INLINE_CODE;
    	if ("AUTO_LINK_OPENING".equals(delimiterTypeName)) return AUTO_LINK_OPENING;
    	if ("AUTO_LINK_CLOSING".equals(delimiterTypeName)) return AUTO_LINK_CLOSING;
    	if ("LINK_TEXT_OPENING".equals(delimiterTypeName)) return LINK_TEXT_OPENING;
    	if ("LINK_TEXT_CLOSING".equals(delimiterTypeName)) return LINK_TEXT_CLOSING;
    	if ("LINK_DESTINATION_OPENING".equals(delimiterTypeName)) return LINK_DESTINATION_OPENING;
    	if ("LINK_DESTINATION_CLOSING".equals(delimiterTypeName)) return LINK_DESTINATION_CLOSING;
    	if ("IMAGE_DESCRIPTION_OPENING".equals(delimiterTypeName)) return IMAGE_DESCRIPTION_OPENING;
    	if ("FOOTNOTE_OPENING".equals(delimiterTypeName)) return FOOTNOTE_OPENING;
    	throw new IllegalArgumentException("Unknown DelimiterType name.");
    }
    
    /// <summary>
    /// Returns array of DelimiterType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ASTERISK,
    		UNDERSCORE,
    		STRIKE_THROUGH,
    		UNDERLINE,
    		INLINE_CODE,
    		AUTO_LINK_OPENING,
    		AUTO_LINK_CLOSING,
    		LINK_TEXT_OPENING,
    		LINK_TEXT_CLOSING,
    		LINK_DESTINATION_OPENING,
    		LINK_DESTINATION_CLOSING,
    		IMAGE_DESCRIPTION_OPENING,
    		FOOTNOTE_OPENING,
    	};
    }

    public static final int length = 13;
}

