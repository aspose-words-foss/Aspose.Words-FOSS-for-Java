// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ConvertUtilCore;
import com.aspose.Debug;
import com.aspose.MathUtil;

/// <summary>
/// Helps to apply CSS line-height style to a model.
/// </summary>
/*static*/ class CssLineHeightStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssLineHeightStyleConverter()
	{}

    /// <summary>
    /// Applies CSS line-height style to a model paragraph format.
    /// </summary>
    static void toParagraphFormat(
        CssDeclarationCollection declarations,
        ParagraphFormat paragraphFormat,
        boolean applyFormattingAsMsWord)
    {
        CssDeclaration lineHeightDeclaration = declarations.get("line-height");
        if (lineHeightDeclaration == null)
            return;
        if (lineHeightDeclaration.getValue().getCount() != 1)
            return;

        CssComputedDeclaration computedLineHeight = ms.as(lineHeightDeclaration, CssComputedDeclaration.class);
        boolean isPercentageSpecified = (computedLineHeight != null) &&
            (computedLineHeight.getOriginalSpecifiedValue().getFirstValue().getValueType() == CssValueType.PERCENTAGE);
        if (isPercentageSpecified)
        {
            // Percentage is computed into absolute length during style resolution but it has a special meaning for HTML
            // import, because we convert percentage into line height multiplicator. Thus we need to know whether
            // the 'line-height' value was specified in percentage terms.
            // Because of this behavior, line heights in imported documents differ from that of documents rendered in
            // browsers. However, MS Word produces the same results. See WORDSNET-11773
            CssValue specifiedValue = computedLineHeight.getOriginalSpecifiedValue().getFirstValue();
            paragraphFormat.setLineSpacingRule(LineSpacingRule.MULTIPLE);
            double lines = ((CssPercentageValue)specifiedValue).getDoubleValue() / 100.0;
            // MS Word has a little bit another rounding for the line spacing value.
            if (applyFormattingAsMsWord)
            {
                int twips = (int)(ConvertUtilCore.lineToPoint(lines) * ConvertUtilCore.TWIPS_PER_POINT);
                paragraphFormat.setLineSpacing(ConvertUtilCore.twipToPoint(twips));
            }
            else
            {
                paragraphFormat.setLineSpacing(ConvertUtilCore.lineToPoint(lines));
            }
        }
        else
        {
            CssValue cssValue = lineHeightDeclaration.getValue().getFirstValue();
            switch (cssValue.getValueType())
            {
                case CssValueType.PERCENTAGE:
                {
                    // Percentage must be computed into absolute length during style resolution, and we should
                    // never get here.
                    Debug.assertTrue(false);
                    break;
                }
                case CssValueType.NUMBER:
                {
                    double numberValue = ((CssNumberValue)cssValue).getDoubleValue();
                    if (numberValue > 0)
                    {
                        CssDeclaration fontSizeDeclaration = declarations.get("font-size");
                        double fontSize = (fontSizeDeclaration != null)
                            ? CssUtil.lengthToPoint(fontSizeDeclaration.getValue())
                            : CssUtil.DEFAULT_FONT_SIZE;
                        if (!MathUtil.areEqual(fontSize, -Double.MAX_VALUE))
                        {
                            paragraphFormat.setLineSpacingRule(LineSpacingRule.AT_LEAST);
                            paragraphFormat.setLineSpacing(fontSize * numberValue);
                        }
                    }
                    break;
                }
                default:
                {
                    double length = CssUtil.lengthToPoint(cssValue);
                    if (!MathUtil.areEqual(length, -Double.MAX_VALUE))
                    {
                        paragraphFormat.setLineSpacingRule(LineSpacingRule.AT_LEAST);
                        paragraphFormat.setLineSpacing(length);
                    }
                    break;
                }
            }
        }
    }
}

