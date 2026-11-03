// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2020 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msString;
import com.aspose.words.ref.RefInt;


/// <summary>
/// Removes duplicate styles with replacing references to them.
/// </summary>
class DuplicateStyleRemover extends IstdVisitor
{
    /// <summary>
    /// Removes duplicate styles in the specified document.
    /// </summary>
    static void execute(DocumentBase doc) throws Exception
    {
        DuplicateStyleRemover remover = new DuplicateStyleRemover();
        remover.executeCore(doc);
    }

    protected /*override*/ void onRunPr(RunPr runPr)
    {
        replaceStyle(runPr, FontAttr.ISTD);
    }

    protected /*override*/ void onParaPr(ParaPr paraPr)
    {
        replaceStyle(paraPr, ParaAttr.ISTD);
    }

    protected /*override*/ void onRowPr(TablePr tablePr)
    {
        replaceStyle(tablePr, TableAttr.ISTD);
    }

    protected /*override*/ void onDocPr(DocPr docPr)
    {
        int newIstd = 0;

        RefInt referenceToNewIstd = new RefInt(newIstd);
        boolean outRefCondition0 = msDictionary.tryGetValue(mReplacingStyleMap, docPr.ClickTypeParaStyleIstd, /*out*/ referenceToNewIstd);
        newIstd = referenceToNewIstd.get();
        if (outRefCondition0)
            docPr.ClickTypeParaStyleIstd = newIstd;

        referenceToNewIstd.set(newIstd);
        boolean outRefCondition1 = msDictionary.tryGetValue(mReplacingStyleMap, docPr.DefaultTableStyleIstd, /*out*/ referenceToNewIstd);
        newIstd = referenceToNewIstd.get();
        if (outRefCondition1)
            docPr.DefaultTableStyleIstd = newIstd;
    }

    /// <summary>
    /// Removes duplicate styles in the specified document.
    /// </summary>
    private void executeCore(DocumentBase doc) throws Exception
    {
        fillReplacingStyleMap(doc.getStyles());

        if (mStylesToRemove.size() == 0)
            return;

        for (Style style : mStylesToRemove)
            doc.getStyles().removeCore(style);

        // Visit all document content to replace style references.
        run(doc);

        replaceBaseLinkedAndNextStyles(doc);
        replaceListStyles(doc);
    }

    /// <summary>
    /// Replaces references to base, linked and next style in document styles.
    /// </summary>
    private void replaceBaseLinkedAndNextStyles(DocumentBase doc)
    {
        for (Style style : doc.getStyles())
        {
            int newIstd = 0;

            RefInt referenceToNewIstd = new RefInt(newIstd);
            boolean outRefCondition2 = (style.getLinkedIstd() != StyleIndex.NIL) && msDictionary.tryGetValue(mReplacingStyleMap, style.getLinkedIstd(), /*out*/ referenceToNewIstd);
            newIstd = referenceToNewIstd.get();
            if (outRefCondition2)
                style.setLinkedIstd(newIstd);

            referenceToNewIstd.set(newIstd);
            boolean outRefCondition3 = (style.getBasedOnIstd() != StyleIndex.NIL) && msDictionary.tryGetValue(mReplacingStyleMap, style.getBasedOnIstd(), /*out*/ referenceToNewIstd);
            newIstd = referenceToNewIstd.get();
            if (outRefCondition3)
                style.setBasedOnIstd(newIstd);

            referenceToNewIstd.set(newIstd);
            boolean outRefCondition4 = (style.getNextIstd() != StyleIndex.NIL) && msDictionary.tryGetValue(mReplacingStyleMap, style.getNextIstd(), /*out*/ referenceToNewIstd);
            newIstd = referenceToNewIstd.get();
            if (outRefCondition4)
                style.setNextIstd(newIstd);
        }
    }

    /// <summary>
    /// Replaces style references in lists.
    /// </summary>
    private void replaceListStyles(DocumentBase doc)
    {
        for (ListDef listDef : doc.getLists().getListDefs())
        {
            int newIstd = 0;
            RefInt referenceToNewIstd = new RefInt(newIstd);
            boolean outRefCondition5 = msDictionary.tryGetValue(mReplacingStyleMap, listDef.getListStyleIstd(), /*out*/ referenceToNewIstd);
            newIstd = referenceToNewIstd.get();
            if (outRefCondition5)
                listDef.setListStyleIstd(newIstd);

            for (ListLevel level : listDef.getLevels())
            {
                referenceToNewIstd.set(newIstd);
                boolean outRefCondition6 = msDictionary.tryGetValue(mReplacingStyleMap, level.getParaStyleIstd(), /*out*/ referenceToNewIstd);
                newIstd = referenceToNewIstd.get();
                if (outRefCondition6)
                    level.setParaStyleIstd(newIstd);
            }
        }
    }

    /// <summary>
    /// Fills the map of correspondence between replacing style Istd and new style Istd.
    /// </summary>
    private void fillReplacingStyleMap(StyleCollection styles)
    {
        HashMap<Integer, ArrayList<Style>> hashes = getStylesByHashMap(styles);
        ArrayList<Style> equalStyles = new ArrayList<Style>();

        for (ArrayList<Style> list : (Iterable<ArrayList<Style>>) hashes.values())
        {
            if (list.size() == 1)
                continue; // No style duplicates.

            while (extractEqualStyles(list, equalStyles))
                addToReplacingStyleMap(equalStyles);
        }
    }

    /// <summary>
    /// Loops through the styles and extracts the first non-null style and all styles that are equal to it, if any.
    /// Returns a flag indicating whether any styles have been extracted.
    /// </summary>
    /// <remarks>
    /// The extracted styles are replaced with <c>null</c> in the input style list.
    /// The output list of styles is ordered: the first item is the style to be preserved in the document.
    /// </remarks>
    private static boolean extractEqualStyles(ArrayList<Style> styles, ArrayList<Style> extractedEqualStyles)
    {
        extractedEqualStyles.clear();
        int minIstdStyleIndex = -1; // Index of a style that has the smallest Istd.

        for (int i = 0; i < styles.size(); i++)
        {
            Style style = styles.get(i);
            if (style == null)
                continue;

            // Skip a style if it doesn't equal to the first one.
            if ((extractedEqualStyles.size() > 0) && !style.equals(extractedEqualStyles.get(0)))
                continue;

            // Let's keep a style with the lowest Istd so that built-in styles take precedence.
            if (canReplaceWithStyle(style) &&
                ((minIstdStyleIndex < 0) || (extractedEqualStyles.get(minIstdStyleIndex).getIstd() > style.getIstd())))
            {
                minIstdStyleIndex = extractedEqualStyles.size();
            }

            msArrayList.add(extractedEqualStyles, style);

            styles.set(i, null);
        }

        boolean hasExtracted = extractedEqualStyles.size() > 0;

        if (minIstdStyleIndex < 0)
        {
            // There is no style that can be used to replace other styles with: clear the list.
            extractedEqualStyles.clear();
        }
        else if (minIstdStyleIndex < 0)
        {
            // Move the style that will be used to replace other styles to the top of the list.
            Style tmp = extractedEqualStyles.get(0);
            extractedEqualStyles.set(0, extractedEqualStyles.get(minIstdStyleIndex));
            extractedEqualStyles.set(minIstdStyleIndex, tmp);
        }

        return hasExtracted;
    }

    /// <summary>
    /// Add styles to the style replacement map. The first item of the list is a style to preserve.
    /// </summary>
    private void addToReplacingStyleMap(ArrayList<Style> styles)
    {
        if (styles.size() < 2)
            return; // No duplicate styles.

        int istdToReplaceWith = styles.get(0).getIstd();

        for (Style style : styles)
        {
            if ((style.getIstd() == istdToReplaceWith) || !canReplaceStyle(style))
                continue;

            msDictionary.add(mReplacingStyleMap, style.getIstd(), istdToReplaceWith);
            msArrayList.add(mStylesToRemove, style);
        }
    }

    /// <summary>
    /// Gets a map of styles by their hash codes.
    /// </summary>
    private static HashMap<Integer, ArrayList<Style>> getStylesByHashMap(StyleCollection styles)
    {
        HashMap<Integer, ArrayList<Style>> map = new HashMap<Integer, ArrayList<Style>>();

        for (Style style : styles)
        {
            if (!canReplaceWithStyle(style) && !canReplaceStyle(style))
                continue;

            int hashCode = getStyleHashCodeForEquality(style);

            ArrayList<Style> list = DictionaryExtension.getValueOrNull(map, hashCode);
            if (list == null)
            {
                list = new ArrayList<Style>();
                map.put(hashCode, list);
            }

            msArrayList.add(list, style);
        }

        return map;
    }

    /// <summary>
    /// Returns a flag indicating whether the specified style can be replaced with another style.
    /// </summary>
    private static boolean canReplaceStyle(Style style)
    {
        if (style.getStyleIdentifier() != StyleIdentifier.USER)
            return false;

        // Let's do not remove styles that are linked to system styles. For example, 'Footer Char'. Such character
        // styles have no special style identifier.
        if (style.getLinkedIstd() != StyleIndex.NIL)
        {
            Style linkedStyle = style.getDocument().getStyles().getByIstd(style.getLinkedIstd(), false);
            return (linkedStyle == null) || (linkedStyle.getStyleIdentifier() == StyleIdentifier.USER);
        }

        return true;
    }

    /// <summary>
    /// Returns a flag indicating whether the specified style can be used to replace another style.
    /// </summary>
    private static boolean canReplaceWithStyle(Style style)
    {
        // Let's do not replace custom styles with special styles, e.g. with Heading, CommentReference and so on.
        // Since assignment of some styles may be an indicator of a certain type of a run/paragraph.

        if (!canReplaceWithStyleCore(style))
            return false;

        // Let's exclude styles that are linked to the special styles. For example, 'Footer Char'. Such character
        // styles have no special style identifier.
        if (style.getLinkedIstd() != StyleIndex.NIL)
        {
            Style linkedStyle = style.getDocument().getStyles().getByIstd(style.getLinkedIstd(), false);
            return (linkedStyle == null) || canReplaceWithStyleCore(linkedStyle);
        }

        return true;
    }

    /// <summary>
    /// Returns a flag indicating whether the specified style can be used to replace another style.
    /// </summary>
    private static boolean canReplaceWithStyleCore(Style style)
    {
        // Let's do not replace custom styles with special styles, e.g. with Heading, CommentReference and so on.
        // Since assignment of some styles may be an indicator of a certain type of a run/paragraph.
        switch (style.getStyleIdentifier())
        {
            case StyleIdentifier.BOOK_TITLE:
            case StyleIdentifier.COMMENT_REFERENCE:
            case StyleIdentifier.EMPHASIS:
            case StyleIdentifier.ENDNOTE_REFERENCE:
            case StyleIdentifier.FOLLOWED_HYPERLINK:
            case StyleIdentifier.FOOTNOTE_REFERENCE:
            case StyleIdentifier.HTML_ACRONYM:
            case StyleIdentifier.HTML_CITE:
            case StyleIdentifier.HTML_CODE:
            case StyleIdentifier.HTML_DEFINITION:
            case StyleIdentifier.HTML_KEYBOARD:
            case StyleIdentifier.HTML_SAMPLE:
            case StyleIdentifier.HTML_TYPEWRITER:
            case StyleIdentifier.HTML_VARIABLE:
            case StyleIdentifier.HYPERLINK:
            case StyleIdentifier.INTENSE_EMPHASIS:
            case StyleIdentifier.INTENSE_REFERENCE:
            case StyleIdentifier.LINE_NUMBER:
            case StyleIdentifier.PAGE_NUMBER:
            case StyleIdentifier.PLACEHOLDER_TEXT:
            case StyleIdentifier.SUBTLE_EMPHASIS:
            case StyleIdentifier.SUBTLE_REFERENCE:
            case StyleIdentifier.BALLOON_TEXT:
            case StyleIdentifier.CLOSING:
            case StyleIdentifier.COMMENT_SUBJECT:
            case StyleIdentifier.COMMENT_TEXT:
            case StyleIdentifier.DATE:
            case StyleIdentifier.DOCUMENT_MAP:
            case StyleIdentifier.EMAIL_SIGNATURE:
            case StyleIdentifier.ENDNOTE_TEXT:
            case StyleIdentifier.FOOTER:
            case StyleIdentifier.FOOTNOTE_TEXT:
            case StyleIdentifier.HEADER:
            case StyleIdentifier.HEADING_1:
            case StyleIdentifier.HEADING_2:
            case StyleIdentifier.HEADING_3:
            case StyleIdentifier.HEADING_4:
            case StyleIdentifier.HEADING_5:
            case StyleIdentifier.HEADING_6:
            case StyleIdentifier.HEADING_7:
            case StyleIdentifier.HEADING_8:
            case StyleIdentifier.HEADING_9:
            case StyleIdentifier.HTML_ADDRESS:
            case StyleIdentifier.HTML_TOP_OF_FORM:
            case StyleIdentifier.HTML_BOTTOM_OF_FORM:
            case StyleIdentifier.HTML_PREFORMATTED:
            case StyleIdentifier.INTENSE_QUOTE:
            case StyleIdentifier.MACRO:
            case StyleIdentifier.MESSAGE_HEADER:
            case StyleIdentifier.NOTE_HEADING:
            case StyleIdentifier.PLAIN_TEXT:
            case StyleIdentifier.QUOTE:
            case StyleIdentifier.SALUTATION:
            case StyleIdentifier.SIGNATURE:
            case StyleIdentifier.SUBTITLE:
            case StyleIdentifier.TITLE:
            case StyleIdentifier.BIBLIOGRAPHY:
            case StyleIdentifier.BLOCK_TEXT:
            case StyleIdentifier.CAPTION:
            case StyleIdentifier.ENVELOPE_ADDRESS:
            case StyleIdentifier.ENVELOPE_RETURN:
            case StyleIdentifier.INDEX_1:
            case StyleIdentifier.INDEX_2:
            case StyleIdentifier.INDEX_3:
            case StyleIdentifier.INDEX_4:
            case StyleIdentifier.INDEX_5:
            case StyleIdentifier.INDEX_6:
            case StyleIdentifier.INDEX_7:
            case StyleIdentifier.INDEX_8:
            case StyleIdentifier.INDEX_9:
            case StyleIdentifier.INDEX_HEADING:
            case StyleIdentifier.LIST:
            case StyleIdentifier.LIST_2:
            case StyleIdentifier.LIST_3:
            case StyleIdentifier.LIST_4:
            case StyleIdentifier.LIST_5:
            case StyleIdentifier.LIST_BULLET:
            case StyleIdentifier.LIST_BULLET_2:
            case StyleIdentifier.LIST_BULLET_3:
            case StyleIdentifier.LIST_BULLET_4:
            case StyleIdentifier.LIST_BULLET_5:
            case StyleIdentifier.LIST_CONTINUE:
            case StyleIdentifier.LIST_CONTINUE_2:
            case StyleIdentifier.LIST_CONTINUE_3:
            case StyleIdentifier.LIST_CONTINUE_4:
            case StyleIdentifier.LIST_CONTINUE_5:
            case StyleIdentifier.LIST_NUMBER:
            case StyleIdentifier.LIST_NUMBER_2:
            case StyleIdentifier.LIST_NUMBER_3:
            case StyleIdentifier.LIST_NUMBER_4:
            case StyleIdentifier.LIST_NUMBER_5:
            case StyleIdentifier.LIST_PARAGRAPH:
            case StyleIdentifier.NO_SPACING:
            case StyleIdentifier.NORMAL_WEB:
            case StyleIdentifier.NORMAL_INDENT:
            case StyleIdentifier.TABLE_OF_AUTHORITIES:
            case StyleIdentifier.TABLE_OF_FIGURES:
            case StyleIdentifier.TOA_HEADING:
            case StyleIdentifier.TOC_1:
            case StyleIdentifier.TOC_2:
            case StyleIdentifier.TOC_3:
            case StyleIdentifier.TOC_4:
            case StyleIdentifier.TOC_5:
            case StyleIdentifier.TOC_6:
            case StyleIdentifier.TOC_7:
            case StyleIdentifier.TOC_8:
            case StyleIdentifier.TOC_9:
            case StyleIdentifier.TOC_HEADING:
            case StyleIdentifier.REVISION:
            case StyleIdentifier.OUTLINE_LIST_1:
            case StyleIdentifier.OUTLINE_LIST_2:
            case StyleIdentifier.OUTLINE_LIST_3:
            case StyleIdentifier.NO_LIST:
            case StyleIdentifier.NIL:
                return false;
            default:
                return true;
        }
    }

    /// <summary>
    /// Replaces a style definition in the specified property collection.
    /// </summary>
    /// <remarks>
    /// The <paramref name="styleAttribute"/> parameter is an attribute that is used to store style Istd values.
    /// </remarks>
    private void replaceStyle(AttrCollection properties, int styleAttribute)
    {
        Object value = properties.getDirectAttr(styleAttribute);
        if (value == null)
            return;

        int istd = (/*int*/Integer)value;
        int newIstd = 0;
        RefInt referenceToNewIstd = new RefInt(newIstd);
        boolean outRefCondition7 = msDictionary.tryGetValue(mReplacingStyleMap, istd, /*out*/ referenceToNewIstd);
        newIstd = referenceToNewIstd.get();
        if (outRefCondition7)
            properties.setAttr(styleAttribute, newIstd);
    }

    /// <summary>
    /// Calculates hash code that is compatible with the <see cref="Style.Equals(Style)"/> method.
    /// </summary>
    /// <dev>
    /// Some style properties are not included in hash calculation similar to the Style.Equal method. Because of this
    /// and due to complexity of the code, it is not implemented as Style.GetHashCode, but is placed in this class.
    /// The method should be changed together with the Style.Equals methods.
    /// Only properties that affect visual representation of a style are included into the calculation.
    /// </dev>
    static int getStyleHashCodeForEquality(Style style)
    {
        return getStyleHashCodeForEquality(style, true);
    }

    /// <summary>
    /// Calculates hash code with ability to define whether base/linked/next styles should be processed.
    /// </summary>
    /// <remarks>
    /// The returned hash code is compatible with the <see cref="Style.Equals(Style)"/> method.
    /// </remarks>
    /// <dev>
    /// Some style properties are not included in hash calculation similar to the Style.Equal method. Because of this
    /// and due to complexity of the code, it is not implemented as Style.GetHashCode, but is placed in this class.
    /// The method should be changed together with the Style.Equals methods.
    /// Only properties that affect visual representation of a style are included into the calculation.
    /// </dev>
    private static int getStyleHashCodeForEquality(Style style, boolean includeReferencedStyles)
    {
        int hashCode = /*enum.hashCode()=(int)value*/style.getType();

        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.isHeading());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.isQuickStyle());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getAutomaticallyUpdate());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getLocked());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getUnhideWhenUsed());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getPersonalCompose());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getPersonalReply());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getPersonal());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.getLidsSet());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.isTopLevelParaStyle());
        hashCode = (hashCode * 397) ^ msNumber.booleanHashCode(style.hasRevisions());

        switch (style.getType())
        {
            case StyleType.CHARACTER:
                hashCode = (hashCode * 397) ^ style.getRunPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                break;
            case StyleType.PARAGRAPH:
            {
                hashCode = (hashCode * 397) ^ style.getRunPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                hashCode = (hashCode * 397) ^ style.getParaPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                break;
            }
            case StyleType.TABLE:
            {
                hashCode = (hashCode * 397) ^ style.getRunPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                hashCode = (hashCode * 397) ^ style.getParaPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);

                TableStyle tableStyle = (TableStyle)style;

                for (ConditionalStyle conditionalStyle : tableStyle.getConditionalStyles().getDefinedStyles())
                {
                    if (conditionalStyle.hasFormatting())
                        hashCode = (hashCode * 397) ^ conditionalStyle.hashCode();
                }

                hashCode = (hashCode * 397) ^ tableStyle.getCellPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                hashCode = (hashCode * 397) ^ tableStyle.getRowPr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                hashCode = (hashCode * 397) ^ tableStyle.getTablePr().hashCode(Style.COMPARISON_IGNORABLE_KEYS);
                break;
            }
            case StyleType.LIST:
                // List hash is taken below.
                break;
            default:
                throw new IllegalStateException(msString.format("Unexpected style type '{0}'.", style.getType()));
        }

        List list = style.getListInternal();
        if (list != null)
            hashCode = (hashCode * 397) ^ list.hashCode();

        if (!includeReferencedStyles)
            return hashCode;

        // To prevent cycling, collect all referenced styles and take their hash codes with includeReferencedStyles
        // set to 'false'.

        ArrayList<Style> referencedStyles = new ArrayList<Style>();

        final int MAX_NESTING = 4;
        collectReferencedStyles(style, referencedStyles, MAX_NESTING, true);

        HashMap<Integer, Integer> hashByIstd = new HashMap<Integer, Integer>();
        msDictionary.add(hashByIstd, style.getIstd(), hashCode);

        for (Style referencedStyle : referencedStyles)
        {
            int hash = 0;
            RefInt referenceToHash = new RefInt(hash);
            boolean outRefCondition8 = !msDictionary.tryGetValue(hashByIstd, referencedStyle.getIstd(), /*out*/ referenceToHash);
            hash = referenceToHash.get();
            if (outRefCondition8)
            {
                hash = getStyleHashCodeForEquality(referencedStyle, false);
                msDictionary.add(hashByIstd, referencedStyle.getIstd(), hash);
            }

            hashCode = (hashCode * 397) ^ hash;
        }

        return hashCode;
    }

    /// <summary>
    /// Recursively collects all base, linked and next styles of the specified style.
    /// </summary>
    /// <param name="style">A style to collect references of.</param>
    /// <param name="styleList">A destination style list.</param>
    /// <param name="nesting">A variable that is decreased on each recursion. It is used to prevent cycling: when
    /// its value becomes 0, recursion is stopped.</param>
    /// <param name="takeAllBaseStyles">When <c>true</c>, all base styles are collected even if
    /// <paramref name="nesting"/> is 0.</param>
    private static void collectReferencedStyles(Style style, ArrayList<Style> styleList, int nesting,
        boolean takeAllBaseStyles)
    {
        if (takeAllBaseStyles || nesting > 0)
            collectReferencedStyle(style.getBaseStyle(), styleList, nesting, takeAllBaseStyles);

        if (nesting > 0)
        {
            collectReferencedStyle(style.getLinkedStyle(), styleList, nesting, false);
            collectReferencedStyle(style.getNextStyle(), styleList, nesting, false);
        }
    }

    /// <summary>
    /// Adds the specified style and its base, linked and next styles to the specified style list with recursion if
    /// the nesting value is not achieved 0 value yet.
    /// </summary>
    /// <param name="style">A style to add to the list.</param>
    /// <param name="styleList">A destination style list.</param>
    /// <param name="nesting">A variable that is decreased on each recursion. It is used to prevent cycling: when
    /// its value becomes 0, recursion is stopped.</param>
    /// <param name="takeAllBaseStyles">When <c>true</c>, all base styles are collected even if
    /// <paramref name="nesting"/> is 0.</param>
    private static void collectReferencedStyle(Style style, ArrayList<Style> styleList, int nesting,
        boolean takeAllBaseStyles)
    {
        if (style == null)
            return;

        msArrayList.add(styleList, style);

        if (takeAllBaseStyles || (nesting > 0))
            collectReferencedStyles(style, styleList, nesting - 1, takeAllBaseStyles);
    }

    /// <summary>
    /// The map of styles to replace: old Istd to new Istd.
    /// </summary>
    private /*final*/ HashMap<Integer, Integer> mReplacingStyleMap = new HashMap<Integer, Integer>();

    private /*final*/ ArrayList<Style> mStylesToRemove = new ArrayList<Style>();
}

