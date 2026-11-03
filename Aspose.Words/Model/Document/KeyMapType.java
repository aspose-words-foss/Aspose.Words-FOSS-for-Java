// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/03/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a keymap customization.
/// </summary>
/*enum*/ final class KeyMapType
{
    private KeyMapType(){}
    
    /// <summary>
    /// No command specified.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Masks out the built-in command.
    /// </summary>
    public static final int MASK = 1;
    /// <summary>
    /// Invokes one of the allocated commands.
    /// </summary>
    public static final int ALLOCATED_COMMAND = 2;
    /// <summary>
    /// Invokes one of the fixed commands.
    /// </summary>
    public static final int FIXED_COMMAND = 3;
    /// <summary>
    /// Invokes a macro by name.
    /// </summary>
    public static final int MACRO = 4;
    /// <summary>
    /// Inserts a character.
    /// </summary>
    public static final int INSERT_CHARACTER = 5;
    /// <summary>
    /// Invokes a legace Word macro by name.
    /// </summary>
    public static final int LEGACY_MACRO = 6;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer KeyMapType value.
    /// </summary>
    public static String getName(/*KeyMapType*/int keyMapType)
    {
    	switch (keyMapType)
    	{
    		case NONE: return "NONE";
    		case MASK: return "MASK";
    		case ALLOCATED_COMMAND: return "ALLOCATED_COMMAND";
    		case FIXED_COMMAND: return "FIXED_COMMAND";
    		case MACRO: return "MACRO";
    		case INSERT_CHARACTER: return "INSERT_CHARACTER";
    		case LEGACY_MACRO: return "LEGACY_MACRO";
    		default: return "Unknown KeyMapType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer KeyMapType value.
    /// </summary>
    public static String toString(/*KeyMapType*/int keyMapType)
    {
    	switch (keyMapType)
    	{
    		case NONE: return "None";
    		case MASK: return "Mask";
    		case ALLOCATED_COMMAND: return "AllocatedCommand";
    		case FIXED_COMMAND: return "FixedCommand";
    		case MACRO: return "Macro";
    		case INSERT_CHARACTER: return "InsertCharacter";
    		case LEGACY_MACRO: return "LegacyMacro";
    		default: return "Unknown KeyMapType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by KeyMapType name.
    /// </summary>
    public static /*KeyMapType*/int fromName(String keyMapTypeName)
    {
    	if ("NONE".equals(keyMapTypeName)) return NONE;
    	if ("MASK".equals(keyMapTypeName)) return MASK;
    	if ("ALLOCATED_COMMAND".equals(keyMapTypeName)) return ALLOCATED_COMMAND;
    	if ("FIXED_COMMAND".equals(keyMapTypeName)) return FIXED_COMMAND;
    	if ("MACRO".equals(keyMapTypeName)) return MACRO;
    	if ("INSERT_CHARACTER".equals(keyMapTypeName)) return INSERT_CHARACTER;
    	if ("LEGACY_MACRO".equals(keyMapTypeName)) return LEGACY_MACRO;
    	throw new IllegalArgumentException("Unknown KeyMapType name.");
    }
    
    /// <summary>
    /// Returns array of KeyMapType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		MASK,
    		ALLOCATED_COMMAND,
    		FIXED_COMMAND,
    		MACRO,
    		INSERT_CHARACTER,
    		LEGACY_MACRO,
    	};
    }

    public static final int length = 7;
}

