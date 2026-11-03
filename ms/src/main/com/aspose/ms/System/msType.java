// 29/09/2014 by Konstantin Sidorenko

package com.aspose.ms.System;

import java.lang.reflect.Modifier;

/**
 * Extension methods for porting System.Type.
 */
public class msType
{
    public static boolean isSealed(Class type)
    {
        return Modifier.isFinal(type.getModifiers());
    }

    public static boolean isStatic(Class type)
    {
        return Modifier.isStatic(type.getModifiers());
    }

    public static boolean isPublic(Class type)
    {
        return Modifier.isPublic(type.getModifiers());
    }

    public static /*TypeCode*/int getTypeCode(Class type)
    {
        if (type == null)
            return TypeCode.EMPTY;

        if (type.isPrimitive())
        {
            if (type == boolean.class)
                return TypeCode.BOOLEAN;

            if (type == int.class)
                return TypeCode.INT_32;

            if (type == char.class)
                return TypeCode.CHAR;

            if (type == byte.class)
                return TypeCode.BYTE;

            if (type == short.class)
                return TypeCode.INT_16;

            if (type == long.class)
                return TypeCode.INT_64;

            if (type == double.class)
                return TypeCode.DOUBLE;

            if (type == float.class)
                return TypeCode.SINGLE;

            if (type == void.class)
                return TypeCode.EMPTY;
        }

        if (type == String.class)
            return TypeCode.STRING;

        // may be java.util.Date?
        if (type == DateTime.class)
            return TypeCode.DATE_TIME;

        // default?
        return TypeCode.OBJECT;
    }

    //JAVA-added
    public static boolean isPrimitiveWrapper(Class type)
    {
        if (type == Boolean.class ||
            type == Integer.class ||
            type == Byte.class    ||
            type == msChar.class  ||
            type == Short.class   ||
            type == Long.class    ||
            type == Double.class  ||
            type == Float.class   ||
            type == Void.class)
            return true;

        return false;
    }

    public static Class getInterface(Class type, String name)
    {
        if (name == null)
            return null;

        Class[] interfaces = type.getInterfaces();
        for (Class iface : interfaces)
        {
            if (name.equals(iface.getSimpleName()))
                return iface;
        }

        return null;
    }
}
