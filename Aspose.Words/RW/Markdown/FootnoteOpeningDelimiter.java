// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown Footnote opening delimiter.
/// </summary>
class FootnoteOpeningDelimiter extends LinkTextDelimiter
{
    /// <summary>
    /// Creates a FootnoteOpeningDelimiter object from a delimiter run.
    /// </summary>
    FootnoteOpeningDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one.
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        return ((other != null) && (other.getType() == DelimiterType.LINK_TEXT_CLOSING) && !isNotIncluded() &&
            FootnoteReferenceBlock.isValid(this, other));
    }

    /// <summary>
    /// Creates a corresponding markdown inline block.
    /// </summary>
    /*override*/ Block toBlock()
    {
        return new FootnoteReferenceBlock();
    }

    /// <summary>
    /// Gets sibling LinkText opening delimiter just before this Footnote delimiter,
    /// or <c>null</c> if there is no such delimiter.
    /// </summary>
    LinkTextOpeningDelimiter getLinkTextOpening()
    {
        Delimiter linkTextOpening = (Delimiter)getPrevNode();

        if ((linkTextOpening == null) || linkTextOpening.isNotIncluded())
            return null;

        if (linkTextOpening.getType() != DelimiterType.LINK_TEXT_OPENING)
            return null;

        if ((linkTextOpening.getStart() + 1) != getEnd())
            return null;

        return (LinkTextOpeningDelimiter)linkTextOpening;
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.FOOTNOTE_OPENING; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return false; }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '^';
}

