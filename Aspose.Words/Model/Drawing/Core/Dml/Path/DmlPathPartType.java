// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/01/2014 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/*enum*/ final class DmlPathPartType
{
    private DmlPathPartType(){}
    
    public static final int ARC_TO = 0;
    public static final int CLOSE = 1;
    public static final int CUBIC_BEZIER_TO = 2;
    public static final int LINE_TO = 3;
    public static final int MOVE_TO = 4;
    public static final int QUADRATIC_BEZIER_TO = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPathPartType value.
    /// </summary>
    public static String getName(/*DmlPathPartType*/int dmlPathPartType)
    {
    	switch (dmlPathPartType)
    	{
    		case ARC_TO: return "ARC_TO";
    		case CLOSE: return "CLOSE";
    		case CUBIC_BEZIER_TO: return "CUBIC_BEZIER_TO";
    		case LINE_TO: return "LINE_TO";
    		case MOVE_TO: return "MOVE_TO";
    		case QUADRATIC_BEZIER_TO: return "QUADRATIC_BEZIER_TO";
    		default: return "Unknown DmlPathPartType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPathPartType value.
    /// </summary>
    public static String toString(/*DmlPathPartType*/int dmlPathPartType)
    {
    	switch (dmlPathPartType)
    	{
    		case ARC_TO: return "ArcTo";
    		case CLOSE: return "Close";
    		case CUBIC_BEZIER_TO: return "CubicBezierTo";
    		case LINE_TO: return "LineTo";
    		case MOVE_TO: return "MoveTo";
    		case QUADRATIC_BEZIER_TO: return "QuadraticBezierTo";
    		default: return "Unknown DmlPathPartType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPathPartType name.
    /// </summary>
    public static /*DmlPathPartType*/int fromName(String dmlPathPartTypeName)
    {
    	if ("ARC_TO".equals(dmlPathPartTypeName)) return ARC_TO;
    	if ("CLOSE".equals(dmlPathPartTypeName)) return CLOSE;
    	if ("CUBIC_BEZIER_TO".equals(dmlPathPartTypeName)) return CUBIC_BEZIER_TO;
    	if ("LINE_TO".equals(dmlPathPartTypeName)) return LINE_TO;
    	if ("MOVE_TO".equals(dmlPathPartTypeName)) return MOVE_TO;
    	if ("QUADRATIC_BEZIER_TO".equals(dmlPathPartTypeName)) return QUADRATIC_BEZIER_TO;
    	throw new IllegalArgumentException("Unknown DmlPathPartType name.");
    }
    
    /// <summary>
    /// Returns array of DmlPathPartType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ARC_TO,
    		CLOSE,
    		CUBIC_BEZIER_TO,
    		LINE_TO,
    		MOVE_TO,
    		QUADRATIC_BEZIER_TO,
    	};
    }

    public static final int length = 6;
}

