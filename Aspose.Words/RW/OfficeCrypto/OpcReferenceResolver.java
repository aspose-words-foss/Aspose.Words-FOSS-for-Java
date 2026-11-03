// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/09/2010 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.OpcPackageBase;
import com.aspose.ms.System.IO.MemoryStream;


/// <summary>
/// Implements interface for resolving OpcPackage parts. Used by the digital signature verification.
/// </summary>
class OpcReferenceResolver implements IReferenceResolver
{
    OpcReferenceResolver(OpcPackageBase _package)
    {
        mPackage = _package;
    }

    /// <summary>
    /// Resolve needed document part from OPC package.
    /// </summary>
    public MemoryStream resolve(Reference reference) throws Exception
    {
        return mPackage.getParts().get(reference.getName()).getAsMemoryStream();
    }

    private /*final*/ OpcPackageBase mPackage;
}
