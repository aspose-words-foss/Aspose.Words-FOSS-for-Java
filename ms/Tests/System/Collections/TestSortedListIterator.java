// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/03/2009 by Konstantin Sidorenko
// 2016/09/09 by Anatoliy Sidorenko

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********

import org.testng.annotations.Test;
import com.aspose.ms.System.Collections.SortedList;
import java.util.Map;
import org.testng.Assert;
import com.aspose.ms.NUnit.Framework.msAssert;
import com.aspose.ms.System.Text.msStringBuilder;
import java.util.Comparator;


@Test
public class TestSortedListIterator
{

    @Test
    public void testEmpty()
    {
        SortedList<String, Integer> list = new SortedList<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
            Assert.fail();

        list = getPopulated();
        list.clear();
        for (Map.Entry<String, Integer> entry : list)
            Assert.fail();
    }

    @Test
    public void testIteration()
    {
        SortedList<String, Integer> list = getPopulated();

        int i = 0;
        for (Map.Entry<String, Integer> entry : list)
        {
            msAssert.areEqual(entry.getKey(), keys[i]);
            msAssert.areEqual(entry.getValue(), values[i]);
            i++;
        }
    }

    /**
         * Try to iterate SortedList with Map.Entry as users will do this.
         */
    @Test
    public void testIterationMapEntry()
    {
        SortedList<String, Integer> list = getPopulated();
        int i = 0;
        for (java.util.Map.Entry entry : list)
        {
            msAssert.areEqual(entry.getKey(), keys[i]);
            msAssert.areEqual(entry.getValue(), values[i]);
            i++;
        }
    }

    //@todo 1 sk rework with new stringOrdinalIgnoreCaseComparer
    @Test
    public void testComparers()
    {
        SortedList<String, Integer> list = new SortedList<String, Integer>();
        addAll(list);
        Debug.writeLine("default(case-sensitive): " + listToString(list));

        list = new SortedList<String, Integer>(String.CASE_INSENSITIVE_ORDER);
	    setAll(list);
	    Debug.writeLine("java case-insensitive: " + listToString(list));

        list = new SortedList<String, Integer>(new CaseInsensitiveInvariantComparator());
        setAll(list);
        Debug.writeLine("Our case-insensitive comparator: " + listToString(list));
    }

    private String listToString(SortedList<String, Integer> list)
    {
        StringBuilder builder = new StringBuilder("\r\n");
        for (Map.Entry<String, Integer> entry : list)
        {
            msStringBuilder.append(msStringBuilder.append(msStringBuilder.append(builder, entry.getKey()), " => ").append(entry.getValue()), "\r\n");
        }
        return builder.toString();
    }

    private static class CaseInsensitiveInvariantComparator implements Comparator<String>
    {
        public int compare(String s1, String s2)
        {
            //Object equality and null string comparision used only in .Net. Taken from Rotor's String.Compare():
            //Our (.Net) paradigm is that null sorts less than any other string and
            //that two nulls sort as equal. (plus: two equal constant strings always stored in a single object).
            if ((Object)s1 == (Object)s2)
                return 0;
            if (s1 == null)
                return (-1);    // null < non-null
            if (s2 == null)
                return (1);     // non-null > null

            int n1 = s1.length(), n2 = s2.length();
            for (int i1 = 0, i2 = 0; i1 < n1 && i2 < n2; i1++, i2++)
            {
                char c1 = s1.charAt(i1);
                char c2 = s2.charAt(i2);

                //Case-insensitive comparison of ascii chars is simple (taken from Rotor COMString::CaseInsensitiveCompHelper).
                if ((c1 | c2) <= 0x7F)
                {
                    // uppercase both chars.
                    if (c1 >= 'a' && c1 <= 'z')
                        c1 = Character.toUpperCase(c1);
                    if (c2 >= 'a' && c2 <= 'z')
                        c2 = Character.toUpperCase(c2);

                    //Return the (case-insensitive) difference between them.
                    if (c1 != c2)
                        return c1 - c2;
                }
                //Non-ascii chars comparision. Added for compatibility. Code taken from java.lang.String.CaseInsensitiveComparator.
                else
                {
                    if (c1 != c2)
                    {
                        c1 = Character.toUpperCase(c1);
                        c2 = Character.toUpperCase(c2);
                        if (c1 != c2)
                        {
                            c1 = Character.toLowerCase(c1);
                            c2 = Character.toLowerCase(c2);
                            if (c1 != c2)
                                return c1 - c2;
                        }
                    }
                }
            }

            return n1 - n2;
        }
    }

    private SortedList<String, Integer> getPopulated()
    {
        SortedList<String, Integer> sl = new SortedList<String, Integer>();
        addAll(sl);

        return sl;
    }

    private void addAll(SortedList<String, Integer> sl)
    {
        for (int i = 0; i < keys.length; i++)
            sl.add(keys[i], values[i]);
    }

    private void setAll(SortedList<String, Integer> sl)
    {
        for (int i = 0; i < keys.length; i++)
            sl.set(keys[i], values[i]);
    }

    private String[] keys = { "0abc", "0абв", "ABC", "Abc", "Bcd", "_abc", "_абв", "abc", "abc0", "abc_", "bcd", "bcd0", "АБВ", "Абв", "БВГ", "Бвг", "абв", "бвг" };
    private int[] values = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

    private String[] Net_keys = { "_abc", "_абв", "0abc", "0абв", "abc", "Abc", "ABC", "abc_", "abc0", "bcd", "Bcd", "bcd0", "абв", "Абв", "АБВ", "бвг", "Бвг", "БВГ" };
    private int[] Net_values = { 5, 6, 0, 1, 7, 3, 2, 9, 8, 10, 4, 11, 16, 13, 12, 17, 15, 14 };
}

