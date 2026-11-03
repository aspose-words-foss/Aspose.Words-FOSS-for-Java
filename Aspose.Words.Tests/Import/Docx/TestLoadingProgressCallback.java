// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FormatterPal;


/// <summary>
/// Loading progress callback for tests.
/// </summary>
public class TestLoadingProgressCallback implements IDocumentLoadingCallback
{
    /// <summary>
    /// The loading arguments collection. Such arguments accepted by a document loading callback.
    /// </summary>
    public ArrayList<DocumentLoadingArgs> getAcceptedArguments() { return mAcceptedArguments; }

    /// <summary>
    /// Progress value after which document loading should be canceled.
    /// </summary>
    public double getCancelAt() { return mCancelAt; }; public void setCancelAt(double value) { mCancelAt = value; };

    private double mCancelAt;

    /// <summary>
    /// Callback method which called during document loading.
    /// </summary>
    /// <param name="args">Loading arguments.</param>
    public void notify(DocumentLoadingArgs args)
    {
        msArrayList.add(mAcceptedArguments, args);
        double approximateProgress = args.getEstimatedProgress();

        if ((getCancelAt() > 0.0d) && (getCancelAt() <= args.getEstimatedProgress()))
        {
            // FormatterPal is used to get the same strings on .Net and Java
            throw new IllegalStateException("ApproximateProgress = " +
                                                 FormatterPal.doubleToStr2Decimals(approximateProgress) +
                                                 "; CancelAt = " + FormatterPal.doubleToStr2Decimals(getCancelAt()));
        }
    }

    private /*final*/ ArrayList<DocumentLoadingArgs> mAcceptedArguments = new ArrayList<DocumentLoadingArgs>();
}

