// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.19 layoutNode (Layout Node)
/// </summary>
class DmlDiagramLayoutNode extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitLayoutNode(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.LAYOUT_NODE; }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    String getStyleLabel() { return mStyleLabel; }
    void setStyleLabel(String value) { mStyleLabel = value; }

    /*DmlChildOrder*/int getChildOrder() { return mChildOrder; }
    void setChildOrder(/*DmlChildOrder*/int value) { mChildOrder = value; }

    String getMoveWith() { return mMoveWith; }
    void setMoveWith(String value) { mMoveWith = value; }

    ArrayList<DmlDiagramLayoutNodeContentItem> getContent() { return mContent; }
    void setContent(ArrayList<DmlDiagramLayoutNodeContentItem> value) { mContent = value; }

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        DmlDiagramLayoutNode copy = (DmlDiagramLayoutNode)memberwiseClone();
        
        copy.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>(getContent().size()));
        for (DmlDiagramLayoutNodeContentItem item : getContent())
            msArrayList.add(copy.getContent(), item.deepCopy());
        
        return copy;
    }

    private String mName;
    private String mStyleLabel;
    private /*DmlChildOrder*/int mChildOrder = DmlChildOrder.BOTTOM;
    private String mMoveWith;
    private ArrayList<DmlDiagramLayoutNodeContentItem> mContent;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

