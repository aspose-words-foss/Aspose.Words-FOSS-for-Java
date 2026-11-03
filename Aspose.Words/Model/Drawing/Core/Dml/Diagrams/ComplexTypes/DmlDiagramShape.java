// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// 21.4.2.27 shape (Shape)
/// </summary>
class DmlDiagramShape extends DmlDiagramLayoutNodeContentItem
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitShape(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.SHAPE; }

    DmlAngle getRotation() { return mRotation; }
    void setRotation(DmlAngle value) { mRotation = value; }

    String getType() { return mType; }
    void setType(String value) { mType = value; }

    String getBlipReference() { return mBlipReference; }
    void setBlipReference(String value) { mBlipReference = value; }

    byte[] getBlipData() { return mBlipData; }
    void setBlipData(byte[] value) { mBlipData = value; }

    int getZOrder() { return mZOrder; }
    void setZOrder(int value) { mZOrder = value; }

    boolean getHideGeometry() { return mHideGeometry; }
    void setHideGeometry(boolean value) { mHideGeometry = value; }

    boolean getPreventTextEditing() { return mPreventTextEditing; }
    void setPreventTextEditing(boolean value) { mPreventTextEditing = value; }

    boolean getImagePlaceholder() { return mImagePlaceholder; }
    void setImagePlaceholder(boolean value) { mImagePlaceholder = value; }

    DmlShapeAdjust[] getAdjustList() { return mAdjustList; }
    void setAdjustList(DmlShapeAdjust[] value) { mAdjustList = value; }

    private DmlAngle mRotation;
    private String mType;
    private int mZOrder;
    private boolean mHideGeometry;
    private boolean mPreventTextEditing;
    private boolean mImagePlaceholder;
    private DmlShapeAdjust[] mAdjustList;
    private String mBlipReference;
    private byte[] mBlipData;
}

