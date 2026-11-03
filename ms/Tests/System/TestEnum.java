// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2011 by Konstantin Sidorenko
// 2015/12/28 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.System.DayOfWeek;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestEnum
{
    @Test
    public void testGetName()
    {
        msAssert.areEqual("Sunday", DayOfWeek.getName(DayOfWeek.SUNDAY));
        msAssert.areEqual("Monday", DayOfWeek.getName(DayOfWeek.MONDAY));
        msAssert.areEqual("Tuesday", DayOfWeek.getName(DayOfWeek.TUESDAY));
        msAssert.areEqual("Wednesday", DayOfWeek.getName(DayOfWeek.WEDNESDAY));
        msAssert.areEqual("Thursday", DayOfWeek.getName(DayOfWeek.THURSDAY));
        msAssert.areEqual("Friday", DayOfWeek.getName(DayOfWeek.FRIDAY));
        msAssert.areEqual("Saturday", DayOfWeek.getName(DayOfWeek.SATURDAY));
    }
}

