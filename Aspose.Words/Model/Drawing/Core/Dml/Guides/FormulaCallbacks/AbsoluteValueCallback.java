// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class AbsoluteValueCallback implements IDmlOneArgumentFormulaCallback
{
    public double calculate(double x)
    {
        if (x < 0)
            return -1.0*x;
        else
            return x;
    }
}
