// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/03/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents list item imported from :before pseudo-element. 
/// </summary>
class HtmlPseudoElementListItemInfo extends HtmlListItemInfo
{
    HtmlPseudoElementListItemInfo(
        Paragraph paragraph,
        CssLength wholeListLeftIndent,
        CssLength leftIndent,
        CssLength bulletPaddingFromPseudoElement,
        CssLength markerToTextDistance,
        boolean isMarkerPositionInside,
        boolean isListItemNoneListStyle)
    {
    	super(
              paragraph,
              wholeListLeftIndent,
              leftIndent,
              bulletPaddingFromPseudoElement,
              markerToTextDistance,
              isMarkerPositionInside,
              isListItemNoneListStyle);
	
        // Empty constructor.
    }

    /*override*/ CssLength getTabStop(
        CssLength leftIndent,
        CssLength textIndent,
        ListLevel listLevel)
    {
        if (!isMarkerPositionInside())
            return null;

        CssLength textLeftIndent = super.getLeftIndent(leftIndent, textIndent);
        return CssLength.op_Addition(CssLength.op_Addition(CssLength.op_Addition(textLeftIndent, getPadding()), getMarkerToTextDistance()), textIndent);
    }

    /*override*/ CssLength getLeftIndent(CssLength leftIndent, CssLength textIndent)
    {
        CssLength textLeftIndent = super.getLeftIndent(leftIndent, textIndent);
        if (isMarkerPositionInside())
            return textLeftIndent;

        return CssLength.op_Addition(CssLength.op_Addition(CssLength.op_Addition(textLeftIndent, getPadding()), getMarkerToTextDistance()), textIndent);
    }
}

