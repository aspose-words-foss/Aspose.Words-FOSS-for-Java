// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies Markup language used for the shape.
/// </summary>
// JAVA: byte backing type added to resolve Shape's ctors signature conflict in java.
public /*enum*/ final class ShapeMarkupLanguage /*: byte*/
{
    private ShapeMarkupLanguage(){}
    
    /// <summary>
    /// Drawing Markup Language is used to define the shape.
    /// </summary>
    /// <remarks>
    /// This is the new standard for drawing for Office Open XML which has appeared first in ECMA-376 1st edition (2006), first appeared in MS Word 2007.
    /// </remarks>
    public static final byte DML = (byte)0;

    /// <summary>
    /// Vector Markup Language is used to define the shape. 
    /// </summary>
    /// <remarks>A deprecated format included in Office Open XML for legacy reasons only.</remarks>
    public static final byte VML = (byte)1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShapeMarkupLanguage value.
    /// </summary>
    public static String getName(/*ShapeMarkupLanguage*/byte shapeMarkupLanguage)
    {
    	if (DML == shapeMarkupLanguage) return "DML";
    	if (VML == shapeMarkupLanguage) return "VML";
    	return "Unknown ShapeMarkupLanguage value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShapeMarkupLanguage value.
    /// </summary>
    public static String toString(/*ShapeMarkupLanguage*/byte shapeMarkupLanguage)
    {
    	if (DML == shapeMarkupLanguage) return "Dml";
    	if (VML == shapeMarkupLanguage) return "Vml";
    	return "Unknown ShapeMarkupLanguage value.";
    }
    
    /// <summary>
    /// Returns integer representation by ShapeMarkupLanguage name.
    /// </summary>
    public static /*ShapeMarkupLanguage*/byte fromName(String shapeMarkupLanguageName)
    {
    	if ("DML".equals(shapeMarkupLanguageName)) return DML;
    	if ("VML".equals(shapeMarkupLanguageName)) return VML;
    	throw new IllegalArgumentException("Unknown ShapeMarkupLanguage name.");
    }
    
    /// <summary>
    /// Returns array of ShapeMarkupLanguage values.
    /// </summary>
    public static byte[] getValues()
    {
    	return new byte[]
    	{
    		DML,
    		VML,
    	};
    }

    public static final int length = 2;
}

