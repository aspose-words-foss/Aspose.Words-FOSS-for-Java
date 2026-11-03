// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/12/2016 by Vyacheslav Durin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;


public class ComparerParams
{
    public StringToStringDictionary toParamMap()
    {
        StringToStringDictionary map = new StringToStringDictionary();
        map.add("Title", getTitle());
        map.add("FileNameSrc", getFileNameSrc());
        map.add("FileNameOut", getFileNameOut());
        map.add("FileNameGold", getFileNameGold());
        map.add("FileNameOriginalGold", getFileNameOriginalGold());
        map.add("FileNameMs", getFileNameMs());

        map.add("PagesCount", Integer.toString(getPagesCount()));
        map.add("Pages", join(getPages()));
        map.add("ComplianceOption", getComplianceOption());
        map.add("UserPassword", getUserPassword());
        map.add("ErrorMessage", getErrorMessage());
        return map;
    }

    private static String join(int[] arr)
    {
        if (arr == null || arr.length == 0)
            return "";

        StringBuilder str = new StringBuilder();
        for (int i : arr)
            msStringBuilder.append(str.append(i), ",");

        return str.delete((str.length() - 1), (str.length() - 1) + (1)).toString();
    }

    public String getTitle() { return mTitle; }; public void setTitle(String value) { mTitle = value; };

    private String mTitle;
    public String getFileNameSrc() { return mFileNameSrc; }; public void setFileNameSrc(String value) { mFileNameSrc = value; };

    private String mFileNameSrc;
    public String getFileNameOut() { return mFileNameOut; }; public void setFileNameOut(String value) { mFileNameOut = value; };

    private String mFileNameOut;
    public String getFileNameGold() { return mFileNameGold; }; public void setFileNameGold(String value) { mFileNameGold = value; };

    private String mFileNameGold;
    public String getFileNameOriginalGold() { return mFileNameOriginalGold; }; public void setFileNameOriginalGold(String value) { mFileNameOriginalGold = value; };

    private String mFileNameOriginalGold;
    public String getFileNameMs() { return mFileNameMs; }; public void setFileNameMs(String value) { mFileNameMs = value; };

    private String mFileNameMs;

    public String getErrorMessage() { return mErrorMessage; }; public void setErrorMessage(String value) { mErrorMessage = value; };

    private String mErrorMessage;

    public int getPagesCount() { return mPagesCount; }; public void setPagesCount(int value) { mPagesCount = value; };

    private int mPagesCount;
    public int[] getPages() { return mPages; }; public void setPages(int[] value) { mPages = value; };

    private int[] mPages;
    public String getComplianceOption() { return mComplianceOption; }; public void setComplianceOption(String value) { mComplianceOption = value; };

    private String mComplianceOption;
    public String getUserPassword() { return mUserPassword; }; public void setUserPassword(String value) { mUserPassword = value; };

    private String mUserPassword;
    public boolean getDialogTopMost() { return mDialogTopMost; }; public void setDialogTopMost(boolean value) { mDialogTopMost = value; };

    private boolean mDialogTopMost;
    public boolean getDialogRestorePosition() { return mDialogRestorePosition; }; public void setDialogRestorePosition(boolean value) { mDialogRestorePosition = value; };

    private boolean mDialogRestorePosition;
}

