// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The ScrollBar control scrolls through a range of values when a user clicks the scroll arrows.
/// </summary>
class ScrollBarControl extends ScrollableControlBase
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ScrollBarControl(String name)
    {
    	super(name, new ScrollBarControlPr());
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Reads the control from a binary stream.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        reader.readUInt16();    // cbScrollBar
        /*ScrollBarPropMask*//*uint*/int flags = (/*ScrollBarPropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int)flags;

        // Data block.
        prReader.readData((/*uint*/int)ScrollBarPropMask.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        prReader.readData((/*uint*/int)ScrollBarPropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)ScrollBarPropMask.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);

        prReader.readData((/*uint*/int)ScrollBarPropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)ScrollBarPropMask.MIN, 4, Forms2Attr.MIN);
        prReader.readData((/*uint*/int)ScrollBarPropMask.MAX, 4, Forms2Attr.MAX);
        prReader.readData((/*uint*/int)ScrollBarPropMask.POSITION, 4, Forms2Attr.POSITION);
        prReader.readData((/*uint*/int)ScrollBarPropMask.PREV_ENABLED, 4, Forms2Attr.PREV_ENABLED);
        prReader.readData((/*uint*/int)ScrollBarPropMask.NEXT_ENABLED, 4, Forms2Attr.NEXT_ENABLED);
        prReader.readData((/*uint*/int)ScrollBarPropMask.SMALL_CHANGE, 4, Forms2Attr.SMALL_CHANGE);
        prReader.readData((/*uint*/int)ScrollBarPropMask.LARGE_CHANGE, 4, Forms2Attr.LARGE_CHANGE);
        prReader.readData((/*uint*/int)ScrollBarPropMask.ORIENTATION, 4, Forms2Attr.ORIENTATION);
        prReader.readData((/*uint*/int)ScrollBarPropMask.PROPORTIONAL_THUMB, 2, Forms2Attr.PROPORTIONAL_THUMB);
        prReader.readData((/*uint*/int)ScrollBarPropMask.DELAY, 2, Forms2Attr.DELAY);
        prReader.readData((/*uint*/int)ScrollBarPropMask.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);

        // Extra block
        prReader.readBytes((/*uint*/int)(flags & ScrollBarPropMask.SIZE), 8, Forms2Attr.SIZE);

        // Stream data.
        if ((flags & ScrollBarPropMask.MOUSE_ICON) != 0)
            prReader.readGuidAndPicture(Forms2Attr.MOUSE_ICON);
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*override*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(FORMS_2_VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbScrollBar
        writer.seek(4, SeekOrigin.CURRENT);// PropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)ScrollBarPropMask.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)ScrollBarPropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)ScrollBarPropMask.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)ScrollBarPropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MIN, 4) ? (((/*uint*/int)ScrollBarPropMask.MIN) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MAX, 4) ? (((/*uint*/int)ScrollBarPropMask.MAX) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.POSITION, 4) ? (((/*uint*/int)ScrollBarPropMask.POSITION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PREV_ENABLED, 4) ? (((/*uint*/int)ScrollBarPropMask.PREV_ENABLED) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.NEXT_ENABLED, 4) ? (((/*uint*/int)ScrollBarPropMask.NEXT_ENABLED) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SMALL_CHANGE, 4) ? (((/*uint*/int)ScrollBarPropMask.SMALL_CHANGE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.LARGE_CHANGE, 4) ? (((/*uint*/int)ScrollBarPropMask.LARGE_CHANGE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.ORIENTATION, 4) ? (((/*uint*/int)ScrollBarPropMask.ORIENTATION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PROPORTIONAL_THUMB, 2) ? (((/*uint*/int)ScrollBarPropMask.PROPORTIONAL_THUMB) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.DELAY, 4) ? (((/*uint*/int)ScrollBarPropMask.DELAY) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)ScrollBarPropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra block.
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)ScrollBarPropMask.SIZE) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbScrollBar and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*ScrollBarPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & ScrollBarPropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.SCROLL_BAR; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.SCROLL_BAR; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return SCROLL_BAR_CONTROL_CLSID; }

    /*FormOrientation*//*uint*/int getOrientation() { return (/*FormOrientation*/Integer)Pr.fetchAttr(Forms2Attr.ORIENTATION); }
    void setOrientation(/*FormOrientation*//*uint*/int value) { Pr.setAttr(Forms2Attr.ORIENTATION, value); }

    /*ProportionalThumb*//*ushort*/short getProportionalThumb() { return (/*ProportionalThumb*/Short)Pr.fetchAttr(Forms2Attr.PROPORTIONAL_THUMB); }
    void setProportionalThumb(/*ProportionalThumb*//*ushort*/short value) { Pr.setAttr(Forms2Attr.PROPORTIONAL_THUMB, value); }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.7.2 ScrollBarPropMask.
    /// </summary>
    private /*enum*/ final class ScrollBarPropMask /*: uint*/
    {
        private ScrollBarPropMask(){}
        
        public static final /*uint*/int FORE_COLOR = 0x00000001;
        public static final /*uint*/int BACK_COLOR = 0x00000002;
        public static final /*uint*/int VARIOUS_PROPERTY_BITS = 0x00000004;
        public static final /*uint*/int SIZE = 0x00000008;
        public static final /*uint*/int MOUSE_POINTER = 0x00000010;
        public static final /*uint*/int MIN = 0x00000020;
        public static final /*uint*/int MAX = 0x00000040;
        public static final /*uint*/int POSITION = 0x00000080;
        public static final /*uint*/int UNUSED_BITS_1 = 0x00000100;
        public static final /*uint*/int PREV_ENABLED = 0x00000200;
        public static final /*uint*/int NEXT_ENABLED = 0x00000400;
        public static final /*uint*/int SMALL_CHANGE = 0x00000800;
        public static final /*uint*/int LARGE_CHANGE = 0x00001000;
        public static final /*uint*/int ORIENTATION = 0x00002000;
        public static final /*uint*/int PROPORTIONAL_THUMB = 0x00004000;
        public static final /*uint*/int DELAY = 0x00008000;
        public static final /*uint*/int MOUSE_ICON = 0x00010000;

        public static final int length = 17;
    }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(13.0, 64.0);
}

