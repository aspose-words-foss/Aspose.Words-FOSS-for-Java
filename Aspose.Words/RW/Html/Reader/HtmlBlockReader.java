// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2022 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.RandomUtil;
import com.aspose.IntStack;
import com.aspose.DrColor;
import com.aspose.DoublePal;
import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ms.System.Collections.msStack;
import com.aspose.IntToObjDictionary;
import com.aspose.HashSetGeneric;


/// <summary>
/// Creates <see cref="HtmlBlock"/> nodes from 'div', 'body' and 'blockquote' HTML elements and uses them to store element's
/// borders and margins.
/// </summary>
class HtmlBlockReader
{
    /// <param name="disabled">
    /// Indicates whether the HTML block reader is disabled.
    /// If it is <c>true</c> then HTML blocks are not created.
    /// </param>
    /// <param name="applyFormattingAsMsWord">
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </param>
    HtmlBlockReader(boolean disabled, boolean applyFormattingAsMsWord)
    {
        mDisabled = disabled;
        mApplyFormattingAsMsWord = applyFormattingAsMsWord;
    }

    /// <summary>
    /// Initializes the HTML block reader.
    /// </summary>
    void init(DocumentBuilder builder)
    {
        Debug.assertTrue(builder != null);

        mBuilder = builder;

        Debug.assertTrue(mBuilder.getCurrentParagraph() != null);
        Paragraph currentParagraph = mBuilder.getCurrentParagraph();

        boolean isSectionEmpty = isCurrentSectionEmpty();

        // Do nothing if the first section of the document is empty
        // and the current paragraph is inside the body.
        if (isSectionEmpty &&
            (currentParagraph.getParentSection() == mBuilder.getDocument().getFirstSection()) &&
            !mApplyFormattingAsMsWord)
        {
            return;
        }

        mInsertHtmlTargetParagraph = currentParagraph;

        if (mDisabled)
            return;

        // Take into account Id's of already created HTML blocks.
        for (int i = 0; i < mInsertHtmlTargetParagraph.getDocument().getHtmlBlockCollection().getCount(); ++i)
        {
            mGeneratedIds.add(mBuilder.getDocument().getHtmlBlockCollection().getHtmlBlockByIndex(i).getId(), null);
        }

        if (!mApplyFormattingAsMsWord)
        {

            // Сreate an empty intermediate HTML block (mimic MS Word behavior).
            mEmptyInitialHtmlBlock = createHtmlBlock();

            boolean isHeaderFooterEmpty = (mInsertHtmlTargetParagraph.getCount() == 0) &&
                (mInsertHtmlTargetParagraph.getParentStory() != null) &&
                (mInsertHtmlTargetParagraph.getParentStory().getNodeType() == NodeType.HEADER_FOOTER) &&
                (mInsertHtmlTargetParagraph.getParentStory().getCount() == 1);

            // If a new HTML block is being inserted into a table or an empty section or an empty header or footer
            // then MS Word marks an empty intermediate HTML block as 'body'
            // and doesn't connect a new HTML block with the parent HTML block.
            if (mInsertHtmlTargetParagraph.isInCell() || isSectionEmpty || isHeaderFooterEmpty)
            {
                mEmptyInitialHtmlBlock.setHtmlBlockType(HtmlBlockType.BODY);
            }
            else
            {
                mEmptyInitialHtmlBlock.setParentId(mInsertHtmlTargetParagraph.getParaPr().getHtmlBlockId());
            }
            // If the HTML document is being inserted into the current paragraph then this
            // paragraph should be enclosed by the empty intermediate HTML block.
            if (mBuilder.isAtStartOfParagraph())
                mInsertHtmlTargetParagraph.getParaPr().setHtmlBlockId(mEmptyInitialHtmlBlock.getId());
        }

        if (mApplyFormattingAsMsWord)
        {
            mInsertHtmlTargetParagraph = null;
        }
        else
        {
            // The initial HTML block doesn't have any background colors.
            rememberOpenedHtmlBlock(mEmptyInitialHtmlBlock, new CssBackgroundColor((CssDeclaration)null));
        }
    }

    /// <summary>
    /// Starts a new HTML block.
    /// </summary>
    /// <remarks>
    /// Returns <c>true</c> if HTML block was in fact started.
    /// </remarks>
    boolean startHtmlBlock(
        String elementName,
        CssDeclarationCollection elementDeclarations,
        /*CssDisplayType*/int elementDisplayType)
    {
        if (!createsHtmlBlock(elementName, elementDeclarations))
            return false;

        HtmlBlock htmlBlock = createHtmlBlock();
        htmlBlock.setHtmlBlockType(getHtmlBlockType(elementName));

        if (isInsideHtmlBlock())
        {
            HtmlBlock parentHtmlBlock = mOpenedHtmlBlocksStack.peek();
            htmlBlock.setParentId(parentHtmlBlock.getId());

            // If the inserted HTML block is 'body' then an empty intermediate HTML block should be removed.
            if (htmlBlock.getHtmlBlockType() == HtmlBlockType.BODY)
            {
                htmlBlock.setParentId(parentHtmlBlock.getParentId());
                forgetOpenedHtmlBlock();
                mEmptyInitialHtmlBlock = null;

                // If the HTML document with 'body' tag was inserted into already created 'body' HTML block
                // then the child HTML block should be created as 'div' HTML block.
                if (hasParentBodyHtmlBlock())
                    htmlBlock.setHtmlBlockType(HtmlBlockType.DIV);
            }
        }
        rememberOpenedHtmlBlock(htmlBlock, new CssBackgroundColor(elementDeclarations));

        setBorder(elementDeclarations, htmlBlock.getParaPr());

        setMargin(elementDeclarations, htmlBlock.getParaPr());

        // WORDSNET-21063 We set the HTML block only for a block-level element.
        // In the case inserting the HTML document into non-empty paragraph
        // an element with non-block display type may create the HTML block or for this paragraph
        // or for the next paragraph. It depends on the inner elements. The HTML block will be set
        // during updating the corresponding paragraph.
        if (elementDisplayType == CssDisplayType.BLOCK)
        {
            mBuilder.getCurrentParagraph().getParaPr().setHtmlBlockId(htmlBlock.getId());
            // If we set the HTML block for the paragraph into which the HTML document will be inserted
            // then we should allow to change any HTML block properties during updating this paragraph.
            if (mInsertHtmlTargetParagraph == mBuilder.getCurrentParagraph())
                mInsertHtmlTargetParagraph = null;
        }

        return true;
    }

    /// <summary>
    /// Ends current HTML block.
    /// </summary>
    void endHtmlBlock(
        String elementName,
        CssDeclarationCollection elementDeclarations,
        HtmlBidiTextArranger bidiTextArranger)
    {
        if (!createsHtmlBlock(elementName, elementDeclarations))
            return;

        Debug.assertTrue(isInsideHtmlBlock());
        HtmlBlock htmlBlock = forgetOpenedHtmlBlock();
        Paragraph paragraph = mBuilder.getCurrentParagraph();
        boolean htmlBlockIsUsed = false;

        // Check all adjacent paragraphs sharing the same HTML block ID.
        while (paragraph.getParaPr().getHtmlBlockId() == htmlBlock.getId())
        {
            // Remove the HTML block from an empty paragraph if it is not before a table
            // and doesn't have any text in the bidi text arranger.
            // The bidi text arranger may contain a text if the HTML block
            // was started from inline `div` or `blockquote` elements.
            if (paragraph.isEmptyOrContainsOnlyCrossAnnotation() &&
                ((bidiTextArranger == null) || bidiTextArranger.isEmpty()) &&
                ((paragraph.getPreviousSibling() == null) || (paragraph.getPreviousSibling().getNodeType() != NodeType.TABLE)) &&
                // WORDSNET-22805 Empty paragraphs created from HTML breaks shouldn't split the HTML block.
                !mEmptyInHtmlParagraphs.contains(paragraph))
            {
                paragraph.getParaPr().remove(ParaAttr.HTML_BLOCK_ID);
            }
            else
            {
                htmlBlockIsUsed = true;
            }

            if ((paragraph.getPreviousSibling() == null) || (paragraph.getPreviousSibling().getNodeType() != NodeType.PARAGRAPH))
            {
                break;
            }

            paragraph = (Paragraph)paragraph.getPreviousSibling();
        }

        if (htmlBlockIsUsed)
        {
            addHtmlBlock(htmlBlock);
            if (htmlBlock.getParentId() != 0)
            {
                addParentHtmlBlock(htmlBlock);
            }
        }
    }

    /// <summary>
    /// Updates paragraph properties if it is inside <see cref="HtmlBlock"/>.
    /// If the paragraph was created from 'body' or 'div' all borders and margins
    /// should be stored in the corresponding HTML block.
    /// </summary>
    /// <param name="removePropertiesAppliedToHtmlBlock">
    /// Indicates whether need to remove paragraph properties if they were applied to the corresponding HTML block.
    /// </param>
    void updateCurrentParagraph(boolean removePropertiesAppliedToHtmlBlock)
    {
        // The empty initial HTML block should be added only during inserting HTML document.
        // In case of reading altChunk the initial HTML block will be added only together with a new HTML block.
        if (mEmptyInitialHtmlBlock != null && !mApplyFormattingAsMsWord)
        {
            addHtmlBlock(mEmptyInitialHtmlBlock);
            mEmptyInitialHtmlBlock = null;
        }

        Paragraph paragraph = mBuilder.getCurrentParagraph();

        // Don't set the HTML block properties for a paragraph inside a table.
        if (paragraph.isInCell())
            return;

        // Don't set the HTML block properties for a paragraph after or into which the HTML document is being inserted.
        if (paragraph == mInsertHtmlTargetParagraph)
            return;

        // If a paragraph is being inserted into an existing HTML block then this paragraph should be part of this block
        // regardless of the HTML block reader's disabled status.
        if (mDisabled && (mInsertHtmlTargetParagraph != null) && (mInsertHtmlTargetParagraph.getParaPr().getHtmlBlockId() != 0))
        {
            paragraph.getParaPr().setHtmlBlockId(mInsertHtmlTargetParagraph.getParaPr().getHtmlBlockId());
        }

        if (mDisabled)
            return;

        if (isInsideHtmlBlock())
        {
            HtmlBlock htmlBlock = mOpenedHtmlBlocksStack.peek();
            updateParagraph(paragraph, htmlBlock, removePropertiesAppliedToHtmlBlock);
            updateBackgroundColor(paragraph);
        }
        else
        {
            // The paragraph arranger copies the HTML block Id to a new inserted paragraph.
            // At this case we should remove the HTML block manually
            // if the current paragraph is outside of any HTML blocks.
            paragraph.getParaPr().remove(ParaAttr.HTML_BLOCK_ID);
        }
    }

    /// <summary>
    /// Marks the specified paragraph as empty in the HTML document.
    /// </summary>
    void markParagraphAsEmptyInHtml(Paragraph paragraph)
    {
        if (mDisabled)
            return;

        mEmptyInHtmlParagraphs.add(paragraph);
    }

    /// <summary>
    /// Updates table properties if it is inside <see cref="HtmlBlock"/>.
    /// </summary>
    void updateTable(Table table)
    {
        if (mDisabled)
            return;

        if (isInsideHtmlBlock() && (table.getFirstRow() != null))
        {
            HtmlBlock htmlBlock = mOpenedHtmlBlocksStack.peek();

            // Set the HTML block for all table rows.
            for (Row row : (Iterable<Row>) table.getRows())
            {
                row.getTablePr().setHtmlBlockId(htmlBlock.getId());
            }

            Paragraph paragraphAfterTable = ms.as(table.getNextSibling(), Paragraph.class);
            if (paragraphAfterTable != null)
            {
                updateParagraph(paragraphAfterTable, htmlBlock, false);
            }
        }
    }

    /// <summary>
    /// Inserts a hidden empty paragraph that prevents the table from concatenating with the previous table in MS Word.
    /// </summary>
    void insertSeparatorParagraphBeforeTable(Table table)
    {
        if (table == null)
            return;

        Table prevTable = ms.as(table.getPreviousNonAnnotationSibling(), Table.class);
        if (prevTable == null)
            return;

        // WORDSNET-24029 In certain cases HTML blocks prevent tables from concatenating and MS Word doesn't
        // write a separator paragraph.
        if (!msWordSeparatesTables(prevTable, table))
            return;

        // Insert a hidden empty separator paragraph.
        Paragraph para = new Paragraph(mBuilder.getDocument());
        para.getParagraphBreakRunPr().setHidden(AttrBoolEx.TRUE);
        prevTable.insertNext(para);

        // If the second table is inside a HTML block, place the separator paragraph in that block too.
        // This is what MS Word does.
        int tableHtmlBlockId = (table.getFirstRow() != null)
            ? table.getFirstRow().getTablePr().getHtmlBlockId()
            : 0;
        if (tableHtmlBlockId != 0)
        {
            para.getParaPr().setHtmlBlockId(tableHtmlBlockId);
        }
    }

    /// <summary>
    /// Indicates whether current HTML element creates a HTML block in the document model.
    /// </summary>
    boolean createsHtmlBlock(String elementName, CssDeclarationCollection elementDeclarations)
    {
        return !mDisabled && CssUtil.createsHtmlBlock(elementName, elementDeclarations, mApplyFormattingAsMsWord);
    }

    private boolean isInsideHtmlBlock() { return mOpenedHtmlBlocksStack.getCount() > 0; }

    private boolean isCurrentSectionEmpty()
    {
        // WORDSNET-22838 MS Word considers a paragraph with the cross annotations only as an empty.
        // At this case it doesn't add an empty intermediate HTML block. Mimic this behavior.
        return mBuilder.getCurrentParagraph().isEmptyOrContainsOnlyCrossAnnotation() &&
        (mBuilder.getCurrentParagraph().getParentStory() != null) &&
        (mBuilder.getCurrentParagraph().getParentStory().getNodeType() == NodeType.BODY) &&
        (mBuilder.getCurrentParagraph().getParentStory().getCount() == 1);
    }

    private HtmlBlock createHtmlBlock()
    {
        HtmlBlock htmlBlock = new HtmlBlock(generateNextId());
        return htmlBlock;
    }

    private static void updateParagraph(
        Paragraph paragraph,
        HtmlBlock htmlBlock,
        boolean removePropertiesAppliedToHtmlBlock)
    {
        paragraph.getParaPr().setAttr(ParaAttr.HTML_BLOCK_ID, htmlBlock.getId());

        // If a new HTML block was started then the current paragraph and HTML block
        // share the same properties. Remove duplicates.
        if (removePropertiesAppliedToHtmlBlock)
        {
            paragraph.getParaPr().remove(ParaAttr.BORDER_LEFT);
            paragraph.getParaPr().remove(ParaAttr.BORDER_RIGHT);
            paragraph.getParaPr().remove(ParaAttr.BORDER_TOP);
            paragraph.getParaPr().remove(ParaAttr.BORDER_BOTTOM);
            paragraph.getParaPr().remove(ParaAttr.LEFT_INDENT);
            paragraph.getParaPr().remove(ParaAttr.RIGHT_INDENT);
            // MS Word sets direct spacing for a paragraph if it is not a list item. Mimic this behavior.
            if (!paragraph.isListItem())
            {
                paragraph.getParaPr().remove(ParaAttr.SPACE_BEFORE);
                paragraph.getParaPr().remove(ParaAttr.SPACE_AFTER);
            }
        }
    }

    private int generateNextId()
    {
        // MS Word uses positive random integers for HTML block ID values.
        // HTML block collection may contain already created HTML blocks.
        int resultingId;
        do
        {
            resultingId = Math.abs(RandomUtil.newGuid().hashCode());
        }
        while ((resultingId == 0) ||
            mGeneratedIds.containsKey(resultingId));

        mGeneratedIds.add(resultingId, null);
        return resultingId;
    }

    /// <summary>
    /// Makes sure all parent blocks of the specified HTML block are also added to the collection.
    /// </summary>
    private void addParentHtmlBlock(HtmlBlock htmlBlock)
    {
        HtmlBlock currentHtmlBlock = htmlBlock;
        // Check all preceding HTML blocks.
        if (currentHtmlBlock.getParentId() != 0)
        {
            for (HtmlBlock htmlBlockId : mOpenedHtmlBlocksStack)
            {
                if (getHtmlBlockById(htmlBlockId.getId()) == null)
                {
                    addHtmlBlock(htmlBlockId);
                }
                if (htmlBlockId.getParentId() == 0)
                    break;
            }
        }
    }

    private boolean hasParentBodyHtmlBlock()
    {
        if ((mInsertHtmlTargetParagraph == null) ||
            (mInsertHtmlTargetParagraph.getParaPr().getHtmlBlockId() == 0))
        {
            return false;
        }

        HtmlBlock currentHtmlBlock = getHtmlBlockById(mInsertHtmlTargetParagraph.getParaPr().getHtmlBlockId());
        while (currentHtmlBlock != null)
        {
            if (currentHtmlBlock.getHtmlBlockType() == HtmlBlockType.BODY)
                return true;

            currentHtmlBlock = getHtmlBlockById(currentHtmlBlock.getParentId());
        }
        return false;
    }

    private HtmlBlock getHtmlBlockById(int id)
    {
        return mBuilder.getDocument().getHtmlBlockCollection().getHtmlBlockById(id);
    }

    private void addHtmlBlock(HtmlBlock htmlBlock)
    {
        mBuilder.getDocument().getHtmlBlockCollection().add(htmlBlock);
    }

    /// <summary>
    /// Enumerates HTML blocks that contain the specified table and return their IDs in a stack. The top of the stack is
    /// the outermost block's ID.
    /// </summary>
    private IntStack enumerateContainingHtmlBlocks(Table table)
    {
        IntStack blockIds = new IntStack();
        int blockId = (table.getFirstRow() != null)
            ? table.getFirstRow().getTablePr().getHtmlBlockId()
            : 0;
        for (HtmlBlock htmlBlock = findHtmlBlock(blockId); htmlBlock != null; htmlBlock = findHtmlBlock(blockId))
        {
            blockIds.push(blockId);
            blockId = htmlBlock.getParentId();
        }
        return blockIds;
    }

    /// <summary>
    /// Returns a value indicating whether MS Word adds an empty hidden separator paragraph between the specified adjacent
    /// tables in order to prevent their rows from concatenating.
    /// </summary>
    private boolean msWordSeparatesTables(Table table1, Table table2)
    {
        IntStack table1Ids = enumerateContainingHtmlBlocks(table1);
        IntStack table2Ids = enumerateContainingHtmlBlocks(table2);

        if ((table1Ids.getCount() == 0) && (table2Ids.getCount() == 0))
        {
            // Adjacent tables that are not enclosed by HTML blocks are always separated by an empty span.
            return true;
        }

        // MS Word demonstrates counter-intuitive behavior when it comes to separating tables in HTML blocks. Tables are
        // separated if the second one is more deeply nested in HTML blocks, or if both tables are nested equally deep
        // but share a common HTML block. If the first table is more deeply nested than the second one, MS Word doesn't
        // separate them.
        boolean haveCommonBlocks =
            (table1Ids.getCount() > 0) &&
            (table2Ids.getCount() > 0) &&
            (table1Ids.peek() == table2Ids.peek());

        return (table2Ids.getCount() > table1Ids.getCount()) ||
            ((table2Ids.getCount() == table1Ids.getCount()) && haveCommonBlocks);
    }

    /// <summary>
    /// Searches for a HTML block by its ID both among blocks that are already stored in the document and among active
    /// open blocks that haven't been added to the document yet.
    /// </summary>
    private HtmlBlock findHtmlBlock(int blockId)
    {
        if (blockId == 0)
            return null;

        HtmlBlock knownBlock = getHtmlBlockById(blockId);
        if (knownBlock != null)
            return knownBlock;

        for (HtmlBlock openBlock : mOpenedHtmlBlocksStack)
        {
            if (openBlock.getId() == blockId)
                return openBlock;
        }

        return null;
    }

    private void updateBackgroundColor(Paragraph paragraph)
    {
        Debug.assertTrue(mOpenedHtmlBlockBackgroundColors.getCount() > 0);
        mOpenedHtmlBlockBackgroundColors.peek().toShading(paragraph.getParagraphFormat().getShading());
    }

    private static void setBorder(
        CssDeclarationCollection elementDeclarations,
        ParaPr paraPr)
    {
        for (/*BorderType*/int borderType : G_BORDER_TYPES)
        {
            setBorder(elementDeclarations, paraPr, borderType);
        }
    }

    private static void setBorder(
        CssDeclarationCollection elementDeclarations,
        ParaPr paraPr,
        /*BorderType*/int borderType)
    {
        Border border;
        CssBorder cssBorder = CssBorder.createHtmlBlockBorder(elementDeclarations, borderType);

        if ((cssBorder.getLineStyle() != LineStyle.NONE) && (!DrColor.equals(cssBorder.getColorInternal(), DrColor.TRANSPARENT)))
        {
            applyCSSBorderStyle(elementDeclarations, paraPr, cssBorder, borderType);
        }
        else
        {
            // Set border color if border declaration is not defined but border color is present.
            String borderColorProperty;
            switch (borderType)
            {
                case BorderType.LEFT:
                    borderColorProperty = "border-left-color";
                    break;
                case BorderType.RIGHT:
                    borderColorProperty = "border-right-color";
                    break;
                case BorderType.TOP:
                    borderColorProperty = "border-top-color";
                    break;
                case BorderType.BOTTOM:
                    borderColorProperty = "border-bottom-color";
                    break;
                default:
                    return;
            }
            DrColor borderColor = elementDeclarations.getColor(borderColorProperty);
            if (borderColor != null)
            {
                border = new Border();
                border.setColorInternal(borderColor);
                setBorder(paraPr, border, borderType);
            }
        }
    }

    private static void setBorder(
        ParaPr paraPr,
        Border border,
        /*BorderType*/int borderType)
    {
        switch (borderType)
        {
            case BorderType.LEFT:
                paraPr.setAttr(ParaAttr.BORDER_LEFT, border);
                break;
            case BorderType.RIGHT:
                paraPr.setAttr(ParaAttr.BORDER_RIGHT, border);
                break;
            case BorderType.TOP:
                paraPr.setAttr(ParaAttr.BORDER_TOP, border);
                break;
            case BorderType.BOTTOM:
                paraPr.setAttr(ParaAttr.BORDER_BOTTOM, border);
                break;
            default:
                // Unsupported border type. Nothing to do.
                break;
        }
    }

    private static void applyCSSBorderStyle(
        CssDeclarationCollection elementDeclarations,
        ParaPr paraPr,
        CssBorder cssBorder,
        /*BorderType*/int borderType)
    {
        Border border;
        String paddingAttribute;
        switch (borderType)
        {
            case BorderType.LEFT:
                border = paraPr.getBorderLeft();
                paddingAttribute = "padding-left";
                break;
            case BorderType.RIGHT:
                border = paraPr.getBorderRight();
                paddingAttribute = "padding-right";
                break;
            case BorderType.TOP:
                border = paraPr.getBorderTop();
                paddingAttribute = "padding-top";
                break;
            case BorderType.BOTTOM:
                border = paraPr.getBorderBottom();
                paddingAttribute = "padding-bottom";
                break;
            default:
                // Unsupported border type. Nothing to do.
                return;
        }

        if (Border.EMPTY.equals(border))
        {
            border = new Border();
            setBorder(paraPr, border, borderType);
        }

        border.setLineStyleInternal(cssBorder.getLineStyle());
        border.setColorInternal(cssBorder.getColorInternal());
        border.setLineWidthSafe(convertToMswBorderWidth(cssBorder.getLineWidth()));

        // If the border has a line style then padding value should be set to distance of the border from text.
        if (border.getLineStyle() != LineStyle.NONE)
        {
            CssDeclaration paddingDeclaration = elementDeclarations.get(paddingAttribute);
            if (paddingDeclaration == null)
                return;

            CssPropertyValue paddingValue = paddingDeclaration.getValue();
            if (paddingValue.getFirstValue().getValueType() == CssValueType.LENGTH)
            {
                CssLengthValue lengthValue = (CssLengthValue)paddingValue.getFirstValue();
                int distanceFromText = DoublePal.roundToIntUp(lengthValue.getLength(CssUnit.PT));

                // WORDSNET-25528 Do not throw exception, if distance from text exceeds maximum value.
                border.setDistanceFromTextSafe(distanceFromText);
            }
        }
    }

    private static void setMargin(
        CssDeclarationCollection elementDeclarations,
        ParaPr paraPr)
    {
        setMargin(elementDeclarations, "margin-left", ParaAttr.HTML_MARGIN_LEFT, paraPr);
        setMargin(elementDeclarations, "margin-right", ParaAttr.HTML_MARGIN_RIGHT, paraPr);
        setMargin(elementDeclarations, "margin-top", ParaAttr.HTML_MARGIN_TOP, paraPr);
        setMargin(elementDeclarations, "margin-bottom", ParaAttr.HTML_MARGIN_BOTTOM, paraPr);
    }

    private static void setMargin(
        CssDeclarationCollection elementDeclarations,
        String propertyName,
        int paraAttr,
        ParaPr paraPr)
    {
        double marginValueInTwips;
        if ("auto".equals(elementDeclarations.getIdentifier(propertyName)))
        {
            // The 'auto' margin value should be converted to zero.
            marginValueInTwips = 0.0;
        }
        else
        {
            double marginValueInPoints = elementDeclarations.getLength(propertyName);
            if (MathUtil.isMinValue(marginValueInPoints))
            {
                // MS Word uses the percentage value as the absolute margin value in twips.
                // The maximum allowed value is 500%.
                marginValueInTwips = Math.min(500.0, elementDeclarations.getPercentage(propertyName));
            }
            else
            {
                // According to ECMA-376-1:2016 17.15.2.26 marLeft (Left Margin for HTML div).
                marginValueInTwips = ConvertUtilCore.pointToTwip(marginValueInPoints);
            }
        }
        if (!MathUtil.isMinValue(marginValueInTwips))
        {
            // Top and Bottom margins should be positive or zero.
            boolean acceptNegativeValues = (!"margin-top".equals(propertyName)) && (!"margin-bottom".equals(propertyName));

            if (MathUtil.isGreaterOrEqual(marginValueInTwips, 0.0) || acceptNegativeValues)
            {
                // MS Word limits the max value for the margins:
                // 21600 twips for the left and the right.
                // 18928 twips for the up and the down.
                int marginLimit = ("margin-left".equals(propertyName)) || ("margin-right".equals(propertyName))
                    ? 21600
                    : 18928;
                paraPr.setAttr(paraAttr, Math.min(marginLimit, MathUtil.doubleToInt(marginValueInTwips)));
            }
        }
    }

    private static /*HtmlBlockType*/int getHtmlBlockType(String elementName)
    {
        switch (gStringSwitchMap.of(elementName))
        {
            case /*"body"*/0:
                return HtmlBlockType.BODY;
            case /*"blockquote"*/1:
                return HtmlBlockType.BLOCK_QUOTE;
            default:
                return HtmlBlockType.DIV;
        }
    }

    /// <summary>
    /// Returns a MS Word border width that is the closest to the specified value.
    /// </summary>
    private static double convertToMswBorderWidth(double borderWidth)
    {
        int borderWidthInEighthPoints = ConvertUtilCore.pointToEightsPoint(borderWidth);

        if (borderWidthInEighthPoints <= G_MSW_BORDER_WIDTHS[0])
        {
            return ConvertUtilCore.eightsPointToPoint(G_MSW_BORDER_WIDTHS[0]);
        }
        for (int i = 0; i < (G_MSW_BORDER_WIDTHS.length - 1); i++)
        {
            int midpoint = (G_MSW_BORDER_WIDTHS[i] + G_MSW_BORDER_WIDTHS[i + 1]) / 2;
            // Return the greater value if the original width is right in the middle between two values.
            if (borderWidthInEighthPoints < midpoint)
            {
                return ConvertUtilCore.eightsPointToPoint(G_MSW_BORDER_WIDTHS[i]);
            }
        }
        return ConvertUtilCore.eightsPointToPoint(G_MSW_BORDER_WIDTHS[G_MSW_BORDER_WIDTHS.length - 1]);
    }

    private void rememberOpenedHtmlBlock(HtmlBlock htmlBlock, CssBackgroundColor backgroundColor)
    {
        mOpenedHtmlBlocksStack.push(htmlBlock);
        mOpenedHtmlBlockBackgroundColors.push(backgroundColor);
    }

    private HtmlBlock forgetOpenedHtmlBlock()
    {
        HtmlBlock htmlBlock = mOpenedHtmlBlocksStack.pop();
        mOpenedHtmlBlockBackgroundColors.pop();
        return htmlBlock;
    }

    /// <summary>
    /// MS Word uses only the following border width values when importing HTML blocks. In 1/8th of a point.
    /// </summary>
    private static /*final*/ int[] G_MSW_BORDER_WIDTHS = { 2, 4, 6, 8, 12, 18, 24, 36, 48 };

    /// <summary>
    /// The paragraph into which the HTML document is being inserted.
    /// </summary>
    private Paragraph mInsertHtmlTargetParagraph;

    private DocumentBuilder mBuilder;

    private HtmlBlock mEmptyInitialHtmlBlock;

    /// <summary>
    /// A flag indicating whether creation of HTML blocks is in fact disabled and the HTML block reader is "idle".
    /// </summary>
    /// <remarks>
    /// If creation of HTML blocks is disabled, the HTML block reader is still called by the outer code but it doesn't do
    /// any useful job. We've introduced this "idle" mode in order to simplify the outer logic and make it unconditional
    /// by moving all condition checks inside the HTML block reader.
    /// </remarks>
    private /*final*/ boolean mDisabled;

    /// <summary>
    /// Indicates whether we should stick to MS Word's rules when applying CSS formatting to document model.
    /// </summary>
    private /*final*/ boolean mApplyFormattingAsMsWord;

    /// <summary>
    /// A stack of HTML blocks that are opened at the moment.
    /// </summary>
    /// <remarks>
    /// Used to resolve nested HTML blocks.
    /// </remarks>
    private /*final*/ msStack<HtmlBlock> mOpenedHtmlBlocksStack = new msStack<HtmlBlock>();

    /// <summary>
    /// A stack of CSS background colors that are connected with the opened HTML block.
    /// </summary>
    private /*final*/ msStack<CssBackgroundColor> mOpenedHtmlBlockBackgroundColors = new msStack<CssBackgroundColor>();

    /// <summary>
    /// Generates unique IDs for created HTML blocks.
    /// </summary>
    /// <remarks>
    /// We use this dictionary as a hash set of integers and are only interested in item keys. Item values are ignored.
    /// We can't use the HTML block collection instead of dictionary because not all opened HTML blocks may be added to the collection.
    /// </remarks>
    private /*final*/ IntToObjDictionary<Object> mGeneratedIds = new IntToObjDictionary<Object>();

    /// <summary>
    /// A set of <see cref="Paragraph"/> nodes that correspond to empty paragraphs in the HTML document.
    /// </summary>
    private /*final*/ HashSetGeneric<Paragraph> mEmptyInHtmlParagraphs = new HashSetGeneric<Paragraph>();

    private static /*final*/ /*BorderType*/int[] G_BORDER_TYPES = new /*BorderType*/int[]
    {
        BorderType.TOP,
        BorderType.RIGHT,
        BorderType.BOTTOM,
        BorderType.LEFT
    };

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"body",
		"blockquote"
	);

}

