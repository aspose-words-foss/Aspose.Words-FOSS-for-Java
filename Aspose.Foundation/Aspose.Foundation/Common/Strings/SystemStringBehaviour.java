// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.msString;


public class SystemStringBehaviour implements IStringBehaviour
{
    private SystemStringBehaviour()
    {
    }

    public static /*final*/ SystemStringBehaviour INSTANCE = new SystemStringBehaviour();

    public boolean /*IStringBehaviour.*/isNullOrEmpty(IString value)
    {
        if (value == null)
            return true;

        return msString.isNullOrEmpty(((SystemStringAdapter)value).getValue());
    }

    public IString /*IStringBehaviour.*/createString(String value)
    {
        return SystemStringAdapter.create(value);
    }

    public IStringBuilder /*IStringBehaviour.*/createBuilder()
    {
        return new SystemStringBuilderAdapter();
    }

    public IStringBuilder /*IStringBehaviour.*/createBuilder(IString value)
    {
        return new SystemStringBuilderAdapter(new StringBuilder(((SystemStringAdapter)value).getValue()));
    }

    public IStringBuilder /*IStringBehaviour.*/createBuilder(int capacity)
    {
        return new SystemStringBuilderAdapter(capacity);
    }

    public IString /*IStringBehaviour.*/getEmptyString() { return SystemStringAdapter.EMPTY; }
}

