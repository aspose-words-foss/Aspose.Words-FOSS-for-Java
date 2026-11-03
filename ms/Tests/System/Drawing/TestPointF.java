// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2012 by Konstantin Sidorenko
// 04/05/2016 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Drawing.msPointF;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestPointF
{
    @Test
    public void testCtor()
    {
        testCtor(-758, -6545);
        testCtor(22348f, -6545);
        testCtor(0f, -6545);
        testCtor(-6545, 0f);
        testCtor(0f, 0f);
        testCtor(0f, Float.NaN);
        testCtor(Float.NaN, 0f);
        testCtor(Float.NEGATIVE_INFINITY, 0f);
        testCtor(0f, Float.NEGATIVE_INFINITY);
        testCtor(Float.POSITIVE_INFINITY, 0f);
        testCtor(0f, Float.POSITIVE_INFINITY);
        testCtor((-Float.MAX_VALUE), 0f);
        testCtor(0f, (-Float.MAX_VALUE));
        testCtor(Float.MAX_VALUE, 0f);
        testCtor(0f, Float.MAX_VALUE);
        testCtor(Float.MAX_VALUE, Float.MAX_VALUE);
        testCtor((-Float.MAX_VALUE), (-Float.MAX_VALUE));
        testCtor(Float.MAX_VALUE, (-Float.MAX_VALUE));
        testCtor((-Float.MAX_VALUE), Float.MAX_VALUE);
    }

    private void testCtor(float x, float y)
    {
        /*PointF*/long point = msPointF.ctor(x, y);
        msAssert.areEqual(msPointF.getX(point), x);
        msAssert.areEqual(msPointF.getY(point), y);
    }
}

