package com.aspose;

import com.aspose.ms.System.msNumber;

/// <summary>Represents a <see cref="long"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableInt64
{
	public NullableInt64(){}

    private /*final*/ long mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableInt64" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableInt64(long value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableInt64" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableInt64" /> object has a value; false if the current <see cref="NullableInt64" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableInt64" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableInt64" /> object if the <see cref="NullableInt64.HasValue" /> property is true. An exception is thrown if the <see cref="NullableInt64.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableInt64.HasValue" /> property is false.</exception>
    public long getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableInt64 doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableInt64" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableInt64.Value" /> property if the  <see cref="NullableInt64.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableInt64" /> object.</returns>
    public long getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableInt64" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableInt64.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableInt64.Value" /> property if the <see cref="NullableInt64.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public long getValueOrDefault(long defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableInt64" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableInt64" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableInt64 && equals((NullableInt64)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableInt64" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableInt64" /> object; otherwise, false.</returns>
    public boolean equals(NullableInt64 other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(long other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableInt64.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableInt64.Value" /> property if the <see cref="NullableInt64.HasValue" /> property is true, or zero if the <see cref="NullableInt64.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : msNumber.longHashCode(mValue);
    }

    public static /*final*/ NullableInt64 NULL = new NullableInt64();
}
