// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.DateTime;


/// <summary>
/// Specifies that the Sdt shall be a date picker when displayed in the document.
/// </summary>
class SdtDate extends SdtControlProperties
{
    /*override*/ /*SdtType*/int getType() { return SdtType.DATE; }

    /// <summary>
    /// <para>This element specifies the language ID which shall be used for displaying a calendar for the current date picker 
    /// structured document tag, if a user interface is present for the sdt.</para>
    /// <para>
    /// If this element is omitted, then the language ID shall be the language ID of the run contents of the parent
    /// structured document tag.</para>
    /// </summary>
    int getLid() { return mLid; }
    void setLid(int value)
    {

        if (mLid != value)
            setNeedToUpdateContent(true);
        mLid = value;
    }

    /// <summary>
    /// The element specifies the display format which shall be used to format any date entered into the parent 
    /// structured document tag in full DateTime format [Example: Through a user interface (a date picker), or through 
    /// custom XML data associated with this structured document tag via the <see cref="XmlMapping"/> element  end 
    /// example] before displaying it in the structured document tag's run content.
    /// </summary>
    /// <remarks>If this element is omitted, then the date shall be formatted using the standard date display mask for the
    /// language ID specified on the <see cref="Lid"/> element if present, or the language ID of the run contents 
    /// otherwise.</remarks>
    String getDateFormat() { return mDateFormat; }
    void setDateFormat(String value)
    {
        if (!msString.equals(mDateFormat, value))
            setNeedToUpdateContent(true);
        mDateFormat = value;
    }

    /// <summary>
    /// Date Picker Calendar Type, specifies the calendar which shall be displayed for the current date picker structured document
    /// tag, if a user interface is present for the structured document tag.
    /// </summary>
    /*SdtCalendarType*/int getCalendarType() { return mCalendarType; }
    void setCalendarType(/*SdtCalendarType*/int value) { mCalendarType = value; }

    /// <summary>
    /// Specifies the full date and time last entered into the parent structured document tag 
    /// using the standard XML Schema DateTime syntax.
    /// </summary>
    DateTime getFullDate() { return mFullDate; }
    void setFullDate(DateTime value)
    {
        if (!DateTime.equals(mFullDate, value))
            setNeedToUpdateContent(true);
        mFullDate = value;
    }

    /// <summary>
    /// Custom XML Data Date Storage Format. This element specifies the translation which 
    /// shall be performed on the displayed date in a date picker structured document tag when 
    /// the current contents are saved into the associated custom XML data via the <see cref="XmlMapping"/> element.
    /// </summary>
    /*SdtDateStorageFormat*/int getStoreMappedDataAs() { return mStoreMappedDataAs; }
    void setStoreMappedDataAs(/*SdtDateStorageFormat*/int value) { mStoreMappedDataAs = value; }

    boolean getNeedToUpdateContent() { return mNeedToUpdateContent; }
    void setNeedToUpdateContent(boolean value) { mNeedToUpdateContent = value; }

    static final int EMPTY_LID = -1;

    private String mDateFormat = "";
    private DateTime mFullDate = DateTime.MinValue; // Init for Java.
    private /*SdtCalendarType*/int mCalendarType = SdtCalendarType.DEFAULT;
    
    private int mLid = EMPTY_LID;

    private /*SdtDateStorageFormat*/int mStoreMappedDataAs = SdtDateStorageFormat.DEFAULT;
    private boolean mNeedToUpdateContent;
}

