// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/08/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to have your own custom method called to 
/// capture loss of fidelity warnings that can occur during document loading or saving.
/// </summary>
public interface IWarningCallback
{
    /// <summary>
    /// Aspose.Words invokes this method when it encounters some issue during document loading 
    /// or saving that might result in loss of formatting or data fidelity.
    /// </summary>
    public void warning(WarningInfo info);
}

