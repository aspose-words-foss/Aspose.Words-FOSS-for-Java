package com.aspose.words;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;


/// <summary>
/// Tests members of the <see cref="AxisBound"/> class.
/// </summary>
@Test
public class TestAxisBoundsWithDate
{
    private static final int YEAR_CORRECTION = 1900;
    private static final int MONTH_CORRECTION = 1;

    @Test
    public void testCreation()
    {
        AxisBound bound = new AxisBound(1d);
        Assert.assertFalse(bound.isAuto());
        msAssert.areEqual(1d, bound.getValue());
        msAssert.areEqual(new Date(1899 - YEAR_CORRECTION, 12 - MONTH_CORRECTION, 31), bound.getValueAsDate());

        Date datetime = new Date(2018 - YEAR_CORRECTION, 5 - MONTH_CORRECTION, 25);
        bound = new AxisBound(datetime);
        Assert.assertFalse(bound.isAuto());
        msAssert.areEqual(datetime, bound.getValueAsDate());

        Assert.assertTrue(new AxisBound().isAuto());
    }

    @Test
    public void testEquals()
    {
        AxisBound bound1 = new AxisBound(1d);
        AxisBound bound2 = new AxisBound(1d);
        AxisBound bound3 = new AxisBound(1.00001d);
        Assert.assertTrue(bound1.equals(bound2));
        Assert.assertTrue(bound2.equals(bound1));
        Assert.assertFalse(bound1.equals(bound3));
        Assert.assertFalse(bound3.equals(bound1));
        Assert.assertFalse(bound1.equals(new AxisBound()));

        bound1 = new AxisBound(new Date(2000 - YEAR_CORRECTION, 1 - MONTH_CORRECTION, 1));
        bound2 = new AxisBound(new Date(2000 - YEAR_CORRECTION, 1 - MONTH_CORRECTION, 1));
        Assert.assertTrue(bound1.equals(bound2));
        Assert.assertTrue(bound2.equals(bound1));
        Assert.assertFalse(bound1.equals(new AxisBound()));

        bound1 = new AxisBound();
        bound2 = new AxisBound();
        Assert.assertTrue(bound1.equals(bound2));
        Assert.assertTrue(bound2.equals(bound1));
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void testCreationWithWrongDate()
    {
        new AxisBound(DateTime.MinValue.toJava());
    }

    @Test
    public void testOverflowAsDate()
    {
        AxisBound bound1 = new AxisBound(10000000.0);
        msAssert.areEqual(DateTime.MinValue.toJava(), bound1.getValueAsDate());

        AxisBound bound2 = new AxisBound(-10000000);
        msAssert.areEqual(DateTime.MinValue.toJava(), bound2.getValueAsDate());
    }
}

