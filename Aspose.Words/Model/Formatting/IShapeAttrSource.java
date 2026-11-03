// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// For more info see <see cref="IRunAttrSource"/>.
/// </summary>
interface IShapeAttrSource
{
    public Object getDirectShapeAttr(int key);

    public Object fetchInheritedShapeAttr(int key);

    public Object fetchShapeAttr(int key);

    public void setShapeAttr(int key, Object value);

    public void removeShapeAttr(int key);
}

