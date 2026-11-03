// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Guid;
import com.aspose.ms.System.msObject;


/// <summary>
/// Model Identifier with GUID value.
/// </summary>
class DmlModelIdGuid extends DmlModelId
{
    DmlModelIdGuid(Guid value)
    {
        mValue = value;
    }

    protected boolean equals(DmlModelIdGuid other)
    {
        return mValue.equals(other.mValue);
    }

    public /*override*/ boolean equals(Object obj)
    {
        if (msObject.referenceEquals(null, obj))
            return false;
        if (msObject.referenceEquals(this, obj))
            return true;
        if (obj.getClass() != DmlModelIdGuid.class)
            return false;
        return equals((DmlModelIdGuid)obj);
    }

    public /*override*/ int hashCode()
    {
        return mValue.hashCode();
    }

    public /*override*/ String toString()
    {
        return mValue.toString();
    }

    Guid getValue() { return mValue; }

    private /*final*/ Guid mValue;
}

