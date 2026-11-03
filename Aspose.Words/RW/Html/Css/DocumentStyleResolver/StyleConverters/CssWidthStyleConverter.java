// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.Debug;


/// <summary>
/// Helps to apply CSS width style to a model.
/// </summary>
/*static*/ class CssWidthStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssWidthStyleConverter()
	{}

    /// <summary>
    /// Applies CSS width style to a model table.
    /// </summary>
    static void toTable(
        CssDeclarationCollection declarations,
        Table table,
        CssBorder appliedLeftBorder,
        CssBorder appliedRightBorder) throws Exception
    {
        CssDeclaration widthDeclaration = declarations.get("width");
        if (widthDeclaration == null)
            return;

        PreferredWidth preferredWidth = getPreferredWidth(widthDeclaration.getValue());
        if (preferredWidth == null)
            return;

        if (preferredWidth.isFixed() && preferredWidth.isPositive())
        {
            // WORDSNET-20289 HTML table width is based on preffered table width and HTML border width.
            // HTML border width may be adjusted during export (see CssBorderWriter.GetBrowserSafeWidth).
            // Also, the original border width may be exported along with CSS border in custom round-trip properties.
            double width = preferredWidth.getValue();
            // WORDSNET-21609 Table direction should be taken into account while getting borders.
            boolean isRtl = table.getFirstRow().getTablePr().getFinalPr().getBidi();
            Border leftBorder = isRtl
                ? table.getFirstRow().getFirstCell().getCellFormat().getBorders().getRight()
                : table.getFirstRow().getFirstCell().getCellFormat().getBorders().getLeft();
            Border rightBorder = isRtl
                ? table.getFirstRow().getLastCell().getCellFormat().getBorders().getLeft()
                : table.getFirstRow().getLastCell().getCellFormat().getBorders().getRight();
            width -= getBorderWidth(leftBorder, appliedLeftBorder) / 2.0;
            width -= getBorderWidth(rightBorder, appliedRightBorder) / 2.0;

            // WORDSNET-9872 We should limit table's width to max allowed value.
            width = MathUtil.fitToRange(width, 0.0, MAX_TABLE_WIDTH);
            preferredWidth = PreferredWidth.fromPointsSafe(width);
        }
        table.setPreferredWidth(preferredWidth);
    }

    /// <summary>
    /// Applies CSS width style to a model cell.
    /// </summary>
    static void toCellFormat(
        CellFormat cellFormat,
        CssDeclarationCollection cellDeclarations,
        CssDeclarationCollection colDeclarations,
        CssDeclarationCollection[] spannedColDeclarations,
        CssBoxBorders appliedBorders,
        boolean adjustPaddings,
        boolean isBorderCollapsed,
        boolean isCellEmpty) throws Exception
    {
        // WORDSNET-23850 Previously, we added half-width of a border to all paddings. Currently, if borders
        // are collapsed then we add half-width of a border to the left and the right paddings only to mimic MS Word's
        // behavior.
        if (adjustPaddings && isBorderCollapsed)
        {
            setupPaddings(cellDeclarations, appliedBorders, cellFormat);
        }

        PreferredWidth preferredWidth = getPreferedCellWidth(
            cellDeclarations,
            colDeclarations,
            spannedColDeclarations,
            isCellEmpty);
        if (preferredWidth == null)
        {
            return;
        }
        if (preferredWidth.getType() == PreferredWidthType.PERCENT)
        {
            cellFormat.setPreferredWidth(preferredWidth);
            return;
        }

        double cellWidth = preferredWidth.getValue();

        if (adjustPaddings)
        {
            // WORDSNET-9414 Left and right paddings and borders don't affect cell width in MS Word but they
            // increase total cell width in HTML. Here we adjust the HTML cell width accordingly.

            double paddingLeft = getLength(cellDeclarations.get("padding-left"));
            cellWidth += paddingLeft;
            cellWidth += getBorderWidth(cellFormat.getBorders().getLeft(), appliedBorders.getLeft()) / 2.0;

            double paddingRight = getLength(cellDeclarations.get("padding-right"));
            cellWidth += paddingRight;
            cellWidth += getBorderWidth(cellFormat.getBorders().getRight(), appliedBorders.getRight()) / 2.0;
        }

        if (cellWidth > 0)
        {
            // WORDSNET-9872 We should limit cell's width to max allowed value.
            cellWidth = Math.min(cellWidth, MAX_CELL_WIDTH);

            cellFormat.setPreferredWidth(PreferredWidth.fromPointsSafe(cellWidth));
        }
    }

    static PreferredWidth getPreferredWidth(CssPropertyValue propertyValue)
    {
        if (propertyValue.getCount() != 1)
            return null;

        PreferredWidth preferredWidth = null;
        CssValue cssValue = propertyValue.getFirstValue();
        if (cssValue.getValueType() == CssValueType.PERCENTAGE)
        {
            double widthPercentRaw = cssValue.getDoubleValue();
            if (widthPercentRaw >= 0)
            {
                widthPercentRaw = PreferredWidth.limitPercentsToSafeRange(widthPercentRaw);
                preferredWidth = PreferredWidth.fromPercentSafe(widthPercentRaw);
            }
        }
        else
        {
            double widthRaw = CssUtil.lengthToPoint(cssValue);
            if (widthRaw >= 0)
            {
                widthRaw = PreferredWidth.limitPointsToSafeRange(widthRaw);
                preferredWidth = PreferredWidth.fromPointsSafe(widthRaw);
            }
        }
        return preferredWidth;
    }

    private static PreferredWidth getPreferedCellWidth(
        CssDeclarationCollection cellDeclarations,
        CssDeclarationCollection colDeclarations,
        CssDeclarationCollection[] spannedColDeclarations,
        boolean isCellEmpty)
    {
        // First, try to get width from the cell itself.
        PreferredWidth result = getPreferredWidth(cellDeclarations, isCellEmpty);
        if (result != null)
        {
            return result;
        }

        // Second, try to get width from the corresponding COL element.
        result = getPreferredWidth(colDeclarations, isCellEmpty);

        // If the cell spans more than one COL element, try to sum widths specified on all of them.
        if ((result != null) && (spannedColDeclarations != null))
        {
            Debug.assertTrue(result.getType() != PreferredWidthType.AUTO);

            int sumValueRaw = result.getValueRaw();
            for (CssDeclarationCollection singleSpannedColDeclaration : spannedColDeclarations)
            {
                PreferredWidth additionalWidth = getPreferredWidth(singleSpannedColDeclaration, isCellEmpty);
                if ((additionalWidth == null) || (additionalWidth.getType() != result.getType()))
                {
                    // We can sum only compatible widths that have the same type.
                    result = null;
                    break;
                }
                sumValueRaw += additionalWidth.getValueRaw();
            }

            // If all widths specified on all COL elements are compatible, return their sum. Otherwise, return no width.
            if (result != null)
            {
                result = PreferredWidth.fromRawSafe(result.getType(), sumValueRaw);
            }
        }

        return result;
    }

    private static PreferredWidth getPreferredWidth(
        CssDeclarationCollection cssDeclarations,
        boolean isCellEmpty)
    {
        PreferredWidth result = null;

        PreferredWidth minWidth = null;
        CssDeclaration minWidthDeclaration = cssDeclarations.get("min-width");
        if (minWidthDeclaration != null)
        {
            minWidth = getPreferredWidth(minWidthDeclaration.getValue());
            // "min-width" doesn't work for table cells in browsers when its value is percentage.
            if (minWidth.getType() == PreferredWidthType.PERCENT)
            {
                minWidth = null;
            }
        }

        // WORDSNET-18622 If a width value is specified, it should be compared with a "min-width" value, 
        // and the result is the maximum value among them.
        // Otherwise, if the cell is empty, the result is the "min-width" value, 
        // and if the cell is not empty, the result is null that'll be converted to "auto" later.
        // This is a limited fix. We effectively ignore "min-width" on non-empty cells, because there is no 
        // counterpart for "min-width" in the document model and the actual width of the cell's contents will 
        // is unknown until the layout stage.
        CssDeclaration cellWidthDeclaration = cssDeclarations.get("width");
        if (cellWidthDeclaration != null)
        {
            result = getPreferredWidth(cellWidthDeclaration.getValue());
            if ((result != null) &&
                (minWidth != null) &&
                (minWidth.getType() == result.getType()) &&
                (result.getValue() < minWidth.getValue()))
            {
                result = minWidth;
            }
        }
        else if (isCellEmpty)
        {
            result = minWidth;
        }

        return result;
    }

    private static void setupPaddings(
        CssDeclarationCollection cellDeclarations,
        CssBoxBorders appliedBorders,
        CellFormat cellFormat) throws Exception
    {
        // Table borders are already integrated to cell borders if they are "collapsed"
        // and it is wise to use them.

        // WORDSNET-21609 We should add half-width of a border only if the padding value is greater than 0,
        // because CssTableWriter.GetHtmlCellPadding subtracts border half-width from the padding value during export
        // to HTML only if the half-width is less than the original padding.
        // WORDSNET-23850 Only the left and the right paddings should be adjusted. Vertical padding of cells
        // in MS Word works in the same way as in browsers.

        if ((cellFormat.getRightPadding() > 0) &&
            (cellDeclarations.get(HtmlConstants.ASPOSE_PADDING_RIGHT) == null))
        {
            cellFormat.setRightPadding(cellFormat.getRightPadding() + (getBorderWidth(cellFormat.getBorders().getRight(), appliedBorders.getRight()) / 2.0));
        }

        if ((cellFormat.getLeftPadding() > 0) &&
            (cellDeclarations.get(HtmlConstants.ASPOSE_PADDING_LEFT) == null))
        {
            cellFormat.setLeftPadding(cellFormat.getLeftPadding() + (getBorderWidth(cellFormat.getBorders().getLeft(), appliedBorders.getLeft()) / 2.0));
        }
    }

    private static double getBorderWidth(Border border, CssBorder appliedBorder)
    {
        if ((border == null) || !border.isVisible())
        {
            return 0.0;
        }
        return (appliedBorder != null)
            ? appliedBorder.getCssBorderWidth()
            : border.getBorderWidth();
    }

    private static double getLength(CssDeclaration paddingDeclaration)
    {
        if (paddingDeclaration == null)
            return 0.0;

        double length = CssUtil.lengthToPoint(paddingDeclaration.getValue());
        if (MathUtil.areEqual(length, -Double.MAX_VALUE))
            length = 0.0;
        return length;
    }

    /// <summary>
    /// MS Word allows cell's width to be manually set max to 22 inches
    /// but when importing from HTML it allows max 15 inches.
    /// </summary>
    private static final double MAX_CELL_WIDTH = 15 * 72;

    /// <summary>
    /// MS Word allows table's width to be manually set max to 22 inches
    /// but when importing from HTML it allows max 15 inches.
    /// </summary>
    private static final double MAX_TABLE_WIDTH = 15 * 72;
}

