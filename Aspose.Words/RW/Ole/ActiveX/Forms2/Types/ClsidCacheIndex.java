// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the type of embedded control.
/// </summary>
/*enum*/ final class ClsidCacheIndex
{
    private ClsidCacheIndex(){}
    
    public static final int FORM = 7;
    public static final int IMAGE = 12;
    public static final int FRAME = 14;
    public static final int MORPH_DATA = 15;
    public static final int SPIN_BUTTON = 16;
    public static final int COMMAND_BUTTON = 17;
    public static final int TAB_STRIP = 18;
    public static final int LABEL = 21;
    public static final int TEXT_BOX = 23;
    public static final int LIST_BOX = 24;
    public static final int COMBO_BOX = 25;
    public static final int CHECK_BOX = 26;
    public static final int OPTION_BUTTON = 27;
    public static final int TOGGLE_BUTTON = 28;
    public static final int SCROLL_BAR = 47;
    public static final int MULTI_PAGE = 57;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ClsidCacheIndex value.
    /// </summary>
    public static String getName(/*ClsidCacheIndex*/int clsidCacheIndex)
    {
    	switch (clsidCacheIndex)
    	{
    		case FORM: return "FORM";
    		case IMAGE: return "IMAGE";
    		case FRAME: return "FRAME";
    		case MORPH_DATA: return "MORPH_DATA";
    		case SPIN_BUTTON: return "SPIN_BUTTON";
    		case COMMAND_BUTTON: return "COMMAND_BUTTON";
    		case TAB_STRIP: return "TAB_STRIP";
    		case LABEL: return "LABEL";
    		case TEXT_BOX: return "TEXT_BOX";
    		case LIST_BOX: return "LIST_BOX";
    		case COMBO_BOX: return "COMBO_BOX";
    		case CHECK_BOX: return "CHECK_BOX";
    		case OPTION_BUTTON: return "OPTION_BUTTON";
    		case TOGGLE_BUTTON: return "TOGGLE_BUTTON";
    		case SCROLL_BAR: return "SCROLL_BAR";
    		case MULTI_PAGE: return "MULTI_PAGE";
    		default: return "Unknown ClsidCacheIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ClsidCacheIndex value.
    /// </summary>
    public static String toString(/*ClsidCacheIndex*/int clsidCacheIndex)
    {
    	switch (clsidCacheIndex)
    	{
    		case FORM: return "Form";
    		case IMAGE: return "Image";
    		case FRAME: return "Frame";
    		case MORPH_DATA: return "MorphData";
    		case SPIN_BUTTON: return "SpinButton";
    		case COMMAND_BUTTON: return "CommandButton";
    		case TAB_STRIP: return "TabStrip";
    		case LABEL: return "Label";
    		case TEXT_BOX: return "TextBox";
    		case LIST_BOX: return "ListBox";
    		case COMBO_BOX: return "ComboBox";
    		case CHECK_BOX: return "CheckBox";
    		case OPTION_BUTTON: return "OptionButton";
    		case TOGGLE_BUTTON: return "ToggleButton";
    		case SCROLL_BAR: return "ScrollBar";
    		case MULTI_PAGE: return "MultiPage";
    		default: return "Unknown ClsidCacheIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ClsidCacheIndex name.
    /// </summary>
    public static /*ClsidCacheIndex*/int fromName(String clsidCacheIndexName)
    {
    	if ("FORM".equals(clsidCacheIndexName)) return FORM;
    	if ("IMAGE".equals(clsidCacheIndexName)) return IMAGE;
    	if ("FRAME".equals(clsidCacheIndexName)) return FRAME;
    	if ("MORPH_DATA".equals(clsidCacheIndexName)) return MORPH_DATA;
    	if ("SPIN_BUTTON".equals(clsidCacheIndexName)) return SPIN_BUTTON;
    	if ("COMMAND_BUTTON".equals(clsidCacheIndexName)) return COMMAND_BUTTON;
    	if ("TAB_STRIP".equals(clsidCacheIndexName)) return TAB_STRIP;
    	if ("LABEL".equals(clsidCacheIndexName)) return LABEL;
    	if ("TEXT_BOX".equals(clsidCacheIndexName)) return TEXT_BOX;
    	if ("LIST_BOX".equals(clsidCacheIndexName)) return LIST_BOX;
    	if ("COMBO_BOX".equals(clsidCacheIndexName)) return COMBO_BOX;
    	if ("CHECK_BOX".equals(clsidCacheIndexName)) return CHECK_BOX;
    	if ("OPTION_BUTTON".equals(clsidCacheIndexName)) return OPTION_BUTTON;
    	if ("TOGGLE_BUTTON".equals(clsidCacheIndexName)) return TOGGLE_BUTTON;
    	if ("SCROLL_BAR".equals(clsidCacheIndexName)) return SCROLL_BAR;
    	if ("MULTI_PAGE".equals(clsidCacheIndexName)) return MULTI_PAGE;
    	throw new IllegalArgumentException("Unknown ClsidCacheIndex name.");
    }
    
    /// <summary>
    /// Returns array of ClsidCacheIndex values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FORM,
    		IMAGE,
    		FRAME,
    		MORPH_DATA,
    		SPIN_BUTTON,
    		COMMAND_BUTTON,
    		TAB_STRIP,
    		LABEL,
    		TEXT_BOX,
    		LIST_BOX,
    		COMBO_BOX,
    		CHECK_BOX,
    		OPTION_BUTTON,
    		TOGGLE_BUTTON,
    		SCROLL_BAR,
    		MULTI_PAGE,
    	};
    }

    public static final int length = 16;
}

