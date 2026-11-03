// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2017 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.W3CNamespaces;
import com.aspose.SortedStringListGeneric;
import com.aspose.DictionaryExtension;


/// <summary>
/// Class that resolves default CSS style of an HTML or SVG element according to its name, attributes,
/// and attributes' values.
/// </summary>
class DefaultElementStyleResolver
{
    DefaultElementStyleResolver(
        /*CssDocumentMode*/int documentMode,
        /*CssUserAgentFormatting*/int omittedFormatting,
        boolean applyFormattingAsMsWord)
    {
        mDocumentMode = documentMode;
        mOmittedFormatting = omittedFormatting;
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    /// <summary>
    /// Returns CSS declarations for the default CSS style of the HTML or SVG element.
    /// </summary>
    /// <param name="element">The element whose default style declarations are to be retuned.</param>
    /// <returns>
    /// A collection of CSS declarations applied to the specified element by default.
    /// If the element has default CSS style, an empty collection is returned.
    /// </returns>
    CssDeclarationCollection getDeclarations(IElementProvider element)
    {
        fillElementDefs();

        CssDeclarationCollectionBuilder result = new CssDeclarationCollectionBuilder();

        if (W3CNamespaces.SVG.equals(element.getElementNamespace()))
            mSvgElementDef.applyDefaultCss(element, result, mOmittedFormatting);
        else
            getHtmlElementDef(element).applyDefaultCss(element, result, mOmittedFormatting);

        return result.getDeclarations();
    }

    /// <summary>
    /// Gets the definition of the specified element.
    /// </summary>
    /// <returns>
    /// The definition of the specified element, or the unknown element definition if the element is not supported.
    /// The returned value is never <c>null</c>.
    /// </returns>
    private void fillElementDefs()
    {
        if (mHtmlElementDefs != null)
            return;

        mHtmlElementDefs = new SortedStringListGeneric<HtmlElementDef>(false);
        mHtmlElementDefs.add("a", new HtmlAElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("abbr", new HtmlAbbrElementDef());
        mHtmlElementDefs.add("address", new HtmlAddressElementDef());
        mHtmlElementDefs.add("article", new HtmlArticleElementDef());
        mHtmlElementDefs.add("aside", new HtmlAsideElementDef());
        mHtmlElementDefs.add("applet", new HtmlEmbedElementDef());
        mHtmlElementDefs.add("b", new HtmlBElementDef());
        mHtmlElementDefs.add("big", new HtmlBigElementDef());
        mHtmlElementDefs.add("bdo", new HtmlBdoElementDef());
        mHtmlElementDefs.add("blockquote", new HtmlBlockquoteElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("body", new HtmlBodyElementDef());
        mHtmlElementDefs.add("br", new HtmlBRElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("caption", new HtmlCaptionElementDef());
        mHtmlElementDefs.add("center", new HtmlCenterElementDef());
        mHtmlElementDefs.add("cite", new HtmlCiteElementDef());
        mHtmlElementDefs.add("code", new HtmlCodeElementDef());
        mHtmlElementDefs.add("col", new HtmlColElementDef());
        mHtmlElementDefs.add("colgroup", new HtmlColgroupElementDef());
        mHtmlElementDefs.add("del", new HtmlDelElementDef());
        mHtmlElementDefs.add("dfn", new HtmlDfnElementDef());
        mHtmlElementDefs.add("dir", new HtmlDirElementDef());
        mHtmlElementDefs.add("div", new HtmlDivElementDef());
        mHtmlElementDefs.add("dd", new HtmlDdElementDef());
        mHtmlElementDefs.add("dl", new HtmlDlElementDef());
        mHtmlElementDefs.add("dt", new HtmlDtElementDef());
        mHtmlElementDefs.add("em", new HtmlEmElementDef());
        mHtmlElementDefs.add("embed", new HtmlEmbedElementDef());
        mHtmlElementDefs.add("figcaption", new HtmlFigcaptionElementDef());
        mHtmlElementDefs.add("figure", new HtmlFigureElementDef());
        mHtmlElementDefs.add("font", new HtmlFontElementDef());
        mHtmlElementDefs.add("footer", new HtmlFooterElementDef());
        mHtmlElementDefs.add("form", new HtmlFormElementDef(mDocumentMode));
        mHtmlElementDefs.add("h1", new HtmlH1ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("h2", new HtmlH2ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("h3", new HtmlH3ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("h4", new HtmlH4ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("h5", new HtmlH5ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("h6", new HtmlH6ElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("header", new HtmlHeaderElementDef());
        mHtmlElementDefs.add("hgroup", new HtmlHgroupElementDef());
        mHtmlElementDefs.add("hr", new HtmlHRElementDef());
        mHtmlElementDefs.add("html", new HtmlHtmlElementDef());
        mHtmlElementDefs.add("i", new HtmlIElementDef());
        mHtmlElementDefs.add("iframe", new HtmlIframeElementDef());
        mHtmlElementDefs.add("img", new HtmlImgElementDef(mDocumentMode));
        mHtmlElementDefs.add("input", new HtmlInputElementDef(mDocumentMode));
        mHtmlElementDefs.add("ins", new HtmlInsElementDef());
        mHtmlElementDefs.add("kbd", new HtmlKbdElementDef());
        mHtmlElementDefs.add("legend", new HtmlLegendElementDef());
        mHtmlElementDefs.add("li", new HtmlLIElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("listing", new HtmlListingElementDef());
        mHtmlElementDefs.add("mark", new HtmlMarkElementDef());
        mHtmlElementDefs.add("marquee", new HtmlMarqueeElementDef());
        mHtmlElementDefs.add("menu", new HtmlMenuElementDef());
        mHtmlElementDefs.add("nav", new HtmlNavElementDef());
        mHtmlElementDefs.add("object", new HtmlImgElementDef(mDocumentMode));
        mHtmlElementDefs.add("ol", new HtmlOLElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("p", new HtmlPElementDef());
        mHtmlElementDefs.add("plaintext", new HtmlPlaintextElementDef());
        mHtmlElementDefs.add("pre", new HtmlPreElementDef());
        mHtmlElementDefs.add("s", new HtmlSElementDef());
        mHtmlElementDefs.add("samp", new HtmlSampElementDef());
        mHtmlElementDefs.add("section", new HtmlSectionElementDef());
        mHtmlElementDefs.add("select", new HtmlSelectElementDef());
        mHtmlElementDefs.add("small", new HtmlSmallElementDef());
        mHtmlElementDefs.add("strike", new HtmlStrikeElementDef());
        mHtmlElementDefs.add("strong", new HtmlStrongElementDef());
        mHtmlElementDefs.add("sub", new HtmlSubElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("summary", new HtmlSummaryElementDef());
        mHtmlElementDefs.add("sup", new HtmlSupElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("table", new HtmlTableElementDef(mDocumentMode));
        mHtmlElementDefs.add("tbody", new HtmlTableSectionElementDef());
        mHtmlElementDefs.add("td", new HtmlCellElementDef(mDocumentMode));
        mHtmlElementDefs.add("textarea", new HtmlTextareaElementDef());
        mHtmlElementDefs.add("tfoot", new HtmlTableSectionElementDef());
        mHtmlElementDefs.add("th", new HtmlCellElementDef(mDocumentMode));
        mHtmlElementDefs.add("thead", new HtmlTableSectionElementDef());
        mHtmlElementDefs.add("tr", new HtmlTRElementDef());
        mHtmlElementDefs.add("tt", new HtmlTtElementDef());
        mHtmlElementDefs.add("u", new HtmlUElementDef());
        mHtmlElementDefs.add("ul", new HtmlULElementDef(mApplyFormattingAsMsWord));
        mHtmlElementDefs.add("var", new HtmlVarElementDef());
        mHtmlElementDefs.add("video", new HtmlVideoElementDef());
        mHtmlElementDefs.add("xmp", new HtmlXmpElementDef());

        mUnknownElementDef = new UnknownElementDef();

        mSvgElementDef = new SvgElementDef();
    }

    private ElementDef getHtmlElementDef(IElementProvider element)
    {
        ElementDef def = DictionaryExtension.getValueOrNull(mHtmlElementDefs, element.getElementName());
        return (def != null)
            ? def
            : mUnknownElementDef;
    }

    /// <summary>
    /// Groups of user agent (default) CSS styles that are omitted from style resolution.
    /// </summary>
    private /*final*/ /*CssUserAgentFormatting*/int mOmittedFormatting;

    /// <summary>
    /// Unknown or unsupported by our CSS engine element definition.
    /// You should not register this class in <see cref="mHtmlElementDefs" />.
    /// </summary>
    private UnknownElementDef mUnknownElementDef;

    /// <summary>
    /// Supported HTML elements list.
    /// </summary>
    private SortedStringListGeneric<HtmlElementDef> mHtmlElementDefs;

    private /*final*/ /*CssDocumentMode*/int mDocumentMode;

    /// <summary>
    /// Any supported by our CSS engine SVG element definition.
    /// </summary>
    private SvgElementDef mSvgElementDef;

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;
}

