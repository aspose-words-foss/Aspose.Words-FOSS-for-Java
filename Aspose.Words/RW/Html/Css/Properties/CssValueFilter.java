// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/09/2021 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.DrColor;


/// <summary>
/// Implements a CSS value filter that passes (accepts) only values of a specified subset.
/// </summary>
/*static*/ class CssValueFilter
{
	/* Simulation of static class by using private constructor */
	private CssValueFilter()
	{}

    /// <summary>
    /// Creates a filter that accepts values that are accepted by any of the specified sub-filters (logical "OR").
    /// </summary>
    static ICssValueFilter anyOf(/*params*/ ICssValueFilter... filters)
    {
        return new CssAnyOfOtherFiltersFilter(filters);
    }

    /// <summary>
    /// Creates a filter that accepts only the specified CSS value.
    /// </summary>
    static ICssValueFilter value(CssValue value)
    {
        return values(value);
    }

    /// <summary>
    /// Creates a filter that accepts any of the specified CSS values (logical "OR").
    /// </summary>
    static ICssValueFilter values(/*params*/ CssValue... values)
    {
        return new CssValuesFilter(values);
    }

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Number"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter NUMBER = new CssValueTypeFilter(CssValueType.NUMBER);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Length"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter LENGTH = new CssValueTypeFilter(CssValueType.LENGTH);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Length"/> values.
    /// In Quirks mode, this filter also accepts <see cref="CssValueType.Number"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter QUIRKY_LENGTH = new CssQuirkyLengthFilter();

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Percentage"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter PERCENTAGE = new CssValueTypeFilter(CssValueType.PERCENTAGE);

    /// <summary>
    /// A filter that accepts non-negative <see cref="CssValueType.Number"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter NON_NEGATIVE_NUMBER = new CssNonNegativeNumericFilter(CssValueType.NUMBER);

    /// <summary>
    /// A filter that accepts non-negative <see cref="CssValueType.Length"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter NON_NEGATIVE_LENGTH = new CssNonNegativeNumericFilter(CssValueType.LENGTH);

    /// <summary>
    /// A filter that accepts non-negative <see cref="CssValueType.Length"/> values.
    /// In Quirks mode, this filter also accepts non-negative <see cref="CssValueType.Number"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter NON_NEGATIVE_QUIRKY_LENGTH = new CssNonNegativeQuirkyLengthFilter();

    /// <summary>
    /// A filter that accepts non-negative <see cref="CssValueType.Percentage"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter NON_NEGATIVE_PERCENTAGE = new CssNonNegativeNumericFilter(CssValueType.PERCENTAGE);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Identifier"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter IDENTIFIER = new CssValueTypeFilter(CssValueType.IDENTIFIER);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.String"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter STRING = new CssValueTypeFilter(CssValueType.STRING);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Hash"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter HASH = new CssValueTypeFilter(CssValueType.HASH);

    /// <summary>
    /// A filter that accepts color values.
    /// </summary>
    /// <remarks>
    /// Note that color values may be represented by identifiers (color names), hash (hex color values),
    /// or functions ("rgb" or "hsl").
    /// </remarks>
    static /*final*/ ICssValueFilter COLOR = new CssColorFilter();

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Uri"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter URI = new CssValueTypeFilter(CssValueType.URI);

    /// <summary>
    /// A filter that accepts <see cref="CssValueType.Function"/> values.
    /// </summary>
    static /*final*/ ICssValueFilter FUNCTION = new CssValueTypeFilter(CssValueType.FUNCTION);

    /// <summary>
    /// Implements a filter that is a logical "OR" combination of other filters.
    /// </summary>
    private static class CssAnyOfOtherFiltersFilter implements ICssValueFilter
    {
        CssAnyOfOtherFiltersFilter(ICssValueFilter[] filters)
        {
            mFilters = filters;
        }

        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            for (ICssValueFilter filter : mFilters)
            {
                if (filter.accepts(value, isInQuirksMode))
                {
                    return true;
                }
            }
            return false;
        }

        private /*final*/ ICssValueFilter[] mFilters;
    }

    /// <summary>
    /// Implements a filter that accepts only specified concrete CSS values.
    /// </summary>
    private static class CssValuesFilter implements ICssValueFilter
    {
        CssValuesFilter(CssValue[] values)
        {
            mValues = values;
        }

        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            return value.equalsAny(mValues);
        }

        private /*final*/ CssValue[] mValues;
    }

    /// <summary>
    /// Implements a filter that accepts only non-negative numeric values of the specified type.
    /// </summary>
    private static class CssNonNegativeNumericFilter implements ICssValueFilter
    {
        CssNonNegativeNumericFilter(/*CssValueType*/int valueType)
        {
            mValueType = valueType;
        }

        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            return (value.getValueType() == mValueType) && (value.getDoubleValue() >= 0);
        }

        private /*final*/ /*CssValueType*/int mValueType;
    }

    /// <summary>
    /// Implements a filter that accepts lengths. In Quirks mode, this filter also accepts unitless numbers.
    /// </summary>
    private static class CssQuirkyLengthFilter implements ICssValueFilter
    {
        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            return value.isLength(isInQuirksMode);
        }
    }

    /// <summary>
    /// Implements a filter that accepts non-negative lengths.
    /// In Quirks mode, this filter also accepts non-negative unitless numbers.
    /// </summary>
    private static class CssNonNegativeQuirkyLengthFilter implements ICssValueFilter
    {
        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            return value.isLength(isInQuirksMode) && (value.getDoubleValue() >= 0);
        }
    }

    /// <summary>
    /// Implements a filter that accepts only CSS values of the specified type.
    /// </summary>
    private static class CssValueTypeFilter implements ICssValueFilter
    {
        CssValueTypeFilter(/*CssValueType*/int valueType)
        {
            mValueType = valueType;
        }

        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            return value.getValueType() == mValueType;
        }

        private /*final*/ /*CssValueType*/int mValueType;
    }

    /// <summary>
    /// Implements a filter that accepts only CSS values that represent colors.
    /// </summary>
    private static class CssColorFilter implements ICssValueFilter
    {
        public boolean accepts(CssValue value, boolean isInQuirksMode)
        {
            DrColor color = value.parseAsColor();
            return color != null;
        }
    }
}

