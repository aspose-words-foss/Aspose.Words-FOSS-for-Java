// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/08/2007 by Vladimir Averkin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Stores information about results of 'gold' import/export test for the package part.
/// </summary>
public class PackagePartComparisonInfo extends ComparisonInfo
{
    PackagePartComparisonInfo(String name)
    {
        mName = name;
    }

    /// <summary>
    /// You must call this to update the comparison info status.
    /// </summary>
    public void updateStatus() throws Exception
    {
        updateStatus(null);
    }

    /// <summary>
    /// Call this to update the comparison info status if using a custom package part comparer.
    /// </summary>
    public void updateStatus(IPackagePartComparer comparer) throws Exception
    {
        resetStatus();
        if (mBufferOut != null)
        {
            mStatusOut = ComparisonStatus.OK;

            mStatusGold = compareBuffers(comparer, mName, mBufferOut, mBufferGold, false);
            mStatusOriginalGold = compareBuffers(comparer, mName, mBufferOut, mBufferOriginalGold, false);

            // It is nice to use XmlDiff when comparing OUT and MS - it ignores things like "UTF-8" vs "utf-8" 
            // and thus shows more "green" in the compare dialog box.
            mStatusMS = compareBuffers(comparer, mName, mBufferOut, mBufferMS, true);
            mStatusMS = ComparisonStatus.OK;
        }
        else
        {
            mStatusOut = ComparisonStatus.MISSING;
            mStatusGold = getStatusForMissingOut(mBufferGold);
            mStatusOriginalGold = getStatusForMissingOut(mBufferOriginalGold);
            mStatusMS = getStatusForMissingOut(mBufferMS);
        }
    }

    private static /*ComparisonStatus*/int compareBuffers(
        IPackagePartComparer comparer,
        String partName,
        byte[] buffer1,
        byte[] buffer2,
        boolean isPreferXmlDiff) throws Exception
    {
        if (buffer2 == null)
            return ComparisonStatus.MISSING;

        if (comparer == null)
        {
            if (isPng(partName))
            {
                try
                {
                    MemoryStream stream1 = new MemoryStream(buffer1);
                    MemoryStream stream2 = new MemoryStream(buffer2);
                    // TODO The CompareImageFiles converts streams to buffers again and compare buffers like we do below.
                    // I do not get the point of special handling of images then.
                    ImageFileComparer.compareImageFiles(stream1, stream2, null, null);
                    return ComparisonStatus.OK;
                }
                catch (Exception e)
                {
                    return ComparisonStatus.DIFFERENT;
                }
            }
            comparer = DefaultPackagePartComparer.INSTANCE;
        }

        return comparer.compareBuffers(partName, buffer1, buffer2, isPreferXmlDiff)
            ? ComparisonStatus.OK
            : ComparisonStatus.DIFFERENT;
    }

    private static /*ComparisonStatus*/int getStatusForMissingOut(byte[] buffer)
    {
        return (buffer != null) ? ComparisonStatus.DIFFERENT : ComparisonStatus.MISSING;
    }

    public String getName() { return mName; }

    public byte[] getBufferOut() { return mBufferOut; }
    public void setBufferOut(byte[] value) { mBufferOut = value; }

    public byte[] getBufferGold() { return mBufferGold; }
    public void setBufferGold(byte[] value) { mBufferGold = value; }

    public byte[] getBufferOriginalGold() { return mBufferOriginalGold; }
    public void setBufferOriginalGold(byte[] value) { mBufferOriginalGold = value; }

    public byte[] getBufferMS() { return mBufferMS; }
    public void setBufferMS(byte[] value) { mBufferMS = value; }

    private /*final*/ String mName;
    private byte[] mBufferOut;
    private byte[] mBufferGold;
    private byte[] mBufferOriginalGold;
    private byte[] mBufferMS;
}

