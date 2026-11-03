// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Progress processor for the saving stages.
/// Uses document paragraph count to calculate estimated progress.
/// </summary>
class SavingProgressProcessor extends
    ProgressProcessorBase<IDocumentSavingCallback, DocumentSavingArgs>
{
    SavingProgressProcessor(
        DocumentBase docBase,
        SaveOptions saveOptions,
        /*SavingStageType*/int stageType)
    {
    	super(docBase, saveOptions.getProgressCallback(), (int)stageType);
	
        mSaveFormat = saveOptions.getSaveFormat();

        if (hasCallback() && isProgressSupported())
            mTotalParaCount = Doc.getChildNodes(NodeType.PARAGRAPH, true).getCount();
    }

    /// <summary>
    /// Notifies a client about next step of overall progress.
    /// </summary>
    void step() throws Exception
    {
        step(1);
    }

    /// <summary>
    /// Notifies a client about next step of overall progress.
    /// </summary>
    void step(int stepCount) throws Exception
    {
        mProgressNodesCounter += stepCount - 1; // It will be also incremented in Execute.
        execute(false);
    }

    /// <summary>
    /// Notifies a client about overall progress completion.
    /// </summary>
    void complete() throws Exception
    {
        execute(true);
    }

    /// <summary>
    /// Notify a client using specified callback.
    /// </summary>
    protected /*override*/ void executeCallback(DocumentSavingArgs args) throws Exception
    {
        if (args == null)
            return;

        Callback.notify(args);
    }

    /// <summary>
    /// Determines that progress supported in current context.
    /// </summary>
    protected /*override*/ boolean isProgressSupported()
    {
        return super.isProgressSupported() && ProgressUtils.isSavingProgressSupported(mSaveFormat);
    }

    /// <summary>
    /// Notifies a client about overall progress.
    /// </summary>
    /// <param name="complete">When true then current stage treated as completed.</param>
    private void execute(boolean complete) throws Exception
    {
        if (!isProgressSupported() || !hasCallback())
            return;

        SavingProgressCalculator calc = new SavingProgressCalculator();
        mProgressNodesCounter = complete ? mTotalParaCount : ++mProgressNodesCounter;
        StageData stageData = getActualStageData(mProgressNodesCounter, mTotalParaCount, calc);

        // Create arguments and notify a client.
        executeCallback((stageData != null) ? new DocumentSavingArgs(stageData, calc) : null);
    }

    /// <summary>
    /// Overall amount of paragraphs which were added to the model during reading.
    /// </summary>
    private /*final*/ int mTotalParaCount;

    /// <summary>
    /// Counter of processed nodes.
    /// </summary>
    private int mProgressNodesCounter = 0;

    /// <summary>
    /// Destination format.
    /// </summary>
    private /*final*/ /*SaveFormat*/int mSaveFormat;
}

