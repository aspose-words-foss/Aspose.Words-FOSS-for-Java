// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/07/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents a markdown AutoLink closing delimiter.
/// </summary>
class AutoLinkClosingDelimiter extends AutoLinkDelimiter
{
    /// <summary>
    /// Creates an AutoLinkClosingDelimiter object from a delimiter run.
    /// </summary>
    AutoLinkClosingDelimiter(DelimiterRun delimiterRun, int offset)
    {
    	super(delimiterRun, offset);
	
    }

    /// <summary>
    /// Gets a delimiter that can be an Opening for this delimiter.
    /// </summary>
    /*override*/ Delimiter getPotentialOpeningBackward()
    {
        Delimiter curDelimiter = (Delimiter)getPrevNode();
        while (curDelimiter != null)
        {
            // Skip removed delimiters.
            if (isNotIncluded())
            {
                curDelimiter = (Delimiter)curDelimiter.getPrevNode();
                continue;
            }

            // WORDSNET-20703 There cannot be nested AutoLinks by spec, so check it and exit if found.     
            if (curDelimiter.getType() == DelimiterType.AUTO_LINK_CLOSING)
                return null;

            // Jump over linked delimiters with the same or greater priority.
            if ((curDelimiter.isLinked() && curDelimiter.isClosing()) && (getPriority() <= curDelimiter.getPriority()))
            {
                Delimiter linkedDelimiter = curDelimiter.getLinkedDelimiter();
                while (curDelimiter != linkedDelimiter)
                {
                    // There cannot be nested AutoLinks by spec, so check it and exit if found.     
                    if (curDelimiter.getType() == DelimiterType.AUTO_LINK_CLOSING)
                        return null;

                    curDelimiter = (Delimiter)curDelimiter.getPrevNode();
                }
            }
            else
            {
                if (curDelimiter.canBeOpeningFor(this))
                    return curDelimiter;
            }

            curDelimiter = (Delimiter)curDelimiter.getPrevNode();
        }

        return null;
    }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be Opening for a specified one. 
    /// </summary>
    /*override*/ boolean canBeOpeningFor(Delimiter other)
    {
        return false;
    }

    /// <summary>
    /// Gets type of the delimiter.
    /// </summary>
    /*override*/ /*DelimiterType*/int getType() { return DelimiterType.AUTO_LINK_CLOSING; }

    /// <summary>
    /// Gets a boolean value indicating the delimiter can be a Closing.
    /// </summary>
    protected /*override*/ boolean canBeClosing() { return !isNotIncluded(); }

    /// <summary>
    /// Gets a corresponding character of the delimiter.
    /// </summary>
    static final char CHARACTER = '>';
}

