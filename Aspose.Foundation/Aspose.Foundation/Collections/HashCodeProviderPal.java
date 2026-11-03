// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 08/07/2013 by Ivan Lyagin

package com.aspose;

import com.aspose.ms.System.Guid;

/// <summary>
/// FOR MANUAL PORTING.
/// 
/// Provides platform-dependent methods to retrieve hash codes for values of primitive types in Java
/// and for values of some specific types as strings.
/// </summary>
/// <dev>
/// Although hash code calculation routines are almost the same on both platforms (and hence they could be placed outside 
/// of a PAL class), their work should be as fast as possible, so we use platform-dependent optimizations and that's why 
/// this class exists.
/// 
/// @SK: please, do not box primitive types on Java to get hash codes, otherwise the whole approach does not make any sense.
/// </dev>
public /*static*/ class HashCodeProviderPal
{
    /* Simulation of static class by using private constructor */
    private HashCodeProviderPal()
    {}

    public static int getHashCodeOrdinalIgnoreCase(String value)
    {
        // .Net: return StringComparer.OrdinalIgnoreCase.GetHashCode(value);
        // MSDN: The StringComparer.OrdinalIgnoreCase converts characters of the strings to uppercase using
        // invariant culture, and then performs a simple byte comparison that is independent of language.
        // Let's do the same on java. java.lang.String.hashCode() used as a base:
        int hash = 0;
        for (int i = 0; i < value.length(); i++)
            hash = 31 * hash + toUpperCase(value.charAt(i));

        return hash;
    }

    private static char toUpperCase(char ch)
    {
        if (ch <= 0x7F)
        {
            if (ch >= 'a' && ch <= 'z')
                return (char) (ch ^ 0x20);
            return ch;
        }
        return Character.toUpperCase(ch);
    }

    public static int hashCode(byte value)
    {
        return (int) value;
    }

    public static int hashCode(short value)
    {
        return (int) value;
    }

    public static int hashCode(int value)
    {
        return value;
    }

    public static int hashCode(long value)
    {
        return (int) (value ^ (value >>> 32));
    }

    public static int hashCode(float value)
    {
        return Float.floatToIntBits(value);
    }

    public static int hashCode(double value)
    {
        long bits = Double.doubleToLongBits(value);
        return (int)(bits ^ (bits >>> 32));
    }

    public static int hashCode(boolean value)
    {
        return value ? 1231 : 1237;
    }

    public static int hashCode(char value)
    {
        return (int)value;
    }

    public static int hashCode(Guid value)
    {
        return value.hashCode();
    }
}

