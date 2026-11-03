// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/01/2020 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public class ProcessExecutionResult
{
    public ProcessExecutionResult(String output, boolean successful, String errors, Exception exception)
    {
        mOutput = output;
        mSuccessful = successful;
        mErrors = errors;
        mException = exception;
    }

    public String getOutput() { return mOutput; }

    public String getErrors() { return mErrors; }

    public Exception getException() { return mException; }

    public boolean getSuccessful() { return mSuccessful; }

    private /*final*/ String mOutput;
    private /*final*/ boolean mSuccessful;
    private /*final*/ String mErrors;
    private /*final*/ Exception mException;
}

