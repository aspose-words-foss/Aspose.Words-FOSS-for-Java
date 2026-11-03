// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/08/2024 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msString;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.StringComparison;
import com.aspose.FormatterPal;


/// <summary>
/// Represents a markdown HTML inline block.
/// </summary>
class HtmlTagBlock extends Block
{
    /// <summary>
    /// Initializes a new instance of <see cref="HtmlTagBlock"/>.
    /// </summary>
    HtmlTagBlock(HtmlTagToken htmlTagToken)
    {
        ArgumentUtil.checkNotNull(htmlTagToken, "htmlTagToken");
        mHtmlTagToken = htmlTagToken;
    }

    /// <summary>
    /// Writes the block into a model using specified context.
    /// </summary>
    /*override*/ void write(MarkdownReaderContext context) throws Exception
    {
        // There is no children, so just Open and Close.
        if (mHtmlTagToken.isStart())
            context.open(this);
        else
            context.close(this);
    }

    /// <summary>
    /// Tries to parse a line of text starting from a specified position.
    /// </summary>
    /*override*/ boolean tryParse(String txtLine, int start)
    {
        return false;
    }

    /// <summary>
    /// Tries to append a specified block to this block.
    /// </summary>
    /*override*/ boolean tryAppend(Block block)
    {
        return false;
    }


    /// <summary>
    /// Gets a string representing tag name of this <see cref="HtmlTagBlock"/>.
    /// </summary>
    /// The returning value is without any space characters and opening/closing characters of HTML elements,
    /// like 'i', 'b', 'br' and so on.
    String getTagName()
    {
        String tagName = msString.replace(mHtmlTagToken.getName(), MarkdownUtil.SOFT_LINE_BREAK_CHAR, ' ');
        return StringUtil.removeAllWhitespaces(tagName).toString();
    }

    /// <summary>
    /// Gets a text of this <see cref="HtmlTagBlock"/>.
    /// </summary>
    /*override*/ String getText() { return getRawText(); }

    /// <summary>
    /// Gets string representing a raw text for the HTML inline block.
    /// </summary>
    /// <remarks>
    /// Actually, the right way to get raw text will be implementing a new corresponding
    /// property in <see cref="HtmlTagToken"/>. But for the moment just try to recover it
    /// from the HTML tag parts.
    /// </remarks>
    String getRawText()
    {
        StringBuilder sb = mHtmlTagToken.isStart() ? new StringBuilder("<") : new StringBuilder("</");

        msStringBuilder.append(sb, mHtmlTagToken.getName());

        if (StringUtil.hasChars(getAttributesText()))
        {
            if ("http:".equals(mHtmlTagToken.getName()) || "http".equals(mHtmlTagToken.getName()))
                msStringBuilder.append(sb, "//");
            else if (!msString.endsWith(mHtmlTagToken.getName(), ":", StringComparison.INVARIANT_CULTURE))
                sb.append(' ');

            msStringBuilder.append(sb, getAttributesText());
        }

        if (mHtmlTagToken.isStart() && mHtmlTagToken.isSelfClosing())
            sb.append('/');

        sb.append('>');

        return sb.toString();
    }

    /// <summary>
    /// Returns level of the Heading, if tag is one of the 'h0'-'h9', or -1 otherwise.
    /// </summary>
    int getHeadingTagLevel()
    {
        if ((getTagName().length() == 2) && msString.startsWith(getTagName(), "h", StringComparison.INVARIANT_CULTURE_IGNORE_CASE))
        {
            int level = FormatterPal.tryParseInt(getTagName().substring(1));
            if (level != Integer.MIN_VALUE)
                return level;
        }

        return -1;
    }

    /// <summary>
    /// Returns true, if HTML element is 'p' or one of the 'h0'-'h9'.
    /// </summary>
    boolean isParaOrHeading() { return ("p".equals(getTagName())) || (getHeadingTagLevel() != -1); }

    /// <summary>
    /// Returns true, if HTML element is 'table'.
    /// </summary>
    boolean isTable() { return ("table".equals(getTagName())); }

    /// <summary>
    /// Returns true, if HTML element is 'td'.
    /// </summary>
    boolean isCell() { return ("td".equals(getTagName())); }

    /// <summary>
    /// Gets block type.
    /// </summary>
    /*override*/ /*BlockType*/byte getType() { return BlockType.HTML_TAG; }

    /// <summary>
    /// Gets block level.
    /// </summary>
    /*override*/ /*MarkdownBlockLevel*/int getBlockLevel() { return MarkdownBlockLevel.INLINE; }

    /// <summary>
    /// Gets the value indicating whether this is a start (<c>true</c>) or end (<c>false</c>) tag.
    /// </summary>
    boolean isStart() { return mHtmlTagToken.isStart(); }

    /// <summary>
    /// Gets the value indicating whether this HTML tag is 'self-closing'.
    /// </summary>
    boolean isSelfClosing() { return mHtmlTagToken.isSelfClosing(); }

    /// <summary>
    /// Gets a string representing attributes collection.
    /// </summary>
    private String getAttributesText()
    {
        StringBuilder sb = new StringBuilder();
        for (HtmlAttribute attribute : mHtmlTagToken.getAttributes())
        {
            msStringBuilder.append(sb, attribute.getName());
            if (StringUtil.hasChars(attribute.getName()) && StringUtil.hasChars(attribute.getValue()))
                sb.append('=');

            if (StringUtil.hasChars(attribute.getValue()))
                sb.append('\"');
            msStringBuilder.append(sb, attribute.getValue());
            if (StringUtil.hasChars(attribute.getValue()))
                sb.append('\"');

            sb.append(' ');
        }

        // Remove space separator after very last attribute.
        if (mHtmlTagToken.getAttributes().getCount() > 0)
            sb.setLength(sb.length() - 1)/*Property--*/;

        return sb.toString();
    }

    private /*final*/ HtmlTagToken mHtmlTagToken;
}

