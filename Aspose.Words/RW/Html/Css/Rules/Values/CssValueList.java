// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/02/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents a list of <see cref="CssValue">CSS values</see>.
/// </summary>
class CssValueList implements Iterable<CssValue>
{
    CssValueList()
    {
        mValues = new ArrayList<CssValue>();
    }

    CssValueList(int capacity)
    {
        mValues = new ArrayList<CssValue>(capacity);
    }

    CssValueList(CssValueList other)
    {
    	this(other.getCount());
	
        msArrayList.addRange(mValues, other.mValues);
    }

    CssValueList(/*params*/ CssValue... values)
    {
    	this();
	
        msArrayList.addRange(mValues, values);
    }

    public Iterator<CssValue> iterator()
    {
        return mValues.iterator();
    }

    /// <summary>
    /// Returns an enumerator object.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    public /*override*/ int hashCode()
    {
        int result = 0;
        /*unchecked*/
        {
            for (CssValue value : mValues)
            {
                result = (result * 397) ^ value.hashCode();
            }
        }
        return result;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;

        if (msObject.referenceEquals(this, obj))
            return true;

        CssValueList valueList = ms.as(obj, CssValueList.class);
        if (valueList == null)
            return false;

        return equals(valueList);
    }

    /// <summary>
    /// Determines whether the specified CSS values are equal to the current values.
    /// </summary>
    boolean equals(CssValueList other)
    {
        // Standard reference comparisons.
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;

        if (getCount() != other.getCount())
            return false;
        for (int i = 0; i < mValues.size(); i++)
        {
            if (!mValues.get(i).equals(other.get(i)))
                return false;
        }
        return true;
    }

    /// <summary>
    /// Determines whether the specified CSS values are equal to the current values.
    /// </summary>
    boolean equals(/*params*/ CssValue... values)
    {
        return equals(new CssValueList(values));
    }

    boolean equals(CssValue value)
    {
        return (getCount() == 1) && mValues.get(0).equals(value);
    }

    /// <summary>
    /// Appends a value to the list.
    /// </summary>
    /// <param name="value">CSS value.</param> 
    /// <returns>The index of the appended value.</returns>
    int add(CssValue value)
    {
        msArrayList.add(mValues, value);
        return mValues.size() - 1;
    }

    /// <summary>
    /// Removes the element at the specified index of the collection.
    /// </summary>
    void removeAt(int index)
    {
        mValues.remove(index);
    }

    void remove(CssValue value)
    {
        mValues.remove(value);
    }

    boolean contains(CssValue value)
    {
        for (int i = 0; i < getCount(); i++)
        {
            if (this.get(i).equals(value))
                return true;
        }
        return false;
    }

    void clear()
    {
        mValues.clear();
    }

    /// <summary>
    /// Creates a shallow copy of a range of items of this list. 
    /// </summary>
    /// <param name="index">The zero-based index at which the range starts.</param>
    /// <param name="count">The number of items in the range. Use -1 to copy all values up to the end of list.</param>
    CssValueList getRange(int index, int count)
    {
        if (count == -1)
        {
            count = getCount() - index;
        }
        CssValueList range = new CssValueList(count);
        for (int i = 0; i < count; i++)
        {
            range.add(this.get(index + i));
        }
        return range;
    }

    /// <summary>
    /// Gets a CSS representation of the values.
    /// </summary>
    String toCss()
    {
        StringBuilder sb = new StringBuilder();
        for (CssValue cssValue : mValues)
        {
            if ((sb.length() > 0) && !(cssValue instanceof CssCommaValue))
                sb.append(' ');
            cssValue.toCss(sb);
        }
        return sb.toString();
    }

    /// <summary>
    /// Returns the number of items in the list.
    /// </summary>
    int getCount() { return mValues.size(); }

    /// <summary>
    /// Gets or sets a CSS value at the specified index.
    /// </summary>
    /// <param name="index">The zero-based index at which the element is located in this list.</param>
    CssValue get(int index) { return mValues.get(index); }
    void set(int index, CssValue value) { mValues.set(index, value); }

    private /*final*/ ArrayList<CssValue> mValues;
}

