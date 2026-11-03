// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 15/11/2005 by Roman Korchagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

/// <summary>
/// Provides utility methods to work with enum types. Has to be manually ported because reflection is different on different platforms.
/// </summary>
//[JavaManual("EffectiveArrayLength is checked on .Net. Use just default value in Java.")]
public /*static*/ class EnumUtilPal
{
    /* Simulation of static class by using private constructor */
    private EnumUtilPal()
    {}

    //JAVA-added
    public static int getEffectiveArrayLength(int defaultValue)
    {
        return defaultValue;
    }

    //JAVA-deleted
    /*
    /// <summary>
    /// Use just default value in Java.
    /// </summary>
    public static int getEffectiveArrayLength(Class enumType, int defaultValue)
    {
        int calculated = getEffectiveArrayLength(enumType);
        if (defaultValue != calculated)
        {
            throw new IllegalArgumentException(
                "Please update defaultValue of effective arry length: it should be greater then max value of the enum." +
                "\r\nEnum Type: " + enumType.FullName +
                "\r\ndefaultValue: " + defaultValue +
                "\r\ncalculated EffectiveArrayLength: " + calculated);
        }
        return defaultValue;
    }

    /// <summary>
    /// Returns the length of an array which can hold items corresponding to all possible values of the enum of
    /// the specified type. The enum should be non-flag and its min value should be greater or equal to zero.
    /// Note, that calls of this method are quite expensive, so the best place to call it is the initialization 
    /// of static fields of interested classes.
    /// </summary>
    /// <param name="enumType">RK Note that you should not pass "typeof(MyEnum)" here because it causes Easobfuscator to 
    /// avoid renaming that enum. Instead, pass "MyEnum.AnyValue.GetType()" - this seems to work. It looks like circumvents
    /// Easobfuscator's heuristics regarding renaming when reflection is involved.</param>
    public static int getEffectiveArrayLength(Class enumType)
    {
        // Ensure that the specified type is a non-flag enum type. 
        assert enumType.IsEnum;
        assert enumType.GetCustomAttributes(.class, false).length == 0;

        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        int count = 0;

        for (int value : (Iterable<Integer>) enum.GetValues(enumType))
        {
            if (value > maxValue)
                maxValue = value;
            if (value < minValue)
                minValue = value;

            count++;
        }

        // Ensure that the enum has at least one value and its min value is greater or equal to zero.
        assert (count != 0) && (minValue >= 0);

        // Ensure that the enum does not have too large "holes" between its sequential values.
        // Let's use 50% as a threshold for these holes' sum length.
        assert ((double)count) / (maxValue + 1) > 0.5d;

        return (maxValue + 1);
    }
    */

    /// <summary>
    /// FIX 871. The object.Equals(object) always returns false if args are enum and/or int.
    /// Use casting to int to compare enums and ints.
    /// </summary>
    public static boolean enumOrIntEquals(Object objA, Object objB)
    {
        if ((objA instanceof /*int*/Integer) &&
            (objB instanceof /*int*/Integer))
        {
            return ((Integer)objA).intValue() == ((Integer)objB).intValue();
        }

        return objA.equals(objB);
    }
}

