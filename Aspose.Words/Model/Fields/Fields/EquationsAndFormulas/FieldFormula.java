// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 30/07/2009 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Implements the = (formula) field.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// Calcualtes the result of an expression.
/// </remarks>
public class FieldFormula extends Field
{
    /*override*/ FieldUpdateAction updateCore() throws Exception
    {
        Constant result = evaluate();

        if (!result.isError())
            return new FieldUpdateActionApplyResult(this, result);
        else
            return new FieldUpdateActionInsertErrorMessage(this, result.getValueString());
    }

    Constant evaluate() throws Exception
    {
        // Formulas are a special kind of fields where we never care about extracting field code tokens
        // and should treat the whole code as a single string.
        String fieldCode = NodeRangeFieldCodeTokenizer.getCodeAsString(this);
        // Remove '=' at the begining.
        fieldCode = msString.trimStart(fieldCode).substring(1);

        return evaluate(fieldCode);
    }

    private Constant evaluate(String expression) throws Exception
    {
        Constant result = ExpressionEvaluator.evaluateFormulaExpression(this, expression);

        if (result instanceof StringConstant)
        {
            if (msString.isNullOrEmpty(result.getValueString()))
                return new DoubleConstant(0d);
            else
                return ErrorConstant.createSyntaxError();
        }
        else if (result instanceof NullConstant)
        {
            return new StringConstant("");
        }

        return result;
    }
}

