// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// November 2009 by Dmitry Burov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Thrown during different path processing, when path type is not supported by processor class.
/// </summary>
// TODO to dburov: never raised.
class UnsupportedPathTypeException extends IllegalStateException  // Derive from ApplicationException for autoporting to work.
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    UnsupportedPathTypeException(String message)
    {
    	super(message);
	
    }
}

