// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 12/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.NullableDouble;


/// <summary>
/// Represents a base class for all functions whose argument is aggregate.
/// </summary>
abstract class AggregateFunction extends Function
{
    protected /*override*/ Constant evaluateCore(ConstantCollection parameters)
    {
        NullableDouble value = getInitialValue();
        int count = 0;

        int numberOfDecimalPlaces = 0;

        for (Constant originalParameter : parameters)
        {
            Constant parameter = originalParameter.getConstantType() != ConstantType.NULL_CELL_REFERENCE
                                 ? originalParameter
                                 : getNullCellReferenceSubstitution();

            if (parameter.getConstantType() == ConstantType.NULL)
            {
                if (getCountNullValues())
                    count++;

                continue;
            }

            count++;

            if (!(parameter instanceof DoubleConstant))
                return ErrorConstant.createSyntaxError();

            numberOfDecimalPlaces = applyNumberOfDecimalPlaces(numberOfDecimalPlaces, (DoubleConstant)parameter);
            value = apply(value, parameter.getValueDouble());
        }

        value = finalizeCalculation(value, count);
        if (value.hasValue())
            return DoubleConstant.createFrom(parameters, value.getValue(), numberOfDecimalPlaces);

        return NullConstant.INSTANCE;
    }

    protected /*virtual*/ NullableDouble apply(NullableDouble currentValue, double parameterValue)
    {
        return currentValue;
    }

    protected /*virtual*/ int applyNumberOfDecimalPlaces(int currentNumberOfDecimalPlaces, DoubleConstant parameter)
    {
        // If field format does not specified, MS Word uses maximum number of decimal places from arguments of most aggregate function for result.
        return Math.max(currentNumberOfDecimalPlaces, parameter.getNumberOfDigitsAfterDecimalPoint());
    }

    protected /*virtual*/ NullableDouble finalizeCalculation(NullableDouble currentValue, int parameterCount)
    {
        return currentValue;
    }

    protected /*virtual*/ NullableDouble getInitialValue() { return new NullableDouble(0d); }

    protected /*virtual*/ Constant getNullCellReferenceSubstitution() { return NullConstant.INSTANCE; }

    protected /*virtual*/ boolean getCountNullValues() { return true; }

    /*override*/ boolean isAggregate() { return true; }

    protected /*override*/ int getParameterCountMin() { return 1; }

    protected /*override*/ int getParameterCountMax() { return Integer.MAX_VALUE; }

    protected /*override*/ boolean isParameterTypeValid(/*ConstantType*/int constantType)
    {
        // Aggregate function self checks parameters types (see method EvaluateCore)
        return true;
    }
}

