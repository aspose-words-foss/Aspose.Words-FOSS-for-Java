package com.aspose.ms.System;
// 25/09/2014 by Konstantin Sidorenko

/**
 * System.TypeCode.
 */
public final class TypeCode
{
    private TypeCode() {}

    public static final int EMPTY = 0; //java void.class?
    public static final int OBJECT = 1;
    public static final int DB_NULL = 2;
    public static final int BOOLEAN = 3;
    public static final int CHAR = 4;
    // Really .Net's sbyte maps to java (signed) byte. But let's use byte here for clarity.
    public static final int S_BYTE = 5;
    public static final int BYTE = 6;
    public static final int INT_16 = 7;
    public static final int U_INT_16 = 8;
    public static final int INT_32 = 9;
    public static final int U_INT_32 = 10;
    public static final int INT_64 = 11;
    public static final int U_INT_64 = 12;
    public static final int SINGLE = 13;
    public static final int DOUBLE = 14;
    public static final int DECIMAL = 15;
    public static final int DATE_TIME = 16;
    public static final int STRING = 18;
}
