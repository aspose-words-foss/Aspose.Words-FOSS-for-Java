// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 31/05/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Performs default cleanup strategy.
/// </summary>
class DefaultFieldCleaner implements IFieldCleaner
{
    public boolean /*IFieldCleaner.*/removeEmptyParagraph(Paragraph paragraph)
    {
        if (needPostponeParagraphRemoval(paragraph))
        {
            mParagraphsToRemove.add(paragraph);
            return false;
        }

        paragraph.remove();
        return true;
    }

    public boolean /*IFieldCleaner.*/removeFieldCode(FieldUpdateContext context) throws Exception
    {
        return removeFieldCodeInternal(context);
    }

    public void /*IFieldCleaner.*/finalizeCleanup() throws Exception
    {
        for (Field field : mFieldsToReplaceWithResult)
            FieldReplacer.replaceWithResult(field);

        for (Paragraph paragraph : mParagraphsToRemove)
        {
            if (paragraph.getParentNode() != null)
                paragraph.remove();
        }
    }

    protected /*virtual*/ boolean removeFieldCodeInternal(FieldUpdateContext context) throws Exception
    {
        if (needPostponeReplaceWithResult(context))
        {
            postponeFieldReplaceWithResult(context.getField());
            return false;
        }

        FieldReplacer.replaceWithResult(context.getField());
        return true;
    }

    protected /*virtual*/ boolean needPostponeParagraphRemoval(Paragraph paragraph)
    {
        if (paragraph.getChild(NodeType.BOOKMARK_START, 0, true) != null)
            return true;

        if (paragraph.getChild(NodeType.BOOKMARK_END, 0, true) != null)
            return true;

        return false;
    }

    protected /*virtual*/ boolean needPostponeReplaceWithResult(FieldUpdateContext context)
    {
        return false;
    }

    protected void postponeFieldReplaceWithResult(Field field)
    {
        mFieldsToReplaceWithResult.add(field);
    }

    private /*final*/ ArrayList<Paragraph> mParagraphsToRemove = new ArrayList<Paragraph>();
    private /*final*/ ArrayList<Field> mFieldsToReplaceWithResult = new ArrayList<Field>();
}

