// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2025 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Defines the minimal functionality required for reading HTML and MsoHtml tables.
/// </summary>
interface ITableFormatter
{
    public void toTable(
        Table table,
        int topCaptionCount,
        int bottomCaptionCount,
        CssBorder htmlTableFirstRowFirstCellLeftBorder,
        CssBorder htmlTableFirstRowLastCellRightBorder) throws Exception;
}

