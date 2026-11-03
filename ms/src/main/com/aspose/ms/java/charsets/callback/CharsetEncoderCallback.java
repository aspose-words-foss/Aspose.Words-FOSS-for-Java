// Copyright (c) 2001-2018 Aspose Pty Ltd. All Rights Reserved.
// 01/06/2013 by Pavel Gorbunov

package com.aspose.ms.java.charsets.callback;

public interface CharsetEncoderCallback
{
	/**
	 * Provide surrogate bytes for malformed or unmapped chars
	 */
	byte[] getSurrogateBytes(CharsetEncoderDecorator encoder, char[] invalidChars);
}

