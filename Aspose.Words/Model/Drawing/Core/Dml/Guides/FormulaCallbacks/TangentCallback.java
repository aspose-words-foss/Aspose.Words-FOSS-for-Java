// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;

class TangentCallback implements IDmlTwoArgumentFormulaCallback
{
    public double calculate(double x, double y)
    {
        y = ConvertUtilCore.dmlAnglesToRadians(y);
        return x * Math.tan(y);
    }
}
