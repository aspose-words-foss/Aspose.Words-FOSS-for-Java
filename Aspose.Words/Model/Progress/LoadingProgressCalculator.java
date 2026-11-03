// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Loading overall progress calculator.
/// </summary>
class LoadingProgressCalculator extends ProgressCalculator
{
    /// <summary>
    /// Ctr.
    /// </summary>
    LoadingProgressCalculator()
    {
    	super();
	
                            AvailableStages = (int[])LoadingStageType.getValues();
        
        // Sum of values must be 100%.
        msDictionary.add(StagePercentageMap, (int)LoadingStageType.READING, 70.0d);
        msDictionary.add(StagePercentageMap, (int)LoadingStageType.PROCESSING, 30.0d);
    }
}

