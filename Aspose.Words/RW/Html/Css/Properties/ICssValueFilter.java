// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a CSS value filter.
/// </summary>
interface ICssValueFilter
{
    /// <summary>
    /// Indicates whether the filter accepts (passes) the specified CSS value in the specified document mode.
    /// </summary>
    public boolean accepts(CssValue value, boolean isInQuirksMode);
}

