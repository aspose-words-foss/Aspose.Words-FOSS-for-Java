// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.IDisposable;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.FontUtil;


/// <summary>
/// Parses field code to switches and arguments.
/// </summary>
final class FieldCodeParser implements IDisposable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldCodeParser(Field field, IFieldCodeTokenInfoProvider tokenInfoProvider)
    {
    	this(new NodeRangeFieldCodeTokenizer(field, true), field.getUpdateContext(), tokenInfoProvider);
	
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    FieldCodeParser(String fieldCode, IFieldCodeTokenInfoProvider tokenInfoProvider)
    {
    	this(new TextFieldCodeTokenizer(fieldCode, true), null, tokenInfoProvider);
	
    }

    public void dispose()
    {
    	/*JAVA-added try/catch to wrap a checked exception into unchecked one*/
    	try
    	{
        	IDisposable disposable = ms.as(mTokenizer, IDisposable.class);
        	if (disposable != null)
            	disposable.dispose();
    	}
    	catch (Exception e)
    	{
    		throw new RuntimeException(e);
    	}
    }

    /// <summary>
    /// Ctor to use solely in <see cref="GetArgumentTextByRange"/>.
    /// </summary>
    private FieldCodeParser(FieldArgument argument)
    {
    	this(new NodeRangeFieldCodeTokenizer(argument, true));
	
        // If the argument refers to a standalone field, then its range corresponds to the field result.
        // So we need to set the parser's state as if it encountered a field separator at this point.
        if (argument.isSingleFieldResult())
        {
            mIsInChildFieldResult = true;
            mIsInToken = true;
        }
    }

    /// <summary>
    /// Ctor to use solely in <see cref="GetFieldType"/>.
    /// </summary>
    private FieldCodeParser(FieldStart fieldStart, FieldChar fieldCodeEnd)
    {
    	this(new NodeRangeFieldCodeTokenizer(fieldStart, fieldCodeEnd, true));
	
    }

    /// <summary>
    /// Ctor for helper routines. Makes language ids' calculation to be omitted.
    /// </summary>
    private FieldCodeParser(IFieldCodeTokenizer tokenizer)
    {
    	this(tokenizer, null, null);
	
        // Language ids are not used in helper routines, set them here to avoid their calculation.
        setLanguageId(RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);
        setLanguageIdFarEast(RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);
        setLanguageIdBi(RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    private FieldCodeParser(
        IFieldCodeTokenizer tokenizer,
        FieldUpdateContext updateContext,
        IFieldCodeTokenInfoProvider tokenInfoProvider)
    {
        setLanguageIdBi(LANGUAGE_NOT_SET);
        setLanguageIdFarEast(LANGUAGE_NOT_SET);
        setLanguageId(LANGUAGE_NOT_SET);
        mTokenizer = tokenizer;
        mUpdateContext = updateContext;
        mTokenInfoProvider = tokenInfoProvider;
    }

    /// <summary>
    /// Returns a string representation of the field type for a field which code is strictly contained
    /// between the specified nodes.
    /// </summary>
    static String getFieldType(FieldStart fieldStart, FieldChar fieldCodeEnd)
    {
        FieldCodeParser parser = new FieldCodeParser(fieldStart, fieldCodeEnd);
        try /*JAVA: was using*/
        {
            return parser.moveToNextToken(false, false)
                ? parser.getCurrentToken()
                : "";
        }
        finally { if (parser != null) parser.dispose(); }
    }

    private static boolean isVerbatimArgument(FieldArgument argument)
    {
        Field field = argument.getField();

        if (!field.hasFieldCodeCache())
            return false;

        int argumentIndex = field.getFieldCodeCache().getArguments().indexOf(argument);
        if (argumentIndex == -1)
            return false;

        return argumentIndex == FieldUtil.getVerbatimArgumentIndex(field.getType());
    }

    private static boolean isIgnoreSymbolicQuotesArgument(FieldArgument argument)
    {
        Field field = argument.getField();

        if (!field.hasFieldCodeCache())
            return false;

        int argumentIndex = field.getFieldCodeCache().getArguments().indexOf(argument);
        if (argumentIndex == -1)
            return false;

        return FieldUtil.ignoreSymbolicQuotes(field.getType(), argumentIndex);
    }

    /// <summary>
    /// Collects text of the specified field argument using its node range.
    /// </summary>
    static String getArgumentTextByRange(FieldArgument argument)
    {
        FieldCodeParser parser = new FieldCodeParser(argument);
        try /*JAVA: was using*/
        {
            // If the parser can not move, then the argument range is empty. Return an empty string in this case.
            if (!parser.moveToNextToken(isVerbatimArgument(argument), isIgnoreSymbolicQuotesArgument(argument)))
                return "";

            String argumentText = parser.getCurrentToken();

            // An argument range should be parsed as a single token. If this condition is not met, then the range
            // probably became invalid because of some node changes. Throw to indicate this.
            //
            // If you have encountered a scenario where this exception is thrown, then you should invalidate the whole
            // field code right after the argument range became invalid to avoid this.
            //
            if (parser.moveToNextToken(false, false))
                throw new IllegalStateException();

            return argumentText;
        }
        finally { if (parser != null) parser.dispose(); }
    }

    /// <summary>
    /// Collects rich text of the specified field argument using its node range.
    /// </summary>
    static RichString getArgumentRichTextByRange(FieldArgument argument)
    {
        FieldCodeParser parser = new FieldCodeParser(argument);
        try /*JAVA: was using*/
        {
            if (!parser.moveToNextToken(isVerbatimArgument(argument), isIgnoreSymbolicQuotesArgument(argument)))
                return RichString.EMPTY;

            RichString argumentRichText = parser.getCurrentRichToken();

            if (parser.moveToNextToken(false, false))
                throw new IllegalStateException();

            return argumentRichText;
        }
        finally { if (parser != null) parser.dispose(); }
    }

    /// <summary>
    /// Returns a value indicating whether the specified character is considered to be a double quote by field code parser.
    /// </summary>
    static boolean isDoubleQuote(char c)
    {
        return StringUtil.isDoubleQuote(c);
    }

    /// <summary>
    /// Returns a value indicating whether the specified token is enclosed in double quotes.
    /// </summary>
    static boolean isTokenInDoubleQuotes(String token)
    {
        // An argument is considered to be enclosed in the double quotes by the MS Word
        // even if there is the opening double quote only ({ QUOTE "97 } is the example).
        // Also it is assumed that a field code parser does not include leading white spaces
        // to a token, so a double quote may appear only at the beginning of a token.
        return StringUtil.hasChars(token) && isDoubleQuote(token.charAt(0));
    }

    /// <summary>
    /// Proceeds to the first field token.
    /// </summary>
    boolean moveToFirstToken(boolean ignoreSwitches)
    {
        return moveToNextToken(false, ignoreSwitches, false);
    }

    /// <summary>
    /// Proceeds to a next field token.
    /// </summary>
    /// <param name="parseUntilEnd">True to parse next token until the end of field code.</param>
    /// <param name="ignoreSymbolicQuotes">True to consider quote as regular char if symbolic font is used.</param>
    /// <returns>True if successful, false if passed the end of field code.</returns>
    boolean moveToNextToken(boolean parseUntilEnd, boolean ignoreSymbolicQuotes)
    {
        return moveToNextToken(parseUntilEnd, false, ignoreSymbolicQuotes);
    }

    private boolean moveToNextToken(boolean parseUntilEnd, boolean ignoreSwitches, boolean ignoreSymbolicQuotes)
    {
        isCurrentTokenInDoubleQuotes(false);
        mIgnoreSwitches = ignoreSwitches;

        if (mIsEof)
            return false;

        // We may have a pending switch to return (i.e. a switch which is not preceded by a delimiter).
        // Note, that it can not have any corresponding first field start and last field end as it can be
        // nothing but a piece of plain (i.e. not preserved (see the summary for IsTextCharPreserved)) text.
        if (!isSwitch() && mSwitchBuilder.hasContent())
        {
            isSwitch(true);
            return true;
        }

        mArgumentBuilder.clear();
        mSwitchBuilder.clear();
        mIsInToken = false;
        isSwitch(false);

        boolean isEscaped = false;

        if (mIsInDoubleQuotes)
        {
            // The only situation when we may have stopped at the beginning of the next token is when
            // an opening double quote was encountered, so add it and register a start point.
            appendToken();
        }

        if (parseUntilEnd)
        {
            if (parseUntilEnd())
                return true;
            mIsEof = true;
            return mArgumentBuilder.hasContent();
        }

        while (!mTokenizer.isEof())
        {
            if (!isEndOfRun())
            {
                if (processCurrentToken())
                {
                    // Found the end of the current token, exit.
                    endProbingSwitch();
                    // WORDSNET-26152 set the end position after the last token.
                    finalizeToken();
                    // This can probably move beyond the range.
                    mTokenizer.moveToNextToken();

                    return true;
                }

                if (mTokenizer.getCurrentToken() == FieldCodeToken.TEXT_CHAR)
                {
                    if (processTextChar(isEscaped, ignoreSymbolicQuotes))
                        return true;

                    isEscaped = (!mIsInChildFieldResult) && (!isEscaped) && (mTokenizer.getCurrentChar() == '\\');
                }
                else
                {
                    processNonTextChar();
                }
            }

            mTokenizer.moveToNextToken();
        }

        // WORDSNET-26152 set the end position after the last token.
        finalizeToken();

        mIsEof = true;
        endProbingSwitch();

        return mArgumentBuilder.hasContent();
    }

    private boolean parseUntilEnd()
    {
        boolean switchIsPossible = true;
        boolean isLeadingWhitespaceSkipped = false;
        FieldCodeTokenBuilder whitespaceBuilder = new FieldCodeTokenBuilder();

        while (!mTokenizer.isEof())
        {
            if (!isEndOfRun())
            {
                if ((mTokenizer.getCurrentToken() == FieldCodeToken.TEXT_CHAR) && msChar.isWhiteSpace(mTokenizer.getCurrentChar()))
                {
                    if (isLeadingWhitespaceSkipped)
                        whitespaceBuilder.appendToken(mTokenizer);
                }
                else
                {
                    isLeadingWhitespaceSkipped = true;

                    // WORDSNET-11718 Try to parse switch first.
                    if (switchIsPossible && (mTokenizer.getCurrentToken() == FieldCodeToken.TEXT_CHAR) && mTokenizer.getCurrentChar() == '\\')
                    {
                        startProbingSwitch();
                    }
                    else
                    {
                        if (whitespaceBuilder.hasContent())
                        {
                            FieldCodeTokenBuilder builder = mSwitchBuilder.hasContent()
                                ? mSwitchBuilder
                                : mArgumentBuilder;
                            builder.mergeWith(whitespaceBuilder);
                        }

                        if (processRegularTextChar())
                            return true;
                    }

                    switchIsPossible = false;
                }
            }

            mTokenizer.moveToNextToken();
        }

        finalizeToken();
        return false;
    }

    /// <summary>
    /// Returns true if found the end of the current token.
    /// </summary>
    private boolean processCurrentToken()
    {
        switch (mTokenizer.getCurrentToken())
        {
            case FieldCodeToken.TEXT_CHAR:
                return false;
            case FieldCodeToken.PARAGRAPH:
                return !mTokenizer.isEndOfToken() && processBreak(ControlChar.PARAGRAPH_BREAK_CHAR, resolveParagraphBreakFormatting(mTokenizer.getCurrentNode()));
            case FieldCodeToken.SECTION:
                return !mTokenizer.isEndOfToken() && processBreak(ControlChar.SECTION_BREAK_CHAR, null);
            case FieldCodeToken.NON_TEXT_NODE:
                appendToken();
                return false;
            case FieldCodeToken.CHILD_FIELD_START:
                return (mIsInToken && !mIsInDoubleQuotes);
            case FieldCodeToken.CHILD_FIELD_SEPARATOR:
                mIsInChildFieldResult = true;
                return false;
            case FieldCodeToken.CHILD_FIELD_END:
                mIsInChildFieldResult = false;
                return !mIsInDoubleQuotes;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static RunPr resolveParagraphBreakFormatting(Node currentParagraph)
    {
        if (currentParagraph == null)
            return null;

        Paragraph previousParagraph = ms.as(currentParagraph.getPreviousSibling(), Paragraph.class);
        return previousParagraph != null
            ? previousParagraph.getParagraphBreakRunPr()
            : null;
    }

    /// <summary>
    /// Processes a token other than a text character (shape, field start/end etc).
    /// </summary>
    private void processNonTextChar()
    {
        // We need to continue parsing, but the current token is not a text character.
        // We just skip it as the further logic requires a character to work.
        // Also, any token except a character breaks a switch.

        endProbingSwitch();

        switch (mTokenizer.getCurrentToken())
        {
            case FieldCodeToken.CHILD_FIELD_START:
                visitFieldStart();
                break;
            case FieldCodeToken.CHILD_FIELD_SEPARATOR:
            case FieldCodeToken.CHILD_FIELD_END:
                appendToken();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /// <summary>
    /// Processes a text character token.
    /// </summary>
    /// <returns>True if the character delimits a token, false to continue parse.</returns>
    private boolean processTextChar(boolean isEscaped, boolean ignoreSymbolicQuotes)
    {
        // WORDSNET-4241 the first character of the field code sets the field language.
        // WORDSNET-4848 Support Japanese formatting picture elements.
        setLanguages();

        char c = mTokenizer.getCurrentChar();

        if (msChar.isWhiteSpace(c))
            return processWhiteSpace();

        if (isDoubleQuoteSymbolicFontAware(c, ignoreSymbolicQuotes))
            return processDoubleQuotes(isEscaped);

        if (c == '\\')
        {
            processBackslash(isEscaped);
            return false;
        }

        return processRegularTextChar();
    }

    private boolean isDoubleQuoteSymbolicFontAware(char c, boolean ignoreSymbolicQuotes)
    {
        if (!isDoubleQuote(c))
            return false;

        if (!ignoreSymbolicQuotes)
            return true;

        return !isSymbolicFont();
    }

    private boolean isSymbolicFont()
    {
        String fontName = mTokenizer.getCurrentFontName();
        return !msString.isNullOrEmpty(fontName) && FontUtil.isSymbolic(fontName);
    }

    private void setLanguages()
    {
        // SPEED Request the value if it is not set yet.
        if (mIsLanguagesInitialized)
            return;

        setLanguageId(mTokenizer.getCurrentLocaleId());
        setLanguageIdFarEast(mTokenizer.getCurrentLocaleIdFarEast());
        setLanguageIdBi(mTokenizer.getCurrentLocaleIdBi());
        setBidi(mTokenizer.getCurrentBidi());

        setLanguageNode(ms.as(mTokenizer.getCurrentNode(), Inline.class));

        mIsLanguagesInitialized = true;
    }

    /// <summary>
    /// Processes a whitespace character token.
    /// </summary>
    /// <returns>True if the character delimits a token, false to continue parse.</returns>
    private boolean processWhiteSpace()
    {
        if (isTextCharPreserved())
        {
            // The token is in double quotes or we are reading a child field's result,
            // so spaces are respected.
            appendToken();
            return false;
        }
        else if (!mIsInToken)
        {
            // Leading space, skip.
            return false;
        }
        else
        {
            // A whitespace after a token, exit.
            endProbingSwitch();
            // WORDSNET-26152 set the end position after the last token.
            finalizeToken();

            return true;
        }
    }

    /// <summary>
    /// Processes a double quotes character token.
    /// </summary>
    /// <returns>True if the character delimits a token, false to continue parse.</returns>
    private boolean processDoubleQuotes(boolean isEscaped)
    {
        if (ignoreDoubleQuotes())
            return false;

        endProbingSwitch();

        if (mIsInToken)
        {
            if (isEscaped || mIsInChildFieldResult)
            {
                // Escaped double quote.
                appendToken();
            }
            else
            {
                if (mIsInDoubleQuotes)
                {
                    // Closing double quote.
                    appendToken();
                    closeDoubleQuotes();

                    // WORDSNET-26152 set the end position after the last token.
                    moveNextAndFinalizeToken();
                }
                else
                {
                    // Starting double quote will be processed as a part of the next token.
                    finalizeToken();
                }

                return true;
            }
        }
        else
        {
            // Opening double quote.
            appendToken();
            openDoubleQuotes();
        }

        return false;
    }

    /// <summary>
    /// Processes a backslash character token.
    /// </summary>
    private void processBackslash(boolean isEscaped)
    {
        endProbingSwitch();

        if (!isEscaped)
        {
            // This is where a switch may begin.
            // WORDSNET-14447 Ignore switches before field type token.
            if (!isTextCharPreserved() && !mIgnoreSwitches)
            {
                // WORDSNET-13773 Set the end position of the current token.
                if (mArgumentBuilder.hasContent())
                    finalizeToken();

                startProbingSwitch();
            }
            else
            {
                appendToken();
            }
        }
        else
        {
            // An escaped backslash.
            appendToken();
        }
    }

    /// <summary>
    /// Processes a regular text character token.
    /// </summary>
    /// <returns>True if the character delimits a token, false to continue parse.</returns>
    private boolean processRegularTextChar()
    {
        appendToken();

        if (!mSwitchBuilder.hasContent())
            return false;

        // We are trying to recognize a switch.
        // According to the OOXML spec, a switch may be 1 or 2 chars long (plus a backslash).
        final int MAX_SWITCH_LENGTH = 3;
        if (mSwitchBuilder.getLength() <= MAX_SWITCH_LENGTH)
        {
            if (isValidSwitch())
            {
                // If we have not collected any argument chars before the switch, make the current token
                // a switch right away. Otherwise, return the argument first.
                isSwitch(!mArgumentBuilder.hasContent());
                // WORDSNET-26152 set the end position after the last token.
                moveNextAndFinalizeToken();

                return true;
            }
        }
        else
        {
            // The possible switch is not recognized, stop probing it.
            endProbingSwitch();
        }

        return false;
    }

    /// <summary>
    /// Processes a paragraph or section break.
    /// </summary>
    /// <returns>True if the break delimits a token, false to continue parse.</returns>
    private boolean processBreak(char breakChar, RunPr runPr)
    {
        if (isTextCharPreserved())
        {
            mArgumentBuilder.appendChar(breakChar, runPr);
            return false;
        }
        else
        {
            return mIsInToken;
        }
    }

    private void visitFieldStart()
    {
        FieldCodeTokenBuilder builder = (!mSwitchBuilder.hasContent()) ? mArgumentBuilder : mSwitchBuilder;
        builder.visitFieldStart(mTokenizer);
    }

    private void appendToken()
    {
        FieldCodeTokenBuilder builder = (!mSwitchBuilder.hasContent()) ? mArgumentBuilder : mSwitchBuilder;
        builder.appendToken(mTokenizer);
        mIsInToken = true;
    }

    private void moveNextAndFinalizeToken()
    {
        // Get the position after the current position.
        mTokenizer.moveToNextToken();
        finalizeToken();
    }

    private void finalizeToken()
    {
        FieldCodeTokenBuilder builder = (!mSwitchBuilder.hasContent()) ? mArgumentBuilder : mSwitchBuilder;
        builder.finalizeToken(mTokenizer);
    }

    private void startProbingSwitch()
    {
        mSwitchBuilder.clear();
        mSwitchBuilder.appendToken(mTokenizer);
        mIsInToken = true;
    }

    private void endProbingSwitch()
    {
        mArgumentBuilder.mergeWith(mSwitchBuilder);
    }

    private void openDoubleQuotes()
    {
        isCurrentTokenInDoubleQuotes(true);
        mIsInDoubleQuotes = true;
        if (mUpdateContext != null)
            mUpdateContext.isInDoubleQuotes(true);

        mOpeningDoubleQuotePosition = mTokenizer.getCurrentPosition();
    }

    private void closeDoubleQuotes()
    {
        mIsInDoubleQuotes = false;
        if (mUpdateContext != null)
            mUpdateContext.isInDoubleQuotes(false);

        mOpeningDoubleQuotePosition = null;
    }

    private boolean ignoreDoubleQuotes()
    {
        if (mOpeningDoubleQuotePosition == null)
            return false;

        // This is a rude way of handling double quotes located e.g. in a table that is enclosed into
        // double quotes to designate an argument.
        if (NodeUtil.getNestingLevel(mOpeningDoubleQuotePosition.getNode()) ==
            NodeUtil.getNestingLevel(mTokenizer.getCurrentPosition().getNode()))
            return false;

        return true;
    }

    /// <summary>
    /// Returns a node range corresponding to the current token.
    /// </summary>
    /// <returns></returns>
    NodeRange getCurrentNodeRange()
    {
        return getCurrentBuilder().getNodeRange();
    }

    /// <summary>
    /// Returns a node range corresponding to the current complete field token.
    /// </summary>
    NodeRange getCompleteFieldNodeRange()
    {
        return getCurrentBuilder().getCompleteFieldNodeRange();
    }

    private boolean isEndOfRun() { return (mTokenizer.getCurrentToken() == FieldCodeToken.TEXT_CHAR) && (mTokenizer.isEndOfToken()); }

    /// <summary>
    /// Returns a value indicating whether a special text character is preserved from being used as a special,
    /// i.e. it should be treated as a regular character (i.e. not a delimiter, etc.). A special text character
    /// is considered to be preserved, when it is contained within double quotes or a child field result.
    /// </summary>
    private boolean isTextCharPreserved() { return (mIsInDoubleQuotes || mIsInChildFieldResult); }

    /// <summary>
    /// Gets whether the possible switch is valid for the field whose code we are parsing.
    /// </summary>
    private boolean isValidSwitch()
    {
        return ((mTokenInfoProvider != null) &&
                (mTokenInfoProvider.getSwitchType(mSwitchBuilder.getText().toLowerCase()) != FieldSwitchType.UNKNOWN));
    }

    private FieldCodeTokenBuilder getCurrentBuilder() { return (!isSwitch()) ? mArgumentBuilder : mSwitchBuilder; }

    /// <summary>
    /// Gets the current token, whether it is an argument or a valid switch.
    /// </summary>
    String getCurrentToken() { return getCurrentBuilder().getText(); }

    /// <summary>
    /// Gets the current rich token.
    /// </summary>
    RichString getCurrentRichToken() { return getCurrentBuilder().getRichText(); }

    /// <summary>
    /// Gets whether the current token is a switch.
    /// </summary>
    boolean isSwitch() { return mIsSwitch; }; private void isSwitch(boolean value) { mIsSwitch = value; };

    private boolean mIsSwitch;

    /// <summary>
    /// Gets the language id of the field code specified via the properties of the first character of the field code.
    /// </summary>
    int getLanguageId() { return mLanguageId; }; private void setLanguageId(int value) { mLanguageId = value; };

    private int mLanguageId;

    /// <summary>
    /// Gets the East Asian language id of the field code specified via the properties of the first character of the field code.
    /// </summary>
    int getLanguageIdFarEast() { return mLanguageIdFarEast; }; private void setLanguageIdFarEast(int value) { mLanguageIdFarEast = value; };

    private int mLanguageIdFarEast;

    /// <summary>
    /// Gets the RTL language id of the field code specified via the properties of the first character of the field code.
    /// </summary>
    int getLanguageIdBi() { return mLanguageIdBi; }; private void setLanguageIdBi(int value) { mLanguageIdBi = value; };

    private int mLanguageIdBi;

    /// <summary>
    /// A boolean value indicating if RTL is specified via the properties of the first character of the field code.
    /// </summary>
    boolean getBidi() { return mBidi; }; private void setBidi(boolean value) { mBidi = value; };

    private boolean mBidi;

    Inline getLanguageNode() { return mLanguageNode; }; private void setLanguageNode(Inline value) { mLanguageNode = value; };

    private Inline mLanguageNode;

    boolean isCurrentTokenInDoubleQuotes() { return mIsCurrentTokenInDoubleQuotes; }; private void isCurrentTokenInDoubleQuotes(boolean value) { mIsCurrentTokenInDoubleQuotes = value; };

    private boolean mIsCurrentTokenInDoubleQuotes;

    private /*final*/ IFieldCodeTokenizer mTokenizer;
    private /*final*/ IFieldCodeTokenInfoProvider mTokenInfoProvider;
    private /*final*/ FieldUpdateContext mUpdateContext;
    private /*final*/ FieldCodeTokenBuilder mArgumentBuilder = new FieldCodeTokenBuilder();
    private /*final*/ FieldCodeTokenBuilder mSwitchBuilder = new FieldCodeTokenBuilder();
    private boolean mIsInToken;
    private boolean mIsInDoubleQuotes;
    private DocumentPosition mOpeningDoubleQuotePosition;
    private boolean mIsInChildFieldResult;
    private boolean mIgnoreSwitches;
    private boolean mIsEof;
    private static final int LANGUAGE_NOT_SET = -1;
    private boolean mIsLanguagesInitialized;
}

