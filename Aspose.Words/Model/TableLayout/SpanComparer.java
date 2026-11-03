// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/08/2006 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.ms.System.msInt;


/// <summary>
/// Compares two table cells by their span values.
/// </summary>
class SpanComparer implements Comparator<SpanCell>
{
    /// <summary>
    /// Ctor.
    /// </summary>
    SpanComparer()
    {
    }

    public int compare(SpanCell x, SpanCell y)
    {
        int span1 = x.getSpan();
        int span2 = y.getSpan();

        return msInt.compareTo(span1, span2);
    }
}

