// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/04/2019 by Denis Panov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.IO.MemoryStream;
import com.aspose.ms.System.IO.StreamWriter;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.ms.System.IO.SeekOrigin;
import com.aspose.CustomTextReader;


/// <summary>
/// The class for testing markdown feature detectors.
/// </summary>
@Test
public class TestMarkdownFeatureDetectors
{
    /// <summary>
    /// Tests FencedCode detector.
    /// </summary>
    @Test
    public void testFencedCodeDetector() throws Exception
    {
        FencedCodeDetector detector = new FencedCodeDetector();

        String[] input = new String[]
        {
            "   ```\n",
            "These lines is",
            "fenced code Block text...\n",
            "```\n",
            "It's simple text\n",
            "   \n",
            "    Indented code is not considered at all\n",
            "``InlineCode spans`` should be detected by EmphasisDetector, so it is not accounted here."
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(1, count);
    }

    /// <summary>
    /// Tests Emphasis detector.
    /// </summary>
    @Test
    public void testEmphasisDetector() throws Exception
    {
        EmphasisDetector detector = new EmphasisDetector();
        String[] input = new String[]
        {
            "It's bold text: **type 1**, __type 2__.",
            "It's bold & italic text: ***type 1***, ___type 2___.",
            "It's ~~strikethrough~~ text",
            "``InlineCodeSpan``\n",
            "Mu`lti line\n",
            "code sp`an",
        };
        int count = executeDetector(detector, input);
        msAssert.areEqual(9, count);
    }
    
    /// <summary>
    /// Tests Heading detector.
    /// </summary>
    @Test
    public void testHeadingDetector() throws Exception
    {
        HeadingDetector detector = new HeadingDetector();
        String[] input = new String[]
        {
            "# Heading 1\n",
            "## Heading 2\n",
            "### Heading 3\n",
            "#### Heading 4\n",
            "##### Heading 5\n",
            "###### Heading 6\n",
            "\n",
            "Heading 7\n",
            "--------\n",
            "Heading 8\n",
            "========\n",
            "\n",
            "> # Heading 9\n",
            "       ######## NotHeading\n",
            "\n",
            "heading1\n",
            "=======\n",
            "\n",
            "=======\n",
            " # heading2\n",
            "======="
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(11, count);
    }

    /// <summary>
    /// Tests Heading detector inside a Quote.
    /// </summary>
    @Test
    public void testHeadingDetectorInQuote() throws Exception
    {
        HeadingDetector detector = new HeadingDetector();
        String[] input = new String[]
        {
            " > # Heading 1\n", 
            " >> ## Heading 2\n",
            "> ### Heading 3\n",
            "       ######## Not Heading",
            "SetextHeading\n",
            " ----------------"
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(4, count);
    }

    /// <summary>
    /// Tests Horizontal rule detector.
    /// </summary>
    @Test
    public void testHorizontalRuleDetector() throws Exception
    {
        HorizontalRuleDetector detector = new HorizontalRuleDetector();
        String[] input = new String[]
        {
            "***\n",
            "---\n",
            "___\n",
            "- - - - - - -\n"
        };
        int count = executeDetector(detector, input);
        msAssert.areEqual(4, count);
    }


    /// <summary>
    /// Tests Link detector.
    /// </summary>
    @Test
    public void testLinkDetector() throws Exception
    {
        LinkDetector detector = new LinkDetector();
        String[] input = new String[]
        {
            "[www.aspose.com](http://www.aspose.com)",
            "[link text](url)",
            "![image](url)",
            "[not link]  (url)",
            "[reference-style link][1]",
            "[1]:<url>"
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(4, count);
    }

    /// <summary>
    /// Tests Link detector for full type links.
    /// </summary>
    @Test
    public void testFullLinkDetector() throws Exception
    {
        LinkDetector detector = new LinkDetector();
        String[] input = new String[]
        {
            "[reference-style link1][1]",
            "[reference-style link2][1]",
            "[reference-style link3][1]",
            "[1]:<url>"
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(3, count);
    }

    /// <summary>
    /// Tests this is not a Link detector because there is no link definition for links.
    /// </summary>
    @Test
    public void testNotLink() throws Exception
    {
        LinkDetector detector = new LinkDetector();
        String[] input = new String[]
        {
            "[reference-style link1][1]",
            "[reference-style link2][1]",
            "[reference-style link3][1]"
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(0, count);
    }


    /// <summary>
    /// Tests List detector.
    /// </summary>
    @Test
    public void testListDetector() throws Exception
    {
        ListDetector detector = new ListDetector();
        String[] input = new String[]
        {
            "List 1:\n",
            "1. Item 1\n",
            "  1. Item 2\n",
            "  9. Item 3\n",
            "List 2:\n",
            "- Item 1\n",
            "  - Item 1\n",
            "List 3:\n",
            "+ Item 1\n",
            "  + Item 1\n",
            "List 4:\n",
            "* Item 1\n",
            "  * Item 1\n"
        };

        int count = executeDetector(detector, input);
        msAssert.areEqual(4, count);
    }

    /// <summary>
    /// Tests Quote detector.
    /// </summary>
    @Test
    public void testQuoteDetector() throws Exception
    {
        QuoteDetector detector = new QuoteDetector();
        String[] input = new String[]
        {
            "> Quote 1 line 1\n",
            "> Quote 1 line 2\n",
            "\n",
            ">>> Quote 2\n",
            "\n",
            ">Quote 3\n"
        };
        int count = executeDetector(detector, input);
        msAssert.areEqual(3, count);
    }

    /// <summary>
    /// Executes a specified detector over a specified input data.
    /// </summary>
    /// <returns>A number of the detected markdown features.</returns>
    private static int executeDetector(MarkdownDetectorBase detector, String[] inputData) throws Exception
    {
        int count = 0;
        MemoryStream stream = new MemoryStream();
        try /*JAVA: was using*/
        {
            StreamWriter writer = new StreamWriter(stream, Encoding.getUTF8());
            for (String s : inputData)
                writer.write(s);

            writer.flush();
            
            stream.seek(0, SeekOrigin.BEGIN);
            
            MarkdownDetectorContext context = new MarkdownDetectorContext(new CustomTextReader(stream));
            while (context.readLine())
            {
                if (detector.detectAndUpdateContext(context))
                    count += detector.getCount();
            }

            writer.close();
        }
        finally { if (stream != null) stream.close(); }

        return count;
    }
}

