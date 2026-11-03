// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the compound line style of a <see cref="Shape"/>.
/// </summary>
/// <seealso cref="Stroke.LineStyle"/>
/// <dev>
/// This enum name violates the rule "enum name equals property name", but I have to avoid
/// collision between Aspose.Words.LineStyle and this line style because otherwise it will
/// be too hard to port to Java as all types are in the same namespace in Java.
/// </dev>
public /*enum*/ final class ShapeLineStyle
{
    private ShapeLineStyle(){}
    
    /// <summary>
    /// Single line.
    /// </summary>
    public static final int SINGLE = 0;
    /// <summary>
    /// Double lines of equal width.
    /// </summary>
    public static final int DOUBLE = 1;
    /// <summary>
    /// Double lines, one thick, one thin.
    /// </summary>
    public static final int THICK_THIN = 2;
    /// <summary>
    /// Double lines, one thin, one thick.
    /// </summary>
    public static final int THIN_THICK = 3;
    /// <summary>
    /// Three lines, thin, thick, thin.
    /// </summary>
    public static final int TRIPLE = 4;
    /// <summary>
    /// Default value is <see cref="Single"/>.
    /// </summary>
    public static final int DEFAULT = SINGLE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShapeLineStyle value.
    /// </summary>
    public static String getName(/*ShapeLineStyle*/int shapeLineStyle)
    {
    	switch (shapeLineStyle)
    	{
    		case SINGLE:
    		/*case DEFAULT:*/
    			return "SINGLE" + " | " + "DEFAULT";
    		case DOUBLE: return "DOUBLE";
    		case THICK_THIN: return "THICK_THIN";
    		case THIN_THICK: return "THIN_THICK";
    		case TRIPLE: return "TRIPLE";
    		default: return "Unknown ShapeLineStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShapeLineStyle value.
    /// </summary>
    public static String toString(/*ShapeLineStyle*/int shapeLineStyle)
    {
    	switch (shapeLineStyle)
    	{
    		case SINGLE:
    		/*case DEFAULT:*/
    			return "Single" + " | " + "Default";
    		case DOUBLE: return "Double";
    		case THICK_THIN: return "ThickThin";
    		case THIN_THICK: return "ThinThick";
    		case TRIPLE: return "Triple";
    		default: return "Unknown ShapeLineStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ShapeLineStyle name.
    /// </summary>
    public static /*ShapeLineStyle*/int fromName(String shapeLineStyleName)
    {
    	if ("SINGLE".equals(shapeLineStyleName)) return SINGLE;
    	if ("DOUBLE".equals(shapeLineStyleName)) return DOUBLE;
    	if ("THICK_THIN".equals(shapeLineStyleName)) return THICK_THIN;
    	if ("THIN_THICK".equals(shapeLineStyleName)) return THIN_THICK;
    	if ("TRIPLE".equals(shapeLineStyleName)) return TRIPLE;
    	if ("DEFAULT".equals(shapeLineStyleName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown ShapeLineStyle name.");
    }
    
    /// <summary>
    /// Returns array of ShapeLineStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SINGLE,
    		DOUBLE,
    		THICK_THIN,
    		THIN_THICK,
    		TRIPLE,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}

