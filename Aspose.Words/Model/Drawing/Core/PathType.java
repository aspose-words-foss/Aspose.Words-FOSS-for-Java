// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies how the individual pieces of a path should be interpreted.
/// 
/// Corresponds to 2.4.30 MSOPATHTYPE in the DOC spec.
/// </summary>
/*enum*/ final class PathType
{
    private PathType(){}
    
    public static final int UNKNOWN = -1;
    /// <summary>
    /// Add a straight line segment from the current end point to the new point.
    /// The number of points to process is equal to the number of segments. 
    /// The last point in the array becomes the new end point.
    /// </summary>
    public static final int LINE_TO = 0;
    /// <summary>
    /// For each segment, three points are used to draw a cubic Bezier curve. 
    /// The first two points are control points and the last point is the new end point. 
    /// The number of points consumed is three times the number of segments.
    /// </summary>
    public static final int CURVE_TO = 1;
    /// <summary>
    /// Start a new sub-path using a single point. The starting point becomes the current end point.
    /// The value of the segment field MUST be zero. The number of points used is one.
    /// </summary>
    public static final int MOVE_TO = 2;
    /// <summary>
    /// If the starting point and the end point are not the same a single straight line is drawn to 
    /// connect the starting point and ending point of the path.
    /// The number of segments MUST be one. The number of points used is zero.
    /// </summary>
    public static final int CLOSE = 3;
    /// <summary>
    /// The end of the current path. All consecutive lines and fill values MUST be drawn before any 
    /// following path or line is drawn. 
    /// The number of segments MUST be zero. The number of points used is zero.
    /// </summary>
    public static final int END = 4;

    /// <summary>
    /// Escape path types are defined below and this is their starting value.
    /// 
    /// RK The spec says that escape path types store editing information (like whether or not to 
    /// allow the control points to be adjusted on a bezier segment), but they are NOT needed to 
    /// understand how to draw the path. But it seems to be some escape path types need to be drawn.
    /// </summary>
    public static final int ESCAPE_BASE = 0xa0;

    /// <summary>
    /// Adds additional points to the escape code that follows msopathEscapeExtension.
    /// RK No idea what this means.
    /// </summary>
    public static final int ESCAPE_EXTENSION =             ESCAPE_BASE + 0x00;
    /// <summary>
    /// The first point specifies the center of the ellipse.
    /// The second point specifies the starting radius in x and the ending radius in y. 
    /// The third point specifies the starting angle in the x value and the ending angle in the y value. 
    /// Angles are in degrees. The number of ellipse segments drawn is equal to the segment count 
    /// divided by three.
    /// </summary>
    public static final int ANGLE_ELLIPSE_TO =        ESCAPE_BASE + 0x01;
    /// <summary>
    /// The first point specifies the center of the ellipse. 
    /// The second point specifies the starting radius in x and the ending radius in y. 
    /// The third point specifies the starting angle in the x value and the ending angle in the y value. 
    /// Angles are in degrees. The number of ellipse segments drawn is equal to the segment count 
    /// divided by three. The first point of the ellipse becomes the first point of a new path.
    /// </summary>
    public static final int ANGLE_ELLIPSE =          ESCAPE_BASE + 0x02;
    /// <summary>
    /// The first two points specify the bounding rectangle of the ellipse. 
    /// The second two points specify the radial vectors for the ellipse. 
    /// The radial vectors are cast from the center of the bounding rectangle. 
    /// The path will start at the point where the first radial vector intersects the bounding 
    /// rectangle to the point where the second radial vector intersects the bounding rectangle. 
    /// The drawing direction is always counterclockwise. If the path has already been started, 
    /// a line is drawn from the last point to the starting point of the arc; otherwise a new 
    /// path is started. The number of arc segments drawn is equal to the segment count 
    /// divided by four.
    /// </summary>
    public static final int ARC_TO =                 ESCAPE_BASE + 0x03;
    /// <summary>
    /// The first two points specify the bounding rectangle of the ellipse. 
    /// The second two points specify the radial vectors for the ellipse. 
    /// The radial vectors are cast from the center of the bounding rectangle. 
    /// The path will start at the point where the first radial vector intersects the 
    /// bounding rectangle to the point where the second radial vector intersects the bounding rectangle. 
    /// The drawing direction is always counterclockwise. The number of arc segments drawn is equal 
    /// to the segment count divided by four.
    /// </summary>
    public static final int ARC =                   ESCAPE_BASE + 0x04;
    /// <summary>
    /// The first two points specify the bounding rectangle of the ellipse. The second two points 
    /// specify the radial vectors for the ellipse. The radial vectors are cast from the center 
    /// of the bounding rectangle. The path will start at the point where the first radial vector intersects
    /// the bounding rectangle to the point where the second radial vector intersects the bounding rectangle.
    /// The drawing direction is always clockwise. If the path has already been started, a line is 
    /// drawn from the last point to the starting point of the arc; otherwise a new path is started. 
    /// The number of arc segments drawn is equal to the segment count divided by four.
    /// </summary>
    public static final int CLOCKWISE_ARC_TO =        ESCAPE_BASE + 0x05;
    /// <summary>
    /// The first two points specify the bounding rectangle of the ellipse. 
    /// The second two points specify the radial vectors for the ellipse. 
    /// The radial vectors are cast from the center of the bounding rectangle. 
    /// The path will start at the point where the first radial vector intersects the bounding 
    /// rectangle to the point where the second radial vector intersects the bounding rectangle. 
    /// The drawing direction is always clockwise. The number of arc segments drawn is equal to 
    /// the segment count divided by four. This escape code always starts a new path.
    /// </summary>
    public static final int CLOCKWISE_ARC =          ESCAPE_BASE + 0x06;
    /// <summary>
    /// Add an ellipse to the path from the current point to the next point starting. 
    /// The ellipse is drawn as a quadrant that starts as a tangent to the X axis. 
    /// Multiple elliptical quadrants are joined by a straight line. 
    /// The number of elliptical quadrants drawn is equal to the segment count.
    /// </summary>
    public static final int ELLIPTICAL_QUADRANT_X =   ESCAPE_BASE + 0x07;
    /// <summary>
    /// Add an ellipse to the path from the current point to the next point starting. 
    /// The ellipse is drawn as a quadrant that starts as a tangent to the Y axis. 
    /// Multiple elliptical quadrants are joined by a straight line. 
    /// The number of elliptical quadrants drawn is equal to the segment count.
    /// </summary>
    public static final int ELLIPTICAL_QUADRANT_Y =   ESCAPE_BASE + 0x08;
    /// <summary>
    /// Each point defines a control point for a quadratic Bezier curve. 
    /// The number of control points is defined by the segment count.
    /// </summary>
    public static final int QUADRATIC_BEZIER =       ESCAPE_BASE + 0x09;
    /// <summary>
    /// The path SHOULD not be filled, even if passed to a rendering routine that would 
    /// normally fill the path.
    /// </summary>
    public static final int NO_FILL =                ESCAPE_BASE + 0x0a;
    /// <summary>
    /// The path SHOULD not be drawn, even if passed to a rendering routine that 
    /// would normally draw the path.
    /// </summary>
    public static final int NO_LINE =                ESCAPE_BASE + 0x0b;
    /// <summary>
    /// For Bezier curve editing, vertex joints are calculated, are equal lengths, and are collinear. 
    /// The segment after the point is a line. The tangent is not visible.
    /// </summary>
    public static final int ESCAPE_AUTO_LINE =              ESCAPE_BASE + 0x0c;
    /// <summary>
    /// For Bezier curve editing, vertex joints are calculated, are equal lengths, and are collinear. 
    /// The segment after the point is a curve. The tangent is not visible.
    /// </summary>
    public static final int ESCAPE_AUTO_CURVE =             ESCAPE_BASE + 0x0d;
    /// <summary>
    /// For Bezier curve editing, vertex joints are not calculated, are not equal lengths, 
    /// and are not collinear. The segment after the point is a line. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_CORNER_LINE =            ESCAPE_BASE + 0x0e;
    /// <summary>
    /// For Bezier curve editing, vertex joints are not calculated, are not equal lengths, 
    /// and are not collinear. The segment after the point is a line. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_CORNER_CURVE =           ESCAPE_BASE + 0x0f;
    /// <summary>
    /// For Bezier curve editing, vertex joints are not calculated, are not equal lengths, 
    /// and are not collinear. The segment after the point is a line. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_SMOOTH_LINE =            ESCAPE_BASE + 0x10;
    /// <summary>
    /// For Bezier curve editing, vertex joints are not calculated, are not equal lengths, 
    /// and are not collinear. The segment after the point is a curve. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_SMOOTH_CURVE =           ESCAPE_BASE + 0x11;
    /// <summary>
    /// For Bezier curve editing, vertex joints are not calculated, are equal lengths, 
    /// and are not collinear. The segment after the point is a line. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_SYMMETRIC_LINE =         ESCAPE_BASE + 0x12;
    /// <summary>
    /// For Bezier curve editing vertex joints are not calculated, are equal lengths, 
    /// and are not collinear. The segment after the point is a curve. The tangent is visible.
    /// </summary>
    public static final int ESCAPE_SYMMETRIC_CURVE =        ESCAPE_BASE + 0x13;
    /// <summary>
    /// For Bezier curve editing, vertex joints are calculated, are equal lengths and are collinear. 
    /// The tangent is not visible.
    /// </summary>
    public static final int ESCAPE_FREE_FORM =              ESCAPE_BASE + 0x14;
    /// <summary>
    /// Sets a new fill color. A single point is used to represent the colors. 
    /// X is an OfficeArtCOLORREF that specifies the new foreground color. 
    /// Y is an OfficeArtCOLORREF that specifies the new background color.
    /// </summary>
    public static final int FILL_COLOR =             ESCAPE_BASE + 0x15;
    /// <summary>
    /// Sets a new line drawing color. A single point is used to represent the colors. 
    /// X is an OfficeArtCOLORREF that specifies the new foreground color. 
    /// Y is an OfficeArtCOLORREF that specifies the new background color.
    /// </summary>
    public static final int LINE_COLOR =             ESCAPE_BASE + 0x16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PathType value.
    /// </summary>
    public static String getName(/*PathType*/int pathType)
    {
    	switch (pathType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case LINE_TO: return "LINE_TO";
    		case CURVE_TO: return "CURVE_TO";
    		case MOVE_TO: return "MOVE_TO";
    		case CLOSE: return "CLOSE";
    		case END: return "END";
    		case ESCAPE_BASE:
    		/*case ESCAPE_EXTENSION:*/
    			return "ESCAPE_BASE" + " | " + "ESCAPE_EXTENSION";
    		case ANGLE_ELLIPSE_TO: return "ANGLE_ELLIPSE_TO";
    		case ANGLE_ELLIPSE: return "ANGLE_ELLIPSE";
    		case ARC_TO: return "ARC_TO";
    		case ARC: return "ARC";
    		case CLOCKWISE_ARC_TO: return "CLOCKWISE_ARC_TO";
    		case CLOCKWISE_ARC: return "CLOCKWISE_ARC";
    		case ELLIPTICAL_QUADRANT_X: return "ELLIPTICAL_QUADRANT_X";
    		case ELLIPTICAL_QUADRANT_Y: return "ELLIPTICAL_QUADRANT_Y";
    		case QUADRATIC_BEZIER: return "QUADRATIC_BEZIER";
    		case NO_FILL: return "NO_FILL";
    		case NO_LINE: return "NO_LINE";
    		case ESCAPE_AUTO_LINE: return "ESCAPE_AUTO_LINE";
    		case ESCAPE_AUTO_CURVE: return "ESCAPE_AUTO_CURVE";
    		case ESCAPE_CORNER_LINE: return "ESCAPE_CORNER_LINE";
    		case ESCAPE_CORNER_CURVE: return "ESCAPE_CORNER_CURVE";
    		case ESCAPE_SMOOTH_LINE: return "ESCAPE_SMOOTH_LINE";
    		case ESCAPE_SMOOTH_CURVE: return "ESCAPE_SMOOTH_CURVE";
    		case ESCAPE_SYMMETRIC_LINE: return "ESCAPE_SYMMETRIC_LINE";
    		case ESCAPE_SYMMETRIC_CURVE: return "ESCAPE_SYMMETRIC_CURVE";
    		case ESCAPE_FREE_FORM: return "ESCAPE_FREE_FORM";
    		case FILL_COLOR: return "FILL_COLOR";
    		case LINE_COLOR: return "LINE_COLOR";
    		default: return "Unknown PathType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PathType value.
    /// </summary>
    public static String toString(/*PathType*/int pathType)
    {
    	switch (pathType)
    	{
    		case UNKNOWN: return "Unknown";
    		case LINE_TO: return "LineTo";
    		case CURVE_TO: return "CurveTo";
    		case MOVE_TO: return "MoveTo";
    		case CLOSE: return "Close";
    		case END: return "End";
    		case ESCAPE_BASE:
    		/*case ESCAPE_EXTENSION:*/
    			return "EscapeBase" + " | " + "EscapeExtension";
    		case ANGLE_ELLIPSE_TO: return "AngleEllipseTo";
    		case ANGLE_ELLIPSE: return "AngleEllipse";
    		case ARC_TO: return "ArcTo";
    		case ARC: return "Arc";
    		case CLOCKWISE_ARC_TO: return "ClockwiseArcTo";
    		case CLOCKWISE_ARC: return "ClockwiseArc";
    		case ELLIPTICAL_QUADRANT_X: return "EllipticalQuadrantX";
    		case ELLIPTICAL_QUADRANT_Y: return "EllipticalQuadrantY";
    		case QUADRATIC_BEZIER: return "QuadraticBezier";
    		case NO_FILL: return "NoFill";
    		case NO_LINE: return "NoLine";
    		case ESCAPE_AUTO_LINE: return "EscapeAutoLine";
    		case ESCAPE_AUTO_CURVE: return "EscapeAutoCurve";
    		case ESCAPE_CORNER_LINE: return "EscapeCornerLine";
    		case ESCAPE_CORNER_CURVE: return "EscapeCornerCurve";
    		case ESCAPE_SMOOTH_LINE: return "EscapeSmoothLine";
    		case ESCAPE_SMOOTH_CURVE: return "EscapeSmoothCurve";
    		case ESCAPE_SYMMETRIC_LINE: return "EscapeSymmetricLine";
    		case ESCAPE_SYMMETRIC_CURVE: return "EscapeSymmetricCurve";
    		case ESCAPE_FREE_FORM: return "EscapeFreeForm";
    		case FILL_COLOR: return "FillColor";
    		case LINE_COLOR: return "LineColor";
    		default: return "Unknown PathType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PathType name.
    /// </summary>
    public static /*PathType*/int fromName(String pathTypeName)
    {
    	if ("UNKNOWN".equals(pathTypeName)) return UNKNOWN;
    	if ("LINE_TO".equals(pathTypeName)) return LINE_TO;
    	if ("CURVE_TO".equals(pathTypeName)) return CURVE_TO;
    	if ("MOVE_TO".equals(pathTypeName)) return MOVE_TO;
    	if ("CLOSE".equals(pathTypeName)) return CLOSE;
    	if ("END".equals(pathTypeName)) return END;
    	if ("ESCAPE_BASE".equals(pathTypeName)) return ESCAPE_BASE;
    	if ("ESCAPE_EXTENSION".equals(pathTypeName)) return ESCAPE_EXTENSION;
    	if ("ANGLE_ELLIPSE_TO".equals(pathTypeName)) return ANGLE_ELLIPSE_TO;
    	if ("ANGLE_ELLIPSE".equals(pathTypeName)) return ANGLE_ELLIPSE;
    	if ("ARC_TO".equals(pathTypeName)) return ARC_TO;
    	if ("ARC".equals(pathTypeName)) return ARC;
    	if ("CLOCKWISE_ARC_TO".equals(pathTypeName)) return CLOCKWISE_ARC_TO;
    	if ("CLOCKWISE_ARC".equals(pathTypeName)) return CLOCKWISE_ARC;
    	if ("ELLIPTICAL_QUADRANT_X".equals(pathTypeName)) return ELLIPTICAL_QUADRANT_X;
    	if ("ELLIPTICAL_QUADRANT_Y".equals(pathTypeName)) return ELLIPTICAL_QUADRANT_Y;
    	if ("QUADRATIC_BEZIER".equals(pathTypeName)) return QUADRATIC_BEZIER;
    	if ("NO_FILL".equals(pathTypeName)) return NO_FILL;
    	if ("NO_LINE".equals(pathTypeName)) return NO_LINE;
    	if ("ESCAPE_AUTO_LINE".equals(pathTypeName)) return ESCAPE_AUTO_LINE;
    	if ("ESCAPE_AUTO_CURVE".equals(pathTypeName)) return ESCAPE_AUTO_CURVE;
    	if ("ESCAPE_CORNER_LINE".equals(pathTypeName)) return ESCAPE_CORNER_LINE;
    	if ("ESCAPE_CORNER_CURVE".equals(pathTypeName)) return ESCAPE_CORNER_CURVE;
    	if ("ESCAPE_SMOOTH_LINE".equals(pathTypeName)) return ESCAPE_SMOOTH_LINE;
    	if ("ESCAPE_SMOOTH_CURVE".equals(pathTypeName)) return ESCAPE_SMOOTH_CURVE;
    	if ("ESCAPE_SYMMETRIC_LINE".equals(pathTypeName)) return ESCAPE_SYMMETRIC_LINE;
    	if ("ESCAPE_SYMMETRIC_CURVE".equals(pathTypeName)) return ESCAPE_SYMMETRIC_CURVE;
    	if ("ESCAPE_FREE_FORM".equals(pathTypeName)) return ESCAPE_FREE_FORM;
    	if ("FILL_COLOR".equals(pathTypeName)) return FILL_COLOR;
    	if ("LINE_COLOR".equals(pathTypeName)) return LINE_COLOR;
    	throw new IllegalArgumentException("Unknown PathType name.");
    }
    
    /// <summary>
    /// Returns array of PathType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		LINE_TO,
    		CURVE_TO,
    		MOVE_TO,
    		CLOSE,
    		END,
    		ESCAPE_BASE,
    		ESCAPE_EXTENSION,
    		ANGLE_ELLIPSE_TO,
    		ANGLE_ELLIPSE,
    		ARC_TO,
    		ARC,
    		CLOCKWISE_ARC_TO,
    		CLOCKWISE_ARC,
    		ELLIPTICAL_QUADRANT_X,
    		ELLIPTICAL_QUADRANT_Y,
    		QUADRATIC_BEZIER,
    		NO_FILL,
    		NO_LINE,
    		ESCAPE_AUTO_LINE,
    		ESCAPE_AUTO_CURVE,
    		ESCAPE_CORNER_LINE,
    		ESCAPE_CORNER_CURVE,
    		ESCAPE_SMOOTH_LINE,
    		ESCAPE_SMOOTH_CURVE,
    		ESCAPE_SYMMETRIC_LINE,
    		ESCAPE_SYMMETRIC_CURVE,
    		ESCAPE_FREE_FORM,
    		FILL_COLOR,
    		LINE_COLOR,
    	};
    }

    public static final int length = 30;
}

