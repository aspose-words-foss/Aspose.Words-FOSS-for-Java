// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/01/2007 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Thrown during document load, when the document appears to be corrupted and impossible to load.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/programming-with-documents/">Programming with Documents</a> documentation article.</para>
/// </summary>
public class FileCorruptedException extends Exception
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    FileCorruptedException(String message)
    {
    	super(message);
	
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    /// <param name="innerException">The inner exception.</param>
    /// <javaName>FileCorruptedException(java.lang.String message, java.lang.Exception innerException)</javaName>
    private FileCorruptedException(String message, Exception innerException)
    {
    	super(message, innerException);
	
    }

    /// <summary>
    /// Initializes a new instance of this class with the standard error message.
    /// </summary>
    FileCorruptedException(Exception innerException)
    {
    	this("The document appears to be corrupted and cannot be loaded.", innerException);
	
    }
}

