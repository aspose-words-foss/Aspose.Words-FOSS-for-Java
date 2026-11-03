// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;

/// <summary>
/// Provides the MERGEFORMAT functionality.
/// </summary>
class MergeFormatProvider implements IFieldResultFormatProvider
{
    MergeFormatProvider(Field field)
    {
        mField = field;
    }

    public Inline getSourceNode()
    {
        for (Node node : mField.getFieldResultRange())
        {
            if (node.getNodeType() == NodeType.RUN)
            {
                // WORDSNET-4384 Skip merge field opening bracket as it might be in different
                // formatting from what the user wants (especially when using embedded fonts).
                // WORDSNET-4384 Cancelled, as this is the way MS Word does it.
                return (Inline)node;
            }
        }

        // Maybe we should traverse it from the very start of the paragraph?
        // E.g. we could consider only runs. This could handle only some weird cases where two
        // fields go adjascent and field end of the first field has improper formatting.
        Inline prevInline = ms.as(mField.getStart().getPreviousSibling(), Inline.class);
        if (prevInline != null)
            return prevInline;

        // The field has no result or no runs, there is no previous node. Lets return properties of the field end.
        return mField.getEnd();
    }

    public IFieldResultFormatApplier getFormatApplier()
    {
        if (FieldUtil.getSeparatorPresence(mField.getType()) != FieldSeparatorPresence.NEVER)
        {
            mField.ensureSeparator(true);

            // WORDSNET-11595 The INCLUDEPICTURE field applies MERGEFORMAT switch in own way.
            if (FieldUtil.isImageFieldResult(mField.getType()))
                return new ImageResultFieldMergeFormatApplier(mField.getOldResultNodes());

            return new MergeFormatApplier(mField.getOldResultNodes(), mField.getOldResultStartParagraph(), mField.getOldResultEndParagraph());
        }
        else
        {
            return null;
        }
    }

    private /*final*/ Field mField;
}

