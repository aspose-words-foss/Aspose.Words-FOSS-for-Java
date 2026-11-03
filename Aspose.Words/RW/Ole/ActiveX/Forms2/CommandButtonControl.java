// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The CommandButton control runs a macro that performs an action when a user clicks it.
/// </summary>
public class CommandButtonControl extends Forms2OleControl
{
    /// <summary>
    /// Initializes a new instance of <see cref="CommandButtonControl"/> class.
    /// </summary>
    public CommandButtonControl()
    {
    	this("CommandButton");
	
        // Mimic Word by setting the same caption alignment.
        setParagraphAlign(ParagraphAlign.CENTER);
    }

    /// <summary>
    /// Ctor.
    /// </summary>
    CommandButtonControl(String name)
    {
    	super(name);
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        reader.readUInt16();     // cbCommandButton
        /*CommandButtonPropMask*/int flags = (/*CommandButtonPropMask*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)CommandButtonPropMask.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        prReader.readData((/*uint*/int)CommandButtonPropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)CommandButtonPropMask.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);
        /*uint*/int cCaption = prReader.readData((/*uint*/int)CommandButtonPropMask.CAPTION, 4);
        prReader.readData((/*uint*/int)CommandButtonPropMask.PICTURE_POSITION, 4, Forms2Attr.PICTURE_POSITION);
        prReader.readData((/*uint*/int)CommandButtonPropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)CommandButtonPropMask.PICTURE, 2, Forms2Attr.PICTURE);
        prReader.readData((/*uint*/int)CommandButtonPropMask.ACCELERATOR, 2, Forms2Attr.ACCELERATOR);
        prReader.readData((/*uint*/int)CommandButtonPropMask.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);

        Pr.setAttrIfNotDefault(Forms2Attr.TAKE_FOCUS_ON_CLICK, ((flags & CommandButtonPropMask.TAKE_FOCUS_ON_CLICK) == 0));

        // Extra block.
        prReader.readString((/*uint*/int)CommandButtonPropMask.CAPTION, cCaption, Forms2Attr.CAPTION);
        prReader.readBytes((/*uint*/int)CommandButtonPropMask.SIZE, 8, Forms2Attr.SIZE);

        prReader.readPadding(4);

        // Stream data.
        if ((flags & CommandButtonPropMask.PICTURE) != 0)
            prReader.readGuidAndPicture(Forms2Attr.PICTURE);
        if ((flags & CommandButtonPropMask.MOUSE_ICON) != 0)
            prReader.readGuidAndPicture(Forms2Attr.MOUSE_ICON);

        // Text props.
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
        writer.seek(2, SeekOrigin.CURRENT);// cbCommandButton
        writer.seek(4, SeekOrigin.CURRENT);// PropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)CommandButtonPropMask.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)CommandButtonPropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)CommandButtonPropMask.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;

        byte[] caption = prWriter.writeCountOfBytesWithCompressionFlag(getCaption());

        flags |= prWriter.write(Forms2Attr.PICTURE_POSITION, 4) ? (((/*uint*/int)CommandButtonPropMask.PICTURE_POSITION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)CommandButtonPropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE, 2) ? (((/*uint*/int)CommandButtonPropMask.PICTURE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.ACCELERATOR, 2) ? (((/*uint*/int)CommandButtonPropMask.ACCELERATOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)CommandButtonPropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;

        flags |= (!Pr.isDefaultValue(Forms2Attr.TAKE_FOCUS_ON_CLICK)) ? (((/*uint*/int)CommandButtonPropMask.TAKE_FOCUS_ON_CLICK) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra data block.
        flags |= prWriter.writeBytes(caption, 4) ? (((/*uint*/int)CommandButtonPropMask.CAPTION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)CommandButtonPropMask.SIZE) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbLabel and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*CommandButtonPropMask*/int)(flags & 0xFFFFFFFFL) & CommandButtonPropMask.PICTURE) != 0)
            prWriter.writeGuidAndPicture(getPicture());
        if (((/*CommandButtonPropMask*/int)(flags & 0xFFFFFFFFL) & CommandButtonPropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        // Text props.
        Forms2TextProps.write(writer, Pr);

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return COMMAND_BUTTON_CONTROL_CLSID; }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.COMMAND_BUTTON; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.COMMAND_BUTTON; }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /// <summary>
    /// Gets or sets <see cref="Aspose.Words.Forms2.ParagraphAlign"/> enumeration value
    /// that is responsible for caption alignment in a <see cref="CommandButtonControl"/>.
    /// </summary>
    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.1.2 CommandButtonPropMask.
    /// </summary>
    private /*enum*/ final class CommandButtonPropMask
    {
        private CommandButtonPropMask(){}
        
        public static final int FORE_COLOR = 0x00000001;
        public static final int BACK_COLOR = 0x00000002;
        public static final int VARIOUS_PROPERTY_BITS = 0x00000004;
        public static final int CAPTION = 0x00000008;
        public static final int PICTURE_POSITION = 0x00000010;
        public static final int SIZE = 0x00000020;
        public static final int MOUSE_POINTER = 0x00000040;
        public static final int PICTURE = 0x00000080;
        public static final int ACCELERATOR = 0x00000100;
        public static final int TAKE_FOCUS_ON_CLICK = 0x00000200;
        public static final int MOUSE_ICON = 0x00000400;

        public static final int length = 11;
    }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(72.0, 24.0);
}

