// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.1.2.3.8 r (Text Run)
/// This element specifies the presence of a run of text within the containing text body. 
/// The run element is the lowest level text separation mechanism within a text body. 
/// A text run can contain text run properties associated with the run. If no properties 
/// are listed then properties specified in the defRPr element are used.
/// </summary>
class DmlRun extends DmlParagraphTextElementBase
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    DmlRun()
    {
    }

    /// <summary>
    /// Initializes a new instance of this class with defining run properties.
    /// </summary>
    DmlRun(DmlRunProperties runProperties)
    {
    	super(runProperties);
	
    }

    String getText()
    {
        if (mText == null)
            mText = "";
        return mText;
    }
    void setText(String value) { mText = value; }

    private String mText;
}
