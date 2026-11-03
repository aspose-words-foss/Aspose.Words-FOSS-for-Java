// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/11/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.NoSuchElementException;
import com.aspose.ms.System.Collections.msArrayList;
import java.util.Iterator;
import com.aspose.Debug;
import java.util.ArrayList;


/// <summary>
/// Represents a collection of constants.
/// </summary>
class ConstantCollection implements Iterable<Constant>
{
    /// <summary>
    /// Adds a constant to the collection.
    /// </summary>
    /// <param name="constant">The constant to add.</param>
    void add(Constant constant)
    {
        msArrayList.add(mItems, constant);
    }

    /// <summary>
    /// Adds a range of constants to the collection.
    /// </summary>
    void addRange(ConstantCollection constantCollection)
    {
        msArrayList.addRange(mItems, constantCollection.mItems);
    }

    public Iterator<Constant> iterator()
    {
        return new ConstantEnumerator(this);
    }

    //JAVA-deleted non-generic namesake:
    //Iterator iterator()

    /// <summary>
    /// Gets a constant at the specified index.
    /// </summary>
    Constant get(int index)
    {
        Debug.assertTrue((index >= 0) && (index < mItems.size()));
        return mItems.get(index);
    }

    /// <summary>
    /// Return the total number of constants in this collection.
    /// </summary>
    int getCount() { return mItems.size(); }

    /// <summary>
    /// Enumerates constants in the specified collection. If a constant is null, slips it. If a constant
    /// is aggregate, enumerates all child constants.
    /// </summary>
    private final static class ConstantEnumerator implements Iterator<Constant>
    {
        /// <summary>
        /// Ctor.
        /// </summary>
        ConstantEnumerator(ConstantCollection constants)
        {
            mConstants = constants;
        }

        public void dispose()
        {
            // Nothing to dispose.
        }

        public boolean hasNext()
        {
            if (isInAggregate())
            {
                if (mAggregateEnumerator.hasNext())
                {
                    return true;
                }
                else
                {
                    mAggregateEnumerator = null;
                }
            }

            while (true)    // Break inside.
            {
                mIndex++;

                // RK Should this be done using HasNextCore?
                if (mIndex == mConstants.getCount())
                    return false;

                if (next().getConstantType() == ConstantType.AGGREGATE)
                {
                    AggregateConstant aggregate = (AggregateConstant)next();
                    mAggregateEnumerator = (ConstantEnumerator)aggregate.getValues().iterator();

                    if (!mAggregateEnumerator.hasNext())
                    {
                        mAggregateEnumerator = null;
                    }
                    else
                    {
                        return true;
                    }
                }
                else
                {
                    mAggregateEnumerator = null;
                    return true;
                }
            }
        }

        public void reset()
        {
            mIndex = -1;
        }

        //JAVA-deleted Namesake Explicit Interface Implementation property:
        //Object IEnumerator.Current

        private boolean isInAggregate() { return mAggregateEnumerator != null; }

        public Constant next() { return isInAggregate() ? mAggregateEnumerator.next() : mConstants.get(mIndex); }

        private /*final*/ ConstantCollection mConstants;
        private int mIndex = -1;
        private ConstantEnumerator mAggregateEnumerator;

    //JAVA-added for IEnumerator emulation
    public void remove() 
    {
        throw new UnsupportedOperationException();
    }
    }

    private /*final*/ ArrayList<Constant> mItems = new ArrayList<Constant>();
}

