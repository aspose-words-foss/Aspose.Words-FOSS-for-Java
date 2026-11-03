// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/02/2017 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.DrColor;


/// <summary>
/// This class extends NrxXmlBuilder and helps to write VML-specific attributes.
/// </summary>
class VmlBuilder
{
    VmlBuilder(NrxXmlBuilder builder)
    {
        Debug.assertTrue(builder != null);
        mBuilder = builder;
    }

    /// <summary>
    /// Writes attribute with object value. Object is converted to VML string.
    /// Value object should have string, int, double, bool or Color underlying type.
    /// </summary>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <param name="value">Object value for the attribute.</param>
    /// <param name="defaultValue">Default value for the attribute. If provided object has default value, the attribute is not written.</param>
    void writeVmlAttributeIfNotDefault(String attributeName, Object value, Object defaultValue)
    {
        if (value != null && !value.equals(defaultValue))
            writeVmlAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with object value. Object is converted to VML string.
    /// Value object should have string, int, double, bool or Color underlying type.
    /// </summary>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <param name="value">Object value for the attribute.</param>
    void writeVmlAttribute(String attributeName, Object value)
    {
        if (value instanceof /*boolean*/Boolean)
            mBuilder.writeAttribute(attributeName, (/*boolean*/Boolean)value ? "t" : "f");
        else if (value instanceof DrColor)
            mBuilder.writeAttribute(attributeName, VmlColor.colorToVml((DrColor)value));
        else
            mBuilder.writeAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes VML attribute with bool value as 't' or 'f'. Attribute is not written if value equlas specified default value.
    /// </summary>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <param name="value">Bool value for the attribute.</param>
    /// <param name="defaultValue">Default value for the attribute.</param>
    void writeVmlAttributeIfNotDefault(String attributeName, boolean value, boolean defaultValue)
    {
        if (value != defaultValue)
            mBuilder.writeAttributeString(attributeName, value ? "t" : "f");
    }

    private /*final*/ NrxXmlBuilder mBuilder;
}

