// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents an individual schema that is attached to a document.
/// </summary>
class XmlSchemaReference implements Cloneable
{
    XmlSchemaReference(String uri, String location)
    {
        mUri = uri;
        mLocation = location;
    }

    /// <summary>
    /// Specifies the target namespace for the XML Schema associated with this schema reference.
    /// </summary>
    String getUri() { return mUri; }

    /// <summary>
    /// Specifies the location of a supplementary XML file which can be downloaded and parsed
    /// when this document is loaded in order to provide additional application-defined
    /// capabilities.
    /// </summary>
    String getLocation() { return mLocation; }

    XmlSchemaReference deepClone()
    {
        return (XmlSchemaReference)memberwiseClone();
    }

    private /*final*/ String mUri;
    private /*final*/ String mLocation;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

