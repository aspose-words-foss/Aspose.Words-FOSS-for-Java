// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2021 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Internal node used during comparison only.
/// </summary>
/// <remarks>
/// AM. According to test Word compares SDT by content.
/// Currently it was implemented using system marker nodes bounding SDT.
/// Actually these marker classes are light-weight version of public StructuredDocumentRangeStart node.
/// Main reason do not use existing StructuredDocumentTagRangeStart/End nodes is location limitation,
/// they can be only immediate child of Body.
/// </remarks>
class SdtMarkerStart extends Node implements ITrackableNode
{
    SdtMarkerStart(DocumentBase doc, StructuredDocumentTag sdt)
    {
    	super(doc);
	
        mInternalSdt = sdt;
    }

    StructuredDocumentTag getInternalSdt()
    {
        // Ensure that encapsulated node belongs to the same document as we can get another document
        // after some manipulation, for example, node cloned and appended to another document.
        // We need to consider making full implementation later instead of SDT encapsulation.
        if(mInternalSdt != null)
            mInternalSdt.setDocument(getDocument());

        return mInternalSdt;
    }

    void setInternalSdt(StructuredDocumentTag value)
    {
        mInternalSdt = value;
    }

    /*override*/ Node deepClone(boolean isCloneChildren, INodeCloningListener cloningListener)
    {
        SdtMarkerStart lhs = (SdtMarkerStart)super.deepClone(isCloneChildren, cloningListener);
        lhs.setInternalSdt((StructuredDocumentTag)getInternalSdt().deepClone(isCloneChildren, cloningListener));

        // AM. When document is cloned we should try to preserve original Ids.
        // Otherwise range link between range start/end will be lost.
        // I don't like this, maybe it's better to handle link translation in Document.Clone()? Postpone for a while.
        lhs.getInternalSdt().setIdExplicitly(getInternalSdt().getId());

        return lhs;
    }

    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SYSTEM; }
    public /*override*/ boolean accept(DocumentVisitor visitor)
    {
        return true;
    }

    public MoveRevision /*IMoveTrackableNode.*/getMoveFromRevision()
    {
        throw new UnsupportedOperationException(SHOULD_NOT_BE_CALLED_MESSAGE);
    }
    public void /*IMoveTrackableNode.*/setMoveFromRevision(MoveRevision value)
    {
        throw new UnsupportedOperationException(SHOULD_NOT_BE_CALLED_MESSAGE);
    }

    public MoveRevision /*IMoveTrackableNode.*/getMoveToRevision()
    {
        throw new UnsupportedOperationException(SHOULD_NOT_BE_CALLED_MESSAGE);
    }
    public void /*IMoveTrackableNode.*/setMoveToRevision(MoveRevision value)
    {
        throw new UnsupportedOperationException(SHOULD_NOT_BE_CALLED_MESSAGE);
    }

    public void /*IMoveTrackableNode.*/removeMoveRevisions()
    {
        throw new UnsupportedOperationException(SHOULD_NOT_BE_CALLED_MESSAGE);
    }

    public EditRevision /*ITrackableNode.*/getInsertRevision() { return mInsertRevision; }
    public void /*ITrackableNode.*/setInsertRevision(EditRevision value) { mInsertRevision = value; }

    public EditRevision /*ITrackableNode.*/getDeleteRevision() { return mDeleteRevision; }
    public void /*ITrackableNode.*/setDeleteRevision(EditRevision value) { mDeleteRevision = value; }

    private StructuredDocumentTag mInternalSdt;
    private EditRevision mInsertRevision;
    private EditRevision mDeleteRevision;

    private static final String SHOULD_NOT_BE_CALLED_MESSAGE = "Should not be called.";
}

