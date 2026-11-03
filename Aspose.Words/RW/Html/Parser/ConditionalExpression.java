// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/12/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents an Internet Explorer conditional expression.
/// </summary>
/// <remarks>
/// For information on conditional expressions see https://msdn.microsoft.com/en-us/library/ms537512(v=vs.85).aspx
/// </remarks>
abstract class ConditionalExpression
{
    /// <summary>
    /// Parses a text into a conditional expression.
    /// </summary>
    /// <param name="text">A text to parse.</param>
    /// <returns>Either the parsed expression or <c>null</c> if an error occurred.</returns>
    static ConditionalExpression parse(String text) throws Exception
    {
        IEConditionalExpressionsParser parser = new IEConditionalExpressionsParser();
        return parser.parse(text);
    }

    /// <summary>
    /// Matches the expression against a set of features. Determines whether the set satisfies the expression.
    /// </summary>
    /// <param name="features">A set of features to match expression against.</param>
    /// <returns>A value indicating whether the expression matches the set.</returns>
    abstract boolean matches(Features features);

    String toStringAsSubexpression()
    {
        return (isSubexpression())
            ? "(" + toString() + ")"
            : toString();
    }

    protected /*virtual*/ boolean isSubexpression() { return true; }
}

