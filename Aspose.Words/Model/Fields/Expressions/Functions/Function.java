// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// When implemented, represents a formula function.
/// </summary>
abstract class Function implements IExecutionItem
{
    protected Function()
    {
        mParameters = new ExecutionItemCollection();
    }

    public Constant /*IExecutionItem.*/evaluate(ConstantStack calculationStack) throws Exception
    {
        if ((getParameters().getCount() < getParameterCountMin()) || (getParameters().getCount() > getParameterCountMax()))
            return ErrorConstant.createSyntaxError();

        ConstantCollection evaluatedParameters = new ConstantCollection();

        for (IExecutionItem parameter : getParameters())
        {
            Constant evaluatedParameter = parameter.evaluate(null);

            if ((evaluatedParameter.isError()) && !isDefinedFunction())
                return evaluatedParameter;

            // WORDSNET-10008 If parameters can not be parsed as double, we should return bookmark error.
            if ((evaluatedParameter.getConstantType() == ConstantType.STRING) && !(isParameterTypeValid(ConstantType.STRING)))
                return ErrorConstant.createBookmarkError(evaluatedParameter.getValueString());

            evaluatedParameters.add(evaluatedParameter);
        }

        return evaluateCore(evaluatedParameters);
    }

    protected abstract Constant evaluateCore(ConstantCollection parameters);

    /// <summary>
    /// Gets whether this function accepts an aggregate argument.
    /// </summary>
    /*virtual*/ boolean isAggregate() { return false; }

    /// <summary>
    /// Gets whether this is DefinedFunction.
    /// Created to fix SonarCube issue.
    /// </summary>
    /*virtual*/ boolean isDefinedFunction() { return false; }

    /// <summary>
    /// Gets the minimum number of function parameters.
    /// </summary>
    protected abstract int getParameterCountMin();
    /// <summary>
    /// Gets the maximum number of function parameters.
    /// </summary>
    protected abstract int getParameterCountMax();

    /// <summary>
    /// Gets a collection of function parameters.
    /// </summary>
    ExecutionItemCollection getParameters() { return mParameters; };

    private ExecutionItemCollection mParameters;

    /// <summary>
    /// Checks parameters for validity
    /// </summary>
    protected abstract boolean isParameterTypeValid(/*ConstantType*/int constantType);
}

