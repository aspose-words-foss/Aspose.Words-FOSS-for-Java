// Parts of this file are a Java port, made by Aspose, of .NET Framework code. Microsoft publishes that source
// under the MIT License at https://github.com/microsoft/referencesource : mscorlib/system/convert.cs
//
// Those parts: Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License, see dotnet-referencesource-LICENSE.txt in com/aspose/ms/System.

package com.aspose.ms.System;

import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.java.util.Base64;
import com.aspose.ms.java.logging.Log;
import com.aspose.words.net.System.OverflowException;

import java.math.BigInteger;
import java.util.Date;

/**
 * Some C# System.Convert methods.
 */
public final class Convert
{
	public static Boolean toBoolean(Object value, msCultureInfo invariantCulture)
	{
		return value != null && Boolean.parseBoolean(value.toString());
	}

	public static Double toDouble(Object value, msCultureInfo invariantCulture)
	{
		return toDouble(value);
	}

	public static Long toInt64(Object value, msCultureInfo invariantCulture)
	{
		//throw new UnsupportedOperationException(value.getClass() + " culture="  + invariantCulture);
		return value != null ? Long.parseLong(value.toString()) : null;
	}

	/**
	 * Converts the string representation of a number in a specified base to an equivalent 64-bit signed integer.
	 * @param value
	 * @param fromBase The base of the number in value, which must be 2, 8, 10, or 16.
	 * @return A 64-bit signed integer that is equivalent to the number in value, or 0 (zero) if value is null.
	 */
	public static Long toInt64(Object value, Integer fromBase)
	{
		return value != null ? Long.parseLong(value.toString(), fromBase) : null;
	}

	public static DateTime toDateTime(Object value, msCultureInfo invariantCulture)
	{
		if (value == null)
			return DateTime.MinValue;

		if (value instanceof DateTime)
			return (DateTime) value;

		if (value instanceof Date)
			return DateTime.fromJava((Date) value);

		if (value instanceof String)
		{
			try
			{
				return DateTime.parse(value.toString());
			}
			catch (Throwable any)
			{
				Log.warn(any);
			}
		}

		throw new UnsupportedOperationException(value + " class=" + value.getClass());
	}

	private Convert() {}

    // RK This is my very simplistic implementation. Not able to parse strings, can only convert numbers.
    public static double toDouble(Object value)
    {
        if (value == null)
            return 0;
        else if (value instanceof Byte)
            return (Byte)value;
        else if (value instanceof Short)
            return (Short)value;
        else if (value instanceof Integer)
            return (Integer)value;
        else if (value instanceof Long)
            return (Long)value;
        else if (value instanceof Float)
            return (Float)value;
        else if (value instanceof Double)
            return (Double)value;
        else if (value instanceof String)
            return Double.parseDouble((String)value);
        else
            throw new ClassCastException("Cannot convert a value to double.");
    }

    // Conversions to Int16

    public static short toInt16(boolean value)
    {
        return (short) (value? /*Boolean.True*/1: /*Boolean.False*/0);
    }

    public static short toInt16(char value)
    {
        if (value > Short.MAX_VALUE)
            throw new OverflowException("Cannot convert value to short.");

        return (short)value;
    }

    public static short toInt16(/*sbyte*/byte value)
    {
        return value;
    }

    public static short toInt16FromByte(byte value)
    {
        // extend unsigned byte
        return (short) (value & 0xFF);
    }

    public static short toInt16(short value)
    {
        return value;
    }

    public static short toInt16FromUshort(/*ushort*/short value)
    {
        int ushort = value & 0xFFFF;
        if (ushort > Short.MAX_VALUE)
            throw new OverflowException("Cannot convert value to short.");

        return (short)ushort;
    }

    public static short toInt16(int value)
    {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE)
            throw new IllegalArgumentException("Integer value: " + value + " can not be converted to 16-bit short!");

        return (short) value;
    }

    public static short toInt16FromUint(/*uint*/int value)
    {
        long uint = value & 0xFFFFFFFFL;
        if (uint > Short.MAX_VALUE)
            throw new OverflowException("Cannot convert value to short.");

        return (short)uint;
    }

    public static short toInt16(long value)
    {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE)
            throw new OverflowException("Cannot convert value to short.");

        return (short)value;
    }

    public static short toInt16(float value)
    {
        return toInt16((double)value);
    }

    public static short toInt16(double value)
    {
        return toInt16(toInt32(value));
    }

    // Conversions to UInt16

    public static /*ushort*/short toUInt16(boolean value)
    {
        return (short) (value? /*Boolean.True*/1: /*Boolean.False*/0);
    }

    public static /*ushort*/short toUInt16(char value)
    {
        int ushort = value & 0xFFFF;
        return (short) ushort;
    }

    public static /*ushort*/short toUInt16(/*sbyte*/byte value)
    {
        if (value < 0)
            throw new OverflowException("Cannot convert value to ushort.");

        return (short) value;
    }

    public static /*ushort*/short toUInt16FromByte(byte value)
    {
        // extend unsigned byte
        return (short) (value & 0xFF);
    }

    public static /*ushort*/short toUInt16(short value)
    {
        if (value < 0)
            throw new OverflowException("Cannot convert value to ushort.");

        return value;
    }

    public static /*ushort*/short toUInt16(int value)
    {
        if (value < msUShort.MIN_VALUE || value > (msUShort.MAX_VALUE & 0xFFFF))
            throw new IllegalArgumentException("Integer value: " + value + " can not be converted to 16-bit short!");

        // no need for mask since negative values are filtered above.
        return (short) value;
    }

    public static /*ushort*/short toUInt16FromUshort(/*ushort*/short value)
    {
        return value;
    }

    public static /*ushort*/short toUInt16FromUint(/*uint*/int value)
    {
        if (value > (msUShort.MAX_VALUE & 0xFFFF))
            throw new OverflowException("Cannot convert value to ushort.");

        return (short)value;
    }

    public static /*ushort*/short toUInt16(long value)
    {
        if (value < msUShort.MIN_VALUE || value > (msUShort.MAX_VALUE & 0xFFFF))
            throw new OverflowException("Cannot convert value to ushort.");

        return (short)value;
    }

    public static /*ushort*/short toUInt16(float value)
    {
        return toUInt16((double) value);
    }

    public static /*ushort*/short toUInt16(double value)
    {
        return toUInt16(toInt32(value));
    }

    // Conversions to Int32

	public static int toInt32(boolean value)
	{
		return value? /*Boolean.True*/1: /*Boolean.False*/0;
	}

	public static boolean toBoolean(int value)
	{
		if (value == 1)
			return true;
		return false;
	}

	public static int toInt32(char value)
	{
		return value;
	}

	public static int toInt32(/*sbyte*/byte value)
    {
		return value;
	}

	public static int toInt32FromByte(byte value)
    {
        // extend unsigned byte
		return value & 0xFF;
	}

	public static int toInt32(short value)
    {
		return value;
	}

	public static int toInt32FromUshort(/*ushort*/short value)
    {
        // extend unsigned short
		return value & 0xFFFF;
	}

	public static int toInt32FromUint(/*uint*/int value)
    {
		if (value > Integer.MAX_VALUE)
		    throw new OverflowException("Overflow_Int32");

		return (int)value;
	}

	public static int toInt32(int value)
    {
		return value;
	}

	public static int toInt32(long value)
    {
		if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)
		    throw new OverflowException("Overflow_Int32");

		return (int)value;
	}

	public static int toInt32(float value)
    {
		return toInt32((double)value);
	}

	public static int toInt32(double value)
    {
		if (value >= 0)
		{
			if (value < 2147483647.5)
			{
				int result = (int)value;
				double dif = value - result;
				if (dif > 0.5 || dif == 0.5 && (result & 1) != 0) result++;
				return result;
			}
		}
		else
        {
			if (value >= -2147483648.5)
			{
				int result = (int)value;
				double dif = value - result;
				if (dif < -0.5 || dif == -0.5 && (result & 1) != 0) result--;
				return result;
			}
		}
		throw new OverflowException("Overflow_Int32");
	}

	// Parses value in base fromBase.  fromBase can only
	// be 2, 8, 10, or 16.  If fromBase is 16, the number may be preceded
	// by 0x; any other leading or trailing characters cause an error.
	public static int toInt32(String value, int fromBase)
	{
		if (fromBase!=2 && fromBase!=8 && fromBase!=10 && fromBase!=16)
			throw new IllegalArgumentException("fromBase can only be 2, 8, 10, or 16.");

		if (value.startsWith("0x"))
			value = value.replace("0x","");

		value = value.trim();
		// Java throws NumberFormatException both for overflow and bad format.
		return Integer.parseInt(value, fromBase);
	}

	public static int toInt32(Object value, msCultureInfo invariantCulture)
	{
		return toInt32(value);
	}

	public static int toInt32(Object value)
	{
		if (value == null)
			return 0;
		else if (value instanceof Integer)
			return (Integer) value;
		else if (value instanceof Byte || value instanceof Short)
			return ((Number) value).intValue();
		//JAVA we can't use ((Number)value).intValue() here because it don't check overflow (but .Net Convert.ToInt32() checks).
		else if (value instanceof Long)
		{
			if (((Long) value) > Integer.MAX_VALUE || ((Long) value) < Integer.MIN_VALUE)
				throw new OverflowException("Overflow in long to int conversion. long value: " + value + ".");
			else
				return ((Long) value).intValue();
		}
		else if (value instanceof Float)
		{
			if (((Float) value) > Integer.MAX_VALUE || ((Float) value) < Integer.MIN_VALUE)
				throw new OverflowException("Overflow in Float to int conversion. Float value: " + value + ".");
			else
				return ((Float) value).intValue();
		}
		else if (value instanceof Double)
		{
			if (((java.lang.Double) value) > Integer.MAX_VALUE || ((java.lang.Double) value) < Integer.MIN_VALUE)
				throw new OverflowException("Overflow in Double to int conversion. Double value: " + value + ".");
			else
				return ((java.lang.Double) value).intValue();
		}
//			else if (value instanceof BigDecimal) //@todo 2 sk retrotranslator can't find BigDecimal.intValueExact()
//			{
//				try
//				{
//					return ((BigDecimal)value).intValueExact();
//				}
//				catch(java.lang.ArithmeticException e)
//				{
//					throw new ClassCastException("Overflow in BigDecimal to int conversion. BigDecimal value: " + value + ".");
//				}
//			}
		else if (value instanceof BigInteger)
		{
			if (((BigInteger) value).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1 ||
				((BigInteger) value).compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) == -1)
				throw new ClassCastException(
						"Overflow in BigInteger to int conversion. BigInteger value: " + value + ".");
			else
				return ((BigInteger) value).intValue();
		}
		else if (value instanceof java.lang.String)
		{
            String stringValue = (String) value;
            stringValue = stringValue.trim();
            // Java throws NumberFormatException both for overflow and bad format.
			return Integer.parseInt(stringValue);
		}
		else if (value instanceof Boolean)
		{
			return ((Boolean) value) ? 1 : 0;
		}
		else if (value instanceof Character)
		{
			Character.getNumericValue(
					((Character) value).charValue());//@todo 2 sk compare the result with System.Convert.ToInt32(char)
		}
		//JAVA: generic enum option using enum's ordinal.
		else if (value instanceof Enum)
		{
			return ((Enum) value).ordinal();
		}

		throw new OverflowException("Can't convert to Integer value of type '" + value.getClass().getName() + "'.");
	}

	public static long toUInt32(Object value)
	{
		long uint;
		final long UINT_MIN_VALUE = 0;
		final long UINT_MAX_VALUE = 0xffffffffL;

		if (value == null)
			uint = 0;
		else if (value instanceof Integer)
			uint = (Integer) value;
		else if (value instanceof Byte || value instanceof Short)
			uint = ((Number) value).intValue();
		else if (value instanceof Long)
			uint = (Long) value;
		else if (value instanceof Float)
			uint = ((Float) value).longValue();
		else if (value instanceof Double)
			uint = ((java.lang.Double) value).longValue();
		else if (value instanceof BigInteger)
			uint = ((BigInteger) value).longValue();
		else if (value instanceof java.lang.String)
		{
			try
			{
				uint = Long.parseLong((java.lang.String) value);
			}
			catch (NumberFormatException e)
			{
				throw new OverflowException("Overflow in String to uint conversion. String value: " + value + ".");
			}
		}
		else if (value instanceof Boolean)
			uint = ((Boolean) value) ? 1 : 0;
		else if (value instanceof Character)
			uint = Character.getNumericValue(((Character) value).charValue());
		else if (value instanceof Enum)
			uint = ((Enum) value).ordinal();
//		else if (value instanceof BigDecimal) //@todo 2 sk retrotranslator can't find BigDecimal.intValueExact()
//		{
//			try
//			{
//				return ((BigDecimal)value).intValueExact();
//			}
//			catch(java.lang.ArithmeticException e)
//			{
//				throw new ClassCastException("Overflow in BigDecimal to int conversion. BigDecimal value: " + value + ".");
//			}
//		}
		else
			throw new ClassCastException("Can't convert to Integer value of type '" + value.getClass().getName() + "'.");

		if (uint < UINT_MIN_VALUE || uint > UINT_MAX_VALUE)
			throw new OverflowException("Overflow in Object to UInt32 conversion. Object type: " + value.getClass() +
			                             " Object value: " + value + ".");

		return uint;
	}

	public static long toUInt32(int value)
	{
		if (value < 0)
			throw new IllegalArgumentException("Integer value: " + value + " can not be converted to unsigned int!");

		return (long) value;
	}

	public static long toUInt32(long value)
	{
		if (value < 0 || value > 0xffffffffL)
			throw new IllegalArgumentException("Long value: " + value + " can not be converted to unsigned int!");

		return value;
	}

	public static long toUInt32(String value, int base)
	{
		return Long.parseLong(value, base);
	}

	public static String toString(char value)
	{
		return Character.toString(value);
	}

	public static String toString(int value)
	{
		return Integer.toString(value);
	}

	public static char toChar(int value)
	{
		if (value < Character.MIN_VALUE || value > Character.MAX_VALUE)
			throw new IllegalArgumentException("Integer value: " + value + " can not be converted to 16-bit char!");

		return (char) value;
	}

	public static byte[] fromBase64String(java.lang.String encoded)
	{
		return Base64.fromBase64CharPtr(encoded.toCharArray(), encoded.length());
	}

	// Convert an array of bytes into a base64 String.
	public static String toBase64String(byte[] inArray)
	{
		if (inArray == null)
			throw new NullPointerException("inArray");

		return Base64.encodeToString(inArray, false);
	}

	public static String toBase64String(byte[] inArray, int offset, int length)
	{
		// Validate the parameters.
		if (inArray == null)
			throw new NullPointerException("inArray");

		if (offset < 0 || offset > inArray.length)
			throw new IndexOutOfBoundsException("offset");

		if (length < 0 || length > (inArray.length - offset))
			throw new IndexOutOfBoundsException("length");

		return Base64.encodeToString(inArray, offset, length, false);
	}
}
