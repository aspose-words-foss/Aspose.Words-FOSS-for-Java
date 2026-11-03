// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/10/2017 by Artem Shabarshin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.DrColor;
import java.util.Map;


/// <summary>
/// Provides methods for updating document content colors from theme.
/// </summary>
/// <remarks>
/// AM. So far only few document parts are updated. Will add more later on customer requests.
/// Actually this class is a workaround, good solution is to join ordinal color and theme color 
/// into one attribute the same way as we did for fonts but it's a very complex task.
/// </remarks>
class ThemeColorUpdater
{
    static void update(Document doc)
    {
        update(doc.getStyles(), doc.getThemeInternal());
    }

    /// <summary>
    /// Updates style colors from given theme.
    /// </summary>
    static void update(Style style, Theme theme)
    {
        TableStyle tableStyle = ms.as(style, TableStyle.class);
        if (tableStyle != null)
            applyTheme(tableStyle.getConditionalStyles().getDefinedStyles(), theme);
        applyTheme(style.getParaPr(), theme);
    }

    /// <summary>
    /// Updates background/foreground table style shading colors from theme colors if specified.
    /// </summary>
    private static void update(StyleCollection styles, Theme theme)
    {
        if ((styles == null) || (theme == null))
            return;

        for (Style style : styles)
            update(style, theme);
    }

    /// <summary>
    /// Updates background/foreground row shading and border colors from theme colors if specified.
    /// If ImportFormatMode.UseDestinationStyles is specified we have to permanently modify direct color formatting of row
    /// using destination document themes. 
    /// </summary>
    static void update(Row row)
    {
        Theme docTheme = row.getDocument().getThemeInternal();

        if (docTheme == null)
            return;

        TablePr tablePr = row.getTablePr();

        if (tablePr.contains(TableAttr.SHADING))
            updateShadingColorFromTheme(tablePr.getShading(), docTheme);

        for (int borderKey : TablePr.POSSIBLE_BORDER_KEYS.getValues())
            updateBorderColorFromTheme((Border)tablePr.get(borderKey), docTheme);
    }

    /// <summary>
    /// Updates background/foreground cell shading and border colors from theme colors if specified.
    /// If ImportFormatMode.UseDestinationStyles is specified we have to permanently modify direct color formatting of cell
    /// using destination document themes. 
    /// </summary>
    static void update(Cell cell)
    {
        Theme docTheme = cell.getDocument().getThemeInternal();

        if (docTheme == null)
            return;

        CellPr cellPr = cell.getCellPr();

        if (cellPr.contains(CellAttr.SHADING))
            updateShadingColorFromTheme(cellPr.getShading(), docTheme);

        for (int borderKey : CellPr.ALL_BORDER_KEYS)
            updateBorderColorFromTheme((Border)cellPr.get(borderKey), docTheme);
    }

    /// <summary>
    /// Updates Color attribute from document theme if needed for run.
    /// </summary>
    static void update(RunPr runPr, Theme theme)
    {
        // ThemeColor should be specified.
        Object themeColor = runPr.get(FontAttr.THEME_COLOR);
        if (themeColor == null)
            return;

        DrColor newColor =
            OfficeColor.resolve(themeColor, runPr.get(FontAttr.THEME_TINT), runPr.get(FontAttr.THEME_SHADE), theme);

        // Don't want to update colors too often so let some threshold.
        // WORDSNET-16424 Do not update color when resolved color is empty.
        if ((newColor != null) && (!newColor.isEmpty()) && (maxComponentDelta(runPr.getColor(), newColor) > 3))
            runPr.setColor(newColor);
    }

    /// <summary>
    /// Updates background/foreground shading colors.
    /// </summary>
    private static void updateShadingColorFromTheme(Shading shading, Theme docTheme)
    {
        // Apply background theme color and shading.
        DrColor finalThemeColor = OfficeColor.resolve(shading.getThemeFill(), shading.getThemeFillTint(), shading.getThemeFillShade(), docTheme);
        if (finalThemeColor != null)
            shading.setBackgroundPatternColorInternal(finalThemeColor);

        // Apply foreground theme color and shading.
        finalThemeColor = OfficeColor.resolve(shading.getThemeColor(), shading.getThemeTint(), shading.getThemeShade(), docTheme);
        if (finalThemeColor != null)
            shading.setForegroundPatternColorInternal(finalThemeColor);
    }

    /// <summary>
    /// Updates border color, applies theme shade and tint.
    /// </summary>
    private static void updateBorderColorFromTheme(Border border, Theme docTheme)
    {
        if ((docTheme == null) || (border == null))
            return;

        DrColor finalThemeColor = OfficeColor.resolve(border.getThemeColorInternal(), border.getThemeTint(), border.getThemeShade(), docTheme);

        if (finalThemeColor != null)
            border.setColorInternal(finalThemeColor);
    }

    /// <summary>
    /// Applies a theme to a table style.
    /// </summary>
    private static void applyTheme(Iterable<ConditionalStyle> conditionalStyles, Theme theme)
    {
        for (ConditionalStyle conditionalStyle : conditionalStyles)
        {
            if ((conditionalStyle != null) && 
                conditionalStyle.hasCellFormatting() && 
                (conditionalStyle.getCellPr().getShading() != null))
                updateShadingColorFromTheme(conditionalStyle.getCellPr().getShading(), theme);
        }
    }

    /// <summary>
    /// Applies a theme to any style.
    /// </summary>
    private static void applyTheme(ParaPr styleParaPr, Theme theme)
    {
        for (Map.Entry<Integer, Integer> borderKey : ParaPr.POSSIBLE_BORDER_KEYS)
            updateBorderColorFromTheme((Border)styleParaPr.get(borderKey.getValue()), theme);
    }

    /// <summary>
    /// Calculates max difference in RGB components.
    /// </summary>
    private static int maxComponentDelta(DrColor color1, DrColor color2)
    {
        int maxR = Math.abs(color1.getR() - color2.getR());
        int maxG = Math.abs(color1.getG() - color2.getG());
        int maxB = Math.abs(color1.getB() - color2.getB());

        return Math.max(Math.max(maxR, maxG), maxB);
    }
}

