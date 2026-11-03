// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// The comparison evaluation result.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
public final class ComparisonEvaluationResult
{
    /// <summary>
    /// Creates a comparison evaluation result.
    /// </summary>
    public ComparisonEvaluationResult(boolean result)
    {
        mResult = result;
    }

    /// <summary>
    /// Creates a failed comparison evaluation result with the corresponding error message.
    /// </summary>
    public ComparisonEvaluationResult(String errorMessage)
    {
        mErrorMessage = errorMessage;
    }

    /// <summary>
    /// Gets the comparison evaluation result.
    /// </summary>
    public boolean getResult() { return mResult; };

    private  boolean mResult;

    /// <summary>
    /// Gets the failed comparison evaluation result's error message.
    /// </summary>
    public String getErrorMessage() { return mErrorMessage; };

    private  String mErrorMessage;
}

