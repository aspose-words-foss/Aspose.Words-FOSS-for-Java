// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The MorphDataControl structure is an aggregate of six controls: CheckBox, ComboBox, ListBox, OptionButton, TextBox, and ToggleButton.
/// </summary>
public abstract class MorphDataControl extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    protected MorphDataControl(String name)
    {
    	super(name, new MorphDataControlPr());
	
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = reader.readInt16();
        Debug.assertTrue(FORMS_2_VERSION == version);

        reader.readInt16();     // cbMorphData
        /*MorphDataPropMask1*//*uint*/int flags1 = (/*MorphDataPropMask1*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);
        /*MorphDataPropMask2*//*uint*/int flags2 = (/*MorphDataPropMask2*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int)flags1;

        prReader.readData((/*uint*/int)MorphDataPropMask1.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);
        prReader.readData((/*uint*/int)MorphDataPropMask1.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)MorphDataPropMask1.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        prReader.readData((/*uint*/int)MorphDataPropMask1.MAX_LENGTH, 4, Forms2Attr.MAX_LENGTH);
        prReader.readData((/*uint*/int)MorphDataPropMask1.BORDER_STYLE, 1, Forms2Attr.BORDER_STYLE);
        prReader.readData((/*uint*/int)MorphDataPropMask1.SCROLL_BARS, 1, Forms2Attr.SCROLL_BARS);
        prReader.readData((/*uint*/int)MorphDataPropMask1.DISPLAY_STYLE, 1, Forms2Attr.DISPLAY_STYLE);
        prReader.readData((/*uint*/int)MorphDataPropMask1.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)MorphDataPropMask1.PASSWORD_CHAR, 2, Forms2Attr.PASSWORD_CHAR);
        prReader.readData((/*uint*/int)MorphDataPropMask1.LIST_WIDTH, 4, Forms2Attr.LIST_WIDTH);
        prReader.readData((/*uint*/int)MorphDataPropMask1.BOUND_COLUMN, 2, Forms2Attr.BOUND_COLUMN);
        prReader.readData((/*uint*/int)MorphDataPropMask1.TEXT_COLUMN, 2, Forms2Attr.TEXT_COLUMN);
        prReader.readData((/*uint*/int)MorphDataPropMask1.COLUMN_COUNT, 2, Forms2Attr.COLUMN_COUNT);
        prReader.readData((/*uint*/int)MorphDataPropMask1.LIST_ROWS, 2, Forms2Attr.LIST_ROWS);
        prReader.readData((/*uint*/int)MorphDataPropMask1.COLUMN_INFO, 2, Forms2Attr.COLUMN_INFO);
        prReader.readData((/*uint*/int)MorphDataPropMask1.MATCH_ENTRY, 1, Forms2Attr.MATCH_ENTRY);
        prReader.readData((/*uint*/int)MorphDataPropMask1.LIST_STYLE, 1, Forms2Attr.LIST_STYLE);
        prReader.readData((/*uint*/int)MorphDataPropMask1.SHOW_DROP_BUTTON_WHEN, 1, Forms2Attr.SHOW_DROP_BUTTON_WHEN);
        prReader.readData((/*uint*/int)MorphDataPropMask1.DROP_BUTTON_STYLE, 1, Forms2Attr.DROP_BUTTON_STYLE);
        prReader.readData((/*uint*/int)MorphDataPropMask1.MULTI_SELECT, 1, Forms2Attr.MULTI_SELECT);
        /*uint*/int cValue = prReader.readData((/*uint*/int)MorphDataPropMask1.VALUE, 4);
        /*uint*/int cCaption = prReader.readData((/*uint*/int)MorphDataPropMask1.CAPTION, 4);
        prReader.readData((/*uint*/int)MorphDataPropMask1.PICTURE_POSITION, 4, Forms2Attr.PICTURE_POSITION);
        prReader.readData((/*uint*/int)MorphDataPropMask1.BORDER_COLOR, 4, Forms2Attr.BORDER_COLOR);
        prReader.readData((/*uint*/int)MorphDataPropMask1.SPECIAL_EFFECT, 4, Forms2Attr.SPECIAL_EFFECT);
        prReader.readData((/*uint*/int)MorphDataPropMask1.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);
        prReader.readData((/*uint*/int)MorphDataPropMask1.PICTURE, 2, Forms2Attr.PICTURE);
        prReader.readData((/*uint*/int)MorphDataPropMask1.ACCELERATOR, 2, Forms2Attr.ACCELERATOR);

        prReader.Flags = (/*uint*/int)flags2;
        /*uint*/int cGroupName = prReader.readData((/*uint*/int)MorphDataPropMask2.GROUP_NAME, 4);

        // Extra block.
        prReader.Flags = (/*uint*/int)flags1;
        prReader.readBytes((/*uint*/int)MorphDataPropMask1.SIZE, 8, Forms2Attr.SIZE);
        prReader.readString((/*uint*/int)MorphDataPropMask1.VALUE, cValue, Forms2Attr.VALUE);
        prReader.readString((/*uint*/int)MorphDataPropMask1.CAPTION, cCaption, Forms2Attr.CAPTION);

        prReader.Flags = (/*uint*/int)flags2;
        prReader.readString((/*uint*/int)MorphDataPropMask2.GROUP_NAME, cGroupName, Forms2Attr.GROUP_NAME);

        prReader.readPadding(4);

        // Stream data.
        if ((flags1 & MorphDataPropMask1.MOUSE_ICON) != 0)
            prReader.readGuidAndPicture(Forms2Attr.MOUSE_ICON);

        if ((flags1 & MorphDataPropMask1.PICTURE) != 0)
            prReader.readGuidAndPicture(Forms2Attr.PICTURE);

        // TextProps.
        Forms2TextProps.read(reader, Pr);
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*override*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(FORMS_2_VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbMorphData
        writer.seek(4, SeekOrigin.CURRENT);// MorphDataPropMask1
        writer.seek(4, SeekOrigin.CURRENT);// MorphDataPropMask2

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);
        // According to [MS-OFORMS] 2.2.5.2, Reserved bit MUST be set to 1 and MUST be ignored.
        /*uint*/int flags1 = (/*uint*/int)MorphDataPropMask1.RESERVED;
        flags1 |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)MorphDataPropMask1.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)MorphDataPropMask1.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)MorphDataPropMask1.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.MAX_LENGTH, 4) ? (((/*uint*/int)MorphDataPropMask1.MAX_LENGTH) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.BORDER_STYLE, 1) ? (((/*uint*/int)MorphDataPropMask1.BORDER_STYLE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.SCROLL_BARS, 1) ? (((/*uint*/int)MorphDataPropMask1.SCROLL_BARS) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.DISPLAY_STYLE, 1) ? (((/*uint*/int)MorphDataPropMask1.DISPLAY_STYLE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)MorphDataPropMask1.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.PASSWORD_CHAR, 2) ? (((/*uint*/int)MorphDataPropMask1.PASSWORD_CHAR) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.LIST_WIDTH, 4) ? (((/*uint*/int)MorphDataPropMask1.LIST_WIDTH) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.BOUND_COLUMN, 2) ? (((/*uint*/int)MorphDataPropMask1.BOUND_COLUMN) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.TEXT_COLUMN, 2) ? (((/*uint*/int)MorphDataPropMask1.TEXT_COLUMN) & 0xFFFFFFFFL): 0;
        flags1 |= prWriter.write(Forms2Attr.COLUMN_COUNT, 2) ? (((/*uint*/int)MorphDataPropMask1.COLUMN_COUNT) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.LIST_ROWS, 2) ? (((/*uint*/int)MorphDataPropMask1.LIST_ROWS) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.COLUMN_INFO, 2) ? (((/*uint*/int)MorphDataPropMask1.COLUMN_INFO) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.MATCH_ENTRY, 1) ? (((/*uint*/int)MorphDataPropMask1.MATCH_ENTRY) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.LIST_STYLE, 1) ? (((/*uint*/int)MorphDataPropMask1.LIST_STYLE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.SHOW_DROP_BUTTON_WHEN, 1) ? (((/*uint*/int)MorphDataPropMask1.SHOW_DROP_BUTTON_WHEN) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.DROP_BUTTON_STYLE, 1) ? (((/*uint*/int)MorphDataPropMask1.DROP_BUTTON_STYLE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.MULTI_SELECT, 1) ? (((/*uint*/int)MorphDataPropMask1.MULTI_SELECT) & 0xFFFFFFFFL) : 0;

        byte[] value = prWriter.writeCountOfBytesWithCompressionFlag(getValue());
        byte[] caption = prWriter.writeCountOfBytesWithCompressionFlag(getCaption());

        flags1 |= prWriter.write(Forms2Attr.PICTURE_POSITION, 4) ? (((/*uint*/int)MorphDataPropMask1.PICTURE_POSITION) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.BORDER_COLOR, 4) ? (((/*uint*/int)MorphDataPropMask1.BORDER_COLOR) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.SPECIAL_EFFECT, 4) ? (((/*uint*/int)MorphDataPropMask1.SPECIAL_EFFECT) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)MorphDataPropMask1.MOUSE_ICON) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.PICTURE, 2) ? (((/*uint*/int)MorphDataPropMask1.PICTURE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.write(Forms2Attr.ACCELERATOR, 2) ? (((/*uint*/int)MorphDataPropMask1.ACCELERATOR) & 0xFFFFFFFFL) : 0;
        byte[] groupName = prWriter.writeCountOfBytesWithCompressionFlag(getGroupName());

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra block.
        flags1 |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)MorphDataPropMask1.SIZE) & 0xFFFFFFFFL) : 0;

        flags1 |= prWriter.writeBytes(value, 4) ? (((/*uint*/int)MorphDataPropMask1.VALUE) & 0xFFFFFFFFL) : 0;
        flags1 |= prWriter.writeBytes(caption, 4) ? (((/*uint*/int)MorphDataPropMask1.CAPTION) & 0xFFFFFFFFL) : 0;

        /*uint*/int flags2 = prWriter.writeBytes(groupName, 4) ? (/*uint*/int)MorphDataPropMask2.GROUP_NAME : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbMorphData and PropMasks.
        prWriter.writeDataSizeAndPropMasks(startPos, flags1, flags2);

        // Stream data.
        if (((/*MorphDataPropMask1*//*uint*/int)(flags1 & 0xFFFFFFFFL) & MorphDataPropMask1.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        if (((/*MorphDataPropMask1*//*uint*/int)(flags1 & 0xFFFFFFFFL) & MorphDataPropMask1.PICTURE) != 0)
            prWriter.writeGuidAndPicture(getPicture());

        // TextProps.
        Forms2TextProps.write(writer, Pr);

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Gets or sets a type of the MorphDataControl.
    /// </summary>
    /*DisplayStyle*/int getDisplayStyle() { return (/*DisplayStyle*/Integer)Pr.fetchAttr(Forms2Attr.DISPLAY_STYLE); }
    void setDisplayStyle(/*DisplayStyle*/int value) { Pr.setAttr(Forms2Attr.DISPLAY_STYLE, value); }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.5.2 MorphDataPropMask (first 32 bits).
    /// </summary>
    private /*enum*/ final class MorphDataPropMask1 /*: uint*/
    {
        private MorphDataPropMask1(){}
        
        public static final /*uint*/int VARIOUS_PROPERTY_BITS = 0x00000001;
        public static final /*uint*/int BACK_COLOR = 0x00000002;
        public static final /*uint*/int FORE_COLOR = 0x00000004;
        public static final /*uint*/int MAX_LENGTH = 0x00000008;
        public static final /*uint*/int BORDER_STYLE = 0x00000010;
        public static final /*uint*/int SCROLL_BARS = 0x00000020;
        public static final /*uint*/int DISPLAY_STYLE = 0x00000040;
        public static final /*uint*/int MOUSE_POINTER = 0x00000080;
        public static final /*uint*/int SIZE = 0x00000100;
        public static final /*uint*/int PASSWORD_CHAR = 0x00000200;
        public static final /*uint*/int LIST_WIDTH = 0x00000400;
        public static final /*uint*/int BOUND_COLUMN = 0x00000800;
        public static final /*uint*/int TEXT_COLUMN = 0x00001000;
        public static final /*uint*/int COLUMN_COUNT = 0x00002000;
        public static final /*uint*/int LIST_ROWS = 0x00004000;
        public static final /*uint*/int COLUMN_INFO = 0x00008000;
        public static final /*uint*/int MATCH_ENTRY = 0x00010000;
        public static final /*uint*/int LIST_STYLE = 0x00020000;
        public static final /*uint*/int SHOW_DROP_BUTTON_WHEN = 0x00040000;
        public static final /*uint*/int UNUSED_BITS_1 = 0x00080000;
        public static final /*uint*/int DROP_BUTTON_STYLE = 0x00100000;
        public static final /*uint*/int MULTI_SELECT = 0x00200000;
        public static final /*uint*/int VALUE = 0x00400000;
        public static final /*uint*/int CAPTION = 0x00800000;
        public static final /*uint*/int PICTURE_POSITION = 0x01000000;
        public static final /*uint*/int BORDER_COLOR = 0x02000000;
        public static final /*uint*/int SPECIAL_EFFECT = 0x04000000;
        public static final /*uint*/int MOUSE_ICON = 0x08000000;
        public static final /*uint*/int PICTURE = 0x10000000;
        public static final /*uint*/int ACCELERATOR = 0x20000000;
        public static final /*uint*/int UNUSED_BITS_2 = 0x40000000;
        public static final /*uint*/int RESERVED = (int) 0x80000000L;

        public static final int length = 32;
    }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.5.2 MorphDataPropMask (last 32 bits).
    /// </summary>
    private /*enum*/ final class MorphDataPropMask2 /*: uint*/
    {
        private MorphDataPropMask2(){}
        
        public static final /*uint*/int GROUP_NAME = 0x00000001;

        public static final int length = 1;
    }
}

