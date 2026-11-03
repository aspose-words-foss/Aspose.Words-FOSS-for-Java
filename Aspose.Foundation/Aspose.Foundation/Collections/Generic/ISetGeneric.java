// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2017 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A set is a collection that contains no duplicate elements, and whose elements are in no particular order.
/// 
/// The interface is a replacement of ISet interface class provided by in .NET 4 and higher. So its methods are created
/// compatible with this class. For now, not all ISet operations are declared, check of .NET ISet interface when adding
/// new declarations to this interface.
/// </summary>
public interface ISetGeneric<T> extends Iterable<T>
{
    /// <summary>
    /// Adds the specified element to a set object.
    /// Returns <c>true</c> if the element is added or <c>false</c> if the element is already present.
    /// </summary>
    public boolean add(T item);

    /// <summary>
    /// Removes all elements from a set object.
    /// </summary>
    public void clear();

    /// <summary>
    /// Determines whether a set contains the specified element.
    /// </summary>
    public boolean contains(T item);

    /// <summary>
    /// Removes the specified element from a set object.
    /// Returns <c>true</c> if the element is successfully found and removed; otherwise, <c>false</c>.
    /// </summary>
    public boolean remove(T item);

    /// <summary>
    /// Gets the number of elements contained in a set object.
    /// </summary>
    public int getCount();
}

