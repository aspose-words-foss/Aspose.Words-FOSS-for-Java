// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.msString;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ArgumentUtil;
import java.awt.Color;
import com.aspose.ms.System.msMath;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Guid;


/// <summary>
/// Represents Microsoft Forms 2.0 OLE control.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-ole-objects/">Working with Ole Objects</a> documentation article.</para>
/// </summary>
public abstract class Forms2OleControl extends OleControl
{
    /// <summary>
    /// Creates Forms2OleControl object with a specified name.
    /// </summary>
    Forms2OleControl(String name)
	{
		this(name, null);
	}
    /// <summary>
    /// Creates Forms2OleControl object with a specified name.
    /// </summary>
    Forms2OleControl(String name, Forms2Pr pr)
    {
    	super(name);
	
        Pr = (pr != null) ? pr : new Forms2Pr();
    }

    /// <summary>
    /// Creates instance of the <see cref="Forms2OleControl"/> object of a specified type and name.
    /// </summary>
    static Forms2OleControl create(/*Forms2OleControlType*/int type, String name)
    {
        switch (type)
        {
            case Forms2OleControlType.OPTION_BUTTON:
                return new OptionButtonControl(name);
            case Forms2OleControlType.LABEL:
                return new LabelControl(name);
            case Forms2OleControlType.TEXTBOX:
                return new TextBoxControl(name);
            case Forms2OleControlType.CHECK_BOX:
                return new CheckBoxControl(name);
            case Forms2OleControlType.TOGGLE_BUTTON:
                return new ToggleButtonControl(name);
            case Forms2OleControlType.SPIN_BUTTON:
                return new SpinButtonControl(name);
            case Forms2OleControlType.COMBO_BOX:
                return new ComboBoxControl(name);
            case Forms2OleControlType.FRAME:
                return new FrameControl(name);
            case Forms2OleControlType.MULTI_PAGE:
                return new MultiPageControl(name);
            case Forms2OleControlType.COMMAND_BUTTON:
                return new CommandButtonControl(name);
            case Forms2OleControlType.IMAGE:
                return new ImageControl(name);
            case Forms2OleControlType.SCROLL_BAR:
                return new ScrollBarControl(name);
            case Forms2OleControlType.TAB_STRIP:
                return new TabStripControl(name);
            case Forms2OleControlType.LIST_BOX:
                return new ListBoxControl(name);
            case Forms2OleControlType.FORM:
                return new FormControl(name);
            default:
                throw new IllegalStateException(msString.format("Unexpected Forms2OleControl type: {0}", type));
        }
    }

    /// <summary>
    /// Reads the control from a storage.
    /// </summary>
    /*override*/ void read(MemoryStorage storage) throws Exception
    {
        MemoryStream contentsStream = storage.getStreamZeroPositioned("contents");
        // WORDSNET-18787 Check ActiveX control has content stream.
        if (contentsStream != null)
            read(new BinaryReader(contentsStream));
    }

    /// <summary>
    /// Writes the control to a storage.
    /// </summary>
    /*override*/ void write(MemoryStorage storage) throws Exception
    {
        MemoryStream stream = new MemoryStream();
        write(new BinaryWriter(stream));
        storage.set("contents", stream);
    }

    /// <summary>
    /// Returns ProgId by a specified type.
    /// </summary>
    static String getProgId(String clsid)
    {
        /*Forms2OleControlType*/int type = clsidToType(clsid);
        return getProgId(type);
    }

    /// <summary>
    /// Returns ProgId by a specified type.
    /// </summary>
    private static String getProgId(/*Forms2OleControlType*/int type)
    {
        return msString.format("Forms.{0}.1", OleUtil.toString(type));
    }

    /// <summary>
    /// Converts a control type to a Clsid.
    /// </summary>
    private static /*Forms2OleControlType*/int clsidToType(String clsid)
    {
        switch (gStringSwitchMap.of(clsid.toLowerCase()))
        {
            case/*OPTION_BUTTON_CONTROL_CLSID*/0: return Forms2OleControlType.OPTION_BUTTON;
            case/*LABEL_CONTROL_CLSID*/1: return Forms2OleControlType.LABEL;
            case
    /*TEXT_BOX_CONTROL_CLSID*/2: return Forms2OleControlType.TEXTBOX;
            case/*CHECK_BOX_CONTROL_CLSID*/3: return Forms2OleControlType.CHECK_BOX;
            case/*TOGGLE_BUTTON_CONTROL_CLSID*/4: return Forms2OleControlType.TOGGLE_BUTTON;
            case/*SPIN_BUTTON_CONTROL_CLSID*/5: return Forms2OleControlType.SPIN_BUTTON;
            case/*COMBO_BOX_CONTROL_CLSID*/6: return Forms2OleControlType.COMBO_BOX;
            case/*FRAME_CONTROL_CLSID*/7: return Forms2OleControlType.FRAME;
            case /*MULTI_PAGE_CONTROL_CLSID*/8: return Forms2OleControlType.MULTI_PAGE;
            case /*COMMAND_BUTTON_CONTROL_CLSID*/9: return Forms2OleControlType.COMMAND_BUTTON;
            case/*IMAGE_CONTROL_CLSID*/10: return Forms2OleControlType.IMAGE;
            case/*SCROLL_BAR_CONTROL_CLSID*/11: return Forms2OleControlType.SCROLL_BAR;
            case/*TAB_STRIP_CONTROL_CLSID*/12: return Forms2OleControlType.TAB_STRIP;
            case/*LIST_BOX_CONTROL_CLSID*/13: return Forms2OleControlType.LIST_BOX;
            case/*FORM_CONTROL_CLSID*/14: return Forms2OleControlType.FORM;
            default:
                throw new IllegalStateException(msString.format("Unexpected CLSID: {0}", clsid));
        }
    }

    /// <summary>
    /// Returns value of the <see cref="VariousPropertiesBits"/> field at a specified bit positions.
    /// </summary>
    private boolean getVariablePropertiesBitsField(/*VariousPropertiesBits*//*uint*/int bits)
    {
        return ((getVariousPropertiesBits() & bits) != 0);
    }

    /// <summary>
    /// Sets the <see cref="VariousPropertiesBits"/> field at a specified bit positions to a specified value.
    /// </summary>
    private void setVariablePropertiesBitsField(/*VariousPropertiesBits*//*uint*/int bits, boolean value)
    {
        if (value)
            setVariousPropertiesBits(getVariousPropertiesBits() | bits);
        else
            setVariousPropertiesBits(getVariousPropertiesBits() & (~bits));
    }

    /// <summary>
    /// Gets or sets a Caption property of the control.
    /// Default value is an empty string.
    /// </summary>
    public String getCaption() { return (String)Pr.fetchAttr(Forms2Attr.CAPTION); }
    public void setCaption(String value) { Pr.setAttr(Forms2Attr.CAPTION, value); }

    /// <summary>
    /// Gets underlying Value property which often represents control state.
    /// For example checked option button has '1' value while unchecked has '0'.
    /// Default value is an empty string.
    /// </summary>
    public String getValue() { return (String)Pr.fetchAttr(Forms2Attr.VALUE); }
    void setValue(String value) { Pr.setAttr(Forms2Attr.VALUE, value); }

    /// <summary>
    /// Returns <c>true</c> if control is in enabled state.
    /// </summary>
    public boolean getEnabled() { return getVariablePropertiesBitsField(VariousPropertiesBits.ENABLED); }
    void setEnabled(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.ENABLED, value); }

    boolean getWordWrap() { return getVariablePropertiesBitsField(VariousPropertiesBits.WORD_WRAP); }
    void setWordWrap(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.WORD_WRAP, value); }

    boolean getMultiline() { return getVariablePropertiesBitsField(VariousPropertiesBits.MULTI_LINE); }
    void setMultiline(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.MULTI_LINE, value); }

    boolean getCaptionAlignmentLeft() { return getVariablePropertiesBitsField(VariousPropertiesBits.ALIGNMENT); }
    void setCaptionAlignmentLeft(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.ALIGNMENT, value); }

    boolean getSelectionMargin() { return getVariablePropertiesBitsField(VariousPropertiesBits.SELECTION_MARGIN); }
    void setSelectionMargin(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.SELECTION_MARGIN, value); }

    /// <summary>
    /// Gets collection of immediate child controls.
    /// </summary>
    /// <remarks>Returns <c>null</c> if this control can not have children.</remarks>
    public /*virtual*/ Forms2OleControlCollection getChildNodes() { return null; }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    
    public abstract /*Forms2OleControlType*/int getType();

    /// <summary>
    /// Gets or sets a string that specifies a group of mutually exclusive controls.
    /// The default value is an empty string.
    /// </summary>
    public String getGroupName() { return (String)Pr.fetchAttr(Forms2Attr.GROUP_NAME); }
    public void setGroupName(String value)
    {
        ArgumentUtil.checkNotNull(value, "GroupName");
        Pr.setAttr(Forms2Attr.GROUP_NAME, value);
    }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    abstract /*ClsidCacheIndex*/int getClsidCacheIndex();

    /// <summary>
    /// Gets a boolean value indicating either the Forms2 OleControl is composite.
    /// </summary>
    /*virtual*/ boolean isComposite() { return false; }

    /// <summary>
    /// Gets a clipboard format of the control.
    /// </summary>
    /*override*/ ClipboardFormat getClipboardFormat() { return G_CLIPBOARD_FORMAT; }

    /// <summary>
    /// Gets or sets a MousePointer that specifies the type of icon displayed as the mouse pointer for the control.
    /// </summary>
    /*MousePointer*/int getMousePointer() { return (/*MousePointer*/Integer)Pr.fetchAttr(Forms2Attr.MOUSE_POINTER); }
    void setMousePointer(/*MousePointer*/int value) { Pr.setAttr(Forms2Attr.MOUSE_POINTER, value); }

    /// <summary>
    /// Gets or sets bytes that specifies a custom icon to display as the mouse pointer for the control, which is used
    /// when the value of the <see cref="MousePointer"/> property is set to <see cref="Forms2.MousePointer.Custom"/>.
    /// </summary>
    byte[] getMouseIcon() { return (byte[])Pr.fetchAttr(Forms2Attr.MOUSE_ICON); }
    void setMouseIcon(byte[] value) { Pr.setAttr(Forms2Attr.MOUSE_ICON, value); }

    /// <summary>
    /// Gets or sets bytes that specifies the picture to display on a control.
    /// </summary>
    byte[] getPicture() { return (byte[])Pr.fetchAttr(Forms2Attr.PICTURE); }
    void setPicture(byte[] value) { Pr.setAttr(Forms2Attr.PICTURE, value); }

    /// <summary>
    /// Gets a string that is associated with a control and that contains data entered by the user.
    /// </summary>
    String getTag() { return (String)Pr.fetchAttr(Forms2Attr.TAG); }

    /// <summary>
    /// Gets a string that specifies the tooltip for the control.
    /// </summary>
    String getTooltip() { return (String)Pr.fetchAttr(Forms2Attr.TOOLTIPS); }

    /// <summary>
    /// Gets a string that specifies the license key of a control.
    /// </summary>
    String getRuntimeLicKey() { return (String)Pr.fetchAttr(Forms2Attr.RUNTIME_LIC_KEY); }

    /// <summary>
    /// Gets a string that specifies a cell in a worksheet that sets the Value property of a control when the
    /// control is loaded and to which the new value of the Value property is stored after it changes in the control.
    /// </summary>
    String getControlSource() { return (String)Pr.fetchAttr(Forms2Attr.CONTROL_SOURCE); }

    /// <summary>
    /// Gets a string that specifies the source for the list of values in a ComboBox or ListBox that is embedded
    /// in a form.
    /// </summary>
    String getRowSource() { return (String)Pr.fetchAttr(Forms2Attr.ROW_SOURCE); }

    /// <summary>
    /// Gets or sets a boolean value that specifies whether the control automatically resizes
    /// to display its entire contents.
    /// </summary>
    boolean getAutoSize() { return (/*boolean*/Boolean)Pr.fetchAttr(Forms2Attr.AUTO_SIZE); }
    void setAutoSize(boolean value) {Pr.setAttr(Forms2Attr.AUTO_SIZE, value);}

    /// <summary>
    /// Gets or sets a foreground color of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    public Color getForeColor() { return getForeColorInternal().getColor(); }
    public void setForeColor(Color value) { setForeColorInternal(OleColor.fromColor(value)); }

    /// <summary>
    /// Gets or sets a background color of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    public Color getBackColor() { return getBackColorInternal().getColor(); }
    public void setBackColor(Color value) { setBackColorInternal(OleColor.fromColor(value)); }

    /// <summary>
    /// Gets or sets a background style for the control.
    /// </summary>
    /*BackStyle*/int getBackStyle()
    {
        return (getVariablePropertiesBitsField(VariousPropertiesBits.BACK_STYLE))
            ? BackStyle.OPAQUE
            : BackStyle.TRANSPARENT;
    }
    void setBackStyle(/*BackStyle*/int value)
    {
        setVariablePropertiesBitsField(VariousPropertiesBits.BACK_STYLE, (value == BackStyle.OPAQUE));
    }

    /// <summary>
    /// Gets or sets a color of border of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    Color getBorderColor() { return getBorderColorInternal().getColor(); }
    void setBorderColor(Color value) { setBorderColorInternal(OleColor.fromColor(value)); }

    /// <summary>
    /// Gets or sets a type of border used by the control.
    /// </summary>
    /*BorderStyle*/int getBorderStyle() { return (/*BorderStyle*/Integer)Pr.fetchAttr(Forms2Attr.BORDER_STYLE); }
    void setBorderStyle(/*BorderStyle*/int value) { Pr.setAttr(Forms2Attr.BORDER_STYLE, value); }

    /*SpecialEffect*/int getSpecialEffect() { return (/*SpecialEffect*/Integer)Pr.fetchAttr(Forms2Attr.SPECIAL_EFFECT); }
    void setSpecialEffect(/*SpecialEffect*/int value) { Pr.setAttr(Forms2Attr.SPECIAL_EFFECT, value); }

    /// <summary>
    /// Gets or sets a size, in HIMETRIC units, of the control.
    /// </summary>
    OleSize getSize() { return (OleSize)Pr.fetchAttr(Forms2Attr.SIZE); }
    void setSize(OleSize value) { Pr.setAttr(Forms2Attr.SIZE, value);}

    /// <summary>
    /// Gets or sets a OlePosition that specifies the location of the top-left corner of an embedded control on a form,
    /// relative to the top-left corner of the LogicalSize of the form.
    /// The default value is (0, 0), which specifies that the top-left corner of the embedded control is at
    /// the top-left corner of the form.
    /// </summary>
    OlePosition getSitePosition() { return (OlePosition)Pr.fetchAttr(Forms2Attr.SITE_POSITION); }
    void setSitePosition(OlePosition value) { Pr.setAttr(Forms2Attr.SITE_POSITION, value); }


    /// <summary>
    /// Gets or sets a width of the control in points.
    /// </summary>
    /// <dev>
    /// Actually, Forms2OleControl keeps this value as integer, measured in HIMETRIC internally.
    /// See <see cref="OleSize"/> and <seealso cref="OlePosition"/> for details. So, the value of this property is
    /// converted from Points to HIMETRICs in setter and vise versa in getter. Note, such conversion can lead to mismatch
    /// in values of setter and getter due to rounding errors. Cannot completely understand rounding algorithm of
    /// Word in GUI and also cannot find it in VBA. So, for a moment, just round up it with 2 digits precision.
    /// </dev>
    public double getWidth() { return msMath.round(ConvertUtilCore.himetricToPoint(getSize().getWidth()), 2); }
    public void setWidth(double value) { setSize(new OleSize(ConvertUtilCore.pointToHimetricInt(value), getSize().getHeight())); }

    /// <summary>
    /// Gets or sets a height of the control in points.
    /// </summary>
    /// <dev>
    /// See additional explanation in <see cref="Width"/> property.
    /// </dev>
    public double getHeight() { return msMath.round(ConvertUtilCore.himetricToPoint(getSize().getHeight()), 2); }
    public void setHeight(double value) { setSize(new OleSize(getSize().getWidth(), ConvertUtilCore.pointToHimetricInt(value))); }

    /// <summary>
    /// Gets or sets a top-coordinate of a top-left corner of the embedded control on a form,
    /// relative to the top-left corner of the LogicalSize of the form.
    /// Measured in Points, with rounding up to 2 digits.
    /// The default value is 0.
    /// </summary>
    /// <dev>
    /// See additional explanation in <see cref="Width"/> property.
    /// </dev>
    double getTop() { return msMath.round(ConvertUtilCore.himetricToPoint(getSitePosition().getTop()), 2); }
    void setTop(double value) { setSitePosition(new OlePosition(ConvertUtilCore.pointToHimetricInt(value), getSitePosition().getLeft())); }

    /// <summary>
    /// Gets or sets a left-coordinate of a top-left corner of the embedded control on a form,
    /// relative to the top-left corner of the LogicalSize of the form.
    /// Measured in Points, with rounding up to 2 digits.
    /// The default value is 0.
    /// </summary>
    /// <dev>
    /// See additional explanation in <see cref="Width"/> property.
    /// </dev>
    double getLeft() { return msMath.round(ConvertUtilCore.himetricToPoint(getSitePosition().getLeft()), 2); }
    void setLeft(double value) { setSitePosition(new OlePosition(getSitePosition().getTop(), ConvertUtilCore.pointToHimetricInt(value))); }

    /// <summary>
    /// Gets or sets a unique identifier for an embedded control on a form.
    /// </summary>
    int getIdInternal() { return (/*int*/Integer)Pr.fetchAttr(Forms2Attr.ID); }
    void setIdInternal(int value) { Pr.setAttr(Forms2Attr.ID, value); }

    /// <summary>
    /// Gets or sets the size, in bytes, of an embedded control that is persisted to the Object stream of a Form.
    /// </summary>
    /*uint*/int getObjectStreamSize() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.OBJECT_STREAM_SIZE); }
    void setObjectStreamSize(/*uint*/int value) { Pr.setAttr(Forms2Attr.OBJECT_STREAM_SIZE, value); }

    /// <summary>
    /// Get or set a Unicode character that specifies the accelerator key for the control.
    /// The default value is 0x0000, no accelerator.
    /// </summary>
    char getAccelerator() { return (/*char*/Character)Pr.fetchAttr(Forms2Attr.ACCELERATOR); }
    void setAccelerator(char value) { Pr.setAttr(Forms2Attr.ACCELERATOR, value); }

    /// <summary>
    /// Gets or sets a boolean value that specifies whether data in the control is locked for editing.
    /// The default value is <c>false</c>.
    /// </summary>
    boolean getLocked() { return getVariablePropertiesBitsField(VariousPropertiesBits.LOCKED); }
    void setLocked(boolean value) { setVariablePropertiesBitsField(VariousPropertiesBits.LOCKED, value); }

    /// <summary>
    /// Gets or sets a boolean value that specifies whether the control takes the focus when clicked.
    /// The default value is <c>true</c>.
    /// </summary>
    boolean getTakeFocusOnClick() { return (/*boolean*/Boolean)Pr.fetchAttr(Forms2Attr.TAKE_FOCUS_ON_CLICK); }
    void setTakeFocusOnClick(boolean value) { Pr.setAttr(Forms2Attr.TAKE_FOCUS_ON_CLICK, value); }

    /// <summary>
    /// Gets a UserType of the control.
    /// </summary>
    protected /*override*/ String getUserType() { return msString.format("Microsoft Forms 2.0 {0}", OleUtil.toString(getType())); }

    /// <summary>
    /// Gets a ProgId of the control.
    /// </summary>
    protected /*override*/ String getProgId() { return getProgId(getType()); }

    /// <summary>
    /// Gets or sets a background color of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    private OleColor getBackColorInternal() { return (OleColor)Pr.fetchAttr(Forms2Attr.BACKGROUND_COLOR); }
    private void setBackColorInternal(OleColor value) { Pr.setAttr(Forms2Attr.BACKGROUND_COLOR, value); }

    /// <summary>
    /// Gets or sets a foreground color of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    private OleColor getForeColorInternal() { return (OleColor)Pr.fetchAttr(Forms2Attr.FOREGROUND_COLOR); }
    private void setForeColorInternal(OleColor value) { Pr.setAttr(Forms2Attr.FOREGROUND_COLOR, value); }

    /// <summary>
    /// Gets or sets a color of border of the control.
    /// The default value depends on a type of the control.
    /// </summary>
    private OleColor getBorderColorInternal() { return (OleColor)Pr.fetchAttr(Forms2Attr.BORDER_COLOR); }
    private void setBorderColorInternal(OleColor value) { Pr.setAttr(Forms2Attr.BORDER_COLOR, value); }

    /// <summary>
    /// Gets or sets a VariousPropertiesBits field.
    /// </summary>
    private /*VariousPropertiesBits*//*uint*/int getVariousPropertiesBits() { return (/*VariousPropertiesBits*/Integer)Pr.fetchAttr(Forms2Attr.VARIOUS_PROPERTY_BITS); }
    private void setVariousPropertiesBits(/*VariousPropertiesBits*//*uint*/int value) { Pr.setAttr(Forms2Attr.VARIOUS_PROPERTY_BITS, value); }

    /// <summary>
    /// The collection of properties of the control.
    /// </summary>
    /*final*/ Forms2Pr Pr;

    /// <summary>
    /// StdPicture GUID as specified in [MS-OFORMS], 2.4.8 GuidAndPicture.
    /// </summary>
    static /*final*/ Guid CLSID_STD_PICTURE = new Guid("0be35204-8f91-11ce-9de3-00aa004bb851");

    /// <summary>
    /// StdPicture preamble as specified in [MS-OFORMS], 2.4.13 StdPicture.
    /// </summary>
    static final int STD_PICTURE_PREAMBLE = 0x0000746C;

    /// <summary>
    /// The version of Forms2OleControls.
    /// </summary>
    static final short FORMS_2_VERSION = 0x0200;

    /// <summary>
    /// Specifies a ClipboardFormat of the Forms 2.0 controls.
    /// </summary>
    private static /*final*/ ClipboardFormat G_CLIPBOARD_FORMAT = new ClipboardFormat("Embedded Object");

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"8bd21d50-ec42-11ce-9e0d-00aa006002f3",
		"978c9e23-d4b0-11ce-bf2d-00aa003f40d0",
		"8bd21d10-ec42-11ce-9e0d-00aa006002f3",
		"8bd21d40-ec42-11ce-9e0d-00aa006002f3",
		"8bd21d60-ec42-11ce-9e0d-00aa006002f3",
		"79176fb0-b7f2-11ce-97ef-00aa006d2776",
		"8bd21d30-ec42-11ce-9e0d-00aa006002f3",
		"6e182020-f460-11ce-9bcd-00aa00608e01",
		"46e31370-3f7a-11ce-bed6-00aa00611080",
		"d7053240-ce69-11cd-a777-00dd01143c57",
		"4c599241-6926-101b-9992-00000b65c6f9",
		"dfd181e0-5e2f-11ce-a449-00aa004a803d",
		"eae50eb0-4a62-11ce-bed6-00aa00611080",
		"8bd21d20-ec42-11ce-9e0d-00aa006002f3",
		"c62a69f0-16dc-11ce-9e98-00aa00574a4f"
	);

}


