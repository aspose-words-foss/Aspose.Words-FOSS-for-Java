// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.Collections.msArrayList;

/// <summary>
/// Represents a markdown AtxHeading.
/// </summary>
class AtxHeadingBlock extends HeadingBlock
{
    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        if (!parse(txtLine, start))
            return false;
            
        if (getOpeningIndentationLength() > MAX_INDENTATION_LENGTH)
            return false;

        if ((getOpening().length() < MIN_OPENING_LENGTH) || (getOpening().length() > MAX_OPENING_LENGTH))
            return false;

        // It is allowed to have an empty content in AtxHeadings. But if it is not empty,
        // then opening sequence of # characters MUST be followed by a whitespace.
        if ((getContentLine().length() > 0) && !StringUtil.isWhiteSpace(getContentLine().charAt(0)))
            return false;

        msArrayList.add(getContentLines(), getContentLine());

        setLevel(getOpening().length());
        
        return true;
    }

    /// <summary>
    /// Returns true, if a specified character is allowed in a sequence of opening characters.
    /// </summary>
    protected /*override*/ boolean isOpeningChar(char c)
    {
        return (c == '#');
    }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.ATX_HEADING; }

    /// <summary>
    /// A AtxHeading min opening sequence length.
    /// </summary>
    private static final int MIN_OPENING_LENGTH = 1;
  
    /// <summary>
    /// A AtxHeading max opening sequence length.
    /// </summary>
    private static final int MAX_OPENING_LENGTH = 6;
}

