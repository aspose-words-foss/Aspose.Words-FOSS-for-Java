// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 17/11/2021 by Alexey Maslov

package com.aspose.ms.java.charsets.callback;

public interface CharsetDecoderCallback
{
	/**
	 * Provide surrogate chars for malformed or unmapped bytes
	 */
	char[] getSurrogateChars(CharsetDecoderDecorator decoder, byte[] invalidBytes);
}

