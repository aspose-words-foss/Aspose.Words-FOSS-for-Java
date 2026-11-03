// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 20.1.2.2.40 txBody (Shape Text Body)
/// This element specifies the existence of text to be contained within the corresponding shape. 
/// All visible text and visible text related properties are contained within this element. 
/// There can be multiple paragraphs and within paragraphs multiple runs of text.
/// </summary>
class DmlTextBody implements Cloneable
{
    DmlTextBody deepClone()
    {
        DmlTextBody lhs = (DmlTextBody)memberwiseClone();

        if (mTextListStyles != null)
            lhs.mTextListStyles = mTextListStyles.deepClone();

        if (mTextBodyProperties != null)
            lhs.mTextBodyProperties = mTextBodyProperties.deepClone();

        if (mParagraphs != null)
        {
            lhs.mParagraphs = new ArrayList<DmlParagraph>(mParagraphs.size());
            for (DmlParagraph para : mParagraphs)
                lhs.addParagraph(para.deepClone());
        }

        return lhs;
    }

    void addParagraph(DmlParagraph paragraph)
    {
        paragraph.setTextBody(this);
        msArrayList.add(getParagraphs(), paragraph);
    }

    DmlTextListStyles getTextListStyles()
    {
        if (mTextListStyles == null)
            mTextListStyles = new DmlTextListStyles();
        return mTextListStyles;
    }
    void setTextListStyles(DmlTextListStyles value) { mTextListStyles = value; }

    DmlTextBodyProperties getProperties()
    {
        if (mTextBodyProperties == null)
            mTextBodyProperties = new DmlTextBodyProperties();
        return mTextBodyProperties;
    }
    void setProperties(DmlTextBodyProperties value) { mTextBodyProperties = value; }

    ArrayList<DmlParagraph> getParagraphs()
    {
        if (mParagraphs == null)
            mParagraphs = new ArrayList<DmlParagraph>();
        return mParagraphs;
    }
    void setParagraphs(ArrayList<DmlParagraph> value) { mParagraphs = value; }

    private ArrayList<DmlParagraph> mParagraphs;
    private DmlTextBodyProperties mTextBodyProperties;
    private DmlTextListStyles mTextListStyles;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
