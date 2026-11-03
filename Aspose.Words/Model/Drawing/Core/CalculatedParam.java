// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies a property or formula to calculate a value of a formula parameter, when it is a calculated value.
/// 
/// 2.2.58 SG in the DOC SPEC.
/// </summary>
/*static*/ class CalculatedParam
{
	/* Simulation of static class by using private constructor */
	private CalculatedParam()
	{}

    /// <summary>
    /// X coordinate of the center of the geometry space of this shape.
    /// </summary>
    static final int GEOMETRY_SPACE_CENTER_X = 0x0140;
    /// <summary>
    /// Y coordinate of the center of the geometry space of this shape.
    /// </summary>
    static final int GEOMETRY_SPACE_CENTER_Y = 0x0141;
    /// <summary>
    /// Width of the geometry space of this shape.
    /// </summary>
    static final int GEOMETRY_SPACE_WIDTH = 0x0142;
    /// <summary>
    /// Height of the geometry space of this shape.
    /// </summary>
    static final int GEOMETRY_SPACE_HEIGHT = 0x0143;

    /// <summary>
    /// The value of this shape‟s adjustValue property.
    /// </summary>
    static final int ADJUST_1 = 0x0147;
    /// <summary>
    /// The value of this shape‟s adjust2Value property.
    /// </summary>
    static final int ADJUST_2 = 0x0148; 
    /// <summary>
    /// The value of this shape‟s adjust3Value property.
    /// </summary>
    static final int ADJUST_3 = 0x0149; 
    /// <summary>
    /// The value of this shape‟s adjust4Value property.
    /// </summary>
    static final int ADJUST_4 = 0x014a; 
    /// <summary>
    /// The value of this shape‟s adjust5Value property.
    /// </summary>
    static final int ADJUST_5 = 0x014b; 
    /// <summary>
    /// The value of this shape‟s adjust6Value property.
    /// </summary>
    static final int ADJUST_6 = 0x014c; 
    /// <summary>
    /// The value of this shape‟s adjust7Value property.
    /// </summary>
    static final int ADJUST_7 = 0x014d; 
    /// <summary>
    /// The value of this shape‟s adjust8Value property.
    /// </summary>
    static final int ADJUST_8 = 0x014e; 

    /// <summary>
    /// The value of this shape‟s xLimo property.
    /// </summary>
    static final int LIMO_X = 0x0153;
    /// <summary>
    /// The value of this shape‟s yLimo property.
    /// </summary>
    static final int LIMO_Y = 0x0154;
    
    /// <summary>
    /// The value of the fLine bit from this shape‟s Line Style Boolean Properties.
    /// </summary>
    static final int STROKED = 0x01fc;

    /// <summary>
    /// RK Basically it is an index into another Formula object. You just need to subtract 
    /// 0x0400 to get the index to the other formula.
    /// 
    /// The value calculated from another SG entry in this shape„s pGuides_complex array. 
    /// The index of the guide is specified by taking the value and subtracting 0x0400. 
    /// The index MUST be less than the size of the pGuides_complex array, 
    /// and it MUST be less than the index of this record in that same array.
    /// </summary>
    static final int FORMULA_REFERENCE_MIN = 0x0400;
    static final int FORMULA_REFERENCE_MAX = 0x047f;

    /// <summary>
    /// The width of a line in this shape in pixels.
    /// </summary>
    static final int LINE_WIDTH_PIXELS = 0x04f7;
    /// <summary>
    /// The width of this shape in pixels.
    /// </summary>
    static final int SHAPE_WIDTH_PIXELS = 0x04f8;
    /// <summary>
    /// The height of this shape in pixels.
    /// </summary>
    static final int SHAPE_HEIGHT_PIXELS = 0x04f9;

    /// <summary>
    /// The width of this shape in English Metric Units.
    /// </summary>
    static final int SHAPE_WIDTH_EMUS = 0x04fc;
    /// <summary>
    /// The height of this shape in English Metric Units.
    /// </summary>
    static final int SHAPE_HEIGHT_EMUS = 0x04fd;
    /// <summary>
    /// The width of this shape in English Metric Units divided by 2.
    /// </summary>
    static final int HALF_SHAPE_WIDTH_EMUS = 0x04fe;
    /// <summary>
    /// The height of this shape in English Metric Units divided by 2.
    /// </summary>
    static final int HALF_SHAPE_HEIGHT_EMUS = 0x04ff;
}

