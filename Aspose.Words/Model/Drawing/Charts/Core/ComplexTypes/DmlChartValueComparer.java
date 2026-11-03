// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2022 by Ilya Egorov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.MathUtil;
import com.aspose.ms.System.msDouble;
import com.aspose.ms.System.msInt;


/// <summary>
/// Defines a method that compares two <see cref="DmlChartValue"/> objects.
/// </summary>
class DmlChartValueComparer implements Comparator<DmlChartValue>
{
    public int compare(DmlChartValue x, DmlChartValue y)
    {
        if (!MathUtil.isZero(msDouble.compareTo(x.getValue(), y.getValue())))
            return msDouble.compareTo(x.getValue(), y.getValue());
        else if (msInt.compareTo(x.getIndex(), y.getIndex()) != 0)
            return msInt.compareTo(x.getIndex(), y.getIndex());
        else
            return 0;
    }
}

