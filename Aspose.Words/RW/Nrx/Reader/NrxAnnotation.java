// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/09/2007 by Vladimir Averkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.FormatterPal;
import com.aspose.ms.System.DateTime;


/// <summary>
/// This class is used as a temporary storage of annotation properties read from 
/// attributes of annotation elements, like bookmarks, revisions, etc.
/// </summary>
class NrxAnnotation
{
    NrxAnnotation(NrxXmlReader xmlReader)
    {
        while (xmlReader.moveToNextAttribute())
        {
            switch (gStringSwitchMap.of(xmlReader.getLocalName()))
            {
                case /*"id"*/0:
                {
                    // andrnosk: WORDSNET-7990 Abs is used to properly handle negative Ids and Ids 
                    // which is greater then Int32 maximum value (in case when this value will be trimmed and became negative).
                    // MS Word also does not preserve negative values upon open/save.
                    int value = xmlReader.getValueAsInt();
                    if (value < 0)
                    {
                        xmlReader.warn(WarningType.UNEXPECTED_CONTENT, WarningSource.NRX,
                            "Negative Ids are not supported by AW, Id was corrected to positive value");

                        value = Math.abs(value);
                    }

                    mId = value;
                    break;
                }
                case /*"date"*/1:
                case /*"createdate"*/2:  // WML
                    // WORDSNET-23812 Word ignores TimeZone in timestamp of revisions.
                    mDate = FormatterPal.xmlToDateTimeIgnoreTimeZone(xmlReader.getValue());
                    break;
                case /*"name"*/3:
                    mName = xmlReader.getValue();
                    break;
                case /*"author"*/4:
                    mAuthor = xmlReader.getValue();
                    break;
                case /*"initials"*/5:
                    mInitials = xmlReader.getValue();
                    break;
                case /*"original"*/6:
                    mOriginal = xmlReader.getValue();
                    break;

                case /*"colFirst"*/7:
                case /*"col-first"*/8:   // WML
                    mColFirst = xmlReader.getValueAsInt();
                    break;
                case /*"colLast"*/9:
                case /*"col-last"*/10:    // WML
                    mColLast = xmlReader.getValueAsInt();
                    break;
                case /*"type"*/11:        // WML
                    // w:type
                    mType = xmlReader.getValue();
                    break;
                case /*"edGrp"*/12:
                    mEdGrp = DocxDopEnum.docxToEditorType(xmlReader.getValue());
                    break;
                case /*"ed"*/13:        
                    mEd = xmlReader.getValue();
                    break;
                case /*"displacedByCustomXml"*/14:
                    mDisplacedByType = DocxDopEnum.docxToDisplacedByType(xmlReader.getValue());
                    break;
                default:
                    break;
            }
        }
    }

    int getId() { return mId; }

    DateTime getDate() { return mDate; }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    String getAuthor() { return mAuthor; }

    String getInitials() { return mInitials; }

    String getOriginal() { return mOriginal; }

    int getColFirst() { return mColFirst; }

    int getColLast() { return mColLast; }

    /// <summary>
    /// WML only.
    /// </summary>
    String getType() { return mType; }

    /// <summary>
    /// Single user for editable range.
    /// </summary>
    String getSingleUser() { return mEd; }

    /// <summary>
    /// Editor group for editable range.
    /// </summary>
    /*EditorType*/int getEditorGroup() { return mEdGrp; }

    /// <summary>
    /// Specifies that the parent annotation's placement shall be directly linked with the location 
    /// of the physical presentation of a custom XML element in the document. This element 
    /// only has an effect when the custom XML element is block-level (i.e. surrounds an entire 
    /// paragraph), as in this scenario the logical and physical placement of the annotation and 
    /// custom XML element can differ.
    /// </summary>
    /*DisplacedByType*/int getDisplacedBy() { return mDisplacedByType; }

    /// <summary>
    /// Checks if annotation element has id defined in 'w:id' attribute.
    /// </summary>
    boolean hasId() { return mId >= 0; }

    /// <summary>
    /// WML only.
    /// Checks if 'aml:annotation' element has 'Word.Comment' type and id defined in 'aml:id'.
    /// </summary>
    boolean isComment() { return ("Word.Comment".equals(mType)) && (mId >= 0); }

    private /*final*/ int mId = -1;
    private /*final*/ DateTime mDate = DateTime.MinValue; // Init for Java.
    private String mName = "";
    private /*final*/ String mAuthor = "";
    private /*final*/ String mInitials = "";
    private /*final*/ String mOriginal = "";
    private /*final*/ int mColFirst = -1;
    private /*final*/ int mColLast = -1;
    private /*final*/ String mType = "";
    private /*final*/ /*EditorType*/int mEdGrp = EditorType.UNSPECIFIED;
    private /*final*/ String mEd = "";
    private /*final*/ /*DisplacedByType*/int mDisplacedByType = DisplacedByType.UNSPECIFIED;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"id",
		"date",
		"createdate",
		"name",
		"author",
		"initials",
		"original",
		"colFirst",
		"col-first",
		"colLast",
		"col-last",
		"type",
		"edGrp",
		"ed",
		"displacedByCustomXml"
	);

}

