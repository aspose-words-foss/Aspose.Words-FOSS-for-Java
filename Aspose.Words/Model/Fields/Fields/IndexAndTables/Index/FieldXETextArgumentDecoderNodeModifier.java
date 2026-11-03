// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/04/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msChar;
import com.aspose.Debug;


/// <summary>
/// Unescapes XE field text argument performing its validity check and trimming of double quotes if present.
/// </summary>
class FieldXETextArgumentDecoderNodeModifier extends FieldTokenDecoderNodeModifier
{
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldXETextArgumentDecoderNodeModifier(NodeRange textRange)
    {
    	super(textRange, FieldTokenDecoderOptions.ALL);
	
    }

    protected /*override*/ boolean prepareModify(Node referenceNode, Node nodeToModify)
    {
        if (NodeUtil.isZln(nodeToModify) || (nodeToModify instanceof InlineStory))
            return false;

        return true;
    }

    protected /*override*/ StringBuilder processDecodedTokenChar(StringBuilder builder, char c, int positionInTokenPart)
    {
        // Replace any whitespace character by the space as MS Word does.
        char modifiedChar = msChar.isWhiteSpace(c)
            ? ControlChar.SPACE_CHAR
            : c;
        return super.processDecodedTokenChar(builder, modifiedChar, positionInTokenPart);
    }

    protected /*override*/ RichStringBuilder processDecodedTokenChar(RichStringBuilder builder, RichChar c, int positionInTokenPart)
    {
        Debug.fail("Rich strings are not supported");

        return super.processDecodedTokenChar(builder, c, positionInTokenPart);
    }
}

