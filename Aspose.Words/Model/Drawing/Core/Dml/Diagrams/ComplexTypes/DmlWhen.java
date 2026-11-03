// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// 21.4.2.15 if (If)
/// </summary>
class DmlWhen extends DmlExtensionListSource implements Cloneable
{
    String getName() { return mName; }
    void setName(String value) { mName = value; }

    DmlIteratorAttributes getIteratorAttributes() { return mIteratorAttributes; }

    /*DmlFunctionType*/int getFunction() { return mFunction; }
    void setFunction(/*DmlFunctionType*/int value) { mFunction = value; }

    /*DmlFunctionOperator*/int getOperator() { return mOperator; }
    void setOperator(/*DmlFunctionOperator*/int value) { mOperator = value; }

    /*DmlVariableType*/int getArgument() { return mArgument; }
    void setArgument(/*DmlVariableType*/int value) { mArgument = value; }

    String getValue() { return mValue; }
    void setValue(String value) { mValue = value; }

    ArrayList<DmlDiagramLayoutNodeContentItem> getContent() { return mContent; }
    void setContent(ArrayList<DmlDiagramLayoutNodeContentItem> value) { mContent = value; }

    DmlWhen deepCopy()
    {
        DmlWhen copy = (DmlWhen)memberwiseClone();
        
        copy.setContent(new ArrayList<DmlDiagramLayoutNodeContentItem>(getContent().size()));
        for (DmlDiagramLayoutNodeContentItem item : getContent())
        {
            msArrayList.add(copy.getContent(), item.deepCopy());
        }

        return copy;
    }

    private String mName;
    private /*final*/ DmlIteratorAttributes mIteratorAttributes = new DmlIteratorAttributes();
    private /*DmlFunctionType*/int mFunction;
    private /*DmlFunctionOperator*/int mOperator;
    private /*DmlVariableType*/int mArgument = DmlVariableType.NONE;
    private String mValue; 
    private ArrayList<DmlDiagramLayoutNodeContentItem> mContent;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

