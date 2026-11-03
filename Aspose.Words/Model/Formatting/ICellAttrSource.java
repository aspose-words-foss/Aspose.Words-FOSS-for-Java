// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// For more info see <see cref="IRunAttrSource"/>.
/// </summary>
interface ICellAttrSource
{
    public Object getDirectCellAttr(int key);

    public Object fetchCellAttr(int key);

    public Object fetchInheritedCellAttr(int key);

    public void setCellAttr(int key, Object value);

    public void clearCellAttrs();
}

