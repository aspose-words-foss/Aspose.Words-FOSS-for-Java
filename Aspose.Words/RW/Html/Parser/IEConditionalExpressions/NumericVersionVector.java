// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.ms;
import com.aspose.ms.System.msString;
import com.aspose.Debug;
import com.aspose.FormatterPal;
import com.aspose.ms.System.msInt;


/// <summary>
/// Represents a supported version vector (version number) of <see cref="Feature"/>. Supported version vectors are either
/// one or two 32-bit non-negative integers delimited by a single dot. For example, "9", or "9.10", or "0.1"
/// </summary>
/// <remarks>
/// For more information see https://msdn.microsoft.com/en-us/library/ms537512(v=vs.85).aspx#Version_Vectors.
/// </remarks>
class NumericVersionVector extends VersionVector
{
    NumericVersionVector(int major)
    {
        Debug.assertTrue(major >= 0);

        mMajor = major;
        mMinor = -1;
    }

    NumericVersionVector(int major, int minor)
    {
        Debug.assertTrue(major >= 0);
        Debug.assertTrue(minor >= 0);

        mMajor = major;
        mMinor = minor;
    }

    boolean hasMinorPart() { return mMinor >= 0; }

    /*override*/ boolean compareTo(VersionVector other, /*ComparisonOperation*/int operation)
    {
        NumericVersionVector otherAsNumericVector = ms.as(other, NumericVersionVector.class);
        if (otherAsNumericVector == null)
        {
            // Cannot compare unsupported types of version vectors.
            return false;
        }

        int compareResult = compareTo(otherAsNumericVector);

        switch (operation)
        {
            case ComparisonOperation.EQUAL:
                return compareResult == 0;
            case ComparisonOperation.GREATER:
                return compareResult > 0;
            case ComparisonOperation.GREATER_OR_EQUAL:
                return compareResult >= 0;
            case ComparisonOperation.LESS:
                return compareResult < 0;
            case ComparisonOperation.LESS_OR_EQUAL:
                return compareResult <= 0;
            default:
                // Unknown comparison operation.
                throw new IllegalStateException();
        }
    }

    public /*override*/ String toString()
    {
        String result = FormatterPal.intToStr(mMajor);
        if (hasMinorPart())
        {
            result = msString.plusEqOperator(result, "." + FormatterPal.intToStr(mMinor));
        }
        return result;
    }

    private int compareTo(NumericVersionVector other)
    {
        int result = msInt.compareTo(mMajor, other.mMajor);

        // Minor parts of version vectors are considered only if both vectors has a minor part. This rule complies with
        // to comparison rules of IE conditional expressions.
        // For example, all the following version comparisons are true:
        //   9 == 9.1
        //   9.2 <= 9
        //   9.2 >= 9
        if ((result == 0) && hasMinorPart() && other.hasMinorPart())
        {
            result = msInt.compareTo(mMinor, other.mMinor);
        }

        return result;
    }

    /// <summary>
    /// The major part of the version vector. Always non-negative.
    /// </summary>
    private /*final*/ int mMajor;

    /// <summary>
    /// The minor part of the version vector. If this part is negative, the version vector has no minor part and it is
    /// not considered during comparison to other vectors.
    /// </summary>
    private /*final*/ int mMinor;
}

