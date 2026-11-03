// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2020 by Mikhail Nepreteamov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


/// <summary>
/// Tests parsing of markdown Image feature.
/// </summary>
public class TestImageParser extends TestMarkdownParserBase
{

    /// <summary>
    /// Tests Image with a title.
    /// </summary>
    @Test
    public void testImageB() throws Exception
    {
        MarkdownDocument doc = open("TestImageB.md");
        msAssert.areEqual(2, doc.getCount());

        ParagraphBlock paragraphBlock = (ParagraphBlock)doc.get(0);
        checkParagraph(paragraphBlock, "alt text/uri \"title\"");
        checkImageBlock(paragraphBlock.get(0), "alt text", "/uri", "title");

        paragraphBlock = (ParagraphBlock)doc.get(1);
        checkParagraph(paragraphBlock, "My foo bar/path/to/train.jpg  \"title\"   ");
        msAssert.areEqual("My ", paragraphBlock.get(0).getText());
        checkImageBlock(paragraphBlock.get(1), "foo bar", "/path/to/train.jpg", "title");
    }

    /// <summary>
    /// Tests that it is recommended that in rendering, only the plain string content of the Image description
    /// be used, without formatting.
    /// </summary>
    @Test
    public void testImageC() throws Exception
    {
        MarkdownDocument doc = open("TestImageC.md");
        msAssert.areEqual(2, doc.getCount());

        ParagraphBlock paragraphBlock = (ParagraphBlock)doc.get(0);
        checkParagraph(paragraphBlock, "foo bar/url2");
        checkImageBlock(paragraphBlock.get(0), "foo bar", "/url2");

        paragraphBlock = (ParagraphBlock)doc.get(1);
        checkParagraph(paragraphBlock, "foo bar/url2");
        checkImageBlock(paragraphBlock.get(0), "foo bar", "/url2");
    }


    /// <summary>
    /// Tests Image with empty description.
    /// </summary>
    @Test
    public void testImageE() throws Exception
    {
        MarkdownDocument doc = open("TestImageE.md");
        msAssert.areEqual(1, doc.getCount());

        ParagraphBlock paragraphBlock = (ParagraphBlock)doc.get(0);
        checkParagraph(paragraphBlock, "/url");
        checkImageBlock(paragraphBlock.get(0), "", "/url");
    }

    /// <summary>
    /// Returns markdown document read from a specified path.
    /// </summary>
    /*override*/ MarkdownDocument open(String fileName) throws Exception
    {
        return super.open(msString.format("ImportMarkdown\\Inlines\\Image\\{0}", fileName));
    }
}

