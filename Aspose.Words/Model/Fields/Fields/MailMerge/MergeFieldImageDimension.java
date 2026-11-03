// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/10/2012 by Ivan Lyagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.FormatterPal;
import com.aspose.StringUtil;
import com.aspose.StringToIntDictionary;


/// <summary>
/// Represents an image dimension (i.e. the width or the height) used across a mail merge process.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-fields/">Working with Fields</a> documentation article.</para>
/// </summary>
/// <remarks>
/// To indicate that the image should be inserted with its original dimension during a mail merge,
/// you should assign a negative value to the <see cref="Value"/> property.
/// <seealso cref="MergeFieldImageDimensionUnit"/>
/// </remarks>
public class MergeFieldImageDimension implements Cloneable
{
    /// <summary>
    /// Creates an image dimension instance with the given value in points.
    /// </summary>
    /// <remarks>
    /// You should use a negative value to indicate that the original value of the corresponding image dimension
    /// should be applied.
    /// </remarks>
    /// <param name="value">The value.</param>
    public MergeFieldImageDimension(double value)
    {
        // Assign through the properties to perform value checks.
        setValue(value);
    }

    /// <summary>
    /// Creates an image dimension instance with the given value and the given unit.
    /// </summary>
    /// <remarks>
    /// You should use a negative value to indicate that the original value of the corresponding image dimension
    /// should be applied.
    /// </remarks>
    /// <param name="value">The value.</param>
    /// <param name="unit">The unit.</param>
    public MergeFieldImageDimension(double value, /*MergeFieldImageDimensionUnit*/int unit)
    {
        // Assign through the properties to perform value checks.
        setValue(value);
        setUnit(unit);
    }

    /// <summary>
    /// Ctor for internal using.
    /// </summary>
    MergeFieldImageDimension()
    {
        setValue(-1.0);
    }

    /// <summary>
    /// Tries to parse the value and the unit from the corresponding string representations and
    /// if succeeded, creates a <see cref="MergeFieldImageDimension"/> instance based on the parsed data.
    /// </summary>
    /// <remarks>
    /// Note that we do not use a <see cref="Boolean"/> result in conjunction with an out parameter
    /// not to create an extra array object on Java, as this method is internal anyways.
    /// </remarks>
    /// <param name="valueString">The string representation of the value.</param>
    /// <param name="unitString">The string representation of the unit.</param>
    /// <returns>The <see cref="MergeFieldImageDimension"/> instance or <c>null</c> if parsing
    /// has not been succeeded.</returns>
    static MergeFieldImageDimension tryParse(String valueString, String unitString)
    {
        double value = FormatterPal.tryParseDoubleInvariant(valueString);
        if (Double.isNaN(value))
            return null;

        // The empty unit string corresponds to the Point unit.
        /*MergeFieldImageDimensionUnit*/int unit = MergeFieldImageDimensionUnit.POINT;
        if (StringUtil.hasChars(unitString))
        {
            int unitInt = G_UNIT_NAME_TO_VALUE_MAP.get(unitString);
            if (StringToIntDictionary.isNullSubstitute(unitInt))
                return null;

            unit = (/*MergeFieldImageDimensionUnit*/int)unitInt;
        }

        MergeFieldImageDimension dimension = new MergeFieldImageDimension();
        dimension.setValue(value);
        dimension.mUnit = unit;
        return dimension;
    }

    /// <summary>
    /// Creates a deep copy of the instance.
    /// </summary>
    /// <returns></returns>
    MergeFieldImageDimension deepClone()
    {
        // A shallow copy is equal to a deep copy in this case, as all member variables of this class are of value types.
        return (MergeFieldImageDimension)memberwiseClone();
    }

    /// <summary>
    /// Converts the value of the given <see cref="MergeFieldImageDimension"/> instance to the points.
    /// </summary>
    /// <param name="imageDimension">The <see cref="MergeFieldImageDimension"/> instance.</param>
    /// <param name="originalValueInPoints">The original value of the corresponding image dimension in points.</param>
    /// <returns></returns>
    static double toPoints(MergeFieldImageDimension imageDimension, double originalValueInPoints)
    {
        // Return the original value of the corresponding image dimension if the given instance does not provide
        // a positive value.
        if (!hasValue(imageDimension))
            return originalValueInPoints;

        switch (imageDimension.mUnit)
        {
            case MergeFieldImageDimensionUnit.POINT:
                return imageDimension.getValue();
            case MergeFieldImageDimensionUnit.PERCENT:
                return originalValueInPoints * imageDimension.getValue() / 100.0;
            default:
                // Throw the exception for the compiler. This code is to be unreachable.
                throw new IllegalStateException("Undefined MergeFieldImageDimensionUnit has been encountered.");
        }
    }

    /// <summary>
    /// Returns a value indicating whether the given <see cref="MergeFieldImageDimension"/> instance provides
    /// a significant (i.e. positive) value or not.
    /// </summary>
    static boolean hasValue(MergeFieldImageDimension imageDimension)
    {
        return (imageDimension != null) && (imageDimension.getValue() >= 0);
    }

    /// <summary>
    /// The value.
    /// </summary>
    /// <remarks>
    /// You should use a negative value to indicate that the original value of the corresponding image dimension
    /// should be applied.
    /// </remarks>
    public double getValue() { return mValue; }; public void setValue(double value) { mValue = value; };

    private double mValue;

    /// <summary>
    /// The unit.
    /// </summary>
    public /*MergeFieldImageDimensionUnit*/int getUnit() { return mUnit; }
    public void setUnit(/*MergeFieldImageDimensionUnit*/int value)
    {
        switch (value)
        {
            case MergeFieldImageDimensionUnit.POINT:
            case MergeFieldImageDimensionUnit.PERCENT:
                mUnit = value;
                break;
            default:
                throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");
        }
    }

    private /*MergeFieldImageDimensionUnit*/int mUnit = MergeFieldImageDimensionUnit.POINT;

    /// <summary>
    /// Stores the map of the unit names to the corresponding unit values.
    /// </summary>
    private static /*final*/ StringToIntDictionary G_UNIT_NAME_TO_VALUE_MAP = buildUnitNameToValueMap();

    private static StringToIntDictionary buildUnitNameToValueMap()
    {
        StringToIntDictionary unitNameToValueMap = new StringToIntDictionary(false);
        unitNameToValueMap.add("pt", (int)MergeFieldImageDimensionUnit.POINT);
        unitNameToValueMap.add("%", (int)MergeFieldImageDimensionUnit.PERCENT);
        return unitNameToValueMap;
    }

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

