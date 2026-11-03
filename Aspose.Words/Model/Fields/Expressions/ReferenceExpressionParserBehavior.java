// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Performs behavior of expression parser related to bookmark and cell references.
/// </summary>
class ReferenceExpressionParserBehavior implements IExpressionParserBehavior
{
    ReferenceExpressionParserBehavior(boolean allowLeadingStringConstants)
    {
        mAllowLeadingStringConstants = allowLeadingStringConstants;
    }

    boolean getExpressionHasLeadingMinusOperator() { return mExpressionHasLeadingMinusOperator; }; private void setExpressionHasLeadingMinusOperator(boolean value) { mExpressionHasLeadingMinusOperator = value; };

    private boolean mExpressionHasLeadingMinusOperator;

    public ArrayList<Object> /*IExpressionParserBehavior.*/normalizeExpression(ArrayList<Object> expression, FieldContext context)
    {
        if (!mAllowLeadingStringConstants)
        {
            if ((expression.size() > 0) && (expression.get(0) instanceof StringConstant))
                return null;
        }

        expression = replaceParentheses(expression);
        expression = removeOperators(expression);
        expression = removeStringConstants(expression);
        expression = addSumOperator(expression);

        setExpressionHasLeadingMinusOperator((expression.size() > 0) && (expression.get(0) instanceof UnaryMinusOperator));

        return expression;
    }

    public ExecutionQueue /*IExpressionParserBehavior.*/normalizeExecutionQueue(ExecutionQueue queue, FieldContext context, String expression)
    {
        return queue;
    }

    public boolean /*IExpressionParserBehavior.*/isDelimiter(char c, String expression, int position, StringBuilder builder)
    {
        char decimalSeparator = FormatterPal.getDecimalSeparatorCurrent();
        char groupSeparator = FormatterPal.getNumberGroupSeparatorCurrent();

        if ((c != groupSeparator) && (c != decimalSeparator))
            return false;

        return isDecimalSeparatorDelimiter(builder, decimalSeparator) || isGroupSeparatorDelimiter(c, expression, position, groupSeparator);
    }

    private static boolean isDecimalSeparatorDelimiter(StringBuilder builder, char decimalSeparator)
    {
        return builder.toString().contains(Character.toString(decimalSeparator));
    }

    private static boolean isGroupSeparatorDelimiter(char c, String expression, int position, char groupSeparator)
    {
        if (c != groupSeparator)
            return false;

        int groupSize = FormatterPal.getNumberGroupSizeCurrent();
        if (groupSize == 0)
            return false;

        if (position + groupSize >= expression.length())
            return true;

        for (int i = 0; i < groupSize; i++)
        {
            char nextChar = expression.charAt(position + i + 1);
            if (!Character.isDigit(nextChar))
                return true;
        }

        return false;
    }

    private static ArrayList<Object> replaceParentheses(ArrayList<Object> items)
    {
        if (items.size() < 3)
            return items;

        ArrayList<Object> result = new ArrayList<Object>(items.size());

        int i = 0;
        while (i < items.size())
        {
            Object item = items.get(i);

            if (isDoubleConstantBetweenParentheses(items, i))
            {
                result.set(result.size() - 1, new UnaryMinusOperator(true));
                i++;
            }

            msArrayList.add(result, item);

            i++;
        }

        return result;
    }

    private static boolean isDoubleConstantBetweenParentheses(ArrayList<Object> items, int index)
    {
        if (index == 0)
            return false;

        if (index == items.size() - 1)
            return false;

        if (!isDoubleConstant(items.get(index)))
            return false;

        if (!isLeftParenthesisOperator(items.get(index - 1)))
            return false;

        if (!isRightParenthesisOperator(items.get(index + 1)))
            return false;

        return true;
    }

    private static ArrayList<Object> removeOperators(ArrayList<Object> items)
    {
        if (items.size() == 0)
            return items;

        ArrayList<Object> result = new ArrayList<Object>(items.size());
        for (int i = 0; i < items.size(); i++)
        {
            Object item = items.get(i);
            Operator _operator = ms.as(item, Operator.class);

            if ((_operator != null) && !_operator.isUnary() && isBetweenStringConstant(items, i))
                continue;

            msArrayList.add(result, item);
        }

        return result;
    }

    private static boolean isBetweenStringConstant(ArrayList<Object> items, int i)
    {
        return previousItemIsStringConstant(items, i) && nextItemIsStringConstant(items, i);
    }

    private static boolean previousItemIsStringConstant(ArrayList<Object> items, int i)
    {
        return (i != 0) && (items.get(i - 1) instanceof StringConstant);
    }

    private static boolean nextItemIsStringConstant(ArrayList<Object> items, int i)
    {
        return (i != items.size() - 1) && (items.get(i + 1) instanceof StringConstant);
    }

    private static ArrayList<Object> removeStringConstants(ArrayList<Object> items)
    {
        if (items.size() == 0)
            return items;

        ArrayList<Object> result = new ArrayList<Object>(items.size());

        for (Object item : items)
        {
            if (!(item instanceof StringConstant))
                msArrayList.add(result, item);
        }

        return result;
    }

    private static ArrayList<Object> addSumOperator(ArrayList<Object> items)
    {
        if (items.size() <= 1)
            return items;

        for (Object item : items)
        {
            if (isDoubleConstant(item) || isUnaryOperator(item) || isSubtractionOperator(item))
                continue;

            return items;
        }

        ArrayList<Object> result = new ArrayList<Object>(items.size() * 2 - 1);

        msArrayList.add(result, items.get(0));

        for (int i = 1; i < items.size(); i++)
        {
            Object lastItem = result.get(result.size() - 1);
            Object currentItem = items.get(i);

            if ((isDoubleConstant(currentItem) || isUnaryMinusOperator(currentItem)) &&
                (isDoubleConstant(lastItem) || isPercentOperator(lastItem)))
                msArrayList.add(result, new AdditionOperator());

            msArrayList.add(result, currentItem);
        }

        return result;
    }

    private static boolean isDoubleConstant(Object item)
    {
        return item instanceof DoubleConstant;
    }

    private static boolean isPercentOperator(Object item)
    {
        return item instanceof UnaryPercentOperator;
    }

    private static boolean isUnaryOperator(Object item)
    {
        return item instanceof UnaryOperator;
    }

    private static boolean isUnaryMinusOperator(Object item)
    {
        return item instanceof UnaryMinusOperator;
    }

    private static boolean isLeftParenthesisOperator(Object item)
    {
        return item instanceof LeftParenthesisOperator;
    }

    private static boolean isRightParenthesisOperator(Object item)
    {
        return item instanceof RightParenthesisOperator;
    }

    private static boolean isSubtractionOperator(Object item)
    {
        return item instanceof SubtractionOperator;
    }

    private /*final*/ boolean mAllowLeadingStringConstants;
}

