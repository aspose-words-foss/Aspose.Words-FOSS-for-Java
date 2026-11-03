// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 2016/01/20 by Konstantin Sidorenko

package com.aspose.words.net.System;

/**
 * Map for .Net's System.OverflowException.
 */
public class OverflowException extends ArithmeticException
{
    public OverflowException()
    {
        super("Arithmetic operation resulted in an overflow.");
    }

    public OverflowException(String message)
    {
        super(message);
    }

    public OverflowException(String message, Throwable cause)
    {
        super(message);
        super.initCause(cause);
    }
}
