// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/09/2021 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Implements ability to wrap a client exception.
/// I.e. it allows to re-throw client original exception in the case of cancellation from callback.
/// </summary>
class CancellationException extends Exception
{
    CancellationException(Exception innerException)
    {
    	super("Processing was canceled from progress callback.", innerException);
	

    }
}

