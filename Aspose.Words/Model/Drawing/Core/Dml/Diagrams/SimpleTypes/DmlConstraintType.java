// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/04/2016 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.21 ST_ConstraintType (Constraint Type)
/// </summary>
/*enum*/ final class DmlConstraintType
{
    private DmlConstraintType(){}
    
    public static final int NONE = 0;
    public static final int ALIGNMENT_OFFSET = 1;
    public static final int BOTTOM = 2;
    public static final int BEGINNING_MARGIN = 3;
    public static final int BEGIN_PADDING = 4;
    public static final int BENDING_DISTANCE = 5;
    public static final int BOTTOM_MARGIN = 6;
    public static final int BOTTOM_OFFSET = 7;
    public static final int CONNECTION_DISTANCE = 8;
    public static final int CENTER_X = 9;
    public static final int CENTER_X_OFFSET = 10;
    public static final int CENTER_Y = 11;
    public static final int CENTER_Y_OFFSET = 12;
    public static final int DIAMETER = 13;
    public static final int END_MARGIN = 14;
    public static final int END_PADDING = 15;
    public static final int HEIGHT = 16;
    public static final int ARROWHEAD_HEIGHT = 17;
    public static final int HEIGHT_OFFSET = 18;
    public static final int LEFT = 19;
    public static final int LEFT_MARGIN = 20;
    public static final int LEFT_OFFSET = 21;
    public static final int PRIMARY_FONT_SIZE = 22;
    public static final int PYRAMID_ACCENT_RATIO = 23;
    public static final int RIGHT = 24;
    public static final int RIGHT_MARGIN = 25;
    public static final int RIGHT_OFFSET = 26;
    public static final int SECONDARY_FONT_SIZE = 27;
    public static final int SECONDARY_SIBLING_SPACING = 28;
    public static final int SIBLING_SPACING = 29;
    public static final int SPACING = 30;
    public static final int STEM_THICKNESS = 31;
    public static final int TOP = 32;
    public static final int TOP_MARGIN = 33;
    public static final int TOP_OFFSET = 34;
    public static final int USER_A = 35;
    public static final int USER_B = 36;
    public static final int USER_C = 37;
    public static final int USER_D = 38;
    public static final int USER_E = 39;
    public static final int USER_F = 40;
    public static final int USER_G = 41;
    public static final int USER_H = 42;
    public static final int USER_I = 43;
    public static final int USER_J = 44;
    public static final int USER_K = 45;
    public static final int USER_L = 46;
    public static final int USER_M = 47;
    public static final int USER_N = 48;
    public static final int USER_O = 49;
    public static final int USER_P = 50;
    public static final int USER_Q = 51;
    public static final int USER_R = 52;
    public static final int USER_S = 53;
    public static final int USER_T = 54;
    public static final int USER_U = 55;
    public static final int USER_V = 56;
    public static final int USER_W = 57;
    public static final int USER_X = 58;
    public static final int USER_Y = 59;
    public static final int USER_Z = 60;
    public static final int WIDTH = 61;
    public static final int ARROWHEAD_WIDTH = 62;
    public static final int WIDTH_OFFSET = 63;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlConstraintType value.
    /// </summary>
    public static String getName(/*DmlConstraintType*/int dmlConstraintType)
    {
    	switch (dmlConstraintType)
    	{
    		case NONE: return "NONE";
    		case ALIGNMENT_OFFSET: return "ALIGNMENT_OFFSET";
    		case BOTTOM: return "BOTTOM";
    		case BEGINNING_MARGIN: return "BEGINNING_MARGIN";
    		case BEGIN_PADDING: return "BEGIN_PADDING";
    		case BENDING_DISTANCE: return "BENDING_DISTANCE";
    		case BOTTOM_MARGIN: return "BOTTOM_MARGIN";
    		case BOTTOM_OFFSET: return "BOTTOM_OFFSET";
    		case CONNECTION_DISTANCE: return "CONNECTION_DISTANCE";
    		case CENTER_X: return "CENTER_X";
    		case CENTER_X_OFFSET: return "CENTER_X_OFFSET";
    		case CENTER_Y: return "CENTER_Y";
    		case CENTER_Y_OFFSET: return "CENTER_Y_OFFSET";
    		case DIAMETER: return "DIAMETER";
    		case END_MARGIN: return "END_MARGIN";
    		case END_PADDING: return "END_PADDING";
    		case HEIGHT: return "HEIGHT";
    		case ARROWHEAD_HEIGHT: return "ARROWHEAD_HEIGHT";
    		case HEIGHT_OFFSET: return "HEIGHT_OFFSET";
    		case LEFT: return "LEFT";
    		case LEFT_MARGIN: return "LEFT_MARGIN";
    		case LEFT_OFFSET: return "LEFT_OFFSET";
    		case PRIMARY_FONT_SIZE: return "PRIMARY_FONT_SIZE";
    		case PYRAMID_ACCENT_RATIO: return "PYRAMID_ACCENT_RATIO";
    		case RIGHT: return "RIGHT";
    		case RIGHT_MARGIN: return "RIGHT_MARGIN";
    		case RIGHT_OFFSET: return "RIGHT_OFFSET";
    		case SECONDARY_FONT_SIZE: return "SECONDARY_FONT_SIZE";
    		case SECONDARY_SIBLING_SPACING: return "SECONDARY_SIBLING_SPACING";
    		case SIBLING_SPACING: return "SIBLING_SPACING";
    		case SPACING: return "SPACING";
    		case STEM_THICKNESS: return "STEM_THICKNESS";
    		case TOP: return "TOP";
    		case TOP_MARGIN: return "TOP_MARGIN";
    		case TOP_OFFSET: return "TOP_OFFSET";
    		case USER_A: return "USER_A";
    		case USER_B: return "USER_B";
    		case USER_C: return "USER_C";
    		case USER_D: return "USER_D";
    		case USER_E: return "USER_E";
    		case USER_F: return "USER_F";
    		case USER_G: return "USER_G";
    		case USER_H: return "USER_H";
    		case USER_I: return "USER_I";
    		case USER_J: return "USER_J";
    		case USER_K: return "USER_K";
    		case USER_L: return "USER_L";
    		case USER_M: return "USER_M";
    		case USER_N: return "USER_N";
    		case USER_O: return "USER_O";
    		case USER_P: return "USER_P";
    		case USER_Q: return "USER_Q";
    		case USER_R: return "USER_R";
    		case USER_S: return "USER_S";
    		case USER_T: return "USER_T";
    		case USER_U: return "USER_U";
    		case USER_V: return "USER_V";
    		case USER_W: return "USER_W";
    		case USER_X: return "USER_X";
    		case USER_Y: return "USER_Y";
    		case USER_Z: return "USER_Z";
    		case WIDTH: return "WIDTH";
    		case ARROWHEAD_WIDTH: return "ARROWHEAD_WIDTH";
    		case WIDTH_OFFSET: return "WIDTH_OFFSET";
    		default: return "Unknown DmlConstraintType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlConstraintType value.
    /// </summary>
    public static String toString(/*DmlConstraintType*/int dmlConstraintType)
    {
    	switch (dmlConstraintType)
    	{
    		case NONE: return "None";
    		case ALIGNMENT_OFFSET: return "AlignmentOffset";
    		case BOTTOM: return "Bottom";
    		case BEGINNING_MARGIN: return "BeginningMargin";
    		case BEGIN_PADDING: return "BeginPadding";
    		case BENDING_DISTANCE: return "BendingDistance";
    		case BOTTOM_MARGIN: return "BottomMargin";
    		case BOTTOM_OFFSET: return "BottomOffset";
    		case CONNECTION_DISTANCE: return "ConnectionDistance";
    		case CENTER_X: return "CenterX";
    		case CENTER_X_OFFSET: return "CenterXOffset";
    		case CENTER_Y: return "CenterY";
    		case CENTER_Y_OFFSET: return "CenterYOffset";
    		case DIAMETER: return "Diameter";
    		case END_MARGIN: return "EndMargin";
    		case END_PADDING: return "EndPadding";
    		case HEIGHT: return "Height";
    		case ARROWHEAD_HEIGHT: return "ArrowheadHeight";
    		case HEIGHT_OFFSET: return "HeightOffset";
    		case LEFT: return "Left";
    		case LEFT_MARGIN: return "LeftMargin";
    		case LEFT_OFFSET: return "LeftOffset";
    		case PRIMARY_FONT_SIZE: return "PrimaryFontSize";
    		case PYRAMID_ACCENT_RATIO: return "PyramidAccentRatio";
    		case RIGHT: return "Right";
    		case RIGHT_MARGIN: return "RightMargin";
    		case RIGHT_OFFSET: return "RightOffset";
    		case SECONDARY_FONT_SIZE: return "SecondaryFontSize";
    		case SECONDARY_SIBLING_SPACING: return "SecondarySiblingSpacing";
    		case SIBLING_SPACING: return "SiblingSpacing";
    		case SPACING: return "Spacing";
    		case STEM_THICKNESS: return "StemThickness";
    		case TOP: return "Top";
    		case TOP_MARGIN: return "TopMargin";
    		case TOP_OFFSET: return "TopOffset";
    		case USER_A: return "UserA";
    		case USER_B: return "UserB";
    		case USER_C: return "UserC";
    		case USER_D: return "UserD";
    		case USER_E: return "UserE";
    		case USER_F: return "UserF";
    		case USER_G: return "UserG";
    		case USER_H: return "UserH";
    		case USER_I: return "UserI";
    		case USER_J: return "UserJ";
    		case USER_K: return "UserK";
    		case USER_L: return "UserL";
    		case USER_M: return "UserM";
    		case USER_N: return "UserN";
    		case USER_O: return "UserO";
    		case USER_P: return "UserP";
    		case USER_Q: return "UserQ";
    		case USER_R: return "UserR";
    		case USER_S: return "UserS";
    		case USER_T: return "UserT";
    		case USER_U: return "UserU";
    		case USER_V: return "UserV";
    		case USER_W: return "UserW";
    		case USER_X: return "UserX";
    		case USER_Y: return "UserY";
    		case USER_Z: return "UserZ";
    		case WIDTH: return "Width";
    		case ARROWHEAD_WIDTH: return "ArrowheadWidth";
    		case WIDTH_OFFSET: return "WidthOffset";
    		default: return "Unknown DmlConstraintType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlConstraintType name.
    /// </summary>
    public static /*DmlConstraintType*/int fromName(String dmlConstraintTypeName)
    {
    	if ("NONE".equals(dmlConstraintTypeName)) return NONE;
    	if ("ALIGNMENT_OFFSET".equals(dmlConstraintTypeName)) return ALIGNMENT_OFFSET;
    	if ("BOTTOM".equals(dmlConstraintTypeName)) return BOTTOM;
    	if ("BEGINNING_MARGIN".equals(dmlConstraintTypeName)) return BEGINNING_MARGIN;
    	if ("BEGIN_PADDING".equals(dmlConstraintTypeName)) return BEGIN_PADDING;
    	if ("BENDING_DISTANCE".equals(dmlConstraintTypeName)) return BENDING_DISTANCE;
    	if ("BOTTOM_MARGIN".equals(dmlConstraintTypeName)) return BOTTOM_MARGIN;
    	if ("BOTTOM_OFFSET".equals(dmlConstraintTypeName)) return BOTTOM_OFFSET;
    	if ("CONNECTION_DISTANCE".equals(dmlConstraintTypeName)) return CONNECTION_DISTANCE;
    	if ("CENTER_X".equals(dmlConstraintTypeName)) return CENTER_X;
    	if ("CENTER_X_OFFSET".equals(dmlConstraintTypeName)) return CENTER_X_OFFSET;
    	if ("CENTER_Y".equals(dmlConstraintTypeName)) return CENTER_Y;
    	if ("CENTER_Y_OFFSET".equals(dmlConstraintTypeName)) return CENTER_Y_OFFSET;
    	if ("DIAMETER".equals(dmlConstraintTypeName)) return DIAMETER;
    	if ("END_MARGIN".equals(dmlConstraintTypeName)) return END_MARGIN;
    	if ("END_PADDING".equals(dmlConstraintTypeName)) return END_PADDING;
    	if ("HEIGHT".equals(dmlConstraintTypeName)) return HEIGHT;
    	if ("ARROWHEAD_HEIGHT".equals(dmlConstraintTypeName)) return ARROWHEAD_HEIGHT;
    	if ("HEIGHT_OFFSET".equals(dmlConstraintTypeName)) return HEIGHT_OFFSET;
    	if ("LEFT".equals(dmlConstraintTypeName)) return LEFT;
    	if ("LEFT_MARGIN".equals(dmlConstraintTypeName)) return LEFT_MARGIN;
    	if ("LEFT_OFFSET".equals(dmlConstraintTypeName)) return LEFT_OFFSET;
    	if ("PRIMARY_FONT_SIZE".equals(dmlConstraintTypeName)) return PRIMARY_FONT_SIZE;
    	if ("PYRAMID_ACCENT_RATIO".equals(dmlConstraintTypeName)) return PYRAMID_ACCENT_RATIO;
    	if ("RIGHT".equals(dmlConstraintTypeName)) return RIGHT;
    	if ("RIGHT_MARGIN".equals(dmlConstraintTypeName)) return RIGHT_MARGIN;
    	if ("RIGHT_OFFSET".equals(dmlConstraintTypeName)) return RIGHT_OFFSET;
    	if ("SECONDARY_FONT_SIZE".equals(dmlConstraintTypeName)) return SECONDARY_FONT_SIZE;
    	if ("SECONDARY_SIBLING_SPACING".equals(dmlConstraintTypeName)) return SECONDARY_SIBLING_SPACING;
    	if ("SIBLING_SPACING".equals(dmlConstraintTypeName)) return SIBLING_SPACING;
    	if ("SPACING".equals(dmlConstraintTypeName)) return SPACING;
    	if ("STEM_THICKNESS".equals(dmlConstraintTypeName)) return STEM_THICKNESS;
    	if ("TOP".equals(dmlConstraintTypeName)) return TOP;
    	if ("TOP_MARGIN".equals(dmlConstraintTypeName)) return TOP_MARGIN;
    	if ("TOP_OFFSET".equals(dmlConstraintTypeName)) return TOP_OFFSET;
    	if ("USER_A".equals(dmlConstraintTypeName)) return USER_A;
    	if ("USER_B".equals(dmlConstraintTypeName)) return USER_B;
    	if ("USER_C".equals(dmlConstraintTypeName)) return USER_C;
    	if ("USER_D".equals(dmlConstraintTypeName)) return USER_D;
    	if ("USER_E".equals(dmlConstraintTypeName)) return USER_E;
    	if ("USER_F".equals(dmlConstraintTypeName)) return USER_F;
    	if ("USER_G".equals(dmlConstraintTypeName)) return USER_G;
    	if ("USER_H".equals(dmlConstraintTypeName)) return USER_H;
    	if ("USER_I".equals(dmlConstraintTypeName)) return USER_I;
    	if ("USER_J".equals(dmlConstraintTypeName)) return USER_J;
    	if ("USER_K".equals(dmlConstraintTypeName)) return USER_K;
    	if ("USER_L".equals(dmlConstraintTypeName)) return USER_L;
    	if ("USER_M".equals(dmlConstraintTypeName)) return USER_M;
    	if ("USER_N".equals(dmlConstraintTypeName)) return USER_N;
    	if ("USER_O".equals(dmlConstraintTypeName)) return USER_O;
    	if ("USER_P".equals(dmlConstraintTypeName)) return USER_P;
    	if ("USER_Q".equals(dmlConstraintTypeName)) return USER_Q;
    	if ("USER_R".equals(dmlConstraintTypeName)) return USER_R;
    	if ("USER_S".equals(dmlConstraintTypeName)) return USER_S;
    	if ("USER_T".equals(dmlConstraintTypeName)) return USER_T;
    	if ("USER_U".equals(dmlConstraintTypeName)) return USER_U;
    	if ("USER_V".equals(dmlConstraintTypeName)) return USER_V;
    	if ("USER_W".equals(dmlConstraintTypeName)) return USER_W;
    	if ("USER_X".equals(dmlConstraintTypeName)) return USER_X;
    	if ("USER_Y".equals(dmlConstraintTypeName)) return USER_Y;
    	if ("USER_Z".equals(dmlConstraintTypeName)) return USER_Z;
    	if ("WIDTH".equals(dmlConstraintTypeName)) return WIDTH;
    	if ("ARROWHEAD_WIDTH".equals(dmlConstraintTypeName)) return ARROWHEAD_WIDTH;
    	if ("WIDTH_OFFSET".equals(dmlConstraintTypeName)) return WIDTH_OFFSET;
    	throw new IllegalArgumentException("Unknown DmlConstraintType name.");
    }
    
    /// <summary>
    /// Returns array of DmlConstraintType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ALIGNMENT_OFFSET,
    		BOTTOM,
    		BEGINNING_MARGIN,
    		BEGIN_PADDING,
    		BENDING_DISTANCE,
    		BOTTOM_MARGIN,
    		BOTTOM_OFFSET,
    		CONNECTION_DISTANCE,
    		CENTER_X,
    		CENTER_X_OFFSET,
    		CENTER_Y,
    		CENTER_Y_OFFSET,
    		DIAMETER,
    		END_MARGIN,
    		END_PADDING,
    		HEIGHT,
    		ARROWHEAD_HEIGHT,
    		HEIGHT_OFFSET,
    		LEFT,
    		LEFT_MARGIN,
    		LEFT_OFFSET,
    		PRIMARY_FONT_SIZE,
    		PYRAMID_ACCENT_RATIO,
    		RIGHT,
    		RIGHT_MARGIN,
    		RIGHT_OFFSET,
    		SECONDARY_FONT_SIZE,
    		SECONDARY_SIBLING_SPACING,
    		SIBLING_SPACING,
    		SPACING,
    		STEM_THICKNESS,
    		TOP,
    		TOP_MARGIN,
    		TOP_OFFSET,
    		USER_A,
    		USER_B,
    		USER_C,
    		USER_D,
    		USER_E,
    		USER_F,
    		USER_G,
    		USER_H,
    		USER_I,
    		USER_J,
    		USER_K,
    		USER_L,
    		USER_M,
    		USER_N,
    		USER_O,
    		USER_P,
    		USER_Q,
    		USER_R,
    		USER_S,
    		USER_T,
    		USER_U,
    		USER_V,
    		USER_W,
    		USER_X,
    		USER_Y,
    		USER_Z,
    		WIDTH,
    		ARROWHEAD_WIDTH,
    		WIDTH_OFFSET,
    	};
    }

    public static final int length = 64;
}

