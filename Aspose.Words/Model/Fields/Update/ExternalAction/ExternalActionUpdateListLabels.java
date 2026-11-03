// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/12/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.lang.Class;


/// <summary>
/// Updates list labels in the document.
/// </summary>
class ExternalActionUpdateListLabels implements IExternalAction
{
    /// <summary>
    /// Initializes an instance of this class.
    /// </summary>
    /// <param name="document"></param>
    ExternalActionUpdateListLabels(Document document)
    {
        mDocument = document;
    }

    public void /*IExternalAction.*/perform() throws Exception
    {
        if (mPerformed)
            return;

        mDocument.updateListLabels();
        mPerformed = true;
    }

    public Class /*IExternalAction.*/getId() { return getClass(); }

    private boolean mPerformed;

    private /*final*/ Document mDocument;
}

