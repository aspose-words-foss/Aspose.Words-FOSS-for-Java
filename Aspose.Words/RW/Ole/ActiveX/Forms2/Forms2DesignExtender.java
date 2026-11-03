// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// Implements [MS-OFORMS] 2.2.10.11.1 DesignExtender.
/// </summary>
/*static*/ class Forms2DesignExtender
{
	/* Simulation of static class by using private constructor */
	private Forms2DesignExtender()
	{}

    /// <summary>
    /// Reads extender data from a binary reader to a specified collection.
    /// </summary>
    static void read(BinaryReader reader, Forms2Pr pr) throws Exception
    {
        int version = reader.readInt16();
        Debug.assertTrue(version == Forms2OleControl.FORMS_2_VERSION);

        reader.readInt16(); // cbDesignExtender
        /*DesignExtenderPropMask*//*uint*/int flags = (/*DesignExtenderPropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, pr);
        prReader.Flags = (/*uint*/int)flags;

        prReader.readData((/*uint*/int)DesignExtenderPropMask.BIT_FLAGS, 4, Forms2Attr.BIT_FLAGS_DX);
        prReader.readData((/*uint*/int)DesignExtenderPropMask.GRID_X, 4, Forms2Attr.GRID_X);
        prReader.readData((/*uint*/int)DesignExtenderPropMask.GRID_Y, 4, Forms2Attr.GRID_Y);
        prReader.readData((/*uint*/int)DesignExtenderPropMask.CLICK_CONTROL_MODE, 4, Forms2Attr.CLICK_CONTROL_MODE);
        prReader.readData((/*uint*/int)DesignExtenderPropMask.DBL_CLICK_CONTROL_MODE, 4, Forms2Attr.DBL_CLICK_CONTROL_MODE);

        prReader.readPadding(4);
    }

    /// <summary>
    /// Writes extender data from a specified collection to a binary writer.
    /// </summary>
    static void write(BinaryWriter writer, Forms2Pr pr) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(Forms2OleControl.FORMS_2_VERSION);

        // The size of data and property flag will be calculated and written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbDesignExtender
        writer.seek(4, SeekOrigin.CURRENT);// PropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, pr);
        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.BIT_FLAGS_DX, 4) ? (((/*uint*/int)DesignExtenderPropMask.BIT_FLAGS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.GRID_X, 4) ? (((/*uint*/int)DesignExtenderPropMask.GRID_X) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.GRID_Y, 4) ? (((/*uint*/int)DesignExtenderPropMask.GRID_Y) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.CLICK_CONTROL_MODE, 4) ? (((/*uint*/int)DesignExtenderPropMask.CLICK_CONTROL_MODE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.DBL_CLICK_CONTROL_MODE, 4) ? (((/*uint*/int)DesignExtenderPropMask.DBL_CLICK_CONTROL_MODE) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbDesignExtender and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);
    }

    /// <summary>
    /// Returns true, if extender data in a specified collection has all default values.
    /// </summary>
    static boolean hasAllDefaultValues(Forms2Pr pr)
    {
        return pr.isDefaultValue(Forms2Attr.BIT_FLAGS_DX) &&
               pr.isDefaultValue(Forms2Attr.GRID_X) &&
               pr.isDefaultValue(Forms2Attr.GRID_Y) &&
               pr.isDefaultValue(Forms2Attr.CLICK_CONTROL_MODE) &&
               pr.isDefaultValue(Forms2Attr.DBL_CLICK_CONTROL_MODE);
    }

    /// <summary>
    /// [MS-OFORMS] 2.2.10.11.2 DesignExtenderPropMask.
    /// </summary>
    private /*enum*/ final class DesignExtenderPropMask /*: uint*/
    {
        private DesignExtenderPropMask(){}
        
        public static final /*uint*/int BIT_FLAGS = 0x00000001;
        public static final /*uint*/int GRID_X = 0x00000002;
        public static final /*uint*/int GRID_Y = 0x00000004;
        public static final /*uint*/int CLICK_CONTROL_MODE = 0x00000010;
        public static final /*uint*/int DBL_CLICK_CONTROL_MODE = 0x00000020;

        public static final int length = 5;
    }
}

