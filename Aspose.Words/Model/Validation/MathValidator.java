// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Collections.msStack;


/// <summary>
/// Encapsulate specifics of Office Math validation and prepare reuse for consumer classes: 
/// <see cref="DocumentValidator"/> and <see cref="DocumentPostLoader"/>
/// </summary>
class MathValidator
{
    MathValidator(DocumentBase doc)
    {
        mDoc = doc;
    }
    
    /*VisitorAction*/int visitOfficeMathStart(OfficeMath officeMath)
    {
        if (officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX)
        {
            // found one more matrix, create a validator for it.
            mMatrixValidatorStack.push(new MathObjectMatrixValidator(true, mDoc));
        }
        else if ((mMatrixValidatorStack.getCount() != 0) && MathObjectMatrixValidator.needToValidateNode(officeMath))
        {
            // Start processing rows and arguments(children of row)
            mMatrixValidatorStack.peek().visitOfficeMathStart(officeMath);
        }

        return VisitorAction.CONTINUE;
    }
    
    /*VisitorAction*/int visitOfficeMathEnd(OfficeMath officeMath)
    {
        if (officeMath.getMathObject().getMathObjectType() == MathObjectType.MATRIX)
        {
            // we are leaving current nested matrix, remove validator from stack.
            mMatrixValidatorStack.pop().visitOfficeMathEnd(officeMath);
        }
        else if ((mMatrixValidatorStack.getCount() != 0) && MathObjectMatrixValidator.needToValidateNode(officeMath))
        {
            //Finish processing rows and arguments(children of row)
            mMatrixValidatorStack.peek().visitOfficeMathEnd(officeMath);
        }
        return VisitorAction.CONTINUE;
    }

    /// <summary>
    /// Stores reference to validated document.
    /// </summary>
    private /*final*/ DocumentBase mDoc;

    /// <summary>
    /// Process math objects m:m (matrices) to perform validation.
    /// Make sure number of columns in matrixPr is equal to number of elements in each matrix row.
    /// Since matrices can be nested, we need a stack of validators, each instance processes one level of matrix nesting.
    /// </summary>
    private /*final*/ msStack<MathObjectMatrixValidator> mMatrixValidatorStack = new msStack<MathObjectMatrixValidator>();
}

