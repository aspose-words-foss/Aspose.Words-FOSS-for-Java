// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/09/2010 by Alexey Morozov
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// This interface is intended to provide format-independed access to document parts.
/// Used in XmlDsig verification.
/// </summary>
interface IReferenceResolver
{
    /// <summary>
    /// Returns Stream object resolved by reference Uri.
    /// </summary>
    public MemoryStream resolve(Reference reference) throws Exception;
}

