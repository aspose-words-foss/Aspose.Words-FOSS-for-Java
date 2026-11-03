// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2018 by Dmitry Burov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


/// <summary>
/// Thrown while trying to create a bitmap. It may occur due to different reasons such as invalid parameters width, height, pixel format, etc.
/// </summary>
/// <remarks>
/// Placed here in non-public API because it should be catched and converted to warning. <see cref="ImageShapeWriter.Write()"/>.
/// </remarks>
public class CantCreateBitmapException extends IllegalStateException // Derive from ApplicationException for autoporting to work.
{
    public CantCreateBitmapException(Exception originalException)
    {
    	super(EXCEPTION_MESSAGE, originalException);
	
    }

    public CantCreateBitmapException(String message)
    {
    	super(msString.format("{0}: {1}", EXCEPTION_MESSAGE, message));
	
    }

    private static final String EXCEPTION_MESSAGE = "Could not create the bitmap with the specified parameters. Possible lack of system resources.";
}

