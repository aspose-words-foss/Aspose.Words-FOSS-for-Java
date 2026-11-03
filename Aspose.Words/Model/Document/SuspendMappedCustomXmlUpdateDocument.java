// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2022 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;


/// <summary>
/// Suspends mapped CustomXML runtime update. Resumes update when disposing.
/// </summary>
final class SuspendMappedCustomXmlUpdateDocument implements IDisposable
{
    SuspendMappedCustomXmlUpdateDocument(DocumentBase document)
    {
        mDocument = document;
        mDocument.suspendMappedCustomXmlUpdate();
    }

    //JAVA-added for autoporting of using statements.
    public void close()
    {
        dispose();
    }

    public void dispose()
    {
        mDocument.resumeMappedCustomXmlUpdate();
    }

    private /*final*/ DocumentBase mDocument;
}

