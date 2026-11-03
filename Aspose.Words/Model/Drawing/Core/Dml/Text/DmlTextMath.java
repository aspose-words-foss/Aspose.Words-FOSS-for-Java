// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// A CT_TextMath element that specifies a math content inside of a text paragraph. 
/// Such a math zone can be either inline math zone or math paragraph.
/// </summary>
class DmlTextMath extends DmlParagraphTextElementBase
{
    public DmlTextMath(MathObject mathObject)
    {
    	this(mathObject, new DmlRunProperties());
	
    }

    public DmlTextMath(MathObject mathObject, DmlRunProperties runPr)
    {
        setRunProperties(runPr);
        mMathObject = mathObject;
    }

    public /*override*/ DmlParagraphTextElementBase deepClone()
    {
        DmlTextMath lhs = (DmlTextMath) super.deepClone();

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
        msArrayList.add(mElements, element);
    }

    ArrayList<DmlParagraphTextElementBase> getElements() { return mElements; }

    MathObject getMathObject() { return mMathObject; }

    /// <summary>
    /// If true indicates that Math is root math element and must be wrapped into 'a14:m' tag.
    /// </summary>
    public boolean isRootElement() { return mIsRootElement; }
    public void isRootElement(boolean value) { mIsRootElement = value; }

    public /*override*/ void setParagraph(DmlParagraph paragraph)
    {
        super.setParagraph(paragraph);
        for (DmlParagraphTextElementBase element : mElements)
        {
            element.setParagraph(paragraph);
        }
    }

    private ArrayList<DmlParagraphTextElementBase> mElements = new ArrayList<DmlParagraphTextElementBase>();
    private /*final*/ MathObject mMathObject;
    private boolean mIsRootElement;
}
