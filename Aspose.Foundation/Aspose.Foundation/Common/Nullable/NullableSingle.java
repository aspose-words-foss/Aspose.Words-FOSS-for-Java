package com.aspose;

/// <summary>Represents a <see cref="float"/> value type that can be assigned null.</summary>
public /*struct*/ final class NullableSingle
{
	public NullableSingle(){}

    private /*final*/ float mValue;

    /// <summary>Initializes a new instance of the <see cref="NullableSingle" /> structure to the specified value. </summary>
    /// <param name="value">A value type.</param>
    public NullableSingle(float value)
    {
        mValue = value;
        mHasValue = true;
    }

    /// <summary>Gets a value indicating whether the current <see cref="NullableSingle" /> object has a valid value of its underlying type. </summary>
    /// <returns>true if the current <see cref="NullableSingle" /> object has a value; false if the current <see cref="NullableSingle" /> object has no value.</returns>
    public boolean hasValue() { return mHasValue; };

    private  boolean mHasValue;

    /// <summary>Gets the value of the current <see cref="NullableSingle" /> object if it has been assigned a valid underlying value.</summary>
    /// <returns>The value of the current <see cref="NullableSingle" /> object if the <see cref="NullableSingle.HasValue" /> property is true. An exception is thrown if the <see cref="NullableSingle.HasValue" /> property is false.</returns>
    /// <exception cref="System.InvalidOperationException">The <see cref="NullableSingle.HasValue" /> property is false.</exception>
    public float getValue()
    {
        if (!hasValue())
            throw new IllegalStateException("NullableSingle doesn't have a value.");
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableSingle" /> object, or the object's default value.</summary>
    /// <returns>The value of the <see cref="NullableSingle.Value" /> property if the  <see cref="NullableSingle.HasValue" /> property is true; otherwise, the default value of the current <see cref="NullableSingle" /> object.</returns>
    public float getValueOrDefault()
    {
        return mValue;
    }

    /// <summary>Retrieves the value of the current <see cref="NullableSingle" /> object, or the specified default value.</summary>
    /// <param name="defaultValue">A value to return if the <see cref="NullableSingle.HasValue" /> property is false.</param>
    /// <returns>The value of the <see cref="NullableSingle.Value" /> property if the <see cref="NullableSingle.HasValue" /> property is true; otherwise, the <paramref name="defaultValue" /> parameter.</returns>
    public float getValueOrDefault(float defaultValue)
    {
        return !hasValue()
            ? defaultValue
            : mValue;
    }

    /// <summary>Indicates whether the current <see cref="NullableSingle" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableSingle" /> object; otherwise, false.</returns>
    public /*override*/ boolean equals(Object other)
    {
        return other instanceof NullableSingle && equals((NullableSingle)other);
    }

    /// <summary>Indicates whether the current <see cref="NullableSingle" /> object is equal to a specified object.</summary>
    /// <param name="other">An object.</param>
    /// <returns>true if the <paramref name="other" /> parameter is equal to the current <see cref="NullableSingle" /> object; otherwise, false.</returns>
    public boolean equals(NullableSingle other)
    {
	    return (hasValue() == other.hasValue()) && (mValue == other.mValue);
    }

    public boolean equals(float other)
    {
        if (!hasValue())
        {
            return false;
        }

        return mValue == other;
    }

    /// <summary>Retrieves the hash code of the object returned by the <see cref="NullableSingle.Value" /> property.</summary>
    /// <returns>The hash code of the object returned by the <see cref="NullableSingle.Value" /> property if the <see cref="NullableSingle.HasValue" /> property is true, or zero if the <see cref="NullableSingle.HasValue" /> property is false. </returns>
    public /*override*/ int hashCode()
    {
        return !hasValue()
            ? 0
            : /*float.hashCode()*/Float.floatToIntBits(mValue);
    }

    public static /*final*/ NullableSingle NULL = new NullableSingle();
}
