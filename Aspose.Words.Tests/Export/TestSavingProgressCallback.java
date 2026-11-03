// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Saving progress callback for tests.
/// </summary>
public class TestSavingProgressCallback implements IDocumentSavingCallback
{
    /// <summary>
    /// The saving arguments collection. Such arguments accepted by a document saving callback.
    /// </summary>
    public ArrayList<DocumentSavingArgs> getAcceptedArguments() { return mAcceptedArguments; }

    /// <summary>
    /// Progress value after which document saving should be canceled.
    /// </summary>
    public double getCancelAt() { return mCancelAt; }; public void setCancelAt(double value) { mCancelAt = value; };

    private double mCancelAt;

    /// <summary>
    /// Callback method which called during document saving.
    /// </summary>
    /// <param name="args">Saving arguments.</param>
    public void notify(DocumentSavingArgs args)
    {
        msArrayList.add(mAcceptedArguments, args);
        double approximateProgress = args.getEstimatedProgress();

        if ((getCancelAt() > 0.0d) && (getCancelAt() <= args.getEstimatedProgress()))
            throw new IllegalStateException("ApproximateProgress = " + approximateProgress + "; CancelAt = " + getCancelAt());
    }

    private /*final*/ ArrayList<DocumentSavingArgs> mAcceptedArguments = new ArrayList<DocumentSavingArgs>();
}

