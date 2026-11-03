// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2016 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents frame related formatting for a paragraph.
/// </summary>
/// <remarks>
/// <p>This object is always created. If a paragraph is a frame, then all properties will contain respective values, otherwise
/// all properties are set to their defaults.</p>
/// <p>Use <see cref="IsFrame" /> to check whether paragraph is a frame.</p>
/// </remarks>
public class FrameFormat
{
    FrameFormat(IParaAttrSource parent)
    {
        mParent = parent;
    }

    /// <summary>
    /// Gets the rule for determining the height of the specified frame.
    /// </summary>
    public /*HeightRule*/int getHeightRule()
    {
        Height height = (Height)mParent.fetchParaAttr(ParaAttr.FRAME_HEIGHT);
        return height.getRule();
    }

    /// <summary>
    /// Gets the height of the specified frame.
    /// </summary>
    public double getHeight()
    {
        Height height = (Height)mParent.fetchParaAttr(ParaAttr.FRAME_HEIGHT);
        return ConvertUtilCore.twipToPoint(height.getValue());
    }

    /// <summary>
    /// Gets horizontal distance between a frame and the surrounding text, in points.
    /// </summary>
    public double getHorizontalDistanceFromText() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_HORIZONTAL_DISTANCE_FROM_TEXT)); }

    /// <summary>
    /// Gets horizontal distance between the edge of the frame and the item specified by the <see cref="RelativeHorizontalPosition" /> property.
    /// </summary>
    public double getHorizontalPosition() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_LEFT)); }

    /// <summary>
    /// Gets the relative horizontal position of a frame.
    /// </summary>
    public /*RelativeHorizontalPosition*/int getRelativeHorizontalPosition() { return (/*RelativeHorizontalPosition*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION); }

    /// <summary>
    /// Gets the relative vertical position of a frame.
    /// </summary>
    public /*RelativeVerticalPosition*/int getRelativeVerticalPosition() { return (/*RelativeVerticalPosition*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION); }

    /// <summary>
    /// Specifies vertical distance (in points) between a frame and the surrounding text.
    /// </summary>
    public double getVerticalDistanceFromText() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_VERTICAL_DISTANCE_FROM_TEXT)); }

    /// <summary>
    /// Gets vertical distance between the edge of the frame and the item specified by the <see cref="RelativeVerticalPosition" /> property.
    /// </summary>
    public double getVerticalPosition() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_TOP)); }

    /// <summary>
    /// Gets the width of the specified frame, in points.
    /// </summary>
    public double getWidth() { return ConvertUtilCore.twipToPoint((/*int*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_WIDTH)); }

    /// <summary>
    /// Gets vertical alignment of the specified frame.
    /// </summary>
    public /*VerticalAlignment*/int getVerticalAlignment() { return (/*VerticalAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT); }

    /// <summary>
    /// Gets horizontal alignment of the specified frame.
    /// </summary>
    public /*HorizontalAlignment*/int getHorizontalAlignment() { return (/*HorizontalAlignment*/Integer)mParent.fetchParaAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT); }

    /// <summary>
    /// Returns <c>true</c> if the paragraph is a frame.
    /// </summary>
    public boolean isFrame() { return isFloatingCore(mParent); }

    static boolean isFloatingCore(IParaAttrSource attrs)
    {
        if (hasHorizontalRule(attrs) || hasVerticalRule(attrs))
            return true;

        Height height = (Height)attrs.fetchParaAttr(ParaAttr.FRAME_HEIGHT);
        if (height.getValue() > 0)
            return true;

        int frameWidth = (/*int*/Integer)attrs.fetchParaAttr(ParaAttr.FRAME_WIDTH);
        if (frameWidth > 0)
            return true;

        // Also it seems that FrameWrapType other than Inline makes floater as well.
        // WORDSNET-6396 Always treat drop capitals as frames.
        /*WrapType*/int wrapType = (/*WrapType*/Integer)attrs.fetchParaAttr(ParaAttr.FRAME_WRAP_TYPE);
        /*DropCapPosition*/int dropCapPosition = (/*DropCapPosition*/Integer)attrs.fetchParaAttr(ParaAttr.DROP_CAP_POSITION);

        return !((wrapType == WrapType.INLINE) && (dropCapPosition == DropCapPosition.NONE));
    }

    private static boolean hasHorizontalRule(IParaAttrSource attrs)
    {
        int frameLeft = (/*int*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_LEFT);
        if (frameLeft > 0)
            return true;

        /*HorizontalAlignment*/int horizontalAlignment =
            (/*HorizontalAlignment*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_HORIZONTAL_ALIGNMENT);
            // According to tests these both values are defaults.
            if ((horizontalAlignment != HorizontalAlignment.NONE) &&
                (horizontalAlignment != HorizontalAlignment.LEFT))
                return true;

        /*RelativeHorizontalPosition*/int relativeHorizontalPosition = 
            (/*RelativeHorizontalPosition*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_RELATIVE_HORIZONTAL_POSITION);

        return relativeHorizontalPosition != RelativeHorizontalPosition.COLUMN;
    }

    private static boolean hasVerticalRule(IParaAttrSource attrs)
    {
        int frameTop = (/*int*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_TOP);
        if (frameTop > 0)
            return true;

        /*VerticalAlignment*/int verticalAlignment =
            (/*VerticalAlignment*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_VERTICAL_ALIGNMENT);

        // According to tests these both values are defaults.
        if ((verticalAlignment != VerticalAlignment.NONE) &&
            (verticalAlignment != VerticalAlignment.INLINE))
                return true;

        /*RelativeVerticalPosition*/int relativeVerticalPosition = 
            (/*RelativeVerticalPosition*/Integer) attrs.fetchParaAttr(ParaAttr.FRAME_RELATIVE_VERTICAL_POSITION);

        // According to tests these both values are default.
        if ((relativeVerticalPosition != RelativeVerticalPosition.MARGIN) &&
            (relativeVerticalPosition != RelativeVerticalPosition.PAGE))
                return true;

        return false;
    }

    private /*final*/ IParaAttrSource mParent;
}

