// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/03/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.FormatterPal;


/// <summary>
/// Represents an argument of pseudo-classes :nth-child(), :nth-last-child(), :nth-of-type(), and :nth-last-of-type().
/// </summary>
/// <remarks>
/// The argument has the form 'an+b'; its syntax is described here: http://www.w3.org/TR/css3-selectors/#nth-child-pseudo
/// </remarks>
class CssIndexArgument
{
    /// <summary>
    /// Initializes a new instance of the class.
    /// </summary>
    /// <param name="step">The 'a' value of the 'an+b' argument.</param>
    /// <param name="offset">The 'b' value of the 'an+b' argument.</param>
    CssIndexArgument(int step, int offset)
    {
        mStep = step;
        mOffset = offset;
    }

    /// <summary>
    /// Indicates whether the specified index satisfies the expression 'an+b', which is represented by the index selector.
    /// </summary>
    /// <param name="index">A zero-based non-negative index.</param>
    /// <returns><c>true</c> if the index satisfies the expression, otherwise <c>false</c>.</returns>
    boolean matches(int index)
    {
        Debug.assertTrue(index >= 0);

        int shiftedIndex = (index + 1 - mOffset);
        if (mStep > 0)
        {
            return (shiftedIndex >= 0) && ((shiftedIndex % mStep) == 0);
        }
        else if (mStep < 0)
        {
            return (shiftedIndex <= 0) && ((shiftedIndex % mStep) == 0);
        }
        else
        {
            return shiftedIndex == 0;
        }
    }

    /// <summary>
    /// Gets a text representation of the argument. 
    /// </summary>
    String getText()
    {
        StringBuilder result = new StringBuilder();

        switch (mStep)
        {
            case -1:
                msStringBuilder.append(result, "-n");
                break;
            case 0:
                break;
            case 1:
                result.append('n');
                break;
            default:
                msStringBuilder.append(result, FormatterPal.intToStr(mStep)).append('n');
                break;
        }

        if ((mOffset != 0) || (mStep == 0))
        {
            if ((mOffset > 0) && (mStep != 0))
            {
                result.append('+');
            }
            msStringBuilder.append(result, FormatterPal.intToStr(mOffset));
        }

        return result.toString();
    }

    /// <summary>
    /// The 'a' value of the 'an+b' argument.
    /// </summary>
    private /*final*/ int mStep;

    /// <summary>
    /// The 'b' value of the 'an+b' argument.
    /// </summary>
    private /*final*/ int mOffset;
}

