// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a record for a HTML element in the user agent style sheet.
/// Derived classes created for concrete HTML elements should be registered
/// in <see cref="DefaultElementStyleResolver" />.
/// </summary>
abstract class HtmlElementDef extends ElementDef
{
    protected /*override*/ void applyStyles(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        applyStyles((IHtmlElementProvider)element, cssDeclarations);
    }

    protected /*override*/ void applyOverridableFontStyles(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        applyOverridableFontStyles((IHtmlElementProvider)element, cssDeclarations);
    }

    protected abstract void applyStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations);

    protected /*virtual*/ void applyOverridableFontStyles(
        IHtmlElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Most HTML element don't have default overridable CSS styles.
    }
    /// <summary>
    /// Translate 'align' attribute to a corresponding CSS rule.
    /// </summary>
    protected static void translateAlignAttributeToCss(IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations, boolean allowAbsmiddleValue)
    {
        final String TEXT_ALIGN = "text-align";

        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("align", ""))))
        {
            case /*"middle"*/0:
            case /*"center"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration(TEXT_ALIGN, CssValue.AwCenter));
                break;
            case /*"absmiddle"*/2:
                if (allowAbsmiddleValue)
                {
                    cssDeclarations.addOrReplace(new CssSpecifiedDeclaration(TEXT_ALIGN, CssValue.Center));
                }
                break;
            case /*"left"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration(TEXT_ALIGN, CssValue.AwLeft));
                break;
            case /*"right"*/4:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration(TEXT_ALIGN, CssValue.AwRight));
                break;
            case /*"justify"*/5:
                // WORDSNET-7232 MS Word processes "justify" value of "align" attribute as "start" (the initial value
                // of the "text-align" attribute). So do we.
                cssDeclarations.remove(TEXT_ALIGN);
                break;
            default:
                // Other 'align' attribute values are ignored.
                break;
        }
    }

    /// <summary>
    /// Translate 'height' attribute to corresponding CSS rule.
    /// </summary>
    protected static void translateHeightAttributeToCss(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // "height" attribute
        CssValue heightValue = CssValue.parseLegacyDimension(element.getAttributeValue("height"));
        if (heightValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("height", heightValue));
        }
    }

    /// <summary>
    /// Translate 'width' attribute to corresponding CSS rule.
    /// </summary>
    protected static void translateWidthAttributeToCss(IHtmlElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // "width" attribute
        CssValue widthValue = CssValue.parseLegacyDimension(element.getAttributeValue("width"));
        if (widthValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("width", widthValue));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"middle",
		"center",
		"absmiddle",
		"left",
		"right",
		"justify"
	);


}

