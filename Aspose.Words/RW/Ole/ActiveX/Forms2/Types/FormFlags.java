// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Set;


/// <summary>
/// Specifies Boolean properties of a form.
/// </summary>
/*enum*/ final class FormFlags /*: uint*/
{
    private FormFlags(){}
    
    public static final /*uint*/int UNUSED_11 = 0x00000001;
    public static final /*uint*/int UNUSED_12 = 0x00000002;
    public static final /*uint*/int ENABLED = 0x00000004;

    public static final /*uint*/int UNUSED_21 = 0x00000008;
    public static final /*uint*/int UNUSED_22 = 0x00000010;
    public static final /*uint*/int UNUSED_23 = 0x00000020;
    public static final /*uint*/int UNUSED_24 = 0x00000040;
    public static final /*uint*/int UNUSED_25 = 0x00000080;
    public static final /*uint*/int UNUSED_26 = 0x00000100;
    public static final /*uint*/int UNUSED_27 = 0x00000200;
    public static final /*uint*/int UNUSED_28 = 0x00000400;
    public static final /*uint*/int UNUSED_29 = 0x00000800;
    public static final /*uint*/int UNUSED_2_A = 0x00001000;
    public static final /*uint*/int UNUSED_2_B = 0x00002000;

    public static final /*uint*/int DESINK_PERSISTED = 0x00004000;
    public static final /*uint*/int DONT_SAVE_CLASS_TABLE = 0x00008000;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FormFlags value.
    /// </summary>
    public static String getName(/*FormFlags*//*uint*/int formFlags)
    {
    	if (UNUSED_11 == formFlags) return "UNUSED_11";
    	if (UNUSED_12 == formFlags) return "UNUSED_12";
    	if (ENABLED == formFlags) return "ENABLED";
    	if (UNUSED_21 == formFlags) return "UNUSED_21";
    	if (UNUSED_22 == formFlags) return "UNUSED_22";
    	if (UNUSED_23 == formFlags) return "UNUSED_23";
    	if (UNUSED_24 == formFlags) return "UNUSED_24";
    	if (UNUSED_25 == formFlags) return "UNUSED_25";
    	if (UNUSED_26 == formFlags) return "UNUSED_26";
    	if (UNUSED_27 == formFlags) return "UNUSED_27";
    	if (UNUSED_28 == formFlags) return "UNUSED_28";
    	if (UNUSED_29 == formFlags) return "UNUSED_29";
    	if (UNUSED_2_A == formFlags) return "UNUSED_2_A";
    	if (UNUSED_2_B == formFlags) return "UNUSED_2_B";
    	if (DESINK_PERSISTED == formFlags) return "DESINK_PERSISTED";
    	if (DONT_SAVE_CLASS_TABLE == formFlags) return "DONT_SAVE_CLASS_TABLE";
    	return "Unknown FormFlags value.";
    }
    
    /// <summary>
    /// Returns string representation of integer FormFlags value.
    /// </summary>
    public static Set<String> getNames(/*FormFlags*//*uint*/int formFlags)
    {
    	Set<String> names = new java.util.HashSet<String>();
    	if ((UNUSED_11 & formFlags) == UNUSED_11) names.add("UNUSED_11");
    	if ((UNUSED_12 & formFlags) == UNUSED_12) names.add("UNUSED_12");
    	if ((ENABLED & formFlags) == ENABLED) names.add("ENABLED");
    	if ((UNUSED_21 & formFlags) == UNUSED_21) names.add("UNUSED_21");
    	if ((UNUSED_22 & formFlags) == UNUSED_22) names.add("UNUSED_22");
    	if ((UNUSED_23 & formFlags) == UNUSED_23) names.add("UNUSED_23");
    	if ((UNUSED_24 & formFlags) == UNUSED_24) names.add("UNUSED_24");
    	if ((UNUSED_25 & formFlags) == UNUSED_25) names.add("UNUSED_25");
    	if ((UNUSED_26 & formFlags) == UNUSED_26) names.add("UNUSED_26");
    	if ((UNUSED_27 & formFlags) == UNUSED_27) names.add("UNUSED_27");
    	if ((UNUSED_28 & formFlags) == UNUSED_28) names.add("UNUSED_28");
    	if ((UNUSED_29 & formFlags) == UNUSED_29) names.add("UNUSED_29");
    	if ((UNUSED_2_A & formFlags) == UNUSED_2_A) names.add("UNUSED_2_A");
    	if ((UNUSED_2_B & formFlags) == UNUSED_2_B) names.add("UNUSED_2_B");
    	if ((DESINK_PERSISTED & formFlags) == DESINK_PERSISTED) names.add("DESINK_PERSISTED");
    	if ((DONT_SAVE_CLASS_TABLE & formFlags) == DONT_SAVE_CLASS_TABLE) names.add("DONT_SAVE_CLASS_TABLE");
    	return names;
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FormFlags value.
    /// </summary>
    public static String toString(/*FormFlags*//*uint*/int formFlags)
    {
    	if (UNUSED_11 == formFlags) return "Unused11";
    	if (UNUSED_12 == formFlags) return "Unused12";
    	if (ENABLED == formFlags) return "Enabled";
    	if (UNUSED_21 == formFlags) return "Unused21";
    	if (UNUSED_22 == formFlags) return "Unused22";
    	if (UNUSED_23 == formFlags) return "Unused23";
    	if (UNUSED_24 == formFlags) return "Unused24";
    	if (UNUSED_25 == formFlags) return "Unused25";
    	if (UNUSED_26 == formFlags) return "Unused26";
    	if (UNUSED_27 == formFlags) return "Unused27";
    	if (UNUSED_28 == formFlags) return "Unused28";
    	if (UNUSED_29 == formFlags) return "Unused29";
    	if (UNUSED_2_A == formFlags) return "Unused2A";
    	if (UNUSED_2_B == formFlags) return "Unused2B";
    	if (DESINK_PERSISTED == formFlags) return "DesinkPersisted";
    	if (DONT_SAVE_CLASS_TABLE == formFlags) return "DontSaveClassTable";
    	return toStringSet(formFlags);
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FormFlags value.
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
    /// Returns integer representation by FormFlags name.
    /// </summary>
    public static /*FormFlags*//*uint*/int fromName(String formFlagsName)
    {
    	if ("UNUSED_11".equals(formFlagsName)) return UNUSED_11;
    	if ("UNUSED_12".equals(formFlagsName)) return UNUSED_12;
    	if ("ENABLED".equals(formFlagsName)) return ENABLED;
    	if ("UNUSED_21".equals(formFlagsName)) return UNUSED_21;
    	if ("UNUSED_22".equals(formFlagsName)) return UNUSED_22;
    	if ("UNUSED_23".equals(formFlagsName)) return UNUSED_23;
    	if ("UNUSED_24".equals(formFlagsName)) return UNUSED_24;
    	if ("UNUSED_25".equals(formFlagsName)) return UNUSED_25;
    	if ("UNUSED_26".equals(formFlagsName)) return UNUSED_26;
    	if ("UNUSED_27".equals(formFlagsName)) return UNUSED_27;
    	if ("UNUSED_28".equals(formFlagsName)) return UNUSED_28;
    	if ("UNUSED_29".equals(formFlagsName)) return UNUSED_29;
    	if ("UNUSED_2_A".equals(formFlagsName)) return UNUSED_2_A;
    	if ("UNUSED_2_B".equals(formFlagsName)) return UNUSED_2_B;
    	if ("DESINK_PERSISTED".equals(formFlagsName)) return DESINK_PERSISTED;
    	if ("DONT_SAVE_CLASS_TABLE".equals(formFlagsName)) return DONT_SAVE_CLASS_TABLE;
    	throw new IllegalArgumentException("Unknown FormFlags name.");
    }
    
    /// <summary>
    /// Returns flags integer representation by FormFlags names.
    /// </summary>
    public static /*FormFlags*//*uint*/int fromNames(Set<String> formFlagsNames)
    {
    	/*FormFlags*//*uint*/int result = 0;
    	for (String name : formFlagsNames)
    		result |= fromName(name);
    	return result;
    }
    
    /// <summary>
    /// Returns array of FormFlags values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		UNUSED_11,
    		UNUSED_12,
    		ENABLED,
    		UNUSED_21,
    		UNUSED_22,
    		UNUSED_23,
    		UNUSED_24,
    		UNUSED_25,
    		UNUSED_26,
    		UNUSED_27,
    		UNUSED_28,
    		UNUSED_29,
    		UNUSED_2_A,
    		UNUSED_2_B,
    		DESINK_PERSISTED,
    		DONT_SAVE_CLASS_TABLE,
    	};
    }

    public static final int length = 16;
}

