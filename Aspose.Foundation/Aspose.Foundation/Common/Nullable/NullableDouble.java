package com.aspose;

import com.aspose.ms.System.msNumber;

/// <summary>Represents a <see cref="double"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableDouble
{
	public NullableDouble(){}

    private /*final*/ double mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableDouble" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableDouble(double value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableDouble" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableDouble" /> object has a value; false if the current <see cref="NullableDouble" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableDouble" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableDouble" /> object if the <see cref="NullableDouble.HasValue" /> property is true. An exception is thrown if the <see cref="NullableDouble.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableDouble.HasValue" /> property is false.</exception>
    public double getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableDouble doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableDouble" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableDouble.Value" /> property if the  <see cref="NullableDouble.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableDouble" /> object.</returns>
    public double getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableDouble" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableDouble.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableDouble.Value" /> property if the <see cref="NullableDouble.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public double getValueOrDefault(double defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableDouble" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableDouble" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableDouble && equals((NullableDouble)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableDouble" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableDouble" /> object; otherwise, false.</returns>
    public boolean equals(NullableDouble other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(double other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableDouble.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableDouble.Value" /> property if the <see cref="NullableDouble.HasValue" /> property is true, or zero if the <see cref="NullableDouble.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : msNumber.doubleHashCode(mValue);
    }

    public static /*final*/ NullableDouble NULL = new NullableDouble();
}
