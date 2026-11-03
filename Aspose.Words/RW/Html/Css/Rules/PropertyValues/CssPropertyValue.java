// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.msObject;
import com.aspose.DrColor;


/// <summary>
/// Represents CSS property value.
/// CSS property value contains one or more CSS values.
/// This class is immutable. All derived classes should be immutable also. 
/// </summary>
class CssPropertyValue
{
    CssPropertyValue(CssValue cssValue)
    {
        Debug.assertTrue(cssValue != null);
        mCssValues = new CssValueList(cssValue);
        setProperties();
    }

    CssPropertyValue(CssValueList cssValues)
    {
        Debug.assertTrue(cssValues.getCount() != 0);
        mCssValues = new CssValueList(cssValues);
        setProperties();
    }

    CssPropertyValue(CssPropertyValue value)
    {
        mCssValues = new CssValueList(value.getCount());
        for (int i = 0; i < value.getCount(); i++)
            mCssValues.add(value.get(i));
        setProperties();
    }

    public /*override*/ int hashCode()
    {
        return mCssValues.hashCode();
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (obj instanceof CssValue)
            return equals((CssValue)obj);
        if (obj instanceof CssPropertyValue)
            return equals((CssPropertyValue)obj);
        if (obj instanceof CssValueList)
            return equals((CssValueList)obj);
        if (obj instanceof CssValue[])
            return equals((CssValue[])obj);

        Debug.assertTrue(false);
        return false;
    }

    /// <summary>
    /// Determines whether the specified CSS property value is equal to the current value.
    /// </summary>
    boolean equals(CssPropertyValue other)
    {
        // Standard reference comparisons.
        if (msObject.referenceEquals(null, other))
            return false;
        if (msObject.referenceEquals(this, other))
            return true;

        return mCssValues.equals(other.mCssValues);
    }

    /// <summary>
    /// Determines whether the specified CSS values are equal to the current value.
    /// </summary>
    boolean equals(CssValueList cssValues)
    {
        return mCssValues.equals(cssValues);
    }

    /// <summary>
    /// Determines whether the specified CSS values are equal to the current value.
    /// </summary>
    boolean equals(/*params*/ CssValue... cssValues)
    {
        return mCssValues.equals(cssValues);
    }

    /// <summary>
    /// Determines whether the specified CSS value is equal to the current value.
    /// </summary>
    boolean equals(CssValue value)
    {
        return (getCount() == 1) && mCssValues.get(0).equals(value);
    }

    /// <summary>
    /// Gets a CSS representation of the property value.
    /// </summary>
    String toCss()
    {
        StringBuilder sb = new StringBuilder();
        toCss(sb);
        return sb.toString();
    }

    /// <summary>
    /// Outputs a CSS representation of the property value to StringBuilder.
    /// </summary>
    /*virtual*/ void toCss(StringBuilder sb)
    {
        if (isInherit())
        {
            CssValue.Inherit.toCss(sb);
        }
        else
        {
            valueToCss(sb);
        }
    }

    /// <summary>
    /// Override in derived classes to output CSS representation of the property value to StringBuilder.
    /// You don't need process 'inherit' value in this method.
    /// </summary>
    protected /*virtual*/ void valueToCss(StringBuilder sb)
    {
        for (int i = 0; i < mCssValues.getCount(); i++)
        {
            if (i != 0)
                sb.append(' ');
            mCssValues.get(i).toCss(sb);
        }
    }

    boolean contains(CssValue value)
    {
        return mCssValues.contains(value);
    }

    DrColor parseAsColor()
    {
        return (getCount() == 1)
            ? getFirstValue().parseAsColor()
            : null;
    }

    private void setProperties()
    {
        mIsInherit = true;
        for (CssValue cssValue : mCssValues)
        {
            if (mIsInherit && !cssValue.equals(CssValue.Inherit))
                mIsInherit = false;
        }

        mIsInitial = (mCssValues.getCount() == 1) && mCssValues.get(0).equals(CssValue.Initial);
    }

    /// <summary>
    /// Gets whether the property value is 'inherit'.
    /// </summary>
    /*virtual*/ boolean isInherit() { return mIsInherit; }

    /// <summary>
    /// Gets whether the property value is 'initial'.
    /// </summary>
    /*virtual*/ boolean isInitial() { return mIsInitial; }

    int getCount() { return mCssValues.getCount(); }

    CssValue get(int index) { return mCssValues.get(index); }

    CssValue getFirstValue() { return mCssValues.get(0); }

    private /*final*/ CssValueList mCssValues;

    /// <summary>
    /// Gets whether the property value is 'inherit'. Added for performance optimization.
    /// </summary>
    private boolean mIsInherit;
    /// <summary>
    /// Gets whether the property value is 'initial'. Added for performance optimization.
    /// </summary>
    private boolean mIsInitial;
}

