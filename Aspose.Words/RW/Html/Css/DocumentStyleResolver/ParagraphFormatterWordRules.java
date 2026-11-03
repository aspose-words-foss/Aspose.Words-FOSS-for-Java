// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;


/// <summary>
/// Applies CSS formatting to paragraphs. Uses same formatting rules as MS Word.
/// </summary>
class ParagraphFormatterWordRules extends ParagraphFormatter
{
    protected /*override*/ void applySpecialFormatting(ParagraphFormat paragraphFormat, CssStyleTracker cssStyleTracker)
    {
        // MS Word applies the following default styles to paragraphs imported from <li> elements.
        IHtmlElementProvider currentElement = cssStyleTracker.getCurrentElement();
        if ("li".equals(currentElement.getElementName()))
        {
            paragraphFormat.setSpaceBefore(5.0);
            paragraphFormat.setSpaceBeforeAuto(true);
            paragraphFormat.setSpaceAfter(5.0);
            paragraphFormat.setSpaceAfterAuto(true);
            paragraphFormat.setLineSpacingRule(LineSpacingRule.MULTIPLE);
            paragraphFormat.setLineSpacing(12.0);
        }

        // WORDSNET-17035 When HTML is imported by MS Word, the "MsoNormal" class name has special meaning:
        // it is an alias for the "Normal" paragraph style. for MS Word. This means that the paragraph imported for
        // the <p class="msonormal"> element will be formatted using the "Normal" style instead of "Normal (Web)".
        // In constrast to "Normal" paragraphs imported from plain text (from anonymous text blocks), paragraphs
        // with the "msonormal" class still get default (auto) vertical margins (paragraph spacing) in MS Word.
        if ("p".equals(currentElement.getElementName()))
        {
            String[] classes = currentElement.getClasses();
            if ((classes.length > 0) && msString.equals(classes[0], "msonormal", StringComparison.ORDINAL_IGNORE_CASE))
            {
                paragraphFormat.setSpaceBefore(5.0);
                paragraphFormat.setSpaceBeforeAuto(true);
                paragraphFormat.setSpaceAfter(5.0);
                paragraphFormat.setSpaceAfterAuto(true);
            }
        }

        // WORDSNET-23496 Now, we use HtmlBlock nodes to store borders and margins of block-level HTML elements,
        // we don't need the box model and we apply only the current element margins directly to the current paragraph.
        applyMargins(paragraphFormat, cssStyleTracker);

        CssLineHeightStyleConverter.toParagraphFormat(cssStyleTracker.getElementDeclarations(), paragraphFormat, true);
    }

    protected /*override*/ void applySpecialFormatting(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssLineHeightStyleConverter.toParagraphFormat(declarations, paragraphFormat, true);
        applyMarginTop(paragraphFormat, declarations);
        applyMarginRight(paragraphFormat, declarations);
        applyMarginBottom(paragraphFormat, declarations);
        applyMarginLeft(paragraphFormat, declarations);

        applyMsoHtmlFormatting(paragraphFormat, declarations);
    }

    private static void applyMargins(ParagraphFormat paragraphFormat, CssStyleTracker cssStyleTracker)
    {
        double left = cssStyleTracker.getElementDeclarations().getLength("margin-left");
        double right = cssStyleTracker.getElementDeclarations().getLength("margin-right");
        double top = cssStyleTracker.getElementDeclarations().getLength("margin-top");
        double bottom = cssStyleTracker.getElementDeclarations().getLength("margin-bottom");

        CssMargins physicalMargins = new CssMargins();
        if (!MathUtil.isMinValue(left))
            physicalMargins.setLeft(left);
        if (!MathUtil.isMinValue(right))
            physicalMargins.setRight(right);
        if (!MathUtil.isMinValue(top))
            physicalMargins.setTop(top);
        if (!MathUtil.isMinValue(bottom))
            physicalMargins.setBottom(bottom);

        CssMargins logicalMargins = physicalMargins.toLogicalMargins(
            CssBlockFlowDirection.HORIZONTAL_TB,
            paragraphFormat.getBidi());

        if (logicalMargins.getUseLeft())
        {
            // If the paragraph is a list item then MS Word sets the left indent
            // as the sum of the list item left indent and the paragraph left margin.
            if (paragraphFormat.isListItem())
            {
                paragraphFormat.setLeftIndent(paragraphFormat.getLeftIndent() + logicalMargins.getLeft());
            }
            else
            {
                paragraphFormat.setLeftIndent(logicalMargins.getLeft());
            }
        }
        if (logicalMargins.getUseRight())
        {
            paragraphFormat.setRightIndent(logicalMargins.getRight());
        }
        if (logicalMargins.getUseTop())
        {
            validateAndSetSpaceBefore(paragraphFormat, Math.max(logicalMargins.getTop(), 0.0));
        }
        if (logicalMargins.getUseBottom())
        {
            validateAndSetSpaceAfter(paragraphFormat, Math.max(logicalMargins.getBottom(), 0.0));
        }
    }

    private static void applyMarginTop(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        double length = declarations.getLength("margin-top");
        if (!MathUtil.isMinValue(length))
        {
            validateAndSetSpaceBefore(paragraphFormat, length);
        }
    }

    private static void applyMarginRight(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        double length = declarations.getLength("margin-right");
        if (!MathUtil.isMinValue(length))
        {
            paragraphFormat.setRightIndent(length);
        }
    }

    private static void applyMarginBottom(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        double length = declarations.getLength("margin-bottom");
        if (!MathUtil.isMinValue(length))
        {
            validateAndSetSpaceAfter(paragraphFormat, length);
        }
    }

    private static void applyMarginLeft(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        double length = declarations.getLength("margin-left");
        if (!MathUtil.isMinValue(length))
        {
            paragraphFormat.setLeftIndent(length);
        }
    }

    private static void validateAndSetSpaceBefore(ParagraphFormat paragraphFormat, double value)
    {
        // WORDSNET-20282 Validate the new space value and if it's too large, use auto-spacing instead.
        // This mimics the MS Word's behavior.
        if (value <= ConvertUtilCore.MAX_SIZE_POINT)
        {
            paragraphFormat.setSpaceBefore(value);
            paragraphFormat.setSpaceBeforeAuto(false);
        }
        else
        {
            paragraphFormat.setSpaceBeforeAuto(true);
        }
    }

    private static void validateAndSetSpaceAfter(ParagraphFormat paragraphFormat, double value)
    {
        // WORDSNET-20282 Validate the new space value and if it's too large, use auto-spacing instead.
        // This mimics the MS Word's behavior.
        if (value <= ConvertUtilCore.MAX_SIZE_POINT)
        {
            paragraphFormat.setSpaceAfter(value);
            paragraphFormat.setSpaceAfterAuto(false);
        }
        else
        {
            paragraphFormat.setSpaceAfterAuto(true);
        }
    }

    private static void applyMsoHtmlFormatting(ParagraphFormat paragraphFormat, CssDeclarationCollection elementDeclarations)
    {
        if (elementDeclarations.containsIdentifier("mso-add-space", "auto"))
        {
            paragraphFormat.setNoSpaceBetweenParagraphsOfSameStyle(true);
        }
    }
}

