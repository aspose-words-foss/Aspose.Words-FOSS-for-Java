// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableBool;


/// <summary>
/// The ToggleButton control indicates a state, such as Yes/No, or a mode, such as On/Off.
/// </summary>
class ToggleButtonControl extends MorphDataControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ToggleButtonControl(String name)
    {
    	super(name);
	
        setDisplayStyle(DisplayStyle.TOGGLE);
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.TOGGLE_BUTTON; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.TOGGLE_BUTTON; }

    /// <summary>
    /// Gets or sets a state of the control.
    /// </summary>
    /*NullableBool*/byte getChecked() { return OleUtil.toNullableBool(getValue()); }
    void setChecked(/*NullableBool*/byte value) { setValue(OleUtil.toString(value)); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return TOGGLE_BUTTON_CONTROL_CLSID; }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(36.0, 40.0);
}

