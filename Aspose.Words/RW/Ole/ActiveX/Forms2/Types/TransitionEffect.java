// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the effect displayed when the user switches between pages in a control.
/// </summary>
/// <dev>
/// [MS-OFORMS], 2.5.93 TransitionEffect.
/// </dev>
/*enum*/ final class TransitionEffect
{
    private TransitionEffect(){}
    
    public static final int NONE = 0;
    public static final int COVER_UP = 1;
    public static final int COVER_RIGHT_UP = 2;
    public static final int COVER_RIGHT = 3;
    public static final int COVER_RIGHT_DOWN = 4;
    public static final int COVER_DOWN = 5;
    public static final int COVER_LEFT_DOWN = 6;
    public static final int COVER_LEFT = 7;
    public static final int COVER_LEFT_UP = 8;
    public static final int PUSH_UP = 9;
    public static final int PUSH_RIGHT = 10;
    public static final int PUSH_DOWN = 11;
    public static final int PUSH_LEFT = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TransitionEffect value.
    /// </summary>
    public static String getName(/*TransitionEffect*/int transitionEffect)
    {
    	switch (transitionEffect)
    	{
    		case NONE: return "NONE";
    		case COVER_UP: return "COVER_UP";
    		case COVER_RIGHT_UP: return "COVER_RIGHT_UP";
    		case COVER_RIGHT: return "COVER_RIGHT";
    		case COVER_RIGHT_DOWN: return "COVER_RIGHT_DOWN";
    		case COVER_DOWN: return "COVER_DOWN";
    		case COVER_LEFT_DOWN: return "COVER_LEFT_DOWN";
    		case COVER_LEFT: return "COVER_LEFT";
    		case COVER_LEFT_UP: return "COVER_LEFT_UP";
    		case PUSH_UP: return "PUSH_UP";
    		case PUSH_RIGHT: return "PUSH_RIGHT";
    		case PUSH_DOWN: return "PUSH_DOWN";
    		case PUSH_LEFT: return "PUSH_LEFT";
    		default: return "Unknown TransitionEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TransitionEffect value.
    /// </summary>
    public static String toString(/*TransitionEffect*/int transitionEffect)
    {
    	switch (transitionEffect)
    	{
    		case NONE: return "None";
    		case COVER_UP: return "CoverUp";
    		case COVER_RIGHT_UP: return "CoverRightUp";
    		case COVER_RIGHT: return "CoverRight";
    		case COVER_RIGHT_DOWN: return "CoverRightDown";
    		case COVER_DOWN: return "CoverDown";
    		case COVER_LEFT_DOWN: return "CoverLeftDown";
    		case COVER_LEFT: return "CoverLeft";
    		case COVER_LEFT_UP: return "CoverLeftUp";
    		case PUSH_UP: return "PushUp";
    		case PUSH_RIGHT: return "PushRight";
    		case PUSH_DOWN: return "PushDown";
    		case PUSH_LEFT: return "PushLeft";
    		default: return "Unknown TransitionEffect value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TransitionEffect name.
    /// </summary>
    public static /*TransitionEffect*/int fromName(String transitionEffectName)
    {
    	if ("NONE".equals(transitionEffectName)) return NONE;
    	if ("COVER_UP".equals(transitionEffectName)) return COVER_UP;
    	if ("COVER_RIGHT_UP".equals(transitionEffectName)) return COVER_RIGHT_UP;
    	if ("COVER_RIGHT".equals(transitionEffectName)) return COVER_RIGHT;
    	if ("COVER_RIGHT_DOWN".equals(transitionEffectName)) return COVER_RIGHT_DOWN;
    	if ("COVER_DOWN".equals(transitionEffectName)) return COVER_DOWN;
    	if ("COVER_LEFT_DOWN".equals(transitionEffectName)) return COVER_LEFT_DOWN;
    	if ("COVER_LEFT".equals(transitionEffectName)) return COVER_LEFT;
    	if ("COVER_LEFT_UP".equals(transitionEffectName)) return COVER_LEFT_UP;
    	if ("PUSH_UP".equals(transitionEffectName)) return PUSH_UP;
    	if ("PUSH_RIGHT".equals(transitionEffectName)) return PUSH_RIGHT;
    	if ("PUSH_DOWN".equals(transitionEffectName)) return PUSH_DOWN;
    	if ("PUSH_LEFT".equals(transitionEffectName)) return PUSH_LEFT;
    	throw new IllegalArgumentException("Unknown TransitionEffect name.");
    }
    
    /// <summary>
    /// Returns array of TransitionEffect values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		COVER_UP,
    		COVER_RIGHT_UP,
    		COVER_RIGHT,
    		COVER_RIGHT_DOWN,
    		COVER_DOWN,
    		COVER_LEFT_DOWN,
    		COVER_LEFT,
    		COVER_LEFT_UP,
    		PUSH_UP,
    		PUSH_RIGHT,
    		PUSH_DOWN,
    		PUSH_LEFT,
    	};
    }

    public static final int length = 13;
}

