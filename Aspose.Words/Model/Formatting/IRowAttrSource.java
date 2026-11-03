// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// For more info see <see cref="IRunAttrSource"/>.
/// </summary>
interface IRowAttrSource
{
    public Object getDirectRowAttr(int key);

    public Object fetchRowAttr(int key);

    public Object fetchInheritedRowAttr(int key);

    public void setRowAttr(int key, Object value);

    public void clearRowAttrs();

    /// <summary>
    /// Resets to default attributes values.
    /// </summary>
    /// <remarks>
    /// This is different from clearing the attributes as default attributes may have values.
    /// </remarks>
    public void resetToDefaultAttrs() throws Exception;
}

