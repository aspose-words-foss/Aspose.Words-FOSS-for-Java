// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Empty SDT.
/// </summary>
/// <remarks>
/// Specification allows cases when Sdt has no associated <see cref="SdtType"/>. In this case we store 
/// instance of this class in the parent <see cref="StructuredDocumentTag"/>.</remarks>
class SdtEmpty extends SdtControlProperties
{
    /*override*/ /*SdtType*/int getType() { return SdtType.NONE; }
}

