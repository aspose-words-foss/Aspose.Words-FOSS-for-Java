// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.StreamReader;
import com.aspose.StringUtil;
import com.aspose.ms.System.IO.StringReader;
import com.aspose.ms.System.IO.TextReader;
import com.aspose.ms.System.msString;


/// <summary>
/// Represents a complete HTML document.
/// </summary>
class HtmlDocument
{
    /// <summary>
    /// Constructor. Initializes a new instance of the class.
    /// </summary>
    /// <param name="root">The root node of the document's HTML tree. May not be <c>null</c>.</param>
    /// <param name="mode">The quirks mode of the document.</param>
    HtmlDocument(HtmlElementNode root, /*HtmlDocumentMode*/int mode)
    {
        Debug.assertTrue(root != null);

        mRoot = root;
        mMode = mode;
        mDefaultLanguage = getDefaultLanguageFromMetaElements(getRoot());
    }

    /// <summary>
    /// Loads an HTML document from a stream with the specified encoding.
    /// If the document has BOM, it will take precedence over the specified encoding.
    /// </summary>
    /// <param name="stream">The input stream. May not be <c>null</c>.</param>
    /// <param name="encoding">The input encoding. May not be <c>null</c>.</param>
    /// <param name="supportedFeatures">
    /// Features that are considered supported when loading conditional parts (IE conditional expressions).
    /// Setting this parameter to <c>null</c> disables support for conditional parts, like in modern browers.
    /// </param>
    /// <param name="isScriptingEnabled">Indicates whether support for scripts is enabled when parsing HTML.</param>
    /// <param name="supportSelfClosingNonHtmlTags">
    /// Indicates whether to support certain self-closing non-HTML tags.
    /// </param>
    static HtmlDocument load(
        Stream stream,
        Encoding encoding,
        Features supportedFeatures,
        boolean isScriptingEnabled,
        boolean supportSelfClosingNonHtmlTags) throws Exception
    {
        Debug.assertTrue(stream != null);
        Debug.assertTrue(encoding != null);

        return load(
            new StreamReader(stream, encoding),
            supportedFeatures,
            isScriptingEnabled,
            supportSelfClosingNonHtmlTags);
    }

    /// <summary>
    /// Loads an HTML document from a file encoded in UTF-8.
    /// </summary>
    /// <param name="filePath">Path to an HTML file to load from.</param>
    /// <remarks>
    /// Support for IE conditional expressions is disabled.
    /// Scripting is disabled.
    /// Self-closing non-HTML tags are not supported.
    /// </remarks>
    static HtmlDocument loadFromFile(String filePath) throws Exception
    {
        Debug.assertTrue(StringUtil.hasChars(filePath));

        StreamReader reader = new StreamReader(filePath, Encoding.getUTF8());
        try /*JAVA: was using*/
        {
            return load(reader, null, false, false);
        }
        finally { if (reader != null) reader.close(); }
    }

    /// <summary>
    /// Loads the HTML document from the specified string.
    /// </summary>
    /// <param name="html">
    /// String containing the HTML document to load. May not be <c>null</c>.
    /// </param>
    /// <remarks>
    /// Support for IE conditional expressions is disabled.
    /// Scripting is disabled.
    /// Self-closing non-HTML tags are not supported.
    /// </remarks>
    static HtmlDocument load(String html) throws Exception
    {
        return load(html, null, false, false);
    }

    /// <summary>
    /// Loads the HTML document from the specified UTF-8 stream.
    /// </summary>
    /// <param name="stream">
    /// Stream containing the HTML document to load. May not be <c>null</c>.
    /// </param>
    /// <remarks>
    /// Support for IE conditional expressions is disabled.
    /// Scripting is disabled.
    /// Self-closing non-HTML tags are not supported.
    /// </remarks>
    static HtmlDocument load(Stream stream) throws Exception
    {
        return load(stream, Encoding.getUTF8(), null, false, false);
    }

    /// <summary>
    /// Loads the HTML document from the specified string.
    /// </summary>
    /// <param name="html">
    /// String containing the HTML document to load. May not be <c>null</c>.
    /// </param>
    /// <param name="supportedFeatures">
    /// Features that are considered supported when loading conditional parts (IE conditional expressions).
    /// Setting this parameter to <c>null</c> disables support for conditional parts, like in modern browers.
    /// </param>
    /// <param name="isScriptingEnabled">Indicates whether support for scripts is enabled when parsing HTML.</param>
    /// <param name="supportSelfClosingNonHtmlTags">
    /// Indicates whether to support certain self-closing non-HTML tags.
    /// </param>
    static HtmlDocument load(
        String html,
        Features supportedFeatures,
        boolean isScriptingEnabled,
        boolean supportSelfClosingNonHtmlTags) throws Exception
    {
        Debug.assertTrue(html != null);

        StringReader reader = new StringReader(html);
        try /*JAVA: was using*/
        {
            return load(reader, supportedFeatures, isScriptingEnabled, supportSelfClosingNonHtmlTags);
        }
        finally { if (reader != null) reader.close(); }
    }

    /// <summary>
    /// Loads an XHTML document from a stream.
    /// </summary>
    static HtmlDocument loadXhtml(Stream stream)
    {
        HtmlDocument result = XhtmlTreeConstructor.construct(stream);

        // Recalculate boxes of HTML elements.
        result.getRoot().buildBoxLayout();

        return result;
    }

    /// <summary>
    /// Gets the root node of the document's HTML tree.
    /// </summary>
    HtmlElementNode getRoot() { return mRoot; };

    private HtmlElementNode mRoot;

    /// <summary>
    /// Gets the quirks mode of the document.
    /// </summary>
    /*HtmlDocumentMode*/int getMode() { return mMode; };

    private /*HtmlDocumentMode*/int mMode;

    /// <summary>
    /// Gets the default language of the document, which is set through the &lt;meta&gt; element.
    /// If no default language is set for the document, this value is an empty string.
    /// </summary>
    /// <remarks>
    /// See http://www.w3.org/TR/html5/document-metadata.html#attr-meta-http-equiv-content-language
    /// </remarks>
    String getDefaultLanguage() { return mDefaultLanguage; };

    private String mDefaultLanguage;

    /// <summary>
    /// Loads the HTML document from the specified TextReader.
    /// </summary>
    /// <param name="reader">The TextReader used to feed the HTML data into the document. May not be <c>null</c>.</param>
    /// <param name="supportedFeatures">
    /// Features that are considered supported when reading IE conditional comments.
    /// Setting this parameter to <c>null</c> disables support for conditional comments, like in modern browers.
    /// </param>
    /// <param name="isScriptingEnabled">Indicates whether support for scripts is enabled when parsing HTML.</param>
    /// <param name="supportSelfClosingNonHtmlTags">
    /// Indicates whether to support certain self-closing non-HTML tags.
    /// </param>
    private static HtmlDocument load(
        TextReader reader,
        Features supportedFeatures,
        boolean isScriptingEnabled,
        boolean supportSelfClosingNonHtmlTags) throws Exception
    {
        Debug.assertTrue(reader != null);

        HtmlDocument result = HtmlTreeConstructor.construct(
            new HtmlTokenizer(reader.readToEnd(), supportedFeatures),
            isScriptingEnabled,
            supportSelfClosingNonHtmlTags);

        // Recalculate boxes of HTML elements.
        result.getRoot().buildBoxLayout();

        return result;
    }

    /// <summary>
    /// Gets the default language of the document from &lt;meta&gt; elements.
    /// </summary>
    /// <param name="root">The element from which tree traversal is to be started.</param>
    /// <returns>The default language of the document. If no default language is set, an empty string is returned.</returns>
    /// <remarks>
    /// The method performs the non-recursive depth-first tree traversal and searches for &lt;meta&gt; elements that declare
    /// the document language (pragma-set default language). The language declared by the last such element is returned.
    /// </remarks>
    private static String getDefaultLanguageFromMetaElements(HtmlElementNode root)
    {
        Debug.assertTrue(root != null);
        String result = "";

        HtmlTreeEnumerator enumerator = new HtmlTreeEnumerator(root);
        while (enumerator.moveNext())
        {
            HtmlElementNode elementNode = ms.as(enumerator.getCurrent(), HtmlElementNode.class);
            if (enumerator.isStart() && (elementNode != null))
            {
                String furtherResult = getPragmaSetDefaultLanguageValue(elementNode);
                if (!msString.equals(furtherResult, ""))
                {
                    result = furtherResult;
                }
            }
        }
        return result;
    }

    /// <summary>
    /// Gets language declaration from the element if it is a &lt;meta&gt; element that declares the document language
    /// (a pragma-set default language).
    /// </summary>
    /// <param name="element">An element of the HTML tree.</param>
    /// <returns>
    /// The language declared by the element. If the element is not a &lt;meta&gt; element or if it does not
    /// contain a valid language declaration, an empty string is returned.
    /// </returns>
    /// <remarks>
    /// The algorithm is described here:
    /// http://www.w3.org/TR/html5/document-metadata.html#attr-meta-http-equiv-content-language
    /// </remarks>
    private static String getPragmaSetDefaultLanguageValue(HtmlElementNode element)
    {
        if (!"meta".equals(element.getName()))
        {
            return "";
        }

        HtmlAttribute httpEquivAttribute = element.getAttributes().get("http-equiv");
        if ((httpEquivAttribute == null) ||
            (!StringUtil.equalsIgnoreCase(httpEquivAttribute.getValue(), "content-language")))
        {
            return "";
        }

        HtmlAttribute contentAttribute = element.getAttributes().get("content");
        if (contentAttribute == null)
        {
            return "";
        }

        String content = contentAttribute.getValue();
        if (StringUtil.contains(content, ",", false))
        {
            return "";
        }

        int startIndex = 0;
        while ((startIndex < content.length()) && HtmlUtil.isWhitespace(content.charAt(startIndex)))
        {
            ++startIndex;
        }

        int length = 0;
        while (((startIndex + length) < content.length()) && (!HtmlUtil.isWhitespace(content.charAt(startIndex + length))))
        {
            ++length;
        }

        return content.substring((startIndex), (startIndex) + (length));
    }
}

