// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/23/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringToObjDictionary;


/// <summary>
/// Base class for all elements which have extension list (CT_OfficeArtExtensionList - [ISO/IEC29500-1:2012] section A.4.1) 
/// in which all future extensions of element type ext is defined. The extension list, along
/// with corresponding future extensions, is used to extend the storage capabilities of the DrawingML 
/// framework. This enables various types of data to be stored natively in the framework.
/// </summary>
abstract class DmlExtensionListSource implements IDmlExtensionListSource
{
    public /*virtual*/ StringToObjDictionary<DmlExtension> getExtensions() { return mExtLst; }
    public /*virtual*/ void setExtensions(StringToObjDictionary<DmlExtension> value) { mExtLst = value; }

    /// <summary>
    /// Clones this extension collection.
    /// </summary>
    protected StringToObjDictionary<DmlExtension> cloneExtensions()
    {
        return cloneExtensions(mExtLst);
    }

    /// <summary>
    /// Clones this extension collection.
    /// </summary>
    static StringToObjDictionary<DmlExtension> cloneExtensions(StringToObjDictionary<DmlExtension> src)
    {
        if (src == null)
            return null;

        StringToObjDictionary<DmlExtension> lhsExtLst = new StringToObjDictionary<DmlExtension>();
        for (DmlExtension ext : src.getValues())
            lhsExtLst.set(ext.getUri(), ext.deepClone());

        return lhsExtLst;
    }

    boolean hasExtensions() { return mExtLst != null; }

    private StringToObjDictionary<DmlExtension> mExtLst;
}
