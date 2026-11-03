// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/04/2022 by Vadim Saltykov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Reference Image feature.
/// </summary>
public class TestReferenceImageParser extends TestMarkdownParserBase
{
    /// <summary>
    /// Tests simple Full Reference Image.
    /// </summary>
    @Test
    public void testFullReferenceImageA() throws Exception
    {
        MarkdownDocument doc = open("TestFRImageA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a image ");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(1), "Alt text", "/uri", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Full Reference Image with square brackets in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestFRImageB.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "Image ");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(1), "link [foo [bar]]", "/uri", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Full Reference Image with escaped square bracket in the label.
    /// </summary>
    @Test
    public void testFullReferenceLinkC() throws Exception
    {
        MarkdownDocument doc = open("TestFRImageC.md");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(0), "link [bar", "/uri");
    }

    /// <summary>
    /// Tests Full Reference Image with space-separated label and reference.
    /// </summary>
    @Test
    public void testFullReferenceLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestFRImageD.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "![foo] ");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "bar", "/url", "title");
    }


    /// <summary>
    /// Tests resolving between Full Reference Image and Shortcut LinkText.
    /// </summary>
    @Test
    public void testFullReferenceLinkF() throws Exception
    {
        MarkdownDocument doc = open("TestFRImageF.md");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url2");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "baz", "/url1");
    }



    /// <summary>
    /// Tests simple Collapsed Reference Image.
    /// </summary>
    @Test
    public void testCollapsedReferenceImageA() throws Exception
    {
        MarkdownDocument doc = open("TestCRImageA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a image ");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(1), "foo", "/uri", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests Collapsed Reference Image with the mixed case reference.
    /// </summary>
    @Test
    public void testCollapsedReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestCRImageB.md");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(0), "Foo", "/url", "title");
    }

    /// <summary>
    /// Tests simple Shortcut Reference Image.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkA() throws Exception
    {
        MarkdownDocument doc = open("TestSRImageA.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "This is a image ");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(1), "foo", "/uri", "title");
        checkInline(((ParagraphBlock)doc.get(0)).get(2), ".");
    }

    /// <summary>
    /// Tests mixing reference image and reference link labels.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkB() throws Exception
    {
        MarkdownDocument doc = open("TestSRImageB.md");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/url1");
        checkLinkTextBlock(((ParagraphBlock)doc.get(1)).get(0), "foo", "/url1");
        checkImageBlock(((ParagraphBlock)doc.get(1)).get(1), "bar", "/url2");
    }

    /// <summary>
    /// Tests the priority of inline definition and reference definition.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkС() throws Exception
    {
        MarkdownDocument doc = open("TestSRImageC.md");
        checkImageBlock(((ParagraphBlock)doc.get(0)).get(0), "foo", "/uri1");
    }

    /// <summary>
    /// Tests an empty reference image.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkD() throws Exception
    {
        MarkdownDocument doc = open("TestSRImageD.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "![]");
        checkInline(((ParagraphBlock)doc.get(1)).get(0), "[]: /uri");
    }

    /// <summary>
    /// Tests the escaped description of the reference image.
    /// </summary>
    @Test
    public void testLinkShortcutReferenceLinkE() throws Exception
    {
        MarkdownDocument doc = open("TestSRImageE.md");
        checkInline(((ParagraphBlock)doc.get(0)).get(0), "!");
        checkLinkTextBlock(((ParagraphBlock)doc.get(0)).get(1), "foo", "/url", "title");
    }


    /// <summary>
    /// Tests simple Image Definition.
    /// </summary>
    @Test
    public void testImageReferenceDefinitionA() throws Exception
    {
        MarkdownDocument doc = open("TestRefImageA.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("REF1"), "/uri1 \"title1\"");
        checkLinkDefinition(doc.getLinkDefinitions().get("REF2"), "/uri2 \"title2\"");
        checkLinkDefinition(doc.getLinkDefinitions().get("REF3"), "/uri3 \"title3\"");
        checkLinkDefinition(doc.getLinkDefinitions().get("REF4"), "/uri4 \"title4\"");
    }

    /// <summary>
    /// Tests Image Definition with duplicate references.
    /// </summary>
    @Test
    public void testImageReferenceDefinitionB() throws Exception
    {
        MarkdownDocument doc = open("TestRefImageB.md");
        checkLinkDefinition(doc.getLinkDefinitions().get("FOO"), "/first");
    }


    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\ReferenceImages\\{0}", fileName));
    }
}

