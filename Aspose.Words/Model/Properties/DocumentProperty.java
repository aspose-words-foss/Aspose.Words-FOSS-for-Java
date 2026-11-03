// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2004 by Roman Korchagin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.StringUtil;
import com.aspose.ms.System.DateTime;
import com.aspose.FormatterPal;
import com.aspose.ArrayUtil;


/// <summary>
/// Represents a custom or built-in document property.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/work-with-document-properties/">Work with Document Properties</a> documentation article.</para>
/// </summary>
/// <seealso cref="DocumentPropertyCollection"/>
public class DocumentProperty implements Cloneable
{
    DocumentProperty(String name, Object value)
    {
        ArgumentUtil.checkHasChars(name, "name");

        mName = name;
        this.setValue(value);
    }

    /// <summary>
    /// Returns the name of the property.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c> and cannot be an empty string.</para>
    /// </remarks>
    public String getName() { return mName; }

    /// <summary>
    /// Gets or sets the value of the property.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// </remarks>
    public Object getValue()
    {
        Object value = getValueInternal();

        // We need to detect and convert DateTime into java.util.Date because all we have is an Object here.
        if (value instanceof DateTime)
            return ((DateTime)value).toJava();
        if (getType() == PropertyType.STRING)
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ((String) value).length(); i++) // spare parenthesis for java autoporting
            { // spare parenthesis for java autoporting
            	char ch = ((String) value).charAt(i);
                // WORDSNET-12511 Word replaces characters below 0x09 with its HEX representation while displays it in UI.
                if (ch <= 0x08)
                    msStringBuilder.appendFormat(sb, "_x{0:X4}_", (int)ch);
                else
                    sb.append(ch);
            }

            value = sb.toString();
        }

        return value;
    }
    public void setValue(Object value)
    {
        if (value instanceof java.util.Date)
            value = DateTime.fromJava((java.util.Date)value);

        setValueInternal(value);
    }

    Object getDefaultValueInternal() { return mDefaultValueInternal; }
    void setDefaultValueInternal(Object value) { mDefaultValueInternal = value; }

    /// <summary>
    /// Gets the property value without converting it to java.util.Date for the public API in Java.
    /// </summary>
    Object getValueInternal() { return (mDefaultValueInternal == null) ? mValue : mDefaultValueInternal; }
    void setValueInternal(Object value)
    {
        ArgumentUtil.checkNotNull(value, "value");

        if (getPropertyTypeFromValue(value) == PropertyType.OTHER)
            throw new IllegalArgumentException("The type of the value is not supported for a document property value.");

        mValue = value;
        mDefaultValueInternal = null;
    }

    /// <summary>
    /// Gets the data type of the property.
    /// </summary>
    
    public /*PropertyType*/int getType() { return getPropertyTypeFromValue(getValueInternal()); }

    /// <summary>
    /// Gets the source of a linked custom document property.
    /// </summary>
    public String getLinkSource() { return mLinkTarget; }

    /// <summary>
    /// Shows whether this property is linked to content or not.
    /// </summary>
    public boolean isLinkToContent()
    {
        return (StringUtil.hasChars(mLinkTarget));
    }

    /// <summary>
    /// Specifies the name of a bookmark in the current document to which the value
    /// of this custom document property is linked.
    /// </summary>
    /// <remarks>
    /// <para>Cannot be <c>null</c>.</para>
    /// </remarks>
    String getLinkTarget() { return mLinkTarget; }
    void setLinkTarget(String value)
    {
        ArgumentUtil.checkNotNull(value, "value");
        mLinkTarget = value;
    }

    /// <summary>
    /// Returns the property value as a string formatted according to the current locale.
    /// </summary>
    /// <remarks>
    /// <p>Converts a boolean property into "Y" or "N".
    /// Converts a date property into a short date string.
    /// For all other types converts a property using Object.ToString().</p>
    /// </remarks>
    public /*override*/ String toString()
    {
        switch (getType())
        {
            case PropertyType.BOOLEAN:
                // This is what MS Word returns;
                return ((/*boolean*/Boolean)getValueInternal()) ? "Y" : "N";
            case PropertyType.DATE_TIME:
                // Return only the date part since no time part is stored in a date time property.
                return ((DateTime)getValueInternal()).toShortDateString();
            case PropertyType.DOUBLE:
                // TestDocumentInformation.testJira12287Original()
                // .Net formats '1.0' as '1'. Let's Java does the same.
                return FormatterPal.doubleToStr((/*double*/Double) getValueInternal());
            case PropertyType.STRING:
            case PropertyType.NUMBER:
            default:
                return getValueInternal().toString();
        }
    }

    /// <summary>
    /// Returns the property value as integer.
    /// </summary>
    /// <remarks>
    /// Throws an exception if the property type is not <see cref="PropertyType.Number"/>.
    /// </remarks>
    public int toInt()
    {
        return (/*int*/Integer)getValueInternal();
    }

    /// <summary>
    /// Returns the property value as double.
    /// </summary>
    /// <remarks>
    /// Throws an exception if the property type is not <see cref="PropertyType.Number"/>.
    /// </remarks>
    public double toDouble()
    {
        return (/*double*/Double)getValueInternal();
    }

    /// <summary>
    /// Returns the property value as <b>DateTime</b> in UTC.
    /// </summary>
    /// <remarks>
    /// <p>Throws an exception if the property type is not <see cref="PropertyType.DateTime"/>.</p>
    /// <p>Microsoft Word stores only the date part (no time) for custom date properties.</p>
    /// </remarks>
    //JAVA: method is internalized
	DateTime toDateTimeInternal()
    {
        return (DateTime)getValueInternal();
    }

	//JAVA-added public wrapper for internalized method
	public java.util.Date toDateTime()
	{
		return DateTime.toJava(toDateTimeInternal());
	}

    /// <summary>
    /// Returns the property value as bool.
    /// </summary>
    /// <remarks>
    /// <p>Throws an exception if the property type is not <see cref="PropertyType.Boolean"/>.</p>
    /// </remarks>
    public boolean toBool()
    {
        return (/*boolean*/Boolean)getValueInternal();
    }

    /// <summary>
    /// Returns the property value as byte array.
    /// </summary>
    /// <remarks>
    /// <p>Throws an exception if the property type is not <see cref="PropertyType.ByteArray"/>.</p>
    /// </remarks>
    public byte[] toByteArray()
    {
        return (byte[])getValueInternal();
    }

    /// <summary>
    /// These are typed methods for setting the value. In our code in AW we use these methods instead of settings the object Value
    /// property. This allows proper autoporting to Java and ensures .NET DateTime is stored instead of Java's Date.
    /// </summary>
    void fromString(String value)
    {
        setValueInternal(value);
    }

    void fromInt(int value)
    {
        setValueInternal(value);
    }

    void fromDouble(double value)
    {
        setValueInternal(value);
    }

    void fromDateTime(DateTime value)
    {
        setValueInternal(value);
    }

    void fromBool(boolean value)
    {
        setValueInternal(value);
    }

    void fromByteArray(byte[] data)
    {
        setValueInternal(data);
    }

    /// <summary>
    /// Makes a copy of the object.
    /// </summary>
    DocumentProperty deepClone()
    {
        // Memberwise copy is enough because all member variables are immutable.
        return (DocumentProperty)this.memberwiseClone();
    }

    static /*PropertyType*/int getPropertyTypeFromValue(Object value)
    {
        if (value instanceof String)
            return PropertyType.STRING;
        else if (value instanceof /*boolean*/Boolean)
            return PropertyType.BOOLEAN;
        else if (value instanceof DateTime)
            return PropertyType.DATE_TIME;
        else if ((value instanceof /*int*/Integer) || (value instanceof /*uint*/Integer))
            return PropertyType.NUMBER;
        else if (value instanceof /*double*/Double)
            return PropertyType.DOUBLE;
        else if (value instanceof String[])
            return PropertyType.STRING_ARRAY;
        else if (value instanceof Object[])
            return PropertyType.OBJECT_ARRAY;
        else if (value instanceof byte[])
            return PropertyType.BYTE_ARRAY;
        else
            return PropertyType.OTHER;
    }

    static Object getDefaultValueForPropertyType(/*PropertyType*/int propType)
    {
        switch (propType)
        {
            case PropertyType.STRING:
                return "";
            case PropertyType.NUMBER:
                return 0;
            case PropertyType.DOUBLE:
                return 0d;
            case PropertyType.BOOLEAN:
                return false;
            case PropertyType.DATE_TIME:
                return DateTime.MinValue;
            case PropertyType.STRING_ARRAY:
                return ArrayUtil.EMPTY_STRING_ARRAY;
            case PropertyType.OBJECT_ARRAY:
                return ArrayUtil.EMPTY_OBJECT_ARRAY;
            case PropertyType.BYTE_ARRAY:
                return ArrayUtil.EMPTY_BYTE_ARRAY;
            case PropertyType.OTHER:
                return null;
            default:
                throw new IllegalStateException("Unknown property type.");
        }
    }

    private /*final*/ String mName;
    private Object mValue;
    private String mLinkTarget = "";
    private Object mDefaultValueInternal = null;

    /// <summary>
    /// 0-1 IDs are reserved by structured storage.
    /// </summary>
    static final int MIN_USER_PROP_ID = 0x00000002;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

