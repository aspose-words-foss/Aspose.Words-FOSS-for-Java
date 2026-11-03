// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/11/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// Implements [MS-OFORMS] 2.3.1 TextProps.
/// </summary>
/*static*/ class Forms2TextProps
{
	/* Simulation of static class by using private constructor */
	private Forms2TextProps()
	{}

    /// <summary>
    /// Reads TextProps from a binary reader.
    /// </summary>
    static void read(BinaryReader reader, Forms2Pr pr) throws Exception
    {
        int version = reader.readInt16();
        Debug.assertTrue(version == Forms2OleControl.FORMS_2_VERSION);

        reader.readInt16(); // cbTextProps
        /*TextPropsPropMask*//*uint*/int flags = (/*TextPropsPropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, pr);
        prReader.Flags = (/*uint*/int)flags;

        /*uint*/int cFontName = prReader.readData((/*uint*/int)TextPropsPropMask.FONT_NAME, 4);
        prReader.readData((/*uint*/int)TextPropsPropMask.FONT_EFFECTS, 4, Forms2Attr.FONT_EFFECTS);
        prReader.readData((/*uint*/int)TextPropsPropMask.FONT_HEIGHT, 4, Forms2Attr.FONT_HEIGHT);
        prReader.readData((/*uint*/int)TextPropsPropMask.FONT_CHAR_SET, 1, Forms2Attr.FONT_CHAR_SET);
        prReader.readData((/*uint*/int)TextPropsPropMask.FONT_PITCH_AND_FAMILY, 1, Forms2Attr.FONT_PITCH_AND_FAMILY);
        prReader.readData((/*uint*/int)TextPropsPropMask.PARAGRAPH_ALIGN, 1, Forms2Attr.PARAGRAPH_ALIGN);
        prReader.readData((/*uint*/int)TextPropsPropMask.FONT_WEIGHT, 2, Forms2Attr.FONT_WEIGHT);

        prReader.readString((/*uint*/int)TextPropsPropMask.FONT_NAME, cFontName, Forms2Attr.FONT_NAME);

        prReader.readPadding(4);
    }

    /// <summary>
    /// Writes TextProps to a binary writer.
    /// </summary>
    static void write(BinaryWriter writer, Forms2Pr pr) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(Forms2OleControl.FORMS_2_VERSION);

        // The size of data and property flag will be calculated and written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbTextProps
        writer.seek(4, SeekOrigin.CURRENT);// PropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, pr);
        /*uint*/int flags = 0;
        byte[] fontName = prWriter.writeCountOfBytesWithCompressionFlag((String)pr.fetchAttr(Forms2Attr.FONT_NAME));
        flags |= prWriter.write(Forms2Attr.FONT_EFFECTS, 4) ? (((/*uint*/int)TextPropsPropMask.FONT_EFFECTS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FONT_HEIGHT, 4) ? (((/*uint*/int)TextPropsPropMask.FONT_HEIGHT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FONT_CHAR_SET, 1) ? (((/*uint*/int)TextPropsPropMask.FONT_CHAR_SET) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FONT_PITCH_AND_FAMILY, 1) ? (((/*uint*/int)TextPropsPropMask.FONT_PITCH_AND_FAMILY) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PARAGRAPH_ALIGN, 1) ? (((/*uint*/int)TextPropsPropMask.PARAGRAPH_ALIGN) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FONT_WEIGHT, 2) ? (((/*uint*/int)TextPropsPropMask.FONT_WEIGHT) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // ExtraData block.
        flags |= prWriter.writeBytes(fontName, 4) ? (((/*uint*/int)TextPropsPropMask.FONT_NAME) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbTextProps and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);
    }

    /// <summary>
    /// Returns true, if a TextProps in a specified collection has all values set to the defaults.
    /// </summary>
    static boolean hasAllDefaultValues(Forms2Pr pr)
    {
        return pr.isDefaultValue(Forms2Attr.FONT_EFFECTS) &&
               pr.isDefaultValue(Forms2Attr.FONT_HEIGHT) &&
               pr.isDefaultValue(Forms2Attr.FONT_CHAR_SET) &&
               pr.isDefaultValue(Forms2Attr.FONT_PITCH_AND_FAMILY) &&
               pr.isDefaultValue(Forms2Attr.PARAGRAPH_ALIGN) &&
               pr.isDefaultValue(Forms2Attr.FONT_WEIGHT) &&
               pr.isDefaultValue(Forms2Attr.FONT_NAME);
    }

    static final String GUID = "afc20920-da4e-11ce-b943-00aa006887b4";

    /// <summary>
    /// Implements [MS-OFORMS] 2.3.2 TextPropsPropMask.
    /// </summary>
    private /*enum*/ final class TextPropsPropMask /*: uint*/
    {
        private TextPropsPropMask(){}
        
        public static final /*uint*/int FONT_NAME = 0x00000001;
        public static final /*uint*/int FONT_EFFECTS = 0x00000002;
        public static final /*uint*/int FONT_HEIGHT = 0x00000004;
        public static final /*uint*/int FONT_CHAR_SET = 0x00000010;
        public static final /*uint*/int FONT_PITCH_AND_FAMILY = 0x00000020;
        public static final /*uint*/int PARAGRAPH_ALIGN = 0x00000040;
        public static final /*uint*/int FONT_WEIGHT = 0x00000080;

        public static final int length = 7;
    }
}

