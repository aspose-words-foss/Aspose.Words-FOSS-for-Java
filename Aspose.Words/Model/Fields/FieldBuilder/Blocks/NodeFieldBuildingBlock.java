// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


class NodeFieldBuildingBlock implements IFieldBuildingBlock
{
    NodeFieldBuildingBlock(Node node)
    {
        mNode = node;
    }

    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder)
    {
        documentBuilder.insertNode(mNode);
    }

    private /*final*/ Node mNode;
}
