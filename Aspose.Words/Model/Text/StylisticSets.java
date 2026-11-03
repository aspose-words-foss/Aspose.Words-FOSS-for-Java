// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/04/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies the stylistic sets to apply to the font.
/// </summary>
/*enum*/ final class StylisticSets
{
    private StylisticSets(){}
    
    /// <summary>
    /// Default value. Specifies that the text is not displayed using stylistic sets.
    /// </summary>
    public static final int DEFAULT = 0x00000;

    /// <summary>
    /// First stylistic set for the specified font.
    /// </summary>
    public static final int SET_01 = 0x00001;

    /// <summary>
    /// Second stylistic set for the specified font.
    /// </summary>
    public static final int SET_02 = 0x00002;

    /// <summary>
    /// Third stylistic set for the specified font.
    /// </summary>
    public static final int SET_03 = 0x00004;

    /// <summary>
    /// Fourth stylistic set for the specified font.
    /// </summary>
    public static final int SET_04 = 0x00008;

    /// <summary>
    /// Fifth stylistic set for the specified font.
    /// </summary>
    public static final int SET_05 = 0x00010;

    /// <summary>
    /// Sixth stylistic set for the specified font.
    /// </summary>
    public static final int SET_06 = 0x00020;

    /// <summary>
    /// Seventh stylistic set for the specified font.
    /// </summary>
    public static final int SET_07 = 0x00040;

    /// <summary>
    /// Eighth stylistic set for the specified font.
    /// </summary>
    public static final int SET_08 = 0x00080;

    /// <summary>
    /// Ninth stylistic set for the specified font.
    /// </summary>
    public static final int SET_09 = 0x00100;

    /// <summary>
    /// Tenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_10 = 0x00200;

    /// <summary>
    /// Eleventh stylistic set for the specified font.
    /// </summary>
    public static final int SET_11 = 0x00400;

    /// <summary>
    /// Twelfth stylistic set for the specified font.
    /// </summary>
    public static final int SET_12 = 0x00800;

    /// <summary>
    /// Thirteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_13 = 0x01000;

    /// <summary>
    /// Fourteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_14 = 0x02000;

    /// <summary>
    /// Fifteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_15 = 0x04000;

    /// <summary>
    /// Sixteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_16 = 0x08000;

    /// <summary>
    /// Seventeenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_17 = 0x10000;

    /// <summary>
    /// Eighteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_18 = 0x20000;

    /// <summary>
    /// Nineteenth stylistic set for the specified font.
    /// </summary>
    public static final int SET_19 = 0x40000;

    /// <summary>
    /// Twentieth stylistic set for the specified font.
    /// </summary>
    public static final int SET_20 = 0x80000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer StylisticSets value.
    /// </summary>
    public static String getName(/*StylisticSets*/int stylisticSets)
    {
    	switch (stylisticSets)
    	{
    		case DEFAULT: return "DEFAULT";
    		case SET_01: return "SET_01";
    		case SET_02: return "SET_02";
    		case SET_03: return "SET_03";
    		case SET_04: return "SET_04";
    		case SET_05: return "SET_05";
    		case SET_06: return "SET_06";
    		case SET_07: return "SET_07";
    		case SET_08: return "SET_08";
    		case SET_09: return "SET_09";
    		case SET_10: return "SET_10";
    		case SET_11: return "SET_11";
    		case SET_12: return "SET_12";
    		case SET_13: return "SET_13";
    		case SET_14: return "SET_14";
    		case SET_15: return "SET_15";
    		case SET_16: return "SET_16";
    		case SET_17: return "SET_17";
    		case SET_18: return "SET_18";
    		case SET_19: return "SET_19";
    		case SET_20: return "SET_20";
    		default: return "Unknown StylisticSets value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer StylisticSets value.
    /// </summary>
    public static Set<String> getNames(/*StylisticSets*/int stylisticSets)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((DEFAULT & stylisticSets) == DEFAULT) names.add("DEFAULT");
    	if ((SET_01 & stylisticSets) == SET_01) names.add("SET_01");
    	if ((SET_02 & stylisticSets) == SET_02) names.add("SET_02");
    	if ((SET_03 & stylisticSets) == SET_03) names.add("SET_03");
    	if ((SET_04 & stylisticSets) == SET_04) names.add("SET_04");
    	if ((SET_05 & stylisticSets) == SET_05) names.add("SET_05");
    	if ((SET_06 & stylisticSets) == SET_06) names.add("SET_06");
    	if ((SET_07 & stylisticSets) == SET_07) names.add("SET_07");
    	if ((SET_08 & stylisticSets) == SET_08) names.add("SET_08");
    	if ((SET_09 & stylisticSets) == SET_09) names.add("SET_09");
    	if ((SET_10 & stylisticSets) == SET_10) names.add("SET_10");
    	if ((SET_11 & stylisticSets) == SET_11) names.add("SET_11");
    	if ((SET_12 & stylisticSets) == SET_12) names.add("SET_12");
    	if ((SET_13 & stylisticSets) == SET_13) names.add("SET_13");
    	if ((SET_14 & stylisticSets) == SET_14) names.add("SET_14");
    	if ((SET_15 & stylisticSets) == SET_15) names.add("SET_15");
    	if ((SET_16 & stylisticSets) == SET_16) names.add("SET_16");
    	if ((SET_17 & stylisticSets) == SET_17) names.add("SET_17");
    	if ((SET_18 & stylisticSets) == SET_18) names.add("SET_18");
    	if ((SET_19 & stylisticSets) == SET_19) names.add("SET_19");
    	if ((SET_20 & stylisticSets) == SET_20) names.add("SET_20");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StylisticSets value.
    /// </summary>
    public static String toString(/*StylisticSets*/int stylisticSets)
    {
    	switch (stylisticSets)
    	{
    		case DEFAULT: return "Default";
    		case SET_01: return "Set01";
    		case SET_02: return "Set02";
    		case SET_03: return "Set03";
    		case SET_04: return "Set04";
    		case SET_05: return "Set05";
    		case SET_06: return "Set06";
    		case SET_07: return "Set07";
    		case SET_08: return "Set08";
    		case SET_09: return "Set09";
    		case SET_10: return "Set10";
    		case SET_11: return "Set11";
    		case SET_12: return "Set12";
    		case SET_13: return "Set13";
    		case SET_14: return "Set14";
    		case SET_15: return "Set15";
    		case SET_16: return "Set16";
    		case SET_17: return "Set17";
    		case SET_18: return "Set18";
    		case SET_19: return "Set19";
    		case SET_20: return "Set20";
    		default: return toStringSet(stylisticSets);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer StylisticSets value.
    /// </summary>
    public static String toStringSet(int attr)
    {
    	String result = "";
    	String separator = "";
    	for (int value : getValues())
    		if (value != 0 && (value & attr) == value)
    		{
    			result += separator + toString(value);
    			separator = ", ";
    		}
    	return result;
    }
    
    /// <summary>
    /// Returns integer representation by StylisticSets name.
    /// </summary>
    public static /*StylisticSets*/int fromName(String stylisticSetsName)
    {
    	if ("DEFAULT".equals(stylisticSetsName)) return DEFAULT;
    	if ("SET_01".equals(stylisticSetsName)) return SET_01;
    	if ("SET_02".equals(stylisticSetsName)) return SET_02;
    	if ("SET_03".equals(stylisticSetsName)) return SET_03;
    	if ("SET_04".equals(stylisticSetsName)) return SET_04;
    	if ("SET_05".equals(stylisticSetsName)) return SET_05;
    	if ("SET_06".equals(stylisticSetsName)) return SET_06;
    	if ("SET_07".equals(stylisticSetsName)) return SET_07;
    	if ("SET_08".equals(stylisticSetsName)) return SET_08;
    	if ("SET_09".equals(stylisticSetsName)) return SET_09;
    	if ("SET_10".equals(stylisticSetsName)) return SET_10;
    	if ("SET_11".equals(stylisticSetsName)) return SET_11;
    	if ("SET_12".equals(stylisticSetsName)) return SET_12;
    	if ("SET_13".equals(stylisticSetsName)) return SET_13;
    	if ("SET_14".equals(stylisticSetsName)) return SET_14;
    	if ("SET_15".equals(stylisticSetsName)) return SET_15;
    	if ("SET_16".equals(stylisticSetsName)) return SET_16;
    	if ("SET_17".equals(stylisticSetsName)) return SET_17;
    	if ("SET_18".equals(stylisticSetsName)) return SET_18;
    	if ("SET_19".equals(stylisticSetsName)) return SET_19;
    	if ("SET_20".equals(stylisticSetsName)) return SET_20;
    	throw new IllegalArgumentException("Unknown StylisticSets name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by StylisticSets names.
    /// </summary>
    public static /*StylisticSets*/int fromNames(Set<String> stylisticSetsNames)
    {
    	/*StylisticSets*/int result = 0;
    	for (String name : stylisticSetsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of StylisticSets values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		SET_01,
    		SET_02,
    		SET_03,
    		SET_04,
    		SET_05,
    		SET_06,
    		SET_07,
    		SET_08,
    		SET_09,
    		SET_10,
    		SET_11,
    		SET_12,
    		SET_13,
    		SET_14,
    		SET_15,
    		SET_16,
    		SET_17,
    		SET_18,
    		SET_19,
    		SET_20,
    	};
    }

    public static final int length = 21;
}

