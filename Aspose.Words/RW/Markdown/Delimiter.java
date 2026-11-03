// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;


/// <summary>
/// Represents a markdown delimiter of inline blocks.
/// </summary>
/// <remarks>
/// The delimiters are represented with the sequence of one of the characters, like: '*', '_', '`', '~' and so on.
/// See also the algorithm for parsing nested emphasis and links at https://spec.commonmark.org
/// </remarks>
abstract class Delimiter implements ILinkedListNode, Cloneable
{
    /// <summary>
    /// Creates Delimiter object from a specified DelimiterRun.
    /// </summary>
    protected Delimiter(DelimiterRun delimiterRun, int offset, int maxLength)
    {
        Debug.assertTrue(delimiterRun != null);

        setStart(delimiterRun.getStart() + offset);
        setEnd((getStart() + calculateLength(delimiterRun, offset, maxLength)) - 1);

        mText = delimiterRun.getText();
        mFlankingType = MarkdownUtil.getFlankingType(getPrevChar(), getNextChar());
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    abstract Block toBlock();

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified delimiter.
    /// </summary>
    abstract boolean canBeOpeningFor(Delimiter other);

    /// <summary>
    /// Gets a delimiter that can be an Opening for this delimiter.
    /// </summary>
    /*virtual*/ Delimiter getPotentialOpeningBackward()
    {
        Delimiter curDelimiter = (Delimiter)getPrevNode();
        while (curDelimiter != null)
        {
            if (!isNotIncluded())
            {
                if (curDelimiter.isLinked())
                {
                    // Jump over linked delimiters with the same or greater priority.
                    if ((curDelimiter.isClosing()) && (getPriority() <= curDelimiter.getPriority()))
                        curDelimiter = curDelimiter.getLinkedDelimiter();
                }
                else
                {
                    if (curDelimiter.canBeOpeningFor(this))
                        return curDelimiter;
                }
            }

            curDelimiter = (Delimiter)curDelimiter.getPrevNode();
        }

        return null;
    }

    /// <summary>
    /// Gets a delimiter that can be a Closing, starting from a specified delimiter forward.
    /// </summary>
    Delimiter getPotentialClosingForward()
    {
        if (canBeClosing())
            return this;

        Delimiter curDelimiter = (Delimiter)getNextNode();
        while (curDelimiter != null)
        {
            if (curDelimiter.canBeClosing())
                return curDelimiter;
            curDelimiter = (Delimiter)curDelimiter.getNextNode();
        }

        return null;
    }

    /// <summary>
    /// Links two delimiters, so that one of them becomes Opening and another one becomes Closing.
    /// </summary>
    /// <remarks>
    /// After two delimiters are linked, their lengths become equal. So, if the delimiters do not have equal
    /// lengths, then those of them which is longer will be truncated.
    /// </remarks>
    /// <returns>
    /// Remained part of the longer delimiter, or <c>null</c>, if the delimiters have equal lengths.
    /// </returns>
    Delimiter link(Delimiter other)
    {
        // There can be a situation when we try to link delimiters and one of them is linked already.
        // The case is possible for FootnoteDelimiter, for example, as it has the same closing type, as LinkTextOpening.
        // In this case we first need to unlink such delimiters.
        if (isLinked())
        {
            Debug.assertTrue(getLinkedDelimiter().getPriority() <= other.getPriority());
            unLink();
        }
        if (other.isLinked())
        {
            Debug.assertTrue(other.getLinkedDelimiter().getPriority() <= getPriority());
            other.unLink();
        }

        setLinkedDelimiter(other);
        other.setLinkedDelimiter(this);

        int minLength = Math.min(getLength(), other.getLength());

        Delimiter remainedDelimiter = cut(minLength);
        if (remainedDelimiter != null)
            return remainedDelimiter;

        remainedDelimiter = other.cut(minLength);
        if (remainedDelimiter != null)
            return remainedDelimiter;

        return null;
    }

    /// <summary>
    /// Unlinks delimiter, so that this delimiter and its linked one become nor Opening neither Closing.
    /// </summary>
    void unLink()
    {
        Debug.assertTrue(isLinked());

        Delimiter opening = (isOpening()) ? this : getLinkedDelimiter();
        Delimiter closing = (isOpening()) ? getLinkedDelimiter() : this;

        if (opening.getPrevNode() != null)
            opening.merge((Delimiter)opening.getPrevNode());

        if (closing.getNextNode() != null)
            closing.merge((Delimiter)closing.getNextNode());

        opening.setLinkedDelimiter(null);
        closing.setLinkedDelimiter(null);
    }

    /// <summary>
    /// Returns true, if this delimiter is located before a specified delimiter in a text.
    /// </summary>
    boolean isBefore(Delimiter delimiter)
    {
        return (getEnd() < delimiter.getStart());
    }

    /// <summary>
    /// Splits linked delimiter in accordance with maximum allowed <see cref="MaxLinkedLength"/> value.
    /// </summary>
    void split(LinkedList delimiters)
    {
        if (!isLinked())
            return;

        Delimiter opening = (isOpening()) ? this : getLinkedDelimiter();
        Delimiter closing = (isOpening()) ? getLinkedDelimiter() : this;

        while (opening.isLinked() && (opening.getLength() > getMaxLinkedLength()))
        {
            // Split opening delimiter.
            delimiters.moveObjectSafe(opening);
            opening = opening.cut(getMaxLinkedLength());
            delimiters.insert(opening);

            // Split closing delimiter.
            delimiters.moveObjectSafe(closing);
            closing = closing.cut(getMaxLinkedLength());
            delimiters.append(closing);

            // Link remained delimiters together.
            if (opening.canBeOpeningFor(closing))
            {
                opening.link(closing);
            }
            else
            {
                if (opening.getPrevNode() != null)
                    opening.merge((Delimiter)opening.getPrevNode());

                if (closing.getNextNode() != null)
                    closing.merge((Delimiter)closing.getNextNode());
            }

            delimiters.moveObjectSafe(closing);
        }
    }

    /// <summary>
    /// Gets corresponding LinkText closing delimiter.
    /// </summary>
    Delimiter getLinkTextClosing()
    {
        if (getPrevNode() == null)
            return null;

        Delimiter linkTextClosing = (Delimiter)getPrevNode();
        if (linkTextClosing.getType() != DelimiterType.LINK_TEXT_CLOSING)
            return null;

        return ((linkTextClosing.getEnd() + 1) == getStart()) ? linkTextClosing : null;
    }

    /// <summary>
    /// Cuts this delimiter to a specified length.
    /// </summary>
    /// <returns>Remained part of the delimiter.</returns>
    private Delimiter cut(int newLength)
    {
        if (newLength >= getLength())
            return null;

        Debug.assertTrue(getLinkedDelimiter() != null);

        Delimiter remainedDelimiter = deepClone();

        if (isOpening())
        {
            remainedDelimiter.setEnd(remainedDelimiter.getEnd() - newLength);
            setStart(remainedDelimiter.getEnd() + 1);
        }
        else
        {
            remainedDelimiter.setStart(remainedDelimiter.getStart() + newLength);
            setEnd(remainedDelimiter.getStart() - 1);
        }

        return remainedDelimiter;
    }

    /// <summary>
    /// Merges this delimiter with a specified one.
    /// </summary>
    private void merge(Delimiter other)
    {
        Debug.assertTrue(other != null);

        // It is allowed to merge only unlinked sibling delimiters.
        if (isLinked() || other.isLinked())
            return;

        if ((getEnd() + 1) == other.getStart())
        {
            setEnd(other.getEnd());
            other.markAsRemoved();
        }

        if ((getStart() - 1) == other.getEnd())
        {
            setStart(other.getStart());
            other.markAsRemoved();
        }
    }

    /// <summary>
    /// Clones the delimiter.
    /// </summary>
    private Delimiter deepClone()
    {
        Delimiter lhs = (Delimiter)memberwiseClone();
        lhs.setLinkedDelimiter(null);

        // Reset index for linked list.
        lhs.markAsRemoved();

        return lhs;
    }

    /// <summary>
    /// Calculates length of the delimiter.
    /// </summary>
    private int calculateLength(DelimiterRun delimiterRun, int offset, int maxLength)
    {
        Debug.assertTrue(offset < delimiterRun.getOpening().length());

        char delimiterChar = delimiterRun.getOpening().charAt(offset);

        int minLength = Math.min(delimiterRun.getOpening().length() - offset, maxLength);
        Debug.assertTrue(minLength > 0);

        int length = 0;
        while ((length < minLength) && (delimiterRun.getOpening().charAt(offset + length) == delimiterChar))
        {
            length++;
        }

        return length;
    }

    //>>>>>>>> #region  ILInkedListNode implementation

    public ILinkedListNode getNextNode() { return mNextNode; }
    public void setNextNode(ILinkedListNode value) { mNextNode = value; }

    public ILinkedListNode getPrevNode() { return mPrevNode; }
    public void setPrevNode(ILinkedListNode value) { mPrevNode = value; }

    public int getIndex() { return mIndex; }
    public void setIndex(int value) { mIndex = value; }

    public long getSecondaryIndex() { return mSecondaryIndex; }
    public void setSecondaryIndex(long value) { mSecondaryIndex = value; }

    public boolean isNotIncluded() { return mIndex == Integer.MIN_VALUE; }

    public void markAsRemoved()
    {
        mIndex = Integer.MIN_VALUE;
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    abstract /*DelimiterType*/int getType();

    /// <summary>
    /// Gets a priority of the delimiter.
    /// </summary>
    /// <remarks> The delimiter with greater priority can break linked delimiters with lower priority.</remarks>
    abstract /*DelimiterPriority*/int getPriority();

    /// <summary>
    /// Gets a priority to unlink inside linked delimiters.
    /// </summary>
    /// <remarks>
    /// This is a priority of a linked delimiter below which there can be no other linked delimiters inside.
    /// </remarks>
    abstract /*DelimiterPriority*/int getUnlinkPriority();

    /// <summary>
    /// Gets a boolean value indicating whether the delimiter is valid.
    /// </summary>
    /*virtual*/ boolean isValid() { return true; }

    /// <summary>
    /// Gets length of the delimiter.
    /// </summary>
    int getLength() { return (getEnd() - getStart()) + 1; }

    /// <summary>
    /// Gets delimiter start position in original text.
    /// </summary>
    int getStart() { return mStart; }; private void setStart(int value) { mStart = value; };

    private int mStart;

    /// <summary>
    /// Gets delimiter end position in original text.
    /// </summary>
    int getEnd() { return mEnd; }; private void setEnd(int value) { mEnd = value; };

    private int mEnd;

    /// <summary>
    /// Gets a boolean value indicating whether the delimiter is linked with another delimiter,
    /// so that they are form a pair of Opening and Closing delimiters.
    /// </summary>
    boolean isLinked() { return (getLinkedDelimiter() != null); }

    /// <summary>
    /// Gets a boolean value indicating the delimiter forms an Opening sequence in a pair of delimiters.
    /// </summary>
    boolean isOpening()
    {
        Debug.assertTrue(isLinked());
        return isBefore(getLinkedDelimiter());
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter forms an Closing sequence in a pair of delimiters.
    /// </summary>
    boolean isClosing()
    {
        Debug.assertTrue(isLinked());
        return getLinkedDelimiter().isBefore(this);
    }

    /// <summary>
    /// Gets linked delimiter.
    /// </summary>
    
    Delimiter getLinkedDelimiter() { return mLinkedDelimiter; }; 
    private void setLinkedDelimiter(Delimiter value) { mLinkedDelimiter = value; };

    private Delimiter mLinkedDelimiter;

    /// <summary>
    /// Gets a string value representing a text this delimiter belongs.
    /// </summary>
    String getText() { return mText; }

    /// <summary>
    /// Gets flanking type of the delimiter.
    /// </summary>
    /*FlankingType*/int getFlankingType() { return mFlankingType; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected abstract boolean canBeClosing();

    /// <summary>
    /// Gets an integer value representing a maximum allowed length of the delimiter in a linked state.
    /// </summary>
    protected abstract int getMaxLinkedLength();

    /// <summary>
    /// Gets a boolean value indicating the delimiter is escaped.
    /// </summary>
    /// <remarks>
    /// Note, for a moment the only InlineCode (`) delimiter can be escaped. All other escaped delimiters
    /// are excluded while building delimiter runs they are belonging.
    /// </remarks>
    protected boolean isEscaped() { return MarkdownUtil.isEscaped(mText, getStart()); }

    /// <summary>
    /// Gets a boolean value indicating the delimiter is intraword.
    /// </summary>
    protected boolean isIntraword() { return MarkdownUtil.isIntraword(getPrevChar(), getNextChar()); }

    /// <summary>
    /// Gets character immediately preceding this delimiter in a text.
    /// </summary>
    private char getPrevChar() { return (getStart() > 0) ? mText.charAt(getStart() - 1) : InlineContainerBlock.LINE_SEPARATOR; }

    /// <summary>
    /// Gets character immediately following this delimiter in a text.
    /// </summary>
    private char getNextChar() { return ((getEnd() + 1) < mText.length()) ? mText.charAt(getEnd() + 1) : InlineContainerBlock.LINE_SEPARATOR; }

    private ILinkedListNode mNextNode;
    private ILinkedListNode mPrevNode;
    private int mIndex = Integer.MIN_VALUE;
    private long mSecondaryIndex;

    private /*final*/ String mText;
    private /*final*/ /*FlankingType*/int mFlankingType;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

