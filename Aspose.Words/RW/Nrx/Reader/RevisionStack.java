// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/02/2015 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.StackExtension;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.Debug;


/// <summary>
/// Provides revisions stacks for each story.
/// </summary>
class StoryRevisionStack
{
    /// <summary>
    /// Stores necessary information for item of revision stack.
    /// </summary>
    private static class RevisionStackItem
    {
        RevisionStackItem(RevisionBase revision)
        {
        	this (revision, false);
	
        }

        RevisionStackItem(RevisionBase revision, boolean isMoveRange)
        {
            mRevision = revision;
            mIsMoveRange = isMoveRange;
        }

        RevisionBase getRevision() { return mRevision; }

        /// <summary>
        /// True indicates that <see cref="Revision"/> is not a real revision but only is created
        /// for a move range. Math nodes inside move range are intended to have a move revision.
        /// </summary>
        boolean isMoveRange() { return mIsMoveRange; }

        private /*final*/ RevisionBase mRevision;
        private /*final*/ boolean mIsMoveRange;
    }

    /// <summary>
    /// Edit and move revisions are stored here to be added to related run, which is read later.
    /// Peek version of this stack is always safe to call, if the stack is empty it will return null.
    /// </summary>
    private static class RevisionStack
    {
        void pushInsertRevision(EditRevision revision)
        {
            mPendingInsertRevisions.push(new RevisionStackItem(revision));
        }

        RevisionStackItem popInsertRevision()
        {
            return mPendingInsertRevisions.pop();
        }

        RevisionStackItem peekInsertRevision()
        {
            return StackExtension.top(mPendingInsertRevisions);
        }

        void pushDeleteRevision(EditRevision revision)
        {
            mPendingDeleteRevisions.push(new RevisionStackItem(revision));
        }

        RevisionStackItem popDeleteRevision()
        {
            return mPendingDeleteRevisions.pop();
        }

        RevisionStackItem peekDeleteRevision()
        {
            return StackExtension.top(mPendingDeleteRevisions);
        }

        void pushMoveToRevision(MoveRevision revision, boolean isMoveRange)
        {
            mPendingMoveToRevisions.push(new RevisionStackItem(revision, isMoveRange));
        }

        RevisionStackItem popMoveToRevision()
        {
            if (mPendingMoveToRevisions.getCount() == 0)
                return null;

            return mPendingMoveToRevisions.pop();
        }

        RevisionStackItem peekMoveToRevision()
        {
            return StackExtension.top(mPendingMoveToRevisions);
        }

        void pushMoveFromRevision(MoveRevision revision, boolean isMoveRange)
        {
            mPendingMoveFromRevisions.push(new RevisionStackItem(revision, isMoveRange));
        }

        RevisionStackItem popMoveFromRevision()
        {
            // WORDSNET-17529 Ignore moveFromRangeEnd, when moveFromRangeStart element is missing.
            if (mPendingMoveFromRevisions.getCount() == 0)
                return null;

            return mPendingMoveFromRevisions.pop();
        }

        RevisionStackItem peekMoveFromRevision()
        {
            return StackExtension.top(mPendingMoveFromRevisions);
        }

        // DD: I'll preallocate each stack to 1 el only as usually this is the case.
        private /*final*/ msStack<StoryRevisionStack.RevisionStackItem> mPendingInsertRevisions = new msStack<StoryRevisionStack.RevisionStackItem>();
        private /*final*/ msStack<StoryRevisionStack.RevisionStackItem> mPendingDeleteRevisions = new msStack<StoryRevisionStack.RevisionStackItem>();
        private /*final*/ msStack<StoryRevisionStack.RevisionStackItem> mPendingMoveToRevisions = new msStack<StoryRevisionStack.RevisionStackItem>();
        private /*final*/ msStack<StoryRevisionStack.RevisionStackItem> mPendingMoveFromRevisions = new msStack<StoryRevisionStack.RevisionStackItem>();
    }

    /// <summary>
    /// Applies read revisions to given property collection.
    /// </summary>
    void apply(RunPr runPr, Node node)
    {
        // If stack is empty there is nothing to apply.
        if (mRevisionStacks.getCount() == 0)
            return;

        RevisionStack curRevisionStack = mRevisionStacks.peek();

        RevisionStackItem item = curRevisionStack.peekDeleteRevision();
        if (item != null)
            runPr.setDeleteRevision((EditRevision)item.getRevision());

        item = curRevisionStack.peekInsertRevision();
        if (item != null)
            runPr.setInsertRevision((EditRevision)item.getRevision());

        item = curRevisionStack.peekMoveFromRevision();
        if ((item != null) &&
            (!item.isMoveRange() || isMoveRevisionByMoveRange(node)))
            runPr.setMoveFromRevision((MoveRevision)item.getRevision());

        item = curRevisionStack.peekMoveToRevision();
        if ((item != null) &&
            (!item.isMoveRange() || isMoveRevisionByMoveRange(node)))
            runPr.setMoveToRevision((MoveRevision)item.getRevision());
    }

    public StoryRevisionStack(IWarningCallback warningCallback, /*WarningSource*/int warningSource)
    {
        mWarningCallback = warningCallback;
        mWarningSource = warningSource;
    }

    /// <summary>
    /// Returns <c>true</c> for node types, which nodes are expected to have a move revision on locating
    /// in a move range.
    /// </summary>
    private static boolean isMoveRevisionByMoveRange(Node node)
    {
        return node.getAncestor(NodeType.OFFICE_MATH) != null;
    }

    /// <summary>
    /// Starts new revisions story.
    /// </summary>
    void pushStory()
    {
        mRevisionStacks.push(new RevisionStack());
    }

    /// <summary>
    /// Returns to previous revision story.
    /// </summary>
    void popStory()
    {
        mRevisionStacks.pop();
    }

    /// <summary>
    /// Pushes edit revision to stack.
    /// </summary>
    void push(EditRevision editRevision)
    {
        if (mRevisionStacks.getCount() == 0)
            pushStory();

        RevisionStack curRevisionStack = mRevisionStacks.peek();

        if (editRevision.getType() == EditRevisionType.DELETION)
            curRevisionStack.pushDeleteRevision(editRevision);
        else
            curRevisionStack.pushInsertRevision(editRevision);
    }

    /// <summary>
    /// Pushes move revision to stack.
    /// </summary>
    void push(MoveRevision moveRevision, boolean isMoveRange)
    {
        if (mRevisionStacks.getCount() == 0)
            pushStory();

        RevisionStack curRevisionStack = mRevisionStacks.peek();

        if (moveRevision.getType() == MoveRevisionType.MOVE_FROM)
            curRevisionStack.pushMoveFromRevision(moveRevision, isMoveRange);
        else
            curRevisionStack.pushMoveToRevision(moveRevision, isMoveRange);
    }

    /// <summary>
    /// Pops revisions from stack.
    /// </summary>
    RevisionBase pop(RevisionBase revision)
    {
        Debug.assertTrue(mRevisionStacks.getCount() > 0);

        if (mRevisionStacks.getCount() == 0)
            return null;

        RevisionStack curRevisionStack = mRevisionStacks.peek();

        MoveRevision moveRevision = ms.as(revision, MoveRevision.class);
        EditRevision editRevision = ms.as(revision, EditRevision.class);

        if (moveRevision != null)
            return popMoveRevision(moveRevision, curRevisionStack);

        if (editRevision != null)
            return (editRevision.getType() == EditRevisionType.DELETION)
                ? curRevisionStack.popDeleteRevision().getRevision()
                : curRevisionStack.popInsertRevision().getRevision();

        Debug.assertTrue(true, "Unexpected revision type.");

        return null;
    }

    /// <summary>
    /// Pops moveToRevision or moveFromRevision depending from move revision type.
    /// </summary>
    private RevisionBase popMoveRevision(MoveRevision moveRevision, RevisionStack curRevisionStack)
    {
        RevisionStackItem item = (moveRevision.getType() == MoveRevisionType.MOVE_FROM)
            ? curRevisionStack.popMoveFromRevision()
            : curRevisionStack.popMoveToRevision();
        MoveRevision popedRevision = (MoveRevision)((item != null) ? item.getRevision() : null);

        if ((popedRevision == null) && (mWarningCallback != null))
        {
            WarningInfo warnInfo =
                new WarningInfo(WarningType.UNEXPECTED_CONTENT, mWarningSource, WarningStrings.MOVE_START_MISSED);
            mWarningCallback.warning(warnInfo);
        }

        return popedRevision;
    }

    private /*final*/ msStack<StoryRevisionStack.RevisionStack> mRevisionStacks = new msStack<StoryRevisionStack.RevisionStack>();
    private /*final*/ IWarningCallback mWarningCallback;
    private /*final*/ /*WarningSource*/int mWarningSource;
}

