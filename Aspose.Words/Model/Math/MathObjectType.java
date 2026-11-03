// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/12/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies type of an Office Math object.
/// </summary>
public /*enum*/ final class MathObjectType
{
    private MathObjectType(){}
    
    /// <summary>
    /// Instance of mathematical text.
    /// </summary>
    public static final int O_MATH = 0; 
    
    /// <summary>
    /// Math paragraph, or display math zone, that contains one or more <see cref="OMath"/> elements that are in display mode.
    /// </summary>
    public static final int O_MATH_PARA = 1;

    /// <summary>
    /// Accent function, consisting of a base and a combining diacritical mark.
    /// </summary>
    public static final int ACCENT = 2;
    
    /// <summary>
    /// Bar function, consisting of a base argument and an overbar or underbar.
    /// </summary>
    public static final int BAR = 3;
    
    /// <summary>
    /// Border Box object, consisting of a border drawn around an instance of mathematical text (such as a formula or equation)
    /// </summary>
    public static final int BORDER_BOX = 4;
    
    /// <summary>
    /// Box object, which is used to group components of an equation or other instance of mathematical text.
    /// </summary>
    public static final int BOX = 5;
    
    /// <summary>
    /// Delimiter object, consisting of opening and closing delimiters (such as parentheses, 
    /// braces, brackets, and vertical bars), and an element contained inside.
    /// </summary>
    public static final int DELIMITER = 6;

    /// <summary>
    /// Degree in the mathematical radical.
    /// </summary>
    public static final int DEGREE = 7;
    
    /// <summary>
    /// Argument object. Encloses Office Math entities when they are used as arguments to other Office Math entities.
    /// </summary>
    public static final int ARGUMENT = 8;
    
    /// <summary>
    /// Array object, consisting of one or more equations, expressions, or other mathematical text runs 
    /// that can be vertically justified as a unit with respect to surrounding text on the line.
    /// </summary>
    public static final int ARRAY = 9;
    
    /// <summary>
    /// Fraction object, consisting of a numerator and denominator separated by a fraction bar.
    /// </summary>
    public static final int FRACTION = 10;
    
    /// <summary>
    /// Denominator of a fraction object.
    /// </summary>
    public static final int DENOMINATOR = 11;
    
    /// <summary>
    /// Numerator of the Fraction object.
    /// </summary>
    public static final int NUMERATOR = 12;
    
    /// <summary>
    /// Function-Apply object, which consists of a function name and an argument element acted upon.
    /// </summary>
    public static final int FUNCTION = 13;
    
    /// <summary>
    /// Name of the function. For example, function names are sin and cos.
    /// </summary>
    public static final int FUNCTION_NAME = 14;
    
    /// <summary>
    /// Group-Character object, consisting of a character drawn above or below text, often 
    /// with the purpose of visually grouping items
    /// </summary>
    public static final int GROUP_CHARACTER = 15;
    
    /// <summary>
    /// Lower limit of the <see cref="LowerLimit"/> object and 
    /// the upper limit of the <see cref="UpperLimit"/> function.
    /// </summary>
    public static final int LIMIT = 16;
    
    /// <summary>
    /// Lower-Limit object, consisting of text on the baseline and reduced-size text immediately below it.
    /// </summary>
    public static final int LOWER_LIMIT = 17;
    
    /// <summary>
    /// Upper-Limit object, consisting of text on the baseline and reduced-size text immediately above it.
    /// </summary>
    public static final int UPPER_LIMIT = 18;
    
    /// <summary>
    /// Matrix object, consisting of one or more elements laid out in one or more rows and one or more columns.
    /// </summary>
    public static final int MATRIX = 19;
    
    /// <summary>
    /// Single row of the matrix.
    /// </summary>
    public static final int MATRIX_ROW = 20;
    
    /// <summary>
    /// N-ary object, consisting of an n-ary object, a base (or operand), and optional upper and lower limits.
    /// </summary>
    public static final int N_ARY = 21;
    
    /// <summary>
    /// Phantom object.
    /// </summary>
    public static final int PHANTOM = 22;
    
    /// <summary>
    /// Radical object, consisting of a radical, a base element, and an optional degree .
    /// </summary>
    public static final int RADICAL = 23;

    /// <summary>
    /// Subscript of the object that can have subscript part.
    /// </summary>
    public static final int SUBSCRIPT_PART = 24;

    /// <summary>
    /// Superscript of the superscript object.
    /// </summary>
    public static final int SUPERSCRIPT_PART = 25;
    
    /// <summary>
    /// Pre-Sub-Superscript object, which consists of a base element and a subscript and superscript placed to the left of the base.
    /// </summary>
    public static final int PRE_SUB_SUPERSCRIPT = 26;
    
    /// <summary>
    /// Subscript object, which consists of a base element and a reduced-size script placed below and to the right.
    /// </summary>
    public static final int SUBSCRIPT = 27;
    
    /// <summary>
    /// Sub-superscript object, which consists of a base element, a reduced-size script placed below and to the right, and a reduced-size script placed above and to the right.
    /// </summary>
    public static final int SUB_SUPERSCRIPT = 28;

    /// <summary>
    /// Superscript object, which consists of a base element and a reduced-size script placed above and to the right.
    /// </summary>
    public static final int SUPERSCRIPT = 29;

    /// <summary>
    /// Type of object is not specified.
    /// </summary>
    public static final int NONE = 30;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathObjectType value.
    /// </summary>
    public static String getName(/*MathObjectType*/int mathObjectType)
    {
    	switch (mathObjectType)
    	{
    		case O_MATH: return "O_MATH";
    		case O_MATH_PARA: return "O_MATH_PARA";
    		case ACCENT: return "ACCENT";
    		case BAR: return "BAR";
    		case BORDER_BOX: return "BORDER_BOX";
    		case BOX: return "BOX";
    		case DELIMITER: return "DELIMITER";
    		case DEGREE: return "DEGREE";
    		case ARGUMENT: return "ARGUMENT";
    		case ARRAY: return "ARRAY";
    		case FRACTION: return "FRACTION";
    		case DENOMINATOR: return "DENOMINATOR";
    		case NUMERATOR: return "NUMERATOR";
    		case FUNCTION: return "FUNCTION";
    		case FUNCTION_NAME: return "FUNCTION_NAME";
    		case GROUP_CHARACTER: return "GROUP_CHARACTER";
    		case LIMIT: return "LIMIT";
    		case LOWER_LIMIT: return "LOWER_LIMIT";
    		case UPPER_LIMIT: return "UPPER_LIMIT";
    		case MATRIX: return "MATRIX";
    		case MATRIX_ROW: return "MATRIX_ROW";
    		case N_ARY: return "N_ARY";
    		case PHANTOM: return "PHANTOM";
    		case RADICAL: return "RADICAL";
    		case SUBSCRIPT_PART: return "SUBSCRIPT_PART";
    		case SUPERSCRIPT_PART: return "SUPERSCRIPT_PART";
    		case PRE_SUB_SUPERSCRIPT: return "PRE_SUB_SUPERSCRIPT";
    		case SUBSCRIPT: return "SUBSCRIPT";
    		case SUB_SUPERSCRIPT: return "SUB_SUPERSCRIPT";
    		case SUPERSCRIPT: return "SUPERSCRIPT";
    		case NONE: return "NONE";
    		default: return "Unknown MathObjectType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathObjectType value.
    /// </summary>
    public static String toString(/*MathObjectType*/int mathObjectType)
    {
    	switch (mathObjectType)
    	{
    		case O_MATH: return "OMath";
    		case O_MATH_PARA: return "OMathPara";
    		case ACCENT: return "Accent";
    		case BAR: return "Bar";
    		case BORDER_BOX: return "BorderBox";
    		case BOX: return "Box";
    		case DELIMITER: return "Delimiter";
    		case DEGREE: return "Degree";
    		case ARGUMENT: return "Argument";
    		case ARRAY: return "Array";
    		case FRACTION: return "Fraction";
    		case DENOMINATOR: return "Denominator";
    		case NUMERATOR: return "Numerator";
    		case FUNCTION: return "Function";
    		case FUNCTION_NAME: return "FunctionName";
    		case GROUP_CHARACTER: return "GroupCharacter";
    		case LIMIT: return "Limit";
    		case LOWER_LIMIT: return "LowerLimit";
    		case UPPER_LIMIT: return "UpperLimit";
    		case MATRIX: return "Matrix";
    		case MATRIX_ROW: return "MatrixRow";
    		case N_ARY: return "NAry";
    		case PHANTOM: return "Phantom";
    		case RADICAL: return "Radical";
    		case SUBSCRIPT_PART: return "SubscriptPart";
    		case SUPERSCRIPT_PART: return "SuperscriptPart";
    		case PRE_SUB_SUPERSCRIPT: return "PreSubSuperscript";
    		case SUBSCRIPT: return "Subscript";
    		case SUB_SUPERSCRIPT: return "SubSuperscript";
    		case SUPERSCRIPT: return "Superscript";
    		case NONE: return "None";
    		default: return "Unknown MathObjectType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathObjectType name.
    /// </summary>
    public static /*MathObjectType*/int fromName(String mathObjectTypeName)
    {
    	if ("O_MATH".equals(mathObjectTypeName)) return O_MATH;
    	if ("O_MATH_PARA".equals(mathObjectTypeName)) return O_MATH_PARA;
    	if ("ACCENT".equals(mathObjectTypeName)) return ACCENT;
    	if ("BAR".equals(mathObjectTypeName)) return BAR;
    	if ("BORDER_BOX".equals(mathObjectTypeName)) return BORDER_BOX;
    	if ("BOX".equals(mathObjectTypeName)) return BOX;
    	if ("DELIMITER".equals(mathObjectTypeName)) return DELIMITER;
    	if ("DEGREE".equals(mathObjectTypeName)) return DEGREE;
    	if ("ARGUMENT".equals(mathObjectTypeName)) return ARGUMENT;
    	if ("ARRAY".equals(mathObjectTypeName)) return ARRAY;
    	if ("FRACTION".equals(mathObjectTypeName)) return FRACTION;
    	if ("DENOMINATOR".equals(mathObjectTypeName)) return DENOMINATOR;
    	if ("NUMERATOR".equals(mathObjectTypeName)) return NUMERATOR;
    	if ("FUNCTION".equals(mathObjectTypeName)) return FUNCTION;
    	if ("FUNCTION_NAME".equals(mathObjectTypeName)) return FUNCTION_NAME;
    	if ("GROUP_CHARACTER".equals(mathObjectTypeName)) return GROUP_CHARACTER;
    	if ("LIMIT".equals(mathObjectTypeName)) return LIMIT;
    	if ("LOWER_LIMIT".equals(mathObjectTypeName)) return LOWER_LIMIT;
    	if ("UPPER_LIMIT".equals(mathObjectTypeName)) return UPPER_LIMIT;
    	if ("MATRIX".equals(mathObjectTypeName)) return MATRIX;
    	if ("MATRIX_ROW".equals(mathObjectTypeName)) return MATRIX_ROW;
    	if ("N_ARY".equals(mathObjectTypeName)) return N_ARY;
    	if ("PHANTOM".equals(mathObjectTypeName)) return PHANTOM;
    	if ("RADICAL".equals(mathObjectTypeName)) return RADICAL;
    	if ("SUBSCRIPT_PART".equals(mathObjectTypeName)) return SUBSCRIPT_PART;
    	if ("SUPERSCRIPT_PART".equals(mathObjectTypeName)) return SUPERSCRIPT_PART;
    	if ("PRE_SUB_SUPERSCRIPT".equals(mathObjectTypeName)) return PRE_SUB_SUPERSCRIPT;
    	if ("SUBSCRIPT".equals(mathObjectTypeName)) return SUBSCRIPT;
    	if ("SUB_SUPERSCRIPT".equals(mathObjectTypeName)) return SUB_SUPERSCRIPT;
    	if ("SUPERSCRIPT".equals(mathObjectTypeName)) return SUPERSCRIPT;
    	if ("NONE".equals(mathObjectTypeName)) return NONE;
    	throw new IllegalArgumentException("Unknown MathObjectType name.");
    }
    
    /// <summary>
    /// Returns array of MathObjectType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		O_MATH,
    		O_MATH_PARA,
    		ACCENT,
    		BAR,
    		BORDER_BOX,
    		BOX,
    		DELIMITER,
    		DEGREE,
    		ARGUMENT,
    		ARRAY,
    		FRACTION,
    		DENOMINATOR,
    		NUMERATOR,
    		FUNCTION,
    		FUNCTION_NAME,
    		GROUP_CHARACTER,
    		LIMIT,
    		LOWER_LIMIT,
    		UPPER_LIMIT,
    		MATRIX,
    		MATRIX_ROW,
    		N_ARY,
    		PHANTOM,
    		RADICAL,
    		SUBSCRIPT_PART,
    		SUPERSCRIPT_PART,
    		PRE_SUB_SUPERSCRIPT,
    		SUBSCRIPT,
    		SUB_SUPERSCRIPT,
    		SUPERSCRIPT,
    		NONE,
    	};
    }

    public static final int length = 31;
}

