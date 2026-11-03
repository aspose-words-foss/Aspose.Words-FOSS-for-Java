// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import com.aspose.words.ref.RefDouble;


class DmlGuideValueProviderStub implements IDmlGuideValueProvider
{
    public double getValue(String guideName)
    {
        double result = 0.0;
        RefDouble referenceToResult = new RefDouble(result);
        boolean outRefCondition0 = !msDictionary.tryGetValue(mNameToValue, guideName, /*out*/ referenceToResult);
        result = referenceToResult.get();
        if (outRefCondition0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "guideName");
        return result;
    }

    public DmlGuideValueProviderStub add(String guideName, double value)
    {
        mNameToValue.put(guideName, value);
        return this;
    }

    private /*final*/ HashMap<String, Double> mNameToValue = new HashMap<String, Double>();
}
