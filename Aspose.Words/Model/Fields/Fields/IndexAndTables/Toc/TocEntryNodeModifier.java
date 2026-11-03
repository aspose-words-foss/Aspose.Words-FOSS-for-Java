// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/03/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.ArrayList;


/// <summary>
/// Removes or preserves tabs and line breaks while copying TOC entry contents.
/// </summary>
class TocEntryNodeModifier implements INodeModifier
{
    TocEntryNodeModifier(boolean preserveTabs, boolean preserveLineBreaks, int entryLevel, boolean isTableOfFigures)
    {
        mPreserveTabs = preserveTabs;
        mPreserveLineBreaks = preserveLineBreaks;
        mEntryLevel = entryLevel;
        mIsTableOfFigures = isTableOfFigures;
    }

    public Node /*INodeModifier.*/modify(Node referenceNode, Node nodeToModify, boolean modifyChildren, INodeCloningListener cloningListener)
    {
        // WORDSNET-26724 Skip deleted nodes.
        // WORDSNET-20677 Skip hidden nodes.
        // WORDSNET-8839 Use referenceNode instead of nodeToModify in this fix, because nodeToModify have no parent,
        // which leads to incorrect determining of toggle attributes.
        Inline inline = ms.as(referenceNode, Inline.class);
        if ((inline != null) && inline.isHiddenOrDeleted())
            return null;

        switch (nodeToModify.getNodeType())
        {
            case NodeType.RUN:
                Run run = (Run)nodeToModify;
                run = trimWhiteSpaces(run);
                modifyRunText(run);
                return run;

            // WORDSNET-26724 Skip comment ranges and comments.
            case NodeType.COMMENT_RANGE_START:
            case NodeType.COMMENT:
            case NodeType.COMMENT_RANGE_END:
            // WORDSNET-5485 Skip footnote nodes.
            case NodeType.FOOTNOTE:
                return null;

            case NodeType.SHAPE:
            case NodeType.GROUP_SHAPE:
                ShapeBase shape = (ShapeBase)nodeToModify;
                if (!shape.isInline())
                    return null;

                processNotWhiteSpace();
                return nodeToModify;

            case NodeType.SPECIAL_CHAR:
                processNotWhiteSpace();
                return null;

            default:
                // Do not modify other nodes
                processNotWhiteSpace();
                return nodeToModify;
        }
    }

    void finalizeEntry()
    {
        trimTrailingWhitespaces();
        ensureEntryTabStop();
    }

    private void trimTrailingWhitespaces()
    {
        for (int index = 0; index < mTrailingWhitespaceRuns.size(); index++)
        {
            Run run = mTrailingWhitespaceRuns.get(index);
            if (index != 0 || mFirstTrailingWhitespaceCharPosition == 0)
                run.remove();
            else
                run.cutText(0, mFirstTrailingWhitespaceCharPosition, false);
        }
    }

    private void ensureEntryTabStop()
    {
        if (mRunWithTab != null && mRunWithTab.getParentNode() != null)
        {
            int tabIndex = msString.indexOf(mRunWithTab.getText(), ControlChar.TAB, StringComparison.ORDINAL);
            mRunWithTab.splitAfter(tabIndex + 1);
            mRunWithTab.splitBefore(tabIndex);

            if (mRunWithTab.getFont().getStyleIdentifier() == StyleIdentifier.HYPERLINK)
                mRunWithTab.getRunPr().remove(FontAttr.ISTD);

            FieldIndexAndTablesUtil.ensureTocEntryTabStop(mRunWithTab, mEntryLevel, mIsTableOfFigures);
        }
    }

    private void modifyRunText(Run run)
    {
        if (run == null)
            return;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < run.getText().length(); i++)
        {
        	char c = run.getText().charAt(i);
            // WORDSNET-15774 Do not copy page breaks to the TOC.
            // WORDSNET-24890 Do not copy column breaks either.
            if ((c == ControlChar.PAGE_BREAK_CHAR) || (c == ControlChar.COLUMN_BREAK_CHAR))
                continue;

            // OOXML specs says "Preserves tab entries within table entries" while Word always copies tab characters,
            // but when \w is specified, it does not copy tab properties. Strange behavior, I prefer simply replace
            // with a space or preserve, just like for line breaks.
            boolean shouldReplaceTab = shouldReplaceTab(c, run);
            boolean shouldReplaceLineBreak = shouldReplaceLineBreak(c);

            if (shouldReplaceTab || shouldReplaceLineBreak)
                builder.append(' ');
            else
                builder.append(c);
        }

        run.setText(builder.toString());
    }

    private Run trimWhiteSpaces(Run run)
    {
        run = trimLeadingWhitespaces(run);
        findPossibleTrailingWhitespaces(run);
        return run;
    }

    private Run trimLeadingWhitespaces(Run run)
    {
        if (mNotWhitespaceReached)
            return run;

        int leadingWhitespaceCharCount = 0;
        for (int i = 0; i < run.getText().length(); i++)
        {
        	char c = run.getText().charAt(i);
            if (StringUtil.isWhiteSpace(c))
            {
                leadingWhitespaceCharCount++;
            }
            else
            {
                processNotWhiteSpace();
                break;
            }
        }

        if (leadingWhitespaceCharCount == run.getText().length())
            return null;

        if (leadingWhitespaceCharCount > 0)
            run.setText(msString.remove(run.getText(), 0, leadingWhitespaceCharCount));

        return run;
    }

    private void findPossibleTrailingWhitespaces(Run run)
    {
        if (!mNotWhitespaceReached)
            return;

        boolean isTrailingWhitespace = false;
        for (int i = 0; i < run.getText().length(); i++)
        {
            char c = run.getText().charAt(i);
            if (StringUtil.isWhiteSpace(c))
            {
                isTrailingWhitespace = true;
                if (mFirstTrailingWhitespaceCharPosition == -1)
                    mFirstTrailingWhitespaceCharPosition = i;
            }
            else
            {
                isTrailingWhitespace = false;
                processNotWhiteSpace();
            }
        }

        if (isTrailingWhitespace)
            msArrayList.add(mTrailingWhitespaceRuns, run);
    }

    private void processNotWhiteSpace()
    {
        mNotWhitespaceReached = true;
        mTrailingWhitespaceRuns.clear();
        mFirstTrailingWhitespaceCharPosition = -1;
    }

    private boolean shouldReplaceTab(char c, Run run)
    {
        if (c != ControlChar.TAB_CHAR)
            return false;

        if (mPreserveTabs)
            return false;

        if (mRunWithTab != null)
            return true;

        mRunWithTab = run;
        return false;
    }

    private boolean shouldReplaceLineBreak(char c)
    {
        return (c == ControlChar.LINE_BREAK_CHAR) && !mPreserveLineBreaks;
    }

    private Run mRunWithTab;

    private boolean mNotWhitespaceReached;
    private /*final*/ ArrayList<Run> mTrailingWhitespaceRuns = new ArrayList<Run>();
    private int mFirstTrailingWhitespaceCharPosition = -1;

    private /*final*/ boolean mPreserveTabs;
    private /*final*/ boolean mPreserveLineBreaks;
    private /*final*/ int mEntryLevel;
    private /*final*/ boolean mIsTableOfFigures;
}

