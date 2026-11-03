// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MemoryStorage;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.HashSetGeneric;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements [MS-OFORMS] 2.2.10.1 FormControl.
/// </summary>
class FormControl extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FormControl(String name)
    {
    	super(name, new FormControlPr());
	
        setSize(mDefaultSize);
    }

    /// <summary>
    /// Reads the control from a storage.
    /// </summary>
    /*override*/ void read(MemoryStorage storage) throws Exception
    {
        readFormControl(storage);
    }

    /// <summary>
    /// Writes the control to a storage.
    /// </summary>
    /*override*/ void write(MemoryStorage storage) throws Exception
    {
        writeFormControl(storage);
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue((VERSION & 0xFFFF) == version);

        reader.readUInt16();        // cbForms
        /*FormPropMask*//*uint*/int flags = (/*FormPropMask*//*uint*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)FormPropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)FormPropMask.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        prReader.readData((/*uint*/int)FormPropMask.NEXT_AVAILABLE_ID, 4, Forms2Attr.NEXT_AVAILABLE_ID);
        prReader.readData((/*uint*/int)FormPropMask.BOOLEAN_PROPERTIES, 4, Forms2Attr.BOOLEAN_PROPERTIES);
        prReader.readData((/*uint*/int)FormPropMask.BORDER_STYLE, 1, Forms2Attr.BORDER_STYLE);
        prReader.readData((/*uint*/int)FormPropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)FormPropMask.SCROLL_BARS, 1, Forms2Attr.SCROLL_BARS);
        prReader.readData((/*uint*/int)FormPropMask.GROUP_CNT, 4, Forms2Attr.GROUP_CNT);
        prReader.skipData((/*uint*/int)FormPropMask.MOUSE_ICON, 2);
        prReader.readData((/*uint*/int)FormPropMask.CYCLE, 1, Forms2Attr.CYCLE);
        prReader.readData((/*uint*/int)FormPropMask.SPECIAL_EFFECT, 1, Forms2Attr.SPECIAL_EFFECT);
        prReader.readData((/*uint*/int)FormPropMask.BORDER_COLOR, 4, Forms2Attr.BORDER_COLOR);
        /*uint*/int cCaption = prReader.readData((/*uint*/int)FormPropMask.CAPTION, 4);
        prReader.skipData((/*uint*/int)FormPropMask.FONT, 2);
        prReader.skipData((/*uint*/int)FormPropMask.PICTURE, 2);
        prReader.readData((/*uint*/int)FormPropMask.ZOOM, 4, Forms2Attr.ZOOM);
        prReader.readData((/*uint*/int)FormPropMask.PICTURE_ALIGNMENT, 1, Forms2Attr.PICTURE_ALIGNMENT);
        prReader.readData((/*uint*/int)FormPropMask.PICTURE_SIZE_MODE, 1, Forms2Attr.PICTURE_SIZE_MODE);
        prReader.readData((/*uint*/int)FormPropMask.SHAPE_COOKIE, 4, Forms2Attr.SHAPE_COOKIE);
        prReader.readData((/*uint*/int)FormPropMask.DRAW_BUFFER, 4, Forms2Attr.DRAW_BUFFER);

        Pr.setAttr(Forms2Attr.PICTURE_TILING, (flags & FormPropMask.PICTURE_TILING) != 0);

        // Extra block
        prReader.readBytes((/*uint*/int)FormPropMask.DISPLAYED_SIZE, 8, Forms2Attr.SIZE);
        prReader.readBytes((/*uint*/int)FormPropMask.LOGICAL_SIZE, 8, Forms2Attr.LOGICAL_SIZE);
        prReader.readBytes((/*uint*/int)FormPropMask.SCROLL_POSITION, 8, Forms2Attr.SCROLL_POSITION);

        prReader.readString((/*uint*/int)FormPropMask.CAPTION, cCaption, Forms2Attr.CAPTION);

        prReader.readPadding(4);

        // Stream data.
        if ((flags & FormPropMask.MOUSE_ICON) != 0)
            prReader.readGuidAndPicture(Forms2Attr.MOUSE_ICON);

        if ((flags & FormPropMask.FONT) != 0)
            prReader.readGuidAndFont();

        if ((flags & FormPropMask.PICTURE) != 0)
            prReader.readGuidAndPicture(Forms2Attr.PICTURE);
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*override*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();
        init();

        writer.writeUInt16(VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbForm
        writer.seek(4, SeekOrigin.CURRENT);// FormPropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);
        /*uint*/int flags = 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)FormPropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)FormPropMask.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.NEXT_AVAILABLE_ID, 4) ? (((/*uint*/int)FormPropMask.NEXT_AVAILABLE_ID) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BOOLEAN_PROPERTIES, 4) ? (((/*uint*/int)FormPropMask.BOOLEAN_PROPERTIES) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BORDER_STYLE, 1) ? (((/*uint*/int)FormPropMask.BORDER_STYLE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)FormPropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SCROLL_BARS, 1) ? (((/*uint*/int)FormPropMask.SCROLL_BARS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.GROUP_CNT, 4) ? (((/*uint*/int)FormPropMask.GROUP_CNT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)FormPropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.CYCLE, 1) ? (((/*uint*/int)FormPropMask.CYCLE) & 0xFFFFFFFFL): 0;
        flags |= prWriter.write(Forms2Attr.SPECIAL_EFFECT, 1) ? (((/*uint*/int)FormPropMask.SPECIAL_EFFECT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BORDER_COLOR, 4) ? (((/*uint*/int)FormPropMask.BORDER_COLOR) & 0xFFFFFFFFL) : 0;
        byte[] caption = prWriter.writeCountOfBytesWithCompressionFlag(getCaption());
        flags |= prWriter.write(Forms2Attr.FONT, 2) ? (((/*uint*/int)FormPropMask.FONT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE, 2) ? (((/*uint*/int)FormPropMask.PICTURE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.ZOOM, 4) ? (((/*uint*/int)FormPropMask.ZOOM) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE_ALIGNMENT, 1) ? (((/*uint*/int)FormPropMask.PICTURE_ALIGNMENT) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.PICTURE_SIZE_MODE, 1) ? (((/*uint*/int)FormPropMask.PICTURE_SIZE_MODE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SHAPE_COOKIE, 4) ? (((/*uint*/int)FormPropMask.SHAPE_COOKIE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.DRAW_BUFFER, 4) ? (((/*uint*/int)FormPropMask.DRAW_BUFFER) & 0xFFFFFFFFL) : 0;

        flags |= ((/*boolean*/Boolean)Pr.fetchAttr(Forms2Attr.PICTURE_TILING)) ? (((/*uint*/int)FormPropMask.PICTURE_TILING) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra block.
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)FormPropMask.DISPLAYED_SIZE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.LOGICAL_SIZE, 8) ? (((/*uint*/int)FormPropMask.LOGICAL_SIZE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.SCROLL_POSITION, 8) ? (((/*uint*/int)FormPropMask.SCROLL_POSITION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(caption, 4) ? (((/*uint*/int)FormPropMask.CAPTION) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbForm and PropMasks.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*FormPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & FormPropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        if (((/*FormPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & FormPropMask.FONT) != 0)
            prWriter.writeGuidAndFont();

        if (((/*FormPropMask*//*uint*/int)(flags & 0xFFFFFFFFL) & FormPropMask.PICTURE) != 0)
            prWriter.writeGuidAndPicture(getPicture());

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Reads FormControl as specified in [MS-OFORMS] 2.2.10.1.
    /// </summary>
    protected void readFormControl(MemoryStorage storage) throws Exception
    {
        MemoryStream fStream = storage.getStreamZeroPositioned("f");
        BinaryReader reader = new BinaryReader(fStream);

        // Read DataBlock, ExtraDataBlock and StreamData.
        read(reader);

        // Read SiteData.
        readSiteData(reader, storage);

        // Read DesignExData.
        /*FormFlags*//*uint*/int formFlags = (/*FormFlags*/Integer)Pr.fetchAttr(Forms2Attr.BOOLEAN_PROPERTIES);
        if ((formFlags & FormFlags.DESINK_PERSISTED) != 0)
            Forms2DesignExtender.read(reader, Pr);
    }

    /// <summary>
    /// Writes FormControl as specified in [MS-OFORMS] 2.2.10.1.
    /// </summary>
    protected void writeFormControl(MemoryStorage storage) throws Exception
        {
        MemoryStream fStream = new MemoryStream();
        BinaryWriter writer = new BinaryWriter(fStream);

        // Write DataBlock, ExtraDataBlock and StreamData.
        write(writer);

        // Write ChildControls to a ObjectStream.
        writeObjectStream(storage);

        // Write SiteData.
        writeSiteData(writer);

        // Write DesignExData.
        // DesinkPersisted flag has to be updated when we wrote DataBlock, so we can rely on it here.
        /*FormFlags*//*uint*/int formFlags = (/*FormFlags*/Integer)Pr.fetchAttr(Forms2Attr.BOOLEAN_PROPERTIES);
        if ((formFlags & FormFlags.DESINK_PERSISTED) != 0)
            Forms2DesignExtender.write(writer, Pr);

        storage.set("f", fStream);
        }

    /// <summary>
    /// Writes SiteClasses as specified in [MS-OFORMS] 2.2.10.6 FormSiteData.
    /// </summary>
    ///<remarks>For a moment we do not write ClassTables of Sites.</remarks>
    private void writeSiteClasses(BinaryWriter writer) throws Exception
    {
        /*FormFlags*//*uint*/int formFlags = (/*FormFlags*/Integer)Pr.fetchAttr(Forms2Attr.BOOLEAN_PROPERTIES);
        if ((formFlags & FormFlags.DONT_SAVE_CLASS_TABLE) == 0)
            writer.writeUInt16((/*ushort*/short)0);
    }

    /// <summary>
    /// Writes [MS-OFORMS] 2.2.10.7 FormObjectDepthTypeCount.
    /// </summary>
    /// <remarks>We do not use this structure for a while, but MUST read it to know its length.</remarks>
    /// <returns>Number of read bytes.</returns>
    private static int readDepthTypeCount(BinaryReader reader) throws Exception
    {
        long startPos = reader.getBaseStream().getPosition();

        // Ignore depth for a while.
        reader.readByte();

        byte typeOrCount = reader.readByte();
        boolean fCount = ((typeOrCount & 0xFF) & 0x80) != 0;
        // Ignore SITE_TYPE for a while.
        if (fCount)
            reader.readByte();

        return (int)(reader.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Writes [MS-OFORMS] 2.2.10.7 FormObjectDepthTypeCount.
    /// </summary>
    /// <returns>Number of written bytes.</returns>
    private static int writeDepthTypeCount(BinaryWriter writer, int count) throws Exception
    {
        long startPos = writer.getBaseStream().getPosition();

        // Ignore depth for a while.
        final byte DEPTH = (byte) 0x00;
        writer.writeByte(DEPTH);

        // Not sure, but seems Word writes fCount = true, when count > 1.
        boolean fCount = (count > 1);
        byte typeOrCount = (byte)(count | (fCount ? 0x80 : 0x00));
        writer.writeByte(typeOrCount);

        if (fCount)
        {
            // 2.2.10.8 SITE_TYPE. Must be always set to 1.
            final byte SITE_TYPE = (byte) 1;
            writer.writeByte(SITE_TYPE);
        }

        return (int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Reads FormSiteData as specified in [MS-OFORMS] 2.2.10.6.
    /// </summary>
    private void readSiteData(BinaryReader reader, MemoryStorage storage) throws Exception
    {
        readSiteClasses(reader);

        int countOfSites = (int)(reader.readUInt32() & 0xFFFFFFFFL);

        // Skip CountOfBytes.
        reader.readUInt32();

        // Empty FormControl, nothing to read.
        if (countOfSites == 0)
            return;

        // Read SiteDepthsAndTypes.
        int len = readDepthTypeCount(reader);

        // Read ArrayPadding.
        int pad = (len % 4 == 0) ? 0 : 4 - len % 4;
        reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + pad);

        // Read Sites.
        OleSiteConcreteControl[] siteControls = new OleSiteConcreteControl[countOfSites];
        for (int i = 0; i < countOfSites; i++)
            siteControls[i] = new OleSiteConcreteControl(reader);

        // Read child controls.
        MemoryStream objectStream = storage.getStreamZeroPositioned("o");
        BinaryReader objectReader = new BinaryReader(objectStream);

        int position = 0;
        for (OleSiteConcreteControl siteControl : siteControls)
        {
            getChildNodes().add(readChildControl(siteControl, objectReader, storage));

            position += siteControl.getObjectStreamSize();
            objectReader.getBaseStream().setPosition(position);
        }
    }

    /// <summary>
    /// Writes site data to a binary writer.
    /// </summary>
    private void writeSiteData(BinaryWriter writer) throws Exception
    {
        // Write ClassTables.
        writeSiteClasses(writer);

        // CountOfSites.
        writer.writeInt32(getChildNodes().getCount());

        // CountOfBytes (write this later).
        int countOfBytesPos = (int)writer.getBaseStream().getPosition();
        writer.writeUInt32((/*uint*/int)0x0);

        // Empty FormControl, nothing to write.
        if (getChildNodes().getCount() == 0)
            return;

        // Write SiteDepthsAndTypes.
        int len =  writeDepthTypeCount(writer, getChildNodes().getCount());

        // Write ArrayPadding.
        int pad = ((len % 4) == 0) ? 0 : 4 - (len % 4);
        writer.writeBytes(new byte[pad]);

        // Write Sites.
        for (int i = 0; i < getChildNodes().getCount(); i++)
            OleSiteConcreteControl.write(writer, getChildNodes().get(i));

        // Write CountOfBytes as the sum of the sizes, in bytes, of SiteDepthsAndTypes, ArrayPadding, and Sites.
        int countOfBytes = ((int)writer.getBaseStream().getPosition() - countOfBytesPos - 4);
        writer.seek(countOfBytesPos, SeekOrigin.BEGIN);
        writer.writeInt32(countOfBytes);
        writer.seek(0, SeekOrigin.END);
    }

    /// <summary>
    /// Reads a child control for a site from a binary reader or storage.
    /// </summary>
    private static Forms2OleControl readChildControl(
        OleSiteConcreteControl siteControl, BinaryReader objectReader, MemoryStorage storage) throws Exception
    {
        Forms2OleControl forms2OleControl = siteControl.getForms2OleControl();
        if (forms2OleControl.isComposite())
            forms2OleControl.read((MemoryStorage)storage.get(siteControl.getStorageName()));
        else
            forms2OleControl.read(objectReader);

        return forms2OleControl;
    }

    /// <summary>
    /// Reads site classes from a binary reader.
    /// </summary>
    private void readSiteClasses(BinaryReader reader) throws Exception
    {
        /*FormFlags*//*uint*/int formFlags = (/*FormFlags*/Integer)Pr.fetchAttr(Forms2Attr.BOOLEAN_PROPERTIES);
        int countOfSiteClassInfo = ((formFlags & FormFlags.DONT_SAVE_CLASS_TABLE) != 0) ? (short) 0 : (reader.readUInt16() & 0xFFFF);

        SiteClassInfo[] sites = new SiteClassInfo[countOfSiteClassInfo];
        for (int i = 0; i < countOfSiteClassInfo; i++)
        {
            sites[i] = new SiteClassInfo(reader);
        }
    }

    /// <summary>
    /// Writes ObjectStream to a storage.
    /// </summary>
    private void writeObjectStream(MemoryStorage storage) throws Exception
    {
        MemoryStream oStream = new MemoryStream();
        BinaryWriter oWriter = new BinaryWriter(oStream);

        HashSetGeneric<Integer> usedIds = new HashSetGeneric<Integer>();
        for (int i = 0; i < getChildNodes().getCount(); i++)
        {
            ensureUniqueId(getChildNodes().get(i), usedIds);

            if (getChildNodes().get(i).isComposite())
            {
                // MemoryStorage childStorage = OleUtil.CreateOleObject(ChildNodes[i]).Data;
                MemoryStorage childStorage = ((IEmbeddedObject)getChildNodes().get(i)).getOleObject().getData();
                // [MS-OFORMS] 2.1.2.2.2 Embedded Parents.
                String childStorageName = msString.format("i{0:d2}", getChildNodes().get(i).getIdInternal());
                storage.set(childStorageName, childStorage);
            }
            else
            {
                /*uint*/int size = getChildNodes().get(i).write(oWriter);
                // Update ObjectStreamSize, as we need it when we will write Sites.
                getChildNodes().get(i).setObjectStreamSize(size);
            }
        }

        storage.set("o", oStream);
    }

    /// <summary>
    /// Initializes FormControl properties that must persist in output stream.
    /// </summary>
    private void init()
    {
        // Update DesinkPersisted flag.
        /*FormFlags*//*uint*/int formFlags = (/*FormFlags*/Integer)Pr.fetchAttr(Forms2Attr.BOOLEAN_PROPERTIES);
        if (Forms2DesignExtender.hasAllDefaultValues(Pr))
            formFlags &= ~FormFlags.DESINK_PERSISTED;
        else
            formFlags |= FormFlags.DESINK_PERSISTED;
        Pr.set(Forms2Attr.BOOLEAN_PROPERTIES, formFlags);

        // // Each Form MUST persist a value for this property.
        // // MUST be in the range from 16000 through 1048576.
        if (!Pr.contains(Forms2Attr.DRAW_BUFFER))
            Pr.setAttr(Forms2Attr.DRAW_BUFFER, (/*uint*/int)32000);

        if (!Pr.contains(Forms2Attr.LOGICAL_SIZE))
            Pr.setAttr(Forms2Attr.LOGICAL_SIZE, new OleSize(0, 0));

        // Note, it is very important to set 'PromoteControls' flag for Frame, MultiPage and Page controls,
        // otherwise Word ignores properties of these controls and creates them 'by default'.
        // See [MS-OFORMS] 2.5.4.1 SITE_FLAG.
        /*SiteFlag*//*uint*/int siteFlag = (Pr.contains(Forms2Attr.BIT_FLAGS_SITE))
            ? (/*SiteFlag*/Integer)Pr.get(Forms2Attr.BIT_FLAGS_SITE)
            : SiteFlag.AUTO_SIZE | SiteFlag.TAB_STOP | SiteFlag.VISIBLE;
        Pr.set(Forms2Attr.BIT_FLAGS_SITE, siteFlag | SiteFlag.PROMOTE_CONTROLS);
    }

    /// <summary>
    /// Ensures uniqueness of <see cref="Forms2OleControl.IdInternal"/> property of the control.
    /// </summary>
    /// <remarks>
    /// The ID of the control can be specified only in the properties of
    /// MS-OFORMS 2.2.10.12.3 SiteDataBlock of the OleSiteConcreteControl.
    /// So, we need ensure the uniqueness of the IDs only within the FormControl object.
    /// </remarks>
    private static void ensureUniqueId(Forms2OleControl control, HashSetGeneric<Integer> usedIds)
    {
        int id = control.getIdInternal();
        while (usedIds.contains(id))
            id++;

        usedIds.add(id);
        control.setIdInternal(id);
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.FORM; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.FORM; }

    /// <summary>
    /// Gets a boolean value indicating either the Forms2 OleControl is composite.
    /// </summary>
    /*override*/ boolean isComposite() { return true; }

    /// <summary>
    /// Gets all immediate child Forms2 OLE controls.
    /// </summary>
    public /*override*/ Forms2OleControlCollection getChildNodes() { return mChildNodes; }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return FORM_CONTROL_CLSID; }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.10.2 FormPropMask.
    /// </summary>
    private /*enum*/ final class FormPropMask /*: uint*/
    {
        private FormPropMask(){}
        
        public static final /*uint*/int UNUSED_1 = 0x00000001;
        public static final /*uint*/int BACK_COLOR = 0x00000002;
        public static final /*uint*/int FORE_COLOR = 0x00000004;
        public static final /*uint*/int NEXT_AVAILABLE_ID = 0x00000008;
        public static final /*uint*/int UNUSED_2 = 0x00000010;
        public static final /*uint*/int UNUSED_3 = 0x00000020;
        public static final /*uint*/int BOOLEAN_PROPERTIES = 0x00000040;
        public static final /*uint*/int BORDER_STYLE = 0x00000080;
        public static final /*uint*/int MOUSE_POINTER = 0x00000100;
        public static final /*uint*/int SCROLL_BARS = 0x00000200;
        public static final /*uint*/int DISPLAYED_SIZE = 0x00000400;
        public static final /*uint*/int LOGICAL_SIZE = 0x00000800;
        public static final /*uint*/int SCROLL_POSITION = 0x00001000;
        public static final /*uint*/int GROUP_CNT = 0x00002000;
        public static final /*uint*/int RESERVED = 0x00004000;
        public static final /*uint*/int MOUSE_ICON = 0x00008000;
        public static final /*uint*/int CYCLE = 0x00010000;
        public static final /*uint*/int SPECIAL_EFFECT = 0x00020000;
        public static final /*uint*/int BORDER_COLOR = 0x00040000;
        public static final /*uint*/int CAPTION = 0x00080000;
        public static final /*uint*/int FONT = 0x00100000;
        public static final /*uint*/int PICTURE = 0x00200000;
        public static final /*uint*/int ZOOM = 0x00400000;
        public static final /*uint*/int PICTURE_ALIGNMENT = 0x00800000;
        public static final /*uint*/int PICTURE_TILING = 0x01000000;
        public static final /*uint*/int PICTURE_SIZE_MODE = 0x02000000;
        public static final /*uint*/int SHAPE_COOKIE = 0x04000000;
        public static final /*uint*/int DRAW_BUFFER = 0x08000000;

        public static final int length = 28;
    }

    private /*final*/ Forms2OleControlCollection mChildNodes = new Forms2OleControlCollection();
    private static final /*ushort*/short VERSION = (short) 0x0400;
    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(216.0, 216.0);
}

