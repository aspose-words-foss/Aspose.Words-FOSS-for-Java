// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableBool;


/// <summary>
/// The CheckBox control toggles a value.
/// </summary>
/// <remarks>
/// It has three possible states: selected, cleared, and neither selected nor cleared,
/// meaning a combination of on and off states.
/// </remarks>
public class CheckBoxControl extends MorphDataControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    CheckBoxControl(String name)
    {
    	super(name);
	
        setDisplayStyle(DisplayStyle.CHECK_BOX);
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    CheckBoxControl(String name, String caption, /*NullableBool*/byte checkedState)
    {
    	this(name);
	
        setCaption(caption);
        setCheckedInternal(checkedState);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.CHECK_BOX; }

    /// <summary>
    /// Gets or sets a boolean value indicating either this <see cref="CheckBoxControl"/> is checked or not.
    /// The default value is <c>false</c>.
    /// </summary>
    public boolean getChecked() { return (OleUtil.toNullableBool(getValue()) == NullableBool.TRUE); }
    public void setChecked(boolean value) { setValue(OleUtil.toString(value ? NullableBool.TRUE : NullableBool.FALSE)); }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.CHECK_BOX; }

    /// <summary>
    /// Gets or sets a state of the control.
    /// </summary>
    /*NullableBool*/byte getCheckedInternal() { return OleUtil.toNullableBool(getValue()); }
    void setCheckedInternal(/*NullableBool*/byte value) { setValue(OleUtil.toString(value)); }

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
    protected /*override*/ String getClsidVirtual() { return CHECK_BOX_CONTROL_CLSID; }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(108.0, 18.0);
}

