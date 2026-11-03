// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/05/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Common object for:
/// 5.1.5.3.5 hlinkClick (Click Hyperlink)
/// 5.1.2.1.23 hlinkHover (Hyperlink for Hover)
/// 21.1.2.3.5 hlinkClick (Click Hyperlink)
/// 21.1.2.3.6 hlinkMouseOver (Mouse-Over Hyperlink)
/// </summary>
class DmlHlink extends DmlExtensionListSource implements Cloneable
{
    public DmlHlink deepClone()
    {
        DmlHlink lhs = (DmlHlink)memberwiseClone();
        lhs.setExtensions(lhs.cloneExtensions());

        return lhs;
    }

    /// <summary>
    /// Drawing Object Hyperlink Target.
    /// Specifies the relationship id that when looked up in this slides relationship file contains 
    /// the target of this hyperlink. This attribute cannot be omitted.
    /// </summary>
    public String getId() { return mId; }
    public void setId(String value) { mId = value; }

    /// <summary>
    /// Specifies the target frame that is to be used when opening this hyperlink. 
    /// </summary>
    public String getTargetFrame() { return mTargetFrame; }
    public void setTargetFrame(String value) { mTargetFrame = value; }

    /// <summary>
    /// Hyperlink Tooltip.
    /// Specifies the tooltip that should be displayed when the hyperlink text is hovered over with the mouse.
    /// If this attribute is omitted, than the hyperlink text itself can be displayed.
    /// </summary>
    public String getTooltip() { return mTooltip; }
    public void setTooltip(String value) { mTooltip = value; }

    private String mId;
    private String mTargetFrame;
    private String mTooltip;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

