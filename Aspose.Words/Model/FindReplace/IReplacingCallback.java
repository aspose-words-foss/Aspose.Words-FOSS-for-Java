// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/08/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to have your own custom method called during a find and replace operation.
/// </summary>
public interface IReplacingCallback
{
    /// <summary>
    /// A user defined method that is called during a replace operation for each match found just before a replace is made.
    /// </summary>
    /// <returns>
    /// A <see cref="ReplaceAction"/> value that specifies the action to be taken for the current match.
    /// </returns>
    public /*ReplaceAction*/int replacing(ReplacingArgs args) throws Exception;
}

