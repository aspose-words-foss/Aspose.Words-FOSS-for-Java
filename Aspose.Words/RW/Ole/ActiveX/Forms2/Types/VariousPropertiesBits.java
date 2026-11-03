// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Implements [MS-OFORMS] 2.5.96.1 VariousPropertiesBitfield.
/// </summary>
/*enum*/ final class VariousPropertiesBits /*: uint*/
{
    private VariousPropertiesBits(){}
    
    public static final /*uint*/int NONE = 0x00000000;
    public static final /*uint*/int RESERVED_1 = 0x00000001;
    public static final /*uint*/int ENABLED = 0x00000002;
    public static final /*uint*/int LOCKED = 0x00000004;
    public static final /*uint*/int BACK_STYLE = 0x00000008;
    public static final /*uint*/int RESERVED_2 = 0x00000010;
    public static final /*uint*/int UNUSED_BITS_11 = 0x00000020;
    public static final /*uint*/int UNUSED_BITS_12 = 0x00000040;
    public static final /*uint*/int UNUSED_BITS_13 = 0x00000080;
    public static final /*uint*/int UNUSED_BITS_14 = 0x00000100;
    public static final /*uint*/int UNUSED_BITS_15 = 0x00000200;
    public static final /*uint*/int COLUMN_HEADS = 0x00000400;
    public static final /*uint*/int INTEGRAL_HEIGHT = 0x00000800;
    public static final /*uint*/int MATCH_REQUIRED = 0x00001000;
    public static final /*uint*/int ALIGNMENT = 0x00002000;
    public static final /*uint*/int EDITABLE = 0x00004000;
    public static final /*uint*/int IME_MODE = 0x00078000;
    public static final /*uint*/int DRAG_BEHAVIOR = 0x00080000;
    public static final /*uint*/int ENTER_KEY_BEHAVIOR = 0x00100000;
    public static final /*uint*/int ENTER_FIELD_BEHAVIOR = 0x00200000;
    public static final /*uint*/int TAB_KEY_BEHAVIOR = 0x00400000;
    public static final /*uint*/int WORD_WRAP = 0x00800000;
    public static final /*uint*/int UNUSED_BITS_2 = 0x01000000;
    public static final /*uint*/int BORDERS_SUPPRESS = 0x02000000;
    public static final /*uint*/int SELECTION_MARGIN = 0x04000000;
    public static final /*uint*/int AUTO_WORD_SELECT = 0x08000000;
    public static final /*uint*/int AUTO_SIZE = 0x10000000;
    public static final /*uint*/int HIDE_SELECTION = 0x20000000;
    public static final /*uint*/int AUTO_TAB = 0x40000000;
    public static final /*uint*/int MULTI_LINE = (int) 0x80000000L;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer VariousPropertiesBits value.
    /// </summary>
    public static String getName(/*VariousPropertiesBits*//*uint*/int variousPropertiesBits)
    {
    	if (NONE == variousPropertiesBits) return "NONE";
    	if (RESERVED_1 == variousPropertiesBits) return "RESERVED_1";
    	if (ENABLED == variousPropertiesBits) return "ENABLED";
    	if (LOCKED == variousPropertiesBits) return "LOCKED";
    	if (BACK_STYLE == variousPropertiesBits) return "BACK_STYLE";
    	if (RESERVED_2 == variousPropertiesBits) return "RESERVED_2";
    	if (UNUSED_BITS_11 == variousPropertiesBits) return "UNUSED_BITS_11";
    	if (UNUSED_BITS_12 == variousPropertiesBits) return "UNUSED_BITS_12";
    	if (UNUSED_BITS_13 == variousPropertiesBits) return "UNUSED_BITS_13";
    	if (UNUSED_BITS_14 == variousPropertiesBits) return "UNUSED_BITS_14";
    	if (UNUSED_BITS_15 == variousPropertiesBits) return "UNUSED_BITS_15";
    	if (COLUMN_HEADS == variousPropertiesBits) return "COLUMN_HEADS";
    	if (INTEGRAL_HEIGHT == variousPropertiesBits) return "INTEGRAL_HEIGHT";
    	if (MATCH_REQUIRED == variousPropertiesBits) return "MATCH_REQUIRED";
    	if (ALIGNMENT == variousPropertiesBits) return "ALIGNMENT";
    	if (EDITABLE == variousPropertiesBits) return "EDITABLE";
    	if (IME_MODE == variousPropertiesBits) return "IME_MODE";
    	if (DRAG_BEHAVIOR == variousPropertiesBits) return "DRAG_BEHAVIOR";
    	if (ENTER_KEY_BEHAVIOR == variousPropertiesBits) return "ENTER_KEY_BEHAVIOR";
    	if (ENTER_FIELD_BEHAVIOR == variousPropertiesBits) return "ENTER_FIELD_BEHAVIOR";
    	if (TAB_KEY_BEHAVIOR == variousPropertiesBits) return "TAB_KEY_BEHAVIOR";
    	if (WORD_WRAP == variousPropertiesBits) return "WORD_WRAP";
    	if (UNUSED_BITS_2 == variousPropertiesBits) return "UNUSED_BITS_2";
    	if (BORDERS_SUPPRESS == variousPropertiesBits) return "BORDERS_SUPPRESS";
    	if (SELECTION_MARGIN == variousPropertiesBits) return "SELECTION_MARGIN";
    	if (AUTO_WORD_SELECT == variousPropertiesBits) return "AUTO_WORD_SELECT";
    	if (AUTO_SIZE == variousPropertiesBits) return "AUTO_SIZE";
    	if (HIDE_SELECTION == variousPropertiesBits) return "HIDE_SELECTION";
    	if (AUTO_TAB == variousPropertiesBits) return "AUTO_TAB";
    	if (MULTI_LINE == variousPropertiesBits) return "MULTI_LINE";
    	return "Unknown VariousPropertiesBits value.";
    }
    
    /// <summary>
    /// Returns string representation of integer VariousPropertiesBits value.
    /// </summary>
    public static Set<String> getNames(/*VariousPropertiesBits*//*uint*/int variousPropertiesBits)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & variousPropertiesBits) == NONE) names.add("NONE");
    	if ((RESERVED_1 & variousPropertiesBits) == RESERVED_1) names.add("RESERVED_1");
    	if ((ENABLED & variousPropertiesBits) == ENABLED) names.add("ENABLED");
    	if ((LOCKED & variousPropertiesBits) == LOCKED) names.add("LOCKED");
    	if ((BACK_STYLE & variousPropertiesBits) == BACK_STYLE) names.add("BACK_STYLE");
    	if ((RESERVED_2 & variousPropertiesBits) == RESERVED_2) names.add("RESERVED_2");
    	if ((UNUSED_BITS_11 & variousPropertiesBits) == UNUSED_BITS_11) names.add("UNUSED_BITS_11");
    	if ((UNUSED_BITS_12 & variousPropertiesBits) == UNUSED_BITS_12) names.add("UNUSED_BITS_12");
    	if ((UNUSED_BITS_13 & variousPropertiesBits) == UNUSED_BITS_13) names.add("UNUSED_BITS_13");
    	if ((UNUSED_BITS_14 & variousPropertiesBits) == UNUSED_BITS_14) names.add("UNUSED_BITS_14");
    	if ((UNUSED_BITS_15 & variousPropertiesBits) == UNUSED_BITS_15) names.add("UNUSED_BITS_15");
    	if ((COLUMN_HEADS & variousPropertiesBits) == COLUMN_HEADS) names.add("COLUMN_HEADS");
    	if ((INTEGRAL_HEIGHT & variousPropertiesBits) == INTEGRAL_HEIGHT) names.add("INTEGRAL_HEIGHT");
    	if ((MATCH_REQUIRED & variousPropertiesBits) == MATCH_REQUIRED) names.add("MATCH_REQUIRED");
    	if ((ALIGNMENT & variousPropertiesBits) == ALIGNMENT) names.add("ALIGNMENT");
    	if ((EDITABLE & variousPropertiesBits) == EDITABLE) names.add("EDITABLE");
    	if ((IME_MODE & variousPropertiesBits) == IME_MODE) names.add("IME_MODE");
    	if ((DRAG_BEHAVIOR & variousPropertiesBits) == DRAG_BEHAVIOR) names.add("DRAG_BEHAVIOR");
    	if ((ENTER_KEY_BEHAVIOR & variousPropertiesBits) == ENTER_KEY_BEHAVIOR) names.add("ENTER_KEY_BEHAVIOR");
    	if ((ENTER_FIELD_BEHAVIOR & variousPropertiesBits) == ENTER_FIELD_BEHAVIOR) names.add("ENTER_FIELD_BEHAVIOR");
    	if ((TAB_KEY_BEHAVIOR & variousPropertiesBits) == TAB_KEY_BEHAVIOR) names.add("TAB_KEY_BEHAVIOR");
    	if ((WORD_WRAP & variousPropertiesBits) == WORD_WRAP) names.add("WORD_WRAP");
    	if ((UNUSED_BITS_2 & variousPropertiesBits) == UNUSED_BITS_2) names.add("UNUSED_BITS_2");
    	if ((BORDERS_SUPPRESS & variousPropertiesBits) == BORDERS_SUPPRESS) names.add("BORDERS_SUPPRESS");
    	if ((SELECTION_MARGIN & variousPropertiesBits) == SELECTION_MARGIN) names.add("SELECTION_MARGIN");
    	if ((AUTO_WORD_SELECT & variousPropertiesBits) == AUTO_WORD_SELECT) names.add("AUTO_WORD_SELECT");
    	if ((AUTO_SIZE & variousPropertiesBits) == AUTO_SIZE) names.add("AUTO_SIZE");
    	if ((HIDE_SELECTION & variousPropertiesBits) == HIDE_SELECTION) names.add("HIDE_SELECTION");
    	if ((AUTO_TAB & variousPropertiesBits) == AUTO_TAB) names.add("AUTO_TAB");
    	if ((MULTI_LINE & variousPropertiesBits) == MULTI_LINE) names.add("MULTI_LINE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VariousPropertiesBits value.
    /// </summary>
    public static String toString(/*VariousPropertiesBits*//*uint*/int variousPropertiesBits)
    {
    	if (NONE == variousPropertiesBits) return "None";
    	if (RESERVED_1 == variousPropertiesBits) return "Reserved1";
    	if (ENABLED == variousPropertiesBits) return "Enabled";
    	if (LOCKED == variousPropertiesBits) return "Locked";
    	if (BACK_STYLE == variousPropertiesBits) return "BackStyle";
    	if (RESERVED_2 == variousPropertiesBits) return "Reserved2";
    	if (UNUSED_BITS_11 == variousPropertiesBits) return "UnusedBits11";
    	if (UNUSED_BITS_12 == variousPropertiesBits) return "UnusedBits12";
    	if (UNUSED_BITS_13 == variousPropertiesBits) return "UnusedBits13";
    	if (UNUSED_BITS_14 == variousPropertiesBits) return "UnusedBits14";
    	if (UNUSED_BITS_15 == variousPropertiesBits) return "UnusedBits15";
    	if (COLUMN_HEADS == variousPropertiesBits) return "ColumnHeads";
    	if (INTEGRAL_HEIGHT == variousPropertiesBits) return "IntegralHeight";
    	if (MATCH_REQUIRED == variousPropertiesBits) return "MatchRequired";
    	if (ALIGNMENT == variousPropertiesBits) return "Alignment";
    	if (EDITABLE == variousPropertiesBits) return "Editable";
    	if (IME_MODE == variousPropertiesBits) return "IMEMode";
    	if (DRAG_BEHAVIOR == variousPropertiesBits) return "DragBehavior";
    	if (ENTER_KEY_BEHAVIOR == variousPropertiesBits) return "EnterKeyBehavior";
    	if (ENTER_FIELD_BEHAVIOR == variousPropertiesBits) return "EnterFieldBehavior";
    	if (TAB_KEY_BEHAVIOR == variousPropertiesBits) return "TabKeyBehavior";
    	if (WORD_WRAP == variousPropertiesBits) return "WordWrap";
    	if (UNUSED_BITS_2 == variousPropertiesBits) return "UnusedBits2";
    	if (BORDERS_SUPPRESS == variousPropertiesBits) return "BordersSuppress";
    	if (SELECTION_MARGIN == variousPropertiesBits) return "SelectionMargin";
    	if (AUTO_WORD_SELECT == variousPropertiesBits) return "AutoWordSelect";
    	if (AUTO_SIZE == variousPropertiesBits) return "AutoSize";
    	if (HIDE_SELECTION == variousPropertiesBits) return "HideSelection";
    	if (AUTO_TAB == variousPropertiesBits) return "AutoTab";
    	if (MULTI_LINE == variousPropertiesBits) return "MultiLine";
    	return toStringSet(variousPropertiesBits);
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer VariousPropertiesBits value.
    /// </summary>
    public static String toStringSet(/*uint*/int attr)
    {
    	String result = "";
    	String separator = "";
    	for (/*uint*/int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by VariousPropertiesBits name.
    /// </summary>
    public static /*VariousPropertiesBits*//*uint*/int fromName(String variousPropertiesBitsName)
    {
    	if ("NONE".equals(variousPropertiesBitsName)) return NONE;
    	if ("RESERVED_1".equals(variousPropertiesBitsName)) return RESERVED_1;
    	if ("ENABLED".equals(variousPropertiesBitsName)) return ENABLED;
    	if ("LOCKED".equals(variousPropertiesBitsName)) return LOCKED;
    	if ("BACK_STYLE".equals(variousPropertiesBitsName)) return BACK_STYLE;
    	if ("RESERVED_2".equals(variousPropertiesBitsName)) return RESERVED_2;
    	if ("UNUSED_BITS_11".equals(variousPropertiesBitsName)) return UNUSED_BITS_11;
    	if ("UNUSED_BITS_12".equals(variousPropertiesBitsName)) return UNUSED_BITS_12;
    	if ("UNUSED_BITS_13".equals(variousPropertiesBitsName)) return UNUSED_BITS_13;
    	if ("UNUSED_BITS_14".equals(variousPropertiesBitsName)) return UNUSED_BITS_14;
    	if ("UNUSED_BITS_15".equals(variousPropertiesBitsName)) return UNUSED_BITS_15;
    	if ("COLUMN_HEADS".equals(variousPropertiesBitsName)) return COLUMN_HEADS;
    	if ("INTEGRAL_HEIGHT".equals(variousPropertiesBitsName)) return INTEGRAL_HEIGHT;
    	if ("MATCH_REQUIRED".equals(variousPropertiesBitsName)) return MATCH_REQUIRED;
    	if ("ALIGNMENT".equals(variousPropertiesBitsName)) return ALIGNMENT;
    	if ("EDITABLE".equals(variousPropertiesBitsName)) return EDITABLE;
    	if ("IME_MODE".equals(variousPropertiesBitsName)) return IME_MODE;
    	if ("DRAG_BEHAVIOR".equals(variousPropertiesBitsName)) return DRAG_BEHAVIOR;
    	if ("ENTER_KEY_BEHAVIOR".equals(variousPropertiesBitsName)) return ENTER_KEY_BEHAVIOR;
    	if ("ENTER_FIELD_BEHAVIOR".equals(variousPropertiesBitsName)) return ENTER_FIELD_BEHAVIOR;
    	if ("TAB_KEY_BEHAVIOR".equals(variousPropertiesBitsName)) return TAB_KEY_BEHAVIOR;
    	if ("WORD_WRAP".equals(variousPropertiesBitsName)) return WORD_WRAP;
    	if ("UNUSED_BITS_2".equals(variousPropertiesBitsName)) return UNUSED_BITS_2;
    	if ("BORDERS_SUPPRESS".equals(variousPropertiesBitsName)) return BORDERS_SUPPRESS;
    	if ("SELECTION_MARGIN".equals(variousPropertiesBitsName)) return SELECTION_MARGIN;
    	if ("AUTO_WORD_SELECT".equals(variousPropertiesBitsName)) return AUTO_WORD_SELECT;
    	if ("AUTO_SIZE".equals(variousPropertiesBitsName)) return AUTO_SIZE;
    	if ("HIDE_SELECTION".equals(variousPropertiesBitsName)) return HIDE_SELECTION;
    	if ("AUTO_TAB".equals(variousPropertiesBitsName)) return AUTO_TAB;
    	if ("MULTI_LINE".equals(variousPropertiesBitsName)) return MULTI_LINE;
    	throw new IllegalArgumentException("Unknown VariousPropertiesBits name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by VariousPropertiesBits names.
    /// </summary>
    public static /*VariousPropertiesBits*//*uint*/int fromNames(Set<String> variousPropertiesBitsNames)
    {
    	/*VariousPropertiesBits*//*uint*/int result = 0;
    	for (String name : variousPropertiesBitsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of VariousPropertiesBits values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		NONE,
    		RESERVED_1,
    		ENABLED,
    		LOCKED,
    		BACK_STYLE,
    		RESERVED_2,
    		UNUSED_BITS_11,
    		UNUSED_BITS_12,
    		UNUSED_BITS_13,
    		UNUSED_BITS_14,
    		UNUSED_BITS_15,
    		COLUMN_HEADS,
    		INTEGRAL_HEIGHT,
    		MATCH_REQUIRED,
    		ALIGNMENT,
    		EDITABLE,
    		IME_MODE,
    		DRAG_BEHAVIOR,
    		ENTER_KEY_BEHAVIOR,
    		ENTER_FIELD_BEHAVIOR,
    		TAB_KEY_BEHAVIOR,
    		WORD_WRAP,
    		UNUSED_BITS_2,
    		BORDERS_SUPPRESS,
    		SELECTION_MARGIN,
    		AUTO_WORD_SELECT,
    		AUTO_SIZE,
    		HIDE_SELECTION,
    		AUTO_TAB,
    		MULTI_LINE,
    	};
    }

    public static final int length = 30;
}

