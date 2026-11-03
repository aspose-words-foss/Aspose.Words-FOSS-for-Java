// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/06/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;
import java.util.Collection;
import java.util.ArrayList;


/// <summary>
/// Helps to create CSS engine classes.
/// </summary>
class CssClassFactory
{
    /// <summary>
    /// Constructor. Creates a factory for the Standards mode with no omitted formatting and with black body text color.
    /// </summary>
    CssClassFactory()
    {
    	this(CssDocumentMode.STANDARDS);
	
        // Empty constructor.
    }

    /// <summary>
    /// Constructor. Creates a factory with no omitted formatting and with black body text color.
    /// </summary>
    /// <param name="documentMode">Document mode.</param>
    CssClassFactory(/*CssDocumentMode*/int documentMode)
    {
    	this(documentMode, CssUserAgentFormatting.NONE, DrColor.BLACK, false);
	
        // Empty constructor.
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="documentMode">Document mode.</param>
    /// <param name="omittedUserAgentFormatting">
    /// Groups of user agent (default) CSS styles that are omitted from style resolution.
    /// </param>
    /// <param name="defaultQuirksBodyColor">
    /// Defines the body color for quirks mode.
    /// </param>
    /// <param name="applyFormattingAsMsWord">
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </param>
    CssClassFactory(
        /*CssDocumentMode*/int documentMode,
        /*CssUserAgentFormatting*/int omittedUserAgentFormatting,
        DrColor defaultQuirksBodyColor,
        boolean applyFormattingAsMsWord)
    {
        mDocumentMode = documentMode;
        mOmittedUserAgentFormatting = omittedUserAgentFormatting;
        mDefaultQuirksBodyColor = defaultQuirksBodyColor;
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    /// <summary>
    /// Creates a new instance of <see cref="CssReader" /> class.
    /// </summary>
    /// <param name="baseUri">Base URI of the document. The base URI is used to resolve relative URI of external CSS documents.</param>
    /// <param name="htmlResourceLoader">HtmlResourceLoader used for loading resources.
    /// This is needed for @import rules when importing from container formats (MHTML).</param>
    CssReader createCssReader(
        String baseUri,
        HtmlResourceLoader htmlResourceLoader)
    {
        return new CssReader(baseUri, htmlResourceLoader, mDocumentMode, mApplyFormattingAsMsWord);
    }

    /// <summary>
    /// Creates a new instance of <see cref="DocumentFormatter" /> class.
    /// </summary>
    /// <param name="document">Document.</param>
    /// <param name="defaultParagraphIstd">
    /// Index of the style that all paragraph styles imported from CSS will be based on.
    /// </param>
    /// <param name="defaultFontIstd">
    /// Index of the style that all font styles imported from CSS will be based on.
    /// </param>
    /// <param name="cssStyleTracker">Instance of <see cref="CssStyleTracker"/></param>
    /// <param name="useHtmlBlocks">
    /// Indicates whether borders and margins of block-level HTML elements are stored using <see cref="HtmlBlock"/>
    /// nodes, as opposed to storing them on child paragraphs.
    /// </param>
    /// <param name="cssFontFaceProvider">Instance of <see cref="CssFontFaceProvider"/> or <c>null</c>.</param>
    DocumentFormatter createDocumentFormatter(
        Document document,
        int defaultParagraphIstd,
        int defaultFontIstd,
        CssStyleTracker cssStyleTracker,
        boolean useHtmlBlocks,
        CssFontFaceProvider cssFontFaceProvider)
    {
        return new DocumentFormatter(
            document,
            defaultParagraphIstd,
            defaultFontIstd,
            mOmittedUserAgentFormatting,
            cssStyleTracker,
            useHtmlBlocks,
            cssFontFaceProvider);
    }

    /// <summary>
    /// Creates a new instance of the <see cref="CssResolver" /> class.
    /// </summary>
    /// <param name="documentRules">CSS rules of a HTML document.</param>
    CssResolver createCssResolver(Collection<CssStyleRule> documentRules)
    {
        return new CssResolver(
            documentRules,
            createDefaultElementStyleResolver(),
            createCascadingOrderResolver(),
            createCssInterdependencyResolver(),
            createComputedDeclarationResolver(),
            mDocumentMode,
            mDefaultQuirksBodyColor);
    }

    /// <summary>
    /// Creates a new instance of <see cref="HtmlElementCategorizer" /> class.
    /// </summary>
    HtmlElementCategorizer createHtmlElementCategorizer()
    {
        return new HtmlElementCategorizer(this);
    }

    /// <summary>
    /// Creates a new instance of <see cref="CssCascadingOrderResolver" /> class.
    /// </summary>
    CssCascadingOrderResolver createCascadingOrderResolver()
    {
        return new CssCascadingOrderResolver();
    }

    /// <summary>
    /// Creates a new instance of <see cref="CssComputedDeclarationResolver" /> class.
    /// </summary>
    CssComputedDeclarationResolver createComputedDeclarationResolver()
    {
        return new CssComputedDeclarationResolver(mApplyFormattingAsMsWord);
    }

    CssStyleTracker createStyleTracker(
        ArrayList<CssStyleRule> cssReaderRules,
        ArrayList<CssPageRule> cssReaderPageRules,
        String htmlElementInlineCssStyle,
        String bodyElementInlineCssStyle,
        CssResolver cssResolver,
        boolean applyFormattingAsMsWord,
        boolean useHtmlBlocks)
    {
        return new CssStyleTracker(
            cssReaderRules,
            cssReaderPageRules,
            htmlElementInlineCssStyle,
            bodyElementInlineCssStyle,
            cssResolver,
            new HtmlElementCategorizer(this),
            new HtmlAttributeResolver(),
            cssResolver.getDefaultElementStyleResolver(),
            createComputedDeclarationResolver(),
            createCssBoxModel(),
            applyFormattingAsMsWord,
            useHtmlBlocks);
    }

    /// <summary>
    /// Creates a new instance of <see cref="DefaultElementStyleResolver" /> class.
    /// </summary>
    DefaultElementStyleResolver createDefaultElementStyleResolver()
    {
        return new DefaultElementStyleResolver(mDocumentMode, mOmittedUserAgentFormatting, mApplyFormattingAsMsWord);
    }

    /// <summary>
    /// Gets an instance of <see cref="CssClassFactory"/> with <see cref="CssDocumentMode.Standards"/> mode.
    /// </summary>
    static CssClassFactory getStandardsFactory()
    {
        // Double-checked locking pattern.
        if (gStandardsFactory == null)
        {
            synchronized (G_STANDARDS_FACTORY_LOCK)
            {
                if (gStandardsFactory == null)
                {
                    gStandardsFactory = new CssClassFactory(CssDocumentMode.STANDARDS);
                }
            }
        }

        return gStandardsFactory;
    }

    /// <summary>
    /// Creates a new instance of <see cref="CssInterdependencyResolver" /> class.
    /// </summary>
    private CssInterdependencyResolver createCssInterdependencyResolver()
    {
        return new CssInterdependencyResolver(mOmittedUserAgentFormatting);
    }

    /// <summary>
    /// Creates a new instance of the <see cref="CssBoxModel" /> class.
    /// </summary>
    private CssBoxModel createCssBoxModel()
    {
        boolean applyDefaultParagraphFormatting = (mOmittedUserAgentFormatting & CssUserAgentFormatting.PARAGRAPH) == 0;
        return new CssBoxModel(
            mDocumentMode,
            applyDefaultParagraphFormatting,
            mApplyFormattingAsMsWord);
    }

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

    /// <summary>
    /// Groups of user agent (default) CSS styles that are omitted from style resolution.
    /// </summary>
    private /*final*/ /*CssUserAgentFormatting*/int mOmittedUserAgentFormatting;

    /// <summary>
    /// Defines the body color for quirks mode.
    /// </summary>
    private /*final*/ DrColor mDefaultQuirksBodyColor;

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;

    /// <summary>
    /// Instance of <see cref="CssClassFactory"/> with <see cref="CssDocumentMode.Standards"/> mode.
    /// </summary>
    private static volatile CssClassFactory gStandardsFactory;

    private static /*final*/ Object G_STANDARDS_FACTORY_LOCK = new Object();
}

