// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/12/2011 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;

/// <summary>
/// Provides the CHARFORMAT functionality.
/// </summary>
class CharFormatProvider implements IFieldResultFormatProvider
{
    CharFormatProvider(Field field)
    {
        mField = field;
    }

    public Inline getSourceNode()
    {
        boolean allowHidden = mField.getStart().isHiddenOrDeleted();
        for (Node node : mField.getFieldCodeRange())
        {
            if (node.getNodeType() == NodeType.RUN)
            {
                Run run = (Run)node;

                if (run.isHiddenOrDeleted() && !allowHidden)
                    continue;

                // Leading spacing is not taken into account.
                if (!StringUtil.containsOnlySpaces(run.getText()))
                    return run;
            }
        }

        // I have never seen fields without field code but should it happen, let's take formatting from the separator.
        return mField.getSeparator();
    }

    public IFieldResultFormatApplier getFormatApplier()
    {
        Inline sourceNode = getSourceNode();

        if (sourceNode == null)
            return DoNothingFieldResultFormatApplier.INSTANCE;

        // WORDSNET-13464 The CHARFORMAT formatting is similar to default formatting, but is not equal to.
        return mField.getFormat().getGeneralFormats().hasFormat(GeneralFormat.CHAR_FORMAT)
            ? new CharFormatApplier(sourceNode)
            : new DefaultFormatApplier(sourceNode);
    }

    private /*final*/ Field mField;

    private static class DoNothingFieldResultFormatApplier implements IFieldResultFormatApplier
    {
        private DoNothingFieldResultFormatApplier()
        {
        }

        public void /*IFieldResultFormatApplier.*/applyFormat(NodeRange result)
        {
            // Do nothing.
        }

        static /*final*/ IFieldResultFormatApplier INSTANCE = new DoNothingFieldResultFormatApplier();
    }
}

