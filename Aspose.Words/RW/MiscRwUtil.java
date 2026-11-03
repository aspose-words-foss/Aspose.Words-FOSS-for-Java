// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Miscellaneous common read/write utilities for all format import/export.
/// </summary>
/*static*/ class MiscRwUtil
{
	/* Simulation of static class by using private constructor */
	private MiscRwUtil()
	{}

    /// <summary>
    /// Creates a PAGE field, that displays current page number when document layout created.
    /// </summary>
    /// <remarks>Used by nrx (docx, wml), doc, rtf</remarks>
    /// <returns>A field represented as array of 5 nodes that are ready to be inserted into the document tree.</returns>
    static Node[] createPageField(DocumentBase doc, RunPr runPr)
    {
        return createFieldByType(doc, runPr, FieldType.FIELD_PAGE, "PAGE");
    }

    /// <summary>
    /// Creates a field by type.
    /// </summary>
    /// <returns>A field represented as array of 5 nodes that are ready to be inserted into the document tree.</returns>
    static Node[] createFieldByType(DocumentBase doc, RunPr runPr, /*FieldType*/int fieldType, String data)
    {
        return new Node[] {
            new FieldStart(doc,  AttrCollectionExtension.deepClone(runPr), fieldType),
            new Run(doc, data,  AttrCollectionExtension.deepClone(runPr)),
            new FieldSeparator(doc,  AttrCollectionExtension.deepClone(runPr), fieldType, null),
            new Run(doc, "XXX",  AttrCollectionExtension.deepClone(runPr)),
            new FieldEnd(doc,  AttrCollectionExtension.deepClone(runPr), fieldType, true) };
    }
}

