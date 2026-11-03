// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/10/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.MathUtil;


/// <summary>
/// An element that might be used to draw a common border in HTML.
/// </summary>
class CommonBorderContainer
{
    CommonBorderContainer(
        CommonBorderContainer parentContainer,
        BordersInfo borders)
    {
        Debug.assertTrue(borders != null);

        mParent = parentContainer;
        mContainerBorders = borders;
        mParagraphBordersPairs = new ArrayList<ParagraphBordersPair>();
    }

    void add(CompositeNode node, BordersInfo borders)
    {
        CommonBorderContainer currentContainer = this;

        // Find a parent with borders.
        while ((currentContainer != null) &&
            !currentContainer.mContainerBorders.hasPaddingOrVisibleBorders())
        {
            currentContainer = currentContainer.mParent;
        }

        // If there is a parent with borders, add the node there.
        if (currentContainer != null)
        {
            Paragraph paragraph = ms.as(node, Paragraph.class);
            if (paragraph != null)
            {
                currentContainer.addParagraph(paragraph, borders);
            }
            else
            {
                Table table = ms.as(node, Table.class);
                if (table != null)
                {
                    currentContainer.addTable(table, borders);
                }
            }
        }
    }

    void applyCommonBorderIfNeeded() throws Exception
    {
        // Nothing to apply if the current container doesn't have visible borders.
        if (!mContainerBorders.hasPaddingOrVisibleBorders())
        {
            return;
        }

        if ((mFirstTable == null) && (mParagraphBordersPairs.size() == 0))
            return;

        if ((mFirstTable != null) && !mIsMultipleTablesAdded)
            applyCommonBorderForSingleTableIfNeeded();
        else if (mParagraphBordersPairs.size() == 1)
            applyCommonBorderForSingleParagraphIfNeeded();
        else
            applyCommonBorderForMultipleParagraphsIfNeeded();
    }

    private void addParagraph(Paragraph paragraph, BordersInfo borders)
    {
        Debug.assertTrue(borders != null);

        if (mFirstTable != null)
            return;

        ParagraphBordersPair paragraphBordersPair = new ParagraphBordersPair(paragraph, borders);

        int index = 0;
        while (index < mParagraphBordersPairs.size())
        {
            ParagraphBordersPair currentPair = mParagraphBordersPairs.get(index);
            if (currentPair.Paragraph == paragraphBordersPair.Paragraph)
                break;
            index++;
        }

        if (index < mParagraphBordersPairs.size())
        {
            // Due to logic of DocumentBuilder and HtmlCommonBorderResolver
            // last added paragraph will have actual information about borders.
            mParagraphBordersPairs.remove(index);
        }
        msArrayList.add(mParagraphBordersPairs, paragraphBordersPair);
    }

    private void addTable(Table table, BordersInfo borders)
    {
        Debug.assertTrue(borders != null);

        if (mFirstTable == null)
        {
            mFirstTable = table;
            mFirstTableBorders = borders;
        }
        else if (!mIsMultipleTablesAdded && !table.isAncestorNode(mFirstTable))
        {
            mIsMultipleTablesAdded = true;
        }
    }

    private void applyCommonBorderForSingleTableIfNeeded() throws Exception
    {
        // Here, if container borders are invisible, the container element has only padding declarations. We don't apply
        // padding to child tables so we just exit.
        if (!mContainerBorders.isVisible())
        {
            return;
        }

        // For proper output apply container's borders only to tables with "width:100%" style.
        if (!mFirstTable.getPreferredWidth().equals(PreferredWidth.fromPercent(100.0)))
            return;

        // Due to logic of DocumentBuilder and HtmlCommonBorderResolver
        // sometimes one paragraph is added even if container doesn't contain any.
        if ((getParagraphsCount() > 0) && (getParagraph(0).getFirstRun() != null))
            return;

        if (!mFirstTableBorders.isVisible())
        {
            for (int i = 0; i < mFirstTable.getRows().getCount(); i++)
                mContainerBorders.applyBorderPropertiesTo(mFirstTable.getRows().get(i).getRowFormat().getBorders());
        }
    }

    private void applyCommonBorderForSingleParagraphIfNeeded() throws Exception
    {
        // We copy container borders to paragraph 'as is' if there is one inner paragraph in the container.
        Paragraph firstPara = getParagraph(0);

        BordersInfo firstParaBorders = getParaBorders(0);

        // WORDSNET-19178 Do not apply the common border to an empty paragraph after multiple tables.
        // However, we should apply borders single empty paragraphs inside conatiners with borders.
        if (!firstParaBorders.isVisible() && ((firstPara.getChildNodes(NodeType.ANY, false).getCount() > 0) || !mIsMultipleTablesAdded))
        {
            // Note that container borders can be invisible here. In that case we apply only padding values to the child
            // paragraph.
            mContainerBorders.applyBorderPropertiesTo(firstPara.getParagraphFormat().getBorders());
            mContainerBorders.applyPaddingTo(firstPara.getParagraphFormat().getBorders());
        }

        // WORDSNET-11086 A div element around a single paragraph may be a special div we have created during export
        // to hold paragraph's border and shading in case the paragraph has a hanging indent. Such paragraphs may also be
        // enclosed in a div with common paragraph borders so they should be reprocessed at a higher level.
        if (mParent != null)
        {
            mParent.add(firstPara, mContainerBorders);
        }
    }

    private void applyCommonBorderForMultipleParagraphsIfNeeded() throws Exception
    {
        // Here, if container borders are invisible, the container element has only padding declarations. The following
        // code is designed to work with visible common borders so we just exit.
        if (!mContainerBorders.isVisible())
        {
            return;
        }

        Paragraph firstPara = getParagraph(0);
        BordersInfo firstParaBorders = getParaBorders(0);

        // AW paragraph border to text distance and after\before spacing are converted into CSS padding property during HTML export.
        // We need to find the common paragraph borders saved in HTML and to restore the original border properties during HTML import. 
        // We ignore common HTML borders that cannot be represented in AW.
        // Please see CssParagraphWriter.PaddingsToCss(...) function to find how common borders are exported into HTML.
        BordersInfo lastParaBorders = getParaBorders(getParagraphsCount() - 1);
        boolean hasCommonHorizontalBorder = firstParaBorders.Bottom.isVisible();
        double commonTopDistanceFromText = firstParaBorders.Top.getPadding();
        double commonBottomDistanceFromText = lastParaBorders.Bottom.getPadding();
        double commonSpaceAfter = (hasCommonHorizontalBorder)
                                      ? firstParaBorders.Bottom.getPadding() - commonTopDistanceFromText
                                      : firstParaBorders.Bottom.getPadding();
        BorderInfo commonHorizontalBorder = (hasCommonHorizontalBorder)
                                                ? firstParaBorders.Bottom
                                                : null;

        boolean isCommonBorderRepresentable = true;
        // There are should be no other nodes between the paragraphs.
        for (int i = 0; i < getParagraphsCount(); i++)
        {
            if ((i != getParagraphsCount() - 1) && (getParagraph(i).getNextNonAnnotationSibling() != getParagraph(i + 1)))
            {
                isCommonBorderRepresentable = false;
                break;
            }
        }

        if (!isCommonBorderRepresentable)
            return;

        // All paragraphs should belong to the same list and list level.
        for (int i = 1; i < getParagraphsCount(); i++)
        {
            Paragraph para = getParagraph(i);
            if ((para.getListFormat().getListId() != firstPara.getListFormat().getListId()) ||
                (para.getListFormat().getListLevelNumberOriginal() != firstPara.getListFormat().getListLevelNumberOriginal()))
            {
                isCommonBorderRepresentable = false;
                break;
            }
        }

        for (int i = 0; i < getParagraphsCount(); i++)
        {
            BordersInfo paraBorders = getParaBorders(i);

            // Ignore common border if an inner paragraph has own border.
            if (paraBorders.Top.isVisible() || paraBorders.Right.isVisible() || paraBorders.Left.isVisible())
            {
                isCommonBorderRepresentable = false;
                break;
            }

            // There is no reason to compare the first paragraph borders with itself.
            if (i == 0)
                continue;

            // Ignore common border if we cannot emulate horizontal border correctly.
            if ((i != getParagraphsCount() - 1) && hasCommonHorizontalBorder &&
                (!paraBorders.Bottom.isVisible() ||
                 (commonHorizontalBorder.getLineStyle() != paraBorders.Bottom.getLineStyle())))
            {
                isCommonBorderRepresentable = false;
                break;
            }

            double paraPaddingTop = paraBorders.Top.getPadding();
            double paraPaddingBottom = paraBorders.Bottom.getPadding();
            if (i == getParagraphsCount() - 1)
            {
                if (hasCommonHorizontalBorder && !MathUtil.areEqual(paraPaddingTop, commonTopDistanceFromText))
                    isCommonBorderRepresentable = false;
                if (!MathUtil.areEqual(paraPaddingBottom, commonBottomDistanceFromText))
                    isCommonBorderRepresentable = false;
            }
            else
            {
                if (!hasCommonHorizontalBorder)
                {
                    if (!MathUtil.areEqual(paraPaddingBottom, commonSpaceAfter))
                        isCommonBorderRepresentable = false;
                }
                else
                {
                    if (!MathUtil.areEqual(paraPaddingTop, commonTopDistanceFromText))
                        isCommonBorderRepresentable = false;
                    if (!MathUtil.areEqual(paraPaddingBottom, commonSpaceAfter + commonTopDistanceFromText))
                        isCommonBorderRepresentable = false;
                }
            }

            if (!isCommonBorderRepresentable)
                break;
        }

        if (!isCommonBorderRepresentable)
            return;

        for (int i = 0; i < getParagraphsCount(); i++)
        {
            ParagraphFormat pf = getParagraph(i).getParagraphFormat();

            if (hasCommonHorizontalBorder)
            {
                commonHorizontalBorder.applyBorderPropertiesTo(pf.getBorders().getHorizontal());
                pf.getBorders().getHorizontal().setDistanceFromTextSafe(commonTopDistanceFromText);
            }

            mContainerBorders.applyBorderPropertiesTo(pf.getBorders());
            pf.getBorders().getTop().setDistanceFromTextSafe(commonTopDistanceFromText);
            pf.getBorders().getBottom().setDistanceFromTextSafe(commonBottomDistanceFromText);
        }
    }

    private Paragraph getParagraph(int index)
    {
        return mParagraphBordersPairs.get(index).Paragraph;
    }

    private BordersInfo getParaBorders(int index)
    {
        return mParagraphBordersPairs.get(index).Borders;
    }

    private int getParagraphsCount() { return mParagraphBordersPairs.size(); }

    /// <summary>
    /// Parent container. <c>null</c> if this is the topmost container (has no parent).
    /// </summary>
    private /*final*/ CommonBorderContainer mParent;

    private /*final*/ BordersInfo mContainerBorders;
    /// <summary>
    /// Stores collection of ParagraphBordersPair objects.
    /// </summary>
    private /*final*/ ArrayList<ParagraphBordersPair> mParagraphBordersPairs;
    private Table mFirstTable;
    private BordersInfo mFirstTableBorders;
    private boolean mIsMultipleTablesAdded;
}

