// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/07/2012 by Dmitry Bormashov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Contains information about border art.
/// </summary>
class PageBorderArtInfo
{
    PageBorderArtInfo(int id, int contraction, int hExpansion, int vExpansion)
    {
        mId = id;
        mContraction = contraction;
        mHExpansion = hExpansion;
        mVExpansion = vExpansion;
        mTopElements = new byte[(int)PageBorderArtElementPosition.LAST + 1][];
        mBottomElements = new byte[(int)PageBorderArtElementPosition.LAST + 1][];
    }

    /// <summary>
    /// Border art id.
    /// </summary>
    int getId() { return mId; }

    /// <summary>
    /// Ratio shows expansion/contraction for middle elements in 
    /// horizontal part of border.
    /// </summary>
    float getHorizontalExpansionContraction() { return (float)mHExpansion / (float)mContraction; }

    /// <summary>
    /// Ratio shows expansion/contraction for middle elements in 
    /// vertical part of border.
    /// </summary>
    float getVerticalExpansionContraction() { return (float)mVExpansion / (float)mContraction; }

    /// <summary>
    /// Sets border art element.
    /// </summary>
    /// <param name="borderType">Border type.</param>
    /// <param name="elementPosition">Element position.</param>
    /// <param name="elementBytes">Element.</param>
    void setElement(
        /*BorderType*/int borderType,
        /*PageBorderArtElementPosition*/int elementPosition,
        byte[] elementBytes)
    {
        switch (borderType)
        {
            case BorderType.TOP:
                mTopElements[(int)elementPosition] = elementBytes;
                break;
            case BorderType.BOTTOM:
                mBottomElements[(int)elementPosition] = elementBytes;
                break;
            case BorderType.LEFT:
                mLeftElement = elementBytes;
                break;
            case BorderType.RIGHT:
                mRightElement = elementBytes;
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "borderType");
        }
    }

    /// <summary>
    /// Gets border art element.
    /// </summary>
    /// <param name="borderType">Border type.</param>
    /// <param name="elementPosition">Element position.</param>
    /// <returns>Border art element.</returns>
    byte[] getElement(/*BorderType*/int borderType, /*PageBorderArtElementPosition*/int elementPosition)
    {
        switch (borderType)
        {
            case BorderType.TOP:
                return mTopElements[(int)elementPosition];
            case BorderType.BOTTOM:
                return mBottomElements[(int)elementPosition];
            case BorderType.LEFT:
                return mLeftElement;
            case BorderType.RIGHT:
                return mRightElement;
            default:
                return new byte[0];
        }
    }

    private /*final*/ int mId;
    private /*final*/ int mContraction;
    private /*final*/ int mHExpansion;
    private /*final*/ int mVExpansion;
    private /*final*/ byte[][] mTopElements;
    private byte[] mRightElement;
    private /*final*/ byte[][] mBottomElements;
    private byte[] mLeftElement;
}

