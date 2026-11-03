// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.MemoryStorage;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.Guid;
import com.aspose.ArgumentUtil;
import com.aspose.ms.System.IO.Stream;


/// <summary>
/// Represents OLE ActiveX control.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-ole-objects/">Working with Ole Objects</a> documentation article.</para>
/// </summary>
public class OleControl implements IEmbeddedObject
{
    /// <summary>
    /// Creates instance of the <see cref="OleControl"/> object with a specified name.
    /// </summary>
    OleControl(String name)
    {
        mName = name;
        mId = UniqueIdManager.generateInteger();
    }

    /// <summary>
    /// Returns instance of the <see cref="OleControl"/> object created from a specified storage,
    /// or <c>null</c> if the storage is not a <see cref="OleControl"/> object.
    /// </summary>
    static OleControl create(MemoryStorage storage) throws Exception
    {
        if (!isOleControl(storage))
            return null;

        String name = getName(storage);

        OleControl oleControl;
        switch (gStringSwitchMap.of(storage.getClsid().toString()))
        {
            case /*HTML_OPTION_CLSID*/0:
                oleControl = new HtmlOptionOleControl(name);
                break;
            case /*HTML_SELECT_CLSID*/1:
            case /*HTML_SUBMIT_BUTTON_CLSID*/2:
            case /*HTML_HIDDEN_CLSID*/3:
            case /*HTML_TEXT_CLSID*/4:
                oleControl = new HtmlOleControl(name);
                break;

            case /*OPTION_BUTTON_CONTROL_CLSID*/5:
                oleControl = new OptionButtonControl(name);
                break;
            case /*LABEL_CONTROL_CLSID*/6:
                oleControl = new LabelControl(name);
                break;
            case /*TEXT_BOX_CONTROL_CLSID*/7:
                oleControl = new TextBoxControl(name);
                break;
            case /*CHECK_BOX_CONTROL_CLSID*/8:
                oleControl = new CheckBoxControl(name);
                break;
            case /*TOGGLE_BUTTON_CONTROL_CLSID*/9:
                oleControl = new ToggleButtonControl(name);
                break;
            case /*SPIN_BUTTON_CONTROL_CLSID*/10:
                oleControl = new SpinButtonControl(name);
                break;
            case /*COMBO_BOX_CONTROL_CLSID*/11:
                oleControl = new ComboBoxControl(name);
                break;
            case /*COMMAND_BUTTON_CONTROL_CLSID*/12:
                oleControl = new CommandButtonControl(name);
                break;
            case /*IMAGE_CONTROL_CLSID*/13:
                oleControl = new ImageControl(name);
                break;
            case /*SCROLL_BAR_CONTROL_CLSID*/14:
                oleControl = new ScrollBarControl(name);
                break;
            case /*TAB_STRIP_CONTROL_CLSID*/15:
                oleControl = new TabStripControl(name);
                break;
            case /*LIST_BOX_CONTROL_CLSID*/16:
                oleControl = new ListBoxControl(name);
                break;
            case /*FORM_CONTROL_CLSID*/17:
                oleControl = new FormControl(name);
                break;
            case /*FRAME_CONTROL_CLSID*/18:
                oleControl = new FrameControl(name);
                break;
            case /*MULTI_PAGE_CONTROL_CLSID*/19:
                oleControl = new MultiPageControl(name);
                break;
            default:
                oleControl = new OleControl(name);
                break;
        }

        oleControl.read(storage);
        return oleControl;
    }

    /// <summary>
    ///Returns true, if a specified Clsid corresponds to <see cref="Forms2OleControl"/>.
    /// </summary>
    static boolean isForms2OleClsid(String clsid)
    {
        return msString.equals(clsid, OPTION_BUTTON_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, LABEL_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, TEXT_BOX_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, CHECK_BOX_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, TOGGLE_BUTTON_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, SPIN_BUTTON_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, COMBO_BOX_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, COMMAND_BUTTON_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, IMAGE_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, SCROLL_BAR_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, TAB_STRIP_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, LIST_BOX_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, FORM_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, FRAME_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE) ||
               msString.equals(clsid, MULTI_PAGE_CONTROL_CLSID, StringComparison.ORDINAL_IGNORE_CASE);
    }

    /// <summary>
    /// Reads the control from a storage.
    /// </summary>
    /*virtual*/ void read(MemoryStorage storage) throws Exception
    {
        mParentOleObjectData = storage;
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*virtual*/ void read(BinaryReader reader) throws Exception
    {
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*virtual*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        return 0;
    }

    /// <summary>
    /// Writes the control to a storage.
    /// </summary>
    /*virtual*/ void write(MemoryStorage storage) throws Exception
    {
    }

    /// <summary>
    /// Adds CompObj stream to OLE data storage.
    /// </summary>
    private void addCompObjStream(MemoryStorage oleData) throws Exception
    {
        CompObjStream compObjStream = new CompObjStream();
        compObjStream.setProgId(getProgId());
        compObjStream.setClsid(new Guid(getClsidVirtual()));
        compObjStream.setUserType(getUserType());
        compObjStream.setAnsiClipboardFormat(getClipboardFormat());
        compObjStream.write(oleData);
    }

    /// <summary>
    /// Returns name of the control from <see cref="OcxNameStream"/> of a specified storage.
    /// </summary>
    private static String getName(MemoryStorage storage) throws Exception
    {
        OcxNameStream ocxName = OcxNameStream.read(storage);
        return (ocxName != null) ? ocxName.getValue() : "";
    }

    /// <summary>
    /// Returns true, if a specified storage is OLE control.
    /// </summary>
    private static boolean isOleControl(MemoryStorage storage) throws Exception
    {
        ObjInfoStream objInfo = ObjInfoStream.read(storage);

        return (objInfo != null) && ((objInfo.getFlags1() & OdtPersist1.OCX) != 0);
    }

    /// <summary>
    /// Gets or sets name of the ActiveX control.
    /// </summary>
    public String getName() { return mName; }
    public void setName(String value)
    {
        ArgumentUtil.checkHasChars(value, "Name");
        mName = value;
    }

    /// <summary>
    /// Returns <c>true</c> if the control is a <see cref="Forms2OleControl"/>.
    /// </summary>
    public boolean isForms2OleControl() { return isForms2OleClsid(getClsidVirtual()); }

    /// <summary>
    /// Gets a ClipboardFormat of the control.
    /// </summary>
    /*virtual*/ ClipboardFormat getClipboardFormat() { return null; }

    /// <summary>
    /// Returns instance of <see cref="OleObject"/> object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public OleObject /*IEmbeddedObject.*/getOleObject() throws Exception
    {
        OcxNameStream ocxName;
        if (mParentOleObjectData != null)
        {
            // As there exists memory storage of the parent OleObject, then this is unparsed OleControl.
            // For the moment, only Name can be changed in such controls.
            ocxName = new OcxNameStream(getName());
            ocxName.write(mParentOleObjectData);
            return new OleObject(mParentOleObjectData);
        }

        MemoryStorage storage = new MemoryStorage(new Guid(getClsidVirtual()));
        addCompObjStream(storage);

        // IN. Actually, ObjInfo and OcxName streams are present only in DOC and RTF. But Word is happy with these streams
        // in other formats as well. See additional comments made by RK in WmlWriter.WriteOleControl().
        ObjInfoStream objInfo = ObjInfoStream.defaultControl();
        objInfo.write(storage);

        ocxName = new OcxNameStream(getName());
        ocxName.write(storage);

        write(storage);

        return new OleObject(storage);
    }

    /// <summary>
    /// Returns the extension (with the leading dot).
    /// The extension must be for the inner (e.g. unwrapped) data that can be saved directly to a file.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String /*IEmbeddedObject.*/getExtensionForUser(String progId)
    {
        return "";
    }

    @ReservedForInternalUse @Deprecated
    public String /*IEmbeddedObject.*/getFileNameForUser()
    {
        return "";
    }

    /// <summary>
    /// Saves the embedded object data in a way that makes it a valid standalone file.
    /// </summary>
    public void /*IEmbeddedObject.*/saveForUser(Stream stream, IShapeAttrSource attrSource)
    {
        // Nothing to do.
    }

    /// <summary>
    /// Gets a boolean value, indicating either the embedded object is Forms2Ole control.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public boolean /*IEmbeddedObject.*/isForms2OleControlInternal() { return isForms2OleControl(); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public String /*IEmbeddedObject.*/getClsidInternal() { return getClsidVirtual(); }

    /// <summary>
    /// Gets or sets Id of the embedded object.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public int /*IEmbeddedObject.*/getId() { return mId; }
    @ReservedForInternalUse @Deprecated
    public void /*IEmbeddedObject.*/setId(int value) { mId = value; }

    @ReservedForInternalUse @Deprecated
    public boolean /*IEmbeddedObject.*/isEmpty() { return false; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*virtual*/ String getClsidVirtual() { return Guid.Empty.toString(); }

    /// <summary>
    /// Gets a UserType of the control.
    /// </summary>
    protected /*virtual*/ String getUserType() { return ""; }

    /// <summary>
    /// Gets a ProgId of the control.
    /// </summary>
    protected /*virtual*/ String getProgId() { return ""; }

    /// <summary>
    /// Represents Id of the embedded object.
    /// </summary>
    private int mId;

    static final String HTML_OPTION_CLSID = "5512d118-5cc6-11cf-8d67-00aa00bdce1d";

    protected static final String HTML_SELECT_CLSID = "5512d122-5cc6-11cf-8d67-00aa00bdce1d";
    protected static final String HTML_SUBMIT_BUTTON_CLSID = "5512d110-5cc6-11cf-8d67-00aa00bdce1d";
    protected static final String HTML_HIDDEN_CLSID = "5512d11c-5cc6-11cf-8d67-00aa00bdce1d";
    protected static final String HTML_TEXT_CLSID = "5512d11a-5cc6-11cf-8d67-00aa00bdce1d";

    protected static final String OPTION_BUTTON_CONTROL_CLSID = "8bd21d50-ec42-11ce-9e0d-00aa006002f3";
    protected static final String LABEL_CONTROL_CLSID = "978c9e23-d4b0-11ce-bf2d-00aa003f40d0";
    protected static final String TEXT_BOX_CONTROL_CLSID = "8bd21d10-ec42-11ce-9e0d-00aa006002f3";
    protected static final String CHECK_BOX_CONTROL_CLSID = "8bd21d40-ec42-11ce-9e0d-00aa006002f3";
    protected static final String TOGGLE_BUTTON_CONTROL_CLSID = "8bd21d60-ec42-11ce-9e0d-00aa006002f3";
    protected static final String SPIN_BUTTON_CONTROL_CLSID = "79176fb0-b7f2-11ce-97ef-00aa006d2776";
    protected static final String COMBO_BOX_CONTROL_CLSID = "8bd21d30-ec42-11ce-9e0d-00aa006002f3";
    protected static final String COMMAND_BUTTON_CONTROL_CLSID = "d7053240-ce69-11cd-a777-00dd01143c57";
    protected static final String IMAGE_CONTROL_CLSID = "4c599241-6926-101b-9992-00000b65c6f9";
    protected static final String SCROLL_BAR_CONTROL_CLSID = "dfd181e0-5e2f-11ce-a449-00aa004a803d";
    protected static final String TAB_STRIP_CONTROL_CLSID = "eae50eb0-4a62-11ce-bed6-00aa00611080";
    protected static final String LIST_BOX_CONTROL_CLSID = "8bd21d20-ec42-11ce-9e0d-00aa006002f3";
    protected static final String FORM_CONTROL_CLSID = "c62a69f0-16dc-11ce-9e98-00aa00574a4f";
    protected static final String FRAME_CONTROL_CLSID = "6e182020-f460-11ce-9bcd-00aa00608e01";
    protected static final String MULTI_PAGE_CONTROL_CLSID = "46e31370-3f7a-11ce-bed6-00aa00611080";

    private String mName;

    /// <summary>
    /// Keeps <see cref="OleObject.Data"/> storage of the OleObject from which this control was created.
    /// This is meaningful only for controls that are not parsed (<see cref="Create"/> for details).
    /// </summary>
    private MemoryStorage mParentOleObjectData;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"5512d118-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d122-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d110-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d11c-5cc6-11cf-8d67-00aa00bdce1d",
		"5512d11a-5cc6-11cf-8d67-00aa00bdce1d",
		"8bd21d50-ec42-11ce-9e0d-00aa006002f3",
		"978c9e23-d4b0-11ce-bf2d-00aa003f40d0",
		"8bd21d10-ec42-11ce-9e0d-00aa006002f3",
		"8bd21d40-ec42-11ce-9e0d-00aa006002f3",
		"8bd21d60-ec42-11ce-9e0d-00aa006002f3",
		"79176fb0-b7f2-11ce-97ef-00aa006d2776",
		"8bd21d30-ec42-11ce-9e0d-00aa006002f3",
		"d7053240-ce69-11cd-a777-00dd01143c57",
		"4c599241-6926-101b-9992-00000b65c6f9",
		"dfd181e0-5e2f-11ce-a449-00aa004a803d",
		"eae50eb0-4a62-11ce-bed6-00aa00611080",
		"8bd21d20-ec42-11ce-9e0d-00aa006002f3",
		"c62a69f0-16dc-11ce-9e98-00aa00574a4f",
		"6e182020-f460-11ce-9bcd-00aa00608e01",
		"46e31370-3f7a-11ce-bed6-00aa00611080"
	);

}

