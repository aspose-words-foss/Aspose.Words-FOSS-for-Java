// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates types of Forms 2.0 controls.
/// </summary>
public /*enum*/ final class Forms2OleControlType
{
    private Forms2OleControlType(){}
    
    /// <summary>
    /// A radio button control.
    /// </summary>
    public static final int OPTION_BUTTON = 0;

    /// <summary>
    /// A control that displays text.
    /// </summary>
    public static final int LABEL = 1;

    /// <summary>
    /// A control that allows the user to enter text.
    /// </summary>
    public static final int TEXTBOX = 2;

    /// <summary>
    /// A control that allows the user to select or deselect an option.
    /// </summary>
    public static final int CHECK_BOX = 3;

    /// <summary>
    /// A control that allows the user to toggle between two states.
    /// </summary>
    public static final int TOGGLE_BUTTON = 4;

    /// <summary>
    /// A control that allows the user to increase or decrease a value.
    /// </summary>
    public static final int SPIN_BUTTON = 5;

    /// <summary>
    /// A control that allows the user to select an item from a list.
    /// </summary>
    public static final int COMBO_BOX = 6;

    /// <summary>
    /// A control that groups other controls.
    /// </summary>
    public static final int FRAME = 7;

    /// <summary>
    /// A control that displays multiple pages of content.
    /// </summary>
    public static final int MULTI_PAGE = 8;

    /// <summary>
    /// A control that allows the user to switch between multiple pages of content.
    /// </summary>
    public static final int TAB_STRIP = 9;

    /// <summary>
    /// A button that triggers an action when clicked.
    /// </summary>
    public static final int COMMAND_BUTTON = 10;

    /// <summary>
    /// A control that displays an image.
    /// </summary>
    public static final int IMAGE = 11;

    /// <summary>
    /// A control that allows the user to scroll through content.
    /// </summary>
    public static final int SCROLL_BAR = 12;

    /// <summary>
    /// A container for other controls.
    /// </summary>
    public static final int FORM = 13;

    /// <summary>
    /// A control that displays a list of items.
    /// </summary>
    public static final int LIST_BOX = 14;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Forms2OleControlType value.
    /// </summary>
    public static String getName(/*Forms2OleControlType*/int forms2OleControlType)
    {
    	switch (forms2OleControlType)
    	{
    		case OPTION_BUTTON: return "OPTION_BUTTON";
    		case LABEL: return "LABEL";
    		case TEXTBOX: return "TEXTBOX";
    		case CHECK_BOX: return "CHECK_BOX";
    		case TOGGLE_BUTTON: return "TOGGLE_BUTTON";
    		case SPIN_BUTTON: return "SPIN_BUTTON";
    		case COMBO_BOX: return "COMBO_BOX";
    		case FRAME: return "FRAME";
    		case MULTI_PAGE: return "MULTI_PAGE";
    		case TAB_STRIP: return "TAB_STRIP";
    		case COMMAND_BUTTON: return "COMMAND_BUTTON";
    		case IMAGE: return "IMAGE";
    		case SCROLL_BAR: return "SCROLL_BAR";
    		case FORM: return "FORM";
    		case LIST_BOX: return "LIST_BOX";
    		default: return "Unknown Forms2OleControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Forms2OleControlType value.
    /// </summary>
    public static String toString(/*Forms2OleControlType*/int forms2OleControlType)
    {
    	switch (forms2OleControlType)
    	{
    		case OPTION_BUTTON: return "OptionButton";
    		case LABEL: return "Label";
    		case TEXTBOX: return "Textbox";
    		case CHECK_BOX: return "CheckBox";
    		case TOGGLE_BUTTON: return "ToggleButton";
    		case SPIN_BUTTON: return "SpinButton";
    		case COMBO_BOX: return "ComboBox";
    		case FRAME: return "Frame";
    		case MULTI_PAGE: return "MultiPage";
    		case TAB_STRIP: return "TabStrip";
    		case COMMAND_BUTTON: return "CommandButton";
    		case IMAGE: return "Image";
    		case SCROLL_BAR: return "ScrollBar";
    		case FORM: return "Form";
    		case LIST_BOX: return "ListBox";
    		default: return "Unknown Forms2OleControlType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Forms2OleControlType name.
    /// </summary>
    public static /*Forms2OleControlType*/int fromName(String forms2OleControlTypeName)
    {
    	if ("OPTION_BUTTON".equals(forms2OleControlTypeName)) return OPTION_BUTTON;
    	if ("LABEL".equals(forms2OleControlTypeName)) return LABEL;
    	if ("TEXTBOX".equals(forms2OleControlTypeName)) return TEXTBOX;
    	if ("CHECK_BOX".equals(forms2OleControlTypeName)) return CHECK_BOX;
    	if ("TOGGLE_BUTTON".equals(forms2OleControlTypeName)) return TOGGLE_BUTTON;
    	if ("SPIN_BUTTON".equals(forms2OleControlTypeName)) return SPIN_BUTTON;
    	if ("COMBO_BOX".equals(forms2OleControlTypeName)) return COMBO_BOX;
    	if ("FRAME".equals(forms2OleControlTypeName)) return FRAME;
    	if ("MULTI_PAGE".equals(forms2OleControlTypeName)) return MULTI_PAGE;
    	if ("TAB_STRIP".equals(forms2OleControlTypeName)) return TAB_STRIP;
    	if ("COMMAND_BUTTON".equals(forms2OleControlTypeName)) return COMMAND_BUTTON;
    	if ("IMAGE".equals(forms2OleControlTypeName)) return IMAGE;
    	if ("SCROLL_BAR".equals(forms2OleControlTypeName)) return SCROLL_BAR;
    	if ("FORM".equals(forms2OleControlTypeName)) return FORM;
    	if ("LIST_BOX".equals(forms2OleControlTypeName)) return LIST_BOX;
    	throw new IllegalArgumentException("Unknown Forms2OleControlType name.");
    }
    
    /// <summary>
    /// Returns array of Forms2OleControlType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OPTION_BUTTON,
    		LABEL,
    		TEXTBOX,
    		CHECK_BOX,
    		TOGGLE_BUTTON,
    		SPIN_BUTTON,
    		COMBO_BOX,
    		FRAME,
    		MULTI_PAGE,
    		TAB_STRIP,
    		COMMAND_BUTTON,
    		IMAGE,
    		SCROLL_BAR,
    		FORM,
    		LIST_BOX,
    	};
    }

    public static final int length = 15;
}

