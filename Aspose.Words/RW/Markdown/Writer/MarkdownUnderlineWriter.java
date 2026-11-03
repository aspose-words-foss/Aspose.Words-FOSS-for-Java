// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/11/2023 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The class responsible for writing Underline emphasis into Markdown.
/// </summary>
class MarkdownUnderlineWriter extends MarkdownEmphasisWriterBase
{
    /// <summary>
    /// Initializes a new instance with the specified content lines and IRunAttrSource object.
    /// </summary>
    MarkdownUnderlineWriter(TxtContentLines contentLines, IRunAttrSource src)
    {
    	super(contentLines);
	
        IsEmphasized = (src != null) && (/*Underline*/Integer)InlineHelper.fetchAttr(src, FontAttr.UNDERLINE) != Underline.NONE;
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected /*override*/ boolean getIsEmphasized(IRunAttrSource src)
    {
        return (src != null) && (/*Underline*/Integer)InlineHelper.fetchAttr(src, FontAttr.UNDERLINE) != Underline.NONE;
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected /*override*/ String getDelimiter()
    {
        return (UseHtmlSyntax)
            ? "<u>"
            : UnderlineInlineBlock.DELIMITER;
    }

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected /*override*/ String getCloseDelimiter()
    {
        return (UseHtmlSyntax)
            ? "</u>"
            : UnderlineInlineBlock.DELIMITER;
    }
}

