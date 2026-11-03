// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines an interface for writers that can write a fragment of a document to a string.
/// </summary>
interface IDocumentFragmentWriter
{
    /// <summary>
    /// Saves just an individual node (document fragment) into a string.
    /// </summary>
    public String saveToString(Node node) throws Exception;
}

