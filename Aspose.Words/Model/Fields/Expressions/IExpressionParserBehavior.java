// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/03/2015 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.ArrayList;


/// <summary>
/// Performs specific behavior of expression parser.
/// </summary>
interface IExpressionParserBehavior
{
    /// <summary>
    /// Performs specific behavior with parsed expression.
    /// </summary>
    public ArrayList<Object> normalizeExpression(ArrayList<Object> expression, FieldContext context) throws Exception;

    /// <summary>
    /// Performs specific behavior with builded execution queue.
    /// </summary>
    public ExecutionQueue normalizeExecutionQueue(ExecutionQueue queue, FieldContext context, String expression);

    /// <summary>
    /// Checks if the specified character is a delimiter.
    /// </summary>
    /// <param name="c">The character to check.</param>
    /// <param name="expression">The parsed expression.</param>
    /// <param name="position">The position of checked character.</param>
    /// <param name="builder">The current token.</param>
    /// <returns>True if the character is a delimiter.</returns>
    public boolean isDelimiter(char c, String expression, int position, StringBuilder builder);
}
