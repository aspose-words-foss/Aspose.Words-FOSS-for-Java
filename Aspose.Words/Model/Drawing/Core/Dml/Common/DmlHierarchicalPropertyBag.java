// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IntToObjDictionary;


class DmlHierarchicalPropertyBag implements IDmlHierarchicalPropertyBag, Cloneable
{
    public Object getProperty(int key)
    {
        // Properties specified in extension overrides properties specified on the base collection.
        Object value = null;
        if (mExtensionProperties != null)
            value = mExtensionProperties.getDirectProperty(key);

        if (value == null)
        {
            value = mProperties.get(key);
            if (value == null && getParentBagProvider() != null)
            {
                IDmlHierarchicalPropertyBag parentBag = getParentBagProvider().getParentBag();
                if (parentBag != null)
                    return parentBag.getProperty(key);
            }
        }

        return value;
    }

    public void remove(int key)
    {
        mProperties.remove(key);
    }

    /// <summary>
    /// Removes all items from this property collection.
    /// </summary>
    public void removeAll()
    {
        mProperties.clear();
    }

    public Object getDirectProperty(int key)
    {
        return mProperties.get(key);
    }
    
    /// <summary>
    /// Determines whether the mProperties contains the specified property.
    /// </summary>
    /// <param name="key">The property key</param>
    /// <returns>"True", if the property is set directly, "false" otherwise</returns>
    public boolean isPropertySpecified(int key)
    {
        return mProperties.containsKey(key);
    }

    /// <summary>
    /// Sets the specified property. If the property value is null, removes the property.
    /// </summary>
    /// <param name="key">The property key</param>
    /// <param name="value">The property value</param>
    public void setProperty(int key, Object value)
    {
        if (value != null)
        {
            mProperties.set(key, value);
        }
        else
        {
            if (mProperties.containsKey(key))
                remove(key);
        }
    }

    public IDmlHierarchicalPropertyBag deepClone()
    {
        DmlHierarchicalPropertyBag clone = (DmlHierarchicalPropertyBag)memberwiseClone();
        clone.mProperties = new IntToObjDictionary<Object>(mProperties);
        if (mParentBagProvider != null)
            clone.mParentBagProvider = mParentBagProvider.deepClone();
        return clone;
    }

    /// <summary>
    /// Returns a flag indicating whether any property of this bag has value different than in the parent bag.
    /// </summary>
    /// <param name="attributesToIgnore">Properties/attributes that should be excluded from the checking.</param>
    /// <param name="parent">If defined, this parent is used in comparison instead a bag of
    /// <see cref="ParentBagProvider"/>.</param>
    /// <returns>A flag indicating whether any property has non-default value.</returns>
    public boolean hasNonDefaultFormatting(int[] attributesToIgnore, IDmlHierarchicalPropertyBag parent)
    {
        if ((getCount() == 0) &&
            ((mExtensionProperties == null) || (mExtensionProperties.getCount() == 0)))
            return false;

        if ((parent == null) &&
            ((getParentBagProvider() == null) || (getParentBagProvider().getParentBag() == null)))
            return true;

        IDmlHierarchicalPropertyBag parentBag = (parent != null) ? parent : getParentBagProvider().getParentBag();

        IntToObjDictionary<Object>.Enumerator enumerator = mProperties.iterator();
        while (enumerator.moveNext())
        {
            if (containsAttribute(attributesToIgnore, enumerator.getCurrentKey()))
                continue;

            Object value = getProperty(enumerator.getCurrentKey());
            if (value == null)
                continue;

            Object parentValue = parentBag.getProperty(enumerator.getCurrentKey());
            if (!value.equals(parentValue))
                return true;
        }

        return false;
    }

    /// <summary>
    /// Returns <c>true</c> if the specified array contains the specified attribute.
    /// </summary>
    private static boolean containsAttribute(int[] attributes, int attribute)
    {
        if (attributes == null)
            return false;

        for (int attr : attributes)
        {
            if (attr == attribute)
                return true;
        }

        return false;
    }

    public IDmlHierarchicalPropertyBagParentProvider getParentBagProvider() { return mParentBagProvider; }
    public void setParentBagProvider(IDmlHierarchicalPropertyBagParentProvider value) { mParentBagProvider = value; }

    public IDmlHierarchicalPropertyBag getExtensionProperties() { return mExtensionProperties; }
    public void setExtensionProperties(IDmlHierarchicalPropertyBag value) { mExtensionProperties = value; }

    public int getCount() { return mProperties.getCount(); }

    private IDmlHierarchicalPropertyBagParentProvider mParentBagProvider;
    private IDmlHierarchicalPropertyBag mExtensionProperties;

    private IntToObjDictionary<Object> mProperties = new IntToObjDictionary<Object>();

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

