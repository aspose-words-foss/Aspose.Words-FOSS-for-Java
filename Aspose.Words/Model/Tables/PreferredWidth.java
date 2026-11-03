// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 05/09/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.MathUtil;
import com.aspose.ConvertUtilCore;
import com.aspose.ArgumentUtil;
import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.FormatterPal;


/// <summary>
/// Represents a value and its unit of measure that is used to specify the preferred width of a table or a cell.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-tables/">Working with Tables</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <para>Preferred width can be specified as a percentage, number of points or a special "none/auto" value.</para>
///
/// <para>The instances of this class are immutable.</para>
///
/// <seealso cref="Table.PreferredWidth"/>
/// <seealso cref="CellFormat.PreferredWidth"/>
/// </remarks>
public final class PreferredWidth
{
    // RK The XXXSafe methods are supposed to silently bring the value into the correct range, but at the moment
    // they just do no checks at all. If we attempt to bring the value into the correct range then some tests
    // in the table layouter fail and I don't want to deal with them now.

    static PreferredWidth fromRawSafe(/*PreferredWidthType*/int type, int rawValue)
    {
        return new PreferredWidth(type, rawValue);
    }

    static PreferredWidth fromTwipsSafe(int twips)
    {
        return twips == 0
            ? ZERO_TWIPS
            : new PreferredWidth(PreferredWidthType.POINTS, twips);
    }

    static PreferredWidth fromPercentSafe(double percent)
    {
        return new PreferredWidth(PreferredWidthType.PERCENT, MathUtil.doubleToInt(percent * PERCENT_FACTOR));
    }

    static PreferredWidth fromPointsSafe(double points)
    {
        return new PreferredWidth(PreferredWidthType.POINTS, ConvertUtilCore.pointToTwip(points));
    }

    /// <summary>
    /// Limits an arbitrary percentage value to the range allowed by this class (safe range).
    /// </summary>
    static double limitPercentsToSafeRange(double percent)
    {
        return MathUtil.fitToRange(percent, 0.0, MAX_PERCENT);
    }

    /// <summary>
    /// Limits an arbitrary value in points to the range allowed by this class (safe range).
    /// </summary>
    static double limitPointsToSafeRange(double points)
    {
        return MathUtil.fitToRange(points, 0.0, MAX_POINTS);
    }

    /// <summary>
    /// Gets a valid table preferred width with out-of-range values matching MS Word interpretation.
    /// </summary>
    /// <remarks>
    /// The main purpose for this method is to interpret values not fitting into int16.
    /// The method also handles negative and 0 value cases.
    /// The method is used for table preferred width only.
    /// For cell preferred width, int32 handling should be the same, but handling 0 values might be different.
    /// </remarks>
    PreferredWidth getCorrespondingValidTblwValue()
    {
        // Cast the value to int16 (TestInvalidTblw()):
        short valueRaw16 = (short)getValueRaw();
        // Treat as auto if the value is negative *after the casting*.
        int validRaw = valueRaw16;
        validRaw = Math.max(0, validRaw);
        // Use the maximum legal twip value if it is exceeded.
        if (getType() != PreferredWidthType.PERCENT)
            validRaw = Math.min(MAX_WIDTH_TWIPS, validRaw);
        // For pct preferred, MaxWidthTwips limits the twip values calculated from percent preferred.

        // Disregard the value if the type is auto.
        if (getType() == PreferredWidthType.AUTO)
            validRaw = 0;
        // It appears that this did not work for TestJira9884 earlier, but currently it works correctly.

        PreferredWidth validPreferred;
        // Do not treat zero dxa as auto, it must be handled specially for nested table metrics. TestTcw0DxaNested.
        if ((validRaw == 0) && (getType() == PreferredWidthType.PERCENT))
            validPreferred = AUTO;
        else if (validRaw == getValueRaw())
            validPreferred = this;
        else
            validPreferred = fromRawSafe(getType(), validRaw);

        return validPreferred;
    }

    /// <summary>
    /// A creation method that returns a new instance that represents a preferred width specified as a percentage.
    /// </summary>
    /// <param name="percent">The value must be from 0 to 100.</param>
    public static PreferredWidth fromPercent(double percent)
    {
        ArgumentUtil.validateRange(percent, 0.0, 0.0, MAX_PERCENT, MAX_PERCENT, true, "percent");
        return fromPercentSafe(percent);
    }

    /// <summary>
    /// A creation method that returns a new instance that represents a preferred width specified using a number of points.
    /// </summary>
    /// <param name="points">The value must be from 0 to 22 inches (22 * 72 points).</param>
    public static PreferredWidth fromPoints(double points)
    {
        ArgumentUtil.validateRange(points, 0.0, 0.0, MAX_POINTS, MAX_POINTS, true, "points");
        return fromPointsSafe(points);
    }

    /// <summary>
    /// Returns an instance that represents the "preferred width is not specified" value.
    /// </summary>
    public static /*final*/ PreferredWidth AUTO = new PreferredWidth(PreferredWidthType.AUTO, 0);

    /// <summary>
    /// Private ctor, the raw value is twips or 1/50th of a percent.
    /// </summary>
    private PreferredWidth(/*PreferredWidthType*/int type, int rawValue)
    {
        mTypeOrig = type;

        // WORDSNET-1311 MS Word has this Nil type, looks like it means Auto.
        if ((int)type == 0)
            type = PreferredWidthType.AUTO;

        // RK In some documents I get a raw value non zero when the type is Auto and MS Word behaves as it is a fixed value,
        // but in some documents I get a small raw value e.g. 80 and MS Word behaves as it is an Auto value.
        // If I try to "correct" it by either setting the value to 0 or by setting the type to "fixed" it causes problems in some documents.
        // I don't have a good explanation for this and therefore leave as is. The layout algorithm seems to be smart enough
        // to handle such confusing values (Auto and Value > 0) correctly and it is enough for me.

        mType = type;
        mValueRaw = rawValue;
    }

    /// <summary>
    /// Gets the unit of measure used for this preferred width value.
    /// </summary>
    
    public /*PreferredWidthType*/int getType() { return mType; }

    /// <summary>
    /// Gets the original unit of measure used for this preferred width value (passed to constructor).
    /// </summary>
    /*PreferredWidthType*/int getTypeOrig() { return mTypeOrig; }

    /// <summary>
    /// Gets the preferred width value. The unit of measure is specified in the <see cref="Type"/> property.
    /// </summary>
    public double getValue()
    {
        switch (mType)
        {
            case PreferredWidthType.AUTO:
                return 0.0;
            case PreferredWidthType.PERCENT:
                return (double)mValueRaw / PERCENT_FACTOR;
            case PreferredWidthType.POINTS:
                return ConvertUtilCore.twipToPoint(mValueRaw);
            default:
                throw new IllegalStateException("Unknown preferred width type.");
        }
    }

    /// <summary>
    /// This is either twips or 1/50th of percent.
    /// </summary>
    int getValueRaw() { return mValueRaw; }

    /// <summary>
    /// Gets the length value in twips.
    /// </summary>
    int getValueTwips()
    {
        Debug.assertTrue(isFixed(), "Attempted to access non-twips as twips.");
        return mValueRaw;
    }

    /// <summary>
    /// Returns the twips value for fixed value type, and zero otherwise.
    /// </summary>
    /// <remarks>
    /// It is used to preserve the original AW behavior when the older code is not aware
    /// of preferred widths in percent units for widthBefore and widthAfter.
    /// </remarks>
    int getValueTwipsSafe()
    {
        return isFixed()
            ? getValueRaw()
            : 0;
    }

    boolean isPositive() { return mValueRaw > 0; }

    static boolean hasPositiveValue(PreferredWidth width)
    {
        return (width != null) && width.isPositive();
    }

    /// <summary>
    /// Returns True if the preferred width is none/auto.
    /// </summary>
    boolean isAuto() { return (mType == PreferredWidthType.AUTO) || (mValueRaw <= 0); }

    /// <summary>
    /// True if the preferred width is expressed in absolute units.
    /// </summary>
    boolean isFixed() { return (mType == PreferredWidthType.POINTS); }

    /// <summary>
    /// True if the preferred width is expressed in percent.
    /// </summary>
    boolean isPercent() { return (mType == PreferredWidthType.PERCENT); }

    /// <summary>
    /// Gets a boolean value indicating either the PreferredWidth is valid.
    /// </summary>
    boolean isValid()
    {
        return (mType == PreferredWidthType.AUTO) ||
               (mType == PreferredWidthType.PERCENT) ||
               (mType == PreferredWidthType.POINTS);
    }

    /// <summary>
    /// Determines whether the specified <see cref="PreferredWidth"/> is equal in value to the current <see cref="PreferredWidth"/>.
    /// </summary>
    public boolean equals(PreferredWidth other)
    {
        // Generated by ReSharper.
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;
        return (other.mType == mType) && (other.mValueRaw == mValueRaw);
    }

    /// <summary>
    /// Determines whether the specified object is equal in value to the current object.
    /// </summary>
    public /*override*/ boolean equals(Object obj)
    {
        // Generated by ReSharper.
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != PreferredWidth.class)
            return false;
        return equals((PreferredWidth)obj);
    }

    /// <summary>
    /// Serves as a hash function for this type.
    /// </summary>
    /// <javaName>int hashCode()</javaName>
    public /*override*/ int hashCode()
    {
        // Generated by ReSharper.
        //JAVA-added explicit conversion from enum to int to workaround Metaspec parser bug:
        //Enum.GetHashCode() reported as Object.GetHashCode().
        return ((/*int.hashCode()*/((int)mType)) * 397) ^ mValueRaw;
    }

    /// <summary>
    /// Returns a user-friendly string that displays the value of this object.
    /// </summary>
    public /*override*/ String toString()
    {
        switch (mType)
        {
            case PreferredWidthType.AUTO:
                return "Auto";
            case PreferredWidthType.PERCENT:
                return FormatterPal.doubleToStr2Decimals(getValue()) + '%';
            case PreferredWidthType.POINTS:
                return FormatterPal.intToStr(getValueTwips());
            default:
                return super.toString();
        }
    }

    private /*final*/ /*PreferredWidthType*/int mType;
    private /*final*/ /*PreferredWidthType*/int mTypeOrig;
    private /*final*/ int mValueRaw;

    /// <summary>
    /// WORDSNET-5520 MS Word allows setting preferred width up to 600% so we do the same.
    /// </summary>
    private static final int MAX_PERCENT = 600;
    private static final int MAX_POINTS = 22 * 72;

    /// <summary>
    /// The maximum table width MS Word supports, in twips (22 inches).
    /// </summary>
    private static final int MAX_WIDTH_TWIPS = 31680;

    /// <summary>
    /// All percent values in the spec as before Iso29500 strict came
    /// without "%" sign. That meant they are specified in 1/50th of percent.
    /// </summary>
    static final int PERCENT_FACTOR = 50;

    /// <summary>
    /// Static immutable zero twips used to avoid creating a new zero object each time it is needed.
    /// </summary>
    static /*final*/ PreferredWidth ZERO_TWIPS = fromRawSafe(PreferredWidthType.POINTS, 0);
    // A combination of 0 width type Points and zero value is actually a special case, not normally saved by MS Word.
    // For grid calculation it is only used to avoid getting auto width for v-merged cells.
}

