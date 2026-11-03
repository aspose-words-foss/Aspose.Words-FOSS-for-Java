// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlParagraphTextElementBase implements Cloneable
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    DmlParagraphTextElementBase()
    {
    }

    /// <summary>
    /// Initializes a new instance of this class with defining run properties.
    /// </summary>
    DmlParagraphTextElementBase(DmlRunProperties runProperties)
    {
        mRunProperties = runProperties;
    }

    public /*virtual*/ DmlParagraphTextElementBase deepClone()
    {
        DmlParagraphTextElementBase lhs = (DmlParagraphTextElementBase)memberwiseClone();

        if (mRunProperties != null)
            lhs.mRunProperties = mRunProperties.deepClone();

        return lhs;
    }

    public /*virtual*/ void setParagraph(DmlParagraph paragraph)
    {
        mParagraph = paragraph;
        getRunProperties().setParentProperties(paragraph.getProperties().getDefaultRunProperties());
    }

    public DmlParagraph getParagraph() { return mParagraph; }

    public DmlRunProperties getRunProperties()
    {
        if (mRunProperties == null)
            mRunProperties = new DmlRunProperties();
        return mRunProperties;
    }
    protected void setRunProperties(DmlRunProperties value) { mRunProperties = value; }

    private DmlParagraph mParagraph;
    private DmlRunProperties mRunProperties;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

