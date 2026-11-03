// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/12/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A simple implementation of field code tokenizer that iterates over a field code represented by a text string.
/// </summary>
class TextFieldCodeTokenizer implements IFieldCodeTokenizer
{
    TextFieldCodeTokenizer(String fieldCode, boolean moveToFirstToken)
    {
        mFieldCode = fieldCode;
        mIndex = -1;

        if (moveToFirstToken)
            moveToNextToken();
    }

    public boolean moveToNextToken()
    {
        mIndex++;
        return !isEof();
    }

    public DocumentPosition getCurrentPosition()
    {
        return DocumentPosition.VOID;
    }

    public /*FieldCodeToken*/int getCurrentToken() { return FieldCodeToken.TEXT_CHAR; }

    public boolean isEndOfToken() { return false; }

    public char getCurrentChar() { return mFieldCode.charAt(mIndex); }

    public Node getCurrentNode() { return null; }

    public boolean isEof() { return mIndex >= mFieldCode.length();  }

    public int getCurrentLocaleId() { return RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID; }

    public int getCurrentLocaleIdFarEast() { return RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID; }

    public int getCurrentLocaleIdBi() { return RunPr.PROCESS_OR_USER_DEFAULT_LANGUAGE_ID; }

    public boolean getCurrentBidi() { return false; }

    public String getCurrentFontName() { return null; }

    private /*final*/ String mFieldCode;
    private int mIndex;
}

