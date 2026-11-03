// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 04/10/2018 by Anatoly Sidorenko

package com.aspose;

import com.aspose.ms.System.IDisposable;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


/// <summary>
/// Helps to lock a resource globally.
/// Usually used to prevent test file locking while tests execution in parallel.
/// </summary>
public final class GlobalLock implements IDisposable
{
    public GlobalLock(String mutexName)throws Exception
    {
        synchronized (GlobalLock.class)
        {
            mMutex = mMutexs.get(mutexName);
            if (mMutex == null)
            {
                mMutex = new Semaphore(1);
                mMutexs.put(mutexName, mMutex);
            }
        }
        mMutex.acquire();
    }

    public void dispose()
    {
        if (mMutex == null)
            return;

        mMutex.release();
        mMutex = null;
    }

    private Semaphore mMutex;

    private static HashMap<String,Semaphore> mMutexs = new HashMap<String,Semaphore>();
}

