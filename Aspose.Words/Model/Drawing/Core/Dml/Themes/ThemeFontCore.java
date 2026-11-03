// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/06/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Corresponds to 2.18.103 ST_Theme (Theme Font).
/// </summary>
/// <dev>
/// This is internal theme fonts related to public <see cref="ThemeFont"/>.
/// </dev>
/*enum*/ final class ThemeFontCore
{
    private ThemeFontCore(){}
    
    public static final int NONE            = 0x00000000;

    public static final int MAJOR_ASCII      = 0x00010001;
    public static final int MAJOR_BIDI       = 0x00010002;
    public static final int MAJOR_EAST_ASIA   = 0x00010003;
    public static final int MAJOR_H_ANSI      = 0x00010004;

    public static final int MINOR_ASCII      = 0x00020001;
    public static final int MINOR_BIDI       = 0x00020002;
    public static final int MINOR_EAST_ASIA   = 0x00020003;
    public static final int MINOR_H_ANSI      = 0x00020004;


    public static final int GROUP_MASK       = 0x00ff0000;
    public static final int MAJOR           = 0x00010000;
    public static final int MINOR           = 0x00020000;

    public static final int REGION_MASK      = 0x0000ffff;
    public static final int ASCII           = 0x00000001;
    public static final int BIDI            = 0x00000002;
    public static final int EAST_ASIA        = 0x00000003;
    public static final int H_ANSI           = 0x00000004;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ThemeFontCore value.
    /// </summary>
    public static String getName(/*ThemeFontCore*/int themeFontCore)
    {
    	switch (themeFontCore)
    	{
    		case NONE: return "NONE";
    		case MAJOR_ASCII: return "MAJOR_ASCII";
    		case MAJOR_BIDI: return "MAJOR_BIDI";
    		case MAJOR_EAST_ASIA: return "MAJOR_EAST_ASIA";
    		case MAJOR_H_ANSI: return "MAJOR_H_ANSI";
    		case MINOR_ASCII: return "MINOR_ASCII";
    		case MINOR_BIDI: return "MINOR_BIDI";
    		case MINOR_EAST_ASIA: return "MINOR_EAST_ASIA";
    		case MINOR_H_ANSI: return "MINOR_H_ANSI";
    		case GROUP_MASK: return "GROUP_MASK";
    		case MAJOR: return "MAJOR";
    		case MINOR: return "MINOR";
    		case REGION_MASK: return "REGION_MASK";
    		case ASCII: return "ASCII";
    		case BIDI: return "BIDI";
    		case EAST_ASIA: return "EAST_ASIA";
    		case H_ANSI: return "H_ANSI";
    		default: return "Unknown ThemeFontCore value.";
    	}
    }
    
    /// <summary>
    /// Returns string representation of integer ThemeFontCore value.
    /// </summary>
    public static Set<String> getNames(/*ThemeFontCore*/int themeFontCore)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & themeFontCore) == NONE) names.add("NONE");
    	if ((MAJOR_ASCII & themeFontCore) == MAJOR_ASCII) names.add("MAJOR_ASCII");
    	if ((MAJOR_BIDI & themeFontCore) == MAJOR_BIDI) names.add("MAJOR_BIDI");
    	if ((MAJOR_EAST_ASIA & themeFontCore) == MAJOR_EAST_ASIA) names.add("MAJOR_EAST_ASIA");
    	if ((MAJOR_H_ANSI & themeFontCore) == MAJOR_H_ANSI) names.add("MAJOR_H_ANSI");
    	if ((MINOR_ASCII & themeFontCore) == MINOR_ASCII) names.add("MINOR_ASCII");
    	if ((MINOR_BIDI & themeFontCore) == MINOR_BIDI) names.add("MINOR_BIDI");
    	if ((MINOR_EAST_ASIA & themeFontCore) == MINOR_EAST_ASIA) names.add("MINOR_EAST_ASIA");
    	if ((MINOR_H_ANSI & themeFontCore) == MINOR_H_ANSI) names.add("MINOR_H_ANSI");
    	if ((GROUP_MASK & themeFontCore) == GROUP_MASK) names.add("GROUP_MASK");
    	if ((MAJOR & themeFontCore) == MAJOR) names.add("MAJOR");
    	if ((MINOR & themeFontCore) == MINOR) names.add("MINOR");
    	if ((REGION_MASK & themeFontCore) == REGION_MASK) names.add("REGION_MASK");
    	if ((ASCII & themeFontCore) == ASCII) names.add("ASCII");
    	if ((BIDI & themeFontCore) == BIDI) names.add("BIDI");
    	if ((EAST_ASIA & themeFontCore) == EAST_ASIA) names.add("EAST_ASIA");
    	if ((H_ANSI & themeFontCore) == H_ANSI) names.add("H_ANSI");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ThemeFontCore value.
    /// </summary>
    public static String toString(/*ThemeFontCore*/int themeFontCore)
    {
    	switch (themeFontCore)
    	{
    		case NONE: return "None";
    		case MAJOR_ASCII: return "MajorAscii";
    		case MAJOR_BIDI: return "MajorBidi";
    		case MAJOR_EAST_ASIA: return "MajorEastAsia";
    		case MAJOR_H_ANSI: return "MajorHAnsi";
    		case MINOR_ASCII: return "MinorAscii";
    		case MINOR_BIDI: return "MinorBidi";
    		case MINOR_EAST_ASIA: return "MinorEastAsia";
    		case MINOR_H_ANSI: return "MinorHAnsi";
    		case GROUP_MASK: return "GroupMask";
    		case MAJOR: return "Major";
    		case MINOR: return "Minor";
    		case REGION_MASK: return "RegionMask";
    		case ASCII: return "Ascii";
    		case BIDI: return "Bidi";
    		case EAST_ASIA: return "EastAsia";
    		case H_ANSI: return "HAnsi";
    		default: return toStringSet(themeFontCore);
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ThemeFontCore value.
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
    /// Returns integer representation by ThemeFontCore name.
    /// </summary>
    public static /*ThemeFontCore*/int fromName(String themeFontCoreName)
    {
    	if ("NONE".equals(themeFontCoreName)) return NONE;
    	if ("MAJOR_ASCII".equals(themeFontCoreName)) return MAJOR_ASCII;
    	if ("MAJOR_BIDI".equals(themeFontCoreName)) return MAJOR_BIDI;
    	if ("MAJOR_EAST_ASIA".equals(themeFontCoreName)) return MAJOR_EAST_ASIA;
    	if ("MAJOR_H_ANSI".equals(themeFontCoreName)) return MAJOR_H_ANSI;
    	if ("MINOR_ASCII".equals(themeFontCoreName)) return MINOR_ASCII;
    	if ("MINOR_BIDI".equals(themeFontCoreName)) return MINOR_BIDI;
    	if ("MINOR_EAST_ASIA".equals(themeFontCoreName)) return MINOR_EAST_ASIA;
    	if ("MINOR_H_ANSI".equals(themeFontCoreName)) return MINOR_H_ANSI;
    	if ("GROUP_MASK".equals(themeFontCoreName)) return GROUP_MASK;
    	if ("MAJOR".equals(themeFontCoreName)) return MAJOR;
    	if ("MINOR".equals(themeFontCoreName)) return MINOR;
    	if ("REGION_MASK".equals(themeFontCoreName)) return REGION_MASK;
    	if ("ASCII".equals(themeFontCoreName)) return ASCII;
    	if ("BIDI".equals(themeFontCoreName)) return BIDI;
    	if ("EAST_ASIA".equals(themeFontCoreName)) return EAST_ASIA;
    	if ("H_ANSI".equals(themeFontCoreName)) return H_ANSI;
    	throw new IllegalArgumentException("Unknown ThemeFontCore name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by ThemeFontCore names.
    /// </summary>
    public static /*ThemeFontCore*/int fromNames(Set<String> themeFontCoreNames)
    {
    	/*ThemeFontCore*/int result = 0;
    	for (String name : themeFontCoreNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of ThemeFontCore values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		MAJOR_ASCII,
    		MAJOR_BIDI,
    		MAJOR_EAST_ASIA,
    		MAJOR_H_ANSI,
    		MINOR_ASCII,
    		MINOR_BIDI,
    		MINOR_EAST_ASIA,
    		MINOR_H_ANSI,
    		GROUP_MASK,
    		MAJOR,
    		MINOR,
    		REGION_MASK,
    		ASCII,
    		BIDI,
    		EAST_ASIA,
    		H_ANSI,
    	};
    }

    public static final int length = 17;
}

