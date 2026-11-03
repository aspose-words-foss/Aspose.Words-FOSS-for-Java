// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/01/2013 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.Debug;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.msObject;
import com.aspose.ms.System.msNumber;


/// <summary>
/// Represents a single CSS declaration. Each declaration consists of a property and a value. The value can be either
/// specified or computed.
/// </summary>
/// <remarks>
/// Only individual properties are allowed in this declaration block. Shorthand properties are converted
/// into sets of individual properties during reading. This makes our CSS model simpler.
/// This class is immutable. All derived classes should be immutable also. 
/// </remarks>
abstract class CssDeclaration
{
    /// <summary>
    /// Creates a new instance of CssDeclaration class.
    /// </summary>
    /// <param name="value">Property value.</param>
    /// <param name="important">Indicates that the declaration is marked as !important.</param>
    protected CssDeclaration(CssPropertyValue value, boolean important)
    {
        Debug.assertTrue(value != null);

        mValue = value;
        mImportant = important;
    }

    /// <summary>
    /// Outputs a CSS representation of the declaration to StringBuilder.
    /// </summary>
    void toCss(StringBuilder sb)
    {
        msStringBuilder.append(sb, getProperty());
        sb.append(':');
        mValue.toCss(sb);
        if (mImportant)
        {
            msStringBuilder.append(sb, " !important");
        }
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        return (obj.getClass() == getClass()) && equals((CssDeclaration)obj);
    }

    protected boolean equals(CssDeclaration other)
    {
        if (other == null)
            return false;
        return mImportant == other.mImportant && mValue.equals(other.mValue);
    }

    public /*override*/ int hashCode()
    {
        /*unchecked*/
        {
            return (msNumber.booleanHashCode(mImportant) * 397) ^ mValue.hashCode();
        }
    }


    /// <summary>
    /// CSS property name (always lowercase).
    /// </summary>
    abstract String getProperty();

    /// <summary>
    /// CSS property value.
    /// </summary>
    CssPropertyValue getValue() { return mValue; }

    /// <summary>
    /// Indicates whether the CSS declaration is marked as !important.
    /// </summary>
    boolean getImportant() { return mImportant; }

    boolean hasSingleValueOfType(/*CssValueType*/int valueType)
    {
        return (getValue().getCount() == 1) && (getValue().getFirstValue().getValueType() == valueType);
    }

    /// <summary>
    /// Either specified or computed CSS property value. 
    /// </summary>
    private /*final*/ CssPropertyValue mValue;

    /// <summary>
    /// Indicates whether the CSS declaration is marked as !important.
    /// </summary>
    private /*final*/ boolean mImportant;


}

