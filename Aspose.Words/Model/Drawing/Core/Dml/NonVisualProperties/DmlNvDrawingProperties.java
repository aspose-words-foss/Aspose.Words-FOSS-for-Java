// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/07/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.3.2.7 cNvPr (Non-Visual Drawing Properties)
/// This element specifies non-visual canvas properties. This allows for additional information that does not affect
/// the appearance of the picture to be stored.
/// </summary>
class DmlNvDrawingProperties extends DmlExtensionListSource implements Cloneable
{
    DmlNvDrawingProperties deepClone()
    {
        DmlNvDrawingProperties lhs = (DmlNvDrawingProperties)memberwiseClone();
        lhs.setExtensions(cloneExtensions());
        return lhs;
    }

    DmlNvDrawingProperties()
    {
    }

    DmlNvDrawingProperties(int id, String name)
    {
        mId = id;
        mName = name;
    }

    /// <summary>
    /// Specifies alternative text for the current DrawingML object.
    /// </summary>
    String getDescription() { return mDescription; }
    void setDescription(String value) { mDescription = value; }

    /// <summary>
    /// Specifies the name of the object.
    /// </summary>
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    /// <summary>
    /// Specifies the title (caption) of the current DrawingML object.
    /// </summary>
    String getTitle() { return mTitle; }
    void setTitle(String value) { mTitle = value; }

    /// <summary>
    /// Specifies whether this DrawingML object is displayed.
    /// </summary>
    boolean getHidden() { return mHidden; }
    void setHidden(boolean value) { mHidden = value; }

    /// <summary>
    /// Specifies a unique identifier for the current DrawingML object within the current
    /// document. This ID can be used to assist in uniquely identifying this object so that it can
    /// be referred to by other parts of the document.
    /// </summary>
    int getId() { return mId; }
    void setId(int value) { mId = value; }

    /// <summary>
    /// Specifies the on-click hyperlink information to be applied to a run of text. When the hyperlink text is clicked the
    /// link will be fetched.
    /// </summary>
    DmlHlink getHlinkClick() { return mHlinkClick; }
    void setHlinkClick(DmlHlink value) { mHlinkClick = value; }

    /// <summary>
    /// Specifies the hyperlink information to be activated when the user's mouse is hovered over the corresponding object.
    /// </summary>
    DmlHlink getHlinkHover() { return mHlinkHover; }
    void setHlinkHover(DmlHlink value) { mHlinkHover = value; }

    private int mId;
    private boolean mHidden;
    private String mName;
    private String mTitle;
    private String mDescription;
    private DmlHlink mHlinkClick;
    private DmlHlink mHlinkHover;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

