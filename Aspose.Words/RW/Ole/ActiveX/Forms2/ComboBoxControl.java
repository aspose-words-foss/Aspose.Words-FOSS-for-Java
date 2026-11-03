// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The ComboBox control combines a <see cref="TextBoxControl"/> with a <see cref="ListBoxControl" />
/// to create a drop-down list box.
/// </summary>
class ComboBoxControl extends MorphDataControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ComboBoxControl(String name)
    {
    	super(name);
	
        setDisplayStyle(DisplayStyle.COMBO);
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.COMBO_BOX; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.COMBO_BOX; }

    /// <summary>
    /// Gets selected value of the ComboBoxControl.
    /// </summary>
    String getSelectedValue() { return (String)Pr.fetchAttr(Forms2Attr.VALUE); }
    void setSelectedValue(String value) { Pr.setAttr(Forms2Attr.VALUE, value); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return COMBO_BOX_CONTROL_CLSID; }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    /*ShowDropButtonWhen*/int getShowDropButtonWhen() { return (/*ShowDropButtonWhen*/Integer)Pr.fetchAttr(Forms2Attr.SHOW_DROP_BUTTON_WHEN); }
    void setShowDropButtonWhen(/*ShowDropButtonWhen*/int value) { Pr.setAttr(Forms2Attr.SHOW_DROP_BUTTON_WHEN, value); }

    /*DropButtonStyle*/int getDropButtonStyle() { return (/*DropButtonStyle*/Integer)Pr.fetchAttr(Forms2Attr.DROP_BUTTON_STYLE); }
    void setDropButtonStyle(/*DropButtonStyle*/int value) { Pr.setAttr(Forms2Attr.DROP_BUTTON_STYLE, value); }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(72.0, 18.0);
}

