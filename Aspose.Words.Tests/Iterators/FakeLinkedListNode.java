// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


class FakeLinkedListNode implements ILinkedListNode
{
    public FakeLinkedListNode(String text)
    {
        mText = text;
        setIndex(Integer.MIN_VALUE);
    }

    public /*override*/ String toString()
    {
        return mText;
    }
    public ILinkedListNode getNextNode() { return mNextNode; }
    public void setNextNode(ILinkedListNode value) { mNextNode = value; }

    public ILinkedListNode getPrevNode() { return mPrevNode; }
    public void setPrevNode(ILinkedListNode value) { mPrevNode = value; }

    public int getIndex() { return mIndex; }
    public void setIndex(int value) { mIndex = value; }

    public long getSecondaryIndex() { return mSecondaryIndex; }
    public void setSecondaryIndex(long value) { mSecondaryIndex = value; }

    public boolean isNotIncluded() { return getIndex() == Integer.MIN_VALUE; }

    public void markAsRemoved()
    {
        setIndex(Integer.MIN_VALUE);
    }
    // override object.Equals
    public /*override*/ boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        return msString.equals(obj.toString(), this.toString());
    }

    // override object.GetHashCode
    public /*override*/ int hashCode()
    {
        return mText.hashCode();
    }

    private /*final*/ String mText;
    private ILinkedListNode mNextNode;
    private ILinkedListNode mPrevNode;
    private int mIndex;
    private long mSecondaryIndex;
}

