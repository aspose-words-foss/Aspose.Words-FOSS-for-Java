// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/06/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.HashSetGeneric;


/// <summary>
/// Class for collecting all necessary information about cloned nodes in <see cref="DocumentMerger"/>.
/// </summary>
class DocMergerInfoCollector implements INodeCloningListener
{
    /// <summary>
    /// Ctor.
    /// </summary>
    DocMergerInfoCollector()
    {
        mCorePropSdts = new HashSetGeneric<StructuredDocumentTag>();
    }

    /// <summary>
    /// This method is invoked when the listened node is cloned.
    /// </summary>
    public void /*INodeCloningListener.*/notifyNodeCloned(Node source, Node clone)
    {
        switch (clone.getNodeType())
        {
            case NodeType.STRUCTURED_DOCUMENT_TAG:
            {
                StructuredDocumentTag sdt = (StructuredDocumentTag)clone;
                if ((sdt.getXmlMapping() != null) && sdt.getXmlMapping().isCoreProperties())
                    getCorePropSdts().add(sdt);
                break;
            }

            case NodeType.FOOTNOTE:
            {
                hasFootnotes(true);
                break;
            }

            case NodeType.FORM_FIELD:
            {
                hasFormFields(true);
                break;
            }
        }
    }

    /// <summary>
    /// The collection of cloned SDTs that are map to document core properties.
    /// </summary>
    HashSetGeneric<StructuredDocumentTag> getCorePropSdts() { return mCorePropSdts; };

    private HashSetGeneric<StructuredDocumentTag> mCorePropSdts;

    /// <summary>
    /// Gets a boolean value indicating, if source document being merged, has footnotes.
    /// </summary>
    boolean hasFootnotes() { return mHasFootnotes; }; private void hasFootnotes(boolean value) { mHasFootnotes = value; };

    private boolean mHasFootnotes;

    /// <summary>
    /// Gets a boolean value indicating, if source document being merged, has form fields.
    /// </summary>
    boolean hasFormFields() { return mHasFormFields; }; private void hasFormFields(boolean value) { mHasFormFields = value; };

    private boolean mHasFormFields;
}

