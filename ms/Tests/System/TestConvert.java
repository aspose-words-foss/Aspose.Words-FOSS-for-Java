// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2007 by Konstantin Sidorenko
// 22/12/2015 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Convert;
import com.aspose.ms.System.DayOfWeek;
import com.aspose.ms.System.VarEnum;
import com.aspose.words.net.System.OverflowException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.NumberFormatException;


@Test
public class TestConvert
{
    @Test
    public void testConvertToInt32()
    {
        msAssert.areEqual(Convert.toInt32(null), 0);
        msAssert.areEqual(Convert.toInt32FromByte((byte)8), 8);
        msAssert.areEqual(Convert.toInt32((short)9), 9);
        msAssert.areEqual(Convert.toInt32(10), 10);

        msAssert.areEqual(Convert.toInt32(32L), 32);
        msAssert.areEqual(Convert.toInt32(8.2F), 8);
        msAssert.areEqual(Convert.toInt32(8.2), 8);

        msAssert.areEqual(Convert.toInt32("333"), 333);
        msAssert.areEqual(Convert.toInt32(true), 1);
        msAssert.areEqual(Convert.toInt32(false), 0);

        //simple ordinal (non-value) enums
        msAssert.areEqual(Convert.toInt32(DayOfWeek.SUNDAY), 0);
        msAssert.areEqual(Convert.toInt32(DayOfWeek.MONDAY), 1);

        //former value enums
        msAssert.areEqual(Convert.toInt32(VarEnum.VT_NULL), 1);
        msAssert.areEqual(Convert.toInt32(VarEnum.VT_FILETIME), 64);
    }

    @Test
    public void testConvertToInt32Overflow()
    {
        long mBigPositive = (long)Integer.MAX_VALUE << 2;
        long mBigNegative = (long)Integer.MIN_VALUE << 2;

        int countTried = 0;
        int countCatched = 0;

        try
        { countTried++; Convert.toInt32(mBigPositive); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(mBigNegative); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32((float)mBigPositive); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32((float)mBigNegative); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32((double)mBigPositive); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32((double)mBigNegative); }
        catch (OverflowException e) { countCatched++; }


        try
        { countTried++; Convert.toInt32(BigDecimal.valueOf(mBigPositive)); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(BigDecimal.valueOf(mBigNegative)); }
        catch (OverflowException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(BigInteger.valueOf(mBigPositive)); }
        catch (ClassCastException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(BigInteger.valueOf(mBigNegative)); }
        catch (ClassCastException e) { countCatched++; }

        // Java throws NumberFormatException both for overflow and bad format:

        try
        { countTried++; Convert.toInt32("333.3"); }
        catch (NumberFormatException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(Long.toString(mBigPositive)); }
        catch (NumberFormatException e) { countCatched++; }

        try
        { countTried++; Convert.toInt32(Long.toString(mBigNegative)); }
        catch (NumberFormatException e) { countCatched++; }

        msAssert.areEqual(countCatched, countTried);
    }
}

