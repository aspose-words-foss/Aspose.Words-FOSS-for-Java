// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2016 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for internable complex attribute.
/// Internable complex attribute should notify parent collection when going to be changed.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/aspose-words-document-object-model/">Aspose.Words Document Object Model (DOM)</a> documentation article.</para>
/// </summary>
public abstract class InternableComplexAttr
{
    /// <summary>
    /// Notifies parent attribute collection that attribute is going to be changed.
    /// </summary>
    protected void notifyChanging()
    {
        if (mPr != null)
        {
            mPr.beforeChange();
            detach();
        }
    }

    /// <summary>
    /// Attaches complex value to its collection.
    /// </summary>
    void attach(AttrCollection pr)
    {
        mPr = pr;
    }

    /// <summary>
    /// Detaches complex value from attribute collection.
    /// </summary>
    void detach()
    {
        mPr = null;
    }

    /// <summary>
    /// Indicates that complex value is attached to attribute collection.
    /// </summary>
    boolean isAttached() { return mPr != null; }

    private AttrCollection mPr;
}

