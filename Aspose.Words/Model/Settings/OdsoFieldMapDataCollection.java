// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/10/2009 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// A typed collection of the <see cref="OdsoFieldMapData"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/mail-merge-and-reporting/">Mail Merge and Reporting</a> documentation article.</para>
/// </summary>
/// <seealso cref="OdsoFieldMapData"/>
/// <seealso cref="Odso.FieldMapDatas"/>
public class OdsoFieldMapDataCollection implements Iterable<OdsoFieldMapData>
{
    /// <summary>
    /// Gets the number of elements contained in the collection.
    /// </summary>
    public int getCount() { return mItems.size(); }

    /// <summary>
    /// Gets or sets an item in this collection.
    /// </summary>
    public OdsoFieldMapData get(int index) { return mItems.get(index); }
    public void set(int index, OdsoFieldMapData value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mItems.set(index, value);
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<OdsoFieldMapData> iterator()
    {
        return mItems.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an object to the end of this collection.
    /// </summary>
    /// <param name="value">The object to add. Cannot be <c>null</c>.</param>
    public int add(OdsoFieldMapData value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        msArrayList.add(mItems, value);
        return mItems.size() - 1;
    }

    /// <summary>
    /// Removes all elements from this collection.
    /// </summary>
    public void clear()
    {
        mItems.clear();
    }

    /// <summary>
    /// Removes the element at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the element.</param>
    public void removeAt(int index)
    {
        mItems.remove(index);
    }

    /// <summary>
    /// In the DOC file, a valid field map data collection must contain 30 elements and they 
    /// must have predefined merge field names in a specific order. 
    /// This method rearranges this contents of this collection so it becomes valid from the DOC format point of view.
    /// </summary>
    void makeValid()
    {
        if (getCount() == 0)
            return;

        // We will temporarily place all data into this array.
        OdsoFieldMapData[] validMap = new OdsoFieldMapData[VALID_MAP_LENGTH];

        for (OdsoFieldMapData fieldMapData : this)
        {
            // Take every item from the original collection and attempt to find its presrcribed position from its name and place in the temp array.
            if (fieldMapData.getType() != OdsoFieldMappingType.NULL)
            {
                /*PredefinedMergeFieldName*/int predefined = MailMergeEnum.stringToPredefinedMergeFieldName(fieldMapData.getMappedName());
                if (predefined != PredefinedMergeFieldName.INVALID)
                    validMap[(int)predefined] = fieldMapData;
            }
        }

        // Remaining null elements in the temp array should be filled with empty mappings.
        for (int i = 0; i < validMap.length; i++)
        {
            if (validMap[i] == null)
                validMap[i] = new OdsoFieldMapData();
        }

        // Now copy the valid temp array into this collection.
        this.clear();
        for (OdsoFieldMapData fieldMapData : validMap)
            this.add(fieldMapData);
    }

    private /*final*/ ArrayList<OdsoFieldMapData> mItems = new ArrayList<OdsoFieldMapData>();

    static final int VALID_MAP_LENGTH = 30;
}

