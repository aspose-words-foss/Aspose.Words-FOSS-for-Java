// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 11/14/2010 by Roman Korchagin

package com.aspose;

import java.util.concurrent.*;

/// <summary>
/// This class is to be ported manually to Java.
/// </summary>
public abstract class ThreadPal
{
    /// <summary>
    /// Starts a new thread.
    /// </summary>
    public synchronized void start()
    {
        // As any Java Thread it cannot be restarted.
        if (!mExecutorService.isShutdown())
        {
            mExecutorService.execute(mDoWorkTask);
            mExecutorService.shutdown();
        }
    }

    /// <summary>
    /// Starts a new thread with limited stack..
    /// </summary>
    public synchronized void start(int maxStackSize)
    {
        // we can not change stack size in Java
        start();
    }

    //JAVA-deleted: java's Thread.join() is final - can't override, use parent join().
    /// <summary>
    /// Blocks the calling thread until the thread represented by this instance terminates.
    /// </summary>
//    public void join()
//    {
//        if (mThread != null)
//            mThread.join();
//    }

    /**
     * JAVA-added wrapper for Thread.join() with unchecked exception.
     * <p>
     * Java's Thread.join() it throws checked exception. But it is final - we can't
     * override it with a method with the same name.
     */
    public void joinSilently()
    {
        try
        {
            mDoWorkTask.get(); //Waits if necessary for the computation to complete, and then retrieves its result.
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /// <summary>
    /// Begins the process of terminating the thread.
    /// </summary>
    public void abort()
    {
        mDoWorkTask.cancel(true);
    }

    /// <summary>
    /// Returns true, if this thread has been started and has not terminated normally or aborted.
    /// </summary>
    public boolean isAlive()
    {
        return !mDoWorkTask.isDone();
    }

    //JAVA-added to implement doWork()
    protected abstract void doWork() throws Exception;

    protected boolean isCancelled()
    {
        return false;
    }

    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private final FutureTask<Object> mDoWorkTask = new FutureTask<Object>(new Callable<Object>()
    {
        @Override
        public Object call() throws Exception
        {
            try
            {
                doWork();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
            return null;
        }
    });
}
