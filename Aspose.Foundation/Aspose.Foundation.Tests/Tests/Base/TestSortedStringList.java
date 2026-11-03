// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 29/03/2009 by Konstantin Sidorenko
package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Text.msStringBuilder;
import org.testng.Assert;


@Test
public class TestSortedStringList
{
    @Test
    public void testDefaultSorting()
    {
        list = new SortedStringListGeneric<Integer>();
        addAll(list);
        //source key-values stored in default case-sensitive order.
        checkSorting(mValues);
    }

    @Test
    public void testDefaultSortingCustomValues()
    {
        list = new SortedStringListGeneric<Integer>();
        addAllCustom(list);
        checkSorting(mCustomSensitive);
    }

    @Test
    public void testInsensitiveSorting()
    {
        list = new SortedStringListGeneric<Integer>(false);
        //we have here duplicate keys from case-insensitive point of view - so using set instead of add.
        setAll(list);
        checkSorting(mInSensitiveValues);
    }

    @Test
    public void testInsensitiveSortingCustomValues()
    {
        list = new SortedStringListGeneric<Integer>(false);
        addAllCustom(list);
        checkSorting(mCustomSensitive);
    }

    private void addAll(SortedStringListGeneric<Integer> sl)
    {
        for (int i = 0; i < mKeys.length; i++)
            sl.add(mKeys[i], mValues[i]);
    }

    private void addAllCustom(SortedStringListGeneric<Integer> sl)
    {
        for (int i = 0; i < mCustomKeys.length; i++)
            sl.add(mCustomKeys[i], mCustomValues[i]);
    }

    //added for insensitive comparers since add() throws on doubles.
    private void setAll(SortedStringListGeneric<Integer> sl)
    {
        for (int i = 0; i < mKeys.length; i++)
            sl.set(mKeys[i], mValues[i]);
    }

    private int[] getArrayOfValues()
    {
        int[] values = new int[list.getCount()];
        for (int i = 0; i < list.getCount(); i++)
            values[i] = (int)list.getByIndex(i);
        return values;
    }

    private void checkSorting(int[] expectedValues)
    {
        int[] sortedValues = getArrayOfValues();
        if (!ArrayUtil.isArrayEqual(sortedValues, expectedValues))
        {
            StringBuilder sb = new StringBuilder();
            msStringBuilder.append(sb, "\r\nsorted\t: expected\r\n");
            msStringBuilder.append(sb, "----------:---------\r\n");
            for (int i = 0; i < expectedValues.length; i++)
                msStringBuilder.appendFormat(sb, "{0} -> {1} : {2}\r\n", list.getKey(i), sortedValues[i], expectedValues[i]);

            Assert.fail(sb.toString());
        }
    }

    private SortedStringListGeneric<Integer> list;
    private String[] mKeys = { "/ABC", "/Abc", "0abc", "0абв", "ABC", "Abc", "Bcd", "\\ABC", "\\Abc", "_abc", "_абв", "abc", "abc0", "abc_", "bcd", "bcd0", "АБВ", "Абв", "БВГ", "Бвг", "абв", "бвг" };
    private int[] mValues = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
    //case-insensitive values: doubles (from case-insensitive point of view) are removed by sorted list.
    private int[] mInSensitiveValues = { 1, 2, 3, 11, 12, 13, 14, 15, 8, 9, 10, 20, 21 };
    private String[] mCustomKeys = { "DN_Bank", "DN_BankKontoNr", "DN_BankRegNr", "DN_By", "DN_CVR", "DN_DNKundeKontaktPerson", "DN_DokumentNummer",
                               "DN_EAN", "DN_KundeAdresse", "DN_KundeNavn", "DN_OprettetAfPrimærEmail", "DN_Version", "DNKontraktDato",
                               "DNKundeKontaktMail", "DNSælger", "INKundeKontaktTlf", "INSælger", "www" };
    private int[] mCustomValues = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
    //default(case-sensitive) sorting of custom values
    private int[] mCustomSensitive = { 12, 13, 14, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 15, 16, 17 };
}

