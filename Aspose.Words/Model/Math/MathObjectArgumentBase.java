// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Acts as a common container for different argument types in Office Math.
/// The most generally used one is <see cref="Words.Math.MathObjectType.Argument"/>.
/// Others are used occasionally as special arguments for other Office Math objects.
/// <example>
/// - <see cref="Words.Math.MathObjectType.Denominator"/> and <see cref="Words.Math.MathObjectType.Numerator"/> are
/// the only special arguments that <see cref="Words.Math.MathObjectType.Fraction"/> can contain.
/// </example>
/// </summary>
/// <remarks>
/// Initially there were individual classes for each specific argument type.
/// But it showed to be overkill: they didn't have any properties and were used in a similar fashion.
/// So to reduce complexity, DD has mereged them into this.
/// </remarks>
class MathObjectArgumentBase extends MathObject
{
    MathObjectArgumentBase(/*MathObjectType*/int type)
    {
        switch (type)
        {
            case MathObjectType.ARGUMENT:
            case MathObjectType.DEGREE:
            case MathObjectType.DENOMINATOR:
            case MathObjectType.FUNCTION_NAME:
            case MathObjectType.LIMIT:
            case MathObjectType.NUMERATOR:
            case MathObjectType.SUBSCRIPT_PART:
            case MathObjectType.SUPERSCRIPT_PART:
                mMathObjectType = type;
                break;
            default:
                throw new IllegalStateException("Please report exception");
        }
    }

    /*override*/ boolean canInsert(Node node)
    {
        return canBeMathArgument(node);
    }

    /// <summary>
    /// Specifies the size, or script level, of an argument.
    /// The default argument size is 0. Valid range is (-2, 2) inclusive.
    /// </summary>
    /// <remarks>
    /// This is the only property in math object that is not implemented throught <see cref="MathAttr"/>.
    /// The reason for it is that it is not stored in m:ctrlPr, it has its own singular pr containing only m:argSz el.
    /// </remarks>
    int getArgumentSize() { return mArgSize; }
    void setArgumentSize(int value)
    {
        int fixedValue = (value < -2) ? -2 : (value > 2) ? 2 : value;
        if (fixedValue != DEFAULT_ARGUMENT_SIZE)
            mArgSize = fixedValue;
    }

    /*override*/ boolean getNeedsMathObjectArgumentWrapper() { return false; }

    /*override*/ boolean canBeArgument() { return false; }

    /*override*/ /*MathObjectType*/int getMathObjectType() { return mMathObjectType; }

    private /*final*/ /*MathObjectType*/int mMathObjectType;

    private int mArgSize = DEFAULT_ARGUMENT_SIZE;

    static final int DEFAULT_ARGUMENT_SIZE = 0;
}

