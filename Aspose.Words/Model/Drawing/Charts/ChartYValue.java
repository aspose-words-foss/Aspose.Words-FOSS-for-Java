// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.msNumber;
import com.aspose.ms.System.msObject;
import com.aspose.Debug;


/// <summary>
/// Represents an Y value for a chart series.
/// </summary>
/// <remarks>
/// <p>This class contains a number of static methods for creating an Y value of a particular type. The
/// <see cref="ValueType"/> property allows you to determine the type of an existing Y value.</p>
/// <p>All non-null Y values of a chart series must be of the same <see cref="ChartYValueType"/> type.</p>
/// </remarks>
public class ChartYValue
{
    /// <summary>
    /// The static methods are used to create instances of this class.
    /// </summary>
    private ChartYValue()
    {
    }

    /// <summary>
    /// Creates a <see cref="ChartYValue"/> instance of the <see cref="ChartYValueType.Double"/> type.
    /// </summary>
    public static ChartYValue fromDouble(double value)
    {
        ChartYValue yValue = new ChartYValue();
        yValue.mDoubleValue = value;
        yValue.mValueType = ChartYValueType.DOUBLE;
        return yValue;
    }

    /// <summary>
    /// Creates a <see cref="ChartYValue"/> instance of the <see cref="ChartYValueType.DateTime"/> type.
    /// </summary>
    //JAVA: method is internalized
	static ChartYValue fromDateTimeInternal(DateTime value)
    {
        ChartYValue yValue = new ChartYValue();
        yValue.mDateTimeValue = value;
        yValue.mValueType = ChartYValueType.DATE_TIME;
        return yValue;
    }

	//JAVA-added public wrapper for internalized method
	public static ChartYValue fromDateTime(java.util.Date value)
	{
		return fromDateTimeInternal(DateTime.fromJava(value));
	}

    /// <summary>
    /// Creates a <see cref="ChartYValue"/> instance of the <see cref="ChartYValueType.Time"/> type.
    /// </summary>
    //JAVA: method is internalized
	static ChartYValue fromTimeSpanInternal(TimeSpan value)
    {
        ChartYValue yValue = new ChartYValue();
        yValue.mTimeValue = value;
        yValue.mValueType = ChartYValueType.TIME;
        return yValue;
    }

	//JAVA-added public wrapper for internalized method
	public static ChartYValue fromTimeSpan(long value)
	{
		return fromTimeSpanInternal(TimeSpan.fromJava(value));
	}

    /// <summary>
    /// Gets a hash code for the current Y value object.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hash = 31 * /*enum.hashCode()=(int)value*/mValueType;
            switch (mValueType)
            {
                case ChartYValueType.DOUBLE:
                    return hash + msNumber.doubleHashCode(mDoubleValue);
                case ChartYValueType.DATE_TIME:
                    return hash + mDateTimeValue.hashCode();
                case ChartYValueType.TIME:
                    return hash + mTimeValue.hashCode();
                default:
                    return hash;
            }
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified object is equal to the current Y value object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        else if (msObject.referenceEquals(this, obj))
            return true;
        else if (obj.getClass() != getClass())
            return false;

        ChartYValue yValue = (ChartYValue)obj;
        if (getValueType() != yValue.getValueType())
            return false;

        switch (getValueType())
        {
            case ChartYValueType.DOUBLE:
                return ((mDoubleValue) == (yValue.mDoubleValue));
            case ChartYValueType.DATE_TIME:
                return mDateTimeValue.equals(yValue.mDateTimeValue);
            case ChartYValueType.TIME:
                return mTimeValue.equals(yValue.mTimeValue);
            default:
                Debug.assertTrue(false);
                return false;
        }
    }

    /// <summary>
    /// Gets the type of the Y value stored in the object.
    /// </summary>
    public /*ChartYValueType*/int getValueType() { return mValueType; }

    /// <summary>
    /// Gets the stored numeric value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="double.NaN"/> if the instance is not of the <see cref="ChartYValueType.Double"/> type.
    /// </dev>
    public double getDoubleValue() { return mDoubleValue; }

    /// <summary>
    /// Gets the stored datetime value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="DateTime.MinValue"/> if the instance is not of the
    /// <see cref="ChartYValueType.DateTime"/> type.
    /// </dev>
    //JAVA: property accessor is internalized
	DateTime getDateTimeValueInternal() { return mDateTimeValue; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getDateTimeValue() { return DateTime.toJava(getDateTimeValueInternal()); }

    /// <summary>
    /// Gets the stored time value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="TimeSpan.Zero"/> if the instance is not of the <see cref="ChartYValueType.Time"/> type.
    /// </dev>
    //JAVA: property accessor is internalized
	TimeSpan getTimeValueInternal() { return mTimeValue; }
	//JAVA-added public wrapper for internalized property accessor
	public long getTimeValue() { return TimeSpan.toJava(getTimeValueInternal()); }

    private /*ChartYValueType*/int mValueType;
    private double mDoubleValue = Double.NaN;
    private DateTime mDateTimeValue = DateTime.MinValue;
    private TimeSpan mTimeValue = TimeSpan.ZERO;
}

