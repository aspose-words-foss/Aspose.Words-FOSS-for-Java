// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/01/2013 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


///<summary>
/// Contains information about failed gold tests.
/// </summary>
public class FailedGoldTestInfo
{
    public FailedGoldTestInfo()
    {
        mGoldFileType = GoldTestFileType.UNKNOWN;
        mTestName = "";
        mTitle = "";
        mFileNameSrc = "";
        mFileNameOut = "";
        mFileNameGold = "";
        mFileNameOriginalGold = "";
        mFileNameMS = "";
        mId = "";
    }

    public /*GoldTestFileType*/int getGoldFileType() { return mGoldFileType; }
    public void setGoldFileType(/*GoldTestFileType*/int value) { mGoldFileType = value; }

    public String getTestName() { return mTestName; }
    public void setTestName(String value) { mTestName = value; }

    public String getFileNameMS() { return mFileNameMS; }
    public void setFileNameMS(String value) { mFileNameMS = value; }

    public String getFileNameGold() { return mFileNameGold; }
    public void setFileNameGold(String value) { mFileNameGold = value; }

    /// <summary>
    /// When JavaGold file is exist, .Net gold is stored as 'Original Gold'.
    /// </summary>
    public String getFileNameOriginalGold() { return mFileNameOriginalGold; }
    public void setFileNameOriginalGold(String value) { mFileNameOriginalGold = value; }

    public String getFileNameOut() { return mFileNameOut; }
    public void setFileNameOut(String value) { mFileNameOut = value; }

    public String getFileNameSrc() { return mFileNameSrc; }
    public void setFileNameSrc(String value) { mFileNameSrc = value; }

    public String getTitle() { return mTitle; }
    public void setTitle(String value) { mTitle = value; }

    public String getId() { return mId; }
    public void setId(String value) { mId = value; }

    private String mTitle;
    private String mFileNameSrc;
    private String mFileNameOut;
    private String mFileNameGold;
    private String mFileNameOriginalGold;
    private String mFileNameMS;
    private String mTestName;
    private String mId;
    private /*GoldTestFileType*/int mGoldFileType;
}
