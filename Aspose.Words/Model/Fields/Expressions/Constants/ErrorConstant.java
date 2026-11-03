// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;

/// <summary>
/// Represents a constant whose value is a result of an erroneous evaluation.
/// </summary>
class ErrorConstant extends Constant
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="errorMessage"></param>
    ErrorConstant(String errorMessage)
    {
        mValueString = errorMessage;
    }

    static ErrorConstant createSyntaxError()
    {
        return new ErrorConstant("!Syntax Error");
    }

    static ErrorConstant createBookmarkError(String bookmarkName)
    {
        return new ErrorConstant(msString.format("!Undefined Bookmark, {0}", bookmarkName.toUpperCase()));
    }

    /*override*/ String getValueString() { return mValueString; };

    private  String mValueString;

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.ERROR; }
}

