// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 18/01/2008 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;

/// <summary>
/// Represents a single custom XML attribute or a smart tag property.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-content-control-sdt/">Structured Document Tags or Content Control</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Used as an item of a <see cref="CustomXmlPropertyCollection"/> collection.</para>
/// </remarks>
public class CustomXmlProperty implements Cloneable
{
    /// <summary>
    /// Initializes a new instance of this class.
    /// </summary>
    /// <param name="name">The name of the property. Cannot be <c>null</c>.</param>
    /// <param name="uri">The namespace URI of the property. Cannot be <c>null</c>.</param>
    /// <param name="value">The value of the property. Cannot be <c>null</c>.</param>
    public CustomXmlProperty(String name, String uri, String value)
    {
        ArgumentUtil.checkNotNull(name, "name");
        mName = name;

        setUri(uri);
        setValue(value);
    }

    /// <summary>
    /// Specifies the name of the custom XML attribute or smart tag property.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// <para>Default is empty string.</para>
    /// </remarks>
    public String getName() { return mName; }

    /// <summary>
    /// Gets or sets the namespace URI of the custom XML attribute or smart tag property.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// <para>Default is empty string.</para>
    /// </remarks>
    public String getUri() { return mUri; }
    public void setUri(String value)
    {
        ArgumentUtil.checkNotNull(value, "uri");
        mUri = value;
    }

    /// <summary>
    /// Gets or sets the value of the custom XML attribute or smart tag property.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// <para>Default is empty string.</para>
    /// </remarks>
    public String getValue() { return mValue; }
    public void setValue(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mValue = value;
    }

    /// <summary>
    /// Makes a deep clone of this object.
    /// </summary>
    CustomXmlProperty deepClone()
    {
        // Shallow copy is actually enough because all fields are strings.
        return (CustomXmlProperty)memberwiseClone();            
    }

    private /*final*/ String mName;
    private String mUri;
    private String mValue;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

