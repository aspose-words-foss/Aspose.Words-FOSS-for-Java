// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableBool;


/// <summary>
/// The OptionButton control enables a single choice in a limited set of mutually exclusive choices.
/// </summary>
public class OptionButtonControl extends MorphDataControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    OptionButtonControl(String name)
    {
    	super(name);
	
        setDisplayStyle(DisplayStyle.OPTION_BUTTON);
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.OPTION_BUTTON; }

    /// <summary>
    /// Gets or sets a boolean value indicating either this <see cref="OptionButtonControl"/> is selected or not.
    /// </summary>
    /// <remarks>
    /// Note, this property allows you to select multiple items in a group of <see cref="OptionButtonControl"/>
    /// with the same <see cref="Forms2OleControl.GroupName"/>. It is up to you to take care of deselecting a previously
    /// selected item when you make this <see cref="OptionButtonControl"/> selected.
    /// </remarks>
    public boolean getSelected() { return (OleUtil.toNullableBool(getValue()) == NullableBool.TRUE); }
    public void setSelected(boolean value) { setValue(OleUtil.toString(value? NullableBool.TRUE : NullableBool.FALSE)); }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.OPTION_BUTTON; }

    /// <summary>
    /// Gets or sets a selected state of the control.
    /// </summary>
    /*NullableBool*/byte getSelectedInternal() { return OleUtil.toNullableBool(getValue()); }
    void setSelectedInternal(/*NullableBool*/byte value) { setValue(OleUtil.toString(value)); }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return OPTION_BUTTON_CONTROL_CLSID; }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(108.0, 18.0);
}

