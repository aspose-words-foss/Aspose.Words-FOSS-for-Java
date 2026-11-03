// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/09/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;


class TxtDocumentFragmentWriter implements IDocumentFragmentWriter
{
    TxtDocumentFragmentWriter()
    {
        mSaveOptions = new TxtSaveOptions();
    }

    TxtDocumentFragmentWriter(TxtSaveOptions saveOptions)
    {
        Debug.assertTrue(saveOptions != null);
        mSaveOptions = saveOptions;
    }

    //>>>>>>>> #region  Implementation of IDocumentFragmentWriter

    public String saveToString(Node node) throws Exception
    {
        Debug.assertTrue(node != null);
        TxtWriter writer = new TxtWriter();
        Document doc = ms.as(node, Document.class);
        // FOSS
        return writer.saveToString(node, mSaveOptions);
    }

    //<<<<<<<< #endregion 

    private /*final*/ TxtSaveOptions mSaveOptions;
}

