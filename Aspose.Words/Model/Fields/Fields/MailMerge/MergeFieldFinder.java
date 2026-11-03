// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/06/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Optimized for quickly finding a merge field by name.
/// At the moment finds only MERGEFIELD fields, should probably be
/// improved to find MACROBUTTON fields used as merge fields too?
/// </summary>
class MergeFieldFinder extends DocumentVisitor
{
    /// <summary>
    /// Helper function to find a merge field in the document. Returns the bookmark or null.
    /// </summary>
    static FieldMergeField findMergeField(Node node, String fieldName) throws Exception
    {
        MergeFieldFinder finder = new MergeFieldFinder();
        finder.mFieldName = fieldName;
        node.accept(finder);
        return finder.mField;
    }

    /// <summary>
    /// Helper function to find a merge field in the document (search starts at the current node.
    /// If field not found - we're looking for it in the entire document).
    /// </summary>
    /// <returns>
    /// Returns the bookmark or null.
    /// </returns>
    static FieldMergeField findMergeFieldFromNode(Node currentNode, String fieldName) throws Exception
    {
        MergeFieldFinder finder = new MergeFieldFinder();
        finder.mFieldName = fieldName;
        finder.findMergeFieldFromNode(currentNode);

        if (finder.mField != null)
            return finder.mField;

        // If currentNode is Document and the field is missing from the document, we should prevent double search.
        if (currentNode instanceof Document)
            return null;

        // If field not found - we're looking for it from document start.
        // TODO: we should prevent double search in the end of document
        currentNode.getDocument().accept(finder);

        return finder.mField;
    }

    private boolean findMergeFieldFromNode(Node currentNode) throws Exception
    {
        Node child = currentNode;
        while (child != null)
        {
            if (!child.accept(this))
                return false;

            Node nextChild = child.getNextSibling();
            child = nextChild;
        }

        if (currentNode.getParentNode() == null || currentNode.getParentNode().getNextSibling() == null)
            return false;

        return findMergeFieldFromNode(currentNode.getParentNode().getNextSibling());
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        // Find fields from nodes without delete revision only.
        if (!fieldStart.isVisitorAcceptable(this))
            return VisitorAction.CONTINUE;

        mIsInMergeField = (fieldStart.getFieldType() == FieldType.FIELD_MERGE_FIELD);
        if (mIsInMergeField)
            mFieldStart = fieldStart;
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        // Find fields from nodes without delete revision only.
        if (!fieldSeparator.isVisitorAcceptable(this))
            return VisitorAction.CONTINUE;

        if (mIsInMergeField)
            mFieldSeparator = fieldSeparator;
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        // Find fields from nodes without delete revision only.
        if (!fieldEnd.isVisitorAcceptable(this))
            return VisitorAction.CONTINUE;

        if (mIsInMergeField)
        {
            FieldMergeField field = (FieldMergeField)FieldFactory.createField(mFieldStart, mFieldSeparator, fieldEnd);
            if (StringUtil.equalsIgnoreCase(field.getFieldNameNoPrefix(), mFieldName))
            {
                //Set the result and terminate further search.
                mField = field;
                return VisitorAction.STOP;
            }
        }
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Informs the acceptor node if deleted nodes should be given to the visitor.
    /// </summary>
    /// <remarks>
    /// MergeFieldFinder does not visit deleted nodes.
    /// </remarks>
    /*override*/ boolean getVisitsDeletedNodes() { return false; }

    /// <summary>
    /// Looking for this field name.
    /// </summary>
    private String mFieldName;
    private boolean mIsInMergeField;
    private FieldStart mFieldStart;
    private FieldSeparator mFieldSeparator;
    /// <summary>
    /// Resulting field that was found.
    /// </summary>
    private FieldMergeField mField;
}

