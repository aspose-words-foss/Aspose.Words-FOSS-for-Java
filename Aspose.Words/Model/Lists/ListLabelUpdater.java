// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.Collections.msStack;
import java.util.ArrayList;


/// <summary>
/// Walks through all paragraphs in the document and updates list labels for all numbered or bulleted paragraphs.
/// </summary>
/// <remarks>
/// <para>At the moment the logic based on playing with Word is as follows:</para>
/// <para>1. There are several areas (normally stories) where numbering goes independently and continuously:
/// body, headers/footers, notes, comments. It seems that numbering does not reset throughout sections.</para>
/// <para>2. Textboxes are inside area too. It seems it's Shape area.</para>
/// <para>3. <see cref="ListNumberGenerator"/> is responsible for numbering inside each area.</para>
///
/// <para>For new <see cref="SaveFormat"/>s that need up-to-date list labels, update <see cref="DocumentValidator.gSaveActions"/>
/// hashtable with new element containing <see cref="DocumentValidatorActions.UpdateListLabels"/> flag.</para>
/// <para>The other classes that need up-to-date list labels, must call <see cref="UpdateListLabels"/> function.</para>
/// <para>For public update of list labels use <see cref="Document.UpdateListLabels"/> function.</para>
/// </remarks>
class ListLabelUpdater extends ListItemProcessor
{
    /// <summary>
    /// <para>Walks through all paragraphs in the document and updates list labels for all numbered or bulleted paragraphs.</para>
    /// <para>Presence of list label for a paragraph can be checked by <see cref="Paragraph.HasListLabel"/> property.</para>
    /// <para>List label text and other info can be retrieved by <see cref="Paragraph.ListLabel"/> property.</para>
    /// </summary>
    /// <param name="doc">Document, list labels are created for.</param>
    static void updateListLabels(Document doc) throws Exception
    {
        ListLabelUpdater originalLabelUpdater = new ListLabelUpdater(doc, RevisionsView.ORIGINAL);
        doc.accept(originalLabelUpdater);

        if (!originalLabelUpdater.mHasRevisions)
            return;

        // We have revisions in the document. Do second pass to update final list labels.
        ListLabelUpdater finalLabelUpdater = new ListLabelUpdater(doc, RevisionsView.FINAL);
        doc.accept(finalLabelUpdater);
    }

    private ListLabelUpdater(Document doc, /*RevisionsView*/int revisionsView)
    {
        mRevisionsView = revisionsView;
        mFieldNumListLabelUpdater = new FieldNumListLabelUpdater(doc);

        // AS Some of list number generators may stay unused for simple documents,
        // but creation of ListNumberGenerator is cheap operation. I believe it's better
        // than maintain hashtable of generators with document area as a key.
        for (int i = 0; i < NUMBER_OF_AREAS; i++)
            mListNumberGenerators[i] = new ListNumberGenerator();

        mBodyListNumberGenerator = mListNumberGenerators[(int)ListNumberingArea.BODY];
    }

    public /*override*/ /*VisitorAction*/int visitSectionStart(Section section)
    {
        mOccuredListIds.clear();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para)
    {
        mCurrentParagraphs.push(new ParagraphProcessingState(para));

        // WORDSNET-18475 Restart from a specific list level.
        if (para.isListItem())
        {
            int listId = para.getListFormat().getListId();
            int listLevel = para.getListFormat().getListLevelNumberOriginal();

            if(!mOccuredListIds.contains(listId))
                for (ListNumberGenerator listNumberGenerator : mListNumberGenerators)
                    listNumberGenerator.restart(listId, listLevel);

            msArrayList.add(mOccuredListIds, listId);
        }

        // SPEED. Do second pass only if document has revisions.
        if (para.hasRevisions())
            mHasRevisions = true;

        processParagraph();

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphEnd(Paragraph paragraph)
    {
        if (!isCurrentParagraphProcessed())
        {
            boolean isInsideBody = getCurrentNumberGenerator() == mBodyListNumberGenerator;
            mFieldNumListLabelUpdater.processParagraph(getCurrentParagraph(), false, isInsideBody);
        }

        mCurrentParagraphs.pop();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldStart)
    {
        mFieldChars.push(fieldStart);

        mFieldNumListLabelUpdater.processFieldStart(fieldStart);
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldSeparator)
    {
        // Protection against standalone field separator.
        if (getCurrentFieldChar() != null)
        {
            mFieldChars.push(fieldSeparator);
            mFieldNumListLabelUpdater.processFieldSeparator(fieldSeparator);
        }

        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldEnd)
    {
        // There could be field separator, pop it.
        if ((getCurrentFieldChar() != null) && (getCurrentFieldChar().getNodeType() == NodeType.FIELD_SEPARATOR))
            mFieldChars.pop();

        // And pop field start.
        if (getCurrentFieldChar() != null)
            mFieldChars.pop();

        mFieldNumListLabelUpdater.processFieldEnd(fieldEnd);

        processParagraph();

        return VisitorAction.CONTINUE;
    }

    protected /*override*/ void onNumberingAreaStart(/*ListNumberingArea*/int area)
    {
        pushNumberGenerator(area);
    }

    protected /*override*/ void onNumberingAreaEnd()
    {
        popNumberGenerator();
    }

    private void processParagraph()
    {
        if (isCurrentParagraphProcessed())
            return;

        // WORDSNET-18309 When in field, update list labels for the topmost fields only.
        if (isInField() && !isInTopmostFieldResult())
            return;

        ListNumberGenerator generator = getCurrentNumberGenerator();
        boolean isInsideBody = generator == mBodyListNumberGenerator;
        Paragraph paragraph = getCurrentParagraph();

        boolean isListLabelUpdated = ListLabelUtil.updateParagraphListLabel(paragraph, generator, mRevisionsView);
        mFieldNumListLabelUpdater.processParagraph(paragraph, isListLabelUpdated, isInsideBody);

        isCurrentParagraphProcessed(true);
    }

    /// <summary>
    /// Pushes a specific area's number generator to the stack.
    /// </summary>
    private void pushNumberGenerator(/*ListNumberingArea*/int area)
    {
        // AS Here we put to stack ListNumberGenerator and not ListNumberingArea to optimize Peek operation in
        // CurrentNumberGenerator accessor that returns the topmost ListNumberGenerator from the stack.
        mNumberGeneratorStack.push(mListNumberGenerators[(int)area]);
        mFieldNumListLabelUpdater.processArea(getCurrentNumberGenerator());
    }

    private void popNumberGenerator()
    {
        mNumberGeneratorStack.pop();
        if (hasCurrentNumberGenerator())
            mFieldNumListLabelUpdater.processArea(getCurrentNumberGenerator());
    }

    private boolean hasCurrentNumberGenerator() { return mNumberGeneratorStack.getCount() > 0; }

    private ListNumberGenerator getCurrentNumberGenerator() { return mNumberGeneratorStack.peek(); }

    /// <summary>
    /// Indicates that we are currently inside a field.
    /// </summary>
    private boolean isInField() { return mFieldChars.getCount() > 0; }

    /// <summary>
    /// Indicates that we are currently inside field result.
    /// </summary>
    private boolean isInTopmostFieldResult()
    {
        // We are in field result if FieldSeparator is at top of stack.
        // WORDSNET-18309 mFieldChars.Count == 2 - means we are in the topmost field result.
        return (mFieldChars.getCount() == 2) && (getCurrentFieldChar().getNodeType() == NodeType.FIELD_SEPARATOR);
    }

    private FieldChar getCurrentFieldChar() { return isInField() ? mFieldChars.peek() : null; }

    /// <summary>
    /// Collects field characters during document traverse.
    /// </summary>
    private /*final*/ msStack<FieldChar> mFieldChars = new msStack<FieldChar>();

    private /*final*/ ListNumberGenerator[] mListNumberGenerators = new ListNumberGenerator[NUMBER_OF_AREAS];
    private /*final*/ ListNumberGenerator mBodyListNumberGenerator;
    private /*final*/ msStack<ListNumberGenerator> mNumberGeneratorStack = new msStack<ListNumberGenerator>();
    private /*final*/ FieldNumListLabelUpdater mFieldNumListLabelUpdater;
    private /*final*/ /*RevisionsView*/int mRevisionsView;
    private /*final*/ msStack<ListLabelUpdater.ParagraphProcessingState> mCurrentParagraphs = new msStack<ListLabelUpdater.ParagraphProcessingState>();

    // Indicates that document has revision.
    private boolean mHasRevisions;

    /// <summary>
    /// Contains list identifiers of numbered paragraph occured after break.
    /// </summary>
    private /*final*/ ArrayList<Integer> mOccuredListIds = new ArrayList<Integer>();

    private boolean isCurrentParagraphProcessed() { return mCurrentParagraphs.peek().isProcessed(); }
    private void isCurrentParagraphProcessed(boolean value) { mCurrentParagraphs.peek().isProcessed(value); }

    private Paragraph getCurrentParagraph() { return mCurrentParagraphs.peek().getParagraph(); }

    private static class ParagraphProcessingState
    {
        ParagraphProcessingState(Paragraph paragraph)
        {
            mParagraph = paragraph;
        }

        Paragraph getParagraph() { return mParagraph; };

        private Paragraph mParagraph;
        boolean isProcessed() { return mIsProcessed; }; void isProcessed(boolean value) { mIsProcessed = value; };

        private boolean mIsProcessed;
    }
}

