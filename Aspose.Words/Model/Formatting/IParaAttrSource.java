// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// For more info see <see cref="IRunAttrSource"/>.
/// </summary>
interface IParaAttrSource
{
    public Object getDirectParaAttr(int key);

    public Object getDirectParaAttr(int key, /*RevisionsView*/int revisionsView);

    public Object fetchInheritedParaAttr(int key);

    public Object fetchParaAttr(int key);
    
    public void setParaAttr(int key, Object value);

    public void removeParaAttr(int key);

    public void clearParaAttrs();
}

