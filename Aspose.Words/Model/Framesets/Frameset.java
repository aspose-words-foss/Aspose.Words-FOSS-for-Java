// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.DrColor;


/// <summary>
/// Represents a frames page or a single frame on a frames page.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
/// <remarks>
/// If the <see cref="ChildFramesets"/> property contains items, this instance is a frames page, otherwise it is
/// a single frame.
/// </remarks>
/// <dev>
/// The class represents both ordinary frame (window) and frameset (set of frames).
/// 
/// I don't like to split this class into separate classes for frame and frameset in two reasons:
///   - It simplifies reader because DOC uses one structure to defines them both.
///   - Proposed public API seems to has imitate Word's behavior i.e. split frame into two frames so 
///     is seems to me that dynamic frame type (Frame/Frameset) is better approach.
/// 
/// RK I am happy with this design. We are not making it public yet, so we can tweak things later if needed.
/// An interesting observation in MS Word Automation this is called Frameset, not Frame. They use Frameset to represent
/// functionality of both a single frame and a frameset. Maybe we should call this Frameset too?
/// 
/// Just in case we want to make our Frameset to look more like MS Word Automation Frameset here is how MS Word looks like:
/// Frameset
///    ChildFramesetCount
///    ChildFramesetItem
///
///    FrameDefaultURL
///    FrameDisplayBorders
///    FrameLinkToFile
///    FrameName
///    FrameResizable
///    FrameScrollbarType
///
///    FramesetBorderColor    
///    FramesetBorderWidth
///
///    Height
///    HeightType
///    
///    ParentFrameset
///    
///    Type (frame/frameset)
///
///    Width
///    WidthType
///
///    AddNewFrame (top, bottom. left or right)
///    Delete - deletes this frameset object
/// </dev>
public class Frameset
{
    /// <summary>
    /// Sets the <see cref="FrameDefaultUrl"/> property without any checks.
    /// </summary>
    void setFrameDefaultUrlInternal(String value)
    {
        mFrameDefaultUrl = value;
    }

    /// <summary>
    /// Sets the <see cref="IsFrameLinkToFile"/> property without any checks.
    /// </summary>
    void setIsFrameLinkToFile(boolean value)
    {
        mIsFrameLinkToFile = value;
    }

    /// <summary>
    /// Gets or sets the web page URL or document file name to display in this frame.
    /// </summary>
    public String getFrameDefaultUrl() { return (hasChild() ? null : mFrameDefaultUrl); }
    public void setFrameDefaultUrl(String value)
    {
        if (hasChild())
        {
            throw new IllegalStateException(
                "The property cannot be set if the current instance is a frames page.");
        }

        mFrameDefaultUrl = value;
    }

    /// <summary>
    /// Gets or sets a value indicating whether the web page or document file name specified in the
    /// <see cref="FrameDefaultUrl"/> property is an external resource the frame is linked with.
    /// </summary>
    public boolean isFrameLinkToFile() { return !hasChild() && mIsFrameLinkToFile; }
    public void isFrameLinkToFile(boolean value)
    {
        if (hasChild())
        {
            throw new IllegalStateException(
                "The property cannot be set if the current instance is a frames page.");
        }

        mIsFrameLinkToFile = value;
    }

    /// <summary>
    /// Gets the collection of child frames and frames pages.
    /// </summary>
    public FramesetCollection getChildFramesets() { return mChildFramesets; }

    /// <summary>
    /// Specifies advisory information about a single frame or frameset. 
    /// This property is analogous to the title attribute on the frame or frameset element in HTML.
    /// </summary>
    /// <remarks>
    /// This frame property is absent in binary DOC but present in DOCX.
    /// </remarks>
    String getTitle() { return mTitle; }
    void setTitle(String value) { mTitle = value; }

    /// <summary>
    /// Frame name or null if object is a frameset.
    /// </summary>
    String getName() { return (hasChild() ? null : mName); }
    void setName(String value)
    {
        // Frameset shouldn't have a name.
        Debug.assertTrue(!hasChild());
        // 17.15.2.30. name (Frame Name) (a) The standard does not limit the length of the value of the val attribute.
        // Word restricts the value of this attribute to be at most 255 characters.
        if (StringUtil.hasChars(value) && value.length() > 255)
            value = value.substring((0), (0) + (255));

        mName = value;
    }

    /// <summary>
    /// Frame filename's data or <c>null</c> if this object is frameset.
    /// </summary>
    byte[] getFileData() { return ((hasChild()) ? null : mFileData); }
    void setFileData(byte[] value)
    {
        // If this object is frameset it shouldn't have file and data.
        Debug.assertTrue(!hasChild());
        mFileData = value;
    }

    /// <summary>
    /// Specifies the units of a frame divider position.
    /// </summary>
    /*FrameDividerPositionType*/int getDividerPositionType() { return mDividerPositionType; }
    void setDividerPositionType(/*FrameDividerPositionType*/int value) { mDividerPositionType = value; }

    /// <summary>
    /// Specifies the position of a frame divider.
    /// </summary>
    int getDividerPositionValue() { return mDividerPositionValue; }
    void setDividerPositionValue(int value) { mDividerPositionValue = value; }

    /// <summary>
    /// Specifies how child frames are arranged.
    /// </summary>
    /*FrameLayoutType*/int getLayoutType() { return mLayoutType; }
    void setLayoutType(/*FrameLayoutType*/int value) { mLayoutType = value; }

    /// <summary>
    /// Specifies the left and right margins, in pixels, for this frame.
    /// </summary>
    int getMarginX() { return mMarginX; }
    void setMarginX(int value) { mMarginX = value; }

    /// <summary>
    /// Specifies the top and bottom margins, in pixels, for this frame.
    /// </summary>
    int getMarginY() { return mMarginY; }
    void setMarginY(int value) { mMarginY = value; }

    /// <summary>
    /// Specifies the scrollbar behavior for the frame.
    /// </summary>
    /*FrameScrollType*/int getScrollType() { return mScrollType; }
    void setScrollType(/*FrameScrollType*/int value) { mScrollType = value; }

    /// <summary>
    /// Specifies whether the size of the frame is locked and cannot be changed.
    /// </summary>
    boolean getNoResize() { return mNoResize; }
    void setNoResize(boolean value) { mNoResize = value; }

    /// <summary>
    /// Returns true if Frame object has any child Frame.
    /// </summary>
    boolean hasChild() { return getChildFramesets().getCount() > 0; }

    /// <summary>
    /// Specifies frame border type for entire frameset.
    /// </summary>
    /*FramesetBorderType*/int getFramesetBorderType() { return mFramesetBorderType; }
    void setFramesetBorderType(/*FramesetBorderType*/int value) { mFramesetBorderType = value; }

    /// <summary>
    /// Specifies frame border color for entire frameset.
    /// </summary>
    DrColor getFramesetBorderColor() { return mFramesetBorderColor; }
    void setFramesetBorderColor(DrColor value) { mFramesetBorderColor = value; }

    /// <summary>
    /// Specifies the width, in twips, of the borders and dividers.
    /// </summary>
    /// <remarks>
    /// If this value is 0 the default border width is used.
    /// 
    /// In docx default width of the splitters in this document shall be 4.5 points (90 twentieths of a point) wide.
    /// Spec says If the noBorder element (§17.15.2.31) is also specified, then this element shall be ignored and no splitters shall be displayed.
    /// you should mention about this in remarks for this property.
    /// </remarks>
    int getFramesetBorderWidth() { return mFramesetBorderWidth; }
    void setFramesetBorderWidth(int value) { mFramesetBorderWidth = value; }

    /// <summary>
    /// Gets <c>true</c> if the frame object has border, <c>false</c> otherwise.
    /// </summary>
    boolean hasBorder() { return (mFramesetBorderType != FramesetBorderType.NONE) && (mFramesetBorderWidth > 0); }

    private String mTitle;
    private String mName;
    private String mFrameDefaultUrl;
    private byte[] mFileData;
    private /*FrameScrollType*/int mScrollType = FrameScrollType.AUTO;
    private /*FrameLayoutType*/int mLayoutType = FrameLayoutType.VERTICAL;
    private /*FrameDividerPositionType*/int mDividerPositionType = FrameDividerPositionType.RELATIVE;
    private int mDividerPositionValue = 1;
    private boolean mIsFrameLinkToFile;
    private boolean mNoResize;
    private int mMarginX;
    private int mMarginY;
    private /*final*/ FramesetCollection mChildFramesets = new FramesetCollection();
    private DrColor mFramesetBorderColor = DrColor.EMPTY;
    private int mFramesetBorderWidth; 
    private /*FramesetBorderType*/int mFramesetBorderType = FramesetBorderType.SIMPLE;
}

