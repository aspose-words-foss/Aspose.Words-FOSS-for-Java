// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements the MERGESEQ field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// At the moment the MERGEREC and MERGESEQ fields implement the same functionality because we don't know for sure
/// how to skip records in Aspose.Words mail merge.
/// </remarks>
public class FieldMergeSeq extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Constant value = getUpdater().getDataProviders().getValue(this);
        if (value == null)
            return MergeFieldUtil.processUnusedField(this, "«Merge Sequence #»");

        return new FieldUpdateActionApplyResult(this, value);
    }
}

