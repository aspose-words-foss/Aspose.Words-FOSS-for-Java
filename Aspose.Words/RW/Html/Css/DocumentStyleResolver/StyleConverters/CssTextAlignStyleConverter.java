// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/12/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Helps to apply CSS text-align style to a model.
/// </summary>
/*static*/ class CssTextAlignStyleConverter
{
	/* Simulation of static class by using private constructor */
	private CssTextAlignStyleConverter()
	{}

    /// <summary>
    /// Applies CSS text-align style to a model paragraph format.
    /// </summary>
    static void toParagraphFormat(CssDeclarationCollection declarations, boolean isBlockRtl, ParagraphFormat paragraphFormat)
    {
        CssDeclaration textAlign = declarations.get("text-align");
        if (textAlign == null)
        {
            return;
        }

        if (textAlign.getValue().equals(CssValue.AwStart))
        {
            // Keep the Word's default paragraph alignment.
        }
        else if (textAlign.getValue().equals(CssValue.Left) || textAlign.getValue().equals(CssValue.AwLeft))
        {
            paragraphFormat.setAlignment((isBlockRtl)
                                            ? ParagraphAlignment.RIGHT
                                            : ParagraphAlignment.LEFT);
        }
        else if (textAlign.getValue().equals(CssValue.Right) || textAlign.getValue().equals(CssValue.AwRight))
        {
            paragraphFormat.setAlignment((isBlockRtl)
                                            ? ParagraphAlignment.LEFT
                                            : ParagraphAlignment.RIGHT);
        }
        else if (textAlign.getValue().equals(CssValue.Center) || textAlign.getValue().equals(CssValue.AwCenter))
        {
            paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
        }
        else if (textAlign.getValue().equals(CssValue.Justify))
        {
            paragraphFormat.setAlignment(ParagraphAlignment.JUSTIFY);
        }
    }

    static boolean textIsAlignedWithStart(CssDeclarationCollection declarations, boolean isBlockRtl)
    {
        CssDeclaration textAlign = declarations.get("text-align");
        if (textAlign == null)
        {
            return true;
        }

        return textAlign.getValue().equals(CssValue.AwStart) ||
            (!isBlockRtl && (textAlign.getValue().equals(CssValue.Left) || textAlign.getValue().equals(CssValue.AwLeft))) ||
            (isBlockRtl && (textAlign.getValue().equals(CssValue.Right) || textAlign.getValue().equals(CssValue.AwRight)));
    }
}

