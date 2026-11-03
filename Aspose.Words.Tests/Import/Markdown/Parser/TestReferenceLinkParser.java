// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Reference Link feature.
/// </summary>
public class TestReferenceLinkParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple Full Reference Link.
    /// </summary>
    @Test
    public void testFullReferenceLinkA() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "text", "http://example.com", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests several Full Reference Links with different labels.
    /// </summary>
    @Test
    public void testFullReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkB.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "text", "https://www.w3schools.com/", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), " and ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(3), "here", "http://example.com", "title1");
        checkInline(((ParagraphBlock)doc.get(0)).get(4), " and ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(5), "here1", "http://example.com", "title1");
        checkInline(((ParagraphBlock)doc.get(0)).get(6), " and ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(7), "here2", "http://example.com", "title1");
        checkInline(((ParagraphBlock)doc.get(0)).get(8), ".");
    }

    /// <summary>
    /// Tests Full Reference Link with square brackets in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkC() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkC.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "link [foo [bar]]", "http://example.com", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Full Reference Link with inner link reference in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkD.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link [link [foo ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "http://google.com", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), "]]");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(3), "ref", "http://example.com", "title1");
        checkInline(((ParagraphBlock)doc.get(0)).get(4), ".");
    }

    /// <summary>
    /// Tests Full Reference Link with escaped square bracket in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkE() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkE.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link [bar", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Link with formatting of the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkF() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkF.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "link *foo **bar** `#`*", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Link with inline image block in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkG() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkG.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "moonmoon.jpg", "/uri");
        checkImageBlock(((LinkTextBlock)((ParagraphBlock)doc.get(0)).get(0)).get(0), "moon", "moon.jpg");
    }

    /// <summary>
    /// Tests Full Reference Link with link text block in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkH() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkH.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "/uri1");
        checkInline(((ParagraphBlock)doc.get(0)).get(3), "]");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(4), "ref", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Link with formatted link text block in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkI() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkI.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo ");
        checkItalicInline(((ParagraphBlock)doc.get(0)).get(1), "*bar baz*");
        checkInline(((ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(1)).get(0), "bar ");
        checkLinkTextBlock(((ItalicInlineBlock)((ParagraphBlock)doc.get(0)).get(1)).get(1), "baz", "/uri");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), "]");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(3), "ref", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Link with start-to-inner formatting of the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkJ() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkJ.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "*");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "foo*", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Link with the mixed case reference.
    /// </summary>
    @Test
    public void testFullReferenceLinkK() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkK.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url", "title");
    }

    /// <summary>
    /// Tests Full Reference Link with the multiline label.
    /// </summary>
    @Test
    public void testFullReferenceLinkL() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkL.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "Baz", "/url");
    }

    /// <summary>
    /// Tests Full Reference Link with space-separated label and reference.
    /// </summary>
    @Test
    public void testFullReferenceLinkM() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkM.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo] ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "/url", "title");
    }

    /// <summary>
    /// Tests Full Reference Link with a missing reference.
    /// </summary>
    @Test
    public void testFullReferenceLinkN() throws Exception
    {
        MarkdownDocument doc = open("TestFRLinkN.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This [foo][bar] baz");
    }

    /// <summary>
    /// Tests simple Collapsed Reference Link.
    /// </summary>
    @Test
    public void testCollapsedReferenceLinkA() throws Exception
    {
        MarkdownDocument doc = open("TestCRLinkA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "here", "http://example.com", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Collapsed Reference Link with formatting of the label.
    /// </summary>
    @Test
    public void testCollapsedReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestCRLinkB.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "*foo* bar", "/url", "title");
        checkItalicInline(((LinkTextBlock)((ParagraphBlock)doc.get(0)).get(0)).get(0), "*foo*");
        checkInline(((LinkTextBlock)((ParagraphBlock)doc.get(0)).get(0)).get(1), " bar");
    }

    /// <summary>
    /// Tests Collapsed Reference Link with the mixed case reference.
    /// </summary>
    @Test
    public void testCollapsedReferenceLinkC() throws Exception
    {
        MarkdownDocument doc = open("TestCRLinkC.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "Foo", "/url", "title");
    }

    /// <summary>
    /// Tests Collapsed Reference Link with the multiline label.
    /// </summary>
    @Test
    public void testCollapsedReferenceLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestCRLinkD.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(1), " "+SOFT_BREAK+"[]");
    }

    /// <summary>
    /// Tests simple Shortcut Reference Link.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkA() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a link ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "here", "http://example.com", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with different definitions.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkB.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "bar", "/url1");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with escaped character.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkC() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkC.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[bar][foo!]");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with unescaped square bracket in the reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkD.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo][ref[]");
        checkInline(((ParagraphBlock)doc.get(1)).get(0), "[ref[]: /uri");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with unescaped square bracket in the reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkE() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkE.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo][ref[bar]]");
        checkInline(((ParagraphBlock)doc.get(1)).get(0), "[ref[bar]]: /uri");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with multiply square brackets in the reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkF() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkF.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[[[foo]]]");
        checkInline(((ParagraphBlock)doc.get(1)).get(0), "[[[foo]]]: /url");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with an escaped square bracket in the reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkG() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkG.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/uri");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with an escaped slash bracket in the reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkH() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkH.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "bar\\", "/uri");
    }

    /// <summary>
    /// Tests Shortcut Reference with the empty reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkI() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkI.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[]");
        checkInline(((ParagraphBlock)doc.get(1)).get(0), "[]: /uri");
    }

    /// <summary>
    /// Tests recursive link resolution.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkJ() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkJ.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url2");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "/url1");
    }

    /// <summary>
    /// Tests Shortcut Reference Links with escaped square bracket.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkK() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkK.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo]");
    }

    /// <summary>
    /// Tests resolving between Shortcut and Inline link reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkL() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkL.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "");
    }

    /// <summary>
    /// Tests resolving between Shortcut and Inline link reference.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkM() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkM.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url1");
        checkInline(((ParagraphBlock)doc.get(0)).get(1), "(not a link)");
    }

    /// <summary>
    /// Tests recursive link resolution.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkN() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkN.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "[foo]");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "/url");
    }

    /// <summary>
    /// Tests recursive link resolution.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkO() throws Exception
    {
        MarkdownDocument doc = open("TestSRLinkO.md");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url2");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "baz", "/url1");
    }

    /// <summary>
    /// Tests simple Link Definition.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionA() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkA.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests Link Definition in the middle.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionB() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkB.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("BAR"), "/baz");
    }

    /// <summary>
    /// Tests Link Definition in the code block.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionC() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkC.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests Link Definition with indent.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionD() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkD.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests Link Definition with the redundant word.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionE() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkE.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests Link Definition with the multiline reference.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionF() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkF.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests Link Definition after Heading definition.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionG() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkG.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests Link Definition with the incorrect title.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionH() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkH.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests Link Definition without line break.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionI() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkI.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests multiline Link Definition.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionJ() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkJ.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url \r\n 'the title'",
            "/url", "the title");
    }


    /// <summary>
    /// Tests Link Definition with the angle bracket.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionL() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkL.md");
        LinkDefinitionBlock linkDefinition = doc.getLinkDefinitions().get("FOO BAR");
        checkLinkDefinition(linkDefinition, "<my url>\r\n 'title'", "my%20url",
            "title");
    }

    /// <summary>
    /// Tests Link Definition with the multiline title.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionM() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkM.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url '\r\ntitle\r\nline1\r\nline2\r\n '",
            "/url", "title\r\nline1\r\nline2");
    }

    /// <summary>
    /// Tests Link Definition with the line break in the title.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionN() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkN.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests multiline Link Definition.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionO() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkO.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests for an incomplete link definition.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionP() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkP.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests Link Definition with the empty angle brackets.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionQ() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkQ.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "<>", "");
    }

    /// <summary>
    /// Tests Link Definition with an incorrect title.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionR() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkR.md");
        msAssert.areEqual(0, doc.getLinkDefinitions().size());
    }

    /// <summary>
    /// Tests Link Definition with escaped characters.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionS() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkS.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url\\bar\\*baz \"foo\\\"bar\\baz\"",
            "/url\\bar*baz", "foo\"bar\\baz");
    }

    /// <summary>
    /// Tests Link Definition with a definition after.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionT() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkT.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "url");
    }

    /// <summary>
    /// Tests Link Definition with duplicate references.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionU() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkU.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "first");
    }

    /// <summary>
    /// Tests Link Definition with the mixed case reference.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionV() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkV.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url");
    }

    /// <summary>
    /// Tests Link Definition with the mixed case reference.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionW() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkW.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("ΑΓΩ"), "/φου");
    }

    /// <summary>
    /// Tests several Link Definitions.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionX() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkX.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/foo-url \"foo\"",
            "/foo-url", "foo");
        checkLinkDefinition(doc.getLinkDefinitions().get("BAR"), "/bar-url\r\n \"bar\"",
            "/bar-url", "bar");
        checkLinkDefinition(doc.getLinkDefinitions().get("BAZ"), "/baz-url");
    }

    /// <summary>
    /// Tests Link Definition with a definition before and after.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionY() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkY.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/url \"title\"",
            "/url", "title");
        checkLinkDefinition(doc.getLinkDefinitions().get("BAR"), "/url1 \"title1\"",
            "/url1", "title1");
    }

    /// <summary>
    /// Tests Link Definition with duplicate references.
    /// </summary>
    @Test
    public void testReferenceLinkDefinitionZ() throws Exception
    {
        MarkdownDocument doc = open("TestRDLinkZ.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("BAR"), "/url \"title\"");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\ReferenceLinks\\{0}", fileName));
    }
}

