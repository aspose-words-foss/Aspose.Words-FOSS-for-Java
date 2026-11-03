// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2025 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.MathUtil;


/// <summary>
/// Applies CSS formatting to table of the document model.
/// </summary>
/*static*/ class HtmlTableFormater
{
	/* Simulation of static class by using private constructor */
	private HtmlTableFormater()
	{}

    /// <summary>
    /// Applies calculated CSS styles to a model table.
    /// </summary>
    /// <param name="table">A model table.</param>
    /// <param name="topCaptionCount">Number of caption rows at the top of the table.</param>
    /// <param name="bottomCaptionCount">Number of caption rows at the bottom of the table.</param>
    /// <param name="appliedLeftBorder">Left CSS border of the table.</param>
    /// <param name="appliedRightBorder">Right CSS border of the table.</param>
    /// <param name="cssStyleTracker"></param>
    /// <remarks>Table captions are imported as table rows. Top captions become leading table rows and bottom captions
    /// become trailing rows. To distinguish 'normal' table rows from caption rows we need to know how many top and bottom
    /// caption rows the table contains.</remarks>
    static void toTable(Table table,
        int topCaptionCount,
        int bottomCaptionCount,
        CssBorder appliedLeftBorder,
        CssBorder appliedRightBorder,
        CssStyleTracker cssStyleTracker) throws Exception
    {
        Debug.assertTrue(table != null);
        Debug.assertTrue(topCaptionCount >= 0);
        Debug.assertTrue(bottomCaptionCount >= 0);

        // Table direction affects table alignment so we apply direction before alignment.
        table.setBidi(cssStyleTracker.isBlockRtl());

        // Apply the alignment declared by "align" attribute of the table's parent. This alignment can be overridden
        // by "align" attribute on the table itself, which is translated to table's CSS properties, so it is processed
        // before the CSS properties.
        applyAlignment(table, cssStyleTracker);
        applyBorderSpacing(table, cssStyleTracker.getElementDeclarations());

        applyMargin(cssStyleTracker.getElementDeclarations(), table);
        for (CssDeclaration declaration : cssStyleTracker.getElementDeclarations())
            ((CssComputedDeclaration)declaration).toTable(table);
        CssWidthStyleConverter.toTable(
            cssStyleTracker.getElementDeclarations(),
            table,
            appliedLeftBorder,
            appliedRightBorder);

        applyBackgroundColor(table, new CssBackgroundColor(cssStyleTracker.getBackground().getParagraphBackgroundColor()));
    }

    private static void applyBackgroundColor(
        Table table,
        CssBackgroundColor backgroundColor)
    {
        Shading shading = new Shading();
        backgroundColor.toShading(shading);
        if (shading.isVisible())
        {
            // Word applies shading to every row and every cell.
            for (Row row : (Iterable<Row>) table.getRows())
            {
                row.getTablePr().setShading(shading.deepClone());

                for (Cell cell : (Iterable<Cell>) row.getCells())
                {
                    // Preserve cell shading processed before.
                    if (!cell.getCellPr().contains(CellAttr.SHADING))
                        cell.getCellPr().setShading(shading.deepClone());
                }
            }
        }
    }

    /// <summary>
    /// Applies CSS border-spacing style to table.
    /// </summary>
    private static void applyBorderSpacing(
        Table table,
        CssDeclarationCollection declarations) throws Exception
    {
        double borderSpacingLength = declarations.getLength("border-spacing");
        double cellSpacing = (!MathUtil.isMinValue(borderSpacingLength)) ? borderSpacingLength : 0.0;

        CssDeclaration borderCollapseDeclaration = declarations.get("border-collapse");
        boolean isBorderSeparate = (borderCollapseDeclaration == null) ||
                                borderCollapseDeclaration.getValue().equals(CssValue.Separate);

        // WORDSNET-1587 For HTML import, we should set cell spacing only if it is not zero, because
        // in MS Word when zero cell spacing is specified, the table appears differently
        // from the case where cell spacing is not specified at all. Looks like MS Word glitch.
        // WORDSNET-11389 We should set zero cell spacing when 'border-collapsed' value is 'separate'.
        if (!MathUtil.isZero(cellSpacing) || isBorderSeparate)
        {
            // Divide HTML cell spacing by two since in MS Word a half of cell spacing is stored.
            table.setCellSpacing(cellSpacing / 2.0);
        }
    }

    /// <summary>
    /// Applies 'text-align' CSS property of the parent element to a table.
    /// </summary>
    private static void applyAlignment(
        Table table,
        CssStyleTracker styleTracker) throws Exception
    {
        // In HTML, containers with right-to-left direction (for example, <div dir=rtl>) align their child tables
        // to the right edge of the page.
        /*TableAlignment*/int alignment = (styleTracker.getCurrentElementInfo().ParentBlockElement.BlockLevelDirection == CssDirection.RTL)
            ? TableAlignment.RIGHT
            : TableAlignment.LEFT;

        CssDeclaration textAlignDeclaration = styleTracker.parentTextAlign();

        if (textAlignDeclaration != null)
        {
            if (textAlignDeclaration.getValue().equals(CssValue.AwLeft))
            {
                alignment = TableAlignment.LEFT;
            }
            else if (textAlignDeclaration.getValue().equals(CssValue.AwRight))
            {
                alignment = TableAlignment.RIGHT;
            }
            else if (textAlignDeclaration.getValue().equals(CssValue.AwCenter))
            {
                alignment = TableAlignment.CENTER;
            }
        }

        alignment = htmlToWordTableAlignment(alignment, table.getBidi());
        if (table.getAlignment() != alignment)
        {
            table.setAlignment(alignment);
        }
    }

    /// <summary>
    /// Converts HTML table alignment to Word table alignment taking the table direction into account.
    /// </summary>
    /// <remarks>
    /// In the document model, table direction affects table alignment. For example, 'left' means 'the left edge
    /// of the page' for LTR tables but 'right edge of the page' for RTL tables. It is not the case in HTML documents
    /// and we need to adjust HTML table alignment during import taking table direction into account.
    /// </remarks>
    private static /*TableAlignment*/int htmlToWordTableAlignment(
        /*TableAlignment*/int alignment,
        boolean isRtlTable)
    {
        /*TableAlignment*/int result = alignment;
        if (isRtlTable)
        {
            switch (alignment)
            {
                case TableAlignment.LEFT:
                    result = TableAlignment.RIGHT;
                    break;
                case TableAlignment.RIGHT:
                    result = TableAlignment.LEFT;
                    break;
                default:
                    // Other alignment values have no opposites.
                    break;
            }
        }
        return result;
    }

    /// <summary>
    /// Converts CSS property 'margin' or 'margin-xxx' into TableAlignment.
    /// This method goes out of our standard style processing. But that's okay since we
    /// only need alignment on tables.
    /// In the future we should think about any other combinations:
    /// 'auto' on one side can coexist with non-zero on another for instance.
    /// </summary>
    public static void applyMargin(
        CssDeclarationCollection declarations,
        Table table) throws Exception
    {
        boolean leftMarginIsAuto = declarations.containsIdentifier("margin-left", "auto");
        boolean rightMarginIsAuto = declarations.containsIdentifier("margin-right", "auto");

        // If both left and right margins are not auto, we shouldn't change the table's alignment, because it is defined
        // by other attributes and properties.
        if (!leftMarginIsAuto && !rightMarginIsAuto)
        {
            return;
        }

        /*TableAlignment*/int alignment = TableAlignment.LEFT;
        if (leftMarginIsAuto)
        {
            alignment = rightMarginIsAuto
                ? TableAlignment.CENTER
                : TableAlignment.RIGHT;
        }
        table.setAlignment(htmlToWordTableAlignment(alignment, table.getBidi()));
    }
}

