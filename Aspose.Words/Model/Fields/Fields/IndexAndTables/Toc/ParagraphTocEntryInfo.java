// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/06/2011 by Dmitry Matveenko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Stores data for a TOC entry.
/// </summary>
/// <remarks>
/// Extracted from TocEntryExtractor as it turned out that ParagraphTocEntry constructor uses all fields from here.
/// </remarks>
class ParagraphTocEntryInfo
{
    private ParagraphTocEntryInfo()
    {
    	this(1);
	
        mIsEmptyTocEntry = true;
    }

    ParagraphTocEntryInfo(int level)
    {
    	this(level, null, null);
	
    }

    ParagraphTocEntryInfo(int level, Node firstNode, Node lastNode)
    {
    	this(level, firstNode, lastNode, false);
	
    }

    ParagraphTocEntryInfo(int level, Node firstNode, Node lastNode, boolean isLinkedStyleTocEntry)
    {
        mLevel = level;
        mFirstChild = firstNode;
        mLastChild = lastNode;
        mIsLinkedStyleTocEntry = isLinkedStyleTocEntry;
    }

    int getLevel() { return mLevel; };

    private int mLevel;

    Node getFirstChild() { return mFirstChild; };

    private Node mFirstChild;

    Node getLastChild() { return mLastChild; };

    private Node mLastChild;

    boolean isEmptyTocEntry() { return mIsEmptyTocEntry; };

    private boolean mIsEmptyTocEntry;

    boolean isLinkedStyleTocEntry() { return mIsLinkedStyleTocEntry; };

    private boolean mIsLinkedStyleTocEntry;

    static /*final*/ ParagraphTocEntryInfo EMPTY_TOC_ENTRY_INFO_INSTANCE = new ParagraphTocEntryInfo();
}

