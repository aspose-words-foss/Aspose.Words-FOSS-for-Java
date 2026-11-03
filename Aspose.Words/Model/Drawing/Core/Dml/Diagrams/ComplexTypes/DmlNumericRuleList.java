// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.25 ruleLst (Rule List)
/// </summary>
class DmlNumericRuleList extends DmlDiagramLayoutNodeContentItem implements Cloneable
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitRuleList(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.NUMERIC_RULE_LIST; }

    ArrayList<DmlNumericRule> getValues() { return mValues; }
    void setValues(ArrayList<DmlNumericRule> value) { mValues = value; }
    
    public static DmlNumericRuleList getEmpty() { return mEmpty; };

    private static DmlNumericRuleList mEmpty;

    /*override*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        DmlNumericRuleList copy = (DmlNumericRuleList)memberwiseClone();

        copy.setValues(new ArrayList<DmlNumericRule>(getValues().size()));
        for (DmlNumericRule value : getValues())
            msArrayList.add(copy.getValues(), value.deepCopy());

        return copy;
    }

    private ArrayList<DmlNumericRule> mValues;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }

    static/* DmlNumericRuleList()*/
    {
        mEmpty = new DmlNumericRuleList();
        getEmpty().setValues(new ArrayList<DmlNumericRule>());
    }
}

