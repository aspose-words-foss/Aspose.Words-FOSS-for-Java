// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/06/2012 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Container class for storing AltChunks upon reading.
/// We store reference paragraph, reference node, AltChunk document and AltChunk importFormatMode 
/// to insert them after reading destination document.
/// </summary>
class DocxAltChunk
{
    DocxAltChunk(CompositeNode referenceParagraph, Node referenceNode, Document altChunkDocument, /*ImportFormatMode*/int importFormatMode, boolean isInline)
    {
        mReferenceContainer = referenceParagraph;
        mReferenceNode = referenceNode;
        mAltChunkDocument = altChunkDocument;
        mImportFormatMode = importFormatMode;
        mIsInline = isInline;
    }

    CompositeNode getReferenceContainer() { return mReferenceContainer; }

    Node getReferenceNode() { return mReferenceNode; }

    Document getAltChunkDocument() { return mAltChunkDocument; }

    /*ImportFormatMode*/int getImportFormatMode() { return mImportFormatMode; }

    boolean isInline() { return mIsInline; }

    private /*final*/ CompositeNode mReferenceContainer;
    private /*final*/ Node mReferenceNode;
    private /*final*/ Document mAltChunkDocument;
    private /*final*/ /*ImportFormatMode*/int mImportFormatMode;
    private /*final*/ boolean mIsInline;
}

