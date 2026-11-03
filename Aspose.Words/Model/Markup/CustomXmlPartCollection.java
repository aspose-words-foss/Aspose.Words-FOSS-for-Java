// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/05/2010 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.msString;
import com.aspose.ArrayUtil;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.XmlUtilPal;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of Custom XML Parts. The items are <see cref="CustomXmlPart"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>You do not normally need to create instances of this class. You can access custom XML data
/// stored in a document via the <see cref="Document.CustomXmlParts"/> property.</para>
///
/// <seealso cref="CustomXmlPart"/>
/// <seealso cref="Document.CustomXmlParts"/>
/// </remarks>
public class CustomXmlPartCollection implements Iterable<CustomXmlPart>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item at the specified index.
    /// </summary>
    /// <param name="index">Zero-based index of the item.</param>
    public CustomXmlPart get(int index) { return mItems.get(index); }
    public void set(int index, CustomXmlPart value) { mItems.set(index, value); }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<CustomXmlPart> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an item to the collection.
    /// </summary>
    /// <param name="part">The custom XML part to add.</param>
    public void add(CustomXmlPart part)
    {
        msArrayList.add(mItems, part);
    }

    /// <summary>
    /// Creates a new XML part with the specified XML and adds it to the collection.
    /// </summary>
    /// <param name="id">Identifier of a new custom XML part.</param>
    /// <param name="xml">XML data of the part.</param>
    /// <returns>Created custom XML part.</returns>
    public CustomXmlPart add(String id, String xml)
    {
        CustomXmlPart part = new CustomXmlPart();
        part.setId(id);
        part.setData(Encoding.getUTF8().getBytes(xml));

        msArrayList.add(mItems, part);
        return part;
    }

    /// <summary>
    /// Removes an item at the specified index.
    /// </summary>
    /// <param name="index">The zero based index.</param>
    public void removeAt(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// Removes all elements from the collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
        setDataStoreRaw(null);
    }

    /// <summary>
    /// Finds and returns a custom XML part by its identifier.
    /// </summary>
    /// <param name="id">Case-sensitive string that identifies the custom XML part.</param>
    /// <returns>Returns <c>null</c> if a custom XML part with the specified identifier is not found.</returns>
    public CustomXmlPart getById(String id)
    {
        for (CustomXmlPart part : mItems)
        {
            if (msString.equals(part.getId(), id))
                return part;
        }

        return null;
    }

    /// <summary>
    /// Makes a deep copy of this collection and its items.
    /// </summary>
    public CustomXmlPartCollection deepClone()
    {
        // We don't have derived classes therefore we can simply create a new object, don't memberwise clone.
        CustomXmlPartCollection lhs = new CustomXmlPartCollection();

        for (CustomXmlPart item : this)
            lhs.add(item.deepClone());

        if (getDataStoreRaw() != null)
        {
            lhs.setDataStoreRaw(new byte[getDataStoreRaw().length]);
            System.arraycopy(getDataStoreRaw(), 0, lhs.getDataStoreRaw(), 0, getDataStoreRaw().length);
        }

        return lhs;
    }

    /// <summary>
    /// Returns <see cref="CustomXmlPart"/> corresponded to a Citation sources.
    /// <seealso cref="SdtCitation"/>
    /// <seealso cref="Fields.FieldCitation"/>
    /// </summary>
    CustomXmlPart getCitationSources() throws Exception
    {
        for (CustomXmlPart xmlPart : mItems)
        {
            // WORDSNET-28059 Resilience against zero length xml part.
            if (!ArrayUtil.hasData(xmlPart.getData()))
                continue;

            MemoryStream stream = new MemoryStream(xmlPart.getData());
            try /*JAVA: was using*/
            {
                org.w3c.dom.Document xml = XmlUtilPal.loadXml(stream, true);
                if ((xml.getDocumentElement() != null) && (CITATION_SOURCES_ELEMENT_NAME.equals(xml.getDocumentElement().getNodeName())))
                    return xmlPart;
            }
            finally { if (stream != null) stream.close(); }
        }

        return null;
    }

    /// <summary>
    /// Determines whether the collection contains a specific item.
    /// </summary>
    /// <param name="item">The item to locate in the collection.</param>
    boolean contains(CustomXmlPart item)
    {
        return mItems.contains(item);
    }

    /// <summary>
    /// Gets/sets raw datastore element for Rtf2Rtf scenario.
    /// </summary>
    /// <remarks>
    /// See remarks for TestJira13324.
    /// </remarks>
    byte[] getDataStoreRaw() { return mDataStoreRaw; }; void setDataStoreRaw(byte[] value) { mDataStoreRaw = value; };

    private byte[] mDataStoreRaw;

    /// <summary>
    /// The name of Citation sources element in a corresponding <see cref="CustomXmlPart"/>.
    /// </summary>
    static final String CITATION_SOURCES_ELEMENT_NAME = "b:Sources";

    private /*final*/ ArrayList<CustomXmlPart> mItems = new ArrayList<CustomXmlPart>();
}

