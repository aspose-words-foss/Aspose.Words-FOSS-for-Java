// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/04/2020 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;
import com.aspose.Debug;


/// <summary>
/// Organizes <see cref="HtmlListReader"/> instances into nested contexts.
/// </summary>
class HtmlListReaderManager
{
    HtmlListReaderManager()
    {
        mListReaders = new msStack<HtmlListReader>();
        openNewContext();
    }

    void openNewContext()
    {
        // A new context starts with no list reader.
        // We'll create one just before we start processing a list.
        mListReaders.push(null);
    }

    void closeContext()
    {
        Debug.assertTrue(mListReaders.getCount() > 0);

        mListReaders.pop();
    }

    void createListReader(
        DocumentBuilder builder,
        DocumentFormatter documentFormatter,
        HtmlResourceLoader htmlResourceLoader,
        String baseUri,
        double defaultWholeListLeftIndent,
        boolean adjustMarkerPositions)
    {
        Debug.assertTrue(getCurrentListReader() == null);

        HtmlListReader newListReader = new HtmlListReader(
            builder,
            documentFormatter,
            htmlResourceLoader,
            baseUri,
            defaultWholeListLeftIndent,
            adjustMarkerPositions);

        mListReaders.pop();
        mListReaders.push(newListReader);
    }

    HtmlListReader getCurrentListReader()
    {
        Debug.assertTrue(mListReaders.getCount() > 0);

        return mListReaders.peek();
    }

    private /*final*/ msStack<HtmlListReader> mListReaders;
}

