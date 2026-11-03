// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2010 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implement this interface if you want to receive notifications and control how
/// Aspose.Words saves document parts when exporting a document to <see cref="Words.SaveFormat.Html"/> 
/// or <see cref="Words.SaveFormat.Epub"/> format.
/// </summary>
public interface IDocumentPartSavingCallback
{
    /// <summary>
    /// Called when Aspose.Words is about to save a document part.
    /// </summary>
    public void documentPartSaving(DocumentPartSavingArgs args) throws Exception;
}

