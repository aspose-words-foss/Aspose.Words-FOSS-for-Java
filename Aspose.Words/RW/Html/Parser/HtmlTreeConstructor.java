// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.W3CNamespaces;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;


/// <summary>
/// Constructs an HTML document tree according to the HTML 5 Specification.
/// </summary>
/// <remarks>
/// This class implements the algorithm described here: http://www.w3.org/TR/html5/syntax.html#tree-construction
/// </remarks>
class HtmlTreeConstructor
{
    /// <summary>
    /// Private constructor to simulate static class.
    /// </summary>
    /// <param name="tokenizer">The tokenizer that emits tokens of the document.</param>
    /// <param name="isScriptingEnabled">Indicates whether support for scripts is enabled when parsing HTML.</param>
    /// <param name="supportSelfClosingNonHtmlTags">
    /// Indicates whether to support certain self-closing non-HTML tags.
    /// </param>
    private HtmlTreeConstructor(
        HtmlTokenizer tokenizer,
        boolean isScriptingEnabled,
        boolean supportSelfClosingNonHtmlTags)
    {
        Debug.assertTrue(tokenizer != null);

        mTokenizer = tokenizer;
        mIsScriptingEnabled = isScriptingEnabled;
        mSupportSelfClosingNonHtmlTags = supportSelfClosingNonHtmlTags;
    }

    /// <summary>
    /// Parses an HTML document and returns the HTML tree.
    /// </summary>
    /// <param name="tokenizer">The tokenizer that emits tokens of the document.</param>
    /// <param name="isScriptingEnabled">Indicates whether support for scripts is enabled when parsing HTML.</param>
    /// <param name="supportSelfClosingNonHtmlTags">
    /// Indicates whether to support certain self-closing non-HTML tags.
    /// </param>
    /// <returns>The root element of the document's HTML tree.</returns>
    static HtmlDocument construct(
        HtmlTokenizer tokenizer,
        boolean isScriptingEnabled,
        boolean supportSelfClosingNonHtmlTags) throws Exception
    {
        HtmlTreeConstructor constructor = new HtmlTreeConstructor(
            tokenizer,
            isScriptingEnabled,
            supportSelfClosingNonHtmlTags);
        constructor.run();
        return new HtmlDocument(constructor.mRootElement, constructor.mDocumentMode);
    }

    /// <summary>
    /// Processes tokens of the document one by one until the end of file.
    /// </summary>
    private void run() throws Exception
    {
        mRootElement = null;
        mHeadElement = null;
        mFormElement = null;
        mOpenedElements.clear();
        mDocumentMode = HtmlDocumentMode.STANDARDS;
        mFramesetOk = true;
        mRemoveFirstLfFromText = false;

        mInsertionMode = InsertionMode.INITIAL;
        HtmlToken token;
        do
        {
            boolean htmlContent = mOpenedElements.isEmpty() || currentElementIsInHtmlNamespace();
            token = mTokenizer.nextToken(htmlContent);

            // WORDSNET-10059 Leading line break character should be removed only if it goes right after a tag
            // that requires such behavior. If there is any other token between the tag and the text with leading line break,
            // the line break should be kept.
            if (token.getType() != HtmlTokenType.TEXT)
            {
                mRemoveFirstLfFromText = false;
            }

            boolean reprocess = true;
            while (reprocess)
            {
                reprocess = false;
                switch (token.getType())
                {
                    case HtmlTokenType.TEXT:
                        if (isInHtmlContent(token))
                        {
                            processText((HtmlTextToken)token);
                        }
                        else
                        {
                            processTextInForeignContent((HtmlTextToken)token);
                        }
                        break;
                    case HtmlTokenType.COMMENT:
                        // Comments are dropped here.
                        break;
                    case HtmlTokenType.DOCTYPE:
                        if (isInHtmlContent(token))
                        {
                            processDoctype((HtmlDoctypeToken)token);
                        }
                        break;
                    case HtmlTokenType.TAG:
                    {
                        HtmlTagToken tokenAsTag = (HtmlTagToken)token;
                        if (tokenAsTag.isStart())
                        {
                            if (isInHtmlContent(token))
                            {
                                processStartTag(tokenAsTag);
                            }
                            else
                            {
                                reprocess = processStartTagInForeignContent(tokenAsTag);
                            }
                        }
                        else
                        {
                            if (isInHtmlContent(token))
                            {
                                processEndTag(tokenAsTag);
                            }
                            else
                            {
                                processEndTagInForeignContent(tokenAsTag);
                            }
                        }
                    }
                    break;
                    default:
                        processEndOfFile();
                        break;
                }
            }
        } while (token.getType() != HtmlTokenType.END_OF_FILE);
    }

    private boolean isInHtmlContent(HtmlToken token)
    {
        // The alogrithm is described here:
        // http://www.w3.org/TR/html5/syntax.html#tree-construction

        if (mOpenedElements.isEmpty())
        {
            return true;
        }

        if (currentElementIsInHtmlNamespace())
        {
            return true;
        }

        if (currentElementIsMathMLTextIntegrationPoint())
        {
            HtmlTagToken tokenAsTag = ms.as(token, HtmlTagToken.class);
            if ((tokenAsTag != null) &&
                tokenAsTag.isStart() &&
                (!"mglyph".equals(tokenAsTag.getName())) &&
                (!"malignmark".equals(tokenAsTag.getName())))
            {
                return true;
            }

            if (token.getType() == HtmlTokenType.TEXT)
            {
                return true;
            }
        }

        HtmlElementNode currentNode = mOpenedElements.getLast();
        if ((W3CNamespaces.MATH_ML.equals(currentNode.getNamespace())) && ("annotation-xml".equals(currentNode.getName())))
        {
            HtmlTagToken tokenAsTag = ms.as(token, HtmlTagToken.class);
            if ((tokenAsTag != null) && tokenAsTag.isStart() && ("svg".equals(tokenAsTag.getName())))
            {
                return true;
            }
        }

        if (currentElementIsHtmlIntegrationPoint())
        {
            HtmlTagToken tokenAsTag = ms.as(token, HtmlTagToken.class);
            if ((tokenAsTag != null) && tokenAsTag.isStart())
            {
                return true;
            }

            if (token.getType() == HtmlTokenType.TEXT)
            {
                return true;
            }
        }

        if (token.getType() == HtmlTokenType.END_OF_FILE)
        {
            return true;
        }

        return false;
    }

    /// <summary>
    /// Processes <see cref="HtmlDoctypeToken"/> tokens.
    /// </summary>
    private void processDoctype(HtmlDoctypeToken doctype)
    {
        if (mInsertionMode != InsertionMode.INITIAL)
        {
            return;
        }
        mInsertionMode = InsertionMode.BEFORE_HTML;

        if (doctype.getForceQuirks())
        {
            mDocumentMode = HtmlDocumentMode.QUIRKS;
            return;
        }

        if (!"html".equals(doctype.getName()))
        {
            mDocumentMode = HtmlDocumentMode.QUIRKS;
            return;
        }

        // Although this array contains only constant strings, we decided not to move it to a static field
        // by the following reasons:
        //   a) code is easier to read when the array is declared near the place where it is used
        //   b) performance impact of array recreation is negligible, because this method is called very seldom
        //      (it parses DOCTYPE declarations and a well-formed HTML document contains no more than one such declaration).
        String[] quirkyPublicPrefixes = new String[]
        {
            "+//silmaril//dtd html pro v0r11 19970101//",
            "-//advasoft ltd//dtd html 3.0 aswedit + extensions//",
            "-//as//dtd html 3.0 aswedit + extensions//",
            "-//ietf//dtd html 2.0 level 1//",
            "-//ietf//dtd html 2.0 level 2//",
            "-//ietf//dtd html 2.0 strict level 1//",
            "-//ietf//dtd html 2.0 strict level 2//",
            "-//ietf//dtd html 2.0 strict//",
            "-//ietf//dtd html 2.0//",
            "-//ietf//dtd html 2.1e//",
            "-//ietf//dtd html 3.0//",
            "-//ietf//dtd html 3.2 final//",
            "-//ietf//dtd html 3.2//",
            "-//ietf//dtd html 3//",
            "-//ietf//dtd html level 0//",
            "-//ietf//dtd html level 1//",
            "-//ietf//dtd html level 2//",
            "-//ietf//dtd html level 3//",
            "-//ietf//dtd html strict level 0//",
            "-//ietf//dtd html strict level 1//",
            "-//ietf//dtd html strict level 2//",
            "-//ietf//dtd html strict level 3//",
            "-//ietf//dtd html strict//",
            "-//ietf//dtd html//",
            "-//metrius//dtd metrius presentational//",
            "-//microsoft//dtd internet explorer 2.0 html strict//",
            "-//microsoft//dtd internet explorer 2.0 html//",
            "-//microsoft//dtd internet explorer 2.0 tables//",
            "-//microsoft//dtd internet explorer 3.0 html strict//",
            "-//microsoft//dtd internet explorer 3.0 html//",
            "-//microsoft//dtd internet explorer 3.0 tables//",
            "-//netscape comm. corp.//dtd html//",
            "-//netscape comm. corp.//dtd strict html//",
            "-//o'reilly and associates//dtd html 2.0//",
            "-//o'reilly and associates//dtd html extended 1.0//",
            "-//o'reilly and associates//dtd html extended relaxed 1.0//",
            "-//softquad software//dtd hotmetal pro 6.0::19990601::extensions to html 4.0//",
            "-//softquad//dtd hotmetal pro 4.0::19971010::extensions to html 4.0//",
            "-//spyglass//dtd html 2.0 extended//",
            "-//sq//dtd html 2.0 hotmetal + extensions//",
            "-//sun microsystems corp.//dtd hotjava html//",
            "-//sun microsystems corp.//dtd hotjava strict html//",
            "-//w3c//dtd html 3 1995-03-24//",
            "-//w3c//dtd html 3.2 draft//",
            "-//w3c//dtd html 3.2 final//",
            "-//w3c//dtd html 3.2//",
            "-//w3c//dtd html 3.2s draft//",
            "-//w3c//dtd html 4.0 frameset//",
            "-//w3c//dtd html 4.0 transitional//",
            "-//w3c//dtd html experimental 19960712//",
            "-//w3c//dtd html experimental 970421//",
            "-//w3c//dtd w3 html//",
            "-//w3o//dtd w3 html 3.0//",
            "-//webtechs//dtd mozilla html 2.0//",
            "-//webtechs//dtd mozilla html//"
        };

        if (doctype.getPublicId() != null)
        {
            for (int i = 0; i < quirkyPublicPrefixes.length; i++)
            {
                if (msString.startsWith(doctype.getPublicId(), quirkyPublicPrefixes[i], StringComparison.ORDINAL_IGNORE_CASE))
                {
                    mDocumentMode = HtmlDocumentMode.QUIRKS;
                    return;
                }
            }
        }

        // Although this array contains only constant strings, we decided not to move it to a static field
        // by the following reasons:
        //   a) code is easier to read when the array is declared near the place where it is used
        //   b) performance impact of array recreation is negligible, because this method is called very seldom
        //      (it parses DOCTYPE declarations and a well-formed HTML document contains no more than one such declaration).
        String[] quirkyPublicIds = new String[]
        {
            "-//w3o//dtd w3 html strict 3.0//en//",
            "-/w3c/dtd html 4.0 transitional/en",
            "html"
        };

        if (doctype.getPublicId() != null)
        {
            for (int i = 0; i < quirkyPublicIds.length; i++)
            {
                if (msString.equals(doctype.getPublicId(), quirkyPublicIds[i], StringComparison.ORDINAL_IGNORE_CASE))
                {
                    mDocumentMode = HtmlDocumentMode.QUIRKS;
                    return;
                }
            }
        }

        if ("http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd".equals(doctype.getSystemId()))
        {
            mDocumentMode = HtmlDocumentMode.QUIRKS;
            return;
        }

        if ((doctype.getSystemId() == null) && (doctype.getPublicId() != null))
        {
            if (msString.startsWith(doctype.getPublicId(), "-//w3c//dtd html 4.01 frameset//", StringComparison.ORDINAL_IGNORE_CASE) ||
                msString.startsWith(doctype.getPublicId(), "-//w3c//dtd html 4.01 transitional//", StringComparison.ORDINAL_IGNORE_CASE))
            {
                mDocumentMode = HtmlDocumentMode.QUIRKS;
                return;
            }
        }

        if (doctype.getPublicId() != null)
        {
            if (msString.startsWith(doctype.getPublicId(), "-//w3c//dtd xhtml 1.0 frameset//", StringComparison.ORDINAL_IGNORE_CASE) ||
                msString.startsWith(doctype.getPublicId(), "-//w3c//dtd xhtml 1.0 transitional//", StringComparison.ORDINAL_IGNORE_CASE))
            {
                mDocumentMode = HtmlDocumentMode.LIMITED_QUIRKS;
                return;
            }
        }

        if ((doctype.getSystemId() != null) && (doctype.getPublicId() != null))
        {
            if (msString.startsWith(doctype.getPublicId(), "-//w3c//dtd html 4.01 frameset//", StringComparison.ORDINAL_IGNORE_CASE) ||
                msString.startsWith(doctype.getPublicId(), "-//w3c//dtd html 4.01 transitional//", StringComparison.ORDINAL_IGNORE_CASE))
            {
                mDocumentMode = HtmlDocumentMode.LIMITED_QUIRKS;
                return;
            }
        }

        mDocumentMode = HtmlDocumentMode.STANDARDS;
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens.
    /// </summary>
    private void processStartTag(HtmlTagToken tag)
    {
        boolean reprocessToken = true;
        while (reprocessToken)
        {
            reprocessToken = false;

            switch (mInsertionMode)
            {
                case InsertionMode.INITIAL:
                    // If the source of the HTML document is the 'srcdoc' attribute of an IFRAME element, the document should be
                    // switched to the Standards mode. But the IFRAME 'srcdoc' attribute is not supported now.
                    mDocumentMode = HtmlDocumentMode.QUIRKS;
                    mInsertionMode = InsertionMode.BEFORE_HTML;
                    reprocessToken = true;
                    break;
                case InsertionMode.BEFORE_HTML:
                    if ("html".equals(tag.getName()))
                    {
                        mRootElement = createElement("html", W3CNamespaces.XHTML, tag.getAttributes(), true);

                        // Get namespace prefix of VML elements. If VML namespace is declared, we will import VML content.
                        for (HtmlAttribute attribute : mRootElement.getAttributes())
                        {
                            if (NrxNamespaces.VML.equals(attribute.getValue()))
                            {
                                final String XMLNS_PREFIX = "xmlns:";
                                if (msString.startsWith(attribute.getName(), XMLNS_PREFIX, StringComparison.ORDINAL))
                                {
                                    String vmlNamespacePrefix = attribute.getName().substring(XMLNS_PREFIX.length());
                                    if (StringUtil.hasChars(vmlNamespacePrefix) && (vmlNamespacePrefix.indexOf(':') < 0))
                                    {
                                        mVmlElementPrefix = vmlNamespacePrefix + ":";
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        mRootElement = createElement("html", W3CNamespaces.XHTML, new HtmlAttributeCollection(), true);
                        reprocessToken = true;
                    }
                    mInsertionMode = InsertionMode.BEFORE_HEAD;
                    break;
                case InsertionMode.BEFORE_HEAD:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            addAttributesToHtmlElement(tag.getAttributes());
                            break;
                        case /*"head"*/1:
                            mHeadElement = insertHtmlElement("head", tag.getAttributes(), true, false);
                            mInsertionMode = InsertionMode.IN_HEAD;
                            break;
                        default:
                            mHeadElement = insertHtmlElement("head", new HtmlAttributeCollection(), true, false);
                            mInsertionMode = InsertionMode.IN_HEAD;
                            reprocessToken = true;
                            break;
                    }
                    break;
                case InsertionMode.IN_HEAD:
                    reprocessToken = processStartTagInHead(tag, false);
                    break;
                case InsertionMode.IN_HEAD_NOSCRIPT:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            addAttributesToHtmlElement(tag.getAttributes());
                            break;
                        case /*"basefont"*/2:
                        case /*"bgsound"*/3:
                        case /*"link"*/4:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                            break;
                        case /*"meta"*/5:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                            // The charset information from META elements is ignored, because we use our own methods
                            // to determine the encoding of the HTML document.
                            break;
                        case /*"noframes"*/6:
                        case /*"style"*/7:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            parseRawText();
                            break;
                        case /*"head"*/1:
                        case /*"noscript"*/8:
                            // Ignore the token.
                            break;
                        default:
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.IN_HEAD;
                            reprocessToken = true;
                            break;
                    }
                    break;
                case InsertionMode.AFTER_HEAD:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        case /*"body"*/9:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            mFramesetOk = false;
                            mInsertionMode = InsertionMode.IN_BODY;
                            break;
                        case /*"frameset"*/10:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            mInsertionMode = InsertionMode.IN_FRAMESET;
                            break;
                        case /*"base"*/11:
                        case /*"basefont"*/2:
                        case /*"bgsound"*/3:
                        case /*"link"*/4:
                        case /*"meta"*/5:
                        case /*"noframes"*/6:
                        case /*"script"*/12:
                        case /*"style"*/7:
                        case /*"title"*/13:
                            mOpenedElements.add(mHeadElement);
                            reprocessToken = processStartTagInHead(tag, false);
                            mOpenedElements.remove(mHeadElement);
                            break;
                        case /*"head"*/1:
                            // Ignore the token.
                            break;
                        default:
                            insertHtmlElement("body", new HtmlAttributeCollection(), true, false);
                            mInsertionMode = InsertionMode.IN_BODY;
                            reprocessToken = true;
                            break;
                    }
                    break;
                case InsertionMode.IN_BODY:
                    reprocessToken = processStartTagInBody(tag, false);
                    break;
                case InsertionMode.TEXT:
                    // Ignore the token.
                    break;
                case InsertionMode.IN_TABLE:
                    reprocessToken = processStartTagInTable(tag);
                    break;
                case InsertionMode.IN_TABLE_TEXT:
                    insertPendingCharacters();
                    reprocessToken = true;
                    break;
                case InsertionMode.IN_CAPTION:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"tbody"*/17:
                        case /*"td"*/18:
                        case /*"tfoot"*/19:
                        case /*"th"*/20:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                            if (hasElementInTableScope("caption", W3CNamespaces.XHTML))
                            {
                                generateImpliedEndTags();
                                popElementsUntil("caption");
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                        default:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                    }
                    break;
                case InsertionMode.IN_COLUMN_GROUP:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        case /*"col"*/15:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                            break;
                        default:
                            if (!currentElementIsRootHtml())
                            {
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                    }
                    break;
                case InsertionMode.IN_TABLE_BODY:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"tr"*/22:
                            clearStackBackToTableBodyContext();
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            mInsertionMode = InsertionMode.IN_ROW;
                            break;
                        case /*"th"*/20:
                        case /*"td"*/18:
                            clearStackBackToTableBodyContext();
                            insertHtmlElement("tr", new HtmlAttributeCollection(), true, false);
                            mInsertionMode = InsertionMode.IN_ROW;
                            reprocessToken = true;
                            break;
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                            if (hasTableBodyElementInTableScope())
                            {
                                clearStackBackToTableBodyContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                        default:
                            reprocessToken = processStartTagInTable(tag);
                            break;
                    }
                    break;
                case InsertionMode.IN_ROW:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"th"*/20:
                        case /*"td"*/18:
                            clearStackBackToRowContext();
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            mInsertionMode = InsertionMode.IN_CELL;
                            mActiveFormattingElements.openNewScope();
                            break;
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                            if (hasElementInTableScope("tr", W3CNamespaces.XHTML))
                            {
                                clearStackBackToRowContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE_BODY;
                                reprocessToken = true;
                            }
                            break;
                        default:
                            reprocessToken = processStartTagInTable(tag);
                            break;
                    }
                    break;
                case InsertionMode.IN_CELL:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"tbody"*/17:
                        case /*"td"*/18:
                        case /*"tfoot"*/19:
                        case /*"th"*/20:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                            if (hasTableCellElementInTableScope())
                            {
                                generateImpliedEndTags();
                                popElementsUntilCell();
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_ROW;
                                reprocessToken = true;
                            }
                            break;
                        default:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                    }
                    break;
                case InsertionMode.IN_SELECT:
                    reprocessToken = processStartTagInSelect(tag);
                    break;
                case InsertionMode.IN_SELECT_IN_TABLE:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"caption"*/14:
                        case /*"table"*/23:
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                        case /*"td"*/18:
                        case /*"th"*/20:
                            if (hasElementInSelectScope("select", W3CNamespaces.XHTML))
                            {
                                popElementsUntil("select");
                                resetInsertionModeAppropriately();
                            }
                            reprocessToken = true;
                            break;
                        default:
                            reprocessToken = processStartTagInSelect(tag);
                            break;
                    }
                    break;
                case InsertionMode.AFTER_BODY:
                case InsertionMode.AFTER_AFTER_BODY:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        default:
                            mInsertionMode = InsertionMode.IN_BODY;
                            reprocessToken = true;
                            break;
                    }
                    break;
                case InsertionMode.IN_FRAMESET:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        case /*"frameset"*/10:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                            break;
                        case /*"frame"*/24:
                            insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                            break;
                        case /*"noframes"*/6:
                            reprocessToken = processStartTagInHead(tag, false);
                            break;
                        default:
                            // Ignore the token.
                            break;
                    }
                    break;
                case InsertionMode.AFTER_FRAMESET:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        case /*"noframes"*/6:
                            reprocessToken = processStartTagInHead(tag, false);
                            break;
                        default:
                            // Ignore the token.
                            break;
                    }
                    break;
                case InsertionMode.AFTER_AFTER_FRAMESET:
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            reprocessToken = processStartTagInBody(tag, false);
                            break;
                        case /*"noframes"*/6:
                            reprocessToken = processStartTagInHead(tag, false);
                            break;
                        default:
                            // Ignore the token.
                            break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown insertion mode.");
            }
        }
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens in the 'in head' insertion mode.
    /// </summary>
    private boolean processStartTagInHead(HtmlTagToken tag, boolean fosterParent)
    {
        boolean reprocessToken = false;
        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"html"*/0:
                addAttributesToHtmlElement(tag.getAttributes());
                break;
            case /*"base"*/11:
            case /*"basefont"*/2:
            case /*"bgsound"*/3:
            case /*"command"*/25:
            case /*"link"*/4:
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                break;
            case /*"meta"*/5:
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                // The charset information from META elements is ignored, because we use our own methods to determine
                // the encoding of the HTML document.
                break;
            case /*"title"*/13:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseRcdata();
                break;
            case /*"noscript"*/8:
                // WORDSNET-21203 Skip any tags inside &lt;noscript&gt; if IgnoreNoScriptTag option was set.
                if (mIsScriptingEnabled)
                {
                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                    parseRawText();
                }
                else
                {
                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                    mInsertionMode = InsertionMode.IN_HEAD_NOSCRIPT;
                }
                break;
            case /*"noframes"*/6:
            case /*"style"*/7:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseRawText();
                break;
            case /*"script"*/12:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseScriptData();
                break;
            case /*"head"*/1:
                // Ignore the token.
                break;
            default:
                mOpenedElements.removeLast();
                mInsertionMode = InsertionMode.AFTER_HEAD;
                reprocessToken = true;
                break;
        }
        return reprocessToken;
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens in the 'in select' insertion mode.
    /// </summary>
    private boolean processStartTagInSelect(HtmlTagToken tag)
    {
        boolean reprocessToken = false;
        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"html"*/0:
                reprocessToken = processStartTagInBody(tag, false);
                break;
            case /*"option"*/26:
                if (currentElementIsOption())
                {
                    mOpenedElements.removeLast();
                }
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                break;
            case /*"optgroup"*/27:
                if (currentElementIsOption())
                {
                    mOpenedElements.removeLast();
                }
                if (currentElementIsOptgroup())
                {
                    mOpenedElements.removeLast();
                }
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                break;
            case /*"select"*/28:
                if (hasElementInSelectScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    popElementsUntil("select");
                    resetInsertionModeAppropriately();
                }
                break;
            case /*"input"*/29:
            case /*"keygen"*/30:
            case /*"textarea"*/31:
                if (hasElementInSelectScope("select", W3CNamespaces.XHTML))
                {
                    popElementsUntil("select");
                    resetInsertionModeAppropriately();
                    reprocessToken = true;
                }
                break;
            case /*"script"*/12:
                reprocessToken = processStartTagInHead(tag, false);
                break;
            default:
                // Ignore the token.
                break;
        }
        return reprocessToken;
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens in the 'in table' insertion mode.
    /// </summary>
    private boolean processStartTagInTable(HtmlTagToken tag)
    {
        boolean reprocessToken = false;
        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"caption"*/14:
                clearStackBackToTableContext();
                mActiveFormattingElements.openNewScope();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                mInsertionMode = InsertionMode.IN_CAPTION;
                break;
            case /*"colgroup"*/16:
                clearStackBackToTableContext();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                mInsertionMode = InsertionMode.IN_COLUMN_GROUP;
                break;
            case /*"col"*/15:
                clearStackBackToTableContext();
                insertHtmlElement("colgroup", new HtmlAttributeCollection(), true, false);
                mInsertionMode = InsertionMode.IN_COLUMN_GROUP;
                reprocessToken = true;
                break;
            case /*"tbody"*/17:
            case /*"tfoot"*/19:
            case /*"thead"*/21:
                clearStackBackToTableContext();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                mInsertionMode = InsertionMode.IN_TABLE_BODY;
                break;
            case /*"td"*/18:
            case /*"th"*/20:
            case /*"tr"*/22:
                clearStackBackToTableContext();
                insertHtmlElement("tbody", new HtmlAttributeCollection(), true, false);
                mInsertionMode = InsertionMode.IN_TABLE_BODY;
                reprocessToken = true;
                break;
            case /*"table"*/23:
                if (hasElementInTableScope("table", W3CNamespaces.XHTML))
                {
                    popElementsUntil("table");
                    resetInsertionModeAppropriately();
                    reprocessToken = true;
                }
                break;
            case /*"style"*/7:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                parseRawText();
                break;
            case /*"script"*/12:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, false);
                parseScriptData();
                break;
            case /*"input"*/29:
            {
                String type = tag.getAttributes().getAttributeValue("type", "");
                if (!msString.equals(type, "hidden", StringComparison.ORDINAL_IGNORE_CASE))
                {
                    reprocessToken = processStartTagInBody(tag, true);
                }
                else
                {
                    insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                }
                break;
            }
            case /*"form"*/32:
                if (mFormElement == null)
                {
                    mFormElement = insertHtmlElement(tag.getName(), tag.getAttributes(), false, false);
                }
                break;
            default:
                reprocessToken = processStartTagInBody(tag, true);
                break;
        }
        return reprocessToken;
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens in the 'in body' insertion mode.
    /// </summary>
    /// <param name="tag">The token being processed.</param>
    /// <param name="fosterParent">
    /// Insert new elements into the foster parent element instead of the current element,
    /// when the current element is a part of a table.
    /// </param>
    private boolean processStartTagInBody(HtmlTagToken tag, boolean fosterParent)
    {
        boolean reprocessToken = false;

        // Check if the current tag starts with the VML prefix.
        String vmlElementName = getLocalVmlName(tag.getName());
        if (!msString.equals(vmlElementName, tag.getName()))
        {
            reconstructActiveFormattingElements(fosterParent);
            insertElement(tag.getName(), NrxNamespaces.VML, tag.getAttributes(), !tag.isSelfClosing(), fosterParent);
            return false;
        }

        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"html"*/0:
                addAttributesToHtmlElement(tag.getAttributes());
                break;
            case /*"base"*/11:
            case /*"basefont"*/2:
            case /*"bgsound"*/3:
            case /*"command"*/25:
            case /*"link"*/4:
            case /*"meta"*/5:
            case /*"noframes"*/6:
            case /*"script"*/12:
            case /*"style"*/7:
            case /*"title"*/13:
                reprocessToken = processStartTagInHead(tag, fosterParent);
                break;
            case /*"body"*/9:
                if (checkNameOfOpenedElement("body", W3CNamespaces.XHTML, 1))
                {
                    mFramesetOk = false;
                    addAttributesToOpenedElement(tag.getAttributes(), 1);
                }
                break;
            case /*"frameset"*/10:
                if (checkNameOfOpenedElement("body", W3CNamespaces.XHTML, 1) && mFramesetOk)
                {
                    HtmlNode bodyElement = getOpenedElement(1);
                    Debug.assertTrue(bodyElement != null);
                    if (bodyElement.getParent() != null)
                    {
                        bodyElement.getParent().getChildren().remove(bodyElement);
                    }

                    // Pop all opened elements but the root "html" element.
                    Debug.assertTrue(!mOpenedElements.isEmpty());
                    mOpenedElements.removeRange(1);

                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                    mInsertionMode = InsertionMode.IN_FRAMESET;
                }
                break;
            case /*"address"*/33:
            case /*"article"*/34:
            case /*"aside"*/35:
            case /*"blockquote"*/36:
            case /*"center"*/37:
            case /*"details"*/38:
            case /*"dialog"*/39:
            case /*"dir"*/40:
            case /*"div"*/41:
            case /*"dl"*/42:
            case /*"fieldset"*/43:
            case /*"figcaption"*/44:
            case /*"figure"*/45:
            case /*"footer"*/46:
            case /*"header"*/47:
            case /*"hgroup"*/48:
            case /*"menu"*/49:
            case /*"nav"*/50:
            case /*"ol"*/51:
            case /*"p"*/52:
            case /*"section"*/53:
            case /*"summary"*/54:
            case /*"ul"*/55:
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"h1"*/56:
            case /*"h2"*/57:
            case /*"h3"*/58:
            case /*"h4"*/59:
            case /*"h5"*/60:
            case /*"h6"*/61:
                closeParagraphIfOpened();
                if (currentElementNameIsHeader())
                {
                    mOpenedElements.removeLast();
                }
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"pre"*/62:
            case /*"listing"*/63:
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mRemoveFirstLfFromText = true;
                mFramesetOk = false;
                break;
            case /*"form"*/32:
                if (mFormElement == null)
                {
                    closeParagraphIfOpened();
                    mFormElement = insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                }
                break;
            case /*"li"*/64:
                mFramesetOk = false;
                closeOpenedItemElements("li", W3CNamespaces.XHTML);
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"dd"*/65:
            case /*"dt"*/66:
                mFramesetOk = false;
                closeOpenedDdAndDtElements();
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"plaintext"*/67:
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mTokenizer.switchToPlaintextState();
                break;
            case /*"button"*/68:
                if (hasElementInScope("button", W3CNamespaces.XHTML))
                {
                    generateImpliedEndTags();
                    popElementsUntil("button");
                    reprocessToken = true;
                }
                else
                {
                    reconstructActiveFormattingElements(fosterParent);
                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                    mFramesetOk = false;
                }
                break;
            case /*"a"*/69:
            {
                HtmlElementNode activeElement = mActiveFormattingElements.findInCurrentScope("a");
                if (activeElement != null)
                {
                    closeFormattingElement("a");
                    mActiveFormattingElements.remove(activeElement);
                    mOpenedElements.remove(activeElement);
                }
                reconstructActiveFormattingElements(fosterParent);
                HtmlElementNode htmlElement = insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mActiveFormattingElements.add(htmlElement);
                break;
            }
            case /*"b"*/70:
            case /*"big"*/71:
            case /*"code"*/72:
            case /*"em"*/73:
            case /*"font"*/74:
            case /*"i"*/75:
            case /*"s"*/76:
            case /*"small"*/77:
            case /*"strike"*/78:
            case /*"strong"*/79:
            case /*"tt"*/80:
            case /*"u"*/81:
            {
                reconstructActiveFormattingElements(fosterParent);
                HtmlElementNode htmlElement = insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mActiveFormattingElements.add(htmlElement);
                break;
            }
            case /*"nobr"*/82:
            {
                reconstructActiveFormattingElements(fosterParent);
                if (hasElementInScope("nobr", W3CNamespaces.XHTML))
                {
                    closeFormattingElement("nobr");
                    reconstructActiveFormattingElements(fosterParent);
                }
                HtmlElementNode htmlElement = insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mActiveFormattingElements.add(htmlElement);
                break;
            }
            case /*"applet"*/83:
            case /*"marquee"*/84:
            case /*"object"*/85:
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mActiveFormattingElements.openNewScope();
                mFramesetOk = false;
                break;
            case /*"table"*/23:
                if (mDocumentMode != HtmlDocumentMode.QUIRKS)
                {
                    closeParagraphIfOpened();
                }
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mFramesetOk = false;
                mInsertionMode = InsertionMode.IN_TABLE;
                break;
            case /*"area"*/86:
            case /*"br"*/87:
            case /*"embed"*/88:
            case /*"img"*/89:
            case /*"keygen"*/30:
            case /*"wbr"*/90:
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                mFramesetOk = false;
                break;
            case /*"image"*/91:
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement("img", tag.getAttributes(), false, fosterParent);
                mFramesetOk = false;
                break;
            case /*"input"*/29:
            {
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                HtmlAttribute typeAttribute = tag.getAttributes().get("type");
                if ((typeAttribute == null) ||
                    !msString.equals(typeAttribute.getValue(), "hidden", StringComparison.ORDINAL_IGNORE_CASE))
                {
                    mFramesetOk = false;
                }
                break;
            }
            case /*"param"*/92:
            case /*"source"*/93:
            case /*"track"*/94:
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                break;
            case /*"hr"*/95:
                closeParagraphIfOpened();
                insertHtmlElement(tag.getName(), tag.getAttributes(), false, fosterParent);
                mFramesetOk = false;
                break;
            case /*"isindex"*/96:
                if (mFormElement == null)
                {
                    closeParagraphIfOpened();

                    HtmlAttributeCollection formAttributes = new HtmlAttributeCollection();
                    HtmlAttribute actionAttribute = tag.getAttributes().get("action");
                    if (actionAttribute != null)
                    {
                        formAttributes.add(actionAttribute);
                    }
                    mFormElement = insertHtmlElement("form", formAttributes, true, fosterParent);

                    insertHtmlElement("hr", new HtmlAttributeCollection(), false, false);
                    reconstructActiveFormattingElements(false);
                    insertHtmlElement("label", new HtmlAttributeCollection(), true, false);

                    HtmlAttribute promptAttribute = tag.getAttributes().get("prompt");
                    if (promptAttribute != null)
                    {
                        if (StringUtil.hasChars(promptAttribute.getValue()))
                        {
                            insertIntoCurrentNode(promptAttribute.getValue());
                        }
                    }
                    else
                    {
                        insertIntoCurrentNode("This is a searchable index. Enter search keywords: ");
                    }

                    HtmlAttributeCollection inputAttributes = new HtmlAttributeCollection();
                    for (HtmlAttribute tagAttribute : tag.getAttributes())
                    {
                        if ((!"name".equals(tagAttribute.getName())) &&
                            (!"action".equals(tagAttribute.getName())) &&
                            (!"prompt".equals(tagAttribute.getName())))
                        {
                            inputAttributes.add(tagAttribute);
                        }
                    }
                    inputAttributes.add(new HtmlAttribute("name", "isindex"));
                    insertHtmlElement("input", inputAttributes, false, false);

                    closeOtherElement("label");
                    insertHtmlElement("hr", new HtmlAttributeCollection(), false, false);

                    generateImpliedEndTags();
                    mOpenedElements.remove(mFormElement);
                    mFormElement = null;

                    mFramesetOk = false;
                }
                break;
            case /*"textarea"*/31:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mRemoveFirstLfFromText = true;
                mFramesetOk = false;
                parseRcdata();
                break;
            case /*"xmp"*/97:
                closeParagraphIfOpened();
                reconstructActiveFormattingElements(fosterParent);
                mFramesetOk = false;
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseRawText();
                break;
            case /*"iframe"*/98:
                mFramesetOk = false;
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseRawText();
                break;
            case /*"noembed"*/99:
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                parseRawText();
                break;
            case /*"noscript"*/8:
                if (mIsScriptingEnabled)
                {
                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                    parseRawText();
                }
                else
                {
                    reconstructActiveFormattingElements(fosterParent);
                    insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                }
                break;
            case /*"select"*/28:
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                mFramesetOk = false;
                switch (mInsertionMode)
                {
                    case InsertionMode.IN_CELL:
                    case InsertionMode.IN_ROW:
                    case InsertionMode.IN_TABLE_BODY:
                    case InsertionMode.IN_CAPTION:
                    case InsertionMode.IN_TABLE:
                        mInsertionMode = InsertionMode.IN_SELECT_IN_TABLE;
                        break;
                    default:
                        mInsertionMode = InsertionMode.IN_SELECT;
                        break;
                }
                break;
            case /*"optgroup"*/27:
            case /*"option"*/26:
                if (currentElementIsOption())
                {
                    closeOpenedElements("option");
                }
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"rp"*/100:
            case /*"rt"*/101:
                if (hasElementInScope("ruby", W3CNamespaces.XHTML))
                {
                    generateImpliedEndTags();
                }
                insertHtmlElement(tag.getName(), tag.getAttributes(), true, fosterParent);
                break;
            case /*"math"*/102:
                reconstructActiveFormattingElements(fosterParent);
                adjustMathMLAttributes(tag.getAttributes());

                // Foreign attributes are not adjusted here, because namespaces are not supported for attributes.

                insertElement(tag.getName(), W3CNamespaces.MATH_ML, tag.getAttributes(), !tag.isSelfClosing(), fosterParent);
                break;
            case /*"svg"*/103:
                reconstructActiveFormattingElements(fosterParent);
                adjustSvgAttributes(tag.getAttributes());

                // Foreign attributes are not adjusted here, because namespaces are not supported for attributes.

                insertElement(tag.getName(), W3CNamespaces.SVG, tag.getAttributes(), !tag.isSelfClosing(), fosterParent);
                break;
            case /*"caption"*/14:
            case /*"col"*/15:
            case /*"colgroup"*/16:
            case /*"frame"*/24:
            case /*"head"*/1:
            case /*"tbody"*/17:
            case /*"td"*/18:
            case /*"tfoot"*/19:
            case /*"th"*/20:
            case /*"thead"*/21:
            case /*"tr"*/22:
                // The token is ignored.
                break;
            default:
            {
                reconstructActiveFormattingElements(fosterParent);

                // RK According to the HTML5 standard, unknown elements are Normal Elements and do not support self-closing
                // syntax. So if the element is unknown and even if it is self-closing, we have to always open it
                // as a parent element. Indeed, this is what Chrome and IE do.

                // WORDSNET-23332 Self-closing "mbp:pagebreak" was added to exclusions, because large number of such
                // open elements led to performance issues when loading MOBI documents.
                // We've seen MOBI documents where text is stored as a single large HTML document with lots
                // of "mbp:pagebreak" that split it into pages. MOBI readers are able to show such documents without issues,
                // because apparently they handle "mbp:pagebreak" in a special manner and are able to somehow stop parsing
                // HTML at such elements in order to split the document and show one page at a time.
                // If we, however, parse such HTML according to HTML5 rules, we end up with a HTML tree with huge nesting
                // depth that we're unable to process in a reasonable time.
                boolean isClosed = mSupportSelfClosingNonHtmlTags && tag.isSelfClosing() && ("mbp:pagebreak".equals(tag.getName()));

                insertHtmlElement(tag.getName(), tag.getAttributes(), !isClosed, fosterParent);
                break;
            }
        }

        return reprocessToken;
    }

    /// <summary>
    /// Processes start <see cref="HtmlTagToken"/> tokens in foreign content.
    /// </summary>
    /// <remarks>
    /// For details see http://www.w3.org/TR/html5/syntax.html#parsing-main-inforeign
    /// </remarks>
    private boolean processStartTagInForeignContent(HtmlTagToken tag)
    {
        boolean reprocess = false;
        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"b"*/70:
            case /*"big"*/71:
            case /*"blockquote"*/36:
            case /*"body"*/9:
            case /*"br"*/87:
            case /*"center"*/37:
            case /*"code"*/72:
            case /*"dd"*/65:
            case /*"div"*/41:
            case /*"dl"*/42:
            case /*"dt"*/66:
            case /*"em"*/73:
            case /*"embed"*/88:
            case /*"h1"*/56:
            case /*"h2"*/57:
            case /*"h3"*/58:
            case /*"h4"*/59:
            case /*"h5"*/60:
            case /*"h6"*/61:
            case /*"head"*/1:
            case /*"hr"*/95:
            case /*"i"*/75:
            case /*"img"*/89:
            case /*"li"*/64:
            case /*"listing"*/63:
            case /*"menu"*/49:
            case /*"meta"*/5:
            case /*"nobr"*/82:
            case /*"ol"*/51:
            case /*"p"*/52:
            case /*"pre"*/62:
            case /*"ruby"*/104:
            case /*"s"*/76:
            case /*"small"*/77:
            case /*"span"*/105:
            case /*"strong"*/79:
            case /*"strike"*/78:
            case /*"sub"*/106:
            case /*"sup"*/107:
            case /*"table"*/23:
            case /*"tt"*/80:
            case /*"u"*/81:
            case /*"ul"*/55:
            case /*"var"*/108:
                mOpenedElements.removeLast();
                clearStackBackToHtmlContent();
                reprocess = true;
                break;
            case /*"font"*/74:
                if (hasColorFaceOrSizeAttribute(tag.getAttributes()))
                {
                    mOpenedElements.removeLast();
                    clearStackBackToHtmlContent();
                    reprocess = true;
                }
                else
                {
                    String ns = mOpenedElements.getLast().getNamespace();
                    if (W3CNamespaces.MATH_ML.equals(ns))
                    {
                        adjustMathMLAttributes(tag.getAttributes());
                    }
                    String adjustedName;
                    if (W3CNamespaces.SVG.equals(ns))
                    {
                        adjustedName = adjustSvgName(tag.getName());
                        adjustSvgAttributes(tag.getAttributes());
                    }
                    else
                    {
                        adjustedName = tag.getName();
                    }

                    // Foreign attributes are not adjusted here, because namespaces are not supported for attributes.

                    insertElement(adjustedName, ns, tag.getAttributes(), !tag.isSelfClosing(), false);
                }
                break;
            default:
            {
                String ns = mOpenedElements.getLast().getNamespace();
                if (W3CNamespaces.MATH_ML.equals(ns))
                {
                    adjustMathMLAttributes(tag.getAttributes());
                }
                String adjustedName;
                if (W3CNamespaces.SVG.equals(ns))
                {
                    adjustedName = adjustSvgName(tag.getName());
                    adjustSvgAttributes(tag.getAttributes());
                }
                else
                {
                    adjustedName = tag.getName();
                }

                // Foreign attributes are not adjusted here, because namespaces are not supported for attributes.

                insertElement(adjustedName, ns, tag.getAttributes(), !tag.isSelfClosing(), false);
                break;
            }
        }
        return reprocess;
    }

    /// <summary>
    /// Processes end <see cref="HtmlTagToken"/> tokens.
    /// </summary>
    private void processEndTag(HtmlTagToken tag)
    {
        boolean reprocessToken = true;
        while (reprocessToken)
        {
            reprocessToken = false;

            switch (mInsertionMode)
            {
                case InsertionMode.INITIAL:
                {
                    // If the source of the HTML document is the 'srcdoc' attribute of an IFRAME element, the document should be
                    // switched to the Standards mode. But the IFRAME 'srcdoc' attribute is not supported now.
                    mDocumentMode = HtmlDocumentMode.QUIRKS;
                    mInsertionMode = InsertionMode.BEFORE_HTML;
                    reprocessToken = true;
                    break;
                }
                case InsertionMode.BEFORE_HTML:
                {
                    if ("head".equals(tag.getName()) ||
                        "body".equals(tag.getName()) ||
                        "html".equals(tag.getName()) ||
                        "br".equals(tag.getName()))
                    {
                        mRootElement = createElement("html", W3CNamespaces.XHTML, new HtmlAttributeCollection(), true);
                        mInsertionMode = InsertionMode.BEFORE_HEAD;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.BEFORE_HEAD:
                {
                    if ("head".equals(tag.getName()) ||
                        "body".equals(tag.getName()) ||
                        "html".equals(tag.getName()) ||
                        "br".equals(tag.getName()))
                    {
                        mHeadElement = insertHtmlElement("head", new HtmlAttributeCollection(), true, false);
                        mInsertionMode = InsertionMode.IN_HEAD;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.IN_HEAD:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"head"*/1:
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.AFTER_HEAD;
                            break;
                        case /*"body"*/9:
                        case /*"html"*/0:
                        case /*"br"*/87:
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.AFTER_HEAD;
                            reprocessToken = true;
                            break;
                        default:
                            // Other end tags are ignored inside <head> elements.
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_HEAD_NOSCRIPT:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"noscript"*/8:
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.IN_HEAD;
                            break;
                        case /*"br"*/87:
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.IN_HEAD;
                            reprocessToken = true;
                            break;
                        default:
                            // Other end tags are ignored inside <noscript> elements.
                            break;
                    }
                    break;
                }
                case InsertionMode.AFTER_HEAD:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"body"*/9:
                        case /*"html"*/0:
                        case /*"br"*/87:
                            insertHtmlElement("body", new HtmlAttributeCollection(), true, false);
                            mInsertionMode = InsertionMode.IN_BODY;
                            reprocessToken = true;
                            break;
                        default:
                            // Other end tags are ignored between <head> and <body> elements.
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_BODY:
                {
                    reprocessToken = processEndTagInBody(tag, false);
                    break;
                }
                case InsertionMode.TEXT:
                {
                    mOpenedElements.removeLast();
                    mInsertionMode = mOriginalInsertionMode;
                    break;
                }
                case InsertionMode.IN_TABLE:
                {
                    reprocessToken = processEndTagInTable(tag);
                    break;
                }
                case InsertionMode.IN_TABLE_TEXT:
                {
                    insertPendingCharacters();
                    reprocessToken = true;
                    break;
                }
                case InsertionMode.IN_CAPTION:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"caption"*/14:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                generateImpliedEndTags();
                                popElementsUntil("caption");
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_TABLE;
                            }
                            break;
                        case /*"table"*/23:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                generateImpliedEndTags();
                                popElementsUntil("caption");
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                        case /*"body"*/9:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"html"*/0:
                        case /*"tbody"*/17:
                        case /*"td"*/18:
                        case /*"tfoot"*/19:
                        case /*"th"*/20:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                            // Ignore the token.
                            break;
                        default:
                            reprocessToken = processEndTagInBody(tag, false);
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_COLUMN_GROUP:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"colgroup"*/16:
                            if (!currentElementIsRootHtml())
                            {
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                            }
                            break;
                        case /*"col"*/15:
                            // Ignore the token.
                            break;
                        default:
                            if (!currentElementIsRootHtml())
                            {
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_TABLE_BODY:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                clearStackBackToTableBodyContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                            }
                            break;
                        case /*"table"*/23:
                            if (hasTableBodyElementInTableScope())
                            {
                                clearStackBackToTableBodyContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE;
                                reprocessToken = true;
                            }
                            break;
                        case /*"body"*/9:
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"html"*/0:
                        case /*"td"*/18:
                        case /*"th"*/20:
                        case /*"tr"*/22:
                            // Ignore the token.
                            break;
                        default:
                            reprocessToken = processEndTagInTable(tag);
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_ROW:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"tr"*/22:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                clearStackBackToRowContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE_BODY;
                            }
                            break;
                        case /*"table"*/23:
                            if (hasElementInTableScope("tr", W3CNamespaces.XHTML))
                            {
                                clearStackBackToRowContext();
                                mOpenedElements.removeLast();
                                mInsertionMode = InsertionMode.IN_TABLE_BODY;
                                reprocessToken = true;
                            }
                            break;
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                if (hasElementInTableScope("tr", W3CNamespaces.XHTML))
                                {
                                    clearStackBackToRowContext();
                                    mOpenedElements.removeLast();
                                    mInsertionMode = InsertionMode.IN_TABLE_BODY;
                                }
                                reprocessToken = true;
                            }
                            break;
                        case /*"body"*/9:
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"html"*/0:
                        case /*"td"*/18:
                        case /*"th"*/20:
                            // Ignore the token.
                            break;
                        default:
                            reprocessToken = processEndTagInTable(tag);
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_CELL:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"td"*/18:
                        case /*"th"*/20:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                generateImpliedEndTags();
                                popElementsUntilCell();
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_ROW;
                            }
                            break;
                        case /*"body"*/9:
                        case /*"caption"*/14:
                        case /*"col"*/15:
                        case /*"colgroup"*/16:
                        case /*"html"*/0:
                            // Ignore the token.
                            break;
                        case /*"table"*/23:
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                generateImpliedEndTags();
                                popElementsUntilCell();
                                mActiveFormattingElements.closeCurrentScope();
                                mInsertionMode = InsertionMode.IN_ROW;
                                reprocessToken = true;
                            }
                            break;
                        default:
                            reprocessToken = processEndTagInBody(tag, false);
                            break;
                    }
                    break;
                }
                case InsertionMode.IN_SELECT:
                {
                    processEndTagInSelect(tag);
                    break;
                }
                case InsertionMode.IN_SELECT_IN_TABLE:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"caption"*/14:
                        case /*"table"*/23:
                        case /*"tbody"*/17:
                        case /*"tfoot"*/19:
                        case /*"thead"*/21:
                        case /*"tr"*/22:
                        case /*"td"*/18:
                        case /*"th"*/20:
                            if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                            {
                                if (hasElementInSelectScope("select", W3CNamespaces.XHTML))
                                {
                                    popElementsUntil("select");
                                    resetInsertionModeAppropriately();
                                }
                                reprocessToken = true;
                            }
                            break;
                        default:
                            processEndTagInSelect(tag);
                            break;
                    }
                    break;
                }
                case InsertionMode.AFTER_BODY:
                {
                    if ("html".equals(tag.getName()))
                    {
                        // If the HTML document being parsed is a fragment, the token should be ignored, but parsing
                        // of HTML fragments is not supported now.
                        mInsertionMode = InsertionMode.AFTER_AFTER_BODY;
                    }
                    else
                    {
                        mInsertionMode = InsertionMode.IN_BODY;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.IN_FRAMESET:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"frameset"*/10:
                            if (!currentElementIsRootHtml())
                            {
                                mOpenedElements.removeLast();

                                // The following code block must not be executed during parsing of HTML fragments,
                                // but parsing of HTML fragments is not currently supported, and the block is always executed.
                                if (!currentElementIsFrameset())
                                {
                                    mInsertionMode = InsertionMode.AFTER_FRAMESET;
                                }
                            }
                            break;
                        default:
                            // Ignore the token.
                            break;
                    }
                    break;
                }
                case InsertionMode.AFTER_FRAMESET:
                {
                    switch (gStringSwitchMap.of(tag.getName()))
                    {
                        case /*"html"*/0:
                            mInsertionMode = InsertionMode.AFTER_AFTER_FRAMESET;
                            break;
                        default:
                            // Ignore the token.
                            break;
                    }
                    break;
                }
                case InsertionMode.AFTER_AFTER_BODY:
                {
                    mInsertionMode = InsertionMode.IN_BODY;
                    reprocessToken = true;
                    break;
                }
                case InsertionMode.AFTER_AFTER_FRAMESET:
                {
                    // Ignore the token.
                    break;
                }
                default:
                {
                    throw new IllegalStateException("Unknown insertion mode.");
                }
            }
        }
    }

    /// <summary>
    /// Processes end <see cref="HtmlTagToken"/> tokens in the 'in select' insertion mode.
    /// </summary>
    private void processEndTagInSelect(HtmlTagToken tag)
    {
        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"optgroup"*/27:
                if (currentElementIsOption() && previousElementIsOptgroup())
                {
                    mOpenedElements.removeLast();
                }
                if (currentElementIsOptgroup())
                {
                    mOpenedElements.removeLast();
                }
                break;
            case /*"option"*/26:
                if (currentElementIsOption())
                {
                    mOpenedElements.removeLast();
                }
                break;
            case /*"select"*/28:
                if (hasElementInSelectScope("select", W3CNamespaces.XHTML))
                {
                    popElementsUntil("select");
                    resetInsertionModeAppropriately();
                }
                break;
            default:
                // Ignore the token.
                break;
        }
    }

    /// <summary>
    /// Processes end <see cref="HtmlTagToken"/> tokens in the 'in table' insertion mode.
    /// </summary>
    private boolean processEndTagInTable(HtmlTagToken tag)
    {
        boolean reprocessToken = false;

        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"table"*/23:
                if (hasElementInTableScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    popElementsUntil("table");
                    resetInsertionModeAppropriately();
                }
                break;
            case /*"body"*/9:
            case /*"caption"*/14:
            case /*"col"*/15:
            case /*"colgroup"*/16:
            case /*"html"*/0:
            case /*"tbody"*/17:
            case /*"td"*/18:
            case /*"tfoot"*/19:
            case /*"th"*/20:
            case /*"thead"*/21:
            case /*"tr"*/22:
                // Ignore the token.
                break;
            default:
                reprocessToken = processEndTagInBody(tag, true);
                break;
        }

        return reprocessToken;
    }

    /// <summary>
    /// Processes end <see cref="HtmlTagToken"/> tokens in the 'in body' insertion mode.
    /// </summary>
    /// <param name="tag">The token being processed.</param>
    /// <param name="fosterParent">
    /// Insert new elements into the foster parent element instead of the current element,
    /// when the current element is a part of a table.
    /// </param>
    private boolean processEndTagInBody(HtmlTagToken tag, boolean fosterParent)
    {
        boolean reprocessToken = false;

        switch (gStringSwitchMap.of(tag.getName()))
        {
            case /*"body"*/9:
                if (hasElementInScope("body", W3CNamespaces.XHTML))
                {
                    mInsertionMode = InsertionMode.AFTER_BODY;
                }
                break;
            case /*"html"*/0:
                if (hasElementInScope("body", W3CNamespaces.XHTML))
                {
                    mInsertionMode = InsertionMode.AFTER_BODY;
                    reprocessToken = true;
                }
                break;
            case /*"address"*/33:
            case /*"article"*/34:
            case /*"aside"*/35:
            case /*"blockquote"*/36:
            case /*"button"*/68:
            case /*"center"*/37:
            case /*"details"*/38:
            case /*"dialog"*/39:
            case /*"dir"*/40:
            case /*"div"*/41:
            case /*"dl"*/42:
            case /*"fieldset"*/43:
            case /*"figcaption"*/44:
            case /*"figure"*/45:
            case /*"footer"*/46:
            case /*"header"*/47:
            case /*"hgroup"*/48:
            case /*"listing"*/63:
            case /*"menu"*/49:
            case /*"nav"*/50:
            case /*"ol"*/51:
            case /*"pre"*/62:
            case /*"section"*/53:
            case /*"summary"*/54:
            case /*"ul"*/55:
                if (hasElementInScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    generateImpliedEndTags();
                    popElementsUntil(tag.getName());
                }
                break;
            case /*"form"*/32:
            {
                HtmlElementNode formElement = mFormElement;
                mFormElement = null;
                if ((formElement != null) && hasElementInScope(formElement))
                {
                    generateImpliedEndTags();
                    mOpenedElements.remove(formElement);
                }
                break;
            }
            case /*"p"*/52:
                if (hasElementInButtonScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    closeElement(tag.getName());
                }
                else
                {
                    insertHtmlElement("p", new HtmlAttributeCollection(), true, fosterParent);
                    reprocessToken = true;
                }
                break;
            case /*"li"*/64:
                if (hasElementInListItemScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    closeElement(tag.getName());
                }
                break;
            case /*"dd"*/65:
            case /*"dt"*/66:
                if (hasElementInScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    closeElement(tag.getName());
                }
                break;
            case /*"h1"*/56:
            case /*"h2"*/57:
            case /*"h3"*/58:
            case /*"h4"*/59:
            case /*"h5"*/60:
            case /*"h6"*/61:
                if (hasHeadingElementInScope())
                {
                    generateImpliedEndTags();
                    popElementsUntilHeading();
                }
                break;
            case /*"a"*/69:
            case /*"b"*/70:
            case /*"big"*/71:
            case /*"code"*/72:
            case /*"em"*/73:
            case /*"font"*/74:
            case /*"i"*/75:
            case /*"nobr"*/82:
            case /*"s"*/76:
            case /*"small"*/77:
            case /*"strike"*/78:
            case /*"strong"*/79:
            case /*"tt"*/80:
            case /*"u"*/81:
                closeFormattingElement(tag.getName());
                break;
            case /*"applet"*/83:
            case /*"marquee"*/84:
            case /*"object"*/85:
                if (hasElementInScope(tag.getName(), W3CNamespaces.XHTML))
                {
                    generateImpliedEndTags();
                    popElementsUntil(tag.getName());
                    mActiveFormattingElements.closeCurrentScope();
                }
                break;
            case /*"br"*/87:
                reconstructActiveFormattingElements(fosterParent);
                insertHtmlElement(tag.getName(), new HtmlAttributeCollection(), false, fosterParent);
                mFramesetOk = false;
                break;
            default:
                closeOpenedElements(tag.getName());
                break;
        }

        return reprocessToken;
    }

    /// <summary>
    /// Processes end <see cref="HtmlTagToken"/> tokens in foreign content.
    /// </summary>
    /// <remarks>
    /// For details see http://www.w3.org/TR/html5/syntax.html#parsing-main-inforeign
    /// </remarks>
    private void processEndTagInForeignContent(HtmlTagToken tag)
    {
        // The element name might be adjusted from lowercase to mixed case, and tag names are always lowercase.
        if (msString.equals(msString.toLowerInvariant(mOpenedElements.getLast().getName()), tag.getName()))
        {
            // Normal case. The end tag matches the current opened element.
            mOpenedElements.removeLast();
        }
        else
        {
            // Error case. The end tag does not match the current opened element.
            // Try to find an opened element for the ened tag. Maybe fall out of foreign context in process.

            // The current element has just been examined, and the loop skips it.
            for (int i = mOpenedElements.getCount() - 2; i >= 0; --i)
            {
                HtmlElementNode node = mOpenedElements.get(i);

                if (W3CNamespaces.XHTML.equals(node.getNamespace()))
                {
                    // Fall out of foreign context back into HTML context.
                    processEndTag(tag);
                    break;
                }

                // The element name might be adjusted from lowercase to mixed case, and tag names are always lowercase.
                if (msString.equals(msString.toLowerInvariant(node.getName()), tag.getName()))
                {
                    // Found an opened element that matches the end tag. Close the element.
                    mOpenedElements.removeRange(i);
                    break;
                }
            }
        }
    }

    /// <summary>
    /// Processes <see cref="HtmlTextToken"/> tokens.
    /// </summary>
    private void processText(HtmlTextToken text)
    {
        Debug.assertTrue(text.getText().length() > 0);

        HtmlText processedText = new HtmlText(text.getText());

        if (mRemoveFirstLfFromText)
        {
            processedText.removeLeadingLineFeedChar();
            mRemoveFirstLfFromText = false;
            if (processedText.isEmpty())
            {
                return;
            }
        }

        boolean reprocessToken = true;
        while (reprocessToken)
        {
            reprocessToken = false;

            switch (mInsertionMode)
            {
                case InsertionMode.INITIAL:
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        // If the source of the HTML document is the 'srcdoc' attribute of an IFRAME element, the document should be
                        // switched to the Standards mode. But the IFRAME 'srcdoc' attribute is not supported now.
                        mDocumentMode = HtmlDocumentMode.QUIRKS;
                        mInsertionMode = InsertionMode.BEFORE_HTML;
                        reprocessToken = true;
                    }
                    break;
                case InsertionMode.BEFORE_HTML:
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        mRootElement = createElement("html", W3CNamespaces.XHTML, new HtmlAttributeCollection(), true);
                        mInsertionMode = InsertionMode.BEFORE_HEAD;
                        reprocessToken = true;
                    }
                    break;
                case InsertionMode.BEFORE_HEAD:
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        mHeadElement = insertHtmlElement("head", new HtmlAttributeCollection(), true, false);
                        mInsertionMode = InsertionMode.IN_HEAD;
                        reprocessToken = true;
                    }
                    break;
                case InsertionMode.IN_HEAD:
                {
                    String leadingWhitespace = processedText.getLeadingWhitespace();
                    if (!msString.equals(leadingWhitespace, ""))
                    {
                        insertIntoCurrentNode(leadingWhitespace);
                    }
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        mOpenedElements.removeLast();
                        mInsertionMode = InsertionMode.AFTER_HEAD;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.IN_HEAD_NOSCRIPT:
                {
                    String leadingWhitespace = processedText.getLeadingWhitespace();
                    if (!msString.equals(leadingWhitespace, ""))
                    {
                        insertIntoCurrentNode(leadingWhitespace);
                    }
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        mOpenedElements.removeLast();
                        mInsertionMode = InsertionMode.IN_HEAD;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.AFTER_HEAD:
                {
                    String leadingWhitespace = processedText.getLeadingWhitespace();
                    if (!msString.equals(leadingWhitespace, ""))
                    {
                        insertIntoCurrentNode(leadingWhitespace);
                    }
                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        insertHtmlElement("body", new HtmlAttributeCollection(), true, false);
                        mInsertionMode = InsertionMode.IN_BODY;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.IN_BODY:
                case InsertionMode.IN_CAPTION:
                case InsertionMode.IN_CELL:
                    processedText.stripNullCharacters();
                    if (!processedText.isEmpty())
                    {
                        reconstructActiveFormattingElements(false);
                        insertIntoCurrentNode(processedText);
                        if (processedText.containsAnythingButWhitespace())
                        {
                            mFramesetOk = false;
                        }
                    }
                    break;
                case InsertionMode.TEXT:
                    if (!processedText.isEmpty())
                    {
                        insertIntoCurrentNode(processedText);
                    }
                    break;
                case InsertionMode.IN_TABLE:
                case InsertionMode.IN_TABLE_BODY:
                case InsertionMode.IN_ROW:
                    if (currentElementIsTableSection())
                    {
                        mPendingTableCharacters.clear();
                        mOriginalInsertionMode = mInsertionMode;
                        mInsertionMode = InsertionMode.IN_TABLE_TEXT;
                        reprocessToken = true;
                    }
                    else
                    {
                        processedText.stripNullCharacters();
                        if (!processedText.isEmpty())
                        {
                            reconstructActiveFormattingElements(false);
                            insertIntoCurrentNode(processedText);
                            if (processedText.containsAnythingButWhitespace())
                            {
                                mFramesetOk = false;
                            }
                        }
                    }
                    break;
                case InsertionMode.IN_TABLE_TEXT:
                    processedText.stripNullCharacters();
                    if (!processedText.isEmpty())
                    {
                        mPendingTableCharacters.append(processedText);
                    }
                    break;
                case InsertionMode.IN_COLUMN_GROUP:
                {
                    String leadingWhitespace = processedText.getLeadingWhitespace();
                    if (!msString.equals(leadingWhitespace, ""))
                    {
                        insertIntoCurrentNode(leadingWhitespace);
                    }

                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        if (mOpenedElements.getCount() > 1)
                        {
                            mOpenedElements.removeLast();
                            mInsertionMode = InsertionMode.IN_TABLE;
                            reprocessToken = true;
                        }
                    }
                    break;
                }
                case InsertionMode.IN_SELECT:
                case InsertionMode.IN_SELECT_IN_TABLE:
                    processedText.stripNullCharacters();
                    if (!processedText.isEmpty())
                    {
                        insertIntoCurrentNode(processedText);
                    }
                    break;
                case InsertionMode.AFTER_BODY:
                case InsertionMode.AFTER_AFTER_BODY:
                {
                    String leadingWhitespace = processedText.getLeadingWhitespace();
                    if (!msString.equals(leadingWhitespace, ""))
                    {
                        reconstructActiveFormattingElements(false);
                        insertIntoCurrentNode(leadingWhitespace);
                        mFramesetOk = true;
                    }

                    processedText.trimLeadingWhitespace();
                    if (!processedText.isEmpty())
                    {
                        mInsertionMode = InsertionMode.IN_BODY;
                        reprocessToken = true;
                    }
                    break;
                }
                case InsertionMode.IN_FRAMESET:
                case InsertionMode.AFTER_FRAMESET:
                {
                    String whitespaces = processedText.getAllWhitespaces();
                    if (!msString.equals(whitespaces, ""))
                    {
                        reconstructActiveFormattingElements(false);
                        insertIntoCurrentNode(whitespaces);
                    }
                    processedText.clear();
                    break;
                }
                case InsertionMode.AFTER_AFTER_FRAMESET:
                {
                    String whitespaces = processedText.getAllWhitespaces();
                    if (!msString.equals(whitespaces, ""))
                    {
                        reconstructActiveFormattingElements(false);
                        insertIntoCurrentNode(whitespaces);
                    }
                    processedText.clear();
                    break;
                }
                default:
                    throw new IllegalStateException("Unknown insertion mode.");
            }
        }
    }

    /// <summary>
    /// Processes <see cref="HtmlTextToken"/> tokens in foreign content.
    /// </summary>
    /// <remarks>
    /// For details see http://www.w3.org/TR/html5/syntax.html#parsing-main-inforeign
    /// </remarks>
    private void processTextInForeignContent(HtmlTextToken text)
    {
        HtmlText processedText = new HtmlText(text.getText());
        if (processedText.containsAnythingButWhitespaceOrNull())
        {
            mFramesetOk = false;
        }
        processedText.replaceNullCharacters();
        insertIntoCurrentNode(processedText);
    }

    /// <summary>
    /// Processes <see cref="HtmlEndOfFileToken"/> token.
    /// </summary>
    private void processEndOfFile()
    {
        boolean reprocessToken = true;
        while (reprocessToken)
        {
            reprocessToken = false;

            switch (mInsertionMode)
            {
                case InsertionMode.INITIAL:
                    // If the source of the HTML document is the 'srcdoc' attribute of an IFRAME element, the document should be
                    // switched to the Standards mode. But the IFRAME 'srcdoc' attribute is not supported now.
                    mDocumentMode = HtmlDocumentMode.QUIRKS;
                    mInsertionMode = InsertionMode.BEFORE_HTML;
                    reprocessToken = true;
                    break;
                case InsertionMode.BEFORE_HTML:
                    mRootElement = createElement("html", W3CNamespaces.XHTML, new HtmlAttributeCollection(), true);
                    mInsertionMode = InsertionMode.BEFORE_HEAD;
                    reprocessToken = true;
                    break;
                case InsertionMode.BEFORE_HEAD:
                    mHeadElement = insertHtmlElement("head", new HtmlAttributeCollection(), true, false);
                    mInsertionMode = InsertionMode.IN_HEAD;
                    reprocessToken = true;
                    break;
                case InsertionMode.IN_HEAD:
                    mOpenedElements.removeLast();
                    mInsertionMode = InsertionMode.AFTER_HEAD;
                    reprocessToken = true;
                    break;
                case InsertionMode.IN_HEAD_NOSCRIPT:
                    mOpenedElements.removeLast();
                    mInsertionMode = InsertionMode.IN_HEAD;
                    reprocessToken = true;
                    break;
                case InsertionMode.AFTER_HEAD:
                    insertHtmlElement("body", new HtmlAttributeCollection(), true, false);
                    mInsertionMode = InsertionMode.IN_BODY;
                    reprocessToken = true;
                    break;
                case InsertionMode.TEXT:
                    mOpenedElements.removeLast();
                    mInsertionMode = mOriginalInsertionMode;
                    reprocessToken = true;
                    break;
                case InsertionMode.IN_TABLE_TEXT:
                    insertPendingCharacters();
                    reprocessToken = true;
                    break;
                case InsertionMode.IN_COLUMN_GROUP:
                    if (mOpenedElements.getCount() == 1)
                    {
                        mOpenedElements.clear();
                    }
                    else
                    {
                        mOpenedElements.removeLast();
                        mInsertionMode = InsertionMode.IN_TABLE;
                        reprocessToken = true;
                    }
                    break;
                case InsertionMode.IN_BODY:
                case InsertionMode.IN_TABLE:
                case InsertionMode.IN_CAPTION:
                case InsertionMode.IN_TABLE_BODY:
                case InsertionMode.IN_ROW:
                case InsertionMode.IN_CELL:
                case InsertionMode.IN_SELECT:
                case InsertionMode.IN_SELECT_IN_TABLE:
                case InsertionMode.AFTER_BODY:
                case InsertionMode.IN_FRAMESET:
                case InsertionMode.AFTER_FRAMESET:
                case InsertionMode.AFTER_AFTER_BODY:
                case InsertionMode.AFTER_AFTER_FRAMESET:
                    mOpenedElements.clear();
                    break;
                default:
                    throw new IllegalStateException("Unknown insertion mode.");
            }
        }
    }

    private void reconstructActiveFormattingElements(boolean fosterParent)
    {
        // The algorithm is described here:
        // http://www.w3.org/TR/html5/syntax.html#reconstruct-the-active-formatting-elements

        // Steps 1 - 6.
        // Find the latest marker or opened element.
        int index = mActiveFormattingElements.getCount() - 1;
        while (!formattingElementIsMarkerOrOpened(index))
        {
            --index;
        }

        // Steps 7 - 11.
        // Reopen formatting elements that follow the latest marker or opened element.
        ++index;
        while (index < mActiveFormattingElements.getCount())
        {
            Debug.assertTrue(index >= 0);
            Debug.assertTrue(index < mActiveFormattingElements.getCount());

            HtmlElementNode entry = mActiveFormattingElements.get(index);
            Debug.assertTrue(entry != null);

            HtmlElementNode newElement = insertHtmlElement(entry.getName(), entry.getAttributes(), true, fosterParent);
            mActiveFormattingElements.replace(index, newElement);

            ++index;
        }
    }

    private void closeFormattingElement(String name)
    {
        // The algorithm is described here:
        // http://www.w3.org/TR/html5/syntax.html#adoptionAgency

        // Steps 1 - 3.
        final int MAX_OUTER_LOOP_COUNT = 8;
        for (int outerLoopCounter = 0; outerLoopCounter < MAX_OUTER_LOOP_COUNT; outerLoopCounter++)
        {
            // Step 4.
            HtmlElementNode formattingElement = mActiveFormattingElements.findInCurrentScope(name);
            if (formattingElement == null)
            {
                closeOtherElement(name);
                return;
            }

            int index = mOpenedElements.indexOf(formattingElement);
            if (index < 0)
            {
                mActiveFormattingElements.remove(formattingElement);
                return;
            }

            if (!hasElementInScope(formattingElement))
            {
                return;
            }

            // Step 5.
            HtmlElementNode furthestBlock = null;
            int furthestBlockIndex = -1;
            for (int i = index + 1; i < mOpenedElements.getCount(); i++)
            {
                HtmlElementNode item = mOpenedElements.get(i);
                if (isInSpecialCategory(item))
                {
                    furthestBlock = item;
                    furthestBlockIndex = i;
                    break;
                }
            }

            // Step 6.
            if (furthestBlock == null)
            {
                mOpenedElements.removeRange(index);
                mActiveFormattingElements.remove(formattingElement);
                return;
            }

            // Step 7.
            // The first element is 'html'.
            Debug.assertTrue(index > 0);
            HtmlElementNode commonAncestor = mOpenedElements.get(index - 1);

            // Step 8.
            int bookmark = mActiveFormattingElements.indexOf(formattingElement);
            Debug.assertTrue(bookmark >= 0);

            // Step 9.
            Debug.assertTrue(furthestBlockIndex > 0);
            int nodeIndex = furthestBlockIndex;
            HtmlElementNode lastNode = furthestBlock;

            // Steps 9.1 - 9.3.
            final int MAX_INNER_LOOP_COUNT = 3;
            for (int innerLoopCounter = 0; innerLoopCounter < MAX_INNER_LOOP_COUNT; innerLoopCounter++)
            {
                // Step 9.4.
                --nodeIndex;
                HtmlElementNode node = mOpenedElements.get(nodeIndex);

                // Step 9.5.
                int nodeIndexInListOfActiveFormattingElements = mActiveFormattingElements.indexOf(node);
                if (nodeIndexInListOfActiveFormattingElements < 0)
                {

                    mOpenedElements.remove(node);
                    continue;
                }

                // Step 9.6.
                if (node == formattingElement)
                {
                    break;
                }

                // Step 9.7.
                HtmlElementNode newNode = createElement(node.getName(), node.getNamespace(), node.getAttributes(), false);
                mActiveFormattingElements.replace(nodeIndexInListOfActiveFormattingElements, newNode);
                mOpenedElements.set(nodeIndex, newNode);
                node = newNode;

                // Step 9.8.
                if (lastNode == furthestBlock)
                {
                    bookmark = nodeIndexInListOfActiveFormattingElements;
                }

                // 9.9.
                if (lastNode.getParent() != null)
                {
                    lastNode.getParent().getChildren().remove(lastNode);
                }
                node.getChildren().add(lastNode);

                // 9.10.
                lastNode = node;
            }

            // Step 10.
            if (lastNode.getParent() != null)
            {
                lastNode.getParent().getChildren().remove(lastNode);
            }
            if (isTableSection(commonAncestor))
            {
                insertIntoFosterParent(lastNode);
            }
            else
            {
                commonAncestor.getChildren().add(lastNode);
            }

            // Step 11.
            HtmlElementNode newFormattingElement = createElement(formattingElement.getName(), formattingElement.getNamespace(), formattingElement.getAttributes(), false);

            // Step 12.
            newFormattingElement.getChildren().moveRange(furthestBlock.getChildren());

            // Step 13.
            furthestBlock.getChildren().add(newFormattingElement);

            // Step 14.
            mActiveFormattingElements.remove(formattingElement);
            mActiveFormattingElements.insert(bookmark, newFormattingElement);

            // Step 15.
            mOpenedElements.remove(formattingElement);
            furthestBlockIndex = mOpenedElements.indexOf(furthestBlock);
            Debug.assertTrue(furthestBlockIndex >= 0);
            mOpenedElements.insert(furthestBlockIndex + 1, newFormattingElement);
        }
    }

    private boolean formattingElementIsMarkerOrOpened(int formattingElementIndex)
    {
        // Assume there are markers before the first element and after the last element in the list
        // of active formatting elements. This assumption helps to simplify loops.
        if ((formattingElementIndex < 0) || (formattingElementIndex >= mActiveFormattingElements.getCount()))
        {
            return true;
        }

        HtmlElementNode element = mActiveFormattingElements.get(formattingElementIndex);
        return (element == null) || mOpenedElements.contains(element);
    }

    private void resetInsertionModeAppropriately()
    {
        // The algorithm is described here:
        // http://www.w3.org/TR/html5/syntax.html#reset-the-insertion-mode-appropriately

        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            // If the HTML document being parsed is a fragment, the context element should be used instead of
            // the first opened element, but parsing of HTML fragments is not supported now.

            HtmlElementNode node = mOpenedElements.get(i);
            if (W3CNamespaces.XHTML.equals(node.getNamespace()))
            {
                switch (gStringSwitchMap.of(node.getName()))
                {
                    case /*"select"*/28:
                        mInsertionMode = InsertionMode.IN_SELECT;
                        return;
                    case /*"td"*/18:
                    case /*"th"*/20:
                        if (i > 0)
                        {
                            mInsertionMode = InsertionMode.IN_CELL;
                            return;
                        }
                        break;
                    case /*"tr"*/22:
                        mInsertionMode = InsertionMode.IN_ROW;
                        return;
                    case /*"tbody"*/17:
                    case /*"thead"*/21:
                    case /*"tfoot"*/19:
                        mInsertionMode = InsertionMode.IN_TABLE_BODY;
                        return;
                    case /*"caption"*/14:
                        mInsertionMode = InsertionMode.IN_CAPTION;
                        return;
                    case /*"colgroup"*/16:
                        mInsertionMode = InsertionMode.IN_COLUMN_GROUP;
                        return;
                    case /*"table"*/23:
                        mInsertionMode = InsertionMode.IN_TABLE;
                        return;
                    case /*"head"*/1:
                    case /*"body"*/9:
                        mInsertionMode = InsertionMode.IN_BODY;
                        return;
                    case /*"frameset"*/10:
                        mInsertionMode = InsertionMode.IN_FRAMESET;
                        return;
                    case /*"html"*/0:
                        mInsertionMode = InsertionMode.BEFORE_HEAD;
                        return;
                    default:
                        // Other elements don't require special processing; they reset the insertion mode to 'in body'.
                        break;
                }
            }
        }
        mInsertionMode = InsertionMode.IN_BODY;
    }

    private void parseRawText()
    {
        mTokenizer.switchToRawtextState();
        mOriginalInsertionMode = mInsertionMode;
        mInsertionMode = InsertionMode.TEXT;
    }

    private void parseScriptData()
    {
        mTokenizer.switchToScriptDataState();
        mOriginalInsertionMode = mInsertionMode;
        mInsertionMode = InsertionMode.TEXT;
    }

    private void parseRcdata()
    {
        mTokenizer.switchToRcdataState();
        mOriginalInsertionMode = mInsertionMode;
        mInsertionMode = InsertionMode.TEXT;
    }

    private void closeParagraphIfOpened()
    {
        if (hasElementInButtonScope("p", W3CNamespaces.XHTML))
        {
            closeElement("p");
        }
    }

    private void closeElement(String name)
    {
        generateImpliedEndTagsExcept(name);
        popElementsUntil(name);
    }

    private void closeOpenedElements(String name)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if (msString.equals(element.getName(), name))
            {
                generateImpliedEndTagsExcept(name);
                popElementsUntil(name);
                return;
            }
            if (isInSpecialCategory(element))
            {
                return;
            }
        }
    }

    private void addAttributesToHtmlElement(HtmlAttributeCollection attributes)
    {
        addAttributesToOpenedElement(attributes, 0);
    }

    private void addAttributesToOpenedElement(HtmlAttributeCollection attributes, int elementIndex)
    {
        HtmlElementNode openedElement = mOpenedElements.get(elementIndex);
        for (HtmlAttribute entry : attributes)
        {
            openedElement.getAttributes().add(entry);
        }
    }

    private HtmlElementNode getOpenedElement(int index)
    {
        if ((index >= 0) && (index < mOpenedElements.getCount()))
        {
            return mOpenedElements.get(index);
        }
        else
        {
            return null;
        }
    }

    private HtmlElementNode getLastOpenedTable()
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((W3CNamespaces.XHTML.equals(element.getNamespace())) && ("table".equals(element.getName())))
            {
                return element;
            }
        }
        return null;
    }

    private void popElementsUntil(String name)
    {
        while (!mOpenedElements.isEmpty())
        {
            HtmlElementNode poppedElement = mOpenedElements.getLast();
            mOpenedElements.removeLast();
            if (msString.equals(poppedElement.getName(), name))
            {
                return;
            }
        }
        // Should not get here.
        Debug.assertTrue(false);
    }

    private void popElementsUntilHeading()
    {
        while (!mOpenedElements.isEmpty())
        {
            HtmlElementNode poppedElement = mOpenedElements.getLast();
            mOpenedElements.removeLast();
            if (isHeadingElement(poppedElement))
            {
                return;
            }
        }
        // Should not get here.
        Debug.assertTrue(false);
    }

    private void popElementsUntilCell()
    {
        while (!mOpenedElements.isEmpty())
        {
            HtmlElementNode poppedElement = mOpenedElements.getLast();
            mOpenedElements.removeLast();
            if (("td".equals(poppedElement.getName())) || ("th".equals(poppedElement.getName())))
            {
                return;
            }
        }
        // Should not get here.
        Debug.assertTrue(false);
    }

    private void clearStackBackToTableContext()
    {
        while (!currentElementIsTableContext())
        {
            mOpenedElements.removeLast();
        }
    }

    private void clearStackBackToTableBodyContext()
    {
        while (!currentElementIsTableBodyContext())
        {
            mOpenedElements.removeLast();
        }
    }

    private void clearStackBackToRowContext()
    {
        while (!currentElementIsRowContext())
        {
            mOpenedElements.removeLast();
        }
    }

    private void clearStackBackToHtmlContent()
    {
        while (!currentElementIsHtmlContent())
        {
            mOpenedElements.removeLast();
        }
    }

    private boolean currentElementIsHtmlContent()
    {
        return currentElementIsMathMLTextIntegrationPoint() ||
            currentElementIsHtmlIntegrationPoint() ||
            currentElementIsInHtmlNamespace();
    }

    private boolean currentElementIsInHtmlNamespace()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return W3CNamespaces.XHTML.equals(currentElement.getNamespace());
    }

    private boolean currentElementIsHtmlIntegrationPoint()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        switch (gStringSwitchMap.of(currentElement.getNamespace()))
        {
            case/*W3CNamespaces.MATH_ML*/109:
            {
                if ("annotation-xml".equals(currentElement.getName()))
                {
                    String encoding = msString.toLowerInvariant(currentElement.getAttributes().getAttributeValue("encoding", ""));
                    if (("text/html".equals(encoding)) || ("application/xhtml+xml".equals(encoding)))
                    {
                        return true;
                    }
                }
                break;
            }
            case/*W3CNamespaces.SVG*/110:
            {
                if (("foreignObject".equals(currentElement.getName())) ||
                    ("desc".equals(currentElement.getName())) ||
                    ("title".equals(currentElement.getName())))
                {
                    return true;
                }
                break;
            }
            case/*NrxNamespaces.VML*/111:
            {
                // VML text boxes can contain HTML content.
                String localVmlName = getLocalVmlName(currentElement.getName());
                if ("textbox".equals(localVmlName))
                {
                    return true;
                }
                break;
            }
            default:
            {
                // Elements from other namespaces are not HTML integration points.
                break;
            }
        }
        return false;
    }

    private boolean currentElementIsMathMLTextIntegrationPoint()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.MATH_ML.equals(currentElement.getNamespace())) &&
            (("mi".equals(currentElement.getName())) ||
            ("mo".equals(currentElement.getName())) ||
            ("mn".equals(currentElement.getName())) ||
            ("ms".equals(currentElement.getName())) ||
            ("mtext".equals(currentElement.getName())));
    }

    private boolean checkNameOfOpenedElement(String name, String ns, int index)
    {
        HtmlElementNode element = getOpenedElement(index);
        return (element != null) && (msString.equals(element.getName(), name)) && (msString.equals(element.getNamespace(), ns));
    }

    private boolean hasElementInScope(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) && (msString.equals(element.getNamespace(), ns)))
            {
                return true;
            }
            if (isScopeElement(element))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasElementInScope(HtmlElementNode element)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode item = mOpenedElements.get(i);
            if (item == element)
            {
                return true;
            }
            if (isScopeElement(item))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasHeadingElementInScope()
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if (isHeadingElement(element))
            {
                return true;
            }
            if (isScopeElement(element))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasElementInButtonScope(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) && (msString.equals(element.getNamespace(), ns)))
            {
                return true;
            }
            if (isScopeElement(element))
            {
                return false;
            }
            if (("button".equals(element.getName())) && (msString.equals(element.getNamespace(), ns)))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasElementInListItemScope(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) && (msString.equals(element.getNamespace(), ns)))
            {
                return true;
            }
            if (isScopeElement(element))
            {
                return false;
            }
            if ((("ol".equals(element.getName())) || ("ul".equals(element.getName()))) && (msString.equals(element.getNamespace(), ns)))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasTableBodyElementInTableScope()
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((("tbody".equals(element.getName())) || ("tfoot".equals(element.getName())) || ("thead".equals(element.getName()))) &&
                (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return true;
            }
            if ((("html".equals(element.getName())) || ("table".equals(element.getName()))) &&
                (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasTableCellElementInTableScope()
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((("td".equals(element.getName())) || ("th".equals(element.getName()))) &&
                (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return true;
            }
            if ((("html".equals(element.getName())) || ("table".equals(element.getName()))) &&
                (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasElementInTableScope(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) &&
                (msString.equals(element.getNamespace(), ns)))
            {
                return true;
            }
            if ((("html".equals(element.getName())) || ("table".equals(element.getName()))) &&
                (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private boolean hasElementInSelectScope(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) &&
                (msString.equals(element.getNamespace(), ns)))
            {
                return true;
            }
            // The following conditions are exclusive.
            // The scope consists of ALL elements EXCEPT 'option' and 'optgroup'.
            if (((!"option".equals(element.getName())) && (!"optgroup".equals(element.getName())))
                || (!W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                return false;
            }
        }
        // Should never get here.
        Debug.assertTrue(false);
        return false;
    }

    private void generateImpliedEndTags()
    {
        // An element's name cannot be empty, so an empty string here means "do not exclude any names".
        generateImpliedEndTagsExcept("");
    }

    private void generateImpliedEndTagsExcept(String excludedName)
    {
        // There will be no infinite loop, because the "html" element is not in the list.
        boolean endTagIsImplied;
        do
        {
            HtmlElementNode currentElement = mOpenedElements.getLast();
            endTagIsImplied =
                W3CNamespaces.XHTML.equals(currentElement.getNamespace()) &&
                ("dd".equals(currentElement.getName()) ||
                "dt".equals(currentElement.getName()) ||
                "li".equals(currentElement.getName()) ||
                "option".equals(currentElement.getName()) ||
                "optgroup".equals(currentElement.getName()) ||
                "p".equals(currentElement.getName()) ||
                "rp".equals(currentElement.getName()) ||
                "rt".equals(currentElement.getName()));
            endTagIsImplied = endTagIsImplied && (!msString.equals(currentElement.getName(), excludedName));
            if (endTagIsImplied)
            {
                mOpenedElements.removeLast();
            }
        } while (endTagIsImplied);
    }

    private boolean currentElementNameIsHeader()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return isHeadingElement(currentElement);
    }

    private boolean currentElementIsTableSection()
    {
        return isTableSection(mOpenedElements.getLast());
    }

    private boolean currentElementIsOption()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            ("option".equals(currentElement.getName()));
    }

    private boolean currentElementIsOptgroup()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            ("optgroup".equals(currentElement.getName()));
    }

    private boolean previousElementIsOptgroup()
    {
        if (mOpenedElements.getCount() > 1)
        {
            HtmlElementNode element = mOpenedElements.get(mOpenedElements.getCount() - 2);
            return (W3CNamespaces.XHTML.equals(element.getNamespace())) && ("optgroup".equals(element.getName()));
        }
        else
        {
            return false;
        }
    }

    private boolean currentElementIsRootHtml()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            ("html".equals(currentElement.getName())) &&
            (mOpenedElements.getCount() == 1);
    }

    private boolean currentElementIsFrameset()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            ("frameset".equals(currentElement.getName()));
    }

    private boolean currentElementIsTableContext()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            (("table".equals(currentElement.getName())) ||
            ("html".equals(currentElement.getName())));
    }

    private boolean currentElementIsTableBodyContext()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            (("tbody".equals(currentElement.getName())) ||
            ("tfoot".equals(currentElement.getName())) ||
            ("thead".equals(currentElement.getName())) ||
            ("html".equals(currentElement.getName())));
    }

    private boolean currentElementIsRowContext()
    {
        HtmlElementNode currentElement = mOpenedElements.getLast();
        return (W3CNamespaces.XHTML.equals(currentElement.getNamespace())) &&
            (("tr".equals(currentElement.getName())) ||
            ("html".equals(currentElement.getName())));
    }

    private void closeOpenedItemElements(String name, String ns)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((msString.equals(element.getName(), name)) && (msString.equals(element.getNamespace(), ns)))
            {
                generateImpliedEndTagsExcept("li");
                popElementsUntil("li");
                return;
            }
            if (isInSpecialCategoryExceptAddressDivP(element))
            {
                return;
            }
        }
    }

    private void closeOpenedDdAndDtElements()
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode element = mOpenedElements.get(i);
            if ((("dd".equals(element.getName())) || ("dt".equals(element.getName()))) && (W3CNamespaces.XHTML.equals(element.getNamespace())))
            {
                generateImpliedEndTagsExcept(element.getName());
                popElementsUntil(element.getName());
                return;
            }
            if (isInSpecialCategoryExceptAddressDivP(element))
            {
                return;
            }
        }
    }

    private void closeOtherElement(String name)
    {
        for (int i = mOpenedElements.getCount() - 1; i >= 0; i--)
        {
            HtmlElementNode item = mOpenedElements.get(i);
            if (msString.equals(item.getName(), name))
            {
                generateImpliedEndTagsExcept(name);
                mOpenedElements.removeRange(i);
                break;
            }
            else if (isInSpecialCategory(item))
            {
                break;
            }
        }
    }

    private HtmlElementNode createElement(String name, String ns, HtmlAttributeCollection attributes, boolean opened)
    {
        HtmlElementNode element = new HtmlElementNode(name, ns, attributes);

        if (opened)
        {
            mOpenedElements.add(element);
        }

        return element;
    }

    private HtmlElementNode insertHtmlElement(String name, HtmlAttributeCollection attributes, boolean opened,
        boolean fosterParentIfInTable)
    {
        return insertElement(name, W3CNamespaces.XHTML, attributes, opened, fosterParentIfInTable);
    }

    private HtmlElementNode insertElement(String name, String ns, HtmlAttributeCollection attributes, boolean opened,
        boolean fosterParentIfInTable)
    {
        HtmlElementNode element = new HtmlElementNode(name, ns, attributes);

        if (fosterParentIfInTable && currentElementIsTableSection())
        {
            insertIntoFosterParent(element);
        }
        else
        {
            insertIntoCurrentNode(element);
        }

        if (opened)
        {
            mOpenedElements.add(element);
        }

        return element;
    }

    private void insertIntoCurrentNode(String text)
    {
        Debug.assertTrue(StringUtil.hasChars(text));

        insertIntoCurrentNode(new HtmlTextNode(text));
    }

    private void insertIntoCurrentNode(HtmlText text)
    {
        Debug.assertTrue(text != null);

        insertIntoCurrentNode(text.getAsString());
    }

    private void insertIntoFosterParent(HtmlText text)
    {
        Debug.assertTrue(text != null);
        Debug.assertTrue(!text.isEmpty());

        insertIntoFosterParent(new HtmlTextNode(text.getAsString()));
    }

    private void insertIntoCurrentNode(HtmlNode node)
    {
        Debug.assertTrue(node != null);

        HtmlElementNode currentNode = mOpenedElements.getLast();
        currentNode.getChildren().add(node);
    }

    private void insertIntoFosterParent(HtmlNode node)
    {
        Debug.assertTrue(node != null);

        HtmlElementNode lastOpenedTable = getLastOpenedTable();
        if (lastOpenedTable == null)
        {
            HtmlElementNode htmlElement = mOpenedElements.get(0);
            htmlElement.getChildren().add(node);
        }
        else
        {
            HtmlElementNode parent = lastOpenedTable.getParent();
            if (parent == null)
            {
                int lastOpenedTableIndex = mOpenedElements.indexOf(lastOpenedTable);
                parent = mOpenedElements.get(lastOpenedTableIndex - 1);
            }
            int newNodeIndex = parent.getChildren().indexOf(lastOpenedTable);
            if (newNodeIndex >= 0)
            {
                parent.getChildren().insert(newNodeIndex, node);
            }
            else
            {
                parent.getChildren().add(node);
            }
        }
    }

    private void insertPendingCharacters()
    {
        if (mPendingTableCharacters.containsAnythingButWhitespace())
        {
            reconstructActiveFormattingElements(true);
            if (currentElementIsTableSection())
            {
                insertIntoFosterParent(mPendingTableCharacters);
            }
            else
            {
                insertIntoCurrentNode(mPendingTableCharacters);
            }
            mFramesetOk = false;
        }
        else
        {
            insertIntoCurrentNode(mPendingTableCharacters);
        }
        mInsertionMode = mOriginalInsertionMode;
    }

    private boolean isScopeElement(HtmlElementNode element)
    {
        // VML text boxes can contain HTML content.
        if (NrxNamespaces.VML.equals(element.getNamespace()))
        {
            String localVmlName = getLocalVmlName(element.getName());
            if ("textbox".equals(localVmlName))
            {
                return true;
            }
        }

        switch (gStringSwitchMap.of(element.getName()))
        {
            case /*"applet"*/83:
            case /*"caption"*/14:
            case /*"html"*/0:
            case /*"table"*/23:
            case /*"td"*/18:
            case /*"th"*/20:
            case /*"marquee"*/84:
            case /*"object"*/85:
                return W3CNamespaces.XHTML.equals(element.getNamespace());
            case /*"mi"*/112:
            case /*"mo"*/113:
            case /*"mn"*/114:
            case /*"ms"*/115:
            case /*"mtext"*/116:
            case /*"annotation-xml"*/117:
                return W3CNamespaces.MATH_ML.equals(element.getNamespace());
            case /*"foreignObject"*/118:
            case /*"desc"*/119:
            case /*"title"*/13:
                return W3CNamespaces.SVG.equals(element.getNamespace());
            default:
                return false;
        }
    }

    private static boolean isHeadingElement(HtmlElementNode element)
    {
        Debug.assertTrue(element != null);
        if (W3CNamespaces.XHTML.equals(element.getNamespace()))
        {
            return
                ("h1".equals(element.getName())) ||
                ("h2".equals(element.getName())) ||
                ("h3".equals(element.getName())) ||
                ("h4".equals(element.getName())) ||
                ("h5".equals(element.getName())) ||
                ("h6".equals(element.getName()));
        }
        else
        {
            return false;
        }
    }

    private static boolean isTableSection(HtmlElementNode element)
    {
        if (W3CNamespaces.XHTML.equals(element.getNamespace()))
        {
            return
                ("table".equals(element.getName())) ||
                ("thead".equals(element.getName())) ||
                ("tbody".equals(element.getName())) ||
                ("tfoot".equals(element.getName())) ||
                ("tr".equals(element.getName()));
        }
        else
        {
            return false;
        }
    }

    private static boolean isInSpecialCategoryExceptAddressDivP(HtmlElementNode element)
    {
        // The namespace is checked in the first method.
        return
            isInSpecialCategory(element) &&
            (!"address".equals(element.getName())) &&
            (!"div".equals(element.getName())) &&
            (!"p".equals(element.getName()));
    }

    private static boolean isInSpecialCategory(HtmlElementNode element)
    {
        switch (gStringSwitchMap.of(element.getName()))
        {
            case /*"address"*/33:
            case /*"applet"*/83:
            case /*"area"*/86:
            case /*"article"*/34:
            case /*"aside"*/35:
            case /*"base"*/11:
            case /*"basefont"*/2:
            case /*"bgsound"*/3:
            case /*"blockquote"*/36:
            case /*"body"*/9:
            case /*"br"*/87:
            case /*"button"*/68:
            case /*"caption"*/14:
            case /*"center"*/37:
            case /*"col"*/15:
            case /*"colgroup"*/16:
            case /*"command"*/25:
            case /*"dd"*/65:
            case /*"details"*/38:
            case /*"dir"*/40:
            case /*"div"*/41:
            case /*"dl"*/42:
            case /*"dt"*/66:
            case /*"embed"*/88:
            case /*"fieldset"*/43:
            case /*"figcaption"*/44:
            case /*"figure"*/45:
            case /*"footer"*/46:
            case /*"form"*/32:
            case /*"frame"*/24:
            case /*"frameset"*/10:
            case /*"h1"*/56:
            case /*"h2"*/57:
            case /*"h3"*/58:
            case /*"h4"*/59:
            case /*"h5"*/60:
            case /*"h6"*/61:
            case /*"head"*/1:
            case /*"header"*/47:
            case /*"hgroup"*/48:
            case /*"hr"*/95:
            case /*"html"*/0:
            case /*"iframe"*/98:
            case /*"img"*/89:
            case /*"input"*/29:
            case /*"isindex"*/96:
            case /*"li"*/64:
            case /*"link"*/4:
            case /*"listing"*/63:
            case /*"marquee"*/84:
            case /*"menu"*/49:
            case /*"meta"*/5:
            case /*"nav"*/50:
            case /*"noembed"*/99:
            case /*"noframes"*/6:
            case /*"noscript"*/8:
            case /*"object"*/85:
            case /*"ol"*/51:
            case /*"p"*/52:
            case /*"param"*/92:
            case /*"plaintext"*/67:
            case /*"pre"*/62:
            case /*"script"*/12:
            case /*"section"*/53:
            case /*"select"*/28:
            case /*"source"*/93:
            case /*"style"*/7:
            case /*"summary"*/54:
            case /*"table"*/23:
            case /*"tbody"*/17:
            case /*"td"*/18:
            case /*"textarea"*/31:
            case /*"tfoot"*/19:
            case /*"th"*/20:
            case /*"thead"*/21:
            case /*"tr"*/22:
            case /*"track"*/94:
            case /*"ul"*/55:
            case /*"wbr"*/90:
            case /*"xmp"*/97:
                return W3CNamespaces.XHTML.equals(element.getNamespace());
            case /*"mi"*/112:
            case /*"mo"*/113:
            case /*"mn"*/114:
            case /*"ms"*/115:
            case /*"mtext"*/116:
                return W3CNamespaces.MATH_ML.equals(element.getNamespace());
            case /*"foreignObject"*/118:
            case /*"desc"*/119:
                return W3CNamespaces.SVG.equals(element.getNamespace());
            case /*"title"*/13:
                return
                    W3CNamespaces.XHTML.equals(element.getNamespace()) ||
                    W3CNamespaces.SVG.equals(element.getNamespace());
            default:
                return false;
        }
    }

    private static String adjustSvgName(String name)
    {
        switch (gStringSwitchMap.of(name))
        {
            case /*"altglyph"*/120:
                return "altGlyph";
            case /*"altglyphdef"*/121:
                return "altGlyphDef";
            case /*"altglyphitem"*/122:
                return "altGlyphItem";
            case /*"animatecolor"*/123:
                return "animateColor";
            case /*"animatemotion"*/124:
                return "animateMotion";
            case /*"animatetransform"*/125:
                return "animateTransform";
            case /*"clippath"*/126:
                return "clipPath";
            case /*"feblend"*/127:
                return "feBlend";
            case /*"fecolormatrix"*/128:
                return "feColorMatrix";
            case /*"fecomponenttransfer"*/129:
                return "feComponentTransfer";
            case /*"fecomposite"*/130:
                return "feComposite";
            case /*"feconvolvematrix"*/131:
                return "feConvolveMatrix";
            case /*"fediffuselighting"*/132:
                return "feDiffuseLighting";
            case /*"fedisplacementmap"*/133:
                return "feDisplacementMap";
            case /*"fedistantlight"*/134:
                return "feDistantLight";
            case /*"feflood"*/135:
                return "feFlood";
            case /*"fefunca"*/136:
                return "feFuncA";
            case /*"fefuncb"*/137:
                return "feFuncB";
            case /*"fefuncg"*/138:
                return "feFuncG";
            case /*"fefuncr"*/139:
                return "feFuncR";
            case /*"fegaussianblur"*/140:
                return "feGaussianBlur";
            case /*"feimage"*/141:
                return "feImage";
            case /*"femerge"*/142:
                return "feMerge";
            case /*"femergenode"*/143:
                return "feMergeNode";
            case /*"femorphology"*/144:
                return "feMorphology";
            case /*"feoffset"*/145:
                return "feOffset";
            case /*"fepointlight"*/146:
                return "fePointLight";
            case /*"fespecularlighting"*/147:
                return "feSpecularLighting";
            case /*"fespotlight"*/148:
                return "feSpotLight";
            case /*"fetile"*/149:
                return "feTile";
            case /*"feturbulence"*/150:
                return "feTurbulence";
            case /*"foreignobject"*/151:
                return "foreignObject";
            case /*"glyphref"*/152:
                return "glyphRef";
            case /*"lineargradient"*/153:
                return "linearGradient";
            case /*"radialgradient"*/154:
                return "radialGradient";
            case /*"textpath"*/155:
                return "textPath";
            default:
                return name;
        }
    }

    /// <summary>
    /// Gets local VML element name, without the namespace prefix.
    /// </summary>
    private String getLocalVmlName(String name)
    {
        if (StringUtil.hasChars(mVmlElementPrefix) && msString.startsWith(name, mVmlElementPrefix, StringComparison.ORDINAL))
        {
            String localName = name.substring(mVmlElementPrefix.length());
            if (StringUtil.hasChars(localName))
            {
                return localName;
            }
        }
        return name;
    }

    private static void adjustMathMLAttributes(HtmlAttributeCollection attributes)
    {
        attributes.rename("definitionurl", "definitionURL");
    }

    private static void adjustSvgAttributes(HtmlAttributeCollection attributes)
    {
        attributes.rename("attributename", "attributeName");
        attributes.rename("attributetype", "attributeType");
        attributes.rename("basefrequency", "baseFrequency");
        attributes.rename("baseprofile", "baseProfile");
        attributes.rename("calcmode", "calcMode");
        attributes.rename("clippathunits", "clipPathUnits");
        attributes.rename("contentscripttype", "contentScriptType");
        attributes.rename("contentstyletype", "contentStyleType");
        attributes.rename("diffuseconstant", "diffuseConstant");
        attributes.rename("edgemode", "edgeMode");
        attributes.rename("externalresourcesrequired", "externalResourcesRequired");
        attributes.rename("filterres", "filterRes");
        attributes.rename("filterunits", "filterUnits");
        attributes.rename("glyphref", "glyphRef");
        attributes.rename("gradienttransform", "gradientTransform");
        attributes.rename("gradientunits", "gradientUnits");
        attributes.rename("kernelmatrix", "kernelMatrix");
        attributes.rename("kernelunitlength", "kernelUnitLength");
        attributes.rename("keypoints", "keyPoints");
        attributes.rename("keysplines", "keySplines");
        attributes.rename("keytimes", "keyTimes");
        attributes.rename("lengthadjust", "lengthAdjust");
        attributes.rename("limitingconeangle", "limitingConeAngle");
        attributes.rename("markerheight", "markerHeight");
        attributes.rename("markerunits", "markerUnits");
        attributes.rename("markerwidth", "markerWidth");
        attributes.rename("maskcontentunits", "maskContentUnits");
        attributes.rename("maskunits", "maskUnits");
        attributes.rename("numoctaves", "numOctaves");
        attributes.rename("pathlength", "pathLength");
        attributes.rename("patterncontentunits", "patternContentUnits");
        attributes.rename("patterntransform", "patternTransform");
        attributes.rename("patternunits", "patternUnits");
        attributes.rename("pointsatx", "pointsAtX");
        attributes.rename("pointsaty", "pointsAtY");
        attributes.rename("pointsatz", "pointsAtZ");
        attributes.rename("preservealpha", "preserveAlpha");
        attributes.rename("preserveaspectratio", "preserveAspectRatio");
        attributes.rename("primitiveunits", "primitiveUnits");
        attributes.rename("refx", "refX");
        attributes.rename("refy", "refY");
        attributes.rename("repeatcount", "repeatCount");
        attributes.rename("repeatdur", "repeatDur");
        attributes.rename("requiredextensions", "requiredExtensions");
        attributes.rename("requiredfeatures", "requiredFeatures");
        attributes.rename("specularconstant", "specularConstant");
        attributes.rename("specularexponent", "specularExponent");
        attributes.rename("spreadmethod", "spreadMethod");
        attributes.rename("startoffset", "startOffset");
        attributes.rename("stddeviation", "stdDeviation");
        attributes.rename("stitchtiles", "stitchTiles");
        attributes.rename("surfacescale", "surfaceScale");
        attributes.rename("systemlanguage", "systemLanguage");
        attributes.rename("tablevalues", "tableValues");
        attributes.rename("targetx", "targetX");
        attributes.rename("targety", "targetY");
        attributes.rename("textlength", "textLength");
        attributes.rename("viewbox", "viewBox");
        attributes.rename("viewtarget", "viewTarget");
        attributes.rename("xchannelselector", "xChannelSelector");
        attributes.rename("ychannelselector", "yChannelSelector");
        attributes.rename("zoomandpan", "zoomAndPan");
    }

    private static boolean hasColorFaceOrSizeAttribute(HtmlAttributeCollection attributes)
    {
        return (attributes.get("color") != null) ||
            (attributes.get("face") != null) ||
            (attributes.get("size") != null);
    }

    /// <summary>
    /// The insertion modes of the HTML tree constructor.
    /// </summary>
    private /*enum*/ final class InsertionMode
    {
        private InsertionMode(){}
        
        public static final int INITIAL = 0;
        public static final int BEFORE_HTML = 1;
        public static final int BEFORE_HEAD = 2;
        public static final int IN_HEAD = 3;
        public static final int IN_HEAD_NOSCRIPT = 4;
        public static final int AFTER_HEAD = 5;
        public static final int IN_BODY = 6;
        public static final int TEXT = 7;
        public static final int IN_TABLE = 8;
        public static final int IN_TABLE_TEXT = 9;
        public static final int IN_CAPTION = 10;
        public static final int IN_COLUMN_GROUP = 11;
        public static final int IN_TABLE_BODY = 12;
        public static final int IN_ROW = 13;
        public static final int IN_CELL = 14;
        public static final int IN_SELECT = 15;
        public static final int IN_SELECT_IN_TABLE = 16;
        public static final int AFTER_BODY = 17;
        public static final int IN_FRAMESET = 18;
        public static final int AFTER_FRAMESET = 19;
        public static final int AFTER_AFTER_BODY = 20;
        public static final int AFTER_AFTER_FRAMESET = 21;

        public static final int length = 22;
    }

    /// <summary>
    /// The tokenizer that parses the HTML document into tokens.
    /// </summary>
    private /*final*/ HtmlTokenizer mTokenizer;

    /// <summary>
    /// Namespace prefix of VML element names in this document. <c>null</c> if we should skip VML content.
    /// </summary>
    private String mVmlElementPrefix;

    /// <summary>
    /// The current insertion mode of the tree constructor.
    /// </summary>
    private /*InsertionMode*/int mInsertionMode;

    /// <summary>
    /// The insertion mode to which the tree constructor must switch back after processing a text token.
    /// </summary>
    private /*InsertionMode*/int mOriginalInsertionMode;

    /// <summary>
    /// The quirks mode of the HTML document.
    /// </summary>
    private /*HtmlDocumentMode*/int mDocumentMode;

    /// <summary>
    /// Flag that indicates that the HTML document can contain frames.
    /// </summary>
    private boolean mFramesetOk;

    /// <summary>
    /// Flag that indicates that if the text of a node starts with a LF character (U+00A0), this character
    /// must be removed.
    /// </summary>
    private boolean mRemoveFirstLfFromText;

    /// <summary>
    /// The root element of the document's HTML tree.
    /// </summary>
    private HtmlElementNode mRootElement;

    /// <summary>
    /// The head element of the document's HTML tree.
    /// </summary>
    private HtmlElementNode mHeadElement;

    /// <summary>
    /// The current (opened) form element.
    /// </summary>
    private HtmlElementNode mFormElement;

    /// <summary>
    /// The list of HTML elements that are still opened. The most recently opened element comes last.
    /// </summary>
    private /*final*/ HtmlOpenedElementList mOpenedElements = new HtmlOpenedElementList();

    /// <summary>
    /// The list of active formatting elements. The most recently opened element comes last.
    /// </summary>
    private /*final*/ HtmlActiveFormattingElementList mActiveFormattingElements = new HtmlActiveFormattingElementList();

    /// <summary>
    /// The temporary buffer that holds text of a table element until it is inserted to an appropriate
    /// place in the HTML tree.
    /// </summary>
    private /*final*/ HtmlText mPendingTableCharacters = new HtmlText();

    /// <summary>
    /// Indicates whether to parse &lt;noscript&gt; Html tags.
    /// </summary>
    private /*final*/ boolean mIsScriptingEnabled;

    /// <summary>
    /// Indicates whether to support certain self-closing non-HTML tags that are used, for example, in MOBI documents.
    /// </summary>
    private /*final*/ boolean mSupportSelfClosingNonHtmlTags;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"html",
		"head",
		"basefont",
		"bgsound",
		"link",
		"meta",
		"noframes",
		"style",
		"noscript",
		"body",
		"frameset",
		"base",
		"script",
		"title",
		"caption",
		"col",
		"colgroup",
		"tbody",
		"td",
		"tfoot",
		"th",
		"thead",
		"tr",
		"table",
		"frame",
		"command",
		"option",
		"optgroup",
		"select",
		"input",
		"keygen",
		"textarea",
		"form",
		"address",
		"article",
		"aside",
		"blockquote",
		"center",
		"details",
		"dialog",
		"dir",
		"div",
		"dl",
		"fieldset",
		"figcaption",
		"figure",
		"footer",
		"header",
		"hgroup",
		"menu",
		"nav",
		"ol",
		"p",
		"section",
		"summary",
		"ul",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"pre",
		"listing",
		"li",
		"dd",
		"dt",
		"plaintext",
		"button",
		"a",
		"b",
		"big",
		"code",
		"em",
		"font",
		"i",
		"s",
		"small",
		"strike",
		"strong",
		"tt",
		"u",
		"nobr",
		"applet",
		"marquee",
		"object",
		"area",
		"br",
		"embed",
		"img",
		"wbr",
		"image",
		"param",
		"source",
		"track",
		"hr",
		"isindex",
		"xmp",
		"iframe",
		"noembed",
		"rp",
		"rt",
		"math",
		"svg",
		"ruby",
		"span",
		"sub",
		"sup",
		"var",
		"http://www.w3.org/1998/Math/MathML",
		"http://www.w3.org/2000/svg",
		"urn:schemas-microsoft-com:vml",
		"mi",
		"mo",
		"mn",
		"ms",
		"mtext",
		"annotation-xml",
		"foreignObject",
		"desc",
		"altglyph",
		"altglyphdef",
		"altglyphitem",
		"animatecolor",
		"animatemotion",
		"animatetransform",
		"clippath",
		"feblend",
		"fecolormatrix",
		"fecomponenttransfer",
		"fecomposite",
		"feconvolvematrix",
		"fediffuselighting",
		"fedisplacementmap",
		"fedistantlight",
		"feflood",
		"fefunca",
		"fefuncb",
		"fefuncg",
		"fefuncr",
		"fegaussianblur",
		"feimage",
		"femerge",
		"femergenode",
		"femorphology",
		"feoffset",
		"fepointlight",
		"fespecularlighting",
		"fespotlight",
		"fetile",
		"feturbulence",
		"foreignobject",
		"glyphref",
		"lineargradient",
		"radialgradient",
		"textpath"
	);

}

