// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/04/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msString;
import com.aspose.UriUtil;
import com.aspose.StringToIntDictionary;
import com.aspose.StringUtil;
import com.aspose.ms.System.StringComparison;
import com.aspose.DrColor;
import java.awt.Color;
import com.aspose.IntToObjDictionary;
import com.aspose.CaseInsensitiveHashSet;


/// <summary>
/// The class that helps to read a Markdown document into the model.
/// </summary>
class MarkdownReaderContext
{
    /// <summary>
    /// Ctor.
    /// </summary>
    MarkdownReaderContext(Document document)
    {
        Debug.assertTrue(document != null);

        mBuilder = new DocumentBuilder(document);

        // Disable bold and italic explicitly to exclude affecting from a parent styles.
        mBuilder.setBold(false);
        mBuilder.setItalic(false);
        mBuilder.getFont().setStrikeThrough(false);

        // Set defaults for cute looking.
        ParaPr defaultParaPr = document.getStyles().getDefaultParaPr();
        defaultParaPr.setSpaceAfter(160);

        RunPr defaultRunPr = document.getStyles().getDefaultRunPr();
        defaultRunPr.setName("Calibri");
        defaultRunPr.setSize(22);
        defaultRunPr.setSizeBi(22);

        // Lets Normal inherit font size from the defaults.
        Style normalStyle = mBuilder.getDocument().getStyles().getByName("Normal", true);
        normalStyle.getRunPr().remove(FontAttr.SIZE);
        normalStyle.getRunPr().remove(FontAttr.SIZE_BI);
    }

    /// <summary>
    /// Opens block in the context.
    /// </summary>
    void open(Block block)
    {
        switch (block.getType())
        {
            // WORDSNET-27290 Implemented loading of HTML inlines.
            case BlockType.HTML_TAG:
            {
                HtmlTagBlock htmlTagBlock = (HtmlTagBlock)block;

                // WORDSNET-28605 Implemented loading HTML tables (and actually other HTML tags).
                if (isInHtml() || !openHtmlInline(htmlTagBlock))
                {
                    String tagName = htmlTagBlock.getTagName();
                    if (G_KNOWN_HTML_TAG_NAMES.contains(tagName))
                    {
                        if (!htmlTagBlock.isSelfClosing())
                            incHtmlTagsStartCount(tagName);
                        msStringBuilder.append(mHtmlBuilder, htmlTagBlock.getRawText());
                    }
                    else
                    {
                        String text = msString.replace(htmlTagBlock.getRawText(), MarkdownUtil.SOFT_LINE_BREAK_CHAR, ' ');
                        mBuilder.write(text);
                    }
                }

                break;
            }
            case BlockType.QUOTE:
            {
                // We MUST separate sibling Quotes with an empty paragraph. Otherwise, we will not be able to understand
                // they are different Quotes in our model.
                if ((block.getParent().getType() == BlockType.DOCUMENT) &&
                    (mLastClosedBlock != null) && (mLastClosedBlock.getType() == BlockType.QUOTE))
                {
                    mBuilder.writeln();
                    mBuilder.getParagraphFormat().setStyle(fetchStyle("Normal", StyleType.PARAGRAPH));
                }
                break;
            }
            case BlockType.PARAGRAPH:
            case BlockType.INDENTED_CODE:
            case BlockType.HORIZONTAL_RULE:
            case BlockType.FENCED_CODE:
            case BlockType.SETEXT_HEADING:
            case BlockType.ATX_HEADING:
            {
                if (mFootnote != null)
                    startFootnoteParagraph();
                break;
            }
            case BlockType.TABLE:
            {
                // Separate sibling Tables with an empty paragraph.
                if (isTable(mLastClosedBlock))
                    mBuilder.writeln();

                // WORDSNET-26451 Reset style to 'Normal' for each newly started table.
                mBuilder.getParagraphFormat().setStyle(fetchStyle("Normal", StyleType.PARAGRAPH));
                break;
            }
            case BlockType.CELL:
            {
                Cell cell = mBuilder.insertCell();
                ParaPr paraPr = cell.getFirstParagraph().getParaPr();
                paraPr.setAlignment(((CellBlock)block).getAlignment());
                // There cannot be lists in a markdown table.
                removeList(paraPr);
                break;
            }
            case BlockType.INLINE_CODE:
            {
                String styleName = getInlineCodeStyleName((InlineCodeBlock)block);
                Style style = fetchStyle(styleName, StyleType.CHARACTER);
                mBuilder.getFont().setStyle(style);
                decorateInlineCode(style);
                break;
            }
            case BlockType.LINK_TEXT:
            {
                LinkDestinationBlock linkDestinationBlock = ((LinkTextBlock)block).getLinkDestinationBlock();
                if (linkDestinationBlock != null)
                {
                    mBuilder.getFont().setStyle(mBuilder.getDocument().getStyles().getBySti(StyleIdentifier.HYPERLINK, true));
                    FieldCodeHyperlink fieldCode = new FieldCodeHyperlink(
                        UriUtil.getAddress(linkDestinationBlock.getUri()),
                        UriUtil.getSubAddress(linkDestinationBlock.getUri()),
                        linkDestinationBlock.getTitle(),
                        "",
                        ((LinkTextBlock)block).getDefinitionLabel(),
                        false);
                    mBuilder.startHyperlink(fieldCode);
                }
                break;
            }
            case BlockType.AUTOLINK:
            {
                mBuilder.getFont().setStyle(mBuilder.getDocument().getStyles().getBySti(StyleIdentifier.HYPERLINK, true));
                break;
            }
            case BlockType.BOLD_INLINE:
            {
                mBuilder.setBold(true);
                mBoldCount++;
                break;
            }
            case BlockType.ITALIC_INLINE:
            {
                mBuilder.setItalic(true);
                mItalicCount++;
                break;
            }
            case BlockType.STRIKETHROUGH:
            {
                mBuilder.getFont().setStrikeThrough(true);
                mStrikethroughCount++;
                break;
            }
            case BlockType.UNDERLINE:
            {
                mBuilder.getFont().setUnderline(Underline.SINGLE);
                mUnderlineCount++;
                break;
            }
            case BlockType.FOOTNOTE_REFERENCE:
            {
                mFootnote = mBuilder.insertFootnote(FootnoteType.FOOTNOTE, "", null);
                // Now we need to write definition paragraphs of the footnote.
                // Thus save formatting of the current parent paragraph of the footnote
                // to restore it after all definitions are written and we will continue
                // to write the interrupted parent paragraph of the footnote.
                saveCurrentFormatting();
                // Clear current formatting in order to avoid its influence on definition paragraphs.
                clearFormatting();
                break;
            }
            default:
                break;
        }

        mLastOpenedBlock = block;
    }

    /// <summary>
    /// Closes block in the context.
    /// </summary>
    void close(Block block) throws Exception
    {
        switch (block.getType())
        {
            case BlockType.DOCUMENT:
            {
                // Flush possible pending raw HTML.
                if (mHtmlBuilder.length() > 0)
                {
                    mBuilder.insertHtml(mHtmlBuilder.toString());
                    mHtmlBuilder.setLength(0);
                }

                // When we create new Document we ensure there is at least one paragraph.
                // So, if this is still empty and there is another paragraph before,
                // we should remove this redundant paragraph created along with the Document.
                Paragraph lastParagraph = ms.as(mBuilder.getDocument().getLastSection().getBody().getLastChild(), Paragraph.class);
                if ((lastParagraph != null) && !lastParagraph.hasChildNodes())
                {
                    Node prevNode = lastParagraph.getPreviousSibling();
                    if (prevNode != null && prevNode.getNodeType() == NodeType.PARAGRAPH)
                        lastParagraph.remove();
                }
                break;
            }
            case BlockType.QUOTE:
            {
                // Set Normal style back when Quote is closed.
                mBuilder.getParagraphFormat().setStyle(fetchStyle("Normal", StyleType.PARAGRAPH));
                break;
            }
            case BlockType.PARAGRAPH:
            case BlockType.HORIZONTAL_RULE:
            case BlockType.ATX_HEADING:
            case BlockType.SETEXT_HEADING:
            case BlockType.INDENTED_CODE:
            case BlockType.FENCED_CODE:
            {
                if (mLastOpenedBlock != null)
                {
                    // Check previous opened is not a HTML <p> or <h0>-<h9>.
                    HtmlTagBlock htmlBlock = ms.as(mLastOpenedBlock, HtmlTagBlock.class);
                    if (htmlBlock == null || !htmlBlock.isParaOrHeading())
                        applyFormatting(mBuilder.getCurrentParagraph().getParaPr(), block);
                }

                startNewPara();
                break;
            }
            case BlockType.TABLE:
            {
                mBuilder.endTable();
                // There cannot be lists in a markdown table, so lets remove it
                // to avoid influences of possible previous list.
                removeList(mBuilder.getCurrentParagraph().getParaPr());
                break;
            }
            case BlockType.ROW:
            {
                RowBlock row = (RowBlock)block;
                TableBlock parentTable = (TableBlock)row.getParent(BlockType.TABLE);
                Debug.assertTrue(parentTable != null);
                // The spec says that if there are a number of cells fewer than the number of cells
                // in the header row, empty cells are inserted.
                for (int i = row.getCount(); i < parentTable.getColumnsCount(); i++)
                {
                    Cell cell = mBuilder.insertCell();
                    cell.getFirstParagraph().getParaPr().setAlignment(((CellBlock)parentTable.getDelimiterRow().get(i)).getAlignment());
                }

                mBuilder.endRow();
                break;
            }
            case BlockType.LINK_TEXT:
            {
                if (((LinkTextBlock)block).getLinkDestinationBlock() != null)
                {
                    mBuilder.endHyperlink();
                    mBuilder.getFont().setStyle(mBuilder.getDocument().getStyles().getBySti(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, true));
                }
                break;
            }
            case BlockType.AUTOLINK:
            case BlockType.INLINE_CODE:
            {
                mBuilder.getFont().setStyle(mBuilder.getDocument().getStyles().getBySti(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, true));
                break;
            }
            case BlockType.BOLD_INLINE:
            {
                mBoldCount--;
                mBuilder.setBold((mBoldCount > 0));
                break;
            }
            case BlockType.ITALIC_INLINE:
            {
                mItalicCount--;
                mBuilder.setItalic((mItalicCount > 0));
                break;
            }
            case BlockType.STRIKETHROUGH:
            {
                mStrikethroughCount--;
                mBuilder.getFont().setStrikeThrough((mStrikethroughCount > 0));
                break;
            }
            case BlockType.UNDERLINE:
            {
                mUnderlineCount--;
                if (mUnderlineCount == 0)
                    mBuilder.getFont().setUnderline(Underline.NONE);
                break;
            }

            case BlockType.FOOTNOTE_REFERENCE:
            {
                Debug.assertTrue(mFootnote != null);

                mBuilder.moveTo(mFootnote.getParentParagraph());
                mFootnote = null;
                // We wrote footnote definition paragraphs and ready to continue to write the rest
                // of interrupted parent paragraph of the footnote. So, restore its formatting.
                restoreFormatting();
                break;
            }

            // WORDSNET-27290 Implemented loading of HTML inlines.
            case BlockType.HTML_TAG:
            {
                HtmlTagBlock htmlTagBlock = (HtmlTagBlock)block;

                // WORDSNET-28605 Implemented loading HTML tables.
                if (isInHtml())
                    msStringBuilder.append(mHtmlBuilder, htmlTagBlock.getRawText());
                else if (!closeHtmlInline(htmlTagBlock))
                    mBuilder.write(htmlTagBlock.getRawText());

                decHtmlTagsStartCount(htmlTagBlock.getTagName());

                if (getHtmlTagsStartedCount() == 0 && (mHtmlBuilder.length() > 0))
                {
                    mBuilder.insertHtml(mHtmlBuilder.toString(), HtmlInsertOptions.REMOVE_LAST_EMPTY_PARAGRAPH);
                    mHtmlBuilder.setLength(0);
                }

                break;
            }

            default:
                break;
        }

        mLastClosedBlock = block;
    }

    /// <summary>
    /// Writes a specified text either directly to <see cref="mBuilder"/> or
    /// to <see cref="mHtmlBuilder"/> for writing it later when raw HTML block will end.
    /// </summary>
    void writeText(String text)
    {
        HtmlTagBlock htmlTagBlock = ms.as(mLastOpenedBlock, HtmlTagBlock.class);
        if (isInHtml() && htmlTagBlock != null)
        {
            switch (gStringSwitchMap.of(htmlTagBlock.getTagName()))
            {
                // Don't write whitespace characters located between some HTML tags.
                // This can be also SoftLineBreaks.
                case /*"tr"*/0:
                case /*"table"*/1:
                {
                    if (!msString.isNullOrEmpty(text) && msString.trim(text).length() > 0)
                        mBuilder.write(text);
                    break;
                }

                case /*"td"*/2:
                {
                    // If 'td' block is still opened, then we can write text into it.
                    // Otherwise, write text directly to the document Builder.
                    if (isInHtmlCell())
                        msStringBuilder.append(mHtmlBuilder, text);
                    else if (!msString.isNullOrEmpty(text) && msString.trim(text).length() > 0)
                        mBuilder.write(text);

                    break;
                }

                default:
                {
                    // As we inside raw HTML block, then append text to HTML StringBuilder.
                    msStringBuilder.append(mHtmlBuilder, text);
                    break;
                }
            }
        }
        else
        {
            mBuilder.write(text);
        }
    }

    /// <summary>
    /// Opens <see cref="HtmlTagBlock"/>.
    /// </summary>
    /// <remarks>
    /// Sometimes it is better to process HTML tags directly, without using <see cref="DocumentBuilder.InsertHtml(string)"/>.
    /// For, example, it allows us to set proper 'InlineCode' style name if encounter 'code' HTML tag.
    /// And it works faster, then InsertHtml().
    /// </remarks>
    private boolean openHtmlInline(HtmlTagBlock block)
    {
        switch (gStringSwitchMap.of(block.getTagName()))
        {
            case /*"p"*/3:
            case /*"h1"*/4:
            case /*"h2"*/5:
            case /*"h3"*/6:
            case /*"h4"*/7:
            case /*"h5"*/8:
            case /*"h6"*/9:
            {
                // h1-h6 HTML elements are inline and must be at least inside InlineContainer block.
                Debug.assertTrue(mLastOpenedBlock != null);
                // If this is not a very first child, then we need to cut current Paragraph and start a new one.
                if (!block.isFirstChild())
                {
                    HtmlTagBlock lastClosedBlock = ms.as(mLastClosedBlock, HtmlTagBlock.class);
                    if ((lastClosedBlock == null) || !lastClosedBlock.isParaOrHeading())
                    {
                        applyFormatting(mBuilder.getCurrentParagraph().getParaPr(), mLastOpenedBlock);

                        // There is ending paragraph already in just inserted Cell node,
                        // so no need to start a new paragraph in this case.
                        if ((mLastOpenedBlock.getType() != BlockType.HTML_TAG) || !((HtmlTagBlock)mLastOpenedBlock).isCell())
                            startNewPara();
                    }
                }

                applyFormatting(mBuilder.getCurrentParagraph().getParaPr(), block);
                break;
            }

            case /*"i"*/10:
            {
                mBuilder.setItalic(true);
                mItalicCount++;
                break;
            }

            case /*"sup"*/11:
            {
                mBuilder.getFont().setSuperscript(true);
                mSuperscriptCount++;
                break;
            }

            case /*"sub"*/12:
            {
                mBuilder.getFont().setSubscript(true);
                mSubscriptCount++;
                break;
            }

            case /*"strong"*/13:
            case /*"b"*/14:
            {
                mBuilder.setBold(true);
                mBoldCount++;
                break;
            }

            case /*"u"*/15:
            {
                mBuilder.getFont().setUnderline(Underline.SINGLE);
                mUnderlineCount++;
                break;
            }

            case /*"strike"*/16:
            case /*"del"*/17:
            case /*"s"*/18:
            {
                mBuilder.getFont().setStrikeThrough(true);
                mStrikethroughCount++;
                break;
            }

            case /*"br"*/19:
            {
                applyFormatting(mBuilder.getCurrentParagraph().getParaPr(), block);
                startNewPara();
                break;
            }

            case /*"code"*/20:
            {
                Style style = fetchStyle(MarkdownUtil.INLINE_CODE_STYLE_NAME, StyleType.CHARACTER);
                mBuilder.getFont().setStyle(style);
                decorateInlineCode(style);
                break;
            }

            default:
            {
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Closes <see cref="HtmlTagBlock"/>.
    /// </summary>
    /// See some details in <see cref="OpenHtmlInline"/>.
    private boolean closeHtmlInline(HtmlTagBlock block)
    {
        switch (gStringSwitchMap.of(block.getTagName()))
        {
            case /*"p"*/3:
            case /*"h1"*/4:
            case /*"h2"*/5:
            case /*"h3"*/6:
            case /*"h4"*/7:
            case /*"h5"*/8:
            case /*"h6"*/9:
            {
                if (!block.isLastChild())
                    startNewPara();
                break;
            }

            case /*"i"*/10:
            {
                mItalicCount--;
                mBuilder.setItalic((mItalicCount > 0));
                break;
            }

            case /*"b"*/14:
            case /*"strong"*/13:
            {
                mBoldCount--;
                mBuilder.setBold((mBoldCount > 0));
                break;
            }

            case /*"sup"*/11:
            {
                mSuperscriptCount--;
                mBuilder.getFont().setSuperscript((mSuperscriptCount > 0));
                break;
            }

            case /*"sub"*/12:
            {
                mSubscriptCount--;
                mBuilder.getFont().setSubscript((mSubscriptCount > 0));
                break;
            }

            case /*"u"*/15:
            {
                mUnderlineCount--;
                if (mUnderlineCount == 0)
                    mBuilder.getFont().setUnderline(Underline.NONE);
                break;
            }

            case /*"strike"*/16:
            case /*"del"*/17:
            case /*"s"*/18:
            {
                mStrikethroughCount--;
                mBuilder.getFont().setStrikeThrough((mStrikethroughCount > 0));
                break;
            }

            case /*"br"*/19:
            {
                applyFormatting(mBuilder.getCurrentParagraph().getParaPr(), block);
                startNewPara();
                break;
            }

            case /*"code"*/20:
            {
                mBuilder.getFont().setStyle(mBuilder.getDocument().getStyles().getBySti(StyleIdentifier.DEFAULT_PARAGRAPH_FONT, true));
                break;
            }

            default:
            {
                return false;
            }
        }

        return true;
    }

    /// <summary>
    /// Applies formatting to a specified <see cref="ParaPr"/> by a specified <see cref="Block"/>.
    /// </summary>
    private void applyFormatting(ParaPr paraPr, Block block)
    {
        Style style = getStyle(block);
        Debug.assertTrue(style != null);

        paraPr.set(ParaAttr.ISTD, style.getIstd());

        // IN. There is slightly complicated behavior for HorizontalRules in specification editor at
        // https://spec.commonmark.org/dingus/ (see TestBulletListK and TestBulletListF). It applies an
        // empty bullet to a first horizontal rule in a list and moves down real non-empty bullet to a
        // first non-horizontal rule paragraph inside the same list. However, it is not obvious behavior
        // that is not somehow explained in the specification. As it very complicates code and seems is not
        // very frequent case, lets postpone this for a while.
        applyList(paraPr, block);
    }

    /// <summary>
    /// Starts a new paragraph.
    /// </summary>
    private void startNewPara()
    {
        // To close paragraph insert paragraph break.
        if (mFootnote == null)
            mBuilder.writeln();
    }

    /// <summary>
    /// Gets a style for a specified block.
    /// </summary>
    private Style getStyle(Block block)
    {
        Style style = null;

        Block prevBlock = null;
        Block curBlock = block;

        // This a hashcode for the current chain of blocks.
        int curHashCode = 0;

        boolean canSkipBlock = false;

        while ((curBlock != null) && (curBlock.getType() != BlockType.DOCUMENT))
        {
            Style baseStyle = style;

            curHashCode = hashCode(curBlock, curHashCode);

            style = mBlocksChainHashcodeToStyle.get(curHashCode);
            if (style == null)
            {
                switch (curBlock.getType())
                {
                    case BlockType.PARAGRAPH:
                    {
                        if (isParaOrHeading(prevBlock))
                            canSkipBlock = true;
                        break;
                    }

                    case BlockType.HTML_TAG:
                    {
                        if (isParaOrHeading(prevBlock) && ((HtmlTagBlock)block).isParaOrHeading())
                            canSkipBlock = true;
                        break;
                    }

                    case BlockType.SETEXT_HEADING:
                    {
                        // For setext heading we want a corresponding Heading style to be the base style.
                        AtxHeadingBlock atxHeadingBlock = new AtxHeadingBlock();
                        atxHeadingBlock.setLevel(((HeadingBlock)curBlock).getLevel());
                        baseStyle = getStyle(atxHeadingBlock);
                        break;
                    }

                    case BlockType.ORDERED_LIST_ITEM:
                    case BlockType.BULLET_LIST_ITEM:
                    {
                        if (prevBlock != null)
                        {
                            // We can omit list style for this block if this is a terminal list (contains only a Leaf block)
                            // or its child non-leaf block is not first in this list.
                            if ((prevBlock.getBlockLevel() == MarkdownBlockLevel.LEAF) || !prevBlock.isFirstChild())
                                canSkipBlock = true;
                        }
                        break;
                    }
                    default:
                        break;
                }

                if (canSkipBlock)
                {
                    style = baseStyle;
                    canSkipBlock = false;
                }
                else
                {
                    style = getExistingStyle(curBlock, baseStyle);

                    // If style is not found in an existing collection, then create a new one.
                    if (style == null)
                        style = createStyle(curBlock, baseStyle, curHashCode);
                }
            }

            prevBlock = curBlock;
            curBlock = curBlock.getParent();
        }

        return style;
    }

    /// <summary>
    /// Returns true, if a specified block represents paragraph or heading.
    /// </summary>
    private static boolean isParaOrHeading(Block block)
    {
        if (block == null)
            return false;

        if (block.getType() == BlockType.PARAGRAPH)
            return true;

        if ((block.getType() == BlockType.HTML_TAG) && ((HtmlTagBlock)block).isParaOrHeading())
            return true;

        return false;
    }

    /// <summary>
    /// Returns true, if a specified block represents a table.
    /// </summary>
    private static boolean isTable(Block block)
    {
        if (block == null)
            return false;

        if (block.getType() == BlockType.TABLE)
            return true;

        if ((block.getType() == BlockType.HTML_TAG) && ((HtmlTagBlock)block).isTable())
            return true;

        return false;
    }

    /// <summary>
    /// Creates a style for the specified block with the specified base style and hash code.
    /// </summary>
    private Style createStyle(Block block, Style baseStyle, int hashCode)
    {
        String styleName = getStyleName(block);
        Style style = fetchStyle(styleName, StyleType.PARAGRAPH);
        if ((style.getStyleIdentifier() != StyleIdentifier.NORMAL) && baseStyle != null)
            style.setBasedOnIstd(baseStyle.getIstd());

        decorate(style, block);
        mBlocksChainHashcodeToStyle.add(hashCode, style);

        return style;
    }

    /// <summary>
    /// Gets hash code for a specified block.
    /// </summary>
    /// <remarks>
    /// This method allows to produce hash codes for the chains of blocks and
    /// allows to compare them effectively for building corresponding hierarchies of styles.
    /// </remarks>
    private static int hashCode(Block block, int initialHashCodeValue)
    {
        /*unchecked*/
        {
            int hashCode;
            switch (block.getType())
            {
                case BlockType.ATX_HEADING:
                case BlockType.SETEXT_HEADING:
                {
                    hashCode = (int)block.getType();
                    hashCode = (hashCode * 397) ^ ((HeadingBlock)block).getLevel();
                    break;
                }

                case BlockType.HTML_TAG:
                {
                    hashCode = (int)block.getType();
                    hashCode = (hashCode * 397) ^ ((HtmlTagBlock)block).getHeadingTagLevel();
                    break;
                }

                case BlockType.BULLET_LIST_ITEM:
                case BlockType.ORDERED_LIST_ITEM:
                {
                    ListItemBlock listItemBlock = (ListItemBlock)block;
                    hashCode = (int)block.getType();
                    hashCode = (hashCode * 397) ^ listItemBlock.getStartAt();
                    hashCode = (hashCode * 397) ^ (int)listItemBlock.getMarker();
                    hashCode = (hashCode * 397) ^ listItemBlock.getLevel();
                    break;
                }
                case BlockType.FENCED_CODE:
                {
                    hashCode = (int)block.getType();
                    hashCode = (hashCode * 397) ^ ((FencedCodeBlock)block).getInfo().hashCode();
                    break;
                }
                case BlockType.HORIZONTAL_RULE:
                    hashCode = (int)BlockType.PARAGRAPH;
                    break;
                default:
                    hashCode = (int)block.getType();
                    break;
            }

            return (initialHashCodeValue * 397) ^ hashCode;
        }
    }

    /// <summary>
    /// Fetches style with a specified name.
    /// </summary>
    private Style fetchStyle(String name, /*StyleType*/int styleType)
    {
        // Try get an existing style.
        Style style = mBuilder.getDocument().getStyles().getByName(name, true);

        // Create a new style.
        if (style == null)
            style = mBuilder.getDocument().getStyles().add(styleType, name);

        return style;
    }

    /// <summary>
    /// Returns style name for a specified block.
    /// </summary>
    private String getStyleName(Block block)
    {
        String styleName;

        switch (block.getType())
        {
            case BlockType.QUOTE:
            case BlockType.BULLET_LIST_ITEM:
            case BlockType.ORDERED_LIST_ITEM:
            case BlockType.FOOTNOTE_DEFINITION:
            {
                styleName = getUniqueStyleName(block);
                break;
            }
            case BlockType.ATX_HEADING:
            {
                styleName = msString.format("{0} {1}", MarkdownUtil.HEADING_STYLE_NAME, ((HeadingBlock)block).getLevel());
                break;
            }
            case BlockType.HTML_TAG:
            {
                int level = ((HtmlTagBlock)block).getHeadingTagLevel();
                styleName = level == -1 ? "Normal" : msString.format("{0} {1}", MarkdownUtil.HEADING_STYLE_NAME, level);
                break;
            }
            case BlockType.SETEXT_HEADING:
            {
                styleName = msString.format("{0}{1}", MarkdownUtil.SETEXT_HEADING_STYLE_NAME, ((HeadingBlock)block).getLevel());
                break;
            }
            case BlockType.INDENTED_CODE:
            {
                styleName = MarkdownUtil.INDENTED_CODE_STYLE_NAME;
                break;
            }
            case BlockType.FENCED_CODE:
            {
                String infoString = ((FencedCodeBlock)block).getInfo();
                styleName = msString.format("{0}{1}{2}",
                    MarkdownUtil.FENCED_CODE_STYLE_NAME, (infoString.length() > 0) ? "." : "", infoString);
                break;
            }
            default:
                styleName = "Normal";
                break;
        }

        return styleName;
    }

    /// <summary>
    /// Gets unique style name for a specified Block-level block.
    /// </summary>
    private String getUniqueStyleName(Block block)
    {
        Debug.assertTrue(block.getBlockLevel() == MarkdownBlockLevel.BLOCK);

        String baseStyleName;
        switch (block.getType())
        {
            case BlockType.QUOTE:
                baseStyleName = MarkdownUtil.QUOTE_STYLE_NAME;
                break;
            case BlockType.BULLET_LIST_ITEM:
            case BlockType.ORDERED_LIST_ITEM:
                baseStyleName = MarkdownUtil.LIST_STYLE_NAME;
                break;
            case BlockType.FOOTNOTE_DEFINITION:
                baseStyleName = MarkdownUtil.FOOTNOTE_STYLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + msString.format("Unexpected block type: {0}", block.getType()));
        }

        String uniqueStyleName;
        int usedCount = mUsedBlockStyles.get(baseStyleName);
        if (StringToIntDictionary.isNullSubstitute(usedCount))
        {
            uniqueStyleName = baseStyleName;
            usedCount = 1;
        }
        else
        {
            uniqueStyleName = msString.format("{0}{1}", baseStyleName, usedCount);
            usedCount++;
        }

        mUsedBlockStyles.set(baseStyleName, usedCount);
        return uniqueStyleName;
    }

    /// <summary>
    /// Gets style with a specified base style from an existing collection.
    /// </summary>
    private Style getExistingStyle(Block block, Style baseStyle)
    {
        int listLevel = -1;
        String styleName;

        switch (block.getType())
        {
            case BlockType.QUOTE:
            {
                styleName = MarkdownUtil.QUOTE_STYLE_NAME;
                break;
            }
            case BlockType.BULLET_LIST_ITEM:
            case BlockType.ORDERED_LIST_ITEM:
            {
                styleName = MarkdownUtil.LIST_STYLE_NAME;
                listLevel = ((ListItemBlock)block).getLevel();
                break;
            }
            default:
                return null;
        }

        return getExistingStyle(block, baseStyle, styleName, listLevel);
    }

    /// <summary>
    /// Gets existing style with a specified name, base style and list level for a specified block.
    /// </summary>
    private Style getExistingStyle(Block block, Style baseStyle, String styleName, int listLevel)
    {
        Debug.assertTrue(StringUtil.hasChars(styleName));

        for (Style existingStyle : mBlocksChainHashcodeToStyle.getValues())
        {
            if ((msString.startsWith(existingStyle.getName(), styleName, StringComparison.ORDINAL) &&
                (existingStyle.getBaseStyle() == baseStyle)))
            {
                List list = existingStyle.getListInternal();

                // Block and style has no list, so it is suitable.
                if ((listLevel == -1) && (list == null))
                    return existingStyle;

                // Both: block and style has list. Check they are suitable.
                if ((listLevel != -1) && (list != null))
                {
                    int styleListLevel = (/*int*/Integer)existingStyle.getParaPr().fetchAttr(ParaAttr.LIST_LEVEL);
                    if (((styleListLevel == listLevel) || (block.getType() == BlockType.BULLET_LIST_ITEM)) &&
                        isSuitableList(list, (ListItemBlock)block))
                        return existingStyle;
                }
            }
        }

        return null;
    }

    /// <summary>
    /// Starts a new paragraph inside Footnote.
    /// </summary>
    private void startFootnoteParagraph()
    {
        Paragraph footnotePara;
        // The very first paragraph of Footnote definition was auto-created along with the Footnote
        // and originally contains only the footnote's marker with rest of text empty.
        // So, we just re-use it.
        // Note, even if this is a completely empty paragraph, it will have at least 2 children:
        // footnote Reference and space char after it as separator.
        if ((mFootnote.getParagraphs().getCount() == 1) && (mFootnote.getParagraphs().get(0).getChildNodes(NodeType.ANY, false).getCount() == 1))
        {
            footnotePara = mFootnote.getParagraphs().get(0);
        }
        else
        {
            footnotePara = new Paragraph(mBuilder.getDocument());
            mFootnote.getParagraphs().add(footnotePara);
        }

        mBuilder.moveTo(footnotePara);

        // Write one space character as a separator between Footnote marker and text for a very first line.
        if (mFootnote.getParagraphs().getCount() == 1)
            mBuilder.write(" ");
    }

    /// <summary>
    /// Gets InlineCode style name.
    /// </summary>
    private static String getInlineCodeStyleName(InlineCodeBlock block)
    {
        String delimiterLength = (block.getDelimiterLength() > 1) ? msString.format(".{0}", block.getDelimiterLength()) : "";
        return msString.format("{0}{1}", MarkdownUtil.INLINE_CODE_STYLE_NAME, delimiterLength);
    }

    /// <summary>
    /// Decorates a specified style.
    /// </summary>
    private void decorate(Style style, Block block)
    {
        switch (block.getType())
        {
            case BlockType.BULLET_LIST_ITEM:
                decorateBulletList(style, (BulletListItemBlock)block);
                break;
            case BlockType.ORDERED_LIST_ITEM:
                decorateOrderedList(style, (OrderedListItemBlock)block);
                break;
            case BlockType.QUOTE:
                decorateQuote(style);
                break;
            case BlockType.INDENTED_CODE:
            case BlockType.FENCED_CODE:
                decorateCode(style);
                break;
            case BlockType.INLINE_CODE:
                decorateInlineCode(style);
                break;
            default:
                break;
        }
    }

    /// <summary>
    /// Decorates BulletList.
    /// </summary>
    private void decorateBulletList(Style style, BulletListItemBlock block)
    {
        List list = fetchBulletList(block.getMarker());
        style.getParaPr().setListId(list.getListId());
        style.getParaPr().setListLevel(block.getLevel());
    }

    /// <summary>
    /// Decorates OrderedList.
    /// </summary>
    private void decorateOrderedList(Style style, OrderedListItemBlock block)
    {
        applyList(style.getParaPr(), block);
    }

    /// <summary>
    /// Decorates Quote.
    /// </summary>
    private static void decorateQuote(Style style)
    {
        style.getParaPr().setBorderLeft(new Border(LineStyle.SINGLE, 18, DrColor.fromArgb(0x9F, 0x9F, 0x9F)));
    }

    /// <summary>
    /// Decorates InlineCode.
    /// </summary>
    private static void decorateInlineCode(Style style)
    {
        style.getRunPr().setName("Consolas");
        style.getRunPr().setColor(DrColor.fromArgb(0xC7, 0x25, 0x4E));
        style.getRunPr().setHighlightColor(DrColor.fromArgb(0xF9, 0xF2, 0xF4));
        style.getRunPr().setSize(20);
        style.getRunPr().setSizeBi(20);
    }

    /// <summary>
    /// Decorates Code (Fenced or Indented).
    /// </summary>
    private static void decorateCode(Style style)
    {
        Shading shading = new Shading();
        shading.setForegroundPatternColor(new Color((0xE2), (0xE2), (0xE2)));
        shading.setTexture(TextureIndex.TEXTURE_SOLID);
        style.getParaPr().setShading(shading);

        style.getRunPr().setName("Consolas");
        style.getRunPr().setSize(20);
        style.getRunPr().setSizeBi(20);
    }

    /// <summary>
    /// Gets a boolean value indicating a specified list can be used for a specified list item block.
    /// </summary>
    private boolean isSuitableList(List list, ListItemBlock listItemBlock)
    {
        if (list == null)
            return false;

        // There cannot be different containers for the same list.
        if ((listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM) &&
            (listItemBlock.getListContainer() != mListContainers.get(list.getListId())))
            return false;

        // For a bullet list item, the marker (and other info) should be obtained from the very first level.
        int level = (listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM) ? listItemBlock.getLevel() : 0;
        ListLevel listLevel = list.getListLevels().get(level);

        // Check the level was not initialized yet.
        if (listLevel.getNumberFormat().length() == 0)
            return true;

        /*ListMarker*/int marker = MarkdownUtil.toListMarker(listLevel.getNumberFormat().charAt(listLevel.getNumberFormat().length() -1));
        if (listItemBlock.getMarker() != marker)
            return false;

        // For the very first block in level it is also important to start at the value specified for this level.
        if ((listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM) &&
            (listItemBlock.isLevelStart() && (listLevel.getStartAt() != listItemBlock.getStartAt())))
            return false;

        return true;
    }

    /// <summary>
    /// Applies list to a specified paraPr in accordance with a specified block.
    /// </summary>
    private void applyList(ParaPr paraPr, Block block)
    {
        ListItemBlock listItemBlock = ms.as(block, ListItemBlock.class);
        if (listItemBlock == null)
            listItemBlock = (ListItemBlock)block.getParent(BlockType.BULLET_LIST_ITEM, BlockType.ORDERED_LIST_ITEM);

        if (listItemBlock != null)
            setList(paraPr, block, listItemBlock);
        else
            removeList(paraPr);
    }

    /// <summary>
    /// Sets list to a specified paraPr by a specified block in accordance with a specified list item block.
    /// </summary>
    private void setList(ParaPr paraPr, Block block, ListItemBlock listItemBlock)
    {
        Block b = block;
        if ((block.getType() == BlockType.HTML_TAG) && ((HtmlTagBlock)block).isParaOrHeading())
        {
            b = block.getParent(BlockType.PARAGRAPH);
            Debug.assertTrue(b != null);
        }

        // Try to mimic non-visible list marker of the spec editor in Word by writing
        // the blank number format for leaf blocks. It seems no matter which number style to
        // use in this case, as it is blank, so always apply a bullet list.
        /*ListMarker*/int marker = (((b.getParent() != listItemBlock) || !b.isFirstChild()) && (b != listItemBlock))
            ? ListMarker.NONE
            : listItemBlock.getMarker();

        List list = ((marker == ListMarker.DOT) || (marker == ListMarker.PARENTHESIS))
            ? fetchOrderedList(listItemBlock)
            : fetchBulletList(marker);

        int listLevel = listItemBlock.getLevel();
        if (marker != ListMarker.NONE)
            mLevelLists[listLevel] = list;

        paraPr.set(ParaAttr.LIST_ID, list.getListId());
        paraPr.set(ParaAttr.LIST_LEVEL, listLevel);
        }

    /// <summary>
    /// Removes list from a specified ParaPr and map of ListContainers.
    /// </summary>
    private void removeList(ParaPr paraPr)
        {
            int listId = (/*int*/Integer)paraPr.fetchAttr(ParaAttr.LIST_ID);
            if (listId != 0)
                mListContainers.remove(listId);

            paraPr.remove(ParaAttr.LIST_ID);
            paraPr.remove(ParaAttr.LIST_LEVEL);
        }

    /// <summary>
    /// Gets ordered list by a specified list item block.
    /// </summary>
    private List getOrderedList(ListItemBlock listItemBlock)
    {
        Debug.assertTrue(listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM);

        int listLevel = listItemBlock.getLevel();

        // Check list for the listItemBlock level was already created.
        List list = mLevelLists[listLevel];

        // If it was not created for that level, then check previous level.
        if ((list == null) && (listLevel > 0))
            list = mLevelLists[listLevel - 1];

        // Check the list is suitable for the specified list item block.
        if (!isSuitableList(list, listItemBlock))
            return null;

        return list;
    }

    /// <summary>
    /// Fetches ordered list by a specified list item block.
    /// </summary>
    private List fetchOrderedList(ListItemBlock listItemBlock)
    {
        Debug.assertTrue(listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM);

        List list = getOrderedList(listItemBlock);
        if (list == null)
        {
            list = mBuilder.getDocument().getLists().addEmpty(ListType.HYBRID_MULTI_LEVEL);
            mListContainers.add(list.getListId(), listItemBlock.getListContainer());
        }

        int level = listItemBlock.getLevel();
        ListLevel listLevel = list.getListLevels().get(level);
        if (listLevel.getNumberFormat().length() == 0)
            initOrderedListLevel(list, level, listItemBlock);

        return list;
    }

    /// <summary>
    /// Gets bullet list by a specified marker.
    /// </summary>
    private List getBulletList(/*ListMarker*/int marker)
    {
        List list = getBulletMarkerToList().get((int)marker);
        return (IntToObjDictionary.isNullSubstitute(list)) ? null : list;
    }

    /// <summary>
    /// Fetches bullet list by a specified marker.
    /// </summary>
    private List fetchBulletList(/*ListMarker*/int marker)
    {
        List list = getBulletList(marker);
        if (list == null)
        {
            list = createBulletList(marker);
            getBulletMarkerToList().add((int)marker, list);
        }

        return list;
    }

    /// <summary>
    /// Creates a bullet list corresponded to a specified marker.
    /// </summary>
    private List createBulletList(/*ListMarker*/int marker)
    {
        List list = ListFactory.createList(mBuilder.getDocument().getLists(), ListTemplate.BULLET_DEFAULT);
        list.getListLevels().get(0).getRunPr().setSymbolFonts(ListFactory.COURIER_FONT);

        String bullet = Character.toString(MarkdownUtil.listMarkerToChar(marker));
        list.getListLevels().get(0).setNumberFormat(bullet);

        if (marker == ListMarker.NONE)
        {
            for (int i = 1; i < 9; i++)
                list.getListLevels().get(i).setNumberFormat(bullet);
        }

        return list;
    }

    /// <summary>
    /// Initializes an ordered list level in accordance with a specified list item block.
    /// </summary>
    private static void initOrderedListLevel(List list, int level, ListItemBlock listItemBlock)
    {
        Debug.assertTrue(listItemBlock.getType() == BlockType.ORDERED_LIST_ITEM);

        int levelIndent = 720 * (level + 1);
        String numberFormat = msString.format("{0}{1}", (char)level, MarkdownUtil.listMarkerToChar(listItemBlock.getMarker()));
        ListLevelInitializer.initNumberListLevel(list, level,
            NumberStyle.ARABIC, numberFormat, ListLevelAlignment.LEFT, levelIndent, levelIndent, -360);

        list.getListLevels().get(level).setStartAt(listItemBlock.getStartAt());
    }

    /// <summary>
    /// Saves current Builder formatting and emphases counters.
    /// </summary>
    private void saveCurrentFormatting()
    {
        mBuilder.pushFont();
        mBuilder.pushParaPr();

        mSavedBoldCount = mBoldCount;
        mSavedItalicCount = mItalicCount;
        mSavedStrikethroughCount = mStrikethroughCount;
        mSavedUnderlineCount = mUnderlineCount;
        mSavedSuperscriptCount = mSuperscriptCount;
        mSavedSubscriptCount = mSubscriptCount;
    }

    /// <summary>
    /// Restores saved Builder formatting and emphases counters.
    /// </summary>
    private void restoreFormatting()
    {
        mBuilder.popFont();
        mBuilder.popParaPr();

        mBoldCount = mSavedBoldCount;
        mItalicCount = mSavedItalicCount;
        mStrikethroughCount = mSavedStrikethroughCount;
        mUnderlineCount = mSavedUnderlineCount;
        mSuperscriptCount = mSavedSuperscriptCount;
        mSubscriptCount = mSavedSubscriptCount;
    }

    /// <summary>
    /// Clears current Builder formatting and all emphases counters.
    /// </summary>
    private void clearFormatting()
    {
        mBuilder.clearFont();
        mBuilder.getCurrentParagraph().getParaPr().clear();

        mBoldCount = 0;
        mItalicCount = 0;
        mStrikethroughCount = 0;
        mUnderlineCount = 0;
    }

    /// <summary>
    /// Increments number of started HTML tags with a specified name in <see cref="mHtmlTagStarts"/>.
    /// </summary>
    private void incHtmlTagsStartCount(String name)
    {
        addToHtmlTagsStartCount(name, 1);
    }

    /// <summary>
    /// Decrements number of started HTML tags with a specified name in <see cref="mHtmlTagStarts"/>.
    /// </summary>
    private void decHtmlTagsStartCount(String name)
    {
        addToHtmlTagsStartCount(name, -1);
    }

    /// <summary>
    /// Adds a specified value to a number of started HTML tags with a specified name in <see cref="mHtmlTagStarts"/>.
    /// </summary>
    private void addToHtmlTagsStartCount(String name, int value)
    {
        int count = mHtmlTagStarts.get(name);
        if (StringToIntDictionary.isNullSubstitute(count))
            count = 0;

        if (count <= 0 && value < 0)
            return;

        mHtmlTagStarts.set(name, count + value);
    }

    /// <summary>
    /// Gets related DocumentBuilder object.
    /// </summary>
    DocumentBuilder getBuilder() { return mBuilder; }

    /// <summary>
    /// Gets map of bullet markers to corresponding lists.
    /// </summary>
    private IntToObjDictionary<List> getBulletMarkerToList()
    {
        if (mBulletMarkerToList == null)
            mBulletMarkerToList = new IntToObjDictionary<List>(4);

        return mBulletMarkerToList;
    }

    /// <summary>
    /// Gets a boolean value indicating whether we are now in raw HTML.
    /// </summary>
    private boolean isInHtml() { return getHtmlTagsStartedCount() > 0; }

    /// <summary>
    /// Gets a boolean value indicating whether we are inside HTML cell.
    /// </summary>
    private boolean isInHtmlCell()
    {
        HtmlTagBlock start = ms.as(mLastOpenedBlock, HtmlTagBlock.class);
        if (start == null)
            return false;

        if (!"td".equals(start.getTagName()))
            return false;

        HtmlTagBlock end = ms.as(mLastClosedBlock, HtmlTagBlock.class);
        if (end == null)
            return true;

        if (!"td".equals(end.getTagName()))
            return true;

        // Actually HTML tag pair can be even in another Paragraph. But for a while let's just check
        // this simple case with common parent.
        Block nextSibling = start.getNextSibling();
        while (nextSibling != null)
        {
            // If </td> is after <td>, then this <td> is closed. Then we are not inside a cell.
            if (end == nextSibling)
                return false;

            nextSibling = nextSibling.getNextSibling();
        }

        return true;
    }

    /// <summary>
    /// Gets integer value representing number of times HTML tags are started at the moment.
    /// </summary>
    private int getHtmlTagsStartedCount()
    {
        int count = 0;
        for (String tagName : mHtmlTagStarts.getKeys())
            count += mHtmlTagStarts.get(tagName);

        return count;
    }

    /// <summary>
    /// The last opened block.
    /// </summary>
    private Block mLastOpenedBlock;

    /// <summary>
    /// The last closed block.
    /// </summary>
    private Block mLastClosedBlock;

    private /*final*/ DocumentBuilder mBuilder;
    private IntToObjDictionary<List> mBulletMarkerToList;

    /// <summary>
    /// Maps hashcode of block chains (block hierarchies) to a Style.
    /// </summary>
    private /*final*/ IntToObjDictionary<Style> mBlocksChainHashcodeToStyle = new IntToObjDictionary<Style>();

    /// <summary>
    /// Keeps last List applied to a corresponding level.
    /// </summary>
    private /*final*/ List[] mLevelLists = new List[9];

    /// <summary>
    /// Maps Lists to ContainerBlocks they are belonging.
    /// </summary>
    private /*final*/ IntToObjDictionary<ContainerBlock> mListContainers = new IntToObjDictionary<ContainerBlock>();

    /// <summary>
    /// All Block styles should have unique ID.
    /// </summary>
    /// <remarks>
    /// For a moment there can be only 3 types of Block level blocks.
    /// The key: style name
    /// The value: the number of times the style is used (i.e. current style index)
    /// </remarks>
    private /*final*/ StringToIntDictionary mUsedBlockStyles = new StringToIntDictionary(3);

    private int mBoldCount;
    private int mItalicCount;
    private int mStrikethroughCount;
    private int mUnderlineCount;
    private int mSuperscriptCount;
    private int mSubscriptCount;

    /// <summary>
    /// The currently started Footnote.
    /// </summary>
    /// <returns> If it is not inside a Footnote, then <c>null</c>, otherwise current Footnote node. </returns>
    private Footnote mFootnote;

    private int mSavedBoldCount;
    private int mSavedItalicCount;
    private int mSavedStrikethroughCount;
    private int mSavedUnderlineCount;
    private int mSavedSuperscriptCount;
    private int mSavedSubscriptCount;

    /// <summary>
    /// Accumulates raw HTML until all started HTML tags are not ended and then inserts it into a Document.
    /// </summary>
    private /*final*/ StringBuilder mHtmlBuilder = new StringBuilder();

    /// <summary>
    /// Keeps number of times particular HTML tag is currently started.
    /// Key: tag name.
    /// Value: count.
    /// </summary>
    private /*final*/ StringToIntDictionary mHtmlTagStarts = new StringToIntDictionary();

    /// <summary>
    /// Keeps known (that will be actually processed as HTML and not as simple text) HTML tags.
    /// </summary>
    private static /*final*/ CaseInsensitiveHashSet G_KNOWN_HTML_TAG_NAMES = new CaseInsensitiveHashSet();

    /// <summary>
    /// Static Ctor.
    /// </summary>
    static/* MarkdownReaderContext()*/
    {
        G_KNOWN_HTML_TAG_NAMES.add("p");
        G_KNOWN_HTML_TAG_NAMES.add("h1");
        G_KNOWN_HTML_TAG_NAMES.add("h2");
        G_KNOWN_HTML_TAG_NAMES.add("h3");
        G_KNOWN_HTML_TAG_NAMES.add("h4");
        G_KNOWN_HTML_TAG_NAMES.add("h5");
        G_KNOWN_HTML_TAG_NAMES.add("h6");
        G_KNOWN_HTML_TAG_NAMES.add("i");
        G_KNOWN_HTML_TAG_NAMES.add("b");
        G_KNOWN_HTML_TAG_NAMES.add("u");
        G_KNOWN_HTML_TAG_NAMES.add("sup");
        G_KNOWN_HTML_TAG_NAMES.add("sub");
        G_KNOWN_HTML_TAG_NAMES.add("strong");
        G_KNOWN_HTML_TAG_NAMES.add("strike");
        G_KNOWN_HTML_TAG_NAMES.add("del");
        G_KNOWN_HTML_TAG_NAMES.add("s");
        G_KNOWN_HTML_TAG_NAMES.add("br");
        G_KNOWN_HTML_TAG_NAMES.add("table");
        G_KNOWN_HTML_TAG_NAMES.add("tr");
        G_KNOWN_HTML_TAG_NAMES.add("td");
        G_KNOWN_HTML_TAG_NAMES.add("code");
        G_KNOWN_HTML_TAG_NAMES.add("html");
        G_KNOWN_HTML_TAG_NAMES.add("head");
        G_KNOWN_HTML_TAG_NAMES.add("meta");
        G_KNOWN_HTML_TAG_NAMES.add("title");
        G_KNOWN_HTML_TAG_NAMES.add("div");
        G_KNOWN_HTML_TAG_NAMES.add("span");
        G_KNOWN_HTML_TAG_NAMES.add("body");
    }

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"tr",
		"table",
		"td",
		"p",
		"h1",
		"h2",
		"h3",
		"h4",
		"h5",
		"h6",
		"i",
		"sup",
		"sub",
		"strong",
		"b",
		"u",
		"strike",
		"del",
		"s",
		"br",
		"code"
	);

}

