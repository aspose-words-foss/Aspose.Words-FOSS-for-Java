// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.Debug;
import com.aspose.ms.System.IO.SeekOrigin;


/// <summary>
/// The MultiPage control presents multiple screens of information as a single set.
/// </summary>
class MultiPageControl extends FormControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MultiPageControl(String name)
    {
    	super(name);
	
        setSize(mDefaultSize);
    }


    /// <summary>
    /// Reads the control from a storage.
    /// </summary>
    /*override*/ void read(MemoryStorage storage) throws Exception
    {
        readFormControl(storage);

        MemoryStream xStream = storage.getStreamZeroPositioned("x");
        BinaryReader xReader = new BinaryReader(xStream);

        // The first PageProperties in the array MUST be ignored. [MS-OFORMS] 2.1.2.3 MultiPage Control Structure.
        readPageProperties(xReader, new FormControl(""));

        // Read the rest of page properties.
        for (Forms2OleControl childNode : getChildNodes())
        {
            if (childNode.getType() == Forms2OleControlType.FORM)
                readPageProperties(xReader, childNode);
        }

        readMultiPageProperties(xReader);
    }

    /// <summary>
    /// Writes the control to a storage.
    /// </summary>
    /*override*/ void write(MemoryStorage storage) throws Exception
    {
        writeFormControl(storage);

        MemoryStream xStream = new MemoryStream();
        BinaryWriter xWriter = new BinaryWriter(xStream);

        // The first PageProperties in the array MUST be ignored, so write dummy page.
        writePageProperties(xWriter, new FormControl(""));

        // Write pages.
        int pagesCount = 0;
        for (Forms2OleControl childNode : getChildNodes())
        {
            if (childNode.getType() == Forms2OleControlType.FORM)
            {
                writePageProperties(xWriter, childNode);
                pagesCount++;
            }
        }

        // Update pages count.
        if (pagesCount > 0)
            Pr.set(Forms2Attr.PAGE_COUNT, pagesCount);

        writeMultiPageProperties(xWriter);

        storage.set("x", xStream);
    }

    /// <summary>
    /// Reads page properties from a binary reader.
    /// </summary>
    private static void readPageProperties(BinaryReader reader, Forms2OleControl page) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        // cbPage
        reader.readUInt16();
        /*PagePropMask*//*uint*/int flags = (/*PagePropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, page.Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)PagePropMask.TRANSITION_EFFECT, 4, Forms2Attr.TRANSITION_EFFECT);
        prReader.readData((/*uint*/int)PagePropMask.TRANSITION_PERIOD, 4, Forms2Attr.TRANSITION_PERIOD);
    }

    /// <summary>
    /// Writes page properties to a binary writer.
    /// </summary>
    private static void writePageProperties(BinaryWriter writer, Forms2OleControl page) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(FORMS_2_VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbPage
        writer.seek(4, SeekOrigin.CURRENT);// PagePropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, page.Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.TRANSITION_EFFECT, 4) ? (((/*uint*/int)PagePropMask.TRANSITION_EFFECT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TRANSITION_PERIOD, 4) ? (((/*uint*/int)PagePropMask.TRANSITION_PERIOD) & 0xFFFFFFFFL) : 0;

        // Write cbPage and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);
    }

    /// <summary>
    /// Reads multi page properties from a binary reader.
    /// </summary>
    private void readMultiPageProperties(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        // cbMultiPage
        reader.readUInt16();
        /*MultiPagePropMask*//*uint*/int flags = (/*MultiPagePropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)MultiPagePropMask.PAGE_COUNT, 4, Forms2Attr.PAGE_COUNT);
        // This is ID of child TabStrip. I think we can skip it, as it was already set when we read Sites.
        prReader.skipData((/*uint*/int)MultiPagePropMask.ID, 4);

        // Set Flag if this property is NOT the file format default. [MS-OFORMS] 2.2.6.2 MultiPagePropertiesPropMask.
        Pr.setAttrIfNotDefault(Forms2Attr.FLAGS, ((flags & MultiPagePropMask.FLAGS) == 0));

        // There are IDs of the Pages at the very end of this stream, but I think we can skip them,
        // as we already set them accordingly when we read Sites.
    }

    /// <summary>
    /// Writes multi page properties to a binary writer.
    /// </summary>
    private void writeMultiPageProperties(BinaryWriter writer) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        writer.writeInt16(FORMS_2_VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbMultiPage
        writer.seek(4, SeekOrigin.CURRENT);// MultiPagePropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);

        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.PAGE_COUNT, 4) ? (((/*uint*/int)MultiPagePropMask.PAGE_COUNT) & 0xFFFFFFFFL) : 0;

        // Here we need to write ID of a child TabStrip.
        for (Forms2OleControl childNode : getChildNodes())
        {
            if (childNode.getType() == Forms2OleControlType.TAB_STRIP)
            {
                prWriter.write((/*uint*/int)childNode.getIdInternal());
                flags |= (/*uint*/int)MultiPagePropMask.ID;

                break;
            }
        }

        // Specifies whether the value of the Flags property is NOT the file format default.
        flags |= (!Pr.isDefaultValue(Forms2Attr.FLAGS)) ? (((/*uint*/int)MultiPagePropMask.FLAGS) & 0xFFFFFFFFL) : 0;

        // Write cbMultiPage and PropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // At the very end we must write IDs of Pages.
        for (Forms2OleControl childNode : getChildNodes())
        {
            if (childNode.getType() == Forms2OleControlType.FORM)
            {
                prWriter.write((/*uint*/int)childNode.getIdInternal());
            }
        }

    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.MULTI_PAGE; }

    /// <summary>
    /// Gets a boolean value indicating either the Forms2 OleControl is composite.
    /// </summary>
    /*override*/ boolean isComposite() { return true; }

    /// <summary>
    /// Gets a collection of pages of this MultiPage control.
    /// The pages in collection are represented as <see cref="FormControl"/> objects.
    /// </summary>
    PageCollection getPages()
    {
        if (mPages == null)
            mPages = new PageCollection(getChildNodes());

        return mPages;
    }

    /// <summary>
    /// Gets Forms 2.0 control UserType.
    /// </summary>
    protected /*override*/ String getUserType() { return "Microsoft Forms 2.0 Form"; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return MULTI_PAGE_CONTROL_CLSID; }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.6.4.2 PagePropMask.
    /// </summary>
    private /*enum*/ final class PagePropMask /*: uint*/
    {
        private PagePropMask(){}
        
        public static final /*uint*/int UNUSED_1 = 0x00000001;
        public static final /*uint*/int TRANSITION_EFFECT= 0x00000002;
        public static final /*uint*/int TRANSITION_PERIOD = 0x00000004;

        public static final int length = 3;
    }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.6.2 MultiPagePropertiesPropMask.
    /// </summary>
    private /*enum*/ final class MultiPagePropMask /*: uint*/
    {
        private MultiPagePropMask(){}
        
        public static final /*uint*/int UNUSED_1 = 0x00000001;
        public static final /*uint*/int PAGE_COUNT = 0x00000002;
        public static final /*uint*/int ID = 0x00000004;
        public static final /*uint*/int FLAGS = 0x00000008;

        public static final int length = 4;
    }

    private PageCollection mPages;
    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(144.0, 108.0);
}

