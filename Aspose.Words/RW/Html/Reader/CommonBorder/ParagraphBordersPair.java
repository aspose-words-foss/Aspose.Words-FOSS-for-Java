// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2015 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Stores Paragraph and BordersInfo pair.
/// </summary>
class ParagraphBordersPair
{
    ParagraphBordersPair(Paragraph paragraph, BordersInfo borders)
    {
        Debug.assertTrue(paragraph != null);
        Debug.assertTrue(borders != null);

        Paragraph = paragraph;
        Borders = borders;
    }

    /*final*/ Paragraph Paragraph;
    /*final*/ BordersInfo Borders;
}

