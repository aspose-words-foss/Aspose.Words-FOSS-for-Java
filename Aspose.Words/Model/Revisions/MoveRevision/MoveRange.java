// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/04/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Wrapper class for MoveRangeStart, MoveRangeEnd nodes.
/// </summary>
class MoveRange
{
    MoveRange(MoveRangeStart start)
    {
        mStart = start;
        mEnd = getStart() != null
            ? MoveRangeFinder.findMoveRangeEnd(start.getDocument(), start.getId())
            : null;
    }

    MoveRange(Node node, boolean isMoveTo)
    {
        mStart = MoveRangeFinder.findMoveRangeStart(node, isMoveTo);
        mEnd = getStart() != null
            ? MoveRangeFinder.findMoveRangeEnd(node.getDocument(), getStart().getId())
            : null;
    }

    /// <summary>
    /// Move range start.
    /// </summary>
    MoveRangeStart getStart() { return mStart; };

    private MoveRangeStart mStart;

    /// <summary>
    /// Move range end.
    /// </summary>
    MoveRangeEnd getEnd() { return mEnd; };

    private MoveRangeEnd mEnd;

    boolean isDefined()
    {
        if (getStart() == null)
            return false;

        if (getEnd() == null)
            return false;

        if (getStart().isRemoved())
            return false;

        if (getEnd().isRemoved())
            return false;

        return true;
    }

    /// <summary>
    /// Indicates whether move range is empty.
    /// </summary>
    boolean isEmpty()
    {
        if (!isDefined())
            return false;

        // Check that between move range start and end doesn't exist any node with corresponding move revision.
        // WORDSNET-15479 Also we can accept only one revision, in this case we must keep move range.
        // Such complex check because in some cases(see TestJira14682) move range start
        // might be located at the end of the previous node.
        Node node = getStart().nextPreOrder(getEnd());
        while (node != null)
        {
            if (RevisionUtil.hasMoveRevision(node, isMoveTo()))
                return false;

            node = node.nextPreOrder(getEnd());
        }

        return true;
    }

    /// <summary>
    /// Returns <c>true</c> if this range is a move-to range.
    /// </summary>
    boolean isMoveTo() { return getStart() instanceof MoveToRangeStart; }

    /// <summary>
    /// Removes move range start/end nodes.
    /// </summary>
    void remove()
    {
        getStart().remove();
        getEnd().remove();
    }
}

