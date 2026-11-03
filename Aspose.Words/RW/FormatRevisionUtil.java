// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/07/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArrayUtil;

/// <summary>
/// Implements methods to convert revision markup to positive-difference form.
/// </summary>
/// <remarks>
/// AM. Docx/Wml formats use BeforeChanges/AfterChanges format revision model and Doc and Aspose.Words
/// use BeforeChanges/PositiveDifference format revision model. For most cases these models are similar,
/// that's why in common revisions are read/written correctly without any additional processing.
///
/// But consider the example to see actual difference:
///
/// DocumentDefaults has SpacingAfter 10pt.
/// Paragraph had SpacingAfter 20pt and was changed to 10pt under change tracking.
///
/// For Docx/Wml Word writes such format revision as:
///   BeforeChanges: SpacingAfter = 20pt.
///   AfterChanges: None (because 10pt value is inherited from DocumentDefaults and Word never writes inherited attributes).
///
/// For Doc Word writes:
///   BeforeChanges: SpacingAfter = 20pt.
///   PositiveDifference: SpacingAfter = 10pt.
///
/// BeforeChanges/AfterChanges model is simpler and even for Doc format style format revision uses this model.
/// To get final markup we should do nothing, just select AfterChanges.
///
/// For PositiveDifference model to get final markup we should calculate BeforeChanges + PositiveDifference.
/// So we need to calculate PositiveDifference upon Docx/Wml reading otherwise final markup will be wrong.
///
/// I have strong feeling that we should change AW revision model and start to use BeforeChanges/AfterChanges model.
/// Going to discuss it later.
/// </remarks>
/*static*/ class FormatRevisionUtil
{
	/* Simulation of static class by using private constructor */
	private FormatRevisionUtil()
	{}

    /// <summary>
    /// Converts paragraph revision markup to PositiveDifference form. Should be called after all styles and lists are imported.
    /// </summary>
    static void convertToPositiveDifference(Paragraph para)
    {
        if (para.getParagraphBreakRunPr().getFormatRevision() != null)
        {
            final /*RunPrExpandFlags*/int RUN_PR_EXPAND_FLAGS = RunPrExpandFlags.DOCUMENT_DEFAULTS;
            final /*RunPrExpandFlags*/int REV_PR_EXPAND_FLAGS = RunPrExpandFlags.AFTER_CHANGES | RunPrExpandFlags.DOCUMENT_DEFAULTS;

            RunPr expandedRunPr = para.getExpandedParagraphBreakRunPr(RUN_PR_EXPAND_FLAGS);
            RunPr expandedRevPr = para.getExpandedParagraphBreakRunPr(REV_PR_EXPAND_FLAGS);

            expandedRevPr.collapse(expandedRunPr);
            para.getParagraphBreakRunPr().getFormatRevision().setRevPr(expandedRevPr);
        }

        if (para.getParaPr().getFormatRevision() != null)
        {
            /*ParaPrExpandFlags*/int paraPrExpandFlags = ParaPrExpandFlags.NORMAL;
            /*ParaPrExpandFlags*/int revPrExpandFlags = ParaPrExpandFlags.AFTER_CHANGES;

            // In table paragraphs should not use Document Defaults.
            if(!para.isInCell())
            {
                paraPrExpandFlags |= ParaPrExpandFlags.DOCUMENT_DEFAULTS;
                revPrExpandFlags |= ParaPrExpandFlags.DOCUMENT_DEFAULTS;
            }

            ParaPr expandedParaPr = para.getExpandedParaPr(paraPrExpandFlags);
            ParaPr expandedRevPr = para.getExpandedParaPr(revPrExpandFlags);

            // Istd is not inherited in revisions so absence of Istd
            // in "normal" revised properties means that styled paragraph goes to Normal,
            // ensure that Normal (0) Istd is implicitly set in revision.
            ParaPr revPr = (ParaPr)para.getParaPr().getFormatRevision().getRevPr();
            if (para.getParaPr().contains(ParaAttr.ISTD) && !revPr.contains(ParaAttr.ISTD))
                revPr.setAttr(ParaAttr.ISTD, 0);

            if (para.getParaPr().getIstd() == revPr.getIstd())
            {
                int originalListId = (/*int*/Integer)expandedParaPr.fetchAttr(ParaAttr.LIST_ID);
                int finalListId = (/*int*/Integer)expandedRevPr.fetchAttr(ParaAttr.LIST_ID);

                ParaPr temp = new ParaPr();
                expandedRevPr.moveTo(temp, ParaAttr.LIST_ID);
                expandedRevPr.moveTo(temp, ParaAttr.LIST_LEVEL);

                if (originalListId != finalListId)
                {
                    // WORDSNET-14997 Since we remove left indent when list revision accepted we need to preserve
                    // this indent in revised properties.
                    // I believe that we should also preserve all list related attributes:
                    // LeftIndent, FirstLineIndent and TabStops but lets postpone for a while.
                    expandedRevPr.moveTo(temp, ParaAttr.LEFT_INDENT);
                    // WORDSNET-19608 Preserve "hanging" attribute in revised properties.
                    expandedRevPr.moveTo(temp, ParaAttr.FIRST_LINE_INDENT);
                }

                expandedRevPr.collapse(expandedParaPr);

                // Restore saved attributes.
                temp.copyTo(expandedRevPr);

                para.getParaPr().getFormatRevision().setRevPr(expandedRevPr);
            }
        }
    }

    /// <summary>
    /// Converts style revision markup to PositiveDifference form. Should be called after all styles and lists are imported.
    /// </summary>
    static void convertToPositiveDifference(Style style)
    {
        if (style.getParaPr().getFormatRevision() != null)
        {
            ParaPr normalParaPr = style.getExpandedParaPr(ParaPrExpandFlags.DOCUMENT_DEFAULTS);
            ParaPr revisedParaPr = style.getExpandedParaPr(ParaPrExpandFlags.AFTER_CHANGES | ParaPrExpandFlags.DOCUMENT_DEFAULTS);

            normalParaPr.remove(ParaAttr.ISTD);
            revisedParaPr.remove(ParaAttr.ISTD);

            revisedParaPr.collapse(normalParaPr);
            style.getParaPr().getFormatRevision().setRevPr(revisedParaPr);
        }

        if (style.getRunPr().getFormatRevision() != null)
        {
            RunPr normalRunPr = style.getExpandedRunPr(RunPrExpandFlags.DOCUMENT_DEFAULTS);
            RunPr revisedRunPr = style.getExpandedRunPr(RunPrExpandFlags.AFTER_CHANGES | RunPrExpandFlags.DOCUMENT_DEFAULTS);

            normalRunPr.remove(FontAttr.ISTD);
            revisedRunPr.remove(FontAttr.ISTD);

            revisedRunPr.collapse(normalRunPr);
            style.getRunPr().getFormatRevision().setRevPr(revisedRunPr);
        }
    }

    /// <summary>
    /// Converts run revision markup to PositiveDifference form. Should be called after all styles and lists are imported.
    /// </summary>
    static void convertToPositiveDifference(IInline inlineNode)
    {
        if (inlineNode.getRunPr_IInline().getFormatRevision() == null)
            return;


        // WORDSNET-15583 Paragraph style has to be skipped on expanding when applying of style was canceled.
        // WORDSNET-24550 Paragraph style should be ignored only in "before" changes.
        /*RunPrExpandFlags*/int beforeExpandFlags = RunPrExpandFlags.DOCUMENT_DEFAULTS;
        /*RunPrExpandFlags*/int afterExpandFlags = RunPrExpandFlags.DOCUMENT_DEFAULTS | RunPrExpandFlags.AFTER_CHANGES;
        if (inlineNode.getParentParagraph_IInline().getParaPr().hasIstdRevision())
            beforeExpandFlags |= RunPrExpandFlags.NO_PARA_STYLE;


        RunPr beforeRunPr = inlineNode.getExpandedRunPr_IInline(beforeExpandFlags);
        RunPr afterRunPr = inlineNode.getExpandedRunPr_IInline(afterExpandFlags);

        // WORDSNET-9784 If the style was applied during a formatting revision we should not perform collapsing.
        if (afterRunPr.containsKey(FontAttr.ISTD))
            return;

        // Convert "after" changes to positive difference.
        afterRunPr.collapse(beforeRunPr);

        // WORDSNET-24550 When there is a parent paragraph style revision, then only attributes
        // that exist in direct "before" properties collection should be added as positive difference
        // to "after" changes RevPr collection.
        if (inlineNode.getParentParagraph_IInline().getParaPr().hasIstdRevision())
        {
            for (int i = 0; i < inlineNode.getRunPr_IInline().getCount(); i++)
            {
                int key = inlineNode.getRunPr_IInline().getKey(i);
                // Skip ignorable attributes.
                if (ArrayUtil.binarySearch(G_IGNORE_ATTRS, 0, G_IGNORE_ATTRS.length, key) == 0)
                    continue;

                // Add or remove collapsed value.
                if (afterRunPr.get(key) != null)
                    inlineNode.getRunPr_IInline().getFormatRevision().getRevPr().set(key, afterRunPr.get(key));
                else
                    inlineNode.getRunPr_IInline().getFormatRevision().getRevPr().remove(key);
            }
        }
        else
        {
            inlineNode.getRunPr_IInline().getFormatRevision().setRevPr(afterRunPr);
        }
    }

    // The set of ignorable attributes.
    private static /*final*/ int[] G_IGNORE_ATTRS = new int[]
    {
        RevisionAttr.DELETE_REVISION /*12*/,
        RevisionAttr.MOVE_FROM_REVISION /*13*/,
        RevisionAttr.INSERT_REVISION /*14*/,
        RevisionAttr.MOVE_TO_REVISION /*15*/,
        FontAttr.RSID_R_PR /* 30 */,
        FontAttr.RSID_R /* 40 */,
        RevisionAttr.FORMAT_REVISION /* 10010 */
    };
}

