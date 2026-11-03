// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/12/2003 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines an interface for writers that can write a model document into a specific format.
/// </summary>
interface IDocumentWriter
{
    /// <summary>
    /// Writes the document in the format of the writer.
    /// </summary>
    /// <param name="saveInfo">The parameters that describe what to save.</param>
    public SaveOutputParameters saveToStream(SaveInfo saveInfo) throws Exception;
}

