// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/08/2021 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Internal node used during comparison only.
/// </summary>
class SdtMarkerEnd extends Node
{
    SdtMarkerEnd(DocumentBase doc, int id, /*MarkupLevel*/int level)
    {
    	super(doc);
	
        Id = id;
        Level = level;
    }

    /*final*/ /*MarkupLevel*/int Level;
    int Id;

    public /*override*/ /*NodeType*/int getNodeType() { return NodeType.SYSTEM; }

    public /*override*/ boolean accept(DocumentVisitor visitor)
    {
        return true;
    }
}

