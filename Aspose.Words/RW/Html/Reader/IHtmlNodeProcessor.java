// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/11/2004 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// I use this interface to allow HtmlTableReader to drive import of HTML tables
/// and call main HtmlReader when necessary to process content of table cells.
/// </summary>
interface IHtmlNodeProcessor
{
    public void processCell(HtmlElementNode cellNode) throws Exception;
}

