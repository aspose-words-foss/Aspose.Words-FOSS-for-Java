// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/12/2011 by Andrey Soldatov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;


/// <summary>
/// Date and time related utility methods.
/// </summary>
public /*static*/ class DateTimeUtil
{
	/* Simulation of static class by using private constructor */
	private DateTimeUtil()
	{}

    /// <summary>
    /// <para>Sets Test flag. This flag is used to make behavior of some methods deterministic for test purposes.
    /// Call this method before running any tests which can be connected to methods of this class.</para>
    /// <para>For now, it's used by <see cref="GetNow"/> and <see cref="ToLocalTime"/> only
    /// to ensure hardcoded date/time in the updated fields so the golds remain unchanged.</para>
    /// </summary>
    public static void setTestMode()
    {
        gTestMode = true;
    }

    /// <summary>
    /// You should mark tests that don't use test mode as NonParallelizable.
    /// </summary>
    public static void setTestMode(boolean testMode)
    {
        gTestMode = testMode;
    }

    /// <summary>
    /// Use this method instead of DateTime.FromFileTimeUtc because it is only available in .NET 1.1.
    /// This code is disassembled from mscorlib.
    /// </summary>
    public static DateTime fromFileTimeUtc(long fileTime, String paramName)
    {
        if (fileTime < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + paramName);

        long num1 = fileTime + WIN_32_EPOCH;
        return new DateTime(num1, DateTimeKind.UTC);
    }

    /// <summary>
    /// Use this method instead of DateTime.ToFileTimeUtc because it is only available in .NET 1.1.
    /// This code is disassembled from mscorlib.
    /// </summary>
    public static long toFileTimeUtc(DateTime value, String paramName)
    {
        long num1 = value.getTicks() - WIN_32_EPOCH;
        if (num1 < 0)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + paramName);

        return num1;
    }

    /// <summary>
    /// Returns the current date/time if running is for "real". Returns a hardcoded constant if running
    /// unit tests which allows unit tests with golds to remain without changes.
    /// </summary>
    public static DateTime getNow()
    {
        if (gTestMode)
            return UNIT_TESTING_DATE_TIME;

        return DateTime.getNow();
    }

    /// <summary>
    /// Returns the specified date/time as is or converts it to local time depending on whether unit testing is being
    /// performed.
    /// </summary>
    public static DateTime toLocalTime(DateTime dateTime)
    {
        if (gTestMode)
            return dateTime;

        return dateTime.toLocalTime();
    }

    /// <summary>
    /// Returns the specified date/time as is or converts it to universal time depending on whether unit testing is being
    /// performed.
    /// </summary>
    public static DateTime toUniversalTime(DateTime dateTime)
    {
        if (gTestMode)
            return dateTime;

        return dateTime.toUniversalTime();
    }

    /// <summary>
    /// All tests which can be connected to methods depending on this flag must call <see cref="SetTestMode"/>
    /// to set this flag to <c>true</c>.
    /// Neither results of tests nor release code in .NET or Java
    /// will change (if <see cref="SetTestMode"/> was not called from release code that MUST NOT be done).
    /// </summary>
    private static boolean gTestMode = false;

    public static /*final*/ DateTime UNIT_TESTING_DATE_TIME = new DateTime(2006, 1, 5, 19, 9, 1);

    private static final long WIN_32_EPOCH = 0x701ce1722770000L;
}

