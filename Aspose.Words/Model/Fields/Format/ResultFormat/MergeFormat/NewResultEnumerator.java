// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import java.util.Iterator;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Enumerates a "new" field result when applying MERGEFORMAT. Collects tokens and formats them.
/// </summary>
class NewResultEnumerator extends ResultEnumerator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private NewResultEnumerator(
        Iterable<Node> resultNodes,
        Paragraph startParagraph,
        Paragraph endParagraph,
        OldResultEnumerator oldResultEnumerator)
    {
    	super(resultNodes, startParagraph);
	
        Debug.assertTrue(!oldResultEnumerator.isInitialized());

        mEndParagraph = endParagraph;
        mOldResultEnumerator = oldResultEnumerator;
    }

    /// <summary>
    /// Merges formattings of new result runs and paragraphs with the old result ones.
    /// </summary>
    /// <remarks>
    /// MS Word format merging algorithm splits text of runs into tokens e.g. words and separators in both
    /// result node sequences and then overrides new result token formattings by old result ones based
    /// nothing but token order. So word's formatting may be overridden by separator's one and vise versa.
    /// Paragraph breaks are treated as separators and become full participants of this overriding too.
    /// Note however that if old result token count inside given paragraph is less than new result one,
    /// then remaining new result tokens' formattings will not be overridden. Otherwise, if these counts
    /// are not equal, remainder of old result token formattings will be moved to next paragraph and so on.
    /// </remarks>
    static void mergeFormat(
        Iterable<Node> resultNodes,
        Paragraph startParagraph,
        Paragraph endParagraph,
        OldResultEnumerator oldResultEnumerator,
        Paragraph oldEndParagraph) throws Exception
    {
        NewResultEnumerator newResultEnumerator = new NewResultEnumerator(
            resultNodes,
            startParagraph,
            endParagraph,
            oldResultEnumerator);

        newResultEnumerator.mergeFormat(oldEndParagraph);
    }

    private void mergeFormat(Paragraph oldEndParagraph) throws Exception
    {
        // mOldResultEnumerator must be moved first we to have an opportunity to use its RunPr property
        // while moving current enumerator.
        while (moveResultsNext())
        {
            if (isCurrentTokenParagraphBreak())
            {
                Paragraph oldResultParagraph = mOldResultEnumerator.isCurrentTokenParagraphBreak()
                    ? mOldResultEnumerator.getLastParagraph()
                    : mOldResultEnumerator.getCurrentParagraph();

                setLastParagraphPr(oldResultParagraph);

                // New result paragraph is reached. Unlock processing if it has been locked previously.
                unlockProcessing();
                mOldResultEnumerator.unsuspend();
            }
            else if (isCurrentTokenTable())
            {
                mergeTables(getCurrentTable(), mOldResultEnumerator.getCurrentTable());
            }
            else if (!mIsProcessingLocked)
            {
                insertCurrentText(mOldResultEnumerator.getRunPr(), false);

                if (mOldResultEnumerator.isCurrentTokenParagraphBreak())
                {
                    // Old result paragraph is reached while new is not.
                    // Lock processing until new result paragraph is reached too.
                    lockProcessing();
                    mOldResultEnumerator.suspend();
                }
            }
        }

        finalizeProcessing();
        removeRuns();

        // It seems like new result end paragraph formatting is always equal to old result end paragraph's one.
        if (oldEndParagraph != null)
            setParaPr(mEndParagraph, oldEndParagraph.getParaPr(), oldEndParagraph.getParagraphBreakRunPr());

        // If old result has paragraphs less than new one then new result current paragraph properties must be set.
        if (getCurrentParagraph() != null && getCurrentParagraph() != mEndParagraph && mOldResultEnumerator.getCurrentParagraph() != null)
            setParaPr(getCurrentParagraph(), mOldResultEnumerator.getCurrentParagraph().getParaPr(), null);
    }

    private void setLastParagraphPr(Paragraph oldResultParagraph)
    {
        if (getLastParagraph() == null)
            return;

        setParaPr(
            getLastParagraph(),
            oldResultParagraph != null ? oldResultParagraph.getParaPr() : null,
            mOldResultEnumerator.getRunPr());
    }

    private boolean moveResultsNext() throws Exception
    {
        if (!mOldResultEnumerator.moveNext())
            return false;

        if (!moveNext())
            return false;

        if (!mOldResultEnumerator.isCurrentTokenTable() && !isCurrentTokenTable())
            return true;

        Paragraph paragraph = (mOldResultEnumerator.getLastParagraph() != null ? mOldResultEnumerator.getLastParagraph() : mOldResultEnumerator.getCurrentParagraph());
        setLastParagraphPr(paragraph);

        lockProcessing();

        return moveToTable(mOldResultEnumerator) && moveToTable(this);
    }

    private static boolean moveToTable(ResultEnumerator enumerator) throws Exception
    {
        // WORDSNET-19826 If the enumerator is suspended, there's no point in MoveNext() because it causes infinite loop.
        if (enumerator.isSuspended())
            return enumerator.isCurrentTokenTable();

        while (!enumerator.isCurrentTokenTable())
            if (!enumerator.moveNext())
                return false;

        return true;
    }

    private static void mergeTables(Table newResultTable, Table oldResultTable) throws Exception
    {
        newResultTable.getTablePr().clear();
        cloneAttrCollectionRevisionsAware(oldResultTable.getTablePr()).copyTo(newResultTable.getTablePr());

        Iterator<Row> oldResultRows = oldResultTable.getRows().<Row>getNodeEnumerator();
        Row oldResultRow = null;

        for (Row newResultRow : (Iterable<Row>) newResultTable.getRows())
        {
            if (oldResultRows.hasNext())
                oldResultRow = oldResultRows.next();
            else if (oldResultRow == null)
                return;

            mergeRow(newResultRow, oldResultRow);
        }
    }

    private static void mergeRow(Row newResultRow, Row oldResultRow) throws Exception
    {
        newResultRow.setTablePr(cloneAttrCollectionRevisionsAware(oldResultRow.getTablePr()));

        Iterator<Cell> newResultCells = newResultRow.getCells().<Cell>getNodeEnumerator();
        Iterator<Cell> oldResultCells = oldResultRow.getCells().<Cell>getNodeEnumerator();

        while (newResultCells.hasNext() && oldResultCells.hasNext())
        {
            Cell newResultCell = newResultCells.next();
            Cell oldResultCell = oldResultCells.next();

            mergeCell(newResultCell, oldResultCell);
        }
    }

    private static void mergeCell(Cell newResultCell, Cell oldResultCell) throws Exception
    {
        newResultCell.setCellPr(cloneAttrCollectionRevisionsAware(oldResultCell.getCellPr()));

        NodeRange newResultNodes = new NodeRange(newResultCell.getFirstParagraph(), newResultCell.getLastParagraph());

        OldResultEnumerator oldResultEnumerator = new OldResultEnumerator(
            new NodeRange(oldResultCell.getFirstParagraph(), oldResultCell.getLastParagraph()),
            oldResultCell.getFirstParagraph());

        mergeFormat(
            newResultNodes,
            newResultCell.getFirstParagraph(),
            newResultCell.getLastParagraph(),
            oldResultEnumerator,
            newResultCell.getFirstParagraph() != newResultCell.getLastParagraph() ? oldResultCell.getLastParagraph() : null);
    }

    private void insertCurrentText(RunPr runPr, boolean forceFinalize)
    {
        if ((mLastInsertedRun == null) ||
            !mLastInsertedRun.getRunPr().equals(runPr, G_RUN_PR_KEYS_TO_IGNORE_IN_COMPARISON) ||
            areNodesBelongToDifferentStdContainers(mLastInsertedRun, mRunToEdit))
        {
            // If current formatting is changed then last inserted run must be finalized.
            finalizeLastInsertedRun();

            if (mCurrentTokenBuilder.length() == 0)
            {
                mLastInsertedRun = null;
                // Last inserted run is finalized and text to insert is empty, so there's nothing left to do.
                return;
            }

            mLastInsertedRun = new Run(mRunToEdit.getDocument(), "", mergeRunPr(runPr, mRunToEdit.getRunPr()));
            mRunToEdit.insertPrevious(mLastInsertedRun);

            // WORDSNET-16372 Apply old formatting to STD container.
            if (mRunToEdit.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG)
            {
                StructuredDocumentTag std = (StructuredDocumentTag)mRunToEdit.getParentNode();
                std.setContentsRunPr(mergeRunPr(runPr, std.getContentsRunPr()));
            }
        }

        if (mCurrentTokenBuilder.length() != 0)
        {
            msStringBuilder.append(mLastInsertedRunTextBuilder, mCurrentTokenBuilder);
            clearCurrentText();
        }

        if (forceFinalize || isNextNodeParagraph())
        {
            // Force finalization of last inserted run in case when paragraph break is met,
            // as run can not cross parapraph borders or in case when it need to be forced.
            finalizeLastInsertedRun();
            mLastInsertedRun = null;
        }
    }

    private static RunPr mergeRunPr(RunPr oldRunPr, RunPr newRunPr)
    {
        return mergeRunPr(oldRunPr, newRunPr, true);
    }

    private static RunPr mergeRunPr(RunPr oldRunPr, RunPr newRunPr, boolean trimRevisions)
    {
        RunPr mergedRunPr = cloneAttrCollectionRevisionsAware(oldRunPr, trimRevisions);
        newRunPr.mirrorTo(mergedRunPr, FontAttr.BIDI);
        return mergedRunPr;
    }

    private static void setParaPr(Paragraph paragraph, ParaPr paraPr, RunPr runPr)
    {
        if (paraPr != null)
            paragraph.setParaPr(cloneAttrCollectionRevisionsAware(paraPr, false));

        if (runPr != null)
            paragraph.setParagraphBreakRunPr(mergeRunPr(runPr, paragraph.getParagraphBreakRunPr(), false));
    }

    private static <T extends WordAttrCollection> T cloneAttrCollectionRevisionsAware(T source)
    {
        return cloneAttrCollectionRevisionsAware(source, true);
    }

    private static <T extends WordAttrCollection> T cloneAttrCollectionRevisionsAware(T source, boolean trimRevisions)
    {
        T result = AttrCollectionExtension.deepClone(source);

        if (trimRevisions)
        {
            // WORDSNET-18892 Trim result nodes revisions.
            result.remove(RevisionAttr.DELETE_REVISION);
            result.remove(RevisionAttr.INSERT_REVISION);
            result.acceptFormatRevision();
        }

        return result;
    }

    private void finalizeProcessing()
    {
        // If we are in the middle of the run, cut its text, otherwise remove edited run.
        if (!applyNextRunToEdit(mNextRunToEdit))
            finalizeRunToEdit();

        mRunToEdit = null;
        mNextRunToEdit = null;

        finalizeLastInsertedRun();
        mLastInsertedRun = null;

        clearCurrentText();
        mNextTokenBuilder.setLength(0);
    }

    private void finalizeLastInsertedRun()
    {
        if (mLastInsertedRun == null)
            return;

        mLastInsertedRun.setText(mLastInsertedRunTextBuilder.toString());
        mLastInsertedRunTextBuilder.setLength(0);
    }

    private void finalizeRunToEdit()
    {
        if (mRunToEdit == null)
            return;

        mRunToEdit.setText(mRunToEdit.getText().substring((getNextTextPosition()), (getNextTextPosition()) + (mRunToEdit.getText().length() - getNextTextPosition())));
    }

    private void initializeProcessing()
    {
        if (isNextNodeRun())
            onNextCharCore(getNextRun().getText().charAt(getNextTextPosition()));
    }

    private void clearCurrentText()
    {
        mCurrentTokenBuilder.setLength(0);
    }

    /// <summary>
    /// Removes the collected "old" runs.
    /// </summary>
    private void removeRuns()
    {
        for (Run run : mRunsToRemove)
            run.remove();
    }

    /// <summary>
    /// Locks additional processing of current and next token relative values.
    /// </summary>
    private void lockProcessing()
    {
        if (mIsProcessingLocked)
            return;

        finalizeProcessing();
        mIsProcessingLocked = true;
    }

    /// <summary>
    /// Unlocks additional processing of current and next token relative values.
    /// </summary>
    private void unlockProcessing()
    {
        if (!mIsProcessingLocked)
            return;

        initializeProcessing();
        mIsProcessingLocked = false;
    }

    protected /*override*/ void onFieldResultBoundary()
    {
        if (mIsProcessingLocked)
            return;

        // Due to the certain circumstances sometimes a field result contains not only nested field results
        // but the whole fields instead. To avoid these fields' updating collisions, we need to separate
        // the results of every of those fields in the individual runs, even if the formatting before, inside
        // and after the corresponding field result is the same.
        insertCurrentText(mOldResultEnumerator.getRunPr(), true);
    }

    protected /*override*/ void onChar(char c)
    {
        if (mIsProcessingLocked)
            return;

        applyNextRunToEdit(getNextRun());
        // Character must be appended after next run to edit to be applied for the case of BIDI text direction change.
        mCurrentTokenBuilder.append(c);
    }

    protected /*override*/ void onNextChar(char c)
    {
        if (mIsProcessingLocked)
            return;

        onNextCharCore(c);
    }

    private void onNextCharCore(char c)
    {
        mNextTokenBuilder.append(c);
        mNextRunToEdit = getNextRun();
    }

    protected /*override*/ void applyNext()
    {
        if (mIsProcessingLocked)
            return;

        applyNextRunToEdit(mNextRunToEdit);
        mNextRunToEdit = null;

        clearCurrentText();
        StringBuilder builder = mCurrentTokenBuilder;
        mCurrentTokenBuilder = mNextTokenBuilder;
        mNextTokenBuilder = builder;
    }

    /// <summary>
    /// Makes next run to edit to be current. If it is current already, returns false.
    /// Otherwise, collects old edited run to be removed and returns true.
    /// </summary>
    private boolean applyNextRunToEdit(Run nextRunToEdit)
    {
        if (mRunToEdit == nextRunToEdit)
            return false;

        if (mRunToEdit != null)
            msArrayList.add(mRunsToRemove, mRunToEdit);

        // Reference equality check is enough for RunPr.Bidi as AttrBoolEx is multiton.
        if ((mRunToEdit != null) && (nextRunToEdit != null) &&
            ((mRunToEdit.getRunPr().getBidi() != nextRunToEdit.getRunPr().getBidi()) || areNodesBelongToDifferentStdContainers(mRunToEdit, nextRunToEdit)))
        {
            // Insert current text if its BIDI direction was changed.
            insertCurrentText(mOldResultEnumerator.getRunPr(), true);
        }

        mRunToEdit = nextRunToEdit;
        return true;
    }

    private static boolean areNodesBelongToDifferentStdContainers(Node x, Node y)
    {
        return (x.getParentNode() != y.getParentNode()) &&
               ((x.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG) ||
                (y.getParentNode().getNodeType() == NodeType.STRUCTURED_DOCUMENT_TAG));
    }

    /*override*/ boolean isSuspended() { return false; }

    private StringBuilder mCurrentTokenBuilder = new StringBuilder();
    private StringBuilder mNextTokenBuilder = new StringBuilder();
    private /*final*/ StringBuilder mLastInsertedRunTextBuilder = new StringBuilder();
    private /*final*/ ArrayList<Run> mRunsToRemove = new ArrayList<Run>();
    private /*final*/ Paragraph mEndParagraph;
    private /*final*/ OldResultEnumerator mOldResultEnumerator;
    private Run mLastInsertedRun;
    private Run mRunToEdit;
    private Run mNextRunToEdit;
    private boolean mIsProcessingLocked;

    // Keep sorted since we use binary search.
    private static /*final*/ int[] G_RUN_PR_KEYS_TO_IGNORE_IN_COMPARISON = { FontAttr.RSID_R_PR, FontAttr.RSID_R, FontAttr.BIDI };
}

