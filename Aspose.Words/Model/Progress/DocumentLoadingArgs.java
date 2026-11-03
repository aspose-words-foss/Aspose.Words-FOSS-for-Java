// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DoublePal;


/// <summary>
/// An argument passed into <see cref="IDocumentLoadingCallback.Notify(DocumentLoadingArgs)"/>.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/specify-load-options/">Specify Load Options</a> documentation article.</para>
/// </summary>
public final class DocumentLoadingArgs
{
    // Hide default ctr.
    private DocumentLoadingArgs() { }

    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="stageData">Stage data.</param>
    /// <param name="progressCalculator">Estimated progress calculator.</param>
    DocumentLoadingArgs(
        StageData stageData,
        LoadingProgressCalculator progressCalculator)
    {
        setEstimatedProgress(DoublePal.trim(progressCalculator.getEstimatedProgress(stageData), LoadingProgressCalculator.PRECISION));
    }


    /// <summary>
    /// Ctr.
    /// </summary>
    /// <param name="estimatedProgress">Estimated progress.</param>
    /// <remarks>
    /// This constructor is called by Pdf2Word plugin.
    /// </remarks>
    DocumentLoadingArgs(double estimatedProgress)
    {
        setEstimatedProgress(DoublePal.trim(estimatedProgress, LoadingProgressCalculator.PRECISION));
    }

    /// <summary>
    /// Overall estimated percentage progress.
    /// </summary>
    public double getEstimatedProgress() { return mEstimatedProgress; }; private void setEstimatedProgress(double value) { mEstimatedProgress = value; };

    private double mEstimatedProgress;
}

