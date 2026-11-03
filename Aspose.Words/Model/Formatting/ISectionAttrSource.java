// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// For more info see <see cref="IRunAttrSource"/>.
/// </summary>
interface ISectionAttrSource
{
    public Object getDirectSectionAttr(int key);

    public Object getDirectSectionAttr(int key, /*RevisionsView*/int revisionsView);

    public Object fetchInheritedSectionAttr(int key);

    public Object fetchSectionAttr(int key);

    public Object fetchSectionAttr(int key, /*RevisionsView*/int revisionsView);

    public void setSectionAttr(int key, Object value);

    public void setSectionAttr(int key, Object value, /*RevisionsView*/int revisionsView);

    public void clearSectionAttrs();
}

