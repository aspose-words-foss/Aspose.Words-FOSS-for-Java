// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/01/2014 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Base class, provides methods to read "inline" elements. Inline elements are those that normally
/// occur inside a paragraph. But in DOCX the same set of elements can occur inside hyperlink,
/// fldSimple, smartTag and few other elements, therefore this common code is factored out here.
/// NOTE: this class should be immutable (state cannot be modified after it is created).
/// </summary>
abstract class NrxInlineReaderBase
{
    /// <summary>
    /// Reads all children of the current element.
    /// The current element is supposed to a container for inline elements such as hyperlink, fldSimple etc.
    /// Reads all children of the current element.
    /// </summary>
    abstract void readChildren(NrxDocumentReaderBase reader, RunPr runPr) throws Exception;

    /// <summary>
    /// Reads all children of the current element.
    /// The current element is supposed to a container for inline elements such as hyperlink, fldSimple etc.
    /// Reads all children of the current element.
    /// </summary>
    void readChildren(NrxDocumentReaderBase reader) throws Exception
    {
        readChildren(reader, null);
    }

    /// <summary>
    /// Reads one current element. Factored into a separate method to make reading of child
    /// of paragraph elements possible since pPr is not an inline element and is read in the
    /// paragraph reader.
    /// </summary>
    void readChild(NrxDocumentReaderBase reader) throws Exception
    {
        readChild(reader, null);
    }

    /// <summary>
    /// Reads one current element. Factored into a separate method to make reading of child
    /// of paragraph elements possible since pPr is not an inline element and is read in the
    /// paragraph reader.
    /// </summary>
    protected abstract void readChild(NrxDocumentReaderBase reader, RunPr runPr) throws Exception;
}

