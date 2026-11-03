// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/07/2013 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Represents an individual schema within the Schema Library <see cref="XmlNamespaceCollection" />.
/// </summary>
class XmlNamespace implements Cloneable
{
    /// <summary>
    /// Creates schema object.
    /// </summary>
    /// <param name="uri">Target namespace for the XML Schema associated with this schema reference.</param>
    /// <param name="manifestLocation">Location of a supplementary XML file.</param>
    /// <param name="schemaLanguage">Media type or the root namespace of the schema language.</param>
    /// <param name="schemaLocation">Location of the XML schema file.</param>
    XmlNamespace(String uri, String manifestLocation, String schemaLanguage, String schemaLocation)
    {
        ArgumentUtil.checkHasChars(uri, "uri");

        mUri = uri;
        mManifestLocation = manifestLocation;
        mSchemaLanguage = schemaLanguage;
        mSchemaLocation = schemaLocation;
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
    String getManifestLocation() { return mManifestLocation; }

    /// <summary>
    /// Specifies the media type or the root namespace of the schema language.
    /// </summary>
    String getSchemaLanguage() { return mSchemaLanguage; }

    /// <summary>
    /// Specifies the location of the XML schema file which should be downloaded and parsed
    /// when this document is loaded.
    /// </summary>
    String getSchemaLocation() { return mSchemaLocation; }

    /// <summary>
    /// Attaches XML schema to document.
    /// </summary>
    void attachToDocument(Document doc)
    {
        // AM. VBA like method. http://msdn.microsoft.com/en-us/library/office/ff191784.aspx Not sure this is really needed.
        doc.getXmlNamespaces().add(this);
        doc.getXmlSchemaReferences().add(this);
    }

    XmlNamespace deepClone()
    {
        return (XmlNamespace)memberwiseClone();
    }

    private /*final*/ String mUri;
    private /*final*/ String mManifestLocation;
    private /*final*/ String mSchemaLanguage;
    private /*final*/ String mSchemaLocation;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

