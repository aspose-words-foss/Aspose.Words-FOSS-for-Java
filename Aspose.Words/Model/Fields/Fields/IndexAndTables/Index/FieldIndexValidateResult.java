// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2013 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// A lightweight structure to pass field code arguments' validation results at once between methods.
/// </summary>
/// <dev>
/// It is immutable and is not intended to be checked on equality, so it should be autoported without any problems.
/// </dev>
/*struct*/ final class FieldIndexValidateResult
{
	public FieldIndexValidateResult(){}
	
    /// <summary>
    /// Ctor.
    /// </summary>
    FieldIndexValidateResult(String errorMessage, FieldCodeIndex fieldCode, Bookmark bookmark)
    {
        mErrorMessage = errorMessage;
        mFieldCode = fieldCode;
        mBookmark = bookmark;
    }

    /// <summary>
    /// Gets a value indicating if an INDEX field's code contains any invalid argument.
    /// </summary>
    boolean isError() { return (getErrorMessage() != null); }

    /// <summary>
    /// Gets an error message.
    /// </summary>
    String getErrorMessage() { return mErrorMessage; };

    private String mErrorMessage;

    /// <summary>
    /// Gets a corresponding <see cref="FieldCodeIndex"/> object.
    /// </summary>
    FieldCodeIndex getFieldCode() { return mFieldCode; };

    private FieldCodeIndex mFieldCode;

    /// <summary>
    /// Gets a corresponding bookmark.
    /// </summary>
    Bookmark getBookmark() { return mBookmark; };

    private Bookmark mBookmark;
}

