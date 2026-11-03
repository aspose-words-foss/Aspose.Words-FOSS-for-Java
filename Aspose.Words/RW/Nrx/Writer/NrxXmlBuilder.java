// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2007 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.AnyXmlBuilder;
import com.aspose.ms.System.IO.Stream;
import com.aspose.ms.System.Text.Encoding;
import com.aspose.DrColor;
import com.aspose.StringUtil;
import com.aspose.FormatterPal;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Guid;
import com.aspose.ms.System.DateTimeKind;
import com.aspose.MathUtil;


/// <summary>
/// There seems to be a lot of similarities between how MS Word writes to HTML and WordML.
/// This class is common code for writing to DOCX, WordML and HTML.
/// </summary>
class NrxXmlBuilder extends AnyXmlBuilder
{
    /// <summary>
    /// Ctor.
    /// </summary>
    /// <param name="stream">Stream to write to.</param>
    /// <param name="encoding">Encoding to use.</param>
    /// <param name="isPrettyFormat">True to use pretty formatting for the output XML.
    /// Note that when this is true, spaces could appear between spans, so use this for testing only when write Html.</param>
    /// <param name="useOnOff">If true, then "on/off" are written for boolean values, otherwise "true/false" are written.</param>
    public NrxXmlBuilder(Stream stream, Encoding encoding, boolean isPrettyFormat, boolean useOnOff) throws Exception
    {
    	super(stream, encoding, isPrettyFormat);
	
        mUseOnOff = useOnOff;
    }

    /// <summary>
    /// Writes an element with value. Valid object types are bool, int, string, color.
    /// If value is null or not of valid type - the element is not written.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">Value object. Must be bool, int, string or color to be written.</param>
    void writeVal(String elementName, Object value)
    {
        // Do not write element if the value not set.
        if (value == null)
            return;

        if (value instanceof /*boolean*/Boolean)
        {
            // This MUST be on a separate line for boolean and integer for correct autoporting to Java.
            // Other cases are done in the same way just for consistency.
            boolean boolValue = (/*boolean*/Boolean)value;
            writeVal(elementName, boolValue);
        }
        else if (value instanceof String)
        {
            String stringValue = (String)value;
            writeVal(elementName, stringValue);
        }
        else if (value instanceof /*int*/Integer)
        {
            int intValue = (/*int*/Integer)value;
            writeVal(elementName, intValue);
        }
        else if (value instanceof DrColor)
        {
            DrColor colorValue = (DrColor)value;
            writeVal(elementName, colorValue);
        }
        else
        {
            throw new IllegalStateException("Unknown object type in the WriteVal method.");
        }
    }

    /// <summary>
    /// Writes element with the specified name and a 'val' attribute if provided value string is not null or empty.
    /// Prefix for 'val' is taken from element name.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">String value to write to element's 'val' attribute. Element is written only if the provided string value is not null or empty.</param>
    void writeVal(String elementName,  String value)
    {
        if (StringUtil.hasChars(value))
        {
            startElement(elementName);
            writeAttributeString(getPrefix(elementName) + "val", value);
            endElement();
        }
    }

    /// <summary>
    /// Writes element with the specified name and a 'val' attribute if provided value string is not null. Prefix for 'val' is taken from element name. Empty strings are written.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">String value to write to element's 'val' attribute. Element is written only if the provided string value is not null. Empty strings are written.</param>
    void writeValEvenIfEmpty(String elementName, String value)
    {
        if (value != null)
        {
            startElement(elementName);
            writeAttributeString(getPrefix(elementName) + "val", value);
            endElement();
        }
    }

    void writeValRequired(String elementName, String value)
    {
        if (!StringUtil.hasChars(value))
            throw new IllegalStateException("Attempted to write a null or empty value, but it is required according to the schema.");

        writeVal(elementName, value);
    }

    /// <summary>
    /// Writes an element with the specified name and a color value written to 'val' attribute. Prefix for 'val' is taken from element name.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">Color value to write to element's 'val' attribute. Specified color is converted to WordML color string.</param>
    void writeVal(String elementName, DrColor value)
    {
        writeVal(elementName, NrxXmlUtil.colorToXml(value));
    }

    /// <summary>
    /// Writes an element with the specified name and an integer value written to 'val' attribute. Prefix for 'val' is taken from element name.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">Integer value to write to element's 'val' attribute.</param>
    void writeVal(String elementName, int value)
    {
        writeVal(elementName, FormatterPal.intToXml(value));
    }

    /// <summary>
    /// Writes an element with a 'val' attribute encoded as base64 if the value is not null or an empty array.
    /// </summary>
    void writeVal(String elementName, byte[] value)
    {
        if ((value != null) && (value.length > 0))
            writeVal(elementName,  NrxXmlUtil.bytesToBase64Attribute(value));
    }

    /// <summary>
    /// Writes an element with the specified name and an integer value written to 'val' attribute. Element is written only if the provided value is greater than zero.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">Integer value to write to element's 'val' attribute. Element is written only if the provided value is greater than zero.</param>
    void writeValIfPositive(String elementName, int value)
    {
        if (value > 0)
            writeVal(elementName, FormatterPal.intToXml(value));
    }

    /// <summary>
    /// Writes element with the specified name and "w:val" attribute with "off" value if specified boolean value is false
    /// and "on" value if specified boolean value is true.
    /// </summary>
    /*virtual*/ void writeBoolValExplicit(String elementName, boolean value)
    {
        writeVal(elementName, value ? "on" : "off");
    }

    /// <summary>
    /// Writes an element with the specified name and a boolean value written to 'val' attribute. Prefix for 'val' is taken from element name.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">Boolean value to write to element's 'val' attribute. Writes "off" if false. If true, element is written without the attribute.</param>
    /*virtual*/ void writeVal(String elementName, boolean value)
    {
        if (value)
            writeEmptyElement(elementName); // do not write "on", "on" is default
        else
            writeVal(elementName, "off");
    }

    /// <summary>
    /// Writes an element with the specified name if the provided boolean value is true.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="value">If true, empty element is written. If false, nothing is written.</param>
    void writeValIfTrue(String elementName, boolean value)
    {
        if (value)
            writeEmptyElement(elementName); // do not write "on", "on" is default
    }

    /// <summary>
    /// Writes an element with the "val" attribute if the value is not equal to the specified default value.
    /// Returns true if the value was written.
    /// </summary>
    void writeValIfNotDefault(String elementName, int value, int defaultValue)
    {
        if (value != defaultValue)
            writeVal(elementName, value);
    }

    /// <summary>
    /// Writes element with a specified attribute name/value list.
    /// At least one of the attributes should be non-empty to write the element.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="attrNamesValues">Name/value attribute list</param>
    void writeElementWithAttributes(String elementName, /*params*/ Object... attrNamesValues)
    {
        if (writeElementWithAttributesStart(elementName, attrNamesValues))
            endElement();
    }

    /// <summary>
    /// Writes element with a specified attribute name/value list.
    /// At least one of the attributes should be non-empty to write the element.
    /// </summary>
    /// <param name="elementName">The local name of the element.</param>
    /// <param name="attrNamesValues">Name/value attribute list</param>
    /// <returns>True, if element start was in fact written.</returns>
    boolean writeElementWithAttributesStart(String elementName, /*params*/ Object... attrNamesValues)
    {
        int length = attrNamesValues.length / 2;

        // check if non-empty attribute values present
        boolean elementHasAttributes = false;

        for (int i = 0; i < length; i++)
        {
            Object attrValue = attrNamesValues[i * 2 + 1];
            if (attrValue != null)
            {
                if ( !( (attrValue instanceof String) && ("".equals((String)attrValue)) ) )
                {
                    elementHasAttributes = true;
                    break;
                }
            }
        }

        // return if no non-empty attributes found
        if (!elementHasAttributes)
            return false;

        startElement(elementName);
        for (int i = 0; i < length; i++)
        {
            String attrName = (String)attrNamesValues[i * 2];
            Object attrValue = attrNamesValues[i * 2 + 1];
            writeAttribute(attrName, attrValue);
        }

        return true;
    }

    /// <summary>
    /// Writes attribute with object value. Object is converted to WordML string.
    /// Value object should have string, int, double, bool, Color or DateTime underlying type.
    ///
    /// RK I don't like this. It will be better to have separate methods for this.
    /// </summary>
    /// <remarks>
    /// NOTE: uint is int on Java. So we can't use writeAttribute() overload for uint
    /// since it conflicts with int on Java. Please use writeAttributeUInt() instead.
    /// </remarks>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <param name="value">Object value for the attribute.</param>
    void writeAttribute(String attributeName, Object value)
    {
        // Do not write element if the value not set.
        if (value == null)
            return;

        if (value instanceof String)
        {
            // These has to be on separate lines for autoporting to work correctly.
            String stringValue = (String)value;
            writeAttribute(attributeName, stringValue);
        }
        else if (value instanceof /*int*/Integer)
        {
            int intValue = (/*int*/Integer)value;
            writeAttribute(attributeName, intValue);
        }
        else if (value instanceof /*long*/Long)
        {
            long longValue = (/*long*/Long)value;
            writeAttribute(attributeName, longValue);
        }
        else if (value instanceof /*double*/Double)
        {
            double doubleValue = (/*double*/Double)value;
            writeAttribute(attributeName, doubleValue);
        }
        else if (value instanceof /*boolean*/Boolean)
        {
            boolean boolValue = (/*boolean*/Boolean)value;
            writeAttribute(attributeName, boolValue);
        }
        else if (value instanceof BooleanConstant)
        {
            boolean boolValue = ((BooleanConstant)value).getValueBoolean();
            writeAttribute(attributeName, boolValue);
        }
        else if (value instanceof DrColor)
        {
            DrColor colorValue = (DrColor)value;
            writeAttribute(attributeName, NrxXmlUtil.colorToXml(colorValue));
        }
        else if (value instanceof DateTime)
        {
            DateTime dateValue = (DateTime)value;
            writeAttribute(attributeName, dateValue);
        }
        else
        {
            throw new IllegalStateException("Unknown object type in WriteAttribute method.");
        }
    }

    /// <summary>
    /// Writes attribute with unsigned integer value.
    /// </summary>
    /// <remarks>
    /// NOTE: uint is int on Java. So we can't use writeAttribute() overload for uint
    /// since it conflicted with int on Java.
    /// </remarks>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Unsigned integer value for the attribute.</param>
    void writeAttributeUInt(String attributeName, Object value)
    {
        // Do not write element if the value not set.
        if (value == null)
            return;

        /*uint*/int uintValue = (/*uint*/Integer)value;
        writeAttributeUInt(attributeName, uintValue);
    }

    void writeAttributeUInt(String attributeName, Object value, Object defaultValue)
    {
        // Do not write element if the value not set.
        if (value == null)
            value = defaultValue;

        writeAttributeUInt(attributeName, value);
    }

    /// <summary>
    /// Writes attribute. If the value is null, writes the specified default value. Object is converted to WordML string.
    /// Value object should have string, int, double, bool or Color underlying type.
    /// </summary>
    /// <param name="attributeName">The name of the attribute.</param>
    /// <param name="value">Value for the attribute.</param>
    /// <param name="defaultValue">Default value for the attribute.</param>
    void writeAttribute(String attributeName, Object value, Object defaultValue)
    {
        if (value == null)
            value = defaultValue;

        writeAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with string value.
    /// Attribute is not written if provided string value is null or empty.
    ///
    /// RK I don't like this. It is confusing with WriteAttributeString.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">String value for the attribute.</param>
    void writeAttribute(String attributeName,  String value)
    {
        if (StringUtil.hasChars(value))
            writeAttributeString(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with string value. Attribute is not written if provided string value is null, empty or equals specified default value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">String value for the attribute.</param>
    /// <param name="defaultValue">Default value for the attribute.</param>
    void writeAttributeIfNotDefault(String attributeName, String value, String defaultValue)
    {
        if (!msString.equals(value, defaultValue))
            writeAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with color value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Color value for the attribute.</param>
    void writeAttribute(String attributeName, DrColor value)
    {
        writeAttributeString(attributeName, NrxXmlUtil.colorToXml(value));
    }

    /// <summary>
    /// Writes attribute with integer value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Integer value for the attribute.</param>
    void writeAttribute(String attributeName, int value)
    {
        writeAttributeString(attributeName, FormatterPal.intToXml(value));
    }

    /// <summary>
    /// Writes attribute with unsigned integer value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Unsigned integer value for the attribute.</param>
    void writeAttributeUInt(String attributeName, /*uint*/int value)
    {
        writeAttributeString(attributeName, FormatterPal.uIntToXml(value));
    }

    /// <summary>
    /// Writes attribute with long value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Long value for the attribute.</param>
    void writeAttribute(String attributeName, long value)
    {
        writeAttributeString(attributeName, FormatterPal.longToXml(value));
    }

    /// <summary>
    /// Writes an attribute as base64 encoded data using "&#xA;" as a separator (the way MS Word does).
    /// </summary>
    void writeAttribute(String attributeName, byte[] value)
    {
        writeAttributeString(attributeName, NrxXmlUtil.bytesToBase64Attribute(value));
    }

    /// <summary>
    /// Writes attribute with double value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Double value for the attribute.</param>
    void writeAttribute(String attributeName, double value)
    {
        writeAttributeString(attributeName, FormatterPal.doubleToStr2Decimals(value));
    }

    /// <summary>
    /// Writes attribute with Guid value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Guid value for the attribute.</param>
    void writeAttribute(String attributeName, Guid value)
    {
        // WORDSNET-10971 Format string "B" matches to the regular expression pattern
        // \{[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}\}
        // and corresponds to ST_Guid type of 5.1.12.27, ECMA-376, part 4.
        writeAttributeString(attributeName, value.toString("B"));
    }

    /// <summary>
    /// Writes attribute with DateTime value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">DateTime value for the attribute.</param>
    /// <remarks>
    /// If the value has <see cref="DateTimeKind.Local"/> kind, the value is converted to UTC before writing.
    /// If it has <see cref="DateTimeKind.Unspecified"/> kind, it is written as is but with the Z UTC postfix.
    /// MS Word uses different behavior for different datetime properties: some are written with conversion to UTC,
    /// others without conversion, but with the Z UTC postfix. The behavior of this method is determined by actual
    /// kind of the datetime value.
    /// </remarks>
    void writeAttribute(String attributeName, DateTime value)
    {
        //WORDSJAVA-2406: if data-time is Local - convert it to UTC.
        if (value.getKind() == DateTimeKind.LOCAL)
            value = value.toUniversalTime();

        writeAttribute(attributeName, FormatterPal.dateTimeToXmlUtc(value));
    }

    /// <summary>
    /// Writes attribute with DateTime value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">DateTime value for the attribute.</param>
    /// <param name="targetKind">Behaviour of the method depends on kind of the datetime value. This parameter
    /// allows to redefine behavior without actual changing datetime value. The datetime value is treated as having
    /// targetKind.</param>
    /// <remarks>
    /// MS Word uses different behavior for different datetime properties: some are written with conversion to
    /// UTC, others without conversion, but with the Z UTC postfix.
    /// This method allows to define the desired behaviour without changing <see cref="DateTime.Kind"/> value.
    /// See description of the method <see cref="WriteAttribute(string,DateTime)"/> for more information.
    /// </remarks>
    void writeAttribute(String attributeName, DateTime value, /*DateTimeKind*/long targetKind)
    {
        writeAttribute(attributeName, DateTime.specifyKind(value, targetKind));
    }

    /// <summary>
    /// Writes attribute with double value is it is no equals zero.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Double value for the attribute.</param>
    void writeAttributeIfNotZero(String attributeName, double value)
    {
        if (!MathUtil.isZero(value))
            writeAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with int value is it is no equals zero.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Int value for the attribute.</param>
    void writeAttributeIfNotZero(String attributeName, int value)
    {
        if (value != 0)
            writeAttribute(attributeName, value);
    }

    /// <summary>
    /// Writes attribute with boolean value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Boolean value for the attribute. Writes "on" if true, "off" if false.</param>
    /*virtual*/ void writeAttribute(String attributeName, boolean value)
    {
        if (mUseOnOff)
            writeAttributeString(attributeName, value ? "on" : "off");
        else
            writeAttributeString(attributeName, value ? "true" : "false");
    }

    /// <summary>
    /// Writes attribute with boolean value.
    /// </summary>
    /// <param name="attributeName">The local name of the attribute.</param>
    /// <param name="value">Boolean value for the attribute. Writes "on" if true. If false, the attribute is not written.</param>
    /*virtual*/ void writeAttributeIfTrue(String attributeName, boolean value)
    {
        if (value)
            writeAttributeString(attributeName, (mUseOnOff) ? "on" : "true");
    }

    /// <summary>
    /// Substitutes 'false' boolean value with null.
    /// </summary>
    protected static Object dontWriteOff(boolean value)
    {
        if (value)
            return value;

        return null;
    }

    /// <summary>
    /// Gets namespace prefix from element name. Used to automatically extract prefixes for
    /// 'val' attributes from element names, because elements with 'val' attributes are written
    /// with either 'w' or 'wx' prefixes.
    /// </summary>
    /// <param name="name">Name to get prefix from.</param>
    /// <returns>Returns prefix if found, terminated with colon. Otherwise, returns an empty string.</returns>
    static String getPrefix(String name)
    {
        int separatorPos = name.indexOf(':');

        if (separatorPos > 0)
            return name.substring((0), (0) + (separatorPos + 1));
        else
            return "";
    }

    /// <summary>
    /// Writes element with several Border child elements.
    /// </summary>
    /// <param name="elementName">The name of the element.</param>
    /// <param name="borderNameValuePairs">Sequence of Name/Border pairs.</param>
    void writeBorders(String elementName, /*params*/ Object... borderNameValuePairs) throws Exception
    {
        int length = borderNameValuePairs.length / 2;

        // check if non-empty attribute values present
        boolean elementHasBorders = false;

        for (int i = 0; i < length; i++)
        {
            Border border = (Border)borderNameValuePairs[i * 2 + 1];

            if (border != null && !border.isInherited())
            {
                elementHasBorders = true;
                break;
            }
        }

        // return if no non-empty attributes found
        if (!elementHasBorders)
            return;

        startElement(elementName);

        for (int i = 0; i < length; i++)
            writeBorder((String)borderNameValuePairs[i * 2], (Border)borderNameValuePairs[i * 2 + 1]);

        endElement();
    }

    /// <summary>
    /// Writes border element.
    /// </summary>
    /// <param name="elementName">Name of the border element.</param>
    /// <param name="border">Border value.</param>
    void writeBorder(String elementName, Border border) throws Exception
    {
        if (border == null || border.isInherited())
            return;

        // WORDSNET-11214 MS Word writes 'DistanceFromText' even when LineStyle is 'None'.
        if (border.isNil())
            writeElementWithAttributes(elementName, "w:val", "nil");
        else
            writeBorderCore(elementName, border);
    }

    /// <summary>
    /// Writes border element.
    /// </summary>
    /// <param name="elementName">Name of the border element.</param>
    /// <param name="border">Border value.</param>
    protected /*virtual*/ void writeBorderCore(String elementName, Border border) throws Exception
    {
        // Do nothing by default.
    }

    /// <summary>
    /// Writes w:shd element.
    /// </summary>
    /// <param name="shading">Shading value.</param>
    /*virtual*/ void writeShd(Shading shading) throws Exception
    {
        // Do nothing by default.
    }

    /// <summary>
    /// Writes length patterned element in WordML.
    /// These elements can have different names but similar set of attributes:
    /// 'w:w' attribute holds value for length expressed in units that are specified in 'w:type' attribute.
    /// If length type is not specified - "dxa" (twips) is written by default.
    /// </summary>
    /// <param name="elementName">Element name (with namespace prefix included).</param>
    /// <param name="lengthInTwips">Value, holding length in twips.</param>
    /// <param name="isIsoStrict">Flag indicates that destination format is Docx ISO Strict.</param>
    void writeLength(String elementName, int lengthInTwips, boolean isIsoStrict)
    {
        writeLength(elementName, lengthInTwips, PreferredWidthType.POINTS, isIsoStrict);
    }

    /// <summary>
    /// Writes length patterned element in WordML.
    /// These elements can have different names but similar set of attributes:
    /// 'w:w' attribute holds value for length expressed in units that are specified in 'w:type' attribute.
    /// Value of the 'w:w' attribute is universal measure in ISO Strict format and has units too.
    /// </summary>
    /// <param name="elementName">Element name (with namespace prefix included).</param>
    /// <param name="value">Length value in units that are specified by the <paramref name="type"/> argument:
    /// in twips or 50ths of percent.</param>
    /// <param name="type">Type of <paramref name="value"/>.</param>
    /// <param name="isIsoStrict">Flag indicates that destination format is Docx ISO Strict.</param>
    protected void writeLength(String elementName, int value, /*PreferredWidthType*/int type, boolean isIsoStrict)
    {
        String strValue = (isIsoStrict)
            ? ((type == PreferredWidthType.PERCENT)
                ? percentToXml((double)value / PreferredWidth.PERCENT_FACTOR)
                : toUniversalMeasure(value, NrxUnit.TWIPS, NrxUnit.POINT))
            : FormatterPal.intToXml(value);

        writeElementWithAttributes(
            elementName,
            "w:w", strValue,
            "w:type", StyleConvertUtil.lengthTypeToXml(type));
    }

    /// <summary>
    /// Writes length patterned element to DOCX or WordML.
    /// These elements can have different names but similar set of attributes:
    /// 'w:w' attribute holds value for length expressed in units that are specified in 'w:type' attribute.
    /// Value of the 'w:w' attribute is universal measure in ISO Strict format and has units too.
    /// </summary>
    /// <param name="elementName">Element name (with namespace prefix included).</param>
    /// <param name="length">Length object, holding the length value and type in its properties.</param>
    /// <param name="isIsoStrict">Flag indicates that destination format is Docx ISO Strict.</param>
    void writeLength(String elementName, PreferredWidth length, boolean isIsoStrict)
    {
        // Do not write this element if the value is not set.
        if (length == null)
            return;

        writeLength(elementName, length.getValueRaw(), length.getType(), isIsoStrict);
    }

    void writeMargins(String elementName, PreferredWidth marginTop, PreferredWidth marginLeft,
        PreferredWidth marginBottom, PreferredWidth marginRight, boolean isDocxIsoStrict)
    {
        if ((marginTop != null) || (marginLeft != null) ||
            (marginBottom != null) || (marginRight != null))
        {
            startElement(elementName);
            writeLength("w:top", marginTop, isDocxIsoStrict);
            writeLength((isDocxIsoStrict) ? "w:start" : "w:left", marginLeft, isDocxIsoStrict);
            writeLength("w:bottom", marginBottom, isDocxIsoStrict);
            writeLength((isDocxIsoStrict) ? "w:end" : "w:right", marginRight, isDocxIsoStrict);
            endElement(); //w:tcMar
        }
    }

    /// <summary>
    /// Writes comma delimited set of string values.
    /// </summary>
    void writeSet(String attributeName, String x, String y)
    {
        writeAttribute(attributeName, msString.trimEnd(msString.format("{0},{1}", x, y), ','));
    }

    /// <summary>
    /// Writes comma delimited set of string values.
    /// </summary>
    void writeSet(String attributeName, String x, String y, String z)
    {
        writeAttribute(attributeName, msString.trimEnd(msString.format("{0},{1},{2}", x, y, z), ','));
    }

    /// <summary>
    /// Converts numeric percent value to format defined by ST_Percentage simple type of ISO/IEC 29500-1:2012.
    /// </summary>
    /// <param name="value">Input value in percentage points.</param>
    /// <returns>String value with percent sign.</returns>
    static String percentToXml(double value)
    {
        return FormatterPal.doubleToStrNDecimals(value, 3) + "%";
    }

    /// <summary>
    /// Writes format revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevisionStart(FormatRevision revision, String elementName, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes edit revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevisionStart(EditRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes move revision element start.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevisionStart(MoveRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes revision element end. Provided for symmetry with StartRevision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevisionEnd() throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes numbering revision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevision(ParagraphNumberRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes field numbering revision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevision(FieldNumberRevision revision, int id)
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Write a simple revision mark. As far as I know it is written only inside p -> pPr -> rPr nesting
    /// to indicate an inserted or deleted paragraph.
    /// </summary>
    /*virtual*/ void writeRevision(EditRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes move revision.
    /// Does nothing. Concrete classes to implement if they support revisions.
    /// </summary>
    /*virtual*/ void writeRevision(MoveRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Write a simple cell's revision mark.
    /// </summary>
    /*virtual*/ void writeCellRevision(EditRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes SDT's edit revision range start mark.
    /// </summary>
    /*virtual*/ void writeSdtRevisionStart(EditRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes SDT's move revision range start mark.
    /// </summary>
    /*virtual*/ void writeSdtRevisionStart(MoveRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes SDT's edit revision range end mark.
    /// </summary>
    /*virtual*/ void writeSdtRevisionEnd(EditRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// Writes SDT's move revision range end mark.
    /// </summary>
    /*virtual*/ void writeSdtRevisionEnd(MoveRevision revision, int id) throws Exception
    {
        throw new UnsupportedOperationException("Concrete classes should implement.");
    }

    /// <summary>
    /// DOCX only. Returns true if this builder is writing the styles XML document.
    /// </summary>
    /*virtual*/ boolean isStylesBuilder() { return false; }

    void startMath()
    {
        mMathNestedLevel++;
    }

    void endMath()
    {
        mMathNestedLevel--;
    }

    /// <summary>
    /// Use this counter to track if writer is inside an Office Math object. In this case we will decorate
    /// "r" and "t" tags with "m" prefix e.g. "m:r" and "m:t.
    /// </summary>
    boolean isInMath() { return mMathNestedLevel > 0; }

    /// <summary>
    /// Converts the input value to a form difined by the ST_UniversalMeasure simple type of ISO/IEC 29500-1:2012.
    /// </summary>
    /// <param name="value">Input value.</param>
    /// <param name="sourceUnit">Measure units of the input value.</param>
    /// <param name="targetUnit">Target measure units. The ST_UniversalMeasure simple type supports only
    /// millimeters, centimeters, inches, points and picas.</param>
    /// <returns>Value formatted as universal measure with <paramref name="targetUnit"/>.</returns>
    static String toUniversalMeasure(double value, /*NrxUnit*/int sourceUnit, /*NrxUnit*/int targetUnit)
    {
        String units = unitToString(targetUnit);
        double targetValue = NrxXmlUtil.convertMeasure(value, sourceUnit, targetUnit);

        return FormatterPal.doubleToStr9Decimals(targetValue) + units;
    }

    /// <summary>
    /// Gets string representation of the specified universal measure unit.
    /// </summary>
    private static String unitToString(/*NrxUnit*/int unit)
    {
        switch (unit)
        {
            case NrxUnit.MILLIMETER:
                return "mm";
            case NrxUnit.CENTIMETER:
                return "cm";
            case NrxUnit.INCH:
                return "in";
            case NrxUnit.POINT:
                return "pt";
            case NrxUnit.PICA:
                return "pc";
            default:
                // ISO 29500 does not define other units for universal measure, so throw
                throw new IllegalStateException("Unknown universal measure.");
        }
    }

    /// <summary>
    /// If set to false, allows to enforce ISO29500 specific rule:
    /// The content model of ST_OnOff (ISO29500, Part1, §22.9.2.7) was changed to an xsd:boolean,
    /// removing the values on and off.
    /// </summary>
    private /*final*/ boolean mUseOnOff;

    private int mMathNestedLevel;
}

