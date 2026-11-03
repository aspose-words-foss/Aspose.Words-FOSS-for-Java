// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Thrown during document load, when the document format is not recognized or not supported by Aspose.Words.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
public class UnsupportedFileFormatException extends Exception
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    UnsupportedFileFormatException(String message)
    {
    	super(message);
	
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    /// <param name="innerException">The inner exception.</param>
    /// <javaName>UnsupportedFileFormatException(java.lang.String message, java.lang.Exception innerException)</javaName>
    UnsupportedFileFormatException(String message, Exception innerException)
    {
    	super(message, innerException);
	
    }
}

