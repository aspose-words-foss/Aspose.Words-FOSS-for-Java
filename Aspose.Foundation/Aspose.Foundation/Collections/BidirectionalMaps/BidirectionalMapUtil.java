// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2013 by Ivan Lyagin

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Provides utility methods shared between bidirectional map classes.
/// </summary>
/*static*/ class BidirectionalMapUtil
{
	/* Simulation of static class by using private constructor */
	private BidirectionalMapUtil()
	{}

    /// <summary>
    /// Validates the given object value. Returns it if it is not null. Otherwise, returns the given
    /// default value if it is not null. Otherwise, throws.
    /// </summary>
    static Object validateValue(Object value, Object defaultValue)
    {
        return (value != null) ? value : validateDefaultValue(defaultValue);
    }

    /// <summary>
    /// Validates the given string value. Returns it if it is not null. Otherwise, returns the given
    /// default value if it is not null. Otherwise, throws.
    /// </summary>
    static String validateValue(String value, String defaultValue)
    {
        return (value != null) ? value : (String)validateDefaultValue(defaultValue);
    }

    /// <summary>
    /// Validates the given integer value. Returns it if it is not equal to <see cref="int.MinValue"/>. 
    /// Otherwise, returns the given default value if it is not equal to <see cref="int.MinValue"/>. 
    /// Otherwise, throws.
    /// </summary>
    static int validateValue(int value, int defaultValue)
    {
        return (value != Integer.MIN_VALUE) ? value : validateDefaultValue(defaultValue);
    }

    private static Object validateDefaultValue(Object defaultValue)
    {
        if (defaultValue != null)
            return defaultValue;

        throw createValueValidationException();
    }

    private static int validateDefaultValue(int defaultValue)
    {
        if (defaultValue != Integer.MIN_VALUE)
            return defaultValue;

        throw createValueValidationException();
    }

    private static IllegalStateException createValueValidationException()
    {
        return new IllegalStateException(
            "A value for the specified key is missing and the default value is not provided.");
    }
}

