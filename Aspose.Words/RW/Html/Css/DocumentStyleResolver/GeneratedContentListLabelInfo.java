// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2022 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToIntDictionary;
import com.aspose.Debug;
import com.aspose.StringUtil;


class GeneratedContentListLabelInfo
{
    GeneratedContentListLabelInfo(
        String numberFormat,
        IntToIntDictionary levelNumberToNumberValueMap,
        IntToIntDictionary levelNumberToNumberStyleMap)
    {
        Debug.assertTrue(StringUtil.hasChars(numberFormat));
        Debug.assertTrue(levelNumberToNumberValueMap != null);
        Debug.assertTrue(levelNumberToNumberStyleMap != null);

        mNumberFormat = numberFormat;
        mLevelNumberToNumberValueMap = levelNumberToNumberValueMap;
        mLevelNumberToNumberStyleMap = levelNumberToNumberStyleMap;
    }

    String getNumberFormat() { return mNumberFormat; };

    private String mNumberFormat;

    IntToIntDictionary getLevelNumberToNumberValueMap() { return mLevelNumberToNumberValueMap; };

    private IntToIntDictionary mLevelNumberToNumberValueMap;

    IntToIntDictionary getLevelNumberToNumberStyleMap() { return mLevelNumberToNumberStyleMap; };

    private IntToIntDictionary mLevelNumberToNumberStyleMap;
}

