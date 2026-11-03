// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class SquareRootCallback implements IDmlOneArgumentFormulaCallback
{
    public double calculate(double x)
    {
        // According to MS-OI29500: Microsoft Office Implementation Information for ISO/IEC-29500 Standard Compliance
        return Math.sqrt(Math.abs(x));
    }
}
