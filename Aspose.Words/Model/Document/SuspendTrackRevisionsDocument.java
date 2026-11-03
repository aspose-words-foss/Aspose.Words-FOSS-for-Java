// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2014 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IDisposable;


/// <summary>
/// Suspends revision tracking. Resumes revision tracking when disposing.
/// </summary>
final class SuspendTrackRevisionsDocument implements IDisposable
{
    SuspendTrackRevisionsDocument(DocumentBase document)
    {
    	this (document, SuspendedRevisionTypes.ALL);
	
    }

    SuspendTrackRevisionsDocument(DocumentBase document, /*SuspendedRevisionTypes*/int revisionTypes)
    {
        mDocument = document;
        mDocument.suspendTrackRevisions(revisionTypes);
        mRevisionTypes = revisionTypes;
    }

    //JAVA-added for autoporting of using statements.
    public void close()
    {
        dispose();
    }

    public void dispose()
    {
        mDocument.resumeTrackRevisions(mRevisionTypes);
    }

    private /*final*/ DocumentBase mDocument;
    private /*final*/ /*SuspendedRevisionTypes*/int mRevisionTypes;
}

