// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 03/04/2015 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import java.util.ArrayList;


/// <summary>
/// Test that node lists and enumeration works.
/// </summary>
@Test
public class TestNodeEnumerator
{
    /// <summary>
    /// Test moving in a single run.
    /// </summary>
    /// <remarks>
    /// The end postion of the range is interesting. Offset n means that symbol at offset n is also included.
    /// But offset string.Length is legal as well. So offset String.Length - 1 and String.Length mean the same.
    /// Weird.
    /// </remarks>
    @Test
    public void testSingleRun() throws Exception
    {
        Run testRun = TestNodeRange.getTestRun(0);
        String testText = testRun.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");

        testSingleRunBody(0, 1, "T");
        testSingleRunBody(0, testText.length() - 1, "Tex");
        testSingleRunBody(0, testText.length(), "Text");
        testSingleRunBody(1, testText.length() - 1, "ex");
        
        testSingleRunBody(1, testText.length(), "ext");

        testSingleRunBody(testText.length() - 1, testText.length(), "t");
    }

    private static void testSingleRunBody(int startOffset, int endOffset, String expectedString) throws Exception
    {
        Run run = TestNodeRange.getTestRun(0);
        NodeEnumerator enumerator = makeTestEnumerator(run, startOffset, run, endOffset);

        String context = TestNodeRange.makeSingleRunContext(expectedString, startOffset, endOffset);

        msAssert.isTrue(enumerator.moveToNextNode(), context);

        Run extracted = (Run)enumerator.extractCurrentNode(null, NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL);
        msAssert.areEqual(expectedString, extracted.getText(), context);
    }

    private static NodeEnumerator makeTestEnumerator(Run run0, int startOffset, Run run1, int endOffset)
    {
        NodeRange range = TestNodeRange.makeRangeFromRuns(run0, startOffset, run1, endOffset);
        return new NodeEnumerator(range);
    }

    @Test
    public void testFailOnFirstMove()
    {
        Run testRun = TestNodeRange.getTestRun(0);
        String testText = testRun.getText();

        // These should fail on the first move.
        testFailOnFirstMoveBody(0, 0);
        testFailOnFirstMoveBody(1, 1);

        testFailOnFirstMoveBody(1, 0);
        testFailOnFirstMoveBody(testText.length() - 1, testText.length() - 1);
        testFailOnFirstMoveBody(testText.length(), testText.length());
        testFailOnFirstMoveBody(testText.length(), testText.length() + 1);
    }

    private static void testFailOnFirstMoveBody(int startOffset, int endOffset)
    {
        Run testRun = TestNodeRange.getTestRun(0);
        NodeEnumerator enumerator = makeTestEnumerator(testRun, startOffset, testRun, endOffset);

        String context = TestNodeRange.makeSingleRunContext("", startOffset, endOffset);

        msAssert.isFalse(enumerator.moveToNextNode(), context);
    }

    @Test
    public void test2Runs() throws Exception
    {
        Run run0 = TestNodeRange.getTestRun(0);
        Run run1 = TestNodeRange.getTestRun(1);
        String testText = run0.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");
        msAssert.areEqual(run1.getText(), "Text", "Unexpected test run text");

        test2RunsBody(0, 1, "Text", "T");
        test2RunsBody(0, testText.length() - 1, "Text", "Tex");
        test2RunsBody(0, testText.length(), "Text", "Text");
        test2RunsBody(1, testText.length() - 1, "ext", "Tex");
        test2RunsBody(1, testText.length(), "ext", "Text");
        test2RunsBody(1, 1, "ext", "T");
        test2RunsBody(testText.length() - 1, 1, "t", "T");

    }

    // Actually, this behavior causes issue 26152 as FieldCodeParser makes such ranges and cosiders them valid.
    // It cannot be fixed easily however, because there are tests already relying on this behaviour.
    @Test
    public void testSecondMoveFailure() throws Exception
    {
        Run run0 = TestNodeRange.getTestRun(0);
        Run run1 = TestNodeRange.getTestRun(1);
        String testText = run0.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");
        msAssert.areEqual(run1.getText(), "Text", "Unexpected test run text");

        // Should fail on the second move:
        test2RunsFailOnTheSecondMoveBody(0, 0, "Text");
        test2RunsFailOnTheSecondMoveBody(testText.length() - 1, 0, "t");
        // These ones move right to the "Text" in the second run on the first move.
        test2RunsFailOnTheSecondMoveBody(testText.length(), 2, "Te");
        test3RunsFailOnTheSecondMoveBody(testText.length(), 0, "Text");
    }

    private static void test2RunsBody(int startOffset, int endOffset, String expectedText0, String expectedText1) throws Exception
    {
        test2RunsBody(startOffset, endOffset, expectedText0, expectedText1, 1);
    }

    private static void test3RunsBody(int startOffset, int endOffset, String expectedText0, String expectedText1) throws Exception
    {
        test2RunsBody(startOffset, endOffset, expectedText0, expectedText1, 2);
    }

    private static void test2RunsBody(int startOffset, int endOffset, String expectedText0, String expectedText1, int run1Index) throws Exception
    {
        Run run0 = TestNodeRange.getTestRun(0);
        Run run1 = TestNodeRange.getTestRun(run1Index);
        NodeEnumerator enumerator = makeTestEnumerator(run0, startOffset, run1, endOffset);

        String context = TestNodeRange.makeDualRunContext(expectedText0, startOffset, expectedText1, endOffset);

        msAssert.isTrue(enumerator.moveToNextNode(), "First move failed. " + context);
        Run extracted = (Run)enumerator.extractCurrentNode(null, NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL);
        msAssert.areEqual(expectedText0, extracted.getText(), context);

        msAssert.isTrue(enumerator.moveToNextNode(), "Second move failed. " + context);
        extracted = (Run)enumerator.extractCurrentNode(null, NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL);
        msAssert.areEqual(expectedText1, extracted.getText(), context);
    }

    private static void test2RunsFailOnTheSecondMoveBody(int startOffset, int endOffset, String expectedText) throws Exception
    {
        test2RunsFailOnTheSecondMoveBody(startOffset, endOffset, expectedText, 1);
    }

    private static void test3RunsFailOnTheSecondMoveBody(int startOffset, int endOffset, String expectedText) throws Exception
    {
        test2RunsFailOnTheSecondMoveBody(startOffset, endOffset, expectedText, 2);
    }

    private static void test2RunsFailOnTheSecondMoveBody(int startOffset, int endOffset, String expectedText, int run1Index) throws Exception
    {
        // TODO remove duplication
        Run run0 = TestNodeRange.getTestRun(0);
        Run run1 = TestNodeRange.getTestRun(run1Index);
        NodeEnumerator enumerator = makeTestEnumerator(run0, startOffset, run1, endOffset);

        String context = TestNodeRange.makeDualRunContext(expectedText, startOffset, "", endOffset);

        msAssert.isTrue(enumerator.moveToNextNode(), "First move failed. " + context);
        Run extracted = (Run)enumerator.extractCurrentNode(null, NodeExtractBehavior.MODIFY_CHILDREN_CLONE_ALL);
        msAssert.areEqual(expectedText, extracted.getText(), context);

        msAssert.isFalse(enumerator.moveToNextNode(), "Second move Not failed. " + context);
    }

    /// <summary>
    /// Just verifies a move to the middle of the second run.
    /// </summary>
    @Test
    public void test3Runs() throws Exception
    {
        Run run0 = TestNodeRange.getTestRun(0);
        Run run2 = TestNodeRange.getTestRun(2);
        String testText = run0.getText();
        msAssert.areEqual(testText, "Text", "Unexpected test run text");
        msAssert.areEqual(run2.getText(), "Text", "Unexpected test run text");

        test3RunsBody(0, 1, "Text", "Text");
        test3RunsBody(1, 1, "ext", "Text");
        test3RunsBody(testText.length() - 1, 1, "t", "Text");
        // This one moves right to the "Text" in the second run on the first move.
        test3RunsBody(testText.length(), 1, "Text", "T");
    }

    /// <summary>
    /// Relates to WORDSNET-27816.
    /// Tests NodeFinder work with an empty Body.
    /// </summary>
    @Test
    public void testNodeFinderWithEmptyBody() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE);
        doc.getFirstSection().getBody().getLastParagraph().remove();
        NodeRange nodeRange = new NodeRange(doc.getFirstSection(), true, doc.getFirstSection(), true);

        // Checks NullReferenceException here.
        ArrayList<Node> nodes = NodeFinder.findNodes(nodeRange, NodeType.PARAGRAPH);
        msAssert.areEqual(0, nodes.size());
    }

    /// <summary>
    /// Relates to WORDSNET-27816.
    /// Tests iteration over empty bodies.
    /// </summary>
    @Test
    public void testNodeEnumeratorOverEmptyBody() throws Exception
    {
        Document doc = new Document(DocumentCtorMode.BLANK_DOCUMENT_NODE);
        doc.getFirstSection().getBody().getLastParagraph().remove();
        doc.getSections().add(doc.getFirstSection().deepClone(true));

        NodeRange nodeRange = new NodeRange(doc.getFirstSection(), true, doc.getLastSection(), true);
        NodeEnumerator nodeEnumerator = new NodeEnumerator(nodeRange);

        // Checks iteration over empty bodies.
        nodeEnumerator.moveToNextNode();
        msAssert.areEqual(doc.getFirstSection(), nodeEnumerator.getCurrentNode());
        nodeEnumerator.moveToNextNode();
        msAssert.areEqual(doc.getLastSection(), nodeEnumerator.getCurrentNode());

        // Checks whether iterations have been completed correctly without causing void DocumentPosition.
        msAssert.isFalse(nodeEnumerator.moveToNextNode());
    }
}

