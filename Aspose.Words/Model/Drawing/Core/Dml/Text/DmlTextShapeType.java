// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Preset text shape types.
/// </summary>
/*enum*/ final class DmlTextShapeType
{
    private DmlTextShapeType(){}
    
    // Value indicates wrap is not set in the document.
    public static final int NONE = 0;
    /// <summary> 
    ///    No Text Shape    
    /// </summary>
    public static final int TEXT_NO_SHAPE = 1;
    /// <summary> 
    ///    Plain Text Shape    
    /// </summary>
    public static final int TEXT_PLAIN = 2;
    /// <summary> 
    ///    Stop Sign Text Shape    
    /// </summary>
    public static final int TEXT_STOP = 3;
    /// <summary> 
    ///    Triangle Text Shape    
    /// </summary>
    public static final int TEXT_TRIANGLE = 4;
    /// <summary> 
    ///    Inverted Triangle Text Shape    
    /// </summary>
    public static final int TEXT_TRIANGLE_INVERTED = 5;
    /// <summary> 
    ///    Chevron Text Shape    
    /// </summary>
    public static final int TEXT_CHEVRON = 6;
    /// <summary> 
    ///    Inverted Chevron Text Shape    
    /// </summary>
    public static final int TEXT_CHEVRON_INVERTED = 7;
    /// <summary> 
    ///    Inside Ring Text Shape    
    /// </summary>
    public static final int TEXT_RING_INSIDE = 8;
    /// <summary> 
    ///    Outside Ring Text Shape    
    /// </summary>
    public static final int TEXT_RING_OUTSIDE = 9;
    /// <summary> 
    ///    Upward Arch Text Shape    
    /// </summary>
    public static final int TEXT_ARCH_UP = 10;
    /// <summary> 
    ///    Downward Arch Text Shape    
    /// </summary>
    public static final int TEXT_ARCH_DOWN = 11;
    /// <summary> 
    ///    Circle Text Shape    
    /// </summary>
    public static final int TEXT_CIRCLE = 12;
    /// <summary> 
    ///    Button Text Shape    
    /// </summary>
    public static final int TEXT_BUTTON = 13;
    /// <summary> 
    ///    Upward Pour Arch Text Shape    
    /// </summary>
    public static final int TEXT_ARCH_UP_POUR = 14;
    /// <summary> 
    ///    Downward Pour Arch Text Shape    
    /// </summary>
    public static final int TEXT_ARCH_DOWN_POUR = 15;
    /// <summary> 
    ///    Circle Pour Text Shape    
    /// </summary>
    public static final int TEXT_CIRCLE_POUR = 16;
    /// <summary> 
    ///    Button Pour Text Shape    
    /// </summary>
    public static final int TEXT_BUTTON_POUR = 17;
    /// <summary> 
    ///    Upward Curve Text Shape    
    /// </summary>
    public static final int TEXT_CURVE_UP = 18;
    /// <summary> 
    ///    Downward Curve Text Shape    
    /// </summary>
    public static final int TEXT_CURVE_DOWN = 19;
    /// <summary> 
    ///    Upward Can Text Shape    
    /// </summary>
    public static final int TEXT_CAN_UP = 20;
    /// <summary> 
    ///    Downward Can Text Shape    
    /// </summary>
    public static final int TEXT_CAN_DOWN = 21;
    /// <summary> 
    ///    Wave 1 Text Shape    
    /// </summary>
    public static final int TEXT_WAVE_1 = 22;
    /// <summary> 
    ///    Wave 2 Text Shape    
    /// </summary>
    public static final int TEXT_WAVE_2 = 23;
    /// <summary> 
    ///    Double Wave 1 Text Shape    
    /// </summary>
    public static final int TEXT_DOUBLE_WAVE_1 = 24;
    /// <summary> 
    ///    Wave 4 Text Shape    
    /// </summary>
    public static final int TEXT_WAVE_4 = 25;
    /// <summary> 
    ///    Inflate Text Shape    
    /// </summary>
    public static final int TEXT_INFLATE = 26;
    /// <summary> 
    ///    Deflate Text Shape    
    /// </summary>
    public static final int TEXT_DEFLATE = 27;
    /// <summary> 
    ///    Bottom Inflate Text Shape    
    /// </summary>
    public static final int TEXT_INFLATE_BOTTOM = 28;
    /// <summary> 
    ///    Bottom Deflate Text Shape    
    /// </summary>
    public static final int TEXT_DEFLATE_BOTTOM = 29;
    /// <summary> 
    ///    Top Inflate Text Shape    
    /// </summary>
    public static final int TEXT_INFLATE_TOP = 30;
    /// <summary> 
    ///    Top Deflate Text Shape    
    /// </summary>
    public static final int TEXT_DEFLATE_TOP = 31;
    /// <summary> 
    ///    Deflate-Inflate Text Shape    
    /// </summary>
    public static final int TEXT_DEFLATE_INFLATE = 32;
    /// <summary> 
    ///    Deflate-Inflate-Deflate Text Shape    
    /// </summary>
    public static final int TEXT_DEFLATE_INFLATE_DEFLATE = 33;
    /// <summary> 
    ///    Right Fade Text Shape    
    /// </summary>
    public static final int TEXT_FADE_RIGHT = 34;
    /// <summary> 
    ///    Left Fade Text Shape    
    /// </summary>
    public static final int TEXT_FADE_LEFT = 35;
    /// <summary> 
    ///    Upward Fade Text Shape    
    /// </summary>
    public static final int TEXT_FADE_UP = 36;
    /// <summary> 
    ///    Downward Fade Text Shape    
    /// </summary>
    public static final int TEXT_FADE_DOWN = 37;
    /// <summary> 
    ///    Upward Slant Text Shape    
    /// </summary>
    public static final int TEXT_SLANT_UP = 38;
    /// <summary> 
    ///    Downward Slant Text Shape    
    /// </summary>
    public static final int TEXT_SLANT_DOWN = 39;
    /// <summary> 
    ///    Upward Cascade Text Shape    
    /// </summary>
    public static final int TEXT_CASCADE_UP = 40;
    /// <summary> 
    ///    Downward Cascade Text Shape    
    /// </summary>
    public static final int TEXT_CASCADE_DOWN = 41;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextShapeType value.
    /// </summary>
    public static String getName(/*DmlTextShapeType*/int dmlTextShapeType)
    {
    	switch (dmlTextShapeType)
    	{
    		case NONE: return "NONE";
    		case TEXT_NO_SHAPE: return "TEXT_NO_SHAPE";
    		case TEXT_PLAIN: return "TEXT_PLAIN";
    		case TEXT_STOP: return "TEXT_STOP";
    		case TEXT_TRIANGLE: return "TEXT_TRIANGLE";
    		case TEXT_TRIANGLE_INVERTED: return "TEXT_TRIANGLE_INVERTED";
    		case TEXT_CHEVRON: return "TEXT_CHEVRON";
    		case TEXT_CHEVRON_INVERTED: return "TEXT_CHEVRON_INVERTED";
    		case TEXT_RING_INSIDE: return "TEXT_RING_INSIDE";
    		case TEXT_RING_OUTSIDE: return "TEXT_RING_OUTSIDE";
    		case TEXT_ARCH_UP: return "TEXT_ARCH_UP";
    		case TEXT_ARCH_DOWN: return "TEXT_ARCH_DOWN";
    		case TEXT_CIRCLE: return "TEXT_CIRCLE";
    		case TEXT_BUTTON: return "TEXT_BUTTON";
    		case TEXT_ARCH_UP_POUR: return "TEXT_ARCH_UP_POUR";
    		case TEXT_ARCH_DOWN_POUR: return "TEXT_ARCH_DOWN_POUR";
    		case TEXT_CIRCLE_POUR: return "TEXT_CIRCLE_POUR";
    		case TEXT_BUTTON_POUR: return "TEXT_BUTTON_POUR";
    		case TEXT_CURVE_UP: return "TEXT_CURVE_UP";
    		case TEXT_CURVE_DOWN: return "TEXT_CURVE_DOWN";
    		case TEXT_CAN_UP: return "TEXT_CAN_UP";
    		case TEXT_CAN_DOWN: return "TEXT_CAN_DOWN";
    		case TEXT_WAVE_1: return "TEXT_WAVE_1";
    		case TEXT_WAVE_2: return "TEXT_WAVE_2";
    		case TEXT_DOUBLE_WAVE_1: return "TEXT_DOUBLE_WAVE_1";
    		case TEXT_WAVE_4: return "TEXT_WAVE_4";
    		case TEXT_INFLATE: return "TEXT_INFLATE";
    		case TEXT_DEFLATE: return "TEXT_DEFLATE";
    		case TEXT_INFLATE_BOTTOM: return "TEXT_INFLATE_BOTTOM";
    		case TEXT_DEFLATE_BOTTOM: return "TEXT_DEFLATE_BOTTOM";
    		case TEXT_INFLATE_TOP: return "TEXT_INFLATE_TOP";
    		case TEXT_DEFLATE_TOP: return "TEXT_DEFLATE_TOP";
    		case TEXT_DEFLATE_INFLATE: return "TEXT_DEFLATE_INFLATE";
    		case TEXT_DEFLATE_INFLATE_DEFLATE: return "TEXT_DEFLATE_INFLATE_DEFLATE";
    		case TEXT_FADE_RIGHT: return "TEXT_FADE_RIGHT";
    		case TEXT_FADE_LEFT: return "TEXT_FADE_LEFT";
    		case TEXT_FADE_UP: return "TEXT_FADE_UP";
    		case TEXT_FADE_DOWN: return "TEXT_FADE_DOWN";
    		case TEXT_SLANT_UP: return "TEXT_SLANT_UP";
    		case TEXT_SLANT_DOWN: return "TEXT_SLANT_DOWN";
    		case TEXT_CASCADE_UP: return "TEXT_CASCADE_UP";
    		case TEXT_CASCADE_DOWN: return "TEXT_CASCADE_DOWN";
    		default: return "Unknown DmlTextShapeType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextShapeType value.
    /// </summary>
    public static String toString(/*DmlTextShapeType*/int dmlTextShapeType)
    {
    	switch (dmlTextShapeType)
    	{
    		case NONE: return "None";
    		case TEXT_NO_SHAPE: return "TextNoShape";
    		case TEXT_PLAIN: return "TextPlain";
    		case TEXT_STOP: return "TextStop";
    		case TEXT_TRIANGLE: return "TextTriangle";
    		case TEXT_TRIANGLE_INVERTED: return "TextTriangleInverted";
    		case TEXT_CHEVRON: return "TextChevron";
    		case TEXT_CHEVRON_INVERTED: return "TextChevronInverted";
    		case TEXT_RING_INSIDE: return "TextRingInside";
    		case TEXT_RING_OUTSIDE: return "TextRingOutside";
    		case TEXT_ARCH_UP: return "TextArchUp";
    		case TEXT_ARCH_DOWN: return "TextArchDown";
    		case TEXT_CIRCLE: return "TextCircle";
    		case TEXT_BUTTON: return "TextButton";
    		case TEXT_ARCH_UP_POUR: return "TextArchUpPour";
    		case TEXT_ARCH_DOWN_POUR: return "TextArchDownPour";
    		case TEXT_CIRCLE_POUR: return "TextCirclePour";
    		case TEXT_BUTTON_POUR: return "TextButtonPour";
    		case TEXT_CURVE_UP: return "TextCurveUp";
    		case TEXT_CURVE_DOWN: return "TextCurveDown";
    		case TEXT_CAN_UP: return "TextCanUp";
    		case TEXT_CAN_DOWN: return "TextCanDown";
    		case TEXT_WAVE_1: return "TextWave1";
    		case TEXT_WAVE_2: return "TextWave2";
    		case TEXT_DOUBLE_WAVE_1: return "TextDoubleWave1";
    		case TEXT_WAVE_4: return "TextWave4";
    		case TEXT_INFLATE: return "TextInflate";
    		case TEXT_DEFLATE: return "TextDeflate";
    		case TEXT_INFLATE_BOTTOM: return "TextInflateBottom";
    		case TEXT_DEFLATE_BOTTOM: return "TextDeflateBottom";
    		case TEXT_INFLATE_TOP: return "TextInflateTop";
    		case TEXT_DEFLATE_TOP: return "TextDeflateTop";
    		case TEXT_DEFLATE_INFLATE: return "TextDeflateInflate";
    		case TEXT_DEFLATE_INFLATE_DEFLATE: return "TextDeflateInflateDeflate";
    		case TEXT_FADE_RIGHT: return "TextFadeRight";
    		case TEXT_FADE_LEFT: return "TextFadeLeft";
    		case TEXT_FADE_UP: return "TextFadeUp";
    		case TEXT_FADE_DOWN: return "TextFadeDown";
    		case TEXT_SLANT_UP: return "TextSlantUp";
    		case TEXT_SLANT_DOWN: return "TextSlantDown";
    		case TEXT_CASCADE_UP: return "TextCascadeUp";
    		case TEXT_CASCADE_DOWN: return "TextCascadeDown";
    		default: return "Unknown DmlTextShapeType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextShapeType name.
    /// </summary>
    public static /*DmlTextShapeType*/int fromName(String dmlTextShapeTypeName)
    {
    	if ("NONE".equals(dmlTextShapeTypeName)) return NONE;
    	if ("TEXT_NO_SHAPE".equals(dmlTextShapeTypeName)) return TEXT_NO_SHAPE;
    	if ("TEXT_PLAIN".equals(dmlTextShapeTypeName)) return TEXT_PLAIN;
    	if ("TEXT_STOP".equals(dmlTextShapeTypeName)) return TEXT_STOP;
    	if ("TEXT_TRIANGLE".equals(dmlTextShapeTypeName)) return TEXT_TRIANGLE;
    	if ("TEXT_TRIANGLE_INVERTED".equals(dmlTextShapeTypeName)) return TEXT_TRIANGLE_INVERTED;
    	if ("TEXT_CHEVRON".equals(dmlTextShapeTypeName)) return TEXT_CHEVRON;
    	if ("TEXT_CHEVRON_INVERTED".equals(dmlTextShapeTypeName)) return TEXT_CHEVRON_INVERTED;
    	if ("TEXT_RING_INSIDE".equals(dmlTextShapeTypeName)) return TEXT_RING_INSIDE;
    	if ("TEXT_RING_OUTSIDE".equals(dmlTextShapeTypeName)) return TEXT_RING_OUTSIDE;
    	if ("TEXT_ARCH_UP".equals(dmlTextShapeTypeName)) return TEXT_ARCH_UP;
    	if ("TEXT_ARCH_DOWN".equals(dmlTextShapeTypeName)) return TEXT_ARCH_DOWN;
    	if ("TEXT_CIRCLE".equals(dmlTextShapeTypeName)) return TEXT_CIRCLE;
    	if ("TEXT_BUTTON".equals(dmlTextShapeTypeName)) return TEXT_BUTTON;
    	if ("TEXT_ARCH_UP_POUR".equals(dmlTextShapeTypeName)) return TEXT_ARCH_UP_POUR;
    	if ("TEXT_ARCH_DOWN_POUR".equals(dmlTextShapeTypeName)) return TEXT_ARCH_DOWN_POUR;
    	if ("TEXT_CIRCLE_POUR".equals(dmlTextShapeTypeName)) return TEXT_CIRCLE_POUR;
    	if ("TEXT_BUTTON_POUR".equals(dmlTextShapeTypeName)) return TEXT_BUTTON_POUR;
    	if ("TEXT_CURVE_UP".equals(dmlTextShapeTypeName)) return TEXT_CURVE_UP;
    	if ("TEXT_CURVE_DOWN".equals(dmlTextShapeTypeName)) return TEXT_CURVE_DOWN;
    	if ("TEXT_CAN_UP".equals(dmlTextShapeTypeName)) return TEXT_CAN_UP;
    	if ("TEXT_CAN_DOWN".equals(dmlTextShapeTypeName)) return TEXT_CAN_DOWN;
    	if ("TEXT_WAVE_1".equals(dmlTextShapeTypeName)) return TEXT_WAVE_1;
    	if ("TEXT_WAVE_2".equals(dmlTextShapeTypeName)) return TEXT_WAVE_2;
    	if ("TEXT_DOUBLE_WAVE_1".equals(dmlTextShapeTypeName)) return TEXT_DOUBLE_WAVE_1;
    	if ("TEXT_WAVE_4".equals(dmlTextShapeTypeName)) return TEXT_WAVE_4;
    	if ("TEXT_INFLATE".equals(dmlTextShapeTypeName)) return TEXT_INFLATE;
    	if ("TEXT_DEFLATE".equals(dmlTextShapeTypeName)) return TEXT_DEFLATE;
    	if ("TEXT_INFLATE_BOTTOM".equals(dmlTextShapeTypeName)) return TEXT_INFLATE_BOTTOM;
    	if ("TEXT_DEFLATE_BOTTOM".equals(dmlTextShapeTypeName)) return TEXT_DEFLATE_BOTTOM;
    	if ("TEXT_INFLATE_TOP".equals(dmlTextShapeTypeName)) return TEXT_INFLATE_TOP;
    	if ("TEXT_DEFLATE_TOP".equals(dmlTextShapeTypeName)) return TEXT_DEFLATE_TOP;
    	if ("TEXT_DEFLATE_INFLATE".equals(dmlTextShapeTypeName)) return TEXT_DEFLATE_INFLATE;
    	if ("TEXT_DEFLATE_INFLATE_DEFLATE".equals(dmlTextShapeTypeName)) return TEXT_DEFLATE_INFLATE_DEFLATE;
    	if ("TEXT_FADE_RIGHT".equals(dmlTextShapeTypeName)) return TEXT_FADE_RIGHT;
    	if ("TEXT_FADE_LEFT".equals(dmlTextShapeTypeName)) return TEXT_FADE_LEFT;
    	if ("TEXT_FADE_UP".equals(dmlTextShapeTypeName)) return TEXT_FADE_UP;
    	if ("TEXT_FADE_DOWN".equals(dmlTextShapeTypeName)) return TEXT_FADE_DOWN;
    	if ("TEXT_SLANT_UP".equals(dmlTextShapeTypeName)) return TEXT_SLANT_UP;
    	if ("TEXT_SLANT_DOWN".equals(dmlTextShapeTypeName)) return TEXT_SLANT_DOWN;
    	if ("TEXT_CASCADE_UP".equals(dmlTextShapeTypeName)) return TEXT_CASCADE_UP;
    	if ("TEXT_CASCADE_DOWN".equals(dmlTextShapeTypeName)) return TEXT_CASCADE_DOWN;
    	throw new IllegalArgumentException("Unknown DmlTextShapeType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextShapeType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		TEXT_NO_SHAPE,
    		TEXT_PLAIN,
    		TEXT_STOP,
    		TEXT_TRIANGLE,
    		TEXT_TRIANGLE_INVERTED,
    		TEXT_CHEVRON,
    		TEXT_CHEVRON_INVERTED,
    		TEXT_RING_INSIDE,
    		TEXT_RING_OUTSIDE,
    		TEXT_ARCH_UP,
    		TEXT_ARCH_DOWN,
    		TEXT_CIRCLE,
    		TEXT_BUTTON,
    		TEXT_ARCH_UP_POUR,
    		TEXT_ARCH_DOWN_POUR,
    		TEXT_CIRCLE_POUR,
    		TEXT_BUTTON_POUR,
    		TEXT_CURVE_UP,
    		TEXT_CURVE_DOWN,
    		TEXT_CAN_UP,
    		TEXT_CAN_DOWN,
    		TEXT_WAVE_1,
    		TEXT_WAVE_2,
    		TEXT_DOUBLE_WAVE_1,
    		TEXT_WAVE_4,
    		TEXT_INFLATE,
    		TEXT_DEFLATE,
    		TEXT_INFLATE_BOTTOM,
    		TEXT_DEFLATE_BOTTOM,
    		TEXT_INFLATE_TOP,
    		TEXT_DEFLATE_TOP,
    		TEXT_DEFLATE_INFLATE,
    		TEXT_DEFLATE_INFLATE_DEFLATE,
    		TEXT_FADE_RIGHT,
    		TEXT_FADE_LEFT,
    		TEXT_FADE_UP,
    		TEXT_FADE_DOWN,
    		TEXT_SLANT_UP,
    		TEXT_SLANT_DOWN,
    		TEXT_CASCADE_UP,
    		TEXT_CASCADE_DOWN,
    	};
    }

    public static final int length = 42;
}
