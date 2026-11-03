// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/10/2024 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;

/// <summary>
/// Represents result of the reference expression evaluation.
/// </summary>
class ReferenceExpressionResult
{
    ReferenceExpressionResult(Constant result, boolean hasLeadingMinusOperator)
    {
        Debug.assertTrue(result != null);

        mResult = result;
        mHasLeadingMinusOperator = hasLeadingMinusOperator;
    }

    Constant getResult() { return mResult; };

    private Constant mResult;

    boolean hasLeadingMinusOperator() { return mHasLeadingMinusOperator; };

    private boolean mHasLeadingMinusOperator;
}

