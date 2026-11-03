// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.3.2 cxn (Connection)
/// </summary>
class DmlDiagramConnection
{
    DmlModelId getModelId() { return mModelId; }
    void setModelId(DmlModelId value) { mModelId = value; }

    /*DmlConnectionType*/int getType() { return mType; }
    void setType(/*DmlConnectionType*/int value) { mType = value; }

    DmlModelId getDestinationId() { return mDestinationId; }
    void setDestinationId(DmlModelId value) { mDestinationId = value; }

    int getDestinationOrder() { return mDestinationOrder; }
    void setDestinationOrder(int value) { mDestinationOrder = value; }

    DmlModelId getSourceId() { return mSourceId; }
    void setSourceId(DmlModelId value) { mSourceId = value; }

    int getSourceOrder() { return mSourceOrder; }
    void setSourceOrder(int value) { mSourceOrder = value; }

    DmlModelId getParentTransitionId() { return mParentTransitionId; }
    void setParentTransitionId(DmlModelId value) { mParentTransitionId = value; }

    DmlModelId getSiblingTransitionId() { return mSiblingTransitionId; }
    void setSiblingTransitionId(DmlModelId value) { mSiblingTransitionId = value; }

    String getPresentationId() { return mPresentationId; }
    void setPresentationId(String value) { mPresentationId = value; }

    private DmlModelId mModelId;
    private /*DmlConnectionType*/int mType = DmlConnectionType.PARENT_OF;
    private DmlModelId mDestinationId;
    private int mDestinationOrder;
    private DmlModelId mSourceId;
    private int mSourceOrder;
    private DmlModelId mParentTransitionId;
    private DmlModelId mSiblingTransitionId;
    private String mPresentationId;
}

