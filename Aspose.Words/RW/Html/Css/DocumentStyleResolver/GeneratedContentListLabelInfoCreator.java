// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2022 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.IntToIntDictionary;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.Convert;


class GeneratedContentListLabelInfoCreator implements IPseudoElementContentPartVisitor
{
    GeneratedContentListLabelInfoCreator(
        CssCounters counters,
        IHtmlElementProvider element,
        int skipListLevelCount,
        int startListLevelNumber)
    {
        Debug.assertTrue(counters != null);
        Debug.assertTrue(element != null);
        mCounters = counters;
        mElement = element;

        mSkipListLevelCount = skipListLevelCount;
        mCurrentListLevelNumber = startListLevelNumber;

        mNumberFormatBuilder = new StringBuilder();
        mLevelNumberToNumberValueMap = new IntToIntDictionary();
        mLevelNumberToNumberStyleMap = new IntToIntDictionary();
    }

    static GeneratedContentListLabelInfo getListLabelInfo(
        PseudoElementContent content,
        CssCounters counters,
        IHtmlElementProvider element,
        int skipListLevelCount,
        int currentListLevelNumber)
    {
        GeneratedContentListLabelInfoCreator creator = new GeneratedContentListLabelInfoCreator(
            counters,
            element,
            skipListLevelCount,
            currentListLevelNumber);

        content.accept(creator);

        return new GeneratedContentListLabelInfo(
            creator.mNumberFormatBuilder.toString(),
            creator.mLevelNumberToNumberValueMap,
            creator.mLevelNumberToNumberStyleMap);
    }

    public void /*IPseudoElementContentPartVisitor.*/visitAttr(String attributeName)
    {
        String attributeValue = mElement.getAttributeValue(attributeName, "");
        handleText(attributeValue);
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounter(String counterName, /*NumberStyle*/int counterStyle)
    {
        int numberValue = mCounters.getValue(counterName);

        if (mSkipListLevelCount > 0)
        {
            handleAsText(numberValue, counterStyle);
        }
        else
        {
            handleAsListLevelNumber(numberValue, counterStyle);
        }
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounters(String counterName, String separator, /*NumberStyle*/int counterStyle)
    {
        int[] numberValues = mCounters.getAllValues(counterName);

        int skipListLevelCount = Math.min(mSkipListLevelCount, numberValues.length);

        for (int i = 0; i < skipListLevelCount; i++)
        {
            handleAsText(numberValues[i], counterStyle);

            if (i != numberValues.length - 1)
            {
                msStringBuilder.append(mNumberFormatBuilder, separator);
            }
        }

        for (int i = skipListLevelCount; i < numberValues.length; i++)
        {
            handleAsListLevelNumber(numberValues[i], counterStyle);

            if (i != numberValues.length - 1)
            {
                msStringBuilder.append(mNumberFormatBuilder, separator);
            }
        }
    }

    public void /*IPseudoElementContentPartVisitor.*/visitText(String text)
    {
        handleText(text);
    }

    private void handleText(String text)
    {
        int offset = 0;
        while (offset < text.length())
        {
            int numberOffset = offset;
            while ((offset < text.length()) && StringUtil.isDigit(text.charAt(offset)))
            {
                offset++;
            }

            int numberLength = offset - numberOffset;
            if (numberLength > 0)
            {
                // String is guaranteed to have only digits.
                String numberValueString = text.substring((numberOffset), (numberOffset) + (numberLength));
                int numberValue = Convert.toInt32(numberValueString);

                if (mSkipListLevelCount > 0)
                {
                    handleAsText(numberValue, NumberStyle.ARABIC);
                }
                else
                {
                    handleAsListLevelNumber(numberValue, NumberStyle.ARABIC);
                }
            }

            while ((offset < text.length()) && !StringUtil.isDigit(text.charAt(offset)))
            {
                mNumberFormatBuilder.append(text.charAt(offset));
                offset++;
            }
        }
    }

    private void handleAsText(int numberValue, /*NumberStyle*/int counterStyle)
    {
        String valueString = NumberConverter.numberToString(numberValue, counterStyle, true);

        msStringBuilder.append(mNumberFormatBuilder, valueString);

        mSkipListLevelCount--;
    }

    private void handleAsListLevelNumber(int numberValue, /*NumberStyle*/int counterStyle)
    {
        mNumberFormatBuilder.append((char)mCurrentListLevelNumber);

        mLevelNumberToNumberValueMap.add(mCurrentListLevelNumber, numberValue);
        mLevelNumberToNumberStyleMap.add(mCurrentListLevelNumber, (int)counterStyle);

        mCurrentListLevelNumber++;
    }

    private /*final*/ CssCounters mCounters;
    private /*final*/ IHtmlElementProvider mElement;

    private int mSkipListLevelCount;
    private int mCurrentListLevelNumber;

    private /*final*/ StringBuilder mNumberFormatBuilder;
    private /*final*/ IntToIntDictionary mLevelNumberToNumberValueMap;
    private /*final*/ IntToIntDictionary mLevelNumberToNumberStyleMap;
}

