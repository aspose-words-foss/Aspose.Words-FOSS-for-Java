// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2010 by Konstantin Sidorenko
// 13/01/2016 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msMath;
import com.aspose.ms.System.MidpointRounding;


@Test
public class TestMsMath
{
    @Test
    public void testRound()
    {
        msAssert.areEqual(msMath.round(0d, 0, MidpointRounding.AWAY_FROM_ZERO), 0d);
        msAssert.areEqual(msMath.round(-0.5d, 0, MidpointRounding.AWAY_FROM_ZERO), -1d);
        msAssert.areEqual(msMath.round(0.5d, 0, MidpointRounding.AWAY_FROM_ZERO), 1d);
        msAssert.areEqual(msMath.round(0.19999d, 2, MidpointRounding.AWAY_FROM_ZERO), 0.2d);
        msAssert.areEqual(msMath.round(-0.19999d, 2, MidpointRounding.AWAY_FROM_ZERO), -0.2d);
        msAssert.areEqual(msMath.round(1.5d, 2, MidpointRounding.AWAY_FROM_ZERO), 1.5d);
        msAssert.areEqual(msMath.round(-1.5d, 2, MidpointRounding.AWAY_FROM_ZERO), -1.5d);
        msAssert.areEqual(msMath.round(1.5d, 0, MidpointRounding.AWAY_FROM_ZERO), 2d);
        msAssert.areEqual(msMath.round(-1.5d, 0, MidpointRounding.AWAY_FROM_ZERO), -2d);
        msAssert.areEqual(msMath.round(2.4d, 1, MidpointRounding.AWAY_FROM_ZERO), 2.4d);
        msAssert.areEqual(msMath.round(-2.4d, 1, MidpointRounding.AWAY_FROM_ZERO), -2.4d);
        msAssert.areEqual(msMath.round(2.4d, 0, MidpointRounding.AWAY_FROM_ZERO), 2d);
        msAssert.areEqual(msMath.round(-2.4d, 0, MidpointRounding.AWAY_FROM_ZERO), -2d);
    }

    @Test
    public void testRoundInfinity()
    {
        double roundPositive = msMath.round(Double.POSITIVE_INFINITY);
        double roundNegative = msMath.round(Double.NEGATIVE_INFINITY);

        msAssert.areEqual(msMath.round(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY);
        msAssert.areEqual(msMath.round(Double.NEGATIVE_INFINITY), Double.NEGATIVE_INFINITY);
        msAssert.areEqual(msMath.round(Double.NaN), Double.NaN);
    }

    @Test
    public void testMathPrecession()
    {
        msAssert.areEqual(MathUtil.getPrecision(0.0), 0);
        msAssert.areEqual(MathUtil.getPrecision(5.0), 0);
        msAssert.areEqual(MathUtil.getPrecision(1.0E-15), -16);
        msAssert.areEqual(MathUtil.getPrecision(1.0E-20), -21);
    }
}

