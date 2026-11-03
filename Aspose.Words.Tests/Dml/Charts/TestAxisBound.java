// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/05/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Tests members of the <see cref="AxisBound"/> class.
/// </summary>
@Test
public class TestAxisBound
{
    @Test
    public void testCreation()
    {
        AxisBound bound = new AxisBound(1d);
        msAssert.isFalse(bound.isAuto());
        msAssert.areEqual(1d, bound.getValue());
        msAssert.areEqual(new DateTime(1899, 12, 31), bound.getValueAsDateInternal());

        DateTime datetime = new DateTime(2018, 5, 25);
        bound = new AxisBound(datetime);
        msAssert.isFalse(bound.isAuto());
        msAssert.areEqual(datetime.toOADate(), bound.getValue());
        msAssert.areEqual(datetime, bound.getValueAsDateInternal());

        msAssert.isTrue(new AxisBound().isAuto());
    }

    @Test
    public void testEquals()
    {
        AxisBound bound1 = new AxisBound(1d);
        AxisBound bound2 = new AxisBound(1d);
        AxisBound bound3 = new AxisBound(1.00001d);
        msAssert.isTrue(bound1.equals(bound2));
        msAssert.isTrue(bound2.equals(bound1));
        msAssert.isFalse(bound1.equals(bound3));
        msAssert.isFalse(bound3.equals(bound1));
        msAssert.isFalse(bound1.equals(new AxisBound()));

        bound1 = new AxisBound(DateTime.getToday());
        bound2 = new AxisBound(DateTime.getToday());
        msAssert.isTrue(bound1.equals(bound2));
        msAssert.isTrue(bound2.equals(bound1));
        msAssert.isFalse(bound1.equals(new AxisBound()));

        bound1 = new AxisBound();
        bound2 = new AxisBound();
        msAssert.isTrue(bound1.equals(bound2));
        msAssert.isTrue(bound2.equals(bound1));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCreationWithWrongDate()
    {
        new AxisBound(DateTime.MinValue);
    }

    @Test
    public void testOverflowAsDate()
    {
        AxisBound bound1 = new AxisBound(10000000.0);
        msAssert.areEqual(DateTime.MinValue, bound1.getValueAsDateInternal());

        AxisBound bound2 = new AxisBound(-10000000);
        msAssert.areEqual(DateTime.MinValue, bound2.getValueAsDateInternal());
    }
}

