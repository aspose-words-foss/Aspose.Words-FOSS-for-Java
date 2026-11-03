// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The Image control is used to display a picture.
/// </summary>
class ImageControl extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    ImageControl(String name)
    {
    	super(name, new ImageControlPr());
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        // cbImage
        reader.readUInt16();
        /*ImagePropMask*//*uint*/int flags = (/*ImagePropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)ImagePropMask.BORDER_COLOR, 4, Forms2Attr.BORDER_COLOR);
        prReader.readData((/*uint*/int)ImagePropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)ImagePropMask.BORDER_STYLE, 1, Forms2Attr.BORDER_STYLE);
        prReader.readData((/*uint*/int)ImagePropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)ImagePropMask.PICTURE_SIZE_MODE, 1, Forms2Attr.PICTURE_SIZE_MODE);
        prReader.readData((/*uint*/int)ImagePropMask.SPECIAL_EFFECT, 1, Forms2Attr.SPECIAL_EFFECT);
        prReader.readData((/*uint*/int)ImagePropMask.PICTURE, 2, Forms2Attr.PICTURE);
        prReader.readData((/*uint*/int)ImagePropMask.PICTURE_ALIGNMENT, 1, Forms2Attr.PICTURE_ALIGNMENT);
        prReader.readData((/*uint*/int)ImagePropMask.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);
        prReader.readData((/*uint*/int)ImagePropMask.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);
        Pr.setAttr(Forms2Attr.AUTO_SIZE, (flags & ImagePropMask.AUTO_SIZE) != 0);
        Pr.setAttr(Forms2Attr.PICTURE_TILING, (flags & ImagePropMask.PICTURE_TILING) != 0);

        // Extra block.
        prReader.readBytes((/*uint*/int)(flags & ImagePropMask.SIZE), 8, Forms2Attr.SIZE);

        // Stream data.
        if ((flags & ImagePropMask.PICTURE) != 0)
            prReader.readGuidAndPicture(Forms2Attr.PICTURE);

        if ((flags & ImagePropMask.MOUSE_ICON) != 0)
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
        writer.seek(2, SeekOrigin.CURRENT);// cbImage
        writer.seek(4, SeekOrigin.CURRENT);// ImagePropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.BORDER_COLOR, 4) ? (((/*uint*/int)ImagePropMask.BORDER_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)ImagePropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BORDER_STYLE, 1) ? (((/*uint*/int)ImagePropMask.BORDER_STYLE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)ImagePropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE_SIZE_MODE, 1) ? (((/*uint*/int)ImagePropMask.PICTURE_SIZE_MODE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SPECIAL_EFFECT, 1) ? (((/*uint*/int)ImagePropMask.SPECIAL_EFFECT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE, 2) ? (((/*uint*/int)ImagePropMask.PICTURE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE_ALIGNMENT, 1) ? (((/*uint*/int)ImagePropMask.PICTURE_ALIGNMENT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)ImagePropMask.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)ImagePropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;

        flags |= (getAutoSize()) ? (((/*uint*/int)ImagePropMask.AUTO_SIZE) & 0xFFFFFFFFL) : 0;
        flags |= (getPictureTiling()) ? (((/*uint*/int)ImagePropMask.PICTURE_TILING) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra data block.
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)ImagePropMask.SIZE) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbImage and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*ImagePropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & ImagePropMask.PICTURE) != 0)
            prWriter.writeGuidAndPicture(getPicture());

        if (((/*ImagePropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & ImagePropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
        }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.IMAGE; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.IMAGE; }

    /// <summary>
    /// Gets or sets PictureSizeMode that specifies how to display the picture.
    /// </summary>
    /*PictureSizeMode*/int getPictureSizeMode() { return (/*PictureSizeMode*/Integer)Pr.fetchAttr(Forms2Attr.PICTURE_SIZE_MODE); }
    void setPictureSizeMode(/*PictureSizeMode*/int value) { Pr.setAttr(Forms2Attr.PICTURE_SIZE_MODE, value); }

    /// <summary>
    /// Gets or sets PictureAlignment that specifies the alignment of the picture.
    /// </summary>
    /*PictureAlignment*/int getPictureAlignment() { return (/*PictureAlignment*/Integer)Pr.fetchAttr(Forms2Attr.PICTURE_ALIGNMENT); }
    void setPictureAlignment(/*PictureAlignment*/int value) { Pr.setAttr(Forms2Attr.PICTURE_ALIGNMENT, value); }

    /// <summary>
    /// Gets or sets a boolean value that specifies whether the picture is tiled across the background.
    /// </summary>
    boolean getPictureTiling() { return (/*boolean*/Boolean)Pr.fetchAttr(Forms2Attr.PICTURE_TILING); }
    void setPictureTiling(boolean value) {Pr.setAttr(Forms2Attr.PICTURE_TILING, value);}

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return IMAGE_CONTROL_CLSID; }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.3.2 ImagePropMask.
    /// </summary>
    private /*enum*/ final class ImagePropMask /*: uint*/
    {
        private ImagePropMask(){}
        
        public static final /*uint*/int UNUSED_BITS_11 = 0x00000001;
        public static final /*uint*/int UNUSED_BITS_12 = 0x00000002;
        public static final /*uint*/int AUTO_SIZE = 0x00000004;
        public static final /*uint*/int BORDER_COLOR = 0x00000008;
        public static final /*uint*/int BACK_COLOR = 0x00000010;
        public static final /*uint*/int BORDER_STYLE = 0x00000020;
        public static final /*uint*/int MOUSE_POINTER = 0x00000040;
        public static final /*uint*/int PICTURE_SIZE_MODE = 0x00000080;
        public static final /*uint*/int SPECIAL_EFFECT = 0x00000100;
        public static final /*uint*/int SIZE = 0x00000200;
        public static final /*uint*/int PICTURE = 0x00000400;
        public static final /*uint*/int PICTURE_ALIGNMENT = 0x00000800;
        public static final /*uint*/int PICTURE_TILING = 0x00001000;
        public static final /*uint*/int VARIOUS_PROPERTY_BITS = 0x00002000;
        public static final /*uint*/int MOUSE_ICON = 0x00004000;

        public static final int length = 15;
    }

    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(72.0, 72.0);
}

