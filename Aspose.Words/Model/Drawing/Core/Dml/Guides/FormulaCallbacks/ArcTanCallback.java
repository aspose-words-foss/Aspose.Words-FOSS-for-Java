// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;

class ArcTanCallback implements IDmlTwoArgumentFormulaCallback
{
    public double calculate(double x, double y)
    {
        double result = Math.atan2(y, x);
        return ConvertUtilCore.radiansToDmlAngles(result);
    }
}
