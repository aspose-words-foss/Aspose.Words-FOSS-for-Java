// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/09/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Text.msStringBuilder;


abstract class CodeBlockBase extends InlineContainerBlock
{
    /// <summary>
    /// Parses text in content lines onto child Inline blocks.
    /// </summary>
    /*override*/ void parse()
    {
        String inlineText = getInlineText();
        InlineBlock inlineBlock = new InlineBlock(inlineText);
        if (!tryAppend(inlineBlock))
            add(inlineBlock);

        // No need to keep text lines in a memory after they were parsed as inlines.
        getContentLines().clear();
        removeAllParts();
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        context.open(this);
        context.getBuilder().write(getText());
        context.close(this);
    }

    /// <summary>
    /// Gets a plain text from the content lines of the inline container.
    /// </summary>
    protected /*override*/ String getInlineText()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getContentLines().size(); i++)
        {
            msStringBuilder.append(sb, getContentLines().get(i));

            // Append EOL to each line except of very last one.
            if (i < getContentLines().size() - 1)
                sb.append(LINE_SEPARATOR);
        }

        return sb.toString();
    }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.LEAF; }
}

