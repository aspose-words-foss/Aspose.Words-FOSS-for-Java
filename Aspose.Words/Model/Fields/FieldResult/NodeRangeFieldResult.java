// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// A field result represented by a node range.
/// </summary>
class NodeRangeFieldResult implements IFieldResult
{
    NodeRangeFieldResult(NodeRange nodeRange)
    {
        mNodeRange = nodeRange;
    }

    public Constant /*IFieldResult.*/getFieldResultValue()
    {
        return new StringConstant(NodeTextCollector.getText(mNodeRange, true));
    }

    public NodeRange /*IFieldResult.*/getFieldResultRange()
    {
        return mNodeRange;
    }

    private /*final*/ NodeRange mNodeRange;
}

