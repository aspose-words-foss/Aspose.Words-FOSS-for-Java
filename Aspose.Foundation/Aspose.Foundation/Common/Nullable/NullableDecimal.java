package com.aspose;

import java.math.BigDecimal;

/// <summary>Represents a <see cref="decimal"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableDecimal
{
	public NullableDecimal(){}

    private /*final*/ BigDecimal mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableDecimal" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableDecimal(BigDecimal value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableDecimal" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableDecimal" /> object has a value; false if the current <see cref="NullableDecimal" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableDecimal" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableDecimal" /> object if the <see cref="NullableDecimal.HasValue" /> property is true. An exception is thrown if the <see cref="NullableDecimal.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableDecimal.HasValue" /> property is false.</exception>
    public BigDecimal getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableDecimal doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableDecimal" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableDecimal.Value" /> property if the  <see cref="NullableDecimal.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableDecimal" /> object.</returns>
    public BigDecimal getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableDecimal" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableDecimal.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableDecimal.Value" /> property if the <see cref="NullableDecimal.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public BigDecimal getValueOrDefault(BigDecimal defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableDecimal" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableDecimal" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableDecimal && equals((NullableDecimal)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableDecimal" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableDecimal" /> object; otherwise, false.</returns>
    public boolean equals(NullableDecimal other)
    {
	    return (hasValue() == other.hasValue()) && (mValue.equals(other.mValue));
    }

    public boolean equals(BigDecimal other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue.equals(other);
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableDecimal.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableDecimal.Value" /> property if the <see cref="NullableDecimal.HasValue" /> property is true, or zero if the <see cref="NullableDecimal.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : mValue.hashCode();
    }

    public static /*final*/ NullableDecimal NULL = new NullableDecimal();
}
