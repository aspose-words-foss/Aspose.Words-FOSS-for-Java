// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/07/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides footnote utility functions.
/// </summary>
/*static*/ class FootnoteUtil
{
	/* Simulation of static class by using private constructor */
	private FootnoteUtil()
	{}

    /// <summary>
    /// Returns "reference" <see cref="StyleIdentifier"/> corresponding to a <see cref="Footnote"/> type.
    /// </summary>
    static /*StyleIdentifier*/int getFootnoteReferenceStyleIdentifier(Footnote footnote)
    {
        return getFootnoteReferenceStyleIdentifier(footnote.getFootnoteType());
    }

    /// <summary>
    /// Returns "reference" <see cref="StyleIdentifier"/> corresponding to a <see cref="FootnoteType"/>.
    /// </summary>
    static /*StyleIdentifier*/int getFootnoteReferenceStyleIdentifier(/*FootnoteType*/int footnoteType)
    {
        switch (footnoteType)
        {
            case FootnoteType.FOOTNOTE:
                return StyleIdentifier.FOOTNOTE_REFERENCE;
            case FootnoteType.ENDNOTE:
                return StyleIdentifier.ENDNOTE_REFERENCE;
            default:
                throw new IllegalArgumentException();
        }
    }

    /// <summary>
    /// Returns "text" <see cref="StyleIdentifier"/> corresponding to a <see cref="Footnote"/> type.
    /// </summary>
    static /*StyleIdentifier*/int getFootnoteTextStyleIdentifier(Footnote footnote)
    {
        return getFootnoteTextStyleIdentifier(footnote.getFootnoteType());
    }

    /// <summary>
    /// Returns "text" <see cref="StyleIdentifier"/> corresponding to a <see cref="FootnoteType"/>.
    /// </summary>
    static /*StyleIdentifier*/int getFootnoteTextStyleIdentifier(/*FootnoteType*/int footnoteType)
    {
        switch (footnoteType)
        {
            case FootnoteType.FOOTNOTE:
                return StyleIdentifier.FOOTNOTE_TEXT;
            case FootnoteType.ENDNOTE:
                return StyleIdentifier.ENDNOTE_TEXT;
            default:
                throw new IllegalArgumentException();
        }
    }
}

