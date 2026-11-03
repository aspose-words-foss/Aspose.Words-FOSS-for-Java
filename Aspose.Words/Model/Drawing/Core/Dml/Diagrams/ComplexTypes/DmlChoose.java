// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.6 choose (Choose Element)
/// </summary>
class DmlChoose extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitChoose(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.CHOOSE; }

    String getName() { return mName; }
    void setName(String value) { mName = value; }

    ArrayList<DmlWhen> getIf() { return mIf; }
    void setIf(ArrayList<DmlWhen> value) { mIf = value; }

    DmlOtherwise getElse() { return mElse; }
    void setElse(DmlOtherwise value) { mElse = value; }

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        DmlChoose copy = (DmlChoose)memberwiseClone();

        copy.setIf(new ArrayList<DmlWhen>(getIf().size()));
        for (DmlWhen dmlWhen : getIf())
            msArrayList.add(copy.getIf(), dmlWhen.deepCopy());

        if (getElse() != null)
            copy.setElse(getElse().deepCopy());

        return copy;
    }

    private String mName;
    private ArrayList<DmlWhen> mIf;
    private DmlOtherwise mElse;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

