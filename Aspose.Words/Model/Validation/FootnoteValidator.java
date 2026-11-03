// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/09/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.LinkedList;


/// <summary>
/// Validates footnote and endnote references.
/// </summary>
class FootnoteValidator
{
    void visitFootnote(Footnote footnote)
    {
        if (!msString.isNullOrEmpty(footnote.getReferenceMark()) || footnote.isAuto())
            return;

        mMissedCustomMarkFootnotes.addLast(footnote);
    }

    void visitRun(Run run, StoryTypeStack storyTypeStack)
    {
        if (run.isRemoved())
            return;

        if (storyTypeStack.getCurrent() != StoryType.MAIN_TEXT)
            return;

        Footnote footnote = moveAndJoinFootnotes(run);
        if (footnote == null)
            return;

        if (!footnote.isAuto())
            footnote.setReferenceMark(collectRunTextWithSameFormatting(run));
    }

    private static String collectRunTextWithSameFormatting(Run run)
    {
        final /*RunPrExpandFlags*/int EXPAND_FLAGS = RunPrExpandFlags.DOCUMENT_DEFAULTS | RunPrExpandFlags.GLOBAL_DEFAULTS;

        Node curNode = run.getNextSibling();
        RunPr runPrExp = null;
        StringBuilder sb = null;

        while (curNode != null)
        {
            if (curNode.getNodeType() != NodeType.RUN)
                break;

            Run curRun = (Run)curNode;

            RunPr curRunPrExp = curRun.getExpandedRunPr(EXPAND_FLAGS);
            if (runPrExp == null)
                runPrExp = run.getExpandedRunPr(EXPAND_FLAGS);

            if (!RunPr.isSameFormatting(curRunPrExp, runPrExp))
                break;

            if (sb == null)
                sb = new StringBuilder(run.getText());

            msStringBuilder.append(sb, curRun.getText());

            curNode = curNode.getNextSibling();

            curRun.remove();
        }

        run.remove();

        return sb != null
            ? sb.toString()
            : run.getText();
    }

    private Footnote moveAndJoinFootnotes(Run run)
    {
        Footnote result = null;

        while (true)
        {
            if (mMissedCustomMarkFootnotes.size() == 0)
                return result;

            Footnote footnote = mMissedCustomMarkFootnotes.peek();

            if (footnote.getParentNode() == run.getParentNode())
                return result;

            if (result != null)
            {
                result.isAuto(false);
                result.insertAfter(footnote.getFirstChild(), null, null);
                footnote.remove();
            }
            else
            {
                run.insertPrevious(footnote);
                footnote.isAuto(isAutoFootnote(footnote));
                footnote.setRunPr(AttrCollectionExtension.deepClone(run.getRunPr()));

                result = footnote;
            }

            mMissedCustomMarkFootnotes.removeFirst();
        }
    }

    private static boolean isAutoFootnote(Footnote footnote)
    {
        if (footnote.getFootnoteType() == FootnoteType.ENDNOTE)
            return false;

        for (SpecialChar specialChar : (Iterable<SpecialChar>) footnote.getChildNodes(NodeType.SPECIAL_CHAR, true))
        {
            if (specialChar.getChar() == ControlChar.FOOTNOTE_REF_CHAR)
                return true;
        }

        return false;
    }

    private /*final*/ LinkedList<Footnote> mMissedCustomMarkFootnotes = new LinkedList<Footnote>();
}

