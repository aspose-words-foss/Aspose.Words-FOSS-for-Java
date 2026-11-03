// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.9 constrLst (Constraint List)
/// </summary>
class DmlConstraintList extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitConstraintList(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.CONSTRAINT_LIST; }

    ArrayList<DmlConstraint> getValues() { return mValues; }
    void setValues(ArrayList<DmlConstraint> value) { mValues = value; }

    public static DmlConstraintList getEmpty() { return mEmpty; };

    private static DmlConstraintList mEmpty;

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        DmlConstraintList copy = (DmlConstraintList)memberwiseClone();
        
        copy.setValues(new ArrayList<DmlConstraint>());
        for (DmlConstraint constraint : getValues())
            msArrayList.add(copy.getValues(), constraint);

        return copy;
    }

    private ArrayList<DmlConstraint> mValues;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    static/* DmlConstraintList()*/
    {
        mEmpty = new DmlConstraintList();
        getEmpty().setValues(new ArrayList<DmlConstraint>());
    }
}

