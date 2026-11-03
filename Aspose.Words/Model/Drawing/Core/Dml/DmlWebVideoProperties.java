// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/09/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an extension that specifies the properties for displaying an online video inside document.
/// </summary>
/// <remarks>
/// [MS-ODRAWXML] 2.20.1.1 webVideoPr.
/// </remarks>
class DmlWebVideoProperties implements Cloneable
{
    DmlWebVideoProperties()
    {
    }

    DmlWebVideoProperties(String embedHtml, double frameWidth, double frameHeight)
    {
        mEmbedHtml = embedHtml;
        mFrameWidth = frameWidth;
        mFrameHeight = frameHeight;
    }

    DmlWebVideoProperties deepClone()
    {
        return (DmlWebVideoProperties)memberwiseClone();
    }

    /// <summary>
    /// Specifies the embedded HTML to be rendered within the BLIP. 
    /// This attribute SHOULD NOT be omitted if the video playback experience is to be fully preserved.
    /// </summary>
    String getEmbedHtml() { return mEmbedHtml; }
    void setEmbedHtml(String value) { mEmbedHtml = value; }

    /// <summary>
    /// Specifies the width of the rendered html page in pixels. 
    /// This attribute SHOULD NOT be omitted if the video playback experience is to be fully preserved.
    /// </summary>
    double getFrameWidth() { return mFrameWidth; }
    void setFrameWidth(double value) { mFrameWidth = value; }

    /// <summary>
    /// Specifies the height of the rendered html page in pixels. 
    /// This attribute SHOULD NOT be omitted if the video playback experience is to be fully preserved.
    /// </summary>
    double getFrameHeight() { return mFrameHeight; }
    void setFrameHeight(double value) { mFrameHeight = value; }

    private String mEmbedHtml;
    private double mFrameWidth;
    private double mFrameHeight;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}
