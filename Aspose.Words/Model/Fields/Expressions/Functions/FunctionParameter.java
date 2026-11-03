// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/03/2012 by Daria

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.StringUtil;
import com.aspose.ms.System.msString;
import com.aspose.FormatterPal;
import com.aspose.ms.System.StringComparison;


/// <summary>
/// Represents a parameter of function in a field.
/// </summary>
class FunctionParameter
{
    /// <summary>
    /// Initializes a new instance of the <see cref="FunctionParameter"/> class with the specified parameter value.
    /// </summary>
    /// <param name="parameterValue">A parameter value.</param>
    public FunctionParameter(String parameterValue)
    {
        initialize(parameterValue);
    }

    /// <summary>
    /// Gets a value indicating whether this parameter is preceded by a space.
    /// </summary>
    public boolean isSpaceBefore() { return (getPeculiarities() & ParameterPeculiarities.SPACE_BEFORE) == ParameterPeculiarities.SPACE_BEFORE; }

    /// <summary>
    /// Gets a value indicating whether this parameter is followed by a space.
    /// </summary>
    public boolean isSpaceAfter() { return (getPeculiarities() & ParameterPeculiarities.SPACE_AFTER) == ParameterPeculiarities.SPACE_AFTER; }

    /// <summary>
    /// Gets a value indicating whether this parameter is a radix-10 number: in additional to digits
    /// contains only negative sign, currency and/or fraction chars.
    /// </summary>
    public boolean isNumber() { return (getPeculiarities() & ParameterPeculiarities.NUMBER) == ParameterPeculiarities.NUMBER; }

    /// <summary>
    /// Gets a value indicating whether this parameter is a fractional number.
    /// </summary>
    public boolean isFractional() { return (getPeculiarities() & ParameterPeculiarities.FRACTIONAL) == ParameterPeculiarities.FRACTIONAL; }

    /// <summary>
    /// Gets a value indicating whether the parameter value is a negative number.
    /// </summary>
    public boolean isNegative() { return (getPeculiarities() & ParameterPeculiarities.NEGATIVE) == ParameterPeculiarities.NEGATIVE; }

    /// <summary>
    /// Gets a value indicating whether a currency sign appears at the beginning of parameter.
    /// </summary>
    public boolean isCurrencyAtStart() { return (getPeculiarities() & ParameterPeculiarities.CURRENCY_AT_START) == ParameterPeculiarities.CURRENCY_AT_START; }

    /// <summary>
    /// Gets a value indicating whether a currency sign appears at the end of parameter.
    /// </summary>
    public boolean isCurrencyAtEnd() { return (getPeculiarities() & ParameterPeculiarities.CURRENCY_AT_END) == ParameterPeculiarities.CURRENCY_AT_END; }

    /// <summary>
    /// Gets a value indicating whether the parameter contains one of supported Word expression operators.
    /// </summary>
    public boolean containsOperator() { return (getPeculiarities() & ParameterPeculiarities.CONTAINS_OPERATOR) == ParameterPeculiarities.CONTAINS_OPERATOR; }

    /// <summary>
    /// Gets the length of integer part of number.
    /// </summary>
    public int getLengthOfIntegerPart() { return mLengthOfIntegerPart; }; private void setLengthOfIntegerPart(int value) { mLengthOfIntegerPart = value; };

    private int mLengthOfIntegerPart;

    /// <summary>
    /// Gets the number of digits at start.
    /// </summary>
    public int getNumberOfDigitsAtStart() { return mNumberOfDigitsAtStart; }; private void setNumberOfDigitsAtStart(int value) { mNumberOfDigitsAtStart = value; };

    private int mNumberOfDigitsAtStart;

    /// <summary>
    /// Gets or sets a string representing parameter.
    /// </summary>
    public String getParameterValue() { return mParameterValue; }; private void setParameterValue(String value) { mParameterValue = value; };

    private String mParameterValue;

    void initialize(String value)
    {
        setPeculiarities(ParameterPeculiarities.NONE);
        setLengthOfIntegerPart(0);
        setNumberOfDigitsAtStart(0);

        if (!StringUtil.hasChars(value))
        {
            setParameterValue("");
            return;
        }

        if (StringUtil.isWhiteSpace(value.charAt(0)))
        {
            setPeculiarities(getPeculiarities() | ParameterPeculiarities.SPACE_BEFORE);
        }

        if (StringUtil.isWhiteSpace(value.charAt(value.length() - 1)))
        {
            setPeculiarities(getPeculiarities() | ParameterPeculiarities.SPACE_AFTER);
        }

        setParameterValue(msString.trim(value));
        if (getParameterValue().length() < 1)
        {
            setPeculiarities(ParameterPeculiarities.NONE);
            return;
        }

        int position = 0;
        if (getParameterValue().charAt(position) == '-')
        {
            if (getParameterValue().length() < 2)
            {
                setPeculiarities(ParameterPeculiarities.NONE);
                return;
            }

            setPeculiarities(getPeculiarities() | ParameterPeculiarities.NEGATIVE);
            position++;
        }

        String currency = FormatterPal.getCurrencySymbolCurrent();
        if ((position + currency.length() <= getParameterValue().length()) && msString.equals(getParameterValue().substring((position), (position) + (currency.length())), currency, StringComparison.ORDINAL))
        {
            position += currency.length();
            if (position == getParameterValue().length())
            {
                setPeculiarities(ParameterPeculiarities.NONE);
                return;
            }

            setPeculiarities(getPeculiarities() | ParameterPeculiarities.CURRENCY_AT_START);
        }

        char decimalSeparator = FormatterPal.getDecimalSeparatorCurrent();
        int firstDigitPosition = position;

        for (int i = 0; i < getParameterValue().length(); i++)
        {
        	char c = getParameterValue().charAt(i);
            if (!Character.isDigit(c))
                break;

            setNumberOfDigitsAtStart(getNumberOfDigitsAtStart() + 1)/*Property++*/;
        }

        if (msString.indexOfAny(getParameterValue(), G_OPERATORS, position, getParameterValue().length() - position) != -1)
        {
            setPeculiarities(getPeculiarities() | ParameterPeculiarities.CONTAINS_OPERATOR);
            return;
        }

        for (; position < getParameterValue().length(); position++)
        {
            if (!Character.isDigit(getParameterValue().charAt(position)))
            {
                if (!isFractional() && getParameterValue().charAt(position) == decimalSeparator)
                {
                    setPeculiarities(getPeculiarities() | ParameterPeculiarities.FRACTIONAL);
                    setLengthOfIntegerPart(position - firstDigitPosition);
                    // check for cases: "$.", ".€" - when parameter contains no digit.
                    if (getLengthOfIntegerPart() == 0 &&
                        (position == getParameterValue().length() - 1 || !Character.isDigit(getParameterValue().charAt(position + 1))))
                    {
                        setPeculiarities(ParameterPeculiarities.NONE);
                        return;
                    }
                }
                else
                {
                    break;
                }
            }
        }

        if (!isFractional())
        {
            setLengthOfIntegerPart(position - firstDigitPosition);
        }

        if (position < getParameterValue().length())
        {
            if (!isCurrencyAtStart() &&
                getParameterValue().length() - position == currency.length() &&
                msString.equals(getParameterValue().substring(position), currency, StringComparison.ORDINAL))
            {
                setPeculiarities(getPeculiarities() | ParameterPeculiarities.CURRENCY_AT_END);
            }
            else
            {
                setPeculiarities(ParameterPeculiarities.NONE);
                setLengthOfIntegerPart(0);
                return;
            }
        }

        setPeculiarities(getPeculiarities() | ParameterPeculiarities.NUMBER);
    }

    /// <summary>
    /// Gets a parameter peculiarities.
    /// </summary>
    /*ParameterPeculiarities*/int getPeculiarities() { return mPeculiarities; }; private void setPeculiarities(/*ParameterPeculiarities*/int value) { mPeculiarities = value; };

    private /*ParameterPeculiarities*/int mPeculiarities;

    private static /*final*/ char[] G_OPERATORS = {'+', '-', '*', '/', '%', '^', '=', '>', '<'};
}

