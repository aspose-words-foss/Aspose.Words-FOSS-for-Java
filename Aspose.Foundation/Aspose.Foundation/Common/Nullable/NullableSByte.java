package com.aspose;

/// <summary>Represents a <see cref="sbyte"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableSByte
{
	public NullableSByte(){}

    private /*final*/ byte mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableSByte" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableSByte(byte value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableSByte" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableSByte" /> object has a value; false if the current <see cref="NullableSByte" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableSByte" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableSByte" /> object if the <see cref="NullableSByte.HasValue" /> property is true. An exception is thrown if the <see cref="NullableSByte.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableSByte.HasValue" /> property is false.</exception>
    public byte getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableSByte doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableSByte" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableSByte.Value" /> property if the  <see cref="NullableSByte.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableSByte" /> object.</returns>
    public byte getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableSByte" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableSByte.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableSByte.Value" /> property if the <see cref="NullableSByte.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public byte getValueOrDefault(byte defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableSByte" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableSByte" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableSByte && equals((NullableSByte)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableSByte" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableSByte" /> object; otherwise, false.</returns>
    public boolean equals(NullableSByte other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(byte other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableSByte.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableSByte.Value" /> property if the <see cref="NullableSByte.HasValue" /> property is true, or zero if the <see cref="NullableSByte.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : (/*sbyte.hashCode()*/(int) (mValue));
    }

    public static /*final*/ NullableSByte NULL = new NullableSByte();
}
