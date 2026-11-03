// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/24/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Iterator;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;
import com.aspose.ms.System.msString;


/// <summary>
/// Provides access to <see cref="SdtListItem"/> elements of a structured document tag.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
public class SdtListItemCollection implements Iterable<SdtListItem>, Cloneable
{
    SdtListItemCollection()
    {
    }

    /// <summary>
    /// Returns an <ms>enumerator</ms><java>iterator</java><cpp>enumerator</cpp> object that can be used to iterate over all items in the collection.
    /// </summary>
    public Iterator<SdtListItem> iterator()
    {
        return mChildren.iterator();
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Adds an item to this collection.
    /// </summary>
    public void add(SdtListItem item)
    {
        msArrayList.add(mChildren, item);
    }

    /// <summary>
    /// Removes a list item at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index of the item to remove.</param>
    public void removeAt(int index)
    {
        SdtListItem selectedValue = (getSelectedIndex() == index) ? null: getSelectedValue();

        mChildren.remove(index);

        setSelectedValueInternal(selectedValue);

        updateParentContent();
    }

    /// <summary>
    /// Clears all items from this collection.
    /// </summary>
    public void clear()
    {
        mChildren.clear();

        setSelectedValue(null);

        updateParentContent();
    }

    SdtListItemCollection deepClone()
    {
        SdtListItemCollection lhs = (SdtListItemCollection)memberwiseClone();

        lhs.mChildren = new ArrayList<SdtListItem>(mChildren.size());
        for (int itemIdx = 0; itemIdx < mChildren.size(); itemIdx++)
            lhs.add(this.get(itemIdx).deepClone());

        return lhs;
    }

    /// <summary>
    /// Searches for item which the Value property equals to the specified value and returns the zero-based index
    /// of the first occurrence within the collection.
    /// </summary>
    int indexOfItemValue(String value)
    {
        for (int i = 0; i < getCount(); i++)
        {
            if (msString.equals(this.get(i).getValue(), value))
            {
                return i;
            }
        }
        return -1;
    }

    /// <summary>
    /// Sets link to parent SDT to able properly update SDT content.
    /// </summary>
    void setParentSdt(StructuredDocumentTag parentSdt)
    {
        mParentSdt = parentSdt;
    }

    void setSelectedValueInternal(SdtListItem item)
    {
        if (item == null)
        {
            mLastValueIdx = -1;
        }
        else if (mChildren.contains(item))
        {
            mLastValueIdx = mChildren.indexOf(item);
        }
        else
        {
            throw new IllegalArgumentException("Can not find such LastValue in this collection.");
        }
    }

    /// <summary>
    /// Specifies currently selected value in this list.
    /// Null value allowed, meaning that no currently selected entry is associated with this list item collection.
    /// </summary>
    public SdtListItem getSelectedValue()
    {
        if (mLastValueIdx != -1)
            return this.get(mLastValueIdx);

        return null;
    }
    public void setSelectedValue(SdtListItem value)
    {
        setSelectedValueInternal(value);

        // WORDSNET-21505 Do not update parent control if display text is not actually changed.
        if ((mParentSdt == null) || (getSelectedValue() == null) || (!msString.equals(mParentSdt.getContentValue(), getSelectedValue().getDisplayText())))
            updateParentContent();
    }

    /// <summary>
    /// Specifies a value associated with current display text of a drop down list structured document tag.
    /// It is used to determine whether current display text in a SDT should be retained when document is loaded.
    /// (See ISO 29500, §17.5.2.5, 15 for more info.)
    /// </summary>
    String getLastItemValue() { return mLastItemValue; }
    void setLastItemValue(String value)
    {
        mLastItemValue = value;

        updateParentContent();
    }

    /// <summary>
    /// Returns a <see cref="SdtListItem"/> object given its zero-based index in the collection.
    /// </summary>
    public SdtListItem get(int index) { return mChildren.get(index); }

    /// <summary>
    /// Gets number of items in the collection.
    /// </summary>
    public int getCount() { return mChildren.size(); }

    /// <summary>
    /// Returns index of the selected value in the list, or -1 if nothing is selected.
    /// This is used to generate active control in PDF.
    /// </summary>
    int getSelectedIndex() { return mLastValueIdx; }

    private void updateParentContent()
    {
        SdtContentUpdater.updateNonBoundDataContent(mParentSdt);
    }

    private int mLastValueIdx = -1;
    private ArrayList<SdtListItem> mChildren = new ArrayList<SdtListItem>();
    private String mLastItemValue;
    private StructuredDocumentTag mParentSdt;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

