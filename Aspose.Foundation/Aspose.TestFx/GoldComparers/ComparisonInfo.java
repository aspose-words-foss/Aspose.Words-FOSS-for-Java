// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/08/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class to store information about the results of 'gold' import/export test.
/// In 'gold' tests, 'out' files, produced by Aspose.Words, are compared with the files 
/// produced during previous tests and saved as 'gold'.
/// Files can be also compared against 'ms' files, produced by MS Word.
/// </summary>
public abstract class ComparisonInfo
{
    /// <summary>
    /// Status of the 'out' file or package part.
    /// 'Ok', if file is present and 'Missing' if file is missing.
    /// </summary>
    public /*ComparisonStatus*/int getOutStatus() { return mStatusOut; }

    /// <summary>
    /// Status of the 'gold' file.
    /// 'Ok', if file is present, 'Missing' if file is missing, and 'Different' if file is different from 'out' file.
    /// </summary>
    public /*ComparisonStatus*/int getGoldStatus() { return mStatusGold; }

    /// <summary>
    /// Status of the 'original gold' file. When JavaGold file is exist, .Net gold is stored as 'Original Gold'.
    /// 'Ok', if file is present, 'Missing' if file is missing, and 'Different' if file is different from 'out' file.
    /// </summary>
    public /*ComparisonStatus*/int getOriginalGoldStatus() { return mStatusOriginalGold; }

    /// <summary>
    /// Status of the 'ms' file.
    /// 'Ok', if file is present, 'Missing' if file is missing, and 'Different' if file is different from 'out' file.
    /// </summary>
    public /*ComparisonStatus*/int getMSStatus() { return mStatusMS; }

    public void resetStatus()
    {
        mStatusOut = ComparisonStatus.DIFFERENT;
        mStatusGold = ComparisonStatus.DIFFERENT;
        mStatusOriginalGold = ComparisonStatus.DIFFERENT;
        mStatusMS = ComparisonStatus.DIFFERENT;
    }

    protected /*ComparisonStatus*/int mStatusOut;
    protected /*ComparisonStatus*/int mStatusGold;
    protected /*ComparisonStatus*/int mStatusOriginalGold;
    protected /*ComparisonStatus*/int mStatusMS;

    public static boolean isXml(String fileName)
    {
        return
            fileName.endsWith(".ncx") ||
            fileName.endsWith(".opf") ||
            fileName.endsWith(".svg") ||
            fileName.endsWith(".xml") ||
            fileName.endsWith(".xaml") ||
            fileName.endsWith(".wml") ||
            fileName.endsWith(".wordml") ||
            fileName.endsWith(".fopc") ||
            fileName.endsWith(".rels") ||
            fileName.endsWith(".fpage") ||
            fileName.endsWith(".fdoc") ||
            fileName.endsWith(".fdseq");
    }

    public static boolean isPng(String fileName)
    {
        return fileName.endsWith(".png");
    }

}

