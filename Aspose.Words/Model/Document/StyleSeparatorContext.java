// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2017 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class StyleSeparatorContext
{
    public Paragraph getStyleSeparatorParagraph() { return mStyleSeparatorParagraph; }
    public void setStyleSeparatorParagraph(Paragraph value) { mStyleSeparatorParagraph = value; }
    public Paragraph getLineBreakParagraph() { return mLineBreakParagraph; }
    public void setLineBreakParagraph(Paragraph value) { mLineBreakParagraph = value; }

    public CompositeNode getLineBreakParentNode() { return mLineBreakParentNode; }
    public void setLineBreakParentNode(CompositeNode value) { mLineBreakParentNode = value; }

    public Node getLineBreakNextSibling() { return mLineBreakNextSibling; }
    public void setLineBreakNextSibling(Node value) { mLineBreakNextSibling = value; }

    public Node getLineBreakPrevSibling() { return mLineBreakPrevSibling; }
    public void setLineBreakPrevSibling(Node value) { mLineBreakPrevSibling = value; }

    public boolean isRepeatedInsertExpected() { return mIsRepeatedInsertExpected; }
    public void isRepeatedInsertExpected(boolean value) { mIsRepeatedInsertExpected = value; }

    private Paragraph mStyleSeparatorParagraph;
    private Paragraph mLineBreakParagraph;
    private CompositeNode mLineBreakParentNode;
    private Node mLineBreakNextSibling;
    private Node mLineBreakPrevSibling;
    private boolean mIsRepeatedInsertExpected;
}

