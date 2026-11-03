// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/06/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The base class for all markdown feature detectors.
/// </summary>
abstract class MarkdownDetectorBase
{
    /// <summary>
    /// Detects and updates a specified context with a detected markdown feature type.
    /// </summary>
    boolean detectAndUpdateContext(MarkdownDetectorContext context) throws Exception
    {
        // Reset the number of detected markdown features to zero.
        mCount = 0;

        if (detect(context))
        {
            context.getCurLineDetectedFeatures().add(getType());
            return true;
        }

        return false;
    }

    /// <summary>
    /// Detects a markdown feature in a specified context.
    /// </summary>
    protected abstract boolean detect(MarkdownDetectorContext context) throws Exception;

    /// <summary>
    /// Gets or sets a count of the detected markdown features.
    /// </summary>
    int getCount() { return mCount; }
    void setCount(int value) { mCount = value; }

    /// <summary>
    /// Gets a weight of the detected markdown features.
    /// </summary>
    int getWeight() { return (mCount * (int)getSignificance()); }

    /// <summary>
    /// Gets markdown feature significance of the detector.
    /// </summary>
    protected abstract /*MarkdownFeatureSignificance*/int getSignificance();
    
    /// <summary>
    /// Gets markdown feature type of the detector.
    /// </summary>
    protected abstract /*MarkdownFeatureType*/int getType();

    private int mCount;
}

