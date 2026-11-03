//Konstantin Sidorenko 7/11/11

package com.aspose.ms.System;

public class BitConverter
{
	// Converts an array of bytes into a short.
	public static short toInt16 (byte[]value, int startIndex)
	{
		if( value == null)
			throw new NullPointerException("value");

		if (startIndex > value.length - 2)
			throw new IllegalArgumentException("startIndex");

		return (short) ((value[startIndex] & 0xFF) | ((value[startIndex + 1] & 0xFF) << 8));
	}

	// Converts an array of bytes into an int.
	public static int toInt32 (byte[]value, int startIndex)
	{
		if( value == null)
			throw new NullPointerException("value");

		if (startIndex > value.length - 4)
			throw new IllegalArgumentException("startIndex");

		return ((value[startIndex] & 0xFF) | ((value[startIndex + 1] & 0xFF) << 8) |
				((value[startIndex + 2] & 0xFF) << 16) | ((value[startIndex + 3] & 0xFF) << 24));
	}

    // Converts an array of bytes into a uint.
    public static /*uint*/int toUInt32 (byte[]value, int startIndex)
    {
        // no need in masking, toInt32() already does this.
        return toInt32(value, startIndex);
    }

    // Converts an array of bytes into a ushort.
    public static /*ushort*/ short toUInt16 (byte[]value, int startIndex)
    {
        // no need in masking, toInt16() already does this.
        return toInt16(value, startIndex);
    }

    public static long doubleToInt64Bits(double value)
    {
        return Double.doubleToLongBits(value);
    }

    /**
     * http://msdn.microsoft.com/en-US/library/3a733s97.aspx
     * */
    public static String toString(byte[] value)
    {
        if (value == null || value.length < 1)
            return EMPTY_STRING;

        int realLength = value.length;
        int startIndex = 0;

        char[] chArray = new char[realLength * 3];
        int i = 0;
        int index = startIndex;

        for (i = 0; i < realLength * 3; i += 3)
        {
            byte b = value[index++];
            byte[] bytes = String.format("%1$02X", b).getBytes();

            chArray[i]= (char) bytes[0];
            chArray[i+1] = (char) bytes[1];
            chArray[i+2] = '-';
        }
        return new String(chArray, 0, chArray.length - 1);
    }

    public static byte[] getBytes(int value)
    {
        //int 32-bit signed, little-endian
        return  new byte[] {
                (byte) (value),
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24),
        };
    }

    public static byte[] getBytes(short value)
    {
        //int 16-bit signed, little-endian
        return  new byte[] {
                (byte) (value),
                (byte) (value >>> 8),
        };
    }

    public static byte[] getBytes(long value)
    {
        //long 64-bit signed, little-endian
        return new byte[] {
                (byte) (value),
                (byte) (value >>> 8),
                (byte) (value >>> 16),
                (byte) (value >>> 24),
                (byte) (value >>> 32),
                (byte) (value >>> 40),
                (byte) (value >>> 48),
                (byte) (value >>> 56),
        };
    }

    public static byte[] getBytes(float value)
    {
        int intBits = Float.floatToRawIntBits(value);
        return getBytes(intBits);
    }

    private static final String EMPTY_STRING = "";
    private static final String HYPHEN = "-";
    private static final String ZERO = "0";
}
