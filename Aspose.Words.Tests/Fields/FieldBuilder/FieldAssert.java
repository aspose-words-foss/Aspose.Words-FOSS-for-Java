// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


public class FieldAssert
{
    private FieldAssert(Field field)
    {
        msAssert.isNotNull(field, "field is null");
        mField = field;
    }

    public static FieldAssert field(Field field)
    {
        return new FieldAssert(field);
    }

    public FieldAssert ofType(/*FieldType*/int fieldType)
    {
        msAssert.areEqual(fieldType, mField.getType());

        return this;
    }

    public FieldAssert inside(CompositeNode parent)
    {
        return inside(parent, parent);
    }

    public FieldAssert inside(CompositeNode startParent, CompositeNode endParent)
    {
        msAssert.areSame(startParent, mField.getStart().getParentNode());
        msAssert.areSame(endParent, mField.getEnd().getParentNode());

        return this;
    }

    public FieldAssert between(Node afterNode, Node beforeNode)
    {
        after(afterNode);
        before(beforeNode);

        return this;
    }

    public FieldNodeRangeAssert contains()
    {
        return new FieldNodeRangeAssert(mField, this);
    }

    private void after(Node node)
    {
        msAssert.areSame(node, mField.getStart().getPreviousSibling());
    }

    private void before(Node node)
    {
        msAssert.areSame(node, mField.getEnd().getNextSibling());
    }

    private /*final*/ Field mField;
}

