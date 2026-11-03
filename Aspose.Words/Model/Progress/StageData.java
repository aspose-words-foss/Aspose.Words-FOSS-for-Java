// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents state of a progress stage.
/// </summary>
/// <remarks>
/// The process is split into stages. Approximate progress may be calculated for every stage.
/// And these parts compound to overall progress estimate.
/// </remarks>
class StageData
{
    /// <summary>
    /// Ctr.
    /// </summary>
    StageData(int stageType, double progress)
    {
        mStageType = stageType;
        mEstimatedProgress = progress;
    }

    /// <summary>
    /// Stage progress status.
    /// </summary>
    /// <remarks>
    /// Can take a value from 0 to 100.
    /// </remarks>>
    double getEstimatedProgress() { return mEstimatedProgress; };

    private double mEstimatedProgress;

    /// <summary>
    /// Current stage type.
    /// </summary>
    int getStageType() { return mStageType; };

    private int mStageType;

}

