// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The ListBox control displays a list of one or more items of text from which a user can choose.
/// </summary>
class ListBoxControl extends MorphDataControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ListBoxControl(String name)
    {
    	super(name);
	
        setDisplayStyle(DisplayStyle.LIST);
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.LIST_BOX; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.LIST_BOX; }

    /// <summary>
    /// Gets selected value of the ListBox.
    /// </summary>
    String getSelectedValue() { return (String)Pr.fetchAttr(Forms2Attr.VALUE); }
    void setSelectedValue(String value) { Pr.setAttr(Forms2Attr.VALUE, value); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return LIST_BOX_CONTROL_CLSID; }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    /*ListStyle*/int getListStyle() { return (/*ListStyle*/Integer)Pr.fetchAttr(Forms2Attr.LIST_STYLE); }
    void setListStyle(/*ListStyle*/int value) { Pr.setAttr(Forms2Attr.LIST_STYLE, value); }

    /*MultiSelect*/int getMultiSelect() { return (/*MultiSelect*/Integer)Pr.fetchAttr(Forms2Attr.MULTI_SELECT); }
    void setMultiSelect(/*MultiSelect*/int value) { Pr.setAttr(Forms2Attr.MULTI_SELECT, value); }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(72.0, 72.0);
}

