// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Special node that is used to make the owner document accessible while the parent of a node is null.
/// </summary>
class NullNode extends Node
{
    NullNode(DocumentBase doc)
    {
        //The parent of the NullNode is the document directly.
        setParent(doc);
    }

    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.NULL; }

    public /*override*/ String getText()
    {
        return "";
    }

    public /*override*/ boolean accept(DocumentVisitor visitor)
    {
        return true;
    }
}

