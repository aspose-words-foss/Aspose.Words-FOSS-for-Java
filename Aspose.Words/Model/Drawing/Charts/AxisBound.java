// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/05/2018 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;
import com.aspose.FormatterPal;


/// <summary>
/// Represents minimum or maximum bound of axis values.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-charts/">Working with Charts</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Bound can be specified as a numeric, datetime or a special "auto" value.</para>
/// <para>The instances of this class are immutable.</para>
/// <seealso cref="AxisScaling.Minimum"/>
/// <seealso cref="AxisScaling.Maximum"/>
/// </remarks>
public final class AxisBound
{
    /// <summary>
    /// Creates a new instance indicating that axis bound should be determined automatically by a word-processing
    /// application.
    /// </summary>
    public AxisBound()
    {
        mIsAuto = true;
    }

    /// <summary>
    /// Creates an axis bound represented as a number.
    /// </summary>
    public AxisBound(double value)
    {
        mValue = value;
    }

    /// <summary>
    /// Creates an axis bound represented as datetime value.
    /// </summary>
    //JAVA: ctor is internalized
	AxisBound(DateTime datetime)
    {
        if ((DateTime.lessOrEqual(datetime, MIN_EXCLUSIVE_DATE)) || (DateTime.greater(datetime, MAX_DATE)))
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "datetime");

        mValue = datetime.toOADate();
    }

	//JAVA-added public wrapper for internalized ctor
	public AxisBound(java.util.Date datetime)
	{
		this(DateTime.fromJava(datetime));
	}

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != AxisBound.class)
            return false;

        AxisBound other = (AxisBound)obj;
        return (mIsAuto == other.mIsAuto) && (mIsAuto || (mValue == other.mValue));
    }

    /// <summary>
    /// Serves as a hash function for this type. 
    /// </summary>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (msNumber.booleanHashCode(mIsAuto) * 397) ^ msNumber.doubleHashCode(mValue);
        }
    }

    /// <summary>
    /// Returns a user-friendly string that displays the value of this object.
    /// </summary>
    public /*override*/ String toString()
    {
        return mIsAuto
            ? "Auto"
            : (mValue > MIN_EXCLUSIVE_DATE_VALUE) && (mValue < MAX_EXCLUSIVE_DATE_VALUE)
                ? mValue + " (" + DateTime.fromOADate(mValue) + ")"
                : FormatterPal.doubleToStr(mValue);
    }

    /// <summary>
    /// Returns a flag indicating that axis bound should be determined automatically.
    /// </summary>
    public boolean isAuto() { return mIsAuto; }

    /// <summary>
    /// Returns numeric value of axis bound.
    /// </summary>
    public double getValue() { return mValue; }

    /// <summary>
    /// Returns value of axis bound represented as datetime.
    /// </summary>
    //JAVA: property accessor is internalized
	DateTime getValueAsDateInternal()
    {
        try
        {
            return DateTime.fromOADate(mValue);
        }
        catch (IllegalArgumentException e)
        {
            // The instance contains value that cannot be represented as date: return predetermined value.
            return DateTime.MinValue;
        }
    }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getValueAsDate() { return DateTime.toJava(getValueAsDateInternal()); }

    private /*final*/ double mValue;
    private /*final*/ boolean mIsAuto;

    private static /*final*/ DateTime MIN_EXCLUSIVE_DATE = new DateTime(100, 1, 1);
    private static /*final*/ DateTime MAX_DATE = DateTime.MaxValue;
    private static final double MIN_EXCLUSIVE_DATE_VALUE = -657435d;
    private static final double MAX_EXCLUSIVE_DATE_VALUE = 2958466d;

    /// <summary>
    /// Returns a static instance indicating that axis bound should be determined automatically by a word-processing
    /// application.
    /// </summary>
    static /*final*/ AxisBound AUTO = new AxisBound();
}

