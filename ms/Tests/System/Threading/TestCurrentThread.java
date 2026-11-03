package com.aspose;

import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = SingleThreadTestGroup.SINGLE_THREAD)
public class TestCurrentThread
{
    @BeforeMethod
    public void beforeEachMethod()
    {
        CurrentThread.clearAll();
    }

    @AfterMethod
    public void afterEachMethod()
    {
        CurrentThread.clearAll();
    }

    @Test
    public void testGCAmountOfThreads() throws InterruptedException
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                msAssert.areEqual(0, CurrentThread.getStoredThreadsCount());
                msCultureInfo info = CurrentThread.getCurrentCulture();
                msAssert.areEqual(1, CurrentThread.getStoredThreadsCount());
            }
        };

        ThreadErrorHandler errorHandler = new ThreadErrorHandler();
        thread.setUncaughtExceptionHandler(errorHandler);

        thread.start();
        thread.join();

        if (errorHandler.error != null)
            msAssert.fail(errorHandler.error.getMessage());
    }

    @Test
    public void testGCOneThread() throws InterruptedException
    {
        CurrentThread.setCurrentCulture("fr-FR");
        CurrentThread.setCurrentCulture("en-US");
        CurrentThread.setCurrentCulture("ru-RU");

        msAssert.areEqual(1, CurrentThread.getStoredThreadsCount());
    }

    @Test
    public void testSetAndGet() throws InterruptedException
    {
        CurrentThread.setCurrentCulture("fr-FR");
        msAssert.areEqual("fr_FR", CurrentThread.getCurrentCulture().getLocale().toString());

        CurrentThread.setCurrentCulture("en-US");
        msAssert.areEqual("en_US", CurrentThread.getCurrentCulture().getLocale().toString());

        CurrentThread.setCurrentCulture("ru-RU");
        msAssert.areEqual("ru_RU", CurrentThread.getCurrentCulture().getLocale().toString());

        msAssert.areEqual(1, CurrentThread.getStoredThreadsCount());
    }

    @Test
    public void testGCMoreThatOneThread() throws InterruptedException
    {
        Thread[] threads = null;

        // Add CurrentThread.MAX_DIFFERENCE-1 threads
        try
        {
            threads = runThreads(gMaxThreadStorage - 1);
            msAssert.assertTrue(CurrentThread.getStoredThreadsCount() < gMaxThreadStorage);
        }
        finally
        {
            joinAll(threads);
        }

        // Add other CurrentThread.MAX_DIFFERENCE-1 threads
        try
        {
            threads = runThreads(gMaxThreadStorage);
        }
        finally
        {
            joinAll(threads);
        }

        // Make sure that some of the threads were destroyed
        threads = null;
        System.gc();

        msAssert.assertTrue(CurrentThread.getStoredThreadsCount() < gMaxThreadStorage);
    }

    private Thread[] runThreads(int amount) throws InterruptedException
    {
        // Increase amount of data
        Thread threads[] = new ThreadForGarbage[amount];
        for (int I=0; I<threads.length; I++)
        {
            Thread thread = threads[I] = new ThreadForGarbage();
            thread.start();
        }

        return threads;
    }

    private void joinAll(Thread[] threads) throws InterruptedException
    {
        for (Thread thread : threads)
            thread.join();
    }

    private class ThreadForGarbage extends Thread
    {
        public void run()
        {
            msCultureInfo info = CurrentThread.getCurrentCulture();
        }
    }

    private class ThreadErrorHandler implements Thread.UncaughtExceptionHandler
    {
        public void uncaughtException(Thread th, Throwable ex)
        {
            error = ex;
        }

        public Throwable error = null;
    }

    // copycat from CurrentThread.
    private static final int gMaxThreadStorage = 64;
}
