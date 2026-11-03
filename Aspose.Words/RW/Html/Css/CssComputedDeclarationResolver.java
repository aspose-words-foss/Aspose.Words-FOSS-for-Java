// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/08/2015 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;


/// <summary>
/// Resolves specified CSS declarations for computing. E.g. 'em' and 'ex' units are computed to absolute lengths.
/// </summary>
class CssComputedDeclarationResolver
{
    CssComputedDeclarationResolver(boolean applyFormattingAsMsWord)
    {
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    /// <summary>
    /// Resolves specified CSS declarations for computing. E.g. 'em' and 'ex' units are computed to absolute lengths.
    /// </summary>
    /// <param name="declarations">Specified CSS declarations for computing.</param>
    /// <returns>Returns computed CSS declarations collection.</returns>
    CssDeclarationCollection resolveToComputed(CssDeclarationCollection declarations)
    {
        return resolveToComputed(declarations, null, null, false);
    }

    /// <summary>
    /// Resolves specified CSS declarations for computing. E.g. 'em' and 'ex' units are computed to absolute lengths.
    /// </summary>
    /// <param name="declarations">Specified CSS declarations for computing.</param>
    /// <param name="parentDeclarations">
    /// Computed declarations of a parent HTML element; can be null, if the element doesn't have a parent.
    /// </param>
    /// <param name="rootDeclarations">
    /// Computed declarations of a root HTML element; can be null, if the element doesn't have a root.
    /// </param>
    /// <param name="isPseudoElement">
    /// Indicates whether the current HTML element is a pseudo-element.
    /// </param>
    /// <returns>Returns computed CSS declarations collection.</returns>
    CssDeclarationCollection resolveToComputed(
        CssDeclarationCollection declarations,
        CssDeclarationCollection parentDeclarations,
        CssDeclarationCollection rootDeclarations,
        boolean isPseudoElement)
    {
        CssDeclarationCollection computedDeclarations = computeDeclarations(declarations, parentDeclarations,
            rootDeclarations, isPseudoElement);
        return resolveInterdependency(computedDeclarations);
    }

    /// <summary>
    /// Resolves specified CSS declarations to computed declarations.
    /// </summary>
    private CssDeclarationCollection computeDeclarations(
        CssDeclarationCollection declarations,
        CssDeclarationCollection parentDeclarations,
        CssDeclarationCollection rootDeclarations,
        boolean isPseudoElement)
    {
        // Skip empty collections since Java throws on them.
        if (declarations.getCount() == 0)
            return CssDeclarationCollection.EMPTY;

        // SPEED. It is actually faster to check whether any declarations should be computed and return fast in case no
        // changes are needed.
        ArrayList<CssSpecifiedDeclaration> declarationsToCompute = new ArrayList<CssSpecifiedDeclaration>();
        ArrayList<CssDeclaration> declarationsToRemove = new ArrayList<CssDeclaration>();
        for (CssDeclaration declaration : declarations)
        {
            CssSpecifiedDeclaration specifiedDeclaration = ms.as(declaration, CssSpecifiedDeclaration.class);
            if (specifiedDeclaration == null)
            {
                // Declaration has been computed already.
                continue;
            }

            if (specifiedDeclaration.getValue().isInherit())
            {
                // If a declaration has the 'inherit' value here, it computes to a default value and thus must be skipped,
                // because all non-default 'inherit' values have already been replaced in the inheritance resolver.
                msArrayList.add(declarationsToRemove, declaration);
                continue;
            }

            msArrayList.add(declarationsToCompute, specifiedDeclaration);
        }
        if ((declarationsToCompute.size() == 0) && (declarationsToRemove.size() == 0))
        {
            return declarations;
        }

        // Compute this element's font size first, because relative values of some properties (for instance, 'line-heigth')
        // refer to the element's font size.
        double fontSize = CssUtil.DEFAULT_FONT_SIZE;

        // According to the CSS specification, the font size should be taken from the HTML element. MS Word, however,
        // always uses a fixed font size value during import altChunk.
        if (!mApplyFormattingAsMsWord)
        {
            CssDeclaration fontSizeDeclaration = declarations.get("font-size");
            if (fontSizeDeclaration != null)
            {
                CssSpecifiedDeclaration specifiedElementFontSize = ms.as(fontSizeDeclaration, CssSpecifiedDeclaration.class);

                CssDeclaration computedElementFontSize;
                if (specifiedElementFontSize != null)
                {
                    CssCascadeContext cssContextWithoutFontSize = new CssCascadeContext(
                        rootDeclarations,
                        parentDeclarations,
                        0.0,
                        isPseudoElement);
                    computedElementFontSize = computeDeclaration(specifiedElementFontSize, cssContextWithoutFontSize);
                }
                else
                {
                    computedElementFontSize = fontSizeDeclaration;
                }

                fontSize = CssUtil.lengthToPoint(computedElementFontSize.getValue().getFirstValue());
                if (MathUtil.isMinValue(fontSize))
                {
                    fontSize = CssUtil.DEFAULT_FONT_SIZE;
                }
            }
        }

        CssCascadeContext cssContext = new CssCascadeContext(rootDeclarations, parentDeclarations, fontSize,
            isPseudoElement);

        CssDeclarationCollectionBuilder computedDeclarationBuilder = new CssDeclarationCollectionBuilder(declarations);
        for (CssDeclaration declaration : declarationsToRemove)
        {
            computedDeclarationBuilder.remove(declaration.getProperty());
        }
        for (CssSpecifiedDeclaration specifiedDeclaration : declarationsToCompute)
        {
            computedDeclarationBuilder.replace(computeDeclaration(specifiedDeclaration, cssContext));
        }

        return computedDeclarationBuilder.getDeclarations();
    }

    /// <summary>
    /// Resolves specified value of the declaration to computed value. E.g. 'em' and 'ex' units are computed to absolute lengths.
    /// </summary>
    /// <remarks>
    /// The computed value is the result of resolving the specified value, generally absolutizing it in preparation for inheritance.
    /// A specified value can be either absolute (i.e., not relative to another value, as in red or 2mm) or relative (i.e., relative to another value, 
    /// as in auto, 2em). Computing a relative value generally absolutizes it:
    ///   - values with relative units (em, ex, vh, vw) must be made absolute by multiplying with the appropriate reference size
    ///   - certain keywords (e.g., smaller, bolder) must be replaced according to their definitions
    ///   - percentages on some properties must be multiplied by a reference value (defined by the property)
    ///   - valid relative URLs must be resolved to become absolute.
    /// </remarks>
    /// <param name="declaration">Specified CSS declarations for computing.</param>
    /// <param name="cssContext">CSS context.</param>
    private static CssComputedDeclaration computeDeclaration(CssSpecifiedDeclaration declaration, CssCascadeContext cssContext)
    {
        CssPropertyDef propertyDef = CssPropertyDefFactory.getPropertyDef(declaration.getProperty());
        CssPropertyValue computedValue = propertyDef.toComputedValue(declaration.getValue(), cssContext);
        return new CssComputedDeclaration(propertyDef, computedValue, declaration.getImportant(), declaration.getValue());
    }

    /// <summary>
    /// Resolves certain interdependent CSS declarations to computed declarations.
    /// </summary>
    private CssDeclarationCollection resolveInterdependency(CssDeclarationCollection declarations)
    {
        // WORDSNET-23496 In common case MS Word doesn't use the 'color' CSS property to
        // specify a border property. We mimic MS Word behavior during import altChunk.
        if (mApplyFormattingAsMsWord)
            return declarations;

        // WORDSNET-14473 If an element's border color is not specified with a border property, 
        // user agents must use the value of the element's 'color' property as the computed value for the border color.
        CssDeclaration color = declarations.get("color");
        boolean hasAllBordersColor =
            (declarations.get("border-top-color") != null) &&
            (declarations.get("border-right-color") != null) &&
            (declarations.get("border-bottom-color") != null) &&
            (declarations.get("border-left-color") != null);

        // The fast path: no changes needed.
        if ((color == null) || hasAllBordersColor)
        {
            return declarations;
        }

        CssDeclarationCollectionBuilder resolvedDeclarationsBuilder = new CssDeclarationCollectionBuilder(declarations);
        copyColorToBorderColor(resolvedDeclarationsBuilder, color.getValue(), "border-top-color");
        copyColorToBorderColor(resolvedDeclarationsBuilder, color.getValue(), "border-right-color");
        copyColorToBorderColor(resolvedDeclarationsBuilder, color.getValue(), "border-bottom-color");
        copyColorToBorderColor(resolvedDeclarationsBuilder, color.getValue(), "border-left-color");
        return resolvedDeclarationsBuilder.getDeclarations();
    }

    /// <summary>
    /// Copies a color value to one of 'border-xxx-color' properties if the border color is missing from the declarations.
    /// </summary>
    private static void copyColorToBorderColor(
        CssDeclarationCollectionBuilder declarations,
        CssPropertyValue colorValue,
        String borderColorPropertyName)
    {
        if (declarations.get(borderColorPropertyName) != null)
        {
            return;
        }

        CssPropertyDef borderColorPropertyDef = CssPropertyDefFactory.getPropertyDef(borderColorPropertyName);
        declarations.add(new CssComputedDeclaration(borderColorPropertyDef, colorValue, false, colorValue));
        // The declaration we have created is considered a part of the user-agent CSS.
        declarations.markUserAgent(borderColorPropertyName);
    }

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;
}

