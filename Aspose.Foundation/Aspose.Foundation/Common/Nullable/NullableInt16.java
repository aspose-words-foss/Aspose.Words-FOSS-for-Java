package com.aspose;

/// <summary>Represents a <see cref="short"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableInt16
{
	public NullableInt16(){}

    private /*final*/ short mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableInt16" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableInt16(short value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableInt16" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableInt16" /> object has a value; false if the current <see cref="NullableInt16" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableInt16" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableInt16" /> object if the <see cref="NullableInt16.HasValue" /> property is true. An exception is thrown if the <see cref="NullableInt16.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableInt16.HasValue" /> property is false.</exception>
    public short getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableInt16 doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableInt16" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableInt16.Value" /> property if the  <see cref="NullableInt16.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableInt16" /> object.</returns>
    public short getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableInt16" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableInt16.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableInt16.Value" /> property if the <see cref="NullableInt16.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public short getValueOrDefault(short defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableInt16" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableInt16" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableInt16 && equals((NullableInt16)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableInt16" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableInt16" /> object; otherwise, false.</returns>
    public boolean equals(NullableInt16 other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(short other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableInt16.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableInt16.Value" /> property if the <see cref="NullableInt16.HasValue" /> property is true, or zero if the <see cref="NullableInt16.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : (/*short.hashCode()*/(int) (mValue));
    }

    public static /*final*/ NullableInt16 NULL = new NullableInt16();
}
