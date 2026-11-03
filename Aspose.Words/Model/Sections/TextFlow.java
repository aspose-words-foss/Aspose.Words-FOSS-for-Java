// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies orientation of text in a section.
/// This has similar values, but not same as <see cref="TextOrientation"/>.
/// </summary>
/*enum*/ final class TextFlow
{
    private TextFlow(){}
    
    /// <summary>
    /// Text flows left to right and top to bottom (lr-tb).
    /// 
    /// Can be selected in MS Word.
    /// </summary>
    public static final int HORIZONTAL = 0;
    /// <summary>
    /// Text flows top to bottom and right to left, vertical (tb-rl).
    /// 
    /// Far East characters appear vertical, other text is rotated 90 degrees to the right 
    /// to appear from top to bottom. 
    /// 
    /// Can be selected in MS Word.
    /// </summary>
    public static final int VERTICAL = 1;
    /// <summary>
    /// Cannot be selected in MS Word.
    /// MS says: Text flows left to right and bottom to top (lr-bt).
    /// </summary>
    public static final int LR_BT = 2;
    /// <summary>
    /// Cannot be selected in MS Word.
    /// MS says: Text flows right to left and top to bottom (rl-tb).
    /// </summary>
    public static final int RL_TB = 3;
    /// <summary>
    /// Text flows left to right and top to bottom, vertical (lr-tb-v).
    /// 
    /// Text is arranged horizontally, but Far East characters are rotated 90 degrees to the left.
    /// 
    /// Can be selected in MS Word.
    /// </summary>
    public static final int HORIZONTAL_ROTATED_FAR_EAST = 4;
    /// <summary>
    /// Cannot be selected in MS Word.
    /// MS says: Text flows vertically, non-vertical font (tb-rl-v)?
    /// </summary>
    public static final int V_NON_V = 5;
    /// <summary>
    /// Text flows top to bottom and left to right, vertical (tb-lr-v).
    /// 
    /// Text is arranged vertically, but Far East characters are rotated 90 degrees to the left.
    /// 
    /// Cannot be selected in MS Word.
    /// </summary>
    public static final int VERTICAL_ROTATED_FAR_EAST = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TextFlow value.
    /// </summary>
    public static String getName(/*TextFlow*/int textFlow)
    {
    	switch (textFlow)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		case LR_BT: return "LR_BT";
    		case RL_TB: return "RL_TB";
    		case HORIZONTAL_ROTATED_FAR_EAST: return "HORIZONTAL_ROTATED_FAR_EAST";
    		case V_NON_V: return "V_NON_V";
    		case VERTICAL_ROTATED_FAR_EAST: return "VERTICAL_ROTATED_FAR_EAST";
    		default: return "Unknown TextFlow value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TextFlow value.
    /// </summary>
    public static String toString(/*TextFlow*/int textFlow)
    {
    	switch (textFlow)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		case LR_BT: return "LrBt";
    		case RL_TB: return "RlTb";
    		case HORIZONTAL_ROTATED_FAR_EAST: return "HorizontalRotatedFarEast";
    		case V_NON_V: return "VNonV";
    		case VERTICAL_ROTATED_FAR_EAST: return "VerticalRotatedFarEast";
    		default: return "Unknown TextFlow value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TextFlow name.
    /// </summary>
    public static /*TextFlow*/int fromName(String textFlowName)
    {
    	if ("HORIZONTAL".equals(textFlowName)) return HORIZONTAL;
    	if ("VERTICAL".equals(textFlowName)) return VERTICAL;
    	if ("LR_BT".equals(textFlowName)) return LR_BT;
    	if ("RL_TB".equals(textFlowName)) return RL_TB;
    	if ("HORIZONTAL_ROTATED_FAR_EAST".equals(textFlowName)) return HORIZONTAL_ROTATED_FAR_EAST;
    	if ("V_NON_V".equals(textFlowName)) return V_NON_V;
    	if ("VERTICAL_ROTATED_FAR_EAST".equals(textFlowName)) return VERTICAL_ROTATED_FAR_EAST;
    	throw new IllegalArgumentException("Unknown TextFlow name.");
    }
    
    /// <summary>
    /// Returns array of TextFlow values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		VERTICAL,
    		LR_BT,
    		RL_TB,
    		HORIZONTAL_ROTATED_FAR_EAST,
    		V_NON_V,
    		VERTICAL_ROTATED_FAR_EAST,
    	};
    }

    public static final int length = 7;
}

