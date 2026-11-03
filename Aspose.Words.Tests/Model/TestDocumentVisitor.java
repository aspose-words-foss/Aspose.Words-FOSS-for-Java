// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.HashMap;
import com.aspose.DictionaryExtension;
import java.util.Map;


/// <summary>
/// This really belongs to examples.
/// </summary>
@Test
public class TestDocumentVisitor
{
    @Test
    public void testExtractNormal() throws Exception
    {
        TestUtil.setUpTests();
        Document doc = TestUtil.open("Model\\Nodes\\TestDocumentVisitor.docx");

        NormalExtractingVisitor visitor = new NormalExtractingVisitor();
        doc.accept(visitor);

        msAssert.areEqual("Normal line 1.\r" +
                "Normal line 2.\r" +
                "\f", visitor.getExtractedText());
    }


    private static void checkTotalNodesCount(Document doc, TestNodeCountingVisitor nodeCounter, /*NodeType*/int nodeType)
    {
        int expectedNodeCount = doc.getChildNodes(nodeType, true).getCount();
        msAssert.areEqual(expectedNodeCount, nodeCounter.getVisitedNodesCounter(nodeType));
    }
}

/// <summary>
/// A sample class that shows how to implement DocumentVisitor
/// to extract text from all paragraphs of Normal style.
/// </summary>
class NormalExtractingVisitor extends DocumentVisitor
{
    public /*override*/ /*VisitorAction*/int visitDocumentStart(Document doc)
    {
        extractedText = new StringBuilder();
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphStart(Paragraph para)
    {
        if ("Normal".equals(para.getParagraphFormat().getStyleName()))
            msStringBuilder.append(extractedText, para.getText());
        return VisitorAction.CONTINUE;
    }

    public String getExtractedText()
    {
        return extractedText.toString();
    }

    private StringBuilder extractedText;
}

/// <summary>
/// Counts visits to the nodes of different types.
/// </summary>
class TestNodeCountingVisitor extends DocumentVisitor
{
    public /*override*/ /*VisitorAction*/int visitFieldStart(FieldStart fieldChar)
    {
        incrementCounter(fieldChar.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldSeparator(FieldSeparator fieldChar)
    {
        incrementCounter(fieldChar.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitFieldEnd(FieldEnd fieldChar)
    {
        incrementCounter(fieldChar.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitRun(Run run)
    {
        incrementCounter(run.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitParagraphEnd(Paragraph paragraph)
    {
        incrementCounter(paragraph.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public /*override*/ /*VisitorAction*/int visitCellEnd(Cell cell)
    {
        incrementCounter(cell.getNodeType());
        return VisitorAction.CONTINUE;
    }

    public int getVisitedNodesCounter(/*NodeType*/int nodeType)
    {
        return getNodeTypeCounter(mNodeTypeCounters, nodeType);
    }

    static int getNodeTypeCounter(HashMap<Integer, Integer> nodeTypeCounters, /*NodeType*/int nodeType)
    {
        return DictionaryExtension.getValueOrDefault(nodeTypeCounters, nodeType, 0);
    }

    private void incrementCounter(/*NodeType*/int nodeType)
    {
        incrementCounter(mNodeTypeCounters, nodeType);
    }

    private static void incrementCounter(Map<Integer, Integer> nodeTypeCounters, /*NodeType*/int nodeType)
    {
        int counter = DictionaryExtension.getValueOrDefault(nodeTypeCounters, nodeType, 0);
        nodeTypeCounters.put(nodeType, counter + 1);
    }

    private /*final*/ HashMap<Integer, Integer> mNodeTypeCounters = new HashMap<Integer, Integer>();
}


