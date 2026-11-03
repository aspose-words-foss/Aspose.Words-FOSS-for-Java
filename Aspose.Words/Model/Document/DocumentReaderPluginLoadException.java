// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2020 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Thrown during document load, when the plugin required for reading the document format cannot be loaded.
/// </summary>
public class DocumentReaderPluginLoadException extends Exception
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    DocumentReaderPluginLoadException(String message)
    {
    	super(message);
	
    }

    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="message">The exception message.</param>
    /// <param name="innerException">The inner exception.</param>
    /// <javaName>DocumentReaderPluginLoadException(java.lang.String message, java.lang.Exception innerException)</javaName>
    DocumentReaderPluginLoadException(String message, Exception innerException)
    {
    	super(message, innerException);
	
    }
}

