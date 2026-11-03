// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/10/2013 by Evgeny Ivanov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


/// <summary>
/// Tests for <see cref="ExpressionParser"/>
/// </summary>
@Test
public class ExpressionParserTest extends TestFieldsBase
{
    /// <summary>
    /// Digits should be parsed as DoubleConstant
    /// </summary>
    @Test
    public void doubleConstantParseTest() throws Exception
    {
        // Arrange
        Field fieldStub = new FieldUnknown();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // Act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "3", new FieldExpressionParserBehavior());

        // Assert
        msAssert.areEqual(1, executionQueue.getCount());

        IExecutionItem executionItem = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, executionItem);
        msAssert.areEqual(3, ((DoubleConstant)executionItem).getValueDouble());
    }

    /// <summary>
    /// Addition expression should be parsed as 3 execution items: 2 double constants and 1 addition operation
    /// </summary>
    @Test
    public void additionOperationParseTest() throws Exception
    {
        // Arrange
        Field fieldStub = new FieldUnknown();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // Act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "3 + 5", new FieldExpressionParserBehavior());

        // Assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem executionItem = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, executionItem);
        msAssert.areEqual(3, ((DoubleConstant)executionItem).getValueDouble());

        IExecutionItem executionItem2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, executionItem2);
        msAssert.areEqual(5, ((DoubleConstant)executionItem2).getValueDouble());

        IExecutionItem executionItem3 = executionQueue.dequeue();
        msAssert.isInstanceOf(AdditionOperator.class, executionItem3);
    }

    /// <summary>
    /// Equality expression, containing bookmark reference, should be parsed correctly
    /// </summary>
    @Test
    public void equalityOperatorWithBookmarkReferenceTest() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        builder.startBookmark("TEST_BM");
        builder.endBookmark("TEST_BM");

        Field field = builder.insertField(FieldType.FIELD_IF, true);
        FieldContext fieldContext = new FieldContext(field);

        // Act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "TEST_BM = \"TEST\"", new FieldExpressionParserBehavior());

        // Assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem executionItem = executionQueue.dequeue();
        msAssert.isInstanceOf(BookmarkReference.class, executionItem);

        IExecutionItem executionItem2 = executionQueue.dequeue();
        msAssert.isInstanceOf(StringConstant.class, executionItem2);
        msAssert.areEqual("\"TEST\"", ((StringConstant)executionItem2).getValueString());

        IExecutionItem executionItem3 = executionQueue.dequeue();
        msAssert.isInstanceOf(EqualityOperator.class, executionItem3);
    }

    /// <summary>
    /// Equality expression, containing bookmark reference, should be parsed correctly
    /// </summary>
    @Test
    public void equalityOperatorWithBookmarkReferenceInOperand2Test() throws Exception
    {
        // Arrange
        DocumentBuilder builder = new DocumentBuilder();
        builder.startBookmark("TEST_BM");
        builder.endBookmark("TEST_BM");
        Field field = builder.insertField(FieldType.FIELD_IF, true);
        FieldContext fieldContext = new FieldContext(field);

        // Act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "\"TEST\" = TEST_BM", new FieldExpressionParserBehavior());

        // Assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem executionItem = executionQueue.dequeue();
        msAssert.isInstanceOf(StringConstant.class, executionItem);
        msAssert.areEqual("\"TEST\"", ((StringConstant)executionItem).getValueString());

        IExecutionItem executionItem2 = executionQueue.dequeue();
        msAssert.isInstanceOf(BookmarkReference.class, executionItem2);

        IExecutionItem executionItem3 = executionQueue.dequeue();
        msAssert.isInstanceOf(EqualityOperator.class, executionItem3);
    }

    @Test
    public void spacingShouldSepareteOperandsTestCase1() throws Exception
    {
        // arrange
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "=123 6/2", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(4, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(123, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(6, ((Constant)item2).getValueDouble());

        IExecutionItem item3 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item3);
        msAssert.areEqual(2, ((Constant)item3).getValueDouble());

        IExecutionItem item4 = executionQueue.dequeue();
        msAssert.isInstanceOf(DivisionOperator.class, item4);
    }

    @Test
    public void spacingShouldSepareteOperandsTestCase2() throws Exception
    {
        // arrange
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "=123  321", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(2, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(123, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(321, ((Constant)item2).getValueDouble());
    }

    @Test
    public void spacingShouldSepareteOperandsTestCase3() throws Exception
    {
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "= 1 2 ", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(2, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(1, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(2, ((Constant)item2).getValueDouble());
    }

    @Test
    public void normalExpressionTestCase1() throws Exception
    {
        // arrange
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "=123 + 6 / 2", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(5, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(123, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(6, ((Constant)item2).getValueDouble());

        IExecutionItem item3 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item3);
        msAssert.areEqual(2, ((Constant)item3).getValueDouble());

        IExecutionItem item4 = executionQueue.dequeue();
        msAssert.isInstanceOf(DivisionOperator.class, item4);

        IExecutionItem item5 = executionQueue.dequeue();
        msAssert.isInstanceOf(AdditionOperator.class, item5);
    }

    @Test
    public void normalExpressionTestCase2() throws Exception
    {
        // arrange
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "=123 + 321", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(123, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(321, ((Constant)item2).getValueDouble());

        IExecutionItem item3 = executionQueue.dequeue();
        msAssert.isInstanceOf(AdditionOperator.class, item3);
    }

    @Test
    public void normalExpressionTestCase3() throws Exception
    {
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "= 1 + 2", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(1, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(2, ((Constant)item2).getValueDouble());

        IExecutionItem item3 = executionQueue.dequeue();
        msAssert.isInstanceOf(AdditionOperator.class, item3);
    }

    @Test
    public void currencySymbolCanBeSeparetedFromOperandTestCase1() throws Exception
    {
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext, "= 1 + $ 2", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(3, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(1, ((Constant)item1).getValueDouble());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(2, ((Constant)item2).getValueDouble());
        msAssert.isTrue(((DoubleConstant)item2).isCurrency());

        IExecutionItem item3 = executionQueue.dequeue();
        msAssert.isInstanceOf(AdditionOperator.class, item3);
    }

    @Test
    public void currencySymbolCanBeSeparetedFromOperandTestCase2() throws Exception
    {
        Field fieldStub = new FieldFormula();
        FieldContext fieldContext = new FieldContext(fieldStub);

        // act
        ExecutionQueue executionQueue = ExpressionParser.parse(fieldContext,  "= 1 $ 2 ", new FieldExpressionParserBehavior());

        // assert
        msAssert.areEqual(2, executionQueue.getCount());

        IExecutionItem item1 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item1);
        msAssert.areEqual(1, ((Constant)item1).getValueDouble());
        msAssert.isTrue(((DoubleConstant)item1).isCurrency());

        IExecutionItem item2 = executionQueue.dequeue();
        msAssert.isInstanceOf(DoubleConstant.class, item2);
        msAssert.areEqual(2, ((Constant)item2).getValueDouble());
    }
}

