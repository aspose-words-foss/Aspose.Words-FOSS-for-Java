// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The class responsible for writing Bold emphasis into Markdown.
/// </summary>
class MarkdownBoldEmphasisWriter extends MarkdownEmphasisWriterBase
{
    /// <summary>
    /// Initializes a new instance with the specified content lines and IRunAttrSource object.
    /// </summary>
    MarkdownBoldEmphasisWriter(TxtContentLines contentLines, IRunAttrSource src)
    {
    	super(contentLines);
	
        IsEmphasized = (src != null) && InlineHelper.getBool(src, FontAttr.BOLD);
    }

    /// <summary>
    /// Switches delimiter string to underscores.
    /// </summary>
    /*override*/ boolean switchToUnderscore()
    {
        // The underscore delimiter cannot be set if flanking of opening delimiter is not strong Left.
        if ((getOpeningFlanking() != FlankingType.LEFT) || UseHtmlSyntax)
            return false;

        if (!BoldInlineBlock.UNDERSCORE_DELIMITER.equals(mDelimiter))
        {
            mDelimiter = BoldInlineBlock.UNDERSCORE_DELIMITER;
            replaceOpening(mDelimiter);
        }

        return true;
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected /*override*/ boolean getIsEmphasized(IRunAttrSource src)
    {
        return (src != null) && InlineHelper.getBool(src, FontAttr.BOLD);
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected /*override*/ String getDelimiter()
    {
        return (UseHtmlSyntax)
            ? "<b>"
            : mDelimiter;
    }

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected /*override*/ String getCloseDelimiter()
    {
        return (UseHtmlSyntax)
            ? "</b>"
            : mDelimiter;
    }

    private String mDelimiter = BoldInlineBlock.ASTERISK_DELIMITER;
}

