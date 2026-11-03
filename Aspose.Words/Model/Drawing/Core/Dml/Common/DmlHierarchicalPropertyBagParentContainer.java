// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class DmlHierarchicalPropertyBagParentContainer implements IDmlHierarchicalPropertyBagParentProvider, Cloneable
{
    DmlHierarchicalPropertyBagParentContainer(IDmlHierarchicalPropertyBag parentBag)
    {
        mParentBag = parentBag;
    }

    DmlHierarchicalPropertyBagParentContainer()
    {
    }

    public IDmlHierarchicalPropertyBag getParentBag() { return mParentBag; }
    public void setParentBag(IDmlHierarchicalPropertyBag value) { mParentBag = value; }

    public IDmlHierarchicalPropertyBagParentProvider deepClone()
    {
        DmlHierarchicalPropertyBagParentContainer lhs = (DmlHierarchicalPropertyBagParentContainer)memberwiseClone();
        if (mParentBag != null)
            lhs.mParentBag = mParentBag.deepClone();

        return lhs;
    }

    private IDmlHierarchicalPropertyBag mParentBag;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

