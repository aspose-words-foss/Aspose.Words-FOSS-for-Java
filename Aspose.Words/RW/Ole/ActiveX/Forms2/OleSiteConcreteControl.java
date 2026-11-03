// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements [MS-OFORMS] 2.2.10.12.1 OleSiteConcreteControl
/// </summary>
class OleSiteConcreteControl
{
    /// <summary>
    /// Reads site from a binary reader.
    /// </summary>
    OleSiteConcreteControl(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue((VERSION & 0xFFFF) == version);

        // cbSite
        reader.readUInt16();
        /*SitePropMask*//*uint*/int flags = (/*SitePropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, mPr);
        prReader.Flags = (/*uint*/int)flags;

        /*uint*/int cName = prReader.readData((/*uint*/int)SitePropMask.NAME, 4);
        /*uint*/int cTag = prReader.readData((/*uint*/int)SitePropMask.TAG, 4);
        prReader.readData((/*uint*/int)SitePropMask.ID, 4, Forms2Attr.ID);
        prReader.readData((/*uint*/int) SitePropMask.HELP_CONTEXT_ID, 4, Forms2Attr.HELP_CONTEXT_ID);
        prReader.readData((/*uint*/int)SitePropMask.BIT_FLAGS, 4, Forms2Attr.BIT_FLAGS_SITE);
        prReader.readData((/*uint*/int) SitePropMask.OBJECT_STREAM_SIZE, 4, Forms2Attr.OBJECT_STREAM_SIZE);
        prReader.readData((/*uint*/int)SitePropMask.TAB_INDEX, 2, Forms2Attr.TAB_INDEX);
        prReader.readData((/*uint*/int) SitePropMask.CLSID_CACHE_INDEX, 2, Forms2Attr.CLSID_CACHE_INDEX);
        prReader.readData((/*uint*/int) SitePropMask.GROUP_ID, 2, Forms2Attr.GROUP_ID);

        /*uint*/int cTipText = prReader.readData((/*uint*/int)SitePropMask.CONTROL_TIP_TEXT, 4);
        /*uint*/int cRuntimeLicKey = prReader.readData((/*uint*/int)SitePropMask.RUNTIME_LIC_KEY, 4);
        /*uint*/int cControlSource = prReader.readData((/*uint*/int)SitePropMask.CONTROL_SOURCE, 4);
        /*uint*/int cRowSource = prReader.readData((/*uint*/int)SitePropMask.ROW_SOURCE, 4);

        prReader.readString((/*uint*/int)SitePropMask.NAME, cName, Forms2Attr.NAME);
        prReader.readString((/*uint*/int)SitePropMask.TAG, cTag, Forms2Attr.TAG);

        prReader.readBytes((/*uint*/int)SitePropMask.POSITION, 8, Forms2Attr.SITE_POSITION);

        prReader.readString((/*uint*/int)SitePropMask.CONTROL_TIP_TEXT, cTipText, Forms2Attr.CONTROL_TIP_TEXT);
        prReader.readString((/*uint*/int)SitePropMask.RUNTIME_LIC_KEY, cRuntimeLicKey, Forms2Attr.RUNTIME_LIC_KEY);
        prReader.readString((/*uint*/int)SitePropMask.CONTROL_SOURCE, cControlSource, Forms2Attr.CONTROL_SOURCE);
        prReader.readString((/*uint*/int)SitePropMask.ROW_SOURCE, cRowSource, Forms2Attr.ROW_SOURCE);

        prReader.readPadding(4);
    }

    /// <summary>
    /// Writes a control site data to a binary writer.
    /// </summary>
    static void write(BinaryWriter writer, Forms2OleControl control) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeUInt16(VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbSite
        writer.seek(4, SeekOrigin.CURRENT);// SitePropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, control.Pr);

        // Update clsidCacheIndex from the actual type of the control,
        // as it can be missed in case when control was created from the scratch.
        if (control.Pr.isDefaultValue(Forms2Attr.CLSID_CACHE_INDEX))
            control.Pr.set(Forms2Attr.CLSID_CACHE_INDEX, control.getClsidCacheIndex());

        /*uint*/int flags = 0;
        byte[] name = prWriter.writeCountOfBytesWithCompressionFlag(control.getName());
        byte[] tag = prWriter.writeCountOfBytesWithCompressionFlag(control.getTag());
        flags |= prWriter.write(Forms2Attr.ID, 4) ? (((/*uint*/int)SitePropMask.ID) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.HELP_CONTEXT_ID, 4) ? (((/*uint*/int)SitePropMask.HELP_CONTEXT_ID) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BIT_FLAGS_SITE, 4) ? (((/*uint*/int)SitePropMask.BIT_FLAGS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.OBJECT_STREAM_SIZE, 4) ? (((/*uint*/int)SitePropMask.OBJECT_STREAM_SIZE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TAB_INDEX, 2) ? (((/*uint*/int)SitePropMask.TAB_INDEX) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.CLSID_CACHE_INDEX, 2) ? (((/*uint*/int)SitePropMask.CLSID_CACHE_INDEX) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.GROUP_ID, 2) ? (((/*uint*/int)SitePropMask.GROUP_ID) & 0xFFFFFFFFL) : 0;
        byte[] tooltip = prWriter.writeCountOfBytesWithCompressionFlag(control.getTooltip());
        byte[] lic = prWriter.writeCountOfBytesWithCompressionFlag(control.getRuntimeLicKey());
        byte[] controlSource = prWriter.writeCountOfBytesWithCompressionFlag(control.getControlSource());
        byte[] rowSource = prWriter.writeCountOfBytesWithCompressionFlag(control.getRowSource());

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra data block.
        flags |= prWriter.writeBytes(name, 4) ? (((/*uint*/int)SitePropMask.NAME) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(tag, 4) ? (((/*uint*/int)SitePropMask.TAG) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SITE_POSITION, 8) ? (((/*uint*/int)SitePropMask.POSITION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(tooltip, 4) ? (((/*uint*/int)SitePropMask.CONTROL_TIP_TEXT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(lic, 4) ? (((/*uint*/int)SitePropMask.RUNTIME_LIC_KEY) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(controlSource, 4) ? (((/*uint*/int)SitePropMask.CONTROL_SOURCE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(rowSource, 4) ? (((/*uint*/int)SitePropMask.ROW_SOURCE) & 0xFFFFFFFFL) : 0;

        prWriter.writePadding(4);

        // Write cbSite and SitePropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);
    }

    /// <summary>
    /// Returns corresponding Forms 2.0 OLE control.
    /// </summary>
    Forms2OleControl getForms2OleControl()
    {
        Forms2OleControl forms2OleControl = Forms2OleControl.create(getType(), getName());
        getPr().copyTo(forms2OleControl.Pr);

        return forms2OleControl;
    }

    /// <summary>
    /// Converts a ClsidCacheIndex to a control type.
    /// </summary>
    private static /*Forms2OleControlType*/int clsidCacheIndexToType(/*ClsidCacheIndex*/int clsidCacheIndex)
    {
        switch (clsidCacheIndex)
    {
            case ClsidCacheIndex.OPTION_BUTTON:
                return Forms2OleControlType.OPTION_BUTTON;
            case ClsidCacheIndex.LABEL:
                return Forms2OleControlType.LABEL;
            case ClsidCacheIndex.TEXT_BOX:
                return Forms2OleControlType.TEXTBOX;
            case ClsidCacheIndex.CHECK_BOX:
                return Forms2OleControlType.CHECK_BOX;
            case ClsidCacheIndex.TOGGLE_BUTTON:
                return Forms2OleControlType.TOGGLE_BUTTON;
            case ClsidCacheIndex.SPIN_BUTTON:
                return Forms2OleControlType.SPIN_BUTTON;
            case ClsidCacheIndex.COMBO_BOX:
                return Forms2OleControlType.COMBO_BOX;
            case ClsidCacheIndex.FRAME:
                return Forms2OleControlType.FRAME;
            case ClsidCacheIndex.MULTI_PAGE:
                return Forms2OleControlType.MULTI_PAGE;
            case ClsidCacheIndex.COMMAND_BUTTON:
                return Forms2OleControlType.COMMAND_BUTTON;
            case ClsidCacheIndex.IMAGE:
                return Forms2OleControlType.IMAGE;
            case ClsidCacheIndex.SCROLL_BAR:
                return Forms2OleControlType.SCROLL_BAR;
            case ClsidCacheIndex.TAB_STRIP:
                return Forms2OleControlType.TAB_STRIP;
            case ClsidCacheIndex.LIST_BOX:
                return Forms2OleControlType.LIST_BOX;
            case ClsidCacheIndex.FORM:
                return Forms2OleControlType.FORM;
            default:
                throw new IllegalStateException(msString.format("Unexpected ClsidCacheIndex: {0}", clsidCacheIndex));
        }
    }

    /// <summary>
    /// Gets size, in bytes, of an embedded control that is persisted to the Object stream of a Form.
    /// </summary>
    int getObjectStreamSize() { return (int)(((/*uint*/Integer)mPr.fetchAttr(Forms2Attr.OBJECT_STREAM_SIZE)) & 0xFFFFFFFFL); }

    /// <summary>
    /// Gets a name of a storage for a parent control on a Form.
    /// </summary>
    String getStorageName()
{
        int id = (/*int*/Integer)mPr.fetchAttr(Forms2Attr.ID);
        return msString.format("i{0:d2}", id);
    }

    /// <summary>
    /// Gets a corresponding Forms2 Ole control type of this Ole site concrete control.
    /// </summary>
    private /*Forms2OleControlType*/int getType() { return clsidCacheIndexToType(getClsidCacheIndex()); }

    /// <summary>
    /// Gets a name of the control.
    /// </summary>
    private String getName() { return (String)mPr.fetchAttr(Forms2Attr.NAME); }

    /// <summary>
    /// Gets Clsid that specifies the type of a FormEmbeddedActiveXControl on a parent control.
    /// </summary>
    private /*ClsidCacheIndex*/int getClsidCacheIndex() { return (/*ClsidCacheIndex*/Integer)mPr.fetchAttr(Forms2Attr.CLSID_CACHE_INDEX); }

    /// <summary>
    /// Gets a collection of properties of the control.
    /// </summary>
    private Forms2Pr getPr() { return mPr; }

    /// <summary>
    /// [MS-OFORMS] 2.2.10.12.2 SitePropMask.
    /// </summary>
    private /*enum*/ final class SitePropMask /*: uint*/
    {
        private SitePropMask(){}
        
        public static final /*uint*/int NAME = 0x00000001;
        public static final /*uint*/int TAG = 0x00000002;

        public static final /*uint*/int ID = 0x00000004;
        public static final /*uint*/int HELP_CONTEXT_ID = 0x00000008;
        public static final /*uint*/int BIT_FLAGS = 0x00000010;
        public static final /*uint*/int OBJECT_STREAM_SIZE = 0x00000020;
        public static final /*uint*/int TAB_INDEX = 0x00000040;
        public static final /*uint*/int CLSID_CACHE_INDEX = 0x00000080;
        public static final /*uint*/int POSITION = 0x00000100;
        public static final /*uint*/int GROUP_ID = 0x00000200;
        public static final /*uint*/int UNUSED_1 = 0x00000400;
        public static final /*uint*/int CONTROL_TIP_TEXT = 0x00000800;
        public static final /*uint*/int RUNTIME_LIC_KEY = 0x00001000;
        public static final /*uint*/int CONTROL_SOURCE = 0x00002000;
        public static final /*uint*/int ROW_SOURCE = 0x00004000;

        public static final int length = 15;
    }

    private /*final*/ Forms2Pr mPr = new Forms2Pr();

    /// <summary>
    /// The version of the control as specified in [MS-OFORMS] 2.2.10.12.1 OleSiteConcreteControl.
    /// </summary>
    private static final /*ushort*/short VERSION = (short) 0x0000;
}

