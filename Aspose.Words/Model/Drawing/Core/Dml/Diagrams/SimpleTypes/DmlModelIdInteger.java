// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msObject;
import com.aspose.FormatterPal;


/// <summary>
/// Model Identifier with integer value.
/// </summary>
class DmlModelIdInteger extends DmlModelId
{
    DmlModelIdInteger(int value)
    {
        mValue = value;
    }

    protected boolean equals(DmlModelIdInteger other)
    {
        return mValue == other.mValue;
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != DmlModelIdInteger.class)
            return false;
        return equals((DmlModelIdInteger)obj);
    }

    public /*override*/ int hashCode()
    {
        return mValue;
    }

    public /*override*/ String toString()
    {
        return FormatterPal.intToStr(mValue);
    }

    int getValue() { return mValue; }

    private /*final*/ int mValue;
}

