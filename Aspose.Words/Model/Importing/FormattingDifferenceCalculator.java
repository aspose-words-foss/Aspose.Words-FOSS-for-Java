// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/10/2017 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import com.aspose.Debug;
import com.aspose.IntToIntDictionary;
import java.util.HashMap;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Implements calculation formatting difference between source and destination documents.
/// </summary>
class FormattingDifferenceCalculator
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FormattingDifferenceCalculator(ImportContext context)
    {
        mContext = context;

        // Prepare frequently used fully expanded attributes.
        Style styleNormal = context.getDstStyles().getBySti(StyleIdentifier.NORMAL, false);
        if (styleNormal != null)
        {
            /*RunPrExpandFlags*/int runPrExpandFlags = RunPrExpandFlags.REVISED;
            // WORDSNET-25741 As we do not collapse attributes in Merger mode,
            // we don't need to expand global defaults.
            if (!mContext.getImportFormatOptions().isMerger())
                runPrExpandFlags |= RunPrExpandFlags.GLOBAL_DEFAULTS;

            final /*ParaPrExpandFlags*/int PARA_PR_EXPAND_FLAGS = ParaPrExpandFlags.REVISED;

            mExpNormal.setRunPr(styleNormal.getExpandedRunPr(runPrExpandFlags));
            mExpNormal.setParaPr(styleNormal.getExpandedParaPr(PARA_PR_EXPAND_FLAGS));

            mExpNormalWithDefaults.setRunPr(styleNormal.getExpandedRunPr(runPrExpandFlags | RunPrExpandFlags.DOCUMENT_DEFAULTS));
            mExpNormalWithDefaults.setParaPr(styleNormal.getExpandedParaPr(PARA_PR_EXPAND_FLAGS | ParaPrExpandFlags.DOCUMENT_DEFAULTS));
        }
        else
        {
            // Get expanded global defaults.
            mExpNormal.getRunPr().expandTo(mExpNormal.getRunPr(), true);
            // Paragraph properties in any case used with document defaults. So, there is not a reason to expand
            // paragraph properties for the case which does not consider document defaults.

            // Get expanded global + document defaults. Note, the source document style defaults should be used.
            // WORDSNET-26869 As we do not collapse attributes in Merger mode, we don't need to expand global defaults.
            context.getSrcStyles().getDefaultRunPr().expandTo(mExpNormalWithDefaults.getRunPr(), !mContext.getImportFormatOptions().isMerger());

            // WORDSNET-23865 Do not expand global defaults to avoid appearing text frame attributes in the direct formatting.
            context.getSrcStyles().getDefaultParaPr().expandTo(mExpNormalWithDefaults.getParaPr(), false);

            // The Normal style does not exist in a destination document, so it will be either imported from the source
            // document or it will be created from the BuiltIn styles collection. We need to determine this to get
            // correct Normal style properties for expand cache. The most correct way to do this would be to check whether
            // there is any paragraph node for importing. But when this constructor is invoked,
            // mContext.TopmostNode property may not be set yet, so it is not so easy to determine here which types
            // of nodes will be imported. As this is a very rare case, lets postpone this for a while and always
            // use Normal style from a source document.
            styleNormal = context.getSrcStyles().getBySti(StyleIdentifier.NORMAL, false);
            if (styleNormal != null)
            {
                styleNormal.getRunPr().expandTo(mExpNormal.getRunPr(), false);

                styleNormal.getRunPr().expandTo(mExpNormalWithDefaults.getRunPr(), false);
                styleNormal.getParaPr().expandTo(mExpNormalWithDefaults.getParaPr(), false);
            }
        }

        mNormal = styleNormal;
    }

    /// <summary>
    /// Calculates attributes difference in source and destination documents for the <paramref name="srcInline"/>
    /// and applies it to the <paramref name="dstInline"/>.
    /// </summary>
    void apply(IInline srcInline, IInline dstInline)
    {
        RunPr runPrDiff = getRunPrDiff(srcInline, false);

        // Preserve edit revisions in case when revision tracking is enabled
        // for destination document (see Document.StartTrackRevisions()).
        if (dstInline.getRunPr_IInline().hasRevisions())
            dstInline.getRunPr_IInline().mirrorTo(runPrDiff, G_REVISION_ATTRS);

        dstInline.setRunPr_IInline(runPrDiff);

        if (srcInline.getRunPr_IInline().hasFormatRevision())
        {
            WordAttrCollection revPr = dstInline.getRunPr_IInline().getFormatRevision().getRevPr();
            revPr.clear();
            getRunPrDiff(srcInline, true).expandTo(revPr);

            // We store in model difference between original and revised attributes.
            // IN. I don't use FormatRevisionUtil.ConvertToPositiveDifference() here because we
            // have already expanded attributes here and there is no any additional processing
            // in that method is performed. In addition, there is no parent paragraph is set yet,
            // so expanding these attributes is pointless.
            revPr.collapse(dstInline.getRunPr_IInline());
        }
    }

    /// <summary>
    /// Calculates attributes difference in source and destination documents for the <paramref name="srcPara"/>
    /// and applies it to the <paramref name="dstPara"/>.
    /// </summary>
    void apply(Paragraph srcPara, Paragraph dstPara)
    {
        // Word does not expand style difference in original revision.
        // It just creates style if it is a builtIn style or imports it,
        // if it is a user defined style.
        if (srcPara.getParaPr().hasFormatRevision())
        {
            if (srcPara.getParaPr().getIstd() != 0)
                dstPara.getParaPr().setIstd(importOrCreateStyle(srcPara.getParagraphStyle()).getIstd());

            // Calculate revised ParaPr.
            WordAttrCollection revPr = dstPara.getParaPr().getFormatRevision().getRevPr();
            revPr.clear();
            getParaPrDiff(srcPara, true).expandTo(revPr);
            // We store in model difference between original and revised attributes.
            FormatRevisionUtil.convertToPositiveDifference(dstPara);
        }
        else
        {
            ParaPr paraPrDiff = getParaPrDiff(srcPara, false);
            // IN. If revision tracking is enabled for the destination document
            // (see Document.StartTrackRevisions()), then revision could be created
            // for the only destination paragraph and we should preserve it in that case.
            // Also, I think that only edit revisions can be tracked by AW for a moment,
            // so I don't care here about Format revisions.
            if (dstPara.getParaPr().hasRevisions())
                dstPara.getParaPr().mirrorTo(paraPrDiff, G_REVISION_ATTRS);

            dstPara.setParaPr(paraPrDiff);
        }

        if (srcPara.getParagraphBreakRunPr().hasFormatRevision())
        {
            // Calculate revised ParagraphBreakRunPr.
            WordAttrCollection revPr = dstPara.getParagraphBreakRunPr().getFormatRevision().getRevPr();
            revPr.clear();
            getParagraphBreakRunPrDiff(srcPara, true).expandTo(revPr);
            // We store in model difference between original and revised attributes.
            // IN. I don't use FormatRevisionUtil.ConvertToPositiveDifference() here because we
            // have already expanded attributes here and there is no any additional processing
            // in that method is performed.
            revPr.collapse(dstPara.getParagraphBreakRunPr());
        }
        else
        {
            // Calculate ParagraphBreakRunPr difference between source and destination documents.
            RunPr breakRunPrDiff = getParagraphBreakRunPrDiff(srcPara, false);

            // Preserve edit revisions in case when revision tracking is enabled
            // for destination document (see Document.StartTrackRevisions()).
            if (dstPara.getParagraphBreakRunPr().hasRevisions())
                dstPara.getParagraphBreakRunPr().mirrorTo(breakRunPrDiff, G_REVISION_ATTRS);

            dstPara.setParagraphBreakRunPr(breakRunPrDiff);
        }
    }

    /// <summary>
    /// Notifies a specified Node is visiting.
    /// </summary>
    void visitNode(Node node)
    {
        switch (node.getNodeType())
        {
            case NodeType.FIELD_START:
            {
                int count = 0;
                RefInt referenceToCount = new RefInt(count);
                msDictionary.tryGetValue(mFieldsCount, ((FieldChar)node).getFieldType(), /*out*/ referenceToCount);
                count = referenceToCount.get();
                mFieldsCount.put(((FieldChar)node).getFieldType(), ++count);
                break;
            }

            case NodeType.FIELD_END:
            {
                int count = 0;
                RefInt referenceToCount = new RefInt(count);
                msDictionary.tryGetValue(mFieldsCount, ((FieldChar)node).getFieldType(), /*out*/ referenceToCount);
                count = referenceToCount.get();
                mFieldsCount.put(((FieldChar)node).getFieldType(), --count);
                break;
            }
        }
    }

    /// <summary>
    /// Returns RunPr that contains attributes difference of the specified
    /// <paramref name="srcInline"/> in source and destination documents.
    /// </summary>
    /// <remarks>
    /// It almost completely duplicates code in <see cref="GetParagraphBreakRunPrDiff"/>,
    /// but at the moment I do not see a simple way to get rid of it.
    /// </remarks>
    private RunPr getRunPrDiff(IInline srcInline, boolean isRevised)
    {
        /*RunPrExpandFlags*/int expandFlags =  RunPrExpandFlags.NO_CHANGE_COMMENT_SIZE;
        // Expand with global defaults to get rid of extra global default properties
        // on collapsing with destination Normal style.
        // WORDSNET-25741 We do not collapse attributes in Merger mode.
        if (!mContext.getImportFormatOptions().isMerger())
            expandFlags |= RunPrExpandFlags.GLOBAL_DEFAULTS;

        // We treat attributes as 'final' when there is no formatting revision,
        // so we use 'revised' flag, because revision can be in a some parent style.
        if (!srcInline.getRunPr_IInline().hasFormatRevision() || isRevised)
            expandFlags |= RunPrExpandFlags.REVISED;

        // When we import, for example, just some inline node, without its parent paragraph, we should not take into
        // account properties of that paragraph. When we copy in Word some text into clipboard without its paragraph mark,
        // and then paste this text into another place, Word does not take into account properties of that paragraph,
        // just because there are no any paragraphs in the clipboard.
        Paragraph parentPara = srcInline.getParentParagraph_IInline();
        boolean isImportParentPara = (parentPara != null) && NodeUtil.isAncestorOrSelf(parentPara, mContext.getTopmostNode());
        expandFlags |= (isImportParentPara) ? RunPrExpandFlags.DOCUMENT_DEFAULTS : RunPrExpandFlags.NO_PARA_STYLE;

        boolean isImportParentTable = isImportParentPara && (parentPara.getParentTable() != null)
            && NodeUtil.isAncestorOrSelf(parentPara.getParentTable(), mContext.getTopmostNode());
        if (!isImportParentTable)
            expandFlags |= RunPrExpandFlags.NO_TABLE_STYLE;

        // WORDSNET-28135 Ignore Hyperlink style in TOC for Merger feature.
        if (mContext.getImportFormatOptions().isMerger() && isInToc() && isInHyperlink())
            expandFlags |= RunPrExpandFlags.IGNORE_HYPERLINK_CHAR_STYLE;

        RunPr diffRunPr = srcInline.getExpandedRunPr_IInline(expandFlags);

        mContext.applyThemeDifference(diffRunPr);

        // WORDSNET-17186 We should not apply Themes to a destination Normal attributes
        // even Themes are different to mimic Word behavior.
        RunPr dstNormalRunPr = getNormalRunPr(expandFlags);

        // Mimic Word behavior and calculate difference with the destination Normal style.
        // It always will be Normal style, because after expanding source attributes,
        // Word removes style.
        // WORDSNET-25741 If destination document has default value in some direct Normal attribute
        // and the same value is in corresponding direct attribute of the source Node and this Node
        // is inside a Table with some TableStyle that also has this attribute set, then this
        // attribute will be lost in destination document due to collapsing with Normal style,
        // if same TableStyle in destination document has different value in corresponding attribute.
        // So, to preserve original formatting of the source document in Merger, we should not do collapse,
        // because in this mode we have to preserve exactly source formatting, but not to mimic Word.
        if (mContext.getImportFormatOptions().isMerger())
        {
            // WORDSNET-26603 We preserve some styles in Merger, so destination
            // style will not be 'Normal' as usual and we have to check this case.
            int srcParaIstd = (/*int*/Integer)srcInline.getParentParagraph_IInline().fetchParaAttr(ParaAttr.ISTD, RevisionsView.FINAL);
            if (isNonRemovableMergerIstd(srcParaIstd))
            {
                int dstParaIstd = getDstIstd(srcInline.getParentParagraph_IInline());
                Style dstStyle = mContext.getDstStyles().getByIstd(dstParaIstd, false);
                if (dstStyle != null)
                    dstNormalRunPr = dstStyle.getExpandedRunPr(expandFlags);
            }

            for (int key : dstNormalRunPr.getKeys())
                diffRunPr.setAttrNoOverride(key, RunPr.getDefaultAttr(key));
        }
        else
        {
            // WORDSNET-27678 Don't collapse revised attributes that also exist
            // in before-changes attributes over a destination Normal style,
            // as we keep in model only difference between before and after changes.
            int[] keysToIgnore = (isRevised) ? srcInline.getRunPr_IInline().getKeys() : new int[] {};
            diffRunPr.collapse(dstNormalRunPr, keysToIgnore);
        }

        diffRunPr.remove(FontAttr.ISTD);

        // Restore non-collapsible attributes. See StyleCollection.CopyRunPr() for details.
        RunPr srcRunPr = isRevised ? (RunPr)srcInline.getRunPr_IInline().getFormatRevision().getRevPr() : srcInline.getRunPr_IInline();
        srcRunPr.mirrorTo(diffRunPr, RunPr.NON_COLLAPSIBLE_FONT_KEYS);

        if (isImportParentTable)
        {
            // Mimic Word behavior: remove attributes, which are equal to the attributes of the imported
            // parent table style when it is not overriden by Normal style attributes.
            // See TestJira14587ParentTableStyleRemoveEqualAttrs() and Test23366() for example.
            Style dstTableStyle = importParentTableStyle(parentPara);
            removeEqualAttrs(diffRunPr, dstTableStyle, isRevised);
        }

        return diffRunPr;
    }

    /// <summary>
    /// Returns ParaPr that contains attributes difference of the specified
    /// <paramref name="srcPara"/> in source and destination documents.
    /// </summary>
    private ParaPr getParaPrDiff(Paragraph srcPara, boolean isRevised)
    {
        // WORDSNET-20066 Global defaults are excluded from the expanding.
        // The global default properties remain in the model, but so far there is no problem.
        /*ParaPrExpandFlags*/int expandFlags = ParaPrExpandFlags.NORMAL;

        if (srcPara.getParentTable() != null)
        {
            // If we import only paragraph itself, without parent table,
            // we should ignore parent table properties on expand.
            if (NodeUtil.isAncestorOrSelf(srcPara.getParentTable(), mContext.getTopmostNode()))
                expandFlags |= ParaPrExpandFlags.EXPAND_TABLE_STYLE;
        }

        // Defaults values of documents have to be taken in attention at any case (no matter
        // where paragraph placed - inside table or not). There is issue, when two documents have
        // different defaults  and table style in the source document does not override default
        // attributes. So, AW loses formatting, when defaults values of source document are not
        // used. For example, see TestJira16660.
        expandFlags |= ParaPrExpandFlags.DOCUMENT_DEFAULTS;

        // We treat attributes as 'final' when there is no formatting revision,
        // so we use 'revised' flag, because revision can be in a some parent style.
        if (!srcPara.getParaPr().hasFormatRevision() || isRevised)
            expandFlags |= ParaPrExpandFlags.REVISED;

        ParaPr diffParaPr = srcPara.getExpandedParaPr(expandFlags);

        // Calculate difference with the destination Normal style.
        // It always will be Normal style, because after expanding source attributes,
        // Word removes paragraph style.
        ParaPr dstNormalParaPr = getNormalParaPr(expandFlags);
        // WORDSNET-25741 We do not collapse in Merger, see details in GetRunPrDiff().
        if (mContext.getImportFormatOptions().isMerger())
        {
            // WORDSNET-26603 We preserve some styles in Merger, so destination
            // style will not be 'Normal' as usual and we have to check this case.
            if (isNonRemovableMergerIstd(srcPara.getParaPr().getIstd()))
            {
                // WORDSNET-26667. As we here because the style of the paragraph will not be reset to 'Normal' below,
                // we have to replace it with the corresponding correct style from the destination document.
                diffParaPr.setIstd(getDstIstd(srcPara));
                Style dstStyle = mContext.getDstStyles().getByIstd(diffParaPr.getIstd(), false);
                if (dstStyle != null)
                    dstNormalParaPr = dstStyle.getExpandedParaPr(expandFlags);
            }

            for (int key : dstNormalParaPr.getKeys())
                diffParaPr.setAttrNoOverride(key, ParaPr.fetchDefaultAttr(key));

            // WORDSNET-25832 Despite we expand Style into direct attributes, Word displays differently
            // paragraphs depending on either the following styles are set explicitly, or not.
            if (!isNonRemovableMergerIstd(srcPara.getParaPr().getIstd()))
            {
                diffParaPr.remove(ParaAttr.ISTD);
            }
            else if (StyleIndex.isHeadingIstd(diffParaPr.getIstd()))
            {
                // Actually, this does not affect output result, but we forced to do this to avoid
                // failing Test25832. See DocumentPostLoader.FixUpParagraphOutlineLevel() for details.
                diffParaPr.remove(ParaAttr.OUTLINE_LEVEL);
            }
        }
        else
        {
            diffParaPr.collapse(dstNormalParaPr);
            diffParaPr.remove(ParaAttr.ISTD);
        }

        // Remove floating attributes in TextBoxes. See for details Paragraph.GetExpandedParaPr().
        if (srcPara.isInShape())
            diffParaPr.removeFloatingAttrs();

        // Restore non-collapsible attribute. See StyleCollection.CopyParaPr() for details.
        ParaPr srcParaPr = isRevised ? (ParaPr)srcPara.getParaPr().getFormatRevision().getRevPr() : srcPara.getParaPr();
        srcParaPr.mirrorTo(diffParaPr, G_NON_COLLAPSIBLE_PARA_KEYS);

        // Import list.
        importList(srcPara, diffParaPr);

        if ((expandFlags & ParaPrExpandFlags.EXPAND_TABLE_STYLE) != 0)
        {
            // Mimic Word behavior, see comments in GetRunPrDiff() for details.
            Style dstTableStyle = importParentTableStyle(srcPara);
            removeEqualAttrs(diffParaPr, dstTableStyle, isRevised);
        }

        return diffParaPr;
    }

    /// <summary>
    /// Returns RunPr that contains attributes difference for <see cref="Paragraph.ParagraphBreakRunPr"/>
    /// of the specified <paramref name="srcPara"/> in source and destination documents.
    /// </summary>
    private RunPr getParagraphBreakRunPrDiff(Paragraph srcPara, boolean isRevised)
    {
        // Expand with global defaults to get rid of extra global default properties
        // on collapsing with destination Normal style.
        /*RunPrExpandFlags*/int expandFlags = RunPrExpandFlags.DOCUMENT_DEFAULTS | RunPrExpandFlags.NO_CHANGE_COMMENT_SIZE;
        // WORDSNET-25741 We do not collapse attributes in Merger mode.
        if (!mContext.getImportFormatOptions().isMerger())
            expandFlags |= RunPrExpandFlags.GLOBAL_DEFAULTS;


        boolean isImportParentTable = (srcPara.getParentTable() != null) && NodeUtil.isAncestorOrSelf(srcPara.getParentTable(), mContext.getTopmostNode());
        if (!isImportParentTable)
            expandFlags |= RunPrExpandFlags.NO_TABLE_STYLE;

        // We treat attributes as 'final' when there is no formatting revision,
        // so we use 'revised' flag, because revision can be in a some parent style.
        if (!srcPara.getParagraphBreakRunPr().hasFormatRevision() || isRevised)
            expandFlags |= RunPrExpandFlags.REVISED;

        RunPr diffRunPr = srcPara.getExpandedParagraphBreakRunPr(expandFlags);

        if (mContext.isThemeFontsDifferent())
            Theme.apply(mContext.getSrcDoc().getThemeInternal(), diffRunPr);

        // WORDSNET-17186 We should not apply Themes to a destination Normal attributes
        // even Themes are different to mimic Word behavior.
        RunPr dstNormalRunPr = getNormalRunPr(expandFlags);

        // Mimic Word behavior and calculate difference with the destination Normal style.
        // It always will be Normal style, because after expanding source attributes,
        // Word removes style at the paragraph.
        // WORDSNET-25741 We do not collapse in Merger, see details in GetRunPrDiff().
        if (mContext.getImportFormatOptions().isMerger())
        {
            // WORDSNET-26603 We preserve some styles in Merger, so destination
            // style will not be 'Normal' as usual and we have to check this case.
            int paraIstd = (/*int*/Integer)srcPara.fetchParaAttr(ParaAttr.ISTD, RevisionsView.FINAL);
            if (isNonRemovableMergerIstd(paraIstd))
            {
                Style dstStyle = mContext.getDstStyles().getByIstd(paraIstd, false);
                if (dstStyle != null)
                    dstNormalRunPr = dstStyle.getExpandedRunPr(expandFlags);
            }

            for (int key : dstNormalRunPr.getKeys())
                diffRunPr.setAttrNoOverride(key, RunPr.getDefaultAttr(key));
        }
        else
        {
            diffRunPr.collapse(dstNormalRunPr);
        }

        diffRunPr.remove(FontAttr.ISTD);

        // Restore non-collapsible attributes. See StyleCollection.CopyRunPr() for details.
        RunPr srcBreakRunPr = isRevised ? (RunPr)srcPara.getParagraphBreakRunPr().getFormatRevision().getRevPr() : srcPara.getParagraphBreakRunPr();
        srcBreakRunPr.mirrorTo(diffRunPr, RunPr.NON_COLLAPSIBLE_FONT_KEYS);

        if (isImportParentTable)
        {
            // Mimic Word behavior, see comments in GetRunPrDiff() for details.
            Style dstTableStyle = importParentTableStyle(srcPara);
            removeEqualAttrs(diffRunPr, dstTableStyle, isRevised);
        }

        return diffRunPr;
    }

    /// <summary>
    /// Returns style from the <see cref="ImportContext.DstStyles"/> with the same name as <paramref name="srcStyle"/>.
    /// If there is no such style and it is <see cref="Style.BuiltIn"/> and it does not
    /// exist in destination collection, then creates new one from the scratch.
    /// Otherwise, imports <paramref name="srcStyle"/> into destination collection.
    /// </summary>
    private Style importOrCreateStyle(Style srcStyle)
    {
        Style dstStyle = mContext.getDstStyles().getByName(srcStyle.getName(), false);
        if (dstStyle != null)
            return dstStyle;

        if (srcStyle.getBuiltIn())
        {
            StyleCollection dstStyles = mContext.getDstStyles();

            Style existingStyle = dstStyles.findLocaleIndependentMatch(srcStyle);
            if (existingStyle != null)
                return existingStyle;

            Style builtInStyle = dstStyles.getBuiltInStyles().getBySti(srcStyle.getStyleIdentifier(), false);
            return dstStyles.addCopy(builtInStyle);
        }

        return mContext.getDstStyles().importStyle(srcStyle);
    }

    /// <summary>
    /// Imports parent table style.
    /// </summary>
    private Style importParentTableStyle(Paragraph srcPara)
    {
        Style srcTableStyle = InlineHelper.getTableStyle(srcPara);
        return (srcTableStyle == null) ? null : mContext.getDstStyles().importStyle(mContext, srcTableStyle);
    }

    /// <summary>
    /// Imports list from a source paragraph to a specified destination paragraph properties.
    /// </summary>
    private void importList(Paragraph srcPara, ParaPr dstParaPr)
    {
        if (dstParaPr.contains(ParaAttr.LIST_ID))
        {
            // WORDSNET - 26044 We clone ParaPr containing the source difference attributes to avoid the
            // source ParaPr mutation during ImportList() process.
            ParaPr paraPrClone = AttrCollectionExtension.deepClone(dstParaPr);
            NodeImporter.importList(paraPrClone, dstParaPr, mContext);
        }
        else
        {
            // WORDSNET-27734 When there is no ListId set in direct attributes of a destination paragraph,
            // Word also checks if there is the same destination style of the imported paragraph and
            // if it is numbered, then collapses its numbering properties over a Normal style,
            // i.e., it sets listId = 1 and listLevel = 0 in this case.
            final int DEFAULT_LIST_ID = 1;
            final int DEFAULT_LIST_LEVEL = 0;

            int dstIstd = getDstIstd(srcPara);
            Style dstStyle = mContext.getDstStyles().getByIstd(dstIstd, false);
            if (dstStyle == null)
                return;

            if (dstStyle.getParaPr().getListId() == 0)
                return;

            dstParaPr.setListId((mContext.getDstLists().getListByListId(DEFAULT_LIST_ID) == null)
                ? mContext.getDstLists().add(ListTemplate.NUMBER_DEFAULT).getListId()
                : DEFAULT_LIST_ID);

            dstParaPr.setListLevel(DEFAULT_LIST_LEVEL);
        }
    }

    /// <summary>
    /// Removes attributes from <paramref name="attrs"/> that are equal to the attributes in <paramref name="tableStyle"/>
    /// and missing in <see cref="mNormal"/> style at the same time.
    /// </summary>
    private void removeEqualAttrs(AttrCollection attrs, Style tableStyle, boolean isRevised)
    {
        if (tableStyle == null)
            return;

        boolean isIgnoreNormalSizeAndJustification =
            InlineHelper.isIgnoreNormalFontSize(mContext.getDstDoc(), (TableStyle)tableStyle);

        boolean isRunAttrs = (attrs instanceof RunPr);
        WordAttrCollection tableStyleAttrs = isRunAttrs ? tableStyle.getRunPr() : (WordAttrCollection)tableStyle.getParaPr();
        if (isRevised && tableStyleAttrs.hasFormatRevision())
            tableStyleAttrs = tableStyleAttrs.getFormatRevision().getRevPr();

        AttrCollection normalAttrs = null;
        if (mNormal != null)
            normalAttrs = isRunAttrs ? mNormal.getRunPr() : (AttrCollection)mNormal.getParaPr();

        for (int i = attrs.getCount() - 1; i >= 0; i--)
        {
            int key = attrs.getKey(i);
            Object tableVal = tableStyleAttrs.get(key);
            if (tableVal == null)
                continue;

            Object normalVal = (normalAttrs != null) ? normalAttrs.get(key) : null;
            // WORDSNET-19927 Process of removing equal attributes must consider compatibility
            // setting "overrideTableStyleFontSizeAndJustification".
            // WORDSNET-23366 Remove equal to table style attribute only when this attribute is not overriden by
            // the Normal style (because paragraph style has obviously precedence over a table style in hierarchy).
            if ((normalVal == null) ||
                (isIgnoreNormalSizeAndJustification && isIgnorableSizeAndJustification(key, normalVal)))
            {
                if (tableVal.equals(attrs.get(key)))
                    attrs.removeAt(i);
            }
        }
    }

    /// <summary>
    /// Returns true, if specified key and value should be ignored in accordance with <see cref="InlineHelper.IsIgnoreNormalFontSize"/>.
    /// </summary>
    private static boolean isIgnorableSizeAndJustification(int key, Object value)
    {
        switch (key)
        {
            case FontAttr.SIZE_BI:
            case FontAttr.SIZE:
                return ((/*int*/Integer)value == 24);

            case ParaAttr.ALIGNMENT:
                return ((/*ParagraphAlignment*/Integer)value == ParagraphAlignment.LEFT);

            default:
                return false;
        }
    }

    /// <summary>
    /// Returns RunPr from the cached Normal style with expanded attributes.
    /// </summary>
    private RunPr getNormalRunPr(/*RunPrExpandFlags*/int flags)
    {
        return ((flags & RunPrExpandFlags.DOCUMENT_DEFAULTS) != 0) ? mExpNormalWithDefaults.getRunPr() : mExpNormal.getRunPr();
    }

    /// <summary>
    /// Returns ParaPr from the cached Normal style with expanded attributes.
    /// </summary>
    private ParaPr getNormalParaPr(/*ParaPrExpandFlags*/int flags)
    {
        return ((flags & ParaPrExpandFlags.DOCUMENT_DEFAULTS) != 0) ? mExpNormalWithDefaults.getParaPr() : mExpNormal.getParaPr();
    }

    /// <summary>
    /// Returns true, if a specified istd should not be removed after style
    /// is expanded when calculating formatting difference for Merger.
    /// </summary>
    private boolean isNonRemovableMergerIstd(int istd)
    {
        if (StyleIndex.isHeadingIstd(istd))
            return true;

        Style style = mContext.getSrcStyles().getBySti(StyleIdentifier.LIST_PARAGRAPH, false);
        if ((style) != null && (style.getIstd() == istd))
            return true;

        return false;
    }

    /// <summary>
    /// Returns istd of the specified source paragraph that it will have in a destination document after the import.
    /// </summary>
    private int getDstIstd(Paragraph srcPara)
    {
        Debug.assertTrue(srcPara != null);

        // First check already imported styles.
        int dstIstd = mContext.getImportedIstds().get(srcPara.getParaPr().getIstd());
        if (!IntToIntDictionary.isNullSubstitute(dstIstd))
            return dstIstd;

        // Then check originally existed in destination document styles.
        Style dstStyleMatch = mContext.getDstStyles().findLocaleIndependentMatch(srcPara.getParagraphStyle());
        if (dstStyleMatch != null)
            return dstStyleMatch.getIstd();

        // Actually, when we calculate formatting difference, the style should be either
        // imported already, or exists in a destination document originally.
        // But let's handle the almost impossible case as well by just returning 0.
        return 0;
    }

    /// <summary>
    /// Gets a boolean value indicating whether the currently processing Node
    /// in corresponding <see cref="NodeImporter"/> is inside a TOC field.
    /// </summary>
    private boolean isInToc()
    {
        int count = 0;
        RefInt referenceToCount = new RefInt(count);
        msDictionary.tryGetValue(mFieldsCount, FieldType.FIELD_TOC, /*out*/ referenceToCount);
        count = referenceToCount.get();

        return count > 0;
    }

    /// <summary>
    /// Gets a boolean value indicating whether the currently processing Node
    /// in corresponding <see cref="NodeImporter"/> is inside a HYPERLINK field.
    /// </summary>
    private boolean isInHyperlink()
    {
        int count = 0;
        RefInt referenceToCount = new RefInt(count);
        msDictionary.tryGetValue(mFieldsCount, FieldType.FIELD_HYPERLINK, /*out*/ referenceToCount);
        count = referenceToCount.get();

        return count > 0;
    }

    /// <summary>
    /// Keeps number of a currently started in corresponding <see cref="NodeImporter"/> types of fields.
    /// Key: field type.
    /// Value: count.
    /// </summary>
    private /*final*/ HashMap<Integer, Integer> mFieldsCount = new HashMap<Integer, Integer>();

    /// <summary>
    /// Importing context.
    /// </summary>
    private /*final*/ ImportContext mContext;

    /// <summary>
    /// Non-collapsible paragraph attributes, <see cref="AttrCollection.IsIgnoredOnCollapse"/>.
    /// </summary>
    private static /*final*/ int[] G_NON_COLLAPSIBLE_PARA_KEYS = { ParaAttr.RSID_P, ParaAttr.SYS_ALIGNMENT_97, ParaAttr.SYS_LEFT_INDENT_97, ParaAttr.SYS_RIGHT_INDENT_97, ParaAttr.SYS_FIRST_LINE_INDENT_97 };

    /// <summary>
    /// Revision attributes.
    /// </summary>
    private static /*final*/ int[] G_REVISION_ATTRS =
    {
        RevisionAttr.DELETE_REVISION,
        RevisionAttr.INSERT_REVISION,
        RevisionAttr.MOVE_FROM_REVISION,
        RevisionAttr.MOVE_TO_REVISION,
        RevisionAttr.FORMAT_REVISION
    };

    /// <summary>
    /// Normal style of the destination document.
    /// </summary>
    private /*final*/ Style mNormal;

    /// <summary>
    /// Normal style with expanded attributes.
    /// </summary>
    /// <remarks>This is a cached version of Normal style with expanded attributes that is very frequently used
    /// in formatting difference calculation.</remarks>
    private /*final*/ Style mExpNormal = Style.create(StyleType.PARAGRAPH);

    /// <summary>
    /// Normal style with expanded attributes considering document Defaults.
    /// </summary>
    /// <remarks>This is a cached version of Normal style with expanded attributes that is very frequently used
    /// in formatting difference calculation.</remarks>
    private /*final*/ Style mExpNormalWithDefaults = Style.create(StyleType.PARAGRAPH);
}

