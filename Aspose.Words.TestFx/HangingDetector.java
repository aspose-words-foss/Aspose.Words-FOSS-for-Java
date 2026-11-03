// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
package com.aspose.words;

import java.lang.Thread;
import java.util.concurrent.*;


/// <summary>
/// Class for determining hanging situations.
/// This class is to be ported manually to Java.
/// </summary>
//[JavaManual("Manual porting by design.")]
class HangingDetector
{
    private HangingDetector(String fileName, int timeout)
    {
        this.mTimeout = timeout;
        this.mFilename = fileName;
    }


    /// <summary>
    /// Returns TRUE if loading process hangs.
    /// </summary>
    /// <param name="fileName">Can be relative to the TestData folder or an explicit path.</param>
    /// <param name="timeOut">Timeout in milliseconds </param>
    static Document loadWithTimeoutCore(String fileName, int timeOut) throws Exception
    {
        HangingDetector detector = new HangingDetector(fileName, timeOut);

        return detector.hangsDuringLoadingCore();
    }

    private Document hangsDuringLoadingCore() throws Exception
    {
        // submit timeout task, returns TRUE after completion
        mCompletionService.submit(new Callable<LoadFileContext>()
        {
            public LoadFileContext call() throws Exception
            {
                LoadFileContext context = new LoadFileContext();
                doWaitTimeOut(context);
                return context;
            }
        });

        // submit file load task, returns FALSE after completion
        mCompletionService.submit(new Callable<LoadFileContext>()
        {
            public LoadFileContext call() throws Exception
            {
                LoadFileContext context = new LoadFileContext();
                loadFile(context);
                return context;
            }
        });

        // get first result
        Future<LoadFileContext> result = mCompletionService.take();

        LoadFileContext loadContext = result.get();

        Document ladedDocument = null;

        if (loadContext.getIsHangs())
        {
            // Just inject a "ThreadAbortException" on the thread and do not wait while it will be interrupted.
            mThreadPool.shutdown();
        }
        else
        {
            ladedDocument = loadContext.getLoadedDocument();
        }

        return ladedDocument;
    }

    private void doWaitTimeOut(LoadFileContext context) throws InterruptedException
    {
        Thread.sleep(mTimeout);
        context.setIsHangs(true);
    }

    private void loadFile(LoadFileContext context) throws Exception
    {
        try
        {
            context.setLoadedDocument(TestUtil.open(mFilename));
            context.setIsHangs(false);
        }
        catch(InterruptedException e)
        {
            mThreadPool.shutdown();
        }
    }

    private final int mTimeout;
    private final String mFilename;
    private final ExecutorService mThreadPool = Executors.newFixedThreadPool(2);
    private final ExecutorCompletionService<LoadFileContext> mCompletionService = new ExecutorCompletionService<LoadFileContext>(mThreadPool);

    private static class LoadFileContext
    {
        public Boolean getIsHangs() { return mIsHangs; }
        public void setIsHangs(Boolean value) { mIsHangs = value; }

        public Document getLoadedDocument() { return mloadedDocument; }
        public void setLoadedDocument(Document value) { mloadedDocument = value; }

        private Boolean mIsHangs;
        private Document mloadedDocument;
    }
}
