// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/04/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.UnicodeUtil;


/// <summary>
/// Parses HTML text into tokens according to HTML 5 rules.
/// </summary>
/// <remarks>
/// This class implements the tokenizer described here:
/// http://www.w3.org/TR/html5/syntax.html#tokenization
/// </remarks>
class HtmlTokenizer
{
    /// <summary>
    /// Creates and initializes a new instance of the class.
    /// </summary>
    /// <param name="html">HTML text that will be parsed into tokens.</param>
    /// <param name="supportedFeatures">
    /// Features that are considered supported when reading IE conditional comments.
    /// Setting this parameter to <c>null</c> disables support for conditional comments, like in modern browers.
    /// </param>
    HtmlTokenizer(String html, Features supportedFeatures)
    {
        mStream = new HtmlStream(html);
        mState = ParseState.DATA;
        mSupportedFeatures = supportedFeatures;
    }

    /// <summary>
    /// Parses the HTML text and returns the next token.
    /// </summary>
    /// <param name="htmlContent">When this flag is <c>true</c>, CDATA sections are parsed as comments and are eventually
    /// ignored (parsing of HTML content). When this flag is <c>false</c>, CDATA sections are parsed as text (parsing of XML
    /// fragments: SVG and MathML).
    /// </param>
    /// <returns>
    /// The next parsed token. If the end of the HTML text is reached, an instance of <see cref="HtmlEndOfFileToken"/>
    /// is returned.
    /// </returns>
    HtmlToken nextToken(boolean htmlContent) throws Exception
    {
        Debug.assertTrue(mText.length() == 0);

        if (mEmittedToken == null)
        {
            parse(htmlContent);
        }

        // The parser does not stop on every character token. Instead it accumulates all the text characters and returns
        // only on markup tokens. Here we check if some characters should be emitted before the markup token. The token
        // itself will be stored and emitted on the next call to the method.
        if (mText.length() > 0)
        {
            String text = mText.toString();
            mText.setLength(0);
            return new HtmlTextToken(text);
        }
        Debug.assertTrue(mEmittedToken != null);

        // Switching parser to special states such as RCDATA, RAWTEXT, and others is only allowed
        // after a start tag has been emitted. Remember that a start tag token has just been emitted.
        HtmlTagToken tagToken = ms.as(mEmittedToken, HtmlTagToken.class);
        mJustEmittedStartTagName = ((tagToken != null) && tagToken.isStart())
            ? tagToken.getName()
            : "";

        // Emit the next token and 'forget' it, but do not 'forget' an EOF token 
        // to prevent reading beyond the end of file.
        HtmlToken result = mEmittedToken;
        if (mEmittedToken.getType() != HtmlTokenType.END_OF_FILE)
        {
            mEmittedToken = null;
        }
        return result;
    }

    /// <summary>
    /// Switches the tokenizer to the RCDATA state.
    /// </summary>
    /// <remarks>
    /// The state can be switched only just after a start tag has been returned by the tokenizer.
    /// </remarks>
    void switchToRcdataState()
    {
        switchToState(ParseState.RCDATA);
    }

    /// <summary>
    /// Switches the tokenizer to the PLAINTEXT state.
    /// </summary>
    /// <remarks>
    /// The state can be switched only just after a start tag has been returned by the tokenizer.
    /// </remarks>
    void switchToPlaintextState()
    {
        switchToState(ParseState.PLAINTEXT);
    }

    /// <summary>
    /// Switches the tokenizer to the RAWTEXT state.
    /// </summary>
    /// <remarks>
    /// The state can be switched only just after a start tag has been returned by the tokenizer.
    /// </remarks>
    void switchToRawtextState()
    {
        switchToState(ParseState.RAWTEXT);
    }

    /// <summary>
    /// Switches the tokenizer to the SCRIPT state.
    /// </summary>
    /// <remarks>
    /// The state can be switched only just after a start tag has been returned by the tokenizer.
    /// </remarks>
    void switchToScriptDataState()
    {
        switchToState(ParseState.SCRIPT_DATA);
    }

    /// <summary>
    /// States of the finite-state automaton used to parse core HTML markup.
    /// </summary>
    private /*enum*/ final class ParseState
    {
        private ParseState(){}
        
        public static final int AFTER_ATTRIBUTE_VALUE_QUOTED = 0;
        public static final int AFTER_ATTRIBUTE_NAME = 1;
        public static final int ATTRIBUTE_NAME = 2;
        public static final int ATTRIBUTE_VALUE_DOUBLE_QUOTED = 3;
        public static final int ATTRIBUTE_VALUE_UNQUOTED = 4;
        public static final int ATTRIBUTE_VALUE_SINGLE_QUOTED = 5;
        public static final int BEFORE_ATTRIBUTE_NAME = 6;
        public static final int BEFORE_ATTRIBUTE_VALUE = 7;
        public static final int COMMENT = 8;
        public static final int BOGUS_COMMENT = 9;
        public static final int UNMATCHED_REVEALED_CONDITIONAL_COMMENT = 10;
        public static final int CDATA_SECTION = 11;
        public static final int DATA = 12;
        public static final int DOCTYPE = 13;
        public static final int END_TAG_OPEN = 14;
        public static final int MARKUP_DECLARATION_OPEN = 15;
        public static final int TAG_NAME = 16;
        public static final int TAG_OPEN = 17;
        public static final int RCDATA = 18;
        public static final int RAWTEXT = 19;
        public static final int PLAINTEXT = 20;
        public static final int SCRIPT_DATA = 21;
        public static final int SELF_CLOSING_START_TAG = 22;

        public static final int length = 23;
    }

    /// <summary>
    /// States of the finite-state automaton used to parse DOCTYPE tokens.
    /// </summary>
    private /*enum*/ final class DoctypeParseState
    {
        private DoctypeParseState(){}
        
        public static final int BEFORE_NAME = 0;
        public static final int NAME = 1;
        public static final int AFTER_NAME = 2;
        public static final int AFTER_PUBLIC_IDENTIFIER = 3;
        public static final int AFTER_PUBLIC_KEYWORD = 4;
        public static final int AFTER_SYSTEM_IDENTIFIER = 5;
        public static final int AFTER_SYSTEM_KEYWORD = 6;
        public static final int BOGUS = 7;

        public static final int length = 8;
    }

    /// <summary>
    /// States of the finite-state automaton used to parse ID strings of DOCTYPE tokens.
    /// </summary>
    private /*enum*/ final class DoctypeIdParseState
    {
        private DoctypeIdParseState(){}
        
        public static final int INITIAL = 0;
        public static final int DOUBLE_QUOTED = 1;
        public static final int SINGLE_QUOTED = 2;

        public static final int length = 3;
    }

    /// <summary>
    /// States of the finite-state automaton used to parse text of SCRIPT areas.
    /// </summary>
    private /*enum*/ final class ScriptParseState
    {
        private ScriptParseState(){}
        
        public static final int DATA = 0;
        public static final int DOUBLE_ESCAPED = 1;
        public static final int DOUBLE_ESCAPED_DASH = 2;
        public static final int DOUBLE_ESCAPED_DASH_DASH = 3;
        public static final int DOUBLE_ESCAPED_LESS_THAN_SIGN = 4;
        public static final int DOUBLE_ESCAPE_END = 5;
        public static final int DOUBLE_ESCAPE_START = 6;
        public static final int END_TAG_NAME = 7;
        public static final int END_TAG_OPEN = 8;
        public static final int ESCAPED = 9;
        public static final int ESCAPED_DASH = 10;
        public static final int ESCAPED_DASH_DASH = 11;
        public static final int ESCAPED_LESS_THAN_SIGN = 12;
        public static final int ESCAPED_END_TAG_OPEN = 13;
        public static final int ESCAPED_END_TAG_NAME = 14;
        public static final int ESCAPE_START = 15;
        public static final int ESCAPE_START_DASH = 16;
        public static final int LESS_THAN_SIGN = 17;

        public static final int length = 18;
    }

    /// <summary>
    /// States of the finite-state automaton used to parse text of RCDATA and RAWTEXT areas.
    /// </summary>
    private /*enum*/ final class TextParseState
    {
        private TextParseState(){}
        
        public static final int TEXT = 0;
        public static final int LESS_THAN_SIGN = 1;
        public static final int END_TAG_OPEN = 2;
        public static final int END_TAG_NAME = 3;

        public static final int length = 4;
    }

    /// <summary>
    /// States of the finite-state automaton used to parse text of comments.
    /// </summary>
    private /*enum*/ final class CommentParseState
    {
        private CommentParseState(){}
        
        public static final int START = 0;
        public static final int START_DASH = 1;
        public static final int TEXT = 2;
        public static final int END = 3;
        public static final int END_DASH = 4;
        public static final int END_BANG = 5;

        public static final int length = 6;
    }

    /// <summary>
    /// A result of parsing and matching a conditional expression.
    /// </summary>
    private /*enum*/ final class ConditionalExpressionConsumeResult
    {
        private ConditionalExpressionConsumeResult(){}
        
        /// <summary>
        /// Not a conditional comment (no '[if...' part).
        /// </summary>
        public static final int NOT_FOUND = 0;
        /// <summary>
        /// A conditional comment with an invalid expression that we failed to parse.
        /// </summary>
        public static final int INVALID = 1;
        /// <summary>
        /// A conditional comment with an expression that doesn't match the set of features we support.
        /// </summary>
        public static final int NOT_MATCHED = 2;
        /// <summary>
        /// A conditional comment with an expression that matches the set of features we support.
        /// </summary>
        public static final int MATCHED = 3;

        public static final int length = 4;
    }

    /// <summary>
    /// Switches the tokenizer to the specified state.
    /// </summary>
    /// <remarks>
    /// The state can be switched only just after a start tag has been returned by the tokenizer.
    /// </remarks>
    private void switchToState(/*ParseState*/int state)
    {
        Debug.assertTrue(mJustEmittedStartTagName.length() > 0);
        mState = state;
    }

    /// <summary>
    /// Reads the HTML text until a token is parsed or the end of the text is reached.
    /// </summary>
    private void parse(boolean htmlContent) throws Exception
    {
        do
        {
            mStream.consumeChar();

            switch (mState)
            {
                case ParseState.DATA:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '&':
                            msStringBuilder.append(mText, consumeCharacterReference(false));
                            break;
                        case '<':
                            mState = ParseState.TAG_OPEN;
                            break;
                        case HtmlStream.EOF_CHAR:
                            emitEof();
                            return;
                        default:
                        {
                            // NULL characters (U+0000) are not replaced in text outside HTML markup.
                            mText.append(mStream.getCurrentChar());
                            break;
                        }
                    }
                    break;
                }
                case ParseState.TAG_OPEN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        createTag(true);
                        mTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        mState = ParseState.TAG_NAME;
                    }
                    else
                    {
                        switch (mStream.getCurrentChar())
                        {
                            case '!':
                                mState = ParseState.MARKUP_DECLARATION_OPEN;
                                break;
                            case '/':
                                mState = ParseState.END_TAG_OPEN;
                                break;
                            case '?':
                            {
                                mState = ParseState.BOGUS_COMMENT;
                                mStream.unconsumeChar();
                                break;
                            }
                            default:
                            {
                                mState = ParseState.DATA;
                                mText.append('<');
                                mStream.unconsumeChar();
                                break;
                            }
                        }
                    }
                    break;
                }
                case ParseState.MARKUP_DECLARATION_OPEN:
                {
                    HtmlStreamBookmark bookmark = mStream.setBookmark();
                    mStream.unconsumeChar();
                    if (tryConsumeOrdinal("--"))
                    {
                        if (tryConsumeConditionalExpression() == ConditionalExpressionConsumeResult.MATCHED)
                        {
                            emitToken(createComment(mStream.getBookmarkedText(bookmark)));
                            mState = ParseState.DATA;
                            return;
                        }
                        mState = ParseState.COMMENT;
                    }
                    else if (tryConsumeAsciiCaseInsensitive("DOCTYPE"))
                    {
                        mState = ParseState.DOCTYPE;
                    }
                    else if ((!htmlContent) && tryConsumeOrdinal("[CDATA["))
                    {
                        mState = ParseState.CDATA_SECTION;
                    }
                    else
                    {
                        switch (tryConsumeConditionalExpression())
                        {
                            case ConditionalExpressionConsumeResult.NOT_FOUND:
                                mState = ParseState.BOGUS_COMMENT;
                                break;
                            case ConditionalExpressionConsumeResult.INVALID:
                            case ConditionalExpressionConsumeResult.NOT_MATCHED:
                                // Unlike IE, Word hides revealed comments that contain invalid expressions. So do we.
                                mState = ParseState.UNMATCHED_REVEALED_CONDITIONAL_COMMENT;
                                break;
                            case ConditionalExpressionConsumeResult.MATCHED:
                                emitToken(createComment(mStream.getBookmarkedText(bookmark)));
                                mState = ParseState.DATA;
                                return;
                            default:
                                // Error. Unexpected result.
                                throw new IllegalStateException();
                        }
                    }
                    break;
                }
                case ParseState.COMMENT:
                {
                    emitToken(parseComment());
                    mState = ParseState.DATA;
                    return;
                }
                case ParseState.BOGUS_COMMENT:
                {
                    emitToken(parseBogusComment());
                    mState = ParseState.DATA;
                    return;
                }
                case ParseState.UNMATCHED_REVEALED_CONDITIONAL_COMMENT:
                {
                    emitToken(parseUnmatchedRevealedComment());
                    mState = ParseState.DATA;
                    return;
                }
                case ParseState.END_TAG_OPEN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        createTag(false);
                        mTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        mState = ParseState.TAG_NAME;
                    }
                    else
                    {
                        switch (mStream.getCurrentChar())
                        {
                            case '>':
                                mState = ParseState.DATA;
                                break;
                            case HtmlStream.EOF_CHAR:
                            {
                                mState = ParseState.DATA;
                                msStringBuilder.append(mText, "</");
                                mStream.unconsumeChar();
                                break;
                            }
                            default:
                            {
                                mState = ParseState.BOGUS_COMMENT;
                                mStream.unconsumeChar();
                                break;
                            }
                        }
                    }
                    break;
                }
                case ParseState.CDATA_SECTION:
                {
                    parseCData();
                    mState = ParseState.DATA;
                    break;
                }
                case ParseState.TAG_NAME:
                {
                    char lowerCaseChar = StringUtil.asciiLowerCase(mStream.getCurrentChar());
                    switch (lowerCaseChar)
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            mState = ParseState.BEFORE_ATTRIBUTE_NAME;
                            break;
                        case '/':
                            mState = ParseState.SELF_CLOSING_START_TAG;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                            mTagName.append(replaceNull(lowerCaseChar));
                            break;
                    }
                    break;
                }
                case ParseState.BEFORE_ATTRIBUTE_NAME:
                {
                    char lowerCaseChar = StringUtil.asciiLowerCase(mStream.getCurrentChar());
                    switch (lowerCaseChar)
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            break;
                        case '/':
                            mState = ParseState.SELF_CLOSING_START_TAG;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            createAttribute();
                            mAttributeName.append(replaceNull(lowerCaseChar));
                            mState = ParseState.ATTRIBUTE_NAME;
                            break;
                        }
                    }
                    break;
                }
                case ParseState.ATTRIBUTE_NAME:
                {
                    char lowerCaseChar = StringUtil.asciiLowerCase(mStream.getCurrentChar());
                    switch (lowerCaseChar)
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            mState = ParseState.AFTER_ATTRIBUTE_NAME;
                            break;
                        case '/':
                            mState = ParseState.SELF_CLOSING_START_TAG;
                            break;
                        case '=':
                            mState = ParseState.BEFORE_ATTRIBUTE_VALUE;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            mAttributeName.append(replaceNull(lowerCaseChar));
                            mState = ParseState.ATTRIBUTE_NAME;
                            break;
                        }
                    }
                    break;
                }
                case ParseState.AFTER_ATTRIBUTE_NAME:
                {
                    char lowerCaseChar = StringUtil.asciiLowerCase(mStream.getCurrentChar());
                    switch (lowerCaseChar)
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            break;
                        case '/':
                            mState = ParseState.SELF_CLOSING_START_TAG;
                            break;
                        case '=':
                            mState = ParseState.BEFORE_ATTRIBUTE_VALUE;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            createAttribute();
                            mAttributeName.append(replaceNull(lowerCaseChar));
                            mState = ParseState.ATTRIBUTE_NAME;
                            break;
                        }
                    }
                    break;
                }
                case ParseState.BEFORE_ATTRIBUTE_VALUE:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            break;
                        case '"':
                            mState = ParseState.ATTRIBUTE_VALUE_DOUBLE_QUOTED;
                            break;
                        case '&':
                        {
                            mState = ParseState.ATTRIBUTE_VALUE_UNQUOTED;
                            mStream.unconsumeChar();
                            break;
                        }
                        case '\'':
                            mState = ParseState.ATTRIBUTE_VALUE_SINGLE_QUOTED;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            mAttributeValue.append(replaceNull(mStream.getCurrentChar()));
                            mState = ParseState.ATTRIBUTE_VALUE_UNQUOTED;
                            break;
                        }
                    }
                    break;
                }
                case ParseState.ATTRIBUTE_VALUE_DOUBLE_QUOTED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '"':
                            mState = ParseState.AFTER_ATTRIBUTE_VALUE_QUOTED;
                            break;
                        case '&':
                            msStringBuilder.append(mAttributeValue, consumeCharacterReference(true));
                            break;
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                            mAttributeValue.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case ParseState.ATTRIBUTE_VALUE_SINGLE_QUOTED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '\'':
                            mState = ParseState.AFTER_ATTRIBUTE_VALUE_QUOTED;
                            break;
                        case '&':
                            msStringBuilder.append(mAttributeValue, consumeCharacterReference(true));
                            break;
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                            mAttributeValue.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case ParseState.ATTRIBUTE_VALUE_UNQUOTED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            mState = ParseState.BEFORE_ATTRIBUTE_NAME;
                            break;
                        case '&':
                            msStringBuilder.append(mAttributeValue, consumeCharacterReference(true));
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                            mAttributeValue.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case ParseState.AFTER_ATTRIBUTE_VALUE_QUOTED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '\t':
                        case '\n':
                        case '\f':
                        case ' ':
                            mState = ParseState.BEFORE_ATTRIBUTE_NAME;
                            break;
                        case '/':
                            mState = ParseState.SELF_CLOSING_START_TAG;
                            break;
                        case '>':
                        {
                            mState = ParseState.DATA;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            mState = ParseState.BEFORE_ATTRIBUTE_NAME;
                            mStream.unconsumeChar();
                            break;
                        }
                    }
                    break;
                }
                case ParseState.SELF_CLOSING_START_TAG:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '>':
                        {
                            mState = ParseState.DATA;
                            mTagIsSelfClosing = true;
                            emitTag();
                            return;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mState = ParseState.DATA;
                            mStream.unconsumeChar();
                            break;
                        }
                        default:
                        {
                            mState = ParseState.BEFORE_ATTRIBUTE_NAME;
                            mStream.unconsumeChar();
                            break;
                        }
                    }
                    break;
                }
                case ParseState.DOCTYPE:
                {
                    emitToken(parseDoctype());
                    mState = ParseState.DATA;
                    return;
                }
                case ParseState.RCDATA:
                {
                    parseTextArea(true);
                    mState = ParseState.DATA;
                    break;
                }
                case ParseState.PLAINTEXT:
                {
                    while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR)
                    {
                        mText.append(replaceNull(mStream.getCurrentChar()));
                        mStream.consumeChar();
                    }
                    emitEof();
                    return;
                }
                case ParseState.RAWTEXT:
                {
                    parseTextArea(false);
                    mState = ParseState.DATA;
                    break;
                }
                case ParseState.SCRIPT_DATA:
                {
                    parseScript();
                    mState = ParseState.DATA;
                    break;
                }
                default:
                    // SQ fix: nothing to do.
                    break;
            }
        } while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR);
    }

    /// <summary>
    /// Parses a DOCTYPE token.
    /// </summary>
    /// <returns>
    /// The parsed DOCTYPE token, which is never <c>null</c>.
    /// </returns>
    private HtmlDoctypeToken parseDoctype()
    {
        StringBuilder name = new StringBuilder();
        String publicId = null;
        String systemId = null;
        /*DoctypeParseState*/int state = DoctypeParseState.BEFORE_NAME;

        while ((mStream.getCurrentChar() != HtmlStream.EOF_CHAR) && (mStream.getCurrentChar() != '>'))
        {
            switch (state)
            {
                case DoctypeParseState.BEFORE_NAME:
                {
                    if (!isWhitespace(mStream.getCurrentChar()))
                    {
                        name.append(replaceNull(StringUtil.asciiLowerCase(mStream.getCurrentChar())));
                        state = DoctypeParseState.NAME;
                    }
                    break;
                }
                case DoctypeParseState.NAME:
                {
                    if (isWhitespace(mStream.getCurrentChar()))
                    {
                        state = DoctypeParseState.AFTER_NAME;
                    }
                    else
                    {
                        name.append(replaceNull(StringUtil.asciiLowerCase(mStream.getCurrentChar())));
                    }
                    break;
                }
                case DoctypeParseState.AFTER_NAME:
                {
                    if (!isWhitespace(mStream.getCurrentChar()))
                    {
                        mStream.unconsumeChar();
                        if (tryConsumeAsciiCaseInsensitive("PUBLIC"))
                        {
                            state = DoctypeParseState.AFTER_PUBLIC_KEYWORD;
                        }
                        else if (tryConsumeAsciiCaseInsensitive("SYSTEM"))
                        {
                            state = DoctypeParseState.AFTER_SYSTEM_KEYWORD;
                        }
                        else
                        {
                            state = DoctypeParseState.BOGUS;
                        }
                    }
                    break;
                }
                case DoctypeParseState.AFTER_PUBLIC_KEYWORD:
                {
                    HtmlParsedDoctypeId parsedId = parseDoctypeId();
                    publicId = parsedId.getValue();
                    // There must be a public ID after the PUBLIC keyword.
                    state = ((parsedId.getValue() != null) && parsedId.getCorrect())
                        ? DoctypeParseState.AFTER_PUBLIC_IDENTIFIER
                        : DoctypeParseState.BOGUS;
                    break;
                }
                case DoctypeParseState.AFTER_PUBLIC_IDENTIFIER:
                {
                    HtmlParsedDoctypeId parsedId = parseDoctypeId();
                    systemId = parsedId.getValue();
                    // There may be no system ID after a public ID.
                    state = parsedId.getCorrect()
                        ? DoctypeParseState.AFTER_SYSTEM_IDENTIFIER
                        : DoctypeParseState.BOGUS;
                    break;
                }
                case DoctypeParseState.AFTER_SYSTEM_KEYWORD:
                {
                    HtmlParsedDoctypeId parsedId = parseDoctypeId();
                    systemId = parsedId.getValue();
                    // There must be a system ID after the PUBLIC keyword.
                    state = ((parsedId.getValue() != null) && parsedId.getCorrect())
                        ? DoctypeParseState.AFTER_SYSTEM_IDENTIFIER
                        : DoctypeParseState.BOGUS;
                    break;
                }
                case DoctypeParseState.AFTER_SYSTEM_IDENTIFIER:
                case DoctypeParseState.BOGUS:
                    // These states do nothing but waiting for the DOCTYPE tag to be closed, and waiting is performed
                    // by the outer loop.
                    break;
                default:
                    // SQ fix: nothing to do.
                    break;
            }

            // Consume next character.
            mStream.consumeChar();
        }

        if (mStream.getCurrentChar() == HtmlStream.EOF_CHAR)
        {
            // End of HTML stream is reached before a DOCTYPE tag is ended.
            mStream.unconsumeChar();
            String name1 = name.toString();
            return new HtmlDoctypeToken(name1, publicId, systemId, true);
        }
        else
        {
            // The DOCTYPE tag is ended (the '>' character has been consumed).
            boolean tagClosedPrematurely = (state != DoctypeParseState.NAME) &&
                                        (state != DoctypeParseState.AFTER_NAME) &&
                                        (state != DoctypeParseState.AFTER_PUBLIC_IDENTIFIER) &&
                                        (state != DoctypeParseState.AFTER_SYSTEM_IDENTIFIER);
            String name1 = name.toString();
            return new HtmlDoctypeToken(name1, publicId, systemId, tagClosedPrematurely);
        }
    }

    /// <summary>
    /// Parses either public or system ID of a DOCTYPE token.
    /// </summary>
    /// <returns>
    /// Both the parsed ID string (can be <c>null</c>) and a value indicating whether any parsing errors occurred.
    /// </returns>
    private HtmlParsedDoctypeId parseDoctypeId()
    {
        StringBuilder id = null;
        /*DoctypeIdParseState*/int state = DoctypeIdParseState.INITIAL;

        while ((mStream.getCurrentChar() != HtmlStream.EOF_CHAR) && (mStream.getCurrentChar() != '>'))
        {
            switch (state)
            {
                case DoctypeIdParseState.INITIAL:
                {
                    if (!isWhitespace(mStream.getCurrentChar()))
                    {
                        switch (mStream.getCurrentChar())
                        {
                            case '"':
                            {
                                id = new StringBuilder();
                                state = DoctypeIdParseState.DOUBLE_QUOTED;
                                break;
                            }
                            case '\'':
                            {
                                id = new StringBuilder();
                                state = DoctypeIdParseState.SINGLE_QUOTED;
                                break;
                            }
                            default:
                                mStream.unconsumeChar();
                                return new HtmlParsedDoctypeId(null, false);
                        }
                    }
                    break;
                }
                case DoctypeIdParseState.DOUBLE_QUOTED:
                {
                    if (mStream.getCurrentChar() == '"')
                    {
                        return new HtmlParsedDoctypeId(id.toString(), true);
                    }
                    else
                    {
                        id.append(replaceNull(mStream.getCurrentChar()));
                    }
                    break;
                }
                case DoctypeIdParseState.SINGLE_QUOTED:
                {
                    if (mStream.getCurrentChar() == '\'')
                    {
                        return new HtmlParsedDoctypeId(id.toString(), true);
                    }
                    else
                    {
                        id.append(replaceNull(mStream.getCurrentChar()));
                    }
                    break;
                }
                default:
                    // SQ fix: nothing to do.
                    break;
            }

            mStream.consumeChar();
        }

        mStream.unconsumeChar();
        String idString = (id != null)
            ? id.toString()
            : null;
        boolean endOfIdIsExpected = state == DoctypeIdParseState.INITIAL;
        return new HtmlParsedDoctypeId(idString, endOfIdIsExpected);
    }

    /// <summary>
    /// Parses text of a SCRIPT area. Stops just before the end tag of the area.
    /// </summary>
    private void parseScript()
    {
        /*ScriptParseState*/int state = ScriptParseState.DATA;

        // The following two buffers can contain letters only. NULL characters (U+0000) never appear in them.
        StringBuilder endTagName = new StringBuilder();
        StringBuilder temporaryBuffer = new StringBuilder();

        HtmlStreamBookmark mostRecentLessThanSignBookmark = null;

        while ((mStream.getCurrentChar() != HtmlStream.EOF_CHAR) || (state != ScriptParseState.DATA))
        {
            // Remember the position just before the most recent less-than sign.
            // If the less-than sign is the first character of an end tag that closes the SCRIPT area,
            // we will return to the remembered position to reparse the end tag.
            if (mStream.getCurrentChar() == '<')
            {
                mStream.unconsumeChar();
                mostRecentLessThanSignBookmark = mStream.setBookmark();
                mStream.consumeChar();
            }

            switch (state)
            {
                case ScriptParseState.DATA:
                {
                    if (mStream.getCurrentChar() == '<')
                    {
                        state = ScriptParseState.LESS_THAN_SIGN;
                    }
                    else
                    {
                        mText.append(replaceNull(mStream.getCurrentChar()));
                    }
                    break;
                }
                case ScriptParseState.LESS_THAN_SIGN:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '/':
                        {
                            temporaryBuffer.setLength(0);
                            state = ScriptParseState.END_TAG_OPEN;
                            break;
                        }
                        case '!':
                        {
                            state = ScriptParseState.ESCAPE_START;
                            msStringBuilder.append(mText, "<!");
                            break;
                        }
                        default:
                        {
                            state = ScriptParseState.DATA;
                            mText.append('<');
                            mStream.unconsumeChar();
                            break;
                        }
                    }
                    break;
                }
                case ScriptParseState.END_TAG_OPEN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        endTagName.setLength(0);
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.setLength(0);
                        temporaryBuffer.append(mStream.getCurrentChar());
                        state = ScriptParseState.END_TAG_NAME;
                    }
                    else
                    {
                        state = ScriptParseState.DATA;
                        msStringBuilder.append(mText, "</");
                        mStream.unconsumeChar();
                    }
                    break;
                }
                case ScriptParseState.END_TAG_NAME:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        // Tag name continues.
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.append(mStream.getCurrentChar());
                    }
                    else if (isValidAfterTagName(mStream.getCurrentChar()) && isAppropriateEndTag(endTagName.toString()))
                    {
                        // Tag name ended. This end tag closes the script area.
                        mStream.unconsumeToBookmark(mostRecentLessThanSignBookmark);
                        return;
                    }
                    else
                    {
                        // Tag name ended. This is not a valid end tag, or this end tag is valid, but it does not match
                        // the start tag of the script area.
                        msStringBuilder.append(msStringBuilder.append(mText, "</"), temporaryBuffer.toString());
                        temporaryBuffer.setLength(0);
                        endTagName.setLength(0);
                        mStream.unconsumeChar();
                        state = ScriptParseState.DATA;
                    }
                    break;
                }
                case ScriptParseState.ESCAPE_START:
                {
                    if (mStream.getCurrentChar() == '-')
                    {
                        state = ScriptParseState.ESCAPE_START_DASH;
                        mText.append('-');
                    }
                    else
                    {
                        state = ScriptParseState.DATA;
                        mStream.unconsumeChar();
                    }
                    break;
                }
                case ScriptParseState.ESCAPE_START_DASH:
                {
                    if (mStream.getCurrentChar() == '-')
                    {
                        mText.append('-');
                        state = ScriptParseState.ESCAPED_DASH_DASH;
                    }
                    else
                    {
                        mStream.unconsumeChar();
                        state = ScriptParseState.DATA;
                    }
                    break;
                }
                case ScriptParseState.ESCAPED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                        {
                            state = ScriptParseState.ESCAPED_DASH;
                            mText.append('-');
                            break;
                        }
                        case '<':
                            state = ScriptParseState.ESCAPED_LESS_THAN_SIGN;
                            break;
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case ScriptParseState.ESCAPED_DASH:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                        {
                            mText.append('-');
                            state = ScriptParseState.ESCAPED_DASH_DASH;
                            break;
                        }
                        case '<':
                            state = ScriptParseState.ESCAPED_LESS_THAN_SIGN;
                            break;
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                        {
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            state = ScriptParseState.ESCAPED;
                            break;
                        }
                    }
                    break;
                }
                case ScriptParseState.ESCAPED_DASH_DASH:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                            mText.append('-');
                            break;
                        case '<':
                            state = ScriptParseState.ESCAPED_LESS_THAN_SIGN;
                            break;
                        case '>':
                        {
                            state = ScriptParseState.DATA;
                            mText.append('>');
                            break;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                        {
                            state = ScriptParseState.ESCAPED;
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            break;
                        }
                    }
                    break;
                }
                case ScriptParseState.ESCAPED_LESS_THAN_SIGN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        temporaryBuffer.setLength(0);
                        temporaryBuffer.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        mText.append('<').append(mStream.getCurrentChar());
                        state = ScriptParseState.DOUBLE_ESCAPE_START;
                    }
                    else
                    {
                        if (mStream.getCurrentChar() == '/')
                        {
                            temporaryBuffer.setLength(0);
                            state = ScriptParseState.ESCAPED_END_TAG_OPEN;
                        }
                        else
                        {
                            mText.append('<');
                            mStream.unconsumeChar();
                            state = ScriptParseState.ESCAPED;
                        }
                    }
                    break;
                }
                case ScriptParseState.ESCAPED_END_TAG_OPEN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        createTag(false);
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.append(mStream.getCurrentChar());
                        state = ScriptParseState.ESCAPED_END_TAG_NAME;
                    }
                    else
                    {
                        msStringBuilder.append(mText, "</");
                        mStream.unconsumeChar();
                        state = ScriptParseState.ESCAPED;
                    }
                    break;
                }
                case ScriptParseState.ESCAPED_END_TAG_NAME:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.append(mStream.getCurrentChar());
                    }
                    else if (isValidAfterTagName(mStream.getCurrentChar()) && isAppropriateEndTag(endTagName.toString()))
                    {
                        mStream.unconsumeToBookmark(mostRecentLessThanSignBookmark);
                        return;
                    }
                    else
                    {
                        msStringBuilder.append(msStringBuilder.append(mText, "</"), temporaryBuffer.toString());
                        temporaryBuffer.setLength(0);
                        endTagName.setLength(0);
                        mStream.unconsumeChar();
                        state = ScriptParseState.ESCAPED;
                    }
                    break;
                }
                case ScriptParseState.DOUBLE_ESCAPE_START:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        temporaryBuffer.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        mText.append(mStream.getCurrentChar());
                    }
                    else if (isValidAfterTagName(mStream.getCurrentChar()))
                    {
                        mText.append(mStream.getCurrentChar());
                        state = ("script".equals(temporaryBuffer.toString()))
                            ? ScriptParseState.DOUBLE_ESCAPED
                            : ScriptParseState.ESCAPED;
                    }
                    else
                    {
                        mStream.unconsumeChar();
                        state = ScriptParseState.ESCAPED;
                    }
                    break;
                }
                case ScriptParseState.DOUBLE_ESCAPED:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                        {
                            mText.append('-');
                            state = ScriptParseState.DOUBLE_ESCAPED_DASH;
                            break;
                        }
                        case '<':
                        {
                            mText.append('<');
                            state = ScriptParseState.DOUBLE_ESCAPED_LESS_THAN_SIGN;
                            break;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case ScriptParseState.DOUBLE_ESCAPED_DASH:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                        {
                            mText.append('-');
                            state = ScriptParseState.DOUBLE_ESCAPED_DASH_DASH;
                            break;
                        }
                        case '<':
                        {
                            mText.append('<');
                            state = ScriptParseState.DOUBLE_ESCAPED_LESS_THAN_SIGN;
                            break;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                        {
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            state = ScriptParseState.DOUBLE_ESCAPED;
                            break;
                        }
                    }
                    break;
                }
                case ScriptParseState.DOUBLE_ESCAPED_DASH_DASH:
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                            mText.append('-');
                            break;
                        case '<':
                        {
                            mText.append('<');
                            state = ScriptParseState.DOUBLE_ESCAPED_LESS_THAN_SIGN;
                            break;
                        }
                        case '>':
                        {
                            mText.append('>');
                            state = ScriptParseState.DATA;
                            break;
                        }
                        case HtmlStream.EOF_CHAR:
                        {
                            mStream.unconsumeChar();
                            state = ScriptParseState.DATA;
                            break;
                        }
                        default:
                        {
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            state = ScriptParseState.DOUBLE_ESCAPED;
                            break;
                        }
                    }
                    break;
                case ScriptParseState.DOUBLE_ESCAPED_LESS_THAN_SIGN:
                {
                    if (mStream.getCurrentChar() == '/')
                    {
                        temporaryBuffer.setLength(0);
                        mText.append('/');
                        state = ScriptParseState.DOUBLE_ESCAPE_END;
                    }
                    else
                    {
                        mStream.unconsumeChar();
                        state = ScriptParseState.DOUBLE_ESCAPED;
                    }
                    break;
                }
                case ScriptParseState.DOUBLE_ESCAPE_END:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        temporaryBuffer.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        mText.append(mStream.getCurrentChar());
                    }
                    else if (isValidAfterTagName(mStream.getCurrentChar()))
                    {
                        mText.append(mStream.getCurrentChar());
                        state = ("script".equals(temporaryBuffer.toString()))
                            ? ScriptParseState.ESCAPED
                            : ScriptParseState.DOUBLE_ESCAPED;
                    }
                    else
                    {
                        mStream.unconsumeChar();
                        state = ScriptParseState.DOUBLE_ESCAPED;
                    }
                    break;
                }
                default:
                    // SQ fix: nothing to do.
                    break;
            }

            mStream.consumeChar();
        }

        // Unconsume the end-of-file character.
        mStream.unconsumeChar();
    }

    /// <summary>
    /// Parses either RAWTEXT or RCDATA text area. Returns just before the end tag that closes the area.
    /// </summary>
    /// <param name="resolveCharacterReferences">
    /// A flag indicating whether named and numeric character references should be replaced with corresponding Unicode
    /// characters.
    /// </param>
    private void parseTextArea(boolean resolveCharacterReferences)
    {
        /*TextParseState*/int state = TextParseState.TEXT;

        // The following two buffers can contain letters only. NULL characters (U+0000) never appear in them.
        StringBuilder endTagName = new StringBuilder();
        StringBuilder temporaryBuffer = new StringBuilder();

        HtmlStreamBookmark mostRecentLessThanSignBookmark = null;

        while ((mStream.getCurrentChar() != HtmlStream.EOF_CHAR) || (state != TextParseState.TEXT))
        {
            // Remember the position just before the most recent less-than sign.
            // If the less-than sign is the first character of an end tag that closes the text area,
            // we will return to the remembered position to reparse the end tag.
            if (mStream.getCurrentChar() == '<')
            {
                mStream.unconsumeChar();
                mostRecentLessThanSignBookmark = mStream.setBookmark();
                mStream.consumeChar();
            }

            switch (state)
            {
                case TextParseState.TEXT:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '&':
                            if (resolveCharacterReferences)
                            {
                                msStringBuilder.append(mText, consumeCharacterReference(false));
                            }
                            else
                            {
                                mText.append('&');
                            }
                            break;
                        case '<':
                            state = TextParseState.LESS_THAN_SIGN;
                            break;
                        default:
                            mText.append(replaceNull(mStream.getCurrentChar()));
                            break;
                    }
                    break;
                }
                case TextParseState.LESS_THAN_SIGN:
                {
                    if (mStream.getCurrentChar() == '/')
                    {
                        temporaryBuffer.setLength(0);
                        state = TextParseState.END_TAG_OPEN;
                    }
                    else
                    {
                        mText.append('<');
                        mStream.unconsumeChar();
                        state = TextParseState.TEXT;
                    }
                    break;
                }
                case TextParseState.END_TAG_OPEN:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        endTagName.setLength(0);
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.append(mStream.getCurrentChar());
                        state = TextParseState.END_TAG_NAME;
                    }
                    else
                    {
                        msStringBuilder.append(mText, "</");
                        mStream.unconsumeChar();
                        state = TextParseState.TEXT;
                    }
                    break;
                }
                case TextParseState.END_TAG_NAME:
                {
                    if (StringUtil.isLetter(mStream.getCurrentChar()))
                    {
                        endTagName.append(StringUtil.asciiLowerCase(mStream.getCurrentChar()));
                        temporaryBuffer.append(mStream.getCurrentChar());
                    }
                    else if (isValidAfterTagName(mStream.getCurrentChar()) && isAppropriateEndTag(endTagName.toString()))
                    {
                        mStream.unconsumeToBookmark(mostRecentLessThanSignBookmark);
                        return;
                    }
                    else
                    {
                        msStringBuilder.append(msStringBuilder.append(mText, "</"), temporaryBuffer.toString());
                        temporaryBuffer.setLength(0);
                        endTagName.setLength(0);
                        mStream.unconsumeChar();
                        state = TextParseState.TEXT;
                    }
                    break;
                }
                default:
                    // SQ fix: nothing to do.
                    break;
            }

            mStream.consumeChar();
        }

        // Unconsume the end-of-file character.
        mStream.unconsumeChar();
    }

    /// <summary>
    /// Parses a CDATA text section. Returns after the "]]&gt;" character sequence is read.
    /// </summary>
    private void parseCData()
    {
        while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR)
        {
            if ((mStream.getCurrentChar() == ']') && tryConsumeOrdinal("]>"))
            {
                // The end character sequence is read. Parsing of CDATA is finished.
                break;
            }

            // NULL characters (U+0000) are not replaced in CDATA sections.
            mText.append(mStream.getCurrentChar());

            mStream.consumeChar();
        }

        // The end-of-file character will be reprocessed at a higher level.
        if (mStream.getCurrentChar() == HtmlStream.EOF_CHAR)
        {
            mStream.unconsumeChar();
        }
    }

    /// <summary>
    /// Parses an HTML comment. Returns after the "--!>" sequence is parsed.
    /// </summary>
    /// <returns>
    /// The parsed comment token.
    /// </returns>
    /// <remarks>
    /// This method should be called after the initial "&lt;!--" sequence is read.
    /// </remarks>
    private HtmlCommentToken parseComment()
    {
        StringBuilder commentText = new StringBuilder();
        /*CommentParseState*/int state = CommentParseState.START;

        while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR)
        {
            switch (state)
            {
                case CommentParseState.START:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                            state = CommentParseState.START_DASH;
                            break;
                        case '>':
                            return new HtmlCommentToken(commentText.toString());
                        default:
                        {
                            commentText.append(replaceNull(mStream.getCurrentChar()));
                            state = CommentParseState.TEXT;
                            break;
                        }
                    }
                    break;
                }
                case CommentParseState.START_DASH:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                            state = CommentParseState.END;
                            break;
                        case '>':
                            return new HtmlCommentToken(commentText.toString());
                        default:
                        {
                            commentText.append('-').append(replaceNull(mStream.getCurrentChar()));
                            state = CommentParseState.TEXT;
                            break;
                        }
                    }
                    break;
                }
                case CommentParseState.TEXT:
                {
                    if (mStream.getCurrentChar() == '-')
                    {
                        state = CommentParseState.END_DASH;
                    }
                    else
                    {
                        commentText.append(replaceNull(mStream.getCurrentChar()));
                    }
                    break;
                }
                case CommentParseState.END_DASH:
                {
                    if (mStream.getCurrentChar() == '-')
                    {
                        state = CommentParseState.END;
                    }
                    else
                    {
                        commentText.append('-').append(replaceNull(mStream.getCurrentChar()));
                        state = CommentParseState.TEXT;
                    }
                    break;
                }
                case CommentParseState.END:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '>':
                            return new HtmlCommentToken(commentText.toString());
                        case '!':
                            state = CommentParseState.END_BANG;
                            break;
                        case '-':
                            commentText.append('-');
                            break;
                        default:
                        {
                            msStringBuilder.append(commentText, "--").append(replaceNull(mStream.getCurrentChar()));
                            state = CommentParseState.TEXT;
                            break;
                        }
                    }
                    break;
                }
                case CommentParseState.END_BANG:
                {
                    switch (mStream.getCurrentChar())
                    {
                        case '-':
                        {
                            msStringBuilder.append(commentText, "--!");
                            state = CommentParseState.END_DASH;
                            break;
                        }
                        case '>':
                            return new HtmlCommentToken(commentText.toString());
                        default:
                        {
                            msStringBuilder.append(commentText, "--!").append(replaceNull(mStream.getCurrentChar()));
                            state = CommentParseState.TEXT;
                            break;
                        }
                    }
                    break;
                }
                default:
                    // SQ fix: nothing to do.
                    break;
            }

            mStream.consumeChar();
        }

        // Unconsume the end-of-file character.
        mStream.unconsumeChar();
        return new HtmlCommentToken(commentText.toString());
    }

    /// <summary>
    /// Parses a bogus HTML comment.
    /// </summary>
    /// <returns>
    /// The parsed comment token.
    /// </returns>
    /// <remarks>
    /// This method should be called after the sequence that starts the comment is read.
    /// </remarks>
    private HtmlCommentToken parseBogusComment()
    {
        HtmlStreamBookmark bookmark = mStream.setBookmark();
        while ((mStream.getCurrentChar() != '>') && (mStream.getCurrentChar() != HtmlStream.EOF_CHAR))
        {
            mStream.consumeChar();
        }
        String commentText = mStream.getBookmarkedText(bookmark);

        // These characters will be reprocessed at a higher level.
        if ((mStream.getCurrentChar() == HtmlStream.EOF_CHAR) || (mStream.getCurrentChar() == '<'))
        {
            mStream.unconsumeChar();
        }

        return createComment(commentText);
    }

    /// <summary>
    /// Parses a revealed conditional comment as a normal comment.
    /// </summary>
    /// <remarks>
    /// If a revealed conditinal comment doesn't match the set of features we support, we must parse it as a normal comment
    /// and, as a result, hide its contents.
    /// </remarks>
    private HtmlCommentToken parseUnmatchedRevealedComment()
    {
        HtmlStreamBookmark bookmark = mStream.setBookmark();
        // Seek to the nearest '<![endif]>' tag.
        while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR)
        {
            if (tryConsumeRevealedCommentEndTag())
            {
                break;
            }
            mStream.consumeChar();
        }
        String commentText = mStream.getBookmarkedText(bookmark);

        // The end-of-file character will be reprocessed at higher level.
        if (mStream.getCurrentChar() == HtmlStream.EOF_CHAR)
        {
            mStream.unconsumeChar();
        }

        return createComment(commentText);
    }

    /// <summary>
    /// Consumes next characters if they constitute a valid end tag of a revealed conditional comment (&lt;![endif]&gt;).
    /// </summary>
    private boolean tryConsumeRevealedCommentEndTag()
    {
        if (!tryConsumeAsciiCaseInsensitive("<![endif]"))
        {
            return false;
        }

        mStream.consumeChar();
        return mStream.getCurrentChar() == '>';
    }

    /// <summary>
    /// Consume next characters if they are equal to the specified string. Ordinal case-sensitive comparison is used.
    /// </summary>
    /// <param name="str">The string that will be consumed in the case of a match.</param>
    /// <returns><c>true</c> if the string has been consumed; <c>false</c> otherwise.</returns>
    private boolean tryConsumeOrdinal(String str)
    {
        if (mStream.nextStringEquals(str, false))
        {
            mStream.consumeChar(str.length());
            return true;
        }
        return false;
    }

    /// <summary>
    /// Consume next characters if they are equal to the specified string. ASCII case-insensitive comparison is used.
    /// </summary>
    /// <param name="str">The string that will be consumed in the case of a match.</param>
    /// <returns><c>true</c> if the string has been consumed; <c>false</c> otherwise.</returns>
    private boolean tryConsumeAsciiCaseInsensitive(String str)
    {
        if (mStream.nextStringEquals(str, true))
        {
            mStream.consumeChar(str.length());
            return true;
        }
        return false;
    }

    /// <summary>
    /// Tries to parse an expression of a conditional comment and to match it against the set of features we support.
    /// </summary>
    private /*ConditionalExpressionConsumeResult*/int tryConsumeConditionalExpression() throws Exception
    {
        // The fact that the list of supported features is null indicates that the calling code explicitly asks
        // the HTML parser not to support conditional comments. Note that no modern browsers support them.
        if (mSupportedFeatures == null)
        {
            return ConditionalExpressionConsumeResult.NOT_FOUND;
        }

        // Set a bookmark so that we can unconsume input in case the condition is not met.
        HtmlStreamBookmark expressionStart = mStream.setBookmark();

        /*ConditionalExpressionConsumeResult*/int consumeResult = ConditionalExpressionConsumeResult.NOT_FOUND;

        if (tryConsumeAsciiCaseInsensitive("[if"))
        {
            StringBuilder expressionText = new StringBuilder();

            mStream.consumeChar();
            while (mStream.getCurrentChar() != HtmlStream.EOF_CHAR)
            {
                // Conditional expressions end with ']>'
                if (mStream.getCurrentChar() == ']')
                {
                    mStream.consumeChar();
                    if (mStream.getCurrentChar() == '>')
                    {
                        consumeResult = ConditionalExpressionConsumeResult.INVALID;
                        break;
                    }
                    mStream.unconsumeChar();
                }

                expressionText.append(replaceNull(mStream.getCurrentChar()));
                mStream.consumeChar();
            }

            // The end-of-file character will be reprocessed at a higher level.
            if (mStream.getCurrentChar() == HtmlStream.EOF_CHAR)
            {
                mStream.unconsumeChar();
            }

            if (consumeResult != ConditionalExpressionConsumeResult.NOT_FOUND)
            {
                // It is a conditional comment. Let's try to parse and match its expression.
                ConditionalExpression expression = ConditionalExpression.parse(expressionText.toString());
                if (expression != null)
                {
                    // Ths expression is parsed sucessfully. Let's check whether available features match the expression.
                    consumeResult = expression.matches(mSupportedFeatures)
                        ? ConditionalExpressionConsumeResult.MATCHED
                        : ConditionalExpressionConsumeResult.NOT_MATCHED;
                }
            }
        }

        // It is not a conditional comment or its expression doesn't match the features. The text will be reprocessed.
        if (consumeResult != ConditionalExpressionConsumeResult.MATCHED)
        {
            mStream.unconsumeToBookmark(expressionStart);
        }

        return consumeResult;
    }

    /// <summary>
    /// Consumes a character reference (either numeric or named).
    /// </summary>
    /// <param name="isInsideAttribute">Indicates whether the currently parsed HTML part is an attribute value.</param>
    /// <returns>
    /// The referenced characters, if the reference has been parsed sucessfully;
    /// the ampersand character otherwise.
    /// </returns>
    private String consumeCharacterReference(boolean isInsideAttribute)
    {
        String characters;

        // Additional allowed characters, which the HTML 5 Specification mentions, are processed correctly
        // when a symbolic reference is parsed, because no reference name starts with any of the additional allowed
        // characters.
        switch (mStream.getNextChar())
        {
            case '\t':
            case '\n':
            case '\f':
            case ' ':
            case '<':
            case '&':
            case HtmlStream.EOF_CHAR:
                characters = "";
                break;
            case '#':
                characters = consumeNumericCharacterReference();
                break;
            default:
                characters = consumeSymbolicCharacterReference(isInsideAttribute);
                break;
        }

        return (characters.length() > 0)
            ? characters
            : "&";
    }

    /// <summary>
    /// Consumes a numeric character reference (either decimal or hexadecimal).
    /// </summary>
    /// <returns>
    /// The referenced characters, if the reference has been parsed sucessfully;
    /// an empty string otherwise.
    /// </returns>
    private String consumeNumericCharacterReference()
    {
        HtmlStreamBookmark positionBeforeReference = mStream.setBookmark();
        mStream.consumeChar(); // '#'

        int value = 0;
        int valueLength = 0;

        // Consume digits of the character reference.
        if ((mStream.getNextChar() == 'x') || (mStream.getNextChar() == 'X'))
        {
            mStream.consumeChar(); // 'x' or 'X'
            while (StringUtil.isHexDigit(mStream.getNextChar()))
            {
                ++valueLength;
                mStream.consumeChar();
                // If the value is already too big, stop parsing its digits to prevent overflow.
                // The value will not be used anyway.
                if (value <= 0x10FFFF)
                {
                    value <<= 4;
                    value += StringUtil.hexCharToDigit(mStream.getCurrentChar());
                }
            }
        }
        else
        {
            while (StringUtil.isDigit(mStream.getNextChar()))
            {
                ++valueLength;
                mStream.consumeChar();
                // If the value is already too big, stop parsing its digits to prevent overflow.
                // The value will not be used anyway.
                if (value <= 0x10FFFF)
                {
                    value *= 10;
                    value += mStream.getCurrentChar() - '0';
                }
            }
        }

        // Consume a semicolon, if there is any.
        if (mStream.getNextChar() == ';')
        {
            mStream.consumeChar();
        }

        if (valueLength == 0)
        {
            mStream.unconsumeToBookmark(positionBeforeReference);
            return "";
        }
        else
        {
            return HtmlCharacterReference.convert(value);
        }
    }

    /// <summary>
    /// Consumes a named character reference.
    /// </summary>
    /// <returns>
    /// The referenced characters, if the reference has been parsed sucessfully (name is known);
    /// an empty string otherwise.
    /// </returns>
    private String consumeSymbolicCharacterReference(boolean isInsideAttribute)
    {
        StringBuilder name = new StringBuilder();
        String longestNameCharacters = "";

        HtmlStreamBookmark positionBeforeReference = mStream.setBookmark();
        boolean entityNameEndsWithSemicolon = false;

        HtmlStreamBookmark positionAfterMatchedName = mStream.setBookmark();
        while (HtmlCharacterReference.longerNamedReferenceExists(name.toString()))
        {
            mStream.consumeChar();
            if (mStream.getCurrentChar() == HtmlStream.EOF_CHAR)
            {
                break;
            }
            name.append(mStream.getCurrentChar());
            String characters = HtmlCharacterReference.convert(name.toString());
            if (characters.length() > 0)
            {
                // Current name is known.
                longestNameCharacters = characters;
                entityNameEndsWithSemicolon = mStream.getCurrentChar() == ';';
                positionAfterMatchedName = mStream.setBookmark();
            }
        }

        mStream.unconsumeToBookmark(positionAfterMatchedName);

        // For historical reasons, there is a quirk for named character references in attribute values.
        if (isInsideAttribute && (!entityNameEndsWithSemicolon))
        {
            if (StringUtil.isLetterOrDigit(mStream.getNextChar()) || (mStream.getNextChar() == '='))
            {
                mStream.unconsumeToBookmark(positionBeforeReference);
                longestNameCharacters = "";
            }
        }

        return longestNameCharacters;
    }

    /// <summary>
    /// Initializes a new tag token.
    /// </summary>
    /// <param name="isStartTag">
    /// Whether the token will be a start tag (<c>true</c>) or an end tag (<c>false</c>).
    /// </param>
    private void createTag(boolean isStartTag)
    {
        mTagName.setLength(0);
        mTagIsStartTag = isStartTag;
        mTagIsSelfClosing = false;
        mAttributes = new HtmlAttributeCollection();

        Debug.assertTrue(mAttributeName.length() == 0);
        Debug.assertTrue(mAttributeValue.length() == 0);
    }

    /// <summary>
    /// Initializes a new attributes.
    /// </summary>
    private void createAttribute()
    {
        addCurrentAttributeToCollectionIfExists();

        mAttributeName.setLength(0);
        mAttributeValue.setLength(0);
    }

    /// <summary>
    /// If a current attributes exists, adds it to the collection of attributes.
    /// </summary>
    /// <remarks>
    /// A filled attribute is added to the current tag's collection of attributes only when another attribute starts,
    /// or when the current tag is emitted.
    /// </remarks>
    private void addCurrentAttributeToCollectionIfExists()
    {
        if (mAttributeName.length() > 0)
        {
            mAttributes.add(new HtmlAttribute(mAttributeName.toString(), mAttributeValue.toString()));
            mAttributeName.setLength(0);
            mAttributeValue.setLength(0);
        }
    }

    /// <summary>
    /// Creates a new comment token. Replaces null characters in the specified text.
    /// </summary>
    private static HtmlCommentToken createComment(String comment)
    {
        return new HtmlCommentToken(replaceNull(comment));
    }

    /// <summary>
    /// Emits the current tag token.
    /// </summary>
    private void emitTag()
    {
        addCurrentAttributeToCollectionIfExists();
        emitToken(new HtmlTagToken(mTagName.toString(), mTagIsStartTag, mTagIsSelfClosing, mAttributes));
    }

    /// <summary>
    /// Emits an end-of-file token.
    /// </summary>
    private void emitEof()
    {
        emitToken(new HtmlEndOfFileToken());
    }

    /// <summary>
    /// Emits the token.
    /// </summary>
    /// <param name="token">A token to be emitted.</param>
    private void emitToken(HtmlToken token)
    {
        Debug.assertTrue(token != null);
        Debug.assertTrue(mEmittedToken == null);

        mEmittedToken = token;
    }

    /// <summary>
    /// Checks whether the specified tag name matches the name of the last emitted start tag.
    /// </summary>
    private boolean isAppropriateEndTag(String tagName)
    {
        // Ordinal case-sensitive comparison. Tag names are lowercase anyway.
        return msString.equals(tagName, mJustEmittedStartTagName);
    }

    /// <summary>
    /// Replaces the null character U+0000 with the replacement character U+FFFD. Leaves other characters unchanged.
    /// </summary>
    private static char replaceNull(char c)
    {
        return (c == '\u0000')
            ? UnicodeUtil.REPLACEMENT_CHAR
            : c;
    }

    /// <summary>
    /// Replaces the null character U+0000 with the replacement character U+FFFD. Leaves other characters unchanged.
    /// </summary>
    private static String replaceNull(String s)
    {
        return msString.replace(s, '\u0000', UnicodeUtil.REPLACEMENT_CHAR);
    }

    /// <summary>
    /// Gets a value indicating whether the specified character is a whitespace character.
    /// </summary>
    /// <param name="c">A character.</param>
    /// <returns><c>true</c> if the specified character is a whitespace character. <c>false</c> otherwise.</returns>
    private static boolean isWhitespace(char c)
    {
        // The HTML whitespace characters are listed here: http://www.w3.org/TR/html5/infrastructure.html#space-character
        // The CR character is not included, because it must be removed from the HTML stream during the preprocessing phase.
        return (c == ' ') ||
               (c == '\t') ||
               (c == '\n') ||
               (c == '\f');
    }

    /// <summary>
    /// Gets a value indicating whether the specified character can follow a tag name.
    /// </summary>
    /// <param name="c">A character.</param>
    /// <returns><c>true</c> if the specified character can follow a tag name. <c>false</c> otherwise.</returns>
    private static boolean isValidAfterTagName(char c)
    {
        return isWhitespace(c) ||
               (c == '/') ||
               (c == '>');
    }

    /// <summary>
    /// The current state of the tokenizer.
    /// </summary>
    private /*ParseState*/int mState;

    /// <summary>
    /// The HTML text being parsed.
    /// </summary>
    private /*final*/ HtmlStream mStream;

    /// <summary>
    /// Features that are considered supported when loading IE conditional comments.
    /// </summary>
    private /*final*/ Features mSupportedFeatures;

    /// <summary>
    /// Contents of the current text token.
    /// </summary>
    private /*final*/ StringBuilder mText = new StringBuilder();

    /// <summary>
    /// The emitted token that waits while the caller processes a text token lazily emitted before it.
    /// </summary>
    private HtmlToken mEmittedToken;

    /// <summary>
    /// Name of the current tag token.
    /// </summary>
    private /*final*/ StringBuilder mTagName = new StringBuilder();

    /// <summary>
    /// Whether the current tag token is a start or an end tag.
    /// </summary>
    private boolean mTagIsStartTag;

    /// <summary>
    /// 'self-closing' flag of the current tag token.
    /// </summary>
    private boolean mTagIsSelfClosing;

    /// <summary>
    /// Name of the current attribute.
    /// </summary>
    private /*final*/ StringBuilder mAttributeName = new StringBuilder();

    /// <summary>
    /// Value of the current attribute.
    /// </summary>
    private /*final*/ StringBuilder mAttributeValue = new StringBuilder();

    /// <summary>
    /// Attributes of the current tag token.
    /// </summary>
    private HtmlAttributeCollection mAttributes;

    /// <summary>
    /// The name of a start tag that has just been emitted. If the last emitted token is not a start tag,
    /// this string is empty, and a tag name cannot be empty.
    /// </summary>
    private String mJustEmittedStartTagName = "";
}

