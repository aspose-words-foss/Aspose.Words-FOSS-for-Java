// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/06/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Base class for all Structured Document Tags control-specific properties.
/// Encapsulates all differences between Sdt controls, allowing to define additional
/// properties and methods in descendant classes.
/// </summary>
/// <remarks>
/// All common Sdt properties are handled in <see cref="StructuredDocumentTag"/>.
/// </remarks>
abstract class SdtControlProperties implements Cloneable
{
    abstract /*SdtType*/int getType();

    /// <summary>
    /// Deep clone. You need to override when needed.
    /// </summary>
    /*virtual*/ SdtControlProperties deepClone()
    {
        return (SdtControlProperties)memberwiseClone();
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

