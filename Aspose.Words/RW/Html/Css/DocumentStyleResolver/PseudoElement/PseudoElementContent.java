// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/10/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.java.collections.StringSwitchMap;
import com.aspose.ms.System.ms;
import com.aspose.Debug;
import java.util.ArrayList;
import com.aspose.ms.System.Collections.msArrayList;


/// <summary>
/// Represents generated content of a pseudo-element. Corresponds to the 'content' CSS property.
/// </summary>
/// <remarks>
/// Generated content consists of typed parts - strings and functions - that are concatenated on rendering.
/// For details, see http://www.w3.org/TR/CSS21/generate.html#propdef-content.
/// </remarks>
class PseudoElementContent
{
    PseudoElementContent(PseudoElementContentPart[] parts)
    {
        Debug.assertTrue(parts != null);
        mParts = parts;
    }

    /// <summary>
    /// Parses values of the 'content' CSS property.
    /// </summary>
    /// <returns>
    /// Parsed content or <c>null</c> if the value is malformed or unsupported.
    /// </returns>
    static PseudoElementContent parse(CssPropertyValue contentPropertyValue)
    {
        ArrayList<PseudoElementContentPart> parts = new ArrayList<PseudoElementContentPart>();
        boolean contentIsMalformed = false;
        for (int i = 0; i < contentPropertyValue.getCount(); i++)
        {
            CssValue contentPart = contentPropertyValue.get(i);
            switch (contentPart.getValueType())
            {
                case CssValueType.STRING:
                {
                    String text = ((CssStringValue)contentPart).getValue();
                    msArrayList.add(parts, new PseudoElementContentText(text));
                    break;
                }
                case CssValueType.FUNCTION:
                {
                    CssFunctionValue functionValue = (CssFunctionValue)contentPart;
                    PseudoElementContentPart parsedPart;
                    switch (gStringSwitchMap.of(functionValue.getName()))
                    {
                        case /*"attr"*/0:
                            parsedPart = PseudoElementContentAttr.parse(functionValue);
                            break;
                        case /*"counter"*/1:
                            parsedPart = PseudoElementContentCounter.parse(functionValue);
                            break;
                        case /*"counters"*/2:
                            parsedPart = PseudoElementContentCounters.parse(functionValue);
                            break;
                        default:
                            // Other functions are not recognized at the moment.
                            parsedPart = null;
                            break;
                    }
                    if (parsedPart != null)
                    {
                        msArrayList.add(parts, parsedPart);
                    }
                    else
                    {
                        contentIsMalformed = true;
                    }
                    break;
                }
                case CssValueType.URI:
                {
                    // At the moment, references to external resources are not supported and they are replaced with
                    // empty strings.
                    msArrayList.add(parts, new PseudoElementContentText(""));
                    break;
                }
                case CssValueType.IDENTIFIER:
                {
                    boolean isQuote = contentPart.equals(CssValue.OpenQuote) ||
                        contentPart.equals(CssValue.CloseQuote) ||
                        contentPart.equals(CssValue.NoOpenQuote) ||
                        contentPart.equals(CssValue.NoCloseQuote);
                    if (isQuote)
                    {
                        // At the moment, quotes are not supported and they are replaced with empty strings.
                        msArrayList.add(parts, new PseudoElementContentText(""));
                    }
                    else
                    {
                        contentIsMalformed = true;
                    }
                    break;
                }
                default:
                {
                    // Other value types are not recognized at the moment.
                    contentIsMalformed = true;
                    break;
                }
            }

            // According to the specification, we should ignore a 'content' property if we cannot parse it,
            // and, as a result, we should hide the pseudo-element.
            if (contentIsMalformed)
            {
                parts.clear();
                break;
            }
        }

        if (contentIsMalformed || (parts.size() == 0))
        {
            return null;
        }

        return new PseudoElementContent(msArrayList.toArray(parts, new PseudoElementContentPart[0]));
    }

    /// <summary>
    /// Returns names of all counters used in 'content' CSS property values.
    /// </summary>
    String[] getCounterNames()
    {
        ArrayList<String> counterNames = new ArrayList<String>();
        for (PseudoElementContentPart part : mParts)
        {
            PseudoElementContentCounterFunction partAsCounterFunction = ms.as(part, PseudoElementContentCounterFunction.class);
            if (partAsCounterFunction != null)
            {
                msArrayList.add(counterNames, partAsCounterFunction.getCounterName());
            }
        }
        return msArrayList.toArray(counterNames, new String[0]);
    }

    void accept(IPseudoElementContentPartVisitor visitor)
    {
        for (PseudoElementContentPart part : mParts)
        {
            part.accept(visitor);
        }
    }


    private /*final*/ PseudoElementContentPart[] mParts;

	//JAVA-added for string switch emulation
	private static final StringSwitchMap gStringSwitchMap = new StringSwitchMap
	(
		"attr",
		"counter",
		"counters"
	);

}

