// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/01/2020 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.IStringBehaviour;
import com.aspose.IString;
import com.aspose.IStringBuilder;


class RichStringBehaviour implements IStringBehaviour
{
    private RichStringBehaviour()
    {
    }

    static /*final*/ RichStringBehaviour INSTANCE = new RichStringBehaviour();

    private static IStringBehaviour getBehaviour() { return INSTANCE; }

    public IString /*IStringBehaviour.*/getEmptyString() { return RichString.EMPTY; }

    public boolean /*IStringBehaviour.*/isNullOrEmpty(IString value)
    {
        return value == null || value.getLength() == 0;
    }

    public IString /*IStringBehaviour.*/createString(String value)
    {
        return RichString.createFromString(value);
    }

    public IStringBuilder /*IStringBehaviour.*/createBuilder()
    {
        return new RichStringBuilder();
    }

    public IStringBuilder createBuilder(IString value)
    {
        return new RichStringBuilder((RichString)value);
    }

    public IStringBuilder /*IStringBehaviour.*/createBuilder(int capacity)
    {
        return new RichStringBuilder(capacity);
    }

    static boolean isNullOrEmptyInternal(RichString value)
    {
        return getBehaviour().isNullOrEmpty(value);
    }
}

