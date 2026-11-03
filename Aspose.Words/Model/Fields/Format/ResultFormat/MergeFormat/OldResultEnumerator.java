// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Enumerates an "old" field result when applying MERGEFORMAT. Finds formatting sources for runs and paragraphs.
/// </summary>
class OldResultEnumerator extends ResultEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="resultNodes"></param>
    /// <param name="startParagraph"></param>
    OldResultEnumerator(Iterable<Node> resultNodes, Paragraph startParagraph)
    {
    	super(resultNodes, startParagraph);
	
    }

    /*override*/ boolean moveNext() throws Exception
    {
        if (mIsSuspended)
        {
            setRunPr(null);
            return true;
        }

        return super.moveNext();
    }

    protected /*override*/ void onChar(char c)
    {
        if (getRunPr() == null)
            setRunPr(getNextRun().getRunPr());
    }

    protected /*override*/ void onNextChar(char c)
    {
        mNextRunPr = getNextRun().getRunPr();
    }

    protected /*override*/ void onNextNode()
    {
        if (isNextNodeParagraph() || isNextNodeTable())
        {
            mNextRunPr = getCurrentParagraph() != null
                ? getCurrentParagraph().getParagraphBreakRunPr()
                : null;
        }
    }

    protected /*override*/ void applyNext()
    {
        setRunPr(mNextRunPr);
        mNextRunPr = null;
    }

    /*override*/ boolean isSuspended() { return mIsSuspended; }

    /// <summary>
    /// Suspends the enumeration.
    /// </summary>
    void suspend()
    {
        mIsSuspended = true;
    }

    /// <summary>
    /// Unsuspends the enumeration.
    /// </summary>
    void unsuspend()
    {
        mIsSuspended = false;
    }

    /// <summary>
    /// Gets the current formatting source for runs and paragraph breaks.
    /// </summary>
    RunPr getRunPr() { return mRunPr; }; private void setRunPr(RunPr value) { mRunPr = value; };

    private RunPr mRunPr;

    private RunPr mNextRunPr;
    private boolean mIsSuspended;
}

