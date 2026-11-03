// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/07/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringSplitOptions;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.msDictionary;
import java.util.HashMap;
import com.aspose.words.ref.RefBoolean;


/// <summary>
/// Searches for a fragment of text in the document formatted with the specified style.
/// The style may be either a paragraph or character style.
/// </summary>
class StyleFinder extends FieldParagraphFinder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    private StyleFinder(String styleName, StyleCollection styles)
    {
        Debug.assertTrue(StringUtil.hasChars(styleName));

        mStyles = styles;

        mStyleName = styleName;
        mInvariantStyleName = StyleNameTranslator.getInvariantStyleName(styleName);

        String separator = Character.toString(FormatterPal.getListSeparatorCurrent());
        mStyleNameParts = msString.split(styleName, new String[] { separator }, StringSplitOptions.REMOVE_EMPTY_ENTRIES);
        mInvariantStyleNameParts = new String[mStyleNameParts.length];
        for (int i = 0; i < mStyleNameParts.length; i++)
            mInvariantStyleNameParts[i] = StyleNameTranslator.getInvariantStyleName(mStyleNameParts[i]);

        mIstd = FormatterPal.tryParseInt(styleName);
    }

    /// <summary>
    /// Searches for the specified style for the given field.
    /// </summary>
    static StyleSearchResult findStyle(FieldStyleRef field, String styleName) throws Exception
    {
        // Ensure that the field is being updated.
        Debug.assertTrue(field.isUpdating());

        StyleFinder finder = new StyleFinder(styleName, field.getDocument().getStyles());
        return finder.findStyle(field);
    }

    private StyleSearchResult findStyle(FieldStyleRef field) throws Exception
    {
        if (field.isInHeaderFooter())
        {
            findStyleHeaderFooter(field);
        }
        else
        {
            findStyleMainText(field);
        }

        return mResult;
    }

    private void findStyleHeaderFooter(FieldStyleRef field) throws Exception
    {
        // WORDSNET-12038 Searches paragraph in backward direction, if \l switch specified.
        mIsForwardDirection = !field.getSearchFromBottom();

        findParagraphHeaderFooter(field);
    }

    protected /*override*/ void onPageScanned()
    {
        // 2. MS Word scans every page before the field's one in backward direction.
        mIsForwardDirection = false;
    }

    protected /*override*/ void onAllPagesScanned()
    {
        // 3. MS Word scans contents after the field in forward direction.
        mIsForwardDirection = true;
    }

    private void findStyleMainText(Field field) throws Exception
    {
        // Go backward first. Go forward then.
        if (!findStyleMainText(field, false))
            findStyleMainText(field, true);
    }

    private boolean findStyleMainText(Field field, boolean isForwardDirection) throws Exception
    {
        mIsForwardDirection = isForwardDirection;

        return (findParagraph(field.getStart().getParentParagraph(), isForwardDirection, false) != null);
    }

    /*override*/ Paragraph confirmParagraph(Paragraph candidateParagraph)
    {
        if (candidateParagraph == null)
            return null;

        mResult = findStyleInParagraph(candidateParagraph);

        return (mResult != null) ? mResult.getParagraph() : null;
    }

    /// <summary>
    /// Searches for a style in the specified paragraph.
    /// </summary>
    private StyleSearchResult findStyleInParagraph(Paragraph paragraph)
    {
        // MS Word considers only main text paragraphs.
        if (paragraph.getAncestor(NodeType.BODY) == null)
            return null;

        if (compareParagraphStyle(paragraph))
        {
            return StyleSearchResult.createWholeParagraphResult(paragraph, mIsForwardDirection);
        }

        // Try to find runs with specified style.
        int startIndex = -1;
        int endIndex = -1;

        // We should start from a paragraph break, if we are moving backward.
        boolean isParagraphBreakStyleApplied = (!mIsForwardDirection && compareParagraphBreakStyle(paragraph));
        boolean isParagraphBreakIncluded = isParagraphBreakStyleApplied && isParagraphBreakIncluded(paragraph);

        // This will only enumerate over inlines that are immediate children of the paragraph.
        // But there could be more inlines, inside Paragraph/SmartTag/Run for example.
        // I could use paragraph.GetChildNodes(NodeType.Any, true) to get a deep collection of inlines,
        // but it will also have its problems - it will include inlines that are inside a textbox shape for example,
        // which I don't want to include.
        // So I leave the current approach as is.
        NodeCollection inlines = getParagraphInlines(paragraph);
        int inlinesCount = inlines.getCount();
        for (int i = 0; i < inlinesCount; i++)
        {
            int currentIndex = mIsForwardDirection ? i : (inlinesCount - 1 - i);
            IInline inline = (IInline)inlines.get(currentIndex);

            if (compareInlineStyle(inline))
            {
                endIndex = currentIndex;
                if (startIndex < 0)
                    startIndex = currentIndex;
            }
            else if ((startIndex >= 0) || isParagraphBreakIncluded)
            {
                // If the style of the current inline differs from the one we are looking for and we have captured something
                // (i.e. a inline or a paragraph break), then it is time to complete the inline index range.
                break;
            }
        }

        // We should consider a paragraph break after all of the paragraph inlines have been considered, if we are moving forward.
        if (mIsForwardDirection)
        {
            // We can include a paragraph break only if:
            //   - we have not captured any paragraph inline or
            //   - we have captured the last paragraph inline.
            int maxIndex = Math.max(startIndex, endIndex);
            boolean canIncludeParagraphBreak = (maxIndex < 0) || (maxIndex == inlinesCount - 1);
            isParagraphBreakStyleApplied = canIncludeParagraphBreak && compareParagraphBreakStyle(paragraph);
            isParagraphBreakIncluded = isParagraphBreakStyleApplied && isParagraphBreakIncluded(paragraph);
        }

        return StyleSearchResult.createIfNeeded(paragraph, startIndex, endIndex, mIsForwardDirection, isParagraphBreakIncluded, isParagraphBreakStyleApplied);
    }

    static NodeCollection getParagraphInlines(Paragraph paragraph)
    {
        return paragraph.getChildNodes(new /*NodeType*/int[] { NodeType.RUN, NodeType.FOOTNOTE, NodeType.FIELD_START, NodeType.FIELD_END }, false);
    }

    /// <summary>
    /// Returns a value indicating whether the specified paragraph's style is the one we are searching for.
    /// </summary>
    private boolean compareParagraphStyle(Paragraph paragraph)
    {
        return isParagraphAcceptable(paragraph) && compareStyle(paragraph.getParaPr(), true);
    }

    private boolean isParagraphAcceptable(Paragraph paragraph)
    {
        return !mIsForwardDirection || !isEmptySectionBreakParagraph(paragraph);
    }

    private static boolean isEmptySectionBreakParagraph(Paragraph paragraph)
    {
        return paragraph.isSectionBreakParagraph() &&
               paragraph.getRuns().getCount() == 0;
    }

    /// <summary>
    /// Returns a value indicating whether the specified paragraph's break has a style we are searching for.
    /// </summary>
    private boolean compareParagraphBreakStyle(Paragraph paragraph)
    {
        return compareStyle(paragraph.getParagraphBreakRunPr(), false);
    }

    private static boolean isParagraphBreakIncluded(Paragraph paragraph)
    {
        if (paragraph.getParentNode().getNodeType() != NodeType.CELL)
            return true;

        if (paragraph.getNextNonAnnotationSibling() != null)
            return true;

        return false;
    }

    /// <summary>
    /// Returns a value indicating whether the specified inline's style is the one we are searching for.
    /// </summary>
    private boolean compareInlineStyle(IInline inline)
    {
        return compareStyle(inline.getRunPr_IInline(), false);
    }

    private boolean compareStyle(AttrCollection attrs, boolean isParaPr)
    {
        // WORDSNET-7376 Look for style by name or by index.
        int key = isParaPr ? ParaAttr.ISTD : FontAttr.ISTD;
        int istd = (/*int*/Integer)attrs.fetchAttr(key);
        if (mIstd != Integer.MIN_VALUE)
            return (mIstd == istd);

        int defaultIstd = isParaPr ? StyleIndex.NORMAL : StyleIndex.DEFAULT_PARAGRAPH_FONT;
        Style style = mStyles.fetchByIstd(istd, defaultIstd);
        return compareStyle(style);
    }

    private boolean compareStyle(Style style)
    {
        if (compareWithStyleName(style.getName()))
            return true;

        // WORDSNET-9885 Search for style by name that includes the alias.
        if (compareWithStyleName(style.getNameWithAliases()))
            return true;

        // WORDSNET-9953 Search for style by styles aliases.
        for (String alias : style.getAliasesInternal())
        {
            if (compareWithStyleName(alias))
                return true;
        }

        return compareStyleParts(style);
    }

    private boolean compareStyleParts(Style style)
    {
        String[] styleAliases = style.getAliasesInternal();
        String styleName = style.getName();

        for (int i = 0; i < mStyleNameParts.length; i++)
        {
            String styleNamePart = mStyleNameParts[i];
            String invariantStyleNamePart = mInvariantStyleNameParts[i];

            if (compareWithStyleName(styleName, styleNamePart, invariantStyleNamePart))
                continue;

            if (compareWithStyleName(styleAliases, styleNamePart, invariantStyleNamePart))
                continue;

            return false;
        }

        return true;
    }

    private boolean compareWithStyleName(String value)
    {
        return compareWithStyleName(value, mStyleName, mInvariantStyleName);
    }

    private boolean compareWithStyleName(String value, String styleName, String invariantStyleName)
    {
        return compareWithStyleNameCaseSensitiveAware(value, styleName) || compareWithStyleNameCaseSensitiveAware(value, invariantStyleName);
    }

    private boolean compareWithStyleNameCaseSensitiveAware(String value, String styleName)
    {
        if (styleName == null)
            return false;

        return requireCaseSensitiveComparison(styleName)
            ? msString.equals(value, styleName, StringComparison.ORDINAL)
            : StringUtil.equalsIgnoreCase(value, styleName);
    }

    private boolean compareWithStyleName(String[] values, String styleName, String invariantStyleName)
    {
        for (String value : values)
        {
            if (compareWithStyleName(value, styleName, invariantStyleName))
                return true;
        }

        return false;
    }

    /*override*/ boolean isForwardPageScan() { return mIsForwardDirection; }

    /*override*/ boolean needCachePageScanResult(Paragraph paragraph)
    {
        return !isForwardPageScan() || paragraph == null;
    }

    private boolean requireCaseSensitiveComparison(String styleName)
    {
        boolean caseSensitive = false;
        RefBoolean referenceToCaseSensitive = new RefBoolean(caseSensitive);
        boolean outRefCondition0 = msDictionary.tryGetValue(mCaseSensitiveCache, styleName, /*out*/ referenceToCaseSensitive);
        caseSensitive = referenceToCaseSensitive.get();
        if (outRefCondition0)
            return caseSensitive;

        boolean result = requireCaseSensitiveComparisonCore(styleName);
        mCaseSensitiveCache.put(styleName, result);

        return result;
    }

    private boolean requireCaseSensitiveComparisonCore(String styleName)
    {
        int count = 0;
        for (Style style : mStyles)
        {
            if (isStyleNameOrAnyAliasSame(style, styleName))
                count++;

            if (count > 1)
                return true;
        }

        return false;
    }

    private static boolean isStyleNameOrAnyAliasSame(Style style, String styleName)
    {
        if (StringUtil.equalsOrdinalIgnoreCase(styleName, style.getName()))
            return true;

        for (String alias : style.getAliasesInternal())
        {
            if (StringUtil.equalsOrdinalIgnoreCase(styleName, alias))
                return true;
        }

        return false;
    }

    private /*final*/ String mStyleName;
    private /*final*/ StyleCollection mStyles;
    private /*final*/ String mInvariantStyleName;
    private /*final*/ String[] mStyleNameParts;
    private /*final*/ String[] mInvariantStyleNameParts;
    private /*final*/ int mIstd;
    private /*final*/ HashMap<String, Boolean> mCaseSensitiveCache = new HashMap<String, Boolean>();

    private boolean mIsForwardDirection;
    private StyleSearchResult mResult;
}

