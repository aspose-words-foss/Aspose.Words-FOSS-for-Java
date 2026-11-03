// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/05/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS media query list.
/// </summary>
/// <remarks>
/// We have implemented a very limited support for media queries. Now we only support "all" and "screen" values.
/// See https://www.w3.org/TR/css3-mediaqueries/ and <see cref="CssParser.ParseMediaQueryList(string)"/>.
/// </remarks>
class CssMediaQueryList
{
    CssMediaQueryList(boolean isSupported)
    {
        mIsSupported = isSupported;
    }

    boolean isSupported() { return mIsSupported; };

    private boolean mIsSupported;

    public /*override*/ String toString()
    {
        return isSupported()
            ? "screen"
            : "unsupported";
    }
}

