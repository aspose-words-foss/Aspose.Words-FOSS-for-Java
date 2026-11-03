// A Java port, made by Aspose, of a .NET Framework class. Microsoft publishes the source of that class
// under the MIT License at https://github.com/microsoft/referencesource : System/services/monitoring/system/diagnosticts/Stopwatch.cs
//
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// Konstantin Sidorenko, 29.03.2013

package com.aspose.ms.System.Diagnostics;

import com.aspose.ms.System.TimeSpan;

// .Net System.Diagnostics.Stopwatch adapted from Rotor.
// System.nanoTime() is used to get timestamps, immediately converted to ".Net ticks" i.e. 100 nanosecond units.
public class Stopwatch
{
    private static final long NANO_PER_MILLIS = 1000000L;
    private static final long NANO_PER_TICKS = 100L;
    private static final long TICKS_PER_MILLISECOND = 10000;

    private long elapsed;
    private long startTimeStamp;
    private boolean isRunning;

    public Stopwatch()
    {
        reset();
    }

    public void start()
    {
        // Calling start on a running Stopwatch is a no-op.
        if (!isRunning)
        {
            startTimeStamp = getTimestamp();
            isRunning = true;
        }
    }

    public static Stopwatch startNew()
    {
        Stopwatch s = new Stopwatch();
        s.start();
        return s;
    }

    public void stop()
    {
        // Calling stop on a stopped Stopwatch is a no-op.
        if (isRunning)
        {
            long endTimeStamp = getTimestamp();
            long elapsedThisPeriod = endTimeStamp - startTimeStamp;
            elapsed += elapsedThisPeriod;
            isRunning = false;
        }
    }

    public void reset()
    {
        elapsed = 0;
        isRunning = false;
        startTimeStamp = 0;
    }

    public boolean isRunning() { return isRunning; }

    public TimeSpan getElapsed() { return new TimeSpan(getElapsedDateTimeTicks()); }

    public long getElapsedMilliseconds() { return getElapsedDateTimeTicks() / TICKS_PER_MILLISECOND; }

    public long getElapsedTicks() { return getRawElapsedTicks(); }

    // Gets the current number of ticks in the timer mechanism.
    public static long getTimestamp()
    {
        //JAVA: .Net tick is 100 nanoseconds.
        return System.nanoTime() / NANO_PER_TICKS;
    }

    // Get the elapsed ticks.
    private long getRawElapsedTicks()
    {
        long timeElapsed = elapsed;

        if (isRunning)
        {
            // If the StopWatch is running, add elapsed time since
            // the Stopwatch is started last time.
            long currentTimeStamp = getTimestamp();
            long elapsedUntilNow = currentTimeStamp - startTimeStamp;
            timeElapsed += elapsedUntilNow;
        }
        return timeElapsed;
    }

    // Get the elapsed ticks.
    private long getElapsedDateTimeTicks()
    {
        long rawTicks = getRawElapsedTicks();
        return rawTicks;
    }
}
