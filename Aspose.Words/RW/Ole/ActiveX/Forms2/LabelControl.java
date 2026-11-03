// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The Label control displays text.
/// </summary>
class LabelControl extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    LabelControl(String name)
    {
    	super(name, new LabelControlPr());
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = reader.readInt16();
        Debug.assertTrue(version == FORMS_2_VERSION);

        // cbLabel
        reader.readUInt16();
        /*LabelPropMask*//*uint*/int flags = (/*LabelPropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)LabelPropMask.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        prReader.readData((/*uint*/int)LabelPropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)LabelPropMask.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);
        /*uint*/int cCaption = prReader.readData((/*uint*/int)LabelPropMask.CAPTION, 4);
        prReader.readData((/*uint*/int)LabelPropMask.PICTURE_POSITION, 4, Forms2Attr.PICTURE_POSITION);
        prReader.readData((/*uint*/int)LabelPropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)LabelPropMask.BORDER_COLOR, 4, Forms2Attr.BORDER_COLOR);
        prReader.readData((/*uint*/int)LabelPropMask.BORDER_STYLE, 2, Forms2Attr.BORDER_STYLE);
        prReader.readData((/*uint*/int)LabelPropMask.SPECIAL_EFFECT, 2, Forms2Attr.SPECIAL_EFFECT);
        prReader.readData((/*uint*/int)LabelPropMask.PICTURE, 2, Forms2Attr.PICTURE);
        prReader.readData((/*uint*/int)LabelPropMask.ACCELERATOR, 2, Forms2Attr.ACCELERATOR);
        prReader.readData((/*uint*/int)LabelPropMask.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);

        // Extra data block.
        prReader.readString((/*uint*/int)(flags & LabelPropMask.CAPTION), cCaption, Forms2Attr.CAPTION);
        prReader.readBytes((/*uint*/int)(flags & LabelPropMask.SIZE), 8, Forms2Attr.SIZE);

        prReader.readPadding(4);

        // Stream data.
        if ((flags & LabelPropMask.PICTURE) != 0)
            prReader.readGuidAndPicture(Forms2Attr.PICTURE);
        if ((flags & LabelPropMask.MOUSE_ICON) != 0)
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
        writer.seek(2, SeekOrigin.CURRENT);// cbLabel
        writer.seek(4, SeekOrigin.CURRENT);// LabelPropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)LabelPropMask.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)LabelPropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)LabelPropMask.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;

        byte[] caption = prWriter.writeCountOfBytesWithCompressionFlag(getCaption());

        flags |= prWriter.write(Forms2Attr.PICTURE_POSITION, 4) ? (((/*uint*/int)LabelPropMask.PICTURE_POSITION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)LabelPropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BORDER_COLOR, 4) ? (((/*uint*/int)LabelPropMask.BORDER_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BORDER_STYLE, 2) ? (((/*uint*/int)LabelPropMask.BORDER_STYLE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SPECIAL_EFFECT, 2) ? (((/*uint*/int)LabelPropMask.SPECIAL_EFFECT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE, 2) ? (((/*uint*/int)LabelPropMask.PICTURE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.ACCELERATOR, 2) ? (((/*uint*/int)LabelPropMask.ACCELERATOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)LabelPropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra data block.
        flags |= prWriter.writeBytes(caption, 4) ? (((/*uint*/int)LabelPropMask.CAPTION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)LabelPropMask.SIZE) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbLabel and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*LabelPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & LabelPropMask.PICTURE) != 0)
            prWriter.writeGuidAndPicture(getPicture());
        if (((/*LabelPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & LabelPropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        // Text props.
        Forms2TextProps.write(writer, Pr);

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.LABEL; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.LABEL; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return LABEL_CONTROL_CLSID; }

    String getFontName() { return (String)Pr.fetchAttr(Forms2Attr.FONT_NAME); }
    void setFontName(String value) { Pr.setAttr(Forms2Attr.FONT_NAME, value); }

    /*uint*/int getFontHeight() { return (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.FONT_HEIGHT); }
    void setFontHeight(/*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_HEIGHT, value); }

    /*FontEffects*//*uint*/int getFontEffects() { return (/*FontEffects*/Integer)Pr.fetchAttr(Forms2Attr.FONT_EFFECTS); }
    void setFontEffects(/*FontEffects*//*uint*/int value) { Pr.setAttr(Forms2Attr.FONT_EFFECTS, value); }

    /*ParagraphAlign*/int getParagraphAlign() { return (/*ParagraphAlign*/Integer)Pr.fetchAttr(Forms2Attr.PARAGRAPH_ALIGN); }
    void setParagraphAlign(/*ParagraphAlign*/int value) { Pr.setAttr(Forms2Attr.PARAGRAPH_ALIGN, value); }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.4.2 LabelPropMask.
    /// </summary>
    private /*enum*/ final class LabelPropMask /*: uint*/
    {
        private LabelPropMask(){}
        
        public static final /*uint*/int FORE_COLOR = 0x00000001;
        public static final /*uint*/int BACK_COLOR = 0x00000002;
        public static final /*uint*/int VARIOUS_PROPERTY_BITS = 0x00000004;
        public static final /*uint*/int CAPTION = 0x00000008;
        public static final /*uint*/int PICTURE_POSITION = 0x00000010;
        public static final /*uint*/int SIZE = 0x00000020;
        public static final /*uint*/int MOUSE_POINTER = 0x00000040;
        public static final /*uint*/int BORDER_COLOR = 0x00000080;
        public static final /*uint*/int BORDER_STYLE = 0x00000100;
        public static final /*uint*/int SPECIAL_EFFECT = 0x00000200;
        public static final /*uint*/int PICTURE = 0x00000400;
        public static final /*uint*/int ACCELERATOR = 0x00000800;
        public static final /*uint*/int MOUSE_ICON = 0x00001000;

        public static final int length = 13;
    }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(72.0, 18.0);
}

