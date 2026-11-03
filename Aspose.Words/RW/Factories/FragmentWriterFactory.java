// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/08/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Document fragment writer factory.
/// </summary>
/*static*/ class FragmentWriterFactory
{
	/* Simulation of static class by using private constructor */
	private FragmentWriterFactory()
	{}

    /// <summary>
    /// Creates and returns a new instance of document fragment writer class.
    /// </summary>
    static IDocumentFragmentWriter createFragmentWriter(/*SaveFormat*/int saveFormat)
    {
        switch (saveFormat)
        {
            case SaveFormat.HTML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.TEXT:
                return new TxtDocumentFragmentWriter();
            default:
                throw new IllegalStateException("Exporting fragments of a document in this format is not supported.");
        }
    }

    /// <summary>
    /// Creates and returns a document fragment writer object using the specified save options.
    /// </summary>
    static IDocumentFragmentWriter createFragmentWriter(SaveOptions saveOptions)
    {
        switch (saveOptions.getSaveFormat())
        {
            case SaveFormat.HTML:
                throw new UnsupportedOperationException("FOSS");
            case SaveFormat.TEXT:
                return new TxtDocumentFragmentWriter((TxtSaveOptions) saveOptions);
            default:
                throw new IllegalStateException("Exporting fragments of a document in this format is not supported.");
        }
    }
}

