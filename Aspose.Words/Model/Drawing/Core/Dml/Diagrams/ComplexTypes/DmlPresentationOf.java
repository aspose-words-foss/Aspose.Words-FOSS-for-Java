// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.2.21 presOf (Presentation Of)
/// </summary>
class DmlPresentationOf extends DmlDiagramLayoutNodeContentItem
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitPresentationOf(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.PRESENTATION_OF; }

    DmlIteratorAttributes getIteratorAttributes() { return mIteratorAttributes; }

    private /*final*/ DmlIteratorAttributes mIteratorAttributes = new DmlIteratorAttributes();
}

