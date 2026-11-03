// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2022 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.StringUtil;

class GeneratedContentListLevelCounter implements IPseudoElementContentPartVisitor
{
    private GeneratedContentListLevelCounter(CssCounters counters, IHtmlElementProvider element)
    {
        Debug.assertTrue(counters != null);
        Debug.assertTrue(element != null);

        mCounters = counters;
        mElement = element;
        mListLevelCount = 0;
    }

    static int getListLevelCount(
        PseudoElementContent content,
        CssCounters counters,
        IHtmlElementProvider element)
    {
        GeneratedContentListLevelCounter counter = new GeneratedContentListLevelCounter(counters, element);
        content.accept(counter);
        return counter.mListLevelCount;
    }

    public void /*IPseudoElementContentPartVisitor.*/visitAttr(String attributeName)
    {
        String attributeValue = mElement.getAttributeValue(attributeName, "");
        handleText(attributeValue);
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounter(String counterName, /*NumberStyle*/int counterStyle)
    {
        mListLevelCount++;
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounters(String counterName, String separator, /*NumberStyle*/int counterStyle)
    {
        int[] numberValues = mCounters.getAllValues(counterName);
        mListLevelCount += numberValues.length;
    }

    public void /*IPseudoElementContentPartVisitor.*/visitText(String text)
    {
        handleText(text);
    }

    private void handleText(String text)
    {
        int listLevelCount = 0;

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
                listLevelCount++;
            }

            while ((offset < text.length()) && !StringUtil.isDigit(text.charAt(offset)))
            {
                offset++;
            }
        }

        mListLevelCount += listLevelCount;
    }

    private /*final*/ CssCounters mCounters;
    private /*final*/ IHtmlElementProvider mElement;
    private int mListLevelCount;
}

