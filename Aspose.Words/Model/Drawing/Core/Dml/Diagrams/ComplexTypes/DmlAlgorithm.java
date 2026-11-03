// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.SortedStringListGeneric;


/// <summary>
/// 21.4.2.3 alg (Algorithm)
/// </summary>
class DmlAlgorithm extends DmlDiagramLayoutNodeContentItem
{
    /*override*/ void accept(IDmlDiagramLayoutNodeContentItemVisitor visitor)
    {
        visitor.visitAlgorithm(this);
    }

    /*override*/ /*DmlDiagramLayoutNodeContentItemType*/int getContentItemType() { return DmlDiagramLayoutNodeContentItemType.ALGORITHM; }

    /*DmlAlgorithmType*/int getType() { return mType; }
    void setType(/*DmlAlgorithmType*/int value) { mType = value; }

    int getRevision() { return mRevision; }
    void setRevision(int value) { mRevision = value; }

    SortedStringListGeneric<String> getParams() { return mParams; }

    private /*DmlAlgorithmType*/int mType;
    private int mRevision;
    private /*final*/ SortedStringListGeneric<String> mParams = new SortedStringListGeneric<String>();
}

