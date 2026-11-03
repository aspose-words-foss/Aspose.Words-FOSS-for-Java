// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// 21.1.2.2.6 p (Text Paragraphs)
/// This element specifies the presence of a paragraph of text within the containing 
/// text body. The paragraph is the highest level text separation mechanism within a 
/// text body. A paragraph can contain text paragraph properties associated with the 
/// paragraph. If no properties are listed then properties specified in the defPPr 
/// element are used.
/// </summary>
class DmlParagraph implements Cloneable
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    DmlParagraph()
    {
    }

    /// <summary>
    /// Initializes a new instance of this class with defining paragraph properties and run properties at paragraph
    /// end.
    /// </summary>
    DmlParagraph(DmlParagraphProperties paragraphProperties, DmlRunProperties endRunProperties)
    {
        mParagraphProperties = paragraphProperties;
        mEndParagraphRunProperties = endRunProperties;
    }

    DmlParagraph deepClone()
    {
        DmlParagraph lhs = (DmlParagraph)memberwiseClone();

        if (mParagraphProperties != null)
            lhs.mParagraphProperties = mParagraphProperties.deepClone();

        if (mEndParagraphRunProperties != null)
            lhs.mEndParagraphRunProperties = mEndParagraphRunProperties.deepClone();

        if (mElements != null)
        {
            lhs.mElements = new ArrayList<DmlParagraphTextElementBase>(mElements.size());
            for (DmlParagraphTextElementBase element : mElements)
                lhs.addElement(element.deepClone());
        }

        return lhs;
    }

    void addElement(DmlParagraphTextElementBase element)
    {
        element.setParagraph(this);
        msArrayList.add(mElements, element);
    }

    void setTextBody(DmlTextBody textBody)
    {
        mTextBody = textBody;
        if (textBody == null)
            return;

        getProperties().setParentProperties(textBody.getTextListStyles());
        getProperties().getDefaultRunProperties().setParentProperties(this, textBody.getTextListStyles());
        getEndParagraphRunProperties().setParentProperties(this, textBody.getTextListStyles());
    }

    /// <summary>
    /// Returns text of child runs.
    /// </summary>
    String getRunText()
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (DmlParagraphTextElementBase element : getElements())
        {
            DmlRun run = ms.as(element, DmlRun.class);
            if (run != null)
                msStringBuilder.append(stringBuilder, run.getText());
        }

        return stringBuilder.toString();
    }

    ArrayList<DmlParagraphTextElementBase> getElements() { return mElements; }

    DmlParagraphProperties getProperties()
    {
        if (mParagraphProperties == null)
            mParagraphProperties = new DmlParagraphProperties();
        return mParagraphProperties;
    }

    DmlRunProperties getEndParagraphRunProperties()
    {
        if (mEndParagraphRunProperties == null)
            mEndParagraphRunProperties = new DmlRunProperties();
        return mEndParagraphRunProperties;
    }
    void setEndParagraphRunProperties(DmlRunProperties value) { mEndParagraphRunProperties = value; }

    DmlTextBody getTextBody() { return mTextBody; }

    /// <summary>
    /// Gets the fist text element of this paragraph. Returns <b>null</b> if the paragraph is empty.
    /// </summary>
    DmlParagraphTextElementBase getFirstElement() { return (mElements.size() > 0) ? mElements.get(0) : null; }

    private ArrayList<DmlParagraphTextElementBase> mElements = new ArrayList<DmlParagraphTextElementBase>();
    private DmlRunProperties mEndParagraphRunProperties;
    private DmlParagraphProperties mParagraphProperties;
    private DmlTextBody mTextBody;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

