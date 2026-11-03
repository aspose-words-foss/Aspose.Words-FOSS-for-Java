// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/09/2021 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ArgumentUtil;
import com.aspose.ms.System.msObject;
import java.util.Iterator;
import java.util.ArrayList;


/// <summary>
/// Contains a collection of <see cref="GradientStop"/> objects.
/// <para>To learn more, visit the <a href="https://docs.aspose.com/words/net/working-with-graphic-elements/">Working with Graphic Elements</a> documentation article.</para>
/// </summary>
/// <remarks>
/// You do not create instances of this class directly.
/// Use the <see cref="Fill.GradientStops"/> property to access gradient stops of fill objects.
/// </remarks>
public class GradientStopCollection implements Iterable<GradientStop>
{
    /// <summary>
    /// Initializes a new instance of the <see cref="GradientStopCollection" /> class.
    /// </summary>
    GradientStopCollection(DmlGradientFill parentFill, IThemeProvider themeProvider)
    {
        ArgumentUtil.checkNotNull(parentFill, "ParentFill");

        mParentFill = parentFill;
        mThemeProvider = themeProvider;
    }

    /// <summary>
    /// Gets or sets a <see cref="GradientStop"/> object in the collection.
    /// </summary>
    public GradientStop get(int index)
    {
        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "index");

        GradientStop gradientStop = getGradientStops().get(index);

        // Keep facade and internal gradient stops synchronized.
        if (!msObject.referenceEquals(gradientStop.getDmlGradientStop(), mParentFill.getGradientStops().get(index)))
            getGradientStops().set(index, new GradientStop(mParentFill.getGradientStops().get(index), mThemeProvider, this));

        return gradientStop;
    }
    public void set(int index, GradientStop value)
    {
        ArgumentUtil.checkNotNull(value, "GradientStop");
        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "index");

        // If the value we are going to set already belongs to some collection,
        // then we cannot reapply this to another collection or even set to another index in the same collection.
        if (value.getParentCollection() != null)
        {
            if (!msObject.referenceEquals(value.getParentCollection(), this))
                throw new IllegalStateException("The gradient stop belongs to another collection already.");

            if (!msObject.referenceEquals(getGradientStops().get(index), value))
                throw new IllegalStateException("The gradient stop is set to another index already.");

            return;
        }

        getGradientStops().set(index, value);
        value.setParentCollection(this);

        // Keep facade and internal gradient stops synchronized.
        mParentFill.getGradientStops().set(index, value.getDmlGradientStop());
    }

    /// <summary>
    /// Inserts a <see cref="GradientStop"/> to the collection at a specified index.
    /// </summary>
    public GradientStop insert(int index, GradientStop gradientStop)
    {
        ArgumentUtil.checkNotNull(gradientStop, "GradientStop");

        if ((index < 0) || (index > getCount()))
            throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "index");

        if (gradientStop.getParentCollection() != null)
            throw new IllegalStateException("The gradient stop belongs to some collection already.");

        // Add to facade.
        getGradientStops().add(index, gradientStop);
        gradientStop.setParentCollection(this);
        // Add to internal.
        mParentFill.getGradientStops().add(index, gradientStop.getDmlGradientStop());

        return gradientStop;
    }

    /// <summary>
    /// Adds a specified <see cref="GradientStop"/> to a gradient.
    /// </summary>
    public GradientStop add(GradientStop gradientStop)
    {
        return insert(getCount(), gradientStop);
    }

    /// <summary>
    /// Removes a <see cref="GradientStop"/> from the collection at a specified index.
    /// </summary>
    /// <returns>Removed <see cref="GradientStop"/>.</returns>
    public GradientStop removeAt(int index)
    {
        if (getCount() <= 2)
            throw new IllegalStateException("There can not be less than two gradient stops in gradient fill.");

        if ((index < 0) || (index >= getCount()))
            throw new IllegalArgumentException(VALUE_OUT_OF_RANGE + "\r\nParameter name: " + "index");

        GradientStop gradientStop = getGradientStops().get(index);
        gradientStop.setParentCollection(null);
        // Remove facade.
        getGradientStops().remove(index);
        // Remove from inner collection.
        mParentFill.getGradientStops().remove(index);

        return gradientStop;
    }

    /// <summary>
    /// Removes a specified <see cref="GradientStop"/> from the collection.
    /// </summary>
    /// <returns><c>true</c> if gradient stop was successfully removed, otherwise <c>false</c>.</returns>
    public boolean remove(GradientStop gradientStop)
    {
        if (getGradientStops().remove(gradientStop))
        {
            gradientStop.setParentCollection(null);
            return mParentFill.getGradientStops().remove(gradientStop.getDmlGradientStop());
        }

        return false;
    }

    //>>>>>>>> #region  IEnumerator implementation
    /// <summary>
    /// Returns an enumerator that iterates through the collection.
    /// </summary>
    public Iterator<GradientStop> iterator()
    {
        return getGradientStops().iterator();
    }

    /// <summary>
    /// Returns an enumerator that iterates through a collection.
    /// </summary>
    //JAVA-deleted non-generic namesake:
    //Iterator iterator()
    //<<<<<<<< #endregion 

    /// <summary>
    /// Gets an integer value indicating the number of items in the collection.
    /// </summary>
    public int getCount() { return mParentFill.getGradientStops().size(); }

    /// <summary>
    /// Gets parent <see cref="DmlGradientFill"/> object.
    /// </summary>
    DmlGradientFill getParentFill() { return mParentFill; }

    /// <summary>
    /// The collection of facade <see cref="GradientStop"/> objects.
    /// </summary>
    private ArrayList<GradientStop> getGradientStops()
    {
        if (mGradientStops == null)
        {
            mGradientStops = new ArrayList<GradientStop>(mParentFill.getGradientStops().size());
            for (DmlGradientStop dmlGradientStop : mParentFill.getGradientStops())
                mGradientStops.add(new GradientStop(dmlGradientStop, mThemeProvider, this));
        }

        return mGradientStops;
    }

    /// <summary>
    /// The collection of facade <see cref="GradientStop"/> objects.
    /// </summary>
    private ArrayList<GradientStop> mGradientStops;
    private /*final*/ IThemeProvider mThemeProvider;
    private /*final*/ DmlGradientFill mParentFill;
    private static final String VALUE_OUT_OF_RANGE = "The specified value is out of range.";
}

