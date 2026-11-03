// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/09/2021 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.MathUtil;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Helps to apply frame properties from our custom CSS properties to document model.
/// </summary>
class CssFrameStyleConverter
{
    static void toParagraphFormat(CssDeclarationCollection declarations, ParagraphFormat paragraphFormat)
    {
        String identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_WRAP_TYPE);
        if (StringUtil.hasChars(identifierValue))
            paragraphFormat.setWrapType(HtmlUtil.parseFrameWrapType(identifierValue));

        identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_RELATIVE_VERTICAL_POSITION);
        if (StringUtil.hasChars(identifierValue))
        {
            /*RelativeVerticalPosition*/int verticalPosition = 0;
            RefInt referenceToVerticalPosition = new RefInt(verticalPosition);
            boolean outRefCondition0 = HtmlUtil.parseFrameRelativeVerticalPosition(identifierValue, /*out*/ referenceToVerticalPosition);
            verticalPosition = referenceToVerticalPosition.get();
            if (outRefCondition0)
                paragraphFormat.setRelativeVerticalPosition(verticalPosition);
        }

        identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_RELATIVE_HORIZONTAL_POSITION);
        if (StringUtil.hasChars(identifierValue))
        {
            paragraphFormat.setRelativeHorizontalPosition(HtmlUtil.parseFrameRelativeHorizontalPosition(identifierValue));
        }

        identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_HEIGHT_RULE);
        if (StringUtil.hasChars(identifierValue))
        {
            paragraphFormat.setFrameHeightRule(HtmlUtil.parseFrameHeightRule(identifierValue));
        }

        identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_LOCK_ANCHOR);
        if (HtmlConstants.FRAME_LOCK_ANCHOR_LOCKED_VALUE.equals(msString.toLowerInvariant(identifierValue)))
        {
            paragraphFormat.setFrameLockAnchor(true);
        }

        // The frame top value may be positive or negative.
        double lengthValue = declarations.getLength(HtmlConstants.FRAME_TOP);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameTop(lengthValue);
        }
        else
        {
            identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_TOP);
            if (StringUtil.hasChars(identifierValue))
            {
                paragraphFormat.setVerticalAlignment(HtmlUtil.parseFrameVerticalAlignment(identifierValue));
            }
        }

        // The frame left value may be positive or negative.
        lengthValue = declarations.getLength(HtmlConstants.FRAME_LEFT);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameLeft(lengthValue);
        }
        else
        {
            identifierValue = declarations.getIdentifier(HtmlConstants.FRAME_LEFT);
            if (StringUtil.hasChars(identifierValue))
            {
                paragraphFormat.setHorizontalAlignment(HtmlUtil.parseFrameHorizontalAligment(identifierValue));
            }
        }

        // The valid range for frame width and height in MS Word (in editor) is from 0.5pt to 1584pt.
        // Since this isn't documented we accept all non-negative values here.
        lengthValue = declarations.getLength(HtmlConstants.FRAME_WIDTH);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameWidth(Math.max(0.0, lengthValue));
        }

        lengthValue = declarations.getLength(HtmlConstants.FRAME_HEIGHT);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameHeight(Math.max(0.0, lengthValue));
        }

        // The valid range for frame horizontal (vertical) distance from text in MS Word (in editor) is from 0.5pt to 1584pt.
        // Since this isn't documented we accept all non-negative values here.
        lengthValue = declarations.getLength(HtmlConstants.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameHorizontalDistanceFromText(Math.max(0.0, lengthValue));
        }

        lengthValue = declarations.getLength(HtmlConstants.FRAME_VERTICAL_DISTANCE_FROM_TEXT);
        if (!MathUtil.isMinValue(lengthValue))
        {
            paragraphFormat.setFrameVerticalDistanceFromText(Math.max(0.0, lengthValue));
        }
    }
}

