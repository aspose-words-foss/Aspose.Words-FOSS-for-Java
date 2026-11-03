// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/04/2014 by Pavel Gorbunov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import java.util.Comparator;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;


@Test
public class TestMemoryStorageNameComparer
{
    @Test
    public void testBasic()
    {
        Comparator<String> comparer = new MemoryStorageNameComparer();

        msAssert.areEqual(0, comparer.compare("1Table", "1Table"));
        msAssert.areEqual(0, comparer.compare("1table", "1Table"));
        msAssert.areEqual(-1, comparer.compare("Item", "Properties"));

        // String.Compare() incorrectly compares some special characters like 0x01, 0x03 and 0x05 that occur in stream names.
        msAssert.areEqual(0, comparer.compare("\u0001CompObj", "\u0001COMPObj"));
        msAssert.areEqual(-2, comparer.compare("\u0001CompObj", "\u0003CompObj"));
        msAssert.areEqual(-1, comparer.compare("\u0001CompObj", "MsoDataStore"));
        msAssert.areEqual(-2, comparer.compare("\u0001\u0003\u0005", "\u0001\u0005\u0003"));

        // The underscore character is treated as greater than all alpha (latin only) chars.
        msAssert.areEqual(30, comparer.compare("_a", "a_"));

        // "ß".ToUpper() gives "ß" in .NET, whereas "ß".ToUpper() gives "SS" in Java,
        // so we refused from String.ToUpper()
        msAssert.areEqual(19, comparer.compare("TßÄÒXÓÛRPEKÁÅÑÛÝCEUGÇÐ==", "TÌÛNÇØ25ÐÄÎÄOÝEOXÏÊNQQ=="));
        msAssert.areEqual(-12, comparer.compare("ÄÄßBJIÏÚ×UÒFVÓJYVÝÎÍSQ==", "ÄÐÆPIÑÒÒÝÄKHÐÑ2XTU01ÉÀ=="));
        msAssert.areEqual(110, comparer.compare("ÆÃÂCNÈ1ÒTÔOÐÅØ21ÙXRJßÐ==", "XÉÁÇÌÐÕÛCÔÖØÀFLÇÁÀWSÆÀ=="));
        msAssert.areEqual(0, comparer.compare("ËØKOMÐGØYEW2ÝWFÛOWÉÎÉQ==", "ËØKOMÐGØYEW2ÝWFÛOWÉÎÉQ=="));
        msAssert.areEqual(-138, comparer.compare("ÌBÄY2ÂGFÜEÆWÛLÒLÆZÒG3À==", "ÌÌÆGNHÜYÂU0OBÀÀÅÚÌHKÊÀ=="));
        msAssert.areEqual(-2, comparer.compare("ÙÌQÆÆJÚÎÀÔ0ßÚTOEÛÁÎEÌÀ==", "ÛWTÃSÈUCÌUÆÖÜYDÀÑÁÙIÛQ=="));
    }
}

