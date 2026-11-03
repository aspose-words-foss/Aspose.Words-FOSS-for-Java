// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/10/2019 by Dmitry Sokolov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a web extension custom property.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-office-add-ins/">Work with Office Add-ins</a> documentation article.</para>
/// </summary>
/// <dev>2.2.1 CT_OsfWebExtensionProperty</dev>
public class WebExtensionProperty
{
    /// <summary>
    /// Hide default ctr.
    /// </summary>
    WebExtensionProperty() { }

    /// <summary>
    /// Creates web extension custom property with specified name and value.
    /// </summary>
    /// <param name="name">Property name.</param>
    /// <param name="value">Property value.</param>
    public WebExtensionProperty(String name, String value)
    {
        setName(name);
        setValue(value);
    }

    /// <summary>
    ///  Specifies a custom property name
    /// </summary>
    public String getName() { return mName; }; public void setName(String value) { mName = value; };

    private String mName;

    /// <summary>
    ///  Specifies a custom property value.
    /// </summary>
    public String getValue() { return mValue; }; public void setValue(String value) { mValue = value; };

    private String mValue;
}

