// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/02/2021 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests the parsing of the markdown Footnote.
/// </summary>
public class TestFootnoteParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests a simple Footnote Reference.
    /// </summary>
    @Test
    public void testFootnote_01() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_01.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote [^1] and definition after two LineBreaks.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and definition after two LineBreaks.") });

        FootnoteReferenceBlock footnoteReferenceBlock = (FootnoteReferenceBlock)((ParagraphBlock)doc.get(0)).get(1);
        checkInlines(footnoteReferenceBlock, new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "1") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests a simple Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_02() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_02.md");
        msAssert.areEqual(1, doc.getCount());

        checkFootnoteDefinition(doc.get(0), "Definition without reference (not shown).");
    }

    /// <summary>
    /// Tests a simple Footnote Reference without Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_03() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_03.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "Here's a footnote[^10] without definition (shown as simple text).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here's a footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^10]"),
            new ExpectedInline(BlockType.INLINE, " without definition (shown as simple text).") });
    }

    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition placed after the one LineBreak.
    /// </summary>
    @Test
    public void testFootnote_04() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_04.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote [^1] and definition after one LineBreak (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and definition after one LineBreak (accepted).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests two Footnote References (same labels) with one Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_05() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_05.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^3], footnote[^3] and definition (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^3]"),
            new ExpectedInline(BlockType.INLINE, ", footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^3]"),
            new ExpectedInline(BlockType.INLINE, " and definition (accepted).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests one Footnote Reference with two Footnote Definitions with the same label.
    /// </summary>
    @Test
    public void testFootnote_06() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_06.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0),
            "Here are footnote[^4] and two definitions with same label (error, accepted line1).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^4]"),
            new ExpectedInline(
                BlockType.INLINE, " and two definitions with same label (error, accepted line1).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text (line1).");
        checkFootnoteDefinition(doc.get(2), "Some footnote text (line2).");
    }

    /// <summary>
    /// Tests a Footnote Reference with a multiline Footnote Definition with one LineBreak.
    /// </summary>
    @Test
    public void testFootnote_07() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_07.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0),
            "Here are footnote [^d] and multiline definition with one LineBreak (accepted as one line).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^d]"),
            new ExpectedInline(
                BlockType.INLINE, " and multiline definition with one LineBreak (accepted as one line).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text (line1)."+SOFT_BREAK+
                                        "Some footnote text (line2)."+SOFT_BREAK+
                                        " Some footnote text (line3)."+SOFT_BREAK+
                                        " Some footnote text (line4).");
    }

    /// <summary>
    /// Tests a Footnote Reference with a multiline Footnote Definition with two LineBreaks and indent.
    /// </summary>
    @Test
    public void testFootnote_08() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_08.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0),
            "Here are footnote [^e] and multiline definition with two LineBreaks and indent (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^e]"),
            new ExpectedInline(
                BlockType.INLINE, " and multiline definition with two LineBreaks and indent (accepted).") });

        FootnoteDefinitionBlock footnoteDefinition = (FootnoteDefinitionBlock)doc.get(1);
        checkParagraph(footnoteDefinition.get(0), "Some footnote text (line1).");
        checkParagraph(footnoteDefinition.get(1), "Some footnote text (line2).");
        checkParagraph(footnoteDefinition.get(2), "Some footnote text (line3).");
    }

    /// <summary>
    /// Tests a Footnote Reference with a multiline Footnote Definition with two LineBreaks and without indent.
    /// </summary>
    @Test
    public void testFootnote_09() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_09.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0),
            "Here are footnote [^f] and multiline definition with two LineBreaks and without indent " +
            "(accepted line1).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^f]"),
            new ExpectedInline(BlockType.INLINE,
                " and multiline definition with two LineBreaks and without indent (accepted line1).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text (line1).");
        checkParagraph(doc.get(2), "Some footnote text (line2).");
    }

    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition with a space in the label.
    /// </summary>
    @Test
    public void testFootnote_10() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_10.md");
        msAssert.areEqual(6, doc.getCount());

        checkParagraph(doc.get(0), "Here are wrong footnote[^ e] and wrong definition (simple text).");
        checkParagraph(doc.get(1), "[^ e]: Some footnote text (simple text).");
        checkParagraph(doc.get(2), "Here are wrong footnote[^e f] and wrong definition (simple text).");
        checkParagraph(doc.get(3), "[^e f]: Some footnote text (simple text).");
        checkParagraph(doc.get(4), "Here are wrong footnote[^e ] and wrong definition (simple text).");
        checkParagraph(doc.get(5), "[^e ]: Some footnote text (simple text).");
    }

    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition with a valid symblols in the label.
    /// </summary>
    @Test
    public void testFootnote_11() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_11.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^t@^%$#()-=+|/>.?;,\"'e] and definition (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^t@^%$#()-=+|/>.?;,\"'e]"),
            new ExpectedInline(BlockType.INLINE, " and definition (accepted).") });
    }

    /// <summary>
    /// Tests that InlineCode delimiters have a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_12() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_12.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are wrong footnote[^t`]` and wrong definition (simple text and code).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are wrong footnote[^t"),
            new ExpectedInline(BlockType.INLINE_CODE, "`]`"),
            new ExpectedInline(BlockType.INLINE, " and wrong definition (simple text and code).") });

        checkParagraph(doc.get(1), "[^t`]`: Some footnote text (simple text and code).");

        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[^t"),
            new ExpectedInline(BlockType.INLINE_CODE, "`]`"),
            new ExpectedInline(BlockType.INLINE, ": Some footnote text (simple text and code).") });
    }


    /// <summary>
    /// Tests that InlineLink title has a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_14() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_14.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0),
            "Here are link with title/uri \"titl[^pe\"], footnote[^pe\")] and definition (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are "),
            new ExpectedInline(BlockType.LINK_TEXT, "link with title"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri \"titl[^pe\""),
            new ExpectedInline(BlockType.INLINE, "], footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^pe\")]"),
            new ExpectedInline(BlockType.INLINE, " and definition (accepted).") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests how the simple text is parsed after the multiline definition.
    /// </summary>
    @Test
    public void testFootnote_15() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_15.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^d], multiline definition and simple text.");
        checkParagraph(doc.get(2), "Simple text.");

        FootnoteDefinitionBlock footnoteDefinition = (FootnoteDefinitionBlock)doc.get(1);
        checkParagraph(footnoteDefinition.get(0), "Some footnote text (para1).");
        checkParagraph(footnoteDefinition.get(1), "Some footnote text (para2).");
    }

    /// <summary>
    /// Tests two Footnote Definitions in a row.
    /// </summary>
    @Test
    public void testFootnote_16() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_16.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^1], footnote[^2] and two definitions in a row (accepted).");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, ", footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^2]"),
            new ExpectedInline(BlockType.INLINE, " and two definitions in a row (accepted).") });

        checkFootnoteDefinition(doc.get(1), "Footnote def1.");
        checkFootnoteDefinition(doc.get(2), "Footnote def2.");
    }


    /// <summary>
    /// Tests IndentedCode in Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_18() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_18.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here is footnote[^indented] with IndentedCode in definition.");
        checkInlines(doc.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "Here is footnote"),
                new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^indented]"),
                new ExpectedInline(BlockType.INLINE, " with IndentedCode in definition.")
            });

        FootnoteDefinitionBlock footnote = (FootnoteDefinitionBlock)doc.get(1);
        checkParagraph(footnote.get(0), "Some footnote text (para1).");
        checkIndentedCode(footnote.get(1), "    Some indented footnote text (para2).");

        msAssert.areEqual(2, footnote.getCount());
    }

    /// <summary>
    /// Tests an empty Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_19() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_19.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^1] and empty definition.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and empty definition.") });

        FootnoteDefinitionBlock footnoteDefinition = (FootnoteDefinitionBlock)doc.get(1);
        msAssert.areEqual(0, footnoteDefinition.getCount());
    }

    /// <summary>
    /// Tests an empty Footnote Definition and followed simple text.
    /// </summary>
    @Test
    public void testFootnote_20() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_20.md");
        msAssert.areEqual(3, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^1], empty definition and simple text.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, ", empty definition and simple text.") });

        FootnoteDefinitionBlock footnoteDefinition = (FootnoteDefinitionBlock)doc.get(1);
        msAssert.areEqual(0, footnoteDefinition.getCount());

        checkParagraph(doc.get(2), "Simple text.");
    }

    /// <summary>
    /// Tests a Footnote Reference in italics.
    /// </summary>
    @Test
    public void testFootnote_21() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_21.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote in italics *[^1]* and definition.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote in italics "),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*[^1]*"),
            new ExpectedInline(BlockType.INLINE, " and definition.") });

        ItalicInlineBlock italicBlock = (ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(1);
        checkInlines(italicBlock, new ExpectedInline[] { new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests a Footnote Reference and a simple text in italics.
    /// </summary>
    @Test
    public void testFootnote_22() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_22.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are *footnote[^1] and* simple text in italics.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are "),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*footnote[^1] and*"),
            new ExpectedInline(BlockType.INLINE, " simple text in italics.") });

        ItalicInlineBlock italicBlock = (ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(1);
        checkInlines(italicBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests a Footnote Reference and a simple text in italics (special cases).
    /// </summary>
    @Test
    public void testFootnote_23() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_23.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote *[^1],* *footnote[^1]* and simple text in italics.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote "),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*[^1],*"),
            new ExpectedInline(BlockType.INLINE, " "),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*footnote[^1]*"),
            new ExpectedInline(BlockType.INLINE, " and simple text in italics.") });

        ItalicInlineBlock italicBlock = (ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(1);
        checkInlines(italicBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, ",") });

        italicBlock = (ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(3);
        checkInlines(italicBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }

    /// <summary>
    /// Tests a Footnote Reference with an indented Footnote Definition.
    /// </summary>
    @Test
    public void testFootnote_24() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_24.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^1] and indented definition.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and indented definition.") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text.");
    }


    /// <summary>
    /// Tests a Footnote Reference with a Footnote Definition containing list.
    /// </summary>
    /// <remarks>
    /// TODO: Footnote Definition containing list is not implemented.
    /// </remarks>
    @Test
    public void testFootnote_26() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_26.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here are footnote[^1] and definition containing list.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here are footnote"),
            new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]"),
            new ExpectedInline(BlockType.INLINE, " and definition containing list.") });

        checkFootnoteDefinition(doc.get(1), "Some footnote text."+SOFT_BREAK+"+ list item 1."+SOFT_BREAK+"+ list item 2.");
    }

    /// <summary>
    /// Tests that InlineLink text has a precedence over a FootnoteReference delimiter.
    /// </summary>
    @Test
    public void testFootnote_27() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_27.md");
        msAssert.areEqual(2, doc.getCount());

        checkParagraph(doc.get(0), "Here is link ^1link_dest.");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "Here is link "),
            new ExpectedInline(BlockType.LINK_TEXT, "^1"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "link_dest"),
            new ExpectedInline(BlockType.INLINE, ".") });

        checkFootnoteDefinition(doc.get(1), "Footnote definition without reference.");
    }

    /// <summary>
    /// Tests emphases in footnote.
    /// </summary>
    @Test
    public void testFootnote_28() throws Exception
    {
        MarkdownDocument doc = open("TestFootnote_28.md");
        msAssert.areEqual(3, doc.getCount());

        // Check paragraph with footnote references.
        checkParagraph(doc.get(0), "Here is footnote in **bold[^1]** and footnote in *italics*[^2].");
        checkInlines(doc.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "Here is footnote in "),
                new ExpectedInline(BlockType.BOLD_INLINE, "**bold[^1]**"),
                new ExpectedInline(BlockType.INLINE, " and footnote in "),
                new ExpectedInline(BlockType.ITALIC_INLINE, "*italics*"),
                new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^2]"),
                new ExpectedInline(BlockType.INLINE, ".")
            });

        // Check first footnote reference is inside bold.
        BoldInlineBlock boldContainer = (BoldInlineBlock)((ParagraphBlock)doc.get(0)).get(1);
        checkInlines(boldContainer,
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "bold"),
                new ExpectedInline(BlockType.FOOTNOTE_REFERENCE, "[^1]")
            });

        // Check first footnote.
        FootnoteDefinitionBlock footnote = (FootnoteDefinitionBlock)doc.get(1);
        checkParagraph(footnote.get(0), "Some **bold footnote** text.");
        checkInlines(footnote.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "Some "),
                new ExpectedInline(BlockType.BOLD_INLINE, "**bold footnote**"),
                new ExpectedInline(BlockType.INLINE, " text.")
            });

        // Check second footnote.
        footnote = (FootnoteDefinitionBlock)doc.get(2);
        checkParagraph(footnote.get(0), "Some *italic footnote*");
        checkInlines(footnote.get(0),
            new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "Some "),
                new ExpectedInline(BlockType.ITALIC_INLINE, "*italic footnote*")
            });
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Footnotes\\{0}", fileName));
    }

    /// <summary>
    /// Verifies the FootnoteDefinition block.
    /// </summary>
    private static void checkFootnoteDefinition(Block footnoteDefinitionBlock, String expectedText)
    {
        FootnoteDefinitionBlock footnoteDefinition = (FootnoteDefinitionBlock)footnoteDefinitionBlock;
        checkParagraph(footnoteDefinition.get(0), expectedText);
    }
}

