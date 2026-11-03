// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.HashMap;
import java.util.Map;


/// <summary>
/// Base class for an overall progress calculator.
/// </summary>
class ProgressCalculator
{
    /// <summary>
    /// Ctr.
    /// </summary>
    protected ProgressCalculator()
    {
        StagePercentageMap = new HashMap<Integer, Double>();
    }

    /// <summary>
    /// Returns approximate overall progress.
    /// </summary>
    /// <param name="currentStage">Current stage data.</param>
    /// <returns>Approximate overall progress.</returns>
    double getEstimatedProgress(StageData currentStage)
    {
        double totalProgress = 0.0;

        for(int i = 0; i < getStagesCount(); i++)
        {
            int nextStageType = AvailableStages[i];
            double nextStageWeight = StagePercentageMap.get(nextStageType);

            if (((nextStageType) == (currentStage.getStageType())))
            {
                // Map progress to current stage weight.
                totalProgress += currentStage.getEstimatedProgress() * nextStageWeight / 100.0d;
                break;
            }

            // Next stage already completed.
            totalProgress += nextStageWeight;
        }

        return totalProgress;
    }

    /// <summary>
    /// Returns index in sequence of the specified stages.
    /// </summary>
    /// <param name="currentStageType"></param>
    /// <returns>Stage index. "-1" when specified stage was not found.</returns>
    int getStageIndex(int currentStageType)
    {
        for (int i = 0; i < AvailableStages.length; ++i)
        {
            if (((currentStageType) == (AvailableStages[i])))
                return i;
        }

        // Not found.
        return -1;
    }

    /// <summary>
    /// Returns previous stage type.
    /// </summary>
    /// <param name="currentStageIndex">Index of stage in the sequence.</param>
    /// <returns>Stage type. Throws exception when specified stage is first in the sequence.</returns>
    int getPrevStageType(int currentStageIndex)
    {
        if (currentStageIndex <= 0)
            throw new IllegalArgumentException("Progress stage has not previous stages.");

        if (currentStageIndex > AvailableStages.length - 1)
            throw new IllegalArgumentException("There is not a progress stage with specified index.");

        return AvailableStages[currentStageIndex - 1];
    }

    /// <summary>
    /// Overall progress precision.
    /// </summary>
    static final int PRECISION = 2;

    /// <summary>
    /// Available stages in progress.
    /// </summary>
    protected int[] AvailableStages;

    /// <summary>
    /// Map of stage type to weight in overall progress.
    /// </summary>
    protected /*final*/ Map<Integer, Double> StagePercentageMap;

    /// <summary>
    /// Specifies overall stages count.
    /// </summary>
    private int getStagesCount() { return AvailableStages.length; }
}

