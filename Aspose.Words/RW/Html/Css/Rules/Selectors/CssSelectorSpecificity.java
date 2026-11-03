// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msInt;

/// <summary>
/// Implements a selector's specificity. Instances of this class are immutable.
/// </summary>
/// <remarks>
/// A selector's specificity is calculated as follows:
/// count the number of ID selectors in the selector (= a)
/// count the number of class selectors, attributes selectors, and pseudo-classes in the selector (= b)
/// count the number of type selectors and pseudo-elements in the selector (= c)
/// ignore the universal selector.
/// Concatenating the three numbers a-b-c (in a number system with a large base) gives the specificity.
/// For details see http://www.w3.org/TR/selectors/#specificity   
/// </remarks>
class CssSelectorSpecificity
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="a">Number of ID selectors in the selector.</param>
    /// <param name="b">Number of class selectors, attribute selectors, and pseudo-classes in the selector.</param>
    /// <param name="c">Number of type selectors and pseudo-elements in the selector.</param>
    CssSelectorSpecificity(int a, int b, int c)
    {
        Debug.assertTrue(a >= 0);
        Debug.assertTrue(b >= 0);
        Debug.assertTrue(c >= 0);

        mA = a;
        mB = b;
        mC = c;
    }

    /// <summary>
    /// Gets a text representation of the specificity.
    /// </summary>
    /// <returns>A string that contains components of the specificity in the form 'A.B.C'. If the specificity has
    /// the maximum possible value, the string 'inline' is returned to indicate that the specificity relates to
    /// an inline CSS rule.
    /// </returns>
    public /*override*/ String toString()
    {
        if ((mA != Integer.MAX_VALUE) || (mB != Integer.MAX_VALUE) || (mC != Integer.MAX_VALUE))
        {
            return mA + "." + mB + "." + mC;
        }
        else
        {
            return "inline";
        }
    }

    /// <summary>
    /// Gets a new specificity value equal to this specificity value plus the given value.
    /// </summary>
    /// <param name="other">The added specificity value.</param>
    /// <returns>
    /// A new specifity instance, whose value is equal to this specificity value plus the given specificity value.
    /// </returns>
    CssSelectorSpecificity add(CssSelectorSpecificity other)
    {
        return new CssSelectorSpecificity(
            sumComponent(mA, other.mA),
            sumComponent(mB, other.mB),
            sumComponent(mC, other.mC));
    }

    /// <summary>
    /// Compares this instance to a given instance and returns an indication of their relative values.
    /// </summary>
    /// <param name="other">Other instance.</param>
    /// <returns>A signed number indicating the relative values of this instance and value.
    /// Less than zero: This instance is less than value.
    /// Zero: This instance is equal to value.
    /// Greater than zero: This instance is greater than value.
    /// </returns>
    int compareTo(CssSelectorSpecificity other)
    {
        if (other == null)
        {
            // By definition, any object compares greater than a null reference.
            // See http://msdn.microsoft.com/en-us/library/43hc6wht(v=vs.80).aspx
            return 1;
        }

        // Compare the components of the specificity from the most significant component to the least significant one.
        int result = msInt.compareTo(mA, other.mA);
        if (result != 0)
        {
            return result;
        }
        result = msInt.compareTo(mB, other.mB);
        if (result != 0)
        {
            return result;
        }
        return msInt.compareTo(mC, other.mC);
    }

    /// <summary>
    /// Sums two values of a specificity component and ensures that the result will not overflow.
    /// </summary>
    /// <param name="left">First component value.</param>
    /// <param name="right">Second component value.</param>
    /// <returns>The sum of the values. The result is always positive.</returns>
    private static int sumComponent(int left, int right)
    {
        int result = /*unchecked*/(left + right);
        // If the values are too large, the result can 'wrap around' the maximum integer value and become negative.
        return (result >= 0)
            ? result
            : Integer.MAX_VALUE;
    }

    /// <summary>
    /// Inline style selector's specificity. It is the greatest possible specificity value.
    /// </summary>
    static /*final*/ CssSelectorSpecificity INLINE_STYLE_SPECIFICITY =
        new CssSelectorSpecificity(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    /// <summary>
    /// Presentational hint's specificity. It is the lowest possible specificity value.
    /// </summary>
    static CssSelectorSpecificity PresentationalHintSpecificity = new CssSelectorSpecificity(0, 0, 0);

    private /*final*/ int mA;
    private /*final*/ int mB;
    private /*final*/ int mC;
}

