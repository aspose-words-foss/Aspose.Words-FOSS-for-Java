// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/09/2020 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.msString;
import com.aspose.Crc32Xfer;
import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.TimeZone;
import com.aspose.ms.System.Threading.CurrentThread;
import java.util.HashMap;


/// <summary>
/// Provides context information to bulk XML mapping update.
/// </summary>
class XmlMappingContext
{
    /// <summary>
    /// Returns mapped custom XML document from cache.
    /// </summary>
    org.w3c.dom.Document get(String storeItemId)
    {
        return mXmlCache.containsKey(storeItemId) ? mXmlCache.get(storeItemId) : null;
    }

    /// <summary>
    /// Returns mapped XML nodes form cache.s
    /// </summary>
    ArrayList<org.w3c.dom.Node> get(String storeItemId, String xPath)
    {
        String cacheKey = msString.format("{0}{1}", storeItemId, xPath);
        return mXPathCache.get(cacheKey);
    }

    /// <summary>
    /// Returns cached custom XML document CRC.
    /// </summary>
    /// <remarks>
    /// AM. Still do not want to cache CRC in CustomXmlPart class as we provide public access to byte array and
    /// we cannot detect changes in byte array itself, for example CustomXmlPart.Data[0] = 100.
    /// This context caches data for bulk update during post-loading/validation only.
    /// </remarks>
    String getStoreItemChecksum(XmlMapping xmlMapping) throws Exception
    {
        // WORDSNET-23673 Missing CustomXmlPart.
        if (xmlMapping.getCustomXmlPart() == null)
            return null;

        String storeItemId = xmlMapping.getStoreItemId();

        if (!mStoreItemChecksumCache.containsKey(storeItemId))
        {
            String checksum = Crc32Xfer.getBase64String(xmlMapping.getCustomXmlPart().getData());
            mStoreItemChecksumCache.put(storeItemId, checksum);
        }

        return mStoreItemChecksumCache.get(storeItemId);
    }

    boolean containsXmlDocument(String storeItemId)
    {
        return mXmlCache.containsKey(storeItemId);
    }

    boolean containsXPath(String storeItemId, String xPath)
    {
        String cacheKey = msString.format("{0}{1}", storeItemId, xPath);
        return mXPathCache.containsKey(cacheKey);
    }

    void add(String storeItemId, String xPath, ArrayList<org.w3c.dom.Node> xmlNodes)
    {
        String cacheKey = msString.format("{0}{1}", storeItemId, xPath);
        msDictionary.add(mXPathCache, cacheKey, xmlNodes);
    }

    org.w3c.dom.Document add(String storeItemId, org.w3c.dom.Document xmlDocument)
    {
        msDictionary.add(mXmlCache, storeItemId, xmlDocument);
        return xmlDocument;
    }

    void remove(String storeItemId)
    {
        if (mXmlCache.containsKey(storeItemId))
            msDictionary.remove(mXmlCache, storeItemId);

        if (mStoreItemChecksumCache.containsKey(storeItemId))
            msDictionary.remove(mStoreItemChecksumCache, storeItemId);

        if (mXPathCache.keySet().size() > 0)
        {
            ArrayList<String> itemsToRemove = new ArrayList<String>();
            for (String key : (Iterable<String>) mXPathCache.keySet())
            {
                if (msString.startsWith(key, storeItemId, StringComparison.ORDINAL))
                    msArrayList.add(itemsToRemove, key);
            }

            for (String key : itemsToRemove)
                msDictionary.remove(mXPathCache, key);
        }
    }

    /// <summary>
    /// Collects SDT loaded with mapped documents.
    /// </summary>
    ArrayList<StructuredDocumentTag> getInnerTags() { return mInnerTags; }

    TimeZone CustomTimeZone = CurrentThread.getTimeZone();
    private /*final*/ HashMap<String, org.w3c.dom.Document> mXmlCache = new HashMap<String, org.w3c.dom.Document>();
    private /*final*/ HashMap<String, String> mStoreItemChecksumCache = new HashMap<String, String>();
    private /*final*/ HashMap<String, ArrayList<org.w3c.dom.Node>> mXPathCache = new HashMap<String, ArrayList<org.w3c.dom.Node>>();
    private /*final*/ ArrayList<StructuredDocumentTag> mInnerTags = new ArrayList<StructuredDocumentTag>();
}

