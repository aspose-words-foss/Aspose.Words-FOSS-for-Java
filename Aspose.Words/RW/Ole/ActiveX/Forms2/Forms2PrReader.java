// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.BitConverter;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.ms.System.Text.Encoding;
import java.util.ArrayList;
import com.aspose.ms.System.Guid;
import com.aspose.Debug;
import com.aspose.ms.System.msString;


/// <summary>
/// Implements reading of the Forms2 control properties.
/// </summary>
class Forms2PrReader
{
    Forms2PrReader(BinaryReader reader, Forms2Pr pr) throws Exception
    {
        mStart = (int) reader.getBaseStream().getPosition();
        mReader = reader;
        mPr = pr;
    }

    void readData(/*uint*/int mask, int count, int key) throws Exception
    {
        if (((Flags & 0xFFFFFFFFL) & (mask & 0xFFFFFFFFL)) != 0)
        {
            /*uint*/int val = readData(mask, count);
            handleValue(key, val);
        }
    }

    /*uint*/int readData(/*uint*/int mask, int count) throws Exception
    {
        if (((Flags & 0xFFFFFFFFL) & (mask & 0xFFFFFFFFL)) == 0)
            return 0;

        readPadding(count);
        byte[] raw = new byte[4];
        byte[] read = mReader.readBytes(count);
        System.arraycopy((read), 0, (raw), 0, (count));
        /*uint*/int val = BitConverter.toUInt32(raw, 0);

        return val;
    }

    void skipData(/*uint*/int mask, int count) throws Exception
    {
        if (((Flags & 0xFFFFFFFFL) & (mask & 0xFFFFFFFFL)) == 0)
            return;

        readPadding(count);
        mReader.getBaseStream().seek(count, SeekOrigin.CURRENT);
    }

    void readString(/*uint*/int mask, /*uint*/int cValue, int key) throws Exception
    {
        String value = readString(mask, cValue);
        if (value != null)
            handleValue(key, value);
    }

    private String readString(/*uint*/int mask, /*uint*/int cValue) throws Exception
    {
        if (((Flags & 0xFFFFFFFFL) & (mask & 0xFFFFFFFFL)) == 0)
            return null;

        readPadding(4);

        return readString(mReader, cValue);
    }

    void readBytes(/*uint*/int mask, int len, int key) throws Exception
    {
        if (((Flags & 0xFFFFFFFFL) & (mask & 0xFFFFFFFFL)) == 0)
            return;

        readPadding(4);
        byte[] bytes =  mReader.readBytes(len);
            handleValue(key, bytes);
    }

    void readPadding(int val) throws Exception
    {
        int pos = (int)mReader.getBaseStream().getPosition() - mStart;

        int div = pos%val;

        if (div == 0 || val == 1)
            return;
        int pad = val - div;
        mReader.readBytes(pad);
    }

    private static String readString(BinaryReader reader, /*uint*/int cValue) throws Exception
    {
        boolean compressed = ((cValue & 0xFFFFFFFFL) & 0x80000000L) != 0;
        /*uint*/int size = (/*uint*/int)((cValue & 0xFFFFFFFFL) & 0x0fffffff);

        byte[] raw = reader.readBytes((int)(size & 0xFFFFFFFFL));

        Encoding encoding = compressed ? Encoding.getASCII() : Encoding.getUnicode();
        return encoding.getString(raw);
    }

    ArrayList<String> readStringArray(/*uint*/int mask, /*uint*/int size) throws Exception
    {
        ArrayList<String> stringList = new ArrayList<String>();

        long startPos = mReader.getBaseStream().getPosition();
        while (startPos + (size & 0xFFFFFFFFL) > mReader.getBaseStream().getPosition())
        {
            /*uint*/int cText = readData(mask, 4);
            String text = readString(mask, cText);
            readPadding(4);
            stringList.add(text);
        }

        return stringList;
    }

    /// <summary>
    /// Reads GuidAndPicture as specified in [MS-OFORMS] 2.4.8 GuidAndPicture into a specified key.
    /// </summary>
    void readGuidAndPicture(int key) throws Exception
    {
        Guid clsid = new Guid(mReader.readBytes(16));
        Debug.assertTrue(Guid.equals(clsid, Forms2OleControl.CLSID_STD_PICTURE));

        // SdtPicture structure.
        int preamble = mReader.readInt32();
        Debug.assertTrue(preamble == Forms2OleControl.STD_PICTURE_PREAMBLE);

        /*uint*/int size = mReader.readUInt32();
        mPr.setAttr(key, mReader.readBytes((int) (size & 0xFFFFFFFFL)));
    }

    /// <summary>
    /// Reads GuidAndFont as specified in [MS-OFORMS] 2.4.7 GuidAndFont.
    /// </summary>
    /// <remarks>
    /// FormFont in this structure can be of two types: <see cref="StdFont"/> or <see cref="Forms2TextProps"/>.
    /// If font is StdFont, then it will be read into <see cref="Forms2Attr.Font"/> key,
    /// otherwise it will be read into a set of Forms2Attr keys that correspond to [MS-OFORMS] 2.3.2 TextPropsPropMask.
     /// </remarks>
    void readGuidAndFont() throws Exception
    {
        String guid = new Guid(mReader.readBytes(16)).toString();

        switch (gStringSwitchMap.of(guid))
        {
            case/*StdFont.GUID*/0:
            {
                StdFont.read(mReader, mPr);
                break;
            }
            case/*Forms2TextProps.GUID*/1:
            {
                Forms2TextProps.read(mReader, mPr);
                break;
            }
            default:
                throw new IllegalStateException(msString.format("Invalid GuidAndFont GUID: {0}", guid));
        }
    }

    private void handleValue(int key, /*uint*/int value)
    {
        switch (key)
        {
            case Forms2Attr.FOREGROUND_COLOR:
            case Forms2Attr.BACKGROUND_COLOR:
            case Forms2Attr.BORDER_COLOR:
                mPr.setAttr(key, OleColor.fromRaw(value));
                break;

            case Forms2Attr.VARIOUS_PROPERTY_BITS:
                mPr.setAttr(key, (/*VariousPropertiesBits*//*uint*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.MATCH_ENTRY:
                mPr.setAttr(key, (/*MatchEntry*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.SHOW_DROP_BUTTON_WHEN:
                mPr.setAttr(key, (/*ShowDropButtonWhen*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.DROP_BUTTON_STYLE:
                mPr.setAttr(key, (/*DropButtonStyle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.LIST_STYLE:
                mPr.setAttr(key, (/*ListStyle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.MULTI_SELECT:
                mPr.setAttr(key, (/*MultiSelect*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.BORDER_STYLE:
                mPr.setAttr(key, (/*BorderStyle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.DISPLAY_STYLE:
                mPr.setAttr(key, (/*DisplayStyle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.SCROLL_BARS:
                mPr.setAttr(key, (/*ScrollBars*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.PICTURE_POSITION:
                mPr.setAttr(key, (/*PicturePosition*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.PICTURE_SIZE_MODE:
                mPr.setAttr(key, (/*PictureSizeMode*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.PICTURE_ALIGNMENT:
                mPr.setAttr(key, (/*PictureAlignment*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.SPECIAL_EFFECT:
                mPr.setAttr(key, (/*SpecialEffect*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.ORIENTATION:
                mPr.setAttr(key, (/*FormOrientation*//*uint*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.PROPORTIONAL_THUMB:
                mPr.setAttr(key, (/*ProportionalThumb*//*ushort*/short)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.MOUSE_POINTER:
                mPr.setAttr(key, (/*MousePointer*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.PARAGRAPH_ALIGN:
                mPr.setAttr(key, (/*ParagraphAlign*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.CYCLE:
                mPr.setAttr(key, (/*Cycle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.BIT_FLAGS_SITE:
                mPr.setAttr(key, (/*SiteFlag*//*uint*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.BIT_FLAGS_DX:
                mPr.setAttr(key, (/*DesignExtenderFlag*//*uint*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.TAB_ORIENATION:
                mPr.setAttr(key, (/*TabOrientation*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.TAB_STYLE:
                mPr.setAttr(key, (/*TabStyle*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.TRANSITION_EFFECT:
                mPr.setAttr(key, (/*TransitionEffect*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.CLICK_CONTROL_MODE:
                mPr.setAttr(key, (/*ClickControlMode*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.DBL_CLICK_CONTROL_MODE:
                mPr.setAttr(key, (/*DblClickControlMode*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.CLSID_CACHE_INDEX:
                mPr.setAttr(key, (/*ClsidCacheIndex*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.BOOLEAN_PROPERTIES:
                mPr.setAttr(key, (/*FormFlags*//*uint*/int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.MIN:
            case Forms2Attr.MAX:
            case Forms2Attr.POSITION:
            case Forms2Attr.PREV_ENABLED:
            case Forms2Attr.NEXT_ENABLED:
            case Forms2Attr.SMALL_CHANGE:
            case Forms2Attr.DELAY:
            case Forms2Attr.ZOOM:
            case Forms2Attr.ID:
            case Forms2Attr.HELP_CONTEXT_ID:
            case Forms2Attr.TAB_INDEX:
            case Forms2Attr.LIST_INDEX:
            case Forms2Attr.PAGE_COUNT:
            case Forms2Attr.GRID_X:
            case Forms2Attr.GRID_Y:
                mPr.setAttr(key, (int)(value & 0xFFFFFFFFL));
                break;

            case Forms2Attr.ACCELERATOR:
                mPr.setAttr(key, (char)(value & 0xFFFFFFFFL));
                break;

            default:
                mPr.setAttr(key, value);
                break;
        }
    }

    private void handleValue(int key, String value)
    {
        switch (key)
        {
            case Forms2Attr.CAPTION:
            case Forms2Attr.VALUE:
            case Forms2Attr.GROUP_NAME:
            case Forms2Attr.FONT_NAME:
                mPr.setAttr(key, value);
                break;

            default:
                mPr.setAttr(key, value);
                break;
        }
    }

    private void handleValue(int key, byte[] value)
    {
        switch (key)
        {
            case Forms2Attr.SIZE:
            case Forms2Attr.LOGICAL_SIZE:
                mPr.setAttr(key, OleSize.fromRaw(value));
                break;

            case Forms2Attr.SCROLL_POSITION:
            case Forms2Attr.SITE_POSITION:
                mPr.setAttr(key, OlePosition.fromRaw(value));
                break;

            default:
                mPr.setAttr(key, value);
                break;
        }
    }

    /*uint*/int Flags;

    private /*final*/ BinaryReader mReader;
    private /*final*/ int mStart;
    private /*final*/ Forms2Pr mPr;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"0be35203-8f91-11ce-9de3-00aa004bb851",
		"afc20920-da4e-11ce-b943-00aa006887b4"
	);

}

