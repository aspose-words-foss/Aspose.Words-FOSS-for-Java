// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The class responsible for writing Italic emphasis into Markdown.
/// </summary>
class MarkdownItalicEmphasisWriter extends MarkdownEmphasisWriterBase
{
    /// <summary>
    /// Initializes a new instance with the specified content lines and IRunAttrSource object.
    /// </summary>
    MarkdownItalicEmphasisWriter(TxtContentLines contentLines, IRunAttrSource src)
    {
    	super(contentLines);
	
        IsEmphasized = (src != null) && InlineHelper.getBool(src, FontAttr.ITALIC);
    }

    /// <summary>
    /// Switches delimiter string to underscores.
    /// </summary>
    /*override*/ boolean switchToUnderscore()
    {
        // The underscore delimiter cannot be set if flanking of opening delimiter is not strong Left.
        if ((getOpeningFlanking() != FlankingType.LEFT) || UseHtmlSyntax)
            return false;

        if (!ItalicInlineBlock.UNDERSCORE_DELIMITER.equals(mDelimiter))
        {
            mDelimiter = ItalicInlineBlock.UNDERSCORE_DELIMITER;
            replaceOpening(mDelimiter);
        }

        return true;
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected /*override*/ boolean getIsEmphasized(IRunAttrSource src)
    {
        return (src != null) && InlineHelper.getBool(src, FontAttr.ITALIC);
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected /*override*/ String getDelimiter()
    {
        return (UseHtmlSyntax)
            ? "<i>"
            : mDelimiter;
    }

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected /*override*/ String getCloseDelimiter()
    {
        return (UseHtmlSyntax)
            ? "</i>"
            : mDelimiter;
    }

    private String mDelimiter = ItalicInlineBlock.ASTERISK_DELIMITER;
}

