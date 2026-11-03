// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.DictionaryExtension;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Class factory for CSS property definitions <see cref="CssPropertyDef" />.
/// </summary>
/*static*/ class CssPropertyDefFactory
{
	/* Simulation of static class by using private constructor */
	private CssPropertyDefFactory()
	{}

    /// <summary>
    /// Gets a CSS property definition class instance by the property name.
    /// </summary>
    /// <param name="propertyName">Property name. Must be lowercase.</param>
    /// <returns>An instance of the corresponding property class or a <see cref="CssUnknownPropertyDef" /> instance
    /// if the property is not supported.
    /// </returns>
    static CssPropertyDef getPropertyDef(String propertyName)
    {
        Debug.assertTrue(StringUtil.hasChars(propertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(propertyName));

        CssPropertyDef propertyDef = DictionaryExtension.getValueOrNull(G_PROPERTY_DEFS, propertyName);
        if (propertyDef == null)
            propertyDef = new CssUnknownPropertyDef(propertyName);
        return propertyDef;
    }

    /// <summary>
    /// Gets a CSS property definition class by the property name. Returns only individual (non-shorthand) properties.
    /// </summary>
    /// <param name="propertyName">Property name. Must be lowercase.</param>
    /// <returns>
    /// The property definition or <c>null</c> if the property is not supported.
    /// </returns>
    static CssIndividualPropertyDef getIndividualPropertyDef(String propertyName)
    {
        Debug.assertTrue(StringUtil.hasChars(propertyName));
        Debug.assertTrue(StringUtil.isAsciiLowerCase(propertyName));

        return ms.as(G_PROPERTY_DEFS.get(propertyName), CssIndividualPropertyDef.class);
    }

    /// <summary>
    /// Supported shorthand properties.
    /// </summary>
    static Collection<CssShorthandPropertyDef> getShorthandProperties() { return G_SHORTHAND_PROPERTY_DEFS; }

    private static void fillPropertyDefs()
    {
        // background
        addPropertyDef(new CssBackgroundAttachmentPropertyDef());
        addPropertyDef(new CssBackgroundColorPropertyDef());
        addPropertyDef(new CssBackgroundImagePropertyDef());
        addPropertyDef(new CssBackgroundPositionPropertyDef());
        addPropertyDef(new CssBackgroundRepeatPropertyDef());
        addPropertyDef(new CssBackgroundPropertyDef());
        // margins
        addPropertyDef(new CssMarginLeftPropertyDef());
        addPropertyDef(new CssMarginRightPropertyDef());
        addPropertyDef(new CssMarginTopPropertyDef());
        addPropertyDef(new CssMarginBottomPropertyDef());
        // padding
        addPropertyDef(new CssPaddingLeftPropertyDef());
        addPropertyDef(new CssPaddingRightPropertyDef());
        addPropertyDef(new CssPaddingTopPropertyDef());
        addPropertyDef(new CssPaddingBottomPropertyDef());
        // -aw-padding-xxx properties
        addPropertyDef(new CssAWPaddingTopPropertyDef());
        addPropertyDef(new CssAWPaddingRightPropertyDef());
        addPropertyDef(new CssAWPaddingBottomPropertyDef());
        addPropertyDef(new CssAWPaddingLeftPropertyDef());
        // list-style
        addPropertyDef(new CssListStyleTypePropertyDef());
        addPropertyDef(new CssListStylePositionPropertyDef());
        addPropertyDef(new CssListStyleImagePropertyDef());
        addPropertyDef(new CssListStylePropertyDef());

        addPropertyDef(new CssColorPropertyDef());
        addPropertyDef(new CssDirectionPropertyDef());
        addPropertyDef(new CssOrphansPropertyDef());
        addPropertyDef(new CssPageBreakInsidePropertyDef());
        addPropertyDef(new CssPageBreakAfterPropertyDef());
        addPropertyDef(new CssPageBreakBeforePropertyDef());
        addPropertyDef(new CssTextAlignPropertyDef());
        addPropertyDef(new CssTextIndentPropertyDef());
        addPropertyDef(new CssWidowsPropertyDef());
        addPropertyDef(new CssWritingModePropertyDef());
        addPropertyDef(new CssMarginPropertyDef());
        addPropertyDef(new CssPaddingPropertyDef());
        addPropertyDef(new CssUnicodeBidiPropertyDef());
        addPropertyDef(new CssVerticalAlignPropertyDef());
        addPropertyDef(new CssFloatPropertyDef());
        addPropertyDef(new CssWidthPropertyDef());
        addPropertyDef(new CssMinWidthPropertyDef());
        addPropertyDef(new CssMaxWidthPropertyDef());
        addPropertyDef(new CssClearPropertyDef());
        addPropertyDef(new CssCaptionSidePropertyDef());
        addPropertyDef(new CssBorderCollapsePropertyDef());
        addPropertyDef(new CssHeightPropertyDef());
        addPropertyDef(new CssMinHeightPropertyDef());
        addPropertyDef(new CssMaxHeightPropertyDef());
        addPropertyDef(new CssBorderSpacingPropertyDef());
        addPropertyDef(new CssWhiteSpacePropertyDef());
        addPropertyDef(new CssTransformPropertyDef());

        addPropertyDef(new CssTextTransformPropertyDef());
        addPropertyDef(new CssTextDecorationPropertyDef());
        addPropertyDef(new CssDisplayPropertyDef());
        addPropertyDef(new CssLetterSpacingPropertyDef());
        addPropertyDef(new CssBoxSizingPropertyDef());
        addPropertyDef(new CssVisibilityPropertyDef());
        addPropertyDef(new CssRubyAlignPropertyDef());
        // @page
        addPropertyDef(new CssPagePropertyDef());
        addPropertyDef(new CssPageSizePropertyDef());
        // font
        addPropertyDef(new CssFontSizePropertyDef());
        addPropertyDef(new CssFontFamilyPropertyDef());
        addPropertyDef(new CssFontWeightPropertyDef());
        addPropertyDef(new CssFontStylePropertyDef());
        addPropertyDef(new CssFontVariantPropertyDef());
        addPropertyDef(new CssLineHeightPropertyDef());
        addPropertyDef(new CssFontPropertyDef());
        // borders shorthand
        addPropertyDef(new CssBorderTopPropertyDef());
        addPropertyDef(new CssBorderRightPropertyDef());
        addPropertyDef(new CssBorderBottomPropertyDef());
        addPropertyDef(new CssBorderLeftPropertyDef());
        // 'border' shorthand property should be placed in mShorthandPropertyDefList after border-(top|right|bottom|left) properties.
        // See CssBorderPropertyDef.ReduceByShorthand for details.
        addPropertyDef(new CssBorderPropertyDef());
        // border widths
        addPropertyDef(new CssBorderWidthPropertyDef());
        addPropertyDef(new CssBorderTopWidthPropertyDef());
        addPropertyDef(new CssBorderRightWidthPropertyDef());
        addPropertyDef(new CssBorderBottomWidthPropertyDef());
        addPropertyDef(new CssBorderLeftWidthPropertyDef());
        // border colors
        addPropertyDef(new CssBorderColorPropertyDef());
        addPropertyDef(new CssBorderTopColorPropertyDef());
        addPropertyDef(new CssBorderRightColorPropertyDef());
        addPropertyDef(new CssBorderBottomColorPropertyDef());
        addPropertyDef(new CssBorderLeftColorPropertyDef());
        // border styles
        addPropertyDef(new CssBorderStylePropertyDef());
        addPropertyDef(new CssBorderTopStylePropertyDef());
        addPropertyDef(new CssBorderRightStylePropertyDef());
        addPropertyDef(new CssBorderBottomStylePropertyDef());
        addPropertyDef(new CssBorderLeftStylePropertyDef());
        // -aw-borders shorthand
        addPropertyDef(new CssAWBorderTopPropertyDef());
        addPropertyDef(new CssAWBorderRightPropertyDef());
        addPropertyDef(new CssAWBorderBottomPropertyDef());
        addPropertyDef(new CssAWBorderLeftPropertyDef());
        // '-aw-border' shorthand property should be placed in mShorthandPropertyDefList after -aw-border-(top|right|bottom|left) properties.
        // See CssAWBorderPropertyDef.ReduceByShorthand for details.
        addPropertyDef(new CssAWBorderPropertyDef());
        // -aw-border widths
        addPropertyDef(new CssAWBorderWidthPropertyDef());
        addPropertyDef(new CssAWBorderTopWidthPropertyDef());
        addPropertyDef(new CssAWBorderRightWidthPropertyDef());
        addPropertyDef(new CssAWBorderBottomWidthPropertyDef());
        addPropertyDef(new CssAWBorderLeftWidthPropertyDef());
        // -aw-border styles
        addPropertyDef(new CssAWBorderStylePropertyDef());
        addPropertyDef(new CssAWBorderTopStylePropertyDef());
        addPropertyDef(new CssAWBorderRightStylePropertyDef());
        addPropertyDef(new CssAWBorderBottomStylePropertyDef());
        addPropertyDef(new CssAWBorderLeftStylePropertyDef());
        // -aw-border colors
        addPropertyDef(new CssAWBorderColorPropertyDef());
        addPropertyDef(new CssAWBorderTopColorPropertyDef());
        addPropertyDef(new CssAWBorderRightColorPropertyDef());
        addPropertyDef(new CssAWBorderBottomColorPropertyDef());
        addPropertyDef(new CssAWBorderLeftColorPropertyDef());
        // -aw-border-insideH, -aw-border-insideV shorthand
        addPropertyDef(new CssAWBorderInsideHPropertyDef());
        addPropertyDef(new CssAWBorderInsideVPropertyDef());
        // -aw-border-inside widths
        addPropertyDef(new CssAWBorderInsideHWidthPropertyDef());
        addPropertyDef(new CssAWBorderInsideVWidthPropertyDef());
        // -aw-border-inside styles
        addPropertyDef(new CssAWBorderInsideHStylePropertyDef());
        addPropertyDef(new CssAWBorderInsideVStylePropertyDef());
        // -aw-border-inside colors
        addPropertyDef(new CssAWBorderInsideHColorPropertyDef());
        addPropertyDef(new CssAWBorderInsideVColorPropertyDef());

        addPropertyDef(new CssAWOutlineLevelPropertyDef());

        addPropertyDef(new CssContentPropertyDef());

        // The "src" property of the @font-face rule.
        addPropertyDef(new CssSrcPropertyDef());

        // SVG
        addPropertyDef(new CssStrokePropertyDef());
        addPropertyDef(new CssStrokeWidthPropertyDef());
        addPropertyDef(new CssStrokeDasharrayPropertyDef());
        addPropertyDef(new CssStrokeMiterlimitPropertyDef());
        addPropertyDef(new CssStrokeLineCapPropertyDef());
        addPropertyDef(new CssStrokeLineJoinPropertyDef());
        addPropertyDef(new CssFillPropertyDef());
        addPropertyDef(new CssFillOpacityPropertyDef());
        addPropertyDef(new CssFillRulePropertyDef());
        addPropertyDef(new CssOpacityPropertyDef());
        addPropertyDef(new CssTextAnchorPropertyDef());
        addPropertyDef(new CssStrokeDashOffsetPropertyDef());
        addPropertyDef(new CssBaseLineShiftPropertyDef());
        addPropertyDef(new CssDominantBaseLinePropertyDef());

        // MSO
        addPropertyDef(new CssMsoAnsiFontSizePropertyDef());
        addPropertyDef(new CssMsoAnsiFontStylePropertyDef());
        addPropertyDef(new CssMsoAnsiFontWeightPropertyDef());
        addPropertyDef(new CssMsoAnsiLanguagePropertyDef());
        addPropertyDef(new CssMsoAsciiFontFamilyPropertyDef());
        addPropertyDef(new CssMsoAsciiThemeFontPropertyDef());
        addPropertyDef(new CssMsoBidiFontFamilyPropertyDef());
        addPropertyDef(new CssMsoBidiFontSizePropertyDef());
        addPropertyDef(new CssMsoBidiFontStylePropertyDef());
        addPropertyDef(new CssMsoBidiFontWeightPropertyDef());
        addPropertyDef(new CssMsoBidiLanguagePropertyDef());
        addPropertyDef(new CssMsoBidiThemeFontPropertyDef());
        addPropertyDef(new CssMsoColorAltPropertyDef());
        addPropertyDef(new CssMsoFareastFontFamilyPropertyDef());
        addPropertyDef(new CssMsoFareastLanguagePropertyDef());
        addPropertyDef(new CssMsoFareastThemeFontPropertyDef());
        addPropertyDef(new CssMsoFontKerningPropertyDef());
        addPropertyDef(new CssMsoHansiFontFamilyPropertyDef());
        addPropertyDef(new CssMsoHansiThemeFontPropertyDef());
        addPropertyDef(new CssMsoNoProofPropertyDef());
        addPropertyDef(new CssMsoOutlineLevelPropertyDef());
        addPropertyDef(new CssMsoPaginationPropertyDef());
        addPropertyDef(new CssMsoParaMarginBottomPropertyDef());
        addPropertyDef(new CssMsoParaMarginLeftPropertyDef());
        addPropertyDef(new CssMsoParaMarginPropertyDef());
        addPropertyDef(new CssMsoParaMarginRightPropertyDef());
        addPropertyDef(new CssMsoParaMarginTopPropertyDef());
    }

    private static void addPropertyDef(CssPropertyDef propertyDef)
    {
        msDictionary.add(G_PROPERTY_DEFS, propertyDef.getName(), propertyDef);

        CssShorthandPropertyDef cssShorthand = ms.as(propertyDef, CssShorthandPropertyDef.class);
        if (cssShorthand != null)
            msArrayList.add(G_SHORTHAND_PROPERTY_DEFS, cssShorthand);
    }

    /// <summary>
    /// All supported properties, including shorthands.
    /// </summary>
    private static /*final*/ HashMap<String, CssPropertyDef> G_PROPERTY_DEFS;

    /// <summary>
    /// Supported shorthand properties.
    /// </summary>
    private static /*final*/ ArrayList<CssShorthandPropertyDef> G_SHORTHAND_PROPERTY_DEFS;

    static/* CssPropertyDefFactory()*/
    {
        G_PROPERTY_DEFS = new HashMap<String, CssPropertyDef>();
        G_SHORTHAND_PROPERTY_DEFS = new ArrayList<CssShorthandPropertyDef>();
        fillPropertyDefs();
    }
}

