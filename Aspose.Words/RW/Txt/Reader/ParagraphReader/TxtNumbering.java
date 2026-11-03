// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Contains numbering or bullet info parsed by <see cref="TxtNumberingDetector" />
/// </summary>
class TxtNumbering
{
    //>>>>>>>> #region  Constructors

    TxtNumbering(TxtNumberingStyle numberingStyle, String[] numbers, String numberingText)
    {
        if (numberingStyle == null) 
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "numberingStyle");
        if (numbers == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "numbers");
        if (numberingText == null) 
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "numberingText");
        mNumberingStyle = numberingStyle;
        mNumbers = numbers;
        mText = numberingText;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Properties

    /// <summary>
    /// Returns or sets the position (in chars) of the number or bullet in the text line
    /// </summary>
    int getNumberPosition() { return mNumberPosition; }
    void setNumberPosition(int value) { mNumberPosition = value; }

    /// <summary>
    /// Previous numbering paragraph (e.g. 1.1 is previous for 1.2)
    /// </summary>
    TxtParagraph getPrevNumberingParagraph() { return mPrevNumberingParagraph; }
    void setPrevNumberingParagraph(TxtParagraph value) { mPrevNumberingParagraph = value; }

    /// <summary>
    /// Parent numbering paragraph (e.g. 1.1 is parent for 1.1.1)
    /// </summary>
    TxtParagraph getParentNumberingParagraph() { return mParentNumberingParagraph; }
    void setParentNumberingParagraph(TxtParagraph value) { mParentNumberingParagraph = value; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Methods

    /// <summary>
    /// Numbering or bullet style
    /// </summary>
    TxtNumberingStyle getNumberingStyle() { return mNumberingStyle; }

    /// <summary>
    /// Numbering or bullet text
    /// </summary>
    String getText() { return mText; }

    /// <summary>
    /// Numbers array for multilevel (like 1.2.3.1) numbering
    /// </summary>
    String[] getNumbers() { return mNumbers; }

    /// <summary>
    /// Zero-based number of numbering level
    /// </summary>
    int getListLevelNumber() { return mNumberingStyle.isBullet() ? 0 : mNumbers.length - 1; }

    /// <summary>
    /// Checks the numbering is first child for another numbering
    /// </summary>
    boolean isFirstChildFor(TxtNumbering numbering)
    {
        if (!mNumberingStyle.isLevelsSupported() ||
            (mNumberingStyle != numbering.getNumberingStyle()) ||
            !mNumberingStyle.isStartNumber(getLastNumber()) ||
            (mNumbers.length - 1 != numbering.getNumbers().length))
            return false;

        for (int i = 0; i < mNumbers.length - 1; i++)
        {
            if (!msString.equals(mNumbers[i], numbering.getNumbers()[i]))
                return false;
        }

        return true;
    }

    /// <summary>
    /// Checks the numbering is previous sibling for another numbering
    /// </summary>
    boolean isPrevSiblingFor(TxtNumbering numbering)
    {
        if (mNumberingStyle != numbering.getNumberingStyle())
            return false;

        if (mNumberingStyle.isBullet())
            return true;

        if (mNumbers.length != numbering.getNumbers().length)
            return false;

        for (int i = 0; i < mNumbers.length - 1; i++)
        {
            if (!msString.equals(mNumbers[i], numbering.getNumbers()[i]))
                return false;
        }

        return msString.equals(mNumberingStyle.getNextNumber(getLastNumber()), numbering.getLastNumber());
    }

    /// <summary>
    /// Returns true if the numbering can start a new list.
    /// </summary>
    boolean isStartNumbering()
    {
        return mNumberingStyle.isBullet() ||
               ((mNumbers.length == 1) && mNumberingStyle.isStartNumber(mNumbers[0]));
    }

    /// <summary>
    /// Returns last number for multilevel numbering
    /// </summary>
    String getLastNumber()
    {
        return mNumbers[mNumbers.length - 1];
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Fields

    private /*final*/ TxtNumberingStyle mNumberingStyle;
    private /*final*/ String mText;
    private /*final*/ String[] mNumbers;
    private int mNumberPosition;
    private TxtParagraph mParentNumberingParagraph;
    private TxtParagraph mPrevNumberingParagraph;

    //<<<<<<<< #endregion 
}
