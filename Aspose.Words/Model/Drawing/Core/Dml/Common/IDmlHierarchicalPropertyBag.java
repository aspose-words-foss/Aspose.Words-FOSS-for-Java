// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


interface IDmlHierarchicalPropertyBag
{
    public Object getProperty(int key);
    public Object getDirectProperty(int key);
    public boolean isPropertySpecified(int key);
    public void setProperty(int key, Object value);
    public void remove(int key);

    /// <summary>
    /// Removes all items from the property bag.
    /// </summary>
    public void removeAll();

    public IDmlHierarchicalPropertyBagParentProvider getParentBagProvider(); public void setParentBagProvider(IDmlHierarchicalPropertyBagParentProvider value);
    public IDmlHierarchicalPropertyBag getExtensionProperties(); public void setExtensionProperties(IDmlHierarchicalPropertyBag value);
    public IDmlHierarchicalPropertyBag deepClone();

    /// <summary>
    /// Returns a flag indicating whether any property of this bag has value different than in the parent bag.
    /// </summary>
    /// <param name="attributesToIgnore">Properties/attributes that should be excluded from the checking.</param>
    /// <param name="parent">If defined, this parent is used in comparison instead of a bag of
    /// <see cref="ParentBagProvider"/>.</param>
    /// <returns>A flag indicating whether any property has non-default value.</returns>
    public boolean hasNonDefaultFormatting(int[] attributesToIgnore, IDmlHierarchicalPropertyBag parent);

    public int getCount();
}

