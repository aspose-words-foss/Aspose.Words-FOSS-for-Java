// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/08/2022 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.TimeSpan;
import com.aspose.ms.System.msNumber;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;


/// <summary>
/// Represents an X value for a chart series.
/// </summary>
/// <remarks>
/// <p>This class contains a number of static methods for creating an X value of a particular type. The
/// <see cref="ValueType"/> property allows you to determine the type of an existing X value.</p>
/// <p>All non-null X values of a chart series must be of the same <see cref="ChartXValueType"/> type.</p>
/// </remarks>
public class ChartXValue
{
    /// <summary>
    /// The static methods are used to create instances of this class.
    /// </summary>
    private ChartXValue()
    {
    }

    /// <summary>
    /// Creates a <see cref="ChartXValue"/> instance of the <see cref="ChartXValueType.String"/> type.
    /// </summary>
    public static ChartXValue fromString(String value)
    {
        ChartXValue xValue = new ChartXValue();
        xValue.mStringValue = value;
        xValue.mValueType = ChartXValueType.STRING;
        return xValue;
    }

    /// <summary>
    /// Creates a <see cref="ChartXValue"/> instance of the <see cref="ChartXValueType.Double"/> type.
    /// </summary>
    public static ChartXValue fromDouble(double value)
    {
        ChartXValue xValue = new ChartXValue();
        xValue.mDoubleValue = value;
        xValue.mValueType = ChartXValueType.DOUBLE;
        return xValue;
    }

    /// <summary>
    /// Creates a <see cref="ChartXValue"/> instance of the <see cref="ChartXValueType.DateTime"/> type.
    /// </summary>
    //JAVA: method is internalized
	static ChartXValue fromDateTimeInternal(DateTime value)
    {
        ChartXValue xValue = new ChartXValue();
        xValue.mDateTimeValue = value;
        xValue.mValueType = ChartXValueType.DATE_TIME;
        return xValue;
    }

	//JAVA-added public wrapper for internalized method
	public static ChartXValue fromDateTime(java.util.Date value)
	{
		return fromDateTimeInternal(DateTime.fromJava(value));
	}

    /// <summary>
    /// Creates a <see cref="ChartXValue"/> instance of the <see cref="ChartXValueType.Time"/> type.
    /// </summary>
    //JAVA: method is internalized
	static ChartXValue fromTimeSpanInternal(TimeSpan value)
    {
        ChartXValue xValue = new ChartXValue();
        xValue.mTimeValue = value;
        xValue.mValueType = ChartXValueType.TIME;
        return xValue;
    }

	//JAVA-added public wrapper for internalized method
	public static ChartXValue fromTimeSpan(long value)
	{
		return fromTimeSpanInternal(TimeSpan.fromJava(value));
	}

    /// <summary>
    /// Creates a <see cref="ChartXValue"/> instance of the <see cref="ChartXValueType.Multilevel"/> type.
    /// </summary>
    public static ChartXValue fromMultilevelValue(ChartMultilevelValue value)
    {
        if (value == null)
            throw new NullPointerException("Value cannot be null.\r\nParameter name: " + "value");

        ChartXValue xValue = new ChartXValue();
        xValue.mMultilevelValue = value;
        xValue.mValueType = ChartXValueType.MULTILEVEL;
        return xValue;
    }

    /// <summary>
    /// Gets a hash code for the current X value object.
    /// </summary>
    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            int hash = 31 * /*enum.hashCode()=(int)value*/mValueType;
            switch (mValueType)
            {
                case ChartXValueType.STRING:
                    return hash + ((mStringValue != null) ? mStringValue.hashCode() : 0);
                case ChartXValueType.DOUBLE:
                    return hash + msNumber.doubleHashCode(mDoubleValue);
                case ChartXValueType.DATE_TIME:
                    return hash + mDateTimeValue.hashCode();
                case ChartXValueType.TIME:
                    return hash + mTimeValue.hashCode();
                case ChartXValueType.MULTILEVEL:
                    Debug.assertTrue(mMultilevelValue != null);
                    return hash + mMultilevelValue.hashCode();
                default:
                    return hash;
            }
        }
    }

    /// <summary>
    /// Gets a flag indicating whether the specified object is equal to the current X value object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        else if (msObject.referenceEquals(this, obj))
            return true;
        else if (obj.getClass() != getClass())
            return false;

        ChartXValue xValue = (ChartXValue)obj;
        if (getValueType() != xValue.getValueType())
            return false;

        switch (getValueType())
        {
            case ChartXValueType.STRING:
                return msObject.equals(mStringValue, xValue.getStringValue());
            case ChartXValueType.DOUBLE:
                return ((mDoubleValue) == (xValue.mDoubleValue));
            case ChartXValueType.DATE_TIME:
                return mDateTimeValue.equals(xValue.mDateTimeValue);
            case ChartXValueType.TIME:
                return mTimeValue.equals(xValue.mTimeValue);
            case ChartXValueType.MULTILEVEL:
                Debug.assertTrue(mMultilevelValue != null);
                return mMultilevelValue.equals(xValue.mMultilevelValue);
            default:
                Debug.assertTrue(false);
                return false;
        }
    }

    /// <summary>
    /// Gets the type of the X value stored in the object.
    /// </summary>
    public /*ChartXValueType*/int getValueType() { return mValueType; }

    /// <summary>
    /// Gets the stored string value.
    /// </summary>
    /// <dev>
    /// Returns <b>null</b> if the instance is not of the <see cref="ChartXValueType.String"/> type.
    /// </dev>
    public String getStringValue() { return mStringValue; }

    /// <summary>
    /// Gets the stored numeric value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="double.NaN"/> if the instance is not of the <see cref="ChartXValueType.Double"/> type.
    /// </dev>
    public double getDoubleValue() { return mDoubleValue; }

    /// <summary>
    /// Gets the stored datetime value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="DateTime.MinValue"/> if the instance is not of the
    /// <see cref="ChartXValueType.DateTime"/> type.
    /// </dev>
    //JAVA: property accessor is internalized
	DateTime getDateTimeValueInternal() { return mDateTimeValue; }
	//JAVA-added public wrapper for internalized property accessor
	public java.util.Date getDateTimeValue() { return DateTime.toJava(getDateTimeValueInternal()); }

    /// <summary>
    /// Gets the stored time value.
    /// </summary>
    /// <dev>
    /// Returns <see cref="TimeSpan.Zero"/> if the instance is not of the <see cref="ChartXValueType.Time"/> type.
    /// </dev>
    //JAVA: property accessor is internalized
	TimeSpan getTimeValueInternal() { return mTimeValue; }
	//JAVA-added public wrapper for internalized property accessor
	public long getTimeValue() { return TimeSpan.toJava(getTimeValueInternal()); }

    /// <summary>
    /// Gets the stored multilevel value.
    /// </summary>
    /// <dev>
    /// Returns <b>null</b> if the instance is not of the <see cref="ChartXValueType.Multilevel"/> type.
    /// </dev>
    public ChartMultilevelValue getMultilevelValue() { return mMultilevelValue; }

    private /*ChartXValueType*/int mValueType;
    private String mStringValue;
    private double mDoubleValue = Double.NaN;
    private DateTime mDateTimeValue = DateTime.MinValue;
    private TimeSpan mTimeValue = TimeSpan.ZERO;
    private ChartMultilevelValue mMultilevelValue;
}

