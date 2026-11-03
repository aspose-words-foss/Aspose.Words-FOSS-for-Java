// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/10/2020 by Tengiz Sharafiev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*static*/ class AttrCollectionExtension
{
	/* Simulation of static class by using private constructor */
	private AttrCollectionExtension()
	{}

    /// <summary>
    /// Creates a deep copy of the collection.
    ///
    /// Attributes that implement the IAttr interface are deep copied,
    /// but value types are value copied.
    /// </summary>
    static <TAttrCollection extends AttrCollection> TAttrCollection deepClone(/*this*/ TAttrCollection attr)
    {
        return (TAttrCollection)attr.cloneCore();
    }

    /// <summary>
    /// Sets attribute value.
    /// </summary>
    static <TAttrCollection extends AttrCollection> TAttrCollection withKeyValue(/*this*/ TAttrCollection attr, int key, Object value)
    {
        attr.set(key, value);
        return attr;
    }
}

