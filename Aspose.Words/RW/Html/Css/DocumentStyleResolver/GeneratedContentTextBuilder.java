// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2016 by Anton Savko

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;


/// <summary>
/// Builds a textual representation of generated content.
/// </summary>
class GeneratedContentTextBuilder implements IPseudoElementContentPartVisitor
{
    private GeneratedContentTextBuilder(CssCounters counters, IHtmlElementProvider element)
    {
        Debug.assertTrue(counters != null);
        Debug.assertTrue(element != null);

        mCounters = counters;
        mElement = element;
        mText = new StringBuilder();
    }

    /// <summary>
    /// Gets a textual representation of generated content.
    /// </summary>
    /// <returns>
    /// Text that represents this content part. The text may be empty but is never <c>null</c>.
    /// </returns>
    static String getText(PseudoElementContent content, CssCounters counters, IHtmlElementProvider element)
    {
        GeneratedContentTextBuilder builder = new GeneratedContentTextBuilder(counters, element);
        content.accept(builder);
        return builder.mText.toString();
    }

    public void /*IPseudoElementContentPartVisitor.*/visitAttr(String attributeName)
    {
        String attributeValue = mElement.getAttributeValue(attributeName, "");
        msStringBuilder.append(mText, attributeValue);
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounter(String counterName, /*NumberStyle*/int counterStyle)
    {
        int numberValue = mCounters.getValue(counterName);

        String valueString = NumberConverter.numberToString(numberValue, counterStyle, true);
        msStringBuilder.append(mText, valueString);
    }

    public void /*IPseudoElementContentPartVisitor.*/visitCounters(String counterName, String separator, /*NumberStyle*/int counterStyle)
    {
        int[] numberValues = mCounters.getAllValues(counterName);

        for (int i = 0; i < numberValues.length; i++)
        {
            String valueString = NumberConverter.numberToString(numberValues[i], counterStyle, true);
            msStringBuilder.append(mText, valueString);

            if (i != numberValues.length - 1)
            {
                msStringBuilder.append(mText, separator);
            }
        }
    }

    public void /*IPseudoElementContentPartVisitor.*/visitText(String text)
    {
        msStringBuilder.append(mText, text);
    }

    private /*final*/ IHtmlElementProvider mElement;
    private /*final*/ CssCounters mCounters;
    private /*final*/ StringBuilder mText;
}

