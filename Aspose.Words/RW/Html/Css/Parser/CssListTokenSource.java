// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Provides CSS tokens for parsing from a list.
/// </summary>
class CssListTokenSource extends CssTokenSource
{
    CssListTokenSource(ArrayList<CssToken> tokens)
    {
        mTokens = tokens;
        mPosition = -1;
    }

    /*override*/ CssToken consume()
    {
        if (mPosition < mTokens.size())
        {
            ++mPosition;
        }
        CssToken result = (mPosition < mTokens.size())
            ? mTokens.get(mPosition)
            : CssToken.EOF;
        return result;
    }

    /*override*/ void reconsume()
    {
        if (mPosition >= 0)
        {
            --mPosition;
        }
    }

    private /*final*/ ArrayList<CssToken> mTokens;
    private int mPosition;
}

