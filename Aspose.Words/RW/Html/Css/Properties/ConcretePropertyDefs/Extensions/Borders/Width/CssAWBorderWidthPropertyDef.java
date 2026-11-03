// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2020 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Implements '-aw-border-width' CSS extended border property. 
/// This property is a shorthand property for setting '-aw-border-top-width', '-aw-border-right-width', 
/// '-aw-border-bottom-width', and '-aw-border-left-width' at the same place in the style sheet.
/// </summary>
/// <remarks>
/// If there is only one component value, it applies to all sides. If there are two values, 
/// the top and bottom borders are set to the first value and the right and left are set to the second. 
/// If there are three values, the top is set to the first value, the left and right are set to 
/// the second, and the bottom is set to the third. If there are four values, they apply to the top, 
/// right, bottom, and left, respectively.
/// Syntax:
///      -aw-border-width: width{1,4}
/// </remarks>
class CssAWBorderWidthPropertyDef extends CssShorthand14PropertyDefBase
{
    CssAWBorderWidthPropertyDef()
    {
    	super(
              HtmlConstants.ASPOSE_BORDER_WIDTH,
              HtmlConstants.ASPOSE_BORDER_TOP_WIDTH,
              HtmlConstants.ASPOSE_BORDER_RIGHT_WIDTH,
              HtmlConstants.ASPOSE_BORDER_BOTTOM_WIDTH,
              HtmlConstants.ASPOSE_BORDER_LEFT_WIDTH);
	
        // Empty constructor.
    }
}

