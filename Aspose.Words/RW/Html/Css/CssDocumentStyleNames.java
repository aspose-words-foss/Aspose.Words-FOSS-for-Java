// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/04/2024 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToIntBidirectionalMap;
import com.aspose.IntList;


/// <summary>
/// Converts names of built-in document styles to CSS identifiers and vice versa for HTML round trip.
/// </summary>
/*static*/ class CssDocumentStyleNames
{
	/* Simulation of static class by using private constructor */
	private CssDocumentStyleNames()
	{}


    static /*StyleIdentifier*/int cssStyleNameToStyleIdentifier(CssPropertyValue styleNamePropertyValue)
    {
        if ((styleNamePropertyValue.getCount() == 1) &&
            (styleNamePropertyValue.getFirstValue().getValueType() == CssValueType.IDENTIFIER))
        {
            String cssStyleName = ((CssIdentifierValue)styleNamePropertyValue.getFirstValue()).getValue();

            int styleIdentifierValue = G_PARAGRAPH_STYLE_NAMES_MAP.tryGetValue(cssStyleName);
            if (!StringToIntBidirectionalMap.isNullSubstitute(styleIdentifierValue))
            {
                return (/*StyleIdentifier*/int)styleIdentifierValue;
            }

            styleIdentifierValue = G_CHARACTER_STYLE_NAMES_MAP.tryGetValue(cssStyleName);
            if (!StringToIntBidirectionalMap.isNullSubstitute(styleIdentifierValue))
            {
                return (/*StyleIdentifier*/int)styleIdentifierValue;
            }
        }

        return StyleIdentifier.NIL;
    }

    static String styleIdentifierToCssStyleName(/*StyleIdentifier*/int styleIdentifier)
    {
        String identifier = G_PARAGRAPH_STYLE_NAMES_MAP.tryGetValue((int)styleIdentifier);
        if (identifier == null)
        {
            identifier = G_CHARACTER_STYLE_NAMES_MAP.tryGetValue((int)styleIdentifier);
        }
        return identifier;
    }

    static boolean validateStyleType(/*StyleIdentifier*/int styleIdentifier, /*StyleType*/int requestedStyleType)
    {
        int key = (int)styleIdentifier;
        if (G_PARAGRAPH_STYLE_NAMES_MAP.containsValue(key))
        {
            return requestedStyleType == StyleType.PARAGRAPH;
        }
        if (G_CHARACTER_STYLE_NAMES_MAP.containsValue(key))
        {
            return requestedStyleType == StyleType.CHARACTER;
        }
        return false;
    }

    static /*StyleIdentifier*/int[] getCharacterStyleIdentifiers()
    {
        /*StyleIdentifier*/int[] identifiers = new /*StyleIdentifier*/int[G_CHARACTER_STYLE_IDENTIFIERS.getCount()];
        for (int i = 0; i < G_CHARACTER_STYLE_IDENTIFIERS.getCount(); i++)
        {
            identifiers[i] = (/*StyleIdentifier*/int)G_CHARACTER_STYLE_IDENTIFIERS.get(i);
        }
        return identifiers;
    }

    private static void addMapEntry(/*StyleIdentifier*/int styleIdentifier, String cssIdentifier, boolean isCharacterStyle)
    {
        if (isCharacterStyle)
        {
            G_CHARACTER_STYLE_NAMES_MAP.addEntry((int)styleIdentifier, cssIdentifier);
            G_CHARACTER_STYLE_IDENTIFIERS.add((int)styleIdentifier);
        }
        else
        {
            G_PARAGRAPH_STYLE_NAMES_MAP.addEntry((int)styleIdentifier, cssIdentifier);
        }
    }

    /// <summary>
    /// Maps CSS identifiers that we use for built-in paragraph styles to the corresponding <see cref="StyleIdentifier"/>
    /// values.
    /// </summary>
    /// <remarks>Keys are CSS identifiers. Values are <see cref="StyleIdentifier"/> values casted to int.</remarks>
    private static /*final*/ StringToIntBidirectionalMap G_PARAGRAPH_STYLE_NAMES_MAP;

    /// <summary>
    /// Maps CSS identifiers that we use for built-in character styles to the corresponding <see cref="StyleIdentifier"/>
    /// values.
    /// </summary>
    /// <remarks>Keys are CSS identifiers. Values are <see cref="StyleIdentifier"/> values casted to int.</remarks>
    private static /*final*/ StringToIntBidirectionalMap G_CHARACTER_STYLE_NAMES_MAP;

    /// <summary>
    /// List of built-in character style identifiers.
    /// </summary>
    /// <remarks>Stores <see cref="StyleIdentifier"/> values casted to int.</remarks>
    private static /*final*/ IntList G_CHARACTER_STYLE_IDENTIFIERS;
    static/* CssDocumentStyleNames()*/
    {
        // CSS identifiers that we use are case-insensitive.
        G_PARAGRAPH_STYLE_NAMES_MAP = new StringToIntBidirectionalMap(false);
        G_CHARACTER_STYLE_NAMES_MAP = new StringToIntBidirectionalMap(false);
        G_CHARACTER_STYLE_IDENTIFIERS = new IntList();

        addMapEntry(StyleIdentifier.BALLOON_TEXT, "balloon-text", false);
        addMapEntry(StyleIdentifier.BODY_TEXT, "body-text", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_2, "body-text2", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_3, "body-text3", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_1_I, "body-text-first-indent", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_1_I_2, "body-text-first-indent2", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_IND, "body-text-indent", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_IND_2, "body-text-indent2", false);
        addMapEntry(StyleIdentifier.BODY_TEXT_IND_3, "body-text-indent3", false);
        addMapEntry(StyleIdentifier.CLOSING, "closing", false);
        addMapEntry(StyleIdentifier.COMMENT_SUBJECT, "comment-subject", false);
        addMapEntry(StyleIdentifier.COMMENT_TEXT, "comment-text", false);
        addMapEntry(StyleIdentifier.DATE, "date", false);
        addMapEntry(StyleIdentifier.DOCUMENT_MAP, "document-map", false);
        addMapEntry(StyleIdentifier.EMAIL_SIGNATURE, "email-signature", false);
        addMapEntry(StyleIdentifier.ENDNOTE_TEXT, "endnote-text", false);
        addMapEntry(StyleIdentifier.FOOTER, "footer", false);
        addMapEntry(StyleIdentifier.FOOTNOTE_TEXT, "footnote-text", false);
        addMapEntry(StyleIdentifier.HEADER, "header", false);
        addMapEntry(StyleIdentifier.HEADING_1, "heading1", false);
        addMapEntry(StyleIdentifier.HEADING_2, "heading2", false);
        addMapEntry(StyleIdentifier.HEADING_3, "heading3", false);
        addMapEntry(StyleIdentifier.HEADING_4, "heading4", false);
        addMapEntry(StyleIdentifier.HEADING_5, "heading5", false);
        addMapEntry(StyleIdentifier.HEADING_6, "heading6", false);
        addMapEntry(StyleIdentifier.HEADING_7, "heading7", false);
        addMapEntry(StyleIdentifier.HEADING_8, "heading8", false);
        addMapEntry(StyleIdentifier.HEADING_9, "heading9", false);
        addMapEntry(StyleIdentifier.HTML_ADDRESS, "html-address", false);
        addMapEntry(StyleIdentifier.HTML_PREFORMATTED, "html-preformatted", false);
        addMapEntry(StyleIdentifier.INTENSE_QUOTE, "intense-quote", false);
        addMapEntry(StyleIdentifier.MACRO, "macro", false);
        addMapEntry(StyleIdentifier.MESSAGE_HEADER, "message-header", false);
        addMapEntry(StyleIdentifier.NOTE_HEADING, "note-heading", false);
        addMapEntry(StyleIdentifier.PLAIN_TEXT, "plain-text", false);
        addMapEntry(StyleIdentifier.QUOTE, "quote", false);
        addMapEntry(StyleIdentifier.SALUTATION, "salutation", false);
        addMapEntry(StyleIdentifier.SIGNATURE, "signature", false);
        addMapEntry(StyleIdentifier.SUBTITLE, "subtitle", false);
        addMapEntry(StyleIdentifier.TITLE, "title", false);

        addMapEntry(StyleIdentifier.BIBLIOGRAPHY, "bibliography", false);
        addMapEntry(StyleIdentifier.BLOCK_TEXT, "block-text", false);
        addMapEntry(StyleIdentifier.CAPTION, "caption", false);
        addMapEntry(StyleIdentifier.ENVELOPE_ADDRESS, "envelope-address", false);
        addMapEntry(StyleIdentifier.ENVELOPE_RETURN, "envelope-return", false);
        addMapEntry(StyleIdentifier.INDEX_1, "index1", false);
        addMapEntry(StyleIdentifier.INDEX_2, "index2", false);
        addMapEntry(StyleIdentifier.INDEX_3, "index3", false);
        addMapEntry(StyleIdentifier.INDEX_4, "index4", false);
        addMapEntry(StyleIdentifier.INDEX_5, "index5", false);
        addMapEntry(StyleIdentifier.INDEX_6, "index6", false);
        addMapEntry(StyleIdentifier.INDEX_7, "index7", false);
        addMapEntry(StyleIdentifier.INDEX_8, "index8", false);
        addMapEntry(StyleIdentifier.INDEX_9, "index9", false);
        addMapEntry(StyleIdentifier.INDEX_HEADING, "index-heading", false);
        addMapEntry(StyleIdentifier.LIST, "list", false);
        addMapEntry(StyleIdentifier.LIST_2, "list2", false);
        addMapEntry(StyleIdentifier.LIST_3, "list3", false);
        addMapEntry(StyleIdentifier.LIST_4, "list4", false);
        addMapEntry(StyleIdentifier.LIST_5, "list5", false);
        addMapEntry(StyleIdentifier.LIST_BULLET, "list-bullet", false);
        addMapEntry(StyleIdentifier.LIST_BULLET_2, "list-bullet2", false);
        addMapEntry(StyleIdentifier.LIST_BULLET_3, "list-bullet3", false);
        addMapEntry(StyleIdentifier.LIST_BULLET_4, "list-bullet4", false);
        addMapEntry(StyleIdentifier.LIST_BULLET_5, "list-bullet5", false);
        addMapEntry(StyleIdentifier.LIST_CONTINUE, "list-continue", false);
        addMapEntry(StyleIdentifier.LIST_CONTINUE_2, "list-continue2", false);
        addMapEntry(StyleIdentifier.LIST_CONTINUE_3, "list-continue3", false);
        addMapEntry(StyleIdentifier.LIST_CONTINUE_4, "list-continue4", false);
        addMapEntry(StyleIdentifier.LIST_CONTINUE_5, "list-continue5", false);
        addMapEntry(StyleIdentifier.LIST_NUMBER, "list-number", false);
        addMapEntry(StyleIdentifier.LIST_NUMBER_2, "list-number2", false);
        addMapEntry(StyleIdentifier.LIST_NUMBER_3, "list-number3", false);
        addMapEntry(StyleIdentifier.LIST_NUMBER_4, "list-number4", false);
        addMapEntry(StyleIdentifier.LIST_NUMBER_5, "list-number5", false);
        addMapEntry(StyleIdentifier.LIST_PARAGRAPH, "list-paragraph", false);
        addMapEntry(StyleIdentifier.NO_SPACING, "no-spacing", false);
        addMapEntry(StyleIdentifier.NORMAL, "normal", false);
        addMapEntry(StyleIdentifier.NORMAL_WEB, "normal-web", false);
        addMapEntry(StyleIdentifier.NORMAL_INDENT, "normal-indent", false);
        addMapEntry(StyleIdentifier.TABLE_OF_AUTHORITIES, "table-of-authorities", false);
        addMapEntry(StyleIdentifier.TABLE_OF_FIGURES, "table-of-figures", false);
        addMapEntry(StyleIdentifier.TOA_HEADING, "toa-heading", false);
        addMapEntry(StyleIdentifier.TOC_1, "toc1", false);
        addMapEntry(StyleIdentifier.TOC_2, "toc2", false);
        addMapEntry(StyleIdentifier.TOC_3, "toc3", false);
        addMapEntry(StyleIdentifier.TOC_4, "toc4", false);
        addMapEntry(StyleIdentifier.TOC_5, "toc5", false);
        addMapEntry(StyleIdentifier.TOC_6, "toc6", false);
        addMapEntry(StyleIdentifier.TOC_7, "toc7", false);
        addMapEntry(StyleIdentifier.TOC_8, "toc8", false);
        addMapEntry(StyleIdentifier.TOC_9, "toc9", false);
        addMapEntry(StyleIdentifier.TOC_HEADING, "toc-heading", false);
        addMapEntry(StyleIdentifier.REVISION, "revision", false);

        addMapEntry(StyleIdentifier.BOOK_TITLE, "book-title", true);
        addMapEntry(StyleIdentifier.COMMENT_REFERENCE, "comment-reference", true);
        addMapEntry(StyleIdentifier.EMPHASIS, "emphasis", true);
        addMapEntry(StyleIdentifier.ENDNOTE_REFERENCE, "endnote-reference", true);
        addMapEntry(StyleIdentifier.FOLLOWED_HYPERLINK, "followed-hyperlink", true);
        addMapEntry(StyleIdentifier.FOOTNOTE_REFERENCE, "footnote-reference", true);
        addMapEntry(StyleIdentifier.HASHTAG, "hashtag", true);
        addMapEntry(StyleIdentifier.HTML_ACRONYM, "html-acronym", true);
        addMapEntry(StyleIdentifier.HTML_CITE, "html-cite", true);
        addMapEntry(StyleIdentifier.HTML_CODE, "html-code", true);
        addMapEntry(StyleIdentifier.HTML_DEFINITION, "html-definition", true);
        addMapEntry(StyleIdentifier.HTML_KEYBOARD, "html-keyboard", true);
        addMapEntry(StyleIdentifier.HTML_SAMPLE, "html-sample", true);
        addMapEntry(StyleIdentifier.HTML_TYPEWRITER, "html-typewriter", true);
        addMapEntry(StyleIdentifier.HTML_VARIABLE, "html-variable", true);
        addMapEntry(StyleIdentifier.HYPERLINK, "hyperlink", true);
        addMapEntry(StyleIdentifier.INTENSE_EMPHASIS, "intense-emphasis", true);
        addMapEntry(StyleIdentifier.INTENSE_REFERENCE, "intense-reference", true);
        addMapEntry(StyleIdentifier.LINE_NUMBER, "line-number", true);
        addMapEntry(StyleIdentifier.MENTION, "mention", true);
        addMapEntry(StyleIdentifier.PAGE_NUMBER, "page-number", true);
        addMapEntry(StyleIdentifier.PLACEHOLDER_TEXT, "placeholder-text", true);
        addMapEntry(StyleIdentifier.SMART_HYPERLINK, "smart-hyperlink", true);
        addMapEntry(StyleIdentifier.SMART_LINK, "smart-link", true);
        addMapEntry(StyleIdentifier.STRONG, "strong", true);
        addMapEntry(StyleIdentifier.SUBTLE_EMPHASIS, "subtle-emphasis", true);
        addMapEntry(StyleIdentifier.SUBTLE_REFERENCE, "subtle-reference", true);
        addMapEntry(StyleIdentifier.UNRESOLVED_MENTION, "unresolved-mention", true);
    }
}

