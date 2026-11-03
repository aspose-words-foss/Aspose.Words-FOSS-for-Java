// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.ms.System.msChar;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.NumberFormattingOptions;
import com.aspose.IString;
import com.aspose.NumberFormattingOptionsUtil;
import com.aspose.words.ref.RefBoolean;
import com.aspose.words.ref.RefDouble;


/// <summary>
/// Represents a constant whose value is a floating point number.
/// </summary>
class DoubleConstant extends Constant
{
    DoubleConstant(double value)
    {
        mValue = value;
        String number = FormatterPal.doubleToStr(value);
        int index = number.indexOf('.');
        setNumberOfDigitsAfterDecimalPoint(index != -1 ? number.substring(index + 1).length() : 0);
    }

    private DoubleConstant(double value, boolean isCurrency, boolean isUseGroupSeparator, int numberOfDigitsAfterDecimalPoint, boolean hasEndNonDigits)
    {
        mValue = value;
        isCurrency(isCurrency);
        isUsesGroupSeparator(isUseGroupSeparator);
        setNumberOfDigitsAfterDecimalPoint(numberOfDigitsAfterDecimalPoint);
        mHasEndNonDigits = hasEndNonDigits;
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(Constant source, double value)
    {
        // JAVA: manual collection initialization for autoportability.
        ConstantCollection col = new ConstantCollection();
        col.add(source);
        return createFromInternal(col, value);
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(Constant source, double value, int numberOfDigitsAfterDecimalPoint)
    {
        // JAVA: manual collection initialization for autoportability.
        ConstantCollection col = new ConstantCollection();
        col.add(source);
        return createFromInternal(col, value, numberOfDigitsAfterDecimalPoint);
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(Constant source1, Constant source2, double value)
    {
        // JAVA: manual collection initialization for autoportability.
        ConstantCollection col = new ConstantCollection();
        col.add(source1);
        col.add(source2);
        return createFromInternal(col, value);
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(Constant source1, Constant source2, double value, int numberOfDigitsAfterDecimalPoint)
    {
        // JAVA: manual collection initialization for autoportability.
        ConstantCollection col = new ConstantCollection();
        col.add(source1);
        col.add(source2);
        return createFromInternal(col, value, numberOfDigitsAfterDecimalPoint);
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(ConstantCollection sources, double value)
    {
        return createFromInternal(sources, value);
    }

    /// <summary>
    /// Creates a floating number constant from the specified value and attempts to copy style from value source.
    /// I need this because any constant in the expression that has currency format or decimal grouping causes
    /// the resulting value (if it is DoubleConstant as well) to be the same.
    /// </summary>
    static DoubleConstant createFrom(ConstantCollection sources, double value, int numberOfDigitsAfterDecimalPoint)
    {
        return createFromInternal(sources, value, numberOfDigitsAfterDecimalPoint);
    }

    private static DoubleConstant createFromInternal(ConstantCollection sources, double value)
    {
        return createFromInternal(sources, value, -1);
    }

    private static DoubleConstant createFromInternal(ConstantCollection sources, double value, int numberOfDigitsAfterDecimalPoint)
    {
        DoubleConstant constant = new DoubleConstant(value);
        if (numberOfDigitsAfterDecimalPoint > -1)
            constant.setNumberOfDigitsAfterDecimalPoint(numberOfDigitsAfterDecimalPoint);

        for (Constant source : sources)
            constant.tryCopyStyleFrom(source);

        return constant;
    }

    private void tryCopyStyleFrom(Constant source)
    {
        if (source.getConstantType() != ConstantType.DOUBLE)
            return;

        DoubleConstant sourceConstant = (DoubleConstant)source;

        if (sourceConstant.isCurrency())
            isCurrency(true);

        if (sourceConstant.isUsesGroupSeparator())
            isUsesGroupSeparator(true);

        if (sourceConstant.isUsesNegativeParentheses())
            isUsesNegativeParentheses(true);
    }

    static DoubleConstant tryParse(String s)
    {
        if (!StringUtil.hasChars(s))
            return null;

        boolean isSignPresence = false;
        if (s.length() > 1 && (s.charAt(0) == '-'))
        {
            s = s.substring((1), (1) + (s.length() - 1));
            isSignPresence = true;
        }

        boolean hasNumberGroupSeparator = s.indexOf(FormatterPal.getNumberGroupSeparatorCurrent()) != -1;

        boolean isCurrency = false;
        RefBoolean referenceToIsCurrency = new RefBoolean(isCurrency);
        s = removeCurrencySymbol(s, /*out*/ referenceToIsCurrency);
        isCurrency = referenceToIsCurrency.get();

        int percentPosition = s.indexOf('%');
        boolean isPercentage = percentPosition != -1;
        double multiplier = isPercentage ? 0.01 : 1.0;

        boolean hasEndNonDigits = checkHasEndNonDigits(s);
        s = StringUtil.trimEndNonDigits(s);

        if (isPercentage && s.length() != percentPosition)
            return null;

        int numberOfDigitsAfterDecimalPoint = getNumberOfDigitsAfterDecimalPoint(s);
        if (isPercentage)
            numberOfDigitsAfterDecimalPoint += 2;

        if (!isValidGroupSeparators(s))
            return null;

        double value = FormatterPal.tryParseCurrencyCurrent(s);

        // WORDSNET-5321 The numeric string can be in exponential notation. Need to check this out.
        if (Double.isNaN(value))
            value = FormatterPal.tryParseDoubleCurrent(s);

        boolean isNegative = isSignPresence;
        if (!Double.isNaN(value))
            return new DoubleConstant(multiplier * (isNegative ? -value : value), isCurrency, hasNumberGroupSeparator, numberOfDigitsAfterDecimalPoint, hasEndNonDigits);

        return null;
    }

    private static boolean checkHasEndNonDigits(String input)
    {
        for (int i = input.length() - 1; i >= 0; i--)
        {
            char c = input.charAt(i);

            if (Character.isDigit(c))
                return false;

            if (!msChar.isWhiteSpace(c) && c != '%')
                return true;
        }

        return false;
    }

    private static boolean isValidGroupSeparators(String value)
    {
        int groupSize = FormatterPal.getNumberGroupSizeCurrent();
        if (groupSize == 0)
            return true;

        char groupSeparator = FormatterPal.getNumberGroupSeparatorCurrent();
        char decimalSeparator = FormatterPal.getDecimalSeparatorCurrent();

        int lastGroupSeparatorIndex = value.indexOf(groupSeparator);
        if (lastGroupSeparatorIndex == -1)
            return true;

        while (true)
        {
            int currentGroupSeparatorIndex = value.indexOf(groupSeparator, lastGroupSeparatorIndex + 1);
            if (currentGroupSeparatorIndex == -1)
                break;

            if (currentGroupSeparatorIndex - lastGroupSeparatorIndex != groupSize + 1)
                return false;

            lastGroupSeparatorIndex = currentGroupSeparatorIndex;
        }


        int decimalSeparatorIndex = value.indexOf(decimalSeparator);
        if (decimalSeparatorIndex == -1)
            return true;

        if (decimalSeparatorIndex < lastGroupSeparatorIndex)
            return false;

        if (decimalSeparatorIndex - lastGroupSeparatorIndex != groupSize + 1)
            return false;

        return true;
    }

    private static int getNumberOfDigitsAfterDecimalPoint(String s)
    {
        int decimalPointPosition = s.indexOf(FormatterPal.getDecimalSeparatorCurrent());
        if (decimalPointPosition == -1)
            return 0;

        return s.length() - decimalPointPosition - 1;
    }

    private static String removeCurrencySymbol(String s, /*out*/RefBoolean isCurrency)
    {
        // WORDSNET-3679 For "fr-CH" culture might be set either of the two currency symbols - "SFr." or (optional) "fr.".
        // WORDSNET-17864 For "fr-CH" culture the currency symbol is "CHF" in .NET 4.0 and above.
        s = FormatterPal.normalizeCurrencySymbols(s);

        // Remove currency symbol of current culture
        String currencySymbolCurrent = FormatterPal.getCurrencySymbolCurrent();
        String afterReplace = StringUtil.removeSubstring(s, currencySymbolCurrent);
        if (!msString.equals(afterReplace, s))
        {
            isCurrency.set(true);
            return afterReplace;
        }

        // Remove currency symbols of known cultures
        isCurrency.set(false);
        for (String currency : G_CURRENCIES)
        {
            afterReplace = StringUtil.removeSubstring(s, currency);
            if (!msString.equals(afterReplace, s))
            {
                return afterReplace;
            }
        }

        return s;
    }

    static FieldFormattingResult tryFormatNumber(double value, RichString format, Field field)
    {
        Debug.assertTrue(!RichStringBehaviour.isNullOrEmptyInternal(format));

        FieldOptions fieldOptions = field.fetchDocument().getFieldOptions();
        /*NumberFormattingOptions*/int options = fieldOptions.getNumberFormattingOptions();
        IFieldResultFormatter resultFormatter = fieldOptions.getResultFormatter();

        if (resultFormatter != null)
        {
            String plainTextResult = resultFormatter.formatNumeric(value, format.toSystemString());
            if (plainTextResult != null)
            {
                return new FieldFormattingResult(
                    RichString.createFromString(plainTextResult),
                    false);
            }
        }

        IString richResult = FormatterPal.numberToStrMSWord(
            value,
            format,
            options,
            RichStringBehaviour.INSTANCE);

        boolean legacyNumberFormat = NumberFormattingOptionsUtil.hasLegacyNumberFormat(options);
        return new FieldFormattingResult((RichString)richResult, !legacyNumberFormat);
    }

    /*override*/ FieldFormattingResult tryFormatNumber(RichString format, Field field)
    {
        return tryFormatNumber(mValue, format, field);
    }

    /*override*/ boolean tryConvertToDouble(/*out*/RefDouble value)
    {
        value.set(mValue);
        return true;
    }

    /*override*/ double getValueDouble() { return mValue; }

    /*override*/ boolean getValueBoolean() { return mValue != 0d; }

    /*override*/ String getValueString()
    {
        return FormatterPal.numberToStrMSWordWithNoFormat(
            mValue,
            isCurrency(),
            isUsesGroupSeparator(),
            getNumberOfDigitsAfterDecimalPoint(),
            isUsesNegativeParentheses());
    }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.DOUBLE; }

    boolean isCurrency() { return mIsCurrency; }; private void isCurrency(boolean value) { mIsCurrency = value; };

    private boolean mIsCurrency;

    boolean isUsesGroupSeparator() { return mIsUsesGroupSeparator; }; private void isUsesGroupSeparator(boolean value) { mIsUsesGroupSeparator = value; };

    private boolean mIsUsesGroupSeparator;

    boolean isUsesNegativeParentheses() { return mIsUsesNegativeParentheses; }; void isUsesNegativeParentheses(boolean value) { mIsUsesNegativeParentheses = value; };

    private boolean mIsUsesNegativeParentheses;

    int getNumberOfDigitsAfterDecimalPoint() { return mNumberOfDigitsAfterDecimalPoint; }; private void setNumberOfDigitsAfterDecimalPoint(int value) { mNumberOfDigitsAfterDecimalPoint = value; };

    private int mNumberOfDigitsAfterDecimalPoint;

    boolean hasEndNonDigits() { return mHasEndNonDigits; };

    private boolean mHasEndNonDigits;

    private /*final*/ double mValue;

    private static /*final*/ String[] G_CURRENCIES = { "$", "€", "¥", "£", "CHF", "C$", "kr", "HK$", "р", "₽" };
}

