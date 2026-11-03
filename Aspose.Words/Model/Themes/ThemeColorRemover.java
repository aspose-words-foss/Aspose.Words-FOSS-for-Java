// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/03/2016 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********




/// <summary>
/// Contains methods to deal with table theme shade attributes upon importing and exporting 
/// to formats that do not support themes.
/// </summary>
/// <remarks>
/// Say if we import one DOCX to an other DOCX with different themes and ImportFormatMode.KeepSourceFormatting is
/// specified, we have to preserve source formatting and avoid its updating from destination themes - to achieve this
/// we have to reset all theme attributes. 
/// </remarks>
class ThemeColorRemover
{
    /// <summary>
    /// Reset row border theme attributes and theme shading attributes of row.
    /// </summary>
    static void resetRowThemeAttrs(Row row)
    {
        resetShadingThemeAttrs(row.getTablePr().getShading());

        for (int borderKey : TablePr.POSSIBLE_BORDER_KEYS.getValues())
            resetBorderThemeAttrs((Border)row.getTablePr().get(borderKey));
    }

    /// <summary>
    /// Reset cell border theme attributes and theme shading attributes of cell.
    /// </summary>
    static void resetCellThemeAttrs(Cell cell)
    {
        resetShadingThemeAttrs(cell.getCellPr().getShading());

        for (int borderKey : CellPr.ALL_BORDER_KEYS)
            resetBorderThemeAttrs((Border)cell.getCellPr().get(borderKey));
    }

    /// <summary>
    /// Reset theme shading attributes.
    /// </summary>
    private static void resetShadingThemeAttrs(Shading shading)
    {
        if (shading != null)
        {
            shading.setThemeShade("");
            shading.setThemeTint("");
            shading.setThemeColor("");

            shading.setThemeFillShade("");
            shading.setThemeFillTint("");
            shading.setThemeFill("");
        }
    }

    /// <summary>
    /// Reset border ThemeShade, ThemeTint and ThemeColor attributes.
    /// </summary>
    private static void resetBorderThemeAttrs(Border border)
    {
        if (border != null)
        {
            border.setThemeShade("");
            border.setThemeTint("");
            border.setThemeColorInternal("");
        }
    }
}

