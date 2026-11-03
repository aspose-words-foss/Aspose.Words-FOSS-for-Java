// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/09/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to control how Aspose.Words saves CSS (Cascading Style Sheet) when 
/// saving a document to HTML.
/// </summary>
public interface ICssSavingCallback
{
    /// <summary>
    /// Called when Aspose.Words saves an CSS (Cascading Style Sheet).
    /// </summary>
    public void cssSaving(CssSavingArgs args) throws Exception;
}

