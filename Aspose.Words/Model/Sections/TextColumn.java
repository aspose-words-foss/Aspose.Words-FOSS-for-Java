// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/04/2005 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ConvertUtilCore;


/// <summary>
/// Represents a single text column. <see cref="TextColumn"/> is a member of the <see cref="TextColumnCollection"/> collection.
/// The <see cref="TextColumn"/> collection includes all the columns in a section of a document.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-sections/">Working with Sections</a> documentation article.</para>
/// </summary>
/// <remarks>
/// <p><see cref="TextColumn"/> objects are only used to specify columns with custom width and spacing. If you want
/// the columns in the document to be of equal width, set TextColumns.<see cref="TextColumnCollection.EvenlySpaced"/> to <c>true</c>.</p>
/// <p>When a new <see cref="TextColumn"/> is created it has its width and spacing set to zero.</p>
/// <seealso cref="TextColumnCollection"/>
/// <seealso cref="PageSetup"/>
/// <seealso cref="Section"/>
/// </remarks>
/// <dev>
/// This is both a presentation and a model class. Represents width and spacing of a single column
/// in a section. Only used for custom width columns (not for evenly spaced columns).
/// </dev>
public class TextColumn implements Cloneable
{
    /// <summary>
    /// Creates a column with zero width and space after.
    /// </summary>
    TextColumn()
    {
    }

    /// <summary>
    /// Gets or sets the width of the text column in points.
    /// </summary>
    public double getWidth() { return ConvertUtilCore.twipToPoint(mWidth); }
    public void setWidth(double value)
    {
        if (0 > value)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        int twipValue = ConvertUtilCore.pointToTwip(value);
        mWidth = twipValue;
    }

    /// <summary>
    /// Gets or sets the space between this column and the next column in points. Not required for the last column.
    /// </summary>
    public double getSpaceAfter() { return ConvertUtilCore.twipToPoint(mSpaceAfter); }
    public void setSpaceAfter(double value) 
    {
        if (0 > value)
            throw new IllegalArgumentException("Specified argument was out of the range of valid values.\r\nParameter name: " + "value");

        int twipValue = ConvertUtilCore.pointToTwip(value);
        mSpaceAfter = twipValue;
    }

    /// <summary>
    /// Creates a new object that is a copy of the current instance.
    /// </summary>
    TextColumn deepClone()
    {
        TextColumn clone = (TextColumn)memberwiseClone();
        return clone;
    }

    /// <summary>
    /// Width of the column in twips. This is used by codecs.
    /// </summary>
    int getRawWidth() { return mWidth; }
    void setRawWidth(int value) { mWidth = value; }

    /// <summary>
    /// Space after the column in twips. This is used by codecs.
    /// </summary>
    int getRawSpaceAfter() { return mSpaceAfter; }
    void setRawSpaceAfter(int value) { mSpaceAfter = value; }

    /// <summary>
    /// Returns <b>true</b> if the column has non-empty width or space after.
    /// </summary>
    boolean hasData() { return (mWidth > 0) || (mSpaceAfter > 0); }

    private int mWidth;
    private int mSpaceAfter;

    //JAVA-added .Net MemberwiseClone emulation
    protected Object memberwiseClone()
    {
        try { return clone(); }
        catch (CloneNotSupportedException e) { throw new IllegalStateException(e); }
    }
}

