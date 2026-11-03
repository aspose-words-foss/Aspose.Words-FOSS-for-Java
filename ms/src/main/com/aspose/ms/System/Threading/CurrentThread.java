// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 27/10/16 by Anatoly Sidorenko
// 03.12.2013 by Nail Islamov, Konstantin Sidorenko

package com.aspose.ms.System.Threading;

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.TimeSpan;

import java.util.*;

/**
 * Emulates .Net thread-isolated Locale settings.
 *
 * ThreadLocal is most effective for such things but... it can cause memory leaks
 * in some web-server environments that use thread pools and reusable threads
 * since ThreadLocal variable is automatically released only after parent Thread died.
 * (So launching Aspose.Words in such reusable thread will create new ThreadLocal variable
 * in addition to existing (non-deleted) variables from former AW launches)
 *
 * From other hand we can't use ThreadLocal.release() to manually release the object
 * since Aspose.Words hasn't finalizer.
 *
 * If customers will report memory problems with their server environment - we have to
 * change these formats to simple local variables.
 */
public class CurrentThread
{
    /**
     * Analog of .Net Thread.CurrentThread.CurrentCulture.
     * Returns thread-local CurrentCulture if it is set.
     * Otherwise derives it from the current Java VM default Locale.
     */
    public static msCultureInfo getCurrentCulture()
    {
        Long threadId = Thread.currentThread().getId();
        msCultureInfo culture = null;

        synchronized (CurrentThread.class)
        {
            culture = gThreadCulture.get(threadId);
            if (culture == null)
            {
                msCultureInfo defaultCulture = getDefaultCulture();
                if (defaultCulture == null)
                    defaultCulture = msCultureInfo.getInvariantCulture();

                setCurrentCulture(defaultCulture);
                culture = gThreadCulture.get(threadId);

                if (culture == null)
                    culture = msCultureInfo.getInvariantCulture();
            }
        }

        return culture;
    }

    public static void setCurrentCulture(String cultureName)
    {
        setCurrentCulture(new msCultureInfo(cultureName));
    }

    public static void setCurrentCulture(msCultureInfo culture)
    {
        Long threadId = Thread.currentThread().getId();

        synchronized (CurrentThread.class)
        {
            if (culture == null)
                culture = getDefaultCulture();

            msCultureInfo threadCulture = gThreadCulture.get(threadId);
            if (threadCulture != null && threadCulture.getLCID().equals(culture.getLCID())
                    && threadCulture.getDateTimeFormat().equals(culture.getDateTimeFormat()))
                return;

            gThreadCulture.put(threadId, culture);
            clearIfNecessary();
        }
    }

    public static TimeZone getTimeZone()
    {
        Long threadId = Thread.currentThread().getId();
        TimeZone timeZone = null;

        synchronized (CurrentThread.class)
        {
            timeZone = gThreadTimeZone.get(threadId);
            if (timeZone == null)
            {
                // If default TimeZone is changed we can return changed TimeZone
                timeZone = TimeZone.getDefault();
            }
        }

        return timeZone;
    }

    /**
     * Set thread-local time zone.
     */
    public static void setTimeZone(TimeZone timeZone)
    {
        Long threadId = Thread.currentThread().getId();

        synchronized (CurrentThread.class)
        {
            if (timeZone == null)
                timeZone = TimeZone.getDefault();

            TimeZone threadTimeZone = gThreadTimeZone.get(threadId);
            if (threadTimeZone != null && threadTimeZone.getID().equals(timeZone.getID()))
                return;

            gThreadTimeZone.put(threadId, timeZone);
            clearIfNecessary();
        }
    }

    public static TimeSpan getUtcOffsetFromCurrentTimeZone(DateTime time)
    {
        if (time.getKind() == DateTimeKind.UTC)
            return TimeSpan.ZERO;

        int unixOffset = getTimeZone().getOffset(time.toJavaTicks());
        return TimeSpan.fromJava(unixOffset);
    }

    public static long getId()
    {
        return Thread.currentThread().getId();
    }

    private static void clearIfNecessary()
    {
        synchronized (CurrentThread.class)
        {
            int storedThreads = getStoredThreadsCount();
            int activeThreads = Thread.activeCount();

            if (storedThreads >= activeThreads + gMaxThreadStorage)
                clearUnused();
        }
    }

    // public only for tests - rework tests?
    public static int getStoredThreadsCount()
    {
        synchronized (CurrentThread.class)
        {
            return Math.max(gThreadCulture.size(), gThreadTimeZone.size());
        }
    }

    // public only for tests - rework tests?
    public static void clearAll()
    {
        synchronized (CurrentThread.class)
        {
            gThreadCulture.clear();
            gThreadTimeZone.clear();
        }
    }

    // Returns Culture based on current JVM-wide default Locale.
    private static msCultureInfo getDefaultCulture()
    {
        try
        {
            Locale defaultLocale = Locale.getDefault();
            if(defaultLocale == null)
                return msCultureInfo.getInvariantCulture();

            msCultureInfo cultureInfo = new msCultureInfo(defaultLocale);

            return cultureInfo != null ? cultureInfo : msCultureInfo.getInvariantCulture();
        }
        catch (Exception e)
        {
            return msCultureInfo.getInvariantCulture();
        }
    }

    private static void clearUnused()
    {
        // Prepare threads' id
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Set<Long> ids = new HashSet<Long>();

        for (Thread thread : threadSet)
        {
            if (thread.isAlive())
                ids.add(thread.getId());
        }

        synchronized (CurrentThread.class)
        {
            // Used variables
            Map<Long, msCultureInfo> gNewThreadCulture = new HashMap<Long, msCultureInfo>();
            Map<Long, TimeZone> gNewThreadTimeZone = new HashMap<Long, TimeZone>();

            // Enumerate CultureInfo
            for (Map.Entry<Long, msCultureInfo> entry : gThreadCulture.entrySet())
            {
                if (ids.contains( entry.getKey()))
                    gNewThreadCulture.put(entry.getKey(), entry.getValue());
            }

            // Enumerate TimeZone
            for (Map.Entry<Long, TimeZone> entry : gThreadTimeZone.entrySet())
            {
                if (ids.contains( entry.getKey()))
                    gNewThreadTimeZone.put(entry.getKey(), entry.getValue());
            }

            gThreadCulture = gNewThreadCulture;
            gThreadTimeZone = gNewThreadTimeZone;
        }
    }

    private static volatile Map<Long, msCultureInfo> gThreadCulture = new HashMap<Long, msCultureInfo>();
    private static volatile Map<Long, TimeZone> gThreadTimeZone = new HashMap<Long, TimeZone>();

    private static final int gMaxThreadStorage = 64;
}
