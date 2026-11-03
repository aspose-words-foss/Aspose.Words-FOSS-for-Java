// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Calculates a formula value given a queue of execution items.
/// </summary>
/*static*/ class ExpressionCalculator
{
	/* Simulation of static class by using private constructor */
	private ExpressionCalculator()
	{}

    /// <summary>
    /// Calculates the result of the execution queue prepared by <see cref="ExpressionParser"/>.
    /// </summary>
    /// <param name="queue">The queue to calculate.</param>
    /// <returns>The result of the calculation.</returns>
    static Constant calculate(ExecutionQueue queue) throws Exception
    {
        ConstantStack calculationStack = new ConstantStack();

        if (queue.getCount() == 0)
            return new ErrorConstant("!Unexpected End of Formula");

        while (queue.getCount() > 0)
        {
            IExecutionItem item = queue.dequeue();

            Constant result = item.evaluate(calculationStack);

            // If the item evaluates to an error, stop the evaluation immediately.
            if (result.isError())
                return result;

            calculationStack.push(result);
        }

        Constant calculationResult = calculationStack.pop();
        if (calculationStack.getCount() > 0)
            return new ErrorConstant("!Missing Operator");

        return calculationResult;
    }
}

