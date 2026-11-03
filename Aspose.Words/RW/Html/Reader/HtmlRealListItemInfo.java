// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;


/// <summary>
/// Represents real list item. 
/// </summary>
class HtmlRealListItemInfo extends HtmlListItemInfo
{
    HtmlRealListItemInfo(
        Paragraph paragraph,
        CssLength wholeListLeftIndent,
        CssLength leftIndent,
        CssLength padding,
        CssLength markerToTextDistance,
        boolean isMarkerPositionInside,
        boolean isListItemNoneListStyle)
    {
    	super(
              paragraph,
              wholeListLeftIndent,
              leftIndent,
              padding,
              markerToTextDistance,
              isMarkerPositionInside,
              isListItemNoneListStyle);
	
    }

    /*override*/ CssLength getTabStop(
        CssLength leftIndent,
        CssLength textIndent,
        ListLevel listLevel)
    {
        if (isMarkerPositionInside() && (listLevel != null))
        {
            if (listLevel.getTrailingCharacter() == ListTrailingCharacter.TAB)
            {
                return CssLength.op_Addition(CssLength.op_Addition(CssLength.op_Addition(getWholeListLeftIndent(), leftIndent), getMarkerToTextDistance()), textIndent);
            }
        }
        else if (!MathUtil.isZero(textIndent.getValue()))
        {
            return CssLength.op_Addition(CssLength.op_Addition(getWholeListLeftIndent(), leftIndent), textIndent);
        }

        return null;
    }
}

