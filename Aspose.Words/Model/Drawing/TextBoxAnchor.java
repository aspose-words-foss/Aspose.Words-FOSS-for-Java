// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies values used for shape text vertical alignment.
/// </summary>
/// <dev>
/// Do not renumber. The values are from the RTF spec.
/// </dev>
public /*enum*/ final class TextBoxAnchor
{
    private TextBoxAnchor(){}
    

    /// <summary>
    /// Text is aligned to the top of the textbox.
    /// </summary>
    public static final int TOP = 0;
    
    /// <summary>
    /// Text is aligned to the middle of the textbox.
    /// </summary>
    public static final int MIDDLE = 1;

    /// <summary>
    /// Text is aligned to the bottom of the textbox.
    /// </summary>
    public static final int BOTTOM = 2;

    /// <summary>
    /// Text is aligned to the top centered of the textbox.
    /// </summary>
    public static final int TOP_CENTERED = 3;

    /// <summary>
    /// Text is aligned to the middle centered of the textbox.
    /// </summary>
    public static final int MIDDLE_CENTERED = 4;

    /// <summary>
    /// Text is aligned to the bottom centered of the textbox.
    /// </summary>
    public static final int BOTTOM_CENTERED = 5;

    /// <summary>
    /// Text is aligned to the top baseline of the textbox.
    /// </summary>
    public static final int TOP_BASELINE = 6;

    /// <summary>
    /// Text is aligned to the bottom baseline of the textbox.
    /// </summary>
    public static final int BOTTOM_BASELINE = 7;

    /// <summary>
    /// Text is aligned to the top centered baseline of the textbox.
    /// </summary>
    public static final int TOP_CENTERED_BASELINE = 8;

    /// <summary>
    /// Text is aligned to the bottom centered baseline of the textbox.
    /// </summary>
    public static final int BOTTOM_CENTERED_BASELINE = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextBoxAnchor value.
    /// </summary>
    public static String getName(/*TextBoxAnchor*/int textBoxAnchor)
    {
    	switch (textBoxAnchor)
    	{
    		case TOP: return "TOP";
    		case MIDDLE: return "MIDDLE";
    		case BOTTOM: return "BOTTOM";
    		case TOP_CENTERED: return "TOP_CENTERED";
    		case MIDDLE_CENTERED: return "MIDDLE_CENTERED";
    		case BOTTOM_CENTERED: return "BOTTOM_CENTERED";
    		case TOP_BASELINE: return "TOP_BASELINE";
    		case BOTTOM_BASELINE: return "BOTTOM_BASELINE";
    		case TOP_CENTERED_BASELINE: return "TOP_CENTERED_BASELINE";
    		case BOTTOM_CENTERED_BASELINE: return "BOTTOM_CENTERED_BASELINE";
    		default: return "Unknown TextBoxAnchor value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextBoxAnchor value.
    /// </summary>
    public static String toString(/*TextBoxAnchor*/int textBoxAnchor)
    {
    	switch (textBoxAnchor)
    	{
    		case TOP: return "Top";
    		case MIDDLE: return "Middle";
    		case BOTTOM: return "Bottom";
    		case TOP_CENTERED: return "TopCentered";
    		case MIDDLE_CENTERED: return "MiddleCentered";
    		case BOTTOM_CENTERED: return "BottomCentered";
    		case TOP_BASELINE: return "TopBaseline";
    		case BOTTOM_BASELINE: return "BottomBaseline";
    		case TOP_CENTERED_BASELINE: return "TopCenteredBaseline";
    		case BOTTOM_CENTERED_BASELINE: return "BottomCenteredBaseline";
    		default: return "Unknown TextBoxAnchor value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextBoxAnchor name.
    /// </summary>
    public static /*TextBoxAnchor*/int fromName(String textBoxAnchorName)
    {
    	if ("TOP".equals(textBoxAnchorName)) return TOP;
    	if ("MIDDLE".equals(textBoxAnchorName)) return MIDDLE;
    	if ("BOTTOM".equals(textBoxAnchorName)) return BOTTOM;
    	if ("TOP_CENTERED".equals(textBoxAnchorName)) return TOP_CENTERED;
    	if ("MIDDLE_CENTERED".equals(textBoxAnchorName)) return MIDDLE_CENTERED;
    	if ("BOTTOM_CENTERED".equals(textBoxAnchorName)) return BOTTOM_CENTERED;
    	if ("TOP_BASELINE".equals(textBoxAnchorName)) return TOP_BASELINE;
    	if ("BOTTOM_BASELINE".equals(textBoxAnchorName)) return BOTTOM_BASELINE;
    	if ("TOP_CENTERED_BASELINE".equals(textBoxAnchorName)) return TOP_CENTERED_BASELINE;
    	if ("BOTTOM_CENTERED_BASELINE".equals(textBoxAnchorName)) return BOTTOM_CENTERED_BASELINE;
    	throw new IllegalArgumentException("Unknown TextBoxAnchor name.");
    }
    
    /// <summary>
    /// Returns array of TextBoxAnchor values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		MIDDLE,
    		BOTTOM,
    		TOP_CENTERED,
    		MIDDLE_CENTERED,
    		BOTTOM_CENTERED,
    		TOP_BASELINE,
    		BOTTOM_BASELINE,
    		TOP_CENTERED_BASELINE,
    		BOTTOM_CENTERED_BASELINE,
    	};
    }

    public static final int length = 10;
}

