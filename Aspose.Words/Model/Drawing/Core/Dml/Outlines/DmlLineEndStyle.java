// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;


/// <summary>
/// This class specifies decorations which can be added to the tail or the head of a line.
/// </summary>
abstract class DmlLineEndStyle
{
    public abstract DmlLineEndStyle deepClone();
    protected void copyTo(DmlLineEndStyle target)
    {
        target.setLength(getLength());
        target.setWidth(getWidth());
        target.setType(getType());
    }

    public /*override*/ boolean equals(Object obj)
    {
        // Same instance.
        if (obj == this)
            return true;

        // Type or hashcode does not match.
        if (!ArgumentUtil.typeAndHashCodeMatches(this, obj))
            return false;

        DmlLineEndStyle value = (DmlLineEndStyle)obj;

        return (value.getType() == getType()) && (value.getLength() == getLength()) && (value.getWidth() == getWidth());
    }

    public /*override*/ int hashCode()
    {
        int hash = /*enum.hashCode()=(int)value*/getType();
        hash ^= /*enum.hashCode()=(int)value*/getLength();
        hash ^= /*enum.hashCode()=(int)value*/getWidth();
        return hash;
    }

    /// <summary>
    /// Specifies the line end length in relation to the line width.
    /// </summary>
    /*ArrowLength*/int getLength() { return mLength; }
    void setLength(/*ArrowLength*/int value) { mLength = value; }

    /// <summary>
    /// Specifies the line end width in relation to the line width.
    /// </summary>
    /*ArrowWidth*/int getWidth() { return mWidth; }
    void setWidth(/*ArrowWidth*/int value) { mWidth = value; }

    /// <summary>
    /// Specifies the line end decoration,
    /// such as a triangle or arrowhead.
    /// </summary>
    /*ArrowType*/int getType() { return mType; }
    void setType(/*ArrowType*/int value) { mType = value; }

    private /*ArrowLength*/int mLength = ArrowLength.MEDIUM;
    private /*ArrowType*/int mType = ArrowType.NONE;
    private /*ArrowWidth*/int mWidth = ArrowWidth.MEDIUM;
}

