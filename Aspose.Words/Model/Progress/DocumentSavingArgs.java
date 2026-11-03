// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DoublePal;


/// <summary>
/// An argument passed into <see cref="IDocumentSavingCallback.Notify(DocumentSavingArgs)"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/save-a-document/">Save a Document</a> documentation article.</para>
/// </summary>
public final class DocumentSavingArgs
{
    // Hide default ctr.
    private DocumentSavingArgs() { }

    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="stageData">Stage data.</param>
    /// <param name="progressCalculator">Estimated progress calculator.</param>
    DocumentSavingArgs(
        StageData stageData,
        SavingProgressCalculator progressCalculator)
    {
        mEstimatedProgress =
            DoublePal.trim(progressCalculator.getEstimatedProgress(stageData), SavingProgressCalculator.PRECISION);
    }

    /// <summary>
    /// Overall estimated percentage progress.
    /// </summary>
    public double getEstimatedProgress() { return mEstimatedProgress; };

    private  double mEstimatedProgress;
}

