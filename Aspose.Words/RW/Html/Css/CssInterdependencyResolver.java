// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Resolves CSS declaration interdependency and converts certain vendor-specific declarations into normal declarations.
/// This is expected to be the final step in CSS declaration resolution.
/// </summary>
class CssInterdependencyResolver
{
    CssInterdependencyResolver(/*CssUserAgentFormatting*/int omittedUserAgentFormatting)
    {
        mOmittedUserAgentFormatting = omittedUserAgentFormatting;
    }

    /// <summary>
    /// Performs in-place resolution of HTML element's interdependent CSS declarations.
    /// </summary>
    void resolve(CssDeclarationCollectionBuilder declarations, IElementProvider element)
    {
        // Here the directionality of the element has already been resolved, so custom direction-specific properties
        // can be replaced with their counterparts.
        replaceStartCustomProperty(declarations, "-aw-margin-start", "margin");
        replaceStartCustomProperty(declarations, "-aw-padding-start", "padding");

        // Values of 'align' attributes of outer elements should not affect table contents, but values of 'text-align'
        // properties of outer elements should. Here the table's 'text-align' value is resetted to the initial value
        // if its current value comes from an 'align' attribute.
        if ("table".equals(element.getElementName()))
        {
            CssDeclaration textAlign = declarations.get("text-align");
            if (textAlign != null)
            {
                if (textAlign.getValue().equals(CssValue.AwLeft) ||
                    textAlign.getValue().equals(CssValue.AwCenter) ||
                    textAlign.getValue().equals(CssValue.AwRight))
                {
                    declarations.remove(textAlign.getProperty());
                    declarations.add(new CssSpecifiedDeclaration("text-align", CssValue.AwStart));
                    declarations.markUserAgent("text-align");
                }
            }

            CssDeclaration borderCollapse = declarations.get("border-collapse");
            if ((borderCollapse != null) && borderCollapse.getValue().equals(CssValue.Collapse))
            {
                // WORDSNET-9462 According to w3w we should ignore border-spacing if value of 'border-collapse' equals to 'collapse'.
                declarations.addOrReplace(new CssSpecifiedDeclaration("border-spacing", CssValue.Zero));
                declarations.markUserAgent("border-spacing");
            }
        }

        // This rule is a part of the user agent style sheet, but it depends on CSS property values of the element's parent,
        // so we have to apply it here.
        if ("th".equals(element.getElementName()))
        {
            CssDeclaration textAlingDeclaration = declarations.get("text-align");
            if ((textAlingDeclaration == null) || (textAlingDeclaration.getValue().equals(CssValue.AwStart)))
            {
                declarations.addOrReplace(new CssSpecifiedDeclaration("text-align", CssValue.Center));
                declarations.markUserAgent("text-align");
            }
        }

        // The following rules affect the font size. They are not applied if it is overridden by the font size specified 
        // in the target document.
        boolean applyDefaultFontFormatting = (mOmittedUserAgentFormatting & CssUserAgentFormatting.FONT) == 0;
        if (applyDefaultFontFormatting)
        {
            // WORDSNET-9334 In modern browsers, if an element has the "font-family:monospace" declaration, its font size
            // is changed to 10pt (as a result, "font-family:monospace" means "font-family:'Courier New';font-size:10pt").
            // However, the font size is not changed when the 'font-family' declaration contains a list of font families,
            // even if 'monospace' is on that list.
            if (declarations.get("font-size") == null)
            {
                CssDeclaration fontFamilyDeclaration = declarations.get("font-family");
                if ((fontFamilyDeclaration != null) && fontFamilyDeclaration.getValue().equals(CssValue.Monospace))
                {
                    declarations.add(new CssSpecifiedDeclaration("font-size", new CssLengthValue(10.0, CssUnit.PT)));
                    declarations.markUserAgent("font-size");
                }
            }
        }
    }

    /// <summary>
    /// Replaces custom direction-specific properties with their counterparts depending on the element directionality.
    /// </summary>
    /// <param name="declarations">CSS declarations of an HTML element.</param>
    /// <param name="customPropertyName">The name of the custom direction-specific property to be replaced.</param>
    /// <param name="commonPropertyPrefix">The first part of the counterpart property name. An appropriate suffix
    /// will be appended to this parameter in order to form the full property name.</param>
    private static void replaceStartCustomProperty(
        CssDeclarationCollectionBuilder declarations,
        String customPropertyName,
        String commonPropertyPrefix)
    {
        CssDeclaration customPropertyDeclaration = declarations.get(customPropertyName);
        if (customPropertyDeclaration == null)
        {
            return;
        }

        // Determine element's direction and writing mode.
        CssDeclaration directionDeclaration = declarations.get("direction");
        boolean isRtl = (directionDeclaration != null) && directionDeclaration.getValue().equals(CssValue.Rtl);

        CssDeclaration writingModeDeclaration = declarations.get("writing-mode");
        boolean isVertical = (writingModeDeclaration != null) &&
            (writingModeDeclaration.getValue().equals(CssValue.VerticalLr) || writingModeDeclaration.getValue().equals(CssValue.VerticalRl));

        // Determine what 'start' in the custom property name means based on element's direction and writing mode.
        String suffix;
        if (isRtl)
        {
            suffix = (isVertical)
                ? "bottom"
                : "right";
        }
        else
        {
            suffix = (isVertical)
                ? "top"
                : "left"; 
        }
        String counterpartPropertyName = commonPropertyPrefix + "-" + suffix;

        // Write a common counterpart to the replaced custom property.
        // The custom properties has lower priority than their counterparts, so the replacement is only performed
        // if no counterpart property is applied to the element.
        CssDeclaration counterpartDeclaration = declarations.get(counterpartPropertyName);
        if (counterpartDeclaration == null)
        {
            declarations.add(new CssSpecifiedDeclaration(counterpartPropertyName, customPropertyDeclaration.getValue()));
            declarations.setFlags(counterpartPropertyName, declarations.getFlags(customPropertyName));
        }

        // Remove the custom property.
        declarations.remove(customPropertyDeclaration.getProperty());
    }

    /// <summary>
    /// Groups of user agent (default) CSS styles that are omitted from style resolution.
    /// </summary>
    private /*final*/ /*CssUserAgentFormatting*/int mOmittedUserAgentFormatting;
}

