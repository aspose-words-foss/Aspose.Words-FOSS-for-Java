// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the variant for a gradient fill.
/// </summary>
/// <remarks>
/// Corresponds to the four variants on the Gradient tab in the Fill Effects dialog box in Word.
/// </remarks>
public /*enum*/ final class GradientVariant
{
    private GradientVariant(){}
    
    /// <summary>
    /// Gradient variant 'None'.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Gradient variant 1.
    /// </summary>
    public static final int VARIANT_1 = 1;

    /// <summary>
    /// Gradient variant 2.
    /// </summary>
    public static final int VARIANT_2 = 2;

    /// <summary>
    /// Gradient variant 3.
    /// </summary>
    /// <remarks>
    /// This variant is not applicable to gradient fill with the style <see cref="GradientStyle.FromCenter"/>,
    /// if object has markup language <see cref="ShapeMarkupLanguage.Vml"/>.
    /// </remarks>
    public static final int VARIANT_3 = 3;

    /// <summary>
    /// Gradient variant 4.
    /// </summary>
    /// <remarks>
    /// This variant is not applicable to gradient fill with the style <see cref="GradientStyle.FromCenter"/>,
    /// if object has markup language <see cref="ShapeMarkupLanguage.Vml"/>.
    /// </remarks>
    public static final int VARIANT_4 = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GradientVariant value.
    /// </summary>
    public static String getName(/*GradientVariant*/int gradientVariant)
    {
    	switch (gradientVariant)
    	{
    		case NONE: return "NONE";
    		case VARIANT_1: return "VARIANT_1";
    		case VARIANT_2: return "VARIANT_2";
    		case VARIANT_3: return "VARIANT_3";
    		case VARIANT_4: return "VARIANT_4";
    		default: return "Unknown GradientVariant value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GradientVariant value.
    /// </summary>
    public static String toString(/*GradientVariant*/int gradientVariant)
    {
    	switch (gradientVariant)
    	{
    		case NONE: return "None";
    		case VARIANT_1: return "Variant1";
    		case VARIANT_2: return "Variant2";
    		case VARIANT_3: return "Variant3";
    		case VARIANT_4: return "Variant4";
    		default: return "Unknown GradientVariant value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GradientVariant name.
    /// </summary>
    public static /*GradientVariant*/int fromName(String gradientVariantName)
    {
    	if ("NONE".equals(gradientVariantName)) return NONE;
    	if ("VARIANT_1".equals(gradientVariantName)) return VARIANT_1;
    	if ("VARIANT_2".equals(gradientVariantName)) return VARIANT_2;
    	if ("VARIANT_3".equals(gradientVariantName)) return VARIANT_3;
    	if ("VARIANT_4".equals(gradientVariantName)) return VARIANT_4;
    	throw new IllegalArgumentException("Unknown GradientVariant name.");
    }
    
    /// <summary>
    /// Returns array of GradientVariant values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		VARIANT_1,
    		VARIANT_2,
    		VARIANT_3,
    		VARIANT_4,
    	};
    }

    public static final int length = 5;
}

