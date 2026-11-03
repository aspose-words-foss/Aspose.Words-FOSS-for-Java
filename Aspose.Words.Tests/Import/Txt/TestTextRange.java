// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/05/2025 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.TextRange;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// The class to test <see cref="TextRange"/>.
/// </summary>
@Test
public class TestTextRange
{
    @BeforeClass (alwaysRun = true)
    public void setUp()
    {
        TestUtil.setUpTests();
    }

    /// <summary>
    /// Tests <see cref="TextRange.Find"/>.
    /// Tests simple case with start, end and two required texts between them.
    /// </summary>
    @Test
    public void testFindA()
    {
        byte[] input = Encoding.getUTF8().getBytes("Lorem ipsum dolor sit amet");
        TextRange range = new TextRange("Lorem","amet", new String[]{"ipsum", "sit"});

        msAssert.isTrue(range.find(input, 0));

        msAssert.areEqual(0, range.getStartIndex());
        msAssert.areEqual(22, range.getEndIndex());
        msAssert.areEqual(26, range.getNextIndex());
    }

    /// <summary>
    /// Tests <see cref="TextRange.Find"/>.
    /// Tests that there can not be end text before required texts.
    /// </summary>
    @Test
    public void testFindB()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start req1 end req2 sit end");
        TextRange range = new TextRange("Start", "end", new String[] { "req1", "req2" });

        msAssert.isFalse(range.find(input, 0));
    }

    /// <summary>
    /// Tests <see cref="TextRange.Find"/>.
    /// Tests that end can be missing in the search array.
    /// </summary>
    @Test
    public void testFindC()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start req1 req2 sit");
        TextRange range = new TextRange("Start", "end", new String[] { "req1", "req2" });

        msAssert.isTrue(range.find(input, 0));
        msAssert.areEqual(0, range.getStartIndex());
        msAssert.areEqual(-1, range.getEndIndex());
        msAssert.areEqual(0, range.getNextIndex());
    }

    /// <summary>
    /// Tests <see cref="TextRange.Find"/>.
    /// Tests when search start index is greater than beginning of start text.
    /// </summary>
    @Test
    public void testFindD()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start req1 req2 sit end");
        TextRange range = new TextRange("Start", "end", new String[] { "req1", "req2" });

        msAssert.isFalse(range.find(input, 1));
    }

    /// <summary>
    /// Tests <see cref="TextRange.FindEnd"/>.
    /// Tests when end is located in other than start input array.
    /// </summary>
    @Test
    public void testFindEnd()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start req1 req2 sit");
        TextRange range = new TextRange("Start", "end", new String[] { "req1", "req2" });

        msAssert.isTrue(range.find(input, 0));
        msAssert.areEqual(0, range.getStartIndex());
        msAssert.areEqual(-1, range.getEndIndex());

        input = Encoding.getUTF8().getBytes("second buffer with end");
        msAssert.isTrue(range.findEnd(input, 0));
        msAssert.areEqual(0, range.getStartIndex());
        msAssert.areEqual(19, range.getEndIndex());
    }

    /// <summary>
    /// Tests <see cref="TextRange.IsBefore"/>.
    /// </summary>
    @Test
    public void testIsBeforeA()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start1 req1 Start2 req2 sit end");
        TextRange rangeA = new TextRange("Start1", "end", new String[] { "req1" });
        TextRange rangeB = new TextRange("Start2", "end", new String[] { "req2" });

        msAssert.isTrue(rangeA.find(input, 0));
        msAssert.isTrue(rangeB.find(input, 0));

        msAssert.isTrue(rangeA.isBefore(rangeB));
        msAssert.isFalse(rangeB.isBefore(rangeA));
    }

    /// <summary>
    /// Tests <see cref="TextRange.IsBefore"/>.
    /// Tests when other range is null.
    /// </summary>
    @Test
    public void testIsBeforeB()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start1 req1 Start2 req2 sit end");
        TextRange rangeA = new TextRange("Start1", "end", new String[] { "req1" });

        msAssert.isTrue(rangeA.find(input, 0));
        msAssert.isTrue(rangeA.isBefore(null));
    }

    /// <summary>
    /// Tests <see cref="TextRange.IsBefore"/>.
    /// Tests when other range is not found.
    /// </summary>
    @Test
    public void testIsBeforeC()
    {
        byte[] input = Encoding.getUTF8().getBytes("Start1 req1 Start2 req2 sit end");
        TextRange rangeA = new TextRange("Start1", "end", new String[] { "req1" });
        TextRange rangeB = new TextRange("No such text", "end", new String[] { "req1" });

        msAssert.isTrue(rangeA.find(input, 0));
        msAssert.isFalse(rangeB.find(input, 0));
        msAssert.isTrue(rangeA.isBefore(rangeB));
    }
}

