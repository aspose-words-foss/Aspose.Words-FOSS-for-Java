// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/09/2012 by Alexey Butalov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import java.util.Comparator;
import com.aspose.DictionaryExtension;
import com.aspose.ms.System.msInt;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.StringComparison;
import com.aspose.ms.System.Collections.SortedList;


/// <summary>
/// Helps to sort CSS declarations in some preferable order.
/// </summary>
class CssPropertyNameComparer implements Comparator<String>
{
    public int compare(String x, String y)
    {
        int xOrderIndex = DictionaryExtension.getValueOrDefault(G_PROPERTY_ORDER_LIST, x, Integer.MAX_VALUE);
        int yOrderIndex = DictionaryExtension.getValueOrDefault(G_PROPERTY_ORDER_LIST, y, Integer.MAX_VALUE);

        if ((xOrderIndex != Integer.MAX_VALUE) || (yOrderIndex != Integer.MAX_VALUE))
            return msInt.compareTo(xOrderIndex, yOrderIndex);

        boolean xStartsWithAWPrefix = msString.startsWith(x, HtmlConstants.ASPOSE_VENDOR_CSS_PREFIX, StringComparison.ORDINAL);
        boolean yStartsWithAWPrefix = msString.startsWith(y, HtmlConstants.ASPOSE_VENDOR_CSS_PREFIX, StringComparison.ORDINAL);

        if (xStartsWithAWPrefix == yStartsWithAWPrefix)
        {
            // We use the InvariantCulture comparison here for historical reasons. This comparison differs from the ordinal
            // comparison in the way it handles hyphen ('-') characters. For example, the ordinal comparison places
            // the string "-webkit-box" before the string "border", while with the invariant culture comparison the order
            // of the strings is reverse: "border" then "-webkit-box". We want to preserve the order of properties,
            // because a lot of gold file will change otherwise.
            return msString.compare(x, y, StringComparison.INVARIANT_CULTURE);
        }

        // Our -aw-* CSS properties should be last.
        return xStartsWithAWPrefix ? -1 : 1;
    }

    private static /*final*/ SortedList<String, Integer> G_PROPERTY_ORDER_LIST;

    /// <summary>
    /// Here we keep some pleasant order to output CSS properties.
    /// If we miss anything here all remaining attributes will be in the end of declaration.
    /// </summary>
    private static /*final*/ String[] G_CSS_PROPERTY_ORDER =
        {
            "size", // for @page rules
            "width",
            "height",

            "margin",
            "margin-top",
            "margin-right",
            "margin-left",
            "margin-bottom",

            "text-indent",
            "text-align",
            "page-break-before",
            "page-break-inside",
            "page-break-after",
            "line-height",
            "widows",
            "orphans",
            "writing-mode",

            "border",
            "border-style",
            "border-width",
            "border-color",
            "border-top",
            "border-top-style",
            "border-top-width",
            "border-top-color",
            "border-right",
            "border-right-style",
            "border-right-width",
            "border-right-color",
            "border-left",
            "border-left-style",
            "border-left-width",
            "border-left-color",
            "border-bottom",
            "border-bottom-style",
            "border-bottom-width",
            "border-bottom-color",

            "padding",
            "padding-top",
            "padding-right",
            "padding-left",
            "padding-bottom",

            "font",
            "font-family",
            "font-size",
            "font-weight",
            "font-style",
            "font-variant",
            "text-decoration",
            "text-transform",
            "letter-spacing",
            "vertical-align",
            "color",
            "background",
            "background-color",
            "display",

            "list-style",
            "list-style-type",
            "list-style-image",
            "list-style-position",

            "src" // for @font-face rules
        };

    static/* CssPropertyNameComparer()*/
    {
        G_PROPERTY_ORDER_LIST = new SortedList<String, Integer>();
        int orderIndex = 0;
        for (String item : G_CSS_PROPERTY_ORDER)
            G_PROPERTY_ORDER_LIST.add(item, ++orderIndex);
    }
}

