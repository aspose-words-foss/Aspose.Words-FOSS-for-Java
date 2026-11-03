// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.12 else (Else)
/// </summary>
class DmlOtherwise extends DmlExtensionListSource implements Cloneable
{
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    ArrayList<DmlDiagramLayoutNodeContentItem> getContent() { return mContent; }
    void setContent(ArrayList<DmlDiagramLayoutNodeContentItem> value) { mContent = value; }

    DmlOtherwise deepCopy()
    {
        DmlOtherwise copy = (DmlOtherwise)memberwiseClone();

        copy.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>(getContent().size()));
        for (DmlDiagramLayoutNodeContentItem item : getContent())
            msArrayList.add(copy.getContent(), item.deepCopy());

        return copy;
    }

    private String mName;
    private ArrayList<DmlDiagramLayoutNodeContentItem> mContent;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

