// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/02/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;

/// <summary>
/// Represents HTML related information about paragraph or row.
/// </summary>
/// <remarks>
/// 17.15.2.6 div (Information About Single HTML div Element)
/// This element specifies information about a single HTML div, body, or blockquote element which was
/// included in this document, so that information (which is stored on a logical structure with no direct analog
/// in WordprocessingML) can be maintained when an HTML document is stored in the WordprocessingML format.
/// The div element stores the following information about these structures:
/// · The child HTML div, and blockquote elements
/// · The borders for the element
/// · The margins for the element
/// When the resulting WordprocessingML document is displayed by an application, the settings specified by this
/// information shall be reflected in the formatting of the resulting paragraphs (i.e. this information shall not only
/// be used when the document is resaved in the HTML format).
/// 
/// AM. There is no common naming across formats about this feature and usage of 'Pgp' is seems to be confusing because of
/// PrettyGoodPrivacy so we decided to use HtmlBlock.
/// </remarks>
class HtmlBlock implements Cloneable
{
    /// <summary>
    /// Ctor.
    /// </summary>
    HtmlBlock(int id)
    {
        mId = id;
    }

    public /*override*/ int hashCode()
    {
        int hashCode = (/*int.hashCode()*/mId);
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/mParentId);
        hashCode = (hashCode * 397) ^ ((mParaPr != null) ? mParaPr.hashCode() : 0);
        hashCode = (hashCode * 397) ^ /*enum.hashCode()=(int)value*/mHtmlBlockType;
        hashCode = (hashCode * 397) ^ (/*int.hashCode()*/mItap);
        return hashCode;
    }

    /// <summary>
    /// Unique value that is used to identify this HtmlBlock.
    /// </summary>
    int getId() { return mId; }

    /// <summary>
    /// The identifier of the immediate parent HtmlBlock.
    /// </summary>
    int getParentId() { return mParentId; }
    void setParentId(int value) { mParentId = value; }

    // The table depth to which this HtmlBlock is applied.
    int getItap() { return mItap; }
    void setItap(int value) { mItap = value; }

    /// <summary>
    /// Paragraph properties collection for this HtmlBlock.
    /// </summary>
    /// <remarks>
    /// AM. This property collection might has only limited set of paragraph attributes.
    /// So if it goes public we need to restrict it somehow.
    /// </remarks>
    ParaPr getParaPr()
    {
        if(mParaPr == null)
            mParaPr = new ParaPr();

        return mParaPr;
    }

    /*HtmlBlockType*/int getHtmlBlockType() { return mHtmlBlockType; }
    void setHtmlBlockType(/*HtmlBlockType*/int value) { mHtmlBlockType = value; }

    /// <summary>
    /// Indicates that the HtmlBlock has child blocks.
    /// </summary>
    /// <remarks>
    /// AM. This feature is rare so I implemented this in a straight way.
    /// Method might be optimized later if needed.
    /// </remarks>
    boolean hasChild()
    {
        if (mHtmlBlockCollection == null)
            return false;

        for (int i = 0; i < mHtmlBlockCollection.getCount(); i++)
            if (mHtmlBlockCollection.getHtmlBlockByIndex(i).getParentId() == this.getId())
                return true;

        return false;
    }

    /// <summary>
    /// Removes ignorable attribute values from property collection.
    /// </summary>
    /// <remarks>
    /// AM. Current implementation removes zero values and empty borders. 
    /// The reason for it is to get unified model regardless of format being read.
    /// So far I'm not sure that zeros/empty borders should be just removed. 
    /// Maybe they need to be collapsed over parent item? Will see later.
    /// </remarks>
    void refine()
    {
        for (int key : G_MARGIN_KEYS)
            if ((/*int*/Integer)getParaPr().fetchAttr(key) == 0)
                getParaPr().remove(key);

        for (int key : ParaPr.POSSIBLE_BORDER_KEYS.getValues())
            if (msObject.equals(getParaPr().getDirectAttr(key), Border.EMPTY))
                getParaPr().remove(key);
    }

    HtmlBlock deepClone()
    {
        HtmlBlock lhs = (HtmlBlock)memberwiseClone();
        if (mParaPr != null)
            lhs.mParaPr = AttrCollectionExtension.deepClone(mParaPr);
        return lhs;
    }

    /// <summary>
    /// HtmlBlockCollection to which this HtmlBlock is belong.
    /// </summary>
    HtmlBlockCollection getHtmlBlockCollection() { return mHtmlBlockCollection; }
    void setHtmlBlockCollection(HtmlBlockCollection value) { mHtmlBlockCollection = value; }

    private /*final*/ int mId;
    private int mParentId;
    private ParaPr mParaPr;
    private /*HtmlBlockType*/int mHtmlBlockType = HtmlBlockType.DIV;
    private int mItap;

    private HtmlBlockCollection mHtmlBlockCollection;

    private static /*final*/ int[] G_MARGIN_KEYS = new int[]
                                        {
                                            ParaAttr.HTML_MARGIN_LEFT, ParaAttr.HTML_MARGIN_RIGHT, ParaAttr.HTML_MARGIN_TOP, ParaAttr.HTML_MARGIN_BOTTOM
                                        };

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

}

