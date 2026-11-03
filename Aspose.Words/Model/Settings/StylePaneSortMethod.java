// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/03/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This element specifies a suggested sorting which should be applied to the list of document styles in this
/// application if the styles are displayed in a user interface.
/// </summary>
/*enum*/ final class StylePaneSortMethod
{
    private StylePaneSortMethod(){}
    
    /// <summary>
    /// Specifies that styles which are visible should be sorted by their names.
    /// </summary>
    public static final int NAME = 0;

    /// <summary>
    /// Specifies that styles which are visible should be sorted by their UI priority 
    /// using the uiPriority element. (iso29500, part1, §17.7.4.19).
    /// </summary>
    public static final int PRIORITY = 1;
    
    /// <summary>
    /// Specifies that styles which are visible should be sorted by the default sorting of the host application.
    /// </summary>
    public static final int DEFAULT = 2;
    
    /// <summary>
    /// Specifies that styles which are visible should be sorted by the font which they apply.
    /// </summary>
    public static final int FONT = 3;
    
    /// <summary>
    /// Specifies that styles which are visible should be sorted by the style on which they
    /// are based using the basedOn element (iso29500, part1, §17.7.4.3).
    /// </summary>
    public static final int BASED_ON = 4;
    
    /// <summary>
    /// Specifies that styles which are visible should be sorted by their style types 
    /// (i.e. character, linked, paragraph).
    /// </summary>
    public static final int STYLE_TYPE = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StylePaneSortMethod value.
    /// </summary>
    public static String getName(/*StylePaneSortMethod*/int stylePaneSortMethod)
    {
    	switch (stylePaneSortMethod)
    	{
    		case NAME: return "NAME";
    		case PRIORITY: return "PRIORITY";
    		case DEFAULT: return "DEFAULT";
    		case FONT: return "FONT";
    		case BASED_ON: return "BASED_ON";
    		case STYLE_TYPE: return "STYLE_TYPE";
    		default: return "Unknown StylePaneSortMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StylePaneSortMethod value.
    /// </summary>
    public static String toString(/*StylePaneSortMethod*/int stylePaneSortMethod)
    {
    	switch (stylePaneSortMethod)
    	{
    		case NAME: return "Name";
    		case PRIORITY: return "Priority";
    		case DEFAULT: return "Default";
    		case FONT: return "Font";
    		case BASED_ON: return "BasedOn";
    		case STYLE_TYPE: return "StyleType";
    		default: return "Unknown StylePaneSortMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by StylePaneSortMethod name.
    /// </summary>
    public static /*StylePaneSortMethod*/int fromName(String stylePaneSortMethodName)
    {
    	if ("NAME".equals(stylePaneSortMethodName)) return NAME;
    	if ("PRIORITY".equals(stylePaneSortMethodName)) return PRIORITY;
    	if ("DEFAULT".equals(stylePaneSortMethodName)) return DEFAULT;
    	if ("FONT".equals(stylePaneSortMethodName)) return FONT;
    	if ("BASED_ON".equals(stylePaneSortMethodName)) return BASED_ON;
    	if ("STYLE_TYPE".equals(stylePaneSortMethodName)) return STYLE_TYPE;
    	throw new IllegalArgumentException("Unknown StylePaneSortMethod name.");
    }
    
    /// <summary>
    /// Returns array of StylePaneSortMethod values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NAME,
    		PRIORITY,
    		DEFAULT,
    		FONT,
    		BASED_ON,
    		STYLE_TYPE,
    	};
    }

    public static final int length = 6;
}

