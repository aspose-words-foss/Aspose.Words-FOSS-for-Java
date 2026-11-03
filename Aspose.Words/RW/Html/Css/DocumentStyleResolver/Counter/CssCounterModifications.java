// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/11/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import java.util.ArrayList;
import com.aspose.Debug;
import com.aspose.ms.System.Collections.msArrayList;
import com.aspose.ms.System.msMath;
import com.aspose.MathUtil;


/// <summary>
/// Represents a series of modifications to CSS counters.
/// </summary>
/// <remarks>
/// Modifications to counters are normally defined by 'counter-reset' and 'counter-increment' CSS properties.
/// </remarks>
class CssCounterModifications
{
    /// <summary>
    /// Represents a number of modifications to CSS counters.
    /// </summary>
    static CssCounterModifications parse(CssDeclarationCollection declarations)
    {
        CssCounterModifications result = EMPTY;

        // Counters are always reset first and then incremented, no matter what is the order in which 'counter-reset'
        // and 'counter-increment' declarations come.

        CssDeclaration counterReset = declarations.get("counter-reset");
        if (counterReset != null)
        {
            ArrayList<CounterNameValuePair> modifications = parsePropertyValue(counterReset.getValue(), 0);
            if ((result == EMPTY) && (modifications.size() > 0))
            {
                result = new CssCounterModifications();
            }
            result.addResetModifications(modifications);
        }

        CssDeclaration counterIncrement = declarations.get("counter-increment");
        if (counterIncrement != null)
        {
            ArrayList<CounterNameValuePair> modifications = parsePropertyValue(counterIncrement.getValue(), 1);
            if ((result == EMPTY) && (modifications.size() > 0))
            {
                result = new CssCounterModifications();
            }
            result.addIncrementModifications(modifications);
        }

        return result;
    }

    void applyTo(CssCounters counters)
    {
        for (CssCounterModification modification : mModifications)
        {
            modification.applyTo(counters);
        }
    }


    private void addResetModifications(Iterable<CounterNameValuePair> modifications)
    {
        for (CounterNameValuePair counterAndValue : modifications)
        {
            add(new CssCounterReset(counterAndValue.getCounterName(), counterAndValue.getValue()));
        }
    }

    private void addIncrementModifications(Iterable<CounterNameValuePair> modifications)
    {
        for (CounterNameValuePair counterAndValue : modifications)
        {
            add(new CssCounterIncrement(counterAndValue.getCounterName(), counterAndValue.getValue()));
        }
    }

    private void add(CssCounterModification modification)
    {
        Debug.assertTrue(this != EMPTY);
        Debug.assertTrue(modification != null);

        msArrayList.add(mModifications, modification);
    }

    /// <summary>
    /// Parses parameters of 'counter-reset' and 'counter-increment' properties. Both properties has the same syntax.
    /// </summary>
    /// <returns>
    /// A list of counter name/value pairs. Items are of the type <see cref="CounterNameValuePair"/>.
    /// </returns>
    private static ArrayList<CounterNameValuePair> parsePropertyValue(CssPropertyValue propertyValue, int defaultValue)
    {
        ArrayList<CounterNameValuePair> result = new ArrayList<CounterNameValuePair>();

        String lastNameWithoutValue = null;
        for (int i = 0; i < propertyValue.getCount(); i++)
        {
            CssValue value = propertyValue.get(i);
            String counterName = parseCounterName(value);
            if (counterName != null)
            {
                if (lastNameWithoutValue != null)
                {
                    msArrayList.add(result, new CounterNameValuePair(lastNameWithoutValue, defaultValue));
                }
                lastNameWithoutValue = counterName;
            }
            else
            {
                if (lastNameWithoutValue == null)
                {
                    // Error. Expected an identifier but found something else.
                    result.clear();
                    return result;
                }

                CssNumberValue increment = ms.as(value, CssNumberValue.class);
                if (increment != null)
                {
                    double integerPart = msMath.truncate(increment.getDoubleValue());
                    boolean hasNoFractionalPart = MathUtil.areEqual(integerPart, increment.getDoubleValue());
                    if (hasNoFractionalPart)
                    {
                        // Constrain the counter value to a signed 32-bit integer.
                        int constrainedValue;
                        if (integerPart > Integer.MAX_VALUE)
                        {
                            constrainedValue = Integer.MAX_VALUE;
                        }
                        else if (integerPart < Integer.MIN_VALUE)
                        {
                            constrainedValue = Integer.MIN_VALUE;
                        }
                        else
                        {
                            constrainedValue = MathUtil.truncate(integerPart);
                        }

                        msArrayList.add(result, new CounterNameValuePair(lastNameWithoutValue, constrainedValue));
                        lastNameWithoutValue = null;
                    }
                    else
                    {
                        // Error. The specified number value is not an integer.
                        result.clear();
                        return result;
                    }
                }
                else
                {
                    // Error. Expected a number but found something else.
                    result.clear();
                    return result;
                }
            }
        }
        if (lastNameWithoutValue != null)
        {
            msArrayList.add(result, new CounterNameValuePair(lastNameWithoutValue, defaultValue));
        }

        return result;
    }

    private static String parseCounterName(CssValue value)
    {
        CssIdentifierValue counterName = ms.as(value, CssIdentifierValue.class);
        // According to the CSS specification, keywords 'none', 'initial', and 'inherit' must not be used as counter names.
        if ((counterName != null) &&
            (!counterName.equals(CssValue.None)) &&
            (!counterName.equals(CssValue.Inherit)) &&
            (!counterName.equals(CssValue.Initial)))
        {
            return counterName.getValue();
        }
        return null;
    }

    static /*final*/ CssCounterModifications EMPTY = new CssCounterModifications();

    private /*final*/ ArrayList<CssCounterModification> mModifications = new ArrayList<CssCounterModification>();
}

