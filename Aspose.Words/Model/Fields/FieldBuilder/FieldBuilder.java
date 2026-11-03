// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/01/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.FormatterPal;
import com.aspose.StringUtil;


/// <summary>
/// Builds a field from field code tokens (arguments and switches).
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public class FieldBuilder implements IFieldBuildingBlock
{
    /// <summary>
    /// Initializes an instance of the <see cref="FieldBuilder"/> class.
    /// </summary>
    /// <param name="fieldType">The type of the field to build.</param>
    public FieldBuilder(/*FieldType*/int fieldType)
    {
        String fieldCode = FieldUtil.fetchFieldCodeSafe(fieldType);
        if (fieldCode == null)
            throw new IllegalArgumentException(msString.format("Field type '{0}' is not supported.", FieldUtil.fieldTypeToString(fieldType)));

        mStart = new FieldStartFieldBuildingBlock(fieldType);
        mEnd = new FieldEndFieldBuildingBlock(fieldType);

        mBuildingBlocks = new ArrayList<IFieldBuildingBlock>();
        msArrayList.add(mBuildingBlocks, mStart);
        msArrayList.add(mBuildingBlocks, DelimiterFieldBuildingBlock.INSTANCE);
        msArrayList.add(mBuildingBlocks, new TextFieldBuildingBlock(fieldCode));
        msArrayList.add(mBuildingBlocks, DelimiterFieldBuildingBlock.INSTANCE);
        msArrayList.add(mBuildingBlocks, mEnd);
    }

    /// <summary>
    /// Adds a field's argument.
    /// </summary>
    /// <param name="argument">The argument value.</param>
    public FieldBuilder addArgument(String argument)
    {
        String encodedArgument = FieldCodeUpdater.encodeArgument(argument);

        appendBuildingBlockWithDelimiter(new TextFieldBuildingBlock(encodedArgument));

        return this;
    }

    /// <summary>
    /// Adds a field's argument.
    /// </summary>
    /// <param name="argument">The argument value.</param>
    public FieldBuilder addArgument(int argument)
    {
        return addArgument(FormatterPal.intToStrCurrentCulture(argument));
    }

    /// <summary>
    /// Adds a field's argument.
    /// </summary>
    /// <param name="argument">The argument value.</param>
    public FieldBuilder addArgument(double argument)
    {
        return addArgument(FormatterPal.doubleToStrCurrentCulture(argument));
    }

    /// <summary>
    /// Adds a child field represented by another <see cref="FieldBuilder"/> to the field's code.
    /// </summary>
    /// <remarks>
    /// This overload is used when the argument consists of a single child field.
    /// </remarks>
    public FieldBuilder addArgument(FieldBuilder argument)
    {
        appendBuildingBlockWithDelimiter(argument);

        return this;
    }

    /// <summary>
    /// Adds a field's argument represented by <see cref="FieldArgumentBuilder"/> to the field's code.
    /// </summary>
    /// <remarks>
    /// This overload is used when the argument consists of a mixture of different parts such as child fields, nodes, and plain text.
    /// </remarks>
    public FieldBuilder addArgument(FieldArgumentBuilder argument)
    {
        appendBuildingBlockWithQuotesAndDelimiter(argument);

        return this;
    }

    /// <summary>
    /// Adds a field's switch.
    /// </summary>
    /// <remarks>
    /// This overload adds a flag (switch without argument).
    /// </remarks>
    /// <param name="switchName">The switch name.</param>
    public FieldBuilder addSwitch(String switchName)
    {
        return addSwitch(switchName, null);
    }

    /// <summary>
    /// Adds a field's switch.
    /// </summary>
    /// <param name="switchName">The switch name.</param>
    /// <param name="switchArgument">The switch value.</param>
    public FieldBuilder addSwitch(String switchName, String switchArgument)
    {
        final String BACKSLASH = "\\";

        String switchStr = StringUtil.startsWithOrdinalIgnoreCase(switchName, BACKSLASH)
                             ? switchName
                             : BACKSLASH + switchName;

        appendBuildingBlockWithDelimiter(new TextFieldBuildingBlock(switchStr));

        if (StringUtil.hasChars(switchArgument))
            addArgument(switchArgument);

        return this;
    }

    /// <summary>
    /// Adds a field's switch.
    /// </summary>
    /// <param name="switchName">The switch name.</param>
    /// <param name="switchArgument">The switch value.</param>
    public FieldBuilder addSwitch(String switchName, int switchArgument)
    {
        return addSwitch(switchName, FormatterPal.intToStrCurrentCulture(switchArgument));
    }

    /// <summary>
    /// Adds a field's switch.
    /// </summary>
    /// <param name="switchName">The switch name.</param>
    /// <param name="switchArgument">The switch value.</param>
    public FieldBuilder addSwitch(String switchName, double switchArgument)
    {
        return addSwitch(switchName, FormatterPal.doubleToStrCurrentCulture(switchArgument));
    }

    /// <summary>
    /// Builds and inserts a field into the document before the specified inline node.
    /// </summary>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field buildAndInsert(Inline refNode) throws Exception
    {
        return buildAndInsertInternal(refNode);
    }

    /// <summary>
    /// Builds and inserts a field into the document to the end of the specified paragraph.
    /// </summary>
    /// <returns>A <see cref="Field"/> object that represents the inserted field.</returns>
    public Field buildAndInsert(Paragraph refNode) throws Exception
    {
        return buildAndInsertInternal(refNode);
    }

    private Field buildAndInsertInternal(Node refNode) throws Exception
    {
        DocumentBuilder documentBuilder = new DocumentBuilder(refNode.fetchDocument());
        documentBuilder.moveTo(refNode);

        ((IFieldBuildingBlock)this).buildBlock(documentBuilder);

        return FieldFactory.createField(mStart.getFieldStart(), mEnd.getFieldSeparator(), mEnd.getFieldEnd());
    }

    @ReservedForInternalUse @Deprecated
    public void /*IFieldBuildingBlock.*/buildBlock(DocumentBuilder documentBuilder) throws Exception
    {
        for (IFieldBuildingBlock buildingBlock : mBuildingBlocks)
            buildingBlock.buildBlock(documentBuilder);
    }

    private void appendBuildingBlock(IFieldBuildingBlock buildingBlock)
    {
        mBuildingBlocks.add(mBuildingBlocks.size() - 1, buildingBlock);
    }

    private void appendBuildingBlockWithDelimiter(IFieldBuildingBlock buildingBlock)
    {
        appendBuildingBlock(buildingBlock);
        appendBuildingBlock(DelimiterFieldBuildingBlock.INSTANCE);
    }

    private void appendBuildingBlockWithQuotesAndDelimiter(IFieldBuildingBlock buildingBlock)
    {
        appendBuildingBlock(QuoteFieldBuildingBlock.INSTANCE);
        appendBuildingBlock(buildingBlock);
        appendBuildingBlock(QuoteFieldBuildingBlock.INSTANCE);
        appendBuildingBlock(DelimiterFieldBuildingBlock.INSTANCE);
    }

    private /*final*/ ArrayList<IFieldBuildingBlock> mBuildingBlocks;
    private /*final*/ FieldStartFieldBuildingBlock mStart;
    private /*final*/ FieldEndFieldBuildingBlock mEnd;
}

