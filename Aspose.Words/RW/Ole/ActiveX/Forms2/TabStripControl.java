// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.IO.BinaryWriter;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.MathUtil;
import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.RefInt;


/// <summary>
/// The TabStrip control presents a set of related controls as a visual group.
/// </summary>
class TabStripControl extends Forms2OleControl
{
    /// <summary>
    /// Ctor.
    /// </summary>
    TabStripControl(String name)
    {
    	super(name);
	
        setSize(mDefaultSize);
        Pr.set(Forms2Attr.LIST_INDEX, 0);
    }

    /// <summary>
    /// Reads the control from a binary reader.
    /// </summary>
    /*override*/ void read(BinaryReader reader) throws Exception
    {
        int version = (reader.readUInt16() & 0xFFFF);
        Debug.assertTrue(version == FORMS_2_VERSION);

        reader.readUInt16();      // cbTabStrip
        /*TabStripPropMask*/int flags = (/*TabStripPropMask*/int)(reader.readUInt32() & 0xFFFFFFFFL);

        Forms2PrReader prReader = new Forms2PrReader(reader, Pr);
        prReader.Flags = (/*uint*/int) flags;

        // Data block.
        prReader.readData((/*uint*/int)TabStripPropMask.LIST_INDEX, 4, Forms2Attr.LIST_INDEX);
        prReader.readData((/*uint*/int)TabStripPropMask.BACK_COLOR, 4, Forms2Attr.BACKGROUND_COLOR);
        prReader.readData((/*uint*/int)TabStripPropMask.FORE_COLOR, 4, Forms2Attr.FOREGROUND_COLOR);
        /*uint*/int itemsSize = prReader.readData((/*uint*/int)TabStripPropMask.ITEMS, 4);
        prReader.readData((/*uint*/int)TabStripPropMask.MOUSE_POINTER, 1, Forms2Attr.MOUSE_POINTER);
        prReader.readData((/*uint*/int)TabStripPropMask.TAB_ORIENTATION, 4, Forms2Attr.TAB_ORIENATION);
        prReader.readData((/*uint*/int)TabStripPropMask.TAB_STYLE, 4, Forms2Attr.TAB_STYLE);
        prReader.readData((/*uint*/int)TabStripPropMask.TAB_FIXED_WIDTH, 4, Forms2Attr.TAB_FIXED_WIDTH);
        prReader.readData((/*uint*/int)TabStripPropMask.TAB_FIXED_HEIGHT, 4, Forms2Attr.TAB_FIXED_HEIGHT);

        /*uint*/int tipsSize = prReader.readData((/*uint*/int)TabStripPropMask.TIP_STRINGS, 4);
        /*uint*/int namesSize = prReader.readData((/*uint*/int)TabStripPropMask.NAMES, 4);

        prReader.readData((/*uint*/int)TabStripPropMask.VARIOUS_PROPERTY_BITS, 4, Forms2Attr.VARIOUS_PROPERTY_BITS);

        prReader.readData((/*uint*/int)TabStripPropMask.TABS_ALLOCATED, 4, Forms2Attr.TABS_ALLOCATED);
        /*uint*/int tagsSize = prReader.readData((/*uint*/int)TabStripPropMask.TAGS, 4);
        prReader.readData((/*uint*/int)TabStripPropMask.TAB_DATA, 4, Forms2Attr.TAB_DATA);
        /*uint*/int accelsSize = prReader.readData((/*uint*/int)TabStripPropMask.ACCELERATOR, 4);
        prReader.readData((/*uint*/int)TabStripPropMask.MOUSE_ICON, 2, Forms2Attr.MOUSE_ICON);

        Pr.setAttr(Forms2Attr.MULTI_ROW, (flags & TabStripPropMask.MULTI_ROW) != 0);
        Pr.setAttr(Forms2Attr.NEW_VERSION, (TabStripPropMask.NEW_VERSION) != 0);

        // Extra block
        prReader.readBytes((/*uint*/int)TabStripPropMask.SIZE, 8, Forms2Attr.SIZE);

        ArrayList<String> captions = prReader.readStringArray((/*uint*/int)TabStripPropMask.ITEMS, itemsSize);
        ArrayList<String> tips = prReader.readStringArray((/*uint*/int)TabStripPropMask.TIP_STRINGS, tipsSize);
        ArrayList<String> names = prReader.readStringArray((/*uint*/int)TabStripPropMask.NAMES, namesSize);
        ArrayList<String> tags = prReader.readStringArray((/*uint*/int)TabStripPropMask.TAGS, tagsSize);
        ArrayList<String> accelerators = prReader.readStringArray((/*uint*/int)TabStripPropMask.ACCELERATOR, accelsSize);

        addTabs(captions, tips, names, tags, accelerators);

        // Stream data.
        if ((flags & TabStripPropMask.MOUSE_ICON) != 0)
            prReader.readGuidAndPicture(Forms2Attr.MOUSE_ICON);

        // TextProps.
        Forms2TextProps.read(reader, Pr);

        //TabFlag data.
        /*uint*/int tabData = (/*uint*/Integer)Pr.fetchAttr(Forms2Attr.TAB_DATA);
        for (int i = 0; i < (tabData & 0xFFFFFFFFL); i++)
        {
            // If Tabs less then tabData, then create missed ones.
            if (i >= mTabs.size())
                mTabs.add(new TabStripTab(""));

            mTabs.get(i).setTabFlag((/*TabFlag*/int)(prReader.readData((/*uint*/int)TabStripPropMask.TAB_DATA, 4) & 0xFFFFFFFFL));
        }
    }

    /// <summary>
    /// Writes the control to a binary writer.
    /// </summary>
    /*override*/ /*uint*/int write(BinaryWriter writer) throws Exception
    {
        int startPos = (int)writer.getBaseStream().getPosition();

        // Update [MS-OFORMS] 2.5.81 TabData.
        Pr.setAttr(Forms2Attr.TAB_DATA, mTabs.size());

        writer.writeInt16(FORMS_2_VERSION);

        // The size of data and property flags will be written later.
        writer.seek(2, SeekOrigin.CURRENT);// cbTabStrip
        writer.seek(4, SeekOrigin.CURRENT);// TabStripPropMask

        Forms2PrWriter prWriter = new Forms2PrWriter(writer, Pr);
        // According to [MS-OFORMS] 2.2.9.1 TabStripControl, fSize and fNewVersion MUST be set to 1.
        /*uint*/int flags = (/*uint*/int)(TabStripPropMask.SIZE | TabStripPropMask.NEW_VERSION);
        flags |= prWriter.write(Forms2Attr.LIST_INDEX, 4) ? (((/*uint*/int)TabStripPropMask.LIST_INDEX) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.BACKGROUND_COLOR, 4) ? (((/*uint*/int)TabStripPropMask.BACK_COLOR) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.FOREGROUND_COLOR, 4) ? (((/*uint*/int)TabStripPropMask.FORE_COLOR) & 0xFFFFFFFFL) : 0;
        /*uint*/int size = 0;
        RefInt referenceToSize = new RefInt(size);
        byte[][] items = Forms2PrWriter.getBytes(getItems(), /*out*/ referenceToSize);
        size = referenceToSize.get();
        if ((size & 0xFFFFFFFFL) > 0)
            prWriter.write(size);
        flags |= prWriter.write(Forms2Attr.MOUSE_POINTER, 1) ? (((/*uint*/int)TabStripPropMask.MOUSE_POINTER) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TAB_ORIENATION, 4) ? (((/*uint*/int)TabStripPropMask.TAB_ORIENTATION) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TAB_STYLE, 4) ? (((/*uint*/int)TabStripPropMask.TAB_STYLE) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TAB_FIXED_WIDTH, 4) ? (((/*uint*/int)TabStripPropMask.TAB_FIXED_WIDTH) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TAB_FIXED_HEIGHT, 4) ? (((/*uint*/int)TabStripPropMask.TAB_FIXED_HEIGHT) & 0xFFFFFFFFL) : 0;
        referenceToSize.set(size);
        byte[][] tips = Forms2PrWriter.getBytes(getTips(), /*out*/ referenceToSize);
        size = referenceToSize.get();
        if ((size & 0xFFFFFFFFL) > 0)
            prWriter.write(size);
        referenceToSize.set(size);
        byte[][] names = Forms2PrWriter.getBytes(getNames(), /*out*/ referenceToSize);
        size = referenceToSize.get();
        if ((size & 0xFFFFFFFFL) > 0)
            prWriter.write(size);
        flags |= prWriter.write(Forms2Attr.VARIOUS_PROPERTY_BITS, 4) ? (((/*uint*/int)TabStripPropMask.VARIOUS_PROPERTY_BITS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.write(Forms2Attr.TABS_ALLOCATED, 4) ? (((/*uint*/int)TabStripPropMask.TABS_ALLOCATED) & 0xFFFFFFFFL) : 0;
        referenceToSize.set(size);
        byte[][] tags = Forms2PrWriter.getBytes(getTags(), /*out*/ referenceToSize);
        size = referenceToSize.get();
        if ((size & 0xFFFFFFFFL) > 0)
            prWriter.write(size);
        flags |= prWriter.write(Forms2Attr.TAB_DATA, 4) ? (((/*uint*/int)TabStripPropMask.TAB_DATA) & 0xFFFFFFFFL) : 0;
        referenceToSize.set(size);
        byte[][] accelerators = Forms2PrWriter.getBytes(getAccelerators(), /*out*/ referenceToSize);
        size = referenceToSize.get();
        if ((size & 0xFFFFFFFFL) > 0)
            prWriter.write(size);
        flags |= prWriter.write(Forms2Attr.MOUSE_ICON, 2) ? (((/*uint*/int)TabStripPropMask.MOUSE_ICON) & 0xFFFFFFFFL) : 0;

        // Specifies whether the value of the MultiRow property is NOT the file format default.
        flags |= (!Pr.isDefaultValue(Forms2Attr.MULTI_ROW)) ? (((/*uint*/int)TabStripPropMask.MULTI_ROW) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Extra block.
        flags |= prWriter.write(Forms2Attr.SIZE, 8) ? (((/*uint*/int)TabStripPropMask.SIZE) & 0xFFFFFFFFL) : 0;

        flags |= prWriter.writeBytes(items) ? (((/*uint*/int)TabStripPropMask.ITEMS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(tips) ? (((/*uint*/int)TabStripPropMask.TIP_STRINGS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(names) ? (((/*uint*/int)TabStripPropMask.NAMES) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(tags) ? (((/*uint*/int)TabStripPropMask.TAGS) & 0xFFFFFFFFL) : 0;
        flags |= prWriter.writeBytes(accelerators) ? (((/*uint*/int)TabStripPropMask.ACCELERATOR) & 0xFFFFFFFFL) : 0;

        // Write padding to the end, so the size of written data is divisible by 4.
        prWriter.writePadding(4);

        // Write cbTabStrip and TabStripPropMask.
        prWriter.writeDataSizeAndPropMask(startPos, flags);

        // Stream data.
        if (((/*TabStripPropMask*/int)(flags & 0xFFFFFFFFL) & TabStripPropMask.MOUSE_ICON) != 0)
            prWriter.writeGuidAndPicture(getMouseIcon());

        // TextProps.
        Forms2TextProps.write(writer, Pr);

        //TabFlag data.
        for (TabStripTab tab : mTabs)
            prWriter.write((/*uint*/int)tab.getTabFlag());

        return (/*uint*/int)(writer.getBaseStream().getPosition() - startPos);
    }

    /// <summary>
    /// Adds tabs to the control.
    /// </summary>
    private void addTabs(ArrayList<String> captions, ArrayList<String> tips, ArrayList<String> names, ArrayList<String> tags,
        ArrayList<String> accelerators)
    {
        int tabsCount = MathUtil.max(captions.size(), tips.size(), names.size(), tags.size(), accelerators.size());
        for (int i = 0; i < tabsCount; i++)
        {
            mTabs.add(new TabStripTab(""));

            if (i < captions.size())
                mTabs.get(i).setCaption(captions.get(i));

            if (i < tips.size())
                mTabs.get(i).setTip(tips.get(i));

            if (i < names.size())
                mTabs.get(i).setName(names.get(i));

            if (i < tags.size())
                mTabs.get(i).setTag(tags.get(i));

            if (i < accelerators.size())
                mTabs.get(i).setAccelerator((StringUtil.hasChars(accelerators.get(i))) ? accelerators.get(i).charAt(0) : '\u0000');
        }
    }

    /// <summary>
    /// Gets type of Forms 2.0 control.
    /// </summary>
    public /*override*/ /*Forms2OleControlType*/int getType() { return Forms2OleControlType.TAB_STRIP; }

    /// <summary>
    /// Gets ClsidCacheIndex.
    /// </summary>
    /*override*/ /*ClsidCacheIndex*/int getClsidCacheIndex() { return ClsidCacheIndex.TAB_STRIP; }

    /// <summary>
    /// Gets a list of the tabs.
    /// </summary>
    ArrayList<TabStripTab> getTabs() { return mTabs; }

    /// <summary>
    /// Gets a index of the selected tab.
    /// </summary>
    int getListIndex() { return (/*int*/Integer)Pr.fetchAttr(Forms2Attr.LIST_INDEX); }

    /// <summary>
    /// Gets a Clsid of the embedded object.
    /// </summary>
    protected /*override*/ String getClsidVirtual() { return TAB_STRIP_CONTROL_CLSID; }

    /// <summary>
    /// Gets a list of captions.
    /// </summary>
    private ArrayList<String> getItems()
    {
        ArrayList<String> captions = new ArrayList<String>();
        for (TabStripTab tab : mTabs)
            captions.add(tab.getCaption());

        return captions;
    }

    /// <summary>
    /// Gets a list of tooltips.
    /// </summary>
    private ArrayList<String> getTips()
    {
        ArrayList<String> tips = new ArrayList<String>();
        for (TabStripTab tab : mTabs)
            tips.add(tab.getTip());

        return tips;
    }

    /// <summary>
    /// Gets a list of names.
    /// </summary>
    private ArrayList<String> getNames()
    {
        ArrayList<String> names = new ArrayList<String>();
        for (TabStripTab tab : mTabs)
            names.add(tab.getName());

        return names;
    }

    /// <summary>
    /// Gets a list of tags.
    /// </summary>
    private ArrayList<String> getTags()
{
        ArrayList<String> tags = new ArrayList<String>();
        for (TabStripTab tab : mTabs)
            tags.add(tab.getTag());

        return tags;
    }

    /// <summary>
    /// Gets a list of accelerators.
    /// </summary>
    private ArrayList<String> getAccelerators()
    {
        ArrayList<String> accelerators = new ArrayList<String>();
        for (TabStripTab tab : mTabs)
        {
            String  accelerator = tab.getAccelerator() > 0 ? msString.newString(tab.getAccelerator(), 1) : "";
            accelerators.add(accelerator);
        }

        return accelerators;
    }

    /// <summary>
    /// Implements [MS-OFORMS] 2.2.9.2 TabStripPropMask.
    /// </summary>
    private /*enum*/ final class TabStripPropMask
    {
        private TabStripPropMask(){}
        
        public static final int LIST_INDEX = 0x00000001;
        public static final int BACK_COLOR = 0x00000002;
        public static final int FORE_COLOR = 0x00000004;
        public static final int UNUSED_1 = 0x00000008;
        public static final int SIZE = 0x00000010;
        public static final int ITEMS = 0x00000020;
        public static final int MOUSE_POINTER = 0x00000040;
        public static final int UNUSED_2 = 0x00000080;
        public static final int TAB_ORIENTATION = 0x00000100;
        public static final int TAB_STYLE = 0x00000200;
        public static final int MULTI_ROW = 0x00000400;
        public static final int TAB_FIXED_WIDTH = 0x00000800;
        public static final int TAB_FIXED_HEIGHT = 0x00001000;
        public static final int TOOLTIPS = 0x00002000;
        public static final int UNUSED_3 = 0x00004000;
        public static final int TIP_STRINGS = 0x00008000;
        public static final int UNUSED_4 = 0x00010000;
        public static final int NAMES = 0x00020000;
        public static final int VARIOUS_PROPERTY_BITS = 0x00040000;
        public static final int NEW_VERSION = 0x00080000;
        public static final int TABS_ALLOCATED = 0x00100000;
        public static final int TAGS = 0x00200000;
        public static final int TAB_DATA = 0x00400000;
        public static final int ACCELERATOR = 0x00800000;
        public static final int MOUSE_ICON = 0x01000000;

        public static final int length = 25;
    }

    private /*final*/ ArrayList<TabStripTab> mTabs = new ArrayList<TabStripTab>();
    private /*final*/ OleSize mDefaultSize = OleSize.fromPoints(144.0, 108.0);
}

