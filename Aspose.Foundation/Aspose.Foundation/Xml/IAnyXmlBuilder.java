// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/01/2014 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.IO.Stream;


public interface IAnyXmlBuilder
{
    /// <summary>
    /// Starts the XML document and the root element.
    /// </summary>
    public void startDocument(String rootElementName) throws Exception;

    /// <summary>
    /// Ends the root element and the XML document itself.
    /// </summary>
    public void endDocument() throws Exception;

    /// <summary>
    /// Writes start of the element with the specified name.
    /// </summary>
    public void startElement(String elementName) throws Exception;

    /// <summary>
    /// Writes end of the current element.
    /// </summary>
    public void endElement() throws Exception;

    /// <summary>
    /// Writes end of the current element.
    /// In debug mode checks the closing element name.
    /// </summary>
    public void endElement(String elementName) throws Exception;

    /// <summary>
    /// Writes the full end tag of the current element.
    /// </summary>
    public void fullEndElement() throws Exception;

    /// <summary>
    /// Writes the full end tag of the current element.
    /// In debug mode checks the closing element name.
    /// </summary>
    public void fullEndElement(String elementName) throws Exception;

    /// <summary>
    /// Writes text as is without entitizing or filtering invalid chars.
    /// </summary>
    public void writeRaw(String text) throws Exception;

    /// <summary>
    /// Writes text as is without entitizing or filtering invalid chars. 
    /// Optionally indents lines of the text to match parent element indentation.
    /// </summary>
    public void writeRaw(String text, boolean indentLinesIfFormattingEnabled) throws Exception;

    /// <summary>
    /// Writes empty element with the specified name.
    /// </summary>
    public void writeEmptyElement(String elementName);

    /// <summary>
    /// Writes element with the specified name and value.
    /// </summary>
    public void writeElement(String elementName, String value);

    /// <summary>
    /// Writes element with the specified name and a string value if the string is not null or empty.
    /// </summary>
    public void writeOptionalElement(String elementName, String value);

    /// <summary>
    /// Writes element with the specified name and a DateTime value.
    /// DateTime is validated (year value should be greater than 1).
    /// If DateTime is invalid the element is not written.
    /// </summary>
    public void writeOptionalElement(String elementName, DateTime value);

    /// <summary>
    /// Writes element with the specified name and an integer value.
    /// </summary>
    public void writeElement(String elementName, int value);

    /// <summary>
    /// Writes the given text content. Filters out invalid characters.
    /// </summary>
    public void writeString(String value) throws Exception;

    /// <summary>
    /// Writes an attribute string if value is not default. Always. Filters out invalid characters.
    /// </summary>
    public void writeAttributeStringIfNotDefault(String name, String value, String defaultValue) throws Exception;

    /// <summary>
    /// Writes an attribute string. Always. Filters out invalid characters.
    /// </summary>
    public void writeAttributeString(String name, String value) throws Exception;

    /// <summary>
    /// Writes MIME-compliant Base64 data. Compliance means that Base64 string is broken into lines, containing
    /// exactly 76 characters each. Only the last line of base64 data can contain less than 76 characters.
    /// </summary>
    public void writeBase64(Stream stream) throws Exception;

    /// <summary>
    /// Writes MIME-compliant Base64 data. Compliance means that Base64 string is broken into lines, containing
    /// exactly 76 characters each. Only the last line of base64 data can contain less than 76 characters.
    /// </summary>
    public void writeBase64(byte[] buffer, int index, int count) throws Exception;
}

