// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/07/2023 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// The class responsible for writing Superscript emphasis into Markdown.
/// </summary>
class MarkdownSuperscriptEmphasisWriter extends MarkdownEmphasisWriterBase
{
    /// <summary>
    /// Initializes a new instance with the specified content lines and IRunAttrSource object.
    /// </summary>
    MarkdownSuperscriptEmphasisWriter(TxtContentLines contentLines, IRunAttrSource src)
    {
    	super(contentLines);
	
        IsEmphasized = hasSuperscriptAttr(src);
    }

    /// <summary>
    /// Gets a boolean value indicating whether the emphasis is applied to the specified run source.
    /// </summary>
    protected /*override*/ boolean getIsEmphasized(IRunAttrSource src)
    {
        return hasSuperscriptAttr(src);
    }

    /// <summary>
    /// Returns True if the corresponding source contains Superscript direct attribute.
    /// </summary>
    private static boolean hasSuperscriptAttr(IRunAttrSource src)
    {
        if (src == null)
            return false;

        Object value = src.getDirectRunAttr(FontAttr.VERTICAL_ALIGNMENT);
        return (value != null) && ((/*RunVerticalAlignment*/Integer)value == RunVerticalAlignment.SUPERSCRIPT);
    }

    /// <summary>
    /// Gets a string value representing delimiter of the emphasis.
    /// </summary>
    protected /*override*/ String getDelimiter() { return "<sup>"; }

    /// <summary>
    /// Gets a string value representing the emphasis closing delimiter.
    /// </summary>
    protected /*override*/ String getCloseDelimiter() { return "</sup>"; }
}

