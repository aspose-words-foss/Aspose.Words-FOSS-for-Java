// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/01/2007 by Konstantin Sidorenko
// 2015/12/21 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.msChar;


@Test
public class TestChar
{
    @Test
    public void testCharIsWhiteSpace()
    {
        msAssert.areEqual(msChar.isWhiteSpace(' '), true);
        msAssert.areEqual(msChar.isWhiteSpace('\u0009'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\n'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\u000b'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\f'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\r'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\u0085'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\u2028'), true);
        msAssert.areEqual(msChar.isWhiteSpace('\u2029'), true);
    }

    @Test
    public void testToUpperInvariant()
    {
        msAssert.areEqual(MICRO_SIGN, msChar.toUpperInvariant(MICRO_SIGN));
        msAssert.areEqual('H', msChar.toUpperInvariant('h'));
    }

    @Test
    public void testToLowerInvariant()
    {
        msAssert.areEqual(MICRO_SIGN, msChar.toLowerInvariant(MICRO_SIGN));
        msAssert.areEqual('h', msChar.toLowerInvariant('H'));
    }

    // J1678: micro sign char stays the same while converting to upper or to lower invariant in .Net.
    private static final char MICRO_SIGN = '\u00b5';
}

