// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class RleDecoderState
{
    private RleDecoderState(){}
    
    public static final int INITIAL = 0;
    public static final int NORMAL = 1;
    public static final int SEEN_ESCAPE = 2;
    public static final int NEED_BYTES = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RleDecoderState value.
    /// </summary>
    public static String getName(/*RleDecoderState*/int rleDecoderState)
    {
    	switch (rleDecoderState)
    	{
    		case INITIAL: return "INITIAL";
    		case NORMAL: return "NORMAL";
    		case SEEN_ESCAPE: return "SEEN_ESCAPE";
    		case NEED_BYTES: return "NEED_BYTES";
    		default: return "Unknown RleDecoderState value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RleDecoderState value.
    /// </summary>
    public static String toString(/*RleDecoderState*/int rleDecoderState)
    {
    	switch (rleDecoderState)
    	{
    		case INITIAL: return "Initial";
    		case NORMAL: return "Normal";
    		case SEEN_ESCAPE: return "SeenEscape";
    		case NEED_BYTES: return "NeedBytes";
    		default: return "Unknown RleDecoderState value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RleDecoderState name.
    /// </summary>
    public static /*RleDecoderState*/int fromName(String rleDecoderStateName)
    {
    	if ("INITIAL".equals(rleDecoderStateName)) return INITIAL;
    	if ("NORMAL".equals(rleDecoderStateName)) return NORMAL;
    	if ("SEEN_ESCAPE".equals(rleDecoderStateName)) return SEEN_ESCAPE;
    	if ("NEED_BYTES".equals(rleDecoderStateName)) return NEED_BYTES;
    	throw new IllegalArgumentException("Unknown RleDecoderState name.");
    }
    
    /// <summary>
    /// Returns array of RleDecoderState values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INITIAL,
    		NORMAL,
    		SEEN_ESCAPE,
    		NEED_BYTES,
    	};
    }

    public static final int length = 4;
}

