// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;

/// <summary>
/// Applies CSS formatting to paragraphs. Uses formatting rules that are different from what MS Word uses but produce
/// better looking results and are more confomant to the CSS and HTML specifications.
/// </summary>
class ParagraphFormatterHtmlRules extends ParagraphFormatter
{
    ParagraphFormatterHtmlRules(boolean useHtmlBlocks)
    {
        mUseHtmlBlocks = useHtmlBlocks;
    }

    protected /*override*/ void applySpecialFormatting(ParagraphFormat paragraphFormat, CssStyleTracker cssStyleTracker)
    {
        CssLineHeightStyleConverter.toParagraphFormat(cssStyleTracker.getElementDeclarations(), paragraphFormat, false);
        CssMargins physicalMargins;

        // WORDSNET-16334 If we use HtmlBlock nodes to store borders and margins of block-level HTML elements,
        // we don't need the box model and we apply only the current element margins directly to the current paragraph.
        if (mUseHtmlBlocks)
        {
            double left = getMargin(cssStyleTracker.getElementDeclarations(), "margin-left");
            double right = getMargin(cssStyleTracker.getElementDeclarations(), "margin-right");
            double top = getMargin(cssStyleTracker.getElementDeclarations(), "margin-top");
            double bottom = getMargin(cssStyleTracker.getElementDeclarations(), "margin-bottom");
            physicalMargins = new CssMargins();

            if (!MathUtil.isZero(left))
                physicalMargins.setLeft(left);

            if (!MathUtil.isZero(right))
                physicalMargins.setRight(right);

            if (!MathUtil.isZero(top))
                physicalMargins.setTop(top);

            if (!MathUtil.isZero(bottom))
                physicalMargins.setBottom(bottom);
        }
        else
        {
            physicalMargins = cssStyleTracker.getBoxModel().getMargins();
        }
        applyMargins(physicalMargins, paragraphFormat, cssStyleTracker.getBlockFlowDirection());
        
        applyOutlineLevel(paragraphFormat, cssStyleTracker.getElementDeclarations(), cssStyleTracker.getCurrentElement().getElementName());
    }

    private static void applyOutlineLevel(
        ParagraphFormat paragraphFormat,
        CssDeclarationCollection declarations,
        String elementName)
    {
        // WORDSNET-23183 Apply OutlineLevel to paragraphs created from HTML heading elements ('h1'..'h6') in order
        // to preserve the semantics of those elements and make their text appear on the navigation pane in MS Word.
        // This is needed for elements that have custom paragraph styles applied via CSS classes, because those custom
        // styles are created with the default outline level.
        if ((elementName.length() == 2) && (elementName.charAt(0) == 'h'))
        {
            char headingLevel = elementName.charAt(1);
            if ((headingLevel >= '1') && (headingLevel <= '6'))
            {
                paragraphFormat.setOutlineLevel((/*OutlineLevel*/int)(headingLevel - '1'));
            }
        }

        double outlineLevelValue = declarations.getNumber(HtmlConstants.OUTLINE_LEVEL);
        if (MathUtil.isMinValue(outlineLevelValue) || (outlineLevelValue < 1) || (outlineLevelValue > 9))
        {
            return;
        }
        paragraphFormat.setOutlineLevel((/*OutlineLevel*/int)((int)outlineLevelValue - 1));
    }

    protected /*override*/ void applySpecialFormatting(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssLineHeightStyleConverter.toParagraphFormat(declarations, paragraphFormat, false);
        applyOutlineLevel(paragraphFormat, declarations, "");
    }

    private static double getMargin(
        CssDeclarationCollection declarations,
        String propertyName)
    {
        double margin = declarations.getLength(propertyName);
        return MathUtil.isMinValue(margin)
            ? 0.0
            : margin;
    }

    private static void applyMargins(
        CssMargins physicalMargins,
        ParagraphFormat paragraphFormat,
        /*CssBlockFlowDirection*/int blockFlowDirection)
    {
        CssMargins logicalMargins = physicalMargins.toLogicalMargins(
            blockFlowDirection,
            paragraphFormat.getBidi());

        // Apply paragraph indents if they are specified in HTML or CSS. Note that left indent of list items is applied
        // by the list reader and we don't want to override its value.
        if (!paragraphFormat.isListItem())
        {
            if (logicalMargins.getUseLeft() || (!MathUtil.isZero(logicalMargins.getLeft())))
            {
                paragraphFormat.setLeftIndent(logicalMargins.getLeft());
            }
        }
        if (logicalMargins.getUseRight() || !MathUtil.isZero(logicalMargins.getRight()))
        {
            paragraphFormat.setRightIndent(logicalMargins.getRight());
        }

        // Top spacing.
        if (logicalMargins.getUseTop() || !MathUtil.isZero(logicalMargins.getTop()))
        {
            // WORDSNET-20282 Validate and set the top margin in the range.
            paragraphFormat.setSpaceBefore(MathUtil.fitToRange(logicalMargins.getTop(), 0.0, ConvertUtilCore.MAX_SIZE_POINT));
        }

        // Bottom spacing.
        if (logicalMargins.getUseBottom() || !MathUtil.isZero(logicalMargins.getBottom()))
        {
            // WORDSNET-20282 Validate and set the bottom margin in the range.
            paragraphFormat.setSpaceAfter(MathUtil.fitToRange(logicalMargins.getBottom(), 0.0, ConvertUtilCore.MAX_SIZE_POINT));
        }
    }

    /// <summary>
    /// Indicates whether to import borders and the margins of parent block-level HTML elements as <see cref="HtmlBlock"/>
    /// nodes.
    /// </summary>
    private /*final*/ boolean mUseHtmlBlocks;
}

