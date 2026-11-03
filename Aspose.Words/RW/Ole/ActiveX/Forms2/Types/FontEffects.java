// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies the visual attributes of the text displayed by the control that contains the TextProps to which this
/// property applies.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.26 FontEffects.
/// </dev>
/*enum*/ final class FontEffects /*: uint*/
{
    private FontEffects(){}
    
    public static final /*uint*/int NONE = 0x00000000;
    public static final /*uint*/int BOLD = 0x00000001;
    public static final /*uint*/int ITALIC = 0x00000002;
    public static final /*uint*/int UNDERLINE = 0x00000004;
    public static final /*uint*/int STRIKEOUT = 0x00000008;
    public static final /*uint*/int DISABLED = 0x00002000;
    public static final /*uint*/int AUTO_COLOR = 0x40000000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FontEffects value.
    /// </summary>
    public static String getName(/*FontEffects*//*uint*/int fontEffects)
    {
    	if (NONE == fontEffects) return "NONE";
    	if (BOLD == fontEffects) return "BOLD";
    	if (ITALIC == fontEffects) return "ITALIC";
    	if (UNDERLINE == fontEffects) return "UNDERLINE";
    	if (STRIKEOUT == fontEffects) return "STRIKEOUT";
    	if (DISABLED == fontEffects) return "DISABLED";
    	if (AUTO_COLOR == fontEffects) return "AUTO_COLOR";
    	return "Unknown FontEffects value.";
    }
    
    /// <summary>
    /// Returns string representation of integer FontEffects value.
    /// </summary>
    public static Set<String> getNames(/*FontEffects*//*uint*/int fontEffects)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((NONE & fontEffects) == NONE) names.add("NONE");
    	if ((BOLD & fontEffects) == BOLD) names.add("BOLD");
    	if ((ITALIC & fontEffects) == ITALIC) names.add("ITALIC");
    	if ((UNDERLINE & fontEffects) == UNDERLINE) names.add("UNDERLINE");
    	if ((STRIKEOUT & fontEffects) == STRIKEOUT) names.add("STRIKEOUT");
    	if ((DISABLED & fontEffects) == DISABLED) names.add("DISABLED");
    	if ((AUTO_COLOR & fontEffects) == AUTO_COLOR) names.add("AUTO_COLOR");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontEffects value.
    /// </summary>
    public static String toString(/*FontEffects*//*uint*/int fontEffects)
    {
    	if (NONE == fontEffects) return "None";
    	if (BOLD == fontEffects) return "Bold";
    	if (ITALIC == fontEffects) return "Italic";
    	if (UNDERLINE == fontEffects) return "Underline";
    	if (STRIKEOUT == fontEffects) return "Strikeout";
    	if (DISABLED == fontEffects) return "Disabled";
    	if (AUTO_COLOR == fontEffects) return "AutoColor";
    	return toStringSet(fontEffects);
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FontEffects value.
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
    /// Returns integer representation by FontEffects name.
    /// </summary>
    public static /*FontEffects*//*uint*/int fromName(String fontEffectsName)
    {
    	if ("NONE".equals(fontEffectsName)) return NONE;
    	if ("BOLD".equals(fontEffectsName)) return BOLD;
    	if ("ITALIC".equals(fontEffectsName)) return ITALIC;
    	if ("UNDERLINE".equals(fontEffectsName)) return UNDERLINE;
    	if ("STRIKEOUT".equals(fontEffectsName)) return STRIKEOUT;
    	if ("DISABLED".equals(fontEffectsName)) return DISABLED;
    	if ("AUTO_COLOR".equals(fontEffectsName)) return AUTO_COLOR;
    	throw new IllegalArgumentException("Unknown FontEffects name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FontEffects names.
    /// </summary>
    public static /*FontEffects*//*uint*/int fromNames(Set<String> fontEffectsNames)
    {
    	/*FontEffects*//*uint*/int result = 0;
    	for (String name : fontEffectsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FontEffects values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		NONE,
    		BOLD,
    		ITALIC,
    		UNDERLINE,
    		STRIKEOUT,
    		DISABLED,
    		AUTO_COLOR,
    	};
    }

    public static final int length = 7;
}

