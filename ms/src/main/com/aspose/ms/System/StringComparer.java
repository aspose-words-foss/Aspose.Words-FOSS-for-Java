package com.aspose.ms.System;

import java.util.Comparator;

public class StringComparer
{
    public static Comparator<String> getOrdinal()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.ORDINAL);
            }
        };
    }

    public static Comparator<String> getOrdinalIgnoreCase()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.ORDINAL_IGNORE_CASE);
            }
        };
    }

    public static Comparator<String> getCurrentCulture()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.CURRENT_CULTURE);
            }
        };
    }

    public static Comparator<String> getCurrentCultureIgnoreCase()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.CURRENT_CULTURE_IGNORE_CASE);
            }
        };
    }

    public static Comparator<String> getInvariantCulture()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.INVARIANT_CULTURE);
            }
        };
    }

    public static Comparator<String> getInvariantCultureIgnoreCase()
    {
        return new Comparator<String>()
        {
            @Override
            public int compare(String a, String b)
            {
                return msString.compare(a, b, StringComparison.INVARIANT_CULTURE_IGNORE_CASE);
            }
        };
    }
}