package com.aspose;

import com.aspose.ms.System.msNumber;

/// <summary>Represents a <see cref="uint"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableUInt32
{
	public NullableUInt32(){}

    private /*final*/ /*uint*/int mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableUInt32" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableUInt32(/*uint*/int value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableUInt32" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableUInt32" /> object has a value; false if the current <see cref="NullableUInt32" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableUInt32" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableUInt32" /> object if the <see cref="NullableUInt32.HasValue" /> property is true. An exception is thrown if the <see cref="NullableUInt32.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableUInt32.HasValue" /> property is false.</exception>
    public /*uint*/int getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableUInt32 doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableUInt32" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableUInt32.Value" /> property if the  <see cref="NullableUInt32.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableUInt32" /> object.</returns>
    public /*uint*/int getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableUInt32" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableUInt32.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableUInt32.Value" /> property if the <see cref="NullableUInt32.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public /*uint*/int getValueOrDefault(/*uint*/int defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableUInt32" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableUInt32" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableUInt32 && equals((NullableUInt32)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableUInt32" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableUInt32" /> object; otherwise, false.</returns>
    public boolean equals(NullableUInt32 other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(/*uint*/int other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableUInt32.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableUInt32.Value" /> property if the <see cref="NullableUInt32.HasValue" /> property is true, or zero if the <see cref="NullableUInt32.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : msNumber.longHashCode(mValue);
    }

    public static /*final*/ NullableUInt32 NULL = new NullableUInt32();
}
