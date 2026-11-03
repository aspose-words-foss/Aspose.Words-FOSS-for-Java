// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2019 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Provides CSS tokens for parsing by taking them from a CSS tokenizer that processes a CSS text.
/// </summary>
class CssTokenizerTokenSource extends CssTokenSource
{
    CssTokenizerTokenSource(String css)
    {
        mTokenizer = new CssTokenizer(css);
    }

    /*override*/ CssToken consume()
    {
        if (!mReconsumed)
        {
            mTokenizer.moveNext();
        }
        mReconsumed = false;
        return mTokenizer.getCurrentToken();
    }

    /*override*/ void reconsume()
    {
        Debug.assertTrue(!mReconsumed);
        mReconsumed = true;
    }

    private /*final*/ CssTokenizer mTokenizer;
    private boolean mReconsumed;
}

