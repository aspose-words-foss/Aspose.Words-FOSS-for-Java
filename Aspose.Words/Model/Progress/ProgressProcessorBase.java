// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import com.aspose.ms.System.IO.Stream;
import com.aspose.DoublePal;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;


/// <summary>
/// Implements base ability to notify a client from the AW modules about a progress.
/// </summary>
/// <typeparam name="CallbackType">A type which implements custom method called during a document lifetime process.</typeparam>
/// <typeparam name="ArgsType">Type of an arguments passing into callback method.</typeparam>
abstract class ProgressProcessorBase<CallbackType extends Object, ArgsType extends Object>
{
    /// <summary>
    /// Hide default ctr.
    /// </summary>
    private ProgressProcessorBase() { }

    /// <summary>
    /// Ctr.
    /// </summary>
    protected ProgressProcessorBase(DocumentBase docBase, CallbackType callback, int stageType)
    {
        Debug.assertTrue(docBase != null);

        Document doc = ms.as(docBase, Document.class);
        if (doc == null)
            return;

        Doc = doc;
        mStageType = stageType;
        Callback = callback;
    }

    protected StageData getActualStageData(
        int processedNodesCount,
        int totalNodesCount,
        ProgressCalculator progressCalculator)
    {
        int currentStageIndex = progressCalculator.getStageIndex(mStageType);

        double prevProgress = getPrevProgress(mStageType, currentStageIndex, progressCalculator);
        double currentStageProgress = getStageProgress(processedNodesCount, totalNodesCount, prevProgress);

        StageData stageData = getActualStageDataCore(currentStageProgress, progressCalculator);
        return stageData;
    }

    protected StageData getActualStageData(
        Stream stream,
        ProgressCalculator progressCalculator,
        boolean complete) throws Exception
    {
        double currentStageProgress = complete ? COMPLETED_PROGRESS_PERCENTAGES : getStageProgress(stream);
        StageData stageData = getActualStageDataCore(currentStageProgress, progressCalculator);

        return stageData;
    }

    protected abstract void executeCallback(ArgsType args) throws Exception;

    /// <summary>
    /// Determines that a client may be notified about the progress in current context.
    /// </summary>
    protected /*virtual*/ boolean isProgressSupported()
    {
        // Filter glossary documents.
        if (Doc == null)
            return false;

        return true;
    }

    private StageData getActualStageDataCore(double stageProgress,
        ProgressCalculator progressCalculator)
    {
        // 1. Create stage data.
        int stageIndex = progressCalculator.getStageIndex(mStageType);
        Debug.assertTrue(stageIndex >= 0);

        StageData stageData = new StageData(mStageType, stageProgress);

        // 2. Ignore notification when rounded overall progress is not changed.
        // It is necessary to avoid insignificant progress notifications.
        double overallProgress = progressCalculator.getEstimatedProgress(stageData);
        double prevOverallProgress = getPrevProgress(OVERALL_KEY, stageIndex, progressCalculator);

        final int PRECISION = ProgressCalculator.PRECISION;
        double roundedProgress = DoublePal.trim(overallProgress, PRECISION);
        double prevRoundedProgress = DoublePal.trim(prevOverallProgress, PRECISION);

        if (prevRoundedProgress >= roundedProgress)
            return null;

        // 3. Update progress map (for current stage and overall value).
        addOrUpdateProgressToMap(mStageType, stageProgress);
        addOrUpdateProgressToMap(OVERALL_KEY, overallProgress);

        return stageData;
    }

    private void addOrUpdateProgressToMap(int stageType, double progress)
    {
        if (mProgressByStages.containsKey(stageType))
            mProgressByStages.put(stageType, progress);
        else
            msDictionary.add(mProgressByStages, stageType, progress);
    }

    /// <summary>
    /// Returns a stage progress based on the stream position.
    /// </summary>
    private static double getStageProgress(Stream stream) throws Exception
    {
        Debug.assertTrue(stream.canSeek());

        if (stream.getLength() == 0)
            return COMPLETED_PROGRESS_PERCENTAGES;

        double percentages = (double)stream.getPosition() / stream.getLength() * 100.0d;
        return percentages;
    }

    /// <summary>
    /// Returns a stage progress based on the processed nodes count.
    /// </summary>
    private static double getStageProgress(int processedNodesCount, int totalNodesCount, double prevProgress)
    {
        Debug.assertTrue(processedNodesCount >= 0);

        // Do not allow exceed 100% for a progress.
        if (processedNodesCount >= totalNodesCount)
            return COMPLETED_PROGRESS_PERCENTAGES;

        double percentages = (double)processedNodesCount / totalNodesCount * 100.0d;

        // Do not rollback a progress.
        percentages = Math.max(percentages, prevProgress);
        Debug.assertTrue(percentages <= COMPLETED_PROGRESS_PERCENTAGES);

        return percentages;
    }

    private double getPrevProgress(
        int stageType,
        int stageIndex,
        ProgressCalculator progressCalculator)
    {
        double prevProgress = 0.0d;
        if (mProgressByStages.containsKey(stageType))
        {
            prevProgress = mProgressByStages.get(stageType);
        }
        else if (((stageType) == (OVERALL_KEY)) && (stageIndex > 0))
        {
            // Looks like the processor did not update data about overall progress yet.
            // I.e. it is first notification on the current stage.
            int prevStageType = progressCalculator.getPrevStageType(stageIndex);
            prevProgress = progressCalculator.getEstimatedProgress(
                new StageData(prevStageType, COMPLETED_PROGRESS_PERCENTAGES));
        }

        return prevProgress;
    }

    protected boolean hasCallback() { return Callback != null; }

    protected /*final*/ Document Doc;
    protected /*final*/ CallbackType Callback;
    private /*final*/ int mStageType;

    /// <summary>
    /// Stores "Stage type -> Progress" map.
    /// </summary>
    private /*final*/ HashMap<Integer, Double> mProgressByStages = new HashMap<Integer, Double>();

    /// <summary>
    /// Overall key for "stage type -> progress" mapping. Actually it is the stub and does not exist in the enumeration.
    /// </summary>
    private static final int OVERALL_KEY = 0;

    private static final double COMPLETED_PROGRESS_PERCENTAGES = 100.0d;
}

