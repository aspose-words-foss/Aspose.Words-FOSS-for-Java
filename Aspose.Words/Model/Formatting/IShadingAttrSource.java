// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Objects that have a shading implement this interface so the Shading class
/// can do the work of exposing the shading to the public.
/// </summary>
interface IShadingAttrSource
{
    /// <summary>
    /// Gets (throws if not found) the shading attribute specified on the parent of the object.
    /// </summary>
    /// <param name="key">The attribute id.</param>
    /// <returns>The Shading object.</returns>
    public Object fetchInheritedShadingAttr(int key);
}

