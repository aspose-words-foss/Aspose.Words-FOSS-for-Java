// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/11/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;

/// <summary>
/// Represents a record for a element in the user agent style sheet.
/// Derived classes created for concrete elements should be registered in <see cref="DefaultElementStyleResolver" />.
/// </summary>
abstract class ElementDef
{
    /// <summary>
    /// Adds default CSS style of the element to the collection of CSS declarations.
    /// </summary>
    /// <param name="element">
    /// The element whose default style is to be returned.
    /// </param>
    /// <param name="cssDeclarations">
    /// The collection of CSS declarations into which element's default CSS style is to be added.
    /// </param>
    /// <param name="omittedFormatting">
    /// Groups of user agent (default) CSS styles that are omitted from style resolution, because they are overridden
    /// by targed document formatting.
    /// </param>
    void applyDefaultCss(
        IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations,
        /*CssUserAgentFormatting*/int omittedFormatting)
    {
        // The 'dir' attribute affects 'unicode-bidi' but this style has lower priority than the default 'unicode-bidi'
        // style of the element, so it is applied before default styles.
        translateDirAttributeToCss(element, cssDeclarations);

        // Default styles of the element that cannot be overridden by target document formatting.
        applyStyles(element, cssDeclarations);

        // Default styles of the element that can be overridden by target document formatting.
        if ((omittedFormatting & CssUserAgentFormatting.FONT) == 0)
        {
            applyOverridableFontStyles(element, cssDeclarations);
        }

        // Value of the 'hidden' attribute has higher priority than default 'display' style of the element,
        // so it is applied after default styles.
        if (canBeHidden())
        {
            translateHiddenAttributeToCss(element, cssDeclarations);
        }
    }

    /// <summary>
    /// Indicates whether the element can be hidden completely by setting "display:none" when the "hidden" attribute 
    /// is applied to it.
    /// </summary>
    /// <returns>
    /// <c>true</c> if the "hidden" attribute sets "display:none" on element. Otherwise, <c>false</c>.
    /// </returns>
    protected /*virtual*/ boolean canBeHidden()
    {
        // For most HTML elements the "hidden" attribute is translated to "display:none".
        return true;
    }

    /// <summary>
    /// Adds normal (mandatory) default styles of the element to the collection of CSS declarations.
    /// </summary>
    /// <param name="element">
    ///     The HTML element whose default styles are to be returned.
    /// </param>
    /// <param name="cssDeclarations">
    ///     The collection of CSS declarations into which element's default styles are to be added.
    /// </param>
    /// <remarks>
    /// Override in derived classes to add HTML element's default styles into the collection.
    /// </remarks>
    protected abstract void applyStyles(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations);

    /// <summary>
    /// Adds overridable default font styles of the element to the collection of CSS declarations.
    /// </summary>
    /// <param name="element">
    ///     The element whose default font styles are to be returned.
    /// </param>
    /// <param name="cssDeclarations">
    ///     The collection of CSS declarations into which element's default font styles are to be added.
    /// </param>
    /// <remarks>
    /// Override in derived classes to add element's overridable default font styles into the collection.
    /// </remarks>
    protected /*virtual*/ void applyOverridableFontStyles(IElementProvider element,
        CssDeclarationCollectionBuilder cssDeclarations)
    {
        // Empty method. Expected to be implemented in descendant classes.
    }

    /// <summary>
    /// Translate 'bgcolor' attribute to corresponding CSS rule.
    /// </summary>
    protected static void translateBGColorAttributeToCss(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // "bgcolor" attribute
        CssValue bgcolorValue = CssValue.parseLegacyColor(element.getAttributeValue("bgcolor"));
        if (bgcolorValue != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("background-color", bgcolorValue));
        }
    }

    /// <summary>
    /// Translate 'valign' attribute to corresponding CSS rule.
    /// </summary>
    protected static void translateVAlignAttributeToCss(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        // "valign" attribute
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("valign", ""))))
        {
            case /*"top"*/0:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Top));
                break;
            case /*"middle"*/1:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Middle));
                break;
            case /*"bottom"*/2:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Bottom));
                break;
            case /*"baseline"*/3:
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("vertical-align", CssValue.Baseline));
                break;
            default:
                // Other 'valign' attribute values are ignored.
                break;
        }
    }

    /// <summary>
    /// Translate 'dir' common attribute to corresponding CSS rule.
    /// </summary>
    private static void translateDirAttributeToCss(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        switch (gStringSwitchMap.of(msString.toLowerInvariant(element.getAttributeValue("dir", ""))))
        {
            case /*"ltr"*/4:
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("direction", CssValue.Ltr));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("unicode-bidi", CssValue.Embed));
                break;
            }
            case /*"rtl"*/5:
            {
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("direction", CssValue.Rtl));
                cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("unicode-bidi", CssValue.Embed));
                break;
            }
            default:
            {
                // Other 'align' attribute values are ignored.
                break;
            }
        }
    }

    /// <summary>
    /// Translate 'hidded' common attribute to corresponding CSS rule.
    /// </summary>
    private static void translateHiddenAttributeToCss(IElementProvider element, CssDeclarationCollectionBuilder cssDeclarations)
    {
        if (element.getAttributeValue("hidden") != null)
        {
            cssDeclarations.addOrReplace(new CssSpecifiedDeclaration("display", CssValue.None));
        }
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"top",
		"middle",
		"bottom",
		"baseline",
		"ltr",
		"rtl"
	);

}

