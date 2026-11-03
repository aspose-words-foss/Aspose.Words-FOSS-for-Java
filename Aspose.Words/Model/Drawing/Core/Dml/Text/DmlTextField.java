// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;


/// <summary>
/// 21.1.2.2.4 fld (Text Field)
/// This element specifies a text field which contains generated text that the application 
/// should update periodically. Each piece of text when it is generated is given a unique 
/// identification number that is used to refer to a specific field. At the time of creation 
/// the text field indicates the kind of text that should be used to update this field. 
/// This update type is used so that all applications that did not create this text field can 
/// still know what kind of text it should be updated with. Thus the new application can then 
/// attach an update type to the text field id for continual updating.
/// </summary>
class DmlTextField extends DmlParagraphTextElementBase
{
    public /*override*/ DmlParagraphTextElementBase deepClone()
    {
        DmlTextField lhs = (DmlTextField)super.deepClone();

        if (mParagraphProperties != null)
            lhs.mParagraphProperties = mParagraphProperties.deepClone();

        return lhs;
    }

    DmlParagraphProperties getParagraphProperties()
    {
        if (mParagraphProperties == null)
            mParagraphProperties = new DmlParagraphProperties();
        return mParagraphProperties;
    }

    /// <summary>
    /// Specifies the unique to this document, host specified token that is used to identify the field. 
    /// This token is generated when the text field is created and persists in the file as the same 
    /// token until the text field is removed. Any application should check the document for conflicting 
    /// tokens before assigning a new token to a text field.
    /// </summary>
    Guid getId() { return mId; }
    void setId(Guid value) { mId = value; }

    /// <summary>
    /// Specifies the type of text that should be used to update this text field. 
    /// This is used to inform the rendering application what text it should use to update this text field. 
    /// There are no specific syntax restrictions placed on this attribute. The generating application can 
    /// use it to represent any text that should be updated before rendering the presentation.
    /// </summary>
    String getType()
    {
        if (mType == null)
            mType = "";
        return mType;
    }
    void setType(String value) { mType = value; }

    String getText()
    {
        if (mText == null)
            mText = "";
        return mText;
    }
    void setText(String value) { mText = value; }

    private Guid mId = Guid.Empty;
    private DmlParagraphProperties mParagraphProperties;
    private String mText;
    private String mType;
}
