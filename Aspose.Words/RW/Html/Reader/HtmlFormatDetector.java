// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/02/2012 by Andrey Soldatov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.CustomTextReader;
import com.aspose.HashSetGeneric;
import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.SortedStringListGeneric;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.CodePage;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// <para>The class is a helper for <see cref="FileFormatDetector"/> and performs two tasks:</para>
/// <para>
/// 1. Confirms or rejects and assumption that a provided stream is HTML;
/// </para>
/// <para>
/// 2. If HTML is confirmed, determines its encoding. This is needed because <see cref="Parser.HtmlDocument"/> cannot do it.
/// </para>
/// </summary>
/// <remarks>
/// During detection process the class tries to hold the following conditions:
/// 1. Read as few characters of provided stream as possible. It's implemented through special
///    <see cref="CustomTextReader"/> which has reading limit and stops reading when the limit is reached.
/// 
/// 2. Confirm as Html even invalid Html as long as the handled stream cannot be of other supported format.
/// 
/// Encoding is determined in two stages:
/// 1. Draft, which makes possible to read ASCII chars (ANSI, UTF-8, UTF-7, UTF-16BE, UTF-16LE, UTF-32BE, UTF-32LE);
///    It comes with <see cref="CustomTextReader"/>. If it's wrong, the stream will be refused and the detection process
///    will be called with another draft encoding.
/// 
/// 2. Precise, which makes possible to read national chars. It's determined by this class basing on XML/HTML headers.
/// 
/// The class contains the only static method accessible from outside: <see cref="Detect(CustomTextReader)"/>,
/// use it to confirm or reject an assumption that a provided stream is HTML.
/// </remarks>
class HtmlFormatDetector
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private HtmlFormatDetector(CustomTextReader textReader)
    {
        mTextReader = textReader;
        mSingleUseTags = new HashSetGeneric<String>();
        mSingleUseTags.add("head");
        mSingleUseTags.add("title");
        mSingleUseTags.add("body");
    }

    /// <summary>
    /// Returns <see cref="FileFormatInfo"/> if Html assumption has been confirmed, or null if rejected.
    /// </summary>
    static FileFormatInfo detect(CustomTextReader streamReader) throws Exception
    {
        return new HtmlFormatDetector(streamReader).detect();
    }

    private FileFormatInfo detect() throws Exception
    {
        // Sets Draft Encoding. Should be selected by a calling class.
        mEncoding = mTextReader.getEncoding();
        Debug.assertTrue(mEncoding != null);

        // Precise Encoding can be found only when HTML structure is encoded by single-byte characters.
        // With UTF-8 HTML structure is encoded by single-byte characters too.
        if (hasMultiByteAsciiChar(mEncoding))
            mIsEncodingConfirmed = true;

        // Stop if either of the following is true:
        // 1. An assumption that the stream is Html is rejected
        // 2. An assumption that the stream is Html is confirmed *AND* encoding is confirmed
        while (!hasDecision() || (isHtmlConfirmed() && !mIsEncodingConfirmed))
        {
            // Text between tags is ignored but we stop cycle if read limit has been reached
            // or syntax error has been found which breaks Html structure.
            if (!skipText())
                break;

            // Stop if read limit has been reached or syntax error has been found.
            if (!processOneTag())
                break;
        }

        FileFormatInfo fileFormatInfo = new FileFormatInfo();
        if (isHtmlConfirmed())
        {
            fileFormatInfo.setLoadFormat(LoadFormat.HTML);
        }
        // WORDSNET-22633 The detector sets the encoding even if it is unsure if the document is in HTML format.
        // In some scenarios, the calling code knows better what the format of the document is and it only needs to
        // determine its encoding.
        fileFormatInfo.setEncoding(mEncoding);
        return fileFormatInfo;
    }

    /// <summary>
    /// <para>Rewinds the handled stream until end of a text.</para>
    /// 
    /// <para>Returns whether reading of a tag was successful.</para>
    /// </summary>
    private boolean skipText() throws Exception
    {
        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();
            switch (ch)
            {
                case '<':
                    mTextReader.stepBack();
                    return true;
                case '>':
                    return false;
                default:
                    // SQ fix: nothing to do.
                    break;
            }
        }

        return false;
    }

    /// <summary>
    /// <para>Reads one tag or text block from the handled stream.</para>
    /// <para>Stops when reading limit set for the stream is reached. It's to avoid reading of whole big file during
    /// detection process. 'Good' tags can increase reading limit for the stream.</para>
    /// 
    /// <para>Returns whether reading of a tag was successful.</para>
    /// </summary>
    private boolean processOneTag() throws Exception
    {
        // Uppercase/lowercase difference in tags isn't considered in detection.
        // One of purposes of the detection algorithm is to confirm Html even if it's invalid Html
        // if it cannot be treated as a file of any other supported format.
        String tagName = detectTagName();

        switch (gStringSwitchMap.of(tagName))
        {
            case /*"?xml"*/0:
            case /*"!doctype"*/1:
            case /*"html"*/2:
            case /*"head"*/3:
            case /*"title"*/4:
            case /*"meta"*/5:
                mTextReader.resetReadLimit();
                break;
            case /*"/head"*/6:
            case /*"body"*/7:
                mTextReader.lockResetReadLimit();
                break;
            case /*"script"*/8:
                // WORDSNET-21454 Skip all content of a Script tag. Scripts require special processing, because
                // they can contain "<" and ">" characters and their parts can be confused with HTML markup.
                return skipElement("</script>", ";{}");
            case /*"!--"*/9:
                return skipComment();
            case /*"style"*/10:
                return skipElement("</style>", ";{},");
            case /*""*/11:
                return false;
            default:
                // SQ fix: nothing to do.
                break;
        }

        return processTagAttrs(tagName);
    }

    /// <summary>
    /// <para>Extracts tag name from the handled stream.</para>
    /// <para>Returns empty string if nothing has been read.</para>
    /// </summary>
    private String detectTagName() throws Exception
    {
        if (!mTextReader.hasChars())
            return "";

        char ch = mTextReader.readChar();
        Debug.assertTrue(ch == '<');

        mStringBuilder.setLength(0);

        while (mTextReader.hasChars())
        {
            ch = mTextReader.readChar();
            if (ch == 0)
                return "";

            if (ch == '>')
            {
                mTextReader.stepBack();
                return mStringBuilder.toString();
            }

            if (StringUtil.isWhiteSpace(ch))
                return mStringBuilder.toString();

            if ((ch == '-') && ("!-".equals(mStringBuilder.toString())))
                return "!--";

            mStringBuilder.append(StringUtil.asciiLowerCase(ch));
        }

        return "";
    }

    /// <summary>
    /// <para>Rewinds the handled stream until end of a comment.</para>
    /// 
    /// <para>Returns whether reading of a tag was successful.</para>
    /// </summary>
    private boolean skipComment() throws Exception
    {
        // Found characters of "-->" sequence.
        // Note that the dash characters of the comment start tag "<!--" are also taken into account by HTML browsers
        // when looking for the end tag. Consequently, the following sequences are all considered valid HTML comments:
        // "<!-->", "<!--->", and "<!---->". That's why the initial value of the dash character counter is 2.
        int dashesFound = 2;

        // WORDSNET-26980 We met a HTML document with a long URL in a comment but the read limit didn't allow us to skip
        // such a long comment and HTML format detection failed. Now we reset the read limit a number of times in order to
        // increase it and let the detector sucessfully skip long HTML comments. The repeat number was chosen arbitrarily
        // and can be changed as necessary.
        for (int i = 0; i < 3; i++)
        {
            while (mTextReader.hasChars())
            {
                char ch = mTextReader.readChar();

                switch (ch)
                {
                    case '-':
                        dashesFound++;
                        break;
                    case '>':
                        if (dashesFound >= 2)
                            return true;
                        dashesFound = 0;
                        break;
                    case '\r':
                    case '\n':
                        mTextReader.resetReadLimit();
                        dashesFound = 0;
                        break;
                    default:
                        dashesFound = 0;
                        break;
                }
            }
            mTextReader.resetReadLimit();
        }

        return false;
    }

    /// <summary>
    /// Skips content of a specified element.
    /// </summary>
    private boolean skipElement(String endTag, String delimiters) throws Exception
    {
        LastTextMatcher endTagMatcher = new LastTextMatcher(endTag);

        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();

            if (endTagMatcher.matchesAfterReadingChar(StringUtil.asciiLowerCase(ch)))
            {
                mTextReader.resetReadLimit();
                return true;
            }

            // Splits the whole block into parts to fit into the read limit. This is a very simplified validity check
            // for content of the element, which allows us to skip large elements without hitting the read limit.
            // We keep reading element's content while it looks valid.
            if (delimiters.indexOf(ch) != -1)
            {
                mTextReader.resetReadLimit();
            }
        }

        return false;
    }

    /// <summary>
    /// <para>Reads all attributes for one tag until closing '>'.</para>
    /// <para>Returns whether reading of attributes was successful.</para>
    /// </summary>
    private boolean processTagAttrs(String tagName) throws Exception
    {
        SortedStringListGeneric<String> tagAttrs = getTagAttrs();

        // If (tagAttrs == null), reading of the tag contents was not successful, they are too long or ill-formed.
        if (tagAttrs == null)
            return false;

        // WORDSNET-22697 Single-use tags should be taken into account only once. A document that uses
        // any of these tags more than once is likely not a HTML document.
        if (mSingleUseTags.contains(tagName))
        {
            mSingleUseTags.remove(tagName);
            mHtmlConfidence++;
            return true;
        }

        switch (gStringSwitchMap.of(tagName))
        {
            case /*"?xml"*/0:
                if (!mIsEncodingConfirmed)
                    extractXmlEncoding(tagAttrs);
                break;
            case /*"!doctype"*/1:
                checkDoctype(tagAttrs);
                break;
            case /*"html"*/2:
                mHtmlConfidence = 3;
                break;
            case /*"table"*/12:
            case /*"tr"*/13:
            case /*"td"*/14:
            case /*"h1"*/15:
            case /*"h2"*/16:
            case /*"h3"*/17:
            case /*"h4"*/18:
            case /*"h5"*/19:
            case /*"h6"*/20:
            case /*"div"*/21:
            case /*"p"*/22:
            case /*"img"*/23:
            case /*"ol"*/24:
            case /*"ul"*/25:
                // AS I could add other Html tags: span, div, a, b, i, u, em, strong, img, h1-h6, p, ul, ol, li.
                // But I don't think we should open as Html any file with text looking as Html tag. It may be just a text.
                // RK I have actually added most of the above tags to fix WORDSNET-6564 where html starts with <table>.
                // andrnosk: I have added <ol> and <ul> to fix WORDSNET-8009.
                mHtmlConfidence++;
                break;
            case /*"meta"*/5:
                mHtmlConfidence++;
                if (!mIsEncodingConfirmed)
                    checkContentType(tagAttrs);
                break;
            default:
                break;
        }

        return true;
    }

    //>>>>>>>> #region  Get Tag's Attributes

    /// <summary>
    /// <para>Parses input stream until the end of a tag or available characters.</para>
    /// 
    /// <para>Key of returned SortedStringList is attribute name, value is attribute value. Both are strings.</para>
    /// <para>Returns <c>null</c> if syntax is invalid.</para>
    /// </summary>
    private SortedStringListGeneric<String> getTagAttrs() throws Exception
    {
        SortedStringListGeneric<String> attributes = new SortedStringListGeneric<String>();

        while (true)
        {
            StringPair keyValuePair = new StringPair();

            if (detectAttrKeyValuePair(keyValuePair))
            {
                addAttrAndResetReadLimitIfNeeded(attributes, keyValuePair);
            }
            else
            {
                if ( detectTagEnd() )
                    return attributes;
                else
                    return null;
            }
        }
    }

    /// <summary>
    /// <para>Detects tag's next attribute as Key-Value pair: [tag key1=value1 key2="value2" key3].</para>
    /// <para>Stores the result to the given StringPair. Returns <c>true</c> when successful.</para>
    /// </summary>
    private boolean detectAttrKeyValuePair(StringPair keyValuePair) throws Exception
    {
        Debug.assertTrue("".equals(keyValuePair.Key));
        Debug.assertTrue("".equals(keyValuePair.Value));

        keyValuePair.Key = detectAttrKeyOrValue();

        if ("".equals(keyValuePair.Key))
            return false;

        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();

            if (StringUtil.isWhiteSpace(ch))
                continue;

            if (ch == '=')
                break;

            mTextReader.stepBack();
            return true;
        }

        keyValuePair.Value = detectAttrKeyOrValue();
        return true;
    }

    /// <summary>
    /// Detects one phrase which is a name of an attribute or its value.
    /// </summary>
    private String detectAttrKeyOrValue() throws Exception
    {
        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();

            if (StringUtil.isWhiteSpace(ch))
                continue;

            if ((ch == '\'') || (ch == '"'))
            {
                return detectQuotation(ch);
            }
            else
            {
                mTextReader.stepBack();
                return detectWord();
            }
        }

        return "";
    }

    /// <summary>
    /// Detects one quoted string.
    /// </summary>
    private String detectQuotation(char quoteChar) throws Exception
    {
        mStringBuilder.setLength(0);

        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();
            if (ch == quoteChar)
                break;
            mStringBuilder.append(ch);
        }

        // FIX 11573. Allow empty string.
        return mStringBuilder.length() > 0 ? mStringBuilder.toString() : "empty";
    }

    /// <summary>
    /// Detects one word.
    /// </summary>
    private String detectWord() throws Exception
    {
        mStringBuilder.setLength(0);

        while (mTextReader.hasChars())
        {
            char ch = mTextReader.readChar();

            if (StringUtil.isWhiteSpace(ch))
                break;

            if ((ch == '/') || (ch == '>') || (ch == '='))
            {
                mTextReader.stepBack();
                break;
            }

            mStringBuilder.append(ch);
        }

        return mStringBuilder.toString();
    }

    /// <summary>
    /// <para>Adds key-value pair to a SortedStringList of attributes.</para>
    /// <para>Clears provided key and value, preparing them to next attribute.</para>
    /// </summary>
    private void addAttrAndResetReadLimitIfNeeded(SortedStringListGeneric<String> attrTable, StringPair keyValuePair)
    {
        if ("".equals(keyValuePair.Key))
            return;

        String attrNameString = msString.toLowerInvariant(keyValuePair.Key);
        String attrValueString = msString.toLowerInvariant(keyValuePair.Value);
        attrTable.set(attrNameString, attrValueString);

        // MS Word generates very long 'html' tag with many 'xmlns' attributes.
        // We increase read limit to find anything except them.
        if (msString.startsWith(attrNameString, "xmlns:", StringComparison.ORDINAL))
            mTextReader.resetReadLimit();

        // WORDSNET-26054 Base64-encoded data URLs can be very long (for example, value of a 'src' attribute on an 'img'
        // tag).
        if (msString.startsWith(attrValueString, "data:", StringComparison.ORDINAL))
            mTextReader.resetReadLimit();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Detects tag's end: ">" or "/>".
    /// </summary>
    private boolean detectTagEnd() throws Exception
    {
        if (!mTextReader.hasChars())
            return false;

        char ch = mTextReader.readChar();

        if (ch == '>')
            return true;

        Debug.assertTrue(ch == '/');
        if (!mTextReader.hasChars())
            return false;

        ch = mTextReader.readChar();

        return ch == '>';
    }

    /// <summary>
    /// Sets encoding basing on 'encoding' attribute.
    /// </summary>
    private void extractXmlEncoding(SortedStringListGeneric<String> tagAttrs)
    {
        String encoding = DictionaryExtension.getValueOrNull(tagAttrs, "encoding");
        if (encoding == null)
            return;

        setEncodingSafe(encoding);
    }

    /// <summary>
    /// Checks if the document has a HTML DOCTYPE.
    /// </summary>
    private void checkDoctype(SortedStringListGeneric<String> doctypeAttributes)
    {
        // The presence of correct HTML DOCTYPE increases the chance that the document is well-formed HTML.
        // WORDSNET-20891 If DOCTYPE of the document is malformed, it should be ignored and shouldn't increase
        // the "confidence level".
        if ((doctypeAttributes.getCount() > 0) && doctypeAttributes.containsKey("html"))
            mHtmlConfidence++;
    }

    /// <summary>
    /// Called for 'meta' tag to extract encoding.
    /// </summary>
    private void checkContentType(SortedStringListGeneric<String> tagAttrs)
    {
        // We consider 2 cases:
        //   <meta charset="utf-8" />
        //   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        String httpEquiv = DictionaryExtension.getValueOrNull(tagAttrs, "http-equiv");
        String charset = DictionaryExtension.getValueOrNull(tagAttrs, "charset");

        // If only one 'charset' attribute exists (HTML5: charset="utf-8")
        if ((charset != null) && (httpEquiv == null))
        {
            setEncodingSafe(charset);
            mHtmlConfidence = 3;
            return;
        }

        if ((httpEquiv == null) || !msString.equals(httpEquiv, "content-type", StringComparison.ORDINAL_IGNORE_CASE))
            return;

        String content = DictionaryExtension.getValueOrNull(tagAttrs, "content");
        if (content == null)
            return;

        Match match = G_CONTENT_TYPET_DETECTOR.match(content);
        if (match.getSuccess())
        {
            String contentType = msString.toLowerInvariant(match.getGroups().get(1).getValue());
            if ("text/html".equals(contentType))
                mHtmlConfidence = 3;
        }
        // If charset joined with content type(HTML4: content="text/html; charset=utf-8")
        match = G_CHARSET_DETECTOR.match(content);
        if (match.getSuccess())
        {
            String matchedCharset = match.getGroups().get(1).getValue();
            setEncodingSafe(matchedCharset);
        }
    }

    /// <summary>
    /// Sets encoding by its name. Never throws exceptions.
    /// </summary>
    private void setEncodingSafe(String encodingString)
    {
        Debug.assertTrue(!mIsEncodingConfirmed);

        try
        {
            Encoding encoding = Encoding.getEncoding(encodingString);
            if (!hasMultiByteAsciiChar(encoding))
            {
                mEncoding = encoding;
                mIsEncodingConfirmed = true;
            }
        }
        catch (IllegalArgumentException e)
        {
            // Does not throw exception. Leaves old value of the encoding.
        }
    }

    /// <summary>
    /// <para>Using charset or encoding keywords, HtmlFormatDetector can refine encoding provided from outside,
    /// but only from one where ASCII Html keywords are single-byte to another.</para>
    /// <para>For example, from ASCII to UTF-8, but not from ASCII to UTF-16.</para>
    /// </summary>
    private static boolean hasMultiByteAsciiChar(Encoding encoding)
    {
        return
            encoding.equals(Encoding.getBigEndianUnicode()) ||
            encoding.equals(Encoding.getUnicode()) ||
            encoding.equals(Encoding.getEncoding(CodePage.CODE_PAGE_UTF_32_BE)) ||
            encoding.equals(Encoding.getUTF32()) ||
            encoding.equals(Encoding.getUTF7());
    }

    /// <summary>
    /// Whether Html format of the stream has been confirmed.
    /// </summary>
    private boolean isHtmlConfirmed() { return mHtmlConfidence >= 3; }

    /// <summary>
    /// Whether a decision is ready: Html or not Html.
    /// </summary>
    private boolean hasDecision() { return isHtmlConfirmed() || (mHtmlConfidence < 0); }

    private static class StringPair
    {
        String Key = "";
        String Value = "";
    }

    /// <summary>
    /// Matches last characters read from a stream against a text pattern using ordinal case-sensitive comparison.
    /// </summary>
    private static class LastTextMatcher
    {
        LastTextMatcher(String textToMatch)
        {
            Debug.assertTrue(StringUtil.hasChars(textToMatch));
            mText = textToMatch;
            mLastChars = new char[textToMatch.length()];
        }

        boolean matchesAfterReadingChar(char c)
        {
            // Last read characters are stored in a fixed-size array that is used as a cycle list.
            mLastChars[mPosition] = c;
            mPosition = (mPosition + 1) % mLastChars.length;
            ++mLength;

            if (mLength < mText.length())
            {
                return false;
            }

            mLength = mText.length();
            // Compare last read characters with the text to find.
            for (int i = 0; i < mText.length(); i++)
            {
                if (mText.charAt(i) != mLastChars[(mPosition + i) % mText.length()])
                {
                    return false;
                }
            }
            return true;
        }

        private /*final*/ String mText;
        private /*final*/ char[] mLastChars;
        private int mLength;
        private int mPosition;
    }

    /// <summary>
    /// It's to extract "test/html" from "text/html; charset=gb2312" string.
    /// </summary>
    private static /*final*/ Regex G_CONTENT_TYPET_DETECTOR = new Regex("^['\"]?([^'\"; ]*)",
        RegexOptions.IGNORE_CASE | RegexOptions.COMPILED | RegexOptions.CULTURE_INVARIANT);

    /// <summary>
    /// It's to extract "gb2312" from "text/html; charset=gb2312" string.
    /// </summary>
    private static /*final*/ Regex G_CHARSET_DETECTOR = new Regex("charset=([^'\"; ]*)",
        RegexOptions.IGNORE_CASE | RegexOptions.COMPILED | RegexOptions.CULTURE_INVARIANT);

    private /*final*/ CustomTextReader mTextReader;

    /// <summary>
    /// Common StringBuilder for several methods of the class.
    /// Don't want to create another instance for each local task.
    /// </summary>
    private /*final*/ StringBuilder mStringBuilder = new StringBuilder();

    /// <summary>
    /// <para>The larger this value the more confidence that the stream is Html.</para>
    /// <para>For a rule of the final decision, see <see cref="IsHtmlConfirmed"/>.</para>
    /// </summary>
    private int mHtmlConfidence;

    private Encoding mEncoding;
    private boolean mIsEncodingConfirmed;

    /// <summary>
    /// Single-use tags. Each of these tags must be used no more than once in a well-formed HTML document.
    /// </summary>
    /// <remarks>
    /// Documents that contain more than one such tag are most likely not HTML documents.
    /// </remarks>
    private /*final*/ HashSetGeneric<String> mSingleUseTags;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"?xml",
		"!doctype",
		"html",
		"head",
		"title",
		"meta",
		"/head",
		"body",
		"script",
		"!--",
		"style",
		"",
		"table",
		"tr",
		"td",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"div",
		"p",
		"img",
		"ol",
		"ul"
	);

}

