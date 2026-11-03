// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2017 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;

/// <summary>
/// Applies CSS formatting to paragraphs.
/// </summary>
abstract class ParagraphFormatter
{
    void format(ParagraphFormat paragraphFormat, CssStyleTracker cssStyleTracker) throws Exception
    {
        // Text direction of the paragraph affect mapping of alignment and indentation, so it is important to determine
        // the direction before alignment and indentation are applied.
        if (cssStyleTracker.getCurrentElementInfo().BlockLevelDirection != CssDirection.UNSPECIFIED)
        {
            paragraphFormat.setBidi(cssStyleTracker.getCurrentElementInfo().BlockLevelDirection == CssDirection.RTL);
        }

        CssDeclarationCollection declarations = cssStyleTracker.getElementDeclarations();
        CssBorderStyleConverter.toParagraphFormat(declarations, paragraphFormat);
        CssTextAlignStyleConverter.toParagraphFormat(declarations, paragraphFormat.getBidi(), paragraphFormat);
        applyDeclarations(paragraphFormat, declarations);
        applyBackgroundColor(paragraphFormat, new CssBackgroundColor(cssStyleTracker.getBackground().getParagraphBackgroundColor()));

        applySpecialFormatting(paragraphFormat, cssStyleTracker);
    }

    void format(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations) throws Exception
    {
        // Here we do no know the context in which the style will be used. We do not know parent and root elements.
        // Also, we do not know what will be the direction of elements to which the style will be applied, 
        // so we load style formatting for the default direction (LTR).
        CssBorderStyleConverter.toParagraphFormat(declarations, paragraphFormat);
        CssTextAlignStyleConverter.toParagraphFormat(declarations, false, paragraphFormat);
        applyDeclarations(paragraphFormat, declarations);
        applyBackgroundColor(paragraphFormat, new CssBackgroundColor(declarations));

        applySpecialFormatting(paragraphFormat, declarations);
    }

    protected abstract void applySpecialFormatting(ParagraphFormat paragraphFormat, CssStyleTracker cssStyleTracker) throws Exception;

    protected abstract void applySpecialFormatting(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations) throws Exception;

    protected static CssValue getSingleValue(CssDeclarationCollection declarations, String propertyName)
    {
        CssDeclaration declaration = declarations.get(propertyName);
        if (declaration == null)
        {
            return null;
        }

        Debug.assertTrue(declaration.getValue().getCount() == 1);
        return declaration.getValue().getFirstValue();
    }

    private static void applyDeclarations(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations) throws Exception
    {
        applyPaddings(paragraphFormat, declarations);
        applyPageBreakInside(paragraphFormat, declarations);
        applyPageBreakBefore(paragraphFormat, declarations);
        applyPageBreakAfter(paragraphFormat, declarations);
        applyOrphans(paragraphFormat, declarations);
        applyWidows(paragraphFormat, declarations);
        applyWritingMode(paragraphFormat, declarations);
        applyTextIndent(paragraphFormat, declarations);
        applyVerticalAlign(paragraphFormat, declarations);
        // WORDSNET-22192 Preserve frame properties during roundtrip.
        applyFrameProperties(paragraphFormat, declarations);
    }

    private static void applyPaddings(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations) throws Exception
    {
        applyPadding("padding-top", BorderType.TOP, paragraphFormat, declarations);
        applyPadding("padding-right", BorderType.RIGHT, paragraphFormat, declarations);
        applyPadding("padding-bottom", BorderType.BOTTOM, paragraphFormat, declarations);
        applyPadding("padding-left", BorderType.LEFT, paragraphFormat, declarations);
    }

    private static void applyPadding(
        String propertyName,
        /*BorderType*/int borderType,
        ParagraphFormat paragraphFormat,
        CssDeclarationCollection declarations) throws Exception
    {
        CssValue cssValue = getSingleValue(declarations, propertyName);
        if (cssValue == null)
        {
            return;
        }

        double length = CssUtil.lengthToPoint(cssValue);
        if (!MathUtil.areEqual(length, -Double.MAX_VALUE))
        {
            Border border = paragraphFormat.getBorders().getByBorderType(borderType);
            border.setDistanceFromTextSafe(length);
        }
    }

    private static void applyPageBreakInside(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "page-break-inside");
        if (cssValue == null)
        {
            return;
        }

        paragraphFormat.setKeepTogether(cssValue.equals(CssValue.Avoid));
    }

    private static void applyPageBreakBefore(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "page-break-before");
        if (cssValue == null)
        {
            return;
        }

        paragraphFormat.setPageBreakBefore(cssValue.equals(CssValue.Always));
    }

    private static void applyPageBreakAfter(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "page-break-after");
        if (cssValue == null)
        {
            return;
        }

        paragraphFormat.setKeepWithNext(cssValue.equals(CssValue.Avoid));
    }

    private static void applyOrphans(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "orphans");
        if (cssValue == null)
        {
            return;
        }

        if (cssValue.getValueType() == CssValueType.NUMBER)
        {
            paragraphFormat.setWidowControl(cssValue.getDoubleValue() > 0);
        }
    }

    private static void applyWidows(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "widows");
        if (cssValue == null)
        {
            return;
        }

        if (cssValue.getValueType() == CssValueType.NUMBER)
        {
            paragraphFormat.setWidowControl(cssValue.getDoubleValue() > 0);
        }
    }

    private static void applyWritingMode(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "writing-mode");
        if (cssValue == null)
        {
            return;
        }

        if (cssValue.getValueType() == CssValueType.IDENTIFIER)
        {
            paragraphFormat.setFrameTextOrientation(CssUtil.cssToTextOrientation(((CssIdentifierValue)cssValue).getValue()));
        }
    }

    private static void applyTextIndent(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        if (paragraphFormat.isListItem())
        {
            return;
        }

        double textIndent = declarations.getLength("text-indent");

        // WORDSNET-24212 Add support for percentage "text-indent" values.
        if (MathUtil.isMinValue(textIndent))
        {
            double percentage = declarations.getPercentage("text-indent");
            if (!MathUtil.isMinValue(percentage))
            {
                // Mimic MS Word's behavior and use a fixed base value to compute percentage.
                textIndent = percentage * CssUtil.DEFAULT_BLOCK_WIDTH / 100.0;
            }
        }

        if (!MathUtil.isMinValue(textIndent))
        {
            // Mimic MS Word's behavior for out-of-range values. Convert the result in twips to Int16 (short), possibly
            // causing an overflow.
            /*unchecked*/
            {
                textIndent = ConvertUtilCore.twipToPoint((short)ConvertUtilCore.pointToTwip(textIndent));
            }

            paragraphFormat.setFirstLineIndent(textIndent);
        }
    }

    private static void applyBackgroundColor(ParagraphFormat paragraphFormat, CssBackgroundColor backgroundColor)
    {
        // Note that ParagraphFormat.Shading will create a Shading object and we want to avoid this
        // unless the background color is defined.
        if (backgroundColor.isDefined())
        {
            backgroundColor.toShading(paragraphFormat.getShading());
        }
    }

    private static void applyVerticalAlign(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssValue cssValue = getSingleValue(declarations, "vertical-align");
        if (cssValue == null)
            return;

        if (cssValue.getValueType() == CssValueType.IDENTIFIER)
        {
            paragraphFormat.setBaselineAlignment(CssUtil.cssToBaselineAlignment(((CssIdentifierValue)cssValue).getValue()));
        }
    }

    private static void applyFrameProperties(ParagraphFormat paragraphFormat, CssDeclarationCollection declarations)
    {
        CssFrameStyleConverter.toParagraphFormat(declarations, paragraphFormat);
    }
}

