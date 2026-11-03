// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Implements processor for document reading stage and uses stream to calculate the estimate progress.
/// </summary>
class LoadingProgressProcessor extends
    ProgressProcessorBase<IDocumentLoadingCallback, DocumentLoadingArgs>
{
    LoadingProgressProcessor(
        DocumentBase docBase,
        LoadOptions loadOptions)
    {
    	super(docBase, loadOptions.getProgressCallback(), (int)LoadingStageType.READING);
	
    }

    /// <summary>
    /// Notifies a client about overall progress.
    /// </summary>
    void execute(Stream stream) throws Exception
    {
        execute(stream, false);
    }

    /// <summary>
    /// Notifies a client about overall progress.
    /// </summary>
    void execute(Stream stream, boolean complete) throws Exception
    {
        if (!isProgressSupported() || !hasCallback() || (stream == null))
            return;

        LoadingProgressCalculator calc = new LoadingProgressCalculator();
        StageData stageData = getActualStageData(stream, calc, complete);

        // Create arguments and notify a client.
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
}

