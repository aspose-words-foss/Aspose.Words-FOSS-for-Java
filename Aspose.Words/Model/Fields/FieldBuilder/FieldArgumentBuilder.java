// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Builds a complex field argument consisting of fields, nodes, and plain text.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldArgumentBuilder implements IFieldBuildingBlock
{
    /// <summary>
    /// Initializes an instance of the <see cref="FieldArgumentBuilder"/> class.
    /// </summary>
    public FieldArgumentBuilder()
    {
        mBuildingBlocks = new ArrayList<IFieldBuildingBlock>();
    }

    /// <summary>
    /// Adds a plain text to the argument.
    /// </summary>
    public FieldArgumentBuilder addText(String text)
    {
        msArrayList.add(mBuildingBlocks, new TextFieldBuildingBlock(text));

        return this;
    }

    /// <summary>
    /// Adds a node to the argument.
    /// </summary>
    /// <remarks>
    /// Only text level nodes are supported at the moment.
    /// </remarks>
    public FieldArgumentBuilder addNode(Inline node)
    {
        msArrayList.add(mBuildingBlocks, new NodeFieldBuildingBlock(node));

        return this;
    }

    /// <summary>
    /// Adds a field represented by a <see cref="FieldBuilder"/> to the argument.
    /// </summary>
    public FieldArgumentBuilder addField(FieldBuilder fieldBuilder)
    {
        msArrayList.add(mBuildingBlocks, fieldBuilder);

        return this;
    }

    @ReservedForInternalUse @Deprecated
    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder) throws Exception
    {
        for (IFieldBuildingBlock buildingBlock : mBuildingBlocks)
            buildingBlock.buildBlock(documentBuilder);
    }

    private /*final*/ ArrayList<IFieldBuildingBlock> mBuildingBlocks;
}

