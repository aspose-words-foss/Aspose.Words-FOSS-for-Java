// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/05/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Text line's structure
/// </summary>
class TxtLine
{
    //>>>>>>>> #region  Contructors

    TxtLine(/*TxtLineType*/int lineType)
    {
    	this("", 0, lineType);
	
    }

    TxtLine(String text, int leftIndent, /*TxtLineType*/int lineType)
    {
        if (text == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "text");
        mLineType = lineType;
        mText = text;
        mLeftIndent = leftIndent;
    }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Properties

    /// <summary>
    /// Line's text
    /// </summary>
    String getText() { return mText; }

    /// <summary>
    /// Line's left indent size (in characters)
    /// </summary>
    int getLeftIndent() { return mLeftIndent; }

    /// <summary>
    /// Line's type
    /// </summary>
    /*TxtLineType*/int getLineType() { return mLineType; }
    void setLineType(/*TxtLineType*/int value) { mLineType = value; }

    /// <summary>
    /// Returns true if FormFeed (0xC) character is terminating this line, meaning that we'll
    /// need to insert new Section after this line.
    /// </summary>
    boolean isNewSectionRequested() { return mIsFormFeed; }
    void isNewSectionRequested(boolean value) { mIsFormFeed = value;  }

    /// <summary>
    /// Line's numbering
    /// </summary>
    TxtNumbering getNumbering() { return mNumbering; }
    void setNumbering(TxtNumbering value) { mNumbering = value; }

    //<<<<<<<< #endregion 

    //>>>>>>>> #region  Fields

    private /*final*/ String mText;
    private /*final*/ int mLeftIndent;
    private /*TxtLineType*/int mLineType;
    private TxtNumbering mNumbering;
    private boolean mIsFormFeed;

    //<<<<<<<< #endregion 
}
