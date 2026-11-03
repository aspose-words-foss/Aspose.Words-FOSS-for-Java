// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for layout node content items.
/// </summary>
abstract class DmlDiagramLayoutNodeContentItem extends DmlExtensionListSource implements Cloneable
{
    abstract void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor);

    abstract /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType();

    /*virtual*/ DmlDiagramLayoutNodeContentItem deepCopy()
    {
        return (DmlDiagramLayoutNodeContentItem) memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

