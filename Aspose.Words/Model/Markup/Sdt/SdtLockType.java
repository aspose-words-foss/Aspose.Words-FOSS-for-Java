// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible set of locking behaviors which can be applied to the contents of the 
/// Structured Document Tag when the contents of this documents are edited by an application (whether 
/// through a user interface or directly).
/// </summary>
/*enum*/ final class SdtLockType
{
    private SdtLockType(){}
    

    /// <summary>
    /// No locking.
    /// </summary>
    public static final int UNLOCKED = 0;

    /// <summary>
    /// Contents cannot be edited at runtime.
    /// </summary>
    public static final int CONTENT_LOCKED = 1;

    /// <summary>
    /// Contents cannot be edited at runtime and SDT cannot be deleted.
    /// </summary>
    public static final int SDT_AND_CONTENT_LOCKED = 2;

    /// <summary>
    /// SDT cannot be deleted.
    /// </summary>
    public static final int SDT_LOCKED = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtLockType value.
    /// </summary>
    public static String getName(/*SdtLockType*/int sdtLockType)
    {
    	switch (sdtLockType)
    	{
    		case UNLOCKED: return "UNLOCKED";
    		case CONTENT_LOCKED: return "CONTENT_LOCKED";
    		case SDT_AND_CONTENT_LOCKED: return "SDT_AND_CONTENT_LOCKED";
    		case SDT_LOCKED: return "SDT_LOCKED";
    		default: return "Unknown SdtLockType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtLockType value.
    /// </summary>
    public static String toString(/*SdtLockType*/int sdtLockType)
    {
    	switch (sdtLockType)
    	{
    		case UNLOCKED: return "Unlocked";
    		case CONTENT_LOCKED: return "ContentLocked";
    		case SDT_AND_CONTENT_LOCKED: return "SdtAndContentLocked";
    		case SDT_LOCKED: return "SdtLocked";
    		default: return "Unknown SdtLockType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtLockType name.
    /// </summary>
    public static /*SdtLockType*/int fromName(String sdtLockTypeName)
    {
    	if ("UNLOCKED".equals(sdtLockTypeName)) return UNLOCKED;
    	if ("CONTENT_LOCKED".equals(sdtLockTypeName)) return CONTENT_LOCKED;
    	if ("SDT_AND_CONTENT_LOCKED".equals(sdtLockTypeName)) return SDT_AND_CONTENT_LOCKED;
    	if ("SDT_LOCKED".equals(sdtLockTypeName)) return SDT_LOCKED;
    	throw new IllegalArgumentException("Unknown SdtLockType name.");
    }
    
    /// <summary>
    /// Returns array of SdtLockType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNLOCKED,
    		CONTENT_LOCKED,
    		SDT_AND_CONTENT_LOCKED,
    		SDT_LOCKED,
    	};
    }

    public static final int length = 4;
}

