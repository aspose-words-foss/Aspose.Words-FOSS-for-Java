// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/06/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base numbering style class
/// </summary>
abstract class TxtNumberingStyle
{
    protected TxtNumberingStyle(boolean isBullet, boolean isLevelsSupported, /*NumberStyle*/int numberStyle)
    {
        mIsBullet = isBullet;
        mIsLevelsSupported = isLevelsSupported;
        mNumberStyle = numberStyle;
    }

    /// <summary>
    /// Returns true is the value is first in the numbering set
    /// </summary>
    abstract boolean isStartNumber(String value);

    /// <summary>
    /// Returns the next numbering value in the sequence. Returns empty string for an illegal or last value in sequence.
    /// </summary>
    abstract String getNextNumber(String prevNumber);

    /// <summary>
    /// Returns information about numbering if the numbering is detected, or null if it is not.
    /// </summary>
    abstract TxtNumberingInfo detectNumbering(String text);

    /// <summary>
    /// Returns AW NumberFormat. level is zero-based.
    /// </summary>
    abstract String getNumberFormat(int level);

    /// <summary>
    /// The numbering is bullet style (*,-)
    /// </summary>
    boolean isBullet() { return mIsBullet; };

    private boolean mIsBullet;

    /// <summary>
    /// The numbering is set style (1) 2) .. or a. b. c. ...)
    /// </summary>
    boolean isSet() { return !isBullet(); }

    /// <summary>
    /// Numbering supports levels (1.1.2. 1.1.3.)
    /// </summary>
    boolean isLevelsSupported() { return mIsLevelsSupported; };

    private boolean mIsLevelsSupported;

    /// <summary>
    /// AW number style
    /// </summary>
    /*NumberStyle*/int getNumberStyle() { return mNumberStyle; };

    private /*NumberStyle*/int mNumberStyle;

    /// <summary>
    /// Allows control the way of numbering detection. See <see cref="Aspose.Words.Loading.TxtLoadOptions.DetectNumberingWithWhitespaces"/> for more details.
    /// </summary>
    boolean getDetectNumberingWithWhitespaces() { return mDetectNumberingWithWhitespaces; }
    void setDetectNumberingWithWhitespaces(boolean value) { mDetectNumberingWithWhitespaces = value; }

    private boolean mDetectNumberingWithWhitespaces = true;
}

