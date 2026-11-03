// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.14 forEach (For Each)
/// </summary>
class DmlForEach extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitForEach(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.FOR_EACH; }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    String getReference() { return mReference; }
    void setReference(String value) { mReference = value; }

    DmlIteratorAttributes getIteratorAttributes() { return mIteratorAttributes; }

    ArrayList<DmlDiagramLayoutNodeContentItem> getContent() { return mContent; }
    void setContent(ArrayList<DmlDiagramLayoutNodeContentItem> value) { mContent = value; }

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        DmlForEach copy = (DmlForEach)memberwiseClone();

        copy.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>(getContent().size()));
        for (DmlDiagramLayoutNodeContentItem item : getContent())
            msArrayList.add(copy.getContent(), item.deepCopy());
        
        return copy;
    }

    private String mName;
    private String mReference;
    private /*final*/ DmlIteratorAttributes mIteratorAttributes = new DmlIteratorAttributes();
    private ArrayList<DmlDiagramLayoutNodeContentItem> mContent;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

