// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/08/2007 by Vladimir Averkin
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.File;


/// <summary>
/// Stores information about results of 'gold' import/export test of a disk file.
/// </summary>
public class FileComparisonInfo extends ComparisonInfo
{
    public FileComparisonInfo(String filenameSrc, String filenameOut, String filenameGold, String filenameMS)
    {
        mFilenameSrc = filenameSrc;
        mFilenameOut = filenameOut;
        mFilenameGold = filenameGold;
        mFilenameMS = filenameMS;
    }

    public FileComparisonInfo(String filenameSrc, String filenameOut, String filenameGold, String filenameOriginalGold, String filenameMS)
    {
    	this(filenameSrc, filenameOut, filenameGold, filenameMS);
	
        mFilenameOriginalGold = filenameOriginalGold;
    }

    /// <summary>
    /// You must call this to update the comparison info status.
    /// </summary>
    public /*virtual*/ void updateStatus() throws Exception
    {
        resetStatus();
        if (File.exists(mFilenameOut))
        {
            mStatusOut = ComparisonStatus.OK;

            if (File.exists(mFilenameGold))
                mStatusGold = TestFxUtil.compareFiles(mFilenameOut, mFilenameGold) ? ComparisonStatus.OK : ComparisonStatus.DIFFERENT;
            else
                mStatusGold = ComparisonStatus.MISSING;

            if (mFilenameOriginalGold != null && File.exists(mFilenameOriginalGold))
                mStatusOriginalGold = TestFxUtil.compareFiles(mFilenameOut, mFilenameOriginalGold) ? ComparisonStatus.OK : ComparisonStatus.DIFFERENT;
            else
                mStatusOriginalGold = ComparisonStatus.MISSING;

            if (File.exists(mFilenameMS))
                mStatusMS = TestFxUtil.compareFiles(mFilenameOut, mFilenameMS) ? ComparisonStatus.OK : ComparisonStatus.DIFFERENT;
            else
                mStatusMS = ComparisonStatus.MISSING;
        }
        else
        {
            mStatusOut = ComparisonStatus.MISSING;
            mStatusGold = (File.exists(mFilenameGold)) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
            mStatusOriginalGold = (mFilenameOriginalGold != null && File.exists(mFilenameOriginalGold)) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
            mStatusMS = (File.exists(mFilenameMS)) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
        }
    }

    public String getFilenameSrc() { return mFilenameSrc; }

    public String getFilenameOut() { return mFilenameOut; }

    public String getFilenameGold() { return mFilenameGold; }

    public String getFilenameOriginalGold() { return mFilenameOriginalGold; }

    public String getFilenameMS() { return mFilenameMS; }

    private /*final*/ String mFilenameSrc;
    private /*final*/ String mFilenameOut;
    private /*final*/ String mFilenameGold;
    private /*final*/ String mFilenameOriginalGold;
    private /*final*/ String mFilenameMS;
}

