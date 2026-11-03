// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/08/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.MathUtil;
import com.aspose.NullableBool;
import com.aspose.NullableBoolUtil;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Utility functions to deal with CSS.
/// </summary>
/*static*/ class CssUtil
{
	/* Simulation of static class by using private constructor */
	private CssUtil()
	{}

    /// <summary>
    /// Gets text direction of an HTML element from its CSS declarations.
    /// </summary>
    static /*CssDirection*/int getDirection(CssDeclarationCollection declarations)
    {
        /*CssDirection*/int result = CssDirection.UNSPECIFIED;
        if (declarations != null)
        {
            CssDeclaration directionDeclaration = declarations.get("direction");
            if (directionDeclaration != null)
            {
                if (directionDeclaration.getValue().equals(CssValue.Ltr))
                {
                    result = CssDirection.LTR;
                }
                else if (directionDeclaration.getValue().equals(CssValue.Rtl))
                {
                    result = CssDirection.RTL;
                }
            }
        }
        return result;
    }

    /// <summary>
    /// Replaces '-aw-start' with either 'left' or 'right' according to the element's 'direction'.
    /// </summary>
    static CssPropertyValue getEffectiveAwStartValue(/*CssDirection*/int direction)
    {
        CssValue result = (direction == CssDirection.RTL)
            ? CssValue.Right
            : CssValue.Left;
        return new CssPropertyValue(new CssValueList(result));
    }

    /// <summary>
    /// Gets the 'unicode-bidi' property value of an HTML element.
    /// </summary>
    static /*CssUnicodeBidi*/int getUnicodeBidi(CssDeclarationCollection declarations, boolean isBlockLevelElement)
    {
        /*CssUnicodeBidi*/int result = CssUnicodeBidi.NORMAL;
        if (!isBlockLevelElement && (declarations != null))
        {
            CssDeclaration directionDeclaration = declarations.get("unicode-bidi");
            if ((directionDeclaration != null) &&
                directionDeclaration.getValue().equals(CssValue.Embed))
            {
                result = CssUnicodeBidi.EMBED;
            }
        }
        return result;
    }

    /// <summary>
    /// Converts <see cref="HtmlDocumentMode"/> to <see cref="CssDocumentMode"/>
    /// </summary>
    static /*CssDocumentMode*/int htmlDocumentModeToCssMode(/*HtmlDocumentMode*/int documentMode)
    {
        switch (documentMode)
        {
            case HtmlDocumentMode.STANDARDS:
                return CssDocumentMode.STANDARDS;
            case HtmlDocumentMode.LIMITED_QUIRKS:
                return CssDocumentMode.LIMITED_QUIRKS;
            case HtmlDocumentMode.QUIRKS:
                return CssDocumentMode.QUIRKS;
            default:
                Debug.assertTrue(false);
                return CssDocumentMode.STANDARDS;
        }
    }

    /// <summary>
    /// Computes length value measured in points for CSS length value.
    /// </summary>
    /// <param name="lengthValue">CSS length value.</param>
    /// <returns>Length value measured in points; double.MinValue if the CSS value is invalid.</returns>
    static double lengthToPoint(CssValue lengthValue)
    {
        double length = absoluteLengthToPoint(lengthValue);
        if (MathUtil.isMinValue(length))
            length = relativeLengthToPoint(lengthValue, null, DEFAULT_FONT_SIZE);

        return length;
    }

    /// <summary>
    /// Computes length value measured in points for CSS length value.
    /// </summary>
    /// <param name="lengthValue">CSS length value.</param>
    /// <returns>Length value measured in points; double.MinValue if the CSS value is invalid.</returns>
    static double lengthToPoint(CssPropertyValue lengthValue)
    {
        return (lengthValue.getCount() == 1)
                   ? lengthToPoint(lengthValue.getFirstValue())
                   : -Double.MAX_VALUE;
    }

    /// <summary>
    /// Computes length value measured in points for relative CSS length value.
    /// </summary>
    /// <param name="lengthValue">Relative CSS length value.</param>
    /// <param name="rootElementDeclarations">CSS declarations of the root element. Can be null.</param>
    /// <param name="elementFontSize">'font-size' of the current CSS element, in points.</param>
    /// <returns>Length value measured in points; double.MinValue if the CSS value is absolute or invalid.</returns>
    static double relativeLengthToPoint(
        CssValue lengthValue,
        CssDeclarationCollection rootElementDeclarations,
        double elementFontSize)
    {
        Debug.assertTrue(elementFontSize >= 0);

        double length = -Double.MAX_VALUE;

        if (lengthValue.getValueType() == CssValueType.LENGTH)
        {
            CssLengthValue cssLength = (CssLengthValue)lengthValue;
            switch (cssLength.getUnit())
            {
                case CssUnit.EM:
                {
                    length = elementFontSize * cssLength.getDoubleValue();
                    break;
                }
                case CssUnit.EX:
                {
                    // 1ex is equivalent to the height of the letter ‘x’ in the current font. In the cases where it is impossible or
                    // impractical to determine the x-height, a value of 0.5em must be assumed.
                    length = elementFontSize * cssLength.getDoubleValue() * 0.5;
                    break;
                }
                case CssUnit.REM:
                {
                    double rootFontSize = computeFontSize(rootElementDeclarations);
                    length = rootFontSize * cssLength.getDoubleValue();
                    break;
                }
                default:
                {
                    break;
                }
            }
        }

        return length;
    }

    /// <summary>
    /// Gets Word line width measured in points from CSS line width.
    /// </summary>
    /// <param name="propertyValue">Property value.</param>
    /// <returns>Line width measured in points; zero, if the property value is invalid.</returns>
    static double getBorderLineWidth(CssPropertyValue propertyValue)
    {
        Debug.assertTrue(propertyValue != null);

        double borderWidth;
        if (propertyValue.equals(CssValue.Thin))
        {
            borderWidth = 1.5; // Looks like IE uses this width.
        }
        else if (propertyValue.equals(CssValue.Medium))
        {
            borderWidth = 3.0; // Looks like IE uses this width.
        }
        else if (propertyValue.equals(CssValue.Thick))
        {
            borderWidth = 4.5; // Looks like IE uses this width.
        }
        else
        {
            double length = lengthToPoint(propertyValue);
            borderWidth = (!MathUtil.isMinValue(length)) ? length : 0.0;
        }

        return borderWidth;
    }

    /// <summary>
    /// Computes length value measured in points for absolute CSS length value.
    /// </summary>
    /// <param name="lengthValue">Absolute CSS length value.</param>
    /// <returns>Length value measured in points; double.MinValue if the CSS value is relative or invalid.</returns>
    private static double absoluteLengthToPoint(CssValue lengthValue)
    {
        double length = -Double.MAX_VALUE;

        switch (lengthValue.getValueType())
        {
            case CssValueType.LENGTH:
            {
                CssLengthValue cssLength = (CssLengthValue)lengthValue;
                switch (cssLength.getUnit())
                {
                    case CssUnit.EM:
                    case CssUnit.EX:
                    case CssUnit.REM:
                        break;
                    case CssUnit.NONE:
                    case CssUnit.IN:
                    case CssUnit.CM:
                    case CssUnit.MM:
                    case CssUnit.PT:
                    case CssUnit.PC:
                    case CssUnit.PX:
                        length = cssLength.getLength(CssUnit.PT);
                        break;
                    default:
                        Debug.assertTrue(false);
                        break;
                }
                break;
            }
            case CssValueType.NUMBER:
            {
                length = ((CssNumberValue)lengthValue).quirkyLengthToPoint();
                break;
            }
            default:
            {
                // It is an error if a length value is neither a CSS length nor a number. We just ignore such values.
                break;
            }
        }

        return length;
    }

    /// <summary>
    /// Finds a 'font-size' CSS declaration in the CSS declarations and computes absolute font size value measured in points for this declaration.
    /// </summary>
    /// <param name="cssDeclarations">CSS declarations. Can be null.</param>
    /// <returns>Font size measured in points; initial font size value if the declarations argument is null, or if a font-size declaration isn't found,
    /// or if CSS value is relative or invalid.</returns>
    static double computeFontSize(CssDeclarationCollection cssDeclarations)
    {
        double fontSize = -Double.MAX_VALUE;
        if (cssDeclarations != null)
        {
            CssDeclaration parentFontSizeDeclaration = cssDeclarations.get("font-size");
            if ((parentFontSizeDeclaration != null) && (parentFontSizeDeclaration.getValue().getCount() == 1))
                fontSize = absoluteLengthToPoint(parentFontSizeDeclaration.getValue().getFirstValue());
        }

        if (MathUtil.isMinValue(fontSize))
        {
            fontSize = DEFAULT_FONT_SIZE;
        }

        return fontSize;
    }

    /// <summary>
    /// Gets a value indicating whether the declarations specify italic font face.
    /// </summary>
    /// <returns>
    /// <see cref="NullableBool.NotDefined"/> if "font-style" is not present in the declarations.
    /// </returns>
    static /*NullableBool*/byte isItalicFont(
        CssDeclarationCollection declarations,
        String propertyName)
    {
        CssDeclaration declaration = declarations.get(propertyName);
        if (declaration == null)
        {
            return NullableBool.NOT_DEFINED;
        }

        boolean isItalic = declaration.getValue().equals(CssValue.Italic) || declaration.getValue().equals(CssValue.Oblique);
        return NullableBoolUtil.asNullable(isItalic);
    }

    /// <summary>
    /// Gets a value indicating whether the declarations specify bold font face.
    /// </summary>
    /// <returns>
    /// <see cref="NullableBool.NotDefined"/> if "font-weight" is not specified.
    /// </returns>
    static /*NullableBool*/byte isBoldFont(
        CssDeclarationCollection declarations,
        String propertyName)
    {
        CssDeclaration declaration = declarations.get(propertyName);
        if ((declaration == null) || (declaration.getValue().getCount() != 1))
        {
            return NullableBool.NOT_DEFINED;
        }

        boolean isBold;
        CssValue cssValue = declaration.getValue().getFirstValue();
        switch (cssValue.getValueType())
        {
            case CssValueType.IDENTIFIER:
                isBold = cssValue.equals(CssValue.Bold) || cssValue.equals(CssValue.Bolder);
                break;
            case CssValueType.NUMBER:
                isBold = cssValue.getDoubleValue() >= 600;
                break;
            default:
                return NullableBool.NOT_DEFINED;
        }
        return NullableBoolUtil.asNullable(isBold);
    }

    /// <summary>
    /// Splits class attribute value to separate values.
    /// </summary>
    /// <param name="classAttribValue">Class attribute value.</param>
    /// <returns>String array of separate parts.</returns>
    static String[] splitClassAttributeValue(String classAttribValue)
    {
        MatchCollection matches = G_CLASSES_REGEX.matches(classAttribValue);
        String[] result = new String[matches.getCount()];
        for (int i = 0; i < matches.getCount(); i++)
        {
            result[i] = matches.get(i).getValue();
        }
        return result;
    }

    /// <summary>
    /// Returns CSS block flow direction for a specified paragraph.
    /// </summary>
    static /*CssBlockFlowDirection*/int getParaCssBlockFlowDirection(Paragraph para)
    {
        return (para.getParentSection() != null)
                   ? textFlowToCssBlockFlowDirection(para.getParentSection().getPageSetup().getTextFlow())
                   : CssBlockFlowDirection.HORIZONTAL_TB;
    }

    /// <summary>
    /// Converts specified Word text flow to a CSS block flow direction.
    /// </summary>
    static /*CssBlockFlowDirection*/int textFlowToCssBlockFlowDirection(/*TextFlow*/int textFlow)
    {
        switch (textFlow)
        {
            case TextFlow.HORIZONTAL:
            case TextFlow.LR_BT:
            case TextFlow.RL_TB:
            case TextFlow.HORIZONTAL_ROTATED_FAR_EAST:
            {
                return CssBlockFlowDirection.HORIZONTAL_TB;
            }
            case TextFlow.VERTICAL:
            case TextFlow.V_NON_V:
            {
                return CssBlockFlowDirection.VERTICAL_RL;
            }
            default:
            {
                Debug.assertTrue(false, "Unknown value!");
                return CssBlockFlowDirection.HORIZONTAL_TB;
            }
        }
    }

    /// <summary>
    /// Converts specified a CSS block flow direction to Word text flow.
    /// </summary>
    static /*TextFlow*/int cssBlockFlowDirectionToTextFlow(/*CssBlockFlowDirection*/int blockFlowDirection)
    {
        switch (blockFlowDirection)
        {
            case CssBlockFlowDirection.HORIZONTAL_TB:
                return TextFlow.HORIZONTAL;
            case CssBlockFlowDirection.VERTICAL_RL:
            case CssBlockFlowDirection.VERTICAL_LR:
                return TextFlow.VERTICAL;
            default:
                Debug.assertTrue(false, "Unknown value of block flow direction");
                return TextFlow.HORIZONTAL;
        }
    }

    /// <summary>
    /// Converts a specified block flow direction to standard CSS writing mode.
    /// </summary>
    static String cssBlockFlowDirectionToWritingMode(/*CssBlockFlowDirection*/int blockFlowDirection)
    {
        switch (blockFlowDirection)
        {
            case CssBlockFlowDirection.HORIZONTAL_TB:
                return "horizontal-tb";
            case CssBlockFlowDirection.VERTICAL_RL:
                return "vertical-rl";
            case CssBlockFlowDirection.VERTICAL_LR:
                return "vertical-lr";
            default:
                Debug.assertTrue(false, "Unknown text flow direction value!");
                return "horizontal-tb";
        }
    }

    /// <summary>
    /// Converts a specified CSS writing mode to block flow direction.
    /// </summary>
    static /*CssBlockFlowDirection*/int cssWritingModeToBlockFlowDirection(String writingMode)
    {
        switch (gStringSwitchMap.of(writingMode))
        {
            case /*"horizontal-tb"*/0:
                return CssBlockFlowDirection.HORIZONTAL_TB;
            case /*"vertical-rl"*/1:
                return CssBlockFlowDirection.VERTICAL_RL;
            case /*"vertical-lr"*/2:
                return CssBlockFlowDirection.VERTICAL_LR;
            default:
                return CssBlockFlowDirection.HORIZONTAL_TB;
        }
    }

    /// <summary>
    /// Converts a specified block flow direction to MS (used in IE) CSS writing mode.
    /// </summary>
    static String cssBlockFlowDirectionToMSWritingMode(/*CssBlockFlowDirection*/int blockFlowDirection)
    {
        switch (blockFlowDirection)
        {
            case CssBlockFlowDirection.HORIZONTAL_TB:
                return "lr-tb";
            case CssBlockFlowDirection.VERTICAL_RL:
                return "tb-rl";
            case CssBlockFlowDirection.VERTICAL_LR:
                return "tb-lr";
            default:
                Debug.assertTrue(false, "Unknown text flow direction value!");
                return "lr-tb";
        }
    }

    /// <summary>
    /// This article is with info about CSS writing-mode attribute.
    /// http://msdn.microsoft.com/library/default.asp?url=/workshop/author/dhtml/reference/properties/layoutflow.asp
    /// We can only do some orientations, not all as HTML does not match MS Word well.
    /// </summary>
    static String textOrientationToCss(/*TextOrientation*/int orientation)
    {
        switch (orientation)
        {
            case TextOrientation.DOWNWARD:
            case TextOrientation.UPWARD:
            case TextOrientation.VERTICAL_FAR_EAST:
                return "tb-rl";
            default:
                return "lr-tb";
        }
    }

    static /*TextOrientation*/int cssToTextOrientation(String value)
    {
        return StringUtil.equalsIgnoreCase(value, "tb-rl")
            ? TextOrientation.VERTICAL_FAR_EAST
            : TextOrientation.HORIZONTAL;
    }

    /// <summary>
    /// Generates properties of a section break. Here we need to output "magic" mso-break-type and mso-column-break-before attribute
    /// so MS Word will consider this break a section break.
    /// For the first section MS Word 2007 doesn't roundtrip section start type. So I won't either. This can be saved
    /// in native Word formats but not in HTML.
    /// </summary>
    static void sectionBreakPropsToCss(/*SectionStart*/int secStart, CssStyle cssStyle)
    {
        switch (secStart)
        {
            case SectionStart.CONTINUOUS:
                cssStyle.setIdentifier("page-break-before", "auto");
                break;
            case SectionStart.NEW_COLUMN:
                cssStyle.setIdentifier("mso-column-break-before", "always");
                break;
            case SectionStart.EVEN_PAGE:
                cssStyle.setIdentifier("page-break-before", "left");
                break;
            case SectionStart.ODD_PAGE:
                cssStyle.setIdentifier("page-break-before", "right");
                break;
            default:
                // SectionStart.NewPage is the default
                cssStyle.setIdentifier("page-break-before", "always");
                break;
        }

        cssStyle.setIdentifier("mso-break-type", "section-break");
        cssStyle.setIdentifier("clear", "both");
    }

    /// <summary>
    /// Converts page setup to CSS in a @page at-rule
    /// </summary>
    static void pageSetupToCssPageAtRule(PageSetup ps, CssStyle cssStyle)
    {
        cssStyle.setPageSize(ps.getPageWidth(), ps.getPageHeight(), CssUnit.PT);
        cssStyle.setMargin(ps.getTopMargin(), ps.getRightMargin(), ps.getBottomMargin(), ps.getLeftMargin());
    }

    static /*BaselineAlignment*/int cssToBaselineAlignment(String aligmentValue)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(aligmentValue)))
        {
            case /*"middle"*/3:
                return BaselineAlignment.CENTER;
            case /*"top"*/4:
                return BaselineAlignment.TOP;
            case /*"bottom"*/5:
                return BaselineAlignment.BOTTOM;
            default:
                return BaselineAlignment.BASELINE;
        }
    }

    static CssValue baselineAlignmentToCss(/*BaselineAlignment*/int alignment)
    {
        switch (alignment)
        {
            case BaselineAlignment.CENTER:
                return CssValue.Middle;
            case BaselineAlignment.TOP:
                return CssValue.Top;
            case BaselineAlignment.BOTTOM:
                return CssValue.Bottom;
            default:
                return CssValue.Baseline;
        }
    }

    /// <summary>
    /// Indicates whether the HTML element creates a <see cref="HtmlBlock"/> instance in the document model.
    /// </summary>
    static boolean createsHtmlBlock(
        String elementName,
        CssDeclarationCollection elementDeclarations,
        boolean applyFormattingAsMsWord)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"body"*/6:
                // The HTML body element creates HTML blocks only if it has appropriate declarations
                // or it is imported from the alt-chunk document.
                return applyFormattingAsMsWord || hasHtmlBlockProperties(elementDeclarations);
            case /*"blockquote"*/7:
                // These elements always create HTML blocks.
                return true;
            case /*"div"*/8:
                // In MS Word, div elements that specify document sections don't create HTML blocks.
                // Neither do div elements that represent headers and footers.
                return !applyFormattingAsMsWord ||
                    ((elementDeclarations.get("page") == null) && (elementDeclarations.get("mso-element") == null));
            default:
                return false;
        }
    }

    /// <summary>
    /// Default font size in points, is equal to 'medium' value.
    /// </summary>
    static final double DEFAULT_FONT_SIZE = 12.0;

    /// <summary>
    /// Default block width (in points) used by MS Word as a base when computing horizontal widths specified in CSS
    /// as percentage.
    /// </summary>
    /// <remarks>
    /// According to the CSS specification, horizontal values of properties like "text-indent" that are specified
    /// as percentage refer to width of the containing block. MS Word doesn't follow that rule and always uses a fixed
    /// base value when computing percentage.
    /// </remarks>
    static final int DEFAULT_BLOCK_WIDTH = 612; // 8.5 inches, width of the Letter paper size.

    /// <summary>
    /// Returns a value indicating whether any of CSS declarations are applicable to <see cref="HtmlBlock"/> nodes.
    /// </summary>
    private static boolean hasHtmlBlockProperties(CssDeclarationCollection declarations)
    {
        /*BorderType*/int[] borderTypes = new /*BorderType*/int[]
        {
            BorderType.TOP,
            BorderType.RIGHT,
            BorderType.BOTTOM,
            BorderType.LEFT
        };
        for (/*BorderType*/int borderType : borderTypes)
        {
            CssBorder cssBorder = CssBorder.createBorder(declarations, borderType, false);
            if ((cssBorder != null) &&
                (cssBorder.getLineStyle() != LineStyle.NONE) &&
                (cssBorder.getCssLineWidth() > 0))
            {
                return true;
            }
        }

        String[] marginProperties = new String[]
        {
            "margin-left",
            "margin-right",
            "margin-top",
            "margin-bottom",
        };
        for (String property : marginProperties)
        {
            if ("auto".equals(declarations.getIdentifier(property)))
                return true;

            if (isValidMarginValue(declarations.getLength(property)) ||
                isValidMarginValue(declarations.getPercentage(property)))
            {
                return true;
            }
        }

        String[] colorProperties = new String[]
        {
            "border-left-color",
            "border-right-color",
            "border-top-color",
            "border-bottom-color"
        };
        for (String property : colorProperties)
        {
            if (declarations.getColor(property) != null)
                return true;
        }

        return false;
    }

    private static boolean isValidMarginValue(double marginValue)
    {
        return !MathUtil.isMinValue(marginValue) && !MathUtil.isZero(marginValue);
    }

    private static /*final*/ Regex G_CLASSES_REGEX = new Regex("[^ \\t\\r\\n\\f]+",
        RegexOptions.COMPILED | RegexOptions.CULTURE_INVARIANT);

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"horizontal-tb",
		"vertical-rl",
		"vertical-lr",
		"middle",
		"top",
		"bottom",
		"body",
		"blockquote",
		"div"
	);

}

