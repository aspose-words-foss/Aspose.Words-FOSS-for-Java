// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/08/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Operation code for a formula of a shape.
/// 2.2.58 SG in the DOC SPEC.
/// </summary>
/*enum*/ final class Operation
{
    private Operation(){}
    
    /// <summary>
    /// Addition and subtraction. param1 + param2 – param3
    /// </summary>
    public static final int SUM = 0;
    /// <summary>
    /// Multiplication and division. (param1*param2)/param3
    /// </summary>
    public static final int PROD = 1;
    /// <summary>
    /// Simple average. (param1+param2)/2
    /// </summary>
    public static final int MID = 2;
    /// <summary>
    /// Absolute value. abs(param1)
    /// </summary>
    public static final int ABS = 3;
    /// <summary>
    /// The lesser of two values. min(param1, param2)
    /// </summary>
    public static final int MIN = 4;
    /// <summary>
    /// The greater of two values. max(param1, param2)
    /// </summary>
    public static final int MAX = 5;
    /// <summary>
    /// Conditional selection. param1 > 0 ? param2 : param3
    /// </summary>
    public static final int IF = 6;
    /// <summary>
    /// Modulus. sqrt(param1^2 + param2^2 + param3^2)
    /// </summary>
    public static final int MOD = 7;
    /// <summary>
    /// Trigonometric arc tangent of a quotient. Result is a FixedPoint of the angles in degrees.
    /// atan2(param2,param1)
    /// </summary>
    public static final int ATAN_2 = 8;
    /// <summary>
    /// Sine. param2 is a FixedPoint of angles in degrees.
    /// param1*sin(param2)
    /// </summary>
    public static final int SIN = 9;
    /// <summary>
    /// Cosine. param2 is a FixedPoint of angles in degrees.
    /// param1*cos(param2)
    /// </summary>
    public static final int COS = 10;
    /// <summary>
    /// Cosine and atan2 in one formula. param1*cos(atan2(param3,param2))
    /// </summary>
    public static final int COS_ATAN_2 = 11;
    /// <summary>
    /// Sine and atan2 in one formula. param1*sin(atan2(param3,param2))
    /// </summary>
    public static final int SIN_ATAN_2 = 12;
    /// <summary>
    /// Square root. sqrt(param1)
    /// </summary>
    public static final int SQRT = 13;
    /// <summary>
    /// Add an angle in degrees as a FixedPoint to two other angles specified in degrees. 
    /// param2 and param3 are scaled by 2^16. param1 + param2*2^16 - param3*2^16
    /// </summary>
    public static final int SUM_ANGLE = 14;
    /// <summary>
    /// The eccentricity formula for an ellipse, where param1 is the length of the semiminor 
    /// axis and param2 is the length of the semimajor axis. param3*sqrt(1-(param1/param2)^2)
    /// </summary>
    public static final int ELLIPSE = 15;
    /// <summary>
    /// Tangent. param2 is a FixedPoint of angles in degrees. param1*tan(param2)
    /// </summary>
    public static final int TAN = 16;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer Operation value.
    /// </summary>
    public static String getName(/*Operation*/int operation)
    {
    	switch (operation)
    	{
    		case SUM: return "SUM";
    		case PROD: return "PROD";
    		case MID: return "MID";
    		case ABS: return "ABS";
    		case MIN: return "MIN";
    		case MAX: return "MAX";
    		case IF: return "IF";
    		case MOD: return "MOD";
    		case ATAN_2: return "ATAN_2";
    		case SIN: return "SIN";
    		case COS: return "COS";
    		case COS_ATAN_2: return "COS_ATAN_2";
    		case SIN_ATAN_2: return "SIN_ATAN_2";
    		case SQRT: return "SQRT";
    		case SUM_ANGLE: return "SUM_ANGLE";
    		case ELLIPSE: return "ELLIPSE";
    		case TAN: return "TAN";
    		default: return "Unknown Operation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer Operation value.
    /// </summary>
    public static String toString(/*Operation*/int operation)
    {
    	switch (operation)
    	{
    		case SUM: return "Sum";
    		case PROD: return "Prod";
    		case MID: return "Mid";
    		case ABS: return "Abs";
    		case MIN: return "Min";
    		case MAX: return "Max";
    		case IF: return "If";
    		case MOD: return "Mod";
    		case ATAN_2: return "Atan2";
    		case SIN: return "Sin";
    		case COS: return "Cos";
    		case COS_ATAN_2: return "CosAtan2";
    		case SIN_ATAN_2: return "SinAtan2";
    		case SQRT: return "Sqrt";
    		case SUM_ANGLE: return "SumAngle";
    		case ELLIPSE: return "Ellipse";
    		case TAN: return "Tan";
    		default: return "Unknown Operation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by Operation name.
    /// </summary>
    public static /*Operation*/int fromName(String operationName)
    {
    	if ("SUM".equals(operationName)) return SUM;
    	if ("PROD".equals(operationName)) return PROD;
    	if ("MID".equals(operationName)) return MID;
    	if ("ABS".equals(operationName)) return ABS;
    	if ("MIN".equals(operationName)) return MIN;
    	if ("MAX".equals(operationName)) return MAX;
    	if ("IF".equals(operationName)) return IF;
    	if ("MOD".equals(operationName)) return MOD;
    	if ("ATAN_2".equals(operationName)) return ATAN_2;
    	if ("SIN".equals(operationName)) return SIN;
    	if ("COS".equals(operationName)) return COS;
    	if ("COS_ATAN_2".equals(operationName)) return COS_ATAN_2;
    	if ("SIN_ATAN_2".equals(operationName)) return SIN_ATAN_2;
    	if ("SQRT".equals(operationName)) return SQRT;
    	if ("SUM_ANGLE".equals(operationName)) return SUM_ANGLE;
    	if ("ELLIPSE".equals(operationName)) return ELLIPSE;
    	if ("TAN".equals(operationName)) return TAN;
    	throw new IllegalArgumentException("Unknown Operation name.");
    }
    
    /// <summary>
    /// Returns array of Operation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SUM,
    		PROD,
    		MID,
    		ABS,
    		MIN,
    		MAX,
    		IF,
    		MOD,
    		ATAN_2,
    		SIN,
    		COS,
    		COS_ATAN_2,
    		SIN_ATAN_2,
    		SQRT,
    		SUM_ANGLE,
    		ELLIPSE,
    		TAN,
    	};
    }

    public static final int length = 17;
}

