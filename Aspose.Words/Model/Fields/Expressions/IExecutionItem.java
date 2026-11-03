// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Represents a formula execution item such as a constant, operator, function, or reference.
/// </summary>
interface IExecutionItem
{
    /// <summary>
    /// Evaluates the execution item.
    /// </summary>
    /// <param name="calculationStack">The current calculation stack.</param>
    /// <returns>The result of the evaluation.</returns>
    public Constant evaluate(ConstantStack calculationStack) throws Exception;
}

