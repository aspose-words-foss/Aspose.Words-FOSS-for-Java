// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.Text.msStringBuilder;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msString;


public class FieldNodeRangeAssert
{
    public FieldNodeRangeAssert(Field field, FieldAssert fieldAssert)
    {
        mFieldAssert = fieldAssert;
        NodeRange range = new NodeRange(field.getStart(), field.getEnd());
        mNodeEnumerator = new NodeEnumerator(range);
    }

    public FieldNodeRangeAssert start()
    {
        moveToNextNodeAndAssert(NodeType.FIELD_START);
        return this;
    }

    public FieldNodeRangeAssert separator()
    {
        moveToNextNodeAndAssert(NodeType.FIELD_SEPARATOR);
        return this;
    }

    public FieldNodeRangeAssert end()
    {
        moveToNextNodeAndAssert(NodeType.FIELD_END);
        return this;
    }

    public FieldNodeRangeAssert text(String expectedText)
    {
        StringBuilder actualText = new StringBuilder();
        while (actualText.length() < expectedText.length())
        {
            Run run = ms.as(moveToNextNode(), Run.class);
            if (run == null)
                break;

            msStringBuilder.append(actualText, run.getText());
        }

        msAssert.areEqual(expectedText, actualText.toString());

        return this;
    }

    public FieldNodeRangeAssert paragraphBreak()
    {
        moveToNextNodeAndAssert(NodeType.PARAGRAPH);
        return this;
    }

    public FieldAssert andNoMore()
    {
        boolean nextNodeExists = mNodeEnumerator.moveToNextNode();

        if (nextNodeExists)
            Assert.fail(msString.format("Field contains extra node {0}", mNodeEnumerator.getCurrentNode()));

        return mFieldAssert;
    }

    private void moveToNextNodeAndAssert(/*NodeType*/int expectedNodetype)
    {
        msAssert.isTrue(mNodeEnumerator.moveToNextNode(), "No more nodes in the field");
        msAssert.areEqual(expectedNodetype, mNodeEnumerator.getCurrentNode().getNodeType());
    }

    private Node moveToNextNode()
    {
        if (!mNodeEnumerator.moveToNextNode())
            return null;

        return mNodeEnumerator.getCurrentNode();
    }

    private /*final*/ FieldAssert mFieldAssert;
    private /*final*/ NodeEnumerator mNodeEnumerator;
}
