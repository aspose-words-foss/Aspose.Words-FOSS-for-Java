// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;


/// <summary>
/// Saving overall progress calculator.
/// </summary>
class SavingProgressCalculator extends ProgressCalculator
{
    /// <summary>
    /// Ctr.
    /// </summary>
    SavingProgressCalculator()
    {
    	super();
	
                            AvailableStages = (int[])SavingStageType.getValues();
        
        // Sum of values must be 100%.
        msDictionary.add(StagePercentageMap, (int)SavingStageType.PROCESSING, 30.0d);
        msDictionary.add(StagePercentageMap, (int)SavingStageType.SAVING, 70.0d);
    }
}

