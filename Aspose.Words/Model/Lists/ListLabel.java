// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2009 by Viktor Sazhaev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ArrayUtil;


/// <summary>
/// Defines properties specific to a list label.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-lists/">Working with Lists</a> documentation article.</para>
/// </summary>
/// <dev>
/// <para>Implements <see cref="IRunAttrSource" /> interface treating list level direct formatting
/// as direct run attributes since they take priority over the other sources.</para>
/// <para><see cref="ListLabel"/> contains the most common textual representation of the list label
/// in its <see cref="LabelString"/> property.</para>
/// <para>There are some classes which need different textual representation of list labels. Use
/// <see cref="ListLabel"/>'s property <see cref="NumberState"/> which introduced to provide enough information
/// to build custom labels from the inside of such classes.</para>
/// <para>Both <see cref="LabelString"/> and <see cref="NumberState"/> are updated
/// by <see cref="Document.UpdateListLabels"/>.</para>
/// </dev>
public class ListLabel implements IRunAttrSource
{
    /// <summary>
    /// Ctor.
    /// You don't normally construct this object. They are constructed by other objects that can have list labels.
    /// </summary>
    ListLabel(Paragraph para)
    {
        mPara = para;
        mListFormat = para.getListFormat();
    }

    void setLabelStringAndValue(
        String[] labelFragments,
        String[] labelArabicNumbers,
        ListNumberState listNumberState,
        /*RevisionsView*/int view)
    {
        if (view == RevisionsView.ORIGINAL)
        {
            mLabelFragmentsOriginal = labelFragments;
            mLabelArabicNumbersOriginal = labelArabicNumbers;
            mNumberStateOriginal = listNumberState;
        }
        else
        {
            mLabelFragmentsFinal = labelFragments;
            mLabelArabicNumbersFinal = labelArabicNumbers;
            mNumberStateFinal = listNumberState;
        }
    }

    /// <summary>
    /// Gets an object containing enough information for building customized list label.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="Document.UpdateListLabels"/> method to update the value of this property.
    /// </remarks>
    ListNumberState getNumberState() { return mNumberStateOriginal; }

    /// <summary>
    /// Gets an object containing enough information for building customized list label.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="Document.UpdateListLabels"/> method to update the value of this property.
    /// </remarks>
    ListNumberState getNumberStateFinal() { return mNumberStateFinal; }

    /// <summary>
    /// Gets the list label font.
    /// </summary>
    public Font getFont()
    {
        if (mFont == null)
            mFont = new Font(this, mPara.getDocument());
        return mFont;
    }

    /// <summary>
    /// Checks whether the list label has text representation.
    /// TODO list label depends on list format which in turn depends on revision options.
    /// Most of the other properties of this class consequently depend on attributes of the displayed revision.
    /// However this property explicitly depends on the original revision which, I believe, might be wrong.
    /// At least the name of the property shall clearly say that it is original label being questioned.
    /// I would recommend thoroughly reviewing this class and how layout revision options are used in the document model.
    /// </summary>
    boolean hasChars() { return ArrayUtil.hasData(mLabelFragmentsOriginal); }

    /// <summary>
    /// Checks whether the list label has text representation in final revision.
    /// </summary>
    boolean hasCharsFinal() { return ArrayUtil.hasData(mLabelFragmentsFinal); }

    /// <summary>
    /// Gets a string array that represents list label split to fragments.
    /// </summary>
    /// <remarks>
    /// This property is required while building the document layout since each part of list label
    /// can have different font properties.
    /// Use the <see cref="Document.UpdateListLabels"/> method to update the value of this property.
    /// </remarks>
    String[] getLabelFragments() { return mLabelFragmentsOriginal; }

    String[] getLabelFragmentsOriginal() { return mLabelFragmentsOriginal; }

    String[] getLabelFragmentsFinal() { return mLabelFragmentsFinal; }

    String[] getLabelArabicNumbers() { return mLabelArabicNumbersOriginal; }

    String[] getLabelArabicNumbersOriginal() { return mLabelArabicNumbersOriginal; }

    String[] getLabelArabicNumbersFinal() { return mLabelArabicNumbersFinal; }

    /// <summary>
    /// Gets a string representation of list label.
    /// </summary>
    public String getLabelString()
    {
        Document doc = ms.as(mPara.getDocument(), Document.class);

        if ((doc != null) && (doc.getRevisionsView() == RevisionsView.FINAL))
            return getLabelStringFinal();

        return getLabelStringOriginal();
    }

    /// <summary>
    /// Gets a numeric value for this label.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="Document.UpdateListLabels"/> method to update the value of this property.
    /// </remarks>
    public int getLabelValue() { return (mNumberStateOriginal == null) ? 0 : mNumberStateOriginal.getNumber(); }

    /// <summary>
    /// Gets a numeric value for this label in final revision.
    /// </summary>
    /// <remarks>
    /// Use the <see cref="Document.UpdateListLabels"/> method to update the value of this property.
    /// </remarks>
    int getLabelValueFinal() { return (mNumberStateFinal == null) ? 0 : mNumberStateFinal.getNumber(); }

    String getLabelStringOriginal() { return ArrayUtil.stringArrayToString(mLabelFragmentsOriginal); }

    String getLabelStringFinal() { return ArrayUtil.stringArrayToString(mLabelFragmentsFinal); }

    /// <summary>
    /// Gets trailing character for this label.
    /// </summary>
    /*ListTrailingCharacter*/int getTrailingCharacter()
    {
        return (mNumberStateOriginal == null) ? ListTrailingCharacter.NOTHING
            : mNumberStateOriginal.getListLevel().getTrailingCharacter();
    }

    /// <summary>
    /// Gets trailing character for this label in final revision.
    /// </summary>
    /*ListTrailingCharacter*/int getTrailingCharacterFinal()
    {
        return (mNumberStateFinal == null) ? ListTrailingCharacter.NOTHING
            : mNumberStateFinal.getListLevel().getTrailingCharacter();
    }

    /// <summary>
    /// Gets a <see cref="ListLevel"/> instance used to build the label.
    /// </summary>
    ListLevel getListLevel() { return mListFormat.getListLevel(); }

    /// <summary>
    /// Gets a <see cref="ListLevel"/> instance used to build the label in final revision.
    /// </summary>
    ListLevel getListLevelFinal() { return mListFormat.getListLevelFinal(); }

    //>>>>>>>> #region  IRunAttrSource

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key)
    {
        return ((IRunAttrSource)this).getDirectRunAttr(key, RevisionsView.ORIGINAL);
    }

    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/getDirectRunAttr(int key, /*RevisionsView*/int revisionsView)
    {
        ListLevel listLevel = mListFormat.getListLevel();
        return (listLevel != null) ? listLevel.getRunPr().get(key) : null;
    }

    /// <summary>
    /// Performs the bottom-up single attribute resolution.
    /// </summary>
    @ReservedForInternalUse @Deprecated
    public Object /*IRunAttrSource.*/fetchInheritedRunAttr(int key)
    {
        // RK This looks like needs rethought and simplification.

        // WORDSNET-3944 Label font does not match in DOC and PDF.
        // RK I don't fully understand the algorithm to resolve this attribute in MS Word,
        // just trying to do what MS Word seems to do.

        // Here we should also check how style affects formatting if referenced from the level!
        // Maybe its attributes should be considered after direct formatting of the paragraph break font.
        // Moved querying from ListLevel to GetDirectRunAttr.

        // 1. Try to get the attribute from paragraph break direct properties.
        // Seemingly MS Word always takes it from there if it is directly specified.
        // Don't take decoration attributes from paragraph break! Maybe we should filter out
        // in another place but MS Word doesn't take paragraph break in consideration for
        // decoration attributes. (Here is another trick: if underline is defined elsewhere then
        // underline color from paragraph break will affect formatting! Don't remove.)
        // If it's a bulleted list we'll also exclude some other attributes. I found that at least should
        // exclude bold and italic (but not emboss and engrave). See TestDefect861.doc.
        if ((key != FontAttr.UNDERLINE) && (key != FontAttr.STRIKE_THROUGH) && (key != FontAttr.DOUBLE_STRIKE_THROUGH))
        {
            boolean excludeParaBreakProps = false;
            if ((key == FontAttr.BOLD) || (key == FontAttr.ITALIC))
            {
                ListLevel listLevel = mListFormat.getListLevel();
                excludeParaBreakProps = (listLevel != null) && (listLevel.getNumberStyle() == NumberStyle.BULLET);

            }

            if (!excludeParaBreakProps)
            {
                Object value = mPara.getParagraphBreakRunPr().get(key);
                if (value != null)
                    return processListLabelAttributeBoolExAware(value, key);
            }
        }

        // 2. If the attribute is not obtained above, get it from the paragraph style.
        // Here we have some problem with attribute resolution. WORDSNET-9953.
        // WORDSNET-10833 The problem occurred because paragraph style is list style and does not have Font.
        if (mPara.getParagraphStyle().getFont() != null)
        {
            // WORDSNET-15348 It seems that the underline attribute is never applied from the paragraph to the list label.
            if (key != FontAttr.UNDERLINE)
                return mPara.getParagraphStyle().getFont().fetchAttr(key);
        }

        // 3. If paragraph style does not have Font definition, try get the attribute from base style.
        Style baseStyle = mPara.getDocument().getStyles().get(mPara.getParagraphStyle().getBaseStyleName());
        if (baseStyle != null && baseStyle.getFont() != null)
            return baseStyle.getFont().fetchAttr(key);

        // 4. Finally, get the attribute from Normal style.
        return mPara.getDocument().getStyles().getByStyleIdentifier(StyleIdentifier.NORMAL).getFont().fetchAttr(key);
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/setRunAttr(int key, Object value)
    {
        ListLevel listLevel = mListFormat.getListLevel();
        if (listLevel != null)
            listLevel.getRunPr().setAttr(key, value);
        else
            throwCannotChangeException();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/removeRunAttr(int key)
    {
        ListLevel listLevel = mListFormat.getListLevel();
        if (listLevel != null)
            listLevel.getRunPr().remove(key);
        else
            throwCannotChangeException();
    }

    @ReservedForInternalUse @Deprecated
    public void /*IRunAttrSource.*/clearRunAttrs()
    {
        ListLevel listLevel = mListFormat.getListLevel();
        if (listLevel != null)
            listLevel.getRunPr().clear();
        else
            throwCannotChangeException();
    }

    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets the complete collection of attributes how the list label is formatted.
    /// Performs the top-bottom complete attribute expansion.
    /// </summary>
    RunPr getExpandedRunPr(boolean expandListLevelFormatting, /*RunPrExpandFlags*/int flags)
    {
        // Basically we need to do exactly opposite of GetDirectAttr + FetchInheritedAttr,
        // but FetchInheritedAttr is quite messy so we just do the simplest that seems enough for now.

        // This expands defaults, paragraph style and paragraph break run properties all what FetchInheritedAttr does.
        // andrnosk: WORDSNET-10119 We have to use revised collection here.
        RunPr result = mPara.getExpandedParagraphBreakRunPr(flags);

        // Note: I believe that there is any "strict" logic in inheritance (non-inheritance) of paragraph formatting
        // attributes to list label formatting attributes. Can't find it.

        // I don't do all the poorly understood complex attribute exclusion rules here, but I clearly
        // can see that underline should not be propagated from the paragraph formatting to the list label.
        result.remove(FontAttr.UNDERLINE);

        // DM Neither should Hidden.
        result.remove(FontAttr.HIDDEN);

        // WORDSNET-6782 List labels ignore shading.
        result.remove(FontAttr.SHADING);

        // expandListLevelFormatting is false when GetExpandedRunPr() is called for list label separators.
        if (!expandListLevelFormatting)
        {
            // WORDSNET-8782 these attributes should be ignored for list label separators.
            result.remove(FontAttr.STRIKE_THROUGH);
            result.remove(FontAttr.DOUBLE_STRIKE_THROUGH);
        }

        boolean isRevised = (flags & RunPrExpandFlags.REVISED) != 0;

        ListLevel listLevel = mPara.getListLevel(isRevised);

        if (listLevel != null)
        {
            // IV For bulleted list Italic and Bold also should not be propagated from the paragraph formatting to the list label.
            if (listLevel.getNumberStyle() == NumberStyle.BULLET)
            {
                result.remove(FontAttr.ITALIC);
                result.remove(FontAttr.BOLD);
            }

            if (listLevel.getRunPr().contains(FontAttr.ISTD))
            {
                // WORDSNET-14864 Word takes font size from Normal style if Istd present in level properties.
                Style styleNormal = mPara.getDocument().getStyles().getBySti(StyleIdentifier.NORMAL, false);

                if (styleNormal != null)
                    styleNormal.getRunPr().mirrorTo(result, FontAttr.SIZE);
            }

            // Apply direct formatting from the list label.
            if (expandListLevelFormatting)
                listLevel.getRunPr().expandTo(result);
        }

        return result;
    }

    /// <summary>
    /// Throws an exception if cannot change attributes of direct formatting.
    /// If you query Paragraph.ListLabel.Font and then remove the paragraph from the list this will occur.
    /// We can consider storing stateless complex attrs in a separate collection. But this is meaningless anyway.
    /// </summary>
    private static void throwCannotChangeException()
    {
        throw new IllegalStateException("Cannot change direct attributes on a list label. Paragraph doesn't belong to a list.");
    }

    /// <summary>
    /// Properly resolves BoolEx values if any.
    /// This is repetition from Font class. We can avoid this if perform redesign.
    /// </summary>
    private Object processListLabelAttributeBoolExAware(Object value, int key)
    {
        // The value is specified directly, but might need to be resolved using parent values.
        if (value instanceof AttrBoolEx)
            return ((AttrBoolEx)value).resolveFetchAttr(mPara.getParagraphStyle().getFont(), key);

        return value;
    }

    /// <summary>
    /// Paragraph that the list label belongs to.
    /// </summary>
    private /*final*/ Paragraph mPara;

    /// <summary>
    /// List format of the paragraph that the list label belongs to.
    /// Holding a reference since this class is stateless and operates only on paragraph state.
    /// That's not good (ineffective) to query ListLevel everytime it's needed. But list and list level
    /// might change and ListLevel might get invalid. We can create another method that expands all
    /// list label attributes if this is considered more effective and needed for optimization.
    /// </summary>
    private /*final*/ ListFormat mListFormat;

    /// <summary>
    /// Lazily initialized font associated with this list label.
    /// </summary>
    private Font mFont;

    /// <summary>
    /// Cached label split to fragments that is updated by a <see cref="ListLabelUpdater.UpdateListLabels"/> function.
    /// </summary>
    private String[] mLabelFragmentsOriginal;

    private String[] mLabelFragmentsFinal;

    private String[] mLabelArabicNumbersOriginal;

    private String[] mLabelArabicNumbersFinal;

    /// <summary>
    /// Cached list number stated of the paragraph. Only used in some scenarios like when REF-like fields need it
    /// to build up list label string in full or relative context.
    /// </summary>
    private ListNumberState mNumberStateOriginal;

    private ListNumberState mNumberStateFinal;
}

