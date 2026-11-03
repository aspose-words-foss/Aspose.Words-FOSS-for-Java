// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/08/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.msChar;


/// <summary>
/// Stores data about expression parsing state.
/// </summary>
class ExpressionContext
{
    ExpressionContext(String expression)
    {
        mExpression = expression;
    }

    /// <summary>
    /// Increments the position.
    /// </summary>
    void nextChar()
    {
        setPosition(getPosition() + 1)/*Property++*/;
    }

    /// <summary>
    /// Compare the specified string with the expression string starting at the current position.
    /// If the comparison is successful, advances the position by the length of the string.
    /// </summary>
    /// <param name="s">The string to compare.</param>
    /// <returns>True if the comparison is successful.</returns>
    boolean compareAndAdvanceIfSuccessful(String s)
    {
        if (msString.compare(getExpression(), getPosition(), s, 0, s.length(), StringComparison.ORDINAL) == 0)
        {
            setPosition(getPosition() + s.length());
            return true;
        }
        else
        {
            return false;
        }
    }

    void skipWhitespace()
    {
        while (!isEof() && msChar.isWhiteSpace(getCurrentChar()))
            nextChar();
    }

    /// <summary>
    /// Finds any of the specified characters starting from the current position.
    /// </summary>
    /// <remarks>
    /// After successful search the <see cref="Position"/> is moved to the found character.
    /// After unsuccessful search the <see cref="IsEof"/> is <c>true</c>.
    /// </remarks>
    /// <param name="searchChars">Characters to search.</param>
    /// <returns><c>True</c>, if one of search character was found; otherwise, <c>false</c>.</returns>
    boolean findAny(char[] searchChars)
    {
        setPosition(msString.indexOfAny(getExpression(), searchChars, getPosition()));
        if (getPosition() < 0)
        {
            setPosition(getExpression().length());
            return false;
        }

        return true;
    }

    /// <summary>
    /// Gets the expression string.
    /// </summary>
    String getExpression() { return mExpression; };

    private String mExpression;

    /// <summary>
    /// Gets the current position.
    /// </summary>
    int getPosition() { return mPosition; }; private void setPosition(int value) { mPosition = value; };

    private int mPosition;

    /// <summary>
    /// Gets the current character.
    /// </summary>
    char getCurrentChar() { return getExpression().charAt(getPosition()); }

    /// <summary>
    /// Gets whether the end of the expression string is reached or the formula is null.
    /// </summary>
    boolean isEof() { return (getExpression() == null) || (getPosition() >= getExpression().length()); }
}

