// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/01/2014 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.BinaryReader;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.IntToObjDictionary;
import com.aspose.Debug;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.EnumUtilPal;


/// <summary>
/// Implements VecVtHyperlink structure. See http://msdn.microsoft.com/en-us/library/dd908491(v=office.12).aspx
/// Used only during DOC import.
/// </summary>
class Hlinks
{
    /// <summary>
    /// Reads VecVtHyperlink.
    /// </summary>
    Hlinks(byte[] data) throws Exception
    {
        BinaryReader reader = new BinaryReader(new MemoryStream(data));

        // Item count.
        int cElements = reader.readInt32() / 6;

        // This array can have elements for few SubDocument. Elements must be sorted by dwAppValue descending. 
        // Break in this order means that next SubDocument should be selected.
        int lastDwApp = Integer.MAX_VALUE;
        int subDocTypeIndex = 0;

        for (int i = 0; i < cElements; i++)
        {
            // Skip unused dwHash.
            readVT_I4(reader);
            int dwApp = readVT_I4(reader);
            int dwOfficeArt = readVT_I4(reader);
            // Skip unused dwInfo.
            readVT_I4(reader);

            String hlink1 = readVtString(reader);
            // Skip unused hlink2.
            readVtString(reader);

            // There are can be several type of links: links to field, links to OfficeArt shape, links to picture etc.
            // Current implementation deals with links to field only.
            if (dwOfficeArt == 0/*dwInfo == 5*/)
            {
                // Order is broken, select next SubDocument.
                if (dwApp >= lastDwApp)
                    subDocTypeIndex++;

                IntToObjDictionary<String> curDictionary = (IntToObjDictionary<String>)mMapBySubDocType[subDocTypeIndex];
                if (curDictionary == null)
                {
                    curDictionary = new IntToObjDictionary<String>();
                    mMapBySubDocType[subDocTypeIndex] = curDictionary;
                }

                curDictionary.add(dwApp, hlink1);

                lastDwApp = dwApp;
            }
            else
            {
                // Linked with something else. Ignore now.
            }
        }
    }

    /// <summary>
    /// Returns hyperlink for given index.
    /// </summary>
    String getLink(/*SubDocType*/int subDocType, int index)
    {
        IntToObjDictionary<String> curDictionary = (IntToObjDictionary<String>)mMapBySubDocType[(int)subDocType];

        if (curDictionary != null)
            return curDictionary.get(index);

        return null;
    }

    /// <summary>
    /// Reads VT_I4 value. See [MS-OLEPS] 2.15 TypedPropertyValue.
    /// </summary>
    private static int readVT_I4(BinaryReader reader) throws Exception
    {
        int type = reader.readInt16();
        Debug.assertTrue(type == 0x0003 /* VT_I4 */);

        int padding = reader.readInt16();
        Debug.assertTrue(padding == 0);

        int value = reader.readInt32();
        return value;
    }

    /// <summary>
    /// Reads VT_LPWSTR value. See [MS-OLEPS] 2.15 TypedPropertyValue.
    /// </summary>
    private static String readVtString(BinaryReader reader) throws Exception
    {
        int type = reader.readInt16();
        Debug.assertTrue(type == 0x001F /* VT_LPWSTR */);

        int padding = reader.readInt16();
        Debug.assertTrue(padding == 0);

        int cch = reader.readInt32();

        byte[] bytes = reader.readBytes(cch * 2);

        // padding.
        if (cch % 2 != 0)
            reader.getBaseStream().setPosition(reader.getBaseStream().getPosition() + 2);

        String value = Encoding.getUnicode().getString(bytes, 0, bytes.length - 2);
        return value;
    }

    /// <summary>
    /// Elements are IntToObjDictionary which elements maps indexes to hyperlink string.
    /// </summary>
    private /*final*/ Object[] mMapBySubDocType = new Object[G_SUB_DOC_TYPE_COUNT];

    private static /*final*/ int G_SUB_DOC_TYPE_COUNT = EnumUtilPal.getEffectiveArrayLength((7));
}

