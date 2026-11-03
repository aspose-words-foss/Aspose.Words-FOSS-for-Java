// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/05/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Link feature.
/// </summary>
public class TestLinkParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple Link.
    /// </summary>
    @Test
    public void testLinkA() throws Exception
    {
        MarkdownDocument doc = open("TestLinkA.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/uri");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "text", "http://example.com");
    }

    /// <summary>
    /// Tests simple Link with a title.
    /// </summary>
    @Test
    public void testLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestLinkB.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/uri", "title");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "text", "http://example.com", "title");
    }

    /// <summary>
    /// Tests empty link text for Link.
    /// </summary>
    @Test
    public void testLinkC() throws Exception
    {
        MarkdownDocument doc = open("TestLinkC.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "", "http://example.com");
    }

    /// <summary>
    /// Tests empty link URI for Link.
    /// </summary>
    @Test
    public void testLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestLinkD.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "text", "");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "link", "");
    }

    /// <summary>
    /// Tests Link titles with different quotes.
    /// </summary>
    @Test
    public void testLinkE() throws Exception
    {
        MarkdownDocument doc = open("TestLinkE.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "text", "http://example.com", "title");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "text", "http://example.com", "title");
    }

    /// <summary>
    /// Tests Link with angle brackets for link URI.
    /// </summary>
    @Test
    public void testLinkF() throws Exception
    {
        MarkdownDocument doc = open("TestLinkF.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "text", "http://example.com");
    }

    /// <summary>
    /// Tests Link titles with backslash-escaped quotes.
    /// </summary>
    @Test
    public void testLinkG() throws Exception
    {
        MarkdownDocument doc = open("TestLinkG.md");
        msAssert.areEqual(13, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "text", "http://example.com", "ti\"tle");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "text", "http://example.com", "ti'tle");
        checkLinkTextBlock(((ParagraphBlock)doc.get(2)).get(0), "text", "http://example.com", "ti)tle");
        checkInlines(doc.get(3), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[text](http://example.com \"ti\"tle\")")});
        checkInlines(doc.get(4), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[text](http://example.com 'ti'tle')")});
        checkInlines(doc.get(5), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[text](http://example.com (ti)tle))")});
        checkLinkTextBlock(((ParagraphBlock)doc.get(6)).get(0), "text", "http://example.com", "ti'tle");
        checkLinkTextBlock(((ParagraphBlock)doc.get(7)).get(0), "text", "http://example.com", "ti\"tle");
        checkLinkTextBlock(((ParagraphBlock)doc.get(8)).get(0), "text", "http://example.com", "ti\"tle");
        checkLinkTextBlock(((ParagraphBlock)doc.get(9)).get(0), "text", "http://example.com", "ti)tle");
        checkInlines(doc.get(10), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[link](/url \"title \"and\" title\")")});
        checkLinkTextBlock(((ParagraphBlock)doc.get(11)).get(0), "link", "/url", "title \"and\" title");
        checkInlines(doc.get(12), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[text](http://example.com (ti(tle))")});
    }

    /// <summary>
    /// Tests that the Link destination cannot contain spaces or line breaks, except spaces enclosed in pointy brackets.
    /// </summary>
    @Test
    public void testLinkH() throws Exception
    {
        MarkdownDocument doc = open("TestLinkH.md");
        msAssert.areEqual(4, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link](/my uri)") });
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "link", "/my%20uri");
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link](foo"+SOFT_BREAK+"bar)") });
        checkInlines(doc.get(3), new ExpectedInline[]
        {
            new ExpectedInline(BlockType.INLINE, "[link]("),
            new ExpectedInline(BlockType.HTML_TAG, "<foo"+SOFT_BREAK+"bar>"),
            new ExpectedInline(BlockType.INLINE, ")")
        });
    }

    /// <summary>
    /// Tests that parentheses inside the Link destination may be escaped.
    /// </summary>
    @Test
    public void testLinkI() throws Exception
    {
        MarkdownDocument doc = open("TestLinkI.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "(foo)");
    }

    /// <summary>
    /// Tests that any number of parentheses in the Link target are allowed without escaping,
    /// as long as they are balanced.
    /// </summary>
    @Test
    public void testLinkJ() throws Exception
    {
        MarkdownDocument doc = open("TestLinkJ.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "foo(and(bar))");
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link](foo(and(bar))") });
    }

    /// <summary>
    /// Tests that if Link have unbalanced parentheses,
    /// they need to be escaped or used with the angle brackets.
    /// </summary>
    @Test
    public void testLinkK() throws Exception
    {
        MarkdownDocument doc = open("TestLinkK.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "foo(and(bar)");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "link", "foo(and(bar)");
    }

    /// <summary>
    /// Tests that parentheses and other symbols in the Link can also be escaped, as usual in Markdown.
    /// </summary>
    @Test
    public void testLinkL() throws Exception
    {
        MarkdownDocument doc = open("TestLinkL.md");
        msAssert.areEqual(2, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "foo):");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "link", "'.+-=*_%60~:!#$%25&@/?%5E%7C;\\%22%3C%3E%5B%5D()%7B%7D");
    }

    /// <summary>
    /// Tests that Links can contain fragment identifiers and queries.
    /// </summary>
    @Test
    public void testLinkM() throws Exception
    {
        MarkdownDocument doc = open("TestLinkM.md");
        msAssert.areEqual(3, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "#fragment");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "link", "http://example.com#fragment");
        checkLinkTextBlock(((ParagraphBlock)doc.get(2)).get(0), "link", "http://example.com?foo=3#frag");
    }

    /// <summary>
    /// Tests that a backslash before a non-escapable character in the Link is just a backslash.
    /// </summary>
    @Test
    public void testLinkN() throws Exception
    {
        MarkdownDocument doc = open("TestLinkN.md");
        msAssert.areEqual(3, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "foo\\bar");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "li\\nk", "foobar");
        checkLinkTextBlock(((ParagraphBlock)doc.get(2)).get(0), "link", "foo", "b\\ar");
    }

    /// <summary>
    /// Tests that URL-escaping should be left alone inside the Link destination,
    /// as all URL-escaped characters are also valid URL characters.
    /// </summary>
    @Test
    public void testLinkO() throws Exception
    {
        MarkdownDocument doc = open("TestLinkO.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "foo%20b&auml;");
    }

    /// <summary>
    /// Tests that, because titles can often be parsed as Link destinations,
    /// if you try to omit the destination and keep the title, you’ll get unexpected results.
    /// </summary>
    @Test
    public void testLinkP() throws Exception
    {
        MarkdownDocument doc = open("TestLinkP.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "%22title%22");
    }

    /// <summary>
    /// Tests that backslash escapes and entity and numeric character references may be used in Link titles.
    /// </summary>
    @Test
    public void testLinkQ() throws Exception
    {
        MarkdownDocument doc = open("TestLinkQ.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/url", "title \"&quot;");
    }

    /// <summary>
    /// Tests that whitespace is allowed around the Link destination and title.
    /// </summary>
    /// <example>
    ///     [link](   /uri
    ///       "title"  )
    /// </example>
    @Test
    public void testLinkR() throws Exception
    {
        MarkdownDocument doc = open("TestLinkR.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/uri", "title");
    }

    /// <summary>
    /// Tests that whitespace is not allowed between the Link text and the following parenthesis.
    /// </summary>
    @Test
    public void testLinkS() throws Exception
    {
        MarkdownDocument doc = open("TestLinkS.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "[link] (/uri)");
        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link] (/uri)") });
    }

    /// <summary>
    /// Tests that the Link text may contain balanced brackets, but not unbalanced ones, unless they are escaped.
    /// </summary>
    @Test
    public void testLinkT() throws Exception
    {
        MarkdownDocument doc = open("TestLinkT.md");
        msAssert.areEqual(4, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link [foo [bar]]", "/uri");
        checkInlines(doc.get(1), new ExpectedInline[] {new ExpectedInline(BlockType.INLINE, "[link] bar](/uri)")});
        checkInlines(doc.get(2), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[link "),
            new ExpectedInline(BlockType.LINK_TEXT, "bar"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri")});
        checkLinkTextBlock(((ParagraphBlock)doc.get(3)).get(0), "link [bar", "/uri");
    }

    /// <summary>
    /// Tests that the Link text may contain inline content.
    /// </summary>
    @Test
    public void testLinkU() throws Exception
    {
        MarkdownDocument doc = open("TestLinkU.md");
        msAssert.areEqual(2, doc.getCount());

        LinkTextBlock linkTextBlock = (LinkTextBlock)((ParagraphBlock)doc.get(0)).get(0);
        checkLinkTextBlock(linkTextBlock, "link *foo **bar** `#`*", "/uri");
        ItalicInlineBlock italicBlock = (ItalicInlineBlock)linkTextBlock.get(1);
        checkInlines(italicBlock, new ExpectedInline[]
            {
                new ExpectedInline(BlockType.INLINE, "foo "),
                new ExpectedInline(BlockType.BOLD_INLINE, "**bar**"),
                new ExpectedInline(BlockType.INLINE, " "),
                new ExpectedInline(BlockType.INLINE_CODE, "`#`")
            });

        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.LINK_TEXT, "moonmoon.jpg"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri")});

        linkTextBlock = (LinkTextBlock)((ParagraphBlock)doc.get(1)).get(0);
        checkImageBlock(linkTextBlock.get(0), "moon", "moon.jpg");
    }

    /// <summary>
    /// Tests that Links may not contain other Links, at any level of nesting.
    /// </summary>
    @Test
    public void testLinkV() throws Exception
    {
        MarkdownDocument doc = open("TestLinkV.md");
        msAssert.areEqual(3, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[foo "),
            new ExpectedInline(BlockType.LINK_TEXT, "bar"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri"),
            new ExpectedInline(BlockType.INLINE, "](/uri)") });

        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[foo "),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*[bar baz/uri](/uri)*"),
            new ExpectedInline(BlockType.INLINE, "](/uri)") });
        ItalicInlineBlock italicBlock = (ItalicInlineBlock)((ParagraphBlock)doc.get(1)).get(1);
        checkInlines(italicBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[bar "),
            new ExpectedInline(BlockType.LINK_TEXT, "baz"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri"),
            new ExpectedInline(BlockType.INLINE, "](/uri)") });

        checkInlines(doc.get(2), new ExpectedInline[] {
            new ExpectedInline(BlockType.IMAGE_DESCRIPTION, "[foo](uri2)"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "uri3")
        });
    }

    /// <summary>
    /// Tests the precedence of Link text grouping over emphasis grouping.
    /// </summary>
    @Test
    public void testLinkW() throws Exception
    {
        MarkdownDocument doc = open("TestLinkW.md");
        msAssert.areEqual(2, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "*"),
            new ExpectedInline(BlockType.LINK_TEXT, "foo*"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri")});
        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.LINK_TEXT, "foo *bar"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "baz*")});
    }

    /// <summary>
    /// Tests that brackets that aren’t part of Links do not take precedence.
    /// </summary>
    @Test
    public void testLinkX() throws Exception
    {
        MarkdownDocument doc = open("TestLinkX.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "*foo [bar* baz]");

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.ITALIC_INLINE, "*foo [bar*"),
            new ExpectedInline(BlockType.INLINE, " baz]") });
    }


    /// <summary>
    /// Tests that a Link destination consists of a sequence of characters between angle brackets that contains
    /// no unescaped angle brackets.
    /// </summary>
    @Test
    public void testLinkZ01() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ01.md");
        msAssert.areEqual(8, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/uri");
        checkInlines(doc.get(1), new ExpectedInline[]
        {
            new ExpectedInline(BlockType.INLINE, "[foo]("),
            new ExpectedInline(BlockType.HTML_TAG, "</uri>"),
            new ExpectedInline(BlockType.INLINE, ")")
        });
        checkLinkTextBlock(((ParagraphBlock)doc.get(2)).get(0), "foo", "%3C/uri%3E");
        checkLinkTextBlock(((ParagraphBlock)doc.get(3)).get(0), "foo", "%3C/uri%3E");
        checkInlines(doc.get(4), new ExpectedInline[]
        {
            new ExpectedInline(BlockType.INLINE, "[foo]("),
            new ExpectedInline(BlockType.HTML_TAG, "</u>"),
            new ExpectedInline(BlockType.INLINE, "ri>)")
        });
        checkLinkTextBlock(((ParagraphBlock)doc.get(5)).get(0), "foo", "/u%3Eri");
        checkInlines(doc.get(6), new ExpectedInline[]
        {
            new ExpectedInline(BlockType.INLINE, "[foo]("),
            new ExpectedInline(BlockType.HTML_TAG, "</u<ri>"),
            new ExpectedInline(BlockType.INLINE, ")")
        });
        checkLinkTextBlock(((ParagraphBlock)doc.get(7)).get(0), "foo", "/u%3Eri");
    }

    /// <summary>
    /// Tests that a Link titles may span multiple lines.
    /// </summary>
    @Test
    public void testLinkZ02() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ02.md");
        msAssert.areEqual(1, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.LINK_TEXT, "link"),
            new ExpectedInline(BlockType.LINK_DESTINATION, "/uri \"ti\u000btle\"")});
    }

    /// <summary>
    /// Tests an empty Link title.
    /// </summary>
    @Test
    public void testLinkZ03() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ03.md");
        msAssert.areEqual(1, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/uri");
    }

    /// <summary>
    /// Tests edge conditions during the Link parsing.
    /// </summary>
    @Test
    public void testLinkZ04() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ04.md");
        msAssert.areEqual(6, doc.getCount());

        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link", "/uri", "title");
        checkInlines(doc.get(1), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link](/uri \"title\" w)") });
        checkInlines(doc.get(2), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[link](/uri \"title\" ww)") });
        checkLinkTextBlock(((ParagraphBlock)doc.get(3)).get(0), "link", "");
        checkLinkTextBlock(((ParagraphBlock)doc.get(4)).get(0), "link", "/uri");
        checkLinkTextBlock(((ParagraphBlock)doc.get(5)).get(0), "link", "/uri");
    }

    /// <summary>
    /// Tests that backslash-escaped left square bracket is not valid for Link.
    /// </summary>
    @Test
    public void testLinkZ05() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ05.md");
        msAssert.areEqual(1, doc.getCount());

        checkParagraph(doc.get(0), "[foo](/uri)");

        checkInlines(doc.get(0), new ExpectedInline[] { new ExpectedInline(BlockType.INLINE, "[foo](/uri)") });
    }


    /// <summary>
    /// Tests that AutoLink and InlineCode delimiters have a precedence over a InlineLink delimiter.
    /// </summary>
    @Test
    public void testLinkZ07() throws Exception
    {
        MarkdownDocument doc = open("TestLinkZ07.md");
        msAssert.areEqual(4, doc.getCount());

        checkInlines(doc.get(0), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[n"),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*o`t`a*"),
            new ExpectedInline(BlockType.BOLD_INLINE, "**li`nk](f`o**"),
            new ExpectedInline(BlockType.INLINE, "o)")});

        checkInlines(doc.get(1), new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "[n"),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*o`t`a*"),
            new ExpectedInline(BlockType.BOLD_INLINE, "**l<in:k](f>o**"),
            new ExpectedInline(BlockType.INLINE, "o)")
        });

        BoldInlineBlock boldInlineBlock = (BoldInlineBlock)((ParagraphBlock)doc.get(1)).get(2);
        checkInlines(boldInlineBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "l"),
            new ExpectedInline(BlockType.AUTOLINK, "<in:k](f>"),
            new ExpectedInline(BlockType.INLINE, "o")});

        LinkTextBlock linkTextBlock = (LinkTextBlock)((ParagraphBlock)doc.get(2)).get(0);
        checkLinkTextBlock(linkTextBlock, "but*this`is`a***link", "f%60o**o");
        checkInlines(linkTextBlock, new ExpectedInline[] {
            new ExpectedInline(BlockType.INLINE, "but"),
            new ExpectedInline(BlockType.ITALIC_INLINE, "*this`is`a*"),
            new ExpectedInline(BlockType.INLINE, "**link")
        });

        checkLinkTextBlock(((ParagraphBlock)doc.get(3)).get(0), "This`is](`link", "foo");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Link\\{0}", fileName));
    }
}

