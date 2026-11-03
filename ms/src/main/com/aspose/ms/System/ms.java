//sk 19/01/2010

package com.aspose.ms.System;

public class ms
{
	/**
	 * Emulates .net as operator:
	 *
	 * s = obj as string
	 * -->
	 * s = m$.as(obj, String.class)
	 *
	 * Note: this as like the .Net original doesn't work with
	 * primitive types.
	 */
	public static <T> T as(Object obj, Class<T> type)
	{
		return type.isInstance(obj) ? (T)obj : null;
	}
	
	public static String nameof(String value)
	{
		return value;
	}

	// >>> Hardcoded methods for .Net sizeof() emulation. NOTE: size in _bytes_:

	public static int sizeofByte()
	{
		return 1;
	}

	public static int sizeofSByte()
	{
		return 1;
	}

	public static int sizeofChar()
	{
		return Character.SIZE / Byte.SIZE;
	}

	public static int sizeofInt16()
	{
		return Short.SIZE / Byte.SIZE;
	}

	public static int sizeofUInt16()
	{
		return Short.SIZE / Byte.SIZE;
	}

	public static int sizeofInt32()
	{
		return Integer.SIZE / Byte.SIZE;
	}

	public static int sizeofUInt32()
	{
		return Integer.SIZE / Byte.SIZE;
	}

	public static int sizeofSingle()
	{
		return Float.SIZE / Byte.SIZE;
	}

	public static int sizeofInt64()
	{
		return Long.SIZE / Byte.SIZE;
	}

	public static int sizeofUInt64()
	{
		return Long.SIZE / Byte.SIZE;
	}

	public static int sizeofDouble()
	{
		return Double.SIZE / Byte.SIZE;
	}

	// <<< Hardcoded methods for .Net sizeof() emulation.

}
