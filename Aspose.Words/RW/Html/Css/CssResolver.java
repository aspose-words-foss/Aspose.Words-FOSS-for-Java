// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.Collection;
import com.aspose.DrColor;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.StringUtil;
import com.aspose.IntToObjDictionary;


/// <summary>
/// Calculates CSS declarations for an HTML element using CSS cascading and inheritance principles.
/// </summary>
class CssResolver
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="documentStyleRules">CSS style rules of a HTML document.</param>
    /// <param name="defaultElementStyleResolver"></param>
    /// <param name="cascadingOrderResolver"></param>
    /// <param name="interdependencyResolver"></param>
    /// <param name="computedDeclarationResolver"></param>
    /// <param name="documentMode"></param>
    /// <param name="defaultQuirksBodyColor"></param>
    CssResolver(
        Collection<CssStyleRule> documentStyleRules,
        DefaultElementStyleResolver defaultElementStyleResolver,
        CssCascadingOrderResolver cascadingOrderResolver,
        CssInterdependencyResolver interdependencyResolver,
        CssComputedDeclarationResolver computedDeclarationResolver,
        /*CssDocumentMode*/int documentMode,
        DrColor defaultQuirksBodyColor)
    {
        Debug.assertTrue(documentStyleRules != null);
        Debug.assertTrue(defaultElementStyleResolver != null);
        Debug.assertTrue(cascadingOrderResolver != null);
        Debug.assertTrue(interdependencyResolver != null);
        Debug.assertTrue(computedDeclarationResolver != null);

        mCssMatcher = new CssMatcher(documentStyleRules, documentMode);
        mDefaultElementStyleResolver = defaultElementStyleResolver;
        mCascadingOrderResolver = cascadingOrderResolver;
        mInterdependencyResolver = interdependencyResolver;
        mDocumentMode = documentMode;
        mDefaultQuirksBodyColor = defaultQuirksBodyColor;
        mComputedDeclarationResolver = computedDeclarationResolver;
        mAncestorCss = new ArrayList<ResolvedCss>();
    }

    /// <summary>
    /// Adds an element to the stack and calculates CSS declarations for this element using the cascading and inheritance principles.
    /// </summary>
    void pushElement(IElementProvider element)
    {
        pushElement(element, null);
    }

    /// <summary>
    /// Adds an element to the stack and calculates CSS declarations for this element using the cascading and inheritance
    /// principles.
    /// </summary>
    /// <param name="element">An element to add.</param>
    /// <param name="additionalInlineDeclarations">
    /// Additional inline CSS declarations. Can be <c>null</c>. These declarations are processed as if they were appended
    /// to the text of the element's "style" attribute.
    /// </param>
    void pushElement(IElementProvider element, CssDeclarationCollection additionalInlineDeclarations)
    {
        CssDeclarationCollectionBuilder declarations = new CssDeclarationCollectionBuilder();
        CssMatchedRules matchedRules = mCssMatcher.push(element);

        // Implicit elements don't exist in original HTML code and don't have declarations from cascade.
        if (!element.isImplicit())
        {
            calculateFromCascade(declarations, element, matchedRules.getRules(HtmlElementPart.ELEMENT),
                additionalInlineDeclarations);
        }

        calculateFromParentElement(declarations);
        resolveInterdependency(declarations, element);

        // A resolved declaration list must contain only computed (not specified) values.
        // This means that value computation must be the last step of declaration resolution.
        CssDeclarationCollection resolvedElementDeclarations = resolveComputedValues(declarations, false);

        // Saving the "color" value for the body. This is the color the table gets in the quirk mode by default.
        if ("body".equals(element.getElementName()))
        {
            mBodyColor = resolvedElementDeclarations.getColor("color");
        }

        ResolvedCss resolvedCss = new ResolvedCss(resolvedElementDeclarations, matchedRules);
        msArrayList.add(mAncestorCss, resolvedCss);

        calculateAndFillPseudoElementDeclarations(matchedRules, HtmlElementPart.BEFORE);
        calculateAndFillPseudoElementDeclarations(matchedRules, HtmlElementPart.AFTER);
    }

    private void calculateAndFillPseudoElementDeclarations(CssMatchedRules matchedRules, /*HtmlElementPart*/int part)
    {
        // Fill declarations for the pseudo-element if there are any CSS rules for that pseudo-element in the
        // document's stylesheet. Implicit (anonymous auto-generated elements) don't have pseudo-elements.
        // Pseudo-elements that have no matched rules cannot have any content and are ignored.
        ArrayList<CssStyleRule> rules = matchedRules.getRules(part);
        if (rules.size() > 0)
        {
            CssDeclarationCollection pseudoElementDeclarations = calculatePseudoElementDeclarations(rules);
            Debug.assertTrue(mAncestorCss.size() > 0);
            ResolvedCss currentCss = getCurrentCss();

            // The pseudo-elements are technically children of the current HTML element and we must know parent's
            // CSS declarations in order to calculate declarations of the pseudo-elements.
            // That is why we have to use the following method that modifies already resolved CSS:
            // 1) calculate and store parent's declarations.
            // 2) use parent's declarations to calculate pseudo-element's declarations.
            // 3) store pseudo-element's declarations next to parent's declarations.
            currentCss.setDeclarations(part, pseudoElementDeclarations);
        }
    }

    /// <summary>
    /// Removes a top element from the stack.
    /// </summary>
    void popElement()
    {
        Debug.assertTrue(mAncestorCss.size() != 0);
        mAncestorCss.remove(mAncestorCss.size() - 1);
        mCssMatcher.pop();
    }

    /// <summary>
    /// Returns CSS declarations calculated for the current HTML tree element.
    /// </summary>
    CssDeclarationCollection getDeclarations()
    {
        return getDeclarations(HtmlElementPart.ELEMENT);
    }

    /// <summary>
    /// Gets CSS declarations applied to the specified part of the current HTML element.
    /// </summary>
    CssDeclarationCollection getDeclarations(/*HtmlElementPart*/int part)
    {
        ResolvedCss currentCss = getCurrentCss();
        return currentCss.getDeclarations(part);
    }

    /// <summary>
    /// Returns CSS selectors matched for the current HTML tree element.
    /// </summary>
    ArrayList<CssSelector> getSelectors()
    {
        return getCurrentCss().getSelectors();
    }

    /// <summary>
    /// Returns CSS selector declarations matched for the current HTML tree element.
    /// </summary>
    CssDeclarationCollection getSelectorDeclarations()
    {
        return getCurrentCss().getSelectorDeclarations();
    }

    DefaultElementStyleResolver getDefaultElementStyleResolver() { return mDefaultElementStyleResolver; }
    void setDefaultElementStyleResolver(DefaultElementStyleResolver value) { mDefaultElementStyleResolver = value; }
    /// <summary>
    /// Calculates CSS declarations for a HTML tree element using the cascade principle.
    /// </summary>
    /// <param name="declarations">A declaration collection builder where calculated declarations will be stored.</param>
    /// <param name="element">HTML tree element.</param>
    /// <param name="matchedRules">CSS rules that match the specified element.</param>
    /// <param name="additionalInlineDeclarations">
    /// Additional inline CSS declarations. Can be <c>null</c>. These declarations are processed as if they were appended
    /// to the text of the element's "style" attribute.
    /// </param>
    /// <remarks>
    /// The cascade is a mechanism for determining which styles should be applied to a given element,
    /// based on the rules that have cascaded down from various sources.
    /// 1. Find all declarations that apply to a given element/property combination, for the target media type.
    /// 2. Sort declarations according to their importance (normal or important) and origin (author, user, or user agent). 
    ///    From highest to lowest precedence:
    ///    1.  user !important declarations
    ///    2.  author !important declarations
    ///    3.  author normal declarations
    ///    4.  user normal declarations
    ///    5.  user agent declarations
    /// 3. If declarations have the same importance and source, sort them by selector specificity.
    /// 4. Finally, if declarations have the same importance, source, and specificity, sort them by the order 
    ///   they are specified in the CSS. The last declaration wins.
    /// http://www.w3.org/TR/CSS21/cascade.html#cascade
    /// </remarks>
    private void calculateFromCascade(
        CssDeclarationCollectionBuilder declarations,
        IElementProvider element,
        Iterable<CssStyleRule> matchedRules,
        CssDeclarationCollection additionalInlineDeclarations)
    {
        // Reset the cascade resolver to initial state.
        mCascadingOrderResolver.clear();
        // Default CSS style and presentational hints.
        CssDeclarationCollection defaultStyleDeclarations = mDefaultElementStyleResolver.getDeclarations(element);

        mCascadingOrderResolver.add(defaultStyleDeclarations, CssDeclarationOrigin.USER_AGENT, CssSelectorSpecificity.PresentationalHintSpecificity);

        // WORDSNET-11639 Incorrect default font color of tables when importing an HTML document in Quirks mode.
        // In Quirks mode, tables get the 'color' value not from their parent elements, but from the <body> element.
        if ((mDocumentMode == CssDocumentMode.QUIRKS) && ("table".equals(element.getElementName())))
        {
            // WORDSNET-11913 Insertion using DocumentBuilder.InsertHtml is always performed in Quirks mode.
            // By default the color should be black. In case of using the DocumentBuilder formatting (useBuilderFormatting=true), 
            // its color must be undefined.
            DrColor color = (mBodyColor != null) ? mBodyColor : mDefaultQuirksBodyColor;
            if (color != null)
            {
                CssDeclarationCollectionBuilder builder = new CssDeclarationCollectionBuilder();
                builder.add(new CssSpecifiedDeclaration("color", CssHashValue.fromColor(color)));
                mCascadingOrderResolver.add(builder.getDeclarations(), CssDeclarationOrigin.USER_AGENT, CssSelectorSpecificity.INLINE_STYLE_SPECIFICITY);
            }
        }

        // Author style sheet.
        for (CssStyleRule rule : matchedRules)
        {
            for (CssSelector selector : rule.getSelectors())
            {
                mCascadingOrderResolver.add(rule.getDeclarations(), CssDeclarationOrigin.AUTHOR, selector.getSpecificity());
            }
        }

        // 'style' HTML attribute.
        CssDeclarationCollection styleDeclarations = null;
        IInlineStyleDeclarationsProvider declarationsProvider = ms.as(element, IInlineStyleDeclarationsProvider.class);
        if (declarationsProvider != null)
        {
            styleDeclarations = declarationsProvider.getInlineStyle();
        }
        else
        {
            String inlineStyle = element.getAttributeValue("style");
            if (StringUtil.hasChars(inlineStyle))
                styleDeclarations = CssParser.parseDeclarations(inlineStyle, mDocumentMode);
        }
        if ((styleDeclarations != null) && (styleDeclarations.getCount() != 0))
        {
            mCascadingOrderResolver.add(styleDeclarations, CssDeclarationOrigin.AUTHOR,
                CssSelectorSpecificity.INLINE_STYLE_SPECIFICITY);
        }

        // Additional inline declarations. These declarations are processed as if they were appended to the end of the
        // 'style' attribute.
        if (additionalInlineDeclarations != null)
        {
            mCascadingOrderResolver.add(additionalInlineDeclarations, CssDeclarationOrigin.AUTHOR,
                CssSelectorSpecificity.INLINE_STYLE_SPECIFICITY);
        }

        mCascadingOrderResolver.resolve(declarations);
    }

    /// <summary>
    /// Calculates CSS declarations for a HTML tree element using the inheritance principle.
    /// </summary>
    /// <remarks>
    /// Inheritance is the process by which elements inherit the values of properties from their ancestors in the DOM tree.
    /// http://www.w3.org/TR/CSS21/cascade.html#inheritance
    /// </remarks>
    private void calculateFromParentElement(CssDeclarationCollectionBuilder declarations)
    {
        if (mAncestorCss.size() == 0)
            return;

        CssDeclarationCollection parentElementDeclarations = getCurrentCss().getDeclarations();
        CssInheritanceResolver.resolve(declarations, parentElementDeclarations);
    }

    /// <summary>
    /// Resolves some specified values to computed values. E.g. 'em' and 'ex' units are computed to absolute lengths.
    /// </summary>
    /// <param name="declarations"></param>
    /// <remarks>
    /// The computed value is the result of resolving the specified value, generally absolutizing it in preparation
    /// for inheritance.
    /// </remarks>
    /// <param name="isPseudoElement">
    /// Indicates whether the current HTML element is a pseudo-element.
    /// </param>
    /// <returns>CSS declarations with the computed values.</returns>
    private CssDeclarationCollection resolveComputedValues(
        CssDeclarationCollectionBuilder declarations,
        boolean isPseudoElement)
    {
        CssDeclarationCollection rootDeclarations = getRootCss().getDeclarations();
        CssDeclarationCollection parentDeclarations = getCurrentCss().getDeclarations();
        return mComputedDeclarationResolver.resolveToComputed(declarations.getDeclarations(), parentDeclarations,
            rootDeclarations, isPseudoElement);
    }

    /// <summary>
    /// Resolves certain interdependent CSS declarations.
    /// This is expected to be the final step of CSS declaration resolution.
    /// </summary>
    private void resolveInterdependency(CssDeclarationCollectionBuilder declarations, IElementProvider element)
    {
        mInterdependencyResolver.resolve(declarations, element);
    }

    /// <summary>
    /// Calculates CSS declarations applied a pseudo-element of the current HTML element.
    /// </summary>
    /// <param name="rules">A collection of <see cref="CssStyleRule"/> that match the pseudo-element.</param>
    private CssDeclarationCollection calculatePseudoElementDeclarations(ArrayList<CssStyleRule> rules)
    {
        Debug.assertTrue(rules.size() > 0);

        if (rules.size() == 0)
        {
            return CssDeclarationCollection.EMPTY;
        }

        CssDeclarationCollectionBuilder declarations = new CssDeclarationCollectionBuilder();

        // Calculate rules applied through the CSS cascade.
        mCascadingOrderResolver.clear();
        for (CssStyleRule rule : rules)
        {
            for (CssSelector selector : rule.getSelectors())
            {
                mCascadingOrderResolver.add(rule.getDeclarations(), CssDeclarationOrigin.AUTHOR, selector.getSpecificity());
            }
        }
        mCascadingOrderResolver.resolve(declarations);

        // Calculate style inherited from the parent element. The element itself is the parent for its pseudo-elements.
        Debug.assertTrue(mAncestorCss.size() > 0);
        CssDeclarationCollection parentElementDeclarations = getDeclarations();
        CssInheritanceResolver.resolve(declarations, parentElementDeclarations);

        return resolveComputedValues(declarations, true);
    }

    /// <summary>
    /// Gets resolved CSS of the current (most recently pushed) element.
    /// </summary>
    /// <returns>
    /// Resolved CSS of the current element. If no elements have been processed yet, empty CSS is returned.
    /// </returns>
    private ResolvedCss getCurrentCss()
    {
        return getAncestorCss(1);
    }

    /// <summary>
    /// Gets resolved CSS of the root element.
    /// </summary>
    /// <returns>
    /// Resolved CSS of the root element. If no elements have been processed yet, empty CSS is returned.
    /// </returns>
    private ResolvedCss getRootCss()
    {
        return getAncestorCss(mAncestorCss.size());
    }

    /// <summary>
    /// Gets resolved CSS for an ancestor element at the specified one-based depth.
    /// </summary>
    /// <param name="depth">One-based depth of the ancestor element to find (1 - most recently processed (current) element,
    /// 2 - its parent, etc.)</param>
    /// <returns>
    /// Resolved CSS of the ancestor element. If the element is not found (the specified depth is incorrect),
    /// empty CSS is returned.
    /// </returns>
    private ResolvedCss getAncestorCss(int depth)
    {
        return (depth >= 1) && (depth <= mAncestorCss.size())
            ? mAncestorCss.get(mAncestorCss.size() - depth)
            : ResolvedCss.EMPTY;
    }

    private /*final*/ CssMatcher mCssMatcher;

    private DefaultElementStyleResolver mDefaultElementStyleResolver;
    private /*final*/ CssCascadingOrderResolver mCascadingOrderResolver;
    private /*final*/ CssInterdependencyResolver mInterdependencyResolver;

    /// <summary>
    /// Resolved CSS of HTML elements pushed to the resolver. Elements are of type <see cref="ResolvedCss"/> and are stored
    /// from outer to inner (the last element in the list is the most recently pushed).
    /// </summary>
    private /*final*/ ArrayList<ResolvedCss> mAncestorCss;

    private DrColor mBodyColor;
    private /*final*/ /*CssDocumentMode*/int mDocumentMode;
    private /*final*/ DrColor mDefaultQuirksBodyColor;
    private /*final*/ CssComputedDeclarationResolver mComputedDeclarationResolver;
}

/// <summary>
/// CSS declarations resolved for an HTML element.
/// </summary>
class ResolvedCss
{
    ResolvedCss(CssDeclarationCollection declarations, CssMatchedRules rules)
    {
        Debug.assertTrue(declarations != null);
        Debug.assertTrue(rules != null);

        mDeclarations = declarations;
        mSelectors = rules.getSelectors(HtmlElementPart.ELEMENT);
        mRules = rules;
    }

    /// <summary>
    /// CSS declarations of the element itself (not of any of its pseudo-elements).
    /// </summary>
    CssDeclarationCollection getDeclarations() { return mDeclarations; }

    /// <summary>
    /// Selectors that match the element itself (not any of its pseudo-elements).
    /// </summary>
    ArrayList<CssSelector> getSelectors() { return mSelectors; }

    CssDeclarationCollection getSelectorDeclarations()
    {
        CssDeclarationCollectionBuilder declarationsBuilder = new CssDeclarationCollectionBuilder();
        CssCascadingOrderResolver resolver = new CssCascadingOrderResolver();
        for (CssStyleRule rule : mRules.getRules(HtmlElementPart.ELEMENT))
        {
            for (CssSelector selector : rule.getSelectors())
            {
                resolver.add(rule.getDeclarations(), CssDeclarationOrigin.AUTHOR, selector.getSpecificity());
            }
        }
        resolver.resolve(declarationsBuilder);
        return declarationsBuilder.getDeclarations();
    }

    /// <summary>
    /// Gets declarations of the given part of the element.
    /// </summary>
    /// <returns>
    /// Css declarations of the given part of the element. If no declarations match the part, an empty collection
    /// is returned.
    /// </returns>
    CssDeclarationCollection getDeclarations(/*HtmlElementPart*/int part)
    {
        CssDeclarationCollection result;
        if (part == HtmlElementPart.ELEMENT)
        {
            result = mDeclarations;
        }
        else if (mPseudoElementDeclarations != null)
        {
            result = mPseudoElementDeclarations.get((int)part);
            if (result == null)
            {
                result = CssDeclarationCollection.EMPTY;
            }
        }
        else
        {
            result = CssDeclarationCollection.EMPTY;
        }

        Debug.assertTrue(result != null);
        return result;
    }

    /// <summary>
    /// Sets declarations of the given part of the element.
    /// </summary>
    /// <remarks>
    /// Only pseudo-element declarations can be set using this method. Declarations of the element itself must be set
    /// by using the constructor.
    /// Declarations can be set only once for any given part. Replacing declarations is not allowed.
    /// </remarks>
    void setDeclarations(/*HtmlElementPart*/int part, CssDeclarationCollection declarations)
    {
        Debug.assertTrue(part != HtmlElementPart.ELEMENT);
        Debug.assertTrue(declarations != null);

        if (mPseudoElementDeclarations == null)
        {
            mPseudoElementDeclarations = new IntToObjDictionary<CssDeclarationCollection>();
        }

        CssDeclarationCollection existingDeclarations = mPseudoElementDeclarations.get((int)part);
        if (existingDeclarations == null)
        {
            mPseudoElementDeclarations.set((int)part, declarations);
        }
        else
        {
            // Replacing declarations is not allowed.
            Debug.assertTrue(false);
        }
    }

    /// <summary>
    /// Empty CSS. Contains no declarations and no selectors.
    /// </summary>
    static /*final*/ ResolvedCss EMPTY = new ResolvedCss(CssDeclarationCollection.EMPTY, new CssMatchedRules());

    private /*final*/ CssDeclarationCollection mDeclarations;

    private /*final*/ ArrayList<CssSelector> mSelectors;

    /// <summary>
    /// CSS declarations of pseudo-elements that belong to this HTML element.
    /// Key is <see cref="HtmlElementPart"/> casted to <c>int</c>.
    /// Key is never equal to <see cref="HtmlElementPart.Element"/>.
    /// Value is <see cref="CssDeclarationCollection"/>. Values are never <c>null</c>.
    /// </summary>
    /// <remarks>
    /// Most elements don't contain visible pseudo-elements and this field is lazily initialized when needed in order
    /// to save memory.
    /// </remarks>
    private IntToObjDictionary<CssDeclarationCollection> mPseudoElementDeclarations;

    private /*final*/ CssMatchedRules mRules;
}

