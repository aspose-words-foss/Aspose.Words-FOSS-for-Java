// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/06/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The class responsible for writing StrikeThrough emphasis into Markdown.
/// </summary>
class MarkdownStrikeThroughWriter extends MarkdownEmphasisWriterBase
{
    /// <summary>
    /// Initializes a new instance with the specified content lines and IRunAttrSource object.
    /// </summary>
    MarkdownStrikeThroughWriter(TxtContentLines contentLines, IRunAttrSource src)
    {
    	super(contentLines);
	
        IsEmphasized = (src != null) && InlineHelper.getBool(src, FontAttr.STRIKE_THROUGH);
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected /*override*/ boolean getIsEmphasized(IRunAttrSource src)
    {
        return (src != null) && InlineHelper.getBool(src, FontAttr.STRIKE_THROUGH);
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected /*override*/ String getDelimiter()
    {
        return (UseHtmlSyntax)
            ? "<s>"
            : StrikethroughInlineBlock.DELIMITER;
    }

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected /*override*/ String getCloseDelimiter()
    {
        return (UseHtmlSyntax)
            ? "</s>"
            : StrikethroughInlineBlock.DELIMITER;
    }
}

