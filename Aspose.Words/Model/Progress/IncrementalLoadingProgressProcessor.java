// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2022 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements processor for document reading stage and uses overall
/// and current numeric values to calculate the estimate progress.
/// </summary>
class IncrementalLoadingProgressProcessor extends ProgressProcessorBase<IDocumentLoadingCallback, DocumentLoadingArgs>
{
    IncrementalLoadingProgressProcessor(
        DocumentBase docBase,
        IDocumentLoadingCallback progressCallback,
        /*LoadingStageType*/int stageType,
        int overallCount)
    {
    	super(docBase, progressCallback, (int)stageType);
	
        OverallCount = overallCount;
    }

    /// <summary>
    /// Notifies a client about overall progress.
    /// </summary>
    /// <param name="complete">When true then current stage treated as completed.</param>
    void execute(boolean complete) throws Exception
    {
        if (!isProgressSupported() || !hasCallback())
            return;

        LoadingProgressCalculator calc = new LoadingProgressCalculator();
        mCurrentCount = complete ? OverallCount : ++mCurrentCount;
        StageData stageData = getActualStageData(mCurrentCount, OverallCount, calc);

        // Create arguments and notify for a client.
        executeCallback((stageData != null) ? new DocumentLoadingArgs(stageData, calc) : null);
    }

    protected /*override*/ void executeCallback(DocumentLoadingArgs args) throws Exception
    {
        if (args == null)
            return;

        try
        {
            Callback.notify(args);
        }
        catch (Exception ex)
        {
            // Exception treated as processing cancellation.
            throw new CancellationException(ex);
        }
    }

    protected /*override*/ boolean isProgressSupported()
    {
        return super.isProgressSupported() && ProgressUtils.isLoadingProgressSupported(Doc.getOriginalLoadFormat());
    }

    /// <summary>
    /// Counter of progress value.
    /// </summary>
    private int mCurrentCount = 0;

    /// <summary>
    /// Overall amount value which means that current stage is complete.
    /// </summary>
    protected int OverallCount;
}

