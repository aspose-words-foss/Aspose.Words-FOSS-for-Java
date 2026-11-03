// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Objects that have one or more borders implement this interface so
/// the Border and Borders classes can do the work of exposing the border to the public.
///
/// It actually looks that GetDirectAttr and SetAttr are used by Borders only while
/// FetchInheritedAttr is used by Border only - so it could be split into two different interfaces.
/// </summary>
interface IBorderAttrSource
{
    /// <summary>
    /// Gets the specified border attribute specified directly on the object.
    /// </summary>
    /// <param name="key">The attribute id.</param>
    /// <returns>The Border object or null.</returns>
    public Object getDirectBorderAttr(int key);

    /// <summary>
    /// Gets (throws if not found) the border attribute specified on the parent of the object.
    /// </summary>
    /// <param name="key">The attribute id.</param>
    /// <returns>The Border object.</returns>
    public Object fetchInheritedBorderAttr(int key);

    /// <summary>
    /// Stores the border attribute in the object.
    /// </summary>
    public void setBorderAttr(int key, Object value) throws Exception;

    /// <summary>
    /// Map of border types to border attribute keys.
    /// </summary>
    public SortedList<Integer, Integer> getPossibleBorderKeys();
}

