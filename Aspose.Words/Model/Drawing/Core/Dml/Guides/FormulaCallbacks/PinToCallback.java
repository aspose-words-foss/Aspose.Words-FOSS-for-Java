// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class PinToCallback implements IDmlThreeArgumentFormulaCallback
{
    public double calculate(double x, double y, double z)
    {
        if (y < x)
            return x;
        else if (y > z)
            return z;
        else
            return y;
    }
}
